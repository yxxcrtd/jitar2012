package cn.edustar.jitar.context.impl;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

import cn.edustar.jitar.context.UserContext;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.MeetingsService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.UserService;

/**
 * 系统环境对象的实现
   * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-25 13:05:27
 */
public class UserContextImpl extends UserContext implements ServletContextAware, ApplicationContextAware {
	private ServletContext servletContext;
	private ApplicationContext applicationContext;
	private UserService userService;
	private GroupService groupService;
	private PrepareCourseService prepareCourseService;
	private MeetingsService meetingsService;
	private UserContext userContext;
	private Map<String, Object> attribute = new Hashtable<String, Object>();

	public UserContextImpl() {
	}

	public void init() {
		servletContext.setAttribute(USER_CONTEXT_KEY_NAME, this);
	}

	public void destroy() {
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public ServletContext getServletContext() {
		return this.servletContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public UserService getUserService() {
		if (null == userService) {
			this.userService = (UserService) this.applicationContext.getBean("userService");
		}
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public GroupService getGroupService() {
		if (null == groupService) {
			this.groupService = (GroupService) this.applicationContext.getBean("groupService");
		}
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public PrepareCourseService getPrepareCourseService() {
		if (null == prepareCourseService) {
			this.prepareCourseService = (PrepareCourseService) this.applicationContext.getBean("prepareCourseService");
		}
		return prepareCourseService;
	}

	public void setPrepareCourseService(PrepareCourseService prepareCourseService) {
		this.prepareCourseService = prepareCourseService;
	}

	public MeetingsService getMeetingsService() {
		if (null == meetingsService) {
			this.meetingsService = (MeetingsService) this.applicationContext.getBean("meetingsService");
		}
		return this.meetingsService;
	}

	public void setMeetingsService(MeetingsService meetingsService) {
		this.meetingsService = meetingsService;
	}

	public UserContext getUserContext() {
		if (null == userContext) {
			this.userContext = (UserContext) this.applicationContext.getBean("userContext");
		}
		return this.userContext;
	}

	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	public Object getAttribute(String key) {
		return this.attribute.get(key);
	}

	public void setAttribute(String key, Object value) {
		this.attribute.put(key, value);
	}

}
