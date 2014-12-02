/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author salim Desc: Rotate a one-dimensional array of n elements to the right
 * by rotateN steps. For instance, with n=7 and rotateN=3, the array
 * {1,2,3,4,5,6,7} is rotated to {5, 6, 7, 1, 2, 3, 4}.
 */

//the main class
class RotateMain {

    //Desc: main method responsible for launching the rotation Application
    public static void main(String args[]) {
        //the program loop, to stop it the user should kill the process
        boolean loop = true;
        //original array passed by the user
        String original;
        //the number of rotation passed by the user
        String pos;
        //infinite prompt on system.in
        while (loop) {
            Scanner console = new Scanner(System.in);
            System.out.println("Step 1 of 2: Enter an Integer Array to reverse seperated by a comma, e.g: 1,2,3 ");
            original = console.nextLine();
            System.out.println("Step 2 of 2: Enter a N position to rotate:");
            pos = console.nextLine();
            //only interprete the params if there are not null and not empty
            if (original != null && pos != null) {
                if (!original.isEmpty() && !pos.isEmpty()) {
                    //retieve the array of Integer as String[]
                    String[] paramArr = original.split(",");
                    Integer rotateNparam = null;
                    try {
                        //parsing the n rotation to Integer
                        rotateNparam = Integer.valueOf(pos);
                        // convert the Array passed as String {1,2,3} -> Integer{1,2,3}
                        Integer[] inArr = Rotate.convertStringtoInt(paramArr);
                        //execute the rotation and getting the result rotated Array  by N position
                        Integer[] rotatedArray = Rotate.rotateOriginal(inArr, rotateNparam);
                        //print out the rotated Array 
                        Rotate.printArray(rotatedArray);
                        System.out.println("----------------------Finish--------------------------\n");
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("Error: you have entered a Letter, only number are permitted\n");
                    }

                } else {
                    System.out.println("Warning: you have entered empty value\n");
                }
            } else {
                System.out.println("Warning: you have entered empty value\n");
            }
        }//end loop
    }//end main
}
