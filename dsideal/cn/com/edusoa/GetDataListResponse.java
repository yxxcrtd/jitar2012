
package cn.com.edusoa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="getDataListResult" type="{http://edusoa.com.cn/}DataListResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getDataListResult"
})
@XmlRootElement(name = "getDataListResponse")
public class GetDataListResponse {

    protected DataListResult getDataListResult;

    /**
     * ��ȡgetDataListResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DataListResult }
     *     
     */
    public DataListResult getGetDataListResult() {
        return getDataListResult;
    }

    /**
     * ����getDataListResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DataListResult }
     *     
     */
    public void setGetDataListResult(DataListResult value) {
        this.getDataListResult = value;
    }

}
