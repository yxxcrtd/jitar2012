package cn.edustar.jitar.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.impl.JitarContextImpl;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 所有Action的基类
 * 
 * @author Yang XinXin
 */
public abstract class ManageBaseAction extends AbstractBasePageAction {
	private static final long serialVersionUID = 233810036435297465L;
	protected String flvHref;
	protected String flvThumbNailHref;

	protected Pager getCurrentPager() {
		return params.createPager();
	}
	protected final boolean hasAdminRight() {
		if (this.isUserLogined() == false) {
			return false;
		}
		User user = getLoginUser();
		if (user == null) {
			return false;
		}
		
		AccessControlService acs = (AccessControlService) JitarContextImpl
				.getCurrentJitarContext().getSpringContext()
				.getBean("accessControlService");
		if (acs == null) {
			return false;
		}
		if (acs.isSystemAdmin(user) || acs.isSystemContentAdmin(user)) {
			return true;
		}
		return false;
	}

	protected static List<String> getDefaultIconList(ServletContext servlet_ctxt) {
		File folder = new File(servlet_ctxt.getRealPath("/images/deficon/"));
		if (folder.exists() == false || folder.isDirectory() == false) {
			return null;
		}
		List<String> icons = new ArrayList<String>();
		for (File f : folder.listFiles()) {
			String ext = CommonUtil.getFileExtension(f.getName()).toLowerCase();
			if ("jpg".equals(ext) || "gif".equals(ext) || "png".equals(ext)) {
				icons.add("images/deficon/" + f.getName());
			}
		}
		return icons;
	}

	public String getFlvHref() {
		return flvHref;
	}

	public void setFlvHref(String flvHref) {
		this.flvHref = flvHref;
	}

	public String getFlvThumbNailHref() {
		return flvThumbNailHref;
	}

	public void setFlvThumbNailHref(String flvThumbNailHref) {
		this.flvThumbNailHref = flvThumbNailHref;
	}

}
