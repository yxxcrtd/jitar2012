
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>dataEXError complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡdataEXCategeory���Ե�ֵ��
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
     * ����dataEXCategeory���Ե�ֵ��
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
     * ��ȡdataEXCode���Ե�ֵ��
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
     * ����dataEXCode���Ե�ֵ��
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
     * ��ȡdataEXDesc���Ե�ֵ��
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
     * ����dataEXDesc���Ե�ֵ��
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
