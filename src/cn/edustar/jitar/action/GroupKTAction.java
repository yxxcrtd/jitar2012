package cn.edustar.jitar.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.GroupMutilcatesQuery;
import cn.edustar.jitar.service.GroupQuery;
import cn.edustar.jitar.service.PlacardQuery;
import cn.edustar.jitar.service.PlacardService;
import cn.edustar.jitar.service.SubjectService;

/**
 * 课题组
 * @author baimindong
 *
 */
public class GroupKTAction extends BaseGroupAction {

	private static final long serialVersionUID = -5307820623127038430L;

	/** 分类服务 */
	private CategoryService cate_svc;
	/** 公告服务 */
	private PlacardService placardService;
	/** 学科服务 */
	private SubjectService subjectService;
	
	@Override
	protected String execute(String cmd) throws Exception {
		/**得到课题组的分类Id*/
		int categoryId = getKtCateId();
		setRequestAttribute("head_nav", "ktgroups");
		String act = param_util.safeGetStringParam("act");
		String type = param_util.safeGetStringParam("type");
		if(act.equals("all")){
			if(type.equals("placard")){
				get_group_placard_page_list(categoryId);
				return "list_placard";
			}else if(type.equals("article")){
				get_group_article_page_list(categoryId);
				return "list_article";
			}else if(type.equals("resource")){
				get_group_resource_page_list(categoryId);
				return "list_resource";
			}else if(type.equals("photo")){
				get_group_photo_page_list(categoryId);
				return "list_photo";
			}else if(type.equals("video")){
				get_group_video_page_list(categoryId);
				return "list_video";
			}
		}
	    //# 学科
	    this.get_subject_list();
	    //# 学段分类.
	    this.get_grade_list();
	    //#课题公告
	    this.get_group_placard_list(categoryId);
	    //#课题成果 文章 资源 图片 视频
	    this.get_group_article_list(categoryId);
	    this.get_group_resource_list(categoryId);
	    this.get_group_photo_list(categoryId);
	    this.get_group_video_list(categoryId);
	    //# 查询课题组.
	    this.query_group_list(categoryId);		
		return "list_success";
	}
	
    private void get_grade_list() {
    	setRequestAttribute("grade_list", this.subjectService.getGradeList());
    }

    private void get_subject_list() {
    	setRequestAttribute("subject_list", this.subjectService.getMetaSubjectList());
    }	
  
    /**
     * 查询课题组.
     * @param groupCateId
     */
    @SuppressWarnings("rawtypes")
	private void query_group_list(int groupCateId){
    	GroupQuery  qry = new GroupQuery(" g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.userCount," +  
                "g.visitCount, g.articleCount, g.topicCount, g.resourceCount, g.groupIntroduce, " +
                "g.groupTags, subj.subjectName, grad.gradeName, sc.name as scName ");
                
      qry.subjectId = param_util.getIntParamZeroAsNull("subjectId");
      qry.categoryId = groupCateId;
      qry.gradeId = param_util.getIntParamZeroAsNull("gradeId");
      qry.searchtype = param_util.getStringParam("searchtype");
      qry.kk = param_util.getStringParam("k");
      qry.parentId = null;		//取得全部课题组，不分主课题，子课题
      
	Pager pager = super.getCurrentPager();
	pager.setItemNameAndUnit("群组", "个");
	//pager.setPageSize(10);
    pager.setTotalRows(qry.count());
      
    List group_list = (List)qry.query_map(pager);
    setRequestAttribute("group_list", group_list);
    List ggg = new ArrayList();
    if(group_list != null){
	    for(int i=0;i<group_list.size();i++){
	    	Object ogroup = group_list.get(i);
	    	HashMap agroup =(HashMap)ogroup;
	    	Integer gid = Integer.parseInt(agroup.get("groupId").toString());  
	        GroupQuery subqry = new GroupQuery(" g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.userCount,"+
	                "g.visitCount, g.articleCount, g.topicCount, g.resourceCount, g.groupIntroduce,"+
	                "g.groupTags ");
		    subqry.parentId = gid;
		    List subgroup_list = (List)subqry.query_map();
		    ggg.add(subgroup_list);
	    }
    }
      setRequestAttribute("subgroups", ggg); 
      setRequestAttribute("pager", pager);
      setRequestAttribute("subjectId", qry.subjectId);
      setRequestAttribute("categoryId", qry.categoryId);
      setRequestAttribute("gradeId", qry.gradeId);
      setRequestAttribute("searchtype", qry.searchtype);
      setRequestAttribute("k", qry.kk);
    }
	/**
	 * 成果视频(分页显示)
	 * @param groupCateId
	 */
	private void get_group_video_page_list(int groupCateId){
		GroupMutilcatesQuery  qry = new GroupMutilcatesQuery(" v.videoId, v.title, v.tags, v.userId,v.href,v.flvHref,v.flvThumbNailHref,v.createDate,g.groupTitle,g.groupName ");
	    qry.videoCateName="成果";
	    qry.groupCateId=groupCateId;
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("视频", "个");
		pager.setPageSize(20);
	    pager.setTotalRows(qry.count());
	    Object video_list = qry.query_map(pager);
	    setRequestAttribute("pager", pager);
	    setRequestAttribute("video_list", video_list);		
	}
	/**
	 * 成果视频(前4条)
	 * @param groupCateId
	 */
	private void get_group_video_list(int groupCateId){
		GroupMutilcatesQuery  qry = new GroupMutilcatesQuery(" v.videoId, v.title, v.tags, v.userId,v.href,v.flvHref,v.flvThumbNailHref,v.createDate,g.groupTitle,g.groupName ");
	    qry.videoCateName="成果";
	    qry.groupCateId=groupCateId;
	    List video_list = qry.query_map(4);
	    setRequestAttribute("video_list", video_list);		
	}
	
	/**
	 * 成果照片(分页显示)
	 * @param groupCateId
	 */
	private void get_group_photo_page_list(int groupCateId){
		GroupMutilcatesQuery qry = new GroupMutilcatesQuery("p.photoId, p.title, p.tags, p.userId, p.createDate,p.href,g.groupTitle,g.groupName ");
	    qry.photoCateName="成果";
	    qry.groupCateId=groupCateId;
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("照片", "张");
		pager.setPageSize(20);
	    pager.setTotalRows(qry.count());
	    Object photo_list = qry.query_map(pager);
	    setRequestAttribute("pager", pager);
	    setRequestAttribute("photo_list", photo_list);		
	}
	/**
	 * 成果照片(前4条)
	 * @param groupCateId
	 */
	private void get_group_photo_list(int groupCateId){
		GroupMutilcatesQuery qry = new GroupMutilcatesQuery("p.photoId, p.title, p.tags, p.userId, p.createDate,p.href,g.groupTitle,g.groupName ");
	    qry.photoCateName="成果";
	    qry.groupCateId=groupCateId;
	    List photo_list = qry.query_map(4);
	    setRequestAttribute("photo_list", photo_list);
	}	
	
	/**
	 * 资源课题成果(分页显示)
	 * @param groupCateId
	 */
	private void get_group_resource_page_list(int groupCateId){
		GroupMutilcatesQuery qry = new GroupMutilcatesQuery(" r.resourceId, r.title,r.userId,r.downloadCount,r.createDate,r.href,g.groupTitle,g.groupName ");
	    qry.resourceCateName="成果";
	    qry.groupCateId=groupCateId;
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("资源", "个");
		pager.setPageSize(20);
	    pager.setTotalRows(qry.count());
	    Object resource_list = qry.query_map(pager);
	    setRequestAttribute("pager", pager);
	    setRequestAttribute("resource_list", resource_list);		
	}
	/**
	 * 资源课题成果(前5条)
	 * @param groupCateId
	 */
	private void get_group_resource_list(int groupCateId){
		GroupMutilcatesQuery qry = new GroupMutilcatesQuery(" r.resourceId, r.title,r.userId,r.downloadCount,r.createDate,r.href,g.groupTitle,g.groupName ");
	    qry.resourceCateName="成果";
	    qry.groupCateId=groupCateId;
	    List resource_list = qry.query_map(5);
	    setRequestAttribute("resource_list", resource_list);
	}	
	
	/**
	 * 文章成果(分页显示)
	 */
	private void get_group_article_page_list(int groupCateId){
		GroupMutilcatesQuery qry = new GroupMutilcatesQuery("a.articleId, a.title,a.userId,a.typeState,a.createDate,g.groupTitle,g.groupName ");
	    qry.articleCateName="成果";
	    qry.groupCateId=groupCateId;
	    
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("文章", "篇");
		pager.setPageSize(20);
	    pager.setTotalRows(qry.count());
	    
	    Object article_list = qry.query_map(pager);
	    setRequestAttribute("pager", pager);
	    setRequestAttribute("article_list", article_list);
	}
	/**
	 * 文章成果(前5条)
	 * @param groupCateId
	 */
	private void get_group_article_list(int groupCateId){
		GroupMutilcatesQuery qry = new GroupMutilcatesQuery("a.articleId, a.title,a.userId,a.typeState,a.createDate,g.groupTitle,g.groupName ");
	    qry.articleCateName="成果";
	    qry.groupCateId=groupCateId;
		List article_list = qry.query_map(5);
		setRequestAttribute("article_list", article_list);
	}
	/**
	 * 课题公告(分页显示)
	 * @param groupCateId
	 */
	private void get_group_placard_page_list(int groupCateId){
		/*
		PlacardQueryParamEx param = new PlacardQueryParamEx();
		param.objType = ObjectType.OBJECT_TYPE_GROUP;
		param.objId = null;
		param.setGroupCateId(groupCateId);
		param.setSelect_Clause("SELECT pla.id, pla.title, pla.createDate, pla.userId,g.groupTitle,g.groupName ");
		*/
		PlacardQuery qry = new PlacardQuery("pld.id, pld.title, pld.createDate, pld.userId,g.groupTitle,g.groupName");
	    qry.objType = ObjectType.OBJECT_TYPE_GROUP.getTypeId();
	    qry.objId = null;
	    qry.groupCateId=groupCateId;

		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("公告", "个");
		pager.setPageSize(20);
		/*
		List placard_list = this.placardService.getPlacardList(param, pager);
		setRequestAttribute("placard_list", placard_list);
		setRequestAttribute("pager", pager);
		*/
		pager.setTotalRows(qry.count());
	    Object placard_list = qry.query_map(pager);
	    setRequestAttribute("pager", pager);
	    setRequestAttribute("placard_list", placard_list);
	}
	/**
	 * 课题公告(前5条)
	 * @param groupCateId
	 */
	private void get_group_placard_list(int groupCateId){
		/*
		PlacardQueryParamEx param = new PlacardQueryParamEx();
		param.objType = ObjectType.OBJECT_TYPE_GROUP;
		param.objId = null;
		param.setGroupCateId(groupCateId);
		param.setSelect_Clause("SELECT pla.id, pla.title, pla.createDate, pla.userId,g.groupTitle,g.groupName ");
		param.count = 5;
		List placard_list = this.placardService.getPlacardList(param, null);
		setRequestAttribute("placard_list", placard_list);
		*/
		PlacardQuery qry = new PlacardQuery("pld.id, pld.title, pld.createDate, pld.userId,g.groupTitle,g.groupName");
	    qry.objType = ObjectType.OBJECT_TYPE_GROUP.getTypeId();
	    qry.objId = null;
	    qry.groupCateId=groupCateId;
	    List placard_list = qry.query_map(5);
	    request.setAttribute("placard_list", placard_list);		
	}
	/**
	 * 得到课题组的分类Id
	 * @return
	 */
	private int getKtCateId(){
		String ktuuid = CategoryService.GROUP_CATEGORY_GUID_KTYJ;
		Category ktcate = this.cate_svc.getCategory(ktuuid);
		if(null != ktcate){
		int categoryId = ktcate.getId();
			return categoryId;
		}else{
			return 0;
		}
	}
	
	public SubjectService getSubjectService(){
		return this.subjectService;
	}
	
	public void setSubjectService(SubjectService subjectService){
		this.subjectService=subjectService;
	}
	
	public CategoryService getCategoryService() {
		return cate_svc;
	}

	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}

	public PlacardService getPlacardService() {
		return placardService;
	}

	public void setPlacardService(PlacardService placardService) {
		this.placardService = placardService;
	}
}
