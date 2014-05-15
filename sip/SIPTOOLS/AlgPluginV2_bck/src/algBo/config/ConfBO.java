/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algBo.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.xml.sax.SAXException;
import algVo.config.ConfVO;

/**
 * @author salim ConfBO: - if the config.xml exists --> retrieve it --> and
 * create the singleton ConfVO - otherwise the user need to fill the parameters
 * from Settings and I should save them in this xml file
 *
 * TODO: Methode to write: 1- dev the write xml: given confVO --> xml doc 2-
 * function that parse from xml to POJO and instantiate the singleton confVO
 *
 * http://stackoverflow.com/questions/4898590/generating-xml-using-sax-and-java
 *
 * Write XML using XMLStreamWriter
 * * <properties>
 * <comment>add comments</comment>
 * <entry key="SIP-SERVER">173.231.103.38</entry>
 * <entry key="SIP-SERVER-PORT">5060</entry>
 * <entry key="Local-IP"></entry>
 * <entry key="SIP-LOCAL-PORT">5060</entry>
 * <entry key="SIP-ID">299</entry>
 * <entry key="SIP-Realm/Domain">TALKSWITCH</entry>
 * <entry key="SIP-PROTOCOL">udp</entry>
 * <entry key="SIP-Auth-ID">user299</entry>
 * <entry key="SIP-PASSWORD">Winter2013</entry>
 * <entry
 * key="SIP-LOCAL-MACHINE-ID">b671df70-60b5-11e3-949a-0800200c9a66</entry>
 * </properties>
 */
public class ConfBO {
   
    
    public static void parseConfVO() throws ParserConfigurationException, SAXException, IOException{
        SAXParserConf.parseConfVO();
    }

     public static String getRegisteredSipURI() {
        ConfVO confVO = ConfVO.getInstance();
        
        String sipid = confVO.getExtSipLocal();
        String serverIp = confVO.getUASIp();
        int portlocal = confVO.getUACPort();
        return "sip:"+sipid+"@"+serverIp+":"+portlocal;
    }
    
     public static String getSipInstanceUUID() {
        ConfVO confVO = ConfVO.getInstance();
        String uuid = confVO.getUuid();
        return "\"<urn:uuid:" + uuid + ">\"";
    }
     public static UUID getRandomUUID(){
         return UUID.randomUUID();
     }
     
    public static void overwriteXMLConfig() throws FileNotFoundException, XMLStreamException, UnsupportedEncodingException {
        ConfVO confVO = ConfVO.getInstance();
        File f = new File("config.xml");
        System.out.println(f.exists());
        OutputStream outputStream = new FileOutputStream(f);

        XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(
                new OutputStreamWriter(outputStream, "utf-8"));

        out.writeStartDocument();
        out.writeStartElement("PROPERTIES");

        out.writeStartElement("SIP-SERVER");
        out.writeCharacters(confVO.getUASIp());
        out.writeEndElement();

        out.writeStartElement("SIP-SERVER-PORT");
        out.writeCharacters(String.valueOf(confVO.getUASPort()));
        out.writeEndElement();

        out.writeStartElement("Local-IP");
        out.writeCharacters(confVO.getUACIp());
        out.writeEndElement();

        out.writeStartElement("SIP-LOCAL-PORT");
        out.writeCharacters(String.valueOf(confVO.getUACPort()));
        out.writeEndElement();

        out.writeStartElement("LOCAL-HOST-NAME");
        out.writeCharacters(confVO.getHostNameLocal());
        out.writeEndElement();
        
        out.writeStartElement("SIP-ID");
        out.writeCharacters(confVO.getExtSipLocal());
        out.writeEndElement();

        out.writeStartElement("SIP-Realm-Domain");
        out.writeCharacters(confVO.getDomain());
        out.writeEndElement();

        out.writeStartElement("SIP-Auth-ID");
        out.writeCharacters(confVO.getUsername());
        out.writeEndElement();

        out.writeStartElement("SIP-PASSWORD");
        out.writeCharacters(confVO.getPassword());
        out.writeEndElement();

        out.writeStartElement("EXT-TO-MONITOR");
        out.writeCharacters(confVO.getExtsToMonitor());
        out.writeEndElement();
        
         out.writeStartElement("SIP-PROTOCOL");
        out.writeCharacters(confVO.getProtocol());
        out.writeEndElement();

        out.writeStartElement("SIP-LOCAL-MACHINE-UUID");
        out.writeCharacters(confVO.getUuid());
        out.writeEndElement();

        out.writeEndElement();
        out.writeEndDocument();

        out.close();
    }

    public static void main(String[] args) throws Exception {
        overwriteXMLConfig();
    }
}
