package cn.edustar.jitar.model;

import javax.servlet.ServletContext;

import org.jasig.cas.client.util.CasConst;
import org.jasig.cas.client.util.JitarConst;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

//import cn.edustar.jitar.JitarConst;

import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import org.jasig.cas.client.util.CasConst;

/**
 * 统一用户地址存取, 在模板中写法为 ${UserMgrClientUrl}, 程序中为 UserMgrModel.getUserMgrClientUrl()
 * 头疼的问题是：服务器地址有内网和外网之分，服务器可能不允许访问自己的外网。
 * 所以统一用户地址设置为2类地址，内网和外网。
 * 此处是客户端使用的地址
 * 对应服务器端用户使用的地址，见 UserMgrModel
 * @author baimindong
 */
public class UserMgrClientModel implements TemplateScalarModel, TemplateModelObject {
	
	/** 统一用户地址 */
	private static String userMgrClientUrl = "";

	/**
	 * 获得统一用户配置的地址
	 * 
	 * @return
	 */
	public static String getUserMgrClientUrl() {
		/*
		 * 此地址的使用策略是：
		 * 如果配置了，就使用配置，否则，就自动进行计算。
		 */
		if(userMgrClientUrl.length() != 0) return userMgrClientUrl;
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		ServletContext sc = wac.getServletContext();
		String _url=null;
		if(sc.getFilterRegistration("CAS-Authentication-Filter")!=null){
			_url = sc.getFilterRegistration("CAS-Authentication-Filter").getInitParameter("casServerLoginUrl");
		}
		if(_url==null || _url.length()==0){
			/* TODO：以下代码有问题，依赖 Request 对象的做法是不正确的 */
			//
			_url = "/" + JitarConst.USERMGR_SITENAME;
			//_url=CasConst.getInstance().getCasServerLoginUrl();
		}
		if(_url.endsWith("/login/")){
			_url=_url.substring(0, _url.length()-7);
		}
		else if(_url.endsWith("/login")){
			_url=_url.substring(0, _url.length()-6);
		}
		if(_url == null || _url.length() == 0){
			_url = "/";
		}		
		if(_url.endsWith("/") == false){
			_url += "/";
		}
		return _url;
	}

	/**
	 * 得到地址
	 * 
	 * @return
	 */
	public String getClientUrl() {
		return getUserMgrClientUrl();
	}

	/**
	 * 从 spring 中注入进来的地址
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		userMgrClientUrl = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see freemarker.template.TemplateScalarModel#getAsString()
	 */
	public String getAsString() throws TemplateModelException {
		return getUserMgrClientUrl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.model.TemplateModelObject#getVariableName()
	 */
	public String getVariableName() {
		return "UserMgrClientUrl";
	}
	
}
