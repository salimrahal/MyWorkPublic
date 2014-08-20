/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sipserver.trf.bean;

/**
 *
 * @author salim
 * handles parameters from the client
 */
public class Param {
    //tstid=66d2b2fc-6f54-49cf-a495-e158ebb814c7;codec=g711;timelength=15;custname=custnamefromAppletclass

    private String timelength;

    public String getTimelength() {
        return timelength;
    }

    public void setTimelength(String timelength) {
        this.timelength = timelength;
    }

    
    private String tstid;

    public String getTstid() {
        return tstid;
    }

    public void setTstid(String tstid) {
        this.tstid = tstid;
    }

    private String codec;

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    private String custname;

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    @Override
    public String toString() {
        return "Param{" + "timelength=" + timelength + ", tstid=" + tstid + ", codec=" + codec + ", custname=" + custname + '}';
    }
    
}
