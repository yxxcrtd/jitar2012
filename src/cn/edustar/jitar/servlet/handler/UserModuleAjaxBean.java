package cn.edustar.jitar.servlet.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import cn.edustar.jitar.module.Module;
import cn.edustar.jitar.module.ModuleRequestImpl;
import cn.edustar.jitar.module.ModuleResponseImpl;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ModuleContainer;
import cn.edustar.jitar.service.UserService;

/**
 * 从 BlogServlet 中调用，为 module 提供通用 ajax 请求处理.
 *   URL: 'http://www.domain.com/ctxt/u/$username/module/$modname?$param'
 *   PathInfo: '/$username/module/$modname'
 *   QueryString: '$param'
 *
 *
 */
public class UserModuleAjaxBean extends ServletBeanBase {
	/** 模块容器 */
	private ModuleContainer mod_cont;
	
	/** 用户服务 */
	private UserService user_svc;
	
	/** URL 中用户登录名 */
	private String user_loginName;
	
	/** URL 模块名. */
	private String mod_name;
	
	private Module module;
	
	private User user_model;

	/** 模块容器 */
	public void setModuleContainer(ModuleContainer mod_cont) {
		this.mod_cont = mod_cont;
	}

	/** 用户服务 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ServletBeanBase#handleRequst()
	 */
	@Override
	protected void handleRequst() throws IOException, ServletException {
		// 得到要请求的 mod
		if (!parseUri()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Request URI");
			return;
		}
		
		this.module = mod_cont.getModule(mod_name);
		if (module == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Module Not Found");
			return;
		}
		
		// 得到用户参数, 如果没有给出参数则 user_model == null
		if (user_loginName != null && user_loginName.length() > 0) {
			this.user_model = user_svc.getUserByLoginName(user_loginName);
			if (this.user_model == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "User Not Found");
				return;
			}
		}
	
		// 使用 module 执行请求
		processModule();
	}
	
	private void processModule() throws IOException {
		// 构造 req, resp
		ModuleRequestImpl req = new ModuleRequestImpl("GET", request.getParameterMap());
		req.setAttribute(ModuleRequestImpl.USER_MODEL_KEY, user_model);
		ModuleResponseImpl resp = new ModuleResponseImpl(response);

		// 向 module 发出请求
		module.handleRequest(req, resp);
	}
	
	// 解析 path info 从中得到 user_loginName, mod_name
	private boolean parseUri() {
		String path_info = request.getPathInfo();	// '/$username/module/$modname'
		if (path_info == null) return false;
		if (path_info.startsWith("/"))
			path_info = path_info.substring(1);		// '$username/module/$modname'
		
		String[] parts = path_info.split("/");
		if (parts.length != 3) return false;
		this.user_loginName = parts[0];
		if (this.user_loginName == null || this.user_loginName.length() == 0)
			return false;
		if ("module".equals(parts[1]) == false)
			return false;
		this.mod_name = parts[2];
		if (this.mod_name == null || this.mod_name.length() == 0)
			return false;

		return true;
	}
}
