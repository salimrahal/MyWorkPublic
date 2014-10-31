/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package odesktest;

import java.util.Scanner;

/**
 *
 * @author salim
 */
class ReverseString {

    public static void main(String args[]) {
        rotateOriginalV2();
    }

    public static void rotateOriginalV2() {
        Integer[] original = new Integer[3];
        original[0] = 1;
        original[1] = 2;
        original[2] = 3;
        
        for (int i = 0; i < original.length; i++) {
            System.out.println("befroe rev: " + original[i]);
        }
        Integer[] reverse = new Integer[3];
        int k = 2;
        int length = original.length;
        Integer[] res1 = rotate(original, 0, length - 1);
        Integer[] res2 = rotate(res1, 0, k - 1);
        Integer[] res3 = rotate(res1, k,  length - 1);
        System.out.println("final res:");
        for (int i = 0; i < original.length; i++) {
            System.out.println("final res: " + res3[i]);
        }
        
        
    }

    public static Integer[] rotate(Integer[] original, int left, int right) {
        
        while (left < right) {
            Integer tmp = original[left];
            original[left] = original[right];
            original[right] = tmp;
            //reverse = reverse + original.charAt(i);
            left++;
            right--;
        }
        
        String s;
        for (int i = 0; i < original.length; i++) {
            System.out.println("After of entered int is: " + original[i]);
        }
         return original ;
    }

public void rotateOriginal() {
        String original, reverse = "";
        Scanner in = new Scanner(System.in);

        System.out.println("Enter a string to reverse");
        original = in.nextLine();

        int length = original.length();

        for (int i = length - 1; i >= 0; i--) {
            reverse = reverse + original.charAt(i);
        }

        System.out.println("Reverse of entered string is: " + reverse);
    }

}
