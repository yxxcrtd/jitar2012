package cn.edustar.jitar.service;


import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class TagQuery extends BaseQuery {
	//标签标识, 缺省 = null, 表示不限定.
	private Integer tagId = null;
	//标签名字, 缺省 = null , 表示不限定.
    private String tagName = null;
    //是否获取被禁用的, 缺省 = 0,表示获取非禁用的.
    private boolean disabled = false;
    //排序方式,缺省 =0, 表示按照Id 逆序排列. =1 ,按照 refCount逆序排序. =2,按照viewCount 逆序排列.
    private int orderType = 0;
    

	public TagQuery(String selectFields) {
		super(selectFields);
	}
	
	/**
	 * 构造需要餐叙的字段
	 */
	@Override
	public void initFromEntities(QueryContext qctx){
		qctx.addEntity("Tag" , "tag" , "");
	}
	
	/**
	 * where条件拼接
	 */
	@Override
	public void applyWhereCondition(QueryContext qctx) {
		 if(this.tagId!=null){
			 qctx.addAndWhere("tag.tagId = :tagId");
		     qctx.setInteger("tagId", this.tagId);
		 }
		 if (this.tagName != null){
			 qctx.addAndWhere("tag.tagName = :tagName");
		     qctx.setString("tagName", this.tagName); 
		 }
		     
		 if (this.disabled != false){
			 qctx.addAndWhere("tag.disabled = :disabled");
		     qctx.setBoolean("disabled", this.disabled);
		 }
	}

	/**
	 * 排序
	 */
	@Override
	public void applyOrderCondition(QueryContext qctx) {
		 if(this.orderType == 0){
			 qctx.addOrder("tag.tagId DESC");
		 }else if(this.orderType == 1){
			 qctx.addOrder("tag.refCount DESC");
		 }else if(this.orderType==2){
			 qctx.addOrder("tag.viewCount DESC");
		 }
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		super.resolveEntity(qctx, entity);
	}
}
