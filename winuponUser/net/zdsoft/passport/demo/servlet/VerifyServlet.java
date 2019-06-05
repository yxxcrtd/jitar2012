/* 
 * @(#)VerifyServlet.java    Created on 2008-10-24
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: VerifyServlet.java 1205 2009-06-02 03:38:41Z huangwj $
 */
package net.zdsoft.passport.demo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.zdsoft.passport.demo.client.SessionManager;
import net.zdsoft.passport.demo.dto.User;
import net.zdsoft.passport.entity.Account;
import net.zdsoft.passport.service.client.PassportClient;

import org.apache.commons.lang3.StringUtils;

/**
 * 本地系统验证用户登录的Servlet类. 此类里应该包含验证用户合法性并进行本地登录的业务逻辑.<br>
 * 当用户浏览器在本系统发起登录请求之后, 会先到Passport去验证此用户是否合法.<br>
 * 如果一切正常, Passport会让浏览器重定向到此Servlet的URL.
 * 
 * @author huangwj
 * @version $Revision: 1205 $, $Date: 2009-06-02 11:38:41 +0800 (星期二, 02 六月 2009) $
 */
public class VerifyServlet extends HttpServlet {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6175197674254313136L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取Passport传递过来的参数
        String ticket = req.getParameter("ticket");
        String auth = req.getParameter("auth");
        String input = req.getParameter("input");
        String url = req.getParameter("url");

        //System.out.println("ticket="+ticket);
        //System.out.println("auth="+auth);
        //System.out.println("input="+input);
        //System.out.println("url="+url);
        
        // 对参数进行校验
        if (StringUtils.isEmpty(ticket)) {
            print("Passport ticket为空", resp);
            return;
        }

        if (StringUtils.isEmpty(auth)) {
            print("Passport校验码为空", resp);
            return;
        }

        if (StringUtils.isEmpty(input)) {
            if (!PassportClient.getInstance().isValidVerifyAuth(ticket, url,
                    auth)) {
                print("Passport校验码非法", resp);
                return;
            }
        }
        else if (!PassportClient.getInstance().isValidVerifyAuth(ticket, url,
                input, auth)) {
            print("Passport校验码非法", resp);
            return;
        }

        // 获取ticket所属的Passport用户信息
        Account account = null;
        try {
            account = PassportClient.getInstance().checkTicket(ticket);
        }
        catch (Exception e) {
            print(e.getMessage(), resp);
            return;
        }

        if (account == null) {
            print("Passport ticket[" + ticket + "]对应的帐号不存在", resp);
            return;
        }

        // 此处只是简单写一下, 实际使用中可以根据account的id从系统中获取关联的用户信息
        // 例如: User user = userService.getUserByAccountId(account.getId());
        User user = new User();
        user.setAccountId(account.getId());
        user.setUsername(account.getUsername());
        user.setNickname(account.getNickname());

        // 获取用户在Passport里的积分
        /*
        Score score = null;
        try {
            score = PassportClient.getInstance().queryScore(account.getId());

        }
        catch (Exception e) {
            print(e.getMessage(), resp);
            return;
        }

        // 如果获取到了用户积分, 则设置到用户信息里
        if (score != null) {
            user.setScore(score.getScore());
        }
		*/
        
        HttpSession session = req.getSession();
        // 将当前登录的用户保存到session中
        session.setAttribute(User.KEY, user);
        // 将属于用户的ticket也记录到session中
        session.setAttribute(SessionManager.PASSPORT_TICKET_KEY, ticket);
        // 同时将ticket保存到SessionManager中,
        // 这样当此session失效的时候就可以通知Passport做ticket失效处理.
        // Passport会通知其他站点也对该用户做退出操作, 即实现“一退全退”.
        SessionManager.putTicket(ticket, session);

        // 如果登录时没有设置要返回的页面, 则跳转到登录系统后的默认页面
        // 否则跳转到指定的返回页面
        if (StringUtils.isEmpty(url)) {
            resp.sendRedirect("/index.py");
        }
        else {
            resp.sendRedirect(url);
        }
    }

    // 输出信息
    private void print(String text, HttpServletResponse resp)
            throws IOException {
        resp.getWriter().println(text);
    }

}
