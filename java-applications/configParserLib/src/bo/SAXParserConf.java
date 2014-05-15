/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import vo.ConfVO;

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
    public static void parseConfVO() throws ParserConfigurationException, SAXException, IOException {
        
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        ConfHandler handler = new ConfHandler();
        //parser.parse(inputStream, handler);
        File f = new File("./config.xml");       
        parser.parse(f, handler);
        /*TODO: property file
         * 1- deve the POJO property file
         * 2- deve the prop handler
         * 3- retreive the prop Pojo
         */
        //Printing the list of employees obtained from XML
        ConfVO confvo = handler.confVO;
        System.out.println("parseConfVO:"+confvo.toString());
    }

    public static void main(String[] args) throws Exception {
         parseConfVO();
    }
}
