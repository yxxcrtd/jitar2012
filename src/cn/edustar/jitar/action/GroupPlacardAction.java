package cn.edustar.jitar.action;

import java.util.Date;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.PlacardQueryParam;
import cn.edustar.jitar.service.PlacardService;

/**
 * 群组公告管理
 */
@SuppressWarnings("serial")
public class GroupPlacardAction extends BaseGroupAction {
	/** 公告服务 */
	private PlacardService pla_svc;
	
	/** 设置公告服务 */
	public void setPlacardService(PlacardService pla_svc) {
		this.pla_svc = pla_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		// 验证登录, 以及协作组和成员. 
		if (isUserLogined() == false) return LOGIN;
		if (hasCurrentGroupAndMember() == false) return ERROR;
		
		if (cmd == null || cmd.length() == 0) cmd = "list";
		
		String uuid=group_svc.getGroupCateUuid(group_model);
		if(uuid.equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
			setRequestAttribute("isKtGroup", "1");
		}
		else if(uuid.equals(CategoryService.GROUP_CATEGORY_GUID_JTBK)){
			setRequestAttribute("isKtGroup", "2");
		}
		else{
			setRequestAttribute("isKtGroup", "0");
		}	
		
		if ("list".equals(cmd))
			return list();
		else if ("view_list".equals(cmd))
			return view_list();
		else if ("add_placard".equals(cmd))
			return add_placard();
		else if ("edit_placard".equals(cmd))
			return edit_placard();
		else if ("save_placard".equals(cmd))
			return save_placard();
		else if ("delete_placard".equals(cmd))
			return delete_placard();
		else if ("hide_placard".equals(cmd))
			return hide_placard();
		else if ("show_placard".equals(cmd))
			return show_placard();
		
		return unknownCommand(cmd);
	}
	
	/**
	 * 列出所有群组公告.
	 * @return
	 */
	private String list() {
		// 得到本群组的公告列表, 包括隐藏的.
		Pager pager = param_util.createPager();
		pager.setItemNameAndUnit("公告", "条");
		
		PlacardQueryParam param = new PlacardQueryParam();
		param.objType = ObjectType.OBJECT_TYPE_GROUP;
		param.objId = group_model.getGroupId();
		param.hideState = null;
		param.orderType = 0;
		
		List<Placard> placard_list = pla_svc.getPlacardList(param, pager);
		
		setRequestAttribute("group", group_model);
		setRequestAttribute("placard_list", placard_list);
		setRequestAttribute("pager", pager);
		
		
		return LIST_SUCCESS;
	}
	
	/**
	 * 列出所有群组公告.
	 * @return
	 */
	private String view_list() {
		// 得到本群组的公告列表, 不包括隐藏的.
		List<Placard> placard_list = pla_svc.getPlacardList(
				ObjectType.OBJECT_TYPE_GROUP, 
				group_model.getGroupId(), false);
		
		setRequestAttribute("group", group_model);
		setRequestAttribute("placard_list", placard_list);
		
		return "View_List";
	}
	
	/**
	 * 添加公告.
	 * @return
	 */
	private String add_placard() {
		Placard placard = new Placard();
		
		setRequestAttribute("placard", placard);
		setRequestAttribute("group", group_model);
		setRequestAttribute("__referer", getRefererHeader());
		return "AddEdit_Placard";
	}

	/**
	 * 编辑群组公告.
	 * @return
	 */
	private String edit_placard() {
		int placardId = param_util.getIntParam("placardId");
		Placard placard = pla_svc.getPlacard(placardId);
		
		if (placard == null) {
			addActionError("未找到标识为 " + placardId + " 的公告, 请确定您是从有效链接进入的");
			return ERROR;
		}
		setRequestAttribute("placard", placard);
		setRequestAttribute("group", group_model);
		setRequestAttribute("__referer", getRefererHeader());
		return "AddEdit_Placard";
	}
	
	/** 编辑/创建公告时候的公告对象 */
	private Placard placard;
	
	private boolean collectPlacardData() {
		this.placard = new Placard();
		placard.setId(param_util.getIntParam("placardId"));
		placard.setTitle(param_util.safeGetStringParam("title"));
		placard.setContent(param_util.safeGetStringParam("content"));
		
		return true;
	}
	
	/**
	 * 保存公告.
	 * @return
	 */
	private String save_placard() {
		if (collectPlacardData() == false) return ERROR;
		
		if (placard.getId() == 0)
			return createPlacard(placard);
		else
			return updatePlacard(placard);
	}
	
	/** 创建公告 */
	private String createPlacard(Placard placard) {
		placard.setCreateDate(new Date());
		placard.setObjType(ObjectType.OBJECT_TYPE_GROUP.getTypeId());
		placard.setObjId(super.group_model.getGroupId());
		
		pla_svc.savePlacard(placard);
		
		addActionMessage("创建公告 '" + placard.getTitle() + "' 成功完成");
		
		return SUCCESS;
	}
	
	/** 更新公告 */
	private String updatePlacard(Placard placard) {
		Placard origin_placard = pla_svc.getPlacard(placard.getPlacardId());
		if (origin_placard == null) {
			addActionError("未找到原公告，也许被并发删除了??");
			return ERROR;
		}
		
		// 逻辑+权限验证.
		if (origin_placard.getObjType() != ObjectType.OBJECT_TYPE_GROUP.getTypeId() ||
				origin_placard.getObjId() != group_model.getGroupId()) {
			addActionError("试图编辑别的对象的公告, 请确定您是从有效链接进入的");
			return ERROR;
		}
		
		placard.setCreateDate(new Date());
		placard.setObjType(ObjectType.OBJECT_TYPE_GROUP.getTypeId());
		placard.setObjId(super.group_model.getGroupId());
		
		pla_svc.savePlacard(placard);
		
		addActionMessage("修改公告 '" + placard.getTitle() + "' 成功完成");
		
		return SUCCESS;
	}
	
	/**
	 * 删除一个/多个公告.
	 * @return
	 */
	private String delete_placard() {
		List<Integer> ids = param_util.safeGetIntValues("placard");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择要删除的公告");
			return ERROR;
		}
		
		// 循环删除每个公告.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到公告.
			Placard placard = pla_svc.getPlacard(id);
			if (placard == null) {
				addActionError("未找到标识为 " + id + " 的公告");
				continue;
			}
			
			if (placard.getObjType() != ObjectType.OBJECT_TYPE_GROUP.getTypeId() 
					|| placard.getObjId() != group_model.getGroupId()) {
				addActionError("试图删除别的对象的公告, 请确定您是从有效链接进入的");
				continue;
			}
			
			// 删除公告.
			pla_svc.deletePlacard(placard);
			addActionMessage("公告"+ placard.getPlacardId() + "已删除");
			++oper_count;
		}
		
		addActionMessage("共删除了 " + oper_count + " 条公告");
		
		return SUCCESS;
	}
	
	/**
	 * 隐藏所选公告.
	 * @return
	 */
	private String hide_placard() {
		List<Integer> ids = param_util.safeGetIntValues("placard");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择要隐藏的公告");
			return ERROR;
		}
		
		// 循环操作每个公告.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到公告.
			Placard placard = pla_svc.getPlacard(id);
			if (placard == null) {
				addActionError("未找到标识为 " + id + " 的公告");
				continue;
			}
			
			if (placard.getObjType() != ObjectType.OBJECT_TYPE_GROUP.getTypeId() 
					|| placard.getObjId() != group_model.getGroupId()) {
				addActionError("试图操作别的对象的公告, 请确定您是从有效链接进入的");
				continue;
			}
			
			// 执行操作.
			pla_svc.hidePlacard(placard);
			++oper_count;
		}
		
		addActionMessage("共隐藏了 " + oper_count + " 条公告");
		
		return SUCCESS;
	}

	/**
	 * 显示公告.
	 * @return
	 */
	private String show_placard() {
		List<Integer> ids = param_util.safeGetIntValues("placard");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择要显示的公告");
			return ERROR;
		}
		
		// 循环操作每个公告.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到公告.
			Placard placard = pla_svc.getPlacard(id);
			if (placard == null) {
				addActionError("未找到标识为 " + id + " 的公告");
				continue;
			}
			
			if (placard.getObjType() != ObjectType.OBJECT_TYPE_GROUP.getTypeId() 
					|| placard.getObjId() != group_model.getGroupId()) {
				addActionError("试图操作别的对象的公告, 请确定您是从有效链接进入的");
				continue;
			}
			
			// 执行操作.
			pla_svc.showPlacard(placard);
			++oper_count;
		}
		
		addActionMessage("共显示了 " + oper_count + " 条公告");
		
		return SUCCESS;
	}
}
