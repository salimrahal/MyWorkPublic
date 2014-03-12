/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiThreading.callable;

import java.util.concurrent.Callable;

/**
 *
 * @author salim
 */
class Foo implements Callable<String> {

    public String call() {
        try {
            // sleep for 10 seconds
            Thread.sleep(3 * 1000);
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Run by "+Thread.currentThread().getName());
        return ("Hello, World!");
    }
}
