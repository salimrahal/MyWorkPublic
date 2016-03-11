/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambda.list;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author salim
 */
public class TestList {

    public static void main(String[] args) {
        testListWithLE(); 
    }
public static void testListWithLE(){
      //Old way:
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        for (Integer n : list1) {
            System.out.println(n);
        }
        //new way
        list1.forEach((e)->System.out.println("using LE:"+e));
        
        //or we can use :: double colon operator in Java 8
        list1.forEach(System.out::println);

}
}
