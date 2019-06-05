from cn.edustar.jitar.pojos import Group, GroupMember, Resource, Message
from cn.edustar.jitar.model import ObjectType
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.service import CategoryService;
from base_blog_page import PageCheckMixiner, ShowGroupBase, ResponseMixiner
from group_query import GroupQuery
from placard_query import PlacardQuery
from bbs_query import TopicQuery 
from article_query import GroupArticleQuery
from resource_query import GroupResourceQuery
from photo_query import GroupPhotoQuery
from video_query import GroupVideoQuery
from base_action import *
from message_query import MessageQuery
from java.util import UUID

# 子协作组管理.
class group_childs (ActionExecutor, ShowGroupBase, PageCheckMixiner, ResponseMixiner):
  def __init__(self):
    # 必须要调用基类的 __init__?? jython 可以自己调用吗?? 
    ActionExecutor.__init__(self) 
    self.params = ParamUtil(request)
    self.group_svc = __jitar__.groupService
    self.msg_svc = __spring__.getBean("messageService")
    
  # execute() 由 ActionExecutor 实现, 我们只需要实现 dispatcher 即可.
  def dispatcher(self, cmd):
    # 必须要求登录, 以及用户状态合法.
    if self.loginUser == None: return ActionResult.LOGIN
    if self.canVisitUser(self.loginUser) == False: return ActionResult.ERROR
    self.parentId = self.params.getIntParamZeroAsNull('parentId')
    if self.parentId==None:
      self.addActionError(u"缺少group标示。")
      return ActionResult.ERROR
    self.group= self.group_svc.getGroup(self.parentId)
    if self.group==None:
      self.addActionError(u"缺少group。")
      return ActionResult.ERROR
    request.setAttribute("group", self.group)
    request.setAttribute("isKtGroup", "1")
    request.setAttribute("parentId", self.parentId)
    
    
    # 派发命令.
    if cmd == None or cmd == "" : cmd = "list"
    
    if cmd == "list":
      return self.list()
    elif cmd == "delete":
      return self.delete()  
    elif cmd == "list_join":
      return self.list_join()
    elif cmd == "my_invite":
      return self.my_invite()     # 我给别人的邀请.
    elif cmd == "invite_me":
      return self.invite_me()     # 给我的加入邀请.
    elif cmd == "my_joinreq":
      return self.my_joinreq()    # 我的加入申请.
    elif cmd == "req_me":
      return self.req_me()        # 别人请求加入协作组.
    
    elif cmd == "manage":
      return self.manage()        # 协作组管理框架页.
    elif cmd == "manage_left":
      return self.manage_left()   # 协作组左侧菜单.
    elif cmd == "home":
      return self.home()          # 协作组欢迎页.
    
    # 成员管理.
    elif cmd == "uninvite":
      return self.uninvite()      # 取消邀请.
    elif cmd == "accept_invite":
      return self.accept_invite() # 同意加入邀请.
    elif cmd == "reject_invite":
      return self.reject_invite() # 拒绝加入邀请.
    elif cmd == "cancel_joinreq":
      return self.cancel_joinreq()  # 取消加入申请.
    elif cmd == "accept_req":
      return self.accept_req()
    elif cmd == "reject_req":
      return self.reject_req()
    elif cmd == "quit":
      return self.quit()
    
    return self.unknownCommand(cmd)
  
  
  # 列出的协作组.
  def list(self):
    qry = __ComplexGroupQuery(""" g.groupId, g.groupName, g.groupTitle, g.groupIcon, g.createDate, 
      g.groupState, g.userCount, g.groupTags, g.groupIntroduce,g.parentId """)
    qry.groupState = None     # 列出所有.
    qry.parentId=self.parentId
    qry.arrGroupState = [Group.GROUP_STATE_NORMAL, Group.GROUP_STATE_LOCKED, Group.GROUP_STATE_HIDED,Group.GROUP_STATE_WAIT_AUDIT]
    group_list = qry.query_map(-1)
    request.setAttribute("group_list", group_list)
    return "/WEB-INF/ftl/group/group_childs.ftl"
    

  # 列出我加入的协作组.
  def list_join(self):
    qry = __ComplexGroupQuery(""" gm.groupRole, gm.status, gm.joinDate, g.groupId, g.groupName, g.groupTitle, 
      g.groupIcon, g.createDate, g.createUserId, g.groupState, g.userCount, g.groupTags, g.groupIntroduce,g.parentId """)
    # 协作组正常、锁定、隐藏的可以进入.
    qry.arrGroupState = [Group.GROUP_STATE_NORMAL, Group.GROUP_STATE_LOCKED, Group.GROUP_STATE_HIDED]
    # 成员状态正常、被锁定的可以进入.
    qry.arrMemberState = [GroupMember.STATUS_NORMAL, GroupMember.STATUS_LOCKED]
    qry.gmUserId = self.loginUser.userId
    
    group_list = qry.query_map(-1)
    request.setAttribute("group_list", group_list)
    
    return "/WEB-INF/ftl/group/list_join.ftl"
  
  
  # 我发出的邀请.
  def my_invite(self):
    qry = __ComplexGroupQuery(""" gm.groupId, gm.userId, gm.joinDate, gm.status, 
          g.groupTitle, g.groupTags, g.groupIntroduce,g.parentId, gmu.nickName, gmu.loginName,
          gmu.userIcon, gmu.blogName """)
    # 协作组正常、隐藏的可以邀请.
    qry.arrGroupState = [Group.GROUP_STATE_NORMAL, Group.GROUP_STATE_HIDED]
    # 成员状态为被邀请.
    qry.arrMemberState = [GroupMember.STATUS_INVITING]
    # 邀请人是自己.
    qry.inviterId = self.loginUser.userId
    
    invite_list = qry.query_map(-1)
    request.setAttribute("invite_list", invite_list)
    
    return "/WEB-INF/ftl/group/list_myinvite.ftl"

  # 删除一个协作组.
  def delete(self):
    groupId = self.params.getIntParamZeroAsNull('groupId')
    g=self.group_svc.getGroup(groupId)
    if g.parentId==0:
      if g.groupState == Group.GROUP_STATE_DELETED:
        self.addActionError(u"协作组 " + g.toDisplayString() + u" 已经被删除, 未再次进行删除.")
        return ActionResult.ERROR
      self.group_svc.deleteGroup(g)
    else:  
      #彻底删除
      self.group_svc.crashGroup(g)
    return self.list()

  # 我收到的邀请, 邀请我加入协作组的.
  def invite_me(self):
    qry = __ComplexGroupQuery(""" gm.groupId, gm.inviterId, gm.joinDate, gm.status, 
          inv.userId, inv.nickName, inv.loginName, inv.userIcon, inv.blogName, 
          g.groupTitle, g.groupTags, g.groupIntroduce,g.parentId """)
    # 协作组正常、隐藏的可以邀请.
    qry.arrGroupState = [Group.GROUP_STATE_NORMAL, Group.GROUP_STATE_HIDED]
    # 成员状态为被邀请.
    qry.arrMemberState = [GroupMember.STATUS_INVITING]
    # 被邀请的是自己.
    qry.gmUserId = self.loginUser.userId
    
    invite_list = qry.query_map(-1)
    request.setAttribute("invite_list", invite_list)
    
    return "/WEB-INF/ftl/group/list_inviteme.ftl"
  
  
  # 列出我申请加入的协作组.
  def my_joinreq(self):
    qry = __ComplexGroupQuery(""" gm.groupId, gm.inviterId, gm.joinDate, gm.status, 
          g.groupIcon, g.groupTitle, g.groupTags, g.groupIntroduce,g.parentId,
          u.userId, u.nickName, u.loginName, u.userIcon, u.blogName """)
    # 协作组正常的可以加入.
    qry.arrGroupState = [Group.GROUP_STATE_NORMAL]
    # 成员状态为未审核.
    qry.arrMemberState = [GroupMember.STATUS_WAIT_AUDIT]
    # 成员是自己.
    qry.gmUserId = self.loginUser.userId
    
    req_list = qry.query_map(-1)
    request.setAttribute("req_list", req_list)
    
    return "/WEB-INF/ftl/group/list_myjoinreq.ftl"
  
   
    
  # 我收到的申请列表.
  def req_me(self):
    # 得到我拥有管理权限的群组列表 (当前设定为是管理员，副管理员，也许以后可变).
    qry = __ComplexGroupQuery(""" gm.groupId """)
    # 我在该组.
    qry.gmUserId = self.loginUser.userId
    # 我在该组身份是管理员或副管理员.
    qry.arrGroupRole = [GroupMember.GROUP_ROLE_MANAGER, GroupMember.GROUP_ROLE_VICE_MANAGER]
    # 我在该组状态正常.
    qry.arrMemberState = [GroupMember.STATUS_NORMAL]
    # 协作组状态必须是正常.
    qry.arrGroupState = [Group.GROUP_STATE_NORMAL]
    
    managed_group_list = qry.list(-1)     # 返回 List<Integer> 是我能够管理的所有协作组.
    #print "managed_group_list = ", managed_group_list 
    if managed_group_list == None or managed_group_list.size() == 0:
      request.setAttribute("req_list", None)
      return "/WEB-INF/ftl/group/list_reqme.ftl"
    
    # 查询这些组中的加入申请.
    qry = __ComplexGroupQuery(""" gm.id, gm.groupId, gm.userId, gm.joinDate, gm.status, 
            g.groupTitle, g.groupTags, g.groupIntroduce, g.parentId,gmu.nickName, gmu.loginName, gmu.userIcon, gmu.blogName """)
    qry.arrGroupId = managed_group_list     # 在所有我能管理的组.
    # 成员状态为待审核.
    qry.arrMemberState = [GroupMember.STATUS_WAIT_AUDIT]
    req_list = qry.query_map(-1)
    #print "req_list = ", req_list
    request.setAttribute("req_list", req_list)
    
    return "/WEB-INF/ftl/group/list_reqme.ftl"
  
  
  # 协作组管理框架页.
  def manage(self):
    # 得到当前协作组及成员身份信息.
    if self.get_current_group() == None:
      return ActionResult.ERROR

    # 得到成员信息, 并验证能否进入协作组.
    self.get_group_member()
    if self.canEnterGroup(self.group, self.loginUser, self.group_member) == False:
      return self.ACCESS_ERROR

    # 根据传递进来的 url 确定右侧显示内容, 缺省为欢迎页.
    url = self.params.getStringParam("url")
    if url == None or url == "":
      url = "?cmd=home&amp;groupId=" + str(self.group.groupId)
    request.setAttribute("url", url);
    
    return "/WEB-INF/ftl/group/group_manage_frame.ftl"
  
  # 组员退出协作组
  def quit(self):
    # 得到当前协作组及成员身份信息.
    if self.get_current_group() == None:
      return ActionResult.ERROR
  
    #对组员退出处理
    gm = self.group_svc.getGroupMemberByGroupIdAndUserId(self.group.groupId, self.loginUser.userId)
    self.group_svc.destroyGroupMember(gm);
    
    return self.list_join()


  # 协作组管理左侧菜单.
  def manage_left(self):
    # 得到当前协作组.
    if self.get_current_group() == None:
      return ActionResult.ERROR
    self.get_group_member()
    
    #课题研究组的分类
    uuid=self.group_svc.getGroupCateUuid(self.group)
    if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
      #课题
      request.setAttribute("isKtGroup", "1")
    elif uuid==CategoryService.GROUP_CATEGORY_GUID_JTBK :
      #备课 
      request.setAttribute("isKtGroup", "2")
    else:    
      request.setAttribute("isKtGroup", "0")
    request.setAttribute("guid", UUID.randomUUID())
    return "/WEB-INF/ftl/group/group_manage_left.ftl"
  
  
  # 协作组欢迎页.
  def home(self):
    # 得到当前协作组.
    if self.get_current_group() == None:
      return ActionResult.ERROR
    
    uuid=self.group_svc.getGroupCateUuid(self.group)
    if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
      request.setAttribute("isKtGroup", "1")
    else:
      request.setAttribute("isKtGroup", "0")
    
    self.__home_member_list()       # 最新成员列表.
    self.__home_placard_list()      # 公告列表.
    self.__home_topic_list()        # 最新主题列表.
    self.__home_article_list()      # 最新文章列表.
    self.__home_resource_list()     # 最新资源简表.
    self.__home_photo_list()        # 最新图片简表.
    self.__home_video_list()        # 最新视频简表.
    
    return "/WEB-INF/ftl/group/group_home.ftl"
  
  
  # 取消对某人加入我的协作组的邀请.
  def uninvite(self):
    # 得到当前协作组.
    if self.get_current_group() == None:
      return ActionResult.ERROR
    
    # 得到被邀请的用户.
    userId = self.params.getIntParam("userId")
    
    # 得到该邀请信息, 可能就不是邀请.
    gm = self.group_svc.getGroupMemberByGroupIdAndUserId(self.group.groupId, userId)
    # print "gm = ", gm
    if gm == None:
      self.addActionError(u"未找到对用户 %d 的邀请." % userId)
      return ActionResult.ERROR
    if gm.status != GroupMember.STATUS_INVITING:
      self.addActionError(u"对用户 % 的邀请已经失效, 无法取消邀请." % userId)
      return ActionResult.ERROR

    # 取消邀请.
    group_svc.uninviteGroupMember(gm)
    
    self.addActionMessage(u"邀请已经取消.")
    return ActionResult.SUCCESS
  
  
  # 同意加入协作组的邀请.
  def accept_invite(self):
    if self.get_current_group() == None:
      return ActionResult.ERROR
    
    # 得到该邀请.
    gm = self.group_svc.getGroupMemberByGroupIdAndUserId(self.group.groupId, self.loginUser.userId)
    if gm == None:
      self.addActionError(u"抱歉: 未找到此加入协作组 %s 的邀请." % self.group.groupTitle)
      return ActionResult.ERROR
    if gm.status != GroupMember.STATUS_INVITING:
      self.addActionError(u"邀请已经过期或者您已经是协作组 %s 的成员了." % self.group.groupTitle)
      return ActionResult.ERROR
    
    # 业务执行.
    group_svc.acceptGroupMemberInvite(gm);
    
    self.addActionMessage(u"您接受了此邀请, 加入了协作组 %s." % self.group.groupTitle)
    self.addActionLink(u"访问协作组首页", self.get_site_url() + "go.action?groupId=" + str(self.group.groupId), "_top");
    self.addActionLink(u"进入协作组", "group.py?cmd=manage&groupId=" + str(self.group.groupId), "_top");
    self.addActionLink(u"返回", "javascript:window.history.back();")
    
    return ActionResult.SUCCESS
  
  def get_site_url(self):
    url = request.getScheme() + "://" + request.getServerName()
    if request.getServerPort() != 80:
      url = url + ":" + str(request.getServerPort())
    url = url + request.getContextPath() + "/"
    return url
  # 婉拒加入邀请.
  def reject_invite(self):
    if self.get_current_group() == None:
      return ActionResult.ERROR
    
    # 得到该邀请.
    gm = self.group_svc.getGroupMemberByGroupIdAndUserId(self.group.groupId, self.loginUser.userId)
    if gm == None:
      self.addActionError(u"抱歉: 未找到此加入协作组 %s 的邀请." % self.group.groupTitle)
      return ActionResult.ERROR
    if gm.status != GroupMember.STATUS_INVITING:
      self.addActionError(u"邀请已经过期或者您已经是协作组 %s 的成员了." % self.group.groupTitle)
      return ActionResult.ERROR
    
    # 业务执行.
    self.group_svc.rejectGroupMemberInvite(gm)
    
    self.addActionMessage(u"您已经拒绝了此加入邀请.");
    return ActionResult.SUCCESS
  
  
  # 取消加入申请.
  def cancel_joinreq(self):
    if self.get_current_group() == None:
      return ActionResult.ERROR
    
    # 得到该申请.
    gm = self.group_svc.getGroupMemberByGroupIdAndUserId(self.group.groupId, self.loginUser.userId)
    if gm == None:
      self.addActionError(u"抱歉: 未找到此加入协作组 %s 的申请." % self.group.groupTitle)
      return ActionResult.ERROR
    if gm.status != GroupMember.STATUS_WAIT_AUDIT:
      self.addActionError(u"申请已经过期或者您已经是协作组 %s 的成员了." % self.group.groupTitle)
      return ActionResult.ERROR
    
    # 执行业务.
    self.group_svc.cancelJoinRequest(gm)
    
    self.addActionMessage(u"您已经取消了加入协作组 %s 的申请." % self.group.groupTitle)
    return ActionResult.SUCCESS
    
  
  # 同意(审核通过) 加入申请.
  def accept_req(self):
    # 得到协作组.
    if self.get_current_group() == None:
      return ActionResult.ERROR

    # 判定协作组状态.
    if self.group.groupState == Group.GROUP_STATE_LOCKED:
      self.addActionError(u"协作组 %s 被管理员锁定了, 被锁定的协作组不能加入新成员." % self.group.groupTitle)
      return ActionResult.ERROR
    if self.group.groupState != Group.GROUP_STATE_NORMAL and self.group.groupState != Group.GROUP_STATE_HIDED:
      self.addActionError(u"协作组 %s 被删除或锁定, 不能加入新成员." % self.group.groupTitle)
      return ActionResult.ERROR
    
    # 判定是否有权限管理该协作组.
    gm = self.get_group_member()
    if gm == None or gm.groupRole < GroupMember.GROUP_ROLE_VICE_MANAGER or gm.status != GroupMember.STATUS_NORMAL:
      self.addActionError(u"您不是协作组 %s 的管理员, 不能管理其组员." % self.group.groupTitle)
      return ActionResult.ERROR

    # 得到该申请.
    userId = self.params.getIntParam("userId")
    gm = self.group_svc.getGroupMemberByGroupIdAndUserId(self.group.groupId, userId)
    if gm == None:
      self.addActionError(u"未能找到该申请, 可能该申请已经过期.")
      return ActionResult.ERROR
    if gm.status != GroupMember.STATUS_WAIT_AUDIT:
      self.addActionError(u"该申请已经过期, 不能执行同意操作.")
      return ActionResult.ERROR

    # 执行业务.
    self.group_svc.auditGroupMember(gm)
    
    # 我给申请的人发送短消息
    msg = Message()
    msg.sendId = self.loginUser.userId
    msg.receiveId = self.params.getIntParam("userId")
    msg.title = u"群组申请审核通过！"
    msg.content = u"您申请加入的群组：" + self.group.groupTitle + u"，已被创建者审核通过！"
    self.msg_svc.sendMessage(msg)

    self.addActionMessage(u"该申请已经审核通过.")
    return ActionResult.SUCCESS
  
  
  # 拒绝加入协作组的申请.
  def reject_req(self):
    # 得到协作组.
    if self.get_current_group() == None:
      return ActionResult.ERROR

    # 判定是否有权限管理该协作组.
    gm = self.get_group_member()
    if gm == None or gm.groupRole < GroupMember.GROUP_ROLE_VICE_MANAGER or gm.status != GroupMember.STATUS_NORMAL:
      self.addActionError(u"您不是协作组 %s 的管理员, 不能管理其组员." % self.group.groupTitle)
      return ActionResult.ERROR 

    # 得到该申请.
    userId = self.params.getIntParam("userId")
    gm = self.group_svc.getGroupMemberByGroupIdAndUserId(self.group.groupId, userId)
    if gm == None:
      self.addActionError(u"未能找到该申请, 可能该申请已经过期.")
      return ActionResult.ERROR
    if gm.status != GroupMember.STATUS_WAIT_AUDIT:
      self.addActionError(u"该申请已经过期, 不能执行拒绝操作.")
      return ActionResult.ERROR

    # 执行业务.
    self.group_svc.destroyGroupMember(gm)
    
    # 我给申请的人发送短消息
    msg = Message()
    msg.sendId = self.loginUser.userId
    msg.receiveId = self.params.getIntParam("userId")
    msg.title = u"群组申请-被婉拒！"
    msg.content = u"您申请加入的群组：" + self.group.groupTitle + u"，已被创建者-婉拒！"
    self.msg_svc.sendMessage(msg)

    self.addActionMessage(u"该申请已经被拒绝.")
    return ActionResult.SUCCESS
  
  
  # 得到最新成员.
  def __home_member_list(self):
    qry = __ComplexGroupQuery(""" gm.groupRole, gm.joinDate, gmu.userId, gmu.loginName, gmu.nickName, gmu.userIcon """)
    # 在当前协作组, 成员状态正常, 按照加入时间排序.
    qry.arrGroupId = [self.group.groupId]
    qry.arrMemberState = [GroupMember.STATUS_NORMAL]
    qry.orderType = __ComplexGroupQuery.ORDER_TYPE_JOINDATE_DESC
    
    member_list = qry.query_map(5)
    request.setAttribute("member_list", member_list)
    
    
  # 得到欢迎页中当前协作组的公告.
  def __home_placard_list(self):
    qry = PlacardQuery(""" pld.id, pld.title, pld.createDate """)
    qry.objType = ObjectType.OBJECT_TYPE_GROUP.typeId
    qry.objId = self.group.groupId
    
    placard_list = qry.query_map(5)
    # print "placard_list = ", placard_list 
    request.setAttribute("placard_list", placard_list)
    return
  
  
  # 最新主题列表.
  def __home_topic_list(self):
    qry = TopicQuery(""" t.topicId, t.title, t.createDate, t.groupId """)
    qry.groupId = self.group.groupId
    topic_list = qry.query_map(5)
    request.setAttribute("topic_list", topic_list)
    return  
  
  # 最新文章列表.
  def __home_article_list(self):
    qry = GroupArticleQuery(""" ga.articleId, ga.title, ga.createDate """)
    qry.groupId = self.group.groupId
    article_list = qry.query_map(5)
    request.setAttribute("article_list", article_list)
    return    
    
  # 最新资源简表.
  def __home_resource_list(self):
    qry = GroupResourceQuery(""" r.resourceId, r.title, r.createDate, r.href """)
    qry.groupId = self.group.groupId
    qry.shareMode = Resource.SHARE_MODE_GROUP
    resource_list = qry.query_map(5)
    request.setAttribute("resource_list", resource_list)
    return   

  # 最新照片简表.
  def __home_photo_list(self):
    qry = GroupPhotoQuery(""" p.photoId, p.title, p.createDate, p.href """)
    qry.groupId = self.group.groupId
    qry.shareMode = Resource.SHARE_MODE_GROUP
    photo_list = qry.query_map(5)
    print "photo_list="+str(photo_list.size()) 
    request.setAttribute("photo_list", photo_list)
    return

    # 最新视频简表.
  def __home_video_list(self):
    qry = GroupVideoQuery(""" v.videoId, v.title, v.createDate, v.href """)
    qry.groupId = self.group.groupId
    qry.shareMode = Resource.SHARE_MODE_GROUP
    video_list = qry.query_map(5)
    request.setAttribute("video_list", video_list)
    return  

  # 得到当前参数指定的协作组.
  def get_current_group(self):
    # 得到协作组参数.
    groupId = self.params.getIntParam("groupId")
    if groupId == 0:
      self.addActionError(u"未给出协作组标识.")
      return None
    
    # 得到该协作组.
    self.group = self.group_svc.getGroup(groupId)
    #print "self.group = ", self.group
    if self.group == None:
      self.addActionError(u"未找到指定标识为 %d 的协作组." % groupId)
      return None
    
    # 判定该协作组的状态是否合法.
    if self.canVisitGroup(self.group) == False:
      return None
    
    request.setAttribute("group", self.group)
    return self.group
    
    
  # 得到当前登录用户在协作组的身份信息.
  def get_group_member(self):
    self.group_member = self.group_svc.getGroupMemberByGroupIdAndUserId(self.group.groupId, self.loginUser.userId)

    # 如果自己是协作组创建者, 但未找到协作组成员信息, 则现在修正它.
    if self.loginUser.userId == self.group.createUserId:
      if self.group_member == None:
        self.group_member = GroupMember()
        self.group_member.groupId = self.group.groupId
        self.group_member.userId = self.loginUser.userId
        self.group_member.groupRole = GroupMember.GROUP_ROLE_MANAGER
        self.group_member.status = GroupMember.STATUS_NORMAL
        self.group_svc.createGroupMember(self.group_member)
      elif self.group_member.status != GroupMember.STATUS_NORMAL or self.group_member.groupRole != GroupMember.GROUP_ROLE_MANAGER:
        self.group_member.groupRole = GroupMember.GROUP_ROLE_MANAGER
        self.group_member.status = GroupMember.STATUS_NORMAL
        self.group_svc.updateGroupMember(self.group_member)
    
    request.setAttribute("group_member", self.group_member)
    return self.group_member
    
    
  def putGroupCategoryTree(self):
    cate_svc = __jitar__.categoryService
    group_categories = cate_svc.getCategoryTree('group')
    # print "group_categories = ", group_categories
    request.setAttribute("group_categories", group_categories)
    

# 协作组管理中使用的协作组查询辅助类.
class __ComplexGroupQuery (GroupQuery):
  ORDER_TYPE_JOINDATE_DESC = 100    # 按照加入时间排序.
  def __init__(self, fields):
    GroupQuery.__init__(self, fields)
    self.groupState = None      # 不需要基类处理 groupState, 我们使用更复杂的 arrGroupState.
    self.arrGroupId = None      # 多个协作组的标识 [1, 3, 8]
    self.gmUserId = None        # GroupMember.userId - 协作组成员标识.
    self.arrGroupState = None   # 协作组状态 [normal, hidden, etc.]
    self.arrMemberState = None  # 成员状态 [normal, invite, etc.]
    self.arrGroupRole = None    # 成员角色 [manager, member ...]
    self.inviterId = None       # 邀请人.
    
  def resolveEntity(self, qctx, entity):
    if entity == "gm":
      qctx.addEntity("GroupMember", "gm", "g.groupId = gm.groupId")
    elif entity == "gmu":     # 协作组成员.
      qctx.addEntity("User", "gmu", "gm.userId = gmu.userId")
    elif entity == "inv":    # 邀请人.
      qctx.addEntity("User", "inv", "gm.inviterId = inv.userId")
    else:
      GroupQuery.resolveEntity(self, qctx, entity);
  
  # 提供 where 条件.
  def applyWhereCondition(self, qctx):
    GroupQuery.applyWhereCondition(self, qctx)
    if self.arrGroupId != None and len(self.arrGroupId) > 0:
      if len(self.arrGroupId) == 1:
        qctx.addAndWhere("g.groupId = :arrGroupId")
        qctx.setInteger("arrGroupId", self.arrGroupId[0])
      else:
        qctx.addAndWhere("g.groupId IN " + self.__toInSql(self.arrGroupId))
    if self.gmUserId != None:
      qctx.addAndWhere("gm.userId = :gmUserId")
      qctx.setInteger("gmUserId", self.gmUserId)
    if self.arrGroupState != None and len(self.arrGroupState) > 0:
      qctx.addAndWhere("g.groupState IN " + self.__toInSql(self.arrGroupState))
    if self.arrMemberState != None and len(self.arrMemberState) > 0:
      if len(self.arrMemberState) == 1:
        qctx.addAndWhere("gm.status = :arrMemberState")
        qctx.setInteger("arrMemberState", self.arrMemberState[0])
      else:
        qctx.addAndWhere("gm.status IN " + self.__toInSql(self.arrMemberState))
    if self.arrGroupRole != None and len(self.arrGroupRole) > 0:
      qctx.addAndWhere("gm.groupRole IN " + self.__toInSql(self.arrGroupRole))
    if self.inviterId != None:
      qctx.addAndWhere("gm.inviterId = :inviterId")
      qctx.setInteger("inviterId", self.inviterId)  


  def applyOrderCondition(self, qctx):
    if self.orderType == __ComplexGroupQuery.ORDER_TYPE_JOINDATE_DESC:
      qctx.addOrder("gm.joinDate DESC")
      return
    GroupQuery.applyOrderCondition(self, qctx)

        
  def __toInSql(self, arr):
    # Python 肯定是处理以下麻烦代码的好方法, 可是我暂时不会写, 只好先写复杂的了.
    buf = "("
    for i in arr:
      buf += str(i) + ','
    buf = buf[:len(buf) - 1] + ")"
    return buf

