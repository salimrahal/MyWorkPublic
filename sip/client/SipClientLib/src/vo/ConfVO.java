/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import java.util.List;


/**
 *
 * @author salim
 * Singleton config file
 */
public class ConfVO {
    //Params
    private   int UASPort = -1;
    private   String UASIp = "" ;
    private String protocol  = "";
    //from Conf
    private   String UACIp  = "" ;
    private   int UACPort  = -1;
   //used for register, and in FROM field
    private   String extSipLocal = "";
    private   String username = "" ;
    private   String password = "";
    private String Domain = ""; //ie: realm/domain
    private   String uuid = "";// the UUID 
    private String extsToMonitor;//122,125,128
    private String hostNameLocal;
    
    private static final ConfVO INSTANCE = new ConfVO();

    private ConfVO() {
    }
   
    public static ConfVO getInstance() {
        return INSTANCE;
    }

    public String getHostNameLocal() {
        return hostNameLocal;
    }

    public void setHostNameLocal(String hostNameLocal) {
        this.hostNameLocal = hostNameLocal;
    }

    public String getExtsToMonitor() {
        return extsToMonitor;
    }

    public void setExtsToMonitor(String extsToMonitor) {
        this.extsToMonitor = extsToMonitor;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    
    public String getDomain() {
        return Domain;
    }

    public void setDomain(String Domain) {
        this.Domain = Domain;
    }  
    
    public int getUASPort() {
        return UASPort;
    }

    public void setUASPort(int UASPort) {
        this.UASPort = UASPort;
    }

    public String getUASIp() {
        return UASIp;
    }

    public void setUASIp(String UASIp) {
        this.UASIp = UASIp;
    }

    public String getUACIp() {
        return UACIp;
    }

    public void setUACIp(String UACIp) {
        this.UACIp = UACIp;
    }

    public int getUACPort() {
        return UACPort;
    }

    public void setUACPort(int UACPort) {
        this.UACPort = UACPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
 

    public String getExtSipLocal() {
        return extSipLocal;
    }

    public void setExtSipLocal(String extSipLocal) {
        this.extSipLocal = extSipLocal;
    }

    @Override
    public String toString() {
        return "ConfVO{" + "UASPort=" + UASPort + ", UASIp=" + UASIp + ", protocol=" + protocol + ", UACIp=" + UACIp + ", UACPort=" + UACPort + ", extSipLocal=" + extSipLocal + ", username=" + username + ", password=" + password + ", Domain=" + Domain + ", instanceUUID=" + uuid + ", extsToMonitor=" + extsToMonitor + ", hostNameLocal=" + hostNameLocal + '}';
    } 
    
}
