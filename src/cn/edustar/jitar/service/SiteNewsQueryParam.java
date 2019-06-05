package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.pojos.SiteNews;

/**
 * 查询站点级新闻的查询参数.
 * 
 * 
 */
public class SiteNewsQueryParam implements QueryParam {
    /** 查询条数, 一般在未指定 pager 的时候使用 */
    public int count = 10;

    /** 限定学科标识, 缺省 = 0 表示获取站点的; = null 表示不限制 */
    public Integer subjectId = 0;

    public String subjectIds = "";

    /** 发布用户标识, 缺省 = null 表示不限制 */
    public Integer userId = null;

    /** 要获取的对象状态, 缺省 = SiteNews.NEWS_STATUS_NORMAL 获取正常状态的; = null 则不限制 */
    public Integer status = SiteNews.NEWS_STATUS_NORMAL;

    /** 要获取的类型, 缺省 = null 表示不限制 */
    public Integer newsType = null;

    /** 是否要求有图片, 缺省 = null 表示不限制 */
    public Boolean hasPicture = null;

    /** 要查询的关键字, 缺省 = null 表示不限制 */
    public String k = null;

    /** 要获取的信息字段, 可能只在部分函数中生效, 且返回的 DataTable 依据此字段设置 Schema */
    public String selectFields = "news.newsId, news.userId, news.title, news.picture, "
            + "news.status, news.newsType, news.createDate, news.viewCount, news.subjectId, " + "u.loginName,u.trueName";

    /** 排序方式, 缺省 = 0 按照创建时间逆序排列 */
    public int orderType = 0;

    /** 排序方式, = 0 按照创建时间逆序排列 */
    public static final int ORDER_TYPE_CREATEDATE_DESC = 0;
    /** 排序方式, = 1 按照点击数逆序排列 */
    public static final int ORDER_TYPE_VIEWCOUNT_DESC = 1;

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.service.QueryParam#createQuery()
     */
    public QueryHelper createQuery() {
        QueryHelper query = new QueryHelper();
        query.selectClause = "SELECT " + selectFields;
        query.fromClause = "FROM SiteNews news, User u ";
        query.addAndWhere("news.userId = u.userId");

        // 限制条件.
        if ((this.subjectIds != null) && (this.subjectIds != "")) {
            String[] a = this.subjectIds.split(",");
            String sSQL = "(";
            for (int i = 0; i < a.length; i++) {
                if (i > 0) {
                    sSQL += " Or ";
                }
                sSQL += "news.subjectId = " + a[i];
            }
            sSQL += ")";
            query.addAndWhere(sSQL);
        }
        if (this.subjectId != null)
            query.addAndWhere("news.subjectId = " + this.subjectId);
        if (this.userId != null)
            query.addAndWhere("news.userId = " + this.userId);
        if (this.status != null)
            query.addAndWhere("news.status = " + this.status);
        if (this.newsType != null)
            query.addAndWhere("news.newsType = " + this.newsType);

        // 图片限制.
        if (this.hasPicture != null) {
            if (this.hasPicture.booleanValue()) {
                // 有图片, 一般是限制有图片.
                query.addAndWhere("news.picture <> '' AND NOT news.picture IS NULL");
            } else {
                // 无图片.
                query.addAndWhere("news.picture = '' OR news.picture IS NULL");
            }
        }

        // 关键字.
        if (this.k != null && this.k.length() > 0) {
            query.addAndWhere("news.title LIKE :titleKey");
            query.setString("titleKey", "%" + this.k + "%");
        }

        // 排序.
        switch (this.orderType) {
            case ORDER_TYPE_CREATEDATE_DESC :
                query.addOrder("news.createDate DESC");
                break;
            case ORDER_TYPE_VIEWCOUNT_DESC :
                query.addOrder("news.viewCount DESC");
                break;
        }

        return query;
    }
}
