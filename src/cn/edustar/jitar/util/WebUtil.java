package cn.edustar.jitar.util;

import javax.servlet.http.HttpSession;

import cn.edustar.jitar.pojos.User;

/**
 * Web 某些常用方法的集合.
 * 
 *
 */
public class WebUtil {
	private WebUtil() {
	}

	/**
	 * 模拟一个账户
	 * @param session
	 * @return
	 */
	public static User getImpersonateUser(HttpSession session) {		
		if(session.getAttribute("platuser") != null)
		{
			
			String logonedUserGuid = session.getAttribute("platuser").toString();			
			
			//String privateKey = session.getServletContext().getInitParameter("privateKey");
			//if(null == privateKey) privateKey = "www.chinaedustar.com";
			//try
			//{
			//	EncryptDecrypt ec = new EncryptDecrypt(privateKey);
			//	logonedUserGuid = ec.decrypt(logonedUserGuid);
			//}
			//catch(Exception ex)
			//{
			//	logonedUserGuid = "10000000-0000-0000-0000-000000000001";
			//}
			
			if(null == logonedUserGuid || logonedUserGuid.equals(""))
				logonedUserGuid = "10000000-0000-0000-0000-000000000001";
			
			User u = new User();
			u.setUserGuid(logonedUserGuid);
			u.setLoginName("LogonedUser");
			u.setTrueName("平台虚拟用户");
			u.setNickName("平台虚拟用户");
			return u;
		}
		else
		{
			return null;
		}
	}
	/**
	 * 得到当前登录用户.
	 * 
	 * @param session
	 * @return
	 */
	public static User getLoginUser(HttpSession session) {		
		if (session == null)
		{
			return null;
		}
		
		return (User) session.getAttribute(User.SESSION_LOGIN_USERMODEL_KEY);
	}

	/**
	 * 清除登录状态, 一般在修改了用户信息之后使用. 将强迫过滤器重新加载用户信息.
	 */
	public static void clearLoginSession(HttpSession session) {
		if (session == null) return;
		User.clearLoginSession(session);
	}

}
