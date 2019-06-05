from cn.edustar.jitar.util import ParamUtil
from group_member_query import GroupMemberQuery
from cn.edustar.jitar.pojos import Group
from base_preparecourse_page import PrepareCoursePlanQuery, PrepareCourseQuery

class group_preparecourse_plan(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner): 
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
        
        qry = PrepareCoursePlanQuery(""" pcp.title, pcp.prepareCoursePlanId, pcp.startDate,pcp.endDate,pcp.planContent,pcp.createDate, pcp.defaultPlan """)
        qry.groupId = group.groupId
        qry.defaultPlan = True
        plan_list = qry.query_map(1)
        if plan_list.size() < 1:
            qry.defaultPlan = None
            plan_list = qry.query_map(6)
            request.setAttribute("hasdefault", "0")
            request.setAttribute("plan_list", plan_list)
        else:
            prepareCoursePlan = plan_list.get(0)
            request.setAttribute("hasdefault", "1")
            request.setAttribute("prepareCoursePlan", prepareCoursePlan)
            qry = PrepareCourseQuery(""" pc.prepareCourseId, pc.title,pc.createDate,pc.leaderId,pc.startDate,pc.endDate """)
            qry.prepareCoursePlanId = prepareCoursePlan["prepareCoursePlanId"]
            qry.orderType = 1
            qry.status = 0
            pc_list = qry.query_map(6)
            request.setAttribute("pc_list", pc_list)
           
        request.setAttribute("group",group)
        return "/WEB-INF/group/default/group_preparecourse_plan.ftl"
        