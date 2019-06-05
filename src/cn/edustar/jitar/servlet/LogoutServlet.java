package cn.edustar.jitar.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UserOnLine;
import cn.edustar.jitar.service.OnLineService;
import cn.edustar.jitar.service.UserService;

public class LogoutServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3035612945184772867L;

	@Override
	public void init() {
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session!=null){
			ServletContext sc =session.getServletContext();
			ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
			UserService userService = (UserService) ac.getBean("userService");
			OnLineService onlineService = (OnLineService) ac.getBean("onlineService");
			
			String ssoId=session.getAttribute(User.SESSION_LOGIN_NAME_KEY).toString();
			if(ssoId!=null && ssoId.length()>0){
				User user=userService.getUserByLoginName(ssoId);
				if(user!=null){
					//long nowTime = System.currentTimeMillis();
					//SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
					//sdf.applyPattern("yyyy年MM月dd日 HH时mm分ss秒");
					UserOnLine userOnLine = new UserOnLine();
					userOnLine.setUserId(Integer.valueOf(user.getUserId()));
					userOnLine.setUserName(user.getLoginName());
					onlineService.removeUserOnLine(userOnLine);
				}
			}
			
			session.invalidate();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		
	}	
}
