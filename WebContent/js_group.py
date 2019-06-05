from cn.edustar.jitar.util import ParamUtil
from group_query import *

class js_group:
    def execute(self):
        # 完整的调用参数：
        # js_group.py?top=10&type=0&cateid=49
        self.params = ParamUtil(request)
        ShowTop = self.params.getIntParam("top")
        ShowType = self.params.getIntParam("type")
        cateid = self.params.getIntParamZeroAsNull("cateid")
        if ShowTop == None or ShowTop == 0:
            ShowTop = 10
        if ShowType == None:
            ShowType = 0        
        
        qry = GroupQuery(" g.groupName,g.groupTitle ")
        qry.categoryId = cateid
        if ShowType == 1:
            qry.isRecommend = True
        elif ShowType == 2:
            qry.isBestGroup = True
                
        group_list = qry.query_map(ShowTop)
        request.setAttribute("group_list", group_list)
        response.contentType = "text/html; charset=utf-8"
        return "/WEB-INF/ftl/js_group.ftl"
