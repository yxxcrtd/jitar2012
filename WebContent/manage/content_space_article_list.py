from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import User, ContentSpaceArticle
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil
from java.util import Date
from contentspacearticle_query import *

class content_space_article_list(BaseAdminAction):
    # 定义要返回的页面常量.
    ADMIN_MAIN = "/WEB-INF/ftl/admin/main.ftl"
    ERROR = "/WEB-INF/ftl/Error.ftl"    
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        self.contentSpaceId = None
        self.contentSpaceArticleId = None
        
    def execute(self):
        user = self.loginUser
        if user == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(user) == False:
            self.addActionError(u"没有管理权限，只有超级管理员才可以使用此功能。")
            return ActionResult.ERROR

        if self.contentSpaceService == None:
            self.addActionError(u"无法获得 contentSpaceService 服务，检查配置文件。")
            return ActionResult.ERROR
        
        self.contentSpaceId = self.params.safeGetIntParam("id")
        if self.contentSpaceId == 0:
            self.addActionError(u"无效的分类，请选择一个自定义分类。")
            return ActionResult.ERROR
        contentSpace = self.contentSpaceService.getContentSpaceById(self.contentSpaceId)
        if contentSpace == None:
            self.addActionError(u"不能加载自定义分类对象，请选择一个正确的标识。")
            return ActionResult.ERROR
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "delete":
                guids = self.params.safeGetIntValues("guid")
                for g in guids:
                    contentSpaceArtice = self.contentSpaceService.getContentSpaceArticleById(g)
                    if contentSpaceArtice != None:
                        self.contentSpaceService.deleteContentSpaceArticle(contentSpaceArtice)
                        #更新分类的统计
                        contentSpace.articleCount = contentSpace.articleCount - 1
                        if contentSpace.articleCount < 0:
                            contentSpace.articleCount = 0
                        self.contentSpaceService.saveOrUpdateContentSpace(contentSpace)        
        
        self.get_artilce_list()
        
        request.setAttribute("contentSpace", contentSpace)
        return "/WEB-INF/ftl/admin/content_space_article_list.ftl"
    
    def get_artilce_list(self):
        qry = ContentSpaceArticleQuery(""" csa.contentSpaceArticleId, csa.title,csa.contentSpaceId,csa.createDate,csa.createUserId,csa.viewCount,cs.contentSpaceId """)
        qry.contentSpaceId = self.contentSpaceId
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        pager.pageSize = 30
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager)
        request.setAttribute("article_list", article_list)
