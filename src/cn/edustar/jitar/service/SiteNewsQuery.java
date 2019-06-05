package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 教研动态、新闻查询。
 * 
 * @author mxh
 * 
 */
public class SiteNewsQuery extends BaseQuery {

    /**
     * 新闻标识, 默认为空, 表示不限制.
     */
    public Integer newsId = null;

    /**
     * 查询条数, 一般在未指定 pager 的时候使用.
     */
    public int count = 10;

    /**
     * 限定学科标识, 缺省 = 0 表示获取站点的; = null 表示不限制.
     */
    public Integer subjectId = null;

    /**
     * 发布用户标识, 缺省 = null 表示不限制.
     */
    public Integer userId = null;

    /**
     * 要获取的对象状态, 缺省 = 0, 表示或缺正常状态的; = null 表示不限制.
     */
    public Integer status = 0;

    /**
     * 要获取的类型, 缺省 = null 表示不限制.
     */
    public Integer newsType = null;

    /**
     * 是否要求有图片, 缺省 = null 表示不限制, True 有, False 没有.
     */
    public Boolean hasPicture = null;

    /**
     * 要查询的关键字, 缺省 = null 表示不限制.
     */
    public String k = null;

    /**
     * 排序方式, 缺省 = 0 按照创建时间逆序排列. = 1,按viewCount DESC. = 2, 按newsId 逆序.
     */
    public int orderType = 0;

    public SiteNewsQuery(String selectFields) {
        super(selectFields);
    }

    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("SiteNews", "snews", "");
    }

    public void resolveEntity(QueryContext qctx, String entity) {
        if ("user".equals(entity)) {
            qctx.addEntity("User", "u", "snews.userId = u.userId");
        } else {
            super.resolveEntity(qctx, entity);
        }
    }

    public void applyWhereCondition(QueryContext qctx) {
        if (userId != null) {
            qctx.addAndWhere("snews.userId = :userId");
            qctx.setInteger("userId", userId);
        }
        if (subjectId != null) {
            qctx.addAndWhere("snews.subjectId = :subjectId");
            qctx.setInteger("subjectId", subjectId);
        }
        if (status != null) {
            qctx.addAndWhere("snews.status = :status");
            qctx.setInteger("status", status);
        }
        if (newsType != null) {
            qctx.addAndWhere("snews.newsType = :newsType");
            qctx.setInteger("newsType", newsType);
        }
        if (hasPicture != null) {
            if (hasPicture) {
                qctx.addAndWhere("snews.picture IS NOT NULL AND snews.picture <> ''");
            } else {
                qctx.addAndWhere("snews.picture IS NULL OR snews.picture = ''");
            }
        }
        if (k != null) {
            String newKey = CommonUtil.escapeSQLString(k);
            qctx.addAndWhere("(snews.title LIKE :likeKey)");
            qctx.setString("likeKey", "%" + newKey + "%");
        }
    }

    public void applyOrderCondition(QueryContext qctx) {
        if (orderType == 1) {
            qctx.addOrder("snews.viewCount DESC");
        } else if (orderType == 2) {
            qctx.addOrder("snews.createDate DESC");
        } else {
            qctx.addOrder("snews.newsId DESC");
        }
    }

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}

	public void setHasPicture(Boolean hasPicture) {
		this.hasPicture = hasPicture;
	}

	public void setK(String k) {
		this.k = k;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
    
}
