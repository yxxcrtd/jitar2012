package cn.edustar.jitar.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.python.core.PyObject;

/**
 * python 脚本要支持的接口
 *
 *
 */
public interface PythonActionHandler {
	
	/**
	 * 建立必要环境以执行指定请求.
	 * @param request
	 * @param response
	 * @return
	 */
	public String processRequest(HttpServletRequest request, HttpServletResponse response, PyObject pyObj);
	
}
