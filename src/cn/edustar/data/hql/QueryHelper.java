package cn.edustar.data.hql;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

import cn.edustar.data.Pager;

/**
 * Hibernate + Spring HQL 查询辅助工具
 */
@SuppressWarnings("rawtypes")
public class QueryHelper extends HqlHelperBase {
    /** SELECT 子句. */
    public String selectClause = "";

    /** FROM 子句. */
    public String fromClause = "";

    /** WHERE 子句. */
    public String whereClause = "";

    /** ORDER BY 子句. */
    public String orderClause = "";

    /** GROUP BY 子句. */
    public String groupbyClause = "";

    /** HAVING 子句 */
    public String havingClause = "";

    /**
     * 添加一个 whereClause 的与(and)条件.
     * 
     * @param condition
     */
    public QueryHelper addAndWhere(String condition) {
        if (condition.length() > 0) {
            if (this.whereClause.length() == 0)
                this.whereClause = " WHERE (" + condition + ")";
            else
                this.whereClause += " AND (" + condition + ")";
        }
        return this;
    }

    /**
     * 添加一个 orderClause 的排序方式.
     * 
     * @param order
     *            - 排序的子条件，如 'id ASC'.
     */
    public QueryHelper addOrder(String order) {
        if (this.orderClause.length() == 0)
            this.orderClause = " ORDER BY " + order;
        else
            this.orderClause += ", " + order;
        return this;
    }

    /**
     * 查询项目总数. 查询语句为 "SELECT COUNT(*) " + fromClause + whereClause.
     * 
     * @return - 返回项目总数.
     */
    public int queryTotalCount(final Session session) {
        final String hql = "SELECT COUNT(*) " + this.fromClause + " " + this.whereClause;
        Object result = session.doReturningWork(new ReturningWork<Object>() {
            public Object execute(Connection connection) throws SQLException {
                Query query = session.createQuery(hql);
                query.setReadOnly(true);
                initQuery(query);
                return query.uniqueResult();
            }
        });

        return safeGetIntResult(result);
    }

    /**
     * 查询项目总数并直接将结果放到 pager 中.
     * 
     * @param hiber
     * @param pager
     */
    public void queryTotalCount(Session session, Pager pager) {
        int tc = queryTotalCount(session);
        pager.setTotalRows(tc);
    }

    /**
     * 查询项目列表.
     */
    public List queryData(Session session) {
        return queryData(session, -1, -1);
    }

    /**
     * 执行当前查询，并返回第一条记录.
     * 
     * @param hiber
     * @return
     */
    public Object querySingleData(Session session) {
        List list = queryData(session, 0, 1);
        if (list == null || list.size() == 0)
            return null;
        return list.get(0);
    }

    /**
     * 执行当前查询，并返回一个整数值，如果为 null 则返回 0.
     * 
     * @param hiber
     * @return
     */
    public int queryIntValue(Session session) {
        List list = queryData(session, 0, 1);
        if (list == null || list.size() == 0)
            return 0;
        Object v = list.get(0);
        if (v == null)
            return 0;
        if (v instanceof Number){
            try{
                Integer count = Integer.valueOf(v.toString());
                return count.intValue();
            }catch(Exception ex){
                return 0;
            }            
        }
        return 0;
    }

    /**
     * 查询项目列表. 查询语句为 selectClause + fromClause + whereClause + orderClause.
     * 
     * @param dao
     * @param first_result
     *            - Set the first row to retrieve. -1 means not set, rows will
     *            be retrieved beginnning from row 0.
     * @param max_results
     *            - Set the maximum number of rows to retrieve. -1 means not
     *            set, there is no limit to the number of rows retrieved.
     */
    public List queryData(Session session, final int first_result, final int max_results) {
        final String hql = this.selectClause + " " + this.fromClause + " " + this.whereClause + " " + this.orderClause + " " + this.groupbyClause
                + " " + this.havingClause;
        Query query = session.createQuery(hql);
        initQuery(query);
        if (first_result > -1)
            query.setFirstResult(first_result);
        if (max_results > -1)
            query.setMaxResults(max_results);
        try {
            return query.list();
        } catch (Exception ex) {
            return null;
        }
    }

    /** 查询项目列表. */
    public List queryData(Session session, final Pager page_info) {
        return queryData(session, (page_info.getCurrentPage() - 1) * page_info.getPageSize(), page_info.getPageSize());
    }

    /**
     * 查询数量及数据.
     * 
     * @return
     */
    public List queryDataAndTotalCount(Session session, Pager pager) {
        int tc = this.queryTotalCount(session);
        if (tc < 0)
            tc = 0;
        pager.setTotalRows(tc);
        if (pager.getCurrentPage() < 1)
            pager.setCurrentPage(1);
        // if(pager.getTotalRows() < pager.getStartRow())
        // pager.setTotalRows(pager.getTotalRows());
        if (pager.getCurrentPage() > pager.getTotalPages())
            pager.setCurrentPage(pager.getTotalPages());

        return this.queryData(session, pager);
    }

    private static final int safeGetIntResult(Object v) {
        if (v == null)
            return 0;
        if (v instanceof Integer)
            return (Integer) v;
        if (v instanceof Number)
            return ((Number) v).intValue();
        return 0;
    }
}
