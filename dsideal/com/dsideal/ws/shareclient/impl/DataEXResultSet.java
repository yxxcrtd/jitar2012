
package com.dsideal.ws.shareclient.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>dataEXResultSet complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="dataEXResultSet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="primaryKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataStructure" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="dataResult" type="{http://dataEX_share}dataEXDataResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataEXResultSet", propOrder = {
    "primaryKey",
    "dataStructure",
    "dataResult"
})
public class DataEXResultSet {

    protected String primaryKey;
    @XmlElement(nillable = true)
    protected List<String> dataStructure;
    protected DataEXDataResult dataResult;

    /**
     * 获取primaryKey属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryKey() {
        return primaryKey;
    }

    /**
     * 设置primaryKey属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryKey(String value) {
        this.primaryKey = value;
    }

    /**
     * Gets the value of the dataStructure property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataStructure property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataStructure().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDataStructure() {
        if (dataStructure == null) {
            dataStructure = new ArrayList<String>();
        }
        return this.dataStructure;
    }

    /**
     * 获取dataResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DataEXDataResult }
     *     
     */
    public DataEXDataResult getDataResult() {
        return dataResult;
    }

    /**
     * 设置dataResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DataEXDataResult }
     *     
     */
    public void setDataResult(DataEXDataResult value) {
        this.dataResult = value;
    }

}
