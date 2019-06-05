
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>dataEXQueryRequest complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="dataEXQueryRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataEX_Token" type="{http://dataEX_share}dataEXToken" minOccurs="0"/>
 *         &lt;element name="dataEX_QueryObject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataEX_QueryCondition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataEXQueryRequest", propOrder = {
    "dataEXToken",
    "dataEXQueryObject",
    "dataEXQueryCondition"
})
public class DataEXQueryRequest {

    @XmlElement(name = "dataEX_Token")
    protected DataEXToken dataEXToken;
    @XmlElement(name = "dataEX_QueryObject")
    protected String dataEXQueryObject;
    @XmlElement(name = "dataEX_QueryCondition")
    protected String dataEXQueryCondition;

    /**
     * ��ȡdataEXToken���Ե�ֵ��
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
     * ����dataEXToken���Ե�ֵ��
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
     * ��ȡdataEXQueryObject���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataEXQueryObject() {
        return dataEXQueryObject;
    }

    /**
     * ����dataEXQueryObject���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataEXQueryObject(String value) {
        this.dataEXQueryObject = value;
    }

    /**
     * ��ȡdataEXQueryCondition���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataEXQueryCondition() {
        return dataEXQueryCondition;
    }

    /**
     * ����dataEXQueryCondition���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataEXQueryCondition(String value) {
        this.dataEXQueryCondition = value;
    }

}
