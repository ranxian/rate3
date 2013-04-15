#include "Python.h"
#include "../rate_run/rate_run.h"
#include <iostream>
using namespace std;

static PyObject *
py_rate_run_main(PyObject *self, PyObject *args)
{
	int timelimit, memlimit;
	char *cmdline;
	PyArg_ParseTuple(args, "iis", &timelimit, &memlimit, &cmdline);
	//cerr << timelimit << endl;
	//cerr << memlimit << endl;
	cerr << cmdline << endl;
	//return Py_None;

	int returncode;
	char stdout_buf[BUFSIZE];
	//cout << main_argc << endl;
	//cout << main_args << endl;
	rate_run_main(timelimit, memlimit, cmdline, returncode, stdout_buf);
	//cout << returncode << endl;
	//cout << stdout_buf << endl;
	
	return Py_BuildValue("is", returncode, stdout_buf);
}

static PyMethodDef rate_run_methods[] = {
	{"rate_run_main", py_rate_run_main, METH_VARARGS, "foo() doc string"},
	{NULL, NULL}
};

PyMODINIT_FUNC
initrate_run(void)
{
	Py_InitModule("rate_run", rate_run_methods);
}
