package cn.edustar.jitar.service;

import javax.servlet.http.HttpServletRequest;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 频道图片查询
 * @author baimindong
 *
 */
public class ChannelPhotoQuery  extends BaseQuery{

    public Integer channelId = null;
    public Integer photoId = null;
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
    	  	
	public ChannelPhotoQuery(String selectFields) {
		super(selectFields);
	}
	
    @Override
    public void initFromEntities(QueryContext qctx) {
    	qctx.addEntity("ChannelPhoto", "cp", "");
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
        if("photo".equals(entity)){
            qctx.addJoinEntity("cp", "cp.photo", "photo","LEFT JOIN");
        }else if("user".equals(entity)){
            qctx.addJoinEntity("cp", "cp.user", "user","LEFT JOIN");
        }else if("unit".equals(entity)){
            qctx.addJoinEntity("cp", "cp.unit", "unit","LEFT JOIN");
        }else{
            super.resolveEntity(qctx, entity);
        }
    }
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
        qctx.addAndWhere("photo.auditState = :auditState");
        qctx.setInteger("auditState", 0);
        qctx.addAndWhere("photo.isPrivateShow = :isPrivateShow");
        qctx.setBoolean("isPrivateShow", false);
        qctx.addAndWhere("photo.delState = :delState");
        qctx.setBoolean("delState", false);
        if(this.channelId != null){
            qctx.addAndWhere("cp.channelId = :channelId");
            qctx.setInteger("channelId", this.channelId);
        }
        if(this.custormAndWhereClause != null){
            qctx.addAndWhere(" " + this.custormAndWhereClause + " ");
        }
        if(this.channelCate != null){
            qctx.addAndWhere("cp.channelCate = :channelCate");
            qctx.setString("channelCate", this.channelCate);
        }
        if(this.k != null && this.k.length() > 0 ){
            String newKey = this.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]");
            if(this.f.equals("uname")){
                qctx.addAndWhere("user.trueName LIKE :keyword ");
                qctx.setString("keyword", "%" + newKey + "%");
            }else if(this.f.equals("unitTitle")){
                qctx.addAndWhere("unit.unitTitle LIKE :keyword ");
                qctx.setString("keyword", "%" + newKey + "%");
            }else{
                qctx.addAndWhere("photo.title LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }
        }
    }
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx){
        if(this.orderType.equals(0)){
            qctx.addOrder("cp.channelPhotoId DESC");    	
        }
    }
}
