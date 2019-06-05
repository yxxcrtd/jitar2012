from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage,PrepareCourseMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery

from base_preparecourse_page import *

class manage_createPrepareCourse_private_comment(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        
    def execute(self):
        if self.loginUser == None:            
            self.printer.write(u"请先<a href='../../login.jsp'>登录</a>，然后才能进行管理")
            return
        
        self.getBaseData()
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return
    
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        if self.prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        
        if self.canManage(self.prepareCourse) == False:
            self.printer.write(u"您无权管理本备课。")
            return
        
        if request.getMethod() == "POST":
            self.deleteComment()
            
        self.getPrepareCoursePrivateCommentList()        
        request.setAttribute("loginUser", self.loginUser)
        request.setAttribute("prepareCourse", self.prepareCourse)
        return "/WEB-INF/ftl/course/manage_createPrepareCourse_private_comment.ftl"

    def getPrepareCoursePrivateCommentList(self):
        pager = self.params.createPager()
        pager.itemName = u"评论"
        pager.itemUnit = u"条"
        qry = PrepareCoursePrivateCommentQuery(""" pcpc.prepareCoursePrivateCommentId,pcpc.title,pcpc.content,pcpc.createDate,pcpc.commentUserId,pcpc.commentedUserId """)
        qry.prepareCourseId = self.prepareCourseId
        pager.totalRows = qry.count()
        comment_list = qry.query_map(pager)
        request.setAttribute("pager",pager)
        request.setAttribute("comment_list",comment_list)
        return
    
    def deleteComment(self):
        guid = self.params.safeGetIntValues("prepareCoursePrivateCommentId")
        for id in guid:
            self.pc_svc.deletePrepareCoursePrivateComment(id)
