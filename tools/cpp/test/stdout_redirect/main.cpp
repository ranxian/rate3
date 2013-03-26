#include <tchar.h>
#include <Windows.h>

#include <iostream>
using namespace std;

#define BUFSIZE 4096

int _tmain1(int argc, TCHAR* argv[])
{
	wchar_t cmdline[255];
	ZeroMemory( &cmdline, sizeof(cmdline) );
	wcscat_s(cmdline, argv[1]);

	SECURITY_ATTRIBUTES sec;
	sec.nLength = sizeof(SECURITY_ATTRIBUTES);
	sec.lpSecurityDescriptor = NULL;	
	sec.bInheritHandle = TRUE;

	HANDLE hstdout_rd = NULL;
	HANDLE hstdout_wr = NULL;
	//HANDLE hstderr_rd = NULL;
	//HANDLE hstderr_wr = NULL;

	if (!CreatePipe(&hstdout_rd, &hstdout_wr, &sec, 0)) {
		cout << "shit" << endl;
	}

	// create the process
	STARTUPINFO si;	
	ZeroMemory( &si, sizeof(si) );
	si.cb = sizeof(si);
	si.hStdInput = GetStdHandle(STD_INPUT_HANDLE);
	si.hStdOutput = hstdout_wr;
	si.hStdError = GetStdHandle(STD_ERROR_HANDLE);
	si.dwFlags = STARTF_USESTDHANDLES;

	PROCESS_INFORMATION pi;
	ZeroMemory( &pi, sizeof(pi) );

	if( !CreateProcess(
		NULL,   // No module name (use command line)
		const_cast<wchar_t*>(cmdline),        // Command line
		NULL,           // Process handle not inheritable
		NULL,           // Thread handle not inheritable
		TRUE,          // Set handle inheritance to TRUE
		0,
		NULL,           // Use parent's environment block
		NULL,           // Use parent's starting directory 
		&si,            // Pointer to STARTUPINFO structure
		&pi )           // Pointer to PROCESS_INFORMATION structure
		)
	{
		printf("failed to create process %d\n", GetLastError());
		return -2;
	}

	// run and wait for the process
	ResumeThread(pi.hThread);
	CloseHandle(pi.hThread);

	WaitForSingleObject(pi.hProcess, INFINITE);
	CloseHandle(pi.hProcess);

	BOOL success = true;
	char buf[BUFSIZE];
	DWORD read, written;
	CloseHandle(hstdout_wr);

	HANDLE outf = CreateFile(L"D:\\user_stdout.txt",
		GENERIC_WRITE,
		FILE_SHARE_READ|FILE_SHARE_WRITE,
		NULL,
		OPEN_EXISTING,
		FILE_ATTRIBUTE_NORMAL,
		NULL);
	SetFilePointer(outf, 0L, NULL, FILE_END);

	while (success) {
		success = ReadFile(hstdout_rd, buf, BUFSIZE, &read, NULL);
		if (!success || read==0) break;

		success = WriteFile(outf, buf, read, &written, NULL);
	}
	CloseHandle(outf);

	return 0;
}