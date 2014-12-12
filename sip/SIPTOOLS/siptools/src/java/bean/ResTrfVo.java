/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.SipToolsBO;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author salim
 */
public class ResTrfVo implements Serializable {

    String cnme;
    String puip;
    String cdc;
    int tlth;
    Date sDate;
    Date eDate;
    String sDateview;
    String eDateView;
    float uppkloss;
    int uplatpeak;
    int uplatav;
    int upjtpeak;
    int upjtav;
    float dopkloss;
    int dolatpeak;
    int dolatav;
    int dojtpeak;
    int dojtav;
    String finalresult;
    String reason;

    public ResTrfVo(String cnme, String puip, String cdc, int tlth) {
        this.cnme = cnme;
        this.puip = puip;
        this.cdc = cdc;
        this.tlth = tlth;
    }

    public String getsDateview() {
        return sDateview;
    }

    public void setsDateview(String sDateview) {
        this.sDateview = sDateview;
    }

    public String geteDateView() {
        return eDateView;
    }

    public void seteDateView(String eDateView) {
        this.eDateView = eDateView;
    }

    public String getCnme() {
        return cnme;
    }

    public void setCnme(String cnme) {
        this.cnme = cnme;
    }

    public String getPuip() {
        return puip;
    }

    public void setPuip(String puip) {
        this.puip = puip;
    }

    public String getCdc() {
        return cdc;
    }

    public void setCdc(String cdc) {
        this.cdc = cdc;
    }

    public int getTlth() {
        return tlth;
    }

    public void setTlth(int tlth) {
        this.tlth = tlth;
    }

    public float getUppkloss() {
        return uppkloss;
    }

    public void setUppkloss(float uppkloss) {
        this.uppkloss = uppkloss;
    }

    public int getUplatpeak() {
        return uplatpeak;
    }

    public void setUplatpeak(int uplatpeak) {
        this.uplatpeak = uplatpeak;
    }

    public int getUplatav() {
        return uplatav;
    }

    public void setUplatav(int uplatav) {
        this.uplatav = uplatav;
    }

    public int getUpjtpeak() {
        return upjtpeak;
    }

    public void setUpjtpeak(int upjtpeak) {
        this.upjtpeak = upjtpeak;
    }

    public int getUpjtav() {
        return upjtav;
    }

    public void setUpjtav(int upjtav) {
        this.upjtav = upjtav;
    }

    public float getDopkloss() {
        return dopkloss;
    }

    public void setDopkloss(float dopkloss) {
        this.dopkloss = dopkloss;
    }

    public int getDolatpeak() {
        return dolatpeak;
    }

    public void setDolatpeak(int dolatpeak) {
        this.dolatpeak = dolatpeak;
    }

    public int getDolatav() {
        return dolatav;
    }

    public void setDolatav(int dolatav) {
        this.dolatav = dolatav;
    }

    public int getDojtpeak() {
        return dojtpeak;
    }

    public void setDojtpeak(int dojtpeak) {
        this.dojtpeak = dojtpeak;
    }

    public int getDojtav() {
        return dojtav;
    }

    public void setDojtav(int dojtav) {
        this.dojtav = dojtav;
    }

    public Date getsDate() {
        return sDate;
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    public Date geteDate() {
        return eDate;
    }

    public void seteDate(Date eDate) {
        this.eDate = eDate;
    }

    public String getFinalresult() {
        if (SipToolsBO.isES(this)) {
            finalresult = SipToolsBO.FINAL_RESULT_FAILED;
        } else {
            finalresult = SipToolsBO.FINAL_RESULT_PASSED;
        }
        return finalresult;
    }

    public void setFinalresult(String finalresult) {
        this.finalresult = finalresult;
    }

    public String getReason() {
        if (SipToolsBO.isES(this)) {
            reason = SipToolsBO.REASON_FAILED_TRF_TIMEOUT;
        } else {
            reason = SipToolsBO.REASON_PASSED_TRF;
        }
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ResVo{" + "cnme=" + cnme + ", puip=" + puip + ", cdc=" + cdc + ", tlth=" + tlth + ", sDate=" + sDate + ", eDate=" + eDate + ", uppkloss=" + uppkloss + ", uplatpeak=" + uplatpeak + ", uplatav=" + uplatav + ", upjtpeak=" + upjtpeak + ", upjtav=" + upjtav + ", dopkloss=" + dopkloss + ", dolatpeak=" + dolatpeak + ", dolatav=" + dolatav + ", dojtpeak=" + dojtpeak + ", dojtav=" + dojtav + '}';
    }

}
