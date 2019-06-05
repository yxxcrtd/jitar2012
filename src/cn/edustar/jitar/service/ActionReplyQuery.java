package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class ActionReplyQuery extends BaseQuery {

    public Integer orderType = 0;
    public Integer actionId = null;
    public Integer userId = null;

    public ActionReplyQuery(String selectFields) {
        super(selectFields);
    }

    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("ActionReply", "actr", "");

    }

    public void resolveEntity(QueryContext qctx, String entity) {
        if ("u".equals(entity)) {
            qctx.addEntity("User", "u", "actr.userId = u.userId");
        } else {
            super.resolveEntity(qctx, entity);
        }
    }

    public void applyWhereCondition(QueryContext qctx) {
        if (this.actionId != null) {
            qctx.addAndWhere("actr.actionId = :actionId");
            qctx.setInteger("actionId", this.actionId);
        }
        if (this.userId != null) {
            qctx.addAndWhere("actr.userId = :userId");
            qctx.setInteger("userId", this.userId);
        }
    }
    public void applyOrderCondition(QueryContext qctx) {
        if (this.orderType == 0) {
            qctx.addOrder("actr.actionReplyId ASC");
        }
    }

}
