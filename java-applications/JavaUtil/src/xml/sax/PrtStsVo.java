/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xml.sax;

/**
 *
 * @author salim
 */
public class PrtStsVo {
    String prtNum;
    String sts; // f or b

    public String getPrtNum() {
        return prtNum;
    }

    public void setPrtNum(String prtNum) {
        this.prtNum = prtNum;
    }

  

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    @Override
    public String toString() {
        return "PrtStsVo{" + "prtNum=" + prtNum + ", sts=" + sts + '}';
    }
    
    
}
