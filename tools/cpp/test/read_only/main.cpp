#include <fstream>
#include <iostream>
#include <cstdio>
using namespace std;

int main()
{
//	fstream ifs = fstream("d:\\yt\\readonly_test\\in.txt", ios_base::in|ios_base::out);
	fstream ifs = fstream("d:\\yt\\readonly_test\\in.txt", ios_base::in);
	if (!ifs.is_open()) {
		cout << "can't open" << endl;
		system("pause");
		return 0;
	}
	char a;
	int sum = 0;
	//system("pause");
	while (!ifs.eof()) {
		ifs >> a;
		sum+=a;
		cout << a << endl;
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