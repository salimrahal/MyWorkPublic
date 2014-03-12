/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsingpidfxml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 *
 * @author salim
 */
public class SAXParserPresence {
     public static void main(String[] args) throws Exception {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        Presencehandler handler = new Presencehandler();       
       // parser.parse(ClassLoader.getSystemResourceAsStream("xml/employee.xml"),
            //    handler);
        parser.parse(ClassLoader.getSystemResourceAsStream("parsingpidfxml/presentity.xml"), handler);
        //Printing the list of employees obtained from XML
       Presence presenceObj = handler.presObj;
         System.out.println(presenceObj.toString());
          System.out.println(presenceObj.getPresentitySipURItarget()+"/"+presenceObj.getPresentityMethod("method="));
}
}
