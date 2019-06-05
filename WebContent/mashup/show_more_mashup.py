from base_mashup import *
from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from javax.servlet.http import Cookie
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import EncryptDecrypt

class show_more_mashup(JythonBaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()
        
        type = self.params.safeGetStringParam("type")        
        qry = MashupContentQuery("""
                mc.mashupContentId,mc.pushUserName,mc.mashupContentState,mc.platformName,
                mc.title,mc.unitName,mc.unitTitle,mc.unitTitle,mc.author,mc.orginId,mc.href,mc.pushDate
                """)
        documentType = "article"
        itemName = u"文章"
        itemUnit = u"篇"        
        if type == "resource":
            documentType = "resource"
            itemName = u"资源"
            itemUnit = u"个"
        elif type == "blog":
            return self.show_mingshi()
            
            
        qry.documentType = documentType
        pager = self.params.createPager()        
        pager.itemName = itemName
        pager.itemUnit = itemUnit
        pager.pageSize = 25
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager)
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)       
        request.setAttribute("documentType", documentType)
        
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
        return "/WEB-INF/mashup/show_more_mashup.ftl"
    
    def show_mingshi(self):
        qry = MashupBlogGroupQuery("""
                mbg.mashupBlogGroupId,mbg.pushUserName,mbg.mashupBlogGroupState,mbg.platformName,mbg.description,
                mbg.trueName,mbg.unitName,mbg.subjectName,mbg.gradeName,mbg.orginId,mbg.href,mbg.pushDate,mbg.icon
                """)
        qry.contentType = "blog"
        qry.mashupBlogGroupState = 0
        qry.orderType = 1
        
        itemName = u"名师"
        itemUnit = u"位"
        pager = self.params.createPager()        
        pager.itemName = itemName
        pager.itemUnit = itemUnit
        pager.pageSize = 25
        pager.totalRows = qry.count()
        blog_list = qry.query_map(pager)        
        request.setAttribute("blog_list", blog_list)
        request.setAttribute("pager", pager)            
        mashupUserEncryptGuid = self.getMashupUserCookie()
        if mashupUserEncryptGuid != "":
            request.setAttribute("mashupuser", mashupUserEncryptGuid)
        request.setAttribute("head_nav", "mashup")
        return "/WEB-INF/mashup/show_more_mashup.ftl"
    
    def getMashupUserCookie(self):
        cookie = ""
        cookies = request.getCookies()
        for c in cookies:
            if c.getName() == "mashup":
                 cookie = c.getValue()
        return cookie
