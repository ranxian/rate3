#include <fstream>
#include <iostream>
#include <cstdio>
using namespace std;

int main()
{
	ifstream ifs = ifstream("in.txt", ios_base::in);
	int a;
	int sum = 0;
	while (!ifs.eof()) {
		ifs >> a;
		sum+=a;
	}
	ifs.close();
	ofstream ofs = ofstream("out.txt", ios_base::out);
	ofs << sum << endl;
	ofs.close();
	ofs = ofstream("append.txt", ios_base::app);
	ofs << sum << endl;
	//remove("out.txt");
	system("pause");
}