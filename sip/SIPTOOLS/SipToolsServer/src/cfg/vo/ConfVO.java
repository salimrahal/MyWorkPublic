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

    String ipServerAlg;
    String ipServerTrf;
    String agentname;
    String sipIdLocal;
    List<PrtVo> prtList;
    Integer portSig;//for signaling 
    //String initialLoc = "/home/salim/Development/MyWorkPublic/sip/SIPTOOLS/SipToolsServer/src/config.xml";//"/home/salim/public_html/siptoolsconfig/config.xml";//local host test
    //for remote test
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

    public String getIpServerAlg() {
        return ipServerAlg;
    }

    public void setIpServerAlg(String ipServerAlg) {
        this.ipServerAlg = ipServerAlg;
    }

    public String getIpServerTrf() {
        return ipServerTrf;
    }

    public void setIpServerTrf(String ipServerTrf) {
        this.ipServerTrf = ipServerTrf;
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

    @Override
    public String toString() {
        return "ConfVO{" + "ipServerAlg=" + ipServerAlg + ", ipServerTrf=" + ipServerTrf + ", agentname=" + agentname + ", sipIdLocal=" + sipIdLocal + ", prtList=" + prtList + ", portSig=" + portSig + ", initialLoc=" + initialLoc + '}';
    }

    
    
}
