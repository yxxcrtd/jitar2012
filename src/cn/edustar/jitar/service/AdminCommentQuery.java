package cn.edustar.jitar.service;

import cn.edustar.jitar.data.QueryContext;

/**
 * 资源带高级过滤条件的搜索.
 * 
 * AdminCommentQuery(showResource.py)
 * 
 * @author baimindong
 *
 */
public class AdminCommentQuery extends CommentQuery {

    public String kk = null ; //  # 查询关键字.
    
	public AdminCommentQuery(String selectFields) {
		super(selectFields);
		f = "0";
	}
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
    	super.applyWhereCondition(qctx);
        if(this.kk != null && this.kk.length() > 0){
            _applyKeywordFilter(qctx);    	
        }
    }
    
    private void _applyKeywordFilter(QueryContext qctx){
        String newKey = kk.replace("'", "''").replace("%", "[%]").replace("_", "[_]").replace("[", "[[]");
        if(f.equals("title")){// # 用资源标题、标签过滤.
            qctx.addAndWhere("cmt.title LIKE :kk");
            qctx.setString("kk", "%" + newKey + "%");
        }else if(f.equals("content")){ 
            qctx.addAndWhere("cmt.content LIKE :kk");
            qctx.setString("kk", "%" + newKey + "%");
        }else if(f.equals("uname")){// # 用户名 (maybe id).
        	int userId = Integer.parseInt(newKey);
        	if(userId > 0){
                qctx.addAndWhere("cmt.userId = :kk");
                qctx.setInteger("kk", userId);
        	}else{
                qctx.addAndWhere("u.loginName = :kk OR u.trueName = :kk");
                qctx.setString("kk", newKey);
        	}
        }
    }
}
