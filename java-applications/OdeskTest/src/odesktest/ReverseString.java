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
//        Scanner in = new Scanner(System.in);
//        System.out.println("Enter a Integer Array to reverse:");
//        String original = in.nextLine();
//        Integer num = Integer. original.split("\,");
//        Integer[] inArr = 
//        rotateOriginalV2();valueOf(original);
//     original.split("\,");
//        Integer[] inArr = 
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
        int k = 1;
        int length = original.length;
        Integer[] res1 = rotate(original, 0, length - 1);
        Integer[] res2 = rotate(res1, 0, k - 1);
        Integer[] res3 = rotate(res1, k, length - 1);
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
            left++;
            right--;
        }
        String s;
        for (int i = 0; i < original.length; i++) {
            System.out.println("After of entered int is: " + original[i]);
        }
        return original;
    }
}
