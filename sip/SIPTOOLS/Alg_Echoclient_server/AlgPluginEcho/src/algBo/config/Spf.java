/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algBo.config;

import algVo.Test;
import algVo.config.ConfVO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.rmi.CORBA.Util;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author salim
 */
public class Spf {

    CHr handler;
    SAXParserFactory parserFactor;

    public Spf() {
        handler = new CHr();
        parserFactor = SAXParserFactory.newInstance();

    }

    public void parseConfVO(String confUri) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        CHr handler = new CHr();
        File f = new File(confUri);
        parser.parse(confUri, handler);
        ConfVO confvo = handler.confVO;
        List<Test> testL = handler.getTestList();
        for (Test t : testL) {
            System.out.println("t:" + t.toString());
        }
        // System.out.println("parseConfVO:" + confvo.toString());
    }

    public static void main(String[] args) throws Exception {
        Spf saxparserconf = new Spf();
        //saxparserconf.parseConfVO();
    }
}
