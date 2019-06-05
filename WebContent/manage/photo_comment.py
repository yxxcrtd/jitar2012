from base_action import ActionExecutor,ActionResult
from cn.edustar.jitar.util import ParamUtil
from comment_query import CommentQuery
from cn.edustar.jitar.pojos import Comment

class photo_comment(ActionExecutor):
    def __init__(self):        
        self.params = ParamUtil(request)
        self.login_user = self.getLoginUser()
        self.writer = response.getWriter()
        self.cmt_svc = __jitar__.getCommentService()
        
    def execute(self):
        if self.login_user == None:
            self.writer.write(u"请先登录。")
            return
        request.setAttribute("user",self.login_user)
        
        cmd = self.params.getStringParam("cmd")
        if cmd == "delete":
            guids = self.params.safeGetIntValues("guid")
            for guid in guids:
                comment = self.cmt_svc.getComment(guid)
                if comment != None:
                    self.cmt_svc.deleteComment(comment)
                    
        if cmd == "audit_comment":
            guids = self.params.safeGetIntValues("guid")
            for guid in guids:
                comment = self.cmt_svc.getComment(guid)
                if comment != None:
                    self.cmt_svc.auditComment(comment)
               
        if cmd == "unaudit_comment":
            guids = self.params.safeGetIntValues("guid")            
            for guid in guids:
                comment = self.cmt_svc.getComment(guid)
                if comment != None:
                    self.cmt_svc.unauditComment(comment) 
            
        if cmd == "reply":
            cmtId = self.params.getIntParam("cmtId")
            cmtContent = self.params.getStringParam("content")
            cmtContent = u"<div class='commentReply'><div>以下为 " + self.login_user.trueName + u" 的回复：</div><div>" + cmtContent + "</div></div>"
            comment = self.cmt_svc.getComment(cmtId)
            comment.setContent(comment.content + cmtContent)
            self.cmt_svc.saveComment(comment)            
        return self.comment_list()
    
    def comment_list(self):
        qry = CommentQuery(""" cmt.id,cmt.content,cmt.audit,cmt.ip,cmt.createDate,cmt.star,cmt.title,cmt.objId,u.loginName,u.userId,u.trueName """)
        qry.objType = 11
        qry.audit = None
        qry.aboutUserId = self.login_user.userId
        pager = self.params.createPager()
        pager.setPageSize(16)
        pager.totalRows = qry.count()
        pager.itemName = u"评论"
        pager.itemUnit = u"条"
        
        result = qry.query_map(pager)
        request.setAttribute("photo_comment_list",result)
        request.setAttribute("pager",pager)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/photo/photo_comment.ftl"
        
        