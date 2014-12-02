
package com.safirasoft;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for codecVoList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="codecVoList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codecList" type="{http://safirasoft.com/}codecVo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "codecVoList", propOrder = {
    "codecList"
})
public class CodecVoList {

    @XmlElement(nillable = true)
    protected List<CodecVo> codecList;

    /**
     * Gets the value of the codecList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codecList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodecList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CodecVo }
     * 
     * 
     */
    public List<CodecVo> getCodecList() {
        if (codecList == null) {
            codecList = new ArrayList<CodecVo>();
        }
        return this.codecList;
    }

}
