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


int main(int argc, char* argv[])
{	
	if (argc<4) {
		cerr << "Usage: " << argv[0] << " timelimit(ms) memlimit(byte) [command line]" << endl;
		return 0;
	}

	char cmd[MAX_PATH], args[MAX_PATH], cmdline[MAX_PATH];
	unsigned int timelimit_ms = atoi(argv[1]);
	SIZE_T memlimit = atoi(argv[2]);
	ParseCommandLine(argc-3, &argv[3], cmd, args, cmdline);

	int returncode;
	char stdout_buf[BUFSIZE];
	rate_run_main(timelimit_ms, memlimit, cmdline, returncode, stdout_buf);
	cout << stdout_buf << endl;
	return returncode;
}

