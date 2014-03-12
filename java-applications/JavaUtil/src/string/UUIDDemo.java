/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package string;

import java.util.UUID;

/**
 *
 * @author salim
 */
public class UUIDDemo {
     public static void main(String[] args) throws Exception {
      // creating UUID      
     // UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");     
        
      // checking the value of random UUID
      System.out.println("Random UUID value: "+UUID.randomUUID().toString());    //out aec5e1c8-9a18-4d80-8668-cbe08e5d177f
     }
}
