
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>dataEXConfirmRequest complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡdataSn���Ե�ֵ��
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
     * ����dataSn���Ե�ֵ��
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
