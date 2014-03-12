/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsingxmlAdv;

/**
 *
 * @author salim
 * http://www.journaldev.com/1198/java-sax-parser-example-tutorial-to-parse-xml-to-list-of-objects
 */
import java.io.File;
import java.io.IOException;
import java.util.List;
 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
 
import org.xml.sax.SAXException;
 
public class XMLParserSax {
 
    public static void main(String[] args) {
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    try {
        SAXParser saxParser = saxParserFactory.newSAXParser();
        MyHandler handler = new MyHandler();
       // saxParser.parse(new File("parsingxmlAdv/employees.xml"), handler);
        saxParser.parse(ClassLoader.getSystemResourceAsStream("parsingxmlAdv/employees.xml"), handler);
        //Get Employees list
        List<Employee> empList = handler.getEmpList();
        //print employee information
        for(Employee emp : empList)
            System.out.println(emp);
    } catch (ParserConfigurationException | SAXException | IOException e) {
        e.printStackTrace();
    }
    }
 
}