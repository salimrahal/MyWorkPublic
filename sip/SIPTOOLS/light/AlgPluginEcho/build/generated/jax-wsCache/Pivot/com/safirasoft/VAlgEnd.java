
package com.safirasoft;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for vAlgEnd complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vAlgEnd">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isalgdetected" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isFirewalldetected" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vAlgEnd", propOrder = {
    "tid",
    "isalgdetected",
    "isFirewalldetected"
})
public class VAlgEnd {

    protected String tid;
    protected boolean isalgdetected;
    protected boolean isFirewalldetected;

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
     * Gets the value of the isalgdetected property.
     * 
     */
    public boolean isIsalgdetected() {
        return isalgdetected;
    }

    /**
     * Sets the value of the isalgdetected property.
     * 
     */
    public void setIsalgdetected(boolean value) {
        this.isalgdetected = value;
    }

    /**
     * Gets the value of the isFirewalldetected property.
     * 
     */
    public boolean isIsFirewalldetected() {
        return isFirewalldetected;
    }

    /**
     * Sets the value of the isFirewalldetected property.
     * 
     */
    public void setIsFirewalldetected(boolean value) {
        this.isFirewalldetected = value;
    }

}
