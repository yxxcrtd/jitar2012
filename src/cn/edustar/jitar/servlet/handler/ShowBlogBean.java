package cn.edustar.jitar.servlet.handler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.service.OnlineManager;
import cn.edustar.jitar.service.PageKey;
import cn.edustar.jitar.service.StatService;

/**
 * 当用户访问博客站点时候的处理 bean, 从 BlogServlet 中调用.
 *  
 *   URL: '/u/$username'
 *   PathInfo: '/$username'
 * 
 *
 * @deprecated 被 '/WEB-INF/py/show_user_index.py' 取代
 */
public class ShowBlogBean extends BaseShowUser {
	/** 文章记录器. */
	private static final Log log = LogFactory.getLog(ShowBlogBean.class);

	/** 在线信息记录 */
	private OnlineManager online_mgr;

	/** 统计写入服务 */
	private StatService stat_svc;
	
	/** 在线信息记录 */
	public void setOnlineManager(OnlineManager online_mgr) {
		this.online_mgr = online_mgr;
	}

	/** 统计写入服务 */
	public void setStatService(StatService stat_writer) {
		this.stat_svc = stat_writer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ServletBeanBase#handleRequst()
	 */
	@Override
	protected void handleRequst() throws IOException, ServletException {
		boolean log_debug = log.isDebugEnabled();

		// 得到访客.
		User visitor = getLoginUser();
		
		// 得到要访问的博客主人对象.
		String user_name = getBlogOwner();
		User blog_user = getUserService().getUserByLoginName(user_name);
		if (log_debug) {
			log.debug("博客主人 = " + blog_user);
		}
		if (blog_user == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// 得到用户的首页.
		PageKey user_index_pk = new PageKey(
				ObjectType.OBJECT_TYPE_USER, blog_user.getUserId(), "index");
		Page page = getPageService().getPageByKey(user_index_pk);
		if (log_debug) {
			log.debug("page = " + page);
		}
		if (page == null) {
			// 如果不存在, 则现在立刻复制一份.
			page = duplicatePage(blog_user, user_index_pk);
			if (page == null) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "PageModel unexist");
				return;
			}
		}

		// 得到该页面的所有内容块.
		List<Widget> widget_list = getPageService().getPageWidgets(page.getPageId());

		// 合成模板.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", blog_user);
		root_map.put("page", page);
		root_map.put("widgets", widget_list);
		root_map.put("visitor", visitor);

		// 使用位于 WEB-INF 下面的用户缺省首页模板.
		String template_name = "/WEB-INF/user/default/index.ftl";

		response.setContentType("text/html; charset=utf-8");
		processTemplate(getTemplateProcessor(), root_map, template_name);
		
		// 增加访问计数.
		incUserBlogStat(blog_user._getUserObject());
		
		// 设置用户活动信息.
		if (visitor != null && online_mgr != null) {
			online_mgr.userActive(visitor.getLoginName(), new Date(), 
					"访问 " + blog_user.getBlogName());
		}
	}
	
	private Page duplicatePage(User blog_user, PageKey user_index_pk) {
		PageKey src_pk = PageKey.SYSTEM_USER_INDEX;
		String title = blog_user.getBlogName();
		if (title == null || title.length() == 0) 
			title = blog_user.getNickName() + " 的工作室";
		getPageService().duplicatePage(src_pk, user_index_pk, title);
		return getPageService().getPageByKey(user_index_pk);
	}
	
	// 增加访问计数.
	private void incUserBlogStat(User user) {
		stat_svc.incUserVisitCount(user);
		
		// 这里更新也许有并发问题, 但不严重, 因为隔一段时间会重新加载.
		user.setVisitCount(user.getVisitCount() + 1);
	}

	/**
	 * 从浏览器的地址栏参数中获得要访问的用户名字。比如：http://localhost/Groups/u/yang，返回 yang
	 * 
	 * @return
	 */
	private String getBlogOwner() {
		String path_info = request.getPathInfo();
		String bias = "/";
		if (path_info.startsWith(bias))
			path_info = path_info.substring(1);
		return path_info;
	}
}
