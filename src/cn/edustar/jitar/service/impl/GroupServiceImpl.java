package cn.edustar.jitar.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.dao.GroupDao;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.model.PhotoModel;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.model.VideoModel;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupArticle;
import cn.edustar.jitar.pojos.GroupDataQuery;
import cn.edustar.jitar.pojos.GroupKTUser;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.GroupMutil;
import cn.edustar.jitar.pojos.GroupNews;
import cn.edustar.jitar.pojos.GroupPhoto;
import cn.edustar.jitar.pojos.GroupResource;
import cn.edustar.jitar.pojos.GroupVideo;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.query.VideoQueryParam;
import cn.edustar.jitar.service.BbsService;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.EventManager;
import cn.edustar.jitar.service.GroupArticleQueryParam;
import cn.edustar.jitar.service.GroupArticleService;
import cn.edustar.jitar.service.GroupMemberQueryParam;
import cn.edustar.jitar.service.GroupNewsQueryParam;
import cn.edustar.jitar.service.GroupPlacardQueryParam;
import cn.edustar.jitar.service.GroupQueryParam;
import cn.edustar.jitar.service.GroupKTUserService;
import cn.edustar.jitar.service.GroupResourceQueryParam;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.LeavewordService;
import cn.edustar.jitar.service.LinkService;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.PageKey;
import cn.edustar.jitar.service.PageService;
import cn.edustar.jitar.service.PhotoQueryParam;
import cn.edustar.jitar.service.PlacardService;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 群组服务实现.
 * 
 *
 *
 */
public class GroupServiceImpl implements GroupService, GroupArticleService {
	/** 文章记录器 */
	private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
	
	/** 访问群组数据库的对象。 */
	private GroupDao group_dao;
	
	/** 标签服务。 */
	private TagService tag_svc;
	
	/** 缓存服务。 */
	private CacheService cache_svc;
	
	/** 事件服务 */
	private EventManager evt_mgr;
	
	/** 页面服务 */
	private PageService page_svc;
	
	/** 短消息服务 */
	private MessageService msg_svc;
	
	/** 用户服务 */
	private UserService user_svc;
	
	/** 论坛服务 */
	private BbsService bbs_svc;
	
	/** 统计服务 */
	private StatService stat_svc;
	
	/** 公告服务 */
	private PlacardService pla_svc;
	
	/** 协作组链接服务 */
	private LinkService link_svc;
	
	/** 留言服务 */
	private LeavewordService lw_svc;
	
	/**课题组课题负责人*/
	private GroupKTUserService groupKTUserService;
	
	/** 文档有关服务的实现 */
	private DocumentServiceImpl doc_impl = new DocumentServiceImpl();
	
	/** 设置分类服务 */
	public void setCategoryService(CategoryService cate_svc) {
		doc_impl.setCategoryService(cate_svc);
	}
	
	/** 设置访问群组数据库的对象。 */
	public void setGroupDao(GroupDao group_dao) {
		this.group_dao = group_dao;
	}
	
	/** 标签服务。 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}
	
	/** 缓存服务。 */
	public void setCacheService(CacheService cache_svc) {
		this.cache_svc = cache_svc;
	}

	/** 事件服务 */
	public void setEventManager(EventManager evt_mgr) {
		this.evt_mgr = evt_mgr;
	}

	/** 页面服务 */
	public void setPageService(PageService page_svc) {
		this.page_svc = page_svc;
	}

	/** 短消息服务 */
	public void setMessageService(MessageService msg_svc) {
		this.msg_svc = msg_svc;
	}

	/** 用户服务 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}
	
	/** 论坛服务 */
	public void setBbsService(BbsService bbs_svc) {
		this.bbs_svc = bbs_svc;
	}

	/** 统计服务 */
	public void setStatService(StatService stat_svc) {
		this.stat_svc = stat_svc;
	}
	
	/** 公告服务 */
	public void setPlacardService(PlacardService pla_svc) {
		this.pla_svc = pla_svc;
	}
	
	/** 协作组链接服务 */
	public void setLinkService(LinkService link_svc) {
		this.link_svc = link_svc;
	}

	/** 留言服务 */
	public void setLeavewordService(LeavewordService lw_svc) {
		this.lw_svc = lw_svc;
	}
	
	/** 根据群组标识计算一个群组在缓存中的键，注意不能和其它对象重复了。 */
	private static final String keyForCache(String groupName) {
		return "g." + groupName + ".group";
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.GroupService#isValidGroupTitle(java.lang.String)
	 */
	public boolean isValidGroupTitle(String title) {
		if (title == null || title.length() == 0)
			return false;
		
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.GroupService#isValidGroupName(java.lang.String)
	 */
	public boolean isValidGroupName(String name) {
		if (!CommonUtil.isValidName(name))
			return false;
		
		// TODO: 验证长度等.
	
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.GroupService#getGroup(int)
	 */
	public Group getGroup(int groupId) {
		return this.group_dao.getGroup(groupId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.GroupService#getGroupByName(java.lang.String)
	 */
	public Group getGroupByName(String groupName) {
		if (groupName == null || groupName.length() == 0)
			throw new java.lang.IllegalArgumentException("groupName is empty");
		return this.group_dao.getGroupByName(groupName);
	}

	public Group getGroupByGuid(String groupGuid)
	{
		return this.group_dao.getGroupByGuid(groupGuid);
	}
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupByTitle(java.lang.String)
	 */
	public Group getGroupByTitle(String groupTitle) {
		return group_dao.getGroupByTitle(groupTitle);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getUserUnanditGroupCount(cn.edustar.jitar.pojos.User)
	 */
	public int getUserUnanditGroupCount(User user) {
		return group_dao.getUserUnanditGroupCount(user);
	}

	/** 群组标识到名字的映射表 */
	private Hashtable<Integer, String> id_name_map = new Hashtable<Integer, String>();
	/** 当删除协作组的时候要重建此映射表, 长时间运行之后重建也是一个不错的选择. */
	private void recreateIdNameMap() {
		this.id_name_map = new Hashtable<Integer, String>();
	}
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupMayCached(int)
	 */
	public Group getGroupMayCached(int groupId) {
		// 尝试从 id 到名字进行映射.
		String name = id_name_map.get(groupId);
		if (name != null)
			return getGroupMayCached(name);
		
		// 如果没有则现在进行加载, 并放到 id_name_map 中.
		Group group = getGroup(groupId);
		if (group != null) {
			id_name_map.put(group.getGroupId(), group.getGroupName());
			String key = keyForCache(group.getGroupName());
			cache_svc.put(key, group);
		}
		return group;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupMayCached(java.lang.String)
	 */
	public Group getGroupMayCached(String groupName) {
		// 先从缓存中查找.
		String key = keyForCache(groupName);
		Group group = (Group)this.cache_svc.get(key);
		if (group != null) return group;
		
		// 没有则加载.
		group = getGroupByName(groupName);
		if (group == null) return null;
		
		// 放到缓存中，并返回.
		this.cache_svc.put(key, group);
		return group;
	}

	/**
	 * 更新系统中协作组统计信息.
	 * @param group
	 * @param incCount
	 */
	private void incGroupCount(Group group, int incCount) {
		if (stat_svc != null)
			stat_svc.incGroupCount(group, incCount);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.GroupService#createGroup(cn.edustar.jitar.pojos.Group)
	 */
	public void createGroup(Group group) {
		groupNotNull(group);
		
		if (logger.isInfoEnabled()) {
			logger.info("即将创建群组, group = " + group);
		}
		
		// 规范化一些数据.
		String[] tags = tag_svc.parseTagList(group.getGroupTags());
		group.setGroupTags(CommonUtil.standardTagsString(tags));
		
		// 发布即将创建群组事件.
		evt_mgr.publishEvent(EVENT_GROUP_CREATING, this, group);
		
		// 1. 保存群组本身.
		group.setUserCount(1);
		group_dao.createGroup(group);
		
		// 2. 保存群组的标签.
		tag_svc.createUpdateMultiTag(group.getGroupId(), ObjectType.OBJECT_TYPE_GROUP, tags, null);
		
		// 3. 保存群组创建者关系 - 群组管理员.
		GroupMember gm = new GroupMember();
		gm.setGroupId(group.getGroupId());
		gm.setUserId(group.getCreateUserId());
		gm.setStatus(GroupMember.STATUS_NORMAL);
		gm.setGroupRole(GroupMember.GROUP_ROLE_MANAGER);
		gm.setJoinDate(group.getCreateDate());
		group_dao.saveGroupMember(gm);

		// 为该群组创建初始化 page.
		checkAndInitGroupPage(group);
		
		// 群组信息可能发生更改，再保存一次, ? flush()
		group_dao.updateGroup(group);
		
		// 统计数 +1 .
		incGroupCount(group, 1);
		
		// 发布群组创建完成事件.
		evt_mgr.publishEvent(EVENT_GROUP_CREATED, this, group);
		
		if (logger.isInfoEnabled()) {
			logger.info("群组创建完成, group = " + group);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#updateGroup(cn.edustar.jitar.pojos.Group)
	 */
	public void updateGroup(Group group) {
		groupNotNull(group);
		
		// 得到原群组信息.
		Group origin_group = group_dao.getGroup(group.getGroupId());
		if (origin_group == null) 
			throw new RuntimeException("指定要更新的群组不存在了?"); 
		group_dao.evict(origin_group);
			
		// 规范化一些数据.
		String[] tags = tag_svc.parseTagList(group.getGroupTags());
		group.setGroupTags(CommonUtil.standardTagsString(tags));

		// 这些属性要么不能被修改，要么不从页面上面传递.
		group.setGroupGuid(origin_group.getGroupGuid());
		group.setGroupName(origin_group.getGroupName());
		group.setCreateUserId(origin_group.getCreateUserId());
		group.setCreateDate(origin_group.getCreateDate());
		group.setIsBestGroup(origin_group.getIsBestGroup());
		group.setGroupState(origin_group.getGroupState());
		group.setAttributes(origin_group.getAttributes());
		
		// 1. 更新群组本身.
		group_dao.updateGroup(group);
		
		// 2. 修改群组的标签.
		if (group.getGroupTags().equals(origin_group.getGroupTags()) == false) {
			String[] origin_tags = tag_svc.parseTagList(origin_group.getGroupTags());
			tag_svc.createUpdateMultiTag(group.getGroupId(), ObjectType.OBJECT_TYPE_GROUP, tags, origin_tags);
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("群组修改完成, group = " + group);
		}
		
		// 更新缓存.
		this.removeGroupCache(group);
	}
	
	/**
	 * 检查群组是否有了必要的页面，并在没有的时候初始化建立该页面.
	 */
	private void checkAndInitGroupPage(Group group) {
		// 检查是否有主页.
		PageKey group_index_pk = new PageKey(ObjectType.OBJECT_TYPE_GROUP, group.getGroupId(), "index");
		Page page = page_svc.getPageByKey(group_index_pk);
		if (page != null) return;		// 已经有了则返回.
		
		// 复制模板 page 及其所属 widget
		// 从 system 的 'group.index' 复制页面模板.
		String cateuuId=getGroupCateUuid(group);
		if(cateuuId.equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ))
		{
			page_svc.duplicatePage(PageKey.SYSTEM_GROUPKT_INDEX,group_index_pk, group.getGroupTitle() + "首页");
			
		}else{
			page_svc.duplicatePage(PageKey.SYSTEM_GROUP_INDEX,group_index_pk, group.getGroupTitle() + "首页");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.GroupService#auditGroup(cn.edustar.jitar.pojos.Group)
	 */
	public void auditGroup(Group group) {
		groupNotNull(group);
		
		// 设置群组审核状态 - 正常.
		int update_count = group_dao.updateGroupState(group.getGroupId(), Group.GROUP_STATE_NORMAL);
		
		// 创建该群组的 页面，如果还没有的话.
		createGroupPage(group);
		
		// 向群组的创建者发送一个短消息，告知其群组已经被审核通过了.此操作已经转移到业务代码中
		// sendGroupAuditedMessage(group);
		
		// 更新统计.
		incGroupCount(group, 1);
		
		// 刷新该群组的缓存, 如果有的话.
		removeGroupCache(group);
		
		// 发布 '群组审核' 事件.
		evt_mgr.publishEvent(EVENT_GROUP_AUDITED, this, group);
		
		if (logger.isDebugEnabled()) {
			logger.debug("群组 " + group + " 进行了审核操作, 更新记录数 = " + update_count);
		}
	}
	
	/** 向群组创建者发送一条短消息, 告知其所创建的群组已经审核通过了 */
	private void sendGroupAuditedMessage(Group group) {
		if (msg_svc == null) {
			logger.debug("未配置消息服务，没有发送审核通过消息给群组创建者");
			return;
		}
		
		// TODO: 消息弄得更好一些.
		Message message = new Message();
		message.setSendId(0);		// TODO: 审核者.

		message.setReceiveId(group.getCreateUserId());
		message.setTitle("您创建的群组 '" + group.getGroupTitle() + "' 已经通过审核");
		message.setContent("祝贺您，您创建的群组 '" + group.getGroupTitle() + "' 已经通过审核");
		
		msg_svc.sendMessage(message);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.GroupService#lockGroup(cn.edustar.jitar.pojos.Group, boolean)
	 */
	public void lockGroup(Group group, boolean lock) {
		groupNotNull(group);
		
		// 设置群组状态 - 锁定/解锁.
		int update_count = group_dao.updateGroupState(group.getGroupId(),lock ? Group.GROUP_STATE_LOCKED : Group.GROUP_STATE_NORMAL);
		
		// 更新统计.
		incGroupCount(group, lock ? -1 : 1);
		
		// 刷新该群组的缓存, 如果有的话.
		removeGroupCache(group);
		
		if (logger.isDebugEnabled()) {
			logger.debug("群组 " + group + " 进行了锁定/解锁操作, 更新记录数 = " + update_count);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#hideGroup(cn.edustar.jitar.pojos.Group, boolean)
	 */
	public void hideGroup(Group group, boolean hide) {
		groupNotNull(group);
		
		// 设置群组状态 - 隐藏/解除隐藏.
		/*int update_count =*/ group_dao.updateGroupState(group.getGroupId(),hide ? Group.GROUP_STATE_HIDED : Group.GROUP_STATE_NORMAL);
		
		// 更新统计.
		incGroupCount(group, hide ? -1 : 1);
		
		// 刷新该群组的缓存, 如果有的话.
		removeGroupCache(group);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.GroupService#deleteGroup(cn.edustar.jitar.pojos.Group)
	 */
	public void deleteGroup(Group group) {
		groupNotNull(group);
		
		// 设置群组状态 - 被删除.
		int update_count = group_dao.updateGroupState(group.getGroupId(),Group.GROUP_STATE_DELETED);
		
		// 统计数 -1.
		incGroupCount(group, -1);
		
		// 刷新该群组的缓存, 如果有的话.
		removeGroupCache(group);
		
		if (logger.isDebugEnabled()) {
			logger.debug("群组 " + group + " 进行了删除操作, 更新记录数 = " + update_count);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.GroupService#recoverGroup(cn.edustar.jitar.pojos.Group)
	 */
	public void recoverGroup(Group group) {
		groupNotNull(group); 
		
		// 设置群组状态 - 正常.
		int update_count = group_dao.updateGroupState(group.getGroupId(),Group.GROUP_STATE_NORMAL);
		
		// TODO: 可能该群组在审核期被删除，则可能没有执行初始化，还需要检查并进行初始化.
	
		// 统计数 +1 .
		incGroupCount(group, 1);
		
		// 刷新该群组的缓存, 如果有的话.
		removeGroupCache(group);
		
		if (logger.isDebugEnabled()) {
			logger.debug("群组 " + group + " 进行了恢复操作, 更新记录数 = " + update_count);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#setGroupBestGroup(cn.edustar.jitar.pojos.Group, boolean)
	 */
	public void setGroupBestGroup(Group group, boolean isBestGroup) {
		group_dao.setGroupBestGroup(group, isBestGroup);
		removeGroupCache(group);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#setGroupRecommend(cn.edustar.jitar.pojos.Group, boolean)
	 */
	public void setGroupRecommend(Group group, boolean isRecommend) {
		group_dao.setGroupRecommend(group, isRecommend);
		removeGroupCache(group);
	}
	
	
	/**
	 * 彻底删除指定的协作组.
	 * TODO: 各个 Service 都被 Spring 封装了事务 AOP 导致这里调用的时候两次进入 AOP.
	 */
	public void crashGroup(Group group) {
		groupNotNull(group);
		
		// 发布即将删除事件.
		this.evt_mgr.publishEvent(EVENT_GROUP_DELETING, this, group);
				
		// 删除标签.
		tag_svc.deleteTagRefByObject(ObjectType.OBJECT_TYPE_GROUP, group.getGroupId());
		
		// 删除协作组公告.
		pla_svc.deletePlacardByObject(ObjectType.OBJECT_TYPE_GROUP, group.getGroupId());
		
		// 删除协作组链接.
		link_svc.deleteLinkByObject(ObjectType.OBJECT_TYPE_GROUP, group.getGroupId());
		
		// 删除协作组留言.
		lw_svc.deleteLeavewordByObject(ObjectType.OBJECT_TYPE_GROUP, group.getGroupId());

		// 删除协作组的资源分类 (形式为 itemType = 'group_1234').
		String itemType = CommonUtil.toGroupResourceCategoryItemType(group.getGroupId());
		doc_impl.getCategoryService().deleteCategoryTree(itemType);
		
		// 删除协作组生成的页面和Widget.
		page_svc.deletePageAndWidgetByObject(ObjectType.OBJECT_TYPE_GROUP, group.getGroupId());
		
		// 删除协作组及其 GroupArticle, GroupResource etc.
		
		group_dao.deleteGroup(group);
		group_dao.flush();
		
		// 更新缓存.
		removeGroupCache(group);
		recreateIdNameMap();
		
		// 发布删除事件.
		this.evt_mgr.publishEvent(EVENT_GROUP_DELETED, this, group);
	}

	// 如果 group == null 则抛出一个 IllegalArgumentException 异常.
	private void groupNotNull(Group group) {
		if (group == null) 
			throw new IllegalArgumentException("group == null");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupList(cn.edustar.jitar.service.GroupQueryParam, cn.edustar.data.Pager)
	 */
	public List<Group> getGroupList(GroupQueryParam param, Pager pager) {
		if (param == null)
			throw new IllegalArgumentException("param == null");
		
		// 查询列表.
		return group_dao.getGroupList(param, pager);
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.GroupService#getGroupList()
	 */
	public List<Group> getGroupList() {
		return group_dao.getGroupList();
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.GroupService#updateGroupStat(int, java.lang.String, java.lang.String)
	 */
	public void updateGroupStat(int groupId, String beginDate, String endDate) {
		group_dao.updateGroupStat(groupId, beginDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupMemberList(cn.edustar.jitar.service.GroupMemberQueryParam, cn.edustar.data.Pager)
	 */
	public DataTable getGroupMemberList(GroupMemberQueryParam param, Pager pager) {
		List<Object[]> list = group_dao.getGroupMemberList(param, pager);
		
		// 组装为 DataTable.
		DataSchema schema = new DataSchema(param.fieldList);
		DataTable data_table = new DataTable(schema);
		data_table.addList(list);
		
		return data_table;
	}
	
	public GroupMember getGroupMemberById(int id){
		return group_dao.getGroupMemberById(id);
	}
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getUserJoinedGroupMemberList(int, cn.edustar.data.Pager)
	 */
	public List<User> getUserJoinedGroupMemberList(int userId, Pager pager) {
		return group_dao.getUserJoinedGroupMemberList(userId, pager);
	}
	
	/** 从 List<GroupMember> 中取出 groupId 构造一个数组 */
	@SuppressWarnings("unused")
	private int[] calcGroupArray(List<GroupMember> managed_g) {
		int[] result = new int[managed_g.size()];
		for (int i = 0; i < managed_g.size(); ++i)
			result[i] = managed_g.get(i).getGroupId();
		return result;
	}

	/** 更新指定群组的缓存 */
	private void removeGroupCache(Group group) {
		String key = keyForCache(group.getGroupName());
		cache_svc.remove(key);
	}
	
	/** 为指定群组创建其页面 */
	private void createGroupPage(Group group) {
		logger.warn("TODO: GroupService.createGroupPage() ");
	}

	/**得到群组的分类的UUID*/
	public String getGroupCateUuidById(int groupId)	{
		Group group=getGroup(groupId);
		return getGroupCateUuid(group);
	}
	
	public String getGroupCateUuid(Group group){
		Integer cateId=group.getCategoryId();
		if(cateId==null){
			return "";
		}
		Category cate=doc_impl.getCategoryService().getCategory(cateId);
		if(cate==null){
			return "";
		}
		return cate.getObjectUuid();
	}
	

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupUserByGroupIdAndUserId(int, int)
	 */
	public GroupMember getGroupMemberByGroupIdAndUserId(int groupId, int userId) {
		return group_dao.getGroupMemberByGroupIdAndUserId(groupId, userId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#createGroupMember(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void createGroupMember(GroupMember gm) {
		group_dao.saveGroupMember(gm);
		
		// 更新群组用户数量.
		updateGroupMemberCount(gm.getGroupId());
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#updateGroupMember(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void updateGroupMember(GroupMember gm) {
		group_dao.saveGroupMember(gm);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#createGroupMember(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void inviteGroupMember(GroupMember gm) {
		if (gm == null) throw new IllegalArgumentException("gm == null");

		// 创建记录.
		group_dao.saveGroupMember(gm);
		
		// 更新群组用户数量.
		updateGroupMemberCount(gm.getGroupId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.GroupService#auditGroupMember(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void auditGroupMember(GroupMember gm) {
		if (gm == null)
			throw new IllegalArgumentException("gm == null");
		
		if (gm.getStatus() != GroupMember.STATUS_WAIT_AUDIT) {
			logger.warn("auditGroupMember gid=" + gm.getGroupId() + ",uid=" + gm.getUserId() + " status != STATUS_WAIT_AUDIT");
			return;
		}

		int update_count = group_dao.updateGroupMemberStatus(gm, GroupMember.STATUS_NORMAL);
		if (update_count != 1) {
			logger.warn("auditGroupMember gid=" + gm.getGroupId() + ",uid=" + gm.getUserId() + ", update_count=" + update_count + ", but it's MUST 1");
		}
		
		// 更新群组用户数量
		updateGroupMemberCount(gm.getGroupId());
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#lockGroupMember(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void lockGroupMember(GroupMember gm) {
		group_dao.updateGroupMemberStatus(gm, GroupMember.STATUS_LOCKED);
		updateGroupMemberCount(gm.getGroupId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#unlockGroupMember(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void unlockGroupMember(GroupMember gm) {
		group_dao.updateGroupMemberStatus(gm, GroupMember.STATUS_NORMAL);
		updateGroupMemberCount(gm.getGroupId());
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#deleteGroupMember(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void deleteGroupMember(GroupMember gm) {
		if (gm == null) throw new IllegalArgumentException("gm == null");
		int update_count = group_dao.updateGroupMemberStatus(gm, GroupMember.STATUS_DELETING);
		
		if (update_count != 1) {
			logger.warn("deleteGroupMember gid=" + gm.getGroupId() + ",uid=" + gm.getUserId() + 
					", update_count=" + update_count + ", but it's MUST 1");
		}
		updateGroupMemberCount(gm.getGroupId());
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#destroyGroupMember(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void destroyGroupMember(GroupMember gm) {
		group_dao.destroyGroupMember(gm);
		updateGroupMemberCount(gm.getGroupId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#recoverGroupMember(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void recoverGroupMember(GroupMember gm) {
		if (gm == null) throw new IllegalArgumentException("gm == null");
		group_dao.updateGroupMemberStatus(gm, GroupMember.STATUS_NORMAL);
		updateGroupMemberCount(gm.getGroupId());
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#uninviteGroupMember(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void uninviteGroupMember(GroupMember gm) {
		if (gm == null) throw new IllegalArgumentException("gm == null");
		
		group_dao.destroyGroupMember(gm);
		updateGroupMemberCount(gm.getGroupId());
		
		// 也许以后可以给该用户发一封友好的通知短消息告知这件事情.
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#acceptGroupMemberInvite(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void acceptGroupMemberInvite(GroupMember gm) {
		gm.setStatus(GroupMember.STATUS_NORMAL);
		group_dao.saveGroupMember(gm);
		
		// 重新统计协作组成员数量.
		updateGroupMemberCount(gm.getGroupId());
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#rejectGroupMemberInvite(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void rejectGroupMemberInvite(GroupMember gm) {
		// 实现似乎和 destroyGroupMember 一样??
		group_dao.destroyGroupMember(gm);
		updateGroupMemberCount(gm.getGroupId());
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#cancelJoinRequest(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void cancelJoinRequest(GroupMember gm) {
		group_dao.destroyGroupMember(gm);
		updateGroupMemberCount(gm.getGroupId());
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#transferGroupToMember(cn.edustar.jitar.pojos.Group, cn.edustar.jitar.pojos.GroupMember)
	 */
	public void transferGroupToMember(Group group, GroupMember newCreator) {
		// 设置原主人为副管理员.
		GroupMember oldCreator = group_dao.getGroupMemberByGroupIdAndUserId(
				group.getGroupId(), group.getCreateUserId());
		if (oldCreator != null) {
			oldCreator.setGroupRole(GroupMember.GROUP_ROLE_VICE_MANAGER);
			oldCreator.setStatus(GroupMember.STATUS_NORMAL);
			group_dao.saveGroupMember(oldCreator);
		} else {
			// ?? 不存在, 一定哪里发生问题了. ??
			oldCreator = new GroupMember();
			oldCreator.setUserId(group.getCreateUserId());
			oldCreator.setGroupId(group.getGroupId());
			oldCreator.setGroupRole(GroupMember.GROUP_ROLE_VICE_MANAGER);
			oldCreator.setStatus(GroupMember.STATUS_NORMAL);
			group_dao.saveGroupMember(oldCreator);
		}
		
		// 设置新主人为管理员.
		newCreator.setGroupRole(GroupMember.GROUP_ROLE_MANAGER);
		newCreator.setStatus(GroupMember.STATUS_NORMAL);
		group_dao.saveGroupMember(newCreator);
		
		// 设置该新主人为该协作组的创建者.
		group_dao.updateGroupCreator(group.getGroupId(), newCreator.getUserId());
		group_dao.flush();
		
		// 更新缓存.
		removeGroupCache(group);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.GroupService#updateGroupUserCount(int)
	 */
	public int updateGroupMemberCount(int groupId) {
		int normal_user_count = group_dao.updateGroupMemberCount(groupId);
		if (normal_user_count == -1) {
			return normal_user_count;
		}
		return normal_user_count;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupActivistList(int)
	 */
	@SuppressWarnings("unchecked")
	public List getGroupActivistList(int groupId) {
		// TODO: CONFIG
		int count = 10;
		List<Object[]> list = group_dao.getGroupActivistList(groupId, count);
		return list;
		/*
		List<UserGroupMemberModel> model_list = new ArrayList<UserGroupMemberModel>(list.size());
		for (Object[] gm_u : list) {
			model_list.add(UserGroupMemberModel.wrap((GroupMember)gm_u[0], (User)gm_u[1]));
		}
		return model_list;
		*/
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupManager(int)
	 */
	public User getGroupManager(int groupId) {
		List<GroupMember> gm_list = group_dao.getGroupMemberByGroupIdAndRole(groupId, GroupMember.GROUP_ROLE_MANAGER);
		if (gm_list == null || gm_list.size() == 0) return null;
		
		GroupMember gm = gm_list.get(0);
		return user_svc.getUserById(gm.getUserId());
	}
	public List<User> getGroupManagers(int groupId) {
		List<GroupMember> gm_list = group_dao.getGroupMemberByGroupIdAndRole(groupId, GroupMember.GROUP_ROLE_MANAGER);
		if (gm_list == null || gm_list.size() == 0) return null;
		List<User> us=new ArrayList<User>();
		for(GroupMember gm : gm_list){
			User u=user_svc.getUserById(gm.getUserId());
			us.add(u);
		}
		return us;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getNewGroupMember(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List getNewGroupMember(int groupId, int count) {
		List<Object[]> list = group_dao.getNewGroupMember(groupId, count);
		return list;
		/*
		List<UserGroupMemberModel> model_list = new ArrayList<UserGroupMemberModel>(list.size());
		for (Object[] gm_u : list) {
			model_list.add(UserGroupMemberModel.wrap((GroupMember)gm_u[0], (User)gm_u[1]));
		}
		return model_list;
		*/
	}

	// --- GroupArticleService 接口实现 --------------------------------------------
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupArticleService#getNewGroupArticleList(int, int)
	 */
	public List<GroupArticle> getNewGroupArticleList(int groupId, int count) {
		List<GroupArticle> list = group_dao.getNewGroupArticleList(groupId, count);
		return list;
	}
	public List<GroupArticle> getNewGroupArticleList(int groupId, int count,boolean includeChildGroup) {
		List<GroupArticle> list = group_dao.getNewGroupArticleList(groupId, count,includeChildGroup);
		return list;
	}
		
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupArticleService#getHotGroupArticleList(int, int)
	 */
	public List<GroupArticle> getHotGroupArticleList(int groupId, int count) {
		List<GroupArticle> list = group_dao.getHotGroupArticleList(groupId, count);
		return list;
	}
	public List<GroupArticle> getHotGroupArticleList(int groupId, int count,boolean includeChildGroup)
	{
		List<GroupArticle> list = group_dao.getHotGroupArticleList(groupId, count,includeChildGroup);
		return list;
	}
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupArticleService#getBestGroupArticleList(int, int)
	 */
	public List<GroupArticle> getBestGroupArticleList(int groupId, int count) {
		List<GroupArticle> list = group_dao.getBestGroupArticleList(groupId, count);
		return list;
	}
	public List<GroupArticle> getBestGroupArticleList(int groupId, int count,boolean includeChildGroup) {
		List<GroupArticle> list = group_dao.getBestGroupArticleList(groupId, count,includeChildGroup);
		return list;
	}
	/** 将 List&lt;Object[Article, GroupArticle]&gt; 包装为 List&lt;ArticleModelEx&gt; */
	private List<ArticleModelEx> wrapArticleModelEx(List<Object[]> list) {
		List<ArticleModelEx> model_list = new ArrayList<ArticleModelEx>(list.size());
		if (list == null || list.size() == 0) return model_list;
		
		for (Object[] obj : list) {
			// obj[0] - Article, obj[1] - GroupArticle
			ArticleModelEx model = ArticleModelEx.wrap((Article)obj[0]);
			model.setGroupArticle((GroupArticle)obj[1]);
			model_list.add(model);
		}
		return model_list;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupArticleByGroupAndArticle(int, int)
	 */
	public GroupArticle getGroupArticleByGroupAndArticle(int groupId, int articleId) {
		return group_dao.getGroupArticleByGroupAndArticle(groupId, articleId);
	}
	
	public GroupArticle getGroupArticle(int groupArticleId){
		return group_dao.getGroupArticle(groupArticleId);
	}
	
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.GroupService#getGroupResourceByGroupAndResource(int, int)
	 */
	public GroupResource getGroupResourceByGroupAndResource(int groupId, int resourceId) {
		return group_dao.getGroupResourceByGroupAndResource(groupId, resourceId);
	}

	public GroupResource getGroupResource(int groupResourceId)
	{
		return group_dao.getGroupResource(groupResourceId);
	}
	
	public GroupPhoto getGroupPhotoByGroupAndPhoto(int groupId,int photoId)
	{
		return group_dao.getGroupPhotoByGroupAndPhoto(groupId, photoId);
	}
	public GroupPhoto getGroupPhoto(int groupPhotoId){
		return group_dao.getGroupPhoto(groupPhotoId);
	}
	public GroupVideo getGroupVideoByGroupAndVideo(int groupId,int videoId){
		return group_dao.getGroupVideoByGroupAndVideo(groupId, videoId);
	}
	public GroupVideo getGroupVideo(int groupVideoId){
		return group_dao.getGroupVideo(groupVideoId);
	}
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupArticleByArticleId(cn.edustar.jitar.pojos.Group, int)
	 */
	public Tuple<Article, GroupArticle> getGroupArticleByArticleId(Group group, int articleId) {
		Object[] a_ga = group_dao.getGroupArticleByArticleId(group.getGroupId(), articleId);
		if (a_ga == null) return null;
		return new Tuple<Article, GroupArticle>((Article)a_ga[0], (GroupArticle)a_ga[1]);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#deleteGroupArticle(cn.edustar.jitar.pojos.GroupArticle)
	 */
	public void deleteGroupArticle(GroupArticle ga) {
		group_dao.deleteGroupArticle(ga);
	}
	
	public boolean isBestInGroupArticle(int articleId){
		return group_dao.isBestInGroupArticle(articleId);
	}
	
	public boolean isBestInGroupResource(int resourceId){
		return group_dao.isBestInGroupResource(resourceId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#bestGroupArticle(cn.edustar.jitar.pojos.GroupArticle)
	 */
	public void bestGroupArticle(GroupArticle ga) {
		group_dao.updateGroupArticleBestState(ga.getId(), true);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#unbestGroupArticle(cn.edustar.jitar.pojos.GroupArticle)
	 */
	public void unbestGroupArticle(GroupArticle ga) {
		group_dao.updateGroupArticleBestState(ga.getId(), false);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#updateGroupArticleCount(cn.edustar.jitar.pojos.Group)
	 */
	public void updateGroupArticleCount(Group group) {
		if (group == null) return;
		
		// 物理更新.
		group_dao.updateGroupArticleCount(group.getGroupId());
		
		// 更新缓存.
		removeGroupCache(group);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupArticleList(int, cn.edustar.jitar.service.ArticleQueryParamEx, cn.edustar.data.Pager)
	 */
	public List<GroupArticle> getGroupArticleList(int groupId, GroupArticleQueryParam param, Pager pager) {
		List<GroupArticle> list = group_dao.getGroupArticleList(groupId, param, pager);
		return list;
	}

	public DataTable getGroupArticleDataTable(GroupArticleQueryParam ga_param, Pager pager) {
		return group_dao.getGroupArticleDataTable(ga_param, pager);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupTopicList(int, cn.edustar.data.Pager)
	 */
	public DataTable getGroupTopicList(int groupId ,Pager pager) {
		//String name = CommonUtil.toGroupBoardName(groupId);
		//Board board = bbs_svc.getBoardByName(name);
		DataTable topics = bbs_svc.getTopicDataTable(groupId, pager);
		return topics;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupReplyList(int, cn.edustar.data.Pager)
	 */
	public DataTable getGroupReplyList(int topicId, Pager pager) {
		DataTable replys = bbs_svc.getReplyDataTable(topicId, pager);
		return replys;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#joinGroup(cn.edustar.jitar.pojos.Group, cn.edustar.jitar.pojos.User)
	 */
	public void joinGroup(Group group, User user) {
		if (group.getJoinLimit() != Group.JOIN_LIMIT_NOLIMIT &&
				group.getJoinLimit() != Group.JOIN_LIMIT_NEEDAUTID) return;
		
		GroupMember gm = new GroupMember();
		gm.setGroupId(group.getGroupId());
		gm.setUserId(user.getUserId());
		gm.setGroupRole(GroupMember.GROUP_ROLE_MEMBER);
		if (group.getJoinLimit() == Group.JOIN_LIMIT_NOLIMIT)
			gm.setStatus(Group.GROUP_STATE_NORMAL);
		else
			gm.setStatus(Group.GROUP_STATE_WAIT_AUDIT);
		group_dao.saveGroupMember(gm);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#updateGroupStatInfo(cn.edustar.jitar.pojos.Group)
	 */
	public void updateGroupStatInfo(Group group) {
		group_dao.updateGroupStatInfo(group);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupResourceByResourceId(int, int)
	 */
	public Tuple<Resource, GroupResource> getGroupResourceByResourceId(int groupId, int resourceId) {
		return group_dao.getGroupResourceByResourceId(groupId, resourceId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#publishResourceToGroup(cn.edustar.jitar.pojos.GroupResource)
	 */
	public void publishResourceToGroup(GroupResource gr) {
		group_dao.saveGroupResource(gr);
	}
	
	public Tuple<Photo, GroupPhoto> getGroupPhotoByPhotoId(int groupId, int photoId){
		return group_dao.getGroupPhotoByPhotoId(groupId, photoId);
	}
	public void publishPhotoToGroup(GroupPhoto gp){
		group_dao.publishPhotoToGroup(gp);
	}
	public Tuple<Video, GroupVideo> getGroupVideoByVideoId(int groupId, int videoId){
		return group_dao.getGroupVideoByVideoId(groupId, videoId);
	}
	public void publishVideoToGroup(GroupVideo gv){
		group_dao.publishVideoToGroup(gv);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#publishArticleToGroup(cn.edustar.jitar.pojos.GroupArticle)
	 */
	public void publishArticleToGroup(GroupArticle ga) {
		group_dao.saveGroupArticle(ga);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#deleteGroupResource(cn.edustar.jitar.pojos.GroupResource)
	 */
	public void deleteGroupResource(GroupResource gr) {
		group_dao.deleteGroupResource(gr);
	}
	public void deleteGroupPhoto(GroupPhoto gp){
		group_dao.deleteGroupPhoto(gp);
	}
	public void deleteGroupVideo(GroupVideo gv){
		group_dao.deleteGroupVideo(gv);
	}
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupResourceList(int, cn.edustar.jitar.service.ResourceQueryParam, cn.edustar.data.Pager)
	 */
	public List<ResourceModelEx> getGroupResourceList(int groupId, ResourceQueryParam param, Pager pager) {
		List<Object[]> list = group_dao.getGroupResourceList(groupId, param, pager);
		List<ResourceModelEx> result = wrapResourceModelEx(list);
		if (param.retrieveGroupCategory) {
			String itemType = CommonUtil.toGroupResourceCategoryItemType(groupId);
			doc_impl.joinCategory(result, new ResourceModelCsGroupCategory(), itemType);
		}
		return result;
	}
	
	public List<PhotoModel> getGroupPhotoList(int groupId, PhotoQueryParam param, Pager pager){
		List<Object[]> list = group_dao.getGroupPhotoList(groupId, param, pager);
		List<PhotoModel> result = wrapPhotoModel(list);
		if (param.retrieveGroupCategory) {
			String itemType = CommonUtil.toGroupPhotoCategoryItemType(groupId);
			doc_impl.joinCategory(result, new PhotoModelCsGroupCategory(), itemType);
		}		
		return result;
	}
	
	public List<VideoModel> getGroupVideoList(int groupId, VideoQueryParam param, Pager pager){
		List<Object[]> list = group_dao.getGroupVideoList(groupId, param, pager);
		List<VideoModel> result = wrapVideoModel(list);
		if (param.retrieveGroupCategory) {
			String itemType = CommonUtil.toGroupVideoCategoryItemType(groupId);
			doc_impl.joinCategory(result, new VideoModelCsGroupCategory(), itemType);
		}			
		return result;
	}
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupResourceDataTable(cn.edustar.jitar.service.GroupResourceQueryParam, cn.edustar.data.Pager)
	 */
	public DataTable getGroupResourceDataTable(GroupResourceQueryParam param, Pager pager) {
		return group_dao.getGroupResourceDataTable(param, pager);
	}

	
	/** getGroupResourceList() 方法使用, 从 ResourceModelEx 中获取/设置群组分类 */
	private static class ResourceModelCsGroupCategory implements DocumentServiceImpl.CategorySupport {
		public Integer getCategoryId(Object o) {
			return ((ResourceModelEx)o).getGroupResource().getGroupCateId();
		}

		public void setCategory(Object o, Category category) {
			((ResourceModelEx)o).setGroupCategory(category);
		}
	}
	
	private static class PhotoModelCsGroupCategory implements DocumentServiceImpl.CategorySupport {
		public Integer getCategoryId(Object o) {
			return ((PhotoModel)o).getGroupPhoto().getGroupCateId();
		}

		public void setCategory(Object o, Category category) {
			((PhotoModel)o).setGroupCategory(category);
		}
	}
	
	private static class VideoModelCsGroupCategory implements DocumentServiceImpl.CategorySupport {
		public Integer getCategoryId(Object o) {
			return ((VideoModel)o).getGroupVideo().getGroupCateId();
		}

		public void setCategory(Object o, Category category) {
			((VideoModel)o).setGroupCategory(category);
		}
	}
	
	// 包装 List<Object[Resource, GroupResource]> 为 List<ResourceModelEx> .
	private List<ResourceModelEx> wrapResourceModelEx(List<Object[]> list) {
		List<ResourceModelEx> result = new ArrayList<ResourceModelEx>();
		if (list == null || list.size() == 0) return result;
		for (Object[] r_gr : list) {
			ResourceModelEx model = ResourceModelEx.wrap((Resource)r_gr[0]);
			model.setGroupResource((GroupResource)r_gr[1]);
			result.add(model);
		}
		return result;
	}
	
	private List<PhotoModel> wrapPhotoModel(List<Object[]> list) {
		List<PhotoModel> result = new ArrayList<PhotoModel>();
		if (list == null || list.size() == 0) return result;
		for (Object[] r_gr : list) {
			PhotoModel model = PhotoModel.wrap((Photo)r_gr[0]);
			model.setGroupPhoto((GroupPhoto)r_gr[1]);
			result.add(model);
		}
		return result;
	}
	
	private List<VideoModel> wrapVideoModel(List<Object[]> list) {
		List<VideoModel> result = new ArrayList<VideoModel>();
		if (list == null || list.size() == 0) return result;
		for (Object[] r_gr : list) {
			VideoModel model = VideoModel.wrap((Video)r_gr[0]);
			model.setGroupVideo((GroupVideo)r_gr[1]);
			result.add(model);
		}
		return result;
	}	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getNewGroupResourceList(int, int)
	 */
	public List<ResourceModelEx> getNewGroupResourceList(int groupId, int count) {
		List<Object[]> list = group_dao.getNewGroupResourceList(groupId, count);
		return wrapResourceModelEx(list);
	}
	public List<ResourceModelEx> getNewGroupResourceList(int groupId, int count,boolean includeChildGroup) {
		List<Object[]> list = group_dao.getNewGroupResourceList(groupId, count,includeChildGroup);
		return wrapResourceModelEx(list);
	}
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getHotGroupResourceList(int, int)
	 */
	public List<ResourceModelEx> getHotGroupResourceList(int groupId, int count) {
		List<Object[]> list = group_dao.getHotGroupResourceList(groupId, count);
		return wrapResourceModelEx(list);
	}
	public List<ResourceModelEx> getHotGroupResourceList(int groupId, int count,boolean includeChildGroup) {
		List<Object[]> list = group_dao.getHotGroupResourceList(groupId, count,includeChildGroup);
		return wrapResourceModelEx(list);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getBestGroupResourceList(int, int)
	 */
	public List<ResourceModelEx> getBestGroupResourceList(int groupId, int count) {
		List<Object[]> list = group_dao.getBestGroupResourceList(groupId, count);
		return wrapResourceModelEx(list);
	}
	public List<ResourceModelEx> getBestGroupResourceList(int groupId, int count,boolean includeChildGroup) {
		List<Object[]> list = group_dao.getBestGroupResourceList(groupId, count,includeChildGroup);
		return wrapResourceModelEx(list);
	}
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#bestGroupResource(cn.edustar.jitar.pojos.GroupResource)
	 */
	public void bestGroupResource(GroupResource gr) {
		group_dao.updateGroupResourceBestState(gr.getId(), true);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#unbestGroupResource(cn.edustar.jitar.pojos.GroupResource)
	 */
	public void unbestGroupResource(GroupResource gr) {
		group_dao.updateGroupResourceBestState(gr.getId(), false);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#updateGroupResourceCategory(cn.edustar.jitar.pojos.GroupResource, java.lang.Integer)
	 */
	public void updateGroupResourceCategory(GroupResource gr, Integer groupCateId) {
		group_dao.updateGroupResourceCategory(gr, groupCateId);
	}

	public void updateGroupArticleCategory(GroupArticle ga, Integer groupCateId)
	{
		group_dao.updateGroupArticleCategory(ga,groupCateId);
	}
	
	public void updateGroupPhotoCategory(GroupPhoto gp, Integer groupCateId){
		group_dao.updateGroupPhotoCategory(gp, groupCateId);
	}
	
	public void updateGroupVideoCategory(GroupVideo gv, Integer groupCateId){
		group_dao.updateGroupVideoCategory(gv,groupCateId);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#deleteGroupResourceByResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void deleteGroupResourceByResource(Resource resource) {
		// 得到要删除的所有群组资源引用.
		List<GroupResource> gr_list = group_dao.getGroupResourceByResource(resource.getResourceId());
		if (gr_list == null || gr_list.size() == 0) return;
		
		// 删除所有这些引用.
		group_dao.deleteGroupResourceByResource(resource.getResourceId());
		
		// 更新统计数据.
		for (GroupResource gr : gr_list) {
			group_dao.incGroupResourceCount(gr.getGroupId(), -1);
		}
	}

	public void deleteGroupPhotoByPhoto(Photo photo){
		List<GroupPhoto> gr_list = group_dao.getGroupPhotoByPhoto(photo.getPhotoId());
		if (gr_list == null || gr_list.size() == 0) return;
		
		// 删除所有这些引用.
		group_dao.deleteGroupPhotoByPhoto(photo.getPhotoId());
	}
	
	public void deleteGroupVideoByVideo(Video video){
		List<GroupVideo> gr_list = group_dao.getGroupVideoByVideo(video.getVideoId());
		if (gr_list == null || gr_list.size() == 0) return;
		
		// 删除所有这些引用.
		group_dao.deleteGroupVideoByVideo(video.getVideoId());		
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupNews(int)
	 */
	public GroupNews getGroupNews(int newsId) {
		return group_dao.getGroupNews(newsId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#saveOrUpdateGroupNews(cn.edustar.jitar.pojos.GroupNews)
	 */
	public void saveOrUpdateGroupNews(GroupNews gn) {
		group_dao.saveOrUpdateGroupNews(gn);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#incGroupNewsViewCount(cn.edustar.jitar.pojos.GroupNews, int)
	 */
	public void incGroupNewsViewCount(GroupNews gn, int incCount) {
		group_dao.incGroupNewsViewCount(gn, incCount);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupNewsDataTable(cn.edustar.jitar.service.GroupNewsQueryParam, cn.edustar.data.Pager)
	 */
	public DataTable getGroupNewsDataTable(GroupNewsQueryParam param, Pager pager) {
		List<Object[]> list = group_dao.getGroupNewsDataTable(param, pager);
		
		DataSchema schema = new DataSchema(param.selectFields);
		DataTable dt = new DataTable(schema);
		dt.addList(list);
		return dt;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.GroupService#getGroupPlacardDataTable(cn.edustar.jitar.service.GroupPlacardQueryParam, cn.edustar.data.Pager)
	 */
	public DataTable getGroupPlacardDataTable(GroupPlacardQueryParam param, Pager pager) {
		List<Object[]> list = group_dao.getGroupPlacardDataTable(param, pager);
		
		DataSchema schema = new DataSchema(param.selectFields);
		DataTable dt = new DataTable(schema);
		dt.addList(list);
		return dt;
	}
	
	public void UpdateBestGroupArticleAndResource()
	{
		group_dao.UpdateBestGroupArticleAndResource();
	}
	
	public List<GroupArticle> getAllGroupArticleByArticleId(int articleId)
	{
		return this.group_dao.getAllGroupArticleByArticleId(articleId);
	}
	
	public List<GroupMember> getUserJoinedAllGroup(int userId)
	{
		return this.group_dao.getUserJoinedAllGroup(userId);
	}
	
	public void saveOrUpdateGroupDataQuery(GroupDataQuery groupDataQuery){
		this.group_dao.saveOrUpdateGroupDataQuery(groupDataQuery);		
	}
	
	public void deleteGroupDataQuery()
	{
		this.group_dao.deleteGroupDataQuery();
	}
	
	public void deleteGroupDataQueryByGuid(String guid)
	{
		this.group_dao.deleteGroupDataQueryByGuid(guid);
	}
	
	public List<Group> getAllUserCreatedGroupByUserId(int createUserId)
	{
		return this.group_dao.getAllUserCreatedGroupByUserId(createUserId);
	}
	/**-----------------协助组综合分类-----------*/
	public GroupMutil getGroupMutilById(int id){
		return this.group_dao.getGroupMutilById(id);
	}
	public GroupMutil getGroupMutilByWidgetId(int widgetId){
		return this.group_dao.getGroupMutilByWidgetId(widgetId);
	}
	public void saveGroupMutil(GroupMutil gm){
		this.group_dao.saveGroupMutil(gm);
	}
	public void deleteGroupMutilById(GroupMutil gm){
		this.group_dao.deleteGroupMutilById(gm);
	}
	public void deleteGroupMutilById(int id){
		this.group_dao.deleteGroupMutilById(id);
	}
	public void deleteGroupMutilByWidgetId(int widgetId){
		this.group_dao.deleteGroupMutilByWidgetId(widgetId);
	}
	
	public GroupKTUserService getGroupKTUserService() {
		return groupKTUserService;
	}

	public void setGroupKTUserService(GroupKTUserService groupKTUserService) {
		this.groupKTUserService = groupKTUserService;
	}
	
	public List<GroupKTUser> GetGroupKTUsers(int groupId){
		return this.groupKTUserService.GetGroupKTUsers(groupId);
	}
	
}
