
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>dataEXError complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="dataEXError">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataEX_Categeory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataEX_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataEX_Desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataEXError", propOrder = {
    "dataEXCategeory",
    "dataEXCode",
    "dataEXDesc"
})
public class DataEXError {

    @XmlElement(name = "dataEX_Categeory")
    protected String dataEXCategeory;
    @XmlElement(name = "dataEX_Code")
    protected String dataEXCode;
    @XmlElement(name = "dataEX_Desc")
    protected String dataEXDesc;

    /**
     * 获取dataEXCategeory属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataEXCategeory() {
        return dataEXCategeory;
    }

    /**
     * 设置dataEXCategeory属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataEXCategeory(String value) {
        this.dataEXCategeory = value;
    }

    /**
     * 获取dataEXCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataEXCode() {
        return dataEXCode;
    }

    /**
     * 设置dataEXCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataEXCode(String value) {
        this.dataEXCode = value;
    }

    /**
     * 获取dataEXDesc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataEXDesc() {
        return dataEXDesc;
    }

    /**
     * 设置dataEXDesc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataEXDesc(String value) {
        this.dataEXDesc = value;
    }

}
