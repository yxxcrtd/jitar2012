
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>queryDataNO complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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

}
