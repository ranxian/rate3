// usage:
// run.exe timelimit(ms) memlimit(b) prog [args]
// returns
//	0:	success
//	-1:	incorrect usage
//	-2: internal error
//
// append perf.txt 程序运行效率
// [cmd] [args] [return value] [time] [mem]
// run result:
//	0: success
//	1: time limit exceeded
//	2: memory limit exceeded
//	3: runtime error
//
// append prog_stdout.txt 用户程序的stdout
// append prog_stderr.txt 用户程序的stderr

#include "rate_run.h"

using namespace std;

// return 0 on success 
// cmd contains the executable path 
// args conatins a string of arguments 
// cmdline contains concat of above
int ParseCommandLine(int argc, char* argv[], char *cmd, char *args, char *cmdline)
{
	ZeroMemory(cmd, sizeof(cmd));
	ZeroMemory(args, sizeof(args));
	ZeroMemory(cmdline, sizeof(cmdline));
	strcpy(cmd, argv[0]);
	for (int i=1; i<argc; i++) {
		strcat(args, argv[i]);
		if (i==argc-1) break;
		strcat(args, " ");
	}
	strcat(cmdline, cmd);
	strcat(cmdline, " ");
	strcat(cmdline, args);

	return 0;
}

int main(int argc, char* argv[])
{	
	USES_CONVERSION;

	wchar_t cwd[MAX_PATH];
	_tgetcwd(cwd, MAX_PATH);
	//cerr << "current_dir is: " << T2A(cwd) << endl;

	SetErrorMode(0xFFFF);

	if (argc<4) {
		cerr << "Usage: " << argv[0] << " timelimit(ms) memlimit(byte) [command line]" << endl;
		return 0;
	}	
	//cerr << "started" << endl;

	unsigned int timelimit_ms = atoi(argv[1]);
	SIZE_T memlimit = atoi(argv[2]);
	char cmd[MAX_PATH], args[MAX_PATH], cmdline[MAX_PATH];
	char stdoutPath[MAX_PATH], stderrPath[MAX_PATH], purfPath[MAX_PATH];
	//strcpy(stdoutPath, argv[3]);
	//strcpy(stderrPath, argv[4]);
	strcpy(purfPath, "rate_run_perf.txt");

	ParseCommandLine(argc-3, &argv[3], cmd, args, cmdline);
	cerr << cmdline << endl;
	cerr << "timelimit:" << timelimit_ms << "ms memlimit:" << memlimit << "byte(" << memlimit/1024 << "kb)" << endl;
	
	ACCOUNTING accounting;
	ZeroMemory(&accounting, sizeof(accounting));

	//HANDLE hstdoutf = NULL, hstderrf = NULL;
	//MakeOutputFileHandle(&hstdoutf, stdoutPath);
	//MakeOutputFileHandle(&hstderrf, stderrPath);	

	//////////
	DWORD runresult;
	runresult = run(timelimit_ms, memlimit, cmdline, &accounting, GetStdHandle(STD_OUTPUT_HANDLE), NULL);
	//////////
	
	if (runresult!=0) {
		accounting.return_value = runresult;
		cerr << "error during execution, client returns " <<  runresult << endl;
		LogError(runresult);
	}
	
	//LogPerformance(timelimit_ms, memlimit, cmd, args, accounting, purfPath);	

	//LOG(INFO) << "finished" << endl; 

	return runresult;
}