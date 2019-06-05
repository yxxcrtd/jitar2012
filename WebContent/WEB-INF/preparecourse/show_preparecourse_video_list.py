from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *

class show_preparecourse_video_list(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()        
        
    def execute(self):
        self.getBaseData() 
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
        pager.itemName = u"视频"
        pager.itemUnit = u"个"
        pager.pageSize = 10   
        qry = PrepareCourseVideoQuery(""" u.userId,u.loginName,u.trueName,u.nickName,pcv.videoTitle,pcv.videoId,pcv.createDate,v.flvThumbNailHref,v.flvHref """)
        qry.prepareCourseId = self.prepareCourseId
        qry.orderType =0
        pager.totalRows = qry.count()
        pcv_list = qry.query_map(pager)
        
        page = self.getPrepareCoursePageWithCustomSkin(prepareCourse)
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"备课基本信息","module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        request.setAttribute("widgets",widgets)
        request.setAttribute("widget_list",widgets)        
        
        request.setAttribute("page", page)
        request.setAttribute("pager", pager)     
        request.setAttribute("prepareCourse",prepareCourse)
        request.setAttribute("pcv_list", pcv_list)
        response.setContentType("text/html; charset=UTF-8")        
        return "/WEB-INF/ftl/course/show_preparecourse_video_list.ftl"