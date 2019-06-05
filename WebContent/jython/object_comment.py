from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Comment
from base_action import BaseAction
from java.util import Date

class object_comment(BaseAction):
    def execute(self):
        params = ParamUtil(request)
        cmt_svc = __jitar__.getCommentService()
        writer = response.getWriter()
        if self.loginUser == None:
            writer.write(u"请先登录。")
            return
        userName = self.loginUser.trueName
        if userName == None:
            userName = self.loginUser.loginName
        title = params.getStringParam("title")
        objtype = params.getIntParam("objtype")
        aboutUserId = params.getIntParam("aboutUserId")
        objId = params.getIntParam("photoId")
        star = params.getIntParam("star")
        content = params.getStringParam("content")
        userIP = request.getHeader("x-forwarded-for")
        if userIP == None or userIP == "":
            userIP = request.getRemoteAddr()
        
        if title == "":
            writer.write(u"请输入标题。")
            return
        if content == "" or content == "<span></span>":
            writer.write(u"请输入评论内容。")
            return
        
        cmt = Comment()
        cmt.setTitle(title)
        cmt.setAboutUserId(aboutUserId)
        cmt.setIp(userIP)
        cmt.setUserName(userName)
        cmt.setUserId(self.loginUser.userId)
        cmt.setCreateDate(Date())
        cmt.setStar(star)
        cmt.setAudit(True)
        cmt.setContent(content)
        cmt.setObjId(objId)
        cmt.setObjType(objtype)
        cmt_svc.saveComment(cmt)
        
        # 评论计数器
        cmd = Command(""" UPDATE Photo SET commentCount = commentCount + 1 WHERE photoId = :photoId """)
        cmd.setInteger("photoId", objId)
        cmd.update()
                
        photoUser = params.getStringParam("photoUser")
        if request.getHeader("Referer") != None:
            response.sendRedirect(request.getHeader("Referer"))
            return
        request.setAttribute("redUrl",photoUser + "/py/user_photo_show.py?photoId=" + str(objId))
        return "/WEB-INF/ftl/photo/PhotoComment_Success.ftl"