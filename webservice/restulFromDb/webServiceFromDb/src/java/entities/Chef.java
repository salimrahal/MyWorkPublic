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
@Table(name = "Chef")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chef.findAll", query = "SELECT c FROM Chef c"),
    @NamedQuery(name = "Chef.findByNochef", query = "SELECT c FROM Chef c WHERE c.nochef = :nochef"),
    @NamedQuery(name = "Chef.findByNomch", query = "SELECT c FROM Chef c WHERE c.nomch = :nomch"),
    @NamedQuery(name = "Chef.findByPrenomch", query = "SELECT c FROM Chef c WHERE c.prenomch = :prenomch")})
public class Chef implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Nochef")
    private Short nochef;
    @Size(max = 25)
    @Column(name = "Nomch")
    private String nomch;
    @Size(max = 20)
    @Column(name = "Prenomch")
    private String prenomch;

    public Chef() {
    }

    public Chef(Short nochef) {
        this.nochef = nochef;
    }

    public Short getNochef() {
        return nochef;
    }

    public void setNochef(Short nochef) {
        this.nochef = nochef;
    }

    public String getNomch() {
        return nomch;
    }

    public void setNomch(String nomch) {
        this.nomch = nomch;
    }

    public String getPrenomch() {
        return prenomch;
    }

    public void setPrenomch(String prenomch) {
        this.prenomch = prenomch;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nochef != null ? nochef.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chef)) {
            return false;
        }
        Chef other = (Chef) object;
        if ((this.nochef == null && other.nochef != null) || (this.nochef != null && !this.nochef.equals(other.nochef))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Chef[ nochef=" + nochef + " ]";
    }
    
}
