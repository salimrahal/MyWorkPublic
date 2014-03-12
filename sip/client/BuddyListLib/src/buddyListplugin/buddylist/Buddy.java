/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package buddyListplugin.buddylist;

import buddyListplugin.buddylist.BuddyCellRenderer.Adapter;
import java.net.URL;

/**
 *
 * @author salim
 *    String name;
        Adapter.Status status;
        String message;
        URL buddyIconURL;

        Buddy(String name, Adapter.Status status, String message, String buddyIcon) {
            this.name = name;
            this.status = status;
            this.message = message;
            this.buddyIconURL = (buddyIcon == null) ? null
                    : Main.class.getResource("/resources/sample-buddy-icons/" + buddyIcon);
        }
 */
public class Buddy {
        public String name;// maybe the SIP ID
        public Adapter.Status status;//equivalent to MSN status
        public Adapter.Status_Address statusAddress;// equiv to Address status
        public String message;
        public URL buddyIconURL;
        
          public Buddy(String name, Adapter.Status status, Adapter.Status_Address statusAddress, String message, String buddyIcon) {
            this.name = name;
            this.status = status;
            this.statusAddress = statusAddress;
            this.message = message;
             this.buddyIconURL = (buddyIcon == null) ? null
                    : Buddy.class.getResource("/buddyListplugin/resources/samplebuddyicons/" + buddyIcon);
           
        }

    @Override
    public String toString() {
      //  return "Buddy{" + "name=" + name + ", status=" + status + ", statusAddress=" + statusAddress + ", message=" + message + ", buddyIconURL=" + buddyIconURL + '}';
         return "name=" + name + "- MsNstatus=" + status + " - status=" + statusAddress;
    }        
          
}
