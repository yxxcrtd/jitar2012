from cn.edustar.jitar.pojos import Action
from cn.edustar.jitar.util import ParamUtil
from action_query import ActionQuery
from cn.edustar.jitar.data import *
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.data import BaseQuery
from base_action import *

class admin_delete_action_comment(BaseAdminAction):
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.user_svc = __jitar__.userService
        self.act_svc = __jitar__.actionService
        
    def execute(self):
        if self.loginUser == None:
            self.out.println(u"请先<a href='../../login.jsp'>登录</a>，然后才能管理活动")
            return
        accessControlService = __spring__.getBean("accessControlService")
        if False == accessControlService.isSystemContentAdmin(self.loginUser):
            self.addActionError(u"没有管理权限。<a href='' onclick='window.history.back();return false;'>返回</a>")
            return ActionResult.ERROR
        
        cmd_type = self.params.getStringParam("cmdtype")
        if cmd_type == None or cmd_type == "":
            self.out.println(u"无效的命令。<a href='' onclick='window.history.back();return false;'>返回</a>")
            return
        if cmd_type == "0":
            user_name = self.params.getStringParam("loginname") 
            if user_name == None or user_name == "":
                self.out.println(u"请输入用户登录名。<a href='' onclick='window.history.back();return false;'>返回</a>")
                return                       
            user = self.user_svc.getUserByLoginName(user_name)
            if user == None:
                self.out.println(u"您输入用户的登录名在本系统中不存在，请检查输入的是否正确。<a href='' onclick='window.history.back();return false;'>返回</a>")
                return
            self.act_svc.deleteActionReplyByUserId(user.userId)
            
        if cmd_type == "1":
            reply_id = user_name = self.params.safeGetIntValues("guid")
            for replyid in reply_id:
                self.act_svc.deleteActionReplyById(replyid)
        response.sendRedirect("admin_action_comment_list.py")
