/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.sax;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 * @author salim
 */
public class Main {

    CHr handler;
    SAXParserFactory parserFactor;

    public Main() {
        parserFactor = SAXParserFactory.newInstance();

    }

    public void parseConfVOPrtSts(String confUri) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        CHr handler = new CHr();
        File f = new File(confUri);

        parser.parse(confUri, handler);
        ConfVO confvo = handler.confVO;
        List<PrtStsVo> prtstsL = handler.getPrtStsList();
//        for (PrtStsVo t : prtstsL) {
//            System.out.println("t:" + t.toString());
//        }
        System.out.println("parseConfVO:" + confvo.toString());
    }

    public void updateConf(String confUri) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        parserFactor.setValidating(true);
        SAXParser parser = parserFactor.newSAXParser();
        UpdateHandler handler = new UpdateHandler();
        File f = new File(confUri);

        parser.parse(confUri, handler);

        ConfVO confvo = handler.confVO;
        List<PrtStsVo> prtstsL = handler.getPrtStsList();
        for (PrtStsVo t : prtstsL) {
            System.out.println("t:" + t.toString());
        }
        System.out.println("parseConfVO:" + confvo.toString());

    
    }

    public static void main(String[] args) throws Exception {
        String confUri = "/home/salim/public_html/siptoolsconfig/config.xml";
        Main saxparserconf = new Main();

        saxparserconf.updateConf(confUri);

//        for(int i=0;i<1000000;i++){
//                saxparserconf.parseConfVOPrtSts(confUri);
//        }
    }

}
