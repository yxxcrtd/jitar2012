# coding=utf-8
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import ParamUtil

# 后台管理的新基类
class BaseManage(JythonBaseAction):
    ERROR = "/WEB-INF/ftl/Error.ftl"
    ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl"
    ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    LOGIN = "/login.jsp"
    
    def __init__(self):
        self.accessControlService = __spring__.getBean("accessControlService")
        self.params = ParamUtil(request)
        if self.loginUser == None:
            # 暂时直接返回到首页
            #response.sendRedirect(self.LOGIN + "?redUrl=" + request.getRequestURL().toString())
            response.sendRedirect( request.getContextPath() + self.LOGIN)
            
    def isSystemAdmin(self):
        if self.loginUser == None:
            return False
        return self.accessControlService.isSystemAdmin(self.loginUser)
        
    def isSystemUserAdmin(self):
        if self.loginUser == None:
            return False
        return self.accessControlService.isSystemUserAdmin(self.loginUser)
    
    def isSystemContentAdmin(self):
        if self.loginUser == None:
            return False
        return self.accessControlService.isSystemContentAdmin(self.loginUser)
    
    def empty_func(*args, **kw):
        return True
        
        
    
    
