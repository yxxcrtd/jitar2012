/* 
 * @(#)LogoutServlet.java    Created on 2008-10-28
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: LogoutServlet.java 1205 2009-06-02 03:38:41Z huangwj $
 */
package net.zdsoft.passport.demo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zdsoft.passport.demo.client.SessionManager;
import net.zdsoft.passport.demo.dto.User;
import net.zdsoft.passport.service.client.PassportClient;

/**
 * 处理用户退出系统操作的Servlet类.
 * 
 * @author huangwj
 * @version $Revision: 1205 $, $Date: 2009-06-02 11:38:41 +0800 (星期二, 02 六月 2009) $
 */
public class LogoutServlet extends HttpServlet {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -3903797543957844738L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取ticket
        String ticket = (String) req.getSession().getAttribute(
                SessionManager.PASSPORT_TICKET_KEY);
        // 从SessionManager中清除ticket
        SessionManager.removeTicket(ticket);

        // 清除ticket是为了在session失效的时候不再重复通知Passport
        req.getSession().removeAttribute(SessionManager.PASSPORT_TICKET_KEY);
        req.getSession().removeAttribute(User.KEY);
        req.getSession().invalidate();

        // 跳转到Passport的退出系统的地址
        String passportLogoutURL = PassportClient.getInstance().getLogoutURL(
                ticket, getWebsiteRoot(req), null);
        resp.sendRedirect(passportLogoutURL);
    }

    private static String getWebsiteRoot(HttpServletRequest request) {
        int serverPort = request.getServerPort();
        return request.getScheme() + "://" + request.getServerName()
                + (serverPort == 80 ? "" : ":" + serverPort)
                + request.getContextPath();
    }

}
