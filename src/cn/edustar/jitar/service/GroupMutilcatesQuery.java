package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

/**
 * 课题组中的文章 资源 图片 视频的查询功能
 * 仿照group_mutilcates_query.py,用java来实现相同的功能
 * 
 * @author baimindong
 *
 */
public class GroupMutilcatesQuery extends BaseQuery{

	/** 分类服务 */
	private CategoryService categoryService;
	
	/** widgetId是指页面中的某个模块标示，支持同一页面多个同类模块下可以显示不同的内容 */
	public Integer widgetId = 0;
	
    /** 群组的文章资源图片视频分类的Id,这4个群组分类的Id是需要和群组Id绑定在一起使用 */
	public Integer articleCateId = null;
	public Integer resourceCateId = null;
	public Integer videoCateId = null;
	public Integer photoCateId = null;
	
	/** 支持按照群组分类的的分类名来查询  */
	public String articleCateName = null;
	public String resourceCateName = null;
	public String videoCateName = null;
	public String photoCateName = null;	
	
    /** 某一类群组 例如 课题组的类型Id [主要用于总站首页的 课题组的汇总]，需要和上面4个分类名绑定一起使用*/
	public Integer groupCateId = null;
    
    /** 群组的父一级的组Id [主要用于某个课题组的资源聚合] */
	public Integer groupPrentId = null;
	
	/** 排序*/
	public int orderType = 0;
    
    /** 查询关键字. */ 
	public String k = null;
    
	public GroupMutilcatesQuery(String selectFields) {
		super(selectFields);
	}

    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("Group", "g", "");
        if(this.videoCateId != null || this.videoCateName != null){
            qctx.addEntity("Video", "v", "");
            qctx.addEntity("GroupVideo", "gv", "gv.videoId=v.videoId and g.groupId=gv.groupId");
            if(this.widgetId != 0){
                qctx.addEntity("GroupMutil", "gm", "gm.videoCateId = gv.groupCateId");
            }
        }
        if(this.photoCateId!=null || this.photoCateName!=null){
            qctx.addEntity("Photo", "p", "");
            qctx.addEntity("GroupPhoto", "gp", "gp.photoId=p.photoId and g.groupId=gp.groupId");
            if(this.widgetId != 0){
                qctx.addEntity("GroupMutil", "gm", "gm.photoCateId = gp.groupCateId");
            }
        }
        if(this.articleCateId!=null || this.articleCateName!=null){
            qctx.addEntity("Article", "a", "");
            qctx.addEntity("GroupArticle", "ga", "ga.articleId=a.articleId and g.groupId=ga.groupId");
            if(this.widgetId != 0){
                qctx.addEntity("GroupMutil", "gm", "gm.articleCateId = ga.groupCateId");
            }
        }
        if(this.resourceCateId!=null || this.resourceCateName!=null){
            qctx.addEntity("Resource", "r", "");
            qctx.addEntity("GroupResource", "gr", "gr.resourceId=r.resourceId and g.groupId=gr.groupId");
            if(this.widgetId != 0){
                qctx.addEntity("GroupMutil", "gm", "gm.resourceCateId = gr.groupCateId");
            }
        }
    }
    
    public void applyWhereCondition(QueryContext qctx) {
        if(this.k != null  && this.k.length()>0 ){
            String newKey = this.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]");
            if(this.articleCateId!=null){
                qctx.addAndWhere("a.title LIKE :likeKey");
                qctx.setString("likeKey", "%" + newKey + "%");
            }else if(this.resourceCateId!=null){
                qctx.addAndWhere("r.title LIKE :likeKey");
                qctx.setString("likeKey", "%" + newKey + "%");
            }
            else if(this.videoCateId!=null){
                qctx.addAndWhere("v.title LIKE :likeKey");
                qctx.setString("likeKey", "%" + newKey + "%");
            }
            else if(this.photoCateId!=null){
                qctx.addAndWhere("p.title LIKE :likeKey");
                qctx.setString("likeKey", "%" + newKey + "%");
            }
        }
        if(this.articleCateId!=null){
            //#只查询分类自己的
            //#qctx.addAndWhere("ga.groupCateId = :cateId")
            //#qctx.setInteger("cateId", this.articleCateId)
            //#查询包含子孙分类的
            //#print "this.articleCateId="+str(this.articleCateId)
        	List<Integer> list=this.categoryService.getCategoryIds(this.articleCateId);
            String cateIds = ListToString(list);
            qctx.addAndWhere("ga.groupCateId IN (" + cateIds +")");
        }else if(this.resourceCateId!=null){
        	/*
            #只查询分类自己的
            #qctx.addAndWhere("gr.groupCateId = :cateId")
            #qctx.setInteger("cateId", this.resourceCateId)
            #查询包含子孙分类的
            #print "this.resourceCateId="+str(this.resourceCateId)
            */
        	List<Integer> list=this.categoryService.getCategoryIds(this.resourceCateId);
        	String cateIds = ListToString(list);
            qctx.addAndWhere("gr.groupCateId IN (" + cateIds +")");
        }else if(this.videoCateId!=null){
        	/*
            #只查询分类自己的
            #qctx.addAndWhere("gv.groupCateId = :cateId")
            #qctx.setInteger("cateId", this.videoCateId)
            #查询包含子孙分类的
            #print "this.videoCateId="+str(this.videoCateId)
            */
        	List<Integer> list=this.categoryService.getCategoryIds(this.videoCateId);
        	String cateIds = ListToString(list);
            qctx.addAndWhere("gv.groupCateId IN (" + cateIds +")");
        }else if(this.photoCateId!=null){
        	/*
            #只查询分类自己的
            #qctx.addAndWhere("gp.groupCateId = :cateId")
            #qctx.setInteger("cateId", this.photoCateId)
            #查询包含子孙分类的
            #print "this.photoCateId="+str(this.photoCateId)
            */
        	List<Integer>  list=this.categoryService.getCategoryIds(this.photoCateId);
        	String cateIds = ListToString(list);
            qctx.addAndWhere("gp.groupCateId IN (" + cateIds +")");
        }else if(this.articleCateName!=null){
            //#qctx.addAndWhere("gm.articleCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
            qctx.addAndWhere("ga.groupCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)");
            qctx.setString("catename", this.articleCateName);
            if(this.groupCateId!=null){
                qctx.addAndWhere("ga.groupId IN (SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE categoryId=:categoryId )");
                qctx.setInteger("categoryId", this.groupCateId);
            }
        }else if(this.resourceCateName!=null){
            //#qctx.addAndWhere("gm.resourceCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
            qctx.addAndWhere("gr.groupCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)");
            qctx.setString("catename", this.resourceCateName);
            if(this.groupCateId!=null){
                qctx.addAndWhere("gr.groupId IN (SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE categoryId=:categoryId )");
                qctx.setInteger("categoryId", this.groupCateId);
            }
        }else if(this.videoCateName!=null){
            //#qctx.addAndWhere("gm.videoCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
            qctx.addAndWhere("gv.groupCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)");
            qctx.setString("catename", this.videoCateName);
            if(this.groupCateId!=null){
                qctx.addAndWhere("gv.groupId IN (SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE categoryId=:categoryId )");
                qctx.setInteger("categoryId", this.groupCateId);
            }
        }else if(this.photoCateName!=null){
            //#qctx.addAndWhere("gm.photoCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)");
            qctx.addAndWhere("gp.groupCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)");
            qctx.setString("catename", this.photoCateName);
            if(this.groupCateId!=null){
                qctx.addAndWhere("gp.groupId IN (SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE categoryId=:categoryId )");
                qctx.setInteger("categoryId", this.groupCateId);
            }
        }
        if(this.groupPrentId!=null){
            qctx.addAndWhere("g.groupId=:grp_Id Or g.groupId IN (SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE parentId=:parentId )");
            qctx.setInteger("grp_Id", this.groupPrentId);
            qctx.setInteger("parentId", this.groupPrentId);
        }else if(this.widgetId != 0){
            qctx.addAndWhere("gm.widgetId = :widgetId");
            qctx.setInteger("widgetId", this.widgetId);
        }
    }
    
    public void applyOrderCondition(QueryContext qctx){
        if(this.orderType == 0){
            if(this.articleCateId!=null || this.articleCateName!=null){
                qctx.addOrder("g.groupId DESC,a.articleId DESC");
            }else if(this.resourceCateId!=null || this.resourceCateName!=null){
                qctx.addOrder("g.groupId DESC,r.resourceId DESC");
            }else if(this.videoCateId!=null || this.videoCateName!=null){
                qctx.addOrder("g.groupId DESC,v.videoId DESC");
            }else if(this.photoCateId!=null || this.photoCateName!=null){
                qctx.addOrder("g.groupId DESC,p.photoId DESC");
            }else{
                qctx.addOrder("g.groupId DESC");
            }
        }
    }
    /**
     * 转换为字符串，项之间用逗号分隔
     * @param list
     * @return
     */
    private String ListToString(List<Integer> list){
    	String cateIds = "";
        for(int i=0;i<list.size();i++){
            if(cateIds.equals("")){
                cateIds=""+list.get(i);
            }else{
                cateIds=cateIds+","+list.get(i);
            }
        }
        return cateIds;
    }
	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}	
}
