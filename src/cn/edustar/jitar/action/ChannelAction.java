package cn.edustar.jitar.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import com.alibaba.fastjson.JSONObject;

import cn.edustar.data.Pager;
import cn.edustar.jitar.data.Command;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.model.SiteUrlModel;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.ChannelModule;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.pojos.SiteNav;
import cn.edustar.jitar.service.CacheProvider;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.PlacardQuery;
import cn.edustar.jitar.service.PlacardService;
import cn.edustar.jitar.service.SiteNavService;
import cn.edustar.jitar.service.UnitTypeService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.PageContent;

/**
 * 自定义频道管理   
 * 
 * 
 * @author baimindong
 *
 */
public class ChannelAction extends BaseChannelManage {

	private static final long serialVersionUID = 4315703009413760335L;
	
	private PlacardService placardService;
	private SiteNavService siteNavService;
	private CacheProvider cacheProvider;
	private UnitTypeService unitTypeService;
	private CategoryService categoryService;
	
	private String moduletype;
	
	protected String execute(String cmd) throws Exception{
		if("gettemplate".equals(cmd)){
			return GetTemplate();
		}
		if(!this.isUserLogined()){
			addActionError("请先登录！");
			addActionLink("登录", getSiteUrl()+"login.jsp","_self");
			return ERROR;
		}

        
		if("add".equals(cmd)){
	        //# 本页面需要系统管理员
	        if (isSystemAdmin() == false){
	            addActionError(" 你没有管理的权限。需要系统管理员权限。");
	            return ERROR;	
	        }
			return channel_add();
		}else if("saveaddchannel".equals(cmd)){
	        //# 本页面需要系统管理员
	        if (isSystemAdmin() == false){
	            addActionError(" 你没有管理的权限。需要系统管理员权限。");
	            return ERROR;	
	        }
	        return channel_savechannel();
		}else if("deletechannel".equals(cmd)){
	        //# 本页面需要系统管理员
	        if (isSystemAdmin() == false){
	            addActionError(" 你没有管理的权限。需要系统管理员权限。");
	            return ERROR;	
	        }
	        return deletechannel();
		}else if("manage".equals(cmd)){
        	return channel_manage();
        }
		
		boolean isSystemAdmin = this.accessControlService.isSystemAdmin(this.getLoginUser());
        List<AccessControl> isChannelSystemAdminList = accessControlService.getAllAccessControlByUserAndObjectType(this.getLoginUser(),11);
        List<AccessControl> isChannelUserAdminList = accessControlService.getAllAccessControlByUserAndObjectType(this.getLoginUser(),12);
        List<AccessControl> isChannelContentAdminList = accessControlService.getAllAccessControlByUserAndObjectType(this.getLoginUser(),13);

        if(isSystemAdmin == false && isChannelSystemAdminList.size() < 1  && isChannelSystemAdminList.size() < 1 && isChannelSystemAdminList.size() < 1){
            addActionError("你无权管理频道。");
            return ERROR;
        }
        
        if("menu".equals(cmd)){
        	return menu();
        }else if("main".equals(cmd)){
        	return "main";
        }else if("head".equals(cmd)){
        	return "head";
        }else if("channelmenu".equals(cmd)){
        	return channelmenu();
        }else if("list".equals(cmd)){
        	return channel_list();
        }else if("delete".equals(cmd)){
        	return channel_delete();
        }else if("edit".equals(cmd)){
        	return channel_edit();
        }else if("save".equals(cmd)){
        	return channel_save();
        }else if("skins".equals(cmd)){
        	return channel_skins();
        }else if("saveskins".equals(cmd)){
        	return channel_skins_save();
        }else if("modulelist".equals(cmd)){
        	return channel_modulelist();  
        }else if("addmodule".equals(cmd)){
        	return channel_addmodule();  
        }else if("savemodulelist".equals(cmd)){
        	return channel_modulelist_save();
        }else if("bulletins".equals(cmd)){
        	return channel_bulletins();
        }else if("addbulletin".equals(cmd)){
        	return channel_addbulletin();
        }else if("navlist".equals(cmd)){
        	return channel_navlist();
        }else if("addnav".equals(cmd)){
        	return channel_addnav();
        }else{
        	return "index";
        }
	}
	private String channelmenu(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);	
        String AdminType = GetAdminType(channel);
        request.setAttribute("AdminType",AdminType);
        request.setAttribute("channel",channel);
    	return "channelmenu";		
	}
	private String channel_manage(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);	
        if(channel == null){
            addActionError(" 无法加载频道对象对象。");
            return ERROR;
        }
		
        if (channel == null){
            addActionError("无法加载频道。");
            return ERROR;
        }
        String AdminType = GetAdminType(channel);
        if(AdminType.equals("")){
            addActionError("你无权管理频道。");
            return ERROR;
        }
        String act = request.getParameter("act");
        request.setAttribute("AdminType",AdminType);
        request.setAttribute("channel",channel);
        if("menu".equals(act)){      
            return "channelmenu";
        }else if("main".equals(act)){
            return "main";
        }else if("head".equals(act)){
            return "head";
        }else{
            return "manage";
        }		
	}
	private String deletechannel(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);	
        if(channel == null){
            addActionError(" 无法加载频道对象对象。");
            return ERROR;
        }
        //#先删除相关的下级内容
        channelPageService.deleteChannelArticle(channelId);
        channelPageService.deleteChannelResource(channelId);
        channelPageService.deleteChannelPhoto(channelId);
        channelPageService.deleteChannelVideo(channelId);
        channelPageService.deleteChannelOtherData(channelId);
        channelPageService.deleteChannel(channel);
        return "editchannelsuccess";
	}
	private String channel_savechannel(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        boolean isNew = false;
        String title = param_util.safeGetStringParam("title");
        if(title.equals("")){
            addActionError("标题不能为空。");
            return ERROR;
        }
        if(channel == null){
            isNew = true;
            channel = new Channel();
            channel.setTitle(title);
            channel.setSkin("");
            channel.setHeaderTemplate("");
            channel.setFooterTemplate("");
            channel.setIndexPageTemplate("");
        }else{
            channel.setTitle(title);
        }
        channelPageService.saveOrUpdateChannel(channel);
        //# 创建导航
        //# 对于新建的频道，将增加系统模块、系统模板、系统导航等内容，
        if(isNew){
            //# 增加导航
            String[] siteName = new String[]{"总站首页", "频道首页", "频道工作室", "频道协助组", "频道文章", "频道资源", "频道视频", "频道图片"};
            String[] siteUrlArray = new String[]{"index.py",
                       "channel/channel.action?channelId=" + channel.getChannelId(),
                       "channel/channel.action?cmd=userlist&channelId=" + channel.getChannelId(),
                       "channel/channel.action?cmd=grouplist&channelId=" + channel.getChannelId(),
                       "channel/channel.action?cmd=articlelist&channelId=" + channel.getChannelId(),
                       "channel/channel.action?cmd=resourcelist&channelId=" + channel.getChannelId(),
                       "channel/channel.action?cmd=videolist&channelId=" + channel.getChannelId(),
                       "channel/channel.action?cmd=photolist&channelId=" + channel.getChannelId()
            			};
            String[] siteHightlightArray = new String[]{"index", "channel", "user","group","article", "resource", "video", "photo"};
            for(int i =0 ;i<siteName.length ;i++){
            	SiteNav siteNav = new SiteNav();
                siteNav.setSiteNavName(siteName[i]);
                siteNav.setIsExternalLink(false);
                siteNav.setSiteNavUrl(siteUrlArray[i]);
                siteNav.setSiteNavIsShow(true);
                siteNav.setSiteNavItemOrder(i);
                siteNav.setCurrentNav(siteHightlightArray[i]);
                siteNav.setOwnerType(SiteNav.SITENAV_OWNERTYPE_CHANNEL);
                siteNav.setOwnerId(channel.getChannelId());
                siteNavService.saveOrUpdateSiteNav(siteNav);
            }
            addSystemModule(channel);
            addSystemTemplate(channel);
        }
        request.setAttribute("channel", channel);
        return "editchannelsuccess";
	}
	private void addSystemTemplate(Channel channel){
        String indexPageTemplate = getFileContent("/WEB-INF/channel/template1/Main.ftl");
        String headerTemplate = getFileContent("/WEB-INF/channel/template1/Header.ftl");
        String footerTemplate = getFileContent("/WEB-INF/channel/template1/Footer.ftl");
        String cssStyle = getFileContent("/css/channel/template1/common.css");
        cssStyle = cssStyle.replace("background:url(", "background:url(../css/channel/");
        channel.setIndexPageTemplate(indexPageTemplate);
        channel.setHeaderTemplate(headerTemplate);
        channel.setFooterTemplate(footerTemplate);
        channel.setCssStyle(cssStyle);
        channel.setLogo(SiteUrlModel.getSiteUrl() + "images/banner.swf");
        channelPageService.saveOrUpdateChannel(channel);
	}
	private void addSystemModule(Channel channel){
			List<JSONObject> sysModuleArray = new ArrayList<JSONObject>(); 
			JSONObject o = new JSONObject();
			
			o.put("displayName", "文章列表");
			o.put("moduleName", "ArticleList");
			o.put("showCount", "10");
			sysModuleArray.add(o);
			
			o = new JSONObject();
			o.put("displayName", "上传资源列表");
			o.put("moduleName", "ResourceList");
			o.put("showCount", "10");
			sysModuleArray.add(o);
			
			o = new JSONObject();
			o.put("displayName", "文章分类列表");
			o.put("moduleName", "ArticleCategory");
			o.put("showCount", "0");
			sysModuleArray.add(o);

			o = new JSONObject();
			o.put("displayName", "资源分类列表");
			o.put("moduleName", "ResourceCategory");
			o.put("showCount", "0");
			sysModuleArray.add(o);
			
			o = new JSONObject();
			o.put("displayName", "网站公告");
			o.put("moduleName", "Bulletin");
			o.put("showCount", "10");
			sysModuleArray.add(o);
			
			o = new JSONObject();
			o.put("displayName", "搜索");
			o.put("moduleName", "Search");
			o.put("showCount", "0");
			sysModuleArray.add(o);
			
			o = new JSONObject();
			o.put("displayName", "学科导航");
			o.put("moduleName", "SubjectNav");
			o.put("showCount", "0");
			sysModuleArray.add(o);
			
			o = new JSONObject();
			o.put("displayName", "频道导航");
			o.put("moduleName", "ChannelNav");
			o.put("showCount", "0");
			sysModuleArray.add(o);
			
			o = new JSONObject();
			o.put("displayName", "总站导航");
			o.put("moduleName", "SiteNav");
			o.put("showCount", "0");
			sysModuleArray.add(o);	
			
			o = new JSONObject();
			o.put("displayName", "频道统计");
			o.put("moduleName", "Stat");
			o.put("showCount", "0");
			sysModuleArray.add(o);	
			
			o = new JSONObject();
			o.put("displayName", "视频列表");
			o.put("moduleName", "VideoList");
			o.put("showCount", "10");
			sysModuleArray.add(o);	
			
			o = new JSONObject();
			o.put("displayName", "视频分类");
			o.put("moduleName", "VideoCategory");
			o.put("showCount", "0");
			sysModuleArray.add(o);	
			
			o = new JSONObject();
			o.put("displayName", "图片列表");
			o.put("moduleName", "PhotoList");
			o.put("showCount", "10");
			sysModuleArray.add(o);	
			
			o = new JSONObject();
			o.put("displayName", "图片分类");
			o.put("moduleName", "PhotoCategory");
			o.put("showCount", "0");
			sysModuleArray.add(o);
			
			o = new JSONObject();
			o.put("displayName", "工作室列表");
			o.put("moduleName", "UserList");
			o.put("showCount", "10");
			sysModuleArray.add(o);
			
			o = new JSONObject();
			o.put("displayName", "协助组列表");
			o.put("moduleName", "GroupList");
			o.put("showCount", "10");
			sysModuleArray.add(o);
			
			o = new JSONObject();
			o.put("displayName", "频道Logo");
			o.put("moduleName", "Logo");
			o.put("showCount", "0");
			sysModuleArray.add(o);			
			
			/*
	        List sysModuleArray = new String[][
	                          {"displayName":u"文章列表", "moduleName":"ArticleList", "showCount":"10"},
	                          {"displayName":u"上传资源列表", "moduleName":"ResourceList", "showCount":"10"},
	                          {"displayName":u"文章分类列表", "moduleName":"ArticleCategory", "showCount":"0"},
	                          {"displayName":u"资源分类列表", "moduleName":"ResourceCategory", "showCount":"0"},
	                          {"displayName":u"网站公告", "moduleName":"Bulletin", "showCount":"10"},
	                          {"displayName":u"搜索", "moduleName":"Search", "showCount":"0"},
	                          {"displayName":u"学科导航", "moduleName":"SubjectNav", "showCount":"0"},
	                          {"displayName":u"频道导航", "moduleName":"ChannelNav", "showCount":"0"},
	                          {"displayName":u"总站导航", "moduleName":"SiteNav", "showCount":"0"},
	                          {"displayName":u"频道统计", "moduleName":"Stat", "showCount":"0"},
	                          {"displayName":u"视频列表", "moduleName":"VideoList", "showCount":"10"},
	                          {"displayName":u"视频分类", "moduleName":"VideoCategory", "showCount":"0"},
	                          {"displayName":u"图片列表", "moduleName":"PhotoList", "showCount":"10"},
	                          {"displayName":u"图片分类", "moduleName":"PhotoCategory", "showCount":"0"},
	                          {"displayName":u"工作室列表", "moduleName":"UserList", "showCount":"10"},
	                          {"displayName":u"协助组列表", "moduleName":"GroupList", "showCount":"10"},
	                          {"displayName":u"频道Logo", "moduleName":"Logo", "showCount":"0"}
	                          ]
	        */
	        for(int i = 0;i <sysModuleArray.size(); i++){
	        	ChannelModule module = new ChannelModule();
	            if(!sysModuleArray.get(i).get("showCount").equals("0")){
	                module.setShowCount(Integer.parseInt(sysModuleArray.get(i).get("showCount").toString()));
	            }
	            module.setChannelId(channel.getChannelId());
	            module.setDisplayName(sysModuleArray.get(i).get("displayName").toString());
	            module.setModuleType(sysModuleArray.get(i).get("moduleName").toString());
	            String template = getFileContent("/WEB-INF/channel/template1/" + sysModuleArray.get(i).get("moduleName").toString() + ".ftl");
	            module.setTemplate(template);
	            module.setPageType("main");
	            channelPageService.saveOrUpdateChannelModule(module);	
	        }
	}
	private String channel_add(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        request.setAttribute("channel", channel);
        return "add";
	}
	
	private String channel_addmodule(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }		
        Integer moduleId = param_util.safeGetIntParam("moduleId");
        String mt = param_util.safeGetStringParam("mt");
        ChannelModule channelModule = channelPageService.getChannelModule(moduleId);
        if (channelModule != null){
            mt = channelModule.getModuleType();
        }
        if("".equals(mt)){
            addActionError("请选择一个模块类型。");
            return ERROR;
        }
        if("UnitShow".equals(mt)){
            //#机构属性
        	List<String> unitTypeList = unitTypeService.getUnitTypeNameList();
            request.setAttribute("unitTypeList", unitTypeList);
        }
        if("CustomCategory".equals(mt)){
            //#得到文章 资源 图片 视频的分类
        	CategoryTreeModel article_category_list =categoryService.getCategoryTree("channel_article_" + channelId);
            request.setAttribute("article_category_list", article_category_list);
            CategoryTreeModel resource_category_list =categoryService.getCategoryTree("channel_resource_" + channelId);
            request.setAttribute("resource_category_list", resource_category_list);
            CategoryTreeModel photo_category_list =categoryService.getCategoryTree("channel_photo_" + channelId);
            request.setAttribute("photo_category_list", photo_category_list);
            CategoryTreeModel video_category_list =categoryService.getCategoryTree("channel_video_" + channelId);
            request.setAttribute("video_category_list", video_category_list);
        }
        if("POST".equals(request.getMethod())){
            return returnAddSystemModule(mt,channelModule,channel,moduleId);
        }
        request.setAttribute("channel",channel);
        request.setAttribute("module",channelModule);
        request.setAttribute("moduleType", mt);
        //System.out.println("mt = " + mt);
        this.moduletype = mt;
        return "addmodule";
        //return "/WEB-INF/ftl/channel/add_module_" + mt + ".ftl"        
	}
	
	private String returnAddSystemModule(String mt,ChannelModule channelModule,Channel channel,int moduleId){
        if(channelModule == null){
            channelModule = new ChannelModule();
        }
        String displayName = param_util.safeGetStringParam("moduleDisplayName");
        String template = param_util.safeGetStringParam("template");
        if (template.equals("")){ template = null;}
        if(param_util.existParam("showCount")){
            Integer showCount = param_util.safeGetIntParam("showCount");
            if(showCount == 0){showCount = 10;}
            channelModule.setShowCount(showCount);
        }
        if(param_util.existParam("moduleContent")){
            String moduleContent = param_util.safeGetStringParam("moduleContent");
            if(moduleContent.equals("")){
                addActionError("模块内容不能为空。");
                return ERROR;
            }
            channelModule.setContent(moduleContent);
        }   
        if(displayName.equals(""))
        {
            addActionError("模块名称不能为空。");
            return ERROR;
        }
        ChannelModule channelExist = channelPageService.getChannelModuleByDisplayName(displayName,channel.getChannelId());
        if(channelExist != null){
            if(channelModule.getModuleId() == 0){ 
                addActionError("模块名称已经存在。");
                return ERROR;
            }else{
                if(channelModule.getModuleId() != channelExist.getModuleId()){ 
                    addActionError("模块名称已经存在。");
                    return ERROR;
                }
            }
        }
        if("UnitShow".equals(mt)){
        	List<String> unitTypeList =unitTypeService.getUnitTypeNameList();
            request.setAttribute("unitTypeList", unitTypeList);
            String typeName = param_util.safeGetStringParam("typeName");
            if(typeName.equals("")){
               addActionError("必须选择机构属性。");
                return ERROR;
            }
           channelModule.setUnitType(typeName);
        }       
        if("CustomCategory".equals(mt)){
            String typeName = param_util.safeGetStringParam("typeName");
            if(typeName.equals("")){
               addActionError("必须选择分类。");
                return ERROR;
            }
           Integer categoryId = param_util.safeGetIntParam(typeName+"Id");
           channelModule.setCateId(categoryId);
            if ("articleCategory".equals(typeName)){
               channelModule.setCateItemType("channel_article_" + channel.getChannelId());
            }
            if ("resourceCategory".equals(typeName)){
               channelModule.setCateItemType("channel_resource_" + channel.getChannelId());
            }
            if ("photoCategory".equals(typeName)){
               channelModule.setCateItemType("chanel_photo_" + channel.getChannelId());
            }
            if ("videoCategory".equals(typeName)){
               channelModule.setCateItemType("channel_video_" + channel.getChannelId());
            }
        }
       channelModule.setChannelId(channel.getChannelId());
       channelModule.setDisplayName(displayName);
       channelModule.setTemplate(template);
       channelModule.setModuleType(mt);
       channelModule.setPageType("main");
       channelPageService.saveOrUpdateChannelModule(channelModule);
        if(moduleId == 0){
           addActionMessage("添加成功。");
        }else{
           addActionMessage("修改成功。");
        }
       addActionLink("返回模块管理", "channel.action?cmd=modulelist&channelId=" + channel.getChannelId());
        return SUCCESS;	
	}
	private String GetTemplate() throws IOException{
		PrintWriter out = response.getWriter();
		//out.append(PageContent.PAGE_UTF8);
		//out.println("");
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
        	out.println("不能加载频道对象。");
    		out.flush();
    		out.close();
    		return NONE;		
        }		
        String skin = "template1";
        if(channel.getSkin() == null){
            skin = "template1";
        }else if(channel.getSkin().equals("")){
            skin = "template1";
        }else{
            skin = channel.getSkin();
        }
        String adminType = GetAdminType(channel);
        if(adminType.indexOf("|SystemSuperAdmin|") >-1 || adminType.indexOf("|ChannelSystemAdmin|") >-1 ){
            String templateName = param_util.safeGetStringParam("name");
            String type = param_util.safeGetStringParam("type");
            if(type.equals("index")){
            	out.println(getIndexTemplate(templateName,skin));
            }else{
                String template = getFileContent("/WEB-INF/channel/" + skin + "/" + templateName + ".ftl");
                out.println(template);
            }
        }else{
        	out.println("你没有管理此频道的权限。");
    		out.flush();
    		out.close();
        }
        return NONE;
	}

    private String getIndexTemplate(String templateName,String skin){
        if("indexPageTemplate".equals(templateName)){
            return getFileContent("/WEB-INF/channel/" + skin + "/Main.ftl");
        }else if("headerTemplate".equals(templateName)){
            return getFileContent("/WEB-INF/channel/" + skin + "/Header.ftl");
        }else if("footerTemplate".equals(templateName)){
            return getFileContent("/WEB-INF/channel/" + skin + "/Footer.ftl");
        }else if("cssStyle".equals(templateName)){
            String cssStyle = getFileContent("/css/channel/" + skin + "/common.css");
            cssStyle = cssStyle.replace("background:url(", "background:url(../css/channel/" + skin + "/");
            return cssStyle;
        }else if("SubjectNav".equals(templateName)){
            return getFileContent("/WEB-INF/ftl/site_subject_nav.ftl");
        }else{
            return "";
        }
    }
    private String channel_addnav(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }    	
        Integer siteNavId = param_util.safeGetIntParam("siteNavId");
        SiteNav siteNav = siteNavService.getSiteNavById(siteNavId);
        
        if("POST".equals(request.getMethod())){
            String siteName = param_util.safeGetStringParam("siteName");
            String siteUrl = param_util.safeGetStringParam("siteUrl");
            Integer siteShow = param_util.safeGetIntParam("siteShow");
            Integer siteOrder = param_util.safeGetIntParam("siteOrder");
            if(siteName.length() == 0){
                addActionError("请输入导航名称.");
                return ERROR;
            }
            if(siteUrl.length() == 0 && siteNav != null && siteNav.getIsExternalLink() == true){
                addActionError("请输入导航地址.");
                return ERROR;            
            }
            if(siteNav == null){
                siteNav = new SiteNav();
                siteNav.setIsExternalLink(true);
                siteNav.setOwnerType(3);
                siteNav.setOwnerId(channel.getChannelId());
            }
            if(siteNav.getIsExternalLink()){
                siteNav.setSiteNavUrl(siteUrl);
            }
            siteNav.setSiteNavName(siteName);
            if(siteShow == 1){
                siteNav.setSiteNavIsShow(true);
            }else{
                siteNav.setSiteNavIsShow(false);
            }

            siteNav.setSiteNavItemOrder(siteOrder);
            siteNavService.saveOrUpdateSiteNav(siteNav);
            CacheService cache = cacheProvider.getCache("sitenav");
            if(cache != null){                    
                List cache_list = cache.getAllKeys();
                String cache_key = "channel_nav_" + channel.getChannelId();
                for(int i=0;i<cache_list.size();i++){
                    Object c = cache_list.get(i);
                	if( c.toString().equals(cache_key)){
                        cache.remove(c.toString());
                	}
                }
            }
            addActionMessage("您成功编辑了一个自定义导航： " + siteName);
            addActionLink("返回", "channel.action?cmd=navlist&channelId=" + channel.getChannelId());
            return SUCCESS;
        }
        if(siteNav != null){
            request.setAttribute("siteNav", siteNav);
        }
        return "addnav";
    }
	private String channel_navlist(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }
        if("POST".equals(request.getMethod())){
            String act = param_util.safeGetStringParam("act");
            if(act.equals("delete")){
            	List<Integer> guids = param_util.safeGetIntValues("guid");
                for(Integer guid : guids){
                	SiteNav siteNav = siteNavService.getSiteNavById(guid);
                    if(siteNav != null){
                        if(siteNav.getIsExternalLink() == true){
                            siteNavService.deleteSiteNav(siteNav);
                        }
                    }
                }
            }
            if(act.equals("order")){
            	List<Integer> guids = param_util.safeGetIntValues("nav_id");
                for(Integer guid : guids){
                    Integer order = param_util.safeGetIntParam("z_" + guid);
                    SiteNav siteNav = siteNavService.getSiteNavById(guid);
                    if (siteNav != null){
                        siteNav.setSiteNavItemOrder(order);
                        siteNavService.saveOrUpdateSiteNav(siteNav);
                    }
                }
            }
            if(act.equals("display")){
                Integer siteId = param_util.safeGetIntParam("siteId");
                Integer siteDisplay = param_util.safeGetIntParam("siteDisplay");
                SiteNav siteNav = siteNavService.getSiteNavById(siteId);
                if( siteNav != null){
                    if(siteDisplay == 1){
                        siteNav.setSiteNavIsShow(true);
                    }else{
                        siteNav.setSiteNavIsShow(false);
                    }
                    siteNavService.saveOrUpdateSiteNav(siteNav);
                }
            }
            CacheService cache = cacheProvider.getCache("sitenav");
            if(cache != null){
            	List cache_list = cache.getAllKeys();
            	String cache_key = "channel_nav_" + channel.getChannelId();
                for(Object c : cache_list){
                    if(cache_key.equals(c.toString())){
                        cache.remove(c.toString());
                    }
                }
            }
        }                
        request.setAttribute("channel",channel);
        List<SiteNav> siteNavItemList = siteNavService.getAllSiteNav(true, 3, channel.getChannelId());
        request.setAttribute("siteNavItemList", siteNavItemList);	
        return "navlist";
	}
	private String channel_addbulletin(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }
        Integer placardId = param_util.safeGetIntParam("placardId");
        Placard placard = placardService.getPlacard(placardId);
        if("POST".equals(request.getMethod())){
            String title = param_util.safeGetStringParam("title");
            String content = param_util.safeGetStringParam("placardContent");
            if(title.equals("") || content.equals("")){
                addActionError("请输入完整的标题和内容。");
                return ERROR;
            }
            if(placard == null){
                placard = new Placard();
                placard.setObjType(19);
                placard.setObjId(channel.getChannelId());
                placard.setUserId(getLoginUser().getUserId());
            }
            placard.setTitle(title);
            placard.setContent(content);
            placardService.savePlacard(placard);
            addActionMessage("编辑完成。");
            addActionLink("返回", "channel.action?cmd=bulletins&channelId=" + channelId);
            return SUCCESS;
        }
        if(placard == null){
        	placard = new Placard();
        	placard.setTitle("");
        	placard.setUserId(getLoginUser().getUserId());
        	placard.setContent("");
        	placard.setObjType(ObjectType.OBJECT_TYPE_CHANNEL.getTypeId());
        	placard.setObjId(channel.getChannelId());
        }
        request.setAttribute("placard", placard);
        request.setAttribute("channel", channel);
        return "addbulletin";
	}
	private String channel_bulletins(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }
        String act = param_util.safeGetStringParam("act");
        if(request.getMethod().equals("POST")){
            List<Integer> guids = param_util.safeGetIntValues("guid");
            for(Integer g : guids){
            	Placard placard = placardService.getPlacard(g);
                if(placard != null){
                    if("delete".equals(act)){
                        placardService.deletePlacard(placard);
                    }else if("show".equals(act)){
                        placard.setHide(false);
                        placardService.savePlacard(placard);
                    }else if("hide".equals(act)){
                        placard.setHide(true);
                        placardService.savePlacard(placard);
                    }
                }
            }
        }
        PlacardQuery qry = new PlacardQuery(" pld.id, pld.title, pld.createDate, pld.hide ");
        qry.objType = 19;
        qry.objId = channel.getChannelId();
        qry.hideState = null;
        
		Pager pager = param_util.createPager();
		pager.setItemName("公告");
		pager.setItemUnit("个");
		pager.setPageSize(10);
		pager.setTotalRows(qry.count());

        List placard_list = (List)qry.query_map(pager);
        request.setAttribute("placard_list", placard_list);
        request.setAttribute("pager", pager);
        request.setAttribute("channel", channel);
		return "bulletins";
	}
	private String channel_modulelist(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }
        List<ChannelModule> moduleList = channelPageService.getChannelModuleList(channelId);
        request.setAttribute("channel", channel);
        request.setAttribute("moduleList", moduleList);
		return "modulelist";
	}
	private String channel_modulelist_save(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }
        String act = param_util.safeGetStringParam("act");
        if(act.equals("delete")){
            deletemodule(channel);
        }else if(act.equals("showCount")){
            saveShowCount();		
        }
        return channel_modulelist();
	}
	private void saveShowCount(){
        List<Integer> guids = param_util.safeGetIntValues("md_id");
        for(Integer g : guids){
        	ChannelModule module = channelPageService.getChannelModule(g);
            if(module != null){
                Integer showCount = param_util.getIntParamZeroAsNull("md_" + g);
                module.setShowCount(showCount);
                channelPageService.saveOrUpdateChannelModule(module);
            }
        }
	}
	private void deletemodule(Channel channel){
        List<Integer> guids = param_util.safeGetIntValues("guid");
        for(Integer g : guids){
        	ChannelModule module = channelPageService.getChannelModule(g);
            if(module != null){
                //# 删除模板里面的标签
                String indexPageTemplate = channel.getIndexPageTemplate();
                String headerTemplate = channel.getHeaderTemplate();
                String footerTemplate = channel.getFooterTemplate();
                indexPageTemplate = indexPageTemplate.replace("#[" + module.getDisplayName()+ "]", "");
                headerTemplate = headerTemplate.replace("#[" + module.getDisplayName() + "]", "");
                footerTemplate = footerTemplate.replace("#[" + module.getDisplayName() + "]", "");
                channel.setIndexPageTemplate(indexPageTemplate);
                channel.setHeaderTemplate(headerTemplate);
                channel.setFooterTemplate(footerTemplate);
                channelPageService.saveOrUpdateChannel(channel);
                channelPageService.deleteChannelModule(module);
            }
        }
    }
	private String channel_skins_save(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }
		
		String skin = "";
        String tmpl = param_util.safeGetStringParam("tmpl");
        if(tmpl.equals("")){
            channel.setSkin("");
            skin = "template1";
        }else{
            channel.setSkin(tmpl);
            skin = tmpl;
        }
        //#选择了样式，需要把样式对应的模块以及页面内容写入到channel中
        String headerTemplate = getFileContent("/WEB-INF/channel/"+skin+"/Header.ftl");
        String footerTemplate = getFileContent("/WEB-INF/channel/"+skin+"/Footer.ftl");
        String indexPageTemplate = getFileContent("/WEB-INF/channel/"+skin+"/Main.ftl");
        String cssStyle = getFileContent("/css/channel/" + skin + "/common.css");
        cssStyle = cssStyle.replace("background:url(", "background:url(../css/channel/" + skin + "/");
        channel.setHeaderTemplate(headerTemplate);
        channel.setFooterTemplate(footerTemplate);
        channel.setIndexPageTemplate(indexPageTemplate);
        channel.setCssStyle(cssStyle);
        channelPageService.saveOrUpdateChannel(channel);    
        return SUCCESS; 		
	}
	/**
	 * 返回 文本文件 内容
	 * @param filepath
	 * @return
	 */
	private String getFileContent(String fp){
		ServletContext ctx = request.getSession().getServletContext();
        String filePath = ctx.getRealPath(fp);
        return CommonUtil.readFileToString(filePath, "utf-8");		
	}
	private String channel_skins(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }
        //# 查找所有样式
        String themeFolder = request.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "channel" + File.separator;
        File file = new File(themeFolder);
        if(file.exists() == true)
        {
            List<String> theme_list = new ArrayList<String>();
            File[] fs = file.listFiles();
            for(File theme : fs){
                //fd = File(themeFolder + theme)
                //if fd.isDirectory() == True:
                //    theme_list.append(theme)
            	if(theme.isDirectory()){
            		theme_list.add(theme.getName());
            	}
            }
            if (theme_list.size() > 0){
                request.setAttribute("theme_list", theme_list);
            }
        }
        request.setAttribute("channel", channel);
        return "skins";
	}
	private String channel_save(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        String tab = param_util.safeGetStringParam("tab");
        if(tab.equals("")){
            tab = "home";
        }
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }
        if (tab.equals("title")){
            return save_edit(channel);
        }else if(tab.equals("home")){
            return save_home(channel);
        }else if(tab.equals("header")){
            return save_header(channel);
        }else if(tab.equals("footer")){
            return save_footer(channel);
        }else if(tab.equals("logo")){
            return save_logo(channel);
        }else if(tab.equals("css")){
            return save_css(channel);
        }else{
        	return channel_edit();
        }
	}
	private String save_css(Channel channel){
        String cssStyle = param_util.safeGetStringParam("cssStyle");
        if(cssStyle.equals("")){ cssStyle = null;}
        channel.setCssStyle(cssStyle);
        channelPageService.saveOrUpdateChannel(channel);
        addActionMessage("修改成功。");
        return SUCCESS;		
	}
	private String save_logo(Channel channel){
        String logo = param_util.safeGetStringParam("logo");
        if(logo.equals("")){logo = null;}
        channel.setLogo(logo);
        channelPageService.saveOrUpdateChannel(channel);
        addActionMessage("修改成功。");
        return SUCCESS;
	}
	private String save_footer(Channel channel){
        String footerTemplate = param_util.safeGetStringParam("footerTemplate");
        channel.setFooterTemplate(footerTemplate);
        channelPageService.saveOrUpdateChannel(channel);
        addActionMessage("修改成功。");
        return SUCCESS;
	}
	private String save_header(Channel channel){
        String headerTemplate = param_util.safeGetStringParam("headerTemplate");
        channel.setHeaderTemplate(headerTemplate);
       channelPageService.saveOrUpdateChannel(channel);
        addActionMessage("修改成功。");
        return SUCCESS;		
	}
	private String save_home(Channel channel){
        String indexPageTemplate = param_util.safeGetStringParam("indexPageTemplate");
        channel.setIndexPageTemplate(indexPageTemplate);
        channelPageService.saveOrUpdateChannel(channel);      
        return channel_edit();
	}
	private String save_edit(Channel channel){
        String title = param_util.safeGetStringParam("title");
        //#skin = param_util.safeGetStringParam("skin") #皮肤样式单独模块处理
        String headerTemplate = param_util.safeGetStringParam("headerTemplate");
        String footerTemplate = param_util.safeGetStringParam("footerTemplate");
        String indexPageTemplate = param_util.safeGetStringParam("indexPageTemplate");
        channel.setTitle(title);
        //#self.channel.setSkin(skin)
        channel.setHeaderTemplate(headerTemplate);
        channel.setFooterTemplate(footerTemplate);
        channel.setIndexPageTemplate(indexPageTemplate);
        channelPageService.saveOrUpdateChannel(channel);
        request.setAttribute("mode","edit");
        return "editchannelsuccess";
		
	}
	private String channel_edit(){
        Integer channelId = param_util.safeGetIntParam("channelId");
        String tab = param_util.safeGetStringParam("tab");
        if(tab.equals("")){
            tab = "home";
        }
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }
        //# 得到本频道的所有模块，供插入使用
        get_module_list(channelId);
        if (channel.getSkin() == null){
            request.setAttribute("skin","template1");
        }else if (channel.getSkin().equals("")){
            request.setAttribute("skin","template1");
        }else{
            request.setAttribute("skin",channel.getSkin());
        }
        request.setAttribute("channel",channel);
        request.setAttribute("tab",tab);
        return "edit";
	}
	
	private void get_module_list(int channelId){
		List<ChannelModule> module_list = channelPageService.getChannelModuleList(channelId);
        request.setAttribute("module_list",module_list);
	}
	private String menu(){
    	List<Channel> channel_list = null;
        //#判断是否是系统管理员
        if(isSystemAdmin()){
            request.setAttribute("admin_type","admin");
            channel_list = channelPageService.getChannelList();
        }else{
            //# 得到当前用户可以管理的频道列表
            //qry = Command("SELECT DISTINCT objectId as objectId FROM AccessControl Where userId = " + getLoginUser().getUserId() + " And (objectType = 11 or objectType = 12 or objectType = 13) Order By objectId ASC"); 
        	List<AccessControl> list = accessControlService.getAllAccessControlByUser(getLoginUser());
            List<Integer> ChannelID = new ArrayList<Integer>();
            for(AccessControl ac:list){
            	if(ac.getObjectType()==11 || ac.getObjectType()==12 || ac.getObjectType()==13){
            		int objectId=ac.getObjectId();
            		if(!ChannelID.contains(objectId)){
            			ChannelID.add(objectId);
            		}
            	}
            }
            channel_list = new ArrayList<Channel>();
            for(Integer id : ChannelID){
                Channel channel = channelPageService.getChannel(id);
                channel_list.add(channel);
            }
            if (channel_list.size() > 0){
                request.setAttribute("admin_type","channeladmin");
            }else{
                request.setAttribute("admin_type","");
            }
        }
        request.setAttribute("channel_list",channel_list);
        return "menu";		
	}
	private String channel_delete()	{
        List<Integer> guids = param_util.safeGetIntValues("guid");
        for(Integer g : guids)
        {
            Channel channel = channelPageService.getChannel(g);
            if(channel != null)
            {
                channelPageService.deleteChannel(channel);
            }
        }
        return "editchannelsuccess";
	}
	private String channel_list(){
        //# 本页面需要系统管理员
        if(isSystemAdmin() == false){
            addActionError("你没有管理的权限。需要系统管理员权限。");
            return "accessderror";
        }
        List<Channel> channel_list = channelPageService.getChannelList();
        request.setAttribute("channel_list",channel_list);
        return "list";
	}
	
	public void setPlacardService(PlacardService placardService){
		this.placardService=placardService;
	}
	public PlacardService getPlacardService(){
		return this.placardService;
	}
	public void setSiteNavService(SiteNavService siteNavService){
		this.siteNavService = siteNavService;
	}
	public SiteNavService getSiteNavService(){
		return this.siteNavService;
	}	
	public void setCacheProvider(CacheProvider cacheProvider){
		this.cacheProvider=cacheProvider;
	}
	public CacheProvider getCacheProvider(){
		return this.cacheProvider;
	}
	public UnitTypeService getUnitTypeService(){
		return this.unitTypeService;
	}
	public void setUnitTypeService(UnitTypeService unitTypeService){
		this.unitTypeService = unitTypeService;
	}
	public CategoryService getCategoryService(){
		return this.categoryService ;
	}
	public void setCategoryService(CategoryService categoryService){
		this.categoryService = categoryService;
	}
	public String getModuletype(){
		return this.moduletype;
	}
	public void setModuletype(String moduletype){
		this.moduletype=moduletype;
	}
}
