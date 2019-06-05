package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.GroupResource;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.pojos.UPunishScore;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.UPunishScoreService;

/**
 * 群组资源管理
 */
@SuppressWarnings("serial")
public class GroupResourceAction extends BaseGroupAction {
	/** 分类服务 */
	private CategoryService cate_svc;
	private ResourceService res_svc;
	private UPunishScoreService pun_svc;
	private MessageService msg_svc;
	/**加罚分服务*/
	public void setUPunishScoreService(UPunishScoreService pun_svc) {
		this.pun_svc = pun_svc;
	}	
	/**消息服务*/
	public void setMessageService(MessageService msg_svc) {
		this.msg_svc = msg_svc;
	}		
	/** 分类服务 */
	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}
	public void setResourceService(ResourceService res_svc) {
		this.res_svc = res_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		// 显示资源属性, 这个不需要登录.
		if ("view".equals(cmd))
			return view();
		
		// 管理操作必须登陆.
		if (isUserLogined() == false) return LOGIN;
		if (hasCurrentGroupAndMember() == false) return ERROR;
		
		// 群组资源.
		if (cmd == null || cmd.length() == 0) cmd = "resman";
		if ("resman".equals(cmd))				// 显示管理框架.
			return resman();
		else if ("resman_left".equals(cmd))		// 框架左侧: 分类树.
			return resman_left();
		else if ("list".equals(cmd))			// 列出资源.
			return list();
		else if ("unref".equals(cmd))
			return unref();
		else if ("rcmd".equals(cmd))
			return rcmd();
		else if ("unrcmd".equals(cmd))
			return unrcmd();
		else if ("best".equals(cmd))
			return best();
		else if ("unbest".equals(cmd))
			return unbest();
		else if ("move".equals(cmd))
			return move();
		else if ("addscore".equals(cmd))
			return addscore();
		else if ("minusscore".equals(cmd))
			return minusscore();	
		return unknownCommand(cmd);
	}
	
	
	private String addscore(){
        int score = param_util.safeGetIntParam("add_score");
        if(score>0){
            score = score*-1;
        }
        String score_reason = param_util.safeGetStringParam("add_score_reason");
        
		// 得到参数.
		if (getResourceIdsParam() == false) return ERROR;
		
		// 循环每个资源进行操作.
		int oper_count = 0;
		for (Integer id : this.res_ids) {
			// 得到资源.
			Tuple<Resource, GroupResource> tuple = group_svc.getGroupResourceByResourceId(
					group_model.getGroupId(), id);
			if (tuple == null) {
				addActionError("未找到指定标识的资源: " + id);
				continue;
			}
			oper_count++;
			UPunishScore upun =this.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(),tuple.getKey().getResourceId(),tuple.getKey().getUserId(),Float.parseFloat(""+score),score_reason,getLoginUser().getUserId(),getLoginUser().getTrueName());
            this.pun_svc.saveUPunishScore(upun)	;	
            
            addActionMessage("资源 '" + tuple.getKey().getTitle() + "' 成功加分");
            
		}
		
		addActionMessage("共 " + oper_count + " 篇资源成功加分");

		return SUCCESS;
	}
	
	private String minusscore(){
        int score = param_util.safeGetIntParam("minus_score");
        if(score<0){
            score = score*-1;
        }
        String score_reason = param_util.safeGetStringParam("minus_score_reason");
		// 得到参数.
		if (getResourceIdsParam() == false) return ERROR;
		
		// 循环每个资源进行操作.
		int oper_count = 0;
		for (Integer id : this.res_ids) {
			// 得到资源.
			Tuple<Resource, GroupResource> tuple = group_svc.getGroupResourceByResourceId(
					group_model.getGroupId(), id);
			if (tuple == null) {
				addActionError("未找到指定标识的资源: " + id);
				continue;
			}
			oper_count++;
			UPunishScore upun =this.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(),tuple.getKey().getResourceId(),tuple.getKey().getUserId(),Float.parseFloat(""+score),score_reason,getLoginUser().getUserId(),getLoginUser().getTrueName());
            this.pun_svc.saveUPunishScore(upun)	;	  
            
            Message message = new Message();
            message.setSendId(getLoginUser().getUserId());
            message.setReceiveId(tuple.getKey().getUserId());
            message.setTitle("管理员删除了您的备课资源及扣分信息");
            if(score_reason != null && score_reason.length()>0){
                message.setContent("您的资源 " + tuple.getKey().getTitle() + " 被删除,扣" + score + "分,原因:" + score_reason);
            }
            else{  
            	message.setContent("您的资源  " + tuple.getKey().getTitle() + " 被删除,扣" + score + "分");
            }
            msg_svc.sendMessage(message);

            addActionMessage("资源 '" + tuple.getKey().getTitle() + "' 被罚分");

            //删除资源 
            this.res_svc.crashResource(tuple.getKey());
            
            
		}

		addActionMessage("共 " + oper_count + " 个资源被罚分");

		return SUCCESS;		
	}	
	
	/**
	 * 显示群组资源管理框架页.
	 * @return
	 */
	private String resman() {
		setRequestAttribute("group", this.group_model);
		
		return "ResMan_Frame";
	}

	/**
	 * 显示群组资源管理的左侧.
	 * @return
	 */
	private String resman_left() {
		setRequestAttribute("group", group_model);
		
		// 列出群组的资源分类树.
		CategoryTreeModel res_cate = cate_svc.getCategoryTree(getGroupResourceCategoryItemType());
		setRequestAttribute("res_cate", res_cate);
		
		return "ResMan_Left";
	}
	
	/**
	 * 列出资源.
	 * @return
	 */
	private String list() {
		setRequestAttribute("group", group_model);
		
		ResourceQueryParam param = new ResourceQueryParam();
		param.retrieveGroupCategory = true;
		param.shareMode=null;
		Pager pager = getCurrentPager();
		param.k = param_util.safeGetStringParam("k", null);
		pager.setItemNameAndUnit("资源", "个");
		param.groupCateId = param_util.getIntParamZeroAsNull("gcid");
		param.shareMode=null;
		setRequestAttribute("gcid", param.groupCateId);
		setRequestAttribute("k", param.k);
		// 得到资源列表.
		List<ResourceModelEx> resource_list = group_svc.getGroupResourceList(
				group_model.getGroupId(), param, pager);
		
		setRequestAttribute("resource_list", resource_list);
		setRequestAttribute("pager", pager);
		
		// 列出群组的资源分类树.
		CategoryTreeModel res_cate = cate_svc.getCategoryTree(getGroupResourceCategoryItemType());
		setRequestAttribute("res_cate", res_cate);
		
		String uuid=group_svc.getGroupCateUuid(group_model);
		if(uuid.equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
			setRequestAttribute("isKtGroup", "1");
		}else{
			setRequestAttribute("isKtGroup", "0");
		}	
		return LIST_SUCCESS;
	}

	private List<Integer> res_ids;
	
	/** 得到要操作的资源标识集合. */
	private boolean getResourceIdsParam() {
		this.res_ids = param_util.safeGetIntValues("resourceId");
		if (res_ids == null || res_ids.size() == 0) {
			addActionError("未选择任何要操作的资源");
			return false;
		}
		return true;
	}
	
	/**
	 * 解除对资源的引用.
	 * @return
	 */
	private String unref() {
		// 得到参数.
		if (getResourceIdsParam() == false) return ERROR;
		
		// 循环每个资源进行操作.
		int oper_count = 0;
		for (Integer id : this.res_ids) {
			// 得到资源.
			Tuple<Resource, GroupResource> tuple = group_svc.getGroupResourceByResourceId(
					group_model.getGroupId(), id);
			if (tuple == null) {
				addActionError("未找到指定标识的资源: " + id);
				continue;
			}
			
			// 消除其引用, 也即删除 GroupResource 对象.
			group_svc.deleteGroupResource(tuple.getValue());
			++oper_count;
			
			addActionMessage("资源 '" + tuple.getKey().getTitle() + "' 已经从群组中移除");
		}
		
		addActionMessage("共 " + oper_count + " 个资源从群组中移除");
		
		// TODO: 更新群组资源数.
		// group_svc.updateGroupResourceCount(group_model._getGroupObject());
		
		return SUCCESS;
	}

	private String rcmd() {
		// 得到参数.
		if (getResourceIdsParam() == false) return ERROR;
		
		// 循环每个资源进行操作.
		int oper_count = 0;
		for (Integer id : this.res_ids) {
			// 得到资源.
			Resource resource = res_svc.getResource(id);
			if (resource == null) {
				addActionError("未找到指定标识的资源: " + id);
				continue;
			}
			// 设置为精华.
			if(resource.getRecommendState()==false)
			{
				res_svc.rcmdResource(resource);
				++oper_count;
				addActionMessage("资源 '" + resource.getTitle() + "' 设置为平台推荐资源");
			}
		}
		addActionMessage("共 " + oper_count + " 个资源被设置为平台推荐资源");
		return SUCCESS;
	}
	private String unrcmd() {
		// 得到参数.
		if (getResourceIdsParam() == false) return ERROR;
		
		// 循环每个资源进行操作.
		int oper_count = 0;
		for (Integer id : this.res_ids) {
			// 得到资源.
			Resource resource = res_svc.getResource(id);
			if (resource == null) {
				addActionError("未找到指定标识的资源: " + id);
				continue;
			}
			// 设置为精华.
			if(resource.getRecommendState()==true)
			{
				res_svc.unrcmdResource(resource);
				++oper_count;
				addActionMessage("资源 '" + resource.getTitle() + "' 取消平台推荐");
			}
		}
		addActionMessage("共 " + oper_count + " 个资源被取消平台推荐");
		return SUCCESS;
	}

	/**
	 * 设置为精华资源.
	 * @return
	 */
	private String best() {
		// 得到参数.
		if (getResourceIdsParam() == false) return ERROR;
		
		// 循环每个资源进行操作.
		int oper_count = 0;
		for (Integer id : this.res_ids) {
			// 得到资源.
			Tuple<Resource, GroupResource> tuple = group_svc.getGroupResourceByResourceId(
					group_model.getGroupId(), id);
			if (tuple == null) {
				addActionError("未找到指定标识的资源: " + id);
				continue;
			}
			
			Resource r = tuple.getKey();
			GroupResource gr = tuple.getValue();
			if (gr.getIsGroupBest()) {
				addActionMessage("资源 '" + r.getTitle() + "' 已经是群组精华, 未进行重复设置");
				continue;
			}
			
			// 设置为精华.
			group_svc.bestGroupResource(gr);
			++oper_count;
			
			addActionMessage("资源 '" + r.getTitle() + "' 设置为群组精华资源");
		}
		
		addActionMessage("共 " + oper_count + " 个资源被设置为群组精华资源");
		
		return SUCCESS;
	}
	
	/**
	 * 取消精华资源设置.
	 * @return
	 */
	private String unbest() {
		// 得到参数.
		if (getResourceIdsParam() == false) return ERROR;
		
		// 循环每个资源进行操作.
		int oper_count = 0;
		for (Integer id : this.res_ids) {
			// 得到资源.
			Tuple<Resource, GroupResource> tuple = group_svc.getGroupResourceByResourceId(
					group_model.getGroupId(), id);
			if (tuple == null) {
				addActionError("未找到指定标识的资源: " + id);
				continue;
			}
			
			Resource r = tuple.getKey();
			GroupResource gr = tuple.getValue();
			if (gr.getIsGroupBest() == false) {
				addActionMessage("资源 '" + r.getTitle() + "' 不是群组精华资源, 未进行取消操作");
				continue;
			}
			
			// 设置为精华.
			group_svc.unbestGroupResource(gr);
			++oper_count;
			
			addActionMessage("资源 '" + r.getTitle() + "' 取消了群组精华资源");
		}
		
		addActionMessage("共 " + oper_count + " 个资源取消了群组精华资源");
		
		return SUCCESS;
	}

	/**
	 * 移动到新的群组分类.
	 * @return
	 */
	private String move() {
		// 得到参数.
		if (getResourceIdsParam() == false) return ERROR;
		Integer groupCateId = param_util.getIntParamZeroAsNull("groupCateId");
		// 验证分类合法性.
		if (groupCateId != null) {
			if (isGroupCategory(groupCateId) == false) {
				addActionError("非法的目标分类");
				return ERROR;
			}
		}
		
		//System.out.print("groupCateId = " + groupCateId);
		// 循环每个资源进行操作.
		int oper_count = 0;
		for (Integer id : this.res_ids) {
			// 得到资源.
			Tuple<Resource, GroupResource> tuple = group_svc.getGroupResourceByResourceId(
					group_model.getGroupId(), id);
			if (tuple == null) {
				addActionError("未找到指定标识的资源: " + id);
				continue;
			}
			
			// 设置其新分类.
			GroupResource gr = tuple.getValue();
			group_svc.updateGroupResourceCategory(gr, groupCateId);
			++oper_count;
			
			addActionMessage("资源 '" + tuple.getKey().getTitle() + "' 改变了组内分类属性");
		}
		
		addActionMessage("共 " + oper_count + " 个移动到了新分类");
		
		return SUCCESS;
	}
	
	/**
	 * 判定指定分类是否是群组资源分类.
	 * @param groupCateId
	 * @return
	 */
	private boolean isGroupCategory(int groupCateId) {
		Category cate = cate_svc.getCategory(groupCateId);
		if (cate == null)
			return false;
		
		String itemType = this.getGroupResourceCategoryItemType();
		if (itemType.equals(cate.getItemType()) == false)
			return false;
		
		return true;
	}
	
	/**
	 * 显示资源属性.
	 * @return
	 */
	private String view() {
		// 得到参数.
		int resourceId = param_util.getIntParam("resourceId");
		if (resourceId == 0) {
			addActionError("未找到指定资源, 请确定您点击了有效的链接");
			return ERROR;
		}
		
		// 得到资源.
		Tuple<Resource, GroupResource> tuple = group_svc.getGroupResourceByResourceId(
				group_model.getGroupId(), resourceId);
		if (tuple == null) {
			addActionError("未找到指定资源, 请确定您点击了有效的链接");
			return ERROR;
		}

		// 准备显示资源.
		setRequestAttribute("resource", tuple.getKey());
		setRequestAttribute("group", group_model);
		setRequestAttribute("group_resource", tuple.getValue());
		
		return "View_ResAttr";
	}
	
	/** 计算群组资源树分类名字 */
	private String getGroupResourceCategoryItemType() {
		return CommonUtil.toGroupResourceCategoryItemType(group_model.getGroupId());
	}
}
