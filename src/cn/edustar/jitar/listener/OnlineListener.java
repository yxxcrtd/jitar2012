package cn.edustar.jitar.listener;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.edustar.jitar.user.Constant;
import cn.edustar.jitar.user.UserSession;

/**
 * 用户在线的监听器
 */
@SuppressWarnings("rawtypes")
public class OnlineListener implements HttpSessionListener {

	@Override
	// 监听 Session 的创建
	public void sessionCreated(HttpSessionEvent se) {
		// 
	}

	@Override
	// 监听 Session 的销毁，在 session.invalidate() 时自动触发
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		UserSession us = (UserSession) session.getAttribute(Constant.USER_SESSION_KEY);
		// session.getServletContext() 是全局的 Application
		CopyOnWriteArrayList onlineList = (CopyOnWriteArrayList) session.getServletContext().getAttribute("onlineList");
		if (null != onlineList && 0 < onlineList.size() && us != null) {
			session.getServletContext().removeAttribute(us.getUsername());
			onlineList.remove(us);
		}
	}

}
