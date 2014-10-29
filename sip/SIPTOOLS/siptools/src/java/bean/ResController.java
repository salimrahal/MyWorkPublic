/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

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
import javax.faces.context.FacesContext;

/**
 *
 * @author salim
 */
@ManagedBean
@SessionScoped
public class ResController  implements Serializable {
     //managed bean injected
    @ManagedProperty("#{resService}")
    private ResService serviceRes;
    List<ResVo> results;
    List<ResVo> filteredresults;
    String algdetector = "algdetect/index.html";
    String trafficsimulator = "trafficsimulator/index.html";
    String customername;
    Date dateCurr;
    
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
    }

    public String handleAlg(){
         FacesMessage msg = new FacesMessage("handleAlg");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return algdetector;
        
    }
    
    public String handleTrfSim(){
          FacesMessage msg = new FacesMessage("handleTrfSim");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return trafficsimulator;
        
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
