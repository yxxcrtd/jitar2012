
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>dataEXPage complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="dataEXPage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pageCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pageRecord" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="recordCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataEXPage", propOrder = {
    "pageCount",
    "pageRecord",
    "recordCount"
})
public class DataEXPage {

    protected int pageCount;
    protected int pageRecord;
    protected int recordCount;

    /**
     * ��ȡpageCount���Ե�ֵ��
     * 
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * ����pageCount���Ե�ֵ��
     * 
     */
    public void setPageCount(int value) {
        this.pageCount = value;
    }

    /**
     * ��ȡpageRecord���Ե�ֵ��
     * 
     */
    public int getPageRecord() {
        return pageRecord;
    }

    /**
     * ����pageRecord���Ե�ֵ��
     * 
     */
    public void setPageRecord(int value) {
        this.pageRecord = value;
    }

    /**
     * ��ȡrecordCount���Ե�ֵ��
     * 
     */
    public int getRecordCount() {
        return recordCount;
    }

    /**
     * ����recordCount���Ե�ֵ��
     * 
     */
    public void setRecordCount(int value) {
        this.recordCount = value;
    }

}
