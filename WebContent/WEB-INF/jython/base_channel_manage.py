# encoding=utf-8
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from cn.edustar.jitar.pojos import AccessControl

# 后台管理的新基类
class BaseChannelManage(JythonBaseAction):
    ERROR = "/WEB-INF/ftl/Error.ftl"
    ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl"
    ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    LOGIN = "login.jsp"
    
    def __init__(self):
        self.channelPageService = __spring__.getBean("channelPageService")
        self.params = ParamUtil(request)
        if self.loginUser == None:
            response.sendRedirect(self.getSiteUrl() + self.LOGIN + "?redUrl=" + self.getSiteUrl() + "manage/channel/index.py")    
        
    def isSystemAdmin(self):
        if self.loginUser == None:return False
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == True:
            return True
        return accessControlService.isSystemAdmin(self.loginUser)
    
    def isChannelSystemAdmin(self, channel):
        accessControlService = __spring__.getBean("accessControlService")
        return accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_CHANNELSYSTEMADMIN, channel.channelId)
    
    def isChannelUserAdmin(self, channel):
        accessControlService = __spring__.getBean("accessControlService")
        return accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_CHANNELUSERADMIN, channel.channelId)
    
    def isChannelContentAdmin(self, channel):
        accessControlService = __spring__.getBean("accessControlService")
        return accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN, channel.channelId)
    
    def GetAdminType(self, channel):
        AdminType = "|"
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser):
            AdminType = AdminType + "SystemSuperAdmin|"
        if accessControlService.isSystemUserAdmin(self.loginUser):
            AdminType = AdminType + "SystemUserAdmin|"
        if accessControlService.isSystemContentAdmin(self.loginUser):
            AdminType = AdminType + "SystemContentAdmin|"
        if accessControlService.getAccessControlByUserAndObject(self.loginUser.userId, AccessControl.OBJECTTYPE_CHANNELSYSTEMADMIN, channel.channelId) != None:
            AdminType = AdminType + "ChannelSystemAdmin|"
        if accessControlService.getAccessControlByUserAndObject(self.loginUser.userId, AccessControl.OBJECTTYPE_CHANNELUSERADMIN, channel.channelId) != None:
            AdminType = AdminType + "ChannelUserAdmin|"
        if accessControlService.getAccessControlByUserAndObject(self.loginUser.userId, AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN, channel.channelId) != None:
            AdminType = AdminType + "ChannelContentAdmin|"
        if AdminType == "|":AdminType = ""
        return AdminType
    def getSiteUrl(self):
        return CommonUtil.getSiteUrl(request)
