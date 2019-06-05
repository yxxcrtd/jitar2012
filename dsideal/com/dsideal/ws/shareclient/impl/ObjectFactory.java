
package com.dsideal.ws.shareclient.impl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.dsideal.ws.shareclient.impl package. 
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

    private final static QName _QueryDataConfirm_QNAME = new QName("http://dataEX_share", "queryDataConfirm");
    private final static QName _QueryDataNOResponse_QNAME = new QName("http://dataEX_share", "queryDataNOResponse");
    private final static QName _QueryData_QNAME = new QName("http://dataEX_share", "queryData");
    private final static QName _QueryDataNO_QNAME = new QName("http://dataEX_share", "queryDataNO");
    private final static QName _Login_QNAME = new QName("http://dataEX_share", "login");
    private final static QName _QueryDataResponse_QNAME = new QName("http://dataEX_share", "queryDataResponse");
    private final static QName _LoginResponse_QNAME = new QName("http://dataEX_share", "loginResponse");
    private final static QName _QueryDataDiffResponse_QNAME = new QName("http://dataEX_share", "queryDataDiffResponse");
    private final static QName _QueryDataDiff_QNAME = new QName("http://dataEX_share", "queryDataDiff");
    private final static QName _QueryDataConfirmResponse_QNAME = new QName("http://dataEX_share", "queryDataConfirmResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.dsideal.ws.shareclient.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryDataDiff }
     * 
     */
    public QueryDataDiff createQueryDataDiff() {
        return new QueryDataDiff();
    }

    /**
     * Create an instance of {@link QueryDataConfirmResponse }
     * 
     */
    public QueryDataConfirmResponse createQueryDataConfirmResponse() {
        return new QueryDataConfirmResponse();
    }

    /**
     * Create an instance of {@link QueryDataDiffResponse }
     * 
     */
    public QueryDataDiffResponse createQueryDataDiffResponse() {
        return new QueryDataDiffResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link QueryData }
     * 
     */
    public QueryData createQueryData() {
        return new QueryData();
    }

    /**
     * Create an instance of {@link QueryDataResponse }
     * 
     */
    public QueryDataResponse createQueryDataResponse() {
        return new QueryDataResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link QueryDataNO }
     * 
     */
    public QueryDataNO createQueryDataNO() {
        return new QueryDataNO();
    }

    /**
     * Create an instance of {@link QueryDataConfirm }
     * 
     */
    public QueryDataConfirm createQueryDataConfirm() {
        return new QueryDataConfirm();
    }

    /**
     * Create an instance of {@link QueryDataNOResponse }
     * 
     */
    public QueryDataNOResponse createQueryDataNOResponse() {
        return new QueryDataNOResponse();
    }

    /**
     * Create an instance of {@link DataEXPage }
     * 
     */
    public DataEXPage createDataEXPage() {
        return new DataEXPage();
    }

    /**
     * Create an instance of {@link DataEXPageResult }
     * 
     */
    public DataEXPageResult createDataEXPageResult() {
        return new DataEXPageResult();
    }

    /**
     * Create an instance of {@link DataEXResultSet }
     * 
     */
    public DataEXResultSet createDataEXResultSet() {
        return new DataEXResultSet();
    }

    /**
     * Create an instance of {@link DataEXDataRow }
     * 
     */
    public DataEXDataRow createDataEXDataRow() {
        return new DataEXDataRow();
    }

    /**
     * Create an instance of {@link DataEXConfirmRequest }
     * 
     */
    public DataEXConfirmRequest createDataEXConfirmRequest() {
        return new DataEXConfirmRequest();
    }

    /**
     * Create an instance of {@link DataEXQueryResult }
     * 
     */
    public DataEXQueryResult createDataEXQueryResult() {
        return new DataEXQueryResult();
    }

    /**
     * Create an instance of {@link DataEXDataResult }
     * 
     */
    public DataEXDataResult createDataEXDataResult() {
        return new DataEXDataResult();
    }

    /**
     * Create an instance of {@link DataEXError }
     * 
     */
    public DataEXError createDataEXError() {
        return new DataEXError();
    }

    /**
     * Create an instance of {@link DataEXToken }
     * 
     */
    public DataEXToken createDataEXToken() {
        return new DataEXToken();
    }

    /**
     * Create an instance of {@link DataEXQueryRequest }
     * 
     */
    public DataEXQueryRequest createDataEXQueryRequest() {
        return new DataEXQueryRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryDataConfirm }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dataEX_share", name = "queryDataConfirm")
    public JAXBElement<QueryDataConfirm> createQueryDataConfirm(QueryDataConfirm value) {
        return new JAXBElement<QueryDataConfirm>(_QueryDataConfirm_QNAME, QueryDataConfirm.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryDataNOResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dataEX_share", name = "queryDataNOResponse")
    public JAXBElement<QueryDataNOResponse> createQueryDataNOResponse(QueryDataNOResponse value) {
        return new JAXBElement<QueryDataNOResponse>(_QueryDataNOResponse_QNAME, QueryDataNOResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dataEX_share", name = "queryData")
    public JAXBElement<QueryData> createQueryData(QueryData value) {
        return new JAXBElement<QueryData>(_QueryData_QNAME, QueryData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryDataNO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dataEX_share", name = "queryDataNO")
    public JAXBElement<QueryDataNO> createQueryDataNO(QueryDataNO value) {
        return new JAXBElement<QueryDataNO>(_QueryDataNO_QNAME, QueryDataNO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dataEX_share", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dataEX_share", name = "queryDataResponse")
    public JAXBElement<QueryDataResponse> createQueryDataResponse(QueryDataResponse value) {
        return new JAXBElement<QueryDataResponse>(_QueryDataResponse_QNAME, QueryDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dataEX_share", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryDataDiffResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dataEX_share", name = "queryDataDiffResponse")
    public JAXBElement<QueryDataDiffResponse> createQueryDataDiffResponse(QueryDataDiffResponse value) {
        return new JAXBElement<QueryDataDiffResponse>(_QueryDataDiffResponse_QNAME, QueryDataDiffResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryDataDiff }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dataEX_share", name = "queryDataDiff")
    public JAXBElement<QueryDataDiff> createQueryDataDiff(QueryDataDiff value) {
        return new JAXBElement<QueryDataDiff>(_QueryDataDiff_QNAME, QueryDataDiff.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryDataConfirmResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dataEX_share", name = "queryDataConfirmResponse")
    public JAXBElement<QueryDataConfirmResponse> createQueryDataConfirmResponse(QueryDataConfirmResponse value) {
        return new JAXBElement<QueryDataConfirmResponse>(_QueryDataConfirmResponse_QNAME, QueryDataConfirmResponse.class, null, value);
    }

}
