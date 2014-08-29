/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfg;

import cfg.vo.ConfVO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.rmi.CORBA.Util;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import vo.PrtVo;

/**
 *
 * @author salim
 */
public class Spf {
    SAXParserFactory parserFactor;
    PortSigHandler codechandler;

    public Spf() {
        parserFactor = SAXParserFactory.newInstance();

    }

    public void parseConfVOPrt(String confUri) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        PortSigHandler handler = new PortSigHandler();
        File f = new File(confUri);
      
        parser.parse(confUri, handler);
        ConfVO confvo = handler.confVO;
        List<PrtVo> prtL = handler.getPortListVo();
//        for (PrtVo t : prtL) {
//            System.out.println("t:" + t.toString());
//        }
        // System.out.println("parseConfVO:" + confvo.toString());
    }
    

    public static void main(String[] args) throws Exception {
        Spf saxparserconf = new Spf();
        saxparserconf.parseConfVOPrt(ConfVO.getInstance().getInitialLoc());
    }
}
