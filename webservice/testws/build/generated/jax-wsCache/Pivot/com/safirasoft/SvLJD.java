
package com.safirasoft;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for svLJD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="svLJD">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ti" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="latdwnpk" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="latdwnav" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="jitdwpk" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="jitdwav" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "svLJD", propOrder = {
    "ti",
    "latdwnpk",
    "latdwnav",
    "jitdwpk",
    "jitdwav"
})
public class SvLJD {

    protected String ti;
    protected int latdwnpk;
    protected int latdwnav;
    protected int jitdwpk;
    protected int jitdwav;

    /**
     * Gets the value of the ti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTi() {
        return ti;
    }

    /**
     * Sets the value of the ti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTi(String value) {
        this.ti = value;
    }

    /**
     * Gets the value of the latdwnpk property.
     * 
     */
    public int getLatdwnpk() {
        return latdwnpk;
    }

    /**
     * Sets the value of the latdwnpk property.
     * 
     */
    public void setLatdwnpk(int value) {
        this.latdwnpk = value;
    }

    /**
     * Gets the value of the latdwnav property.
     * 
     */
    public int getLatdwnav() {
        return latdwnav;
    }

    /**
     * Sets the value of the latdwnav property.
     * 
     */
    public void setLatdwnav(int value) {
        this.latdwnav = value;
    }

    /**
     * Gets the value of the jitdwpk property.
     * 
     */
    public int getJitdwpk() {
        return jitdwpk;
    }

    /**
     * Sets the value of the jitdwpk property.
     * 
     */
    public void setJitdwpk(int value) {
        this.jitdwpk = value;
    }

    /**
     * Gets the value of the jitdwav property.
     * 
     */
    public int getJitdwav() {
        return jitdwav;
    }

    /**
     * Sets the value of the jitdwav property.
     * 
     */
    public void setJitdwav(int value) {
        this.jitdwav = value;
    }

}
