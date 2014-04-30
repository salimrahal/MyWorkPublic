/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author salim
 * http://www.javaworld.com/article/2077322/core-java/sockets-programming-in-java-a-tutorial.html
 */
public class Main {
    
     public static void main(String[] args) {
          Socket MyClient;
    try {
           System.out.println("open socket..");
           MyClient = new Socket("209.208.79.151", 5060);
    }
    catch (IOException e) {
        System.out.println(e);
    }
     }
}
