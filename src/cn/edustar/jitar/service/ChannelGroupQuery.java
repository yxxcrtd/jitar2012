package cn.edustar.jitar.service;

import javax.servlet.http.HttpServletRequest;
import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 频道协作组查询
 * @author baimindong
 *
 */
public class ChannelGroupQuery extends BaseQuery {
    
	public Integer channelId = null;
    public Integer orderType = 0;
    
	public ChannelGroupQuery(String selectFields) {
		super(selectFields);
	}
    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("Group", "g", "");
        qctx.addEntity("ChannelUser", "cu", "g.createUserId = cu.userId");
    }
    
    public void resolveEntity(QueryContext qctx, String entity){
    	super.resolveEntity(qctx, entity);
    }
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
        qctx.addAndWhere("g.groupState = 0");
        if(this.channelId != null){
            qctx.addAndWhere("cu.channelId = :channelId");
            qctx.setInteger("channelId", this.channelId);
        }
    }
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx){
        if(this.orderType == 0){
            qctx.addOrder("g.groupId DESC");
        }
    }
}
