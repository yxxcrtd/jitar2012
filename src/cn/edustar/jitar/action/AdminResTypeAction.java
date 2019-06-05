package cn.edustar.jitar.action;

import java.util.List;
import cn.edustar.jitar.pojos.ResType;
import cn.edustar.jitar.service.ResTypeService;

/**
 * 资源类型后台管理.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Jun 25, 2008 9:55:27 PM
 */
public class AdminResTypeAction extends ManageBaseAction {

	/** 序列号 */
	private static final long serialVersionUID = 5155864228320052753L;
	
	/**
	 * 资源分类服务接口.
	 */
	private ResTypeService resTypeService;

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		// 验证权限.
		if (this.canAdmin() == false) {
			this.addActionError("没有管理元数据类型的权限.");
			return ERROR;
		}
		
		if (cmd == null || cmd.length() == 0)
			cmd = "list";
		if ("list".equalsIgnoreCase(cmd))
			return list();
		else if ("add".equalsIgnoreCase(cmd))
			return add();
		else if ("save".equalsIgnoreCase(cmd))
			return save();
		else if ("delete".equalsIgnoreCase(cmd))
			return delete();
		else if ("selrestype".equals(cmd))
			return selrestype();
		return unknownCommand(cmd);
	}

	/**
	 * 资源类型选择
	 * @return
	 */
	protected String selrestype() {
		List<ResType> resType_list = resTypeService.getChildResTypes(0);
		setRequestAttribute("resType_list", resType_list);
		return "Select";
	}
	
	/**
	 * 列出指定分类下的资源类型.
	 * 
	 * @return
	 */
	private String list() {
		int tcParent=param_util.getIntParam("tcParent");
		List<ResType> resType_list = resTypeService.getChildResTypes(tcParent);
		setRequestAttribute("resType_list", resType_list);
		setRequestAttribute("tcParent", tcParent);
		setRequestAttribute("__referer", getRefererHeader());
		return LIST_SUCCESS;
	}

	/**
	 * 添加资源类型。
	 * 
	 * @return
	 */
	private String add() {
		if(param_util.existParam("resTypetId"))
		{
			int resTypeId = param_util.getIntParam("resTypetId");
			ResType resType=resTypeService.getResType(resTypeId);
			if(resType == null)
			{
				addActionError("不能加载资源分类！");
				return SUCCESS;
			}
			
			ResType parentResType = resTypeService.getResType(resType.getTcParent());
			List<ResType> resType_list = null;
			if(parentResType == null)
			{
				resType_list = resTypeService.getChildResTypes(0);
			}
			else
			{
				resType_list = resTypeService.getChildResTypes(parentResType.getTcParent());
			}
			setRequestAttribute("resType_list", resType_list);
			setRequestAttribute("resType", resType);
		}
		else
		{
			ResType resType= new ResType();
			resType.setTcId(0);
			int tcParent=param_util.safeGetIntParam("tcParent");
			List<ResType> resType_list = resTypeService.getChildResTypes(tcParent);
			setRequestAttribute("resType_list", resType_list);
			setRequestAttribute("tcParent",tcParent);
			setRequestAttribute("resType", resType);
		}		
		setRequestAttribute("__referer", getRefererHeader());
		return "Add_Or_Edit";
	}

	/**
	 * 保存资源类型（新建）。
	 * 
	 * @return
	 */
	private String save() {
		ResType resType= new ResType();
		resType.setTcCode(param_util.safeGetStringParam("tcCode"));
		resType.setTcComments(param_util.safeGetStringParam("tcComments"));
		//resType.setTcFlag(Short.parseShort(param_util.safeGetStringParam("tcFlag", "0")));
		resType.setTcParent(param_util.getIntParam("tcParent"));
		resType.setTcSort(param_util.getIntParam("tcSort"));
		resType.setTcTitle(param_util.safeGetStringParam("tcTitle"));
		resType.setTcId(param_util.getIntParam("tcId"));
		
		ResType resTypeCheck = resTypeService.getResTypeByNameAndParentId(resType.getTcTitle(), resType.getTcParent());
		if(resTypeCheck != null)
		{
			addActionError("该资源分类已存在，请更换名称或者上级分类。");
			return ERROR;
		}
		
		try {
			resTypeService.createResType(resType);
		} catch (Exception e) {
			addActionError("资源分类 " + resType.getTcTitle() + " 保存失败，错误信息为："+e.toString()+"！");
		}
		
		return SUCCESS;
	}
	/**
	 * 删除资源类型
	 * 
	 * @return
	 */
	private String delete() {
		int resTypeId = param_util.getIntParam("resTypeId");
		ResType resType = resTypeService.getResType(resTypeId);
		if (resType == null) {
			addActionError("未找到标识为 " + resTypeId + " 的资源分类");
		}
		if(resTypeService.getChildResTypes(resTypeId).size()>0)
		{
			addActionError("资源分类 " + resType.getTcTitle() + " 存在下级资源分类，不允许删除！");
		}
		try {
			resTypeService.delResType(resType);
		} catch (Exception e) {
			addActionError("资源分类 " + resType.getTcTitle() + " 删除失败，错误信息为："+e.toString()+"！");
		}
		
		return SUCCESS;
	}
	public ResTypeService getResTypeService() {
		return resTypeService;
	}

	public void setResTypeService(ResTypeService resTypeService) {
		this.resTypeService = resTypeService;
	}

	
}
