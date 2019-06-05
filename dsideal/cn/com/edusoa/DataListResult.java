
package cn.com.edusoa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DataListResult complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡresultTitle1���Ե�ֵ��
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
     * ����resultTitle1���Ե�ֵ��
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
     * ��ȡresultTitle2���Ե�ֵ��
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
     * ����resultTitle2���Ե�ֵ��
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
     * ��ȡresultTitle3���Ե�ֵ��
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
     * ����resultTitle3���Ե�ֵ��
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
     * ��ȡcolumnTitle1���Ե�ֵ��
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
     * ����columnTitle1���Ե�ֵ��
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
     * ��ȡcolumnTitle2���Ե�ֵ��
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
     * ����columnTitle2���Ե�ֵ��
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
     * ��ȡcolumnTitle3���Ե�ֵ��
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
     * ����columnTitle3���Ե�ֵ��
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
     * ��ȡcolumnUrl1���Ե�ֵ��
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
     * ����columnUrl1���Ե�ֵ��
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
     * ��ȡcolumnUrl2���Ե�ֵ��
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
     * ����columnUrl2���Ե�ֵ��
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
     * ��ȡcolumnUrl3���Ե�ֵ��
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
     * ����columnUrl3���Ե�ֵ��
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
     * ��ȡresultUrl���Ե�ֵ��
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
     * ����resultUrl���Ե�ֵ��
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
