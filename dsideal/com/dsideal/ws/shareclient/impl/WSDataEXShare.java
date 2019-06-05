package com.dsideal.ws.shareclient.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2013-05-15T09:54:20.046+08:00
 * Generated source version: 2.7.4
 * 
 */
@WebService(targetNamespace = "http://dataEX_share", name = "WS_DataEX_Share")
@XmlSeeAlso({ObjectFactory.class})
public interface WSDataEXShare {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryDataDiff", targetNamespace = "http://dataEX_share", className = "com.dsideal.ws.shareclient.impl.QueryDataDiff")
    @WebMethod
    @ResponseWrapper(localName = "queryDataDiffResponse", targetNamespace = "http://dataEX_share", className = "com.dsideal.ws.shareclient.impl.QueryDataDiffResponse")
    public com.dsideal.ws.shareclient.impl.DataEXQueryResult queryDataDiff(
        @WebParam(name = "queryRequest", targetNamespace = "")
        com.dsideal.ws.shareclient.impl.DataEXQueryRequest queryRequest
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "login", targetNamespace = "http://dataEX_share", className = "com.dsideal.ws.shareclient.impl.Login")
    @WebMethod
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://dataEX_share", className = "com.dsideal.ws.shareclient.impl.LoginResponse")
    public java.lang.String login(
        @WebParam(name = "userName", targetNamespace = "")
        java.lang.String userName,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password,
        @WebParam(name = "loginTime", targetNamespace = "")
        java.lang.String loginTime
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryDataNO", targetNamespace = "http://dataEX_share", className = "com.dsideal.ws.shareclient.impl.QueryDataNO")
    @WebMethod
    @ResponseWrapper(localName = "queryDataNOResponse", targetNamespace = "http://dataEX_share", className = "com.dsideal.ws.shareclient.impl.QueryDataNOResponse")
    public com.dsideal.ws.shareclient.impl.DataEXPageResult queryDataNO(
        @WebParam(name = "queryRequest", targetNamespace = "")
        com.dsideal.ws.shareclient.impl.DataEXQueryRequest queryRequest
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryData", targetNamespace = "http://dataEX_share", className = "com.dsideal.ws.shareclient.impl.QueryData")
    @WebMethod
    @ResponseWrapper(localName = "queryDataResponse", targetNamespace = "http://dataEX_share", className = "com.dsideal.ws.shareclient.impl.QueryDataResponse")
    public com.dsideal.ws.shareclient.impl.DataEXQueryResult queryData(
        @WebParam(name = "queryRequest", targetNamespace = "")
        com.dsideal.ws.shareclient.impl.DataEXQueryRequest queryRequest,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1
    );

    @RequestWrapper(localName = "queryDataConfirm", targetNamespace = "http://dataEX_share", className = "com.dsideal.ws.shareclient.impl.QueryDataConfirm")
    @WebMethod
    @ResponseWrapper(localName = "queryDataConfirmResponse", targetNamespace = "http://dataEX_share", className = "com.dsideal.ws.shareclient.impl.QueryDataConfirmResponse")
    public void queryDataConfirm(
        @WebParam(name = "queryRequest", targetNamespace = "")
        com.dsideal.ws.shareclient.impl.DataEXConfirmRequest queryRequest,
        @WebParam(name = "arg1", targetNamespace = "")
        java.util.List<java.lang.String> arg1
    );
}