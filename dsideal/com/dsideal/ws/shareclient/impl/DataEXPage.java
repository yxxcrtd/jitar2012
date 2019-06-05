
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>dataEXPage complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取pageCount属性的值。
     * 
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * 设置pageCount属性的值。
     * 
     */
    public void setPageCount(int value) {
        this.pageCount = value;
    }

    /**
     * 获取pageRecord属性的值。
     * 
     */
    public int getPageRecord() {
        return pageRecord;
    }

    /**
     * 设置pageRecord属性的值。
     * 
     */
    public void setPageRecord(int value) {
        this.pageRecord = value;
    }

    /**
     * 获取recordCount属性的值。
     * 
     */
    public int getRecordCount() {
        return recordCount;
    }

    /**
     * 设置recordCount属性的值。
     * 
     */
    public void setRecordCount(int value) {
        this.recordCount = value;
    }

}
