/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cfg;

import cfg.vo.ConfVO;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import vo.PrtVo;

/**
 *
 * @author salim
 */
public class PortSigHandler extends DefaultHandler {

    ConfVO confVO = null;
    //var unsed, no content to retrieve
    String content = null;

    //List to hold Employees object
    private List<PrtVo> portListVo = null;
    private PrtVo prtVo = null;

    //getter method for employee list
    public List<PrtVo> getPortListVo() {
        return portListVo;
    }
   
    @Override
    //Triggered when the start of tag is found.
    public void startElement(String uri, String localName,
            String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase("configuration")) {
            //Create a new presence object when the start tag is found         
            confVO = ConfVO.getInstance();
        }
       if (qName.equalsIgnoreCase("port-sig")) {
            String portnumStr = attributes.getValue("value");
            ConfVO.getInstance().setPortSig(Integer.parseInt(portnumStr));
//            prtVo = new PrtVo();
//            prtVo.setPrtNum(portnumStr);
//           
//
//            if (portListVo == null) {
//                portListVo = new ArrayList<>();
//            }
        }
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException {
//        if (qName.equalsIgnoreCase("port-sig")) {
//            //add test object to list
//            portListVo.add(prtVo);
//            confVO.setPrtList(portListVo);
//        }
    }

    //get the content of the node
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();
    }
}
