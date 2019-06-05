package cn.edustar.jitar.action;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.UnitWebpart;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.util.FileCache;
import cn.edustar.jitar.util.ParamUtil;
import cn.edustar.jitar.util.WebUtil;

/**
 * @author 孟宪会
 *
 */
public class UnitWebpartAction extends AbstractServletAction {


	private static final long serialVersionUID = -4666532821124276482L;
	
	private static final Logger log = LoggerFactory.getLogger(UnitWebpartAction.class);
	
	/** 参数获取辅助对象 */
	protected ParamUtil param_util;
	
	/** 请求对象 */
	protected HttpServletRequest request;
	
	/** 响应对象 */
	protected HttpServletResponse response;
	
	private UnitService unitService;
	private AccessControlService accessControlService;
	
	private UnitWebpart unitWebpart = null;
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}	
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
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
	
	private void deleteUnitIndexFile(String unitName)
	{
		FileCache fc = new FileCache();
		fc.deleteUnitCacheFile(unitName);
		fc = null;
	}
	
	private String moveWidget() throws IOException
	{
		int widgetId = this.param_util.safeGetIntParam("widgetId");
		int col = this.param_util.safeGetIntParam("col");
		int nextWidgetId = this.param_util.safeGetIntParam("wbi");
		log.info("widgetId = " + widgetId + " col = " + col + " nextWidgetId=" + nextWidgetId);
		unitWebpart = this.unitService.getUnitWebpartById(widgetId);
		if(unitWebpart == null)
		{
			return this.ajax_response(HttpServletResponse.SC_NOT_FOUND, "未找到指定标识的模块");
		}
		
		if(this.canOperation() == false)
		{
			return this.ajax_response(HttpServletResponse.SC_FORBIDDEN, "权限被拒绝。");
		}
		
		unitWebpart.setWebpartZone(col);
		this.unitService.setUnitWebpartPosition(unitWebpart, col, nextWidgetId);
		this.unitService.saveOrUpdateUnitWebpart(unitWebpart);
		Unit u = this.unitService.getUnitById(unitWebpart.getUnitId());
		if(u != null)
		{
			this.deleteUnitIndexFile(u.getUnitName());
		}
		return ajax_success();
	}
	
	private String deleteWidget() throws IOException
	{
		int widgetId = this.param_util.safeGetIntParam("widgetId");
		unitWebpart = this.unitService.getUnitWebpartById(widgetId);
		if(unitWebpart == null)
		{
			return this.ajax_response(HttpServletResponse.SC_NOT_FOUND, "未找到指定标识的模块");
		}
		
		if(this.canOperation() == false)
		{
			return this.ajax_response(HttpServletResponse.SC_FORBIDDEN, "权限被拒绝。");
		}
		if(unitWebpart.getSystemModule())
		{
			unitWebpart.setVisible(false);
			this.unitService.saveOrUpdateUnitWebpart(unitWebpart);
		}
		else
		{
			this.unitService.deleteUnitWebpart(unitWebpart);
		}
		
		Unit u = this.unitService.getUnitById(unitWebpart.getUnitId());
		if(u != null)
		{
			this.deleteUnitIndexFile(u.getUnitName());
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
		//本操作需要系统管理员
		HttpSession session = this.request.getSession();
		log.info("session = " + session);
		
		User u = WebUtil.getLoginUser(session);
		log.info("getLoginUser = " + u);
		log.info("unitWebpart = " + unitWebpart);
		
		if (u == null) return false;
		if(unitWebpart == null) return false;
		int unitId = unitWebpart.getUnitId();		
				
		//判断是否是整站系统管理员
		if(this.accessControlService.isSystemAdmin(u) == true)
		{
			return true;
		}
		
		//判断是否是单位系统管理员
		if(this.accessControlService.userIsUnitSystemAdmin(u, this.unitService.getUnitById(unitId)) == true)
		{
			return true;
		}
		
		return false;
	}

}
