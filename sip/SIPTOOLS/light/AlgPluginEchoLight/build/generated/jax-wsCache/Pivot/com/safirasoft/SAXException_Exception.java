
package com.safirasoft;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "SAXException", targetNamespace = "http://safirasoft.com/")
public class SAXException_Exception
    extends java.lang.Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private SAXException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public SAXException_Exception(String message, SAXException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public SAXException_Exception(String message, SAXException faultInfo, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.safirasoft.SAXException
     */
    public SAXException getFaultInfo() {
        return faultInfo;
    }

}
