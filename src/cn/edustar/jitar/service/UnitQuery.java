package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.CommonUtil;

public class UnitQuery extends BaseQuery {

    public Integer parentId = null;
    public Boolean hasChild = null;
    public String k = null;
    public String unitName = null;
    public int orderType = 0;
    public Boolean delState = null;

    public UnitQuery(String selectFields) {
        super(selectFields);
    }
    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("Unit", "unit", "");
    }

    public void resolveEntity(QueryContext qctx, String entity) {
        super.resolveEntity(qctx, entity);
    }

    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
        if (k != null && k.trim().length() != 0) {
            String newKey = CommonUtil.escapeSQLString(k);
            qctx.addAndWhere("(unit.unitName LIKE :likeKey) OR (unit.unitType LIKE :likeKey)");
            qctx.setString("likeKey", "%" + newKey + "%");
        }
        if (parentId != null) {
            qctx.addAndWhere("unit.parentId = :parentId");
            qctx.setInteger("parentId", parentId);
        }
        if (hasChild != null) {
            qctx.addAndWhere("unit.hasChild = :hasChild");
            qctx.setBoolean("hasChild", hasChild);
        }
        
        if (delState != null) {
            qctx.addAndWhere("unit.delState = :delState");
            qctx.setBoolean("delState", delState);
        }
        
        if (orderType == 1) {
            qctx.addAndWhere("parentId <> 0");
        }
    }

    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx) {
        switch (orderType) {
            case 0 :
                qctx.addOrder("unit.unitId DESC");
                break;
            case 1 : //周活跃排名
                qctx.addOrder("unit.rank DESC");
                break;
            default :
                break;
        }
    }
}
