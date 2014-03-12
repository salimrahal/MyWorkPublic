/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import vo.ConfVO;

/**
 *
 * @author salim
 */
public class ConfHandler extends DefaultHandler {

    ConfVO confVO = null;
    //var unsed, no content to retrieve
    String content = null;

    @Override
    //Triggered when the start of tag is found.
    public void startElement(String uri, String localName,
            String qName, Attributes attributes)
            throws SAXException {

        switch (qName) {
            //Create a new presence object when the start tag is found
            case "PROPERTIES":
                confVO = ConfVO.getInstance();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException {
        switch (qName) {
            //Add the employee to list once end tag is found

            //For all other end tags the employee has to be updated.
            case "SIP-SERVER":
                confVO.setUASIp(content);
                break;
            case "SIP-SERVER-PORT":
                confVO.setUASPort(Integer.valueOf(content));
                break;
            case "Local-IP":
                confVO.setUACIp(content);
                break;
            case "SIP-LOCAL-PORT":
                confVO.setUACPort(Integer.valueOf(content));
            case "LOCAL-HOST-NAME":
                confVO.setHostNameLocal(content);
                break;
            case "SIP-ID":
                confVO.setExtSipLocal(content);
                break;
            case "SIP-Realm-Domain":
                confVO.setDomain(content);
                break;
            case "SIP-Auth-ID":
                confVO.setUsername(content);
                break;
            case "SIP-PASSWORD":
                confVO.setPassword(content);
                break;
            case "SIP-PROTOCOL":
                confVO.setProtocol(content);
                break;
            case "EXT-TO-MONITOR":
                confVO.setExtsToMonitor(content);
                break;
            case "SIP-LOCAL-MACHINE-UUID":
                confVO.setUuid(content);
                break;

        }
    }

    //method unused here
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();
    }
}
