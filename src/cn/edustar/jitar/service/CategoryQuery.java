package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class CategoryQuery  extends BaseQuery {
    //#分类的父分类, 缺省 = null
    public Integer parentId = null;
    
    //#分类的类型　缺省是文章分类
    public String itemType = "default";
    
	public CategoryQuery(String selectFields) {
		super(selectFields);
	}
    @Override
    public void initFromEntities(QueryContext qctx) {
    	qctx.addEntity("Category" , "cat" , "");
    }
    
    public void resolveEntity(QueryContext qctx, String entity) {
    	super.resolveEntity(qctx, entity);
    }
    
    public void applyWhereCondition(QueryContext qctx) {
        if (this.parentId == null){
            qctx.addAndWhere("cat.parentId = NULL");
        }else if(this.parentId == 0){
            qctx.addAndWhere("cat.parentId = NULL");
        }else{
            qctx.addAndWhere("cat.parentId = :parentId");
            qctx.setInteger("parentId", this.parentId);
        }
        if (this.itemType != null){
            qctx.addAndWhere("cat.itemType = :itemType");
            qctx.setString("itemType", this.itemType);
        }
    	
    }
    
    public void applyOrderCondition(QueryContext qctx) {
    	qctx.addOrder("cat.orderNum ASC");
    }
}
