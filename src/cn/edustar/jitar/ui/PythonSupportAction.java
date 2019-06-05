package cn.edustar.jitar.ui;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.action.AbstractBasePageAction;
import cn.edustar.jitar.action.AbstractServletAction;
import cn.edustar.jitar.service.ScriptManager;

/**
 * 支持执行 python 脚本的 action.
 * 
 *
 */
public class PythonSupportAction extends AbstractBasePageAction implements ServletContextAware {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1716101368318595341L; 	
	/** 脚本服务 */
	private ScriptManager script_mgr;
	
	/** 脚本路径, 缺省 = null 表示不执行脚本 */
	private String scriptPath;

	/** Web 应用系统环境 */
	private ServletContext servlet_ctxt;
	
	/**
	 * 构造.
	 */
	public PythonSupportAction() {
		this.script_mgr = JitarContext.getCurrentJitarContext().getScriptManager();
	}
	
	/** 脚本服务 */
	public void setScriptManager(ScriptManager script_mgr) {
		this.script_mgr = script_mgr;
	}
	
	/** 脚本路径, 缺省 = null 表示不执行脚本 */
	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}
	
	/** Web 应用系统环境 */
	public void setServletContext(ServletContext val) {
		this.servlet_ctxt = val;
	}

	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute(String cmd) throws Exception {
		if (scriptPath == null || scriptPath.length() == 0) {
			return SUCCESS;
		} else {
			// 得到脚本.
			String path = servlet_ctxt.getRealPath(scriptPath);
			ActionHandler script = script_mgr.getScript(path);
			// 执行此脚本.
			String result = script.execute(request, response);
			
			// 将返回值作为 execute 的返回值返回.
			return result;
		}
	}
}
