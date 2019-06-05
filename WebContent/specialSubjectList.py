from cn.edustar.jitar.util import ParamUtil
from base_specialsubject_page import *
from base_action import *
from specialSubjectArticle_query import SpecialSubjectArticleQuery
from photo_query import PhotoQuery
from plugintopic_query import *

class specialSubjectList(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.specialSubject_svc = __spring__.getBean("specialSubjectService")
        self.type = ""
        self.specialSubjectId = 0
        self.specialSubject = None
        
    def execute(self):
        response.contentType = "text/html; charset=UTF-8"

        self.type = self.params.safeGetStringParam("type")
        self.specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
        if self.specialSubjectId < 1:
            self.addActionError(u"无效的专题标识。")
            return self.ERROR
        self.specialSubject = self.specialSubject_svc.getSpecialSubject(self.specialSubjectId)
        if self.specialSubject == None:
            self.addActionError(u"无法加载专题对象。")
            return self.ERROR
        
        request.setAttribute("specialSubject", self.specialSubject)
        request.setAttribute("head_nav", "special_subject")
        
        if self.type == "photo":
            return self.photoList()
        elif self.type == "article":
            return self.articleList()
        elif self.type == "specialsubject":
            return self.specialsubjectList()
        elif self.type == "topic":
            return self.topicList()
        elif self.type == "video":
            return self.videoList()
        else:
            self.addActionError(u"无效的操作命令。")
            return self.ERROR
        
    def photoList(self):
        pager = self.params.createPager()
        pager.itemName = u"图片"
        pager.itemUnit = u"张"
        pager.pageSize = 48
        qry = PhotoQuery(""" p.photoId, p.title, p.userId,p.href, p.userTrueName,u.loginName """)
        qry.specialSubjectId = self.specialSubjectId
        pager.totalRows = qry.count()
        photo_list = qry.query_map(pager)
        request.setAttribute("photo_list", photo_list)
        request.setAttribute("pager", pager)
        return "/WEB-INF/ftl/specialsubject/specialsubject_photo_list.ftl"
    
    def articleList(self):
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        qry = SpecialSubjectArticleQuery("ssa.articleId, ssa.title, ssa.userId, ssa.userTrueName,ssa.createDate,ssa.typeState")
        qry.specialSubjectId = self.specialSubjectId
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager)
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)
        return "/WEB-INF/ftl/specialsubject/specialsubject_article_list.ftl"
    
    def specialsubjectList(self):
        pager = self.params.createPager()
        pager.itemName = u"专题"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        qry = SpecialSubjectQuery(""" ss.specialSubjectId,ss.title,ss.logo,ss.description,ss.createDate,ss.expiresDate """)
        qry.objectType = "system"
        pager.totalRows = qry.count()
        specialsubject_list = qry.query_map(pager)
        request.setAttribute("specialsubject_list", specialsubject_list)
        request.setAttribute("pager", pager)
        return "/WEB-INF/ftl/specialsubject/specialsubject_specialsubject_list.ftl"
    
    def topicList(self):
        pager = self.params.createPager()
        pager.itemName = u"讨论"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        qry = PlugInTopicQuery(""" pt.plugInTopicId,pt.title, pt.createUserId, pt.createUserName, pt.createDate """)
        qry.parentGuid = self.specialSubject.objectGuid
        pager.totalRows = qry.count()
        topic_list = qry.query_map(pager)
        request.setAttribute("topic_list", topic_list)
        request.setAttribute("pager", pager)
        return "/WEB-INF/ftl/specialsubject/specialsubject_topic_list.ftl"
    
    def videoList(self):
        pager = self.params.createPager()
        pager.itemName = u"视频"
        pager.itemUnit = u"个"
        pager.pageSize = 24
        qry = VideoQuery(""" v.videoId, v.flvHref, v.flvThumbNailHref, v.title,v.userId,v.createDate """)
        qry.specialSubjectId = self.specialSubjectId
        pager.totalRows = qry.count()
        video_list = qry.query_map(pager)
        request.setAttribute("video_list", video_list)
        request.setAttribute("pager", pager)
        return "/WEB-INF/ftl/specialsubject/specialsubject_video_list.ftl"
