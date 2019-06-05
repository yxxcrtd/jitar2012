package cn.edustar.jitar.service;

import javax.servlet.http.HttpServletRequest;
import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;


/**
 * 频道单位统计查询
 * @author baimindong
 *
 */
public class ChannelUnitStatQuery extends BaseQuery {
    public Integer channelId = null;
    public String statGuid = null;
    public Integer orderType = 0;
    
  	/** 请求对象 */
  	protected HttpServletRequest request;

  	public void setRequest(HttpServletRequest request) {
  		this.request = request;
  	}
	public ChannelUnitStatQuery(String selectFields) {
		super(selectFields);
	}
    @Override
    public void initFromEntities(QueryContext qctx) {
    	qctx.addEntity("ChannelUnitStat", "cuns", "");
    }
    
    public void resolveEntity(QueryContext qctx, String entity){
    	super.resolveEntity( qctx, entity);
    }
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
        if (this.channelId != null){
            qctx.addAndWhere("cuns.channelId = :channelId");
            qctx.setInteger("channelId", this.channelId);
        }
        if(this.statGuid != null){
            qctx.addAndWhere("cuns.statGuid = :statGuid");
            qctx.setString("statGuid", this.statGuid);
        }
    }
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx){
        if(this.orderType == 0){
            qctx.addOrder("cuns.id ASC");
        }
    }
}
