package cn.edustar.jitar.interceptor;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edustar.jitar.action.AdminStatAction;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.user.Constant;
import cn.edustar.jitar.user.UserSession;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 用户登录拦截器
 *
 * @author Yang XinXin
 * @version 1.0.0 Apr 1, 2009 1:10:55 PM
 */
@SuppressWarnings("serial")
public class LoginInterceptor extends AbstractInterceptor {

	/* (non-Javadoc)
	 *
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Object action = actionInvocation.getAction();
		String sessionId = ServletActionContext.getRequest().getSession().getId();
		
		// 不拦截 AdminStatAction
		if (action instanceof AdminStatAction) {
			return actionInvocation.invoke();
		}

		ActionContext ac = actionInvocation.getInvocationContext();		
		UserSession us = (UserSession) ac.getSession().get(Constant.USER_SESSION_KEY);
		
		// 正常的用户在线
		if (null == us) {
			long nowTime = System.currentTimeMillis();
			// 往用户Session对象中放入一个游客对象
			us = new UserSession();
			us.setSessionId(sessionId);
			us.setUsername("_" + nowTime);
			us.setTrueName("");
			us.setPositionId(0);
			us.setIsAdmin(0);
			us.setSuperAdmin("");
			ac.getSession().put(Constant.USER_SESSION_KEY, us);
		} else {
			// 系统中的用户名
			String username = "";
			
			// Session 中的用户对象
			User user = (User) ac.getSession().get(User.SESSION_LOGIN_USERMODEL_KEY);
			
			// 获取用户名
			if (null == user) {
				// 如果没有从Session中获取到用户名（几乎不可能），直接将游客Session赋给用户名
				username = us.getUsername();
			} else {
				username = user.getLoginName();
			}
			
			// 判断同一用户是否重复登录
			if ("on".equals(ServletActionContext.getServletContext().getAttribute(username))) {
				return actionInvocation.invoke();
			}
			
			// 如果 用户Session对象中的用户名 等于 用户对象中的用户名，就不用再次赋值了
			if (!username.equals(us.getUsername())) {
				// 从游客变成注册用户（删除之前的游客session，如果还是游客、或已登录用户再次访问首页的就不必重复删除）
				us.setUsername(username);
				if (!username.startsWith("_")) {
					// 从缓存中获取用户的其他信息
					us.setTrueName(user.getTrueName());
					us.setPositionId(user.getPositionId());
					
					// 根据用户权限表设置用户属性
					ServletContext servletContext = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
					WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
					AccessControlService accessControlService = (AccessControlService) wc.getBean("accessControlService");
					ArrayList<AccessControl> list = (ArrayList<AccessControl>) accessControlService.getAllAccessControlByUser(user);
					if (null == list || 1 > list.size()) {
						us.setIsAdmin(0);
						us.setSuperAdmin("");
					} else {
						us.setIsAdmin(1);
						for (AccessControl accessControl : list) {
							if (1 == accessControl.getObjectType() && 1 == accessControl.getObjectId()) {
								us.setSuperAdmin(convertAccessToTitle(accessControl));
							} else {
								us.setSuperAdmin("");
							}
						}
					}
				}
				ServletActionContext.getServletContext().setAttribute(username, "on");
			}
		}
		
		// 继续执行 Action
		if(null == actionInvocation) return null;
		return actionInvocation.invoke();
	}
	
	private String convertAccessToTitle(AccessControl accessControl) {
		switch (accessControl.getObjectType()) {
		case AccessControl.OBJECTTYPE_METASUBJECTCONTENTADMIN:
			return "元学科内容管理员";
		case AccessControl.OBJECTTYPE_SUBJECTCONTENTADMIN:
			return "学科内容管理员";
		case AccessControl.OBJECTTYPE_SUBJECTSYSTEMADMIN:
			return "学科系统管理员";
		case AccessControl.OBJECTTYPE_SUBJECTUSERADMIN:
			return "学科用户管理员";
		case AccessControl.OBJECTTYPE_SUPERADMIN:
			return "超级管理员";
		case AccessControl.OBJECTTYPE_SYSTEMCONTENTADMIN:
			return "系统内容管理员";
		case AccessControl.OBJECTTYPE_SYSTEMUSERADMIN:
			return "系统用户管理员";
		case AccessControl.OBJECTTYPE_UNITCONTENTADMIN:
			return "机构内容管理员";
		case AccessControl.OBJECTTYPE_UNITSYSTEMADMIN:
			return "机构系统管理员";
		case AccessControl.OBJECTTYPE_UNITUSERADMIN:
			return "机构用户管理员";
		case AccessControl.OBJECTTYPE_CHANNELSYSTEMADMIN:
			return "频道系统管理员";
		case AccessControl.OBJECTTYPE_CHANNELUSERADMIN:
			return "频道用户管理员";
		case AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN:
			return "频道内容管理员";
		case AccessControl.OBJECTTYPE_CUSTORMCOLUMNADMIN:
			return "特定栏目管理员";
		default:
			return "未定义的管理权限";
		}
	}

}
