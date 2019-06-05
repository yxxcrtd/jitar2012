package cn.edustar.jitar.servlet.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import cn.edustar.jitar.module.Module;
import cn.edustar.jitar.module.ModuleRequestImpl;
import cn.edustar.jitar.module.ModuleResponseImpl;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.ModuleContainer;

/**
 * 从 GroupServlet 中调用，为群组 module 提供通用 ajax 请求处理.
 *   URL: 'http://www.domain.com/ctxt/g/$groupName/module/$modname
 *   PathInfo: '/$groupName/module/$modname'
 *   QueryString: '各模块自己指定'
 *
 *
 */
public class GroupModuleAjaxBean extends ServletBeanBase {
	/** 模块容器 */
	private ModuleContainer mod_cont;
	
	/** 群组服务 */
	private GroupService group_svc;
	
	/** 群组名 */
	private String group_name;
	
	/** 模块名 */
	private String mod_name;
	
	/** 访问的群组 */
	private Group group_model;
	
	/** 要访问的模块 */
	private Module module;
	
	/** 模块容器 */
	public void setModuleContainer(ModuleContainer mod_cont) {
		this.mod_cont = mod_cont;
	}

	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ServletBeanBase#handleRequst()
	 */
	@Override
	protected void handleRequst() throws IOException, ServletException {
		// 解开 uri
		if (!parseUri()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		// 得到模块
		this.module = mod_cont.getModule(this.mod_name);
		if (this.module == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Module Not Found");
			return;
		}
		
		// 得到群组
		this.group_model = group_svc.getGroupMayCached(this.group_name);
		if (this.group_model == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Group Not Found");
			return;
		}
		
		// 使用 module 执行请求
		processModule();
	}

	private void processModule() throws IOException {
		// 构造 req, resp
		ModuleRequestImpl req = new ModuleRequestImpl("GET", request.getParameterMap());
		req.setAttribute(ModuleRequestImpl.GROUP_MODEL_KEY, group_model);
		ModuleResponseImpl resp = new ModuleResponseImpl(response);

		// 向 module 发出请求
		module.handleRequest(req, resp);
	}

	/**
	 * 解析请求进入的 uri 地址, uri 地址必须是 '/$groupName/module/$modname' (不含 context path)
	 * @return
	 */
	private boolean parseUri() {
		String path_info = request.getPathInfo();
		String[] parts = path_info.split("/");	
		// 必须含有4个部分 ['', '$groupname', 'module', '$modname']
		if (parts.length != 4) return false;
		if (parts[1].length() == 0) return false;		// '$groupname'
		if (!"module".equals(parts[2])) return false;	// 'module'
		if (parts[3].length() == 0) return false;		// '$modname'
		
		this.group_name = parts[1];
		this.mod_name = parts[3];
		
		return true;
	}
}
