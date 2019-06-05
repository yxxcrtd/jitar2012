from base_action import *
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import AccessControl, JitarColumnNews
from jitar_column_news_query import *

class column_news_edit(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.columnId = 0
        self.jitarColumn = None
        self.newsId = 0
        
    def execute(self):
        if self.loginUser == None:            
            return self.LOGIN
        
        self.columnId = self.params.safeGetIntParam("columnId")
        self.newsId = self.params.safeGetIntParam("newsId")
        
        jitarColumnService = __spring__.getBean("jitarColumnService")
        self.jitarColumn = jitarColumnService.getJitarColumnById(self.columnId)
        if self.jitarColumn == None:
            self.addActionError(u"请选择栏目.")
            return self.ERROR
        
        if self.canManegeColumn() == False:
            self.addActionError(u"你没有管理的权限.")
            return self.ERROR
        
        if self.newsId > 0:
            jitarColumnNews = jitarColumnService.getJitarColumnNewsById(self.newsId)
            request.setAttribute("jitarColumnNews", jitarColumnNews)
        else:
            jitarColumnNews = JitarColumnNews()
            jitarColumnNews.setUserId(self.loginUser.userId)
            jitarColumnNews.setColumnId(self.columnId)
            
        if request.getMethod() == "POST":
            title = self.params.safeGetStringParam("title")
            content = self.params.safeGetStringParam("columnContent")
            picture = self.params.safeGetStringParam("picture")
            urlReferer = self.params.safeGetStringParam("urlReferer")
            jitarColumnNews.setTitle(title)
            jitarColumnNews.setContent(content)
            if picture == "":
                jitarColumnNews.setPicture(None)
            else:
                jitarColumnNews.setPicture(picture)
            jitarColumnService.saveOrUpdateJitarColumnNews(jitarColumnNews)
            
            self.addActionMessage(u"添加/修改成功。")
            if urlReferer == "":
                urlReferer = "column_news_list.py?cmd=list&columnId=" + str(self.columnId)
            self.addActionLink(u"返回", urlReferer)
            return self.SUCCESS
        else:
            urlReferer = request.getHeader("Referer")
            if urlReferer != None and urlReferer != "":
                request.setAttribute("urlReferer", urlReferer)
        
        request.setAttribute("jitarColumn", self.jitarColumn)
        return "/WEB-INF/ftl/column/column_news_edit.ftl"
        
    def canManegeColumn(self):
        if self.loginUser == None:
            return False
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == True:
            return True
        return accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_CUSTORMCOLUMNADMIN, self.columnId)
 