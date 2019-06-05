
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>queryDataNO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="queryDataNO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="queryRequest" type="{http://dataEX_share}dataEXQueryRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryDataNO", propOrder = {
    "queryRequest"
})
public class QueryDataNO {

    protected DataEXQueryRequest queryRequest;

    /**
     * 获取queryRequest属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DataEXQueryRequest }
     *     
     */
    public DataEXQueryRequest getQueryRequest() {
        return queryRequest;
    }

    /**
     * 设置queryRequest属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DataEXQueryRequest }
     *     
     */
    public void setQueryRequest(DataEXQueryRequest value) {
        this.queryRequest = value;
    }

}
