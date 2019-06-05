from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil,DateUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,Action, ActionReply
from java.text import SimpleDateFormat
from java.util import Date,Calendar,GregorianCalendar
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *
from action_reply_query import ActionReplyQuery

class show_preparecourse_action_content(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter() 
        self.out = response.getWriter()
        self.Action = None
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.act_svc = __jitar__.actionService
        self.frd_svc = __jitar__.friendService
        self.grp_svc = __jitar__.groupService
        
    
    def execute(self):
        self.getBaseData()
        if self.prepareCourseId == 0:
            self.printer.println(u"无效的课程标识。")
            return
        self.prepareCourse = self.getBasePrepareCourse()
        if self.prepareCourse == None:
            self.printer.println(u"没有加载到所请求的备课。")
            return
        
        page = self.getPrepareCoursePageWithCustomSkin(self.prepareCourse)
        widgets = [                  
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        
        
        self.actionId = self.params.getIntParam("actionId")
        
        if self.actionId == None:
            operation_result = u"缺少活动的标识。"
            request.setAttribute("operation_result",operation_result)
            request.setAttribute("prepareCourse",self.prepareCourse)
            request.setAttribute("widget_list",widgets)
            request.setAttribute("page", page)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"    

        self.action = self.act_svc.getActionById(self.actionId)
        
        if self.action == None:
            operation_result = u"该活动不存在。"
            request.setAttribute("prepareCourse",self.prepareCourse)
            request.setAttribute("operation_result",operation_result)
            request.setAttribute("widget_list",widgets)
            request.setAttribute("page", page)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"          
          
        if self.action.status != 0:
            operation_result = u"该活动的状态目前不正常。不能浏览活动信息。"
            request.setAttribute("operation_result",operation_result)
            request.setAttribute("prepareCourse",self.prepareCourse)
            request.setAttribute("widget_list",widgets)
            request.setAttribute("page", page)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"        
        
        request.setAttribute("widget_list",widgets)
        request.setAttribute("page", page)
        request.setAttribute("prepareCourse",self.prepareCourse)
        request.setAttribute("prepareCourseId",self.prepareCourseId)
        # 能否参加的标志        
        self.canAttend = self.current_can_attend_action()
        
        if self.can_view() == False:
            operation_result = u"该活动的为非公开，你无权查看。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        
        user_list = self.act_svc.getActionUserWithDistUnit()
        if request.getMethod() == "POST":
            if self.params.getStringParam("cmd") == "comment":
                self.actionComment()
            else:                
                if self.canAttend == "1":
                    if self.act_svc.userIsInAction(self.loginUser.userId, self.action.actionId) == False:
                        return self.saveActionUser()
                    else:
                        self.out.println(u"用户 " + str(self.loginUser.userId) + u" 已经存在。")
                else:
                    self.out.println(u"您暂时不能参加该活动。")
                    return
                
        if self.loginUser == None:
            can_manage = "0"
        else:
            accessControlService = __spring__.getBean("accessControlService")
            can_manage = str((self.loginUser == self.action.createUserId) or (accessControlService.isSystemAdmin(self.loginUser)))
        request.setAttribute("can_manage", can_manage)
        request.setAttribute("can_comment", self.check_user_can_post_comment_action())
        request.setAttribute("action", self.action)
        request.setAttribute("loginUser", self.loginUser)
        request.setAttribute("canAttend", self.canAttend)
        self.get_reply_list()
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/show_preparecourse_action_content.ftl"
    
    def saveActionUser(self):
        attenduserCount = self.params.getStringParam("usernum")
        if attenduserCount.isdigit() == False:
            self.out.println(u"活动参加人数不能为非数字。")
            
        actionUser = ActionUser()
        actionUser.userId = self.loginUser.userId
        actionUser.actionId = self.action.actionId
        actionUser.attendUserCount = 1
        actionUser.isApprove = 1
        actionUser.status = 0
        actionUser.description = request.getParameter("userdesc")
        
        self.act_svc.addActionUser(actionUser)
        return ""

    def actionComment(self):
        if self.loginUser == None:
            self.out.println(u"请<a href='../../../../login.jsp'>登录</a>发表留言。")
            return
        title = self.params.getStringParam("title")
        comment = self.params.getStringParam("actionComment")
        if title == None or title == "":
            self.out.println(u"请输入留言标题。")
            return
        if comment == None or comment == "":
            self.out.println(u"请输入留言内容。")
            return
        
        actionReply = ActionReply()
        actionReply.setTopic(title)
        actionReply.setContent(comment)
        actionReply.setAddIp(self.get_client_ip())
        actionReply.setUserId(self.loginUser.userId)
        actionReply.setActionId(self.action.actionId)
        self.act_svc.addComment(actionReply)
        
        #计算转向页面地址
        pageSize = self.params.getIntParam("pageSize")
        totalRows = self.params.getIntParam("totalRows") + 1
        if pageSize == 0:pageSize = 10
        pageCount = totalRows / pageSize 
        if totalRows % pageSize != 0: pageCount = pageCount + 1
        response.sendRedirect("show_preparecourse_action_content.py?actionId=" + str(self.action.actionId) + "&page=" + str(pageCount))
        return None 
        
    def current_can_attend_action(self):        
        usercount = self.act_svc.getUserCountByActionId(self.action.actionId)
        request.setAttribute("usercount", usercount)
        
        # 活动如果报名日期截止了，不能再参加,当前日期大于设置的报名截止日期
        if DateUtil.compareDateTime(Date(), self.action.attendLimitDateTime) > -1:
            request.setAttribute("isDeadLimit", "1")            
            return "0"
        
        # 活动状态不正常，不能参加
        if self.action.status != 0:
            return "0"
        
        # 当前用户没有登录，不能参加
        if self.loginUser == None:
            return "0"
        
        # 活动如果是不是公开的，不能参加，只能邀请        
        if self.action.visibility != 0:
            return "0"
                  
        
        # 参加的人数是否超过了许可人数：0 表示不限制
        if self.action.userLimit == 0:
            return "1"
        else:            
            if usercount < self.action.userLimit :
                return "1"
            else:
                return "0"
        
        # 如果只能邀请，用户也不能参加
        if self.action.actionType == 2:
            return "0"
                
        # 外部条件已经判断完毕
        # 下面判断个人的情况
                
        if self.action.actionType == 0: # 任意参加
            return "1"
        elif self.action.actionType == 1:  # 组内人员参加
            # 如果是组内，
            # 如果用户，则好友可以参加
            # 如果是群组。则组内人员可以参加
            # 如果是集体备课，则组内人员可以参加
            return self.check_user_in_group()
    
    def check_user_in_group(self): 
        if self.loginUser== None:
            return "0"
        if self.action.ownerType == "user":
            # 得到创建者的好友列表            
            friend_list = self.frd_svc.isUserFriend(self.loginUser.userId, self.action.createUserId)
            if friend_list == True:
                return "1"
            else:
                return "0"
            #print "friend_list = ", friend_list
        elif self.action.ownerType == "group":
            # 当前用户是否是组内成员
            # 得到群组信息
            self.group = self.grp_svc.getGroup(self.action.ownerId)
            if self.group == None:
                return "0"
            group_member = self.grp_svc.getGroupMemberByGroupIdAndUserId(self.group.groupId, self.loginUser.userId)
            
            if group_member == None:
                return "0"
            else:
                return "1"            

        # 集体备课
        elif self.action.ownerType == "preparecourse":
            if self.pc_svc.checkUserInPreCourse(self.action.ownerId, self.loginUser.userId):
                return "1"
            else:
                return "0"
            
    def get_client_ip(self):
        strip = request.getRemoteAddr()
        if strip == None or strip == "":
            strip = request.getHeader("x-forwarded-for")
        return strip
    
    def get_reply_list(self):
        pager = self.params.createPager()
        qry = ActionReplyQuery(""" actr.actionReplyId, actr.createDate,actr.topic,actr.content,actr.userId """)
        qry.actionId = self.action.actionId
        pager.setPageSize(6)        
        pager.itemName = u"回复"
        pager.itemUnit = u"个"
        pager.setTotalRows(qry.count())
        action_reply_list = qry.query_map(pager)
        if action_reply_list.size() > 0:
            request.setAttribute("action_reply_list",action_reply_list)
        request.setAttribute("pager",pager)

    def check_user_can_post_comment_action(self):
        if self.loginUser == None:
            return "0"        
        if self.action.status != 0:
            return "0"
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser):
            return "1"
        if self.loginUser.userId == self.action.createUserId:
            return "1"
        return self.check_user_in_group()
        
    def can_view(self):
        # 如果是非公开的，则只有组内用户可以看到
        if self.action.visibility == 1:
            if self.check_user_in_group() == "1":
                return True
            else:
                return False
        else:
            return True