package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.PlacardQueryParam;
import cn.edustar.jitar.service.PlacardService;

/**
 * 我的公告(用户)模块.
 * 
 * 使用的模板： '/WEB-INF/user/default/user_placard.ftl'
 * 
 *
 *
 */
public class UserPlacardModule extends AbstractModuleWithTP {
	/** 模块名. */
	public static final String MODULE_NAME = "user_placard";
	
	/** 公告服务. */
	private PlacardService pla_svc;

	/**
	 * 构造.
	 */
	public UserPlacardModule() {
		super("user_placard", "工作室公告");
	}
	
	/** 公告服务. */
	public void setPlacardService(PlacardService pla_svc) {
		this.pla_svc = pla_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到用户.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);
		
		// 得到用户公告.
		// TODO: count read from parameter or get from config
		PlacardQueryParam param = new PlacardQueryParam();
		param.count = 1;		// 获取第一条.
		param.objType = ObjectType.OBJECT_TYPE_USER;
		param.objId = user.getUserId();
		List<Placard> placard_list = pla_svc.getPlacardList(param, null);
		
		// 组装输出为 XHTML.
		outputHtmlResult(user, placard_list, response);
	}
	
	// 产生 XHTML 结果，这要求 title, content 都是良好 html 格式
	private void outputHtmlResult(User user, List<Placard> placard_list,
			ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("placard_list", placard_list);
		
		String template_name = "/WEB-INF/user/default/user_placard.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
