from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Group
from base_action import ActionExecutor
from action_query import ActionQuery
from base_blog_page import *

class group_action_list(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    
    def __init__(self):                
        self.params = ParamUtil(request)
        self.group_svc = __jitar__.groupService   
        
    def execute(self):
        response.setContentType("text/html; charset=UTF-8")
        groupName = request.getAttribute("groupName")
        if groupName == None or groupName == "":
            return ""        
        
        group = group_svc.getGroupByName(groupName)
        if group == None:
            return self.notFound()        
        
        page = self.getGroupIndexPage(group)
        page = {
                "pageId":0,
                "layoutId":2, # 固定是布局2
                "isSystemPage" : "true", 
                "owner" : "user", 
                "title" :"",
                "skin":page.skin
                }
        
        # 构造widgets .
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"协作组信息","module":"group_info", "ico":"", "data":""}
                  ]        
        self.getGroupInfo(group.groupName)
        request.setAttribute("widget_list", widgets)
        request.setAttribute("widgets", widgets)
        request.setAttribute("page", page)
        pager = self.params.createPager()
        pager.itemName = u"活动"
        pager.itemUnit = u"个"
        pager.pageSize = 20        
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount, u.trueName, u.loginName
                            """)
        qry.ownerType = "group"
        qry.status = 0
        qry.ownerId = group.groupId
        #print "qry = ", str(qry.ownerId)
        pager.totalRows = qry.count()
        action_list = qry.query_map(pager)
                
        request.setAttribute("pager", pager)
        request.setAttribute("action_list", action_list)        
        request.setAttribute("group", group)
        return "/WEB-INF/group/default/group_action_list.ftl" 
    