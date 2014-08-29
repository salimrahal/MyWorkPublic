/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safirasoft;

import bo.Logic;
import cfg.Spf;
import cfg.vo.ConfVO;
import java.io.IOException;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import vo.CodecVo;
import vo.CodecVoList;
import vo.PrtMiscVo;
import vo.PrtStsVo;
import vo.PrtStstVoList;

/**
 *
 * @author salim
 */
@WebService(serviceName = "Pivot")
public class Pivot {

    Spf saxparserconf;

    public Pivot() {
        saxparserconf = new Spf();
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /*
    it returns port/status and server Ip address
    */
    @WebMethod(operationName = "getPrtSts")
    public PrtStstVoList getPrtSts() throws ParserConfigurationException, SAXException, IOException {
/*
        TODO: ws should extract port/status from DB instead of xml file
        */
        ConfVO confVo = ConfVO.getInstance();
        saxparserconf.parseConfVOPrtSts(confVo.getInitialLoc());
        List<PrtStsVo> prtstsL = confVo.getPrtStsList();
        PrtStstVoList pl = new PrtStstVoList(prtstsL);
        pl.setServerIp(confVo.getIpServer());
        return pl;
    }
    
    

    /**
     * Web service operation
     * called by Sip tool server jar
     */
    @WebMethod(operationName = "getConfLoc")
    public String getConfLoc() {
        ConfVO confVo = ConfVO.getInstance();
        return confVo.getInitialLoc();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getcodecs")
    public CodecVoList getcodecs() throws ParserConfigurationException, SAXException, IOException {
        ConfVO confVo = ConfVO.getInstance();
        saxparserconf.parseConfVOCodec(confVo.getInitialLoc());
        List<CodecVo> codecVoL = confVo.getCodecList();
        CodecVoList cl = new CodecVoList(codecVoL);
        return cl;
    }

    /**
     * Web service operation
     * 1- it gets 2 free port from Db
       2- signaling port from the xml
       3- Ip server
*/
    @WebMethod(operationName = "getMiscPorts")
    public PrtMiscVo getMiscPorts() throws ParserConfigurationException, SAXException, IOException {
        bo.Logic logic = new Logic();
        PrtMiscVo prtMisc = logic.renderPortMiscVo("f");
        return prtMisc;
    }

}
