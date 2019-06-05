from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from base_action import ActionExecutor, ActionResult
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.pojos import Group
from cn.edustar.jitar.dao.hibernate import ChatRoomDaoHibernate;

class head(ActionExecutor):
    def __init__(self):
        self.chatroom_svc = __spring__.getBean("chatRoomService")
        self.group_svc = __spring__.getBean("groupService")
        self.prepareCourse_svc = __spring__.getBean("prepareCourseService")
        self.params = ParamUtil(request)
        
    def execute(self):
        if self.loginUser == None:
            request.setAttribute("err", u"请先登录")
            return "/WEB-INF/ftl/chat/chatErr.ftl"
        userName = self.loginUser.trueName
        group = None
        if userName == None:
            userName = self.loginUser.loginName
        roomId = self.params.safeGetIntParam("roomId")
        groupId = self.params.safeGetIntParam("groupId")
        prepareCourseId = self.params.safeGetIntParam("prepareCourseId")
        prepareCourse = None
        iType = 0
        ChatRoomName = u"聊天室"
        if roomId == None or roomId == 0:
            if groupId == None or groupId == 0:
                if prepareCourseId == None or prepareCourseId == 0:
                    roomId = 1
                else:
                    #备课
                    iType = 1
                    prepareCourse = self.prepareCourse_svc.getPrepareCourse(prepareCourseId)
                    ChatRoomName = prepareCourse.getTitle() + u"聊天室"
                    roomId = str(self.chatroom_svc.getPrepareCourseRoomId(prepareCourseId))
                    if roomId == "-1":
                        request.setAttribute("err", u"没有找到集体备课信息")
                        return "/WEB-INF/ftl/chat/chatErr.ftl"
            else:
                iType = 2
                group = self.group_svc.getGroup(groupId)  
                ChatRoomName = group.getGroupTitle() + u"聊天室"
                roomId = str(self.chatroom_svc.getRoomId(groupId))
                if roomId == "-1":
                    request.setAttribute("err", u"没有找到群组信息")
                    return "/WEB-INF/ftl/chat/chatErr.ftl"
        if roomId == None or roomId == 0:
            roomId = 1
        
        #检查是否是群组,是的话需要显示的群组的Banner
        request.setAttribute("type", iType)
        request.setAttribute("group", group)
        request.setAttribute("groupId", groupId)
        request.setAttribute("prepareCourse", prepareCourse)
        request.setAttribute("ChatRoomName", ChatRoomName)
        request.setAttribute("roomId", roomId)   
        request.setAttribute("ru", request.getScheme() + "://" + request.getServerName() + ":" + str(request.getServerPort()) + request.getContextPath()) 
        return "/WEB-INF/ftl/chat/head.ftl"
