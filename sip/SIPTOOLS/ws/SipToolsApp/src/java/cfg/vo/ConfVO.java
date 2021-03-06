/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfg.vo;

import java.util.List;
import vo.CodecVo;
import vo.PrtStsVo;

/**
 *
 * @author salim Singleton config file
 */
public class ConfVO {
//ipserver used for Traffic Gen
    String ipServer;
    String agentname;
    String sipIdLocal;
    List<PrtStsVo> prtStsList;
    List<CodecVo> codecList;
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

    
    public List<CodecVo> getCodecList() {
        return codecList;
    }

    public void setCodecList(List<CodecVo> codecList) {
        this.codecList = codecList;
    }

    public String getInitialLoc() {
        return initialLoc;
    }

    public void setInitialLoc(String initialLoc) {
        this.initialLoc = initialLoc;
    }
   

    public List<PrtStsVo> getPrtStsList() {
        return prtStsList;
    }

    public void setPrtStsList(List<PrtStsVo> prtStsList) {
        this.prtStsList = prtStsList;
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

//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 89 * hash + (this.lcomb != null ? this.lcomb.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final ConfVO other = (ConfVO) obj;
//        return true;
//    }
}
