package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupArticle;
import cn.edustar.jitar.pojos.GroupDataQuery;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.GroupNews;
import cn.edustar.jitar.pojos.GroupPhoto;
import cn.edustar.jitar.pojos.GroupResource;
import cn.edustar.jitar.pojos.GroupVideo;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.pojos.GroupMutil;
import cn.edustar.jitar.query.VideoQueryParam;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.GroupArticleQueryParam;
import cn.edustar.jitar.service.GroupMemberQueryParam;
import cn.edustar.jitar.service.GroupNewsQueryParam;
import cn.edustar.jitar.service.GroupPlacardQueryParam;
import cn.edustar.jitar.service.GroupQueryParam;
import cn.edustar.jitar.service.GroupResourceQueryParam;
import cn.edustar.jitar.service.PhotoQueryParam;
import cn.edustar.jitar.service.ResourceQueryParam;

/**
 * 访问群组/协作组的数据库接口定义. 
 * 
 *
 *
 */
/**
 * @author 孟宪会
 *
 */
public interface GroupDao extends Dao {
	/**
	 * 得到指定标识的群组对象.
	 * @param groupId
	 * @return 如果未找到则返回 null.
	 */
	public Group getGroup(int groupId);

	
	public Group getGroupByGuid(String groupGuid);
	
	/**
	 * 通过群组唯一名字获得群组对.
	 * @param groupName
	 * @return
	 */
	public Group getGroupByName(String groupName);
	
	/**
	 * 通过协作组标题得到协作组对象.
	 * @param groupTitle
	 * @return
	 */
	public Group getGroupByTitle(String groupTitle);

	/**
	 * 得到指定用户未被审核通过的协作组数量.
	 * @param user
	 * @return
	 */
	public int getUserUnanditGroupCount(User user);

	/**
	 * 创建一个群组.
	 * @param group
	 */
	public void createGroup(Group group);
	
	/**
	 * 更新一个群组.
	 * @param group
	 */
	public void updateGroup(Group group);
	
	/**
	 * 删除一个协作组及其相关的 GroupArticle, GroupResource, GroupMember 等记录.
	 * @param group
	 */
	public void deleteGroup(Group group);

	/**
	 * 设置群组审核状态.
	 * @param groupId - 群组标识.
	 * @param groupState - 群组状态.
	 * @return 返回更新的记录数量，对于正确的调用，返回应该 = 1 .
	 */
	public int updateGroupState(int groupId, short groupState);

	/**
	 * 设置指定协作组的优秀团队标志.
	 * @param group
	 * @param isBestGroup
	 * @return
	 */
	public int setGroupBestGroup(Group group, boolean isBestGroup);
	
	/**
	 * 设置指定协作组的推荐协作组标志.
	 * @param group
	 * @param isRecommend
	 */
	public int setGroupRecommend(Group group, boolean isRecommend);
	
	/**
	 * 更改指定协作组的创建者.
	 * @param groupId
	 * @param createUserId
	 * @return
	 */
	public int updateGroupCreator(int groupId, int createUserId);
	
	/**
	 * 查询指定条件下的群组列表.
	 * @param param - 查询参数.
	 * @param pager - 分页参数，可能为 null.
	 * @return
	 */
	public List<Group> getGroupList(GroupQueryParam param, Pager pager);
	
	/**
	 * 得到所有群组的统计列表
	 *
	 * @return
	 */
	public List<Group> getGroupList();
	
	/**得到子组*/
	public List<Group> getGroupList(int parentId);	
	/**
	 * 根据群组Id、开始日期和结束日期，修改群组的统计信息数(包括：成员数、文章数、资源数，主题数、讨论数、活动数、群组状态)
	 *
	 * @param groupId
	 * @param bedinDate
	 * @param endDate
	 */
	public void updateGroupStat(int groupId, String beginDate, String endDate);
	
	/**
	 * 查询我加入的协作组.
	 * @param userId
	 * @return 返回为 List&lt;[Group, GroupMember]&gt; 即每个元素为一个数组,
	 *    数组中有两个元素，分别为 Group, GroupMember .
	 */
	public List<Object[]> getMyJoinedGroupList(int userId);

	/**
	 * 得到我创建的群组列表，按照 id 逆序排列.
	 * @param userId - 用户标识.
	 * @return 返回指定用户创建的群组列表.
	 */
	public List<Group> getMyCreatedGroupList(int userId);

	/** getMyInviteList() 方法返回的字段 */
	public static final String MYINVITE_LIST_FIELDS = 
		"gm.id, gm.groupId, gm.userId, gm.joinDate, gm.status, " +
		"g.groupTitle, u.nickName, u.loginName";
	
	/**
	 * 得到我发出的邀请的列表.
	 * @param userId
	 * @return 返回 List&lt;Object[]&gt; 字段顺序为: 
	 *    gm.id, gm.groupId, gm.userId, gm.joinDate, gm.status,
	 *    g.groupTitle, user.nickName, user.loginName
	 */
	public List<Object[]> getMyInviteList(int userId);
	
	/** getInviteMeList() 方法返回的字段, 返回的 nickName, loginName 为邀请人的 */
	public static final String INVITEME_LIST_FIELDS = 
		"gm.id, gm.groupId, gm.inviterId, gm.joinDate, gm.status, " +
		"g.groupTitle, u.nickName, u.loginName";
	
	/**
	 * 得到邀请我的列表.
	 * @param userId
	 * @return
	 */
	public List<Object[]> getInviteMeList(int userId);
	
	/** getMyJoinReqList() 方法返回的字段, 返回的 nickName, loginName 为群组创建者的 */
	public static final String MYJOINREQ_LIST_FIELDS = 
		"gm.id, gm.groupId, g.createUserId, gm.joinDate, gm.status, " +
		"g.groupTitle, u.nickName, u.loginName";
	
	/**
	 * 得到我发出的申请列表.
	 * @param userId
	 * @return
	 */
	public List<Object[]> getMyJoinReqList(int userId);
	
	/**
	 * 得到指定用户具有指定角色的群组集合.
	 * @param userId
	 * @param groupRoles - 角色数组，如果为 null 或者长度 = 0 则不限定角色.
	 * @return 返回在该群组 用户具有该角色 的群组用户信息集合.

	 */
	public List<GroupMember> getGroupWithRole(int userId, int[] groupRoles);
	
	/** getJoinReqList() 方法返回的字段, 返回的 nickName, loginName 为申请人的 */
	public static final String JOINREQ_LIST_FIELDS = 
		"gm.id, gm.groupId, gm.userId, gm.joinDate, gm.status, " +
		"g.groupTitle, u.nickName, u.loginName";
	
	/**
	 * 得到指定群组的加入申请列表 (以后可能要实现分页).
	 * @param groupIds - 加入申请属于哪个群组.
	 * @return 字段为 JOINREQ_LIST_FIELDS.
	 */
	public List<Object[]> getJoinReqList(int[] groupIds);

	/** getGroupMemberList() 方法返回的字段 */ 
	public static final String GROUP_USER_LIST_FIELDS = 
		"gm.id, gm.userId, gm.groupId, gm.status, gm.groupRole, gm.joinDate AS joinDate, " + 
		"gm.articleCount, gm.resourceCount, gm.topicCount, gm.replyCount, " +
		"u.loginName, u.nickName, u.email, u.userIcon";

	/**
	 * 得到指定群组中的用户列表.
	 * @param groupId
	 * @return 返回 Object[] 集合，字段参见 param.field 和 GROUP_USER_LIST_FIELDS
	 */
	public List<Object[]> getGroupMemberList(GroupMemberQueryParam param, Pager pager);
	
	
	public GroupMember getGroupMemberById(int id);
	/**
	 * 得到指定用户加入的所有群组的用户信息.
	 * @param userId - 用户标识.
	 * @param pager - 分页.
	 * @return 返回 User 集合.
	 */
	public List<User> getUserJoinedGroupMemberList(int userId, Pager pager);

	/**
	 * 保存一个群组成员关系对象.
	 * @param gm
	 */
	public void saveGroupMember(GroupMember gm);

	/**
	 * 彻底删除一个群组成员对象.
	 * @param gm
	 */
	public void destroyGroupMember(GroupMember gm);
	
	/**
	 * 根据群组标识和用户标识得到 GroupMember 对象，其包括用户在群组的角色、状态、统计等信息.
	 * @param groupId - 群组标识.
	 * @param userId - 用户标识.
	 * @return
	 */
	public GroupMember getGroupMemberByGroupIdAndUserId(int groupId, int userId);

	/**
	 * 根据群组标识和群组角色获得 GroupMember 对象.
	 * @param groupId
	 * @param groupRole
	 * @return
	 */
	public List<GroupMember> getGroupMemberByGroupIdAndRole(int groupId, int groupRole);
	
	/**
	 * 更新指定的群组用户信息的状态，使用 gm.id 做为 update 语句的参数.
	 * @param gm
	 * @param status
	 * @return 返回更新的记录数量， == 1 应该表示更新成功；其它数值应该不正确.
	 */
	public int updateGroupMemberStatus(GroupMember gm, int status);
	
	/**
	 * 更新指定群组的成员用户数量.
	 * @param groupId
	 * @return 返回当前成员用户数量； -1 表示群组不存在.
	 */
	public int updateGroupMemberCount(int groupId);

	/**
	 * 得到群组中活跃分子列表.
	 * @param groupId - 群组标识
	 * @param count - 获取数量
	 * @return 返回 List&lt;Object[GroupMember, User]&gt; 集合
	 */
	public List<Object[]> getGroupActivistList(int groupId, int count);

	/**
	 * 得到新加入群组的成员列表.
	 * @param groupId - 群组标识
	 * @param count - 获取数量
	 * @return 返回 List&lt;Object[GroupMember, User]&gt; 集合
	 */
	public List<Object[]> getNewGroupMember(int groupId, int count);

	/**
	 * 得到指定群组指定数量的最新文章列表.
	 * @param groupId - 群组标识.
	 * @param count - 获取数量.
	 * @return 返回 List&lt;Object[Article, GroupArticle]&gt; 集合.
	 */
	public List<GroupArticle> getNewGroupArticleList(int groupId, int count);
	public List<GroupArticle> getNewGroupArticleList(int groupId, int count,boolean includeChildGroup);
	/**
	 * 得到指定群组指定数量的热门文章列表.
	 * @param groupId - 群组标识.
	 * @param count - 获取数量.
	 * @return 返回 List&lt;Object[Article, GroupArticle]&gt; 集合.
	 */
	public List<GroupArticle> getHotGroupArticleList(int groupId, int count);
	public List<GroupArticle> getHotGroupArticleList(int groupId, int count,boolean includeChildGroup);
	
	/**
	 * 得到指定群组指定数量的精华文章列表.
	 * @param groupId - 群组标识.
	 * @param count - 获取数量.
	 * @return 返回 List&lt;Object[Article, GroupArticle]&gt; 集合.
	 */
	public List<GroupArticle> getBestGroupArticleList(int groupId, int count);
	public List<GroupArticle> getBestGroupArticleList(int groupId, int count,boolean includeChildGroup);
	
	/**
	 * 得到指定协作组和文章的 GroupArticle 对象.
	 * @param groupId
	 * @param articleId
	 * @return
	 */
	public GroupArticle getGroupArticleByGroupAndArticle(int groupId, int articleId);
	public GroupArticle getGroupArticle(int groupArticleId);

	/**
	 * 得到指定协作组和资源的'GroupResource'对象.
	 *
	 * @param groupId
	 * @param resourceId
	 * @return
	 */
	public GroupResource getGroupResourceByGroupAndResource(int groupId, int resourceId);
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
	 * 得到指定协作组和视频的'GroupVideo'对象.
	 * 
	 * @param groupId
	 * @param videoId
	 * @return
	 */
	public GroupVideo getGroupVideoByGroupAndVideo(int groupId,int videoId);
	public GroupVideo getGroupVideo(int groupVideoId);	
	/**
	 * 得到指定群组指定标识的文章对象.
	 * @param groupId
	 * @param articleId
	 * @return 返回 Object[Article, GroupArticle] 对象对.
	 */
	public Object[] getGroupArticleByArticleId(int groupId, int articleId);

	/**
	 * 删除指定的群组文章引用.
	 */
	public void deleteGroupArticle(GroupArticle ga);
	/**
	 * 删除协作组图片引用.
	 * 
	 * @param gp
	 */
	public void deleteGroupPhoto(GroupPhoto gp);
	
	public int deleteGroupPhotoByPhoto(int photoId);
	
	public int deleteGroupVideoByVideo(int videoId);
	/**
	 * 删除协作组视频引用.
	 * 
	 * @param gv
	 */
	public void deleteGroupVideo(GroupVideo gv);		
	/**
	 * 更新群组文章精华状态.
	 * @param groupArticleId
	 * @param best
	 * @return 返回更新的记录数量.
	 */
	public int updateGroupArticleBestState(int groupArticleId, boolean best);
	
	/**
	 * 更新指定群组中的文章数统计.
	 * @param groupId
	 * @return 返回群组内文章数量.
	 */
	public int updateGroupArticleCount(int groupId);
	
	/**
	 * 得到指定群组下指定条件的文章列表.
	 * @param groupId - 群组标识.
	 * @param param - 查询参数, 仅支持部分查找条件.
	 * @param pager - 分页要求.
	 * @return 返回 List&lt;Object[Article, GroupArticle]&gt; 集合.
	 */
	public List<GroupArticle> getGroupArticleList(int groupId, GroupArticleQueryParam param, Pager pager);
	
	/**
	 * 得到指定查询条件下的文章列表.
	 * @param ga_param
	 * @param pager
	 * @return
	 */
	public DataTable getGroupArticleDataTable(GroupArticleQueryParam ga_param, Pager pager);

	/**
	 * 更新指定群组的统计信息
	 * 
	 * @param group
	 */
	public void updateGroupStatInfo(Group group);
	
	/**
	 * 根据群组标识和资源标识获得 GroupResource
	 * 
	 * @param groupId
	 * @param resourceId
	 * @return
	 */
	public Tuple<Resource, GroupResource> getGroupResourceByResourceId(int groupId, int resourceId);

	/**
	 * 新建/更新一个 GroupResource 对象.
	 * @param gr
	 */
	public void saveGroupResource(GroupResource gr);
	
	/**
	 * 新建/更新一个 GroupArticle 对象.
	 * @param ga
	 */
	public void saveGroupArticle(GroupArticle ga);
	
	/**
	 * 删除群组资源引用.
	 * @param gr
	 */
	public void deleteGroupResource(GroupResource gr);
	
	/**
	 * 更新指定协作组的资源数量.
	 * @param groupId
	 * @return
	 */
	public int updateGroupResourceCount(int groupId);

	/**
	 * 得到指定群组指定条件下的资源列表.
	 * @param groupId
	 * @param param
	 * @param pager
	 * @return 返回为 List&lt;Object[Resource, GroupResource]&gt; 集合.
	 */
	public List<Object[]> getGroupResourceList(int groupId, ResourceQueryParam param, Pager pager);
	
	public List<Object[]> getGroupPhotoList(int groupId, PhotoQueryParam param, Pager pager);
	
	public List<Object[]> getGroupVideoList(int groupId, VideoQueryParam param, Pager pager);

	/**
	 * 得到指定群组的资源列表.
	 * @param param
	 * @param pager
	 * @return
	 */
	public DataTable getGroupResourceDataTable(GroupResourceQueryParam param, Pager pager);

	/**
	 * 得到指定群组最新 count 条资源.
	 * @param groupId
	 * @param count
	 * @return 返回 List&lt;Object[Resource, GroupResource]&gt;
	 */
	public List<Object[]> getNewGroupResourceList(int groupId, int count);
	public List<Object[]> getNewGroupResourceList(int groupId, int count,boolean includeChildGroup);

	/**
	 * 得到指定群组热门 count 条资源.
	 * @param groupId
	 * @param count
	 * @return 返回 List&lt;Object[Resource, GroupResource]&gt;
	 */
	public List<Object[]> getHotGroupResourceList(int groupId, int count);
	public List<Object[]> getHotGroupResourceList(int groupId, int count,boolean includeChildGroup);

	/**
	 * 得到指定群组精华 count 条资源.
	 * @param groupId
	 * @param count
	 * @return 返回 List&lt;Object[Resource, GroupResource]&gt;
	 */
	public List<Object[]> getBestGroupResourceList(int groupId, int count);
	public List<Object[]> getBestGroupResourceList(int groupId, int count,boolean includeChildGroup);

	/**
	 * 更新群组资源精华状态.
	 * @param groupResourceId
	 * @param best
	 * @return 返回更新的记录数量.
	 */
	public int updateGroupResourceBestState(int groupResourceId, boolean best);

	/**
	 * 更新群组资源的分类.
	 * @param gr
	 * @param groupCateId
	 * @return
	 */
	public int updateGroupResourceCategory(GroupResource gr, Integer groupCateId);
	public int updateGroupArticleCategory(GroupArticle ga, Integer groupCateId);
	public int updateGroupPhotoCategory(GroupPhoto gp, Integer groupCateId);
	public int updateGroupVideoCategory(GroupVideo gv, Integer groupCateId);

	/**
	 * 得到指定标识的资源的所有群组引用.
	 * @param resourceId
	 * @return
	 */
	public List<GroupResource> getGroupResourceByResource(int resourceId);
	
	/**
	 * 
	 * @param photoId
	 * @return
	 */
	public List<GroupPhoto> getGroupPhotoByPhoto(int photoId);
	
	/**
	 * 
	 * @param videoId
	 * @return
	 */
	public List<GroupVideo> getGroupVideoByVideo(int videoId);

	/**
	 * 删除指定资源标识的所有资源引用.
	 * @param resourceId
	 * @return 返回删除的记录数.
	 */
	public int deleteGroupResourceByResource(int resourceId);
	
	/**
	 * 增加/减少指定群组的资源数.
	 * @param groupId
	 * @param inc_num - 大于 0 表示增加, 小于 0 表示减少, = 0 不处理.
	 */
	public void incGroupResourceCount(int groupId, int inc_num);

	
	/**
	 * 得到指定标识的群组新闻对象.
	 * @param newsId
	 * @return
	 */
	public GroupNews getGroupNews(int newsId);
	
	/**
	 * 创建或更新指定的群组新闻对象.
	 * @param gn
	 */
	public void saveOrUpdateGroupNews(GroupNews gn);
	
	/**
	 * 增加/减少指定群组新闻的点击次数.
	 * @param gn
	 * @param incCount
	 */
	public void incGroupNewsViewCount(GroupNews gn, int incCount);
	
	/**
	 * 得到指定条件的群组新闻列表.
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Object[]> getGroupNewsDataTable(GroupNewsQueryParam param, Pager pager);

	/**
	 * 得到指定条件的群组公告列表.
	 * @param param - 查询参数.
	 * @param pager - 分页参数.
	 * @return
	 */
	public List<Object[]> getGroupPlacardDataTable(GroupPlacardQueryParam param, Pager pager);

	/**
	 * 重新统计并更新本协作组中主题数量.
	 * @param groupId
	 */
	public int updateGroupTopicCount(int groupId);
	
	public void UpdateBestGroupArticleAndResource();
	
	public boolean isBestInGroupArticle(int articleId);
	public boolean isBestInGroupResource(int resourceId);
	
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
	
	public int updateGroupPhotoCount(int groupId);
	public int updateGroupVideoCount(int groupId);
	
}
