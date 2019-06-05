package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class PraiseQuery extends BaseQuery {

    public Integer objType = null;
    public Integer objId = null;

    public PraiseQuery(String selectFields) {
        super(selectFields);
    }

    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("Praise", "p", "");
    }

    public void resolveEntity(QueryContext qctx, String entity) {
        if (entity.equals("u")) {
            qctx.addEntity("User", "u", "p.userId = u.userId");
        } else {
            super.resolveEntity(qctx, entity);
        }
    }

    public void applyWhereCondition(QueryContext qctx) {
        if (null != objType) {
            qctx.addAndWhere("p.objType = :objType");
            qctx.setInteger("objType", objType);
        }
        if (null != objId) {
            qctx.addAndWhere("p.objId = :objId");
            qctx.setInteger("objId", objId);
        }
    }

    public void applyOrderCondition(QueryContext qctx) {
        qctx.addOrder("p.praiseId DESC");
    }

}
