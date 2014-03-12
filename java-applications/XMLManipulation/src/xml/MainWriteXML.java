/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author salim
 * http://stackoverflow.com/questions/4898590/generating-xml-using-sax-and-java
 */
/*
 * Write XML using XMLStreamWriter
 */
public class MainWriteXML {
     public static void main(String[] args) throws Exception {
    OutputStream outputStream = new FileOutputStream(new File("doc.xml"));

XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(
                new OutputStreamWriter(outputStream, "utf-8"));

out.writeStartDocument();
out.writeStartElement("doc");

out.writeStartElement("title");
out.writeCharacters("Document Title");
out.writeEndElement();

out.writeEndElement();
out.writeEndDocument();

out.close();
    }
}
