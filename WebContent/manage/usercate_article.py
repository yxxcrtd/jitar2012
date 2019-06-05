from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import ContentSpace
from java.util import Date
from contentspacearticle_query import *

class usercate_article(BaseAdminAction):
    # 定义要返回的页面常量.
    ADMIN_MAIN = "/WEB-INF/ftl/admin/main.ftl"
    ERROR = "/WEB-INF/ftl/Error.ftl"    
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        self.categoryService = __spring__.getBean("categoryService")
    def execute(self):
        user = self.loginUser
        if user == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(user) == False and accessControlService.isSystemContentAdmin(user) == False:
            self.addActionError(u"没有管理权限，只有超级管理员和系统内容管理员才可以使用此功能。")
            return ActionResult.ERROR

        if self.contentSpaceService == None:
            self.addActionError(u"无法获得 contentSpaceService 服务，检查配置文件。")
            return ActionResult.ERROR
        f = self.params.safeGetStringParam("f")
        if f == "createUserId":
            k = self.params.safeGetStringParam("k")
            if k.isdigit() == False:
                self.addActionError(u"要按作者 id 搜索，请输入一个数字的作者id")
                return ActionResult.ERROR
            
            
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "move_cate":
                newcate = self.params.safeGetIntParam("new_cate")
                if newcate < 1:
                    self.addActionError(u"要移动分类，您需要选选择一个新的分类。")
                    return ActionResult.ERROR
                guids = self.params.safeGetIntValues("guid")
                for g in guids:
                    contentSpaceArticle = self.contentSpaceService.getContentSpaceArticleById(g)
                    if contentSpaceArticle != None:
                        if newcate != contentSpaceArticle.contentSpaceId:
                            contentSpaceArticle.setContentSpaceId(newcate)
                            self.contentSpaceService.saveOrUpdateArticle(contentSpaceArticle)        
            if cmd == "delete":
                guids = self.params.safeGetIntValues("guid")
                for g in guids:
                    contentSpaceArticle = self.contentSpaceService.getContentSpaceArticleById(g)
                    if contentSpaceArticle != None:
                        self.contentSpaceService.deleteContentSpaceArticle(contentSpaceArticle)
                        
        self.get_artilce_list()
        self.get_content_space_cate()
        return "/WEB-INF/ftl/admin/usercate_article.ftl"
    
    
    def get_artilce_list(self):
        contentSpaceId = self.params.safeGetIntParam("contentSpaceId")
        qry = ContentSpaceArticleQuery(" cs.spaceName, csa.contentSpaceArticleId, csa.title,csa.contentSpaceId,csa.createDate,csa.createUserId,csa.viewCount, csa.pictureUrl ")
        qry.ownerType = ContentSpace.CONTENTSPACE_OWNERTYPE_DEFAULT
        qry.ownerId = 0
        if contentSpaceId != 0 :
            qry.contentSpaceId = contentSpaceId
        
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        pager.pageSize = 30
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("contentSpaceId", contentSpaceId)
        request.setAttribute("article_list", article_list)        
        
    def get_content_space_cate(self):
        #catelist = self.contentSpaceService.getAllContentSpaceList(0, 0)
        #request.setAttribute("catelist", catelist)
        
        #得到了categoryList
        categoryList = self.contentSpaceService.getContentSpaceTreeList(ContentSpace.CONTENTSPACE_OWNERTYPE_DEFAULT, 0)
        #需要把categoryList转换为CategoryTreeModel
        category_tree = self.categoryService.getCategoryTree(categoryList);
        request.setAttribute("category_tree", category_tree)
        
