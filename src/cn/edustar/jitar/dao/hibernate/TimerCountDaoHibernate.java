package cn.edustar.jitar.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.edustar.jitar.dao.TimerCountDao;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.TimerCount;
import cn.edustar.jitar.pojos.BackYear;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.util.CommonUtil;

/**
 * <p>
 * 定时统计的数据实现类。
 * </p>
 * 
 * @author mxh
 * 
 */
public class TimerCountDaoHibernate extends BaseDaoHibernate implements TimerCountDao {

    /**
     * 得到某种类型的统计数据，目前只限全站统计。
     */
    public TimerCount getTimerCountById(int countId) {
        return (TimerCount) this.getSession().get(TimerCount.class, countId);
    }

    /**
     * 更新/保存一个站点统计对象。
     */
    public void saveOrUpdateTimerCount(TimerCount timerCount) {
        this.getSession().saveOrUpdate(timerCount);
    }

    /**
     * 统计历史文章数据。
     * 
     * @return
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public int doHistoryArticleCount() {
        int ret = 0;
        List<BackYear> lb = this.getSession().createQuery("FROM BackYear WHERE backYearType = :backYearType").setString("backYearType", "article")
                .list();
        for (BackYear b : lb) {
            ret += b.getBackYearCount();
        }
        return ret;
    }

    /**
     * 执行站点统计，并返回最新的统计对象。 注意：此统计数据包含未审核、待删除等等所有数据。
     */
    @Override
    public TimerCount doSiteCount() {
        // 计算日期
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        Date todayDate = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date yesterdayDate = calendar.getTime();
        String today = sdf.format(todayDate);
        String yesterday = sdf.format(yesterdayDate);

        // 整站统计
        TimerCount tc = (TimerCount) this.getSession().get(TimerCount.class, TimerCount.COUNT_TYPE_SITE);
        if (null == tc) {
            tc = new TimerCount();
            tc.setCountId(TimerCount.COUNT_TYPE_SITE);
        }
        String hql;
        long userCount;
        long groupCount = 0;
        long totalArticleCount = 0;
        long totalResourceCount = 0;
        long commentCount = 0;
        long photoCount = 0;
        long videoCount = 0;
        long prepareCourseCount = 0L;
        long todayArticleCount = 0;
        long yesterdayArticleCount = 0;
        long todayResourceCount = 0;
        long yesterdayResourceCount;

        // 统计用户数
        hql = "SELECT COUNT(*) FROM User"; // 审核通过的用户 WHERE userStatus =
                                           // :userStatus
        userCount = ((Long) this.getSession().createQuery(hql).iterate().next()).intValue();

        // 统计协作组
        hql = "SELECT COUNT(*) FROM Group"; // 审核通过的 WHERE groupState =
                                            // :groupState
        groupCount = ((Long) this.getSession().createQuery(hql).iterate().next()).intValue();

        // 相册
        hql = "SELECT COUNT(*) FROM Photo"; // 审核通过的 WHERE auditState =
                                            // :auditState
        photoCount = ((Long) this.getSession().createQuery(hql).iterate().next()).intValue();

        // 视频
        hql = "SELECT COUNT(*) FROM Video"; // 审核通过的 WHERE auditState =
                                            // :auditState
        videoCount = ((Long) this.getSession().createQuery(hql).iterate().next()).intValue();

        // 集备
        hql = "SELECT COUNT(*) FROM PrepareCourse";
        prepareCourseCount = ((Long) this.getSession().createQuery(hql).iterate().next()).intValue();

        // 评论
        hql = "SELECT COUNT(*) FROM Comment";
        commentCount = ((Long) this.getSession().createQuery(hql).iterate().next()).intValue();

        // 总文章数
        hql = "SELECT COUNT(*) FROM HtmlArticleBase";
        totalArticleCount = ((Long) this.getSession().createQuery(hql).iterate().next()).intValue();

        // 资源数
        hql = "SELECT COUNT(*) FROM Resource";
        totalResourceCount = ((Long) this.getSession().createQuery(hql).iterate().next()).intValue();

        String startToday = today + " 00:00:00";
        String endToday = today + " 23:59:59";
        String startYesterday = yesterday + " 00:00:00";
        String endYesterday = yesterday + " 23:59:59";

        hql = "SELECT COUNT(*) FROM Article WHERE createDate BETWEEN '" + startToday + "' And '" + endToday + "'";
        todayArticleCount = ((Long) this.getSession().createQuery(hql).iterate().next()).intValue();

        hql = "SELECT COUNT(*) FROM Resource WHERE createDate BETWEEN '" + startToday + "' And '" + endToday + "'";
        todayResourceCount = ((Long) this.getSession().createQuery(hql).iterate().next()).intValue();
        
        if (!today.equals(tc.getCountDate())) {
           
            hql = "SELECT COUNT(*) FROM Article WHERE createDate BETWEEN '" + startYesterday + "' And '" + endYesterday + "'";
            yesterdayArticleCount = ((Long) this.getSession().createQuery(hql).iterate().next()).intValue();

            hql = "SELECT COUNT(*) FROM Resource WHERE createDate BETWEEN '" + startYesterday + "' And '" + endYesterday + "'";
            yesterdayResourceCount = ((Long) this.getSession().createQuery(hql).iterate().next()).intValue();            
            
            tc.setYesterdayArticleCount(Integer.valueOf(String.valueOf(yesterdayArticleCount)));
            tc.setYesterdayResourceCount(Integer.valueOf(String.valueOf(yesterdayResourceCount)));
            tc.setCountDate(today);
            // 每天更新一次历史文章数量，由于数据量极少，执行一次也没有太大的性能问题，可以提高准确性
            // 直接使用基础数据表进行统计，不再需要访问历史表了。
            // tc.setHistoryArticleCount(this.doHistoryArticleCount());
        }

        tc.setCommentCount(Integer.valueOf(String.valueOf(commentCount)));
        tc.setGroupCount(Integer.valueOf(String.valueOf(groupCount)));
        tc.setPhotoCount(Integer.valueOf(String.valueOf(photoCount)));
        tc.setUserCount(Integer.valueOf(String.valueOf(userCount)));
        tc.setVideoCount(Integer.valueOf(String.valueOf(videoCount)));
        tc.setPrepareCourseCount(Integer.valueOf(String.valueOf(prepareCourseCount)));
        tc.setTodayArticleCount(Integer.valueOf(String.valueOf(todayArticleCount)));
        tc.setTodayResourceCount(Integer.valueOf(String.valueOf(todayResourceCount)));
        tc.setTotalArticleCount(Integer.valueOf(String.valueOf(totalArticleCount)));
        tc.setTotalResourceCount(Integer.valueOf(String.valueOf(totalResourceCount)));

        this.getSession().saveOrUpdate(tc);
        this.getSession().flush();
        return tc;
    }

    /**
     * 执行某学科统计。
     */
    public void doSubjectCount(Subject subject) {
        if (null == subject)
            return;
        int metaSubjId = subject.getMetaSubject().getMsubjId();
        //int startGradeId = subject.getMetaGrade().getStartId();
        //int endGradeId = subject.getMetaGrade().getEndId() - 1;
        
        int startGradeId = CommonUtil.convertRoundMinNumber( subject.getMetaGrade().getGradeId());
        int endGradeId = CommonUtil.convertRoundMaxNumber( subject.getMetaGrade().getGradeId()) -1;
        //System.out.println("startGradeId=" + startGradeId + ",endGradeId=" + endGradeId);
        long userCount = 0L;
        long userExCount = 0L;
        long articleCount = 0L;
        long resourceCount = 0L;
        long groupCount = 0L;
        // long photoCount = 0L;
        long videoCount = 0L;
        long prepareCourseCount = 0L;
        long actionCount = 0L;

        String hql;

        hql = "SELECT COUNT(*) FROM User WHERE subjectId = :subjectId And gradeId BETWEEN :startGradeId And :endGradeId";
        userCount = ((Long) this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId).setInteger("startGradeId", startGradeId)
                .setInteger("endGradeId", endGradeId).iterate().next()).intValue();

        hql = "SELECT COUNT(*) FROM UserSubjectGrade WHERE subjectId = :subjectId And gradeId BETWEEN :startGradeId And :endGradeId";
        userExCount = ((Long) this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId).setInteger("startGradeId", startGradeId)
                .setInteger("endGradeId", endGradeId).iterate().next()).intValue();

        userCount += userExCount;

        hql = "SELECT COUNT(*) FROM Article WHERE subjectId = :subjectId And gradeId BETWEEN :startGradeId And :endGradeId";
        articleCount = ((Long) this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId).setInteger("startGradeId", startGradeId)
                .setInteger("endGradeId", endGradeId).iterate().next()).intValue();

        hql = "SELECT COUNT(*) FROM Resource WHERE subjectId = :subjectId And gradeId BETWEEN :startGradeId And :endGradeId";
        resourceCount = ((Long) this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId).setInteger("startGradeId", startGradeId)
                .setInteger("endGradeId", endGradeId).iterate().next()).intValue();

        /*
         * hql =
         * "SELECT COUNT(*) FROM Photo WHERE subjectId = :subjectId And gradeId BETWEEN :startGradeId And :endGradeId"
         * ; photoCount = ((Long) this.getSession().createQuery(hql)
         * .setInteger("subjectId", metaSubjId) .setInteger("startGradeId",
         * startGradeId) .setInteger("endGradeId", endGradeId).iterate().next())
         * .intValue();
         */

        hql = "SELECT COUNT(*) FROM Video WHERE subjectId = :subjectId And gradeId BETWEEN :startGradeId And :endGradeId";
        videoCount = ((Long) this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId).setInteger("startGradeId", startGradeId)
                .setInteger("endGradeId", endGradeId).iterate().next()).intValue();

        hql = "SELECT COUNT(*) FROM Group WHERE subjectId = :subjectId And gradeId BETWEEN :startGradeId And :endGradeId";
        groupCount = ((Long) this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId).setInteger("startGradeId", startGradeId)
                .setInteger("endGradeId", endGradeId).iterate().next()).intValue();

        hql = "SELECT COUNT(*) FROM PrepareCourse WHERE status = 0 And metaSubjectId = :subjectId And gradeId BETWEEN :startGradeId And :endGradeId";
        prepareCourseCount = ((Long) this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId).setInteger("startGradeId", startGradeId)
                .setInteger("endGradeId", endGradeId).iterate().next()).intValue();

        hql = "SELECT COUNT(*) FROM Action WHERE ownerType = 'subject' And ownerId = :ownerId";
        actionCount = ((Long) this.getSession().createQuery(hql).setInteger("ownerId", subject.getSubjectId()).iterate().next()).intValue();

        subject.setArticleCount(Integer.valueOf(String.valueOf(articleCount)));
        subject.setGroupCount(Integer.valueOf(String.valueOf(groupCount)));
        subject.setResourceCount(Integer.valueOf(String.valueOf(resourceCount)));
        subject.setUserCount(Integer.valueOf(String.valueOf(userCount)));
        subject.setActionCount(Integer.valueOf(String.valueOf(actionCount)));
        subject.setPrepareCourseCount(Integer.valueOf(String.valueOf(prepareCourseCount)));
        subject.setVideoCount(Integer.valueOf(String.valueOf(videoCount)));
        // this.getSession().saveOrUpdate(subject);
        //System.out.println("学科统计" + subject.getArticleCount());
        this.getSession().merge(subject);
        //System.out.println("学科统计" + subject.getArticleCount());
    }

    /**
     * 执行单位统计。
     */
    public void doUnitCount(Unit unit) {
        if (null == unit)
            return;
        long userCount = 0L;
        long articleCount = 0L;
        long resourceCount = 0L;
        long photoCount = 0L;
        long videoCount = 0L;

        String hql;

        hql = "SELECT COUNT(*) FROM User WHERE unitId = :unitId";
        userCount = ((Long) this.getSession().createQuery(hql).setInteger("unitId", unit.getUnitId()).iterate().next()).intValue();

        hql = "SELECT COUNT(*) FROM Article WHERE unitId = :unitId";
        articleCount = ((Long) this.getSession().createQuery(hql).setInteger("unitId", unit.getUnitId()).iterate().next()).intValue();

        hql = "SELECT COUNT(*) FROM Resource WHERE unitId = :unitId";
        resourceCount = ((Long) this.getSession().createQuery(hql).setInteger("unitId", unit.getUnitId()).iterate().next()).intValue();

        hql = "SELECT COUNT(*) FROM Photo WHERE unitId = :unitId";
        photoCount = ((Long) this.getSession().createQuery(hql).setInteger("unitId", unit.getUnitId()).iterate().next()).intValue();

        hql = "SELECT COUNT(*) FROM Video WHERE unitId = :unitId";
        videoCount = ((Long) this.getSession().createQuery(hql).setInteger("unitId", unit.getUnitId()).iterate().next()).intValue();

        unit.setArticleCount(Integer.valueOf(String.valueOf(articleCount)));
        unit.setPhotoCount(Integer.valueOf(String.valueOf(photoCount)));
        unit.setResourceCount(Integer.valueOf(String.valueOf(resourceCount)));
        unit.setUserCount(Integer.valueOf(String.valueOf(userCount)));
        unit.setVideoCount(Integer.valueOf(String.valueOf(videoCount)));
        this.getSession().saveOrUpdate(unit);
        this.getSession().flush();
    }

    @Override
    public void evict(Object object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

}
