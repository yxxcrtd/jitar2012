package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.LeavewordQueryParam;
import cn.edustar.jitar.service.LeavewordService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 留言模块的实现。使用的模板: /WEB-INF/user/default/user_leaveword.html
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 27, 2008 10:51:25 PM
 */
public class UserLeavewordModule extends AbstractModuleWithTP {
	/** 留言服务 */
	private LeavewordService lw_svc;

	public UserLeavewordModule() {
		super("user_leaveword", "我的留言");
	}
	
	/** 留言服务的set方法 */
	public void setLeavewordService(LeavewordService leavewordService) {
		this.lw_svc = leavewordService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest,
	 *      cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到当前用户.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);

		// 得到QueryString参数.
		ParamUtil param_util = new ParamUtil(request.getParameters());
		int count = param_util.safeGetIntParam("count");
		if (count <= 0) count = 6;
		
		// 构造查询参数.
		LeavewordQueryParam param = new LeavewordQueryParam();
		param.count = count;		// 取最新 count 条.
		param.objType = ObjectType.OBJECT_TYPE_USER.getTypeId();
		param.objId = user.getUserId();
		Object leaveword_list = lw_svc.getLeaveWordList(param, null);

		// 合成模板.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("leaveword_list", leaveword_list);

		// 使用位于 WEB-INF 下面的用户模板.
		String template_name = "/WEB-INF/user/default/user_leaveword.ftl";
		response.setContentType("text/html; charset=UTF-8");
		processTemplate(root_map, response.getOut(), template_name);
	}

}