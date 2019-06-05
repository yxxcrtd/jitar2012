package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class TagGroupQuery extends BaseQuery {

	public TagGroupQuery(String selectFields) {
		super(selectFields);
	}

	private Integer tagId = null;//
	private Integer objectType = 2;
	private Integer groupState = 0;
	private Integer orderType = 0;

	@Override
	public void initFromEntities(QueryContext qctx) {
		qctx.addEntity("TagRef", "tr", "");
	}

	@Override
	public void applyWhereCondition(QueryContext qctx) {
		if (tagId != null) {
			qctx.addAndWhere("tr.tagId = :tagId");
			qctx.setInteger("tagId", tagId);
		}

		if (objectType != null) {
			qctx.addAndWhere("tr.objectType = :objectType");
			qctx.setInteger("objectType", objectType);
		}

		if (groupState != null) {
			qctx.addAndWhere("g.groupState = :groupState");
			qctx.setInteger("groupState", groupState);
		}

	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == 0) {
			qctx.addOrder("g.groupId DESC");
		}

	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		if ("g".equals(entity.trim())) {
			qctx.addEntity("Group", "g", "g.groupId = tr.objectId");
		} else {
			super.resolveEntity(qctx, entity);
		}

	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public void setGroupState(Integer groupState) {
		this.groupState = groupState;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

}
