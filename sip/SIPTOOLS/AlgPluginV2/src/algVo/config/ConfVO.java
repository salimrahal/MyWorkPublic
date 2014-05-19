/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algVo.config;

import algVo.Test;
import java.util.List;

/**
 *
 * @author salim Singleton config file
 */
public class ConfVO {

    List<Test> testL;

    String ipServer;
    String agentname;
    String sipIdLocal;

    private static final ConfVO INSTANCE = new ConfVO();

    private ConfVO() {
    }

    public List<Test> getTestL() {
        return testL;
    }

    public void setTestL(List<Test> testL) {
        this.testL = testL;
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

    @Override
    public String toString() {
        return "ConfVO{" + "lcomb=" + testL + ", ipServer=" + ipServer + ", agentname=" + agentname + ", sipIdLocal=" + sipIdLocal + '}';
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
