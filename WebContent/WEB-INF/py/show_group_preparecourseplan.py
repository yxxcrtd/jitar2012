from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Group
from base_preparecourse_page import PrepareCoursePlanQuery, PrepareCourseQuery
from base_blog_page import *

class show_group_preparecourseplan(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner): 
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
        
        planId = self.params.safeGetIntParam("planId")        
        plan = self.preparecourse_svc.getPrepareCoursePlan(planId)
        if plan == None:
            return self.notFound()
        
        # 获取组内备课计划下的所有集备
        qry = PrepareCourseQuery(" pc.title, pc.prepareCourseId, pc.leaderId, pc.createDate, pc.startDate, pc.endDate ")
        qry.prepareCoursePlanId = plan.prepareCoursePlanId
        qry.orderType = 1
        qry.status = 0
        pager = self.params.createPager()
        pager.setPageSize(16)        
        pager.itemName = u"集备"
        pager.itemUnit = u"个"
        pager.totalRows = qry.count()
        pc_list = qry.query_map(pager)
                      
        page = self.getGroupIndexPage(group)
        page = {
                "pageId":0,
                "layoutId":2, # 固定是布局2
                "isSystemPage" : "true", 
                "owner" : "user", 
                "title" :"",
                "skin":page.skin
                }
        
        # 构造widgets 
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"协作组信息","module":"group_info", "ico":"", "data":""}
                  ]
        self.getGroupInfo(group.groupName)
        request.setAttribute("widget_list", widgets)
        request.setAttribute("widgets", widgets)
        request.setAttribute("page", page)
        request.setAttribute("prepareCoursePlan", plan)
        request.setAttribute("pc_list", pc_list)        
        request.setAttribute("pager", pager)
        
        return "/WEB-INF/group/default/show_group_preparecourseplan.ftl"
    
    def notFound(self):
        out = response.writer
        out.write(u"未找到该协作组或参数不正确")
        return None