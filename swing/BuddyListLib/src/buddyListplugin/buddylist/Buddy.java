/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

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
        String name;// maybe the SIP ID
        Adapter.Status status;//equivalent to MSN status
        Adapter.Status_Address statusAddress;// equiv to Address status
        String message;
        URL buddyIconURL;//TODO: the icon will be added lastly
        
          public Buddy(String name, Adapter.Status status, Adapter.Status_Address statusAddress, String message) {
            this.name = name;
            this.status = status;
            this.statusAddress = statusAddress;
            this.message = message;
           
        }
}
