#include <fstream>
#include <iostream>
#include <ctime>
using namespace std;

int main()
{
	ofstream ofs = ofstream("D:\\yt\\write_speed_test.txt", ios_base::out);
	char a='c';
	clock_t start = clock();
	for (int i=0; i<100000; i++) {
		for (int j=0; j<1000; j++) {
			ofs << a;
		}
	}
	clock_t end = clock();
	ofs.close();
	cout << end-start << endl;
	system("pause");
}