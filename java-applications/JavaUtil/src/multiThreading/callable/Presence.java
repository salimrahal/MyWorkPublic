/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiThreading.callable;

/**
 *
 * @author salim
 */
public class Presence {
String status;

    public Presence(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Presence{" + "status=" + status + '}';
    }
    
}
