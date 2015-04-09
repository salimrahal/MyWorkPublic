/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author salim
 */
@Entity
@Table(name = "Fiche_DescriptiveMin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FicheDescriptiveMin.findAll", query = "SELECT f FROM FicheDescriptiveMin f"),
    @NamedQuery(name = "FicheDescriptiveMin.findByIdFicheDescriptive", query = "SELECT f FROM FicheDescriptiveMin f WHERE f.idFicheDescriptive = :idFicheDescriptive"),
    @NamedQuery(name = "FicheDescriptiveMin.findByIdSpec", query = "SELECT f FROM FicheDescriptiveMin f WHERE f.idSpec = :idSpec"),
    @NamedQuery(name = "FicheDescriptiveMin.findByNoEtud", query = "SELECT f FROM FicheDescriptiveMin f WHERE f.noEtud = :noEtud"),
    @NamedQuery(name = "FicheDescriptiveMin.findByTypeProjet", query = "SELECT f FROM FicheDescriptiveMin f WHERE f.typeProjet = :typeProjet")})
public class FicheDescriptiveMin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFiche_Descriptive")
    private Integer idFicheDescriptive;
    @Column(name = "IdSpec")
    private Short idSpec;
    @Column(name = "NoEtud")
    private Integer noEtud;
    @Size(max = 30)
    @Column(name = "Type_Projet")
    private String typeProjet;

    public FicheDescriptiveMin() {
    }

    public FicheDescriptiveMin(Integer idFicheDescriptive) {
        this.idFicheDescriptive = idFicheDescriptive;
    }

    public Integer getIdFicheDescriptive() {
        return idFicheDescriptive;
    }

    public void setIdFicheDescriptive(Integer idFicheDescriptive) {
        this.idFicheDescriptive = idFicheDescriptive;
    }

    public Short getIdSpec() {
        return idSpec;
    }

    public void setIdSpec(Short idSpec) {
        this.idSpec = idSpec;
    }

    public Integer getNoEtud() {
        return noEtud;
    }

    public void setNoEtud(Integer noEtud) {
        this.noEtud = noEtud;
    }

    public String getTypeProjet() {
        return typeProjet;
    }

    public void setTypeProjet(String typeProjet) {
        this.typeProjet = typeProjet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFicheDescriptive != null ? idFicheDescriptive.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FicheDescriptiveMin)) {
            return false;
        }
        FicheDescriptiveMin other = (FicheDescriptiveMin) object;
        if ((this.idFicheDescriptive == null && other.idFicheDescriptive != null) || (this.idFicheDescriptive != null && !this.idFicheDescriptive.equals(other.idFicheDescriptive))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FicheDescriptiveMin[ idFicheDescriptive=" + idFicheDescriptive + " ]";
    }
    
}
