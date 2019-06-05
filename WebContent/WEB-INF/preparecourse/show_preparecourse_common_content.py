
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil,CommonUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse, Comment
from java.text import SimpleDateFormat
from java.util import Date
from java.io import File
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *
from comment_query import CommentQuery
from cn.edustar.jitar.model import ObjectType

class show_preparecourse_common_content(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.commentService = __jitar__.getCommentService()
        
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
        if request.getMethod() == "POST":
            self.pubComment()            
        
        page = self.getPrepareCoursePageWithCustomSkin(prepareCourse)
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1, "title":u"备课基本信息", "module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2, "title":"", "module":"placeholder", "ico":"", "data":""},
                   {"id": "placerholder2", "pageId":0, "columnIndex":1, "title":"", "module":"placeholder", "ico":"", "data":""},
                   {"id": "placerholder3", "pageId":0, "columnIndex":1, "title":"", "module":"placeholder", "ico":"", "data":""}
                  ]
        qry = PrepareCourseMemberQuery(""" u.userId, u.userIcon, u.loginName,u.trueName,u.nickName""")  
        qry.prepareCourseId = self.prepareCourse.prepareCourseId
        qry.orderType = 1
        qry.status = 0
        user_list = qry.query_map()
        prepareCourseEdit_list = self.pc_svc.getPrepareCourseEditList(self.prepareCourseId)
        request.setAttribute("prepareCourseEdit_list", prepareCourseEdit_list)
        request.setAttribute("user_list", user_list)
        request.setAttribute("page", page)
        request.setAttribute("widget_list", widgets)
        request.setAttribute("prepareCourse", prepareCourse)
        request.setAttribute("loginUser", self.loginUser)
        self.getCommentList()
        
        if prepareCourse.contentType == 2 or prepareCourse.contentType == 3 or prepareCourse.contentType == 4 or prepareCourse.contentType == 5 or prepareCourse.contentType == 100:
            #服务器 名称。默认是本站服务器
            courseFileServer = ""
            
            swf = prepareCourse.commonContent            
            if swf == None or swf == "":
                request.setAttribute("error", u"该集备的共案选择了文件的方式，但并没有上载或者编写任何内容、或者没有保存成功，无法进行显示。")
                return "/WEB-INF/ftl/course/show_preparecourse_common_content2.ftl"
            
            if swf.find(".") > -1:
                swf = swf[0:swf.find(".")]
            
            prepareCourseFileServer = request.getSession().getServletContext().getInitParameter("PrepareCourseFileServer")
            pcFolder = CommonUtil.GetPrepareCourseFolder(request)
            if prepareCourseFileServer == None or prepareCourseFileServer == "":
                swfUrl = pcFolder[1] + str(self.prepareCourse.prepareCourseId) + "/"
                courseFileServer = CommonUtil.getSiteUrl(request)
            else:
                if prepareCourseFileServer.endswith("/") == False:prepareCourseFileServer += "/"
                courseFileServer = prepareCourseFileServer
                swfUrl = prepareCourseFileServer + "preparecoursefolder/" + str(self.prepareCourse.prepareCourseId) + "/"
                
            swfUrl = swfUrl + swf + ".swf"
            request.setAttribute("swfUrl", swfUrl)
            request.setAttribute("courseFileServer", courseFileServer)
            
        response.setContentType("text/html; charset=UTF-8")        
        return "/WEB-INF/ftl/course/show_preparecourse_common_content2.ftl"
    
    def getUserIp(self):
        userIP = request.getHeader("x-forwarded-for")
        if userIP == None or userIP == "":
            userIP = request.getRemoteAddr()
        return userIP
    
    def getCommentList(self):
        qry = CommentQuery("cmt.id,cmt.userId,cmt.content,cmt.createDate,cmt.userName")
        qry.objType = ObjectType.OBJECT_TYPE_PREPARECOURSE.getTypeId()
        qry.objId = self.prepareCourseId
        qry.orderType = 3
        qry.audit = 1
        pager = self.params.createPager()
        pager.itemName = u"评论"
        pager.itemUnit = u"条"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        comment_list = qry.query_map(pager)
        request.setAttribute("comment_list", comment_list)
        request.setAttribute("pager", pager)
        
    def pubComment(self):
        commentReply = self.params.safeGetStringParam("comment")
        if commentReply == "":
            return
        refId = self.params.safeGetIntParam("id")
        if refId > 0:
            refComment = self.commentService.getComment(refId)        
            content = ""
            if refComment != None:
                content = refComment.getContent()
                if content == None:
                    content = ""
                    
            content += "<div class='commentReply'>" + u"<div>以下为  " + self.loginUser.trueName + u" 的回复：</div><div>" + commentReply + "</div></div>";
            refComment.setContent(content);
            self.commentService.saveComment(refComment)
        else:
            comment = Comment()
            comment.setObjType(ObjectType.OBJECT_TYPE_PREPARECOURSE.getTypeId())
            comment.setObjId(self.prepareCourse.prepareCourseId)
            comment.setTitle(self.prepareCourse.title + u"的评论")
            comment.setContent(commentReply)
            comment.setAudit(True)
            comment.setStar(0)
            comment.setCreateDate(Date())
            comment.setUserId(self.loginUser.userId)
            comment.setUserName(self.loginUser.trueName)
            comment.setIp(self.getUserIp())
            
            self.commentService.saveComment(comment)
