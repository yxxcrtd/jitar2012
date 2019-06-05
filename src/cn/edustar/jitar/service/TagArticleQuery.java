package cn.edustar.jitar.service;


import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class TagArticleQuery extends BaseQuery {
	
	public TagArticleQuery(String selectFields) {
		super(selectFields);
	}

	private Integer tagId = null;
	private Integer objectType = 3;
	private Integer auditState = 0;
	private Integer hideState = 0;
	private Boolean draftState = false;
	private Boolean delState = false;
	private Integer orderType = 0;

	@Override
	public void initFromEntities(QueryContext qctx) {
		qctx.addEntity("TagRef" , "tr" , "");
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
			qctx.addAndWhere("a.auditState = :auditState");
			qctx.setInteger("auditState", auditState);
		}

		if (hideState != null) {
			qctx.addAndWhere("a.hideState = :hideState");
			qctx.setInteger("hideState", hideState);
		}

		if (draftState != null) {
			qctx.addAndWhere("a.draftState = :draftState");
			qctx.setBoolean("draftState", draftState);
		}

		if (delState != null) {
			qctx.addAndWhere("a.delState = :delState");
			qctx.setBoolean("delState", delState);
		}

	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == 0) {
			qctx.addOrder("a.articleId DESC");
		}

	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		if ("a".equals(entity.trim())) {
			qctx.addEntity("Article", "a", "a.articleId = tr.objectId");
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

	public void setHideState(Integer hideState) {
		this.hideState = hideState;
	}

	public void setDraftState(Boolean draftState) {
		this.draftState = draftState;
	}

	public void setDelState(Boolean delState) {
		this.delState = delState;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
}
