package cn.edustar.jitar.service;

import cn.edustar.jitar.data.QueryContext;

public class UserQuery2 extends UserQuery {

    public String  kk = null ;
    public String ff = "name";
    public Boolean isAdmin = null ;     //# 是否查找用户管理员.
    public Boolean isCensor = null ;    //# 是否查找内容管理员.
    public Boolean isCommon = true ;   //# 是否查找非删除人员

	public UserQuery2(String selectFields) {
		super(selectFields);
	}
	
	/*
	@Override
	public void initFromEntities(QueryContext qctx) {
		qctx.addEntity("User", "u", "");
	}*/
	
	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
	      if(entity.equals("subj")){
	        qctx.addJoinEntity("u", "u.subject", "subj", "LEFT JOIN");
	      }else if(entity.equals("grad")){
	        qctx.addJoinEntity("u", "u.grade", "grad", "LEFT JOIN");
	      }else if(entity.equals("unit")){
	        qctx.addJoinEntity("u", "u.unit", "unit", "LEFT JOIN");
	      }else if(entity.equals("sc")){
	        qctx.addJoinEntity("u", "u.sysCate", "sc", "LEFT JOIN");
	      }else{  
	    	  super.resolveEntity(qctx, entity);
	      }
	}
	
	@Override
	public void applyWhereCondition(QueryContext qctx) {
	    super.applyWhereCondition(qctx);
	    if(this.kk != null && this.kk.length() > 0){
	      String newKey = this.kk.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]");
	      if(this.ff.equals("name")){
		        qctx.addAndWhere("u.loginName LIKE :keyword OR u.nickName LIKE :keyword OR u.trueName LIKE :keyword");
		        qctx.setString("keyword", "%" + newKey + "%");
	      }
	    }
	}
}
