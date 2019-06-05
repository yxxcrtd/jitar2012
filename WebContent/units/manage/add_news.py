from unit_page import *
from cn.edustar.jitar.pojos import UnitNews
from java.util import Date
from cn.edustar.jitar.util import FileCache

class add_news(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        self.unitNews = None
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.isUnitAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        unitNewsId = self.params.safeGetIntParam("unitNewsId")
        if unitNewsId != 0:
            self.unitNews = self.unitService.getUnitNewsById(unitNewsId)
            
        if request.getMethod() == "POST":
            self.clear_cache()
            return self.save_update_new()
        
        fc = FileCache()
        fc.deleteUnitCacheFile(self.unit.unitName)
        fc = None
        request.setAttribute("unitNews", self.unitNews)
        request.setAttribute("unit", self.unit)
        return "/WEB-INF/unitsmanage/add_news.ftl"
            
    def save_update_new(self):
        title = self.params.safeGetStringParam("news_title")
        picture = self.params.safeGetStringParam("news_picture")
        type = self.params.safeGetIntParam("news_type")
        content = self.params.safeGetStringParam("news_content")
        
        if title == "":
            self.addActionError(u"请输入标题。")
            return self.ERROR
        
        if content == "":
            self.addActionError(u"请输入内容。")
            return self.ERROR
        
        if type == 0:
            if picture == "":
                self.addActionError(u"图片新闻必须输入图片地址。")
                return self.ERROR
        if self.unitNews == None:
            self.unitNews = UnitNews()
        
        self.unitNews.setTitle(title)
        self.unitNews.setCreateUserId(self.loginUser.userId)
        self.unitNews.setUnitId(self.unit.unitId)
        self.unitNews.setCreateDate(Date())
        self.unitNews.setViewCount(0)
        self.unitNews.setContent(content)
        if type == 0:
            self.unitNews.setPicture(picture)
        else:
            self.unitNews.setPicture(None)
        self.unitNews.setItemType(type)
        
        self.unitService.saveOrUpdateUnitNews(self.unitNews)
        
        link = "unit_news.py?type=1&unitId=" + str(self.unit.unitId)
        self.addActionLink(u"返回", link)
        return self.SUCCESS
