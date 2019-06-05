package cn.edustar.jitar.action;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class UserVoteQuery extends BaseQuery {

	private String parentGuid = null;
	private String parentObjectType = null;
	private Integer orderType = 0;
	private Integer userId = null;

	public UserVoteQuery(String selectFields) {
		super(selectFields);
	}

	@Override
	public void initFromEntities(QueryContext qctx) {
		qctx.addEntity("VoteUser", "vu", "");
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
		if (userId != null) {
			qctx.addAndWhere("vu.userId = :userId");
			qctx.setInteger("userId", userId);
		}

	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == 0) {
			qctx.addOrder("vu.voteUserId DESC");
		}
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		if ("vote".equals(entity.trim())) {
			qctx.addEntity("Vote", "vote", "vote.voteId = vu.voteId");
		} else {
			super.resolveEntity(qctx, entity);
		}

	}
	

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setParentObjectType(String parentObjectType) {
		this.parentObjectType = parentObjectType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setParentGuid(String parentGuid) {
		this.parentGuid = parentGuid;
	}
}
