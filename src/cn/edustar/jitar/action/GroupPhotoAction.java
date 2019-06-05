package cn.edustar.jitar.action;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.PhotoModel;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupPhoto;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.GroupPhotoQueryParam;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.PhotoQueryParam;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.service.UPunishScoreService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.pojos.UPunishScore;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.UPunishScoreService;

@SuppressWarnings("serial")
public class GroupPhotoAction extends BaseGroupAction {
	
	/** 分类服务 */
	private CategoryService cate_svc;
	private PhotoService photo_svc;
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
	public void setPhotoService(PhotoService photo_svc) {
		this.photo_svc = photo_svc;
	}	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		
		// 验证登录和在群组中.
		if (isUserLogined() == false)
			return LOGIN;
		
		if (hasCurrentGroupAndMember() == false)
			return ERROR;

		if (cmd == null || cmd.length() == 0)
			cmd = "list";
		if ("list".equals(cmd))
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

		return unknownCommand(cmd);
	}
	/**
	 * 分页列出群组图片.
	 * 
	 * @return
	 */
	private String list() {
		// 构造分页对象, 准备查询.
		Pager pager = getCurrentPager();
		pager.setItemNameAndUnit("图片", "张");	

		PhotoQueryParam param = new PhotoQueryParam();
		param.k = param_util.safeGetStringParam("k", null);
		param.retrieveGroupCategory = true;
		param.groupCateId = param_util.getIntParamZeroAsNull("gcid");
		
		// 进行查询.
		
		List<PhotoModel> photo_list = group_svc.getGroupPhotoList(group_model.getGroupId(), param, pager);

		setRequestAttribute("gcid", param.groupCateId);
		setRequestAttribute("k", param.k);
		
		setRequestAttribute("group", group_model);
		setRequestAttribute("photo_list", photo_list);
		setRequestAttribute("pager", pager);

		// 列出群组的图片分类树.
		CategoryTreeModel res_cate = cate_svc.getCategoryTree(getGroupPhotoCategoryItemType());
		setRequestAttribute("res_cate", res_cate);
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
		return LIST_SUCCESS;
	}

	private List<Integer> photo_ids;
	
	/** 得到要操作的图片标识集合. */
	private boolean getVideoIdsParam() {
		this.photo_ids = param_util.safeGetIntValues("photoId");
		if (photo_ids == null || photo_ids.size() == 0) {
			addActionError("未选择任何要操作的图片");
			return false;
		}
		return true;
	}
	
	private boolean isGroupCategory(int groupCateId) {
		Category cate = cate_svc.getCategory(groupCateId);
		if (cate == null)
			return false;
		
		String itemType = this.getGroupPhotoCategoryItemType();
		if (itemType.equals(cate.getItemType()) == false)
			return false;
		
		return true;
	}
	
	private String move() {
		// 得到参数.
		if (getVideoIdsParam() == false) return ERROR;
		Integer groupCateId = param_util.getIntParamZeroAsNull("groupCateId");
		// 验证分类合法性.
		if (groupCateId != null) {
			if (isGroupCategory(groupCateId) == false) {
				addActionError("非法的目标分类");
				return ERROR;
			}
		}
		
		// 循环每个图片进行操作.
		int oper_count = 0;
		for (Integer id : this.photo_ids) {
			// 得到图片.
			GroupPhoto gp = group_svc.getGroupPhotoByGroupAndPhoto(group_model.getGroupId(), id);
			if (gp == null) {
				addActionError("未找到指定标识的图片: " + id);
				continue;
			}
			
			group_svc.updateGroupPhotoCategory(gp, groupCateId);
			++oper_count;
			
			addActionMessage("图片 '" + gp.getPhotoId() + "' 移动到了新分类(或设置为未分类)");
		}
		
		addActionMessage("共 " + oper_count + " 个移动到了新分类");
		
		return SUCCESS;
	}
	
	/**
	 * 解除对图片的引用.
	 * 
	 * @return
	 */
	private String unref() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("photoId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的图片");
			return ERROR;
		}

		// 循环每个图片进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到图片.
			//System.out.println(""+id);
			Tuple<Photo, GroupPhoto> tuple = group_svc.getGroupPhotoByPhotoId(group_model.getGroupId(), id);
			if (tuple == null) {
				addActionError("未找到指定标识的图片: " + id);
				continue;
			}

			// 消除其引用, 也即删除 GroupArticle 对象.
			group_svc.deleteGroupPhoto(tuple.getValue());
			++oper_count;

			addActionMessage("图片 '" + tuple.getKey().getTitle() + "' 已经从群组中移除");
		}

		addActionMessage("共 " + oper_count + " 篇图片从群组中移除");

		// 更新群组图片数.
		//group_svc.updateGroupArticleCount(group_model._getGroupObject());

		return SUCCESS;
	}

	
	private String rcmd(){
		List<Integer> ids = param_util.safeGetIntValues("photoId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的图片");
			return ERROR;
		}
		// 循环每个图片进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到图片.
			Photo photo = photo_svc.findById(id);
			if (photo == null) {
				addActionError("未找到指定标识的图片: " + id);
				continue;
			}
		}
		return SUCCESS;
	}
	
	private String unrcmd(){
		List<Integer> ids = param_util.safeGetIntValues("photoId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的图片");
			return ERROR;
		}
		// 循环每个图片进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到图片.
			Photo photo = photo_svc.findById(id);
			if (photo == null) {
				addActionError("未找到指定标识的图片: " + id);
				continue;
			}
		}
		addActionMessage("共 " + oper_count + " 篇图片取消平台推荐图片");

		return SUCCESS;
	}	
	
	/**
	 * 设置为群组精华图片.
	 * 
	 * @return
	 */
	private String best() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("photoId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的图片");
			return ERROR;
		}

		// 循环每个图片进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到图片.
			GroupPhoto gp = group_svc.getGroupPhotoByGroupAndPhoto(group_model.getGroupId(), id);
			if (gp == null) {
				addActionError("未找到指定标识的图片: " + id);
				continue;
			}

			// 设置为精华.
			//group_svc.bestGroupPhoto(gp);
			++oper_count;

			addActionMessage("图片 '" + gp.getPhotoId() + "' 成功设置为群组精华图片");
		}

		addActionMessage("共 " + oper_count + " 篇图片设置为群组精华图片");

		return SUCCESS;
	}

	/**
	 * 取消群组精华图片.
	 * 
	 * @return
	 */
	private String unbest() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("photoId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的图片");
			return ERROR;
		}

		// 循环每个图片进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到图片.
			GroupPhoto gp = group_svc.getGroupPhotoByGroupAndPhoto(group_model.getGroupId(), id);
			if (gp == null) {
				addActionError("未找到指定标识的图片: " + id);
				continue;
			}

			// 设置为精华.
			//group_svc.unbestGroupPhoto(gp);
			++oper_count;

			addActionMessage("图片 '" + gp.getPhotoId() + "' 取消了群组精华图片");
		}

		addActionMessage("共 " + oper_count + " 篇图片被取消了精华设置");

		return SUCCESS;
	}

	/** 计算群组图片树分类名字 */
	private String getGroupPhotoCategoryItemType() {
		return CommonUtil.toGroupPhotoCategoryItemType(group_model.getGroupId());
	}	
}
