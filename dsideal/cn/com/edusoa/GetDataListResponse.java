
package cn.com.edusoa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取getDataListResult属性的值。
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
     * 设置getDataListResult属性的值。
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
