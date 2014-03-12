/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsingpidfxml;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author salim
 */
public class Presencehandler extends DefaultHandler {

   // List<Presence> presList = new ArrayList<>();
    Presence presObj = null;
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
                presObj = new Presence();
                break;
            case "presentity":
                presObj.presentity_URI = attributes.getValue("uri");
                break;
            case "atom":
                String atom_id = attributes.getValue("id");               
                if(atom_id != null){
                    presObj.atom_ID = Integer.valueOf(atom_id);
                }
                break;
            case "address":
                presObj.address_URI = attributes.getValue("uri");
                break;
            case "status":
                presObj.status_status = attributes.getValue("status");
                break;
            case "msnstatus":
                presObj.msnstatus_substatus = attributes.getValue("substatus");
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
