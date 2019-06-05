from base_action import *
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import AccessControl
from jitar_column_news_query import *

class column_news_list(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.jitarColumnService = None
        self.columnId = None
        self.jitarColumn = None
        
    def execute(self):
        if self.loginUser == None:            
            return self.LOGIN
        
        self.columnId = self.params.safeGetIntParam("columnId")
        cmd = self.params.safeGetStringParam("cmd")
        self.jitarColumnService = __spring__.getBean("jitarColumnService")
        self.jitarColumn = self.jitarColumnService.getJitarColumnById(self.columnId)
        if self.jitarColumn == None:
            self.addActionError(u"请选择栏目.")
            return self.ERROR
        
        if self.canManegeColumn() == False:
            self.addActionError(u"你没有管理的权限.")
            return self.ERROR
        if cmd == "delete":
            self.delete()
            
        self.list()
        request.setAttribute("jitarColumn", self.jitarColumn)
        return "/WEB-INF/ftl/column/column_news_list.ftl"
    
    def delete(self):
        newsId = self.params.safeGetIntValues("newsId")
        for id in newsId:
            jitarColumnNews = self.jitarColumnService.getJitarColumnNewsById(id)
            if jitarColumnNews != None:
                self.jitarColumnService.deleteJitarColumnNews(jitarColumnNews)
                
    def list(self):
        qry = JitarColumnNewsQuery("jcn.columnNewsId, jcn.userId, jcn.title, jcn.picture, jcn.createDate, jcn.viewCount")
        qry.columnId = self.columnId
        pager = self.params.createPager()
        pager.itemName = u""
        pager.itemUnit = u"条"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        jc_list = qry.query_map(pager)
        request.setAttribute("jc_list", jc_list)
        request.setAttribute("pager", pager)
        
    def canManegeColumn(self):
        if self.loginUser == None:
            return False
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == True:
            return True
        return accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_CUSTORMCOLUMNADMIN, self.columnId)
