package cn.edustar.jitar.servlet.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.service.PageKey;

/**
 * 显示一个用户的完整档案的页面.
 *
 *
 */
public class UserProfileBean extends BaseShowUser {
	/** 用户标识 */
	private String userLoginName;
	
	/** 用户对象 */
	private User user_model;
	
	/** 页面对象 */
	private Page entry_page;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ServletBeanBase#handleRequst()
	 */
	@Override
	protected void handleRequst() throws IOException, ServletException {
		// 解析请求 uri, 得到要访问的用户名.
		if (parseUri() == false) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		// 获得用户.
		this.user_model = getUserService().getUserByLoginName(this.userLoginName);
		if (this.user_model == null) {
			// 用户不存在.
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		// 得到 page. 当前先实现使用系统缺省的.
		this.entry_page = getSystemPageWithUserSkin(PageKey.SYSTEM_USER_PROFILE, user_model._getUserObject());
		if (this.entry_page == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "System entry page unexist");
			return;
		}
		
		// 得到该页面所有 widget.
		List<Widget> webpart_list = getPageService().getPageWidgets(this.entry_page.getPageId());
		
		// 合成模板.
		Map<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user_model);
		root_map.put("page", this.entry_page);
		root_map.put("widgets", webpart_list);
		
		// 使用位于 WEB-INF 下面的用户缺省首页模板.
		String template_name = "/WEB-INF/user/default/full_profile_page.ftl";

		response.setContentType("text/html; charset=UTF-8");
		processTemplate(getTemplateProcessor(), root_map, template_name);
	}
	
	// 解析请求 uri, 从里面得到用户标识.
	// 从 BlogServlet 中调用的时候，path_info = '/$userName/profile';
	private boolean parseUri() {
		String path_info = request.getPathInfo();	// '/$username/profile'
		if (path_info == null) return false;
		if (path_info.startsWith("/"))
			path_info = path_info.substring(1);		// '$username/profile'
		
		String[] o = path_info.split("/");		// [$username, profile]
		if (o.length < 2) return false;
		this.userLoginName = o[0];
		if ("profile".equals(o[1]) == false)
			return false;
		
		return true;
	}
}
