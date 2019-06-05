
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>dataEXConfirmRequest complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="dataEXConfirmRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataEX_Token" type="{http://dataEX_share}dataEXToken" minOccurs="0"/>
 *         &lt;element name="data_sn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataEXConfirmRequest", propOrder = {
    "dataEXToken",
    "dataSn"
})
public class DataEXConfirmRequest {

    @XmlElement(name = "dataEX_Token")
    protected DataEXToken dataEXToken;
    @XmlElement(name = "data_sn")
    protected String dataSn;

    /**
     * 获取dataEXToken属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DataEXToken }
     *     
     */
    public DataEXToken getDataEXToken() {
        return dataEXToken;
    }

    /**
     * 设置dataEXToken属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DataEXToken }
     *     
     */
    public void setDataEXToken(DataEXToken value) {
        this.dataEXToken = value;
    }

    /**
     * 获取dataSn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSn() {
        return dataSn;
    }

    /**
     * 设置dataSn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSn(String value) {
        this.dataSn = value;
    }

}
