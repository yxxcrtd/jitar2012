from base_channel_manage import *
from admin_edit_content import AdminArticleEdit

class channel_article_edit(BaseChannelManage,AdminArticleEdit):
    def __init__(self):        
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
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
        
        channelArticleId = self.params.safeGetIntParam("channelArticleId")
        channelArticle = self.channelPageService.getChannelArticleById(channelArticleId)
        if channelArticle == None:
            self.addActionError(u"无法加载该文章。")
            return self.ERROR
        #检查是否可以管理文章
        if channelArticle.channelId == None or channelArticle.channelId != self.channelId:
            self.addActionError(u"该文章不属于当前频道，无法进行管理。")
            return self.ERROR
        
        article = self.articleService.getArticle(channelArticle.articleId);
        if article == None:
            self.addActionError(u"该文章可能已经被删除。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            if self.edit_post(article) == False:
                self.addActionError(u"您输入的信息不全，请检查输入。")
                return self.ERROR
            
            self.addActionMessage(u"修改成功。")
            self.addActionLink(u"返回前页", "channel_article_list.py?channelId=" + str(self.channel.channelId))
            return self.SUCCESS            
        
        self.edit_get(article)
        request.setAttribute("formUrl","")
        return "/WEB-INF/ftl/channel/channel_article_edit.ftl"