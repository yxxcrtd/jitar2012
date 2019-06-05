package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class SpecialSubjectQuery extends BaseQuery {
	
	public String objectType = null;
	public Integer specialSubjectId = null;
	public Integer objectId = null;
	public String orderType = null;
	
	@Override
	public void initFromEntities(QueryContext qctx) {
		qctx.addEntity("SpecialSubject", "ss", "");
	}

	@Override
	public void applyWhereCondition(QueryContext qctx) {
		if (specialSubjectId != null) {
			qctx.addAndWhere("ss.specialSubjectId = :specialSubjectId");
			qctx.setInteger("specialSubjectId", specialSubjectId);
		}
		if (objectType != null) {
			qctx.addAndWhere("ss.objectType = :objectType");
			qctx.setString("objectType", objectType);
		}
		if (objectId != null) {
			qctx.addAndWhere("ss.objectId = :objectId");
			qctx.setInteger("objectId", objectId);
		}
	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == null) {
			qctx.addOrder("ss.specialSubjectId DESC");
		}
	}

	public SpecialSubjectQuery(String selectFields) {
		super(selectFields);
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		super.resolveEntity(qctx, entity);
	}
	
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public void setSpecialSubjectId(Integer specialSubjectId) {
		this.specialSubjectId = specialSubjectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}
