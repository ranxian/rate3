#include <windows.h>
#include <iostream>
using namespace std;

int main()
{
	wchar_t cmdline[] = L"time_exceed.exe";
	STARTUPINFO si;
	PROCESS_INFORMATION pi;

	ZeroMemory( &si, sizeof(si) );
	si.cb = sizeof(si);
	int flag;
	for (int i=0; i<2; i++) {
		//sandbox_run是不允许breakaway_from_job的，如果使用此参数，会发生access_denied(5)错误
		Sleep(500);
		flag = CreateProcess(
			NULL,   // No module name (use command line)
			const_cast<wchar_t*>(cmdline),        // Command line
			NULL,           // Process handle not inheritable
			NULL,           // Thread handle not inheritable
			TRUE,          // Set handle inheritance to TRUE		
			//CREATE_BREAKAWAY_FROM_JOB, 
			0,
			NULL,           // Use parent's environment block
			NULL,           // Use parent's starting directory 
			&si,            // Pointer to STARTUPINFO structure
			&pi           // Pointer to PROCESS_INFORMATION structure
			);
		if (!flag) {
			cout << "error:" << GetLastError();
			cout << "error:" << GetLastError();
		}
		cout << "child:" << pi.dwProcessId << endl;
	}

	while(1);

	system("pause");

	return 0;	
}