from cn.edustar.jitar.util import ParamUtil, CommonUtil
from site_config import SiteConfig
from base_action import ActionExecutor, ActionResult
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.pojos import Group
from cn.edustar.jitar.pojos import GroupMember
from cn.edustar.jitar.dao.hibernate import ChatRoomDaoHibernate

class chat(ActionExecutor):
    def __init__(self):
        self.chatroom_svc = __spring__.getBean("chatRoomService")
        self.group_svc = __spring__.getBean("groupService")
        self.prepareCourse_svc = __spring__.getBean("prepareCourseService")
        self.params = ParamUtil(request)
        
    def execute(self):
        if self.loginUser == None:
            backUrl = request.getAttribute('javax.servlet.forward.request_uri')
            if backUrl == None or backUrl == "":backUrl = request.requestURI
            backUrl = backUrl + "?" + request.getQueryString()
            response.sendRedirect(CommonUtil.getSiteUrl(request) + "login.jsp?redUrl=" + CommonUtil.urlUtf8Encode(CommonUtil.getSiteServer(request) + backUrl))
        
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
        
        if groupId == None or groupId == 0:
            if prepareCourseId == None or prepareCourseId == 0:
                if roomId == None or roomId == 0:
                    roomId = 1
            else:
                #备课
                iType = 1
                prepareCourse = self.prepareCourse_svc.getPrepareCourse(prepareCourseId)
                if prepareCourse == None:
                    request.setAttribute("err", u"无法加载集体备课。")
                    return "/WEB-INF/ftl/chat/chatErr.ftl"
                pm = self.prepareCourse_svc.getPrepareCourseMemberByCourseIdAndUserId(prepareCourseId,self.loginUser.userId)
                if pm == None:
                    request.setAttribute("err", u"只有集备成员才能进行聊天。")
                    return "/WEB-INF/ftl/chat/chatErr.ftl"
                ChatRoomName = prepareCourse.getTitle() + u"聊天室"
                roomId = str(self.chatroom_svc.getPrepareCourseRoomId(prepareCourseId))
                if roomId == "-1":
                    request.setAttribute("err", u"没有找到集体备课信息")
                    return "/WEB-INF/ftl/chat/chatErr.ftl"
        else:
            iType = 2
            group = self.group_svc.getGroup(groupId)
            if group == None:
                request.setAttribute("err", u"无法加载协作组。")
                return "/WEB-INF/ftl/chat/chatErr.ftl"
            #判断组成员
            gm = self.group_svc.getGroupMemberByGroupIdAndUserId(groupId,self.loginUser.userId)
            if gm == None:
                request.setAttribute("err", u"只有该组成员才能进行聊天。")
                return "/WEB-INF/ftl/chat/chatErr.ftl"
            if gm.status!=GroupMember.STATUS_NORMAL:
                if gm.status==GroupMember.STATUS_WAIT_AUDIT:
                    request.setAttribute("err", u"该组成员状态待审核。")
                    return "/WEB-INF/ftl/chat/chatErr.ftl"
                elif gm.status==GroupMember.STATUS_DELETING:
                    request.setAttribute("err", u"该组成员状态待删除。")
                    return "/WEB-INF/ftl/chat/chatErr.ftl"
                elif gm.status==GroupMember.STATUS_LOCKED:
                    request.setAttribute("err", u"该组成员状态被锁定。")
                    return "/WEB-INF/ftl/chat/chatErr.ftl"
                elif gm.status==GroupMember.STATUS_INVITING:
                    request.setAttribute("err", u"该组成员邀请未回应。")
                    return "/WEB-INF/ftl/chat/chatErr.ftl"
                else:
                    request.setAttribute("err", u"该组成员状态非正常状态。")
                    return "/WEB-INF/ftl/chat/chatErr.ftl"
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
        request.setAttribute("prepareCourseId", prepareCourseId)
        request.setAttribute("prepareCourse", prepareCourse)
        request.setAttribute("ChatRoomName", ChatRoomName)
        request.setAttribute("roomId", roomId)    
        return "/WEB-INF/ftl/chat/chat.ftl"