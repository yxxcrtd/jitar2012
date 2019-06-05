package cn.edustar.jitar.service;

import javax.servlet.http.HttpServletRequest;
import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 频道用户查询
 * @author baimindong
 *
 */
public class ChannelUserQuery  extends BaseQuery{
	
    public Integer channelId = null;
    public Integer userStatus = 0;
    public Integer orderType = 0;
    public String custormAndWhereClause = null;
    public String k = null ;
    public String f = null ;
    
  	/** 请求对象 */
  	protected HttpServletRequest request;

  	public void setRequest(HttpServletRequest request) {
  		this.request = request;
  	}
  	
	public ChannelUserQuery(String selectFields) {
		super(selectFields);
	}
	
    @Override
    public void initFromEntities(QueryContext qctx) {
    	 qctx.addEntity("ChannelUser", "cu", "");
     	ParamUtil params = new ParamUtil(request);
        this.k = params.getStringParam("k");
        this.f = params.getStringParam("f");
        if(this.f == null || this.f.length() == 0){
            this.f = "title";
        }
        request.setAttribute("k", this.k);
        request.setAttribute("f", this.f);      	
    }
    
    public void resolveEntity(QueryContext qctx, String entity){
        if("user".equals(entity)){
            qctx.addJoinEntity("cu", "cu.user", "user", "LEFT JOIN");
        }else if("channel".equals(entity)){
            qctx.addJoinEntity("cu", "cu.channel", "channel", "LEFT JOIN");
        }else{
            super.resolveEntity( qctx, entity);
        }
    }
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
        if(this.userStatus != null){
            qctx.addAndWhere("user.userStatus = :userStatus");
            qctx.setInteger("userStatus", this.userStatus);
        }
        if(this.channelId != null){
            qctx.addAndWhere("cu.channelId = :channelId");
            qctx.setInteger("channelId", this.channelId);
        }
        if(this.k != null && this.k.length() > 0){
            String newKey = this.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]");            
            if(this.f.equals("email")){
                qctx.addAndWhere("user.email LIKE :keyword");
            }else if(this.f.equals("trueName")){
                qctx.addAndWhere("user.trueName LIKE :keyword");
            }else if(this.f.equals("tags")){
                qctx.addAndWhere("user.userTags LIKE :keyword");
            }else if(this.f.equals("intro")){
                qctx.addAndWhere("user.blogName LIKE :keyword OR user.blogIntroduce LIKE :keyword");
            }else if(this.f.equals("unitTitle")){
                qctx.addAndWhere("cu.unitTitle LIKE :keyword");
            }else if(this.f.equals("loginName")){
                qctx.addAndWhere("user.loginName LIKE :keyword");
            }else if(this.f.equals("name")){
                qctx.addAndWhere("cu.userId LIKE :keyword OR user.loginName LIKE :keyword OR user.trueName LIKE :keyword");
            }else{  
                qctx.addAndWhere("user.userId LIKE :keyword OR user.loginName LIKE :keyword ");
            }
            qctx.setString("keyword", "%" + newKey + "%");
        }
        if(this.custormAndWhereClause != null){
            qctx.addAndWhere(" " + this.custormAndWhereClause + " ");
        }
    }
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx){
        if(this.orderType == 0){
            qctx.addOrder("cu.channelUserId DESC");
        }
    }
}
