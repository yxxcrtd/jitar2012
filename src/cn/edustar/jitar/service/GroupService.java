package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.model.PhotoModel;
import cn.edustar.jitar.model.VideoModel;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.GroupKTUser;
import cn.edustar.jitar.pojos.GroupMutil;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupArticle;
import cn.edustar.jitar.pojos.GroupPhoto;
import cn.edustar.jitar.pojos.GroupVideo;
import cn.edustar.jitar.pojos.GroupDataQuery;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.GroupNews;
import cn.edustar.jitar.pojos.GroupResource;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.query.VideoQueryParam;

/**
 * 协作组/协作组服务接口定义.
 * 
 *
 * 
 */
public interface GroupService extends GroupArticleService {
	/** 事件: 正准备创建一个新协作组，事件对象为 Group 对象. (对象尚未保存到数据库, 可能没有 id) */
	public static final String EVENT_GROUP_CREATING = "jitar.group.creating";

	/** 事件: 已经创建一个新协作组，事件对象为 Group 对象. (对象已经保存到数据库) */
	public static final String EVENT_GROUP_CREATED = "jitar.group.created";

	/** 事件: 审核通过一个协作组，事件对象为 Group 对象. */
	public static final String EVENT_GROUP_AUDITED = "jitar.group.audited";

	/** 事件: 即将彻底删除了一个协作组，事件对象为 Group 对象. */
	public static final String EVENT_GROUP_DELETING = "jitar.group.deleting";

	/** 事件: 彻底删除了一个协作组，事件对象为 Group 对象. */
	public static final String EVENT_GROUP_DELETED = "jitar.group.deleted";

	/**
	 * 判定所给协作组标题是否合法.
	 * 
	 * @param title
	 * @return true 表示合法, false 表示不合法.
	 * @deprecated - 不要再用此方法了.
	 */
	public boolean isValidGroupTitle(String title);

	/**
	 * 判定所给协作组名字是否合法.
	 * 
	 * @param name
	 * @return true 表示合法, false 表示不合法.
	 * @deprecated - 不要再用此方法了.
	 */
	public boolean isValidGroupName(String name);

	/**
	 * 得到指定标识的协作组信息.
	 * 
	 * @param groupId
	 * @return
	 */
	public Group getGroup(int groupId);

	/**
	 * 通过名字得到协作组对象信息.
	 * 
	 * @param groupName
	 * @return
	 */
	public Group getGroupByName(String groupName);

	public Group getGroupByGuid(String groupGuid);
	/**
	 * 通过协作组标题得到协作组对象.
	 * 
	 * @param groupTitle
	 * @return
	 */
	public Group getGroupByTitle(String groupTitle);

	/**
	 * 得到指定用户未被审核通过的协作组数量.
	 * 
	 * @param user
	 * @return
	 */
	public int getUserUnanditGroupCount(User user);
	/**得到群组的分类的UUID*/
	public String getGroupCateUuidById(int groupId);
	public String getGroupCateUuid(Group group);
	/**
	 * 得到指定标识的协作组, 如果在缓存中存在, 则直接从缓存中获取.
	 * 
	 * @param groupId -
	 *           协作组标识.
	 * @return
	 */
	public Group getGroupMayCached(int groupId);

	/**
	 * 得到指定名字的协作组信息，如果在缓存中存在，则直接从缓存中获取.
	 * 
	 * @param groupName 协作组名字.
	 * @return 如果没有则返回 null
	 */
	public Group getGroupMayCached(String groupName);

	/**
	 * 创建一个协作组.
	 * 
	 * @param group
	 */
	public void createGroup(Group group);

	/**
	 * 更新一个协作组的信息.
	 */
	public void updateGroup(Group group);

	/**
	 * 审核通过一个协作组.
	 * 
	 * @param group
	 */
	public void auditGroup(Group group);

	/**
	 * 锁定/解锁一个协作组.
	 * 
	 * @param group 要锁定/解锁的协作组
	 * @param lock true 表示锁定，false 表示解锁
	 */
	public void lockGroup(Group group, boolean lock);

	/**
	 * 隐藏/解除隐藏一个协作组.
	 * 
	 * @param group
	 * @param hide
	 */
	public void hideGroup(Group group, boolean hide);

	/**
	 * 逻辑删除一个协作组(设置删除标志), 但不是从数据库中删除协作组记录.
	 * 
	 * @param group
	 */
	public void deleteGroup(Group group);

	/**
	 * 恢复一个被删除的协作组(状态从删除变成正常).
	 * 
	 * @param group
	 */
	public void recoverGroup(Group group);

	/**
	 * 设置指定协作组的优秀团队标志.
	 * 
	 * @param group
	 * @param isBestGroup
	 * @return
	 */
	public void setGroupBestGroup(Group group, boolean isBestGroup);

	/**
	 * 设置指定协作组的推荐协作组标志.
	 * 
	 * @param group
	 * @param isRecommend
	 */
	public void setGroupRecommend(Group group, boolean isRecommend);

	/**
	 * 彻底删除一个协作组(把整个协作组及其相关的东西都删除了).
	 * 
	 * @param group
	 */
	public void crashGroup(Group group);

	/**
	 * 得到指定条件下的协作组列表
	 * 
	 * @param param 查询参数
	 * @param pager 分页选项，可以为'null'
	 * @return 返回协作组集合
	 * 
	 */
	public List<Group> getGroupList(GroupQueryParam param, Pager pager);
	
	/**
	 * 得到所有群组的统计列表
	 *
	 * @return
	 */
	public List<Group> getGroupList();
	
	/**
	 * 根据群组Id、开始日期和结束日期，修改群组的统计信息数(包括：成员数、文章数、资源数，主题数、讨论数、活动数)
	 *
	 * @param groupId
	 * @param bedinDate
	 * @param endDate
	 */
	public void updateGroupStat(int groupId, String beginDate, String endDate);

	// 下面这些方法不用分页吧，分页主要是页面展示很麻烦

	/**
	 * 得到指定用户加入的协作组.
	 * 
	 * @param userId 用户标识
	 * @return 返回 List&lt;GroupModel&gt; 集合，其中每个 GroupModel 对象中设置了 _groupUser 属性
	 */
	// public List<Group> getMyJoinedGroupList(int userId);
	/**
	 * 得到我创建的协作组列表，按照 id 逆序排列.
	 * 
	 * @param userId -
	 *           用户标识.
	 * @return 返回指定用户创建的协作组列表.
	 */
	// public List<Group> getMyCreatedGroupList(int userId);
	/**
	 * 得到我发出的邀请集合.
	 * 
	 * @param userId
	 * @return 返回 DataTable, 包括的字段参见 GroupDao.MYINVITE_LIST_FIELDS
	 */
	// public DataTable getMyInviteList(int userId);
	/**
	 * 得到我收到的邀请集合.
	 * 
	 * @param userId
	 * @return 返回 DataTable, 包括的字段参见 GroupDao.INVITEME_LIST_FIELDS
	 */
	// public DataTable getInviteMeList(int userId);
	/**
	 * 得到我发出的申请.
	 * 
	 * @param userId
	 * @return 返回 DataTable, 包括的字段参见 GroupDao.MYJOINREQ_LIST_FIELDS
	 */
	// public DataTable getMyJoinReqList(int userId);
	/**
	 * 得到我收到的申请.
	 * 
	 * @param userId
	 * @return 返回 DataTable, 包括的字段参见 GroupDao.JOINREQ_LIST_FIELDS
	 */
	// public DataTable getReqMeList(int userId);
	/**
	 * 得到指定协作组下用户列表.
	 * 
	 * @param groupId
	 * @return 返回包装了的 DataTable 其中字段参见 GroupDao.GROUP_USER_LIST_FIELDS
	 */
	public DataTable getGroupMemberList(GroupMemberQueryParam param, Pager pager);

	/****************************************************************************
	 * / 得到相关度最高的组. @param tag_Ids - 标签标识数组. @param relative_groups - 相关的协作组.
	 * @return 返回具有最大相关度的组.
	 *//*
		 * public Group getHeightRelativeGroup(List<Integer> tag_Ids, List<Group>
		 * relative_groups);
		 */
	/**
	 * 得到指定用户加入的所有协作组的用户信息.
	 * 
	 * @param userId -
	 *           用户标识.
	 * @param pager -
	 *           分页.
	 * @return
	 */
	public List<User> getUserJoinedGroupMemberList(int userId, Pager pager);

	/**
	 * 根据协作组标识和用户标识得到 GroupMember 对象，其包括用户在协作组的角色、状态、统计等信息.
	 * 
	 * @param groupId -
	 *           协作组标识.
	 * @param userId -
	 *           用户标识.
	 * @return
	 */
	public GroupMember getGroupMemberByGroupIdAndUserId(int groupId, int userId);
	
	public GroupMember getGroupMemberById(int id);

	/**
	 * 创建一个协作组成员.
	 * 
	 * @param gm
	 */
	public void createGroupMember(GroupMember gm);

	/**
	 * 更新一个协作组成员的信息.
	 * 
	 * @param gm
	 */
	public void updateGroupMember(GroupMember gm);

	/**
	 * 创建一个协作组成员, 邀请、加入等操作的时候可以使用.
	 * 
	 * @param gm
	 */
	public void inviteGroupMember(GroupMember gm);

	/**
	 * 审核通过一个协作组成员，使得其正式成为协作组的一员
	 * 
	 * @param gm
	 * @remark 在进行了协作组成员操作之后，有必要调用'updateGroupMemberCount()'方法来更新协作组成员数量统计数据
	 * 
	 */
	public void auditGroupMember(GroupMember gm);

	/**
	 * 加锁一个协作组成员.
	 * 
	 * @param gm
	 */
	public void lockGroupMember(GroupMember gm);

	/**
	 * 解锁一个协作组成员.
	 * 
	 * @param gm
	 */
	public void unlockGroupMember(GroupMember gm);

	/**
	 * 逻辑删除一个协作组成员, 设置该成员状态为 DELETING.
	 * 
	 * @param gm
	 * @remark 在进行了协作组成员操作之后，有必要调用 updateGroupMemberCount() 方法来更新_ 协作组成员数量统计数据.
	 */
	public void deleteGroupMember(GroupMember gm);

	/**
	 * 物理删除一个协作组成员记录. 和 deleteGroupMember() 不同之处在于 deleteGroupMember()
	 * 仅设置删除标志而不删除记录, 而 destroyGroupMember() 则删除掉记录.
	 * 
	 * @param gm
	 */
	public void destroyGroupMember(GroupMember gm);

	/**
	 * 恢复一个被删除的协作组成员, 和 deleteGroupMember() 几乎是反操作.
	 * 
	 * @param gm
	 */
	public void recoverGroupMember(GroupMember gm);

	/**
	 * 取消对一个用户的加入协作组的邀请.
	 * 
	 * @param gm
	 */
	public void uninviteGroupMember(GroupMember gm);

	/**
	 * 同意加入协作组的邀请.
	 * 
	 * @param gm
	 */
	public void acceptGroupMemberInvite(GroupMember gm);

	/**
	 * 拒绝加入协作组的邀请.
	 * 
	 * @param gm
	 */
	public void rejectGroupMemberInvite(GroupMember gm);

	/**
	 * 取消加入协作组的申请(实际实现为删除此记录).
	 * 
	 * @param gm
	 */
	public void cancelJoinRequest(GroupMember gm);

	/**
	 * 将协作组 group 转让给 newCreator.
	 * 
	 * @param group
	 * @param newCreator
	 */
	public void transferGroupToMember(Group group, GroupMember newCreator);

	/**
	 * 重新计算指定协作组的成员用户数量，并更新 Group 的信息(可能同时更新缓存) 成员用户仅包括正常状态 (gm.status == NORMAL)
	 * 的协作组成员.
	 * 
	 * @param groupId -
	 *           协作组标识.
	 * @return 返回该协作组当前成员用户数量； -1 表示协作组不存在(不正常情况)
	 */
	public int updateGroupMemberCount(int groupId);

	/**
	 * 得到指定协作组的活跃分子列表.
	 * 
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getGroupActivistList(int groupId);

	/**
	 * 得到指定协作组的管理者.
	 * 
	 * @param groupId -
	 *           协作组标识
	 * 
	 * @return
	 */
	public User getGroupManager(int groupId);
	
	/**支持多个管理者*/
	public List<User> getGroupManagers(int groupId);

	/**
	 * 获得最新加入协作组的成员信息.
	 * 
	 * @param groupId -
	 *           协作组标识
	 * 
	 * @param count -
	 *           获取数量
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getNewGroupMember(int groupId, int count);

	/**
	 * 得到指定协作组和文章的 GroupArticle 对象.
	 * 
	 * @param groupId
	 * @param articleId
	 * @return
	 */
	public GroupArticle getGroupArticleByGroupAndArticle(int groupId,
			int articleId);
	
	public GroupArticle getGroupArticle(int groupArticleId);

	/**
	 * 得到指定协作组和资源的'GroupResource'对象.
	 * 
	 * @param groupId
	 * @param resourceId
	 * @return
	 */
	public GroupResource getGroupResourceByGroupAndResource(int groupId,int resourceId);
	public GroupResource getGroupResource(int groupResourceId);
	
	/**
	 * 得到指定协作组和图片的'GroupPhoto'对象.
	 * 
	 * @param groupId
	 * @param photoId
	 * @return
	 */
	public GroupPhoto getGroupPhotoByGroupAndPhoto(int groupId,int photoId);
	public GroupPhoto getGroupPhoto(int groupPhotoId);	

	/**
	 * 得到指定协作组和视频的'GroupVideo'对象.
	 * 
	 * @param groupId
	 * @param videoId
	 * @return
	 */
	public GroupVideo getGroupVideoByGroupAndVideo(int groupId,int videoId);
	public GroupVideo getGroupVideo(int groupVideoId);	
	
	/**
	 * 得到指定协作组中指定标识的文章对象.
	 * 
	 * @param group
	 * @param articleId
	 * @return
	 */
	public Tuple<Article, GroupArticle> getGroupArticleByArticleId(Group group,
			int articleId);

	/**
	 * 删除指定的协作组文章引用.
	 * 
	 * @param ga
	 */
	public void deleteGroupArticle(GroupArticle ga);
	

	/**
	 * 设置为协作组精华文章.
	 * 
	 * @param ga
	 */
	public void bestGroupArticle(GroupArticle ga);

	/**
	 * 取消协作组精华文章.
	 * 
	 * @param ga
	 */
	public void unbestGroupArticle(GroupArticle ga);

	/**
	 * 更新指定协作组的文章引用数.
	 * 
	 * @param group
	 */
	public void updateGroupArticleCount(Group group);

	/**
	 * 得到指定协作组指定查询条件的文章列表
	 * 
	 * @param groupId 协作组标识
	 * @param param 查询参数, 仅支持部分条件
	 * @param pager 分页参数
	 * @return List&lt;ArticleModelEx&gt;集合
	 */
	public List<GroupArticle> getGroupArticleList(int groupId, GroupArticleQueryParam param, Pager pager);
	
	

	/**
	 * 得到指定查询条件下的文章列表
	 * 
	 * @param ga_param
	 * @param pager
	 * @return
	 */
	public DataTable getGroupArticleDataTable(GroupArticleQueryParam ga_param, Pager pager);

	/**
	 * 通过协作组的标识得到主题列表.
	 * 
	 * @author Yang XinXin
	 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
	 * @param groupId
	 * @return
	 */
	public DataTable getGroupTopicList(int groupId, Pager pager);

	/**
	 * 通过主题标识得到主题回复列表.
	 * 
	 * @param
	 * @return
	 */
	public DataTable getGroupReplyList(int topicId, Pager pager);

	/**
	 * 加入一个协作组.
	 * 
	 * @param group
	 * @param user
	 */
	public void joinGroup(Group group, User user);

	/**
	 * 更新该协作组的统计信息
	 * 
	 * @param group
	 */
	public void updateGroupStatInfo(Group group);

	/**
	 * 根据协作组标识和资源标识得到 Resource, GroupResource 对象.
	 * 
	 * @param groupId
	 * @param resourceId
	 * @return
	 */
	public Tuple<Resource, GroupResource> getGroupResourceByResourceId(
			int groupId, int resourceId);

	/**
	 * 发布资源到组.
	 * 
	 * @param gr
	 */
	public void publishResourceToGroup(GroupResource gr);

	/**
	 * 根据协作组标识和图片标识得到 Photo, GroupPhoto 对象.
	 * 
	 * @param groupId
	 * @param photoId
	 * @return
	 */
	public Tuple<Photo, GroupPhoto> getGroupPhotoByPhotoId(
			int groupId, int photoId);

	/**
	 * 发布图片到组.
	 * 
	 * @param gp
	 */
	public void publishPhotoToGroup(GroupPhoto gp);

	/**
	 * 根据协作组标识和视频标识得到 Video, GroupVideo 对象.
	 * 
	 * @param groupId
	 * @param videoId
	 * @return
	 */
	public Tuple<Video, GroupVideo> getGroupVideoByVideoId(
			int groupId, int videoId);

	/**
	 * 发布视频到组.
	 * 
	 * @param gv
	 */
	public void publishVideoToGroup(GroupVideo gv);
	
	/**
	 * 发布文章到组.
	 * 
	 * @param ga
	 */
	public void publishArticleToGroup(GroupArticle ga);

	/**
	 * 删除协作组资源引用.
	 * 
	 * @param gr
	 */
	public void deleteGroupResource(GroupResource gr);

	/**
	 * 删除协作组图片引用.
	 * 
	 * @param gp
	 */
	public void deleteGroupPhoto(GroupPhoto gp);
	
	/**
	 * 删除协作组视频引用.
	 * 
	 * @param gv
	 */
	public void deleteGroupVideo(GroupVideo gv);	
	/**
	 * 得到指定协作组的资源列表.
	 * 
	 * @param groupId
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<ResourceModelEx> getGroupResourceList(int groupId,
			ResourceQueryParam param, Pager pager);

	public List<PhotoModel> getGroupPhotoList(int groupId, PhotoQueryParam param, Pager pager);
	
	public List<VideoModel> getGroupVideoList(int groupId, VideoQueryParam param, Pager pager);
	
	public void deleteGroupPhotoByPhoto(Photo photo);
	
	public void deleteGroupVideoByVideo(Video video);
	
	/**
	 * 得到指定协作组的资源列表.
	 * 
	 * @param param
	 * @param pager
	 * @return
	 */
	public DataTable getGroupResourceDataTable(GroupResourceQueryParam param,
			Pager pager);

	/**
	 * 得到指定协作组最新 count 条资源.
	 * 
	 * @param groupId
	 * @param count
	 * @return
	 */
	public List<ResourceModelEx> getNewGroupResourceList(int groupId, int count);
	public List<ResourceModelEx> getNewGroupResourceList(int groupId, int count,boolean includeChildGroup);

	/**
	 * 得到指定协作组最热 count 条资源.
	 * 
	 * @param groupId
	 * @param count
	 * @return
	 */
	public List<ResourceModelEx> getHotGroupResourceList(int groupId, int count);
	public List<ResourceModelEx> getHotGroupResourceList(int groupId, int count,boolean includeChildGroup);

	/**
	 * 得到指定协作组精华 count 条资源.
	 * 
	 * @param groupId
	 * @param count
	 * @return
	 */
	public List<ResourceModelEx> getBestGroupResourceList(int groupId, int count);
	public List<ResourceModelEx> getBestGroupResourceList(int groupId, int count,boolean includeChildGroup);

	/**
	 * 设置为协作组精华资源.
	 * 
	 * @param gr
	 */
	public void bestGroupResource(GroupResource gr);

	/**
	 * 取消协作组精华资源.
	 * 
	 * @param gr
	 */
	public void unbestGroupResource(GroupResource gr);

	/**
	 * 更新协作组资源的新分类.
	 * 
	 * @param gr
	 * @param groupCateId -
	 *           新分类标识, 为 null 表示取消分类.
	 */
	public void updateGroupResourceCategory(GroupResource gr, Integer groupCateId);
	
	public void updateGroupArticleCategory(GroupArticle ga, Integer groupCateId);
	
	public void updateGroupPhotoCategory(GroupPhoto gp, Integer groupCateId);
	
	public void updateGroupVideoCategory(GroupVideo gv, Integer groupCateId);

	/**
	 * 删除所有协作组对指定资源的引用.
	 * 
	 * @param resource
	 */
	public void deleteGroupResourceByResource(Resource resource);

	/**
	 * 得到指定标识的协作组新闻对象.
	 * 
	 * @param newsId
	 * @return
	 */
	public GroupNews getGroupNews(int newsId);

	/**
	 * 创建或更新指定的协作组新闻对象.
	 * 
	 * @param gn
	 */
	public void saveOrUpdateGroupNews(GroupNews gn);

	/**
	 * 增加/减少指定协作组新闻的点击次数.
	 * 
	 * @param gn
	 * @param incCount
	 */
	public void incGroupNewsViewCount(GroupNews gn, int incCount);

	/**
	 * 得到指定条件的协作组新闻列表.
	 * 
	 * @param param
	 * @param pager
	 * @return
	 */
	public DataTable getGroupNewsDataTable(GroupNewsQueryParam param, Pager pager);

	/** getGroupPlacardDataTable 方法缺省的字段列表 */
	public static final String GROUP_PLACARD_FIELDS = "pla.id, pla.title, pla.createDate, "
			+ "g.groupId, g.groupTitle, "
			+ "u.userId, u.loginName, u.nickName, u.userIcon";

	/**
	 * 得到指定条件的协作组公告列表.
	 * 
	 * @param param -
	 *           查询参数.
	 * @param pager -
	 *           分页参数.
	 * @return
	 */
	public DataTable getGroupPlacardDataTable(GroupPlacardQueryParam param,
			Pager pager);
	
	public void UpdateBestGroupArticleAndResource();
	
	public List<GroupArticle> getAllGroupArticleByArticleId(int articleId);
	
	public List<GroupMember> getUserJoinedAllGroup(int userId);
	
	public void saveOrUpdateGroupDataQuery(GroupDataQuery groupDataQuery);
	
	public void deleteGroupDataQuery();	
	
	public void deleteGroupDataQueryByGuid(String guid);
	
	public List<Group> getAllUserCreatedGroupByUserId(int createUserId);
	
	/**-----------------协助组综合分类-----------*/
	public GroupMutil getGroupMutilById(int id);
	public GroupMutil getGroupMutilByWidgetId(int widgetId);
	public void saveGroupMutil(GroupMutil gm);
	public void deleteGroupMutilById(GroupMutil gm);
	public void deleteGroupMutilById(int id);
	public void deleteGroupMutilByWidgetId(int widgetId);

	public List<GroupKTUser> GetGroupKTUsers(int groupId);
}
