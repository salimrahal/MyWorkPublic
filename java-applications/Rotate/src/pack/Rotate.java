/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import java.util.Arrays;

/**
 *
 * @author salim: This class declare method that rotate a one-dimensional array of n elements to the right
 * by rotateN steps. For instance, with n=7 and rotateN=3, the array
 * {1,2,3,4,5,6,7} is rotated to {5, 6, 7, 1, 2, 3, 4}.

 */
public class Rotate {
    
    /**
     *it prints out the rotated Array 
     * @param ArrayInt 
     */
     public static void printArray(Integer[] ArrayInt) {
        System.out.println("Rotated Array="+Arrays.toString(ArrayInt));
    }

     /**
      * it converts an array of String into Array of Integer
      * @param paramArr
      * @return 
      */
    public static Integer[] convertStringtoInt(String[] paramArr) {
        Integer[] intArr = new Integer[paramArr.length];
        for (int i = 0; i < paramArr.length; i++) {
            intArr[i] = Integer.valueOf(paramArr[i]);
        }
        return intArr;
    }

    /**
     *  it launches three reverse operation: 
     * a- One for the entire string 
     * b- one from index 0 to rotateN-1 
     * c- lastly index rotateN to n-1
     *
     * @param paramArray
     * @param rotateN
     * @return 
     */
    public static Integer[] rotateOriginal(Integer[] paramArray, int rotateN) {
        Integer[] original = paramArray;
        int n = original.length;
        //reverse the entire string
        Integer[] res1 = reverse(original, 0, n - 1);
        //  reverse from index 0 to rotateN-1
        Integer[] res2 = reverse(res1, 0, rotateN - 1);
        // lastly reverse from index rotateN to n-1
        Integer[] res3 = reverse(res2, rotateN, n - 1);
        return res3;
    }

    /**
     * It reverses a given array, from left to right
     * example: 1 2 3 -> 3 2 1
     * @param original
     * @param left
     * @param right
     * @return 
     */
    public static Integer[] reverse(Integer[] original, int left, int right) {
        while (left < right) {
            Integer tmp = original[left];
            original[left] = original[right];
            original[right] = tmp;
            left++;
            right--;
        }
        return original;
    }
}
