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

    String prtTrfNumUp = "null";
    String prtTrfNumDown = "null";
    String prtLatNum = "null";
    String prtSigNum = "null";
    String serverIp;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
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

    public String getPrtTrfNumUp() {
        return prtTrfNumUp;
    }

    public void setPrtTrfNumUp(String prtTrfNumUp) {
        this.prtTrfNumUp = prtTrfNumUp;
    }

    public String getPrtTrfNumDown() {
        return prtTrfNumDown;
    }

    public void setPrtTrfNumDown(String prtTrfNumDown) {
        this.prtTrfNumDown = prtTrfNumDown;
    }

    @Override
    public String toString() {
        return "PrtMiscVo{" + "prtTrfNumUp=" + prtTrfNumUp + ", prtTrfNumDown=" + prtTrfNumDown + ", prtLatNum=" + prtLatNum + ", prtSigNum=" + prtSigNum + ", serverIp=" + serverIp + '}';
    }

   

}
