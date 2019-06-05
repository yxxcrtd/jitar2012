package cn.edustar.jitar.action;

import cn.edustar.jitar.model.SiteUrlModel;

/**
 * 返回首页
 * 
 * @author renliang
 */
@SuppressWarnings("serial")
public class SubjectHomeAction extends AbstractBasePageAction {

	@Override
	protected String execute(String cmd) throws Exception {
		String siteUrl = SiteUrlModel.getSiteUrl();
		response.setStatus(301);
		response.setHeader("Location", siteUrl);
		response.setHeader("Connection", "close");
		return null;
	}

}
