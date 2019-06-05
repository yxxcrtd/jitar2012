package cn.edustar.jitar.servlet.handler;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;

import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.TemplateProcessor;

/**
 * 提供群组 rss 信息.
 *
 *
 */
public class GroupRssBean extends ServletBeanBase {
	/** 要访问的群组名字 */
	private String groupName;
	
	/** 获取文章类型 0 - new, 1 - hot, 2 - best */
	private int articleType;
	
	/** 群组服务. */
	private GroupService group_svc;
	
	/** 模板合成器 */
	private TemplateProcessor t_proc;

	/** 群组服务. */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}

	/** 模板合成器 */
	public void setTemplateProcessor(TemplateProcessor t_proc) {
		this.t_proc = t_proc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.servlet.handler.ServletBeanBase#handleRequst()
	 */
	@Override
	protected void handleRequst() throws IOException, ServletException {
		// 解析请求 PathInfo.
		if (parsePathInfo() == false) {
			not_found();
			return;
		}
		
		// 得到群组.
		Group group = group_svc.getGroupMayCached(groupName);
		if (group == null) {
			not_found();
			return;
		}
		
		// 得到群组文章.
		Object article_list = null;
		if (this.articleType == 0) {
			article_list = group_svc.getNewGroupArticleList(group.getGroupId(), 8);
		} else if (this.articleType == 1) {
			article_list = group_svc.getHotGroupArticleList(group.getGroupId(), 8);
		} else { // MUTS this.articleType == 2
			article_list = group_svc.getBestGroupArticleList(group.getGroupId(), 8);
		}
		
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group", group);
		root_map.put("article_list", article_list);

		// 使用位于 WEB-INF/group/default 下面的 group_rss.ftl 模板.
		String template_name = "/WEB-INF/group/default/group_rss.ftl";

		response.setContentType("application/xml; charset=utf-8");
		processTemplate(t_proc, root_map, template_name);
	}
	
	private boolean parsePathInfo() {
		// '/$groupName/rss/article.xml'
		String path_info = request.getPathInfo();
		// ['', '$groupName', 'rss', 'article.xml']
		String[] parts = path_info.split("/"); 
		if (parts.length != 4) return false;
		if ("rss".equals(parts[2]) == false) return false;
	
		this.groupName = parts[1];
		if (this.groupName == null || this.groupName.length() == 0)
			return false;
		
		if ("article.xml".equals(parts[3]))
			this.articleType = 0;
		else if ("hot_article.xml".equals(parts[3]))
			this.articleType = 1;
		else if ("best_article.xml".equals(parts[3]))
			this.articleType = 2;
		else
			return false;
		
		return true;
	}
}
