from java.util import Date
from cn.edustar.jitar.util import DateUtil
from base_action import BaseAction
from cn.edustar.jitar.pojos import Action, ActionUser, ActionReply
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from action_query import ActionQuery
from site_config import SiteConfig
from cn.edustar.jitar.data import *
from action_reply_query import ActionReplyQuery

class showAction(BaseAction):
    
    act_svc = __jitar__.actionService
    frd_svc = __jitar__.friendService
    grp_svc = __jitar__.groupService
    
    def execute(self):
        self.pc_svc = __jitar__.getPrepareCourseService()
        site_config = SiteConfig()
        site_config.get_config()
        
        self.out = response.writer
        self.params = ParamUtil(request)
        actionId = self.params.getIntParam("actionId")
        
        if actionId == None or actionId == 0:
            self.addActionLink(u"返回活动列表",CommonUtil.getSiteUrl(request) + "actions.py")
            self.addActionError(u"缺少标识。")
            return self.ERROR
        #更新统计
        self.act_svc.updateActionUserStatById(actionId)
        
        self.action = self.act_svc.getActionById(actionId)
        
        if self.action == None:
            self.addActionLink(u"返回活动列表",CommonUtil.getSiteUrl(request) + "actions.py")
            self.addActionError(u"该活动不存在。")
            return self.ERROR
        if self.action.status != 0 and self.action.status != 3:
            self.addActionLink(u"返回活动列表",CommonUtil.getSiteUrl(request) + "actions.py")
            self.addActionError(u"该活动的状态目前不正常。不能浏览活动信息。")
            return self.ERROR
        
        if self.can_view() == False:
            self.addActionLink(u"返回活动列表",CommonUtil.getSiteUrl(request) + "actions.py")
            self.addActionError(u"该活动为非公开，你无权查看。")
            return self.ERROR
        
        #得到该活动的回复
        self.get_reply_list()        
        # 能否参加的标志
        self.canAttend = self.current_can_attend_action()        
        # loginUser 来自 BaseAction     
        user_list = self.act_svc.getActionUserWithDistUnit()
        
        if request.getMethod() == "POST":
            if self.params.getStringParam("cmd") == "comment":
                #发表评论的条件：
                # 登录用户，活动成员，或者组内成员
                if self.loginUser == None:
                    self.addActionLink(u"返回活动",CommonUtil.getSiteUrl(request) + "manage/action/showAction.py?actionId=" + str(self.action.actionId))
                    self.addActionError(u"请先登录。")
                    return self.ERROR
                canPublishComment = self.can_publish_comment()
                if canPublishComment["status"] == "true":
                    return self.actionComment()
                else:
                    self.addActionLink(u"返回活动",CommonUtil.getSiteUrl(request) + "manage/action/showAction.py?actionId=" + str(self.action.actionId))
                    self.addActionError(canPublishComment["message"])
                    return self.ERROR
            else:                
                if self.canAttend == "1":
                    if self.act_svc.userIsInAction(self.loginUser.userId, self.action.actionId) == False:
                        #检查参加的人数
                        if self.action.userLimit != 0:
                            uc = self.act_svc.getUserCountByActionId(self.action.actionId)
                            if uc >= self.action.userLimit:
                                self.addActionLink(u"返回活动",CommonUtil.getSiteUrl(request) + "manage/action/showAction.py?actionId=" + str(self.action.actionId))
                                self.addActionError(u"此活动人数已满。")
                                return self.ERROR
                        return self.saveActionUser()
                    else:
                        self.addActionLink(u"返回活动",CommonUtil.getSiteUrl(request) + "manage/action/showAction.py?actionId=" + str(self.action.actionId))
                        self.addActionError(u"您已经参加了该活动，请勿重复申请。")
                        return self.ERROR
                else:
                    self.addActionLink(u"返回活动列表",CommonUtil.getSiteUrl(request) + "actions.py")
                    self.addActionError(u"您暂时不能参加该活动。")
                    return self.ERROR
                
        if self.loginUser == None:
            can_manage = "0"
        else:
            if self.act_svc.canManageAction(self.action, self.loginUser) == True:
                can_manage = "1"
            else:
                can_manage = "0"                
        
        redUrl = request.getRequestURI()
        if request.getQueryString() != None:
            redUrl += '?' + request.getQueryString()
        if self.loginUser != None:
            actionUser = self.act_svc.getActionUserByUserIdAndActionId(self.loginUser.userId, self.action.actionId)
            request.setAttribute("actionUser", actionUser)
        request.setAttribute("can_manage", can_manage)
        request.setAttribute("can_comment", self.check_user_can_post_comment_action())
        request.setAttribute("action", self.action)
        request.setAttribute("loginUser", self.loginUser)
        request.setAttribute("canAttend", self.canAttend)
        request.setAttribute("redUrl", redUrl)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/action/action_show.ftl"

    def saveActionUser(self):
        attenduserCount = request.getParameter("usernum")
        if attenduserCount.isdigit() == False:
            self.out.write(u"活动参加人数不能为非数字。")
            
        actionUser = ActionUser()
        actionUser.userId = self.loginUser.userId
        actionUser.actionId = self.action.actionId
        actionUser.attendUserCount = 1
        actionUser.isApprove = 1
        actionUser.status = 1
        actionUser.description = request.getParameter("userdesc")
        
        self.act_svc.addActionUser(actionUser)
        return self.SUCCESS

    def actionComment(self):
        if self.loginUser == None:
            self.addActionLink(u"返回活动",CommonUtil.getSiteUrl(request) + "manage/action/showAction.py?actionId=" + str(self.action.actionId))
            self.addActionError(u"请<a href='../../login.jsp'>登录</a>发表留言。")
            return self.ERROR
        
        title = self.params.getStringParam("title")
        comment = self.params.getStringParam("actionComment")
        if title == None or title == "":
            self.addActionLink(u"返回活动",CommonUtil.getSiteUrl(request) + "manage/action/showAction.py?actionId=" + str(self.action.actionId))
            self.addActionError(u"请输入讨论标题。")
            return self.ERROR
        if comment == None or comment == "":
            self.addActionLink(u"返回活动",CommonUtil.getSiteUrl(request) + "manage/action/showAction.py?actionId=" + str(self.action.actionId))
            self.addActionError(u"请输入讨论内容。")
            return self.ERROR
        
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
        if pageSize <= 0:pageSize = 10
        pageCount = totalRows / pageSize 
        if totalRows % pageSize != 0: pageCount = pageCount + 1
        response.sendRedirect("showAction.py?actionId=" + str(self.action.actionId) + "&page=" + str(pageCount))
        return None
        
    def current_can_attend_action(self):
        
        usercount = self.act_svc.getUserCountByActionId(self.action.actionId)
        request.setAttribute("usercount", usercount)
        
        # 活动如果报名日期截止了，不能再参加,当前日期大于设置的报名截止日期
        if DateUtil.compareDateTime(Date(), self.action.attendLimitDateTime) > -1:
            request.setAttribute("isDeadLimit", "1")
            #print "已经超过活动截止时间"
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
        
        # 如果只能邀请，用户也不能参加
        if self.action.actionType == 2:
            return "0"
        elif self.action.actionType == 0: #任意参加
            if self.action.userLimit == 0:
                return "1"
            else:
                if usercount < self.action.userLimit :
                    return "1"
                else:
                    return "0"
        elif self.action.actionType == 1: #组内成员参加
            return self.check_user_in_group()
        else:
            return "0"            
    
    def check_user_in_group(self):
        if self.loginUser == None:
            return "0"
        #先检查是否在活动人员列表里面
        if self.act_svc.userIsInAction(self.loginUser.userId, self.action.actionId) == True:
            return "1"
        
        if self.action.ownerType == "user":
            # 得到创建者的好友列表
            
            isUserFriend = self.frd_svc.isUserFriend(self.loginUser.userId, self.action.createUserId)
            if isUserFriend == True:
                return "1"
            else:
                #print "您不是用户的好友"
                return "0"
            #print "friend_list = ", friend_list
        elif self.action.ownerType == "group":            
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
        # 学科
        elif self.action.ownerType == "subject":
            sub_svc = __spring__.getBean("subjectService")
            subject = sub_svc.getSubjectById(self.action.ownerId)
            if subject == None:
                return "0"
            if self.loginUser == None:
                return "0"
            if sub_svc.checkUserInSubject(self.loginUser, subject.subjectId):
                return "1"
            else:
                return "0"
            
    def can_publish_comment(self):
        if self.loginUser == None:
            return {"status":"false","message": u"您没有登录。"}
        #先检查是否在活动人员列表里面，列表优先
        actionUser = self.act_svc.getActionUserByUserIdAndActionId(self.loginUser.userId, self.action.actionId)
        if actionUser != None:
            if actionUser.status == 1:
                return {"status":"true","message": u""}
            else:                
                arr = [u"未回复",u"已参加",u"已退出",u"已请假"]
                return {"status":"false","message": u"你不能进行评论，您的状态是：" + arr[actionUser.status]}        
        if self.action.ownerType == "user":
            # 得到创建者的好友列表
            isUserFriend = self.frd_svc.isUserFriend(self.loginUser.userId, self.action.createUserId)
            if isUserFriend == True:
                return {"status":"true","message": u""}
            else:
                return {"status":"false","message": u"该活动是个人活动，但您不是该活动创建者的好友。"}
            #print "friend_list = ", friend_list
        elif self.action.ownerType == "group":            
            # 得到群组信息
            self.group = self.grp_svc.getGroup(self.action.ownerId)
            if self.group == None:
                return {"status":"false","message": u"该活动是协作组活动，但无法加载协作组信息。"}
            group_member = self.grp_svc.getGroupMemberByGroupIdAndUserId(self.group.groupId, self.loginUser.userId)            
            if group_member == None:
                return {"status":"false","message": u"该活动是协作组活动，但无您不是该协作组成员。"}
            else:
                if group_member.status == 0:
                    return {"status":"true","message": u""}
                else:
                    arr = [u"正常可用状态",u"申请后待审核",u"待删除",u"锁定",u"邀请未回应"]
                    return {"status":"false","message": u"该活动是协作组活动，您是该协作组成员，但成员状态处于" + arr[group_member.status] + u"。"}
        # 集体备课
        elif self.action.ownerType == "preparecourse":
            if self.pc_svc.checkUserInPreCourse(self.action.ownerId, self.loginUser.userId):
                return {"status":"true","message": u""}
            else:
                return {"status":"false","message": u"该活动是集体备课活动，但您不在该集备成员里面或者状态未审核。"}
        # 学科
        elif self.action.ownerType == "subject":
            sub_svc = __spring__.getBean("subjectService")
            subject = sub_svc.getSubjectById(self.action.ownerId)
            if subject == None:
                return {"status":"false","message": u"该活动是学科活动，但无法加载该学科信息。"}
            if sub_svc.checkUserInSubject(self.loginUser, subject.subjectId):
                return {"status":"true","message": u""}
            else:
                return {"status":"false","message": u"该活动是学科活动，但您不属于该学科。"}
        return {"status":"false","message": u"无法进行判断您在该活动中的状态。"}
    
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
            request.setAttribute("action_reply_list", action_reply_list)
        request.setAttribute("pager", pager)

    def check_user_can_post_comment_action(self):
        if self.loginUser == None:
            return "0"        
        if self.action.status != 0:
            return "0"
        if self.loginUser.loginName == "admin":
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
