package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class EvaluationTemplateQuery extends BaseQuery{

    public Boolean enabled = null;
    public Integer orderType = 0;
    
	public EvaluationTemplateQuery(String selectFields) {
		super(selectFields);
	}
	
    @Override
    public void initFromEntities(QueryContext qctx) {
    	qctx.addEntity("EvaluationTemplate", "et", "");
    }
    
    public void resolveEntity(QueryContext qctx, String entity){
    	super.resolveEntity(qctx, entity);
    }
    
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
        if(this.enabled != null){
            qctx.addAndWhere("et.enabled = :enabled");
            qctx.setBoolean("enabled", this.enabled);
        }
    }
    
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx){
        if(this.orderType == 0){
            qctx.addOrder("et.evaluationTemplateId DESC");
        }
    }
}

    