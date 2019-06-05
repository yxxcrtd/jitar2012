package cn.edustar.jitar.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ServletContextAware;

import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.ResourceDao;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CommentQueryParam;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.EventManager;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.UserResourceQueryParam;
import cn.edustar.jitar.service.ViewCountService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 资源服务接口的实现
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 29, 2008 2:51:20 PM
 */
public class ResourceServiceImpl extends DocumentServiceImpl implements ResourceService, ServletContextAware {

	/** 日志记录器。 */
	private static final Log log = LogFactory.getLog(ResourceServiceImpl.class);

	/** 资源数据库实现 */
	private ResourceDao res_dao;

	/** 标签服务 */
	private TagService tag_svc;

	/** 群组服务 */
	private GroupService group_svc;

	/** 评论服务 */
	private CommentService cmt_svc;

	/** 统计更新服务 */
	private StatService statService;

	/** 事件服务 */
	private EventManager evt_mgr;

	/** 点击率服务 */
	private ViewCountService viewcount_svc;

	private PrepareCourseService prepareCourseService;

	/** 学科服务 */
	private SubjectService subj_svc;

	private ServletContext servlet_ctxt;

	/** 资源数据库实现 */
	public void setResourceDao(ResourceDao res_dao) {
		this.res_dao = res_dao;
	}

	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}

	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}

	/** 评论服务 */
	public void setCommentService(CommentService cmt_svc) {
		this.cmt_svc = cmt_svc;
	}

	/** 统计更新服务 */
	public void setStatService(StatService statService) {
		this.statService = statService;
	}

	/** 事件服务 */
	public void setEventManager(EventManager evt_mgr) {
		this.evt_mgr = evt_mgr;
	}

	/** 学科服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/** 点击率服务 */
	public void setViewCountService(ViewCountService viewcount_svc) {
		this.viewcount_svc = viewcount_svc;
	}

	public PrepareCourseService getPrepareCourseService() {
		return prepareCourseService;
	}

	public void setPrepareCourseService(PrepareCourseService prepareCourseService) {
		this.prepareCourseService = prepareCourseService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servlet_ctxt = servletContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#getResource(int)
	 */
	public Resource getResource(int resourceId) {
		return res_dao.getResource(resourceId);
	}

	public List<Resource> getTodayResources(int userId){
		return res_dao.getTodayResources(userId);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#getResourceList(cn.edustar.jitar.service.ResourceQueryParam, cn.edustar.data.Pager)
	 */
	public List<ResourceModelEx> getResourceList(ResourceQueryParam param, Pager pager) {
		// 得到 Resource 集合.
		List<Resource> list = res_dao.getResourceList(param, pager);

		// 包装为 model list.
		List<ResourceModelEx> model_list = wrapResourceModelEx(list);

		// 如果要获取 systemCategory 则合并 systemCategory.
		if (param.retrieveSystemCategory) {
			super.joinCategory(model_list, new RmeSysCateSupport(), null);
		}

		// 如果要获取 userCategory 则合并 userCategory.
		if (param.retrieveUserCategory) {
			super.joinCategory(model_list, new RmeUserCateSupport(), null);
		}

		return model_list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#getResourceDataTable(cn.edustar.jitar.service.UserResourceQueryParam, cn.edustar.data.Pager)
	 */
	public DataTable getResourceDataTable(UserResourceQueryParam param, Pager pager) {
		List<Object[]> list = res_dao.getResourceDataTable(param, pager);
		if (list == null || list.size() == 0) {
			return DataTable.EMPTY_DATATABLE;
		}
		DataTable dt = new DataTable(new DataSchema(param.selectFields));
		dt.addList(list);
		return dt;
	}

	/** ResourceModelEx System CategorySupport implement. */
	private static final class RmeSysCateSupport implements CategorySupport {
		public Integer getCategoryId(Object o) {
			return ((ResourceModelEx) o).getSysCateId();
		}

		public void setCategory(Object o, Category category) {
			((ResourceModelEx) o).setSystemCategory(category);
		}
	}

	/** ResourceModelEx User CategorySupport implement. */
	private static final class RmeUserCateSupport implements CategorySupport {
		public Integer getCategoryId(Object o) {
			return ((ResourceModelEx) o).getUserCateId();
		}

		public void setCategory(Object o, Category category) {
			((ResourceModelEx) o).setUserCategory(category);
		}
	}

	/**
	 * 将一个 List[Resource] 包装为 List[ResourceModelEx] .
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<ResourceModelEx> wrapResourceModelEx(List<Resource> list) {
		if (list == null || list.size() == 0)
			return CommonUtil.EMPTY_LIST;
		List<ResourceModelEx> model_list = new ArrayList<ResourceModelEx>(list
				.size());
		for (Resource resource : list) {
			model_list.add(ResourceModelEx.wrap(resource));
		}
		return model_list;
	}

	/** 对外发布一个事件, this 做为发布者 */
	private void publishEvent(String eventName, Object eventObject) {
		if (evt_mgr == null) {
			if (log.isDebugEnabled())
				log.debug("event service is null when publishEvent: " + eventName);
			return;
		}
		evt_mgr.publishEvent(eventName, this, eventObject);
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#createResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void createResource(Resource resource) {
		if (resource == null)
			throw new IllegalArgumentException("resource == null");

		// 参数规范化
		canocializeResource(resource);

		// 检查分类存在和合法
		checkRefObject(resource);

		// 物理创建资源记录
		res_dao.createResource(resource);

		// 写入标签，如果有的话
		String[] tags = tag_svc.parseTagList(resource.getTags());
		if (tags.length > 0) {
			tag_svc.createUpdateMultiTag(resource.getResourceId(), ObjectType.OBJECT_TYPE_RESOURCE, tags, null);
		}

		// 根据审核状态，更新统计数据
		incResourceStat(resource);

		// 发布创建事件
		publishEvent(EVENT_RESOURCE_CREATED, resource);
	}
	
	public String uploadify() {
		
		return "";
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#updateResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void updateResource(Resource resource) {
		// 得到原资源信息
		Resource origin_resource = this.res_dao.getResource(resource.getResourceId());
		if (origin_resource == null)
			throw new RuntimeException("未能找到标识为：" + resource.getResourceId() + "，的资源！");
		res_dao.evict(origin_resource);

		// 复制不修改的属性
		resource.setObjectUuid(origin_resource.getObjectUuid());
		resource.setUserId(origin_resource.getUserId());
		resource.setAuditState(origin_resource.getAuditState());
		resource.setDelState(origin_resource.getDelState());
		resource.setDownloadCount(origin_resource.getDownloadCount());
		resource.setHref(origin_resource.getHref());
		resource.setFsize(origin_resource.getFsize());
		resource.setAddIp(origin_resource.getAddIp());
		resource.setUnitPathInfo(origin_resource.getUnitPathInfo());
		resource.setOrginPathInfo(origin_resource.getOrginPathInfo());
		resource.setApprovedPathInfo(origin_resource.getApprovedPathInfo());
		resource.setRcmdPathInfo(origin_resource.getRcmdPathInfo());
		resource.setUnitId(origin_resource.getUnitId());
		
		// 参数规范化.
		canocializeResource(resource);

		// 检查分类存在和合法.
		checkRefObject(resource);

		// 设置更新时刻属性.
		resource.setLastModified(new Date());

		// 物理修改资源记录.
		res_dao.updateResource(resource);

		// 更改 tags.
		if (resource.getTags().equals(origin_resource.getTags()) == false) {
			String[] old_tags = tag_svc.parseTagList(origin_resource.getTags());
			String[] new_tags = tag_svc.parseTagList(resource.getTags());
			tag_svc.createUpdateMultiTag(resource.getResourceId(), ObjectType.OBJECT_TYPE_RESOURCE, new_tags, old_tags);
		}

		// 根据状态更新统计数据
		updateResourceStat(resource, origin_resource);
	}

	/** 增加用户的一个资源计数, 同时增加站点一个资源计数. */
	private void incResourceStat(Resource resource) {
		if (resource.getAuditState() == Resource.AUDIT_STATE_OK)
			statService.incResourceCount(resource, 1);
	}

	/** 减少用户的一个资源计数, 同时增加站点一个资源计数. */
	private void decResourceStat(Resource resource) {
		if (resource.getAuditState() == Resource.AUDIT_STATE_OK)
			statService.incResourceCount(resource, -1);
	}

	/** 根据资源状态更新统计数据. */
	private void updateResourceStat(Resource resource, Resource origin_resource) {
		if (resource.getAuditState() == Resource.AUDIT_STATE_OK
				&& origin_resource.getAuditState() != Resource.AUDIT_STATE_OK)
			// 原来是未审核通过, 现在是审核通过, 则资源统计 + 1
			statService.incResourceCount(resource, 1);
		else if (resource.getAuditState() != Resource.AUDIT_STATE_OK
				&& origin_resource.getAuditState() == Resource.AUDIT_STATE_OK)
			// 原来是审核通过, 现在变成了未通过, 则资源统计 - 1
			statService.incResourceCount(resource, -1);

		// 否则不改变资源统计.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#deleteResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void deleteResource(Resource resource) {
		if (resource == null)
			throw new IllegalArgumentException("resource == null");

		res_dao.deleteResourceFromPrepareCourseStage(resource.getId());
		// 如果已经删除了，则不进行操作.
		if (resource.getDelState())
			return;

		// 设置其被删除状态.
		int update_count = res_dao.updateResourceDeleteStatus(resource.getId(),	true);
		if (update_count != 1) {
			log.warn("deleteResource id=" + resource.getId() + " update_count="
					+ update_count + ", but MUST be 1");
		}

		// 更新数量统计信息.
		decResourceStat(resource);

		// 更改点击率的修改
		viewcount_svc.changeViewCountdDelStatus(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(), resource.getResourceId(), 1);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#recoverResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void recoverResource(Resource resource) {
		if (resource == null)
			throw new IllegalArgumentException("resource == null");

		// 如果未删除，则不进行操作.
		if (resource.getDelState() == false)
			return;

		// 恢复其被删除状态.
		int update_count = res_dao.updateResourceDeleteStatus(resource.getId(), false);
		if (update_count != 1) {
			log.warn("recoverResource id=" + resource.getId() + " update_count=" + update_count + ", but MUST be 1");
		}

		// 更新数量统计信息.
		incResourceStat(resource);

		// 更改点击率的修改
		viewcount_svc.changeViewCountdDelStatus(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(), resource.getResourceId(), 0);

	}

	/** 规范化 resource 中某些参数, 如 null 用 "" 安全的代替等 */
	private void canocializeResource(Resource resource) {
		// categoryId = 0 当作 null 处理
		if (CommonUtil.isZeroOrNull(resource.getSysCateId()))
			resource.setSysCateId(null);

		if (CommonUtil.isZeroOrNull(resource.getUserCateId()))
			resource.setUserCateId(null);

		if (resource.getHref() == null)
			resource.setHref("");

		// 标签规范化
		resource.setTags(CommonUtil.standardTagsString(resource.getTags()));
	}

	/** 检查所给分类、学科学段存在性和合法性. */
	private void checkRefObject(Resource resource) {
		// 验证系统分类存在性.
		if (resource.getSysCateId() != null) {
			Category sys_cate = cate_svc.getCategory(resource.getSysCateId());
			if (sys_cate == null)
				throw new RuntimeException("找不到指定标识的系统分类");
		}

		// 验证用户分类存在, 以及是资源所属用户的.
		if (resource.getUserCateId() != null) {
			Category user_cate = cate_svc.getCategory(resource.getUserCateId());
			String itemType = CommonUtil.toUserResourceCategoryItemType(resource.getUserId());
			if (user_cate == null || itemType.equals(user_cate.getItemType()) == false)
				throw new RuntimeException("不正确的用户分类");
		}

		// 检测元学科是否存在
		if (resource.getSubjectId() != null) {
			MetaSubject msubj = subj_svc.getMetaSubjectById(resource.getSubjectId());
			if (msubj == null)
				throw new RuntimeException("不正确的元学科类型.");
		}

		// 检测学段是否存在
		if (resource.getGradeId() != null) {
			Grade grade = subj_svc.getGrade(resource.getGradeId());
			if (grade == null)
				throw new RuntimeException("不正确的学段或年级.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#updateResourceShareMode(cn.edustar.jitar.pojos.Resource,
	 *      int)
	 */
	public void updateResourceShareMode(Resource resource, int shareMode) {
		res_dao.updateResourceShareMode(resource.getResourceId(), shareMode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#crashResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void crashResource(Resource resource) {
		if (resource == null)
			throw new IllegalArgumentException("resource == null");

		// 发布即将被销毁事件.
		this.publishEvent(EVENT_RESOURCE_CRASH, resource);

		// 删除该资源相关的标签.
		tag_svc.deleteTagRefByObject(ObjectType.OBJECT_TYPE_RESOURCE, resource.getResourceId());

		// 删除该资源发布到的协作组, 并更新 Group 统计数据.
		group_svc.deleteGroupResourceByResource(resource);

		// 删除该资源的评论.
		cmt_svc.deleteCommentByObject(ObjectType.OBJECT_TYPE_RESOURCE, resource.getResourceId());

		// 删除资源记录, 并在完成所有数据库操作后 flush.
		res_dao.deleteResource(resource);
		res_dao.flush();

		// 删除资源实体文件.
		deleteResourceFile(resource.getHref());

		// 删除点击率
		viewcount_svc.deleteViewCount(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(), resource.getResourceId());

	}

	/**
	 * 删除资源物理文件.
	 * 
	 * @param href
	 */
	private void deleteResourceFile(String href) {
		if (href == null || href.length() == 0)
			return;
		href = href.trim();
		// 重要: 仅删除位于 'user/' 虚拟目录下面的资源.
		if (href.startsWith("user/") == false)
			return;

		// TODO: 得到物理文件地址, 这里算法可能以后要改进.
		
		ServletContext sc = this.servlet_ctxt;
		String fileUserConfigPath = sc.getInitParameter("userPath");
		String filePath="";
		if(fileUserConfigPath=="" || fileUserConfigPath==null)
			filePath = sc.getRealPath(href);
		else
		{
			href=href.replace('/', '\\');
			if(!fileUserConfigPath.endsWith("\\"))
				if(href.startsWith("\\"))
					filePath=fileUserConfigPath+href;
				else
					filePath=fileUserConfigPath+"\\"+href;
			else
				if(href.startsWith("\\"))
					filePath=fileUserConfigPath+href.substring(1);
				else
					filePath=fileUserConfigPath+href;
		}
		
		String fileName=filePath;
		//String fileName = this.servlet_ctxt.getRealPath("/" + href);
		File file = new File(fileName);
		if (file.exists() == false)
			return;

		// 删除该物理文件.
		if (file.delete() == false)
			file.deleteOnExit();

		log.debug("删除资源物理文件位于 " + fileName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#moveResourceCategory(cn.edustar.jitar.pojos.Resource)
	 */
	public void moveResourceCategory(Resource resource) {
		res_dao.updateResourceCategory(resource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#statResourceComment(cn.edustar.jitar.pojos.User)
	 */
	public Object statResourceComment(User user) {
		// 构造查询参数.
		CommentQueryParam param = new CommentQueryParam();
		if (user != null)
			param.aboutUserId = user.getUserId();
		param.objectType = ObjectType.OBJECT_TYPE_RESOURCE;

		// 按照资源标识统计评论数.
		List<Object[]> list = cmt_svc.statCommentCountByUserAndObject(param);
		if (list == null || list.size() == 0)
			return null;

		// 更新资源的评论数字段.
		for (Object[] obj_a : list) {
			Integer resourceId = CommonUtil.safeXtransHiberInteger(obj_a[0]);
			if (resourceId == null)
				continue;
			Integer commentCount = CommonUtil.safeXtransHiberInteger(obj_a[1]);
			if (commentCount == null)
				commentCount = 0;
			res_dao.updateResourceCommentCount(resourceId, commentCount);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#batchClearResourceUserCategory(int)
	 */
	public void batchClearResourceUserCategory(int userCateId) {
		this.res_dao.batchClearResourceUserCategory(userCateId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#auditResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void auditResource(Resource resource) {
		if (resource.getAuditState() == Resource.AUDIT_STATE_OK)
			return;
		resource.setAuditState(Resource.AUDIT_STATE_OK);
		this.res_dao.updateResource(resource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#unauditResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void unauditResource(Resource resource) {
		if (resource.getAuditState() == Resource.AUDIT_STATE_WAIT_AUDIT)
			return;
		resource.setAuditState(Resource.AUDIT_STATE_WAIT_AUDIT);
		this.res_dao.updateResource(resource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#rcmdResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void rcmdResource(Resource resource) {
		if (resource.getRecommendState())
			return;
		resource.setRecommendState(true);
		this.res_dao.updateResource(resource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#unrcmdResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void unrcmdResource(Resource resource) {
		if (!resource.getRecommendState())
			return;
		resource.setRecommendState(false);
		this.res_dao.updateResource(resource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#updateResourceSysCate(cn.edustar.jitar.pojos.Resource,
	 *      java.lang.Integer)
	 */
	public void updateResourceSysCate(Resource resource, Integer sysCateId) {
		res_dao.updateResourceSysCate(resource, sysCateId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#incResourceCommentCount(int)
	 */
	public void incResourceCommentCount(int resourceId) {
		res_dao.incResourceCommentCount(resourceId);
	}

	/**
	 * @return
	 */
	public StatService getStatService() {
		return statService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ResourceService#increaseViewCount(int)
	 */
	public void increaseViewCount(int resourceId) {
		res_dao.increaseViewCount(resourceId);
		viewcount_svc.incViewCount(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(), resourceId, 1);
	}
	
	public void increaseViewCount(int resourceId, int count){
	    res_dao.increaseViewCount(resourceId, count);     
        viewcount_svc.incViewCount(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(), resourceId, count);
	}
	public List<Resource> getUserResourceOfAll(int userId)
	{
		return this.res_dao.getUserResourceOfAll(userId);
	}
	
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.ResourceService#totalResourceSize(int)
	 */
	public int totalResourceSize(int userId) {
		return res_dao.totalResourceSize(userId);
	}
	
	public void setPushState(Resource resource, int pushState)
	{
		this.res_dao.setPushState(resource, pushState);
	}

}
