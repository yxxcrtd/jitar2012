from cn.edustar.jitar.util import ParamUtil
from resource_query import *

class js_resource:    
    def execute(self):
        # 完整的调用参数：
        # top=10&count=10&type=0&cateid=53&author=1&date=1&groupId=42
        unitService = __jitar__.unitService
        self.params = ParamUtil(request)
        ShowCount = self.params.getIntParam("count")
        ShowTop = self.params.getIntParam("top")
        ShowType = self.params.getIntParam("type")
        ShowAuthor = self.params.getIntParamZeroAsNull("author")
        ShowDate = self.params.getIntParamZeroAsNull("date")
        
        unitId = self.params.getIntParamZeroAsNull("unitid")
        cateid = self.params.getIntParamZeroAsNull("cateid")
        groupId = self.params.getIntParamZeroAsNull("groupId")
        if unitId == None:
            rootUnit = unitService.getRootUnit()
        else:
            rootUnit = unitService.getUnitById(unitId)
        
        if rootUnit == None:
            response.getWriter().println(u"document.write('没有根单位。')")
            return
        
        if ShowTop == None or ShowTop == 0:
            ShowTop = 10
        if ShowCount == None or ShowCount == 0:
            ShowCount = 10
        if ShowType == None:
            ShowType = 0
        
        if groupId != None and groupId != 0:
            qry = GroupResourceQuery(""" r.resourceId, r.title, r.createDate, u.userId, u.loginName, u.trueName """)
            qry.groupId = groupId
            if ShowType == 1:
                # 小组精华
                qry.isGroupBest = True
        else:
            qry = ResourceQuery(""" r.resourceId, r.title, r.createDate, u.userId, u.loginName, u.trueName """)            
            qry.sysCateId = cateid            
            if ShowType == 1:
                #推荐资源                
                qry.custormAndWhereClause = "r.rcmdPathInfo Like '%/" + str(rootUnit.unitId) + "/%' and r.approvedPathInfo Like '%/" + str(rootUnit.unitId) + "/%'"
            else:
                qry.custormAndWhereClause = "r.approvedPathInfo Like '%/" + str(rootUnit.unitId) + "/%'"
        resource_list = qry.query_map(ShowTop)
        request.setAttribute("resource_list", resource_list)
        request.setAttribute("ShowAuthor", ShowAuthor)
        request.setAttribute("ShowDate", ShowDate)
        request.setAttribute("ShowCount", ShowCount)
        response.contentType = "text/html; charset=utf-8"
        return "/WEB-INF/ftl/js_resource.ftl"
