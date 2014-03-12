/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vo;

import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author salim
 */
public class Status {
    public static  enum STATUS{   
        DISCONNETED, CONNECTED;
    }
    private static String status = "UNKNOWN";
        //this method should be accessed by many threads [StatusRunnable] to update the state of the local Sip IP
    //it has not effect on the UI when running on windows JVM
 public static synchronized void computingStatus(JLabel jlabel, String status) {
       jlabel.setText(status);
       if(status.equalsIgnoreCase("CONNECTED")){
            jlabel.setForeground(Color.GREEN); 
       }
       //handling the disconntect status
       else{
             jlabel.setForeground(Color.RED); 
       }   
    }

 
 //called by SIPBLF frame
    public synchronized static String getStatus() {
        return status;
    }

    public synchronized static void setStatus(String status) {
        Status.status = status;
    }
 
 
 
}
