
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>dataEXPageResult complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="dataEXPageResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataEX_Error" type="{http://dataEX_share}dataEXError" minOccurs="0"/>
 *         &lt;element name="page" type="{http://dataEX_share}dataEXPage" minOccurs="0"/>
 *         &lt;element name="pageResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataEXPageResult", propOrder = {
    "dataEXError",
    "page",
    "pageResult"
})
public class DataEXPageResult {

    @XmlElement(name = "dataEX_Error")
    protected DataEXError dataEXError;
    protected DataEXPage page;
    protected String pageResult;

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

    /**
     * ��ȡpage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DataEXPage }
     *     
     */
    public DataEXPage getPage() {
        return page;
    }

    /**
     * ����page���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DataEXPage }
     *     
     */
    public void setPage(DataEXPage value) {
        this.page = value;
    }

    /**
     * ��ȡpageResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPageResult() {
        return pageResult;
    }

    /**
     * ����pageResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageResult(String value) {
        this.pageResult = value;
    }

}
