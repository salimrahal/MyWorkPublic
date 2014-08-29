/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vo;

/**
 *
 * @author salim
 */
public class PrtMiscVo {
     String prtTrfNum = "null";
     String prtLatNum = "null";
     String prtSigNum = "null";
    String serverIp;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    
    
    public String getPrtTrfNum() {
        return prtTrfNum;
    }

    public void setPrtTrfNum(String prtTrfNum) {
        this.prtTrfNum = prtTrfNum;
    }

    public String getPrtLatNum() {
        return prtLatNum;
    }

    public void setPrtLatNum(String prtLatNum) {
        this.prtLatNum = prtLatNum;
    }

    public String getPrtSigNum() {
        return prtSigNum;
    }

    public void setPrtSigNum(String prtSigNum) {
        this.prtSigNum = prtSigNum;
    }

    @Override
    public String toString() {
        return "PrtMiscVo{" + "prtTrfNum=" + prtTrfNum + ", prtLatNum=" + prtLatNum + ", prtSigNum=" + prtSigNum + ", serverIp=" + serverIp + '}';
    }
     
   
     
}
