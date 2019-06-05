from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage, PrepareCourseMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery

from base_preparecourse_page import *
from comment_query import CommentQuery
from cn.edustar.jitar.model import ObjectType

class manage_createPrepareCourse_common_comment(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.commentService = __jitar__.getCommentService()
        
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
            
        self.getCommentList()        
        request.setAttribute("loginUser", self.loginUser)
        request.setAttribute("prepareCourse", self.prepareCourse)
        return "/WEB-INF/ftl/course/manage_createPrepareCourse_common_comment.ftl"

    def getCommentList(self):
        qry = CommentQuery("cmt.id,cmt.userId,cmt.content,cmt.createDate,cmt.userName")
        qry.objType = ObjectType.OBJECT_TYPE_PREPARECOURSE.getTypeId()
        qry.objId = self.prepareCourseId        
        qry.audit = 1
        pager = self.params.createPager()
        pager.itemName = u"评论"
        pager.itemUnit = u"条"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        comment_list = qry.query_map(pager)
        request.setAttribute("comment_list", comment_list)
        request.setAttribute("pager", pager)
        
    def deleteComment(self):
        commentsId = self.params.safeGetIntValues("commentId")
        for id in commentsId:
            refComment = self.commentService.getComment(id)
            if refComment != None:
                self.commentService.deleteComment(refComment)