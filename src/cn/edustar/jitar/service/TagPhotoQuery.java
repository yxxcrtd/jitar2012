package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class TagPhotoQuery extends BaseQuery {

	public TagPhotoQuery(String selectFields) {
		super(selectFields);
	}

	private Integer tagId = null;
	private Integer objectType = 11;
	private Boolean delState = false;
	private Boolean isPrivateShow = false;
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

		if (isPrivateShow != null) {
			qctx.addAndWhere("p.isPrivateShow = :isPrivateShow");
			qctx.setBoolean("isPrivateShow", isPrivateShow);
		}

		if (delState != null) {
			qctx.addAndWhere("p.delState = :delState");
			qctx.setBoolean("delState", delState);
		}

	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == 0) {
			qctx.addOrder("p.photoId DESC");
		}
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		if ("p".equals(entity.trim())) {
			qctx.addEntity("Photo", "p", "p.photoId = tr.objectId");
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

	public void setDelState(Boolean delState) {
		this.delState = delState;
	}

	public void setIsPrivateShow(Boolean isPrivateShow) {
		this.isPrivateShow = isPrivateShow;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}


}
