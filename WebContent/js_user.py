from cn.edustar.jitar.util import ParamUtil
from user_query import *

class js_user:
    def execute(self):
        # 完整的调用参数：
        # js_user.py?top=10&count=10&type=2&cateid=99
        
        # 默认支持 subjectId，gradeId 参数带这些值，默认是支持的
        # 更多参数请参见  user_query 的成员定义。
        
        self.params = ParamUtil(request)
        ShowCount = self.params.getIntParam("count")
        ShowTop = self.params.getIntParam("top")
        ShowType = self.params.getIntParam("type")
        
        unitId = self.params.getIntParamZeroAsNull("unitid")
        cateid = self.params.getIntParamZeroAsNull("cateid")
        groupId = self.params.getIntParamZeroAsNull("groupId")
        if ShowTop == None or ShowTop == 0:
            ShowTop = 10
        if ShowCount == None or ShowCount == 0:
            ShowCount = 10
        if ShowType == None:
            ShowType = 0
        
        qry = UserQuery(" u.userId,u.loginName,u.trueName ")
        qry.unitId = unitId
        qry.sysCateId = cateid
        qry.groupId = groupId
        
        if ShowType > 0: qry.userTypeId = ShowType
        
        user_list = qry.query_map(ShowTop)
        request.setAttribute("user_list", user_list)
        request.setAttribute("ShowCount", ShowCount)
        response.contentType = "text/html; charset=utf-8"
        return "/WEB-INF/ftl/js_user.ftl"
