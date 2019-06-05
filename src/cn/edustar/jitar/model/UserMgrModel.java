package cn.edustar.jitar.model;

import javax.servlet.ServletContext;

import org.jasig.cas.client.util.JitarConst;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

/**
 * 统一用户地址存取, 在模板中写法为 ${UserMgrUrl}, 程序中为 UserMgrModel.getUserMgrUrl()
 */
public class UserMgrModel implements TemplateScalarModel, TemplateModelObject {
	
	/** 统一用户地址 */
	private static String userMgrUrl = "";

	/**
	 * 获得统一用户配置的地址
	 * 
	 * @return
	 */
	public static String getUserMgrUrl() {
		if(userMgrUrl==null || userMgrUrl.length()==0){
			userMgrUrl = "/" + JitarConst.USERMGR_SITENAME;
			//userMgrUrl=CasConst.getInstance().getCasServerUrlPrefix();
		}
		return userMgrUrl;
	}

	/**
	 * 得到地址
	 * 
	 * @return
	 */
	public String getUrl() {
		if(userMgrUrl==null || userMgrUrl.length()==0){
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			ServletContext sc = wac.getServletContext();
			userMgrUrl  = sc.getContextPath() + "/" + JitarConst.USERMGR_SITENAME;
			
			//userMgrUrl=CasConst.getInstance().getCasServerUrlPrefix();
		}
		return userMgrUrl;
	}

	/**
	 * 从 spring 中注入进来的地址
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		userMgrUrl = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see freemarker.template.TemplateScalarModel#getAsString()
	 */
	public String getAsString() throws TemplateModelException {
		return getUserMgrUrl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.model.TemplateModelObject#getVariableName()
	 */
	public String getVariableName() {
		return "UserMgrUrl";
	}
	
}
