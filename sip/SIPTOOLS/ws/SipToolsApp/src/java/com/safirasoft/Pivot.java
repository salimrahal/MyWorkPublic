/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safirasoft;

import bo.Logic;
import cfg.Spf;
import cfg.vo.ConfVO;
import dao.PrtDao;
import dao.TestDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import vo.CodecVo;
import vo.CodecVoList;
import vo.JtrVo;
import vo.LatVo;
import vo.PrtMiscVo;
import vo.PrtStsVo;
import vo.ResVo;

/**
 *
 * @author salim
 */
@WebService(serviceName = "Pivot")
public class Pivot {

    Spf saxparserconf;
    TestDao tstDao;
    PrtDao prtDao;

    public Pivot() {
        saxparserconf = new Spf();
        tstDao = new TestDao();
        prtDao = new PrtDao();
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
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
     *  used by Udp traffic generator:
     */
    @WebMethod(operationName = "getcodecs")
    public CodecVoList getcodecs() throws ParserConfigurationException, SAXException, IOException {
        ConfVO confVo = ConfVO.getInstance();
        saxparserconf.parseConfVOCodec(confVo.getInitialLoc());
        List<CodecVo> codecVoL = confVo.getCodecList();
        CodecVoList cl = new CodecVoList(codecVoL);
        return cl;
    }

    /** used by Udp traffic generator:
     * Web service operation 1- it gets 2 free port from Db 2- signaling port
     * from the xml 3- Ip server
     */
    @WebMethod(operationName = "getMiscPorts")
    public PrtMiscVo getMiscPorts() throws ParserConfigurationException, SAXException, IOException {
        bo.Logic logic = new Logic();
        //todo: retrieve only one port instead of 3 ports (lat, packtUp, down)
        PrtMiscVo prtMisc = logic.renderPortMiscVo("f");
        return prtMisc;
    }

    /** used by Udp traffic generator:
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
     *  used by Udp traffic generator:
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
     * Web service operation: retrieve test result for the client and should
     * hide the testId from the response
     *  used by Udp traffic generator:
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

    /**
     * Web service operation
     * used by Udp traffic generator:
     * @return
     */
    @WebMethod(operationName = "retreiveAllPorts")
    public String retreiveAllPorts() {
        List<PrtStsVo> l;
        String res = null;
        try {
            l = prtDao.retrieveAllPorts();
            res = Arrays.toString(l.toArray());
        } catch (NamingException ex) {
            Logger.getLogger(Pivot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Pivot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "svAlgpam")
    public Integer svAlgpam(@WebParam(name = "tid") String tid, @WebParam(name = "trs") String trs, @WebParam(name = "prs") int prs, @WebParam(name = "prdes") int prdes, @WebParam(name = "cus") String cus, @WebParam(name = "pip") String pip) {
       //todo: same params into DB and start time too
        return null;
    }


    
    
}
