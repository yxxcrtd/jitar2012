package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

/**
 * 短消息查询.
 * 
 * /WEB-INF/jython/message_query.py
 * @author baimindong
 *
 */
public class MessageQuery extends BaseQuery  {

    public Integer sendId = null;  //      # 消息发送者标识, 缺省不限定.
    public Integer receiveId = null; //   # 消息接收者标识, 缺省不限定.
    
    public Boolean isDel = false;   //   # 删除标识(删除至回收站) 默认为false, 表示查询未删除的.
    public Boolean isSenderDel = null ; // # 发送者删除消息标识, 缺省为 None 不限定.
    public int orderType = 0 ;//  # 缺省按照 id 逆序排列.
	
	public MessageQuery(String selectFields) {
		super(selectFields);
	}
	
    @Override
    public void initFromEntities(QueryContext qctx) {
    	qctx.addEntity("Message" , "msg", "");
    }
    
    public void resolveEntity(QueryContext qctx, String entity){
    	if("send".equals(entity)){  			//关联到接收者.
            qctx.addJoinEntity("msg", "msg.sender" , "send", "LEFT JOIN");
    	}else if("recv".equals(entity)){ 		//关联到发送者.
            qctx.addJoinEntity("msg", "msg.receiver" , "recv", "LEFT JOIN");
    	}else{
        	super.resolveEntity(qctx, entity);
    	}
    }
    
    public void applyWhereCondition(QueryContext qctx) {
        if(this.sendId != null){
            qctx.addAndWhere("msg.sendId = :sendId");
            qctx.setInteger("sendId", this.sendId);
        }
        if(this.receiveId != null){
            qctx.addAndWhere("msg.receiveId = :receiveId");
            qctx.setInteger("receiveId", this.receiveId);
        }
        if(this.isDel != null){
            qctx.addAndWhere("msg.isDel = :isDel");
            qctx.setBoolean("isDel", this.isDel) ;
        }
        if(this.isSenderDel != null){
            qctx.addAndWhere("msg.isSenderDel = :isSenderDel");
            qctx.setBoolean("isSenderDel", this.isSenderDel);
        }
    }
    
    public void applyOrderCondition(QueryContext qctx) {
        if(this.orderType == 0){
            qctx.addOrder("msg.id DESC");
        }else if(this.orderType == 1){
            qctx.addOrder("msg.sendTime DESC");
        }
    }
}
