
package com.safirasoft;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for prtMiscVo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="prtMiscVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="prtLatNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prtSigNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prtTrfNumDown" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prtTrfNumUp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serverIp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prtMiscVo", propOrder = {
    "prtLatNum",
    "prtSigNum",
    "prtTrfNumDown",
    "prtTrfNumUp",
    "serverIp"
})
public class PrtMiscVo {

    protected String prtLatNum;
    protected String prtSigNum;
    protected String prtTrfNumDown;
    protected String prtTrfNumUp;
    protected String serverIp;

    /**
     * Gets the value of the prtLatNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrtLatNum() {
        return prtLatNum;
    }

    /**
     * Sets the value of the prtLatNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrtLatNum(String value) {
        this.prtLatNum = value;
    }

    /**
     * Gets the value of the prtSigNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrtSigNum() {
        return prtSigNum;
    }

    /**
     * Sets the value of the prtSigNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrtSigNum(String value) {
        this.prtSigNum = value;
    }

    /**
     * Gets the value of the prtTrfNumDown property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrtTrfNumDown() {
        return prtTrfNumDown;
    }

    /**
     * Sets the value of the prtTrfNumDown property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrtTrfNumDown(String value) {
        this.prtTrfNumDown = value;
    }

    /**
     * Gets the value of the prtTrfNumUp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrtTrfNumUp() {
        return prtTrfNumUp;
    }

    /**
     * Sets the value of the prtTrfNumUp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrtTrfNumUp(String value) {
        this.prtTrfNumUp = value;
    }

    /**
     * Gets the value of the serverIp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerIp() {
        return serverIp;
    }

    /**
     * Sets the value of the serverIp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerIp(String value) {
        this.serverIp = value;
    }

}
