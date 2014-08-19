/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cfg;

import cfg.vo.ConfVO;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import vo.CodecVo;
import vo.PrtStsVo;

/**
 *
 * @author salim
 */
public class Codechandler extends DefaultHandler {

    ConfVO confVO = null;
    //var unsed, no content to retrieve
    String content = null;

    //List to hold Employees object
    private List<CodecVo> codecListVo = null;
    private CodecVo codecVo = null;

    //getter method for employee list
    public List<CodecVo> getCodecListVo() {
        return codecListVo;
    }

    public void setCodecListVo(List<CodecVo> codecListVo) {
        this.codecListVo = codecListVo;
    }

    @Override
    //Triggered when the start of tag is found.
    public void startElement(String uri, String localName,
            String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase("configuration")) {
            //Create a new presence object when the start tag is found         
            confVO = ConfVO.getInstance();
        }
       if (qName.equalsIgnoreCase("codec")) {
            String codec = attributes.getValue("name");
            boolean enabled = Boolean.valueOf(attributes.getValue("enabled"));
            codecVo = new CodecVo();
            codecVo.setCodec(codec);
            codecVo.setEnabled(enabled);
           

            if (codecListVo == null) {
                codecListVo = new ArrayList<>();
            }
        }
        /*
         TODO: complete all other properties
         */
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException {
        if (qName.equalsIgnoreCase("codec")) {
            //add test object to list
            codecListVo.add(codecVo);
            confVO.setCodecList(codecListVo);
        }
    }

    //get the content of the node
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();
    }
}
