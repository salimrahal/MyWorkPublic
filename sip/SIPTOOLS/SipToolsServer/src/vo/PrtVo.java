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
public class PrtVo {
    String prtNum;
   

    public String getPrtNum() {
        return prtNum;
    }

    public void setPrtNum(String prtNum) {
        this.prtNum = prtNum;
    }

    @Override
    public String toString() {
        return "PrtVo{" + "prtNum=" + prtNum + '}';
    }
    

}
