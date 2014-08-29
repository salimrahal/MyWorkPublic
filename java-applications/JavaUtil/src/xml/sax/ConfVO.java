/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.sax;

import java.util.List;

/**
 *
 * @author salim Singleton config file
 */
public class ConfVO {

    String ipServer;
    String agentname;
    String sipIdLocal;
    List<PrtStsVo> prtStsList;
    //String loc = "/home/salim/public_html/siptoolsconfig/config.xml";
    String initialLoc = "/var/www/html/siptools.nexogy.com/siptoolsconfig/config.xml";
    private static final ConfVO INSTANCE = new ConfVO();

    private ConfVO() {
    }
    
    public String getLoc() {
        return initialLoc;
    }

    public void setLoc(String location) {
        this.initialLoc = location;
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

    @Override
    public String toString() {
        return "ConfVO{" + "ipServer=" + ipServer + ", agentname=" + agentname + ", sipIdLocal=" + sipIdLocal + ", prtStsList=" + prtStsList + ", initialLoc=" + initialLoc + '}';
    }
}
