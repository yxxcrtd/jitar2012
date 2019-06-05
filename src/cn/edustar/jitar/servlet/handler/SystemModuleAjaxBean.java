package cn.edustar.jitar.servlet.handler;

import java.io.IOException;

import javax.servlet.ServletException;

import cn.edustar.jitar.module.Module;
import cn.edustar.jitar.module.ModuleRequestImpl;
import cn.edustar.jitar.module.ModuleResponseImpl;
import cn.edustar.jitar.service.ModuleContainer;

/**
 * 支持 '/s/module/$modname' ajax 请求的处理 bean.
 *
 *
 */
public class SystemModuleAjaxBean extends ServletBeanBase {
	/** 模块容器 */
	private ModuleContainer mod_cont;

	/** 模块容器 */
	public void setModuleContainer(ModuleContainer mod_cont) {
		this.mod_cont = mod_cont;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.servlet.handler.ServletBeanBase#handleRequst()
	 */
	@Override
	protected void handleRequst() throws IOException, ServletException {
		// 分解参数
		String path_info = request.getPathInfo();	// '/module/$modname'
		String[] parts = path_info.split("/");		// ['', 'module', '$modname']
		if (parts.length != 3 || "module".equals(parts[1]) == false) {
			response.sendError(SC_NOT_FOUND);
			return;
		}
		
		// 获得模块
		String mod_name = parts[2];
		Module mod = mod_cont.getModule(mod_name);
		if (mod == null) {
			response.sendError(SC_NOT_FOUND);
			return;
		}

		processModule(mod);
	}

	/** 处理请求, 系统模块没有特别绑定的数据. */
	private void processModule(Module module) throws IOException {
		// 构造 req, resp
		ModuleRequestImpl req = new ModuleRequestImpl("GET", request.getParameterMap());
		ModuleResponseImpl resp = new ModuleResponseImpl(response);

		// 向 module 发出请求
		module.handleRequest(req, resp);
	}
}
