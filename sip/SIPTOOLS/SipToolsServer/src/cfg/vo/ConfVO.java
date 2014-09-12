/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfg.vo;

import java.util.List;
import vo.PrtVo;

/**
 *
 * @author salim Singleton config file
 */
public class ConfVO {

    String ipServer;
    String agentname;
    String sipIdLocal;
    List<PrtVo> prtList;
    Integer portSig;//for signaling
    //String initialLoc = "/home/salim/public_html/siptoolsconfig/config.xml";//local host test
    String initialLoc = "/var/www/html/siptools.nexogy.com/siptoolsconfig/config.xml";
    private static final ConfVO INSTANCE = new ConfVO();

    private ConfVO() {
    }

    public Integer getPortSig() {
        return portSig;
    }

    public void setPortSig(Integer portSig) {
        this.portSig = portSig;
    }
    
    

    public String getInitialLoc() {
        return initialLoc;
    }

    public void setInitialLoc(String initialLoc) {
        this.initialLoc = initialLoc;
    }
    
    
    public List<PrtVo> getPrtList() {
        return prtList;
    }

    public void setPrtList(List<PrtVo> prtStsList) {
        this.prtList = prtStsList;
    }

    public static ConfVO getInstance() {
        return INSTANCE;
    }

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getSipIdLocal() {
        return sipIdLocal;
    }

    public void setSipIdLocal(String sipIdLocal) {
        this.sipIdLocal = sipIdLocal;
    }
}
