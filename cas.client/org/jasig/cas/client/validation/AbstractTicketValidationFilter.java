/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.cas.client.validation;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CasConst;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.ReflectUtils;

import javax.net.ssl.HostnameVerifier;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * The filter that handles all the work of validating ticket requests.
 * <p>
 * This filter can be configured with the following values:
 * <ul>
 * <li><code>redirectAfterValidation</code> - redirect the CAS client to the same URL without the ticket.</li>
 * <li><code>exceptionOnValidationFailure</code> - throw an exception if the validation fails.  Otherwise, continue
 *  processing.</li>
 * <li><code>useSession</code> - store any of the useful information in a session attribute.</li>
 * </ul>
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.1
 */
public abstract class AbstractTicketValidationFilter extends AbstractCasFilter {

    /** The TicketValidator we will use to validate tickets. */
    private TicketValidator ticketValidator;

    /**
     * Specify whether the filter should redirect the user agent after a
     * successful validation to remove the ticket parameter from the query
     * string.
     */
    private boolean redirectAfterValidation = false;

    /** Determines whether an exception is thrown when there is a ticket validation failure. */
    private boolean exceptionOnValidationFailure = true;

    private boolean useSession = true;

    /**
     * Template method to return the appropriate validator.
     *
     * @param filterConfig the FilterConfiguration that may be needed to construct a validator.
     * @return the ticket validator.
     */
    protected TicketValidator getTicketValidator(final FilterConfig filterConfig) {
        return this.ticketValidator;
    }
   
    /**
     * Gets the configured {@link HostnameVerifier} to use for HTTPS connections
     * if one is configured for this filter.
     * @param filterConfig Servlet filter configuration.
     * @return Instance of specified host name verifier or null if none specified.
     */
    protected HostnameVerifier getHostnameVerifier(final FilterConfig filterConfig) {
        final String className = getPropertyFromInitParams(filterConfig, "hostnameVerifier", null);
        log.trace("Using hostnameVerifier parameter: " + className);
        final String config = getPropertyFromInitParams(filterConfig, "hostnameVerifierConfig", null);
        log.trace("Using hostnameVerifierConfig parameter: " + config);
        if (className != null) {
            if (config != null) {
                return ReflectUtils.newInstance(className, config);
            } else {
                return ReflectUtils.newInstance(className);
            }
        }
        return null;
    }

    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        setExceptionOnValidationFailure(parseBoolean(getPropertyFromInitParams(filterConfig, "exceptionOnValidationFailure", "true")));
        log.trace("Setting exceptionOnValidationFailure parameter: " + this.exceptionOnValidationFailure);
        setRedirectAfterValidation(parseBoolean(getPropertyFromInitParams(filterConfig, "redirectAfterValidation", "false")));
        log.trace("Setting redirectAfterValidation parameter: " + this.redirectAfterValidation);
        setUseSession(parseBoolean(getPropertyFromInitParams(filterConfig, "useSession", "true")));
        log.trace("Setting useSession parameter: " + this.useSession);
        setTicketValidator(getTicketValidator(filterConfig));
        super.initInternal(filterConfig);
    }

    public void init() {
        super.init();
        CommonUtils.assertNotNull(this.ticketValidator, "ticketValidator cannot be null.");
    }

    /**
     * Pre-process the request before the normal filter process starts.  This could be useful for pre-empting code.
     *
     * @param servletRequest The servlet request.
     * @param servletResponse The servlet response.
     * @param filterChain the filter chain.
     * @return true if processing should continue, false otherwise.
     * @throws IOException if there is an I/O problem
     * @throws ServletException if there is a servlet problem.
     */
    protected boolean preFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        return true;
    }

    /**
     * Template method that gets executed if ticket validation succeeds.  Override if you want additional behavior to occur
     * if ticket validation succeeds.  This method is called after all ValidationFilter processing required for a successful authentication
     * occurs.
     *
     * @param request the HttpServletRequest.
     * @param response the HttpServletResponse.
     * @param assertion the successful Assertion from the server.
     */
    protected void onSuccessfulValidation(final HttpServletRequest request, final HttpServletResponse response, final Assertion assertion) {
        // nothing to do here.                                                                                            
    }

    /**
     * Template method that gets executed if validation fails.  This method is called right after the exception is caught from the ticket validator
     * but before any of the processing of the exception occurs.
     *
     * @param request the HttpServletRequest.
     * @param response the HttpServletResponse.
     */
    protected void onFailedValidation(final HttpServletRequest request, final HttpServletResponse response) {
        // nothing to do here.
    	//提示处理错误！票证不合法
    	//exceptionOnValidationFailure=false;
    }
    
    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {

        if (!preFilter(servletRequest, servletResponse, filterChain)) {
            return;
        }
        //log.debug("----------------------------------------------------");
        //log.debug("------------------验证的过滤器开始了---------------------");
        //log.debug("----------------------------------------------------");
        
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        String ticket = CommonUtils.safeGetParameter(request, getArtifactParameterName());
        //log.debug("当前的ticket="+ticket);
        if(ticket==null || ticket.length()==0){
        	//看看是否有
        }
        
        if (CommonUtils.isNotBlank(ticket)) {
            if (log.isDebugEnabled()) {
                log.debug("Attempting to validate ticket: " + ticket);
            }

            try {
                final Assertion assertion = this.ticketValidator.validate(ticket, constructServiceUrl(request, response));
                if(assertion==null){
                	//期间出错了。
                	filterChain.doFilter(request, response);
                	return;
                }
                if (log.isDebugEnabled()) {
                	
                    log.debug("Successfully authenticated user: " + assertion.getPrincipal().getName());
                }

                request.setAttribute(CONST_CAS_ASSERTION, assertion);

                if (this.useSession) {
                    request.getSession().setAttribute(CONST_CAS_ASSERTION, assertion);
                }
                onSuccessfulValidation(request, response, assertion);

                if (this.redirectAfterValidation) {
                    log. debug("Redirecting after successful ticket validation.");
                    
                    //转向了service
                    //response.sendRedirect(constructServiceUrl(request, response));
                    
                    
                    //转向了首页
                    response.sendRedirect(CasConst.getInstance().getSiteServerUrl());
                    
                    return;
                }
            } catch (final TicketValidationException e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                log.warn(e, e);
                //验证失败
                onFailedValidation(request, response);

                if (this.exceptionOnValidationFailure) {
                    //throw new ServletException(e);
                	log.error(e.getMessage());
                	log.error(e.getStackTrace().toString());
                }

                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    public final void setTicketValidator(final TicketValidator ticketValidator) {
    this.ticketValidator = ticketValidator;
}

    public final void setRedirectAfterValidation(final boolean redirectAfterValidation) {
        this.redirectAfterValidation = redirectAfterValidation;
    }

    public final void setExceptionOnValidationFailure(final boolean exceptionOnValidationFailure) {
        this.exceptionOnValidationFailure = exceptionOnValidationFailure;
    }

    public final void setUseSession(final boolean useSession) {
        this.useSession = useSession;
    }




}
