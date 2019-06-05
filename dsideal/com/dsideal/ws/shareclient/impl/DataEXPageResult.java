
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>dataEXPageResult complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取dataEXError属性的值。
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
     * 设置dataEXError属性的值。
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
     * 获取page属性的值。
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
     * 设置page属性的值。
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
     * 获取pageResult属性的值。
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
     * 设置pageResult属性的值。
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
