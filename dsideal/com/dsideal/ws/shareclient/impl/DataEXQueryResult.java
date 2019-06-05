
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>dataEXQueryResult complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="dataEXQueryResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataEX_Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataEX_ResultSet" type="{http://dataEX_share}dataEXResultSet" minOccurs="0"/>
 *         &lt;element name="dataEX_Error" type="{http://dataEX_share}dataEXError" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataEXQueryResult", propOrder = {
    "dataEXResult",
    "dataEXResultSet",
    "dataEXError"
})
public class DataEXQueryResult {

    @XmlElement(name = "dataEX_Result")
    protected String dataEXResult;
    @XmlElement(name = "dataEX_ResultSet")
    protected DataEXResultSet dataEXResultSet;
    @XmlElement(name = "dataEX_Error")
    protected DataEXError dataEXError;

    /**
     * ��ȡdataEXResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataEXResult() {
        return dataEXResult;
    }

    /**
     * ����dataEXResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataEXResult(String value) {
        this.dataEXResult = value;
    }

    /**
     * ��ȡdataEXResultSet���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DataEXResultSet }
     *     
     */
    public DataEXResultSet getDataEXResultSet() {
        return dataEXResultSet;
    }

    /**
     * ����dataEXResultSet���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DataEXResultSet }
     *     
     */
    public void setDataEXResultSet(DataEXResultSet value) {
        this.dataEXResultSet = value;
    }

    /**
     * ��ȡdataEXError���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DataEXError }
     *     
     */
    public DataEXError getDataEXError() {
        return dataEXError;
    }

    /**
     * ����dataEXError���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DataEXError }
     *     
     */
    public void setDataEXError(DataEXError value) {
        this.dataEXError = value;
    }

}
