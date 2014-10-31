/* 
 * File:   main.cpp
 * Author: salim
 *
 * Created on October 31, 2014, 9:27 AM
 */

#include <cstdlib>
#include <iostream>
#include <string.h>

using namespace std;

/*
 * The trick is to do three reverse operation:
 * One for the entire string, 
 * one from index 0 to k-1, 
 * and lastly index k to n-1. Magically, 
 * this will yield the correct rotated array, without any extra space! 
 * (Of course, you need a temporary variable for swapping).
 */
void reverse_string(char* str, int left, int right) {
    std::cout << "params = [left=" << left << ",right=" << right << "]\n";
    char* p1 = str + left;//shit the pointer to the element with index left and return a char *
    char* p2 = str + right;//shit the pointer to the element with index right and return a char *

    std::cout << "[";
    int sizestr = sizeof (str);
    std::cout << str << "= str size =" << sizestr << ";";

    int sizep1 = sizeof (p1);
    std::cout << p1 << "=p1 size =" << sizep1 << ";";

    int sizep2 = sizeof (p2);
    std::cout << p2 << "=p2 size =" << sizep2 << ";]\n";

   
    while (p1 < p2) {
        char temp = *p1;
        *p1 = *p2;
        *p2 = temp;
        p1++;
        p2--;
    }
        std::cout <<"After resverse="<< str << "= str -size =" << sizestr << ";\n";
}

void rotate(char* str, int k) {
    int n = strlen(str);
    reverse_string(str, 0, n - 1);
    reverse_string(str, 0, k - 1);
    reverse_string(str, k, n - 1);
}

int main(int argc, char** argv) {
    char arr[5] = {'a', 'b', 'c', 'd', 'e'};
    rotate(arr, 1);
    std::cout << arr << "=final result";
    return 0;
}





