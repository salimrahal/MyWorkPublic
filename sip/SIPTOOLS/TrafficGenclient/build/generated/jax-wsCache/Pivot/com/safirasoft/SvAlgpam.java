
package com.safirasoft;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for svAlgpam complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="svAlgpam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="prdes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "svAlgpam", propOrder = {
    "tid",
    "cus",
    "pip",
    "trs",
    "prs",
    "prdes"
})
public class SvAlgpam {

    protected String tid;
    protected String cus;
    protected String pip;
    protected String trs;
    protected int prs;
    protected int prdes;

    /**
     * Gets the value of the tid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTid() {
        return tid;
    }

    /**
     * Sets the value of the tid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTid(String value) {
        this.tid = value;
    }

    /**
     * Gets the value of the cus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCus() {
        return cus;
    }

    /**
     * Sets the value of the cus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCus(String value) {
        this.cus = value;
    }

    /**
     * Gets the value of the pip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPip() {
        return pip;
    }

    /**
     * Sets the value of the pip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPip(String value) {
        this.pip = value;
    }

    /**
     * Gets the value of the trs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrs() {
        return trs;
    }

    /**
     * Sets the value of the trs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrs(String value) {
        this.trs = value;
    }

    /**
     * Gets the value of the prs property.
     * 
     */
    public int getPrs() {
        return prs;
    }

    /**
     * Sets the value of the prs property.
     * 
     */
    public void setPrs(int value) {
        this.prs = value;
    }

    /**
     * Gets the value of the prdes property.
     * 
     */
    public int getPrdes() {
        return prdes;
    }

    /**
     * Sets the value of the prdes property.
     * 
     */
    public void setPrdes(int value) {
        this.prdes = value;
    }

}
