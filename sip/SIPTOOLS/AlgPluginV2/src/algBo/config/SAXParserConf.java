/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algBo.config;

import algVo.Test;
import algVo.config.ConfVO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author salim
 */
public class SAXParserConf {

    ConfHandler handler;
    SAXParserFactory parserFactor;

    public SAXParserConf() {
        handler = new ConfHandler();
        parserFactor = SAXParserFactory.newInstance();

    }

    /*
     * It pareses from XML to POJO the confVO
     */
    public void parseConfVO() throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        ConfHandler handler = new ConfHandler();
        //parser.parse(inputStream, handler);
        File f = new File("./config.xml");
        System.out.println("config exists?" + f.exists());
        parser.parse(f, handler);
        /*TODO: property file
         * 1- deve the POJO property file
         * 2- deve the prop handler
         * 3- retreive the prop Pojo
         */
        //Printing the list of employees obtained from XML
        ConfVO confvo = handler.confVO;
        List<Test> testL = handler.getTestList();
        for(Test t:testL)
            System.out.println("t:" + t.toString());
        System.out.println("parseConfVO:" + confvo.toString());
    }

    public static void main(String[] args) throws Exception {
        SAXParserConf saxparserconf = new SAXParserConf();
        saxparserconf.parseConfVO();
    }
}
