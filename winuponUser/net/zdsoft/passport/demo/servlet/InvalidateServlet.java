/* 
 * @(#)InvalidateServlet.java    Created on 2008-10-28
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: InvalidateServlet.java 1205 2009-06-02 03:38:41Z huangwj $
 */
package net.zdsoft.passport.demo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.passport.demo.client.SessionManager;
import net.zdsoft.passport.service.client.PassportClient;

/**
 * 本地系统对当前会话的已登录用户做退出系统操作的Servlet类.<br>
 * 当用户在其他系统退出并通知Passport之后, Passport会让浏览器重定向到此Servlet的URL.
 * 
 * @author huangwj
 * @version $Revision: 1205 $, $Date: 2009-06-02 11:38:41 +0800 (星期二, 02 六月 2009) $
 */
public class InvalidateServlet extends HttpServlet {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2028065667782348781L;

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取Passport传递过来的参数
        String ticket = req.getParameter("ticket");
        String auth = req.getParameter("auth");

        // 对参数进行校验
        if (StringUtils.isEmpty(ticket) || StringUtils.isEmpty(auth)) {
            print(ERROR, resp);
            return;
        }

        if (!PassportClient.getInstance().isValidInvalidateAuth(ticket, auth)) {
            print(ERROR, resp);
            return;
        }

        // 从SessionManager中通过ticket取出绑定到session, 对其做失效处理
        HttpSession session = SessionManager.removeTicket(ticket);
        if (session != null) {
            session.removeAttribute(SessionManager.PASSPORT_TICKET_KEY);
            session.invalidate();
        }

        print(SUCCESS, resp);
    }

    // 输出信息
    private void print(String text, HttpServletResponse resp)
            throws IOException {
        resp.getWriter().println(text);
    }

}
