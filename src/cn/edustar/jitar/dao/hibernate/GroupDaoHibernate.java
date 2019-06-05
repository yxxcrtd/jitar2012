package cn.edustar.jitar.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.jdbc.Work;

import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.GroupDao;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupArticle;
import cn.edustar.jitar.pojos.GroupDataQuery;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.GroupMutil;
import cn.edustar.jitar.pojos.GroupNews;
import cn.edustar.jitar.pojos.GroupPhoto;
import cn.edustar.jitar.pojos.GroupResource;
import cn.edustar.jitar.pojos.GroupVideo;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.PrepareCoursePlan;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.query.VideoQueryParam;
import cn.edustar.jitar.service.GroupArticleQueryParam;
import cn.edustar.jitar.service.GroupMemberQueryParam;
import cn.edustar.jitar.service.GroupNewsQueryParam;
import cn.edustar.jitar.service.GroupPlacardQueryParam;
import cn.edustar.jitar.service.GroupQueryParam;
import cn.edustar.jitar.service.GroupResourceQueryParam;
import cn.edustar.jitar.service.PhotoQueryParam;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 群组信息数据库访问实现.
 * 
 *
 */
public class GroupDaoHibernate extends BaseDaoHibernate implements GroupDao {

	/** 日志 */
	private static final Log log = LogFactory.getLog(GroupDaoHibernate.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getGroup(int)
	 */
	@SuppressWarnings("unchecked")
	public Group getGroup(int groupId) {
		String hql = "FROM Group WHERE groupId = ?";
		List list = this.getSession().createQuery(hql).setInteger(0, groupId).list();
		if (list == null || list.size() == 0)
			return null;
		return (Group) list.get(0);
	}
	
	public List<Group> getGroupList(int parentId){
		String hql = "FROM Group WHERE parentId = ?";
		List<Group> list = this.getSession().createQuery(hql).setInteger(0, parentId).list();
		return list;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupByName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Group getGroupByName(String groupName) {
		String hql = "FROM Group WHERE groupName = ?";
		List list = this.getSession().createQuery(hql).setString(0, groupName).list();

		if (list == null || list.size() == 0)
			return null;
		return (Group) list.get(0);
	}

	@SuppressWarnings("unchecked")
	public Group getGroupByGuid(String groupGuid)
	{
		String hql = "FROM Group WHERE groupGuid = ?";
		List list = this.getSession().createQuery(hql).setString(0, groupGuid).list();

		if (list == null || list.size() == 0)
			return null;
		return (Group) list.get(0);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupByTitle(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Group getGroupByTitle(String groupTitle) {
		String hql = "FROM Group WHERE groupTitle = ?";
		List list = this.getSession().createQuery(hql).setString(0, groupTitle).list();

		if (list == null || list.size() == 0)
			return null;
		return (Group) list.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getUserUnanditGroupCount(cn.edustar.jitar.pojos.User)
	 */
	public int getUserUnanditGroupCount(User user) {
		// 逻辑: 计算该用户创建的, 审核状态为 待审核 的协作组数量.
		String hql = "SELECT COUNT(*) FROM Group WHERE createUserId = ? AND groupState = ?";
		return ((Long)this.getSession().createQuery(hql).setInteger(0, user.getUserId()).setShort(1, Group.GROUP_STATE_WAIT_AUDIT).iterate().next() ).intValue();
		//return getSession().executeIntScalar(hql, user.getUserId(), Group.GROUP_STATE_WAIT_AUDIT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#createGroup(cn.edustar.jitar.pojos.Group)
	 */
	public void createGroup(Group group) {
		this.getSession().save(group);
		this.getSession().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#saveGroup(cn.edustar.jitar.pojos.Group)
	 */
	public void updateGroup(Group group) {
		this.getSession().update(group);
		this.getSession().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#deleteGroup(cn.edustar.jitar.pojos.Group)
	 */
	public void deleteGroup(Group group) {
		boolean debug = log.isDebugEnabled();
		if (debug)
			log.debug("deleteGroup groupId = " + group.getGroupId() + ", name = " + group.getGroupName() + ", title = " + group.getGroupTitle());

		//删除子协作组！！！！
		List<Group> childGroups= getGroupList(group.getGroupId());
		for(Group p : childGroups){
			deleteGroup(p);
		}
		// 删除协作组文章引用.
		String hql = "DELETE FROM GroupArticle WHERE groupId = ?";
		int count = this.getSession().createQuery(hql).setInteger(0, group.getGroupId()).executeUpdate();
		if (debug)
			log.debug("deleteGroupArticle DeleteCount = " + count);

		// 删除协作组成员记录.
		hql = "DELETE FROM GroupMember WHERE groupId = ?";
		count = this.getSession().createQuery(hql).setInteger(0, group.getGroupId()).executeUpdate();
		if (debug)
			log.debug("deleteGroupMember DeleteCount = " + count);

		// 删除协作组新闻.
		hql = "DELETE FROM GroupNews WHERE groupId = ?";
		count = this.getSession().createQuery(hql).setInteger(0, group.getGroupId()).executeUpdate();
		if (debug)
			log.debug("deleteGroupNews DeleteCount = " + count);

		// 删除协作组关系.
		hql = "DELETE FROM GroupRelation WHERE srcGroup = ? OR dstGroup = ?";
		count = this.getSession().createQuery(hql).setInteger(0, group.getGroupId()).setInteger(1, group.getGroupId()).executeUpdate();
		if (debug)
			log.debug("deleteGroupRelation DeleteCount = " + count);

		// 删除协作组资源.
		hql = "DELETE FROM GroupResource WHERE groupId = ?";
		count = this.getSession().createQuery(hql).setInteger(0, group.getGroupId()).executeUpdate();
		if (debug)
			log.debug("deleteGroupResource DeleteCount = " + count);

		//删除协作组图片
		hql = "DELETE FROM GroupPhoto WHERE groupId = ?";
		count = this.getSession().createQuery(hql).setInteger(0, group.getGroupId()).executeUpdate();
		if (debug)
			log.debug("deleteGroupPhoto DeleteCount = " + count);

		//删除协作组视频
		hql = "DELETE FROM GroupVideo WHERE groupId = ?";
		count = this.getSession().createQuery(hql).setInteger(0, group.getGroupId()).executeUpdate();
		if (debug)
			log.debug("deleteGroupVideo DeleteCount = " + count);

		//如果是课题组，则删除课题组负责人
		hql = "DELETE FROM GroupKTUser WHERE groupId = ?";
		count = this.getSession().createQuery(hql).setInteger(0, group.getGroupId()).executeUpdate();
		if (debug)
			log.debug("deleteGroupKTUser DeleteCount = " + count);

		
		// 删除协作组主题和回复.
		hql = "DELETE FROM Reply WHERE groupId = ?";
		count = this.getSession().createQuery(hql).setInteger(0, group.getGroupId()).executeUpdate();
		if (debug)
			log.debug("deleteGroupReply DeleteCount = " + count);

		hql = "DELETE FROM Topic WHERE groupId = ?";
		count = this.getSession().createQuery(hql).setInteger(0, group.getGroupId()).executeUpdate();
		if (debug)
			log.debug("deleteGroupTopic DeleteCount = " + count);

		
		deletePrepareCourseOfGroup(group.getGroupId(),0);
		// 删除协作组自身.
		this.getSession().delete(group);
		this.getSession().flush();
	}

	@SuppressWarnings("unchecked")
	public void deletePrepareCourseOfGroup(int groupId,int userId)
	{
		String queryString;
		//得到协作组的所有备课计划
		List<PrepareCoursePlan> plan_list;
		if(userId == 0)
		{
			queryString = "FROM PrepareCoursePlan Where groupId = ?";
			plan_list = (List<PrepareCoursePlan>)this.getSession().createQuery(queryString).setInteger(0, groupId).list();
		}
		else
		{
			queryString = "FROM PrepareCoursePlan Where groupId = ? And createUserId=?";
			plan_list = (List<PrepareCoursePlan>)this.getSession().createQuery(queryString).setInteger(0, groupId).setInteger(1, userId).list();
		}
		
		for(int i = 0; i< plan_list.size();i++)
		{
			PrepareCoursePlan plan = (PrepareCoursePlan)plan_list.get(i);			
			queryString = "FROM PrepareCourse Where prepareCoursePlanId = ?";
			List<PrepareCourse> pc_list = (List<PrepareCourse>)this.getSession().createQuery(queryString).setInteger(0, plan.getPrepareCoursePlanId()).list(); 
			//this.getSession().find(queryString, plan.getPrepareCoursePlanId());
			
			for(int j = 0;j<pc_list.size();j++)
			{
				PrepareCourse pc = (PrepareCourse)pc_list.get(j);
				int prepareCourseId = pc.getPrepareCourseId();
				
				queryString = "DELETE FROM PrepareCourseTopicReply WHERE prepareCourseId = ?";
				this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
				
				queryString = "DELETE FROM PrepareCourseTopic WHERE prepareCourseId = ?";
				this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
				
				queryString = "DELETE FROM PrepareCourseResource WHERE prepareCourseId = ?";
				this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
				
				queryString = "DELETE FROM PrepareCourseArticle WHERE prepareCourseId = ?";
				this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
				
				queryString = "DELETE FROM PrepareCoursePrivateComment WHERE prepareCourseId = ?";
				this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
				
				queryString = "DELETE FROM PrepareCourseEdit WHERE prepareCourseId = ?";
				this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
				
				queryString = "DELETE FROM PrepareCourseStage WHERE prepareCourseId = ?";
				this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
				
				queryString = "DELETE FROM PrepareCourseMember WHERE prepareCourseId = ?";
				this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
				
				queryString = "DELETE FROM PrepareCourse WHERE prepareCourseId = ?";
				this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
			}
			
			queryString = "DELETE FROM PrepareCoursePlan Where prepareCoursePlanId = ?";
			this.getSession().createQuery(queryString).setInteger(0, plan.getPrepareCoursePlanId()).executeUpdate();
		}
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#updateGroupState(int, short)
	 */
	public int updateGroupState(int groupId, short groupState) {
		//子群组同时处理！
		String hql2 = "UPDATE Group SET groupState = ? WHERE parentId = ?";
		this.getSession().createQuery(hql2).setShort(0, groupState).setInteger(1, groupId).executeUpdate();
		//getSession().bulkUpdate(hql2, new Object[] { groupState, groupId });
		
		String hql = "UPDATE Group SET groupState = ? WHERE groupId = ?";
		return this.getSession().createQuery(hql).setShort(0, groupState).setInteger(1, groupId).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#updateGroupCreator(int, int)
	 */
	public int updateGroupCreator(int groupId, int createUserId) {
		String hql = "UPDATE Group SET createUserId = ? WHERE groupId = ?";
		return this.getSession().createQuery(hql).setInteger(0, createUserId).setInteger(1, groupId).executeUpdate();
		//getSession().bulkUpdate(hql, new Object[] { createUserId, groupId });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#setGroupBestGroup(cn.edustar.jitar.pojos.Group,
	 *      boolean)
	 */
	public int setGroupBestGroup(Group group, boolean isBestGroup) {
		String hql = "UPDATE Group SET isBestGroup = ? WHERE groupId = ?";
		return this.getSession().createQuery(hql).setBoolean(0, isBestGroup).setInteger(1, group.getGroupId()).executeUpdate();
		//return getSession().bulkUpdate(hql, new Object[] { isBestGroup, group.getGroupId() });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#setGroupRecommend(cn.edustar.jitar.pojos.Group,
	 *      boolean)
	 */
	public int setGroupRecommend(Group group, boolean isRecommend) {
		String hql = "UPDATE Group SET isRecommend = ? WHERE groupId = ?";
		return this.getSession().createQuery(hql).setBoolean(0, isRecommend).setInteger(1, group.getGroupId()).executeUpdate();
		//return getSession().bulkUpdate(hql, new Object[] { isRecommend, group.getGroupId() });
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupList(cn.edustar.jitar.service.GroupQueryParam,
	 *      cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Group> getGroupList(GroupQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager != null) {
			return query.queryDataAndTotalCount(this.getSession(),pager);
		} else {
			return query.queryData(this.getSession(), -1, param.count);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupList()
	 */
	@SuppressWarnings("unchecked")
	public List<Group> getGroupList() {
		return this.getSession().createQuery("FROM Group").list();
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#updateGroupStat(int, java.lang.String, java.lang.String)
	 */
	public void updateGroupStat(final int groupId, final String beginDate, final String endDate) {		
		this.getSession().doWork(new Work() {
			public void execute(Connection connection)	throws SQLException {
			CallableStatement cs = connection.prepareCall("{call statAllGroup(?, ?, ?)}");
			cs.setInt(1, groupId);
			cs.setString(2, beginDate);
			cs.setString(3, endDate);
			cs.executeUpdate();
		}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getMyJoinedGroupList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getMyJoinedGroupList(int userId) {
		String hql = "SELECT g, u FROM Group g, GroupMember u WHERE g.groupId = u.groupId AND u.userId = " + userId + " ORDER BY g.createDate DESC";
		return this.getSession().createQuery(hql).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getMyCreatedGroupList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Group> getMyCreatedGroupList(int userId) {
		String hql = "FROM Group WHERE createUserId = " + userId + " ORDER BY groupId DESC";
		return  this.getSession().createQuery(hql).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getMyInviteList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getMyInviteList(int userId) {
		String hql = "SELECT "
				+ MYINVITE_LIST_FIELDS
				+ " FROM GroupMember gm, Group g, User u "
				+ " WHERE g.groupId = gm.groupId AND gm.userId = u.userId "
				+ " AND g.groupState = 0 "
				+ " AND gm.inviterId = " + userId + "   AND gm.status = "
				+ GroupMember.STATUS_INVITING 
				+ " ORDER BY gm.id DESC";
		return  this.getSession().createQuery(hql).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getInviteMeList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getInviteMeList(int userId) {
		String hql = "SELECT " + INVITEME_LIST_FIELDS
				+ " FROM GroupMember gm, Group g, User u "
				+ " WHERE g.groupId = gm.groupId "
				+ " AND gm.inviterId = u.userId "
				+ " AND gm.inviterId IS NOT NULL " + " AND g.groupState = 0 "
				+ " AND gm.userId = " + userId + " AND gm.status = "
				+ GroupMember.STATUS_INVITING +
				" ORDER BY gm.id DESC ";
		return  this.getSession().createQuery(hql).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getMyJoinReqList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getMyJoinReqList(int userId) {
		String hql = "SELECT " + MYJOINREQ_LIST_FIELDS
				+ " FROM GroupMember gm, Group g, User u "
				+ " WHERE g.groupId = gm.groupId "
				+ "   AND g.createUserId = u.userId " + "   AND gm.userId = "
				+ userId + "   AND gm.status = " + GroupMember.STATUS_WAIT_AUDIT
				+ " ORDER BY gm.id DESC ";
		return  this.getSession().createQuery(hql).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupWithRole(int, int[])
	 */
	@SuppressWarnings("unchecked")
	public List<GroupMember> getGroupWithRole(int userId, int[] groupRoles) {
		String hql = " FROM GroupMember gm " + " WHERE gm.userId = " + userId;
		if (groupRoles == null || groupRoles.length == 0) {
		} else {
			hql += "  AND gm.groupRole IN " + CommonUtil.toSqlInString(groupRoles);
		}
		hql += " AND gm.status = 0";
		return  this.getSession().createQuery(hql).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getJoinReqList(int[])
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getJoinReqList(int[] groupIds) {
		if (groupIds == null || groupIds.length == 0)
			return null;

		String hql = "SELECT " + JOINREQ_LIST_FIELDS
				+ " FROM GroupMember gm, Group g, User u "
				+ " WHERE g.groupId = gm.groupId " + "   AND gm.userId = u.userId "
				+ "   AND gm.groupId IN " + CommonUtil.toSqlInString(groupIds)
				+ "   AND gm.status = " + GroupMember.STATUS_WAIT_AUDIT
				+ " ORDER BY gm.id DESC ";
		return  this.getSession().createQuery(hql).list();
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupMemberList(cn.edustar.jitar.service.GroupMemberQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getGroupMemberList(GroupMemberQueryParam param, Pager pager) {
		// 也许以后我们可以把 SQL 语句放到配置里面.
		QueryHelper query = param.createQuery();

		// 如果有分页需求，则用分页查询，否则查询所有.
		if (pager == null)
			return query.queryData(this.getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(this.getSession(), pager);
	}

	/** 得到指定用户加入的所有群组的标识 */
	@SuppressWarnings("unchecked")
	private List<Integer> getUserJoinedGroupIds(int userId) {
		String hql = "SELECT DISTINCT gm.groupId" + " FROM GroupMember gm WHERE gm.userId = ?";
		return  this.getSession().createQuery(hql).setInteger(0, userId).list();
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#getUserJoinedGroupMemberList(int, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUserJoinedGroupMemberList(int userId, Pager pager) {
		// 得到用户加入的所有群组.
		List<Integer> group_ids = getUserJoinedGroupIds(userId);
		if (group_ids == null || group_ids.size() == 0)
			return java.util.Collections.EMPTY_LIST;

		// 构造查询.
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT DISTINCT u ";
		query.fromClause = " FROM GroupMember gm, User u ";
		query.whereClause = " WHERE gm.userId = u.userId AND gm.groupId IN " + CommonUtil.toSqlInString(group_ids);
		query.orderClause = " ORDER BY u.id DESC";

		// 如果有分页需求，则用分页查询，否则查询所有.
		if (pager == null) {
			return query.queryData(this.getSession());
		} else {
			pager.setTotalRows(query.queryTotalCount(this.getSession()));
			return query.queryData(this.getSession(), pager);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#saveGroupMember(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void saveGroupMember(GroupMember gm) {
		this.getSession().saveOrUpdate(gm);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#destroyGroupMember(cn.edustar.jitar.pojos.GroupMember)
	 */
	public void destroyGroupMember(GroupMember gm) {
		deletePrepareCourseOfGroup(gm.getGroupId(),gm.getUserId());
		this.getSession().delete(gm);
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupMemberByGroupIdAndUserId(int, int)
	 */
	public GroupMember getGroupMemberByGroupIdAndUserId(int groupId, int userId) {
		String hql = "FROM GroupMember WHERE groupId = ? AND userId = ?";
		List<GroupMember> ls = this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, userId).list();
		if(ls == null || ls.size() == 0) return null;
		return (GroupMember)ls.get(0);
		//return (GroupMember) getSession().findFirst(hql, groupId, userId);
	}

	public GroupMember getGroupMemberById(int Id) {
		String hql = "FROM GroupMember WHERE id = ?";
		List<GroupMember> ls = this.getSession().createQuery(hql).setInteger(0, Id).list();
		if(ls == null || ls.size() == 0) return null;
		return (GroupMember)ls.get(0);
		//return (GroupMember) getSession().findFirst(hql, Id);
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupMemberByGroupIdAndRole(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<GroupMember> getGroupMemberByGroupIdAndRole(int groupId, int groupRole) {
		String hql = "FROM GroupMember WHERE groupId = ? AND groupRole = ?";
		return this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, groupRole).list();
		//return getSession().find(hql, new Object[] { groupId, groupRole });
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#updateGroupMemberStatus(cn.edustar.jitar.pojos.GroupMember, int)
	 */
	public int updateGroupMemberStatus(GroupMember gm, int status) {
		String hql = "UPDATE GroupMember SET status = ? WHERE id = ?";
		return this.getSession().createQuery(hql).setInteger(0, status).setInteger(1, gm.getId() ).executeUpdate();
		//return getSession().bulkUpdate(hql, new Object[] { status, gm.getId() });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#updateGroupMemberCount(int)
	 */
	public int updateGroupMemberCount(int groupId) {
		// 1，统计现在群组成员用户数量
		String stat_hql = "SELECT COUNT(*) FROM GroupMember gm, User u WHERE gm.userId = u.userId AND gm.groupId = ? AND gm.status = " + GroupMember.STATUS_NORMAL;
		Object o = this.getSession().createQuery(stat_hql).setInteger(0, groupId).uniqueResult();
		int count = 0;
		if(o != null)
		{
			count = Integer.valueOf(o.toString()).intValue();
		}
		//getSession().executeIntScalar(stat_hql, groupId);
		log.info("当前群组的成员个数：" + count);

		// 2，更新群组信息
		String update_hql = "UPDATE Group SET userCount = ? WHERE groupId = ?";		
		
		int update_count = this.getSession().createQuery(update_hql).setInteger(0, count).setInteger(1, groupId).executeUpdate();
				
				//getSession().bulkUpdate(update_hql, new Object[] { count, groupId });

		// 如果群组不存在，则 update_count 将发生 = 0 的事情.
		if (update_count != 1)
			return -1;
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupActivistList(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getGroupActivistList(int groupId, int count) {
		// 活跃分子是按照其在群组中发文数量排列, 也许以后有更好的算法.
		String hql = "SELECT gm, u FROM User u, GroupMember gm WHERE gm.userId = u.userId AND gm.groupId = ? AND gm.status = 0 ORDER BY gm.articleCount DESC";
		
		//return this.getSession().createQuery(hql).setInteger(0, groupId).setFirstResult(count).list();
		//setFirstResult表示从查询得到的结果集的第几条开始获取，setMaxResults表示获取多少条数据
		return this.getSession().createQuery(hql).setInteger(0, groupId).setFirstResult(0).setMaxResults(count).list();
		//return getSession().findTopCount(hql, count, groupId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getNewGroupMember(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getNewGroupMember(int groupId, int count) {
		// 最新成员毫无疑问是按照加入时间排列了.
		String hql = "SELECT gm, u " + " FROM User u, GroupMember gm WHERE gm.userId = u.userId AND gm.groupId = ? AND gm.status = 0 ORDER BY gm.joinDate DESC";
		//return this.getSession().createQuery(hql).setInteger(0, groupId).setFirstResult(count).list();  //BUG
		//setFirstResult表示从查询得到的结果集的第几条开始获取，setMaxResults表示获取多少条数据
		return this.getSession().createQuery(hql).setInteger(0, groupId).setFirstResult(0).setMaxResults(count).list();
		//return getSession().findTopCount(hql, count, groupId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getNewGroupArticleList(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<GroupArticle> getNewGroupArticleList(int groupId, int count) {
		String hql = "SELECT ga FROM GroupArticle ga"
				+ " WHERE ga.groupId = ? And ga.articleState = true "
				+ " ORDER BY ga.pubDate DESC, ga.id DESC ";
		return this.getSession().createQuery(hql).setInteger(0, groupId).setFirstResult(0).setMaxResults(count).list();
		//return findTopCount(hql, count, groupId);
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupArticle> getNewGroupArticleList(int groupId, int count,boolean includeChildGroup) {
		if(includeChildGroup){
			String hql = "SELECT ga FROM GroupArticle ga"
					+ " WHERE (ga.groupId = ? Or ga.groupId In(SELECT groupId FROM Group WHERE parentId=?)) And ga.articleState = true "
					+ " ORDER BY ga.pubDate DESC, ga.id DESC ";
			return this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, groupId).setFirstResult(0).setMaxResults(count).list();
			//return getSession().findTopCount(hql, count, groupId, groupId);
		}else{
			return getNewGroupArticleList(groupId, count);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getHotGroupArticleList(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<GroupArticle> getHotGroupArticleList(int groupId, int count) {
		String hql = "SELECT ga FROM GroupArticle ga"
				+ " WHERE ga.groupId = ? And ga.articleState = true "
				+ " ORDER BY ga.id DESC ";
		//return this.getSession().createQuery(hql).setInteger(0, groupId).setFirstResult(count).list(); //BUG
		//setFirstResult表示从查询得到的结果集的第几条开始获取，setMaxResults表示获取多少条数据
		return this.getSession().createQuery(hql).setInteger(0, groupId).setFirstResult(0).setMaxResults(count).list();
		//return getSession().findTopCount(hql, count, groupId);
	}
	@SuppressWarnings("unchecked")
	public List<GroupArticle> getHotGroupArticleList(int groupId, int count,boolean includeChildGroup) {
		if(includeChildGroup){
		String hql = "SELECT ga FROM GroupArticle ga"
				+ " WHERE (ga.groupId = ? Or ga.groupId In(SELECT groupId FROM Group WHERE parentId=?)) And ga.articleState = true "
				+ " ORDER BY ga.id DESC ";
		return this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, groupId).setFirstResult(0).setMaxResults(count).list();
		//return getSession().findTopCount(hql, count, groupId,groupId);
		}else{
			return getHotGroupArticleList(groupId, count);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getBestGroupArticleList(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<GroupArticle> getBestGroupArticleList(int groupId, int count) {
		String hql = "SELECT ga FROM GroupArticle ga"
				+ " WHERE ga.groupId = ? And ga.isGroupBest = true And ga.articleState = true"	
				+ " ORDER BY ga.id DESC "; 
		//return this.getSession().createQuery(hql).setInteger(0, groupId).setFirstResult(count).list(); //BUG
		//setFirstResult表示从查询得到的结果集的第几条开始获取，setMaxResults表示获取多少条数据
		return this.getSession().createQuery(hql).setInteger(0, groupId).setFirstResult(0).setMaxResults(count).list();
		//return getSession().findTopCount(hql, count, groupId);
	}
	@SuppressWarnings("unchecked")
	public List<GroupArticle> getBestGroupArticleList(int groupId, int count,boolean includeChildGroup) {
		if(includeChildGroup){
		String hql = "SELECT ga FROM GroupArticle ga"
				+ " WHERE (ga.groupId = ? Or ga.groupId In(SELECT groupId FROM Group WHERE parentId=?)) And ga.isGroupBest = true And ga.articleState = true"	
				+ " ORDER BY ga.id DESC "; 
		return this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, groupId).setFirstResult(0).setMaxResults(count).list();
		//return getSession().findTopCount(hql, count, groupId,groupId);
		}
		else{
			return getBestGroupArticleList(groupId, count);
		}
	}
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupArticleByGroupAndArticle(int, int)
	 */
	public GroupArticle getGroupArticleByGroupAndArticle(int groupId, int articleId) {
		String hql = "FROM GroupArticle WHERE groupId = ? AND articleId = ?";
		List<GroupArticle> ls = this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, articleId).list();
		if(ls == null || ls.size() == 0) return null;
		return (GroupArticle)ls.get(0);
		//return (GroupArticle) getSession().findFirst(hql, groupId, articleId);
	}
	
	public GroupArticle getGroupArticle(int groupArticleId)
	{
		return (GroupArticle)this.getSession().get(GroupArticle.class, groupArticleId);
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupResourceByGroupAndResource(int, int)
	 */
	public GroupResource getGroupResourceByGroupAndResource(int groupId, int resourceId) {
		String hql = "FROM GroupResource WHERE groupId = ? AND resourceId = ?";
		List<GroupResource> ls = this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, resourceId).list();
		if(ls == null || ls.size() == 0) return null;
		return (GroupResource)ls.get(0);

	}

	public GroupResource getGroupResource(int groupResourceId)
	{
		return (GroupResource)this.getSession().get(GroupResource.class, groupResourceId);
	}
	
	public GroupPhoto getGroupPhotoByGroupAndPhoto(int groupId,int photoId){
		String hql = "FROM GroupPhoto WHERE groupId = ? AND photoId = ?";
		List<GroupPhoto> ls = this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, photoId).list();
		if(ls == null || ls.size() == 0) return null;
		return (GroupPhoto)ls.get(0);
				//getSession().findFirst(hql, groupId, photoId);
	}
	
	public GroupPhoto getGroupPhoto(int groupPhotoId){
		return (GroupPhoto)this.getSession().get(GroupPhoto.class, groupPhotoId);
	}
	
	public GroupVideo getGroupVideoByGroupAndVideo(int groupId,int videoId){
		String hql = "FROM GroupVideo WHERE groupId = ? AND videoId = ?";
		List<GroupVideo> ls = this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, videoId).list();
		if(ls == null || ls.size() == 0) return null;
		return (GroupVideo)ls.get(0);
				
				//getSession().findFirst(hql, groupId, videoId);
	}
	
	public GroupVideo getGroupVideo(int groupVideoId){
		return (GroupVideo)this.getSession().get(GroupVideo.class, groupVideoId);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupArticleByArticleId(int, int)
	 */
	public Object[] getGroupArticleByArticleId(int groupId, int articleId) {
		String hql = "SELECT a, ga FROM GroupArticle ga, Article a WHERE ga.groupId = ? AND ga.articleId = a.articleId AND a.articleId = ?";
		List x = this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, articleId).list();
		if(x == null || x.size() == 0) return null;
		return (Object[])x.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#deleteGroupArticle(cn.edustar.jitar.pojos.GroupArticle)
	 */
	public void deleteGroupArticle(GroupArticle ga) {
		this.getSession().delete(ga);
		this.getSession().flush();
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#updateGroupArticleBestState(int, boolean)
	 */
	public int updateGroupArticleBestState(int groupArticleId, boolean best) {
		String hql = "UPDATE GroupArticle " + " SET isGroupBest = ? WHERE id = ? ";
		return this.getSession().createQuery(hql).setBoolean(0, best).setInteger(1, groupArticleId).executeUpdate(); // getSession().bulkUpdate(hql, new Object[] { best, groupArticleId });
	}

	public boolean isBestInGroupArticle(int articleId)
	{
		String stat_hql = "SELECT COUNT(*) FROM GroupArticle ga, Article a WHERE ga.articleId = a.articleId AND a.articleId = ? AND ga.isGroupBest = true";
		Object o = this.getSession().createQuery(stat_hql).setInteger(0, articleId).uniqueResult();
		if(o == null) return false;		
		int count = Integer.valueOf(o.toString()).intValue(); //getSession().executeIntScalar(stat_hql, articleId);
		if (count>0)
			return true;
		else
			return false;
	}

	public boolean isBestInGroupResource(int resourceId)
	{
		String stat_hql = "SELECT COUNT(*) FROM GroupResource gr, Resource a WHERE gr.resourceId = a.resourceId AND a.resourceId = ? AND gr.isGroupBest = true";
		Object o = this.getSession().createQuery(stat_hql).setInteger(0, resourceId).uniqueResult();
		if(o == null) return false;		
		int count = Integer.valueOf(o.toString()).intValue(); //getSession().executeIntScalar(stat_hql, articleId);
		if (count>0)
			return true;
		else
			return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#updateGroupArticleCount(int)
	 */
	public int updateGroupArticleCount(int groupId) {
		// 1. 统计现在群组文章数量
		String stat_hql = "SELECT COUNT(*) FROM GroupArticle ga WHERE ga.groupId = ? And ga.articleState = true";
		Object o = this.getSession().createQuery(stat_hql).setInteger(0, groupId).uniqueResult();
		int count = 0;
		if(o != null) {
			count = Integer.valueOf(o.toString()).intValue();
		}
		
		log.info("当前群组的文章数量：" + count);

		// 2. 更新群组信息
		String update_hql = "UPDATE Group SET articleCount = ? WHERE groupId = ?";
		int update_count = this.getSession().createQuery(update_hql).setInteger(0, count).setInteger(1, groupId).executeUpdate();

		// 如果群组不存在，则 update_count 将发生 = 0 的事情
		if (update_count != 1)
			return -1;
		return count;
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupArticleList(int, cn.edustar.jitar.service.ArticleQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<GroupArticle> getGroupArticleList(int groupId, GroupArticleQueryParam param, Pager pager) {
		// 根据条件创建查询对象.
		QueryHelper query = param.createQuery();
		query.selectClause = "SELECT ga ";
		query.fromClause = " FROM GroupArticle ga ";
		query.addAndWhere("ga.groupId = " + groupId);

		if (pager == null) {
			// 按照 param 中指定的数量获取.
			return query.queryData(this.getSession(), -1, param.count);
		} else {
			return query.queryDataAndTotalCount(this.getSession(), pager);
		}
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupArticleDataTable(cn.edustar.jitar.service.GroupArticleQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public DataTable getGroupArticleDataTable(GroupArticleQueryParam ga_param, Pager pager) {
		// 查询.
		QueryHelper query = ga_param.createQuery();
		List list = pager == null ? query.queryData(this.getSession(), -1, ga_param.count) : query.queryDataAndTotalCount(this.getSession(), pager);

		// 组装为 dataTable.
		DataTable dt = new DataTable(new DataSchema(ga_param.selectFields));
		dt.addList(list);
		return dt;
	}

	/*
	 * 群组的统计信息包括：组内成员数，组内文章数，组内资源数和组内主题数
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#updateGroupStatInfo(cn.edustar.jitar.pojos.Group)
	 */
	public void updateGroupStatInfo(Group group) {
		this.updateGroupMemberCount(group.getGroupId());
		this.updateGroupArticleCount(group.getGroupId());
		this.updateGroupResourceCount(group.getGroupId());
		this.updateGroupTopicCount(group.getGroupId());
		this.updateGroupPhotoCount(group.getGroupId());
		this.updateGroupVideoCount(group.getGroupId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupResourceByResourceId(int, int)
	 */
	public Tuple<Resource, GroupResource> getGroupResourceByResourceId(int groupId, int resourceId) {
		String hql = "SELECT r, gr FROM Resource r, GroupResource gr WHERE r.resourceId = gr.resourceId AND gr.groupId = ? AND r.resourceId = ?";
		List x = this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, resourceId).list();
		if(x == null || x.size() == 0) return null;
		Object[] o = (Object[])x.get(0);
		if (o == null)
			return null;
		return new Tuple<Resource, GroupResource>((Resource) o[0], (GroupResource) o[1]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#saveGroupResource(cn.edustar.jitar.pojos.GroupResource)
	 */
	public void saveGroupResource(GroupResource gr) {
		this.getSession().saveOrUpdate(gr);
	}

	public Tuple<Photo, GroupPhoto> getGroupPhotoByPhotoId(int groupId, int photoId){
		String hql = "SELECT p, gp FROM Photo p, GroupPhoto gp WHERE p.photoId = gp.photoId AND gp.groupId = ? AND p.photoId = ?";
		List x = this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, photoId).list();
		if(x == null || x.size() == 0) return null;
		Object[] o = (Object[])x.get(0);
		if (o == null)
			return null;
		return new Tuple<Photo, GroupPhoto>((Photo) o[0], (GroupPhoto) o[1]);
	}
	
	public void publishPhotoToGroup(GroupPhoto gp){
		this.getSession().saveOrUpdate(gp);
	}

	public Tuple<Video, GroupVideo> getGroupVideoByVideoId(int groupId, int videoId){
		String hql = "SELECT v, gv FROM Video v, GroupVideo gv WHERE v.videoId = gv.videoId AND gv.groupId = ? AND v.videoId = ?";
		List x = this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, videoId).list();
		if(x == null || x.size() == 0) return null;		
		Object[] o = (Object[])x.get(0);
		if (o == null)
			return null;
		return new Tuple<Video, GroupVideo>((Video) o[0], (GroupVideo) o[1]);
	}
	
	public void publishVideoToGroup(GroupVideo gv){
		this.getSession().saveOrUpdate(gv);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#saveGroupArticle(cn.edustar.jitar.pojos.GroupArticle)
	 */
	public void saveGroupArticle(GroupArticle ga) {
		this.getSession().saveOrUpdate(ga);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#deleteGroupResource(cn.edustar.jitar.pojos.GroupResource)
	 */
	public void deleteGroupResource(GroupResource gr) {
		this.getSession().delete(gr);
	}

	public int updateGroupVideoCount(int groupId) {
		// 1. 统计现在群组视频数量
		String stat_hql = "SELECT COUNT(*) FROM GroupVideo gv, Video v WHERE gv.videoId = v.videoId AND gv.groupId = ? AND v.auditState = 0";
		Object o = this.getSession().createQuery(stat_hql).setInteger(0, groupId).uniqueResult();
		int count = 0;
		if(o != null){
			count = Integer.valueOf(o.toString()).intValue();
		}
		log.info("当前群组的视频数量：" + count);

		// 2. 更新群组信息
		String update_hql = "UPDATE Group SET videoCount = ? WHERE groupId = ?";
		int update_count = this.getSession().createQuery(update_hql).setInteger(0, count).setInteger(1, groupId).executeUpdate();//getSession().bulkUpdate(update_hql, new Object[] { count, groupId });

		// 如果群组不存在，则 update_count 将发生 = 0 的事情
		if (update_count != 1)
			return -1;
		return count;
	}

	public int updateGroupPhotoCount(int groupId) {
		// 1. 统计现在群组图片数量
		String stat_hql = "SELECT COUNT(*) FROM GroupPhoto gp, Photo ph WHERE gp.photoId = ph.photoId AND gp.groupId = ? AND ph.delState = false";
		Object o =  this.getSession().createQuery(stat_hql).setInteger(0, groupId).uniqueResult();
		int count = 0;
		log.info("当前群组的图片数量：" + count);

		if(o != null){
			count = Integer.valueOf(o.toString()).intValue();
		}
		
		// 2. 更新群组信息
		String update_hql = "UPDATE Group SET photoCount = ? WHERE groupId = ?";
		int update_count = this.getSession().createQuery(update_hql).setInteger(0, count).setInteger(1, groupId).executeUpdate();//getSession().bulkUpdate(update_hql, new Object[] { count, groupId });

		// 如果群组不存在，则 update_count 将发生 = 0 的事情
		if (update_count != 1)
			return -1;
		return count;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#updateGroupResourceCount(int)
	 */
	public int updateGroupResourceCount(int groupId) {
		// 1. 统计现在群组资源数量
		String stat_hql = "SELECT COUNT(*) FROM GroupResource gr, Resource r WHERE gr.resourceId = r.resourceId AND gr.groupId = ? AND r.auditState = 0 AND r.delState = false";
		Object o = this.getSession().createQuery(stat_hql).setInteger(0, groupId).uniqueResult();
		int count = 0;
		if(o != null)
		{
			count = Integer.valueOf(o.toString());
		}
		log.info("当前群组的资源数量：" + count);

		// 2. 更新群组信息
		String update_hql = "UPDATE Group SET resourceCount = ? WHERE groupId = ?";
		int update_count =  this.getSession().createQuery(update_hql).setInteger(0, count).setInteger(1, groupId).executeUpdate(); // getSession().bulkUpdate(update_hql, new Object[] { count, groupId });

		// 如果群组不存在，则 update_count 将发生 = 0 的事情
		if (update_count != 1)
			return -1;
		return count;
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupResourceList(int, cn.edustar.jitar.service.ResourceQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getGroupResourceList(int groupId, ResourceQueryParam param, Pager pager) {
		// 构造查询.
		QueryHelper query = param.createQuery();
		query.selectClause = "SELECT r, gr ";
		query.fromClause = "FROM Resource r, GroupResource gr ";
		query.addAndWhere("r.resourceId = gr.resourceId");
		query.addAndWhere("gr.groupId = :groupId");
		query.setInteger("groupId", groupId);

		// 查询资源列表.
		if (pager == null) {
			// 按照 param 中指定的数量获取.
			return query.queryData(this.getSession(), -1, param.count);
		} else {
			return query.queryDataAndTotalCount(this.getSession(), pager);
		}
	}
	@SuppressWarnings("unchecked")
	public List<Object[]> getGroupPhotoList(int groupId, PhotoQueryParam param, Pager pager){
		// 构造查询.
		QueryHelper query = param.createQuery();
		query.selectClause = "SELECT ph, gp ";
		query.fromClause = "FROM Photo ph, GroupPhoto gp ";
		query.addAndWhere("ph.photoId = gp.photoId");
		query.addAndWhere("gp.groupId = :groupId");
		query.setInteger("groupId", groupId);

		// 查询资源列表.
		if (pager == null) {
			// 按照 param 中指定的数量获取.
			return query.queryData(this.getSession(), -1, param.count);
		} else {
			return query.queryDataAndTotalCount(this.getSession(), pager);
		}
	}
	@SuppressWarnings("unchecked")
	public List<Object[]> getGroupVideoList(int groupId, VideoQueryParam param, Pager pager){
		// 构造查询.
		QueryHelper query = param.createQuery();
		query.selectClause = "SELECT v, gv ";
		query.fromClause = "FROM Video v, GroupVideo gv ";
		query.addAndWhere("v.videoId = gv.videoId");
		query.addAndWhere("gv.groupId = :groupId");
		query.setInteger("groupId", groupId);

		// 查询资源列表.
		if (pager == null) {
			// 按照 param 中指定的数量获取.
			return query.queryData(this.getSession(), -1, param.count);
		} else {
			return query.queryDataAndTotalCount(this.getSession(), pager);
		}		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupResourceDataTable(cn.edustar.jitar.service.GroupResourceQueryParam,
	 *      cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public DataTable getGroupResourceDataTable(GroupResourceQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();
		List list = pager == null ? query.queryData(this.getSession(), -1, 
				param.resourceQueryParam.count) : query.queryDataAndTotalCount(
						this.getSession(), pager);
		DataTable dt = new DataTable(new DataSchema(param.selectFields));
		dt.addList(list);
		return dt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getNewGroupResourceList(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getNewGroupResourceList(int groupId, int count) {
		String hql = "SELECT r, gr" + " FROM Resource r, GroupResource gr "
				+ " WHERE gr.groupId = ? " + "   AND gr.resourceId = r.resourceId "
				+ "   AND r.auditState = " + Resource.AUDIT_STATE_OK + // 审核通过.
				"   AND r.shareMode >= " + Resource.SHARE_MODE_GROUP + // 协作组以上的共享.
				"   AND r.delState = false " + // 非删除.
				" ORDER BY gr.pubDate DESC, gr.id DESC ";
		return this.getSession().createQuery(hql).setInteger(0, groupId).setFirstResult(0).setMaxResults(count).list();//getSession().findTopCount(hql, count, groupId);
	}
	@SuppressWarnings("unchecked")
	public List<Object[]> getNewGroupResourceList(int groupId, int count,boolean includeChildGroup) {
		if(includeChildGroup){
			String hql = "SELECT r, gr" + " FROM Resource r, GroupResource gr "
					+ " WHERE (gr.groupId = ? or gr.groupId In(SELECT groupId FROM Group WHERE parentId=?))" + "   AND gr.resourceId = r.resourceId "
					+ "   AND r.auditState = " + Resource.AUDIT_STATE_OK + // 审核通过.
					"   AND r.shareMode >= " + Resource.SHARE_MODE_GROUP + // 协作组以上的共享.
					"   AND r.delState = false " + // 非删除.
					" ORDER BY gr.pubDate DESC, gr.id DESC ";
			return this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, groupId).setFirstResult(0).setMaxResults(count).list();//getSession().findTopCount(hql, count, groupId,groupId);
		}else{
			return getNewGroupResourceList(groupId, count);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getHotGroupResourceList(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getHotGroupResourceList(int groupId, int count) {
		String hql = "SELECT r, gr" + " FROM Resource r, GroupResource gr "
				+ " WHERE gr.groupId = ? " + "   AND gr.resourceId = r.resourceId "
				+ "   AND r.auditState = " + Resource.AUDIT_STATE_OK + // 审核通过.
				"   AND r.shareMode >= " + Resource.SHARE_MODE_GROUP + // 协作组以上的共享.
				"   AND r.delState = false " + // 非删除.
				" ORDER BY r.viewCount DESC, gr.id DESC "; // 按照点击次数排序.
		return this.getSession().createQuery(hql).setInteger(0, groupId).setFirstResult(0).setMaxResults(count).list(); //getSession().findTopCount(hql, count, groupId);
	}
	@SuppressWarnings("unchecked")
	public List<Object[]> getHotGroupResourceList(int groupId, int count,boolean includeChildGroup) {
		if(includeChildGroup){
			String hql = "SELECT r, gr" + " FROM Resource r, GroupResource gr "
					+ " WHERE (gr.groupId = ? or gr.groupId In(SELECT groupId FROM Group WHERE parentId=?))  " + "   AND gr.resourceId = r.resourceId "
					+ "   AND r.auditState = " + Resource.AUDIT_STATE_OK + // 审核通过.
					"   AND r.shareMode >= " + Resource.SHARE_MODE_GROUP + // 协作组以上的共享.
					"   AND r.delState = false " + // 非删除.
					" ORDER BY r.viewCount DESC, gr.id DESC "; // 按照点击次数排序.
			return this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, groupId).setFirstResult(0).setMaxResults(count).list();//getSession().findTopCount(hql, count, groupId,groupId);
		}else{
			return getHotGroupResourceList(groupId, count) ;
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getBestGroupResourceList(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getBestGroupResourceList(int groupId, int count) {
		String hql = "SELECT r, gr" + " FROM Resource r, GroupResource gr "
				+ " WHERE gr.groupId = ? " + "   AND gr.resourceId = r.resourceId "
				+ "   AND gr.isGroupBest = true " + "   AND r.auditState = "
				+ Resource.AUDIT_STATE_OK + // 审核通过.
				"   AND r.shareMode >= " + Resource.SHARE_MODE_GROUP + // 协作组以上的共享.
				"   AND r.delState = false " +
				" ORDER BY gr.pubDate DESC, gr.id DESC ";
		return this.getSession().createQuery(hql).setInteger(0, groupId).setFirstResult(0).setMaxResults(count).list(); //getSession().findTopCount(hql, count, groupId);
	}
	@SuppressWarnings("unchecked")
	public List<Object[]> getBestGroupResourceList(int groupId, int count,boolean includeChildGroup) {
		if(includeChildGroup){
			String hql = "SELECT r, gr" + " FROM Resource r, GroupResource gr "
					+ " WHERE (gr.groupId = ? or gr.groupId In(SELECT groupId FROM Group WHERE parentId=?)) " + "   AND gr.resourceId = r.resourceId "
					+ "   AND gr.isGroupBest = true " + "   AND r.auditState = "
					+ Resource.AUDIT_STATE_OK + // 审核通过.
					"   AND r.shareMode >= " + Resource.SHARE_MODE_GROUP + // 协作组以上的共享.
					"   AND r.delState = false " +
					" ORDER BY gr.pubDate DESC, gr.id DESC ";
			return this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, groupId).setFirstResult(0).setMaxResults(count).list();
		}else{
			return getBestGroupResourceList(groupId, count);
		}
	}
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#updateGroupResourceBestState(int, boolean)
	 */
	public int updateGroupResourceBestState(int groupResourceId, boolean best) {
		String hql = "UPDATE GroupResource SET isGroupBest = ? WHERE id = ?";
		return this.getSession().createQuery(hql).setBoolean(0, best).setInteger(1, groupResourceId).executeUpdate();
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#updateGroupResourceCategory(cn.edustar.jitar.pojos.GroupResource, java.lang.Integer)
	 */
	public int updateGroupResourceCategory(GroupResource gr, Integer groupCateId) {
		String hql = "UPDATE GroupResource " + " SET groupCateId = ? " + " WHERE id = ? ";
		return this.getSession().createQuery(hql).setParameter(0, groupCateId).setInteger(1,  gr.getId()).executeUpdate();//getSession().bulkUpdate(hql, new Object[] { groupCateId, gr.getId() });
	}
	
	public int updateGroupArticleCategory(GroupArticle ga, Integer groupCateId)
	{
		String hql = "UPDATE GroupArticle " + " SET groupCateId = ? " + " WHERE id = ? ";
		return this.getSession().createQuery(hql).setParameter(0, groupCateId).setInteger(1,  ga.getId()).executeUpdate(); //getSession().bulkUpdate(hql, new Object[] { groupCateId, ga.getId() });
	}
	
	public int updateGroupPhotoCategory(GroupPhoto gp, Integer groupCateId){
		String hql = "UPDATE GroupPhoto " + " SET groupCateId = ? " + " WHERE id = ? ";
		return this.getSession().createQuery(hql).setParameter(0, groupCateId).setInteger(1,  gp.getId()).executeUpdate(); //getSession().bulkUpdate(hql, new Object[] { groupCateId, gp.getId() });
	}
	
	public int updateGroupVideoCategory(GroupVideo gv, Integer groupCateId){
		String hql = "UPDATE GroupVideo " + " SET groupCateId = ? " + " WHERE id = ? ";
		return this.getSession().createQuery(hql).setParameter(0, groupCateId).setInteger(1, gv.getId()).executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupResourceByResource(int)
	 */
	@SuppressWarnings("unchecked")
	public List<GroupResource> getGroupResourceByResource(int resourceId) {
		String hql = "FROM GroupResource " + " WHERE resourceId = " + resourceId;
		return (List<GroupResource>) this.getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	public List<GroupPhoto> getGroupPhotoByPhoto(int photoId){
		String hql = "FROM GroupPhoto " + " WHERE photoId = " + photoId;
		return (List<GroupPhoto>)this.getSession().createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupVideo> getGroupVideoByVideo(int videoId){
		String hql = "FROM GroupVideo " + " WHERE videoId = " + videoId;
		return (List<GroupVideo>)this.getSession().createQuery(hql).list();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#deleteGroupResourceByResource(int)
	 */
	public int deleteGroupResourceByResource(int resourceId) {
		String hql = "DELETE GroupResource " + " WHERE resourceId = " + resourceId;
		return this.getSession().createQuery(hql).executeUpdate();
	}

	public int deleteGroupPhotoByPhoto(int photoId) {
		String hql = "DELETE GroupPhoto " + " WHERE photoId = " + photoId;
		return this.getSession().createQuery(hql).executeUpdate();
	}
	
	public int deleteGroupVideoByVideo(int videoId){
		String hql = "DELETE GroupVideo " + " WHERE videoId = " + videoId;
		return this.getSession().createQuery(hql).executeUpdate();
	}
	
	public void deleteGroupPhoto(GroupPhoto gp){
		this.getSession().delete(gp);
		this.getSession().flush();		
	}
	
	public void deleteGroupVideo(GroupVideo gv){
		this.getSession().delete(gv);
		this.getSession().flush();		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#incGroupResourceCount(int, int)
	 */
	public void incGroupResourceCount(int groupId, int inc_num) {
		if (inc_num == 0)
			return;
		String hql = "UPDATE Group " + " SET resourceCount = resourceCount + (" + inc_num + ") " + " WHERE groupId = " + groupId;
		this.getSession().createQuery(hql).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupNews(int)
	 */
	public GroupNews getGroupNews(int newsId) {
		return (GroupNews) this.getSession().get(GroupNews.class, newsId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#saveOrUpdateGroupNews(cn.edustar.jitar.pojos.GroupNews)
	 */
	public void saveOrUpdateGroupNews(GroupNews gn) {
		this.getSession().saveOrUpdate(gn);
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#incGroupNewsViewCount(cn.edustar.jitar.pojos.GroupNews, int)
	 */
	public void incGroupNewsViewCount(GroupNews gn, int incCount) {
		String hql = "UPDATE GroupNews SET viewCount = viewCount + (" + incCount + ")" + " WHERE newsId = " + gn.getNewsId();
		this.getSession().createQuery(hql).executeUpdate();
		gn.setViewCount(gn.getViewCount() + incCount);
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupNewsDataTable(cn.edustar.jitar.service.GroupNewsQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getGroupNewsDataTable(GroupNewsQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager == null)
			return query.queryData(this.getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(this.getSession(), pager);
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.GroupDao#getGroupPlacardDataTable(cn.edustar.jitar.service.GroupPlacardQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getGroupPlacardDataTable(GroupPlacardQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager == null)
			return query.queryData(this.getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(this.getSession(), pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.GroupDao#updateGroupTopicCount(int)
	 */
	public int updateGroupTopicCount(int groupId) {
		// 1. 统计现在群组主题数量
		String stat_hql = "SELECT COUNT(*) FROM Topic t WHERE t.groupId = ? AND t.isDeleted = false";
		Object o = this.getSession().createQuery(stat_hql).setInteger(0, groupId).uniqueResult();
		int count = 0;
		if(o!= null)
		{
			count = Integer.valueOf(o.toString()).intValue();
		}
		log.info("当前群组的主题数量：" + count);

		// 2. 更新群组信息
		String update_hql = "UPDATE Group SET topicCount = ? WHERE groupId = ?";
		int update_count = this.getSession().createQuery(update_hql).setInteger(0, count).setInteger(1, groupId).executeUpdate(); //getSession().bulkUpdate(update_hql, new Object[] { count, groupId });

		// 如果群组不存在，则 update_count 将发生 = 0 的事情
		if (update_count != 1)
			return -1;
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void UpdateBestGroupArticleAndResource() {
	
			this.getSession().doWork(new Work() {
				public void execute(Connection connection)	throws SQLException {
					CallableStatement cs = connection.prepareCall("{call BestGroupArticleAndResource()}");
					cs.executeUpdate();
				}
			});			
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupArticle> getAllGroupArticleByArticleId(int articleId)
	{
		String queryString = "FROM GroupArticle Where articleId = ?";
		return (List<GroupArticle>)this.getSession().createQuery(queryString).setInteger(0, articleId).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupMember> getUserJoinedAllGroup(int userId)
	{
		String queryString ="FROM GroupMember Where userId=?";
		return (List<GroupMember>)this.getSession().createQuery(queryString).setInteger(0, userId).list();
	}
	
	public void saveOrUpdateGroupDataQuery(GroupDataQuery groupDataQuery)
	{
		this.getSession().saveOrUpdate(groupDataQuery);
	}
	
	public void deleteGroupDataQuery(){
		//SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		//String beforeDate = sf.format(new Date());
		String queryString ="DELETE FROM GroupDataQuery Where DateDiff(day,createDate,getDate()) > 2";
		this.getSession().createQuery(queryString).executeUpdate();
		
	}
	public void deleteGroupDataQueryByGuid(String guid)
	{
		String queryString ="DELETE FROM GroupDataQuery Where objectGuid = ?";
		this.getSession().createQuery(queryString).setString(0, guid).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Group> getAllUserCreatedGroupByUserId(int createUserId)
	{
		String queryString ="FROM Group Where createUserId = ?";
		return (List<Group>)this.getSession().createQuery(queryString).setInteger(0, createUserId).list();
	}
	
	/**-----------------协助组综合分类-----------*/
	public GroupMutil getGroupMutilById(int id){
		String hql = "FROM GroupMutil WHERE id = ?";
		List list = this.getSession().createQuery(hql).setInteger(0, id).list();
		if (list == null || list.size() == 0)
			return null;
		return (GroupMutil) list.get(0);
	}
	public GroupMutil getGroupMutilByWidgetId(int widgetId){
		String hql = "FROM GroupMutil WHERE widgetId = ?";
		List list = this.getSession().createQuery(hql).setInteger(0, widgetId).list();
		if (list == null || list.size() == 0)
			return null;
		return (GroupMutil) list.get(0);		
	}
	public void saveGroupMutil(GroupMutil gm){
		this.getSession().saveOrUpdate(gm);
		this.getSession().flush();
	}
	public void deleteGroupMutilById(GroupMutil gm){
		String hql = "DELETE FROM GroupMutil WHERE id = ?";
		int count = this.getSession().createQuery(hql).setInteger(0, gm.getId()).executeUpdate(); 
	}
	public void deleteGroupMutilById(int id){
		String hql = "DELETE FROM GroupMutil WHERE id = ?";
		int count = this.getSession().createQuery(hql).setInteger(0, id).executeUpdate();
	}
	public void deleteGroupMutilByWidgetId(int widgetId){
		String hql = "DELETE FROM GroupMutil WHERE widgetId = ?";
		int count = this.getSession().createQuery(hql).setInteger(0, widgetId).executeUpdate();
	}

	@Override
	public void evict(Object object) {
		this.getSession().evict(object);		
	}

	@Override
	public void flush() {
		this.getSession().flush();		
	}
	
}
