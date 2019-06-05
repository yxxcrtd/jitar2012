package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class PlugInTopicQuery extends BaseQuery {

	public String parentGuid = null;
	public String parentObjectType = null;
	public Integer createUserId = null;
	public Integer orderType = 0;

	public PlugInTopicQuery(String selectFields) {
		super(selectFields);
	}

	@Override
	public void initFromEntities(QueryContext qctx) {
		qctx.addEntity("PlugInTopic", "pt", "");
	}

	@Override
	public void applyWhereCondition(QueryContext qctx) {
		if (parentGuid != null) {
			qctx.addAndWhere("pt.parentGuid = :parentGuid");
			qctx.setString("parentGuid", parentGuid);
		}
		if (parentObjectType != null) {
			qctx.addAndWhere("pt.parentObjectType = :parentObjectType");
			qctx.setString("parentObjectType", parentObjectType);
		}
		if (createUserId != null) {
			qctx.addAndWhere("pt.createUserId = :createUserId");
			qctx.setInteger("createUserId", createUserId);
		}
	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType == 0) {
			qctx.addOrder("pt.plugInTopicId DESC");
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
