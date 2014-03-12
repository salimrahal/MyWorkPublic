/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package buddylist;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salim
 * 
 * 
<?xml version="1.0" encoding="UTF-8"?>
<presence>
<presentity uri="sip:299@10.0.254.125:5060;method=SUBSCRIBE"/>
<atom id="1">
<address uri="sip:299@10.0.254.125:5060">
<status status="open"/>
<msnstatus substatus="online"/>
</address>
</atom>
</presence>
* 
 * PresenceVO{presentity_URI=sip:299@10.0.254.125:5060;method=SUBSCRIBE, atom_ID=1, 
 * address_URI=sip:299@10.0.254.125:5060, status_status=open, msnstatus_substatus=online}
 */
public class PresenceVO {
    String presentity_URI;
    int atom_ID;
    String address_URI;
    String status_status;
    String msnstatus_substatus;
    
    public String getPresentity_URI() {
        return presentity_URI;
    }

    public void setPresentity_URI(String presentity_URI) {
        this.presentity_URI = presentity_URI;
    }

    public int getAtom_ID() {
        return atom_ID;
    }

    public void setAtom_ID(int atom_ID) {
        this.atom_ID = atom_ID;
    }

    public String getAddress_URI() {
        return address_URI;
    }

    public void setAddress_URI(String address_URI) {
        this.address_URI = address_URI;
    }

    public String getStatus_status() {
        return status_status;
    }

    public void setStatus_status(String status_status) {
        this.status_status = status_status;
    }

    public String getMsnstatus_substatus() {
        return msnstatus_substatus;
    }

    public void setMsnstatus_substatus(String msnstatus_substatus) {
        this.msnstatus_substatus = msnstatus_substatus;
    }   

    @Override
    public String toString() {
        return "Presence{" + "presentity_URI=" + presentity_URI + ", atom_ID=" + atom_ID + ", address_URI=" + address_URI + ", status_status=" + status_status + ", msnstatus_substatus=" + msnstatus_substatus + '}';
    }
    /* getPresentitySipURItarget
     * get  sip:299@10.0.254.125:5060;method=SUBSCRIBE ,
    returns sip:299@10.0.254.125:5060
    
    * */
    public String getPresentitySipURItarget(){
       String uri = this.getPresentity_URI();
       String[] chaine = uri.split(";");
       return chaine[0];   
    }
    
     /*method_seperator should be: "method="
      * returns the method : Subscribe, register, etc
      * */
    public String getPresentityMethod(String method_seperator){
       String uri = this.getPresentity_URI();
       String[] chaine = uri.split(method_seperator);
       return chaine[1];   
    }
    /**
     * sip:299@10.0.254.125:5060;method=SUBSCRIBE
     * @return sipId
     */
    public String getPresentitySipSIPID(){
        String uri = this.getPresentity_URI();
          String[] chaine = uri.split(":");//sip  299@10.0.254.125  5060
          String[] sipIdandIp = chaine[1].split("@");//299 10.0.254.125
          String sipId = sipIdandIp[0];
          return sipId;
    }
    
}
