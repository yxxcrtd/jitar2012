from cn.edustar.jitar.util import ParamUtil
from action_query import ActionQuery

class actions:
    def __init__(self):
        self.params = ParamUtil(request)
    
    def execute(self):
        self.get_action_list()        
        request.setAttribute("head_nav", "actions")
        return "/WEB-INF/ftl/site_actions.ftl"    
            
    def get_action_list(self):        
        ownerType = self.params.getStringParam("ownerType")
        showtype = self.params.getStringParam("type")
        request.setAttribute("type", showtype)
        request.setAttribute("ownerType", ownerType) 
        
        self.pager = self.params.createPager()
        self.pager.itemName = u"活动"
        self.pager.itemUnit = u"个"
        self.pager.pageSize = 20
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount,u.loginName,u.trueName
                          """)
        if not(ownerType == None or len(ownerType) < 1):
            qry.ownerType = ownerType
 
        if showtype == "running":
            qry.qryDate = 1 #正在进行
        elif showtype == "finish":
            qry.qryDate = 2 #已经完成的活动
        elif showtype == "new":
            qry.qryDate = 0 #正在报名的活动

        self.pager.totalRows = qry.count()
        action_list = qry.query_map(self.pager)
        request.setAttribute("action_list", action_list)
        request.setAttribute("pager", self.pager)
