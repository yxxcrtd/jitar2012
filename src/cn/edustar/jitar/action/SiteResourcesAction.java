package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.service.CacheProvider;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ResourceQuery;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserQuery;
import cn.edustar.jitar.util.HtmlPager;

/**
 * ResourcePyAction
 * resource.action是展示的，还有一个resource.action是管理的
 * ResourcePyAction 是 /resource.py中的功能
 * 
 * @author baimindong
 *
 */
public class SiteResourcesAction  extends ManageBaseAction {
	
	private static final long serialVersionUID = -8885302411821428986L;

	/** 学科服务 */
	private SubjectService sbj_svc;
	
	/** 单位服务 */
	private UnitService unitService;
	
	/** 缓存管理器. */
	private CacheProvider cacheProvider;
	
	/** 分类服务 */
	private CategoryService categoryService;
	
	private String k = null;
	@Override
	protected String execute(String cmd) throws Exception {
	    //this.provider = JitarContext.getCurrentJitarContext().getCacheProvider();
	    k = params.safeGetStringParam("k");
	    if(k.equals("")){
	        k = null;
	    }
	    String type = param_util.safeGetStringParam("type");
        if(type.length() == 0){type = "new";}
        setRequestAttribute("type", type);
        
	    setRequestAttribute("k", k);
	    
 
	    if(cmd.equals("query")){
	      get_resource_querylist();
	      return "query";
	    }	   
	    CacheService cache = this.cacheProvider.getCache("main");
	   
	    
	    if(cmd.equals("ajax")){
	    	get_resource_list(rootUnit);
		      return "ajax";
		    }
	    
	    setRequestAttribute("outHtml", getGradeSubjectTreeHtml(cache));
	    
	    //# 学科, 区县, 资源类型.
	    get_subject_list();
	    get_res_cate();
	    
	    //# 学段分类.
	    get_grade_list();
	    
	    //# 用户上载排行.
	    get_upload_user_list();
	    get_resource_list(this.rootUnit);
	    get_download_resource_list();
	    
	    //# 页面导航高亮为 'resources''
	    setRequestAttribute("head_nav", "resources");
	    
	    return "list2";	
	}

	//# 资源下载排行.
	private void get_download_resource_list(){
		ResourceQuery qry = new ResourceQuery(" r.resourceId, r.href, r.title, r.downloadCount ");		
	    qry.subjectId = param_util.getIntParamZeroAsNull("subjectId");
	    qry.sysCateId = param_util.getIntParamZeroAsNull("categoryId");
	    qry.gradeId = param_util.getIntParamZeroAsNull("gradeId");
	    qry.k = k;
	    qry.orderType = 4;
	    List download_resource_list = qry.query_map(10); 
   		setRequestAttribute("download_resource_list", download_resource_list);
	    setRequestAttribute("subjectId", qry.subjectId);
	    setRequestAttribute("categoryId", qry.sysCateId);
	    setRequestAttribute("gradeId", qry.gradeId);
	}    
	//# 资源查询.
	private void get_resource_list(Unit unit){
		ResourceQuery qry = new ResourceQuery(" r.resourceId, r.href, r.title, r.fsize, r.createDate, u.loginName, u.nickName, r.subjectId as subjectId, grad.gradeName, sc.name as scName ");

	    String type = param_util.getStringParam("type");
	    if(type == null || type.length() == 0){ type = "new";}
	    String list_type = "";
	    if(type.equals("hot")){
	      qry.orderType = ResourceQuery.ORDER_TYPE_HOT_DESC;
	      qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + unit.getUnitId() + "/%' ";
	      list_type = "最高人气";
	    }else if(type.equals("rcmd")){
	      //#qry.rcmdState = True
	      qry.custormAndWhereClause = " r.rcmdPathInfo Like '%/" + unit.getUnitId() + "/%' And r.approvedPathInfo Like '%/" + unit.getUnitId() + "/%' ";
	      list_type = "编辑推荐";
	    }else if(type.equals("cmt")){
	      qry.orderType = ResourceQuery.ORDER_TYPE_COMMENTCOUNT_DESC;
	      qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + unit.getUnitId() + "/%' ";
	      list_type = "评论最多";
	    }else{
	      type = "new";
	      qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + unit.getUnitId() + "/%' ";
	      list_type = "最新资源" ;
	    }
	    setRequestAttribute("type", type);
	    setRequestAttribute("list_type", list_type);
	    
	    qry.gradelevel = param_util.getIntParamZeroAsNull("level");
	    qry.subjectId = param_util.getIntParamZeroAsNull("subjectId");
	    qry.sysCateId = param_util.getIntParamZeroAsNull("categoryId");
	    qry.gradeId = param_util.getIntParamZeroAsNull("gradeId");
	    qry.k = k;
	    
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("资源", "个");
		pager.setPageSize(20);
	    pager.setTotalRows(qry.count());
	    
	    List resource_list = (List)qry.query_map(pager);
	    
	    setRequestAttribute("resource_list", resource_list);
	    setRequestAttribute("pager", pager);
	    setRequestAttribute("subjectId", qry.subjectId);
	    setRequestAttribute("categoryId", qry.sysCateId);   
	    setRequestAttribute("gradeId", qry.gradeId);
	    
        String html = HtmlPager.render(pager.getTotalPages(), 3, pager.getCurrentPage());
        request.setAttribute("HtmlPager", html);
	    
	}
	//# 资源上载排行.
	private void get_upload_user_list(){
		UserQuery qry = new UserQuery(" u.resourceCount, u.loginName,  u.trueName,  u.nickName ");
		qry.custormAndWhere = " u.resourceCount > 0 ";
	    qry.orderType = 4; //#UserQuery.ORDER_TYPE_RESOURCE_COUNT_DESC
	    qry.userStatus = 0;
	    List upload_user_list = qry.query_map(10); 
	    setRequestAttribute("upload_user_list", upload_user_list);	
	}
	
	//# 资源分类.
	private void get_res_cate(){
		CategoryTreeModel res_cate = this.categoryService.getCategoryTree("resource");
	    setRequestAttribute("res_cate", res_cate);
	}
    private void get_grade_list() {
    	setRequestAttribute("grade_list", this.sbj_svc.getGradeList());
    }

    private void get_subject_list() {
    	setRequestAttribute("subject_list", this.sbj_svc.getMetaSubjectList());
    }	
    
	//# 资源查询.
	private void get_resource_querylist(){
		ResourceQuery qry = new ResourceQuery(" r.resourceId, r.href, r.title, r.fsize, r.createDate,r.auditState ");
	    String resId = param_util.getStringParam("id");
	    qry.resourceIds = resId;
	    qry.auditState=null;
	    List resource_list = qry.query_map();
	    setRequestAttribute("resource_list", resource_list);
	}
	
	public SubjectService getSubjectService() {
		return sbj_svc;
	}
	public void setSubjectService(SubjectService subjectService) {
		this.sbj_svc = subjectService;
	}
	
	public UnitService getUnitService() {
		return unitService;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

    public void setCacheProvider(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }		
}
