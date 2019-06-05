from cn.edustar.jitar.util import ParamUtil
from group_member_query import GroupMemberQuery
from cn.edustar.jitar.pojos import Group
from base_preparecourse_page import PrepareCoursePlanQuery, PrepareCourseQuery

class group_preparecourseplan_list(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner): 
    def __init__(self):
        self.params = ParamUtil(request)
        self.group_svc = __jitar__.groupService
        self.preparecourse_svc = __jitar__.prepareCourseService
        
    def execute(self):
        response.setContentType("text/html; charset=UTF-8")
        groupName = request.getAttribute("groupName")
        if groupName == None or groupName == "":
            return self.notFound()     
        
        group = group_svc.getGroupByName(groupName)
        if group == None:
            return self.notFound()
        
        pager = self.params.createPager()
        qry = PrepareCoursePlanQuery(""" pcp.title, pcp.prepareCoursePlanId, pcp.startDate,pcp.endDate,pcp.planContent,pcp.createDate, pcp.defaultPlan """)
        qry.groupId = group.groupId
        pager.setPageSize(16)        
        pager.itemName = u"备课计划"
        pager.itemUnit = u"个"
        plan_list = qry.query_map(pager)
        pager.totalRows = plan_list.size()        
  
        request.setAttribute("plan_list", plan_list)
        request.setAttribute("pager",pager)
        
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
        return "/WEB-INF/group/default/group_preparecourseplan_list.ftl"