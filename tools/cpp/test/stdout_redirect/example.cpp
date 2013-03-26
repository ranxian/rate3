#include <windows.h>
#include <tchar.h>
#include <stdio.h>

int _tmain(int argc, _TCHAR* argv[])
{
	wchar_t cmdline[] = L"\"c:\\windows\\system32\\netstat.exe\"";
	BOOL ok;
	DWORD error;
	STARTUPINFO si;
	PROCESS_INFORMATION pi;

	ZeroMemory( &si, sizeof(si) );
	si.cb = sizeof(si);

	SECURITY_ATTRIBUTES sec;
	ZeroMemory( &sec, sizeof(sec) );
	sec.nLength = sizeof(SECURITY_ATTRIBUTES);
	sec.lpSecurityDescriptor = NULL;    
	sec.bInheritHandle = TRUE;
	HANDLE hstdoutf = CreateFile(L"D:\\user_stdout.txt",
		GENERIC_WRITE,
		FILE_SHARE_READ|FILE_SHARE_WRITE,
		&sec,
		CREATE_ALWAYS,
		FILE_ATTRIBUTE_NORMAL,
		NULL);

	if (hstdoutf!=INVALID_HANDLE_VALUE) {
		si.dwFlags |= STARTF_USESTDHANDLES;
		si.hStdInput = GetStdHandle(STD_INPUT_HANDLE);
		si.hStdOutput = hstdoutf;
		si.hStdError = GetStdHandle(STD_ERROR_HANDLE);
	}

	ok = CreateProcess(
		NULL,   // No module name (use command line)
		const_cast<wchar_t*>(cmdline),        // Command line
		NULL,           // Process handle not inheritable
		NULL,           // Thread handle not inheritable
		TRUE,          // Set handle inheritance to TRUE
		CREATE_NO_WINDOW | CREATE_BREAKAWAY_FROM_JOB , 
		NULL,           // Use parent's environment block
		NULL,           // Use parent's starting directory 
		&si,            // Pointer to STARTUPINFO structure
		&pi           // Pointer to PROCESS_INFORMATION structure
		);

	if (!ok)
	{
		error = GetLastError();
		printf("CreateProcess failed with error %lx\n", error);
	}
	else
	{
		CloseHandle(hstdoutf);
		// Wait until child process exits.
		WaitForSingleObject( pi.hProcess, INFINITE );
	}

	return !ok;	// so that it returns 0 on success
}