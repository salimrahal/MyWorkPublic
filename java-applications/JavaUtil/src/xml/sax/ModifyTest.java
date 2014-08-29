/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.sax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author salim
 */
public class ModifyTest {

    public static void main(String[] args) throws Exception {
        //modifyingXml();
        modifyXmlreadFile();
    }

    public static void modifyXmlreadFile() throws SAXException, TransformerConfigurationException, TransformerException, FileNotFoundException {
        String confUri = "/home/salim/public_html/siptoolsconfig/config.xml";
        File f = new File(confUri);
        InputStream inputStream = new FileInputStream(confUri);
        Reader reader = new InputStreamReader(inputStream);
        InputSource isrc = new InputSource(reader);
        
        XMLReader xr = new XMLFilterImpl(XMLReaderFactory.createXMLReader()) {
            private String tagName = "";
            private String attName = "";

            @Override
            public void startElement(String uri, String localName, String qName, Attributes atts)
                    throws SAXException, org.xml.sax.SAXException {
                tagName = qName;
                System.out.println("start element found:" + tagName + "/att[0]" + attName);
                super.startElement(uri, localName, qName, atts);
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                tagName = "";
                super.endElement(uri, localName, qName);
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException, org.xml.sax.SAXException {
                if (tagName.equals("value")) {
                    System.out.println("found value");
                    ch = "7777".toCharArray();
                    start = 0;
                    length = ch.length;
                }
                super.characters(ch, start, length);
            }
        };
        Source src = new SAXSource(xr, isrc);
        Result res = new StreamResult(System.out);
        TransformerFactory.newInstance().newTransformer().transform(src, res);
    }

    public static void modifyingXml() throws SAXException, TransformerConfigurationException, TransformerException {
        String xml = "<users><user><name id=\"1\">user1</name></user></users>";
        System.out.println("before=" + xml);
        XMLReader xr = new XMLFilterImpl(XMLReaderFactory.createXMLReader()) {
            private String tagName = "";
            private String attName = "";

            @Override
            public void startElement(String uri, String localName, String qName, Attributes atts)
                    throws SAXException, org.xml.sax.SAXException {
                tagName = qName;
                System.out.println("start element found:" + tagName + "/att[0]" + attName);
                super.startElement(uri, localName, qName, atts);
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                tagName = "";
                super.endElement(uri, localName, qName);
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException, org.xml.sax.SAXException {
                if (tagName.equals("name")) {
                    ch = "user2".toCharArray();
                    start = 0;
                    length = ch.length;
                }
                super.characters(ch, start, length);

                if (attName.equals("id")) {
                    ch = "2".toCharArray();
                    start = 0;
                    length = ch.length;
                }
                super.characters(ch, start, length);
            }
        };
        Source src = new SAXSource(xr, new InputSource(new StringReader(xml)));
        Result res = new StreamResult(System.out);
        TransformerFactory.newInstance().newTransformer().transform(src, res);
    }
}
