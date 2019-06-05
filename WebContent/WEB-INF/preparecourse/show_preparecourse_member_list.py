from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import PrepareCoursePageService


class show_preparecourse_member_list(PrepareCoursePageService):
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
        
        
        pager = self.params.createPager()
        pager.itemName = u"成员"
        pager.itemUnit = u"个"        
        qry = PrepareCourseMemberQuery(""" pcm.replyCount, pcm.joinDate, u.userId, u.userIcon, u.loginName,u.trueName,u.nickName,u.unitId, unit.unitName """)
        qry.prepareCourseId = self.prepareCourseId
        qry.status = 0
        pager.totalRows = qry.count()
        member_list = qry.query_map(pager)
        
        page = self.getPrepareCoursePageWithCustomSkin(prepareCourse)
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1, "title":u"备课基本信息", "module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2, "title":"", "module":"placeholder", "ico":"", "data":""}
                  ]
        request.setAttribute("widgets", widgets)
        request.setAttribute("widget_list", widgets)        
        
        request.setAttribute("page", page)
        request.setAttribute("pager", pager)     
        request.setAttribute("prepareCourse", prepareCourse)
        request.setAttribute("member_list", member_list)
        
        return "/WEB-INF/ftl/course/show_preparecourse_member_list.ftl"
