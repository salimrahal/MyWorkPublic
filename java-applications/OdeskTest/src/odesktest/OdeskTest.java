/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package odesktest;

import java.util.Arrays;

/**
 *
 * @author salim Write a function that takes an array of integers and returns
 * that array rotated by N positions. For example, if N=2, given the input array
 * [1, 2, 3, 4, 5, 6] the function should return [5, 6, 1, 2, 3, 4]
 *
 * Please provide a fully unittest covered functionality and make sure you
 * clearly comment your code. Also please make sure you provide clear
 * instructions on how to run the code and the tests.
 */
public class OdeskTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        char[] letters = new char[3];
        letters[0] = 'A';
        letters[1] = 'B';
        letters[2] = 'C';
        // rotateRight(letters, 2);
        //rotate(letters, 2);
        rotateInPlace();
        String s = "salim";
    }

    public static void rotateRight(char[] letters, int n) {
        char last = letters[letters.length - 1];          // save off first element
        for (int i = 0; i < n; i++) {
            for (int index = letters.length - 2; index >= 0; index--) {
                letters[index + 1] = letters[index];
            }
        }
// shift right

        // wrap last element into first slot
        letters[0] = last;
        System.out.print("\nshifted Array: " + Arrays.toString(letters));
    }

    public static void rotateRight(char[] letters) {
        char last = letters[letters.length - 1];          // save off first element

        // shift right
        for (int index = letters.length - 2; index >= 0; index--) {
            letters[index + 1] = letters[index];
        }

        // wrap last element into first slot
        letters[0] = last;
        System.out.print("\nshifted Array: " + Arrays.toString(letters));
    }

    /*
     http://stackoverflow.com/questions/9270745/rotating-an-array
     */
    private static String rotate(final char[] array, final int n) {
        for (int i = 0; i < n; i++) {
            char tmp = array[array.length - 1];
            for (int j = array.length - 1; j >= 0; j--) {
                array[j] = j == 0 ? tmp : array[(j - 1 + array.length) % array.length];
            }
        }
        System.out.print("\nshifted Array: " + Arrays.toString(array));
        return new String(array);
    }

    public static void rotateInPlace() {
        Integer[] tempArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int shiftCountOrCurrentIndex = 4;
        int arrayLength = tempArray.length;
        int startIndex = 0;
        int index = startIndex;
        boolean takeOnce = true;
        for (int cnt = 0; cnt < 2; cnt++) {
            System.out.print("{");
            while (index != startIndex || takeOnce == true) {
                if (index == startIndex) {
                    takeOnce = false;
                }
                System.out.print(tempArray[index++] + ",");
                if ((index) % arrayLength == 0) {
                    index = 0;
                }
            }
            System.out.println("}");
            startIndex = shiftCountOrCurrentIndex;
            index = startIndex;
            takeOnce = true;
        }

    }
    /*
    void reverse_string(char* str, int left, int right) {
  char* p1 = str + left;
  char* p2 = str + right;
  while (p1 < p2) {
    char temp = *p1;
    *p1 = *p2;
    *p2 = temp;
    p1++;
    p2--;
  }
}
*/
    public void reverse_string(String[] str, int left, int right) {
  
}
    
    /*
void rotate(char* str, int k) {
  int n = strlen(str);
  reverse_string(str, 0, n-1);
  reverse_string(str, 0, k-1);
  reverse_string(str, k, n-1);
}
    */
}
