/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.xmlparser;

import vo.PresenceVO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.sip.message.Request;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author salim
 * It parses the xml to a POJO
 */
public class SAXParserPresence {

    Presencehandler handler;
    SAXParserFactory parserFactor;

    public SAXParserPresence() {
        handler = new Presencehandler();
        parserFactor = SAXParserFactory.newInstance();

    }

    public PresenceVO buildPresence(InputStream inputStreamPresence) throws ParserConfigurationException, SAXException, IOException {
        SAXParser parser = parserFactor.newSAXParser();
        // parser.parse(ClassLoader.getSystemResourceAsStream("xml/employee.xml"),
        //    handler);
        parser.parse(inputStreamPresence, handler);
        //Printing the list of employees obtained from XML
        PresenceVO presenceObj = handler.presObj;
        return presenceObj;
    }

    /*
     * this function is accessed by multiple threads
     */
    synchronized public PresenceVO ProcessPresence(Request request, SAXParserPresence saxparser) throws ParserConfigurationException, SAXException, IOException {
        //get the presence as input stream
        InputStream presenceInputStream = retrievePresenceAsInputStream(request);
        //retreive the obj given the xml as input stream
        PresenceVO presenceObj = saxparser.buildPresence(presenceInputStream);
        return presenceObj;
    }

    /**
     * retrievePresenceAsString: it returns the presence XML as InputStream from
     * a Request
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    public InputStream retrievePresenceAsInputStream(Request request) throws UnsupportedEncodingException {
        InputStream presenceInputStream;
        byte[] raw = request.getRawContent();
        presenceInputStream = new ByteArrayInputStream(raw);
//               ContentEncodingHeader contentEncoHd =  request.getContentEncoding();
//               String encoding = UTF_8_ENCODING;
//               if(contentEncoHd != null){
//                   encoding = contentEncoHd.getEncoding();
//               }else{
//                   System.out.println("\n contentEncoHeader is NULL");
//               }
        // presenceContent = new String(raw, encoding);
        return presenceInputStream;
    }

    public static void main(String[] args) throws Exception {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        Presencehandler handler = new Presencehandler();
        // parser.parse(ClassLoader.getSystemResourceAsStream("xml/employee.xml"),
        //    handler);
        parser.parse(ClassLoader.getSystemResourceAsStream("bo/xmlparser/presentity.xml"), handler);

        //Printing the list of employees obtained from XML
        PresenceVO presenceObj = handler.presObj;
        System.out.println(presenceObj.toString());
        System.out.println(presenceObj.getPresentitySipURItarget() + "/" + presenceObj.getPresentityMethod("method="));
    }
    //create XML file
}
