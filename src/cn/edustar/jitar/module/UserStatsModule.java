package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.pojos.User;

/**
 * 统计信息(用户) 模块.
 * 
 * AJAX URL:  ?
 *   通用用户 AJAX URL: /manage/user/ajax?user=${userid}&module=${mod_name}
 * 产生的数据格式:  ?
 *
 *
 */
public class UserStatsModule extends AbstractModuleWithTP {
	/** 模块名字 */
	public static final String MODULE_NAME = "user_stats";
	
	/**
	 * 构造.
	 */
	public UserStatsModule() {
		super("user_stats", "工作室统计");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 计算各个统计信息，包括:
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);
		
		// 1. 积分  user.getUserScore();
		// 2. 总浏览量 TODO: 现在没有字段放这个.
		// 3. 资源数  TODO: 现在没有字段放这个.
		// 4. 文章数  user.getArticleCount();
		// 5. 站内订阅数  无此统计 
		// 6. 评论数  user.getCommentCount()
		// 7. 留言数  ?? user.getMessageCount()
		
		genResponseString(user, response);
	}
	
	// 使用模板产生用户统计信息.
	private void genResponseString(User user, ModuleResponse response) 
			throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		String template_name = "/WEB-INF/user/default/user_stats.ftl";
		processTemplate(root_map, response.getOut(), template_name);
	}
}
