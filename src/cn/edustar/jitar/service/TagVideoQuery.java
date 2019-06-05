package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.model.ObjectType;

public class TagVideoQuery  extends BaseQuery{


	private Integer tagId = null;
	private Integer objectType = ObjectType.OBJECT_TYPE_VIDEO.getTypeId();
    public Integer auditState = 0;
    private Integer orderType = 0;

	public TagVideoQuery(String selectFields) {
		super(selectFields);
	}
	
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
            qctx.addAndWhere("v.auditState = :auditState");
            qctx.setInteger("auditState", auditState);
        }
	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == 0) {
			qctx.addOrder("v.videoId DESC");
		}
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		if ("v".equals(entity.trim())) {
			qctx.addEntity("Video", "v", "v.videoId = tr.objectId");
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

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
}
