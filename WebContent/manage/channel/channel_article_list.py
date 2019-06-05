from base_channel_manage import *
from channel_query import ChannelArticleQuery

class channel_article_list(BaseChannelManage):
    def __init__(self):        
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
        self.categoryService = __jitar__.categoryService
        self.articleService = __jitar__.articleService
        
    def execute(self):
        self.channelId = self.params.safeGetIntParam("channelId")
        self.channel = self.channelPageService.getChannel(self.channelId)
        if self.channel == None:
            self.addActionError(u"不能加载频道对象。")
            return self.ERROR
        if self.isSystemAdmin() == False and self.isChannelSystemAdmin(self.channel) == False and self.isChannelContentAdmin(self.channel) == False:
            self.addActionError(u"你无权管理本频道。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.save_post()
            #response.sendRedirect(request.getHeader("Referer"))
                
        return self.article_list()
    
    def save_post(self):        
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.getRequestParamValues("guid")
        if guids == None or len(guids) == 0:
            return  
        if cmd == "remove":
            for guid in guids:
                self.channelPageService.deleteChannelArticleById(int(guid))
        elif cmd == "recate":
            newCate = self.params.safeGetStringParam("newCate")            
            if newCate == "":
                for guid in guids:
                    channelArticle = self.channelPageService.getChannelArticleById(int(guid))
                    if channelArticle != None and channelArticle.channelCateId != None:
                        channelArticle.channelCate = None
                        channelArticle.channelCateId = None
                        self.channelPageService.saveOrUpdateChannelArticle(channelArticle)
            else:
                newCateArray = newCate.split("/")
                if len(newCateArray) > 2:
                    newCateId = newCateArray[len(newCateArray)-2]
                    for guid in guids:
                        channelArticle = self.channelPageService.getChannelArticleById(int(guid))
                        if channelArticle != None and channelArticle.channelCate != newCate:                            
                            channelArticle.channelCate = newCate
                            channelArticle.channelCateId = int(newCateId)
                            self.channelPageService.saveOrUpdateChannelArticle(channelArticle)
    
    def article_list(self):
        self.collectionQueryString()
        qry = ChannelArticleQuery("ca.channelArticleId, ca.articleId, ca.articleGuid, ca.title, ca.userId, ca.articleState, ca.typeState, ca.createDate,ca.loginName, ca.userTrueName,ca.channelCate, ca.channelCateId")
        qry.channelId = self.channel.channelId
        if self.channelCate != "":
            qry.channelCate = self.channelCate
        if self.articleState == "1":
            qry.articleState = True
        elif self.articleState == "0":
            qry.articleState = False
        else:
            qry.articleState = None
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        pager.pageSize = 25
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager)
        article_categories = self.categoryService.getCategoryTree("channel_article_" + str(self.channelId))        
        request.setAttribute("article_categories", article_categories)
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)
        request.setAttribute("channel", self.channel)
        request.setAttribute("ss", self.channelCate)
        return "/WEB-INF/ftl/channel/channel_article_list.ftl"
    
    def collectionQueryString(self):
        self.channelCate = self.params.safeGetStringParam("ss", "")
        self.articleState = self.params.safeGetStringParam("sarticleState", "")
        
        request.setAttribute("sc", self.channelCate)
        request.setAttribute("articleState", self.articleState)
        
    def triblesimu(self, int1):
        if int1 == "0":
            return False
        else:
            return True