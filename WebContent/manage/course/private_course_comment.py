from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCoursePrivateComment
from java.text import SimpleDateFormat
from java.util import Date
from message_out import MessagePrint
from base_action import BaseAction
from base_preparecourse_page import PrepareCoursePrivateCommentQuery


class private_course_comment(BaseAction):
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.printer = response.getWriter()
        
    def execute(self):
        if self.loginUser == None:            
            self.printer.write(u"请先<a href='../../login.jsp'>登录</a>，然后才能进行管理")
            return
        
        if request.getMethod() == "POST":
            self.deleteComment()
            
        self.getPrepareCoursePrivateCommentList()        
        request.setAttribute("loginUser", self.loginUser)
        return "/WEB-INF/ftl/course/private_course_comment.ftl"

    def getPrepareCoursePrivateCommentList(self):
        pager = self.params.createPager()
        pager.itemName = u"评论"
        pager.itemUnit = u"条"
        qry = PrepareCoursePrivateCommentQuery(""" pcpc.prepareCoursePrivateCommentId,pcpc.title,pcpc.content,pcpc.createDate,pcpc.commentUserId, pcpc.commentedUserId, pcpc.prepareCourseId,pc.title as pctitle """)
        qry.commentedUserId = self.loginUser.userId
        pager.totalRows = qry.count()
        comment_list = qry.query_map(pager)
        request.setAttribute("pager",pager)
        request.setAttribute("comment_list",comment_list)
        return
    
    def deleteComment(self):
        guid = self.params.safeGetIntValues("guid")
        for id in guid:
            self.pc_svc.deletePrepareCoursePrivateComment(id)