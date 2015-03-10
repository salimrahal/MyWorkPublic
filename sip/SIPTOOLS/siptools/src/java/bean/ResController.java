/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.SipToolsBO;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author salim
 */
@ManagedBean
@SessionScoped
public class ResController implements Serializable {

    String customername;
    Date dateCurr;
    String ipPublic;
    public String URL_ALG = "http://siptools.nexogy.com/alg/index.html";
    public String URL_TRF_GEN = "http://siptools.nexogy.com/trfgen/index.html";
    public String admin = "admin";

    public ResController() {
        dateCurr = new Date();
    }

    @PostConstruct
    public void init() {
    }

    public void handleAlg() throws IOException {
        System.out.println("handleAlg():..");
        //FacesMessage msg = new FacesMessage("handleAlg");
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        if (customername != null && !customername.isEmpty()) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(URL_ALG + "?cust=" + customername);
        } else {
            System.out.println("handleAlg:custname is empty!");
        }
    }

    public void handleTrf() throws IOException {
        System.out.println("handleTrf():..");
        //FacesMessage msg = new FacesMessage("handleTrf");
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        if (customername != null && !customername.isEmpty()) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(URL_TRF_GEN + "?cust=" + customername);
        } else {
            System.out.println("handleTrf:custname is empty!");
        }
    }

    public void handleAlg(String cust) throws IOException {
        customername = cust;
        System.out.println("handleAlg():..");
        //FacesMessage msg = new FacesMessage("handleAlg");
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        if (customername != null && !customername.isEmpty()) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(URL_ALG + "?cust=" + customername);
        } else {
            System.out.println("handleAlg:custname is empty!");
        }
    }

    public void handleTrf(String cust) throws IOException {
        customername = cust;
        System.out.println("handleTrf():..");
        //FacesMessage msg = new FacesMessage("handleTrf");
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        if (customername != null && !customername.isEmpty()) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(URL_TRF_GEN + "?cust=" + customername);
        } else {
            System.out.println("handleTrf:custname is empty!");
        }
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getIpPublic() {
        return ipPublic;
    }

    public void setIpPublic(String ipPublic) {
        this.ipPublic = ipPublic;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public Date getDateCurr() {
        return dateCurr;
    }

    public void setDateCurr(Date dateCurr) {
        this.dateCurr = dateCurr;
    }

    public String getURL_ALG() {
        return URL_ALG;
    }

    public void setURL_ALG(String URL_ALG) {
        this.URL_ALG = URL_ALG;
    }

    public String getURL_TRF_GEN() {
        return URL_TRF_GEN;
    }

    public void setURL_TRF_GEN(String URL_TRF_GEN) {
        this.URL_TRF_GEN = URL_TRF_GEN;
    }
    

}
