package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.SubjectDao;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.SiteNews;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.SubjectWebpart;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UserSubjectGrade;
import cn.edustar.jitar.service.SiteNewsQueryParam;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 学科数据库访问实现.
 * 
 * 
 */
@Transactional
public class SubjectDaoHibernate extends BaseDaoHibernate implements SubjectDao {
    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.SubjectDao#getMetaSubjectList()
     */
    @SuppressWarnings("unchecked")
    public List<MetaSubject> getMetaSubjectList() {
        return (List<MetaSubject>) this.getSession().createQuery("FROM MetaSubject ORDER BY orderNum, msubjId").list();
    }

    public MetaSubject getMetaSubjectByName(String msubjName) {
        // TODO Auto-generated method stub
        String hql = "FROM MetaSubject WHERE msubjName = ? ";
        return (MetaSubject) this.getSession().createQuery(hql).setString(0, msubjName).uniqueResult();
    }

    public MetaSubject getMetaSubjectByCode(String msubjCode) {
        String hql = "FROM MetaSubject WHERE msubjCode = ? ";
        return (MetaSubject) this.getSession().createQuery(hql).setString(0, msubjCode).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Grade> getMetaGradeListByMetaSubjectId(int metaSubjectId) {
        String queryString = "Select s.metaGrade FROM Subject s LEFT JOIN s.metaGrade g LEFT JOIN s.metaSubject ms WHERE ms.msubjId = ? ORDER BY g.gradeId DESC";
        return (List<Grade>) this.getSession().createQuery(queryString).setInteger(0, metaSubjectId).list();
    }

    // 对5200等圆整到6000
    private int getMaxGradeId(int gradeId) {
        String strGradeId = String.valueOf(gradeId);
        int dataLength = strGradeId.length();
        String firstData = strGradeId.substring(0, 1);
        firstData = String.valueOf(Integer.valueOf(firstData) + 1);
        for (int i = 0; i < dataLength - 1; i++) {
            firstData = firstData + "0";
        }
        return Integer.valueOf(firstData);
    }

    // 把5200圆整为5000
    private int getMinGradeId(int gradeId) {
        String strGradeId = String.valueOf(gradeId);
        int dataLength = strGradeId.length();
        String firstData = strGradeId.substring(0, 1);
        for (int i = 0; i < dataLength - 1; i++) {
            firstData = firstData + "0";
        }
        return Integer.valueOf(firstData);
    }

    // 得到某学段下的所有年级
    @SuppressWarnings("unchecked")
    public List<Grade> getGradeLevelListByGradeId(int gradeId) {
        String queryString = " FROM Grade Where gradeId < ? And gradeId >? Order By gradeId ASC";
        // System.out.println("测试：" + queryString);
        List<Grade> grade = (List<Grade>) this.getSession().createQuery(queryString).setInteger(0, getMaxGradeId(gradeId))
                .setInteger(1, getMinGradeId(gradeId)).list();
        return grade;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.SubjectDao#getAllSubject()
     */
    @SuppressWarnings("unchecked")
    public List<Subject> getAllSubject() {
        return (List<Subject>) getSession().createQuery("FROM Subject subj ORDER BY subj.orderNum, subj.subjectId").list();
    }

    @SuppressWarnings("unchecked")
    public List<Subject> getSubjectList(String searchSubjectName) {
        return (List<Subject>) getSession().createQuery(
                "FROM Subject subj WHERE subj.subjectName LIKE '%" + searchSubjectName.replace("'", "''")
                        + "%' ORDER BY subj.orderNum, subj.subjectId").list();
    }
    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.SubjectDao#saveOrUpdateSubject(cn.edustar.jitar.
     * pojos.Subject)
     */
    public void saveOrUpdateSubject(Subject subject) {
        this.getSession().saveOrUpdate(subject);
        this.getSession().merge(subject);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.SubjectDao#saveOrUpdateSubject(cn.edustar.jitar.
     * pojos.MetaSubject)
     */
    public void saveOrUpdateSubject(MetaSubject msubject) {
        this.getSession().saveOrUpdate(msubject);
        this.getSession().merge(msubject);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.SubjectDao#saveOrUpdateGrade(cn.edustar.jitar.pojos
     * .Grade)
     */
    public void saveOrUpdateGrade(Grade grade) {
        this.getSession().saveOrUpdate(grade);
        this.getSession().merge(grade);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.SubjectDao#updateGrade(cn.edustar.jitar.pojos.Grade,
     * int)
     */
    public void updateGrade(Grade grade, int oldGradeId) {
        String hql = " UPDATE Grade set gradeId=" + grade.getGradeId() + ", gradeName='" + grade.getGradeName() + "' ,isGrade=" + grade.getIsGrade()
                + " WHERE gradeId=" + oldGradeId;
        this.getSession().createQuery(hql).executeUpdate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.SubjectDao#deleteSubject(cn.edustar.jitar.pojos.
     * Subject)
     */
    public void deleteSubject(Subject subject) {
        getSession().delete(subject);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.SubjectDao#deleteMetaSubject(cn.edustar.jitar.pojos
     * .MetaSubject)
     */
    public void deleteMetaSubject(MetaSubject msubject) {
        this.getSession().delete(msubject);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.SubjectDao#deleteGrade(cn.edustar.jitar.pojos.Grade)
     */
    public void deleteGrade(Grade grade) {
        this.getSession().delete(grade);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.SubjectDao#getMetaSubjectMaxOrderNum()
     */
    @SuppressWarnings("unchecked")
    public int getMetaSubjectMaxOrderNum() {
        String hql = " SELECT orderNum FROM MetaSubject ORDER BY orderNum DESC";
        List list = this.getSession().createQuery(hql).list();
        if (list == null || list.size() == 0)
            return 0;
        int orderNum = Integer.parseInt(list.get(0).toString());
        return orderNum;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.SubjectDao#getSubjectMaxOrderNum()
     */
    @SuppressWarnings("unchecked")
    public int getSubjectMaxOrderNum() {
        String hql = " SELECT orderNum FROM Subject ORDER BY orderNum DESC";
        List list = this.getSession().createQuery(hql).list();
        if (list == null || list.size() == 0)
            return 0;
        int orderNum = Integer.parseInt(list.get(0).toString());
        return orderNum;
    }
    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.SubjectDao#getGrade(int)
     */
    public Grade getGrade(int gradeId) {
        return (Grade) getSession().get(Grade.class, gradeId);
    }
    public Grade getGradeLevel(int gradeId) {
        String queryString = " FROM Grade Where isGrade = 1 And gradeId <= ? Order By gradeId DESC";
        List<Grade> grades = (List<Grade>) this.getSession().createQuery(queryString).setInteger(0, gradeId).list();
        if (grades == null || grades.size() == 0)
            return null;
        else
            return (Grade) grades.get(0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.SubjectDao#getGradeList()
     */
    @SuppressWarnings("unchecked")
    public List<Grade> getGradeList() {
        return getSession().createQuery("FROM Grade").list();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.SubjectDao#getSiteNews(int)
     */
    public SiteNews getSiteNews(int newsId) {
        return (SiteNews) getSession().get(SiteNews.class, newsId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.SubjectDao#saveOrUpateSiteNews(cn.edustar.jitar.
     * pojos.SiteNews)
     */
    public void saveOrUpateSiteNews(SiteNews news) {
        getSession().saveOrUpdate(news);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.SubjectDao#deleteSiteNews(cn.edustar.jitar.pojos
     * .SiteNews)
     */
    public void deleteSiteNews(SiteNews news) {
        this.getSession().delete(news);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.SubjectDao#updateSiteNewsStatus(cn.edustar.jitar
     * .pojos.SiteNews, int)
     */
    public void updateSiteNewsStatus(SiteNews news, int status) {
        String hql = "UPDATE SiteNews SET status = " + status + " WHERE newsId = " + news.getNewsId();
        this.getSession().createQuery(hql).executeUpdate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.SubjectDao#incSiteNewsViewCount(int, int)
     */
    public void incSiteNewsViewCount(int newsId, int incCount) {
        String hql = "UPDATE SiteNews SET viewCount = viewCount + (" + incCount + ") WHERE newsId = " + newsId;
        this.getSession().createQuery(hql).executeUpdate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.SubjectDao#getSiteNewsDataTable(cn.edustar.jitar
     * .service.SiteNewsQueryParam, cn.edustar.data.Pager)
     */
    @SuppressWarnings("unchecked")
    public DataTable getSiteNewsDataTable(SiteNewsQueryParam param, Pager pager) {
        QueryHelper query = param.createQuery();
        List result;
        if (pager == null) {
            result = query.queryData(this.getSession(), -1, param.count);
        } else {
            result = query.queryDataAndTotalCount(this.getSession(), pager);
        }
        DataTable dt = new DataTable(new DataSchema(param.selectFields));
        dt.addList(result);
        return dt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.SubjectDao#subjectAutoStat()
     */
    public void subjectAutoStat() {
        // statSubjectUserCount();
        // statSubjectGroupCount();
        // statSubjectArticleCount();
        // statSubjectResourceCount();
    }

    /**
     * 重新统计每个学科的用户数.
     */
    @SuppressWarnings({"unchecked", "unused"})
    private void statSubjectUserCount() {
        try {
            String hql = "SELECT subjectId, COUNT(u.userId) " + "FROM User u " + "WHERE (NOT subjectId IS NULL) " + "GROUP BY subjectId";
            List<Object[]> stat = this.getSession().createQuery(hql).list();
            // logger.info("userCount for subject: " + stat.size());
            for (Object[] array : stat) {
                Integer subjectId = ParamUtil.safeParseIntegerWithNull(array[0]);
                Integer count = ParamUtil.safeParseIntegerWithNull(array[1]);
                if (subjectId != null && count != null) {
                    String update_hql = "UPDATE Subject SET userCount = ? WHERE subjectId = ?";
                    this.getSession().createQuery(update_hql).setInteger(0, count).setInteger(1, subjectId);
                    // getSession().bulkUpdate(update_hql, new Object[] { count,
                    // subjectId });
                }
            }
        } catch (DataAccessException ex) {
            log.error("statSubjectUserCount() failed", ex);
        }
    }

    /**
     * 重新统计每个学科的协作组数.
     */
    @SuppressWarnings({"unchecked", "unused"})
    private void statSubjectGroupCount() {
        try {
            String hql = "SELECT subjectId, COUNT(g.groupId) " + "FROM Group g " + "WHERE (NOT subjectId IS NULL) " + "GROUP BY subjectId";
            List<Object[]> stat = this.getSession().createQuery(hql).list();
            // logger.info("groupCount for subject: " + stat.size());
            for (Object[] array : stat) {
                Integer subjectId = ParamUtil.safeParseIntegerWithNull(array[0]);
                Integer count = ParamUtil.safeParseIntegerWithNull(array[1]);
                if (subjectId != null && count != null) {
                    String update_hql = "UPDATE Subject SET groupCount = ? WHERE subjectId = ?";
                    this.getSession().createQuery(update_hql).setInteger(0, count).setInteger(1, subjectId).executeUpdate();
                }
            }
        } catch (DataAccessException ex) {
            log.error("statSubjectGroupCount() failed", ex);
        }
    }

    /**
     * 重新统计每个学科的文章数.
     */
    @SuppressWarnings({"unchecked", "unused"})
    private void statSubjectArticleCount() {
        try {
            String hql = "SELECT subjectId, COUNT(a.articleId) " + "FROM Article a "
                    + "WHERE a.auditState = 0 AND a.delState = false AND a.draftState = false " + "GROUP BY subjectId";
            List<Object[]> stat = this.getSession().createQuery(hql).list();
            // logger.info("articleCount for subject: " + stat.size());
            for (Object[] array : stat) {
                Integer subjectId = ParamUtil.safeParseIntegerWithNull(array[0]);
                Integer count = ParamUtil.safeParseIntegerWithNull(array[1]);
                if (subjectId != null && count != null) {
                    String update_hql = "UPDATE Subject SET articleCount = ? WHERE subjectId = ?";
                    this.getSession().createQuery(update_hql).setInteger(0, count).setInteger(1, subjectId);
                    // getSession().bulkUpdate(update_hql, new Object[] { count,
                    // subjectId });
                }
            }
        } catch (DataAccessException ex) {
            log.error("statSubjectArticleCount() failed", ex);
        }
    }

    /**
     * 重新统计每个学科的资源数.
     */
    @SuppressWarnings({"unchecked", "unused"})
    private void statSubjectResourceCount() {
        try {
            String hql = "SELECT subjectId, COUNT(r.resourceId) " + "FROM Resource r " + "WHERE r.auditState = 0 AND r.delState = false "
                    + "GROUP BY subjectId";
            List<Object[]> stat = this.getSession().createQuery(hql).list();
            // logger.info("resourceCount for subject, subj num: " +
            // stat.size());
            for (Object[] array : stat) {
                Integer subjectId = ParamUtil.safeParseIntegerWithNull(array[0]);
                Integer count = ParamUtil.safeParseIntegerWithNull(array[1]);
                if (subjectId != null && count != null) {
                    String update_hql = "UPDATE Subject SET resourceCount = ? WHERE subjectId = ?";
                    this.getSession().createQuery(update_hql).setInteger(0, count).setInteger(1, subjectId).executeUpdate();
                    // getSession().bulkUpdate(update_hql, new Object[] { count,
                    // subjectId });
                }
            }
        } catch (DataAccessException ex) {
            log.error("statSubjectResourceCount() failed", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Grade> getGradeListOnlyIsGrade() {
        String queryString = "FROM Grade Where isGrade = true Order By gradeId DESC";
        return (List<Grade>) this.getSession().createQuery(queryString).list();
    }

    /**
     * 得到学科的所有内容块
     * 
     * @param subjectId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SubjectWebpart> getSubjectWebpartList(int subjectId, Boolean isShow) {
        String queryString;
        if (isShow != null)
            if (isShow) {
                queryString = "FROM SubjectWebpart Where visible = 1 And subjectId = ? Order By webpartZone ASC,rowIndex ASC";
            } else {
                queryString = "FROM SubjectWebpart Where visible = 0 And subjectId = ? Order By webpartZone ASC,rowIndex ASC";
            }
        else {
            queryString = "FROM SubjectWebpart Where subjectId = ? Order By webpartZone ASC,rowIndex ASC";
        }

        return (List<SubjectWebpart>) this.getSession().createQuery(queryString).setInteger(0, subjectId).list();
    }

    /**
     * 更新学科内容块
     * 
     * @param subjectWebpart
     */
    public void saveOrUpdateSubjectWebpart(SubjectWebpart subjectWebpart) {
        this.getSession().saveOrUpdate(subjectWebpart);
    }

    /**
     * 得到学科的全部自定义内容块列表
     * 
     * @param subjectId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SubjectWebpart> getWebpartList(int subjectId, Boolean isSystemModule) {
        String queryString;
        if (isSystemModule == null) {
            queryString = "FROM SubjectWebpart Where subjectId=? Order By subjectWebpartId ASC";
            return (List<SubjectWebpart>) this.getSession().createQuery(queryString).setInteger(0, subjectId).list();
        } else if (isSystemModule == true) {
            queryString = "FROM SubjectWebpart Where subjectId=? And systemModule = 1 Order By subjectWebpartId ASC";
            return (List<SubjectWebpart>) this.getSession().createQuery(queryString).setInteger(0, subjectId).list();
        } else {
            queryString = "FROM SubjectWebpart Where subjectId=? And systemModule = 0 Order By subjectWebpartId ASC";
            return (List<SubjectWebpart>) this.getSession().createQuery(queryString).setInteger(0, subjectId).list();
        }

    }
    /**
     * 加载一个内容块
     * 
     * @param subjectWebpartId
     * @return
     */
    public SubjectWebpart getSubjectWebpartById(int subjectWebpartId) {
        return (SubjectWebpart) this.getSession().get(SubjectWebpart.class, subjectWebpartId);
    }

    /**
     * 删除一个内容块
     * 
     * @param subjectWebpart
     */
    public void deleteSubjectWebpart(SubjectWebpart subjectWebpart) {
        this.getSession().delete(subjectWebpart);
    }
    /**
     * 移动内容块的位置
     * 
     * @param subjectWebpart
     * @param columnIndex
     * @param widgetBeforeId
     * @return
     */
    @SuppressWarnings("unchecked")
    public int setSubjectWebpartPosition(SubjectWebpart subjectWebpart, int columnIndex, int widgetBeforeId) {
        String find_hql = "FROM SubjectWebpart WHERE subjectId = ? AND webpartZone = ? ORDER BY rowIndex ASC, subjectWebpartId ASC";
        List<SubjectWebpart> subjectWebpart_list = this.getSession().createQuery(find_hql).setInteger(0, subjectWebpart.getSubjectId())
                .setInteger(1, columnIndex).list();

        // List<SubjectWebpart> subjectWebpart_list =
        // getSession().find(find_hql, new
        // Object[]{subjectWebpart.getSubjectId(), columnIndex});

        int this_row_index = 1;
        int update_count = 0;
        for (SubjectWebpart witer : subjectWebpart_list) {
            // 如果找到了插入位置, 则插入 widget 到这个 this_row_index.
            if (witer.getSubjectWebpartId() == widgetBeforeId) {
                String ins_hql = "UPDATE SubjectWebpart SET webpartZone = ?, rowIndex = ? WHERE subjectWebpartId = ?";
                update_count = this.getSession().createQuery(ins_hql).setInteger(0, columnIndex).setInteger(1, this_row_index)
                        .setInteger(2, subjectWebpart.getSubjectWebpartId()).executeUpdate();
                // update_count = getSession().bulkUpdate(ins_hql, new
                // Object[]{columnIndex, this_row_index,
                // subjectWebpart.getSubjectWebpartId()});
                ++this_row_index;
            } else if (witer.getSubjectWebpartId() == subjectWebpart.getSubjectWebpartId())
                // 碰到自己忽略.
                continue;

            // 如果应该的位置 和 witer 的位置不符合, 则现在更新它.
            if (this_row_index != witer.getRowIndex()) {
                String move_hql = "UPDATE SubjectWebpart SET rowIndex = ? WHERE subjectWebpartId = ?";
                this.getSession().createQuery(move_hql).setInteger(0, this_row_index).setInteger(1, witer.getSubjectWebpartId()).executeUpdate();
                // getSession().bulkUpdate(move_hql, new
                // Object[]{this_row_index, witer.getSubjectWebpartId()});
            }
            ++this_row_index;
        }

        // 可能没找到插入位置, 则插入到末尾.
        if (update_count == 0) {
            String append_hql = "UPDATE SubjectWebpart SET webpartZone = ?, rowIndex = ? WHERE subjectWebpartId = ?";

            update_count = this.getSession().createQuery(append_hql).setInteger(0, columnIndex).setInteger(1, this_row_index)
                    .setInteger(2, subjectWebpart.getSubjectWebpartId()).executeUpdate();
            // update_count = getSession().bulkUpdate(append_hql, new
            // Object[]{columnIndex, this_row_index,
            // subjectWebpart.getSubjectWebpartId()});
        }
        return update_count;

    }

    /**
     * 根据GUID加载对象
     * 
     * @param objectGuid
     * @return
     */
    public Subject getSubjectByGuid(String objectGuid) {
        return (Subject) this.getSession().createQuery("FROM Subject Where subjectGuid = ?").setString(0, objectGuid).uniqueResult();
    }

    /**
     * 检查某用户是否属于某学科学段
     * 
     * @param userId
     * @param subjectId
     * @return
     */
    public boolean checkUserInSubject(User user, int subjectId) {
        if (user == null) {
            return false;
        }

        Subject subject = (Subject) this.getSession().get(Subject.class, subjectId);
        if (subject == null) {
            return false;
        }

        if (user.getSubjectId() != null && user.getGradeId() != null && user.getSubjectId() == subject.getMetaSubject().getMsubjId()
                && user.getGradeId() == subject.getMetaGrade().getGradeId()) {
            return true;
        }

        String queryString = "FROM UserSubjectGrade WHERE userId=?";
        List<UserSubjectGrade> lusg = this.getSession().createQuery(queryString).setInteger(0, user.getUserId()).list();
        if (lusg == null || lusg.size() == 0) {
            return false;
        }
        for (UserSubjectGrade usg : lusg) {
            if (usg.getGradeId() != null && usg.getSubjectId() != null && usg.getSubjectId() == subject.getMetaSubject().getMsubjId()
                    && usg.getGradeId() == subject.getMetaGrade().getGradeId()) {
                return true;
            }
        }
        return false;
    }

    /** 更新相关的显示问题 */
    public void updateAccessControlSubjectTitle(Subject subject) {
        if (subject == null)
            return;
        String queryString = "UPDATE AccessControl Set objectTitle = ? Where objectId = ? And (objectType="
                + AccessControl.OBJECTTYPE_SUBJECTCONTENTADMIN + " Or objectType=" + AccessControl.OBJECTTYPE_SUBJECTSYSTEMADMIN + " Or objectType="
                + AccessControl.OBJECTTYPE_SYSTEMUSERADMIN + ")";
        this.getSession().createQuery(queryString).setString(0, subject.getSubjectName()).setInteger(1, subject.getSubjectId());

        // super.getSession().bulkUpdate(queryString,new
        // Object[]{subject.getSubjectName(), subject.getSubjectId()});
    }

}
