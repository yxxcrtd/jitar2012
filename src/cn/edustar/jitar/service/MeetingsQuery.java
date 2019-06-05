package cn.edustar.jitar.service;

import java.text.SimpleDateFormat;

import com.jitar2Infowarelab.model.MeetingObjectType;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.CommonUtil;

public class MeetingsQuery extends BaseQuery {

    public Integer orderType = 0;
    public String obj = null;
    public Integer objId = null;
    public Integer status = null;
    public String qryDate = null;
    public String k = null;
    public SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void setK(String k) {
		this.k = k;
	}
	public void setQryDate(String qryDate) {
		this.qryDate = qryDate;
	}

	public void setSft(SimpleDateFormat sft) {
		this.sft = sft;
	}

	
    public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public void setObj(String obj) {
		this.obj = obj;
	}
	
	public MeetingsQuery(String selectFields) {
		super(selectFields);
	}

	@Override
	public void initFromEntities(QueryContext qctx) {
		qctx.addEntity("Meetings", "m", "");
	}

	 public void applyWhereCondition(QueryContext qctx) {

	        if (status != null) {
	            qctx.addAndWhere("m.status = :status");
	            qctx.setInteger("status", this.status);
	        }
	        if (this.qryDate != null) {                    
                qctx.addAndWhere("(:nowDate >= m.startTime) And (:nowDate <= m.endTime)");
                qctx.setString("nowDate", this.qryDate);
	        }
	        if (this.k != null && this.k.trim().length() != 0) {
	            String newKey = CommonUtil.escapeSQLString(this.k);
                qctx.addAndWhere("m.subject LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
	        }
	    }

	    public void applyOrderCondition(QueryContext qctx) {
	        if (orderType == 0) {
	            qctx.addOrder("m.startTime DESC");
	        }
	    }	
}
