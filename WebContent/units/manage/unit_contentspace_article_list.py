from unit_page import UnitBasePage
from cn.edustar.jitar.pojos import ContentSpace
from contentspacearticle_query import *

class unit_contentspace_article_list(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        self.categoryService = __spring__.getBean("categoryService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.unitService == None:
            self.addActionError(u"无法加载组织机构服务，请检查  applicationContext.xml 配置文件，是否缺少 unitService 节点。")
            return self.ERROR
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.isContentAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        if self.contentSpaceService == None:
            self.addActionError(u"无法获得 contentSpaceService 服务，检查配置文件。")
            return ActionResult.ERROR
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
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
            if request.getHeader("Referer") != None and request.getHeader("Referer") != "":
                response.sendRedirect(request.getHeader("Referer"))
        self.get_artilce_list()
        self.get_contentspace_list()
        request.setAttribute("unit", self.unit)
        return "/WEB-INF/unitsmanage/unit_contentspace_article_list.ftl"
    
    
    def get_artilce_list(self):
        contentSpaceId = self.params.safeGetIntParam("contentSpaceId")
        qry = ContentSpaceArticleQuery(""" cs.spaceName, csa.contentSpaceArticleId, csa.title,csa.contentSpaceId,csa.createDate,csa.createUserId,csa.viewCount """)
        qry.ownerType = ContentSpace.CONTENTSPACE_OWNERTYPE_UNIT
        qry.ownerId = self.unit.unitId
        if contentSpaceId != 0:
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
          
    def get_contentspace_list(self):
        #contentSpaceList = self.contentSpaceService.getAllContentSpaceList(ContentSpace.CONTENTSPACE_OWNERTYPE_UNIT, self.unit.unitId)
        #request.setAttribute("catelist", contentSpaceList)
        #得到了categoryList
        categoryList = self.contentSpaceService.getContentSpaceTreeList(ContentSpace.CONTENTSPACE_OWNERTYPE_UNIT, self.unit.unitId)
        #需要把categoryList转换为CategoryTreeModel
        category_tree = self.categoryService.getCategoryTree(categoryList);
        request.setAttribute("category_tree", category_tree)             
