package cn.edustar.jitar.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.ResType;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ChannelPageService;
import cn.edustar.jitar.service.ResTypeService;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.InfoUtil;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 资源管理的基类, 即可以在前台使用, 也可以在后台使用
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Jun 25, 2008 9:55:27 PM
 *
 * 当前有两个子类: ResourceAction - 前台用户使用的资源管理; AdminResourceAction - 管理员使用的资源管理.
 */
public abstract class BaseResourceAction extends ManageDocBaseAction {

	private static final long serialVersionUID = 3346104094004566405L;

	/** 日志 */
	private static final Log log = LogFactory.getLog(BaseResourceAction.class);
	
	/** 资源类型服务 */
	protected ResTypeService restype_svc;
	
	/** 分类服务 */
	protected CategoryService cate_svc;
	
	/** 资源服务 */
	protected ResourceService res_svc;
	
	/** 学科服务 */
	protected SubjectService subj_svc;
	
	/** 用户服务 */
	protected UserService user_svc;
	
	protected ChannelPageService channelPageService;
	
	/** 操作一组资源的时候的资源标识集合. */
	protected List<Integer> res_ids;
	
	/** 学段Id */
	private int gradeId;
	
	/** 学段列表 */
	@SuppressWarnings({"unused", "rawtypes" })
	private List gradeList = new ArrayList();

	/** 对前资源对象 */
	protected Resource cur_resource;
	
	protected String username;
	
	protected String resourceId;

	/**
	 * 构造
	 */
	protected BaseResourceAction() {
		JitarContext jtar_ctxt = JitarContext.getCurrentJitarContext();
		if (jtar_ctxt != null) {
			this.res_svc = jtar_ctxt.getResourceService();
			this.user_svc = jtar_ctxt.getUserService();
			this.cate_svc = jtar_ctxt.getCategoryService();
			this.subj_svc = jtar_ctxt.getSubjectService();
			this.channelPageService = jtar_ctxt.getChannelPageService();
		}
	}
	
	/**
	 * 从参数中获取 id 集合, 结果放在 art_ids 成员中.
	 * @return 返回 true 表示有参数; false 表示未给出.
	 */
	protected boolean getResourceIds() {
		this.res_ids = param_util.safeGetIntValues("resourceId");
		if (res_ids == null || res_ids.size() == 0 || 
				(res_ids.size() == 1 && res_ids.get(0).intValue() == 0)) {
			addActionError("未选择或给出要操作的资源");
			return false;
		}
		return true;
	}
	
	/**
	 * 删除所选的资源.
	 * @return
	 */
	protected String delete() {
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
			
			if (checkRight(resource, "delete_resource") == false) {
				addActionError("没有权限. 对资源 id=" + resource.getId() + " 的删除操作因为权限不足被拒绝.");
				continue;
			}
			
			if (resource.getDelState() == true) {
				// 已经被删除, 不要重复删除.
				addActionMessage("资源 id=" + resource.getId() + " '" + resource.getTitle() + "' 已经被删除, 不需要再次删除");
				continue;
			}
			
			// 删除资源.
			res_svc.deleteResource(resource);
			
			
			
			++oper_count;
			addActionMessage("资源 id=" + resource.getId() + " '" + resource.getTitle() + "' 已经被放到了回收站");
		}
		
		addActionMessage("共删除了 " + oper_count + " 个资源");
		
		return SUCCESS;
	}
	
	/**
	 * 恢复被删除的资源.
	 * @return
	 */
	protected String recover() {
		if (this.getResourceIds() == false) return ERROR;
		
		// 遍历每个资源执行操作.
		int oper_count = 0;
		for (Integer resourceId : this.res_ids) {
			// 验证资源是否存在.
			Resource resource = res_svc.getResource(resourceId);
			if (resource == null) {
				addActionError("未找到指定标识的资源, resourceId = " + resourceId);
				continue;
			}
			
			// 验证权限.
			if (resource.getUserId() != getLoginUser().getUserId()) {
				addActionError("权限不足, 不能操作他人的资源");
				continue;
			}
			if (resource.getDelState() == false) {
				addActionMessage("资源 resourceId = " + resource.getResourceId() + " 未删除, 恢复操作被忽略");
				continue;
			}
			
			// 执行操作.
			res_svc.recoverResource(resource);
			
			++oper_count;
			addActionMessage("资源 " + resource.getTitle() + "(id=" + resource.getResourceId() + ") 成功恢复");
		}
		
		addActionMessage("共 " + oper_count + " 个资源被成功恢复");
		
		return SUCCESS;
	}
	
	/**
	 * 彻底删除资源.
	 * @return
	 */
	protected String crash() {
		if (this.getResourceIds() == false) return ERROR;
		
		// 遍历每个资源执行操作.
		int oper_count = 0;
		for (Integer resourceId : this.res_ids) {
			// 验证资源是否存在.
			Resource resource = res_svc.getResource(resourceId);
			if (resource == null) {
				addActionError("未找到指定标识的资源, resourceId = " + resourceId);
				continue;
			}
			
			// 验证权限.
			if (resource.getUserId() != getLoginUser().getUserId()) {
				addActionError("权限不足, 不能操作他人的资源");
				continue;
			}
			if (resource.getDelState() == false) {
				addActionMessage("资源 resourceId = " + resource.getResourceId() + " 未删除, 将直接被彻底删除");
			}
			String resPath=resource.getHref();
			try {
				JitarRequestContext request = JitarRequestContext.getRequestContext();
				File file = new File( request.getServletContext().getRealPath(resPath) );
				file.delete();
			} catch (Exception e) {
				addActionMessage("文件  " + resPath + " 未删除,请手动删除！");
			}
			
			// 执行操作.
			res_svc.crashResource(resource);
			
			++oper_count;
			addActionMessage("资源 " + resource.getTitle() + "(id=" + resource.getResourceId() + ") 已经被彻底删除");
		}
		
		addActionMessage("共 " + oper_count + " 个资源被彻底删除");
		
		return SUCCESS;
	}

	/**
	 * 列出被删除的资源列表.
	 * @return
	 */
	protected String recycle_list() {
		// 构造查询参数.
		Pager pager = getCurrentPager();
		pager.setItemNameAndUnit("资源", "个");
		
		ResourceQueryParam param = new ResourceQueryParam();
		param.delState = true;
		param.userId = getLoginUser().getUserId();
		param.retrieveUserCategory = true;
		param.retrieveSystemCategory = true;
		param.shareMode = null;

		// 进行查询.
		List<ResourceModelEx> resource_list = res_svc.getResourceList(param, pager);
		
		setRequestAttribute("resource_list", resource_list);
		setRequestAttribute("pager", pager);
		
		return "Recycle_List";
	}
	
	/**
	 * 设置/修改所选资源的共享方式.
	 * @return
	 */
	protected String set_share_mode() {
		// 获得资源标识参数.
		if (getResourceIds() == false) return ERROR;

		// 得到共享模式参数.
		int shareMode = standardShareMode(param_util.getIntParam("shareMode"));
		
		// 为每个对象执行.
		int oper_count = 0;
		for (Integer resourceId : this.res_ids) {
			// 得到资源.
			Resource resource = res_svc.getResource(resourceId);
			if (resource == null) {
				addActionError("未找到标识为 " + resourceId + " 的资源.");
				continue;
			}
			
			// 验证权限.
			if (checkRight(resource, "set_share_mode") == false) {
				addActionError("没有权限. 不能设置资源 " + resource.toDisplayString() + " 的共享模式.");
				continue;
			}
			
			// 设置共享模式.
			res_svc.updateResourceShareMode(resource, shareMode);
			
			++oper_count;
			addActionMessage("资源 " + resource.toDisplayString() + " 设置共享模式为: " 
					+ InfoUtil.shareModeToString(shareMode));
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 规范化为当前支持的共享方式.
	 * @param shareMode
	 * @return
	 */
	public static int standardShareMode(int shareMode) {
		if (shareMode <= Resource.SHARE_MODE_PRIVATE) return Resource.SHARE_MODE_PRIVATE;
		if (shareMode >= Resource.SHARE_MODE_FULL) return Resource.SHARE_MODE_FULL;
		
		// 当前只支持 full, group, private 三个.
		if (shareMode >= Resource.SHARE_MODE_GROUP) return Resource.SHARE_MODE_GROUP;
		return Resource.SHARE_MODE_PRIVATE;
	}
	
	/**
	 * 显示'编辑资源'页面.
	 * 
	 * @return
	 */
	protected String edit() {
		
		// 得到要编辑的资源.
		if (getCurrentResource() == false) {
			return ERROR;
		}
		
		String resType = "";
		if (cur_resource.getResTypeId() != null) {
			ResType resTypeModel = restype_svc.getResType(cur_resource.getResTypeId());
			if (resTypeModel != null)
				resType = resTypeModel.getTcTitle();
		}
		setRequestAttribute("resType", resType);
		setRequestAttribute("resource", cur_resource);
		setRequestAttribute("resTypeList", restype_svc.getResTypes());

		// 系统分类, 用户分类, 学科.
		putResourceCategoryAndSubjectList();
		putGradeList();	
		
		/*User resUser = this.user_svc.getUserById(cur_resource.getUserId());
		if(resUser != null && resUser.getChannelId() != null)
		{			
			Channel channel = this.channelPageService.getChannel(resUser.getChannelId());
			if(channel != null) putChannelResourceCategoryToRequest(channel);
			//System.out.println("channel.getChannelId: " + channel.getChannelId());
		}*/
		
		if (cur_resource.getGradeId() != null)
			gradeId = cur_resource.getGradeId();

		return "Edit_Resource";
	}
	
	/**
	 * 得到当前要操作的资源对象放到 cur_resource 中, 如果没有则返回 false.
	 * @return
	 */
	protected boolean getCurrentResource() {
		// 获得参数.
		int resourceId = param_util.getIntParam("resourceId");
		if (resourceId == 0) {
			addActionError("未给出要操作的资源标识或标识非法");
			return false;
		}
		
		// 获得资源对象.
		this.cur_resource = res_svc.getResource(resourceId);
		if (this.cur_resource == null) {
			addActionError("未找到指定标识的资源");
			return false;
		}
		return true;
	}
	
	/** 
	 * 将系统分类，用户分类，学科放到 request 中.
	 */
	protected void putResourceCategoryAndSubjectList() {
		putResourceSystemCategory();
		putResourceUserCategory();
		putMetaSubjectList();
	}
	
	protected void putChannelResourceCategoryToRequest(Channel  channel)
	{
		Object cate_tree = cate_svc.getCategoryTree("channel_resource_" + channel.getChannelId());
		setRequestAttribute("channel_resource_categories", cate_tree);
	}

	/** 将系统资源分类放到 request 中 */
	protected void putResourceSystemCategory() {
		CategoryTreeModel res_cate = getCategoryService().getCategoryTree(getResourceSystemCategoryItemType());
		setRequestAttribute("res_cate", res_cate);
	}

	/** 将资源类型类放到 request 中. */
	protected void putResourceType() {
		CategoryTreeModel res_type = getCategoryService().getCategoryTree(CategoryService.RESOURCE_TYPE_TYPE);
		setRequestAttribute("res_type", res_type);
	}
	
	/** 将个人资源分类放到 request 中. */
	protected void putResourceUserCategory() {
		CategoryTreeModel user_res_cate = getCategoryService().getCategoryTree(getResourceUserCategoryItemType());
		setRequestAttribute("user_res_cate", user_res_cate);
	}
	
	/** 将学科列表放到 request 中. */
	protected void putMetaSubjectList() {
		List<MetaSubject> subject_list = subj_svc.getMetaSubjectList();
		setRequestAttribute("subject_list", subject_list);
	}
	
	/** 把学段列表放到 request 中. */
	/*TODO:检查这里，使用了基类的方法
	protected void putGradeList() {
		Object grade_list = subj_svc.getGradeList();
		request.setAttribute("gradeList", grade_list);
	}
	*/
	
	/** 计算资源系统分类树的 itemType */
	private String getResourceSystemCategoryItemType() {
		return CategoryService.RESOURCE_CATEGORY_TYPE;
	}
	
	
	
	/**
	 * 移动选定资源到目标分类.
	 * @param moveType 移动类型  user or admin
	 * @return
	 */
	protected String move(String moveType)	{
		// 获得资源标识参数.
		if (getResourceIds() == false) return ERROR;
		
		// 得到目标分类参数. 
		Integer sysCateId = param_util.getIntParamZeroAsNull("sysCateId");
		Integer userCateId = param_util.getIntParamZeroAsNull("userCateId");
		if ("admin".equals(moveType))
		{
			/// enableSysCate=true;
		}
		if (ParamUtil.MINUS_ONE.equals(sysCateId) && ParamUtil.MINUS_ONE.equals(userCateId)) {
			addActionError("目标系统分类和目标用户分类必须至少选择一者.");
			return ERROR;
		}
		
		// 验证分类正确.
		if (sysCateId != null && ParamUtil.MINUS_ONE.equals(sysCateId) == false) {
			Category sysCate = cate_svc.getCategory(sysCateId);
			if (sysCate == null) {
				addActionError("目标系统分类不存在.");
				return ERROR;
			}
			if (CategoryService.RESOURCE_CATEGORY_TYPE.equals(sysCate.getItemType()) == false) {
				addActionError("目标系统分类 '" + sysCate.getName() + "' 不是一个合法的系统资源分类.");
				return ERROR;
			}
		}
		if (userCateId != null && ParamUtil.MINUS_ONE.equals(userCateId) == false) {
			Category userCate = cate_svc.getCategory(userCateId);
			if (userCate == null) {
				addActionError("目标用户分类不存在.");
				return ERROR;
			}
			String itemType = this.getResourceUserCategoryItemType();
			if (itemType.equals(userCate.getItemType()) == false) {
				addActionError("用户分类不正确.");
				return ERROR;
			}
		}
		
		// 对每个资源做移动操作.
		int oper_count = 0;
		for (Integer resourceId : res_ids) {
			// 获得要操作的资源.
			Resource resource = res_svc.getResource(resourceId);
			if (resource == null) {
				addActionError("未找到标识为 " + resourceId + " 的资源");
				continue;
			}
			// 验证是这个用户的，(权限验证)
			if (resource.getUserId() != getLoginUser().getUserId()) {
				addActionError("权限不足. 试图操作他人的资源.");
				continue;
			}

			if (ParamUtil.MINUS_ONE.equals(sysCateId) == false)
				resource.setSysCateId(sysCateId);
			if (ParamUtil.MINUS_ONE.equals(userCateId) == false)
				resource.setUserCateId(userCateId);
			
			// 移动分类.
			res_svc.moveResourceCategory(resource);
			
			++oper_count;
			addActionMessage("资源 " + resource.toDisplayString() + " 成功移动到了目标分类.");
		}
		
		addActionMessage("共移动了 " + oper_count + " 个资源到目标分类.");
		
		return SUCCESS;
	}
	
	/** 计算用户个人 资源分类树的 itemType */
	protected String getResourceUserCategoryItemType() {
		return CommonUtil.toUserResourceCategoryItemType(getLoginUser().getUserId());
	}
	
	/**
	 * 检测是否对指定资源具有指定操作的权限
	 * 
	 * @param resource
	 * @param cmd
	 * @return
	 */
	protected boolean checkRight(Resource resource, String cmd) {
		return true;
	}

	protected static final long FSIZE_K = 1024L; // 1K
	protected static final long FSIZE_M = 1024L * 1024L; // 1M
	// protected static final long FSIZE_MAX = 1024L * 1024L * 100; // 100M
		
	/**
	 * 计算已经上传的(资源+照片)总量和剩余的空间
	 *
	 * @param resSize
	 * @return
	 */
	protected String calculate(String totalSize, long longTotalSize, long resSize) {
		log.info("已经上传的资源总量：" + resSize);
		log.info("剩余的空间：" + (longTotalSize - resSize));

		// 小于1K
		if (resSize < FSIZE_K) {
			if (resSize == 0) {
				return "您还没有上传任何资源！您的空间是：<b>" + totalSize + "M</b> ！";
			} else {
				return "您已经使用了 <b><1K</b> 空间！ 目前还剩余：<b>" + (int) ((longTotalSize - resSize) / FSIZE_M) + "M</b> 空间！";
			}
		}

		// 大于1K，小于1M
		if (resSize < FSIZE_M) {
			return "您已经使用了 <b>>" + (resSize / FSIZE_K) + "K</b> 空间！目前还剩余：<b>" + (int) ((longTotalSize - resSize) / FSIZE_M) + "M</b> 空间！";
		}

		// 大于1M，小于系统的设置的？M
		if (resSize < longTotalSize) {
			return "您已经使用了 <b>>" + (resSize / FSIZE_M) + "M</b> 空间！目前还剩余：<b>" + (int) ((longTotalSize - resSize) / FSIZE_M) + "M</b> 空间！";
		} else
			return "您已经使用了超过 <b>" + totalSize + "M</B> 的空间！目前已经无法再上传资源！";
	}
	
	/** 分类服务的set方法 */
	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}

	/** 分类服务 */
	public CategoryService getCategoryService() {
		 return this.cate_svc ;
	}
	
	/** 学科服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}
	
	/** 用户服务 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}
	
	/** 资源服务 */
	public void setResourceService(ResourceService res_svc) {
		this.res_svc = res_svc;
	}

	public ResTypeService getRestype_svc() {
		return restype_svc;
	}

	public void setRestype_svc(ResTypeService restype_svc) {
		this.restype_svc = restype_svc;
	}

	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId =resourceId;
	}
	
}
