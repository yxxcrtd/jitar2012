
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>queryData complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="queryData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="queryRequest" type="{http://dataEX_share}dataEXQueryRequest" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryData", propOrder = {
    "queryRequest",
    "arg1"
})
public class QueryData {

    protected DataEXQueryRequest queryRequest;
    protected int arg1;

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

    /**
     * 获取arg1属性的值。
     * 
     */
    public int getArg1() {
        return arg1;
    }

    /**
     * 设置arg1属性的值。
     * 
     */
    public void setArg1(int value) {
        this.arg1 = value;
    }

}
