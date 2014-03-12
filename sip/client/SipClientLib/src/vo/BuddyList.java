/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;
import buddyListplugin.buddylist.Buddy;
import buddyListplugin.buddylist.BuddyCellRenderer;
import java.util.ArrayList;

/**
 *
 * @author salim
 */
public class BuddyList {
    
    
      /**
     * it takes the sip IDs passed from the UI: 298,299,140,112
     *
     * @param sipIdscommas
     * @return Array: Sip Ids as Array: 298=str[0], etc
     */
    public static ArrayList generateAsBuddyList(String sipIdscommas) {
        StringBuilder strbuilder = new StringBuilder(sipIdscommas);
        String sipIds = strbuilder.toString();
        // removes all whitespaces and non visible characters such as tab, \n
        sipIds = sipIds.replaceAll("\\s+", "");
        String[] sipArr = sipIds.split(",");
//        
//         String[] sipArr = SipClientBO.retrieveSIpIdAsArray("298,112, 140,299");
               ArrayList buddyList = new ArrayList<>();
                for (int i = 0; i < sipArr.length; i++) {
                    buddyList.add(new Buddy(sipArr[i], BuddyCellRenderer.Adapter.Status.UNKNOWN, BuddyCellRenderer.Adapter.Status_Address.CLOSED, "message", null));
                }
                //System.out.println("buddyList length"+buddyList.size());
        return buddyList;
    }   
}
