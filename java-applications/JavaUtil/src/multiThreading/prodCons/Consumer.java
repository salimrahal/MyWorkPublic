/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiThreading.prodCons;

/**
 *
 * @author salim
 */
class Consumer implements Runnable { 
Q q; 
Consumer(Q q) { 
this.q = q; 
new Thread(this, "Consumer").start(); 
} 
public void run() { 
while(true) { 
q.get(); 
} 
} 
}
