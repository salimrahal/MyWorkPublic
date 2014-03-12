/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsingxml;

/**
 *
 * @author salim
 */
public class Employee {
    String id;
    String firstName;
    String lastName;
    String location;

    @Override
    public String toString() {
        return firstName + " " + lastName + "(" + id + ")" + location;
    }
}
