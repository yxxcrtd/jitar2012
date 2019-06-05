from base_mashup import *
from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from javax.servlet.http import Cookie
from cn.edustar.jitar.util import EncryptDecrypt
from cn.edustar.jitar.jython import JythonBaseAction

class show_mashup(JythonBaseAction):
    def __init__(self):
        self.mashupService = __spring__.getBean("mashupService")
        
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()
        self.params = ParamUtil(request)        
        
        if self.loginUser != None:
            loginUserGuid = self.loginUser.userGuid
            servletContext = request.getSession().getServletContext()
            privateKey = servletContext.getInitParameter("privateKey")
            if privateKey == None or privateKey == "":
                privateKey = "www.chinaedustar.com"
            des = EncryptDecrypt(privateKey)
            mashupUserEncryptGuid = des.encrypt(loginUserGuid)
            request.setAttribute("mashupType", "")
        else:
            mashupUserEncryptGuid = self.getMashupUserCookie()
        if mashupUserEncryptGuid != "":
            request.setAttribute("mashupuser", mashupUserEncryptGuid)
        request.setAttribute("head_nav", "mashup")
        
        self.show_article()
        self.show_resource()
        self.show_blog()
        self.show_platform()
        
        return "/WEB-INF/mashup/show_mashup.ftl"
    
    def show_article(self):        
        qry = MashupContentQuery("mc.title,mc.unitName,mc.unitTitle,mc.author,mc.orginId, mc.href,mc.pushDate,mc.platformName")
        qry.documentType = "article"
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        pager.pageSize = 10
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager)
        request.setAttribute("article_list", article_list)
        
    def show_resource(self):
        qry = MashupContentQuery(" mc.title,mc.unitName,mc.unitTitle,mc.author,mc.orginId,mc.href,mc.pushDate,mc.platformName")
        qry.documentType = "resource"
        pager = self.params.createPager()
        pager.itemName = u"资源"
        pager.itemUnit = u"个"
        pager.pageSize = 10
        pager.totalRows = qry.count()
        resource_list = qry.query_map(pager)
        request.setAttribute("resource_list", resource_list)
        
    def show_blog(self):
        qry = MashupBlogGroupQuery("""
                mbg.mashupBlogGroupId,mbg.pushUserName,mbg.mashupBlogGroupState,mbg.platformName,mbg.description,
                mbg.trueName,mbg.unitName,mbg.unitTitle,mbg.subjectName,mbg.gradeName,mbg.orginId,mbg.href,mbg.pushDate,mbg.icon
                """)
        qry.contentType = "blog"
        qry.mashupBlogGroupState = 0
        qry.orderType = 1
        blog_list = qry.query_map(8)
        request.setAttribute("blog_list", blog_list)
    
    def show_platform(self):
        platfotm_list = self.mashupService.getAllMashupPlatform(True)
        if self.loginUser != None:
            userGuid = self.loginUser.userGuid
            servletContext = request.getSession().getServletContext()
            privateKey = servletContext.getInitParameter("privateKey")
            if privateKey == None or privateKey == "":
                privateKey = "www.chinaedustar.com"
                
            des = EncryptDecrypt(privateKey)
            userGuid = des.encrypt(userGuid)
            request.setAttribute("encUserGuid", userGuid)
            
        request.setAttribute("platfotm_list", platfotm_list)        
        
    def getMashupUserCookie(self):
        cookie = ""
        cookies = request.getCookies()
        if cookies == None:
            return cookie
        for c in cookies:
            if c.getName() == "mashup":
                 cookie = c.getValue()
        return cookie
