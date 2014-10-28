/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import java.util.Date;

/**
 *
 * @author salim `uid` varchar(36) DEFAULT NULL, `customerName` varchar(40)
 * DEFAULT NULL, `publicIp` varchar(16) DEFAULT NULL, `codec` varchar(8) DEFAULT
 * NULL, `testLength` smallint(5) DEFAULT NULL, `startTime` datetime DEFAULT
 * NULL, `endTime` datetime DEFAULT NULL, `uploadPacketLost` smallint(4) DEFAULT
 * NULL, `uploadLatencyPeak` smallint(4) DEFAULT NULL, `uploadLatencyAvg`
 * smallint(4) DEFAULT NULL, `uploadJitterPeak` smallint(4) DEFAULT NULL,
 * `uploadJitterAvg` smallint(4) DEFAULT NULL, `downloadPacketLost` smallint(4)
 * DEFAULT NULL, `downloadLatencyPeak` smallint(4) DEFAULT NULL,
 * `downloadLatencyAvg` smallint(4) DEFAULT NULL, `downloadJitterPeak`
 * smallint(4) DEFAULT NULL, `downloadJitterAvg` smallint(4) DEFAULT NULL,
 *
 */
public class ResVo {

    String cnme;
    String puip;
    String cdc;
    int tlth;
    Date sDate;
    Date eDate;
    float uppkloss = -1;
    int uplatpeak = -1;
    int uplatav = -1;
    int upjtpeak = -1;
    int upjtav = -1;
    float dopkloss = -1;
    int dolatpeak = -1;
    int dolatav = -1;
    int dojtpeak = -1;
    int dojtav = -1;

    public ResVo(String cnme, String puip, String cdc, int tlth) {
        this.cnme = cnme;
        this.puip = puip;
        this.cdc = cdc;
        this.tlth = tlth;
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

    @Override
    public String toString() {
        return "ResVo{" + "cnme=" + cnme + ", puip=" + puip + ", cdc=" + cdc + ", tlth=" + tlth + ", sDate=" + sDate + ", eDate=" + eDate + ", uppkloss=" + uppkloss + ", uplatpeak=" + uplatpeak + ", uplatav=" + uplatav + ", upjtpeak=" + upjtpeak + ", upjtav=" + upjtav + ", dopkloss=" + dopkloss + ", dolatpeak=" + dolatpeak + ", dolatav=" + dolatav + ", dojtpeak=" + dojtpeak + ", dojtav=" + dojtav + '}';
    }

}
