from cn.edustar.jitar.util import ParamUtil
from tag_query import *

class show_tag:
    
    def execute(self):
        tag_svc = __jitar__.getTagService()
        param = ParamUtil(request)
        tagId = param.getIntParam("tagId")
        tagName = param.safeGetStringParam("tagName")
        if tagId != 0:
            self.tag = tag_svc.getTag(tagId)
        else:
            self.tag = tag_svc.getTagByName(tagName)
        #print "tagName = ",tagName
        if self.tag == None:
            actionErrors = [u"标签不存在。"]
            request.setAttribute("actionErrors", actionErrors)
            return "/WEB-INF/ftl/Error.ftl"
        
        tag_svc.incTagViewCount(self.tag, 1)
        request.setAttribute("tag", self.tag)
        request.setAttribute("head_nav", "tags")            
        self.get_tag_article_list()
        self.get_tag_group_list()
        self.get_tag_preparecourse_list()
        self.get_tag_user_list()
        self.get_tag_resource_list()   
        self.get_tag_photo_list()  
        return "/WEB-INF/ftl/show_tag.ftl"
    
    def get_tag_article_list(self):
        qry = TagArticleQuery(""" a.title,a.articleId """)
        qry.tagId = self.tag.tagId
        tag_article_list = qry.query_map(10)
        request.setAttribute("tag_article_list", tag_article_list)
        
    def get_tag_user_list(self):
        qry = TagUserQuery(""" u.loginName,u.trueName,u.nickName """)
        qry.tagId = self.tag.tagId
        tag_user_list = qry.query_map(10)
        request.setAttribute("tag_user_list", tag_user_list)
        
    def get_tag_resource_list(self):
        qry = TagResourceQuery(""" r.title,r.resourceId """)
        qry.tagId = self.tag.tagId
        tag_resource_list = qry.query_map(10)
        request.setAttribute("tag_resource_list", tag_resource_list)
        
    def get_tag_group_list(self):
        qry = TagGroupQuery(""" g.groupName,g.groupTitle """)
        qry.tagId = self.tag.tagId
        tag_group_list = qry.query_map(10)
        request.setAttribute("tag_group_list", tag_group_list)
        
    def get_tag_preparecourse_list(self):
        qry = TagPrepareCourseQuery(""" pc.prepareCourseId,pc.title """)
        qry.tagId = self.tag.tagId
        tag_preparecourse_list = qry.query_map(10)
        request.setAttribute("tag_preparecourse_list", tag_preparecourse_list)
        
    def get_tag_photo_list(self):
        qry = TagPhotoQuery(""" p.photoId,p.title,p.userId """)
        qry.tagId = self.tag.tagId
        tag_photo_list = qry.query_map(10)
        request.setAttribute("tag_photo_list", tag_photo_list)
        
