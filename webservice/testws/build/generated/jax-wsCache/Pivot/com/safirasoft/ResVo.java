
package com.safirasoft;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for resVo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cdc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cnme" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dojtav" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dojtpeak" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dolatav" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dolatpeak" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dopkloss" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="puip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tlth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="upjtav" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="upjtpeak" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="uplatav" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="uplatpeak" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="uppkloss" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="eDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="sDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resVo", propOrder = {
    "cdc",
    "cnme",
    "dojtav",
    "dojtpeak",
    "dolatav",
    "dolatpeak",
    "dopkloss",
    "puip",
    "tlth",
    "upjtav",
    "upjtpeak",
    "uplatav",
    "uplatpeak",
    "uppkloss",
    "eDate",
    "sDate"
})
public class ResVo {

    protected String cdc;
    protected String cnme;
    protected int dojtav;
    protected int dojtpeak;
    protected int dolatav;
    protected int dolatpeak;
    protected float dopkloss;
    protected String puip;
    protected int tlth;
    protected int upjtav;
    protected int upjtpeak;
    protected int uplatav;
    protected int uplatpeak;
    protected float uppkloss;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar eDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sDate;

    /**
     * Gets the value of the cdc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCdc() {
        return cdc;
    }

    /**
     * Sets the value of the cdc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCdc(String value) {
        this.cdc = value;
    }

    /**
     * Gets the value of the cnme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCnme() {
        return cnme;
    }

    /**
     * Sets the value of the cnme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCnme(String value) {
        this.cnme = value;
    }

    /**
     * Gets the value of the dojtav property.
     * 
     */
    public int getDojtav() {
        return dojtav;
    }

    /**
     * Sets the value of the dojtav property.
     * 
     */
    public void setDojtav(int value) {
        this.dojtav = value;
    }

    /**
     * Gets the value of the dojtpeak property.
     * 
     */
    public int getDojtpeak() {
        return dojtpeak;
    }

    /**
     * Sets the value of the dojtpeak property.
     * 
     */
    public void setDojtpeak(int value) {
        this.dojtpeak = value;
    }

    /**
     * Gets the value of the dolatav property.
     * 
     */
    public int getDolatav() {
        return dolatav;
    }

    /**
     * Sets the value of the dolatav property.
     * 
     */
    public void setDolatav(int value) {
        this.dolatav = value;
    }

    /**
     * Gets the value of the dolatpeak property.
     * 
     */
    public int getDolatpeak() {
        return dolatpeak;
    }

    /**
     * Sets the value of the dolatpeak property.
     * 
     */
    public void setDolatpeak(int value) {
        this.dolatpeak = value;
    }

    /**
     * Gets the value of the dopkloss property.
     * 
     */
    public float getDopkloss() {
        return dopkloss;
    }

    /**
     * Sets the value of the dopkloss property.
     * 
     */
    public void setDopkloss(float value) {
        this.dopkloss = value;
    }

    /**
     * Gets the value of the puip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPuip() {
        return puip;
    }

    /**
     * Sets the value of the puip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPuip(String value) {
        this.puip = value;
    }

    /**
     * Gets the value of the tlth property.
     * 
     */
    public int getTlth() {
        return tlth;
    }

    /**
     * Sets the value of the tlth property.
     * 
     */
    public void setTlth(int value) {
        this.tlth = value;
    }

    /**
     * Gets the value of the upjtav property.
     * 
     */
    public int getUpjtav() {
        return upjtav;
    }

    /**
     * Sets the value of the upjtav property.
     * 
     */
    public void setUpjtav(int value) {
        this.upjtav = value;
    }

    /**
     * Gets the value of the upjtpeak property.
     * 
     */
    public int getUpjtpeak() {
        return upjtpeak;
    }

    /**
     * Sets the value of the upjtpeak property.
     * 
     */
    public void setUpjtpeak(int value) {
        this.upjtpeak = value;
    }

    /**
     * Gets the value of the uplatav property.
     * 
     */
    public int getUplatav() {
        return uplatav;
    }

    /**
     * Sets the value of the uplatav property.
     * 
     */
    public void setUplatav(int value) {
        this.uplatav = value;
    }

    /**
     * Gets the value of the uplatpeak property.
     * 
     */
    public int getUplatpeak() {
        return uplatpeak;
    }

    /**
     * Sets the value of the uplatpeak property.
     * 
     */
    public void setUplatpeak(int value) {
        this.uplatpeak = value;
    }

    /**
     * Gets the value of the uppkloss property.
     * 
     */
    public float getUppkloss() {
        return uppkloss;
    }

    /**
     * Sets the value of the uppkloss property.
     * 
     */
    public void setUppkloss(float value) {
        this.uppkloss = value;
    }

    /**
     * Gets the value of the eDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEDate() {
        return eDate;
    }

    /**
     * Sets the value of the eDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEDate(XMLGregorianCalendar value) {
        this.eDate = value;
    }

    /**
     * Gets the value of the sDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSDate() {
        return sDate;
    }

    /**
     * Sets the value of the sDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSDate(XMLGregorianCalendar value) {
        this.sDate = value;
    }

}
