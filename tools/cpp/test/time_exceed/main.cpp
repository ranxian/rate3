#include <iostream>
#include <ctime>
#include <windows.h>
#include <process.h>
using namespace std;

int eight();

int main(int argc, char* argv[])
{
	//system("pause");
	for (int i=0; i<argc; i++) {
		cout << argv[i] << endl;
	}

	HANDLE h = GetCurrentProcess();
	if (h==NULL) {
		cout << "failed GetCurrentProcess" << endl;
	}

	FILETIME na;
	FILETIME nb;
	FILETIME kt;
	FILETIME ut;
	SYSTEMTIME kt1; 
	SYSTEMTIME ut1;

	while(true) {		
		for (int i=0; i<100; i++) {
			eight();
		}
		if (!GetProcessTimes(h, &na, &nb, &kt, &ut)) {
			cout << GetLastError() << endl;
			return -1;
		}
		FileTimeToSystemTime(&kt, &kt1);
		FileTimeToSystemTime(&ut, &ut1);
		cout << "pid " << _getpid() << ":" << kt1.wSecond << " " << kt1.wMilliseconds << "  ||  " << ut1.wSecond << " " << ut1.wMilliseconds << endl;
		//cerr << "shit err" << endl;
		//printf("what's wrong");
	}
	return 0;
}



/////////////////////////////////////////////////////////////////////////////
// ��������ҵİ˻ʺ����
/////////////////////////////////////////////////////////////////////////////
#include<stdio.h>
#define NUM 8 
int a[NUM+1];
int eight()
{
	int i,k,flag,not_finish=1,count=0;
	i=1; /*���ڴ����Ԫ���±꣬��ʾǰi-1��Ԫ���ѷ���Ҫ�����ڴ����i��Ԫ��*/
	a[1]=1; /*Ϊ����ĵ�һ��Ԫ�ظ���ֵ*/
	//printf("The possible configuration of 8 queens are: ");
	while(not_finish) /*not_finish=1:������δ����*/
	{
		while(not_finish&&i<=NUM) /*������δ�����һ�û������NUM��Ԫ��*/
		{
			for(flag=1,k=1;flag&&k<i;k++) /*�ж��Ƿ��ж���ʺ���ͬһ��*/
				if(a[k]==a[i])flag=0;
			for(k=1;flag&&k<i;k++) /*�ж��Ƿ��ж���ʺ���ͬһ�Խ���*/
				if((a[i]==a[k]-(k-i))||(a[i]==a[k]+(k-i))) flag=0;
			if(!flag) /*������ì�ܲ�����Ҫ����Ҫ�������õ�i��Ԫ��*/
			{
				if(a[i]==a[i-1]) /*��a[i]��ֵ�Ѿ�����һȦ׷��a[i-1]��ֵ*/
				{
					i--; /*�˻�һ����������̽����ǰһ��Ԫ��*/
					if(i>1&&a[i]==NUM)
						a[i]=1; /*��a[i]ΪNUMʱ��a[i]��ֵ��1*/
					else if(i==1&&a[i]==NUM)
						not_finish=0; /*����һλ��ֵ�ﵽNUMʱ����*/
					else a[i]++; /*��a[i]��ֵȡ��һ��ֵ*/
				}
				else if(a[i]==NUM) a[i]=1;
				else a[i]++; /*��a[i]��ֵȡ��һ��ֵ*/
			}
			else if(++i<=NUM)
				if(a[i-1]==NUM) a[i]=1; /*��ǰһ��Ԫ�ص�ֵΪNUM��a[i]=1*/
				else a[i]=a[i-1]+1; /*����Ԫ�ص�ֵΪǰһ��Ԫ�ص���һ��ֵ*/
		}
		if(not_finish)
		{
			++count;
			//printf((count-1)%3?" [%2d]: ":" [%2d]: ",count);
			//for(k=1;k<=NUM;k++) /*������*/
			//	printf(" %d",a[k]);
			if(a[NUM-1]<NUM) a[NUM-1]++; /*�޸ĵ����ڶ�λ��ֵ*/
			else a[NUM-1]=1;
			i=NUM-1; /*��ʼѰ����һ���������Ľ�*/
		}
	}
	return 0;
}
