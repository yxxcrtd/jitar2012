package org.jasig.cas.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CasConst {
	Logger log = LoggerFactory.getLogger(CasConst.class);
	private String casServerLoginUrl=null;
	private String casServerUrlPrefix=null;	
	private String siteServer=null;
	private String siteServerUrl=null;
	private CasConst(){}
	private static CasConst instance = new CasConst();
	public static CasConst getInstance() {    
		return instance;      
	}
	public String getCasServerLoginUrl() {
		return casServerLoginUrl;
	}
	public void setCasServerLoginUrl(String casServerLoginUrl) {
		this.casServerLoginUrl = casServerLoginUrl;
	}
	public String getCasServerUrlPrefix() {
		return casServerUrlPrefix;
	}
	public void setCasServerUrlPrefix(String casServerUrlPrefix) {
		this.casServerUrlPrefix = casServerUrlPrefix;
	}
	public String getSiteServer() {
		return siteServer;
	}
	public void setSiteServer(String siteServer) {
		this.siteServer = siteServer;
	}
	public String getSiteServerUrl() {
		return siteServerUrl;
	}
	public void setSiteServerUrl(String siteServerUrl) {
		this.siteServerUrl = siteServerUrl;
	}

	
}
