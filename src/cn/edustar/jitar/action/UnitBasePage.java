package cn.edustar.jitar.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.SiteNav;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.CacheProvider;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.SiteNavService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UnitTypeService;

/**
 * 单位机构 (unit_page.py)
 * 
 * /jitar2/WebContent/WEB-INF/jython/unit_page.py
 * @author baimindong
 *
 */
public abstract class UnitBasePage extends ManageBaseAction {
	private static final long serialVersionUID = 2242058722036232903L;
	protected UnitService unitService;  
    protected AccessControlService accessControlService;
    protected SubjectService subjectService;
    protected CategoryService categoryService;	
    protected CacheProvider cacheProvider;
    protected SiteNavService siteNavService;
    protected UnitTypeService unitTypeService;
    
    protected Integer unitId = null;
    protected Unit unit = null;
    protected String unitName;
    protected String unitRootUrl = null;
    
	@Override
	protected String beforeExecute() throws Exception{
		this.unitId = param_util.safeGetIntParam("unitId");
		this.unitName =(String) request.getAttribute("unitName");
        if(this.unitId != 0){
            this.unit = this.unitService.getUnitById(this.unitId);
        }else if(this.unitName != null && this.unitName.length() > 0){
        	this.unit = this.unitService.getUnitByName(this.unitName);
        }
		return super.beforeExecute();
	}
	
	protected Unit getUnit(){
        if(this.unitName != null && this.unitName.length() > 0){
            this.unit = this.unitService.getUnitByName(this.unitName);
        }
        if(this.unitId != 0){
            this.unit = this.unitService.getUnitById(this.unitId);
        }
        if(this.unit == null){
            return null;
        }
        this.unitId = this.unit.getUnitId();
        this.unitName = this.unit.getUnitName();
        
        if(this.unit != null){
            this.unitRootUrl =(String) request.getAttribute("UnitRootUrl");
            if(this.unitRootUrl == null){
                String configUnitSiteRoot = request.getSession().getServletContext().getInitParameter("unitUrlPattern");
                if(configUnitSiteRoot == null || configUnitSiteRoot.length() == 0){
                    this.unitRootUrl = this.getCurrentSiteUrl(request) + "d/" + this.unit.getUnitName() + "/";
                }else{
                    this.unitRootUrl = configUnitSiteRoot.replaceAll("\\{unitName\\}", this.unit.getUnitName());
                }
            }
            request.setAttribute("UnitRootUrl", this.unitRootUrl);
            CacheService cache = cacheProvider.getCache("sitenav");
            String cache_k = "unit_nav_" + this.unit.getUnitId();
            List<SiteNav>  unitSiteNavList =(List<SiteNav> ) cache.get(cache_k);
            if(unitSiteNavList == null){
            	unitSiteNavList = siteNavService.getAllSiteNav(false, 1, this.unit.getUnitId());
                cache.put(cache_k, unitSiteNavList);
            }
            request.setAttribute("UnitSiteNavList", unitSiteNavList);
            request.setAttribute("canManege", this.canManege());
        }
        return this.unit;
	}
    protected boolean canManege(){
    	if(this.getLoginUser() == null){
            return false;
        }
        return (this.isUnitAdmin() || this.isContentAdmin() || this.isUserAdmin());
    }
    protected boolean isUnitAdmin(){
        if(this.unitId == 0){
            return false;
        }
        if(this.getLoginUser() == null){return false;}
        if(this.accessControlService.isSystemAdmin(this.getLoginUser())){
            return true;
        }
        
        boolean unitSystemAdmin = this.accessControlService.userIsUnitSystemAdmin(this.getLoginUser(), this.unit);
        return unitSystemAdmin;
    }
    protected boolean isContentAdmin(){
        if(this.unitId == 0){
            return false;
        }
        if(this.getLoginUser() == null){return false;}
        if(this.accessControlService.isSystemAdmin(this.getLoginUser())){
            return true;
        }
       
        if(this.isUnitAdmin() == true){
            return true;
        }
        boolean unitContentAdmin = this.accessControlService.userIsUnitContentAdmin(this.getLoginUser(), this.unit);
        return unitContentAdmin;
    }
    protected boolean isUserAdmin(){
        if(this.unitId == 0){
            return false;
        }
        if(this.getLoginUser() == null){return false;}
        if(this.accessControlService.isSystemAdmin(this.getLoginUser())){
            return true;
        }
        if(this.isUnitAdmin() == true){
            return true;
        }
        boolean unitUserAdmin = this.accessControlService.userIsUnitUserAdmin(this.getLoginUser(), this.unit);
        return unitUserAdmin;
    }
    protected void clear_cache(){
        if(this.unit != null){
        	CacheService cache = cacheProvider.getCache("unit");
            if(cache != null){                    
                List<String> cache_list = cache.getAllKeys();
                String cache_key_head = "unit" + this.unit.getUnitId();
                for(String c : cache_list){
                    if(c.split("_")[0] == cache_key_head){
                        cache.remove(c);
                    }
                }
            }
        }
    }                    
    protected String getCurrentSiteUrl(HttpServletRequest request){
        String root = request.getScheme() + "://" + request.getServerName();
        if(request.getServerPort() != 80){
            root = root + ":" + request.getServerPort();
        }
        root = root + request.getContextPath() + "/";
        return root;
    }
    /*TODO:检查这里，使用了基类的方法
    protected void putGradeList(){
    	List<Grade>  grade_list = this.subjectService.getGradeList();
        request.setAttribute("grade_list", grade_list);
    }*
    
    /*TODO:检查这里，使用了基类的方法
    protected void putSubjectList(){
    	List<MetaSubject> subject_list = this.subjectService.getMetaSubjectList();
        request.setAttribute("subject_list", subject_list);
    }
    */
    
    protected void putResouceCateList(){
    	CategoryTreeModel res_cate = this.categoryService.getCategoryTree("resource");
        request.setAttribute("res_cate", res_cate)	;
    }
	public void setAccessControlService(AccessControlService accessControlService){
		this.accessControlService = accessControlService;
	}
	public AccessControlService getAccessControlService(){
		return this.accessControlService ;
	}
	public void setSubjectService(SubjectService subjectService){
		this.subjectService = subjectService;
	}
	public SubjectService getSubjectService(){
		return this.subjectService;
	}
	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}	
	
	public void setCacheProvider(CacheProvider cacheProvider){
		this.cacheProvider = cacheProvider;
	}
	public CacheProvider getCacheProvider() {
		return cacheProvider;
	}	
	
	public void setSiteNavService(SiteNavService siteNavService){
		this.siteNavService = siteNavService;
	}
	public SiteNavService getSiteNavService() {
		return siteNavService;
	}	
	
	public void setUnitTypeService(UnitTypeService unitTypeService){
		this.unitTypeService = unitTypeService;
	}
	public UnitTypeService getUnitTypeService(){
		return this.unitTypeService;
	}
}
