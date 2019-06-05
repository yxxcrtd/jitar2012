from cn.edustar.jitar.util import ParamUtil
from user_query import UserQuery

class user_select_bottom:
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        # 选择类型，单选还是多选？type:1单选,0多选
        type = self.params.safeGetStringParam("type")
        idTag = self.params.safeGetStringParam("idTag")
        titleTag = self.params.safeGetStringParam("titleTag")
        backType = self.params.safeGetStringParam("back")
        
        qry = UserQuery(" u.userId, u.loginName, u.trueName, u.createDate, unit.unitName, unit.unitTitle ")
        qry.orderType = 0
        qry.userStatus = 0
        pager = self.params.createPager()
        pager.itemName = u"用户"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)
        request.setAttribute("pager", pager)        
        request.setAttribute("user_list" , user_list)
        request.setAttribute("type", type)
        request.setAttribute("idTag", idTag)
        request.setAttribute("titleTag", titleTag)
        request.setAttribute("back", backType)
        return "/WEB-INF/common/user_select_bottom.ftl"
