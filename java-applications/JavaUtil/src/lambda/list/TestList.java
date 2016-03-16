/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambda.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author salim
 */
public class TestList {

    public static void main(String[] args) {
        testListWithLE();
    }

    public static void testListWithLE() {
        //Old way:
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        for (Integer n : list1) {
            System.out.println(n);
        }
        list1.forEach((e) -> System.out.println("using Lambda Exp:" + e));

        //or we can use :: double colon operator in Java 8
        list1.forEach(System.out::println);

    }

    public static void testListWithLE_FlatFunction() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        list1.forEach((e) -> {
            System.out.println("behh");
            int x = 0;
            x = x+e;
        });
    }

    public static void testListWithLE_InnerFunction() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        list1.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer e) {
                List<Integer> l = new ArrayList<>();
                l.add(e);
                System.out.println("Inner function addin " + e);
            }
        });
    }
}
