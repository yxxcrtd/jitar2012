from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from preparecourse_member_query import PrepareCourseMemberQuery
from base_preparecourse_page import PrepareCoursePageService
from action_query import ActionQuery

class show_preparecourse_action_list(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()        
        
    def execute(self):
        self.getBaseData()
        response.setContentType("text/html; charset=UTF-8")
        
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的课程标识。")
            return
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        if self.canView(prepareCourse) == False:
            self.printer.write(u"您无权查看本内容。")
            return
        page = self.getPrepareCoursePageWithCustomSkin(prepareCourse)
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"备课基本信息","module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        
        pager = self.params.createPager()
        pager.itemName = u"活动"
        pager.itemUnit = u"个"         
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount
                            """)
        qry.ownerType = "preparecourse"
        #qry.status = 0        
        qry.ownerId = self.prepareCourseId
        action_list = qry.query_map(pager)
        pager.totalRows = qry.count()      
       
        request.setAttribute("widgets",widgets)
        request.setAttribute("widget_list",widgets)
        request.setAttribute("page", page)
        request.setAttribute("pager", pager)     
        request.setAttribute("prepareCourse",prepareCourse)
        request.setAttribute("action_list", action_list)
        
        return "/WEB-INF/ftl/course/show_preparecourse_action_list.ftl"