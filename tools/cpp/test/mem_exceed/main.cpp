#include <iostream>
#include <windows.h>
#include <psapi.h>

#pragma comment( lib, "Psapi.lib" )
#define DPSAPI_VERSION (1)

using namespace std;

int main(int argc, char* argv[])
{

	HANDLE h = GetCurrentProcess();
	if (h==NULL) {
		cout << "failed GetCurrentProcess" << endl;
	}

	int n = 100*1024;
	while (n--) {
		PROCESS_MEMORY_COUNTERS pmc;
		if (!GetProcessMemoryInfo(h, &pmc, sizeof(pmc))) {
			// TODO 
			return GetLastError();
		}
		cout << "PageFaultCount: " << pmc.PageFaultCount << " PeakPageFileUsage: " << pmc.PeakPagefileUsage << " PeakWorkingSetSize: " << pmc.PeakWorkingSetSize << " WorkingSetSize: " << pmc.WorkingSetSize << endl;
		char *a = new char[1024];
		for (int i=0; i<1024; i++)
			a[i] = 'a';
		//Sleep(10);
	}
	return 0;
}