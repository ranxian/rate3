#ifndef SANDBOXRUN_H
#define SANDBOXRUN_H

//#define DEBUG

#include <winsock2.h>
#pragma comment(lib, "Ws2_32.lib")
#include <iostream>
#include <wchar.h>
#include <Windows.h>
#include <stdlib.h>
#include <tchar.h>
#include <stdio.h>
#include <ctime>
#include <iostream>
#include <tchar.h>
#include <iostream>
#include <fstream>
#include <wchar.h>
#include <atlstr.h>
#include <Strsafe.h>
#include <intrin.h>
#include <winuser.h>

// Windows Process Status Api
#pragma comment(lib, "Psapi.lib")
#define DPSAPI_VERSION (1)

using namespace std;

//#ifndef DEBUG
#define RUN_IN_ALTERNATE_DESKTOP // 不显示子进程产生的图形界面
#define ALTERNATE_DESKTOP_NAME "sandbox_run_desktop"
//#endif 

typedef struct _ACCOUNTING{
	unsigned int user_time; // ms
	unsigned int kernel_time;
	SIZE_T max_mem; // byte
	int return_value;
} ACCOUNTING, *PACCOUNTING;

HANDLE MakeOutputFileHandle(HANDLE* h, wchar_t* filename);
int SetUpLog(const char* path);
DWORD run(unsigned int timelimit_ms, SIZE_T memlimit, const char* cmdline, PACCOUNTING accounting, HANDLE user_stdout, HANDLE user_stderr);
int LogPerformance(unsigned int timelimit_ms, SIZE_T memlimit, const char* cmd, const char* args, const ACCOUNTING &accounting, const char* filename);
void LogError(DWORD dwError);

#define BUFSIZE 4096

#endif