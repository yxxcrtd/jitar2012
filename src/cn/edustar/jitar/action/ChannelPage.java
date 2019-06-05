package cn.edustar.jitar.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.ChannelModule;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.SiteNav;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ChannelArticleQuery;
import cn.edustar.jitar.service.ChannelGroupQuery;
import cn.edustar.jitar.service.ChannelPageService;
import cn.edustar.jitar.service.ChannelPhotoQuery;
import cn.edustar.jitar.service.ChannelResourceQuery;
import cn.edustar.jitar.service.ChannelUserQuery;
import cn.edustar.jitar.service.ChannelVideoQuery;
import cn.edustar.jitar.service.PlacardQuery;
import cn.edustar.jitar.service.SiteNavService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.TemplateProcessor;
import cn.edustar.jitar.service.UnitService;

/**
 * 频道浏览
 * @author baimindong
 *
 */
public abstract class ChannelPage extends ManageBaseAction {
	private static final long serialVersionUID = -5989715145152051617L;
	protected ChannelPageService channelPageService;
	protected TemplateProcessor templateProcessor;
	protected CategoryService categoryService;
	protected UnitService unitService;
	protected SubjectService subjectService;
	protected SiteNavService siteNavService;
	protected AccessControlService accessControlService;
	protected Integer channelId;
	protected Channel channel;
	protected String skin = "template1";
	
	@Override
	protected String beforeExecute() throws Exception{
        this.channelId = param_util.safeGetIntParam("channelId");
        this.channel = channelPageService.getChannel(this.channelId);    
        if (this.channel != null){
            if(this.channel.getSkin() != null && this.channel.getSkin().length() > 0){
                this.skin = this.channel.getSkin();
            }
        }
		return super.beforeExecute();
	}
	
	protected String GenerateSubjectNavContent(ChannelModule mdl){
    	List<Integer> mGradeId = subjectService.getGradeIdList();
        List MetaGrade = new ArrayList();
        List metaSubject = new ArrayList();
        for(Integer grade : mGradeId){
        	Grade mGrade = subjectService.getGrade(grade);
            MetaGrade.add(mGrade);
            List<Subject> subj = subjectService.getSubjectByGradeId(grade);
            List m = new ArrayList();
            if(subj != null && subj.size() > 0){ 
                for(int sj=0; sj < subj.size(); sj ++){
                    m.add(subj.get(sj).getMetaSubject());
            	}
                //metaSubject.add(new SON {"gradeName" : mGrade.gradeName, "gradeId" : grade, "metaSubject" : m });
    	    	JSONObject jsonObj = new JSONObject();
    	    	jsonObj.put("gradeName", mGrade.getGradeName());
    	    	jsonObj.put("gradeId", grade);
    	    	jsonObj.put("metaSubject", m);
    	    	metaSubject.add(jsonObj);
            }
        }
        HashMap map = new HashMap();
        map.put("metaGrade", MetaGrade);
        map.put("meta_Grade", MetaGrade);
        map.put("SubjectNav", metaSubject);
        String content = null;
        if(mdl.getTemplate() == null){
            content = templateProcessor.processTemplate(map, "/WEB-INF/ftl/site_subject_nav.ftl", "utf-8");
        }else{
            content = templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
	protected String GenerateChannelNavContent(ChannelModule mdl, String strCurrentNav){
		if(this.channel == null) {
			return "";
		}
		List<SiteNav> SiteNavList = siteNavService.getAllSiteNav(false, 3, this.channel.getChannelId());
		HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("SiteNavList", SiteNavList);
        map.put("loginUser", getLoginUser());
        map.put("head_nav", strCurrentNav);    
        
        //# 判断权限        
        String AdminType = GetAdminType();
        map.put("AdminType", AdminType);
        String content = "";
        if(mdl.getTemplate() == null){
            request.setAttribute("ru", request.getScheme() + ")){//" + request.getServerName() + ")){" + request.getServerPort() + request.getContextPath());
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/ChannelNav.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;    
	}
    protected String GenerateSiteNavContent(ChannelModule mdl){
    	List<SiteNav> SiteNavList = siteNavService.getAllSiteNav(false, 0, 0);
        HashMap map = new HashMap();
        map.put("SiteNavList", SiteNavList);
        String content = "";
        if(mdl.getTemplate() == null ){
            content = templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/SiteNav.ftl", "utf-8");
        }else{
            content = templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GenerateGroupListContent(ChannelModule mdl){
        Integer count = mdl.getShowCount();
        if(count == null){count = 10;}
        ChannelGroupQuery qry = new ChannelGroupQuery(" g.groupId, g.groupName, g.groupTitle, g.groupIcon, g.groupIntroduce ");
        qry.channelId = this.channel.getChannelId();
        List group_list = qry.query_map(count);
        HashMap map = new HashMap();
        map.put("group_list", group_list);
        String content= "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/GroupList.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GenerateArticleListContent(ChannelModule mdl){
        Integer count = mdl.getShowCount();
        if(count == null){count = 10;}
        ChannelArticleQuery qry = new ChannelArticleQuery(" ca.articleId, ca.title, ca.createDate,ca.typeState, ca.userId, ca.loginName, ca.userTrueName ");
        qry.setRequest(request);
        qry.channelId = this.channel.getChannelId();
        List article_list = qry.query_map(count);
        HashMap map = new HashMap();
        map.put("NewArticleList", article_list);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/ArticleList.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    
    protected String GenerateArticleCategory(ChannelModule mdl){        
    	CategoryTreeModel article_category = this.categoryService.getCategoryTree("channel_article_" + this.channelId);
        HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("ArticleCategory", article_category);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/ArticleCategory.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GenerateSearch(ChannelModule mdl){
        HashMap map = new HashMap();
        map.put("channel", this.channel);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/Search.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GenerateStat(ChannelModule mdl){
        HashMap map = new HashMap();
        ChannelArticleQuery qry = new ChannelArticleQuery("ca.articleId");
        qry.setRequest(request);
        qry.channelId = this.channel.getChannelId();
        int article_count = qry.count();
        map.put("article_count", article_count);
        
        ChannelResourceQuery qry2 = new ChannelResourceQuery("cr.resourceId");
        qry2.setRequest(request);
        qry2.channelId = this.channel.getChannelId();
        int resource_count = qry2.count();
        map.put("resource_count", resource_count);
        
        ChannelPhotoQuery qry3 = new ChannelPhotoQuery("cp.photoId");
        qry3.setRequest(request);
        qry3.channelId = this.channel.getChannelId();
        int photo_count = qry3.count();
        map.put("photo_count", photo_count);
        
        ChannelVideoQuery qry4 = new ChannelVideoQuery("cv.videoId");
        qry4.setRequest(request);
        qry4.channelId = this.channel.getChannelId();
        int video_count = qry4.count();
        map.put("video_count", video_count);
        
        ChannelUserQuery qry5 = new ChannelUserQuery("cu.channelUserId");
        qry5.setRequest(request);
        qry5.channelId = this.channel.getChannelId();
        int user_count = qry5.count();
        map.put("user_count", user_count);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/Stat.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GenerateUserList(ChannelModule mdl){
        Integer count = mdl.getShowCount();
        if(count == null){count = 10;}
        ChannelUserQuery qry = new ChannelUserQuery(" cu.userId, user.loginName, user.trueName, user.userIcon ");
        qry.setRequest(request);
        qry.channelId = this.channelId;
        List user_list = qry.query_map(count);
        HashMap map = new HashMap();
        map.put("user_list", user_list);
        map.put("channel", this.channel);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/UserList.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GenerateResourceCategory(ChannelModule mdl){        
    	CategoryTreeModel resource_category = this.categoryService.getCategoryTree("channel_resource_" + this.channelId);
        HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("resource_category", resource_category);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/ResourceCategory.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GenerateResourceList(ChannelModule mdl){
        Integer count = mdl.getShowCount();
        if(count == null){count = 10;}
        ChannelResourceQuery qry = new ChannelResourceQuery(" cr.resourceId, cr.userId, resource.href, resource.title, resource.fsize, resource.createDate, cr.viewCount,user.loginName, user.trueName as userTrueName ");
        qry.setRequest(request);
        qry.channelId = this.channelId;
        List resource_list = qry.query_map(count);
        HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("resource_list", resource_list);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/ResourceList.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GenerateVideoList(ChannelModule mdl){
        Integer count = mdl.getShowCount();
        if(count == null){count = 10;}
        ChannelVideoQuery qry = new ChannelVideoQuery(" cv.videoId, video.title, video.createDate, cv.userId, user.loginName, user.trueName as userTrueName, video.flvThumbNailHref");
        qry.setRequest(request);
        qry.channelId = this.channelId;
        List video_list = qry.query_map(count);
        HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("video_list", video_list);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/VideoList.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GenerateVideoCategory(ChannelModule mdl){
    	CategoryTreeModel video_category = this.categoryService.getCategoryTree("channel_video_" + this.channelId);
    	HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("video_category", video_category);
        String content ="";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/VideoCategory.ftl", "utf-8");
        }else{
        	content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GeneratePhotoList(ChannelModule mdl){
        String pw = request.getSession().getServletContext().getInitParameter("photo_width");
        String ph = request.getSession().getServletContext().getInitParameter("photo_height");
        if(pw == null || pw.length()  == 0 ){ pw = "150";}
        if(ph == null || ph.length()  == 0 ){ ph = "120";}
        request.setAttribute("pw", pw);
        request.setAttribute("ph", ph);
        ChannelPhotoQuery qry = new ChannelPhotoQuery(" cp.photoId, photo.title, photo.href, photo.createDate, user.loginName, user.trueName as userTrueName ");
        qry.setRequest(request);
        qry.channelId = this.channelId;
        Integer count = mdl.getShowCount();
        if(count == null){count = 10;}
        List photo_list = qry.query_map(count);
        HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("photo_list", photo_list);
        map.put("pw", pw);
        map.put("ph", ph);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/PhotoList.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GeneratePhotoCategory(ChannelModule mdl){        
    	CategoryTreeModel photo_category = this.categoryService.getCategoryTree("channel_photo_" + this.channelId);
    	HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("photo_category", photo_category);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/PhotoCategory.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;    
    }
    protected String GenerateBulletin(ChannelModule mdl){
    	PlacardQuery qry = new PlacardQuery(" pld.id, pld.title, pld.createDate ");
        qry.objType = 19;
        qry.objId = this.channel.getChannelId();
        Integer count = mdl.getShowCount();
        if(count == null){count = 10;}
        List placard_list = qry.query_map();
        HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("placard_list", placard_list);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/Bulletin.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GenerateLogo(ChannelModule mdl){
        HashMap map = new HashMap();
        map.put("channel", this.channel);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/Logo.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GenerateContentFromTemplateString(String strTemplate, String strCurrentNav, String css){
        if(strTemplate == null || strTemplate.isEmpty()){return "";}
        HashMap map = new HashMap();
        map.put("loginUser", getLoginUser());
        String AdminType = GetAdminType();  
        map.put("AdminType", AdminType);
        map.put("channel", this.channel);
        
        if(css != null){
            map.put("cssStyle", css);
        }
        String content = this.templateProcessor.processStringTemplate(map, strTemplate);
        ArrayList<String> moduleList = this.channelPageService.getLabel(strTemplate);
        for(String m : moduleList){
            content = content.replace("#[" + m + "]", GetContentByModuleName(m, strCurrentNav));
        }
        return content;
    }
    protected String GetAdminType(){
        String AdminType = "|";
        if(getLoginUser() == null){ return "";}
        if(accessControlService.isSystemAdmin(getLoginUser())){
            AdminType = AdminType + "SystemSuperAdmin|";
        }
        if(accessControlService.isSystemUserAdmin(getLoginUser())){
            AdminType = AdminType + "SystemUserAdmin|";
        }
        if(accessControlService.isSystemContentAdmin(getLoginUser())){
            AdminType = AdminType + "SystemContentAdmin|";
        }
        if(accessControlService.getAccessControlByUserAndObject(getLoginUser().getUserId(), AccessControl.OBJECTTYPE_CHANNELSYSTEMADMIN, this.channel.getChannelId()) != null){
            AdminType = AdminType + "ChannelSystemAdmin|";
        }
        if(accessControlService.getAccessControlByUserAndObject(getLoginUser().getUserId(), AccessControl.OBJECTTYPE_CHANNELUSERADMIN, this.channel.getChannelId()) != null){
            AdminType = AdminType + "ChannelUserAdmin|";
        }
        if(accessControlService.getAccessControlByUserAndObject(getLoginUser().getUserId(), AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN, this.channel.getChannelId()) != null){
            AdminType = AdminType + "ChannelContentAdmin|";
        }
        if(AdminType.equals("|")){ AdminType = "";}
        return AdminType;
    }
    //# 自定义分类模块
    protected String GenerateCustomCategory(ChannelModule mdl){
        Integer count = mdl.getShowCount();
        String cateItemType = mdl.getCateItemType(); // channel_article_频道Id  channel_resource_频道Id  channel_photo_频道Id  channel_video_频道Id
        Integer cateId = mdl.getCateId();  // 分类id
        HashMap map = new HashMap();
        // 图片
        if(cateItemType.startsWith("channel_photo")){
            if(count == null){count = 10;}
            ChannelPhotoQuery qry = new ChannelPhotoQuery(" cp.photoId, photo.title, photo.href, photo.createDate, user.loginName, user.trueName as userTrueName ");
            qry.setRequest(request);
            qry.channelId = this.channelId;
            if(cateId != 0){
                qry.custormAndWhereClause = " channelCate LIKE '%/" + cateId + "/%'";
            }
            // print "查询频道图片"    
            List photo_list = qry.query_map(count);
            map.put("list", photo_list);
        // 视频
       }else if(cateItemType.startsWith("channel_video")){
            if(count == null){count = 10;}
            ChannelVideoQuery qry = new ChannelVideoQuery(" cv.videoId, video.title, video.createDate,cv.userId, user.loginName, user.trueName as userTrueName, video.flvThumbNailHref");
            qry.setRequest(request);
            qry.channelId = this.channelId;
            if(cateId != 0){            
                qry.custormAndWhereClause = " channelCate LIKE '%/" + cateId + "/%'";
            }
            // print "查询频道视频"    
            List video_list = qry.query_map(count);
            map.put("list", video_list);
        // 文章
       }else if(cateItemType.startsWith("channel_article")){
            if(count == null){count = 10;}
            ChannelArticleQuery qry = new ChannelArticleQuery("ca.articleId, ca.title, ca.createDate, ca.userId,ca.typeState, ca.loginName, ca.userTrueName");
            qry.setRequest(request);
            qry.channelId = this.channel.getChannelId();
            qry.articleState = true;
            if(cateId != 0){            
                qry.custormAndWhereClause = " channelCate LIKE '%/" + cateId + "/%'";
            }
            List article_list = qry.query_map(count); 
            // print "查询频道文章:分类 = "   + cateId + ",得到的文件数量:" + str(article_list.size())
            map.put("list", article_list);
        // 资源    
        }else if(cateItemType.startsWith("channel_resource")){
            if(count == null){count = 10;}
            ChannelResourceQuery qry = new ChannelResourceQuery(" cr.resourceId, resource.href, resource.title, resource.fsize, resource.createDate, cr.viewCount,user.loginName, user.trueName as userTrueName ");
            qry.setRequest(request);
            qry.channelId = this.channel.getChannelId();
            if(cateId != 0){            
                qry.custormAndWhereClause = " channelCate LIKE '%/" + cateId + "/%'";
            }
            // print "查询频道资源"    
            List resource_list = qry.query_map(count);
            map.put("list", resource_list);
        }
        
        map.put("channel", this.channel);
        map.put("cateItemType", cateItemType);
        map.put("cateId", cateId);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/CustomCategory.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    // 机构展示模块    
    protected String GenerateUnitShow(ChannelModule mdl){
        String unitType = mdl.getUnitType();
        // 得到学校单位的列表
        List<Unit> unlitList = this.unitService.getChildUnitListByUnitType(unitType);
        HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("unitType", unitType);
        map.put("unlitList", unlitList);
        String content = "";
        if(mdl.getTemplate() == null){
            content = this.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + this.skin + "/UnitShow.ftl", "utf-8");
        }else{
            content = this.templateProcessor.processStringTemplate(map, mdl.getTemplate());
        }
        return content;
    }
    protected String GetContentByModuleName(String moduleDisplayName, String strCurrentNav){
    	ChannelModule module = this.channelPageService.getChannelModuleByDisplayName(moduleDisplayName, this.channel.getChannelId());
        if(module == null){return "";}
        // print "module.moduleType="+module.moduleType
        String content = "";
        if(module.getModuleType().equals("Custorm")){
            if(module.getContent() != null){
                return module.getContent();
            }else{
                return "";
            }
        }else if(module.getModuleType().equals("ArticleList")){
            content = GenerateArticleListContent(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("SubjectNav")){
            content = GenerateSubjectNavContent(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("SiteNav")){
            content = GenerateSiteNavContent(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("ChannelNav")){
            content = GenerateChannelNavContent(module, strCurrentNav);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("ArticleCategory")){
            content = GenerateArticleCategory(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("Search")){
            content = GenerateSearch(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("Stat")){
            content = GenerateStat(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("ResourceList")){
            content = GenerateResourceList(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("ResourceCategory")){
            content = GenerateResourceCategory(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("Bulletin")){
            content = GenerateBulletin(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("VideoList")){
            content = GenerateVideoList(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("VideoCategory")){
            content = GenerateVideoCategory(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("PhotoList")){
            content = GeneratePhotoList(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("PhotoCategory")){
            content = GeneratePhotoCategory(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("UserList")){
            content = GenerateUserList(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("GroupList")){
            content = GenerateGroupListContent(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("Logo")){
            content = GenerateLogo(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("CustomCategory")){
            content = GenerateCustomCategory(module);
            module.setContent(content);
            return content;
        }else if(module.getModuleType().equals("UnitShow")){
            content = GenerateUnitShow(module);
            module.setContent(content);
            return content;
        }else{
            return "<h2>不存在的系统模块：" + module.getDisplayName() + "</h2>";
        }
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

	
	public TemplateProcessor getTemplateProcessor() {
		return templateProcessor;
	}

	public void setTemplateProcessor(TemplateProcessor templateProcessor) {
		this.templateProcessor = templateProcessor;
	}	
	
	public ChannelPageService getChannelPageService() {
		return channelPageService;
	}

	public void setChannelPageService(ChannelPageService channelPageService) {
		this.channelPageService = channelPageService;
	}
	
	public void setSubjectService(SubjectService subjectService){
		this.subjectService = subjectService;
	}
	public SubjectService getSubjectService(){
		return this.subjectService;
	}
	
	public void setSiteNavService(SiteNavService siteNavService){
		this.siteNavService = siteNavService;
	}
	public SiteNavService getSiteNavService(){
		return this.siteNavService ;
	}
	
	public void setAccessControlService(AccessControlService accessControlService){
		this.accessControlService = accessControlService;
	}
	public AccessControlService getAccessControlService(){
		return this.accessControlService ;
	}
}
