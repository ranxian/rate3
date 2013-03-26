// guest_run.exe prog [prog_args]
// return 0 on success, -1 on failure
// Ҫ��
// 1. ����Guest�˻���
// 2. Guest����ִ�еĳ�����ж���ִ��Ȩ�ޣ����Ըó��������ļ�������ӦȨ�ޣ���
// 3. ��ִ�г���������������Ҳ��Ҫ��Guest��Ȩ�޵��ļ����¡�

#include <iostream>
#include <tchar.h>
//#include <Windows.h>
//#include <atlconv.h>

#include "../rate_run/rate_run.h" // FIXME: bad style

using namespace std;

int run_as_guest(const char *cmdline)
{
	USES_CONVERSION;

	cerr << "cmdline: " << cmdline << endl;

	STARTUPINFO si;
	PROCESS_INFORMATION pi;
	ZeroMemory( &si, sizeof(si) );
	si.cb = sizeof(si);
	ZeroMemory(&pi, sizeof(pi));

	// redirect output
	HANDLE hstdoutf = NULL, hstderrf = NULL;
	si.dwFlags |= STARTF_USESTDHANDLES;
	si.hStdInput = GetStdHandle(STD_INPUT_HANDLE);
	MakeOutputFileHandle(&hstdoutf, "guest_stdout.txt");
	MakeOutputFileHandle(&hstderrf, "guest_stderr.txt");
	si.hStdOutput = hstdoutf;
	si.hStdError = hstderrf;

	wchar_t cwd[MAX_PATH];
	_tgetcwd(cwd, MAX_PATH);
	cerr << "current_dir is: " << T2A(cwd) << endl;
	if( !CreateProcessWithLogonW(
		L"Guest",
		L"localhost", 
		L"",
		0,
		NULL,  
		(LPWSTR)A2T(cmdline),
		0,
		NULL,
		cwd, //NULL, //L"C:\\GuestRunDir",
		&si,   
		&pi )  
		)
	{
		int e = GetLastError();
		cerr << "CreateProcessWithLogonW Failed with error: " << e << endl;
		LogError(e);
		return -1;
	}

	WaitForSingleObject(pi.hProcess, INFINITE);	

	return 0;
}

int main(int argc, char* argv[])
{
	if (argc<2) {
		cout << "Usage: " << argv[0] << " prog [prog_args]" << endl;
	}
	char cmdline[65535] = "\0"; 
	for (int i=1; i<argc; i++) {
		strcat(cmdline, argv[i]);
		strcat(cmdline, " ");
	}
	return run_as_guest(cmdline);
}