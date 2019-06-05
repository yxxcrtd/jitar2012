package com.winupon.action;
import java.net.URLEncoder;

import net.zdsoft.passport.service.client.PassportClient;
import cn.edustar.jitar.action.ManageBaseAction;
import cn.edustar.jitar.model.SiteUrlModel;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.MD5;

public class RegisterAction  extends ManageBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1225228758208962735L;
	
	/** 用户服务 */
	private UserService userService;
	
	@Override
	protected String execute(String cmd) throws Exception {
		String passportURL=PassportClient.getInstance().getPassportURL();
		int serverId=PassportClient.getInstance().getServerId();
		String serverPassword=PassportClient.getInstance().getVerifyKey();
		
		String urlAddr=passportURL+"/register";
		String url=SiteUrlModel.getSiteUrl();		//原来的站点地址
		String auth=serverPassword+""+serverId+url+"0"+url;
		auth=MD5.toMD5(auth);
		
		String registerUrl=urlAddr+"?server="+serverId+"&url="+URLEncoder.encode(url,"UTF-8")+"&root=0&input="+URLEncoder.encode(url,"UTF-8")+"&auth="+auth;
		setRequestAttribute("registerUrl", registerUrl);	
		return INPUT;	
	}

	/** 用户服务 */
	public UserService getUserService() {
		return this.userService;
	}
	
	/** 用户服务 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}		
}
