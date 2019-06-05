
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>queryData complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡqueryRequest���Ե�ֵ��
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
     * ����queryRequest���Ե�ֵ��
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
     * ��ȡarg1���Ե�ֵ��
     * 
     */
    public int getArg1() {
        return arg1;
    }

    /**
     * ����arg1���Ե�ֵ��
     * 
     */
    public void setArg1(int value) {
        this.arg1 = value;
    }

}
