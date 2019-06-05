from cn.edustar.jitar.pojos import Article
from specialSubjectArticle_query import SpecialSubjectArticleQuery
from base_action import *
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.jython import BaseAdminAction

class admin_specialsubject_article_list(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.specialSubjectId = 0
    
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN    
        
        accessControlService = __spring__.getBean("accessControlService")
        if False == accessControlService.isSystemContentAdmin(self.loginUser):
            self.addActionError(u"管理专题需要管理员权限。")
            return self.ERROR
        
        self.specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
        
        if self.specialSubjectId == 0:
            self.addActionError(u"无效的专题标识。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            specialSubjectService = __spring__.getBean("specialSubjectService")
            
            guid = self.params.safeGetIntValues("guid")
            for g in guid:
                specialSubjectService.deleteSubjectArticleByArticleId(g)          
        
        qry = SpecialSubjectArticleQuery(""" ssa.title,ssa.articleId,ssa.userId, ssa.loginName,ssa.userTrueName """)
        qry.specialSubjectId = self.specialSubjectId
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        sa_list = qry.query_map(pager)
        request.setAttribute("sa_list", sa_list)
        request.setAttribute("pager", pager)
        
        return "/WEB-INF/ftl/specialsubject/admin_specialsubject_article_list.ftl"