/* 
 * @(#)SessionManager.java    Created on 2008-10-23
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: SessionManager.java 1203 2009-06-02 03:29:44Z huangwj $
 */
package net.zdsoft.passport.demo.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.passport.exception.PassportException;
import net.zdsoft.passport.service.client.PassportClient;

/**
 * 用户会话管理类.
 * 
 * @author huangwj
 * @version $Revision: 1203 $, $Date: 2009-06-02 11:29:44 +0800 (星期二, 02 六月 2009) $
 */
public class SessionManager implements HttpSessionListener {

    public static final String PASSPORT_TICKET_KEY = "passport.ticket";

    private static final Logger log = LoggerFactory.getLogger(SessionManager.class);

    private static Map<String, HttpSession> ticket2SessionMap = new ConcurrentHashMap<String, HttpSession>();

    public SessionManager() {
    }

    // 当session被web容器创建后会调用此方法
    public void sessionCreated(HttpSessionEvent sessionEvent) {
    }

    // 当session被web容器销毁之前会调用此方法
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        if (session != null) {
            // 取出登录时记录的ticket
            String ticket = (String) session.getAttribute(PASSPORT_TICKET_KEY);
            if (ticket != null) {
                try {
                    // 删除ticket
                    removeTicket(ticket);
                    // 调用Passport服务, 让Passport通知其他站点也做退出操作
                    PassportClient.getInstance().invalidate(ticket);
                    log.info("Invalidated ticket[" + ticket + "]");
                }
                catch (PassportException e) {
                    log.error("Could not invalidate ticket[" + ticket + "]", e);
                }
            }
        }
    }

    // 将ticket和对应的session保存到map中
    public static void putTicket(String ticket, HttpSession session) {
        if (ticket != null) {
            ticket2SessionMap.put(ticket, session);
        }
    }

    // 将指定的ticket从保存ticket的map中删除
    public static HttpSession removeTicket(String ticket) {
        return ticket == null ? null : ticket2SessionMap.remove(ticket);
    }

}
