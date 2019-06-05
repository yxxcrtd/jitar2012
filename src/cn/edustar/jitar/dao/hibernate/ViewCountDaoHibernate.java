package cn.edustar.jitar.dao.hibernate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import cn.edustar.jitar.dao.ViewCountDao;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.ViewCount;

public class ViewCountDaoHibernate extends BaseDaoHibernate implements ViewCountDao {
    /**
     * 根据ID获得记录.
     * 
     * @param Id
     * @return
     */
    @SuppressWarnings("unchecked")
    public ViewCount getViewCount(int id) {
        String hql = "FROM ViewCount WHERE id = :id";
        List<ViewCount> ol = this.getSession().createQuery(hql).setInteger("id", id).list();
        if (ol == null || ol.size() == 0)
            return null;
        return (ViewCount) ol.get(0);

    }

    /**
     * 根据日期获得记录.
     * 
     * @param date
     * @return
     */
    @SuppressWarnings("unchecked")
    public ViewCount getViewCount(int objType, int objId, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int days = cal.get(Calendar.DAY_OF_MONTH);
        // String hql =
        // "FROM ViewCount WHERE year(viewDate) = ? and month(viewDate) = ? and day(viewDate) = ? and objGUID = ? ";
        // return (ViewCount)getSession().findFirst (hql, new Object[] { year,
        // month, days, objGUID});
        String hql = "FROM ViewCount WHERE year(viewDate) = " + year + " and month(viewDate) = " + month + " and day(viewDate) = " + days
                + " and objId = " + objId;
        // System.out.println("******"+hql);
        List<ViewCount> ol = this.getSession().createQuery(hql).list();
        if (ol == null || ol.size() == 0)
            return null;
        return (ViewCount) ol.get(0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.ViewCountDao#getViewCount(int,
     * java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public ViewCount getViewCount(int objType, int objId) {
        String hql = "FROM ViewCount WHERE objType = " + objType + " and objId=" + objId;
        List<ViewCount> ol = this.getSession().createQuery(hql).list();
        if (ol == null || ol.size() == 0)
            return null;
        return (ViewCount) ol.get(0);
    }

    /**
     * 创建对象.
     * 
     * @param viewCount
     */
    public void createViewCount(ViewCount viewCount) {
        this.getSession().save(viewCount);
        this.getSession().flush();
    }

    /**
     * 修改对象.
     * 
     * @param viewCount
     */
    public void updateViewCount(ViewCount viewCount) {
        this.getSession().update(viewCount);
        this.getSession().flush();

    }

    /**
     * 删除,当该删除原始记录的时候,删除对应的记录
     * 
     * 
     * @param viewCount
     */
    public void crashViewCount(ViewCount viewCount) {
        String del_from_viewcount = "DELETE FROM ViewCount WHERE id = :id";
        this.getSession().createQuery(del_from_viewcount).setInteger("id", viewCount.getId()).executeUpdate();
    }

    /**
     * 标记为删除状态
     * 
     * @param objType
     * @param objGUID
     * @param del
     */
    public void changeViewCountdDelStatus(int objType, int objId, int del) {
        String del_from_viewcount = "UPDATE ViewCount set deled=:deled WHERE objType =:objType and objId=:objId";
        this.getSession().createQuery(del_from_viewcount).setInteger("deled", del).setInteger("objType", objType).setInteger("objId", objId)
                .executeUpdate();
    }

    /**
     * 统计数量(返回前多少天的数量)
     * 
     * @param days
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ViewCount> getViewCountList(int objType, int days) {
        String hql = "SELECT objType, objId,sum(viewCount) as objViewCount FROM viewCount WHERE deled=0 and objType = " + objType
                + " and DATEDIFF(day, viewDate, getdate())<=" + days + " GROUP by objType,objId Order by sum(viewCount) desc ";
        List<ViewCount> result = this.getSession().createQuery(hql).list();
        if (result == null || result.size() == 0)
            return null;
        else
            return result;
    }

    /**
     * 统计数量(返回前多少天的数量)
     * 
     */

    @SuppressWarnings("rawtypes")
    public List getViewCountListEx(int objType, int days, int topNum) {
        String hql = "";
        if (topNum > 0) {
            if (objType == 3) {
                hql = "SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate, a.typeState as typeState, u.userId as userId, u.userIcon as userIcon, u.trueName as trueName, u.loginName as loginName,Sum(vc.viewCount) as articleViewCount) FROM Article a ,User u,ViewCount vc Where a.userId = u.userId and a.articleId = vc.objId And vc.objType = "
                        + ObjectType.OBJECT_TYPE_ARTICLE.getTypeId()
                        + " and DateDiff(day,viewDate,getdate())<="
                        + days
                        + " and vc.objType="
                        + objType
                        + " AND a.auditState = "
                        + Article.AUDIT_STATE_OK
                        + // 审核通过.
                        " AND a.draftState = false "
                        + // 非草稿.
                        " AND a.delState = false "
                        + // 非删除.
                        " AND a.hideState = 0 "
                        + // 非隐藏的
                        " GROUP BY a.articleId, a.title, a.createDate, a.typeState,  u.userId, u.userIcon, u.userIcon, u.trueName, u.loginName  Order By Sum(vc.viewCount) DESC";
            } else if (objType == 12) {
                hql = "SELECT new Map(*) ";
                hql = hql + " FROM ResourceViewCount";
                hql = hql + " Where (DATEDIFF(day, viewDate, getdate()) <= " + days;
                hql = hql + " Order By viewCount DESC";

            }
            Query query = this.getSession().createQuery(hql);
            query.setMaxResults(topNum);
            return query.list();
        } else {
            if (objType == 3) {
                hql = "SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate, a.typeState as typeState,  u.userId as userId, u.userIcon as userIcon, u.trueName as trueName, u.loginName as loginName,Sum(vc.viewCount) as articleViewCount) FROM Article a,User u,ViewCount vc Where a.userId = u.userId and a.articleId = vc.objId And vc.objType = "
                        + ObjectType.OBJECT_TYPE_ARTICLE.getTypeId()
                        + " and DateDiff(day,viewDate,getdate())<="
                        + days
                        + " and vc.objType="
                        + objType
                        + " AND a.auditState = "
                        + Article.AUDIT_STATE_OK
                        + // 审核通过.
                        " AND a.draftState = false "
                        + // 非草稿.
                        " AND a.delState = false "
                        + // 非删除.
                        " AND a.hideState = 0 "
                        + // 非隐藏的
                        " GROUP BY a.articleId, a.title, a.createDate, a.typeState,  u.userId, u.userIcon, u.userIcon, u.trueName, u.loginName  Order By Sum(vc.viewCount) DESC";
            } else if (objType == 12) {
                hql = "SELECT new Map(*) ";
                hql = hql + " FROM ResourceViewCount";
                hql = hql + " Where (DATEDIFF(day, viewDate, getdate()) <= " + days;
                hql = hql + " Order By viewCount DESC";
            }
            List result = this.getSession().createQuery(hql).list();
            if (result == null || result.size() == 0)
                return null;
            else
                return result;
        }
    }

    /**
     * 相对增加点击率.
     * 
     * @param viewCount
     *            - 文章的评论对象.
     * @param count
     *            为正表示增加数量, 为 0 不操作.
     */
    public void incViewCount(ViewCount viewCount, int count) {
        String hql = "UPDATE ViewCount SET viewCount = viewCount + :viewCount WHERE id = :id";
        this.getSession().createQuery(hql).setInteger("viewCount", count).setInteger("id", viewCount.getId()).executeUpdate();
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
