from base_blog_page import *
from base_action import BaseAction
from cn.edustar.jitar.pojos import Action
from cn.edustar.jitar.util import ParamUtil

class modi_action_status(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    act_svc = __jitar__.actionService
    def execute(self):
        self.params = ParamUtil(request)
        self.out = response.writer
        if self.loginUser == None:
            self.out.write(u"请先<a href='../../login.jsp'>登录</a>，然后才能编辑活动")
            return
        
        canManage = False
        
        # 权限管理，
        
        
        self.cmdtype = self.params.getStringParam("cmdtype")
        self.actionIds = self.params.getRequestParamValues("guid")
        if self.cmdtype == None or self.cmdtype == "" :
            self.out.write(u"无效的命令。<a href='' onclick='window.history.back();return false;'>返回</a>")
            return
        action_status = -100
        try:
            action_status = int(self.cmdtype)
        except ValueError:
            self.out.write(u"给定的值不是一个数字。<a href='' onclick='window.history.back();return false;'>返回</a>")
            return
        
        for actionId in self.actionIds:
            if actionId.isdigit():
                self.act_svc.updateActionStatus(action_status, int(actionId))
        
        if self.params.getStringParam("return") == "admin":
            response.sendRedirect("admin_action_list.py")
        else:
            response.sendRedirect("actionlist.py?type=owner")