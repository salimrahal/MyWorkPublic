/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bo.sipserverBO;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author salim
 */
@ManagedBean
@SessionScoped
public class ResController implements Serializable {

    //managed bean injected
    @ManagedProperty("#{resService}")
    private ResService serviceRes;
    List<ResVo> results;
    List<ResVo> filteredresults;
    String customername;
    Date dateCurr;
    String ipPublic;
    public String URL_ALG = "http://siptools.nexogy.com/alg/index.html";
    public String URL_TRF_GEN = "http://siptools.nexogy.com/trfgen";

    public ResController() {
        dateCurr = new Date();
    }

    @PostConstruct
    public void init() {
        try {
            results = serviceRes.retrieveResults();
        } catch (Exception ex) {
            Logger.getLogger(ResController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ipPublic = bo.Networking.getmyPIP();
        } catch (IOException ex) {
            Logger.getLogger(ResController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleAlg() throws IOException {
        System.out.println("handleAlg():..");
        FacesMessage msg = new FacesMessage("handleAlg");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        if (customername != null && !customername.isEmpty()) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(URL_ALG + "?cust=" + customername);
        } else {
            System.out.println("handleAlg:custname is empty!");
        }
    }

//    public String handleAlg() {
//        try {
//            FacesMessage msg = new FacesMessage("handleAlg");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//            externalContext.redirect(URL_ALG);
//
//        } catch (IOException ex) {
//            Logger.getLogger(ResController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "algforward.jsp";
//    }
    public String handleTrfSim() {
        FacesMessage msg = new FacesMessage("handleTrfSim");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return URL_TRF_GEN;

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

    public ResService getServiceRes() {
        return serviceRes;
    }

    public void setServiceRes(ResService serviceRes) {
        this.serviceRes = serviceRes;
    }

    public List<ResVo> getResults() {
        return results;
    }

    public void setResults(List<ResVo> results) {
        this.results = results;
    }

    public String formatDate(Date date) {
        String formattedDate = null;
        if (date != null) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");//yyyy-MM-dd HH:mm:ss
            formattedDate = sf.format(date);
        }
        return formattedDate;
    }

    public List<ResVo> getFilteredresults() {
        return filteredresults;
    }

    public void setFilteredresults(List<ResVo> filteredresults) {
        this.filteredresults = filteredresults;
    }

    public Date getDateCurr() {
        return dateCurr;
    }

    public void setDateCurr(Date dateCurr) {
        this.dateCurr = dateCurr;
    }

}
