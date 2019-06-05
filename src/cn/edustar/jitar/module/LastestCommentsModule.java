package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CommentService;

/**
 * 对用户文章最新回复列表模块(用户模块).
 * 
 *
 */
public class LastestCommentsModule extends AbstractModuleWithTP {
	/** 评论服务 */
	private CommentService cmt_svc;
	
	/**
	 * 构造.
	 */
	public LastestCommentsModule() {
		super("lastest_comments", "最新评论");
	}

	/** 评论服务 */
	public void setCommentService(CommentService cmt_svc) {
		this.cmt_svc = cmt_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到用户.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);

		// 得到最新回复.		List<Comment> cmt_list = cmt_svc.getRecentCommentAboutUser(user.getUserId(), 5);
		
		// TODO: 根据 comment.objType, objId 得到被评论的对象, 合成数据.
		
		// 显示这些评论.
		outputHtml(cmt_list, response);
	}
	
	/** 显示 HTML 格式的评论内容 */
	private void outputHtml(List<Comment> comment_list, ModuleResponse response) throws IOException {
		response.setContentType(Module.TEXT_HTML_UTF_8);
		
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("comment_list", comment_list);

		String template_name = "/WEB-INF/user/default/lastest_comments.ftl";
		String content = this.getTemplateProcessor().processStringTemplate(root_map, template_name);
		response.getOut().write( "xxxxx");
		return;
		//processTemplate(root_map, response.getOut(), template_name);
	}
}
