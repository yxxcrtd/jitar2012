package cn.edustar.jitar.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jdbc.Work;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.UserDao;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.PrepareCoursePlan;
import cn.edustar.jitar.pojos.StatInfo;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UserSubjectGrade;
import cn.edustar.jitar.pojos.UserType;
import cn.edustar.jitar.service.AdminUserQueryParam;
import cn.edustar.jitar.service.StoreManager;
import cn.edustar.jitar.service.UserQueryParam;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 用户DAO的实现
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 26, 2008 1:57:23 PM
 */
@SuppressWarnings({"unchecked", "rawtypes", "deprecation"})
public class UserDaoHibernate extends BaseDaoHibernate implements UserDao {

    /** 根据用户名查询用户对象 */
    private static final String LOAD_FIND_BY_LOGINNAME = "FROM User U WHERE U.loginName = :loginName";

    /** 根据用户的机构ID查询用户对象 */
    private static final String LOAD_FIND_BY_UNIT = "FROM User U WHERE U.unitId = :unitId";

    /** 用户文件存储服务 */
    private StoreManager sto_mgr;

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.UserDao#getByUserId(int)
     */
    public User getByUserId(int userId) {
        return (User) this.getSession().get(User.class, userId);
    }

    public User getByAccountId(String accountId) {
        List list = this.getSession().createQuery("FROM User WHERE accountId = :accountId").setString("accountId", accountId).list();
        if (list != null && list.size() >= 1) {
            return (User) list.get(0);
        }
        return null;
    }
    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.UserDao#getByLoginName(java.lang.String)
     */
    public User getByLoginName(String loginName) {
        List list = this.getSession().createQuery("FROM User WHERE loginName = :loginName").setString("loginName", loginName).list();
        if (list != null && list.size() >= 1) {
            return (User) list.get(0);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.iface.FriendDao#findByLoginName(java.lang.String)
     */
    public List<User> findByLoginName(String loginName) {
        return this.getSession().createQuery(LOAD_FIND_BY_LOGINNAME).setString("loginName", loginName).list();
        // return this.getSession().find(LOAD_FIND_BY_LOGINNAME, loginName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.UserDao#findByUnitId(int)
     */
    public List<User> findByUnitId(int unitId) {
        return this.getSession().createQuery(LOAD_FIND_BY_UNIT).setInteger("unitId", unitId).list();
        // return this.getSession().find(LOAD_FIND_BY_UNIT, unitId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.UserDao#getUserList()
     */
    public List<User> getUserList() {
        return this.getSession().createQuery("FROM User").list();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.UserDao#getUserList(cn.edustar.jitar.service.
     * UserQueryParam, cn.edustar.data.Pager)
     */
    public List<User> getUserList(UserQueryParam param, Pager pager) {
        QueryHelper query = param.createQuery();
        if (pager == null)
            return query.queryData(this.getSession(), -1, param.count);
        else
            return query.queryDataAndTotalCount(this.getSession(), pager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.UserDao#getUserAdminDataTable(cn.edustar.jitar.service
     * .AdminUserQueryParam, cn.edustar.data.Pager)
     */
    public List<Object[]> getUserAdminDataTable(AdminUserQueryParam param, Pager pager) {
        QueryHelper query = param.createQuery();
        if (pager == null)
            return query.queryData(this.getSession(), -1, param.count);
        else
            return query.queryDataAndTotalCount(this.getSession(), pager);
    }

    public List<User> getUsers(int beginId, int endId) {
        // 查询在 [beginId, endId] 之间的所有用户
        String hql = "FROM User WHERE userId >= " + beginId + " AND userId <= " + endId + " ORDER BY userId ASC";
        return this.getSession().createQuery(hql).list();
    }

    public List<User> getUserByIds(List<Integer> user_ids) {
        if (user_ids == null || user_ids.size() == 0)
            return new ArrayList<User>();
        String hql = "FROM User WHERE userId IN " + CommonUtil.toSqlInString(user_ids) + " ORDER BY userId DESC";
        return this.getSession().createQuery(hql).list();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.UserDao#createUser(cn.edustar.jitar.pojos.User)
     */
    public void createUser(User user) {
        if (user.getCreateDate() == null) {
            user.setCreateDate(new Date());
        }
        if(user.getNickName() == null || user.getNickName().trim().equals("")){
            user.setNickName(user.getTrueName());
        }
        this.getSession().save(user);
        this.getSession().flush();
    }

    public void updateUser(User user) {
        if(user.getNickName() == null || user.getNickName().trim().equals("")){
            user.setNickName(user.getTrueName());
        }
        this.getSession().update(user);
        this.getSession().flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.usermgr.dao.RegisterDao#isDuplicateLoginName(java.lang.String)
     */

    public boolean isDuplicateLoginName(String strLoginName) {
        List list = this.getSession().createQuery("FROM User WHERE loginName = :loginName").setString("loginName", strLoginName).list();
        if (list != null && list.size() >= 1) {
            return true;
        } else
            return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.usermgr.dao.RegisterDao#isDuplicateEmail(java.lang.String)
     */

    public boolean isDuplicateEmail(String strEmail) {
        List list = this.getSession().createQuery("FROM User WHERE email = :email").setString("email", strEmail).list();
        // List list = this.getSession().find(LOAD_IS_DUPLICATE_EMAIL,
        // strEmail);
        if (list != null && list.size() >= 1) {
            return true;
        } else
            return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.UserDao#statForUser(cn.edustar.jitar.pojos.User)
     */
    public Object statForUser(final User user) {
        this.getSession().doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                CallableStatement cs = connection.prepareCall("{call statOneUser(?)}");
                cs.setInt(1, user.getUserId());
                cs.executeUpdate();

            }
        });
        // 统计该用户在协作组中的信息
        List<Integer> group_ids = getJoinedGroupIds(user);
        statUserGroupInfo(user, group_ids);
        return null;
    }

    public void statHistoryArticleForUser(final User user) {
        this.getSession().doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                CallableStatement cs = connection.prepareCall("{call CountYearArticleUser(?)}");
                cs.setInt(1, user.getUserId());
                cs.executeUpdate();
            }
        });
    }

    public Object statAllUser() {
        this.getSession().doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                CallableStatement cs = connection.prepareCall("{call statAllUser()}");
                cs.executeUpdate();
            }
        });

        return null;
    }

    /**
     * 统计指定用户在指定协作组的统计信息
     * 
     * @param user
     * @param group_ids
     */
    private void statUserGroupInfo(User user, List<Integer> group_ids) {
        if (group_ids == null || group_ids.size() == 0)
            return;

        // 初始化统计值, Map<groupId, 'StatInfo for user in groupId'>
        HashMap<Integer, StatInfo> stat = new HashMap<Integer, StatInfo>();
        for (Integer i : group_ids) {
            StatInfo si = new StatInfo();
            si.groupId = i;
            stat.put(i, si);
        }

        // 统计用户在协作组中文章数, 资源数, 主题数, 回复数
        statGroupArticleForUser(user, stat);
        statGroupResourceForUser(user, stat);
        statGroupTopicForUser(user, stat);
        statGroupReplyForUser(user, stat);

        // 更新统计数据
        String hql = "UPDATE GroupMember SET articleCount = ?, resourceCount = ?, topicCount = ?, replyCount = ? WHERE groupId = ? AND userId = ?";
        for (StatInfo si : stat.values()) {
            this.getSession().createQuery(hql).setInteger(0, si.articleCount).setInteger(1, si.resourceCount).setInteger(2, si.topicCount)
                    .setInteger(3, si.replyCount).setInteger(4, si.groupId).setInteger(5, user.getUserId()).executeUpdate();
        }
    }

    /**
     * 统计用户在协作组中发表的文章数
     * 
     * @param user
     * @param stat
     */
    private void statGroupArticleForUser(User user, Map<Integer, StatInfo> stat) {
        // 统计用户发在协作组中的文章数
        String hql = "SELECT ga.groupId, COUNT(*) " + " FROM GroupArticle ga, Article a " + " WHERE ga.articleId = a.articleId AND a.userId = ?"
                + " AND a.auditState = 0 AND a.delState = false AND a.draftState = false " + " GROUP BY ga.groupId ";
        List<Object[]> u_a = this.getSession().createQuery(hql).setInteger(0, user.getUserId()).list();
        for (Object[] t : u_a) {
            Integer groupId = CommonUtil.safeXtransHiberInteger(t[0]);
            Integer count = CommonUtil.safeXtransHiberInteger(t[1]);
            if (groupId != null && count != null) {
                StatInfo s = stat.get(groupId);
                if (s != null)
                    s.articleCount += count.intValue();
            }
        }
    }

    /**
     * 统计用户在协作组中发表的资源数
     * 
     * @param user
     * @param stat
     */
    private void statGroupResourceForUser(User user, Map<Integer, StatInfo> stat) {
        // 统计用户发在协作组中的资源数.
        String hql = "SELECT gr.groupId, COUNT(*) " + " FROM GroupResource gr, Resource r " + " WHERE gr.resourceId = r.resourceId AND r.userId = ?"
                + " AND r.auditState = 0 AND r.delState = false " + " GROUP BY gr.groupId ";
        List<Object[]> u_a = this.getSession().createQuery(hql).setInteger(0, user.getUserId()).list();
        for (Object[] t : u_a) {
            Integer groupId = CommonUtil.safeXtransHiberInteger(t[0]);
            Integer count = CommonUtil.safeXtransHiberInteger(t[1]);
            if (groupId != null && count != null) {
                StatInfo s = stat.get(groupId);
                if (s != null)
                    s.resourceCount += count.intValue();
            }
        }
    }

    /**
     * 统计用户在协作组中发表的主题数
     * 
     * @param user
     * @param stat
     */
    private void statGroupTopicForUser(User user, Map<Integer, StatInfo> stat) {
        String hql = "SELECT tpc.groupId, COUNT(*) FROM Topic tpc WHERE tpc.userId = ? AND tpc.isDeleted = false GROUP BY tpc.groupId ";
        List<Object[]> u_a = this.getSession().createQuery(hql).setInteger(0, user.getUserId()).list();
        for (Object[] t : u_a) {
            Integer groupId = CommonUtil.safeXtransHiberInteger(t[0]);
            Integer count = CommonUtil.safeXtransHiberInteger(t[1]);
            if (groupId != null && count != null) {
                StatInfo s = stat.get(groupId);
                if (s != null)
                    s.topicCount += count.intValue();
            }
        }
    }

    /**
     * 统计用户在协作组中发表的回复数
     * 
     * @param user
     * @param stat
     */
    private void statGroupReplyForUser(User user, Map<Integer, StatInfo> stat) {
        String hql = "SELECT rpy.groupId, COUNT(*) " + " FROM Reply rpy WHERE rpy.userId = ? AND rpy.isDeleted = false GROUP BY rpy.groupId ";
        List<Object[]> u_a = this.getSession().createQuery(hql).setInteger(0, user.getUserId()).list();
        for (Object[] t : u_a) {
            Integer groupId = CommonUtil.safeXtransHiberInteger(t[0]);
            Integer count = CommonUtil.safeXtransHiberInteger(t[1]);
            if (groupId != null && count != null) {
                StatInfo s = stat.get(groupId);
                if (s != null)
                    s.replyCount += count.intValue();
            }
        }
    }

    // 得到指定用户加入的协作组标识集合, 其中协作组为正常状态、成员也为正常状态才获取.
    private List<Integer> getJoinedGroupIds(User user) {
        String hql = "SELECT gm.groupId FROM GroupMember gm, Group g WHERE gm.groupId = g.groupId AND g.groupState = 0  AND gm.status = 0 AND gm.userId = :userId";
        return (List<Integer>) this.getSession().createQuery(hql).setInteger("userId", user.getUserId()).list();
    }

    public void updateUserStatus(User user, int status) {
        // 更新自己的用户表
        int userId = user.getUserId();
        String hql = "UPDATE User SET userStatus =:userStatus WHERE userId =:userId";
        this.getSession().createQuery(hql).setInteger("userStatus", status).setInteger("userId", userId).executeUpdate();
        this.getSession().flush();
    }

    public void deleteUser(User user) {
        if (user == null)
            return;
        try {
            this.deletePrepareCourseOfUser(user.getUserId());
            this.deleteUserOtherThings(user.getUserId());
            sto_mgr.DeleteUserDir(user);
        } catch (Exception e) {

        } finally {

        }
        // 直接删除用户
        this.getSession().delete(user);
    }

    public void deletePrepareCourseOfUser(int userId) {
        String queryString;
        // 先删除创建的
        List<PrepareCoursePlan> plan_list;
        List<PrepareCourse> pc_list;
        queryString = "FROM PrepareCoursePlan Where createUserId = :createUserId";
        plan_list = (List<PrepareCoursePlan>) this.getSession().createQuery(queryString).setInteger("createUserId", userId).list();

        for (int i = 0; i < plan_list.size(); i++) {
            PrepareCoursePlan plan = (PrepareCoursePlan) plan_list.get(i);
            queryString = "FROM PrepareCourse Where prepareCoursePlanId = :prepareCoursePlanId";
            pc_list = (List<PrepareCourse>) this.getSession().createQuery(queryString)
                    .setInteger("prepareCoursePlanId", plan.getPrepareCoursePlanId()).list();

            for (int j = 0; j < pc_list.size(); j++) {
                PrepareCourse pc = (PrepareCourse) pc_list.get(j);
                deleteCourse(pc);
            }

            queryString = "DELETE FROM PrepareCoursePlan Where prepareCoursePlanId = :prepareCoursePlanId";
            this.getSession().createQuery(queryString).setInteger("prepareCoursePlanId", plan.getPrepareCoursePlanId()).executeUpdate();
            // this.getSession().bulkUpdate(queryString,plan.getPrepareCoursePlanId());
        }

        // 删除创建的备课
        queryString = "FROM PrepareCourse Where createUserId = :createUserId";
        pc_list = (List<PrepareCourse>) this.getSession().createQuery(queryString).setInteger("createUserId", userId).list();

        for (int j = 0; j < pc_list.size(); j++) {
            PrepareCourse pc = (PrepareCourse) pc_list.get(j);
            deleteCourse(pc);
        }

        // 更新主备人：
        queryString = "Update PrepareCourse Set leaderId = createUserId Where leaderId = :leaderId";
        this.getSession().createQuery(queryString).setInteger("leaderId", userId).executeUpdate();
        // this.getSession().bulkUpdate(queryString,userId);

        // 删除参与的内容
        deleteCourseByUserId(userId);
    }

    private void deleteCourse(PrepareCourse prepareCourse) {
        String queryString;
        int prepareCourseId = prepareCourse.getPrepareCourseId();

        queryString = "DELETE FROM PrepareCourseTopicReply WHERE prepareCourseId = :prepareCourseId";
        this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, prepareCourseId);

        queryString = "DELETE FROM PrepareCourseTopic WHERE prepareCourseId = :prepareCourseId";
        this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, prepareCourseId);

        queryString = "DELETE FROM PrepareCourseResource WHERE prepareCourseId = :prepareCourseId";
        this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, prepareCourseId);

        queryString = "DELETE FROM PrepareCourseArticle WHERE prepareCourseId = :prepareCourseId";
        this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, prepareCourseId);

        queryString = "DELETE FROM PrepareCoursePrivateComment WHERE prepareCourseId = :prepareCourseId";
        this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, prepareCourseId);

        queryString = "DELETE FROM PrepareCourseEdit WHERE prepareCourseId = :prepareCourseId";
        this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, prepareCourseId);

        queryString = "DELETE FROM PrepareCourseStage WHERE prepareCourseId = :prepareCourseId";
        this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, prepareCourseId);

        queryString = "DELETE FROM PrepareCourseMember WHERE prepareCourseId = :prepareCourseId";
        this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, prepareCourseId);

        queryString = "DELETE FROM PrepareCourse WHERE prepareCourseId = :prepareCourseId";
        this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, prepareCourseId);
    }

    private void deleteCourseByUserId(int userId) {
        String queryString;

        queryString = "DELETE FROM PrepareCourseTopicReply WHERE userId = :userId";
        this.getSession().createQuery(queryString).setInteger("userId", userId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, userId);

        queryString = "DELETE FROM PrepareCourseTopic WHERE userId = :userId";
        this.getSession().createQuery(queryString).setInteger("userId", userId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, userId);

        queryString = "DELETE FROM PrepareCourseResource WHERE userId = :userId";
        this.getSession().createQuery(queryString).setInteger("userId", userId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, userId);

        queryString = "DELETE FROM PrepareCourseArticle WHERE userId = :userId";
        this.getSession().createQuery(queryString).setInteger("userId", userId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, userId);

        queryString = "DELETE FROM PrepareCoursePrivateComment WHERE commentedUserId = :commentedUserId";
        this.getSession().createQuery(queryString).setInteger("commentedUserId", userId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, userId);

        queryString = "DELETE FROM PrepareCoursePrivateComment WHERE commentUserId = :commentUserId";
        this.getSession().createQuery(queryString).setInteger("commentUserId", userId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, userId);

        queryString = "DELETE FROM PrepareCourseEdit WHERE editUserId = :userId";
        this.getSession().createQuery(queryString).setInteger("userId", userId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, userId);

        queryString = "DELETE FROM PrepareCourseMember WHERE userId =:userId";
        this.getSession().createQuery(queryString).setInteger("userId", userId).executeUpdate();
        // this.getSession().bulkUpdate(queryString, userId);
    }

    /**
     * 根据Guid得到用户对象
     */
    public User getUserByGuid(String guid) {
        String hql = "FROM User WHERE userGuid = :userGuid";
        return (User) this.getSession().createQuery(hql).setString("userGuid", guid).uniqueResult();
    }

    /**
     * 删除用户的业务
     * 
     * @param userid
     */
    public void deleteUserOtherThings(final int userid) {
        this.getSession().doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                CallableStatement cs = connection.prepareCall("{call DeleteUser(?)}");
                cs.setInt(1, userid);
                cs.executeUpdate();
            }
        });
    }
    /** 用户文件存储服务的set方法 */
    public void setStoreManager(StoreManager sto_mgr) {
        this.sto_mgr = sto_mgr;
    }

    /**
     * 设置为待推送
     * 
     * @param user
     * @param operateUser
     */
    public void setToPush(User user, User operateUser) {
        String queryString = "UPDATE User Set pushState = 2, pushUserId=:pushUserId Where userId = :userId";
        this.getSession().createQuery(queryString).setInteger("pushUserId", operateUser.getUserId()).setInteger("userId", user.getUserId())
                .executeUpdate();
        // this.getSession().bulkUpdate(queryString, new
        // Object[]{operateUser.getUserId(),user.getUserId()});
    }

    /**
     * 设置为已经推送
     * 
     * @param user
     */
    public void setPushed(User user) {
        String queryString = "UPDATE User Set pushState = 1 Where userId =:userId";
        this.getSession().createQuery(queryString).setInteger("userId", user.getUserId()).executeUpdate();
        // this.getSession().bulkUpdate(queryString, user.getUserId());

    }

    /**
     * 取消推送
     * 
     * @param user
     */
    public void setUnPush(User user) {
        String queryString = "UPDATE User Set pushState = 0, pushUserId = null Where userId = :userId";
        this.getSession().createQuery(queryString).setInteger("userId", user.getUserId()).executeUpdate();
        // this.getSession().bulkUpdate(queryString, user.getUserId());
    }

    public void saveOrUpdateUserSubjectGrade(UserSubjectGrade userSubjectGrade) {
        this.getSession().saveOrUpdate(userSubjectGrade);
    }
    public void deleteUserSubjectGrade(UserSubjectGrade userSubjectGrade) {
        this.getSession().delete(userSubjectGrade);
    }
    public UserSubjectGrade getUserSubjectGradeById(int userSubjectGradeId) {
        return (UserSubjectGrade) this.getSession().get(UserSubjectGrade.class, userSubjectGradeId);
    }

    public List<UserSubjectGrade> getUserSubjectGradeListByUserId(int userId) {
        return this.getSession().createQuery("FROM UserSubjectGrade WHERE userId=:userId").setInteger("userId", userId).list();
    }

    public int getMaxUserId() {
        String queryString = "Select Max(userId) FROM User";
        Object o = this.getSession().createQuery(queryString).iterate().next();
        if (o == null)
            return 0;
        return Integer.valueOf(o.toString()).intValue();
    }

    public int getMinUserId() {
        String queryString = "Select Min(userId) FROM User";
        Object o = this.getSession().createQuery(queryString).iterate().next();
        if (o == null)
            return 0;
        return Integer.valueOf(o.toString()).intValue();
    }

    public void evict(Object object) {
        this.getSession().evict(object);

    }

    public void flush() {
        this.getSession().flush();

    }

    public User getUserByEmail(String email) {
        List list = this.getSession().createQuery("FROM User WHERE email = :email").setString("email", email).list();
        if (list != null && list.size() >= 1) {
            return (User) list.get(0);
        }
        return null;
    }
    public User getUserByNickName(String nickName) {
        List list = this.getSession().createQuery("FROM User WHERE nickName = :nickName").setString("nickName", nickName).list();
        if (list != null && list.size() >= 1) {
            return (User) list.get(0);
        }
        return null;
    }

    public void addVisitCount(int userId) {
        String queryString = "UPDATE User Set visitCount = visitCount + 1 Where userId =:userId";
        this.getSession().createQuery(queryString).setInteger("userId", userId).executeUpdate();
        this.getSession().flush();

        queryString = "UPDATE UserStat Set visitCount = visitCount + 1 Where userId =:userId";
        this.getSession().createQuery(queryString).setInteger("userId", userId).executeUpdate();
        this.getSession().flush();
    }

    /** 用户类型：如名师、教研员、专家等 */
    public void saveOrUpdateUserType(UserType userType) {
        this.getSession().saveOrUpdate(userType);
    }
    public void deleteUserType(UserType userType) {
        this.getSession().delete(userType);
    }
    public List<UserType> getAllUserType() {
        String queryString = "FROM UserType ut ORDER BY ut.id ASC";
        return this.getSession().createQuery(queryString).list();
    }

    /** 更新用户的头衔信息，如教研员、名师等 */
    public void updateUserType(int userId, String userType) {
        String queryString = "UPDATE User SET userType=:userType WHERE userId=:userId";
        this.getSession().createQuery(queryString).setParameter("userType", userType).setInteger("userId", userId).executeUpdate();
        this.getSession().flush();
    }

    public List<Integer> getAllUserIdByUserType(int userType) {
        String queryString = "SELECT userId FROM User WHERE userType LIKE :userType";
        return this.getSession().createQuery(queryString).setString("userType", "%/" + userType + "/%").list();
    }
    
    public int getUserCount(){
       String queryString = "SELECT COUNT(*) FROM User";
       Object o = this.getSession().createQuery(queryString).uniqueResult();
       if(o == null){
           return 0;
       }
       else
       {
           return Integer.valueOf(o.toString()).intValue();
       }
    }
    
    public void updateUserIconTemp(User user){
        String queryString = "UPDATE User SET userIcon=:userIcon WHERE userId=:userId";
        this.getSession().createQuery(queryString).setParameter("userIcon", user.getUserIcon()).setInteger("userId", user.getUserId()).executeUpdate();
        this.getSession().flush();
    }
}
