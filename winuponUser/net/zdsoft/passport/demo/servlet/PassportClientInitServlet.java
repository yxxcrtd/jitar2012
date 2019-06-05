/* 
 * @(#)PassportClientInitServlet.java    Created on 2008-10-28
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: PassportClientInitServlet.java 1204 2009-06-02 03:38:34Z huangwj $
 */
package net.zdsoft.passport.demo.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.zdsoft.passport.service.client.PassportClient;
import net.zdsoft.passport.service.client.PassportClientParam;
import net.zdsoft.passport.service.client.RemotingServiceProvider;
import net.zdsoft.passport.service.client.provider.*;

import org.apache.commons.lang3.StringUtils;

/**
 * 对 {@link PassportClient} 类进行初始化的Servlet类.<br>
 * 此Servlet应该在应用启动时进行初始化操作.
 * 
 * @author huangwj
 * @version $Revision: 1204 $, $Date: 2009-06-02 11:38:34 +0800 (星期二, 02 六月 2009) $
 */
public class PassportClientInitServlet extends HttpServlet {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7552414514624448570L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 获取适合自身系统的serverId(服务器编号)和verifyKey(校验码)
        String passportURL = config.getInitParameter("passportURL");
        String serverId = config.getInitParameter("serverId");
        String verifyKey = config.getInitParameter("verifyKey");
        if(passportURL == null) return;
        
        if (StringUtils.isEmpty(passportURL)) {
            throw new NullPointerException("passportURL is null");
        }

        if (StringUtils.isEmpty(serverId)) {
            throw new NullPointerException("serverId is null");
        }

        if (StringUtils.isEmpty(verifyKey)) {
            throw new NullPointerException("verifyKey is null");
        }

        // 创建PassportClient参数对象
        PassportClientParam param = new PassportClientParam(passportURL,
                Integer.parseInt(serverId), verifyKey);

        // 创建远程服务提供对象, 可选服务协议有SOAP和Spring HttpInvoker
        //RemotingServiceProvider provider = new HttpInvokerProvider();
        RemotingServiceProvider provider = new WebServiceProvider();

        // 对PassportClient初始化, 这里使用Spring HttpInvoker协议
        PassportClient.getInstance().init(param, provider);
    }

}
