from base_channel_manage import *
from cn.edustar.jitar.data import Command
from java.util import ArrayList


class index(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        
    def execute(self):
        accessControlService = __spring__.getBean("accessControlService")
        cmd = request.getParameter("cmd")
        isSystemAdmin = accessControlService.isSystemAdmin(self.loginUser)
        isChannelSystemAdminList = accessControlService.getAllAccessControlByUserAndObjectType(self.loginUser,11)
        isChannelUserAdminList = accessControlService.getAllAccessControlByUserAndObjectType(self.loginUser,12)
        isChannelContentAdminList = accessControlService.getAllAccessControlByUserAndObjectType(self.loginUser,13)

        if isSystemAdmin == False and len(isChannelSystemAdminList) < 1 and len(isChannelSystemAdminList) < 1 and len(isChannelSystemAdminList) < 1:
            self.addActionError(u"你无权管理频道。")
            return self.ERROR
        
        if cmd == "menu":
            
            channel_list = None
            #判断是否是系统管理员
            
            if isSystemAdmin:
                request.setAttribute("admin_type","admin")
                channel_list = self.channelPageService.getChannelList()
            else:
                # 得到当前用户可以管理的频道列表
                qry = Command("SELECT DISTINCT objectId as objectId FROM AccessControl Where userId = " + str(self.loginUser.userId) + " And (objectType = 11 or objectType = 12 or objectType = 13) Order By objectId ASC""") 
                ChannelID = qry.open()
                channel_list = []
                for id in ChannelID:
                    channel = self.channelPageService.getChannel(id)
                    channel_list.append(channel)
                if len(channel_list) > 0:
                    request.setAttribute("admin_type","channeladmin")
                else:
                    request.setAttribute("admin_type","")
            
            request.setAttribute("channel_list",channel_list)          
            return "/WEB-INF/ftl/channel/menu.ftl"
        elif cmd == "main":
            return "/WEB-INF/ftl/channel/main.ftl"
        elif cmd == "head":
            return "/WEB-INF/ftl/channel/head.ftl"
        else:
            return "/WEB-INF/ftl/channel/index.ftl"