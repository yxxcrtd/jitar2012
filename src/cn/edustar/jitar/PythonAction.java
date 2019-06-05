package cn.edustar.jitar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.python.core.Py;
import org.python.core.PyObject;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.PythonActionHandler;
import cn.edustar.jitar.ui.GeneralAction;
import cn.edustar.jitar.util.ParamUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 提供作为 .py 的基类
 * 
 *
 */
@SuppressWarnings("deprecation")
public class PythonAction extends GeneralAction implements PythonActionHandler {

	/** serialVersionUID */
	private static final long serialVersionUID = -2700796216774510596L;

	/** 日志 */
	private static final Log log = LogFactory.getLog(PythonAction.class);

	/** 请求对象 */
	public HttpServletRequest request;

	/** 响应对象 */
	public HttpServletResponse response;

	/** 当前页面访问者 */
	private User visitor;

	/** Python 对象 */
	public PyObject pyObj;

	/**
	 * 建立必要环境以执行指定请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public final String processRequest(HttpServletRequest request, HttpServletResponse response, PyObject pyObj) {
		// 建立执行环境
		this.request = request;
		this.response = response;
		this.pyObj = pyObj;
		super.param = new ParamUtil(request.getParameterMap());
		this.injectPythonObject(pyObj);
		String result;
		result = beforeExecute();
		if (result != null)
			return result;

		// 执行 python 脚本
		try {
			result = execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.prepareData();

		afterExecute();

		// 返回
		return result;
	}

	/**
	 * 我们能够通过此方法将属性 '注入' 到 python 对象中
	 * 
	 * @param pyObj
	 */
	private void injectPythonObject(PyObject pyObj) {
		PyObject value = Py.java2py("Hello python 带中文字符");
		String name = "myprop".intern();
		pyObj.__setattr__(name, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.AbstractPageAction#setData(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setData(String name, Object value) {
		request.setAttribute(name, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.ui.GeneralAction#beforeExecute()
	 */
	@Override
	protected String beforeExecute() {
		String result = super.beforeExecute();
		if (result != null)
			return result;
		prepareVisitor();
		return null;
	}

	/**
	 * 准备页面访问者的信息
	 */
	private void prepareVisitor() {
		this.visitor = getLoginUser();
		if (this.visitor != null)
			super.setData("visitor", visitor);
	}

	/**
	 * 得到当前登录用户, 登录用户在 统一用户认证 Filter 里面被设置
	 * 
	 * @return 如果没有则返回 null
	 */
	public User getLoginUser() {
		if (getSession() == null)
			return null;
		return (User) getSession().getAttribute(User.SESSION_LOGIN_USERMODEL_KEY);
	}

	/**
	 * 获取参数对象
	 * 
	 * @return
	 */
	public ParamUtil getParam() {
		return this.param;
	}

	/**
	 * 得到请求对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * 得到响应对象
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return this.response;
	}

	/**
	 * 得到当前 Session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return this.request.getSession();
	}

	/**
	 * 得到当前 Web 环境对象
	 * 
	 * @return
	 */
	public ServletContext getApplication() {
		return getJitar().getServletContext();
	}

	/**
	 * 得到当前教研环境对象
	 * 
	 * @return
	 */
	public JitarContext getJitar() {
		return JitarContext.getCurrentJitarContext();
	}

	/**
	 * 得到页面访问者
	 * 
	 * @return
	 */
	public User getVisitor() {
		return this.visitor;
	}

	/**
	 * 提供给 python 重载的方法
	 * 
	 * @return
	 */
	public String execute(String cmd) {
		return ActionSupport.NONE;
	}

	/**
	 * 输出调试字符串
	 * 
	 * @param msg
	 */
	public void debug(String msg) {
		log.debug(msg);
	}

	/**
	 * 输出信息调试字符串
	 * 
	 * @param msg
	 */
	public void info(String msg) {
		log.info(msg);
	}

	/**
	 * 输出错误信息调试字符串
	 * 
	 * @param msg
	 */
	public void error(String msg) {
		log.error(msg);
	}

	/**
	 * 获得日志对象
	 * 
	 * @return
	 */
	public Log getLogger() {
		return log;
	}

	/**
	 * 
	 *
	 * @param pystr
	 * @return
	 */
	public String getstr(String pystr) {
		String t = pystr;
		return t;
	}
	
}
