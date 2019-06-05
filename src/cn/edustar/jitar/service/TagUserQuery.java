package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class TagUserQuery extends BaseQuery {

	public TagUserQuery(String selectFields) {
		super(selectFields);
	}

	private Integer objectType = 1;//
	private Integer orderType = 0;//
	private Integer userStatus = 0;//
	private Integer tagId = null;

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

		if (userStatus != null) {
			qctx.addAndWhere("u.userStatus = :userStatus");
			qctx.setInteger("userStatus", userStatus);
		}
	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == 0) {
			qctx.addOrder("u.userId DESC");
		}
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		if ("u".equals(entity.trim())) {
			qctx.addEntity("User", "u", "u.userId = tr.objectId");
		} else {
			super.resolveEntity(qctx, entity);
		}
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

}
