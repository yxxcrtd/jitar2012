package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class TagResourceQuery extends BaseQuery {

	public TagResourceQuery(String selectFields) {
		super(selectFields);
	}

	private Integer tagId = null;//
	private Integer objectType = 12;//
	private Integer auditState = 0;//
	private Integer shareMode = 1000;//
	private Boolean delState = false;//
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

		if (auditState != null) {
			qctx.addAndWhere("r.auditState = :auditState");
			qctx.setInteger("auditState", auditState);
		}

		if (delState != null) {
			qctx.addAndWhere("r.delState = :delState");
			qctx.setBoolean("delState", delState);
		}

		if (shareMode != null) {
			qctx.addAndWhere("r.shareMode >= :shareMode");
			qctx.setInteger("shareMode", shareMode);
		}

	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == 0) {
			qctx.addOrder("r.resourceId DESC");
		}

	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		if ("r".equals(entity.trim())) {
			qctx.addEntity("Resource", "r", "r.resourceId = tr.objectId");
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

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public void setShareMode(Integer shareMode) {
		this.shareMode = shareMode;
	}

	public void setDelState(Boolean delState) {
		this.delState = delState;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

}
