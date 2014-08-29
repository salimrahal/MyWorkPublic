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
import vo.CodecVo;
import vo.PrtStsVo;

/**
 *
 * @author salim
 */
public class Spf {

    CHr handler;
    SAXParserFactory parserFactor;
    Codechandler codechandler;

    public Spf() {
        parserFactor = SAXParserFactory.newInstance();

    }

    @Deprecated
    public void parseConfVOPrtSts(String confUri) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        CHr handler = new CHr();
        File f = new File(confUri);
      
        parser.parse(confUri, handler);
        ConfVO confvo = handler.confVO;
        List<PrtStsVo> prtstsL = handler.getPrtStsList();
        for (PrtStsVo t : prtstsL) {
            System.out.println("t:" + t.toString());
        }
        // System.out.println("parseConfVO:" + confvo.toString());
    }
    
     public void parseConfVOPrtSig(String confUri) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        PortSigHandler handler = new PortSigHandler();
        File f = new File(confUri);
        parser.parse(confUri, handler);
        // System.out.println("parseConfVO:" + confvo.toString());
    }
    
    
    public void parseConfVOCodec(String confUri) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        codechandler = new Codechandler();
        File f = new File(confUri);
        parser.parse(confUri, codechandler);
        ConfVO confvo = codechandler.confVO;
        List<CodecVo> codecVoL = codechandler.getCodecListVo();
        for (CodecVo t : codecVoL) {
            System.out.println("t:" + t.toString());
        }
        // System.out.println("parseConfVO:" + confvo.toString());
    }

    public static void main(String[] args) throws Exception {
        Spf saxparserconf = new Spf();
        saxparserconf.parseConfVOPrtSts("/home/salim/public_html/siptoolsconfig/config.xml");
    }
}
