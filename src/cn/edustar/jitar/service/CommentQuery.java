package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 评论查询
 * 
 * CommentQuery.py
 * 
 * @author baimindong
 *
 */
public class CommentQuery extends BaseQuery {
	  //#发表评论的用户标识, 缺省 = null, 表示不限定
      public Integer userId = null ;
      //#被评论的文章/资源的所属用户的标识,缺省 = null 表示不限定
      public Integer aboutUserId = null ;
      //#被评论的对象类型, 缺省 = null 表示不限定; 一般和objectId 一起使用.
      public Integer objType = null ;
      //#被评论的对象标识, 缺省 = null 表示不限定
      public Integer objId = null ;
      //#审核标志; 缺省 = 1 表示获取审核通过的, = 0 表示获取未审核通过的; = null 表示不限定
      public Integer audit = 1;
      //#排序方式, 0 - 按照 id 逆序排列,  1 - createDate 逆序排列
      public Integer orderType = 0;
      //# 查询关键字. 
      public String k = null ;    
      //# 查询字段. 0 标题或内容  1用户
      public String f = "0";
      public Integer unitId = null ;
      public String commentType = null ;
      public Integer lastId = null; //用来进行指定范围
      
      
	public CommentQuery(String selectFields) {
		super(selectFields);
	}
	
    @Override
    public void initFromEntities(QueryContext qctx) {
    	qctx.addEntity("Comment" , "cmt" , "");
    }
    
    public void resolveEntity(QueryContext qctx, String entity){
          if("u".equals(entity)){
            if("aboutUser".equals(commentType)){
              qctx.addEntity("User", "u", "cmt.aboutUserId = u.userId");
            }else{
              qctx.addEntity("User", "u", "cmt.userId = u.userId");
            }
          }else if("r".equals(entity)){
            qctx.addEntity("Resource", "r", "cmt.objId = r.resourceId");
          }else if("a".equals(entity)){
            qctx.addEntity("Article", "a", "cmt.objId = a.articleId");
          }else if("p".equals(entity)){
            qctx.addEntity("Photo", "p", "cmt.objId = p.photoId");
          }else{
            super.resolveEntity(qctx, entity);
          }
    }
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
          if(this.k != null && this.k.length() > 0){
            String newKey = CommonUtil.escapeSQLString(this.k);
            if(this.f.equals("0")){
              qctx.addAndWhere("(cmt.title LIKE :likeKey) OR (cmt.content LIKE :likeKey)");
              qctx.setString("likeKey", "%" + newKey + "%");
            }else if(this.f.equals("1")){
              qctx.addAndWhere("(u.nickName LIKE :likeKey) OR (u.trueName LIKE :likeKey) OR (u.loginName LIKE :likeKey)");
              qctx.setString("likeKey", "%" + newKey + "%");
            }
          }
          if(this.userId != null){
            qctx.addAndWhere("cmt.userId = :userId");
            qctx.setInteger("userId", this.userId);
          }
          if(this.aboutUserId != null){
            qctx.addAndWhere("cmt.aboutUserId = :aboutUserId");
            qctx.setInteger("aboutUserId", this.aboutUserId);
          }
          if(this.objType != null){
            qctx.addAndWhere("cmt.objType = :objType");
            qctx.setInteger("objType", this.objType);
          }
          if(this.objId != null){
            qctx.addAndWhere("cmt.objId = :objId");
            qctx.setInteger("objId", this.objId);
          }
          if(this.audit != null){
            qctx.addAndWhere("cmt.audit = :audit");
            qctx.setInteger("audit", this.audit);
    	  }
          if(this.unitId != null){
            qctx.addAndWhere("u.unitId = :unitId");
            qctx.setInteger("unitId", this.unitId) ;
          }
          if(this.lastId != null){
              qctx.addAndWhere("cmt.id < :lastId");
              qctx.setInteger("lastId", this.lastId);
          }
    }
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx){
        //# 排序方式, 0 - commentId DESC, 1 - createDate
        if(this.orderType.equals(0)){  
          qctx.addOrder("cmt.id DESC");
        }else if (this.orderType.equals(1)){
          qctx.addOrder("cmt.createDate DESC"); 
        }else if(this.orderType.equals(3)){  
          qctx.addOrder("cmt.id ASC") ;
        }
    }
}
