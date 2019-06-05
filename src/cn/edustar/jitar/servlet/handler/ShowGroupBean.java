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
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.OnlineManager;
import cn.edustar.jitar.service.PageKey;
import cn.edustar.jitar.service.PageService;
import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.service.TemplateProcessor;

/**
 * 当用户访问群组时候的处理 bean.
 *   URL:   'http://www.domain.com/groups/g/groupname?param' (缺点: 必须要给出群组名)
 *   URL:   'http://www.domain.com/groups/g/$groupid?param' (缺点: 数字看着不清晰)
 *   
 *
 * @deprecated 已经被 /WEB-INF/py/show_group.py 取代了.
 */
public class ShowGroupBean extends ServletBeanBase {
	/** 日志 */
	private static final Log log = LogFactory.getLog(ShowGroupBean.class);
	
	/** 群组服务 */
	private GroupService group_svc;
	
	/** 页面服务 */
	private PageService page_svc;
	
	/** 在线信息记录 */
	private OnlineManager online_mgr;

	/** 统计写入服务 */
	private StatService stat_svc;

	/** 模板合成器 */
	private TemplateProcessor t_proc;

	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	/** 页面服务 */
	public void setPageService(PageService page_svc) {
		this.page_svc = page_svc;
	}
	
	/** 在线信息记录 */
	public void setOnlineManager(OnlineManager online_mgr) {
		this.online_mgr = online_mgr;
	}

	/** 统计写入服务 */
	public void setStatService(StatService stat_writer) {
		this.stat_svc = stat_writer;
	}

	/** 模板合成器 */
	public void setTemplateProcessor(TemplateProcessor t_proc) {
		this.t_proc = t_proc;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ServletBeanBase#handleRequst()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void handleRequst() throws IOException, ServletException {
		boolean log_debug = log.isDebugEnabled();
		
		// 得到访客
		User visitor = getLoginUser();
		
		// 得到要访问的群组对象
		String group_name = getGroupName();
		Group group_model = group_svc.getGroupMayCached(group_name);
		if (log_debug) {
			log.debug("group_model  = " + group_model);
		}
		if (group_model == null) { 
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Group Not Found");
			return;
		}
		
		// 计算该访客在该协作组的角色.
		String visitor_role = "guest";
		if (visitor != null) {
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(group_model.getGroupId(), visitor.getUserId());
			if (gm != null && gm.getStatus() == GroupMember.STATUS_NORMAL && gm.getGroupRole() >= GroupMember.GROUP_ROLE_VICE_MANAGER)
				visitor_role = "admin";
		}
		
		// 得到群组的首页
		PageKey index_pk = new PageKey(ObjectType.OBJECT_TYPE_GROUP, group_model.getGroupId(), "index");
		Page page = page_svc.getPageByKey(index_pk);
		if (log_debug) {
			log.debug("page =  " + page);
		}
		if (page == null) {
			// 如果不存在，则现在立刻复制一份
			page = duplicatePage(group_model, index_pk);
			if (page == null) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "PageModel unexist");
				return;
			}
		}
		
		// 得到该页面的所有内容块
		List webpart_list = page_svc.getPageWidgets(page.getPageId());
	
		// 合成模板.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group", group_model);
		root_map.put("visitor_role", visitor_role);
		root_map.put("page", page);
		root_map.put("widget_list", webpart_list);
		
		// 使用位于 WEB-INF 下面 group 的缺省首页模板.
		String template_name = "/WEB-INF/group/default/index.ftl";
		
		processTemplate(t_proc, root_map, template_name);
		
		// 增加访问计数.
		incGroupVisitCount(group_model._getGroupObject());
		
		// 设置用户活动信息.
		if (visitor != null && online_mgr != null) {
			online_mgr.userActive(visitor.getLoginName(), new Date(), "访问群组 " + group_model.getGroupTitle());
		}
	}
	
	private Page duplicatePage(Group group, PageKey index_pk) {
		PageKey src_pk = PageKey.SYSTEM_GROUP_INDEX;
		String title = group.getGroupTitle() + "首页";
		page_svc.duplicatePage(src_pk, index_pk, title);
		return page_svc.getPageByKey(index_pk);
	}

	// 增加访问计数.
	private void incGroupVisitCount(Group group) {
		log.info("当前群组的访问次数：" + group.getVisitCount());
		stat_svc.incGroupVisitCount(group);
		
		// 这里更新也许有并发问题, 但不严重, 因为隔一段时间会重新加载.
		group.setVisitCount(group.getVisitCount() + 1);
	}

	/**
	 * 从参数中获得要访问的群组名字
	 * @return
	 */
	private String getGroupName() {
		String path_info = request.getPathInfo();
		if (path_info.startsWith("/"))
			path_info = path_info.substring(1);
		return path_info;
	}
}
