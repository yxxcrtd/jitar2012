package cn.edustar.jitar.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.CommonUtil;


/**
 * 活动
 * @author mxh
 *
 */
public class ActionQuery extends BaseQuery {

    public Integer orderType = 0;
    public Integer createUserId = null;
    public String ownerType = null;
    public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setQryDate(Integer qryDate) {
		this.qryDate = qryDate;
	}

	public void setSft(SimpleDateFormat sft) {
		this.sft = sft;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public void setK(String k) {
		this.k = k;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public Integer ownerId = null;
    public Integer status = null;
    public Integer qryDate = null;
    public SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String nowDate;

    public String k = null;
    public String filter = null;
    public ActionQuery(String selectFields) {
        super(selectFields);
        nowDate = sft.format(new Date());
    }

    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("Action", "act", "");
    }
    public void resolveEntity(QueryContext qctx, String entity) {
        if ("u".equals(entity)) {
            qctx.addEntity("User", "u", "act.createUserId = u.userId");
        } else {
            super.resolveEntity(qctx, entity);
        }
    }

    public void applyWhereCondition(QueryContext qctx) {
        if (createUserId != null) {
            qctx.addAndWhere("act.createUserId = :createUserId");
            qctx.setInteger("createUserId", createUserId);
        }

        if (ownerType != null) {
            qctx.addAndWhere("act.ownerType = :ownerType");
            qctx.setString("ownerType", ownerType);
        }
        if (status != null) {
            qctx.addAndWhere("act.status = :status");
            qctx.setInteger("status", this.status);
        }
        if (this.ownerId != null) {
            qctx.addAndWhere("act.ownerId = :ownerId");
            qctx.setInteger("ownerId", this.ownerId);
        }
        if (this.qryDate != null) {                    
            if (this.qryDate == 0) { //全部
                //qctx.addAndWhere("(:nowDate < act.attendLimitDateTime)");
                //qctx.setString("nowDate", this.nowDate);
            }
            if (this.qryDate == 1) { //正在报名的活动
                qctx.addAndWhere("(:nowDate >= act.startDateTime) And (:nowDate <= act.finishDateTime)");
                qctx.setString("nowDate", this.nowDate);
                qctx.setString("nowDate", this.nowDate);
            }
            if (this.qryDate == 2) { //已经完成的活动
                qctx.addAndWhere("(:nowDate > act.finishDateTime)");
                qctx.setString("nowDate", this.nowDate);
            }
        }
        if (this.k != null && this.k.trim().length() != 0) {
            String newKey = CommonUtil.escapeSQLString(this.k);
            if (this.filter == null || this.filter.trim().length() == 0) {
                this.filter = "title";
            }

            if (this.filter.equals("title")) {
                qctx.addAndWhere("act.title LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (this.filter.equals("description")) {
                qctx.addAndWhere("act.description LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (this.filter.equals("place")) {
                qctx.addAndWhere("act.place LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (this.filter.equals("loginName")) {
                qctx.addAndWhere("u.loginName LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (this.filter.equals("trueName")) {
                qctx.addAndWhere("u.trueName LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }
        }
    }

    public void applyOrderCondition(QueryContext qctx) {
        if (orderType == 0) {
            qctx.addOrder("act.actionId DESC");
        } else if (orderType == 1) {
            qctx.addOrder("act.createDate DESC");
        }
    }
}
