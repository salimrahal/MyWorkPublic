/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safirasoft;

import bo.Logic;
import cfg.Spf;
import cfg.vo.ConfVO;
import dao.TestDao;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import vo.CodecVo;
import vo.CodecVoList;
import vo.JtrVo;
import vo.LatVo;
import vo.PrtMiscVo;
import vo.PrtStsVo;
import vo.PrtStstVoList;
import vo.ResVo;

/**
 *
 * @author salim
 */
@WebService(serviceName = "Pivot")
public class Pivot {

    Spf saxparserconf;
    TestDao tstDao;

    public Pivot() {
        saxparserconf = new Spf();
        tstDao = new TestDao();
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
     * Web service operation called by Sip tool server jar
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
     * Web service operation 1- it gets 2 free port from Db 2- signaling port
     * from the xml 3- Ip server
     */
    @WebMethod(operationName = "getMiscPorts")
    public PrtMiscVo getMiscPorts() throws ParserConfigurationException, SAXException, IOException {
        bo.Logic logic = new Logic();
        PrtMiscVo prtMisc = logic.renderPortMiscVo("f");
        return prtMisc;
    }

    /**
     * Web service operation
     *
     * @param tid
     * @param pld
     * @return result 1 | -1
     */
    @WebMethod(operationName = "savePLD")
    public Integer savePLD(@WebParam(name = "tid") String tid, @WebParam(name = "pld") float pld) {
        Integer res = -1;
        try {
            //securitycheck for the testid
//        if (tid.length() == StaticVar.TEST_ID_SIZE) {
            if (tstDao.updateTestPacketLostDown(tid, pld)) {
                res = 1;
            }
//        } else {
//            System.out.println("Error: savePLD:: received testId is different from the accepted length!");
//        }
        } catch (Exception ex) {
            Logger.getLogger(Pivot.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    /**
     * Web service operation: save lat, jitter down
     */
    @WebMethod(operationName = "svLJD")
    public Integer svLJD(@WebParam(name = "ti") String ti, @WebParam(name = "latdwnpk") int latdwnpk, @WebParam(name = "latdwnav") int latdwnav, @WebParam(name = "jitdwpk") int jitdwpk, @WebParam(name = "jitdwav") int jitdwav) {
        int res = -1;
        LatVo latObj = new LatVo(latdwnpk, latdwnav);
        JtrVo jObj = new JtrVo(jitdwpk, jitdwav);
        try {
            //securitycheck for the testid
         //   if (ti.length() == StaticVar.TEST_ID_SIZE) {
                if (tstDao.updateLatJitDown(ti, latObj, jObj)) {
                    res = 1;
                }
//            } else {
//                System.out.println("Error: svLJD:: received testId is different from the accepted length!");
//            }
        } catch (Exception ex) {
            Logger.getLogger(Pivot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    /*
    
     */

    /**
     * Web service operation:
     *  retrieve test result for the client and should hide the testId from the response
     */
    @WebMethod(operationName = "getrs")
    public ResVo getrs(@WebParam(name = "ti") String ti) {
        ResVo rs = null;
        try {
            rs = tstDao.getRes(ti);
            
        } catch (Exception ex) {
            Logger.getLogger(Pivot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
