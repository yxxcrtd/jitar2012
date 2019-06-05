package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class QuestionQuery extends BaseQuery {
	public Integer questionId = null;
	public String parentGuid = null;
	public Integer createUserId = null;
	public Integer orderType = 0;

	public QuestionQuery(String selectFields) {
		super(selectFields);
	}

	@Override
	public void initFromEntities(QueryContext qctx) {
		qctx.addEntity("Question", "q", "");
	}

	@Override
	public void applyWhereCondition(QueryContext qctx) {
		if (parentGuid != null) {
			qctx.addAndWhere("q.parentGuid = :parentGuid");
			qctx.setString("parentGuid", parentGuid);
		}
		if (createUserId != null) {
			qctx.addAndWhere("q.createUserId = :createUserId");
			qctx.setInteger("createUserId", createUserId);
		}
	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == 0) {
			qctx.addOrder("q.questionId DESC");
		}
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		super.resolveEntity(qctx, entity);
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public void setParentGuid(String parentGuid) {
		this.parentGuid = parentGuid;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
}
