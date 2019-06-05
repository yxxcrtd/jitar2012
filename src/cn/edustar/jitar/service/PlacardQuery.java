package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class PlacardQuery extends BaseQuery {

    public Integer objType = null;
    public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public void setGroupCateId(Integer groupCateId) {
		this.groupCateId = groupCateId;
	}

	public void setHideState(Boolean hideState) {
		this.hideState = hideState;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	// # 要查找的公告的所属对象标识, 缺省 = null 表示不限制.
    public Integer objId = null;
    // #是否是某分类下的（例如 课题组的)
    public Integer groupCateId = null;
    // # 是否查询隐藏的, 缺省 = false 表示查询非隐藏的; = null 表示不限制; = true 表示查询隐藏的.
    public Boolean hideState = false;

    // # 排序方式, 缺省 = 0 表示按照 id 逆序排列.
    public Integer orderType = 0;

    public PlacardQuery(String selectFields) {
        super(selectFields);
    }

    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("Placard", "pld", "");
        if (null != groupCateId) {
            qctx.addEntity("Group", "g", "g.groupId=pld.objId");
        }
    }

    public void resovleEntity(QueryContext qctx, String entity) {
        super.resolveEntity(qctx, entity);
    }

    public void applyWhereCondition(QueryContext qctx) {
        if (null != objType) {
            qctx.addAndWhere("pld.objType = :objType");
            qctx.setInteger("objType", objType);
        }
        if (null != objId) {
            qctx.addAndWhere("pld.objId = :objId");
            qctx.setInteger("objId", objId);
        }
        if (null != hideState) {
            qctx.addAndWhere("pld.hide = :hideState");
            qctx.setBoolean("hideState", hideState);
        }
        if (null != groupCateId) {
            // #qctx.addAndWhere("pld.objId IN (SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE categoryId=:categoryId )");
            qctx.addAndWhere("g.categoryId=:categoryId");
            qctx.setInteger("categoryId", groupCateId);
        }
    }
    public void applyOrderCondition(QueryContext qctx) {

        if (orderType == 1) {
            qctx.addOrder("pld.title DESC");
        } else {
            qctx.addOrder("pld.id DESC");
        }

    }
}
