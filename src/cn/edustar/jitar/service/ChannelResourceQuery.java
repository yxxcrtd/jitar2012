package cn.edustar.jitar.service;

import javax.servlet.http.HttpServletRequest;
import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.ParamUtil;
/**
 * 频道资源查询
 * @author baimindong
 *
 */
public class ChannelResourceQuery  extends BaseQuery{
    public Integer channelId = null;
    public String channelCate = null;
    public Integer orderType = 0;
    public String custormAndWhereClause = null;
    public String k = null ; // public Integer params.getStringParam("k")
    public String f = null; //public Integer params.getStringParam("f")

    
  	/** 请求对象 */
  	protected HttpServletRequest request;

  	public void setRequest(HttpServletRequest request) {
  		this.request = request;
  	}
	public ChannelResourceQuery(String selectFields) {
		super(selectFields);
	}
	
    @Override
    public void initFromEntities(QueryContext qctx) {
    	qctx.addEntity("ChannelResource", "cr", "");
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
        if("resource".equals(entity)){
            qctx.addJoinEntity("cr", "cr.resource", "resource","LEFT JOIN");
        }else if("user".equals(entity)){
            qctx.addJoinEntity("cr", "cr.user", "user","LEFT JOIN");
        }else if("unit".equals(entity)){
            qctx.addJoinEntity("cr", "cr.unit", "unit","LEFT JOIN");
        }else{
            super.resolveEntity(qctx, entity);
        }
    }
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
        qctx.addAndWhere("resource.auditState = :auditState");
        qctx.setInteger("auditState", 0);
        qctx.addAndWhere("resource.delState = :delState");
        qctx.setBoolean("delState", false);
        qctx.addAndWhere("resource.shareMode > :shareMode");
        qctx.setInteger("shareMode", 500);
        if(this.channelId != null){
            qctx.addAndWhere("cr.channelId = :channelId");
            qctx.setInteger("channelId", this.channelId);
        }
        if(this.custormAndWhereClause != null){
            qctx.addAndWhere(" " + this.custormAndWhereClause + " ");
        }
        if(this.channelCate != null){
            qctx.addAndWhere("cr.channelCate = :channelCate");
            qctx.setString("channelCate", this.channelCate)   ;
        }
        if(this.k != null && this.k.length() >0){
            String newKey = this.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]");            
            if("uname".equals(this.f)){
                qctx.addAndWhere("user.trueName LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }else if("unitTitle".equals(this.f)){
                qctx.addAndWhere("unit.unitTitle LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }else{
                qctx.addAndWhere("resource.title LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%")    ;
            }
        }
    }
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx){
        if (this.orderType == 0){
            qctx.addOrder("cr.channelResourceId DESC");
        }
    }	

}
