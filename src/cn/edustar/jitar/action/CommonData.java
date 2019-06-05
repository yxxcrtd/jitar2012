package cn.edustar.jitar.action;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import cn.edustar.jitar.jython.JythonBaseAction;
import cn.edustar.jitar.model.CommonObject;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.PluginAuthorityCheckService;
import cn.edustar.jitar.service.QuestionAnswerService;
import cn.edustar.jitar.util.ParamUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 活动 课程
 * 
 * @author renliang
 */
public class CommonData extends JythonBaseAction {
	
	public String ERROR = "/WEB-INF/ftl/Error.ftl";
	public String ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl";
	public String ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl";
	public String SUCCESS = "/WEB-INF/ftl/success.ftl";
	public String LOGIN = "/login.jsp";
	public ParamUtil params = null;
	public String parentGuid = null;
	public String parentType = null;
	public QuestionAnswerService questionAnswerService = null;
	public PageFrameService pageFrameService = null;
	public PluginAuthorityCheckService pluginAuthorityCheckService = null;
	public CommonObject commonObject = null;

	public CommonData() {
		init();
	}
	public CommonData(HttpServletRequest r) {
	    this.request = r;
        init();
    }

	public HttpServletRequest request = null;

	public void init() {
		pageFrameService = ContextLoader.getCurrentWebApplicationContext().getBean("pageFrameService", PageFrameService.class);
		questionAnswerService = ContextLoader.getCurrentWebApplicationContext().getBean("questionAnswerService", QuestionAnswerService.class);
		pluginAuthorityCheckService = ContextLoader.getCurrentWebApplicationContext().getBean("pluginAuthorityCheckService", PluginAuthorityCheckService.class);
		params = new ParamUtil(ActionContext.getContext().getParameters());
		parentGuid = params.safeGetStringParam("guid");
		parentType = params.safeGetStringParam("type");
		commonObject = new CommonObject(parentType, parentGuid);
		if(this.request == null){
		    request = super.getRequest();
		}
		// # 因为随处需要使用这2个变量，则此时就装入请求对象中。
		request.setAttribute("parentGuid", parentGuid);
		request.setAttribute("parentType", parentType);
		request.setAttribute("SiteUrl", pageFrameService.getSiteUrl());
		request.setAttribute("UserMgrUrl", pageFrameService.getUserMgrUrl());
	}

	public String getERROR() {
		return ERROR;
	}

	public String getACCESS_DENIED() {
		return ACCESS_DENIED;
	}

	public String getACCESS_ERROR() {
		return ACCESS_ERROR;
	}

	public String getSUCCESS() {
		return SUCCESS;
	}

	public String getLOGIN() {
		return LOGIN;
	}

	public ParamUtil getParams() {
		return params;
	}

	public String getParentGuid() {
		return parentGuid;
	}

	public String getParentType() {
		return parentType;
	}

	public CommonObject getCommonObject() {
		return commonObject;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public String getClientIp() {
		String strip = request.getHeader("x-forwarded-for");
		if (strip == null || "".equals(strip.trim())) {
			strip = request.getRemoteAddr();
		}
		return strip;
	}

	public void writeToResponse(String _content) throws IOException {
		getResponse().getWriter().println(_content);
	}
	
	public PageFrameService getPageFrameService() {
		return pageFrameService;
	}
}
