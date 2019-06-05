from base_channel_manage import *
from channel_query import ChannelUserQuery
from cn.edustar.jitar.pojos import AccessControl, ChannelUser

class channel_user_list(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
        self.userService = __spring__.getBean("userService")
        self.unitService = __spring__.getBean("unitService")
        
    def execute(self):
        self.channelId = self.params.safeGetIntParam("channelId")
        self.channel = self.channelPageService.getChannel(self.channelId)
        if self.channel == None:
            self.addActionError(u"不能加载频道对象。")
            return self.ERROR
        if self.isSystemAdmin() == False and self.isChannelSystemAdmin(self.channel) == False and self.isChannelUserAdmin(self.channel) == False:
            self.addActionError(u"你无权管理本频道。")
            return self.ERROR
        admin_type = ""        
        if self.isChannelUserAdmin(self.channel) == True:
            admin_type = "user_admin"
        if self.isSystemAdmin() == True or self.isChannelSystemAdmin(self.channel) == True:
            admin_type = "sys_admin"
        
        self.cmd = self.params.safeGetStringParam("cmd")
        request.setAttribute("cmd", self.cmd)
        request.setAttribute("admin_type", admin_type)
        if request.getMethod() == "POST":
            self.save_post()
        
        return self.get_list()
    
    def save_post(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            user = self.userService.getUserById(g)
            if user != None:
                if self.cmd == "remove":
                    self.set_admin(user, "remove")
                elif self.cmd == "content_admin":
                    self.set_admin(user, "content")
                elif self.cmd == "uncontent_admin":       
                    self.set_admin(user, "uncontent")
                elif self.cmd == "user_admin":
                    self.set_admin(user, "user")
                elif self.cmd == "unuser_admin":
                    self.set_admin(user, "unuser")
                elif self.cmd == "add":
                    self.set_admin(user, "add")
                    
    def set_admin(self, user, admin_type):
        accessControlService = __spring__.getBean("accessControlService")
        if admin_type == "content":
            if accessControlService.checkUserAccessControlIsExists(user.userId, AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN, self.channel.channelId) == False:
                accessControl = AccessControl()
                accessControl.setUserId(user.userId)
                accessControl.setObjectType(AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN)
                accessControl.setObjectId(self.channel.channelId)
                accessControl.setObjectTitle(self.channel.title)
                accessControlService.saveOrUpdateAccessControl(accessControl)
        elif admin_type == "uncontent":                
            if accessControlService.checkUserAccessControlIsExists(user.userId, AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN, self.channel.channelId) == True:
                accessControlService.deleteAccessControlByUserIdObjectTypeObjectId(user.userId, AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN, self.channel.channelId)
        elif admin_type == "user":                
            if accessControlService.checkUserAccessControlIsExists(user.userId, AccessControl.OBJECTTYPE_CHANNELUSERADMIN, self.channel.channelId) == False:
                accessControl = AccessControl()
                accessControl.setUserId(user.userId)
                accessControl.setObjectType(AccessControl.OBJECTTYPE_CHANNELUSERADMIN)
                accessControl.setObjectId(self.channel.channelId)
                accessControl.setObjectTitle(self.channel.title)
                accessControlService.saveOrUpdateAccessControl(accessControl)
        elif admin_type == "unuser":                
            if accessControlService.checkUserAccessControlIsExists(user.userId, AccessControl.OBJECTTYPE_CHANNELUSERADMIN, self.channel.channelId) == True:
                accessControlService.deleteAccessControlByUserIdObjectTypeObjectId(user.userId, AccessControl.OBJECTTYPE_CHANNELUSERADMIN, self.channel.channelId)
        elif admin_type == "remove":
            channelUser = self.channelPageService.deleteChannelUserByUserIdAndChannelId(self.channel.channelId, user.userId)
        elif admin_type == "add":
            channelUser = self.channelPageService.getChannelUserByUserIdAndChannelId(user.userId, self.channel.channelId)
            if channelUser == None:
                if user.unitId != None:
                    unit = self.unitService.getUnitById(user.unitId)
                    if unit != None:
                        channelUser = ChannelUser()                    
                        channelUser.setChannelId(self.channel.channelId)
                        channelUser.setUserId(user.userId)
                        channelUser.setUnitId(unit.unitId)
                        channelUser.setUnitTitle(unit.unitTitle)
                        self.channelPageService.saveOrUpdateChannelUser(channelUser)
        
    def get_list(self):
        k = self.params.safeGetStringParam("k")
        f = self.params.safeGetStringParam("f")
        ustate = self.params.safeGetStringParam("ustate")
        if ustate == "":
            ustate = "-1"
        if f == "":f = "name"
        qry = ChannelUserQuery("cu.channelUserId, cu.userId, user.loginName, user.trueName, user.userStatus, user.createDate, user.userType, cu.unitId, cu.unitTitle")
        qry.orderType = 0
        qry.channelId = self.channel.channelId
        if ustate == "0":
            qry.userStatus = 0
        elif ustate == "1":
            qry.userStatus = 1
        elif ustate == "2":
            qry.userStatus = 2
        elif ustate == "3":
            qry.userStatus = 3
        else:
            qry.userStatus = None
        if k != "":
            qry.f = f
            qry.k = k
            request.setAttribute("k", k)
            request.setAttribute("f", f)
        pager = self.createPager()
        
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)
        request.setAttribute("ustate", ustate)
        request.setAttribute("pager", pager)       
        request.setAttribute("user_list" , user_list)
        request.setAttribute("channel" , self.channel) 
        return "/WEB-INF/ftl/channel/channel_user_list.ftl"
        
    def createPager(self):
        # 调用Java的函数.
        pager = self.params.createPager()
        pager.itemName = u"用户"
        pager.itemUnit = u"个"
        pager.pageSize = 10
        return pager
        