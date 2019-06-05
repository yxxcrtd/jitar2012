
package cn.com.edusoa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DataListResult complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DataListResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultTitle1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultTitle2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultTitle3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="columnTitle1" type="{http://edusoa.com.cn/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="columnTitle2" type="{http://edusoa.com.cn/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="columnTitle3" type="{http://edusoa.com.cn/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="columnUrl1" type="{http://edusoa.com.cn/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="columnUrl2" type="{http://edusoa.com.cn/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="columnUrl3" type="{http://edusoa.com.cn/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="resultUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataListResult", propOrder = {
    "resultTitle1",
    "resultTitle2",
    "resultTitle3",
    "columnTitle1",
    "columnTitle2",
    "columnTitle3",
    "columnUrl1",
    "columnUrl2",
    "columnUrl3",
    "resultUrl"
})
public class DataListResult {

    protected String resultTitle1;
    protected String resultTitle2;
    protected String resultTitle3;
    protected ArrayOfString columnTitle1;
    protected ArrayOfString columnTitle2;
    protected ArrayOfString columnTitle3;
    protected ArrayOfString columnUrl1;
    protected ArrayOfString columnUrl2;
    protected ArrayOfString columnUrl3;
    protected String resultUrl;

    /**
     * 获取resultTitle1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultTitle1() {
        return resultTitle1;
    }

    /**
     * 设置resultTitle1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultTitle1(String value) {
        this.resultTitle1 = value;
    }

    /**
     * 获取resultTitle2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultTitle2() {
        return resultTitle2;
    }

    /**
     * 设置resultTitle2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultTitle2(String value) {
        this.resultTitle2 = value;
    }

    /**
     * 获取resultTitle3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultTitle3() {
        return resultTitle3;
    }

    /**
     * 设置resultTitle3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultTitle3(String value) {
        this.resultTitle3 = value;
    }

    /**
     * 获取columnTitle1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getColumnTitle1() {
        return columnTitle1;
    }

    /**
     * 设置columnTitle1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setColumnTitle1(ArrayOfString value) {
        this.columnTitle1 = value;
    }

    /**
     * 获取columnTitle2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getColumnTitle2() {
        return columnTitle2;
    }

    /**
     * 设置columnTitle2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setColumnTitle2(ArrayOfString value) {
        this.columnTitle2 = value;
    }

    /**
     * 获取columnTitle3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getColumnTitle3() {
        return columnTitle3;
    }

    /**
     * 设置columnTitle3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setColumnTitle3(ArrayOfString value) {
        this.columnTitle3 = value;
    }

    /**
     * 获取columnUrl1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getColumnUrl1() {
        return columnUrl1;
    }

    /**
     * 设置columnUrl1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setColumnUrl1(ArrayOfString value) {
        this.columnUrl1 = value;
    }

    /**
     * 获取columnUrl2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getColumnUrl2() {
        return columnUrl2;
    }

    /**
     * 设置columnUrl2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setColumnUrl2(ArrayOfString value) {
        this.columnUrl2 = value;
    }

    /**
     * 获取columnUrl3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getColumnUrl3() {
        return columnUrl3;
    }

    /**
     * 设置columnUrl3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setColumnUrl3(ArrayOfString value) {
        this.columnUrl3 = value;
    }

    /**
     * 获取resultUrl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultUrl() {
        return resultUrl;
    }

    /**
     * 设置resultUrl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultUrl(String value) {
        this.resultUrl = value;
    }

}
