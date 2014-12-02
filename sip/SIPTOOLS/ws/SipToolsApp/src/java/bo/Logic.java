/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import cfg.Spf;
import cfg.vo.ConfVO;
import dao.PrtDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import vo.PrtMiscVo;

/**
 *
 * @author salim
 */
public class Logic {
PrtDao prtdao ;
    public Logic() {
        prtdao = new PrtDao();
    }
    
    /*
    
    */
    public PrtMiscVo renderPortMiscVo(String status) throws ParserConfigurationException, SAXException, IOException{
       PrtMiscVo prtMisc = null ;   
    try {
        //retreive free ports from Db
        prtMisc = prtdao.retrievePorts(status);
        //retreive sigport and serverIp from xml
          Spf saxparserconf = new Spf();
          ConfVO confvo = ConfVO.getInstance();
          saxparserconf.parseConfVOPrtSig(confvo.getInitialLoc());
          prtMisc.setPrtSigNum(String.valueOf(confvo.getPortSig()));
          prtMisc.setServerIp(confvo.getIpServer());
          //prtMisc.toString();
          
    } catch (NamingException ex) {
        Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return prtMisc;
    }
}
