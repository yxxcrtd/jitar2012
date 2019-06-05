package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class PlugInTopicReplyQuery extends BaseQuery {

	private Integer plugInTopicId = null;
	private Integer orderType = 1;
	public void setPlugInTopicId(Integer plugInTopicId) {
		this.plugInTopicId = plugInTopicId;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	private Integer createUserId = null;

	public PlugInTopicReplyQuery(String selectFields) {
		super(selectFields);
	}

	@Override
	public void initFromEntities(QueryContext qctx) {
		qctx.addEntity("PlugInTopicReply", "ptr", "");
	}

	@Override
	public void applyWhereCondition(QueryContext qctx) {
		if (plugInTopicId != null) {
			qctx.addAndWhere("ptr.plugInTopicId = :plugInTopicId");
			qctx.setInteger("plugInTopicId", plugInTopicId);
		}
		if (createUserId != null) {
			qctx.addAndWhere("ptr.createUserId = :createUserId");
			qctx.setInteger("createUserId", createUserId);
		}
	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == 0) {
			qctx.addOrder("ptr.plugInTopicReplyId DESC");
		}
		if (orderType == 1) {
			qctx.addOrder("ptr.plugInTopicReplyId ASC");
		}
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		if ("pt".equals(entity)) {
			qctx.addEntity("PlugInTopic", "pt",
					"pt.plugInTopicId = ptr.plugInTopicId");
		} else {
			super.resolveEntity(qctx, entity);
		}

	}
}
