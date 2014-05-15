/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algVo.config;

import algVo.Combination;
import java.util.List;

/**
 *
 * @author salim Singleton config file
 */
public class ConfVO {

    List<Combination> lcomb;

    private static String ipServer;
    private static String agentname;
    private static String sipIdLocal;

    private static final ConfVO INSTANCE = new ConfVO();

    private ConfVO() {
    }

    public List<Combination> getLcomb() {
        return lcomb;
    }

    public void setLcomb(List<Combination> lcomb) {
        this.lcomb = lcomb;
    }

    public static ConfVO getInstance() {
        return INSTANCE;
    }

    public static String getAgentname() {
        return agentname;
    }

    public static void setAgentname(String agentname) {
        ConfVO.agentname = agentname;
    }

    public static String getIpServer() {
        return ipServer;
    }

    public static void setIpServer(String ipServer) {
        ConfVO.ipServer = ipServer;
    }

    public static String getSipIdLocal() {
        return sipIdLocal;
    }

    public static void setSipIdLocal(String sipIdLocal) {
        ConfVO.sipIdLocal = sipIdLocal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.lcomb != null ? this.lcomb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConfVO other = (ConfVO) obj;
        return true;
    }

}
