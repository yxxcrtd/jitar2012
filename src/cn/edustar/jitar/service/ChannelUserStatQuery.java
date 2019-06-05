package cn.edustar.jitar.service;

import javax.servlet.http.HttpServletRequest;
import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 频道的用户统计查询
 * @author baimindong
 *
 */
public class ChannelUserStatQuery extends BaseQuery  {
    public Integer channelId = null;
    public String statGuid = null;
    public Integer orderType = 0;
    public String k = null ; // public Integer params.getStringParam("k")
    public String f = null; //public Integer params.getStringParam("f")

    
  	/** 请求对象 */
  	protected HttpServletRequest request;

  	public void setRequest(HttpServletRequest request) {
  		this.request = request;
  	}
	public ChannelUserStatQuery(String selectFields) {
		super(selectFields);
	}
	
    @Override
    public void initFromEntities(QueryContext qctx) {
    	qctx.addEntity("ChannelUserStat", "cus", "");
     	ParamUtil params = new ParamUtil(request);
        this.k = params.getStringParam("k");
        this.f = params.getStringParam("f");
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
            qctx.addAndWhere("cus.channelId = :channelId");
            qctx.setInteger("channelId", this.channelId);
        }
        if(this.statGuid != null){
            qctx.addAndWhere("cus.statGuid = :statGuid");
            qctx.setString("statGuid", this.statGuid);
        }
    }
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx){
        if(this.orderType == 0){
            qctx.addOrder("cus.id ASC");
        }
    }
}
