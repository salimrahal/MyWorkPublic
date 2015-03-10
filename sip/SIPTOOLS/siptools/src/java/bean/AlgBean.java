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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;

/**
 *
 * @author salim
 */
@ManagedBean
@ViewScoped
public class AlgBean implements Serializable {

    //managed bean injected
    @ManagedProperty("#{resService}")
    private ResService serviceRes;
    List<ResAlgVo> resultsAlg;
    List<ResAlgVo> filteredresults;

    List<ResTrfVo> resultsTrf;
    List<ResTrfVo> filteredresultsTrf;

    @PostConstruct
    public void init() {
        try {
            resultsAlg = serviceRes.retrieveAlgResults();
            resultsTrf = serviceRes.retrieveTrfResults();
        } catch (Exception ex) {
            Logger.getLogger(ResController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reloadDataAlg() throws Exception {
        resultsAlg = serviceRes.retrieveAlgResults();
    }

    public ResService getServiceRes() {
        return serviceRes;
    }

    public void setServiceRes(ResService serviceRes) {
        this.serviceRes = serviceRes;
    }

    public List<ResAlgVo> getResultsAlg() {
        return resultsAlg;
    }

    public void setResultsAlg(List<ResAlgVo> resultsAlg) {
        this.resultsAlg = resultsAlg;
    }

    public List<ResAlgVo> getFilteredresults() {
        return filteredresults;
    }

    public void setFilteredresults(List<ResAlgVo> filteredresults) {
        this.filteredresults = filteredresults;
    }

    public List<ResTrfVo> getResultsTrf() {
        return resultsTrf;
    }

    public void setResultsTrf(List<ResTrfVo> results) {
        this.resultsTrf = results;
    }

    public List<ResTrfVo> getFilteredresultsTrf() {
        return filteredresultsTrf;
    }

    public void setFilteredresultsTrf(List<ResTrfVo> filteredresultsTrf) {
        this.filteredresultsTrf = filteredresultsTrf;
    }
}
