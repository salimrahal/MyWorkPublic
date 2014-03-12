/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.xmlparser;

import buddyListplugin.buddylist.BuddyCellRenderer;
import vo.PresenceVO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author salim
 */
public class Presencehandler extends DefaultHandler{
      // List<Presence> presList = new ArrayList<>();
    PresenceVO presObj = null;
    //var unsed, no content to retrieve
    String content = null;

    @Override
    //Triggered when the start of tag is found.
    public void startElement(String uri, String localName,
            String qName, Attributes attributes)
            throws SAXException {
        
        switch (qName) {
            //Create a new presence object when the start tag is found
            case "presence":
                presObj = new PresenceVO();
                break;
            case "presentity":
                presObj.setPresentity_URI(attributes.getValue("uri")) ;
                break;
            case "atom":
                String atom_id = attributes.getValue("id");               
                if(atom_id != null){
                    presObj.setAtom_ID( Integer.valueOf(atom_id));
                }
                break;
            case "address":
                presObj.setAddress_URI(attributes.getValue("uri"));
                break;
            case "status":
                presObj.setStatus_status(attributes.getValue("status"));
                if(attributes.getValue("status").equalsIgnoreCase("open")){
                    presObj.statusAddress = BuddyCellRenderer.Adapter.Status_Address.OPEN;
                }
                else if(attributes.getValue("status").equalsIgnoreCase("CLOSED")){
                    presObj.statusAddress = BuddyCellRenderer.Adapter.Status_Address.CLOSED;
                }
                else if(attributes.getValue("status").equalsIgnoreCase("INUSE")){
                    presObj.statusAddress = BuddyCellRenderer.Adapter.Status_Address.INUSE;
                }
                break;
            case "msnstatus":
                
                presObj.setMsnstatus_substatus(attributes.getValue("substatus"));
                 if(attributes.getValue("substatus").equalsIgnoreCase("online")){
                    presObj.status = BuddyCellRenderer.Adapter.Status.ONLINE;
                }
                 else if(attributes.getValue("substatus").equalsIgnoreCase("AWAY")){
                    presObj.status = BuddyCellRenderer.Adapter.Status.AWAY;
                }
                 else if(attributes.getValue("substatus").equalsIgnoreCase("BERIGHTBACK")){
                    presObj.status = BuddyCellRenderer.Adapter.Status.BERIGHTBACK;
                }
                 else if(attributes.getValue("substatus").equalsIgnoreCase("BUSY")){
                    presObj.status = BuddyCellRenderer.Adapter.Status.BUSY;
                }
                 else if(attributes.getValue("substatus").equalsIgnoreCase("IDLE")){
                    presObj.status = BuddyCellRenderer.Adapter.Status.IDLE;
                }
                 else if(attributes.getValue("substatus").equalsIgnoreCase("UNKNOWN")){
                    presObj.status = BuddyCellRenderer.Adapter.Status.UNKNOWN;
                }else if(attributes.getValue("substatus").equalsIgnoreCase("ONTHEPHONE")){
                    presObj.status = BuddyCellRenderer.Adapter.Status.ONTHEPHONE;
                }
                else if(attributes.getValue("substatus").equalsIgnoreCase("OUTTOLUNCH")){
                    presObj.status = BuddyCellRenderer.Adapter.Status.OUTTOLUNCH;
                }
                if(attributes.getValue("substatus").equalsIgnoreCase("OFFLINE")){
                    presObj.status = BuddyCellRenderer.Adapter.Status.OFFLINE;
                }
                
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException {
//        switch (qName) {
//            //Add the employee to list once end tag is found
//
//            //For all other end tags the employee has to be updated.
//            case "firstName":
//                emp.firstName = content;
//                break;
//            case "lastName":
//                emp.lastName = content;
//                break;
//            case "location":
//                emp.location = content;
//                break;
//        }
    }

    //method unused here
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();
    }
}
