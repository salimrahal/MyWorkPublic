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
public class ResAlgVo implements Serializable {

    String cnme;
    String puip;
    Date sDate;
    Date eDate;
    String sDateview;
    String eDateView;
    String transport;
    int prtsrc;
    int prtdest;
    boolean algDetected;
    boolean firewallDetected;
    String finalresult;
    String reason;

    public ResAlgVo(String cnme, String puip, Date sDate, Date eDate, String transport, int prtsrc, int prtdest, boolean algDetected, boolean firewallDetected) {
        this.cnme = cnme;
        this.puip = puip;
        this.sDate = sDate;
        this.eDate = eDate;
        this.transport = transport;
        this.prtsrc = prtsrc;
        this.prtdest = prtdest;
        this.algDetected = algDetected;
        this.firewallDetected = firewallDetected;
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

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public int getPrtsrc() {
        return prtsrc;
    }

    public void setPrtsrc(int prtsrc) {
        this.prtsrc = prtsrc;
    }

    public int getPrtdest() {
        return prtdest;
    }

    public void setPrtdest(int prtdest) {
        this.prtdest = prtdest;
    }

    public boolean isAlgDetected() {
        return algDetected;
    }

    public void setAlgDetected(boolean algDetected) {
        this.algDetected = algDetected;
    }

    public boolean isFirewallDetected() {
        return firewallDetected;
    }

    public void setFirewallDetected(boolean firewallDetected) {
        this.firewallDetected = firewallDetected;
    }

    public void setFinalresult(String finalresult) {
        this.finalresult = finalresult;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFinalresult() {
        if (firewallDetected) {
            finalresult = SipToolsBO.FINAL_RESULT_FAILED;
        } else if (algDetected) {
            finalresult = SipToolsBO.FINAL_RESULT_FAILED;
        } else {
            finalresult = SipToolsBO.FINAL_RESULT_PASSED;
        }
        return finalresult;
    }

    public String getReason() {
        if (firewallDetected) {
            reason = SipToolsBO.REASON_FAILED_FW;
        } else if (algDetected) {
            reason = SipToolsBO.REASON_FAILED_ALG;
        } else {
            reason = SipToolsBO.REASON_PASSED_ALG;;
        }
        return reason;
    }

}
