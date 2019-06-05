package org.jasig.cas.client.authentication;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CasConst;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Filter implementation to intercept all requests and attempt to authenticate
 * the user by redirecting them to CAS (unless the user has a ticket).
 * <p>
 * This filter allows you to specify the following parameters (at either the context-level or the filter-level):
 * <ul>
 * <li><code>casServerLoginUrl</code> - the url to log into CAS, i.e. https://cas.rutgers.edu/login</li>
 * <li><code>renew</code> - true/false on whether to use renew or not.</li>
 * <li><code>gateway</code> - true/false on whether to use gateway or not.</li>
 * </ul>
 *
 * <p>Please see AbstractCasFilter for additional properties.</p>
 */
public class AuthenticationFilter extends AbstractCasFilter {

    /**
     * The URL to the CAS Server login.
     */
    private String casServerLoginUrl;
    
    /**
     * 返回地址
     */
    private String returnUrl;
    
    /**
     * 注销地址
     */
    private String callbackLogOutUrl;
    /**
     * Whether to send the renew request or not.
     */
    private boolean renew = false;

    /**
     * Whether to send the gateway request or not.
     */
    private boolean gateway = false;
    
    
    private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();

    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        if (!isIgnoreInitConfiguration()) {
            super.initInternal(filterConfig);
            
            this.callbackLogOutUrl=getPropertyFromInitParams(filterConfig, "callbackLogOutUrl", "");
            
            String retUrl=getPropertyFromInitParams(filterConfig, "returnUrl", "");
            if(retUrl!=null && retUrl.length()>0){
            	if(retUrl.startsWith("http://") || retUrl.startsWith("https://")){
            		
            	}else{
            		String server= CasConst.getInstance().getSiteServerUrl();
            		if(server == null) return; /* server 为 null 启动会报错？ */
            		if(server.endsWith("/")){
            			server=server.substring(0,server.length()-1);
            		}
            		if(!retUrl.startsWith("/")){
            			retUrl = "/" + retUrl;
            		}
            		retUrl = server+retUrl;
            	}
            }
            this.returnUrl=retUrl;
            
            
            String loginurl=getPropertyFromInitParams(filterConfig, "casServerLoginUrl", null);
            if(loginurl==null || loginurl.length()==0){
            	setCasServerLoginUrl(CasConst.getInstance().getCasServerLoginUrl());
            }else{
            	setCasServerLoginUrl(loginurl);
            }
            
            log.info("CAS登录过滤器地址：" + this.casServerLoginUrl);
            setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
            log.info("Loaded renew parameter: " + this.renew);
            setGateway(parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));
            log.info("Loaded gateway parameter: " + this.gateway);

            final String gatewayStorageClass = getPropertyFromInitParams(filterConfig, "gatewayStorageClass", null);

            if (gatewayStorageClass != null) {
                try {
                    this.gatewayStorage = (GatewayResolver) Class.forName(gatewayStorageClass).newInstance();
                } catch (final Exception e) {
                    log.error(e,e);
                    throw new ServletException(e);
                }
            }
        }
    }

    public void init() {
        super.init();
        //casServerLoginUrl参数允许为空
        //CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
    }

    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        //log.debug("----------------------------------------------------");
        log.debug("------------------登录的过滤器开始了---------------------");
        //log.debug("----------------------------------------------------");
        
        //如果casServerLoginUrl参数为空，获得默认的casServerLoginUrl
        log.debug("当前过滤的地址是 getRequestURI="+request.getRequestURI());
        log.debug("casServerLoginUrl= "+this.casServerLoginUrl);
        log.debug("returnUrl= "+this.returnUrl);
        
        String loginurl=request.getServletContext().getFilterRegistration("CAS-Authentication-Filter").getInitParameter("casServerLoginUrl");
        if(loginurl==null || loginurl.length()==0){
        	//如果没设置，则重新得到默认
        	this.casServerLoginUrl=CasConst.getInstance().getCasServerLoginUrl();
        }
        else{
	    	if(this.casServerLoginUrl==null || this.casServerLoginUrl.length()==0){
	    		this.casServerLoginUrl=CasConst.getInstance().getCasServerLoginUrl();
	    	}
        }
    	log.debug("casServerLoginUrl="+this.casServerLoginUrl);
    	
        final HttpSession session = request.getSession(false);
        final Assertion assertion = session != null ? (Assertion) session.getAttribute(CONST_CAS_ASSERTION) : null;

        String ru=request.getParameter("ru");
        if(this.returnUrl!=null && this.returnUrl.length()>0){
        	if(this.returnUrl.startsWith("http://") || this.returnUrl.startsWith("https://")){
        		
        	}else{
        		String server= CasConst.getInstance().getSiteServerUrl();
        		if(server != null){
	        		if(server.endsWith("/")){
	        			server=server.substring(0,server.length()-1);
	        		}
	        		if(!this.returnUrl.startsWith("/")){
	        			this.returnUrl="/"+this.returnUrl;
	        		}
	        		this.returnUrl=server+this.returnUrl;
        		}
        	}
        }else{
        	this.returnUrl=CasConst.getInstance().getSiteServerUrl();
        }
        
    	if(ru==null){
    		ru=returnUrl;
    	}
        
        //保存参数ru,但是可能会访问两次，第2次是null
    	String ruu=request.getParameter("ruu");
    	if(ruu!=null && ruu.length()>0){
    		String root=CasConst.getInstance().getSiteServerUrl();
    		if(root.endsWith("/")){
    			root=root.substring(0,root.length()-1);
    		}
    		ru=root+"/login/index.jsp";
    		
            HttpSession mySession = request.getSession(true);
    		log.debug("保存到SESSION 中的ruu= "+request.getParameter("ruu"));
        	mySession.setAttribute("__ruu", request.getParameter("ruu"));
        	
    	}

        if (assertion != null) {
        	//System.out.println("**************************检查Assertion**************************");
        	//System.out.println("ValidFromDate="+assertion.getValidFromDate().toString()); 
        	//去掉下面的注释，再登录用户变成待审核状态时会出现null引用异常
        	//System.out.println("ValidUntilDate="+assertion.getValidUntilDate().toString());
            filterChain.doFilter(request, response);
            return;
        }

        /*
	    System.out.println("**********-----------------检查Session的时间设置-----------------****");
	    if(session==null){
	    	System.out.println("session:不存在！需要重新登录！！！"); 
	    }
	    else{
	    	System.out.println("获取session的Id:" + session.getId());
	    	java.util.Date date1 = new java.util.Date(session.getCreationTime());
	    	System.out.println("获取session的创建时间:" + date1.toString());
	    	java.util.Date date2 = new java.util.Date(session.getLastAccessedTime());
	    	System.out.println("获取上次与服务器交互时间:" + date2.toString());
	    	System.out.println("获取session最大的不活动的间隔时间，以秒为单位:"+session.getMaxInactiveInterval());	        
	    }
	    */
        
        final String serviceUrl = constructServiceUrl(request, response);
        final String ticket = CommonUtils.safeGetParameter(request,getArtifactParameterName());
        final boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);

        if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
            filterChain.doFilter(request, response);
            return;
        }

        final String modifiedServiceUrl;

        log.debug("no ticket and no assertion found");
        if (this.gateway) {
            log.debug("setting gateway attribute in session");
            modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
        } else {
            modifiedServiceUrl = serviceUrl;
        }

        if (log.isDebugEnabled()) {
            log.debug("Constructed service url: " + modifiedServiceUrl);
        }

        String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);
        urlToRedirectTo=urlToRedirectTo+"&ru="+URLEncoder.encode(ru, "UTF-8");

        if(this.callbackLogOutUrl==null || this.callbackLogOutUrl.length()==0)
        {
        	this.callbackLogOutUrl=CasConst.getInstance().getSiteServerUrl();
        }else{
        	if(this.callbackLogOutUrl.startsWith("http://") || this.callbackLogOutUrl.startsWith("https://"))
        	{
        		//nothing to do
        	}
        	else {
        		String _logoutUrl=this.callbackLogOutUrl;
        		if (this.callbackLogOutUrl.startsWith("/")){
        			_logoutUrl=_logoutUrl.substring(1);
        		}
        		this.callbackLogOutUrl=CasConst.getInstance().getSiteServerUrl()+_logoutUrl;
        	}
        }
        
        urlToRedirectTo=urlToRedirectTo+"&callbackLogOutUrl="+URLEncoder.encode(this.callbackLogOutUrl, "UTF-8");
        
        if (log.isDebugEnabled()) {
             log.debug("redirecting to \"" + urlToRedirectTo + "\"");
        }
        
        response.sendRedirect(urlToRedirectTo);

    }

    public final void setRenew(final boolean renew) {
        this.renew = renew;
    }

    public final void setGateway(final boolean gateway) {
        this.gateway = gateway;
    }

    public final void setCasServerLoginUrl(final String casServerLoginUrl) {
    	if(casServerLoginUrl==null || casServerLoginUrl.length()==0){
    		this.casServerLoginUrl=CasConst.getInstance().getCasServerLoginUrl();
    	}else{
    		this.casServerLoginUrl = casServerLoginUrl;
    	}
    }
    
    public final void setGatewayStorage(final GatewayResolver gatewayStorage) {
    	this.gatewayStorage = gatewayStorage;
    }


	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
}
