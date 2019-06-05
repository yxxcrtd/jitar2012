package cn.edustar.jitar.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.SubjectWebpart;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.ParamUtil;
import cn.edustar.jitar.util.WebUtil;

/**
 * @author 孟宪会
 *
 */
public class SubjectWebpartAction extends AbstractServletAction {

	private static final long serialVersionUID = 7621527188104013245L;
	private static final Logger log = LoggerFactory.getLogger(SubjectWebpartAction.class);
	
	/** 参数获取辅助对象 */
	protected ParamUtil param_util;
	
	/** 请求对象 */
	protected HttpServletRequest request;
	
	/** 响应对象 */
	protected HttpServletResponse response;
	
	private SubjectService subjectService;
	
	private SubjectWebpart subjectWebpart = null;
	private AccessControlService accessControlService;
	
	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	
	public String execute() throws Exception {		
		this.param_util = new ParamUtil(getActionContext().getParameters());
		String cmd = param_util.safeGetStringParam("cmd");
		if(cmd == null)
		{
			return ERROR;
		}
		
		log.info("命令：" + cmd);
		if("move".equalsIgnoreCase(cmd))
			return this.moveWidget();
		else if("delete".equalsIgnoreCase(cmd))
			return this.deleteWidget();
		else
			return ERROR;
	}
	
	private String moveWidget() throws IOException{
		int widgetId = this.param_util.safeGetIntParam("widgetId");
		int col = this.param_util.safeGetIntParam("col");
		int nextWidgetId = this.param_util.safeGetIntParam("wbi");
		log.info("widgetId = " + widgetId + " col = " + col + " nextWidgetId=" + nextWidgetId);
		subjectWebpart = this.subjectService.getSubjectWebpartById(widgetId);
		if(subjectWebpart == null)
		{
			return this.ajax_response(HttpServletResponse.SC_NOT_FOUND, "未找到指定标识的模块");
		}
		
		if(this.canOperation() == false)
		{
			return this.ajax_response(HttpServletResponse.SC_FORBIDDEN, "权限被拒绝。");
		}
		
		subjectWebpart.setWebpartZone(col);
		this.subjectService.setSubjectWebpartPosition(subjectWebpart, col, nextWidgetId);
		this.subjectService.saveOrUpdateSubjectWebpart(subjectWebpart);
		return ajax_success();
	}
	private String deleteWidget() throws IOException
	{
		int widgetId = this.param_util.safeGetIntParam("widgetId");
		subjectWebpart = this.subjectService.getSubjectWebpartById(widgetId);
		if(subjectWebpart == null)
		{
			return this.ajax_response(HttpServletResponse.SC_NOT_FOUND, "未找到指定标识的模块");
		}
		
		if(this.canOperation() == false)
		{
			return this.ajax_response(HttpServletResponse.SC_FORBIDDEN, "权限被拒绝。");
		}
		if(subjectWebpart.getSystemModule() == true)
		{
			subjectWebpart.setVisible(false);
			this.subjectService.saveOrUpdateSubjectWebpart(subjectWebpart);
		}
		else
		{
			this.subjectService.deleteSubjectWebpart(subjectWebpart);
		}
		
		return ajax_success();
	}
	
	
	/**
	 * 向 client 返回指定的信息.
	 * @param errCode
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	private String ajax_response(int errCode, String msg) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("" + errCode + " " + msg);
		return NONE;
	}

	/**
	 * 向 client 返回操作成功信息 SC_OK, "OK" .
	 * @return
	 * @throws IOException
	 */
	private String ajax_success() throws IOException {
		return ajax_response(HttpServletResponse.SC_OK, "OK");
	}	
	
	/**
	 * 权限验证
	 * @return
	 */
	private boolean canOperation()
	{
		HttpSession session = this.request.getSession();
		log.info("session = " + session);
		
		User u = WebUtil.getLoginUser(session);
		
		if (u == null) return false;
		if (subjectWebpart == null) return false;
		Subject subject = subjectService.getSubjectById(subjectWebpart.getSubjectId());
		if(subject == null) return false;
		
		if(this.accessControlService.isSystemAdmin(u))
		{
			return true;
		}
		
		if(this.accessControlService.userIsSubjectAdmin(u, subject) == true)
		{
			return true;
		}
		
		
		
		return false;
	}
}
