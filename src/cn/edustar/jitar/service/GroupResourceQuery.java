package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

/**
 * 协作组资源查询.
 * 
 * GroupResourceQuery 从py更改为java
 * 
 * @author baimindong
 *
 */
public class GroupResourceQuery  extends ResourceQuery {

    public Integer groupId = null ; //       # int, 限定某个协作组.
    public Boolean isGroupBest = null ; //   # boolean, 是否限定协作组精华.
    public Integer gresCateId = null; //    # int, 限定协作组资源分类.
    public Integer publishToZyk = null ; // #发布到资源库
    public Boolean includeChildGroup=false; //  #是否包括子分组的
    public String gartCateName = null ;//    #分类名    
	
	  /** 分类服务 */
	  private CategoryService cate_svc;
	  
	 @Override
	 public void initFromEntities(QueryContext qctx) {
		    qctx.addEntity("GroupResource", "gr", "gr.resourceId = r.resourceId");
		    qctx.addEntity("Resource", "r", "");
	 }
	 
	public  GroupResourceQuery(String selectFields) {
		super(selectFields);
	}

	public void resolveEntity(QueryContext qctx, String entity){
	    //# 能够关联到协作组 g, 协作组资源分类 grc.
	    if("g".equals(entity)){
	      qctx.addEntity("Group", "g", "gr.groupId = g.groupId");
	    }else if("grc".equals(entity)){
	      qctx.addJoinEntity("gr", "gr.groupCate", "grc", "LEFT JOIN");
	    }else{
	      super.resolveEntity(qctx, entity);
	    }
	}
	/**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
          if (this.groupId != null){
            if(this.includeChildGroup==false){
              qctx.addAndWhere("gr.groupId = :groupId");
              qctx.setInteger("groupId", this.groupId);
            }else{
              qctx.addAndWhere("gr.groupId = :groupId or gr.groupId In(SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE parentId=:parentId)");
              qctx.setInteger("groupId", this.groupId);
              qctx.setInteger("parentId", this.groupId);
            }
          }
          if(this.isGroupBest != null){
            qctx.addAndWhere("gr.isGroupBest = :isGroupBest");
            qctx.setBoolean("isGroupBest", this.isGroupBest);
          }
          if(this.gartCateName!=null){
            qctx.addAndWhere("gr.groupCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)");
            qctx.setString("catename", this.gartCateName);
          }else if(this.gresCateId != null){
            //#只查询分类自己的  
            //#qctx.addAndWhere("gr.groupCateId = :gresCateId");
            //#qctx.setInteger("gresCateId", this.gresCateId);
            //#查询自己和子孙分类
            List<Integer> list=this.cate_svc.getCategoryIds(this.gresCateId);
            String cateIds="";
            for(int i=0;i<list.size();i++){
              if(cateIds.equals("")){
                cateIds=""+list.get(i);
              }else{
                cateIds=cateIds+","+list.get(i);
              }
            }
            qctx.addAndWhere("gr.groupCateId IN (" + cateIds + ")");
          }        
          if(this.publishToZyk != null){
            qctx.addAndWhere("r.publishToZyk = :publishToZyk");
            qctx.setInteger("publishToZyk", this.publishToZyk);
          }
          super.applyWhereCondition(qctx);    	
    }
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx){
        if(this.orderType == 0){
            qctx.addOrder("gr.id DESC");
        }
    }
    
	public CategoryService getCategoryService() {
		return cate_svc;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.cate_svc = categoryService;
	}    
}
