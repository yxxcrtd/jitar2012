package com.jitar2Infowarelab.utils;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * http://www.blogjava.net/junglesong/archive/2008/02/21/181196.html
 * 
 * 处理service返回的XML
 * @author dell
 *
 */
public class ResponseXML {
	
	/**
	 * 返回的处理结果
	 */
	private boolean result = false;
	
	private String exceptionID = null;
	private String reason = null;
	/**
	 * 返回的对象XML 
	 */
	private String body = null;
	/**
	 * 不带 body标志
	 */
	private String nakedBody = null;
	
	/**
	 * 得到节点内容
	 * @param xml
	 * @param xPath
	 * @return
	 * @throws DocumentException
	 */
	public String selectSingleNodeText(String xml , String xPath) throws DocumentException{
		if(null == xml || xml.length() == 0){
			return null;
		}	
		
		Document document = DocumentHelper.parseText(xml);
		Node resultNode = document.selectSingleNode(xPath);
		
		if(null == resultNode){
			return null;
		}
		return resultNode.getText();
	}
	
	/**
	 * 得到某节点的XML
	 * @param xml
	 * @param xPath
	 * @return
	 * @throws DocumentException
	 */
	public String selectSingleNodeXML(String xml , String xPath) throws DocumentException{
		if(null == xml || xml.length() == 0){
			return null;
		}	
		
		Document document = DocumentHelper.parseText(xml);
		Node resultNode = document.selectSingleNode(xPath);
		
		if(null == resultNode){
			return null;
		}
		return resultNode.asXML();
	}
	
	public boolean Parse(String xml) throws DocumentException{
		if(xml.length() == 0){
			return false;
		}
		Document document = DocumentHelper.parseText(xml);	
		Element root=document.getRootElement();
		Node resultNode = document.selectSingleNode("/Message/header/result");
		if(null == resultNode){
			return false;
		}
		String resultValue = resultNode.getText();
		if( null != resultValue && resultValue.length() > 0){
			if("SUCCESS".toLowerCase().equals(resultValue.toLowerCase())){
				this.result = true;
			}else if("FAILURE".toLowerCase().equals(resultValue.toLowerCase())){
				//得到错误号和原因
				Node exceptionIDNode = document.selectSingleNode("/Message/header/exceptionID");
				if(null != exceptionIDNode){
					this.exceptionID = exceptionIDNode.getText();
				}
				Node reasonNode = document.selectSingleNode("/Message/header/reason");
				if(null != reasonNode){
					this.reason = reasonNode.getText();
				}
			}
		}
		Node resultBody = document.selectSingleNode("/Message/body");
		if( null !=resultBody){
			String _body = resultBody.asXML();
			this.body = _body;
			//去掉<body>  </body>
			if(_body.startsWith("<body>")){
				_body = _body.substring(6);
				_body = _body.substring(0,_body.length()-7);
			}
			this.nakedBody = _body;
		}
		
		return true;
		
	}

	public boolean isResult() {
		return result;
	}

	public String getBody() {
		return body;
	}

	public String getNakedBody() {
		return nakedBody;
	}

	public String getExceptionID() {
		return exceptionID;
	}


	public String getReason() {
		return reason;
	}


}
