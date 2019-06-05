package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class UnitNewsNoticeQuery  extends BaseQuery{
    public Integer itemType = null;
    public Integer unitId = null;
    public Integer orderType = 0;
    
	public UnitNewsNoticeQuery(String selectFields) {
		super(selectFields);
	}
	
    @Override
    public void initFromEntities(QueryContext qctx) {
    	qctx.addEntity("UnitNews", "un", "");
    }
    
    public void resolveEntity(QueryContext qctx, String entity) {
        super.resolveEntity(qctx, entity);
    }
    
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
        if(this.itemType != null){
            qctx.addAndWhere("un.itemType = :itemType");
            qctx.setInteger("itemType", this.itemType);
        }
            
        if(this.unitId != null){
            qctx.addAndWhere("un.unitId = :unitId");
            qctx.setInteger("unitId", this.unitId);
        }
    	
    }
    
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx) {
        if(this.orderType == 0){
            qctx.addOrder("un.unitNewsId DESC");
        }
    }
}
