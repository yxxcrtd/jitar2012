package cn.edustar.jitar.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edustar.jitar.JitarContext;

/**
 * 做为其它教研系统标签的基类, 提供通用功能.
 *
 *
 */
public abstract class AbstractBodyTag extends BodyTagSupport {
	/**
	 * 得到当前教研环境对象.
	 * @return
	 */
	public static JitarContext getJitarContext() {
		return JitarContext.getCurrentJitarContext();
	}
	
	/**
	 * 得到当前 request/pageContext 的 sprint context.
	 * @return
	 */
	public ApplicationContext getSprintContext() {
		WebApplicationContext app_ctxt = WebApplicationContextUtils
			.getWebApplicationContext(pageContext.getServletContext());
		return app_ctxt;
	}
}
