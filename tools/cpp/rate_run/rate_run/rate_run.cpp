//// windows 7 ��Ҫ�ر������鱨����񣬷��򵯴�����
//// �������->ϵͳ�Ͱ�ȫ->��������->�����Ĳ�����������->�·����ⱨ������->�Ӳ�
//// services.msc Windows Error Reporting Service ��Ϊ����
// ����: ����SetErrorMode(0xFFFF)��ͳ��װ����д��󱨸涼ȥ���ˡ�


#include "rate_run.h"

#include <psapi.h>
#include <iostream>

using namespace std;

HANDLE GetDesktop()
{
	HANDLE d;
	d = CreateDesktop(
		TEXT(ALTERNATE_DESKTOP_NAME),
		NULL, 
		NULL, 
		0,
		GENERIC_ALL,
		NULL
		);
	if (d==NULL) {
		cerr << "Failed to CreateDesktop with error: " << GetLastError() << endl;
	};
	return d;
}


DWORD run(unsigned int timelimit_ms, SIZE_T memlimit, const char* cmdline, PACCOUNTING paccounting, HANDLE user_stdout, HANDLE user_stderr)
{		
	USES_CONVERSION;

#ifdef RUN_IN_ALTERNATE_DESKTOP
	HANDLE hdesktop = GetDesktop();
#endif

	// 1 second = 1000 millisecond 
	// 1 millisecond = 1000 microsecond 
	// 1 microsecond = 1000 nanosecond

	LARGE_INTEGER timelimit = {timelimit_ms, 0};
	timelimit.QuadPart *= 10000;

	// create a job object
	HANDLE job = CreateJobObject(NULL, NULL);
	if (!job) {
		int err = GetLastError();
		cerr << "Failed to CreateJobObject with error: " << err << endl;
		return err;
	}
	cerr << "Job created" << endl;

	// set limits to the job object
	JOBOBJECT_BASIC_ACCOUNTING_INFORMATION jobobject_accounting;	
	JOBOBJECT_EXTENDED_LIMIT_INFORMATION extendedlimit;
	extendedlimit.BasicLimitInformation.LimitFlags = JOB_OBJECT_LIMIT_JOB_TIME | JOB_OBJECT_LIMIT_JOB_MEMORY | JOB_OBJECT_LIMIT_PROCESS_TIME | JOB_OBJECT_LIMIT_DIE_ON_UNHANDLED_EXCEPTION;
	extendedlimit.BasicLimitInformation.PerJobUserTimeLimit = timelimit;
	extendedlimit.BasicLimitInformation.PerProcessUserTimeLimit = timelimit;
	extendedlimit.JobMemoryLimit = memlimit;
	if (!SetInformationJobObject(job, JobObjectExtendedLimitInformation, &extendedlimit, sizeof(extendedlimit))) {
		int err = GetLastError();
		cerr << "Failed to SetInformationJobObject with error: " << err << endl;
		return err;
	}
	cerr << "Job limited" << endl;

	JOBOBJECT_BASIC_UI_RESTRICTIONS uirestriction = { JOB_OBJECT_UILIMIT_ALL };

	// create the process
	STARTUPINFO si;
	PROCESS_INFORMATION pi;
	ZeroMemory( &si, sizeof(si) );
	si.cb = sizeof(si);
	si.dwFlags = 0;

#ifdef RUN_IN_ALTERNATE_DESKTOP
	si.lpDesktop = L"sandbox_run_desktop"; // run in another desktop so no window will appear
#endif

	si.dwFlags |= STARTF_USESTDHANDLES;
	si.hStdInput = GetStdHandle(STD_INPUT_HANDLE);
	si.hStdOutput = user_stdout;
	si.hStdError = user_stderr;

	ZeroMemory( &pi, sizeof(pi) );	

	SetErrorMode(0xFFFF); // important! �ӳ������exception��ʱ�򲻻��ٵ���

	wchar_t cwd[MAX_PATH];
	_tgetcwd(cwd, MAX_PATH);

	// CreateProcessWithLogonW��svchost.exe�����½��̣�����Ĭ��assign to a job��Ȼ��������̾��޷���assign���Զ����job
	if( !CreateProcess(
		NULL,   // No module name (use command line)
		const_cast<wchar_t*>(A2T(cmdline)),        // Command line
		NULL,           // Process handle not inheritable
		NULL,           // Thread handle not inheritable
		TRUE,          // Set handle inheritance to TRUE
		CREATE_BREAKAWAY_FROM_JOB | CREATE_SUSPENDED,              
		// CREATE_BREAKAWAY_FROM_JOB so that the child is not in the same job that this process is in, so that we can assign the child to the job object previously created.
		NULL,           // Use parent's environment block
		NULL,           // Use parent's starting directory 
		&si,            // Pointer to STARTUPINFO structure
		&pi )           // Pointer to PROCESS_INFORMATION structure
		)
	{
		int err = GetLastError();
		cerr << "Failed to CreateProcess with error: " << err << endl;
		return err;
	}	
			
	cerr << "Process created with pid: " << pi.dwProcessId << endl;

	// associate the process with the job
	if (!AssignProcessToJobObject(job, pi.hProcess)) {
		int err = GetLastError();
		cerr << "Failed to AssignProcessToJobObject: " << err << endl;
		return err;
	}
	cerr << "Process assigned to job" << endl;

	// run and wait for the process	
	ResumeThread(pi.hThread);
	CloseHandle(pi.hThread);
	
	cerr << "Process running" << endl;

	// job object����ʱ��Ŀ��ƺ���������3s���ҵ���ʱ�����Ի���Ҫ�Լ�����
	// �����ʹ���ڴ�ļ���ҲҪ�Լ�����
	unsigned int starttime = clock();
	DWORD exitcode = STILL_ACTIVE;
	
	//GetExitCodeProcess(pi.hProcess, &exitcode); �����Ѿ������Ľ�����TerminateProcessҲû����
	while (exitcode==STILL_ACTIVE) {
		// ��ʱ��ļ�����ֹ������job�Ͻ��У������process��ֹ�ˣ���ʹ�仹���ӽ��̣�Ҳ������ѭ����������ٴμ��job״̬
		// �������ʱ�䣬�Ͽ��ɣ���timelimit_ms������
		clock_t clockTime = clock() - starttime;
		if (clockTime > (timelimit_ms)*2) {
			cerr << "clock time limit exceed" << endl;
			TerminateJobObject(job, ERROR_NOT_ENOUGH_QUOTA);
			WaitForSingleObject(pi.hProcess, INFINITE);
		}
		// ���user cpu time���ϸ�
		else {
			if (!QueryInformationJobObject(job, JobObjectBasicAccountingInformation, &jobobject_accounting, sizeof(jobobject_accounting), NULL)) {
				int err = GetLastError();
				cerr << "QueryInformationJobObject error: " << err << endl;
				return err;
			}
			if (jobobject_accounting.TotalUserTime.QuadPart/10000 > timelimit_ms) {
				cerr << "user time limit exceed" << endl;
				TerminateJobObject(job, ERROR_NOT_ENOUGH_QUOTA);
				WaitForSingleObject(pi.hProcess, INFINITE);
			}
		}
		Sleep(100);
		if (!GetExitCodeProcess(pi.hProcess, &exitcode)) {
			int err = GetLastError();
			cerr << "GetExitCodeProcess error: " << err << endl;
			return err;
		}		
		cerr << "clock time: " << clockTime << " user time:" << jobobject_accounting.TotalUserTime.QuadPart/10000 << endl;
	}
	cerr << "Process terminated" << endl;
	//Sleep(1000);
	if (exitcode!=0 && GetLastError()!=0) {		
		exitcode = GetLastError();
	}
	cerr << "Process return: " << exitcode << endl;
	
	// terminate the job����ֹ�����ӽ���û�н���
	while(true) {
		if (!QueryInformationJobObject(job, JobObjectBasicAccountingInformation, &jobobject_accounting, sizeof(jobobject_accounting), NULL)) {
			int err = GetLastError();
			cerr << "QueryInformationJobObject error: " << err << endl;
			return err;
		}
		if (jobobject_accounting.ActiveProcesses == 0) {
			break;
		}
		cerr << "User process exit, but it's subprocesses are still running" << endl;
		if (!TerminateJobObject(job, 0)) {
			int err = GetLastError();
			cerr << "TerminateJobObject error: " << err << endl;
			return err;
		}
	}
	cerr << "Job terminated" << endl;

	// accounting
	paccounting->kernel_time = (int)(jobobject_accounting.TotalKernelTime.QuadPart / 10000);
	paccounting->user_time = (int)(jobobject_accounting.TotalUserTime.QuadPart / 10000);
	paccounting->return_value = exitcode;
	PROCESS_MEMORY_COUNTERS pmc;
	if (!GetProcessMemoryInfo(pi.hProcess, &pmc, sizeof(pmc))) {
		int err = GetLastError();
		cerr << "GetProcessMemoryInfo error: " << err << endl;
		return err;
	}
	paccounting->max_mem = pmc.PeakWorkingSetSize;

	// cleanup
	CloseHandle(pi.hProcess);
	CloseHandle(job);


#ifdef RUN_IN_ALTERNATE_DESKTOP
	CloseHandle(hdesktop);
#endif

	return exitcode;
}

int LogPerformance(unsigned int timelimit_ms, SIZE_T memlimit, const char* cmd, const char* args, const ACCOUNTING &accounting, const char* filename)
{

	ofstream wofs(filename, ios_base::app);
	wofs << "[" << timelimit_ms << "]" << " ";
	wofs << "[" << memlimit << "]" << " ";
	wofs << "[" << cmd << "]" << " ";
	wofs << "[" << args << "]" << " ";
	wofs << "[" << accounting.return_value << "]" << " ";
	wofs << "[" << accounting.kernel_time << "]" << " ";
	wofs << "[" << accounting.user_time << "]" << " ";
	wofs << "[" << accounting.max_mem << "]" << " ";
	wofs << endl;
	wofs.close();

	return 0;
}

HANDLE MakeOutputFileHandle(HANDLE* h, const char* filename)
{
	USES_CONVERSION;

	SECURITY_ATTRIBUTES sec;
	sec.nLength = sizeof(SECURITY_ATTRIBUTES);
	sec.lpSecurityDescriptor = NULL;    
	sec.bInheritHandle = TRUE;
	*h = CreateFile(A2T(filename),
		GENERIC_WRITE,
		FILE_SHARE_READ|FILE_SHARE_WRITE,
		&sec,
		OPEN_ALWAYS,
		FILE_ATTRIBUTE_NORMAL,
		NULL);
	if (*h==INVALID_HANDLE_VALUE) {
		cerr << "CreateFile error: " << GetLastError() << endl;
		return *h;
	}
	SetFilePointer(*h, 0L, NULL, FILE_END);
	return *h;
}

void LogError(DWORD dwError)
{
	LPVOID lpMsgBuf;
	DWORD dw = dwError; 

	if (!FormatMessage(
		FORMAT_MESSAGE_ALLOCATE_BUFFER | 
		FORMAT_MESSAGE_FROM_SYSTEM |
		FORMAT_MESSAGE_IGNORE_INSERTS,
		NULL,
		dw,
		MAKELANGID(LANG_ENGLISH, SUBLANG_DEFAULT),
		(LPTSTR) &lpMsgBuf,
		0, NULL ))
	{
		cerr << "LogError Message: " << ": can't recognize error " << dwError;
		return;
	};
	
	// Display the error message and exit the process
	USES_CONVERSION;
	cerr << "Error Message: " << ": " << T2A((LPCWSTR)lpMsgBuf) << endl;
}