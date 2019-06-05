#coding=utf-8
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import ParamUtil
from java.util import HashMap
from cn.edustar.jitar.model import CommonObject

class CommonData(JythonBaseAction):      
    def __init__(self):
        self.ERROR = "/WEB-INF/ftl/Error.ftl"
        self.ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl"
        self.ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
        self.SUCCESS = "/WEB-INF/ftl/success.ftl"
        self.LOGIN = "/login.jsp"
        self.params = ParamUtil(request)
        self.parentGuid = self.params.safeGetStringParam("guid")
        self.parentType = self.params.safeGetStringParam("type")
        self.questionAnswerService = __spring__.getBean("questionAnswerService")
        self.pageFrameService = __spring__.getBean("pageFrameService")
        self.pluginAuthorityCheckService = __spring__.getBean("pluginAuthorityCheckService")
        self.commonObject = CommonObject(self.parentType, self.parentGuid)
        
        # 因为随处需要使用这2个变量，则此时就装入请求对象中。
        request.setAttribute("parentGuid", self.parentGuid)
        request.setAttribute("parentType", self.parentType)
        request.setAttribute("SiteUrl",self.pageFrameService.getSiteUrl())
        request.setAttribute("UserMgrUrl",self.pageFrameService.getUserMgrUrl())
        
        pass
    
    def get_client_ip(self):
        strip = request.getHeader("x-forwarded-for")
        if strip == None or strip == "":
            strip = request.getRemoteAddr()
        return strip
    
    def writeToResponse(self, _content):
        response.getWriter().println(_content)