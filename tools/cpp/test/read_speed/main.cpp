#include <fstream>
#include <iostream>
#include <ctime>
using namespace std;

int main()
{
	ifstream ifs = ifstream("./read_speed_test.txt", ios_base::in);
	char a;
	clock_t start = clock();
	while (!ifs.eof()) {
		ifs >> a;
	}
	clock_t end = clock();
	ifs.close();
	cout << end-start << endl;
	system("pause");
}