# coding=utf-8
from java.util import HashMap, ArrayList
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import AccessControl
from cn.edustar.jitar.data import Command, BaseQuery
from base_action import BaseAction
from channel_query import *
from placard_query import PlacardQuery

class ChannelPage(BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.channelPageService = __spring__.getBean("channelPageService")        
        self.templateProcessor = __spring__.getBean("templateProcessor")
        self.categoryService = __jitar__.categoryService     
        self.channelId = self.params.safeGetIntParam("channelId")
        self.channel = self.channelPageService.getChannel(self.channelId)      
        self.unitService = __jitar__.unitService
        self.skin = "template1"
        if self.channel != None :
            if self.channel.skin != None and self.channel.skin != "" :
                self.skin = self.channel.skin
    def GenerateSubjectNavContent(self, mdl):
        subjectService = __spring__.getBean("subjectService")
        mGradeId = subjectService.getGradeIdList()
        MetaGrade = ArrayList()
        metaSubject = ArrayList()
        for grade in mGradeId:
            mGrade = subjectService.getGrade(int(grade))
            MetaGrade.add(mGrade)
            subj = subjectService.getSubjectByGradeId(int(grade))
            m = ArrayList()
            if subj != None:
                for sj in range(0, subj.size()):
                    m.add(subj[sj].metaSubject)
                metaSubject.add({"gradeName" : mGrade.gradeName, "gradeId" : grade, "metaSubject" : m })
        map = HashMap()
        map.put("metaGrade", MetaGrade)
        map.put("meta_Grade", MetaGrade)
        map.put("SubjectNav", metaSubject)
        
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/ftl/site_subject_nav.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)            
        return content
    
    def GenerateChannelNavContent(self, mdl, strCurrentNav=None):
        siteNavService = __spring__.getBean("siteNavService")
        SiteNavList = siteNavService.getAllSiteNav(False, 3, self.channel.channelId)
        map = HashMap()
        map.put("channel", self.channel)
        map.put("SiteNavList", SiteNavList)
        map.put("loginUser", self.loginUser)
        map.put("head_nav", strCurrentNav)       
        
        # 判断权限        
        AdminType = self.GetAdminType()        
        map.put("AdminType", AdminType)
        if mdl.template == None:
            request.setAttribute("ru", request.getScheme() + "://" + request.getServerName() + ":" + str(request.getServerPort()) + request.getContextPath())
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/ChannelNav.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content    
    
    def GenerateSiteNavContent(self, mdl):
        siteNavService = __spring__.getBean("siteNavService")
        SiteNavList = siteNavService.getAllSiteNav(False, 0, 0)
        map = HashMap()
        map.put("SiteNavList", SiteNavList)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/SiteNav.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
    
    def GenerateGroupListContent(self, mdl):
        count = mdl.showCount
        if count == None:count = 10
        qry = ChannelGroupQuery(" g.groupId, g.groupName, g.groupTitle, g.groupIcon, g.groupIntroduce ")
        qry.channelId = self.channel.channelId
        group_list = qry.query_map(count)
        map = HashMap()
        map.put("group_list", group_list)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/GroupList.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
        
    def GenerateArticleListContent(self, mdl):
        count = mdl.showCount
        if count == None:count = 10
        qry = ChannelArticleQuery(" ca.articleId, ca.title, ca.createDate,ca.typeState, ca.userId, ca.loginName, ca.userTrueName ")
        qry.channelId = self.channel.channelId
        article_list = qry.query_map(count)
        map = HashMap()
        map.put("NewArticleList", article_list)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/ArticleList.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
    
    def GenerateArticleCategory(self, mdl):        
        article_category = self.categoryService.getCategoryTree("channel_article_" + str(self.channelId))
        map = HashMap()
        map.put("channel", self.channel)
        map.put("ArticleCategory", article_category)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/ArticleCategory.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
    
    def GenerateSearch(self, mdl):
        map = HashMap()
        map.put("channel", self.channel)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/Search.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
      
    def GenerateStat(self, mdl):
        map = HashMap()
        qry = ChannelArticleQuery("ca.articleId")
        qry.channelId = self.channel.channelId
        article_count = qry.count()
        map.put("article_count", article_count)
        
        qry = ChannelResourceQuery("cr.resourceId")
        qry.channelId = self.channel.channelId
        resource_count = qry.count()
        map.put("resource_count", resource_count)
        
        qry = ChannelPhotoQuery("cp.photoId")
        qry.channelId = self.channel.channelId
        photo_count = qry.count()
        map.put("photo_count", photo_count)
        
        qry = ChannelVideoQuery("cv.videoId")
        qry.channelId = self.channel.channelId
        video_count = qry.count()
        map.put("video_count", video_count)
        
        qry = ChannelUserQuery("cu.channelUserId")
        qry.channelId = self.channel.channelId
        user_count = qry.count()
        map.put("user_count", user_count)
        
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/Stat.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
    
    def GenerateUserList(self, mdl):
        count = mdl.showCount
        if count == None:count = 10
        qry = ChannelUserQuery(" cu.userId, user.loginName, user.trueName, user.userIcon ")
        qry.channelId = self.channelId
        user_list = qry.query_map(count)
        map = HashMap()
        map.put("user_list", user_list)
        map.put("channel", self.channel)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/UserList.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
    
    def GenerateResourceCategory(self, mdl):        
        resource_category = self.categoryService.getCategoryTree("channel_resource_" + str(self.channelId))
        map = HashMap()
        map.put("channel", self.channel)
        map.put("resource_category", resource_category)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/ResourceCategory.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
    
    def GenerateResourceList(self, mdl):
        count = mdl.showCount
        if count == None:count = 10
        qry = ChannelResourceQuery(" cr.resourceId, cr.userId, resource.href, resource.title, resource.fsize, resource.createDate, cr.viewCount,user.loginName, user.trueName as userTrueName ")
        qry.channelId = self.channelId
        resource_list = qry.query_map(count)
        map = HashMap()
        map.put("channel", self.channel)
        map.put("resource_list", resource_list)    
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/ResourceList.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
    
    def GenerateVideoList(self, mdl):
        count = mdl.showCount
        if count == None:count = 10
        qry = ChannelVideoQuery(" cv.videoId, video.title, video.createDate, cv.userId, user.loginName, user.trueName as userTrueName, video.flvThumbNailHref") 
        qry.channelId = self.channelId
        video_list = qry.query_map(count)
        map = HashMap()
        map.put("channel", self.channel)
        map.put("video_list", video_list)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/VideoList.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
    
    def GenerateVideoCategory(self, mdl):
        video_category = self.categoryService.getCategoryTree("channel_video_" + str(self.channelId))
        map = HashMap()
        map.put("channel", self.channel)
        map.put("video_category", video_category)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/VideoCategory.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
    
    def GeneratePhotoList(self, mdl):
        pw = request.getSession().getServletContext().getInitParameter("photo_width")
        ph = request.getSession().getServletContext().getInitParameter("photo_height")
        if pw == None or pw == "" or pw.isdigit() == False : pw = 150
        if ph == None or ph == "" or ph.isdigit() == False : ph = 120
        request.setAttribute("pw", pw)
        request.setAttribute("ph", ph)
        qry = ChannelPhotoQuery(" cp.photoId, photo.title, photo.href, photo.createDate, user.loginName, user.trueName as userTrueName ")
        qry.channelId = self.channelId
        count = mdl.showCount
        if count == None:count = 10
        photo_list = qry.query_map(count)
        map = HashMap()
        map.put("channel", self.channel)
        map.put("photo_list", photo_list)
        map.put("pw", pw)
        map.put("ph", ph)
        
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/PhotoList.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
    
    def GeneratePhotoCategory(self, mdl):        
        photo_category = self.categoryService.getCategoryTree("channel_photo_" + str(self.channelId))
        map = HashMap()
        map.put("channel", self.channel)
        map.put("photo_category", photo_category)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/PhotoCategory.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)   
        return content    
    
    def GenerateBulletin(self, mdl):
        qry = PlacardQuery(" pld.id, pld.title, pld.createDate ")
        qry.objType = 19
        qry.objId = self.channel.channelId
        count = mdl.showCount
        if count == None:count = 10
        placard_list = qry.query_map()
        map = HashMap()
        map.put("channel", self.channel)
        map.put("placard_list", placard_list)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/Bulletin.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
    
    def GenerateLogo(self, mdl):
        map = HashMap()
        map.put("channel", self.channel)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/Logo.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)
        return content
    
    def GenerateContentFromTemplateString(self, strTemplate, strCurrentNav=None, css=None):
        if strTemplate == None or strTemplate == "":return ""
        map = HashMap()
        map.put("loginUser", self.loginUser)
        AdminType = self.GetAdminType()        
        map.put("AdminType", AdminType)
        map.put("channel", self.channel)
        
        if css != None:
            map.put("cssStyle", css)
        content = self.templateProcessor.processStringTemplate(map, strTemplate)
        moduleList = self.channelPageService.getLabel(strTemplate)
        for m in moduleList:
            content = content.replace("#[" + m + "]", self.GetContentByModuleName(m, strCurrentNav))
        return content
    
    def GetAdminType(self):
        AdminType = "|"
        if self.loginUser == None: return ""
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser):
            AdminType = AdminType + "SystemSuperAdmin|"
        if accessControlService.isSystemUserAdmin(self.loginUser):
            AdminType = AdminType + "SystemUserAdmin|"
        if accessControlService.isSystemContentAdmin(self.loginUser):
            AdminType = AdminType + "SystemContentAdmin|"
        if accessControlService.getAccessControlByUserAndObject(self.loginUser.userId, AccessControl.OBJECTTYPE_CHANNELSYSTEMADMIN, self.channel.channelId) != None:
            AdminType = AdminType + "ChannelSystemAdmin|"
        if accessControlService.getAccessControlByUserAndObject(self.loginUser.userId, AccessControl.OBJECTTYPE_CHANNELUSERADMIN, self.channel.channelId) != None:
            AdminType = AdminType + "ChannelUserAdmin|"
        if accessControlService.getAccessControlByUserAndObject(self.loginUser.userId, AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN, self.channel.channelId) != None:
            AdminType = AdminType + "ChannelContentAdmin|"
        if AdminType == "|" : AdminType = ""
        return AdminType
    
    # 自定义分类模块
    def GenerateCustomCategory(self, mdl):
        count = mdl.showCount
        cateItemType = mdl.cateItemType  # channel_article_频道Id  channel_resource_频道Id  channel_photo_频道Id  channel_video_频道Id
        cateId = mdl.cateId  # 分类id
        map = HashMap()
        # 图片
        if cateItemType[:13] == "channel_photo":
            if count == None:count = 10
            qry = ChannelPhotoQuery(" cp.photoId, photo.title, photo.href, photo.createDate, user.loginName, user.trueName as userTrueName ")
            qry.channelId = self.channelId
            if cateId != 0:            
                qry.custormAndWhereClause = " channelCate LIKE '%/" + str(cateId) + "/%'"
            # print "查询频道图片"    
            photo_list = qry.query_map(count)
            map.put("list", photo_list)
        # 视频
        elif cateItemType[:13] == "channel_video":
            if count == None:count = 10
            qry = ChannelVideoQuery(" cv.videoId, video.title, video.createDate,cv.userId, user.loginName, user.trueName as userTrueName, video.flvThumbNailHref") 
            qry.channelId = self.channelId
            if cateId != 0:            
                qry.custormAndWhereClause = " channelCate LIKE '%/" + str(cateId) + "/%'"
            # print "查询频道视频"    
            video_list = qry.query_map(count)
            map.put("list", video_list)
        # 文章
        elif cateItemType[:15] == "channel_article":
            if count == None:count = 10
            qry = ChannelArticleQuery("ca.articleId, ca.title, ca.createDate, ca.userId,ca.typeState, ca.loginName, ca.userTrueName")
            qry.channelId = self.channel.channelId
            qry.articleState = True
            if cateId != 0:            
                qry.custormAndWhereClause = " channelCate LIKE '%/" + str(cateId) + "/%'"
            article_list = qry.query_map(count) 
            # print "查询频道文章:分类 = "   + str(cateId) + ",得到的文件数量:" + str(article_list.size())
            map.put("list", article_list)
        # 资源    
        elif cateItemType[:16] == "channel_resource":
            if count == None:count = 10
            qry = ChannelResourceQuery(" cr.resourceId, resource.href, resource.title, resource.fsize, resource.createDate, cr.viewCount,user.loginName, user.trueName as userTrueName ")
            qry.channelId = self.channel.channelId
            if cateId != 0:            
                qry.custormAndWhereClause = " channelCate LIKE '%/" + str(cateId) + "/%'"
            # print "查询频道资源"    
            resource_list = qry.query_map(count)
            map.put("list", resource_list)
        
        
        map.put("channel", self.channel)
        map.put("cateItemType", cateItemType)
        map.put("cateId", cateId)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/CustomCategory.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)   
        return content
    # 机构展示模块    
    def GenerateUnitShow(self, mdl):
        unitType = mdl.unitType
        # 得到学校单位的列表
        unlitList = self.unitService.getChildUnitListByUnitType(unitType)
        map = HashMap()
        map.put("channel", self.channel)
        map.put("unitType", unitType)
        map.put("unlitList", unlitList)
        if mdl.template == None:
            content = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + self.skin + "/UnitShow.ftl", "utf-8")
        else:
            content = self.templateProcessor.processStringTemplate(map, mdl.template)   
        return content
        
    def GetContentByModuleName(self, moduleDisplayName, strCurrentNav=None):
        module = self.channelPageService.getChannelModuleByDisplayName(moduleDisplayName, self.channel.channelId)
        if module == None:return ""
        # print "module.moduleType="+module.moduleType
        if module.moduleType == "Custorm":
            if module.content != None:
                return module.content
            else:
                return ""
        elif module.moduleType == "ArticleList":
            content = self.GenerateArticleListContent(module)
            module.setContent(content)
            return content
        elif module.moduleType == "SubjectNav":
            content = self.GenerateSubjectNavContent(module)
            module.setContent(content)
            return content
        elif module.moduleType == "SiteNav":
            content = self.GenerateSiteNavContent(module)
            module.setContent(content)
            return content
        elif module.moduleType == "ChannelNav":
            content = self.GenerateChannelNavContent(module, strCurrentNav)
            module.setContent(content)
            return content
        elif module.moduleType == "ArticleCategory":
            content = self.GenerateArticleCategory(module)
            module.setContent(content)
            return content
        elif module.moduleType == "Search":
            content = self.GenerateSearch(module)
            module.setContent(content)
            return content
        elif module.moduleType == "Stat":
            content = self.GenerateStat(module)
            module.setContent(content)
            return content        
        elif module.moduleType == "ResourceList":
            content = self.GenerateResourceList(module)
            module.setContent(content)
            return content
        elif module.moduleType == "ResourceCategory":
            content = self.GenerateResourceCategory(module)
            module.setContent(content)
            return content
        elif module.moduleType == "Bulletin":
            content = self.GenerateBulletin(module)
            module.setContent(content)
            return content
        elif module.moduleType == "VideoList":
            content = self.GenerateVideoList(module)
            module.setContent(content)
            return content
        elif module.moduleType == "VideoCategory":
            content = self.GenerateVideoCategory(module)
            module.setContent(content)
            return content
        elif module.moduleType == "PhotoList":
            content = self.GeneratePhotoList(module)
            module.setContent(content)
            return content
        elif module.moduleType == "PhotoCategory":
            content = self.GeneratePhotoCategory(module)
            module.setContent(content)
            return content
        elif module.moduleType == "UserList":
            content = self.GenerateUserList(module)
            module.setContent(content)
            return content
        elif module.moduleType == "GroupList":
            content = self.GenerateGroupListContent(module)
            module.setContent(content)
            return content
        elif module.moduleType == "Logo":
            content = self.GenerateLogo(module)
            module.setContent(content)
            return content        
        elif module.moduleType == "CustomCategory":
            content = self.GenerateCustomCategory(module)
            module.setContent(content)
            return content        
        elif module.moduleType == "UnitShow":
            content = self.GenerateUnitShow(module)
            module.setContent(content)
            return content        
        else:
            return u"<h2>不存在的系统模块：" + module.displayName + u"</h2>"
