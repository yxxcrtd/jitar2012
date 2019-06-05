from base_channel_manage import BaseChannelManage
from cn.edustar.jitar.pojos import AccessControl
from accesscontrol_query import AccessControlQuery

class admin_channel_manager_by_channel_right(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelPageService = __spring__.getBean("channelPageService")
        self.accessControlService = __spring__.getBean("accessControlService")
        self.channel = None
        
    def execute(self):
        if self.accessControlService.isSystemAdmin(self.loginUser) == False:
            if self.accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SUPERADMIN, 0) == False and self.accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SYSTEMUSERADMIN, 0) == False:      # 没有用户管理权.
                self.addActionError(u"您不具有用户管理权限。")
                return self.ERROR
        
        channelId = self.params.safeGetIntParam("channelId")
        self.channel = self.channelPageService.getChannel(channelId)

        if request.getMethod() == "POST":
            guids = self.params.safeGetIntValues("guid")
            for accessControlId in guids:
                accessControl = self.accessControlService.getAccessControlById(accessControlId)
                if accessControl != None:
                    #print accessControl
                    self.accessControlService.deleteAccessControl(accessControl)
        
        self.get_admin_list()
        ObjectType = [u"未知的权限", u"系统超级管理员", u"系统用户管理员", u"系统内容管理员", u"机构系统管理员", u"机构用户管理员", u"机构内容管理员", u"学科系统管理员", u"学科用户管理员", u"学科内容管理员", u"元学科内容管理员",u"频道系统管理员",u"频道用户管理员",u"频道内容管理员"]
        request.setAttribute("ObjectType", ObjectType)
        request.setAttribute("channel", self.channel)
        return "/WEB-INF/ftl/admin/admin_channel_manager_by_channel_right.ftl"
    
    def get_admin_list(self):
        objectType = self.params.safeGetIntParam("objectType")
        qry = AccessControlQuery("ac.accessControlId, ac.userId, ac.objectType, ac.objectId, ac.objectTitle, u.loginName,u.trueName,u.unitId")
        if objectType != 0:
            qry.objectType = objectType
        else:
            qry.custormAndWhere = "(ac.objectType = 11 or ac.objectType = 12 or ac.objectType = 13)"            
        qry.orderType = 1
        
        if self.channel != None:
            qry.objectId = self.channel.channelId
        pager = self.createPager()
        pager.totalRows = qry.count() 
        admin_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("objectType", objectType)
        request.setAttribute("admin_list", admin_list)
        
    def createPager(self):
        # pager.
        pager = self.params.createPager()
        pager.itemName = u"管理员"
        pager.itemUnit = u"名"
        pager.pageSize = 20
        return pager