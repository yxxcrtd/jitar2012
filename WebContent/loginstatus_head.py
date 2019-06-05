from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.model import Configure
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import ParamUtil
from net.zdsoft.passport.service.client import PassportClient

class loginstatus_head(JythonBaseAction):

    def __init__(self):
    	self.userService = __spring__.getBean("userService")
    
    def execute(self):
        # 是否是浙大统一用户  
        if request.getServletContext().getInitParameter("passportURL") == None:
            self.passportURL = None
        else:
            self.passportURL = PassportClient.getInstance().getPassportURL()
        if self.passportURL == None:
            self.passportURL = ""
        if self.passportURL == "http://":
            self.passportURL = ""
        if request.getServletContext().getInitParameter("passportURL") != None:                
            self.passportServerId = PassportClient.getInstance().getServerId()
            self.passportVerifyKey = PassportClient.getInstance().getVerifyKey()
            request.setAttribute("passportURL", self.passportURL)
            request.setAttribute("passportServerId", self.passportServerId)
            request.setAttribute("passportVerifyKey", self.passportVerifyKey)
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) or accessControlService.isSystemContentAdmin(self.loginUser) or accessControlService.isSystemUserAdmin(self.loginUser):
            request.setAttribute("isSysAdmin", "1")
        
        unitService = __spring__.getBean("unitService")
        rootUnit = unitService.getRootUnit()
        request.setAttribute("unit", rootUnit)
        request.setAttribute("ru", request.getScheme() + "://" + request.getServerName() + ":" + str(request.getServerPort()) + request.getContextPath())
        ssoId = request.getRemoteUser()
        if ssoId != None and ssoId != "":
        	session.setAttribute("ssoId", ssoId)
        	user = self.userService.getUserByLoginName(ssoId)
        	if user != None:
        		session.setAttribute(User.SESSION_LOGIN_NAME_KEY, ssoId);
        		session.setAttribute(User.SESSION_LOGIN_USERMODEL_KEY, user)   
        
        if self.passportURL == "":        
            return "/WEB-INF/ftl/mengv1/index/loginstatus_head.ftl"
        else:
            return "/zdsoft/loginstatus_head.ftl"