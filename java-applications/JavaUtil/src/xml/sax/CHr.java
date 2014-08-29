/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.sax;

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
 * <!-- udp traffic generator config -->
 * <port value="5094" status="b"></port>
 * <port value="5095" status="f"></port>
 * <port value="5096" status="b"></port>
 * <port value="5097" status="f"></port>
 * <port value="5098" status="b"></port>
 *
 * </configuration>
 */
public class CHr extends DefaultHandler {

    ConfVO confVO = null;
    //var unsed, no content to retrieve
    String content = null;

    //List to hold Employees object
    private List<PrtStsVo> prtStsListVo = null;
    private PrtStsVo prtStsVo = null;

    //getter method for employee list
    public List<PrtStsVo> getPrtStsList() {
        return prtStsListVo;
    }

    public void setPrtStsList(List<PrtStsVo> prtStsList) {
        this.prtStsListVo = prtStsList;
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
        }
        if (qName.equalsIgnoreCase("port")) {

            String value = attributes.getValue("value");
            String status = attributes.getValue("status");
            prtStsVo = new PrtStsVo();
            prtStsVo.setPrtNum(value);
            prtStsVo.setSts(status);

            if (prtStsListVo == null) {
                prtStsListVo = new ArrayList<>();
            }
        }
        /*
         TODO: complete all other properties
         */
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException {
        if (qName.equalsIgnoreCase("port")) {
            //add test object to list
            prtStsListVo.add(prtStsVo);
            confVO.setPrtStsList(prtStsListVo);
        }
    }

    //get the content of the node
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

//below: retreiving the node content
             content = String.copyValueOf(ch, start, length).trim();
//        if (bPortsrc) {
//            test.setPortscr(Integer.valueOf(content));
//            bPortsrc = false;
//        } else if (bPortdest) {
//            test.setPortDest(Integer.valueOf(content));
//            bPortdest = false;
//        } else if (bTransport) {
//            test.setTransport(content);
//            bTransport = false;
//        }
    }
}
