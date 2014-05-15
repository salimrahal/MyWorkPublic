/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algBo.config;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import algVo.config.ConfVO;

/**
 *
 * @author salim
 * <configuration>
    <sipServer ip="209.208.79.151"/>
    <sipIdLocal sipid=="ALGdetector"/>
    <agentname agentname="Cisco/SPA303-8.0.1"/>
    <test id="testtA">      
            <portsrc portsrc="5060"/>
            <portdest portdest="5060"/>
            <transport transport="udp"/>
    </test>
      <test id="testB">      
            <portsrc portsrc="5060"/>
            <portdest portdest="5060"/>
            <transport transport="tcp"/>
    </test>
      <test id="testC">      
            <portsrc portsrc="5062"/>
            <portdest portdest="5060"/>
            <transport transport="udp"/>
    </test>
      <test id="testD">      
            <portsrc portsrc="5062"/>
            <portdest portdest="5060"/>
            <transport transport="tcp"/>
    </test>
</configuration>
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

         if(qName.equalsIgnoreCase("configuration")) {
            //Create a new presence object when the start tag is found         
                confVO = ConfVO.getInstance();
        }
       if(qName.equalsIgnoreCase("sipServer")) {
            //Add the employee to list once end tag is found
             ConfVO.setIpServer(attributes.getValue("ip")) ;
       }else if(qName.equalsIgnoreCase("sipIdLocal")){
         ConfVO.setIpServer(attributes.getValue("sipid")) ;//attributes.getValue("sipid")) ;
       }  else if(qName.equalsIgnoreCase("agentname")){
         ConfVO.setAgentname(attributes.getValue("agentname"));
       }     
       /*
       TODO: complete all other properties
       */
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException {   
    }

    //method unused here
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();
    }
}


/*
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
*/
