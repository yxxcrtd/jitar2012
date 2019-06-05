from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import *
from cn.edustar.jitar.jython import BaseAdminAction
from base_action import *

class admin_validate_user(BaseAdminAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.out = response.writer
    def execute(self):
        if self.loginUser == None:
            self.out.write(u"请先登录。")
            return
        accessControlService = __spring__.getBean("accessControlService")
        if not(accessControlService.isSystemAdmin(self.loginUser) or accessControlService.isSystemContentAdmin(self.loginUser)):
            self.out.write(u"您没有管理的权限。")
            return
        
        userName = self.params.getStringParam("loginName")
        if userName == None or userName == "":
            self.out.write(u"请输入用户的登录名。")
            return
        
        user_svc = __jitar__.userService
        user = user_svc.getUserByLoginName(userName)
        if user == None:
            self.out.write(u"您输入用户的登录名在本系统中不存在，请检查输入的是否正确。")
            return
        else:
            self.out.write("OK")
            return        