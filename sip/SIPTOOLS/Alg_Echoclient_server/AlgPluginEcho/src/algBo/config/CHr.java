/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algBo.config;

import algVo.Test;
import algVo.config.ConfVO;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author salim
 * <configuration>
 * <sipServer ip="209.208.79.151"/>
 * <sipIdLocal sipid="ALGdetector"/>
 * <agentname agentname="Cisco/SPA303-8.0.1"/>
 * <!-- test with id=1 is equivalent to test A in the plugin GUI -->
 * <test id="1">
 * <portsrc> 5060</portsrc>
 * <portdest>5060</portdest>
 * <transport>udp</transport>
 * </test>
 * <!-- test with id=2 is equivalent to test B in the plugin GUI -->
 * <test id="2">
 * <portsrc>5060</portsrc>
 * <portdest>5060</portdest>
 * <transport>tcp</transport>
 * </test>
 * <!-- test with id=3 is equivalent to test C in the plugin GUI -->
 * <test id="3">
 * <portsrc> 5062</portsrc>
 * <portdest>5060</portdest>
 * <transport>udp</transport>
 * </test>
 * <!-- test with id=4 is equivalent to test D in the plugin GUI -->
 * <test id="4">
 * <portsrc>5062</portsrc>
 * <portdest>5060</portdest>
 * <transport>tcp</transport>
 * </test>
 * </configuration>
 */
public class CHr extends DefaultHandler {

    ConfVO confVO = null;
    //var unsed, no content to retrieve
    String content = null;

    //List to hold Employees object
    private List<Test> testList = null;
    private Test test = null;

    //getter method for employee list
    public List<Test> getTestList() {
        return testList;
    }

    boolean bPortsrc = false;
    boolean bPortdest = false;
    boolean bTransport = false;

    @Override
    //Triggered when the start of tag is found.
    public void startElement(String uri, String localName,
            String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase("configuration")) {
            //Create a new presence object when the start tag is found         
            confVO = ConfVO.getInstance();
        }
        if (qName.equalsIgnoreCase("sipServer")) {
            //Add the employee to list once end tag is found
            confVO.setIpServer(attributes.getValue("ip"));
        } else if (qName.equalsIgnoreCase("sipIdLocal")) {
            confVO.setSipIdLocal(attributes.getValue("sipid"));//attributes.getValue("sipid")) ;
        } else if (qName.equalsIgnoreCase("agentname")) {
            confVO.setAgentname(attributes.getValue("agentname"));
        } else if (qName.equalsIgnoreCase("agentname")) {
            confVO.setAgentname(attributes.getValue("agentname"));
        } else if (qName.equalsIgnoreCase("test")) {
            String idstr = attributes.getValue("id");
            test = new Test();
            test.setSeqNumber(Integer.parseInt(idstr));

            if (testList == null) {
                testList = new ArrayList<Test>();
            }
        } else if (qName.equalsIgnoreCase("portsrc")) {
            bPortsrc = true;
        } else if (qName.equalsIgnoreCase("portdest")) {
            bPortdest = true;
        } else if (qName.equalsIgnoreCase("transport")) {
            bTransport = true;
        }
        /*
         TODO: complete all other properties
         */
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException {
        if (qName.equalsIgnoreCase("test")) {
            //add test object to list
            testList.add(test);
            confVO.setTestL(testList);
        }
    }

    //get the content of the node
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();
        if (bPortsrc) {
            test.setPortscr(Integer.valueOf(content));
            bPortsrc = false;
        } else if (bPortdest) {
            test.setPortDest(Integer.valueOf(content));
            bPortdest = false;
        } else if (bTransport) {
            test.setTransport(content);
            bTransport = false;
        }
    }
}
