/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author salim
 */
@Entity
@Table(name = "Example")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Example.findAll", query = "SELECT e FROM Example e"),
    @NamedQuery(name = "Example.findByIdExample", query = "SELECT e FROM Example e WHERE e.idExample = :idExample"),
    @NamedQuery(name = "Example.findByA", query = "SELECT e FROM Example e WHERE e.a = :a"),
    @NamedQuery(name = "Example.findByB", query = "SELECT e FROM Example e WHERE e.b = :b")})
public class Example implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idExample")
    private Integer idExample;
    @Column(name = "a")
    private Integer a;
    @Column(name = "b")
    private Integer b;

    public Example() {
    }

    public Example(Integer idExample) {
        this.idExample = idExample;
    }

    public Integer getIdExample() {
        return idExample;
    }

    public void setIdExample(Integer idExample) {
        this.idExample = idExample;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExample != null ? idExample.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Example)) {
            return false;
        }
        Example other = (Example) object;
        if ((this.idExample == null && other.idExample != null) || (this.idExample != null && !this.idExample.equals(other.idExample))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.Example[ idExample=" + idExample + " ]";
    }
    
}
