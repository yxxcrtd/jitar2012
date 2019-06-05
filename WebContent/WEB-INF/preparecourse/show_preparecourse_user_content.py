from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseMember, PrepareCoursePrivateComment
from java.text import SimpleDateFormat
from java.util import Date
from java.io import File
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *
from cn.edustar.jitar.model import ObjectType

class show_preparecourse_user_content(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.pun_svc = __jitar__.UPunishScoreService
        
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
        showUser = None
        if self.params.existParam("userId"):                      
            self.userId = self.params.getIntParam("userId")
            if self.userId == None or self.userId == 0:
                self.printer.write(u"请选择一个要查看的用户。")
                return
            showUser = self.user_svc.getUserById(self.userId)
        elif self.params.existParam("userGuid"): 
            self.userGuid = self.params.safeGetStringParam("userGuid")
            showUser = self.user_svc.getUserByGuid(self.userGuid)
        if showUser == None:
            self.printer.write(u"无效的用户。")
            return
        
        self.userId = showUser.getUserId()
        
        self.prepareCourseMember = self.pc_svc.getPrepareCourseMemberByCourseIdAndUserId(self.prepareCourseId, self.userId)
        if self.prepareCourseMember == None:
            self.printer.write(u"您访问的用户不在此备课内，或者未通过审核，或者加载成员对象失败。")
            return
        if request.getMethod() == "POST":
            self.saveComment()
            
        page = self.getPrepareCoursePageWithCustomSkin(prepareCourse)
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1, "title":u"备课基本信息", "module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2, "title":"", "module":"placeholder", "ico":"", "data":""},
                   # {"id": "placerholder2", "pageId":0, "columnIndex":1,"title":"","module":"placeholder", "ico":"", "data":""},
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
        request.setAttribute("prepareCourseMember", self.prepareCourseMember)        
        request.setAttribute("showUser", showUser)
        request.setAttribute("loginUser", self.loginUser)
        self.getPrepareCoursePrivateCommentList()
        
        punshScore= self.pun_svc.getUPunishScore(ObjectType.OBJECT_TYPE_PREPARECOURSEMEMBER.getTypeId() , self.prepareCourseMember.prepareCourseMemberId)
        if punshScore!=None:
            if punshScore.getScore()<0 or punshScore.getScore()>0:
                request.setAttribute("scoreCreateUserId", punshScore.getCreateUserId())
                request.setAttribute("scoreCreateUserName", punshScore.getCreateUserName())
                request.setAttribute("score", -1*punshScore.getScore())
                request.setAttribute("scoreReason", punshScore.getReason())
                request.setAttribute("scoreDate", punshScore.getPunishDate())
                request.setAttribute("scoreObjId", punshScore.getObjId())
                request.setAttribute("scoreObjTitle", punshScore.getObjTitle())
                        
        if self.prepareCourseMember.contentType == 2 or self.prepareCourseMember.contentType == 3 or self.prepareCourseMember.contentType == 4 or self.prepareCourseMember.contentType == 5 or self.prepareCourseMember.contentType == 100:
            #服务器 名称。默认是本站服务器
            courseFileServer = ""
            #文件名
            swf = self.prepareCourseMember.privateContent
            if swf == None or swf == "":
                request.setAttribute("error", u"您的个案选择了文件的方式，但并没有上载或者编写任何内容、或者没有保存成功，无法进行显示。")
                return "/WEB-INF/ftl/course/show_preparecourse_user_content_2.ftl"
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
            #request.setAttribute("prepareCourseUrl", pcFolder[1])
            
        response.setContentType("text/html; charset=UTF-8")        
        return "/WEB-INF/ftl/course/show_preparecourse_user_content_2.ftl"
    
    def getPrepareCoursePrivateCommentList(self):
        pager = self.params.createPager()
        pager.itemName = u"评论"
        pager.itemUnit = u"条"
        pager.setPageSize(10)
        qry = PrepareCoursePrivateCommentQuery(" pcpc.title,pcpc.content,pcpc.createDate,pcpc.commentUserId ")
        qry.prepareCourseId = self.prepareCourseId
        qry.commentedUserId = self.userId
        pager.totalRows = qry.count()
        comment_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("comment_list", comment_list)
        return
    
    def saveComment(self):
        comment = self.params.getStringParam("content")
        title = self.params.getStringParam("commentTitle")
        if title == None or title == "":
            error_msg = u"<script>alert('请输入标题。')</script>"
            request.setAttribute("error_msg", error_msg)
            return
        if comment == None or comment == "":
            error_msg = u"<script>alert('请输入评论内容。')</script>"
            request.setAttribute("error_msg", error_msg)
            return
        prepareCoursePrivateComment = PrepareCoursePrivateComment()
        prepareCoursePrivateComment.setPrepareCourseId(self.prepareCourseId)
        prepareCoursePrivateComment.setReferIp(request.getRemoteAddr())
        prepareCoursePrivateComment.setContent(comment)
        prepareCoursePrivateComment.setPrepareCourseMemberId(self.prepareCourseMember.prepareCourseMemberId)
        prepareCoursePrivateComment.setCommentUserId(self.loginUser.userId)
        prepareCoursePrivateComment.setCommentedUserId(self.prepareCourseMember.userId)
        prepareCoursePrivateComment.setCreateDate(Date())
        prepareCoursePrivateComment.setTitle(title)
        self.pc_svc.addPrepareCoursePrivateComment(prepareCoursePrivateComment)
        
