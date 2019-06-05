package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class TagPrepareCourseQuery extends BaseQuery {

	public TagPrepareCourseQuery(String selectFields) {
		super(selectFields);
	}

	private Integer tagId = null;//
	private Integer objectType = 15;//
	private Integer status = 0;//
	private Integer orderType = 0;//

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

		if (status != null) {
			qctx.addAndWhere("pc.status = :status");
			qctx.setInteger("status", status);
		}

	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == 0) {
			qctx.addOrder("pc.prepareCourseId DESC");
		}
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		if ("pc".equals(entity.trim())) {
			qctx.addEntity("PrepareCourse", "pc",
					"pc.prepareCourseId = tr.objectId");
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

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

}
