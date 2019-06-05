package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class VoteQuery extends BaseQuery {

	private String parentGuid = null;
	private String parentObjectType = null;
	private Integer createUserId = null;
	private Integer orderType = 0;

	public VoteQuery(String selectFields) {
		super(selectFields);
	}

	@Override
	public void initFromEntities(QueryContext qctx) {
		qctx.addEntity("Vote", "vote", "");
	}

	@Override
	public void applyWhereCondition(QueryContext qctx) {
		if (parentGuid != null) {
			qctx.addAndWhere("vote.parentGuid = :parentGuid");
			qctx.setString("parentGuid", parentGuid);
		}
		if (parentObjectType != null) {
			qctx.addAndWhere("vote.parentObjectType = :parentObjectType");
			qctx.setString("parentObjectType", parentObjectType);
		}

		if (createUserId != null) {
			qctx.addAndWhere("vote.createUserId = :createUserId");
			qctx.setInteger("createUserId", createUserId);
		}

	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == 0) {
			qctx.addOrder("vote.voteId DESC");
		}
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		super.resolveEntity(qctx, entity);
	}

	public void setParentObjectType(String parentObjectType) {
		this.parentObjectType = parentObjectType;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setParentGuid(String parentGuid) {
		this.parentGuid = parentGuid;
	}
}
