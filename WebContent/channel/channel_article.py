from cn.edustar.jitar.util import ParamUtil
from base_action import BaseAction, ActionResult
from java.util import HashMap
from base_channel_page import ChannelPage
from channel_query import ChannelArticleQuery

class channel_article(ChannelPage):
    def __init__(self):
        ChannelPage.__init__(self)
        self.params = ParamUtil(request)
        self.channelPageService = __spring__.getBean("channelPageService")
        self.templateProcessor = __spring__.getBean("templateProcessor")
        self.channel = None
        
    def execute(self):
        channelId = self.params.safeGetIntParam("channelId")
        if channelId == 0:
            self.addActionError(u"您所访问的页面不存在！")
            return ActionResult.ACCESS_ERROR
        self.channel = self.channelPageService.getChannel(channelId)
        if self.channel == None:
            self.addActionError(u"无法加载指定的页面！")
            return ActionResult.ACCESS_ERROR
        
        if self.channel.skin == None :
            skin = "template1"
        elif self.channel.skin == "" :
            skin = "template1"
        else:
            skin = self.channel.skin
        
        map = HashMap()
        map.put("channel", self.channel)
        head_nav = "article"
        # 输出页头
        headerContent = self.GenerateContentFromTemplateString(self.channel.headerTemplate, head_nav)
        footerContent = self.GenerateContentFromTemplateString(self.channel.footerTemplate, head_nav)        
        # 输出主体部分：       
        categoryId = self.params.safeGetIntParam("categoryId")
        if categoryId == 0:
            article_category = self.categoryService.getCategoryTree("channel_article_" + str(self.channel.channelId))
        else:
            article_category = self.categoryService.getCategoryTree("channel_article_" + str(self.channel.channelId),categoryId)
        map.put("article_category", article_category)
        map.put("categoryId", categoryId)
        
        channelCateId = self.params.safeGetIntParam("channelCateId")
        if channelCateId == 0:
            channelCateId = categoryId
        qry = ChannelArticleQuery("ca.articleId, ca.title, ca.createDate, ca.userId,ca.typeState, ca.loginName, ca.userTrueName")
        qry.channelId = self.channel.channelId
        qry.articleState = True
        if channelCateId != 0:            
            qry.custormAndWhereClause = " channelCate LIKE '%/" + str(channelCateId) + "/%'"
        pager = self.createPager()
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager)
        map.put("article_list", article_list)
        map.put("pager", pager)        
        mainContent = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + skin + "/channel_article.ftl", "utf-8")        
        out = response.getWriter()
        if headerContent == "" and footerContent == "" and mainContent == "":
            out.println(u"该频道没有指定模板，无法显示页面内容。")
            return
        out.println(headerContent)
        out.println(mainContent)
        out.println(footerContent)
                
    def createPager(self):
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        pager.pageSize = 20
        return pager