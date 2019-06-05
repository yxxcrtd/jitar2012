package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.UserService;

/**
 * 我的聚合圈，自动把自己标签相同的个人网站显示出来. *
 *
 */
public class UserTagsFeedModule extends AbstractModuleWithTP {
	private static final Log logger = LogFactory.getLog(UserTagsFeedModule.class);
	
	/** 标签服务 */
	private TagService tag_svc;
	
	/** 用户服务。 */
	private UserService user_svc;
	
	/**
	 * 构造.
	 */
	public UserTagsFeedModule() {
		super("utags_feed", "我的聚合圈");
	}
	
	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}
	
	/** 用户服务。 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		boolean log_debug = logger.isDebugEnabled();
		if (log_debug)
			logger.debug("handleRequest req = " + request + ", resp = " + response);
		
		// 得到用户.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);
		if (log_debug)
			logger.debug("  user = " + user);
		
		// 得到此用户的所有标签 (也即该用户的个人博客站点的标签)
		List<Tag> my_tags = tag_svc.getRefTagList(user.getUserId(), ObjectType.OBJECT_TYPE_USER);
		if (log_debug) 
			logger.debug("  my_tags = " + my_tags);
		if (my_tags.size() == 0) 
			return;		// TODO: 显示当前您没有设置标签.		
		// 得到具有这些标签的博客站点.		List<Integer> user_ids = tag_svc.getObjectListByTags(getTagIds(my_tags), ObjectType.OBJECT_TYPE_USER);
		if (log_debug)
			logger.debug("  user_ids = " + user_ids);
		// user_ids.remove(new Integer(user.getId()));		// 排除掉自己.		
		// 得到找到的这些用户的信息，合并在线与否信息.		List<User> user_list = user_svc.getUserByIds(user_ids);
		if (log_debug) 
			logger.debug("  user_list = " + user_list);
		
		// 生成 HTML 或 JSON.
		{
			String result = genHtmlOutput(user_list);
			response.getOut().write(result);
		}
	}
	
	// 测试用，生成一个简单的 HTML 来显示找到的用户.
	private static String genHtmlOutput(List<User> user_list) {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("<ul>");
		for (int i = 0; i < user_list.size(); ++i) {
			User user = user_list.get(i);
			strbuf.append("<li>");
			strbuf.append("User = ").append(user.getNickName())
				.append(", ico=").append(user.getUserIcon())
				.append(", intro=").append(user.getBlogIntroduce())
				.append(", online=").append("TODO:");
			strbuf.append("</li>");
		}
		strbuf.append("</ul>");
		return strbuf.toString();
	}
	
	private static final List<Integer> getTagIds(List<Tag> tags) {
		List<Integer> ids = new ArrayList<Integer>();
		if (tags == null || tags.size() == 0) 
			return ids;
		for (int i = 0; i < tags.size(); ++i)
			ids.add(tags.get(i).getTagId());
		return ids;
	}
}
