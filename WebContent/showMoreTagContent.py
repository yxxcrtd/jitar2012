from cn.edustar.jitar.util import ParamUtil
from tag_query import *
from site_config import SiteConfig

class showMoreTagContent:
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()
        tag_svc = __jitar__.getTagService()
        self.param = ParamUtil(request)
        tagId = self.param.getIntParam("tagId")
        tagType = self.param.safeGetStringParam("type")
        self.tag = tag_svc.getTag(tagId)
        if self.tag == None:
            actionErrors = [u"标签不存在。"]
            request.setAttribute("actionErrors", actionErrors)
            return "/WEB-INF/ftl/Error.ftl"
        
        if tagType == "article":
            self.get_tag_article_list()
        elif tagType == "photo":
            self.get_tag_photo_list()
        elif tagType == "user":
            self.get_tag_user_list()
        elif tagType == "resource":
            self.get_tag_resource_list()
        elif tagType == "preparecourse":
            self.get_tag_preparecourse_list()
        elif tagType == "group":
            self.get_tag_group_list()
        else:
            actionErrors = [u"无效的参数。"]
            request.setAttribute("actionErrors", actionErrors)
            return "/WEB-INF/ftl/Error.ftl"
        
        request.setAttribute("tag", self.tag)
        return "/WEB-INF/ftl/showMoreTagContent.ftl"
        
    def get_tag_article_list(self):
        qry = TagArticleQuery(""" a.title,a.articleId """)
        qry.tagId = self.tag.tagId
        self.pager = self.param.createPager()
        self.pager.itemName = u"文章"
        self.pager.itemUnit = u"篇"
        self.pager.totalRows = qry.count()
        self.pager.pageSize = 20
        request.setAttribute("pager", self.pager)
        tag_article_list = qry.query_map(self.pager)
        request.setAttribute("tag_article_list", tag_article_list)
        
    def get_tag_user_list(self):
        qry = TagUserQuery(""" u.loginName,u.trueName,u.nickName """)
        qry.tagId = self.tag.tagId
        self.pager = self.param.createPager()
        self.pager.itemName = u"用户"
        self.pager.itemUnit = u"个"
        self.pager.totalRows = qry.count()
        self.pager.pageSize = 20
        request.setAttribute("pager", self.pager)
        tag_user_list = qry.query_map(self.pager)
        request.setAttribute("tag_user_list", tag_user_list)
        
    def get_tag_resource_list(self):
        qry = TagResourceQuery(""" r.title,r.resourceId """)
        qry.tagId = self.tag.tagId
        self.pager = self.param.createPager()
        self.pager.itemName = u"资源"
        self.pager.itemUnit = u"个"
        self.pager.totalRows = qry.count()
        self.pager.pageSize = 20
        request.setAttribute("pager", self.pager)
        tag_resource_list = qry.query_map(self.pager)
        request.setAttribute("tag_resource_list", tag_resource_list)
        
    def get_tag_group_list(self):
        qry = TagGroupQuery(""" g.groupName,g.groupTitle """)
        qry.tagId = self.tag.tagId
        self.pager = self.param.createPager()
        self.pager.itemName = u"协作组"
        self.pager.itemUnit = u"个"
        self.pager.totalRows = qry.count()
        self.pager.pageSize = 20
        request.setAttribute("pager", self.pager)
        tag_group_list = qry.query_map(self.pager)
        request.setAttribute("tag_group_list", tag_group_list)
        
    def get_tag_preparecourse_list(self):
        qry = TagPrepareCourseQuery(""" pc.prepareCourseId,pc.title """)
        qry.tagId = self.tag.tagId
        self.pager = self.param.createPager()
        self.pager.itemName = u"备课"
        self.pager.itemUnit = u"个"
        self.pager.totalRows = qry.count()
        self.pager.pageSize = 20
        request.setAttribute("pager", self.pager)
        tag_preparecourse_list = qry.query_map(self.pager)
        request.setAttribute("tag_preparecourse_list", tag_preparecourse_list)
        
    def get_tag_photo_list(self):
        qry = TagPhotoQuery(""" p.photoId,p.title,p.userId """)
        qry.tagId = self.tag.tagId
        self.pager = self.param.createPager()
        self.pager.itemName = u"照片"
        self.pager.itemUnit = u"张"
        self.pager.totalRows = qry.count()
        self.pager.pageSize = 20
        request.setAttribute("pager", self.pager)
        tag_photo_list = qry.query_map(self.pager)
        request.setAttribute("tag_photo_list", tag_photo_list)