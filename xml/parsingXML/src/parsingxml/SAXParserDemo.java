/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsingxml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author salim
 */
public class SAXParserDemo {

    public static void main(String[] args) throws Exception {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SAXHandlerObj handler = new SAXHandlerObj();       
       // parser.parse(ClassLoader.getSystemResourceAsStream("xml/employee.xml"),
            //    handler);
        parser.parse(ClassLoader.getSystemResourceAsStream("parsingxml/employee.xml"), handler);
        //Printing the list of employees obtained from XML
        for (Employee emp : handler.empList) {
            System.out.println(emp);
        }
    }
}
