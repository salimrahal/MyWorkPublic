/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package siplib;

/**
 *
 * @author salim
 */
public class SipServerTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SipListenerServer sipserver = new SipListenerServer();
        sipserver.initializeSipServer();
    }
    
}
