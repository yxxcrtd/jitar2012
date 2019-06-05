
package cn.com.edusoa;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.com.edusoa package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.com.edusoa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDataList }
     * 
     */
    public GetDataList createGetDataList() {
        return new GetDataList();
    }

    /**
     * Create an instance of {@link GetDataListResponse }
     * 
     */
    public GetDataListResponse createGetDataListResponse() {
        return new GetDataListResponse();
    }

    /**
     * Create an instance of {@link DataListResult }
     * 
     */
    public DataListResult createDataListResult() {
        return new DataListResult();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

}
