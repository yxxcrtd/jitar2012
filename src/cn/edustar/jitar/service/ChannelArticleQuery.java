package cn.edustar.jitar.service;
import javax.servlet.http.HttpServletRequest;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 频道文章查询
 * @author baimindong
 *
 */
public class ChannelArticleQuery extends BaseQuery{

    public Integer channelId = null;
    public Boolean articleState = true;
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
  	
	public ChannelArticleQuery(String selectFields) {
		super(selectFields);
	}
	
    @Override
    public void initFromEntities(QueryContext qctx) {
    	qctx.addEntity("ChannelArticle", "ca", "");
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
    	super.resolveEntity(qctx, entity);
    }
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
        if(this.channelId != null){
            qctx.addAndWhere("ca.channelId = :channelId");
            qctx.setInteger("channelId", this.channelId);
        }
        if(this.custormAndWhereClause != null){
            qctx.addAndWhere(" " + this.custormAndWhereClause + " ");
        }
        if(this.channelCate != null){
            qctx.addAndWhere("ca.channelCate = :channelCate");
            qctx.setString("channelCate", this.channelCate);
        }
        if(this.articleState != null){
            qctx.addAndWhere("ca.articleState = :articleState");
            qctx.setBoolean("articleState", this.articleState);
        }
        if(this.k != null && this.k.length() >0 ){
            String newKey = this.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]");
            if("title".equals(this.f)){
                qctx.addAndWhere("ca.title LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }else if("uname".equals(this.f)){
                qctx.addAndWhere("ca.loginName LIKE :keyword OR ca.userTrueName LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }else{
                qctx.addAndWhere("ca.title LIKE :keyword OR ca.loginName LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }
       }
    }
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx){
        if(this.orderType.equals(0)){
            qctx.addOrder("ca.channelArticleId DESC");
        }
    }
}
