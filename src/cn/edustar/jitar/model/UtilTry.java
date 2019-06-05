package cn.edustar.jitar.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import org.springframework.web.context.ServletContextAware;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ParamUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 此类实现与 UtilModel相同的功能，
 * @author 孟宪会
 *
 */
public class UtilTry implements ServletContextAware, TemplateHashModelEx,
		TemplateModelObject {

	/** WEB 应用程序环境对象。 */
	private ServletContext servlet_ctxt;
	
	/** 所有方法的集合 */
	private Map<String, Object> methods = new HashMap<String, Object>();

	/**
	 * 用户服务，可以添加其他的服务，无需在配置文件中进行定义
	 */
	private UserService userService;

	/**
	 * 构造.
	 */
	public UtilTry() {
		// 目前暂时提供这2种方法
		methods.put("userById", new UserById());
		methods.put("url", new Url());
	}

	/**
	 * ServletContext 环境
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servlet_ctxt = servletContext;
	}

	/**
	 * 设置新的方法, 实质是在原基础上添加.
	 * 
	 * @param m
	 */
	@SuppressWarnings("unchecked")
	public void setMethods(Map m) {
		methods.putAll(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.model.TemplateModelObject#getVariableName()
	 */
	public String getVariableName() {
		return "Util";
	}

	/**
	 * 得到指定名字的函数、属性等.
	 * 
	 * @param name
	 * @return
	 * @throws TemplateModelException
	 */
	public TemplateModel get(String name) throws TemplateModelException {
		Object val = this.methods.get(name);
		if (val == null)
			return TemplateModel.NOTHING;
		if (val instanceof TemplateModel)
			return (TemplateModel) val;
		return Environment.getCurrentEnvironment().getObjectWrapper().wrap(val);
	}

	/**
	 * 判定集合是否非空.
	 * 
	 * @return
	 * @throws TemplateModelException
	 */
	public boolean isEmpty() throws TemplateModelException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see freemarker.template.TemplateHashModelEx#keys()
	 */
	public TemplateCollectionModel keys() throws TemplateModelException {
		return (TemplateCollectionModel) Environment.getCurrentEnvironment()
				.getObjectWrapper().wrap(this.methods.keySet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see freemarker.template.TemplateHashModelEx#size()
	 */
	public int size() throws TemplateModelException {
		return this.methods.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see freemarker.template.TemplateHashModelEx#values()
	 */
	public TemplateCollectionModel values() throws TemplateModelException {
		throw new TemplateModelException("Unsupport values() method.");
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 根据用户标识得到用户对象的函数方法
	 */
	public class UserById implements TemplateMethodModel {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数
			if (args == null || args.size() == 0)
				throw new TemplateModelException("userById 需要 1 个整数型参数");
			String idstr = args.get(0).toString();
			if (ParamUtil.isInteger(idstr) == false)
				throw new TemplateModelException("userById 需要 1 个整数型参数");
			// 得到用户标识参数
			int id = Integer.parseInt(idstr);
			User u = userService.getUserById(id);
			return u == null ? NOTHING : u;
		}
	}

	public class Url implements TemplateMethodModel {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() == 0)
				throw new TemplateModelException("url() 方法需要 1 个字符串参数");
			String href = args.get(0).toString();
			if (href == null || href.length() == 0)
				return "";

			// 如果恰好以 '/Groups/' 开头则认为是绝对地址.
			String ctxt_path = servlet_ctxt.getContextPath();
			if (ctxt_path.length() > 1 && href.startsWith(ctxt_path + "/"))
				return href;
			return CommonUtil.calcAbsUrl(href);
		}
	}
}
