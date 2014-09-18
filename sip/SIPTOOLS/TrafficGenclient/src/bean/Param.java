/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

/**
 *
 * @author salim
 * handles parameters from the client
 */
public class Param {
    //traffic TCPServer:receiving:tstid=8c0514f3-621e-493d-9a71-c1b836c95dab;codec=SILK;
    //timelength=120;custname=custnamefromAppletclass;portlat=5095;porttrf=5108

    private String timelength;//second
    private String tstid;
    private String codec;
    
    private String custname;
    String clientIp;
    String portlat;
    String portrfU;
    String portrfD;

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getPortlat() {
        return portlat;
    }

    public void setPortlat(String portlat) {
        this.portlat = portlat;
    }

    public String getPortrfU() {
        return portrfU;
    }

    public void setPortrfU(String portrfU) {
        this.portrfU = portrfU;
    }

    public String getPortrfD() {
        return portrfD;
    }

    public void setPortrfD(String portrfD) {
        this.portrfD = portrfD;
    }

    
    
    
    public String getTimelength() {
        return timelength;
    }

    public void setTimelength(String timelength) {
        this.timelength = timelength;
    }

    

    public String getTstid() {
        return tstid;
    }

    public void setTstid(String tstid) {
        this.tstid = tstid;
    }



    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }


    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    @Override
    public String toString() {
        return "Param{" + "timelength=" + timelength + ", tstid=" + tstid + ", codec=" + codec + ", custname=" + custname + ", clientIp=" + clientIp + ", portlat=" + portlat + ", portrfU=" + portrfU + ", portrfD=" + portrfD + '}';
    }

    
    
}
