package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 资源后台管理.
 * 
 *
 */
public class AdminResourceAction extends BaseResourceAction {
	/** serialVersionUID */
	private static final long serialVersionUID = -104973179544080233L;
	
	/**
	 * 构造函数.
	 */
	public AdminResourceAction() {
		JitarContext jtar_ctxt = JitarContext.getCurrentJitarContext();
		if (jtar_ctxt != null) {
			this.res_svc = jtar_ctxt.getResourceService();
			this.cate_svc = jtar_ctxt.getCategoryService();
			this.user_svc = jtar_ctxt.getUserService();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	public String execute(String cmd) {
		if (isUserLogined() == false) return LOGIN;
		
		if (cmd == null || cmd.length() == 0) cmd = "list";
		
		if ("list".equals(cmd))
			return list("all");
		else if ("best_list".equals(cmd))
			return list("best");
		else if ("audit_list".equals(cmd))
			return list("audit");
		else if ("edit".equals(cmd))
			return edit();
		else if ("delete".equals(cmd))
			return delete();
		else if ("set_share_mode".equals(cmd))
			return set_share_mode();
		else if ("move".equals(cmd))
			return move("admin");

		// 审核, 推荐.
		else if ("audit".equals(cmd))
			return audit();
		else if ("unaudit".equals(cmd))
			return unaudit();
		else if ("rcmd".equals(cmd))
			return rcmd();
		else if ("unrcmd".equals(cmd))
			return unrcmd();
		
		return unknownCommand(cmd);
	}

	/**
	 * 推荐所选的资源.
	 * @return
	 */
	protected String rcmd() {
		// 获得资源标识参数.
		if (getResourceIds() == false) return ERROR;
		
		// 为每个对象执行.
		int oper_count = 0;
		for (Integer resourceId : this.res_ids) {
			// 获得文章对象.
			Resource resource = res_svc.getResource(resourceId);
			if (resource == null) {
				addActionError("未找到标识为 " + resourceId + " 的资源");
				continue;
			}
			
			if (checkRight(resource, "audit_resource") == false) {
				addActionError("没有权限. 对资源 id=" + resource.getId() + " 的推荐操作因为权限不足被拒绝.");
				continue;
			}
			res_svc.rcmdResource(resource);
			++oper_count;
			addActionMessage("资源 id=" + resource.getId() + " '" + resource.getTitle() + "' 已推荐");
		}
		
		addActionMessage("共推荐了 " + oper_count + " 个资源");
		
		return SUCCESS;
	}
	
	/**
	 * 取消推荐所选的资源.
	 * @return
	 */
	protected String unrcmd() {
		// 获得资源标识参数.
		if (getResourceIds() == false) return ERROR;
		
		// 为每个对象执行.
		int oper_count = 0;
		for (Integer resourceId : this.res_ids) {
			// 获得文章对象.
			Resource resource = res_svc.getResource(resourceId);
			if (resource == null) {
				addActionError("未找到标识为 " + resourceId + " 的资源");
				continue;
			}
			
			if (checkRight(resource, "audit_resource") == false) {
				addActionError("没有权限. 对资源 id=" + resource.getId() + " 的取消推荐操作因为权限不足被拒绝.");
				continue;
			}
			res_svc.unrcmdResource(resource);
			++oper_count;
			addActionMessage("资源 id=" + resource.getId() + " '" + resource.getTitle() + "' 已取消推荐");
		}
		
		addActionMessage("共取消推荐了 " + oper_count + " 个资源");
		
		return SUCCESS;
	}
	
	/**
	 * 取消审核所选的资源.
	 * @return
	 */
	protected String unaudit() {
		// 获得资源标识参数.
		if (getResourceIds() == false) return ERROR;
		
		// 为每个对象执行.
		int oper_count = 0;
		for (Integer resourceId : this.res_ids) {
			// 获得文章对象.
			Resource resource = res_svc.getResource(resourceId);
			if (resource == null) {
				addActionError("未找到标识为 " + resourceId + " 的资源");
				continue;
			}
			
			if (checkRight(resource, "audit_resource") == false) {
				addActionError("没有权限. 对资源 id=" + resource.getId() + " 的取消审核操作因为权限不足被拒绝.");
				continue;
			}
			res_svc.unauditResource(resource);
			++oper_count;
			addActionMessage("资源 id=" + resource.getId() + " '" + resource.getTitle() + "' 已取消审核");
		}
		
		addActionMessage("共取消审核了 " + oper_count + " 个资源");
		
		return SUCCESS;
	}
	
	/**
	 * 审核所选的资源.
	 * @return
	 */
	protected String audit() {
		// 获得资源标识参数.
		if (getResourceIds() == false) return ERROR;
		
		// 为每个对象执行.
		int oper_count = 0;
		for (Integer resourceId : this.res_ids) {
			// 获得文章对象.
			Resource resource = res_svc.getResource(resourceId);
			if (resource == null) {
				addActionError("未找到标识为 " + resourceId + " 的资源");
				continue;
			}
			
			if (checkRight(resource, "audit_resource") == false) {
				addActionError("没有权限. 对资源 id=" + resource.getId() + " 的审核操作因为权限不足被拒绝.");
				continue;
			}
			res_svc.auditResource(resource);
			++oper_count;
			addActionMessage("资源 id=" + resource.getId() + " '" + resource.getTitle() + "' 已通过审核");
		}
		
		addActionMessage("共审核了 " + oper_count + " 个资源");
		
		return SUCCESS;
	}
	
	/**
	 * 列表显示所有资源.
	 * @return
	 */
	private String list(String type) {
		// 构造查询参数.
		String listType = "所有资源";
		ResourceQueryParam param = new ResourceQueryParam();
		param.retrieveSystemCategory = true;
		String k = param_util.safeGetStringParam("k", "");
		param.shareMode = null;
		int f = param_util.getIntParam("f");
		int sc = param_util.getIntParam("sc");
		int rt = param_util.getIntParam("rt");
		int sm = param_util.safeGetIntParam("sm", -1);

		if (f == 0) {				// 资源标题或标签.
			param.k = k;
		} else if (f == 1) {		// 用户.
			param.userId = calcUserId(k);
		}
		if (sc != 0)
			param.sysCateId = sc;
		if (rt != 0)
			param.resTypeId = rt;
		if (sm != -1)
			param.shareMode = sm;
		
		
		if(type.equals("best"))
		{
			param.recommendState=true;
		} 
		else if(type.equals("audit"))
		{
			param.auditState=1;
		}
		
		setRequestAttribute("k", k);
		setRequestAttribute("f", f);
		setRequestAttribute("sc", sc);
		setRequestAttribute("rt", rt);
		setRequestAttribute("sm", sm);
		setRequestAttribute("listType", listType);
		
		// 构造分页对象.
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("资源", "个");
		
		// 进行查询.
		List<ResourceModelEx> resource_list = res_svc.getResourceList(param, pager);
		
		// 设置到 request 中，准备用模板展现.
		setRequestAttribute("resource_list", resource_list);
		setRequestAttribute("pager", pager);

		// 系统分类树.
		putResourceSystemCategory();
		
		// 资源分类树.
		putResourceType();
		
		return LIST_SUCCESS;
	}
	
	private Integer calcUserId(String k) {
		if (ParamUtil.isInteger(k))		// 做为 id 看待.
			return ParamUtil.safeParseIntegerWithNull(k);
		else {							// 做为用户名看待.
			User u = user_svc.getUserByLoginName(k);
			return u == null ? 0 : u.getUserId();
		}
	}
}
