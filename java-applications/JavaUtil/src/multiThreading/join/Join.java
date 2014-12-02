/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multiThreading.join;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sample Java class to illustrate How to join two threads in Java.
 * join() method allows you to serialize processing of two threads.
 * 
Output:
main is Started
Thread-0 is Started
Thread-0 is Completed
main is Completed


Read more: http://javarevisited.blogspot.com/2013/02/how-to-join-multiple-threads-in-java-example-tutorial.html#ixzz3DS9zKS2u
 */

public class Join {
  
    public static void main(String args[]) throws InterruptedException{
      
        System.out.println(Thread.currentThread().getName() + " is Started");
      
        Thread exampleThread = new Thread(){
            public void run(){
                try {
                    System.out.println(Thread.currentThread().getName() + " is Started");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " is Completed");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Join.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
      
        exampleThread.start();
        exampleThread.join();
      
        System.out.println(Thread.currentThread().getName() + " is Completed");
    }
  
}

