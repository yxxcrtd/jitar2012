from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery

from base_preparecourse_page import *
from action_query import ActionQuery

class manage_createPrepareCourse_action(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.act_svc = __jitar__.getActionService()
        
    def execute(self): 
        self.getBaseData()
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return        
       
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        
        if self.canManage(prepareCourse) == False:
            self.printer.write(u"您无权管理本备课。")
            return
        
        if request.getMethod() == "POST":            
            cmd = self.params.safeGetIntParam("cmdtype")
            guid = self.params.safeGetIntValues("guid")
            for actionId in guid:
                self.act_svc.updateActionStatus(cmd, int(actionId))
       
        pager = self.params.createPager()
        pager.itemName = u"活动"
        pager.itemUnit = u"个"
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount,u.trueName,u.loginName
                            """)
        qry.k = None #防止右边查询冲突
        qry.ownerType = "preparecourse"
        qry.ownerId = prepareCourse.prepareCourseId
        pager.totalRows = qry.count()
        action_list = qry.query_map(pager)      
           
        request.setAttribute("pager",pager)
        request.setAttribute("action_list",action_list)    
        request.setAttribute("prepareCourse",prepareCourse)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/manage_createPrepareCourse_action.ftl"