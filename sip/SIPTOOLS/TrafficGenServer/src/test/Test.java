/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

/**
 *
 * @author salim
 */
public class Test {
    public static void main(String[] args){
        byte bte = (byte)0xd5;// -43
        int num = (byte)0xd5;//-43 (byte)0x55
        System.out.println("bb="+bte+"/num="+num);
        
        byte bte2 = (byte)0x55;// 85
       
        System.out.println("bb="+bte2);
    }
}
