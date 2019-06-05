package cn.edustar.jitar.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.UnitType;
import cn.edustar.jitar.service.ChannelArticleQuery;
import cn.edustar.jitar.service.ChannelGroupQuery;
import cn.edustar.jitar.service.ChannelPhotoQuery;
import cn.edustar.jitar.service.ChannelResourceQuery;
import cn.edustar.jitar.service.ChannelUserQuery;
import cn.edustar.jitar.service.ChannelVideoQuery;
import cn.edustar.jitar.service.PlacardQuery;
import cn.edustar.jitar.service.PlacardService;
import cn.edustar.jitar.service.UnitTypeService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 频道浏览
 * @author baimindong
 *
 */
public class ChannelHomeAction extends ChannelPage{

	private static final long serialVersionUID = 760416039561681644L;
	protected UnitTypeService unitTypeService;
	protected PlacardService placardService;
	protected String execute(String cmd) throws Exception{
        if(this.channel == null){
            addActionError("无法加载指定的页面！");
            return ERROR;
        }
        if(cmd == null || cmd.length() == 0){
        	cmd = "show";
        }
        if(cmd.equals("show")){
        	return show();
        }else if(cmd.equals("userlist")){
        	return userlist();
        }else if(cmd.equals("grouplist")){
        	return grouplist();
        }else if(cmd.equals("articlelist")){
        	return articlelist();
        }else if(cmd.equals("resourcelist")){
        	return resourcelist();
        }else if(cmd.equals("videolist")){
        	return videolist();
        }else if(cmd.equals("photolist")){
        	return photolist();
        }else if(cmd.equals("unitlist")){
        	return unitlist();
        }else if(cmd.equals("placardlist")){
        	return placardlist();
        }else if(cmd.equals("bulletin")){
        	return bulletin();
        }else if(cmd.equals("search")){
        	return search();
        }else{
        	return show();
        }
    }
	private String search() throws IOException{
		HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("head_nav", "search");
        
        //# 输出页头
        String headerContent = GenerateContentFromTemplateString(this.channel.getHeaderTemplate(),null,null);
        String footerContent = GenerateContentFromTemplateString(this.channel.getFooterTemplate(),null,null);
        //# 输出主体部分：
        String k = param_util.safeGetStringParam("k");
        String f = param_util.safeGetStringParam("f");
        k = URLEncoder.encode(k,"utf-8");
        String mainContent  = "";
        if(k.length() == 0){
            mainContent = "<div style='padding:20px;font-size:14px;color:red;text-align:center'>请输入关键字。<br/><br/><a href='channel.py?channelId=" +this.channel.getChannelId() + "'>重新输入</a></div>";
        }else{
            if(f.equals("article")){
                response.sendRedirect("channel.action?cmd=articlelist&channelId=" + this.channel.getChannelId() + "&k=" + k + "&f=title");
                return NONE;
            }else if(f.equals("resource")){
                response.sendRedirect("channel.action?cmd=resourcelist&channelId=" + this.channel.getChannelId() + "&k=" + k + "&f=title");
                return NONE;
            }else if(f.equals("video")){
                response.sendRedirect("channel.action?cmd=videolist&channelId=" + this.channel.getChannelId() + "&k=" + k + "&f=3");
                return NONE;
            }else if(f.equals("photo")){
                response.sendRedirect("channel.action?cmd=photolist&channelId=" + this.channel.getChannelId() + "&k=" + k + "&f=3");
                return NONE;
            }else if(f.equals("user")){
                response.sendRedirect("channel.action?cmd=userlist&channelId=" + this.channel.getChannelId() + "&k=" + k + "&f=trueName");
                return NONE;
            }else{
                mainContent = "<div style='padding:20px;font-size:14px;color:red;text-align:center'>查询的类别不正确。</div>";
            }
        }
		PrintWriter out = response.getWriter();
        if(headerContent.length() == 0 && footerContent.length() == 0 && mainContent.length() == 0){
            out.println("该频道没有指定模板，无法显示页面内容。");
            return NONE;
        }           
        out.println(headerContent);
        out.println(mainContent);
        out.println(footerContent);
        return NONE;
	}
	private String bulletin() throws IOException{
		HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("head_nav", "video");
        
        if(this.channel.getSkin() == null){
            skin = "template1";
		}else if(this.channel.getSkin().length() == 0){
            skin = "template1";
		}else{
            skin = this.channel.getSkin();
		}
                    
        //# 输出页头
        String headerContent = GenerateContentFromTemplateString(channel.getHeaderTemplate(),null,null);
        String footerContent = GenerateContentFromTemplateString(channel.getFooterTemplate(),null,null);
        //# 输出主体部分：
        String mainContent = "";
        Integer placardId = param_util.getIntParam("placardId");
        if(placardId == null){
            mainContent = "";
        }else{            
        	Placard placard = placardService.getPlacard(placardId);
            map.put("placard", placard);       
            mainContent = templateProcessor.processTemplate(map, "/WEB-INF/channel/" + skin + "/show_bulletin.ftl", "utf-8");
        }
		PrintWriter out = response.getWriter();
        if(headerContent.length() == 0 && footerContent.length() == 0 && mainContent.length() == 0){
            out.println("该频道没有指定模板，无法显示页面内容。");
            return NONE;
        }        
        out.println(headerContent);
        out.println(mainContent);
        out.println(footerContent);
        return NONE;
	}
	private String placardlist() throws IOException{
		HashMap map = new HashMap();
        map.put("channel", this.channel);
        map.put("head_nav", "placard");
        
        //# 输出页头
        String headerContent = GenerateContentFromTemplateString(this.channel.getHeaderTemplate(),"placard",null);
        String footerContent = GenerateContentFromTemplateString(this.channel.getFooterTemplate(),null,null);        
        //# 输出主体部分：    
        PlacardQuery qry = new PlacardQuery("  pld.id, pld.title, pld.createDate ");
        qry.objType = 19;
        qry.objId = this.channel.getChannelId();
        
		Pager pager = param_util.createPager();
		pager.setItemName("公告");
		pager.setItemUnit("个");
		pager.setPageSize(20);
        
        List placard_list = (List)qry.query_map(pager);
            
        map.put("placard_list", placard_list);
        map.put("pager", pager);

        if(this.channel.getSkin() == null){
            skin = "template1";
		}else if(this.channel.getSkin().length() == 0){
            skin = "template1";
		}else{
            skin = this.channel.getSkin();
		}
        String mainContent = templateProcessor.processTemplate(map, "/WEB-INF/channel/" + skin + "/channel_placard.ftl", "utf-8");
        
		PrintWriter out = response.getWriter();
        if(headerContent.length() == 0 && footerContent.length() == 0 && mainContent.length() == 0){
            out.println("该频道没有指定模板，无法显示页面内容。");
            return NONE;
        }
        out.println(headerContent);
        out.println(mainContent);
        out.println(footerContent);
        return NONE;
	}
	private String unitlist() throws IOException{
        String head_nav = "channel";
        HashMap map = new HashMap();
        map.put("channel", this.channel);
        List unitLists =new ArrayList();
        String unittypeIds = param_util.safeGetStringParam("unittypeId");
        if(unittypeIds.indexOf(",") >= 0){
            String[] aunittypeIds = unittypeIds.split(",");
            String unitType = "";
            for(int i = 0 ; i<aunittypeIds.length ; i++){
            	String tId = aunittypeIds[i];
                int unittypeId = Integer.parseInt(tId);
                UnitType oUnitType = unitTypeService.getUnitTypeById(unittypeId);
                if(oUnitType != null){
                    String unitType1 = oUnitType.getUnitTypeName();
                    List<Unit> unlitList = unitService.getChildUnitListByUnitType(unitType1);
                    unitType = unitType + unitType1;
                    if(unlitList != null){
                        unitLists.add(unlitList);
                    }
                }
            }
            map.put("unlitList", unitLists);
            map.put("unitType", unitType);
        }else{    
            int unittypeId = Integer.parseInt(unittypeIds);
            UnitType oUnitType = unitTypeService.getUnitTypeById(unittypeId);
            String unitType="";
            if(oUnitType != null){
                unitType = oUnitType.getUnitTypeName();
                List<Unit> unlitList = unitService.getChildUnitListByUnitType(unitType);
                if(unlitList != null){
                    unitLists.add(unlitList);
                }
            }
            map.put("unlitList", unitLists);
            map.put("unitType", unitType);
        }
        if(this.channel.getSkin() == null){
            skin = "template1";
		}else if(this.channel.getSkin().length() == 0){
            skin = "template1";
		}else{
            skin = this.channel.getSkin();
		}

        String mainContent = templateProcessor.processTemplate(map, "/WEB-INF/channel/" + skin + "/channel_unitlist.ftl", "utf-8");
		String headerContent = GenerateContentFromTemplateString(this.channel.getHeaderTemplate(), head_nav,null);
		String footerContent = GenerateContentFromTemplateString(this.channel.getFooterTemplate(),null,null);
		String indexPageTemplate = GenerateContentFromTemplateString(this.channel.getIndexPageTemplate(),null,null);
        
		PrintWriter out = response.getWriter();
        if(headerContent.length() == 0 && footerContent.length() == 0 && mainContent.length() == 0){
            out.println("该频道没有指定模板，无法显示页面内容。");
            return NONE;
        }
        out.println(headerContent);
        out.println(mainContent);
        out.println(footerContent);
        return NONE;	
	
	}
	
	private String photolist() throws IOException{
		HashMap map = new HashMap();
        map.put("channel", this.channel);
        String head_nav = "photo";
        
        //# 输出页头
        String headerContent = GenerateContentFromTemplateString(this.channel.getHeaderTemplate(), head_nav,null);
        String footerContent = GenerateContentFromTemplateString(this.channel.getFooterTemplate(), head_nav,null);       
        //# 输出主体部分：
        //# 分类树
        CategoryTreeModel photo_category = categoryService.getCategoryTree("channel_photo_" + this.channel.getChannelId());
        map.put("photo_category", photo_category);
        
        Integer channelCateId = param_util.safeGetIntParam("channelCateId");
        ChannelPhotoQuery qry = new ChannelPhotoQuery(" cp.photoId, photo.title, photo.createDate, photo.href, cp.userId, user.loginName, user.trueName as userTrueName ");
        qry.setRequest(request);
        qry.channelId = this.channel.getChannelId();        
        if(channelCateId != 0){            
            qry.custormAndWhereClause = " channelCate LIKE '%/" + channelCateId + "/%'";
        }
		Pager pager = param_util.createPager();
		pager.setItemName("图片");
		pager.setItemUnit("张");
		pager.setPageSize(20);
        
        List photo_list = (List)qry.query_map(pager);
            
        map.put("photo_list", photo_list);
        map.put("pager", pager);
        
        if(this.channel.getSkin() == null){
            skin = "template1";
        }else if(this.channel.getSkin().length() == 0){
            skin = "template1";
        }else{
            skin = this.channel.getSkin();
        }
                    
        String mainContent = templateProcessor.processTemplate(map, "/WEB-INF/channel/" + skin + "/channel_photo.ftl", "utf-8");
        PrintWriter out = response.getWriter();
        if(headerContent.length() == 0 && footerContent.length() == 0 && mainContent.length() == 0){
            out.println("该频道没有指定模板，无法显示页面内容。");
            return NONE;
        }
        out.println(headerContent);
        out.println(mainContent);
        out.println(footerContent);
        return NONE;	
	}
	private String videolist() throws IOException{
		HashMap map = new HashMap();
        map.put("channel", this.channel);
        String head_nav = "video";
        
        //# 输出页头
        String headerContent = GenerateContentFromTemplateString(this.channel.getHeaderTemplate(), head_nav,null);
        String footerContent = GenerateContentFromTemplateString(this.channel.getFooterTemplate(), head_nav,null);
        //# 输出主体部分：
        //# 分类树
        CategoryTreeModel video_category = categoryService.getCategoryTree("channel_video_" + this.channel.getChannelId());
        map.put("video_category", video_category);
        
        Integer channelCateId = param_util.safeGetIntParam("channelCateId");
        ChannelVideoQuery qry = new ChannelVideoQuery("cv.videoId, video.title, video.createDate, video.flvThumbNailHref, cv.userId, user.loginName, user.trueName");
        qry.setRequest(request);
        qry.channelId = this.channel.getChannelId();        
        if(channelCateId != 0){            
            qry.custormAndWhereClause = " channelCate LIKE '%/" + channelCateId + "/%'";
        }
		Pager pager = param_util.createPager();
		pager.setItemName("视频");
		pager.setItemUnit("个");
		pager.setPageSize(20);
        List video_list =(List) qry.query_map(pager);

        map.put("video_list", video_list);
        map.put("pager", pager);
        
        if(this.channel.getSkin() == null){
            skin = "template1";
        }else if(this.channel.getSkin().length() == 0){
            skin = "template1";
        }else{
            skin = this.channel.getSkin();
        }
                    
        String mainContent = templateProcessor.processTemplate(map, "/WEB-INF/channel/" + skin + "/channel_video.ftl", "utf-8");
        PrintWriter out = response.getWriter();
        if(headerContent.length() == 0 && footerContent.length() == 0 && mainContent.length() == 0){
            out.println("该频道没有指定模板，无法显示页面内容。");
            return NONE;
        }
        out.println(headerContent);
        out.println(mainContent);
        out.println(footerContent);
        return NONE;		
	}
	private String resourcelist() throws IOException{
		HashMap map = new HashMap();
        map.put("channel", this.channel);
        String head_nav = "resource";
        
        //# 输出页头
        String headerContent = GenerateContentFromTemplateString(this.channel.getHeaderTemplate(),head_nav,null);
        String footerContent = GenerateContentFromTemplateString(this.channel.getFooterTemplate(),head_nav,null);
        
        //# 输出主体部分：
        //# 分类树
        CategoryTreeModel resource_category = categoryService.getCategoryTree("channel_resource_" + this.channel.getChannelId());
        map.put("resource_category", resource_category);
        
        Integer channelCateId = param_util.safeGetIntParam("channelCateId");
        ChannelResourceQuery qry = new ChannelResourceQuery(" cr.resourceId, resource.href, resource.title, resource.fsize, resource.createDate, cr.viewCount,user.loginName, user.trueName as userTrueName ");
        qry.setRequest(request);
        qry.channelId = this.channel.getChannelId();
        if(channelCateId != 0){            
            qry.custormAndWhereClause = " channelCate LIKE '%/" + channelCateId + "/%'";
        }
		Pager pager = param_util.createPager();
		pager.setItemName("资源");
		pager.setItemUnit("个");
		pager.setPageSize(20);
        List resource_list = (List )qry.query_map(pager);
            
        if(this.channel.getSkin() == null){
            skin = "template1";
        }else if(this.channel.getSkin().length() == 0){
            skin = "template1";
        }else{
            skin = this.channel.getSkin();
        }
                        
        map.put("resource_list", resource_list);
        map.put("pager", pager);
        String mainContent = templateProcessor.processTemplate(map, "/WEB-INF/channel/" + skin + "/channel_resource.ftl","utf-8");
        PrintWriter out = response.getWriter();
        if(headerContent.length() == 0 && footerContent.length() == 0 && mainContent.length() == 0){
            out.println("该频道没有指定模板，无法显示页面内容。");
            return NONE;
        }
        out.println(headerContent);
        out.println(mainContent);
        out.println(footerContent);
        return NONE;	
	}
	private String articlelist() throws IOException{
        if(this.channel.getSkin() == null){
            skin = "template1";
        }else if(this.channel.getSkin().length() == 0){
            skin = "template1";
        }else{
            skin = this.channel.getSkin();
        }
        
        HashMap map = new HashMap();
        map.put("channel", this.channel);
        String head_nav = "article";
        //# 输出页头
        String headerContent = GenerateContentFromTemplateString(this.channel.getHeaderTemplate(), head_nav,null);
        String footerContent = GenerateContentFromTemplateString(this.channel.getFooterTemplate(), head_nav,null) ;       
        //# 输出主体部分：       
        Integer categoryId = param_util.safeGetIntParam("categoryId");
        CategoryTreeModel article_category =null;
        if(categoryId == 0){
            article_category = categoryService.getCategoryTree("channel_article_" + this.channel.getChannelId());
        }else{
            article_category = categoryService.getCategoryTree("channel_article_" + this.channel.getChannelId(),categoryId);
        }
        map.put("article_category", article_category);
        map.put("categoryId", categoryId);
        
        Integer channelCateId = param_util.safeGetIntParam("channelCateId");
        if(channelCateId == 0){
            channelCateId = categoryId;
        }
        ChannelArticleQuery qry = new ChannelArticleQuery("ca.articleId, ca.title, ca.createDate, ca.userId,ca.typeState, ca.loginName, ca.userTrueName");
        qry.setRequest(request);
        qry.channelId = this.channel.getChannelId();
        qry.articleState = true;
        if(channelCateId != 0){            
            qry.custormAndWhereClause = " channelCate LIKE '%/" + channelCateId + "/%'";
        }
		Pager pager = param_util.createPager();
		pager.setItemName("文章");
		pager.setItemUnit("篇");
		pager.setPageSize(20);
		List article_list = (List)qry.query_map(pager);
        map.put("article_list", article_list);
        map.put("pager", pager);
        String mainContent = templateProcessor.processTemplate(map, "/WEB-INF/channel/" + skin + "/channel_article.ftl", "utf-8");        
        PrintWriter out = response.getWriter();
        if(headerContent.length() == 0 && footerContent.length() == 0 && mainContent.length() == 0){
            out.println("该频道没有指定模板，无法显示页面内容。");
            return NONE;
        }
        out.println(headerContent);
        out.println(mainContent);
        out.println(footerContent);
        return NONE;
	}
	private String grouplist() throws IOException{
		HashMap map = new HashMap();
        map.put("channel", this.channel);
        String head_nav = "group";
        
        //# 输出页头
        String headerContent = GenerateContentFromTemplateString(this.channel.getHeaderTemplate(), head_nav,null);
        String footerContent = GenerateContentFromTemplateString(this.channel.getFooterTemplate(), head_nav,null);
        //# 输出主体部分：
        
        ChannelGroupQuery qry = new ChannelGroupQuery(" g.groupId, g.groupName, g.groupTitle, g.groupIcon, g.isRecommend, g.isBestGroup," +
                                "g.subjectId, g.gradeId, g.userCount, g.visitCount, g.createDate, g.groupIntroduce, g.groupTags," +
                                "cu.user");
        qry.channelId = this.channel.getChannelId();
        
		Pager pager = param_util.createPager();
		pager.setItemName("协作组");
		pager.setItemUnit("个");
		pager.setPageSize(20);
		pager.setTotalRows(qry.count());
		
        List group_list = (List)qry.query_map(pager);
            
        map.put("group_list", group_list);
        map.put("pager", pager);
        
        if(this.channel.getSkin() == null){
            skin = "template1";
        }else if(this.channel.getSkin().length() == 0){
            skin = "template1";
        }else{
            skin = this.channel.getSkin();
        }
                    
        String mainContent = templateProcessor.processTemplate(map, "/WEB-INF/channel/" + skin + "/channel_group.ftl", "utf-8");
        PrintWriter out = response.getWriter();
        if(headerContent.length() == 0 && footerContent.length() == 0 && mainContent.length() == 0){
            out.println("该频道没有指定模板，无法显示页面内容。");
            return NONE;
        }
        
        out.println(headerContent);
        out.println(mainContent);
        out.println(footerContent);
        return NONE;
	}
	private String userlist() throws IOException{
		HashMap map = new HashMap();
        map.put("channel", this.channel);
        String head_nav = "user";
        
        //# 输出页头
        String headerContent = GenerateContentFromTemplateString(this.channel.getHeaderTemplate(), head_nav,null);
        String footerContent = GenerateContentFromTemplateString(this.channel.getFooterTemplate(), head_nav,null);
        //# 输出主体部分：
        ChannelUserQuery qry = new ChannelUserQuery(" cu.userId, user.loginName, user.userTags, user.articleCount, user.userIcon, user.blogName,user.trueName, user.createDate, user.visitCount, user.myArticleCount, user.otherArticleCount, user.resourceCount, user.commentCount,user.userScore ");
        qry.setRequest(request);
        qry.channelId = channel.getChannelId();
        
        
		Pager pager = param_util.createPager();
		pager.setItemName("工作室");
		pager.setItemUnit("个");
		pager.setPageSize(20);
		pager.setTotalRows(qry.count());
        List user_list = (List)qry.query_map(pager);
            
        map.put("user_list", user_list);
        map.put("pager", pager);
        if (this.channel.getSkin() == null){
            skin = "template1";
        }else if (this.channel.getSkin().length() == 0){
            skin = "template1";
        }else{
            skin = this.channel.getSkin();
        }
        String mainContent = templateProcessor.processTemplate(map, "/WEB-INF/channel/" + skin + "/channel_user.ftl", "utf-8");
        PrintWriter out = response.getWriter();
        if(headerContent.length() == 0 && footerContent.length() == 0 && mainContent.length() == 0){
            out.println("该频道没有指定模板，无法显示页面内容。");
            return NONE;
        }
        
        out.println(headerContent);
        out.println(mainContent);
        out.println(footerContent);
        return NONE;
	}
	private String show() throws IOException{
        String head_nav = "channel";
        PrintWriter out = response.getWriter();
        String skin = param_util.safeGetStringParam("theme");
        String headerContent = "";
        String footerContent = "";
        String indexPageTemplate = "";
        if(skin.length() == 0){
            headerContent = GenerateContentFromTemplateString(this.channel.getHeaderTemplate(), head_nav,null);
            footerContent = GenerateContentFromTemplateString(this.channel.getFooterTemplate(),null,null);
            indexPageTemplate = GenerateContentFromTemplateString(this.channel.getIndexPageTemplate(),null,null);
        }else{
            String cssStyle = getFileContent("/css/channel/" + skin + "/common.css");
            cssStyle = cssStyle.replace("background:url(", "background:url(../css/channel/" + skin + "/");
            
            String headerTemplate = getFileContent("/WEB-INF/channel/" + this.skin + "/Header.ftl");
            headerContent = GenerateContentFromTemplateString(headerTemplate, head_nav, cssStyle);
            String footerTemplate = getFileContent("/WEB-INF/channel/" + this.skin + "/Footer.ftl");
            footerContent = this.GenerateContentFromTemplateString(footerTemplate,null,null);
            indexPageTemplate = getFileContent("/WEB-INF/channel/" + this.skin + "/Main.ftl");
            indexPageTemplate = this.GenerateContentFromTemplateString(indexPageTemplate,null,null);
        }
        if(headerContent.length() == 0 && footerContent.length() == 0 && indexPageTemplate.length() == 0){
            out.println("该频道没有指定模板,无法显示页面内容。");
            return NONE;
        }
                
        out.println(headerContent);
        out.println(indexPageTemplate);
        out.println(footerContent);	
        return NONE;		
	}
    private String getFileContent(String fp){
    	ServletContext ctx = request.getSession().getServletContext();
        String filePath = ctx.getRealPath(fp);
        return CommonUtil.readFileToString(filePath, "utf-8");
    }
    
    public void setUnitTypeService(UnitTypeService unitTypeService){
    	this.unitTypeService = unitTypeService;
    }
    public UnitTypeService getUnitTypeService(){
    	return this.unitTypeService;
    }
    
    public void setPlacardService(PlacardService placardService){
    	this.placardService = placardService;
    }
    public PlacardService getPlacardService(){
    	return this.placardService;
    }    
}
