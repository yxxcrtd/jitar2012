# coding=utf-8from base_action import *
from message_query import MessageQuery
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Message
#===============================================================================# # 个人短消息管理.#===============================================================================
class message (ActionExecutor):
  # 定义要返回的页面常量.  MESSAGE_INBOX_LIST = "/WEB-INF/ftl/message/msg_inbox_list.ftl"
  MESSAGE_OUTBOX_LIST = "/WEB-INF/ftl/message/msg_outbox_list.ftl"
  MESSAGE_TRASH_LIST = "/WEB-INF/ftl/message/msg_trash_list.ftl"
  MESSAGE_ADD = "/WEB-INF/ftl/message/msg_add.ftl"
  MESSAGE_REPLY = "/WEB-INF/ftl/message/msg_reply.ftl"
    
  def __init__(self):
    self.params = ParamUtil(request) 
    self.user_svc = __jitar__.userService               #用户服务.
    self.friend_svc = __spring__.getBean("friendService")   #好友服务.
    self.msg_svc = __spring__.getBean("messageService") #消息服务.
    # self.moduleContainer = __spring__.getBean("moduleContainer")  #模块容器服务
    return


  def dispatcher(self, cmd):    # 验证登录才可用.
    if self.loginUser == None:
      return ActionResult.LOGIN
    if self.canVisitUser(self.loginUser) == False:      return ActionResult.ERROR    
    if cmd == "" or cmd == None or cmd == "list" or cmd == "inbox" : #默认显示收件箱.      return self.inbox()         # 收件箱消息列表 .
    elif cmd == "outbox" :        # 发件箱消息列表.      return self.outbox()    elif cmd == "trash":        # 回收站消息列表.      return self.trash()                    # 以下为未锁定情况下可用.    if self.canManageBlog(self.loginUser) == False:      return ActionResult.ERROR    
    if cmd == "write":            #写短消息.
      return self.write()         
    elif cmd == "send" or cmd == "save":           #发送短消息.      return self.send()
    elif cmd == "reply":          # 回复短消息.      return self.reply()    elif cmd == "delete":         # 删除收件箱中收到的短消息.      return self.delete()    elif cmd =="deleteAll":      return self.deleteAll()    elif cmd == "recover":        # 恢复回收站中的消息.      return self.recover()    elif cmd =="recoverAll":      return self.recoverAll()    elif cmd == "crash":          # 彻底删除短消息.         return self.crash()    elif cmd =="crashAll":      return self.crashAll()    elif cmd == "senderdel":      #删除发件箱的消息.
      return self.senderdel()    elif cmd =="senderdelAll":      return self.senderdelAll()    elif cmd == "getnew":         #计算有多少条未读消息.
      return self.getNew()
    elif cmd == "show":           #ajax处理,点击更新未读消息数目.
      return self.show()
    self.addActionError(u"未知命令：%s." % cmd)    return ActionResult.ERROR
 
  
  # 列出收件箱中的消息.
  def inbox(self):    query = MessageQuery(""" msg.id, msg.sendId, msg.title, msg.content, msg.sendTime, msg.isRead, msg.isReply,           send.loginName, send.trueName, send.email """)
    #query = self.createQuery()
    query.receiveId = self.loginUser.userId     # 接收者是自己.
    query.isDel = False         # 未删除的.    
    pager = self.createPager()
    pager.totalRows = query.count()
    message_list = query.query_map(pager)    
    request.setAttribute("pager", pager)
    request.setAttribute("message_list", message_list)
    request.setAttribute("type", "receive")
    
    totalRows = pager.totalRows  # self.getMessageTotalRows();
    unreadRows = self.getMessageUnreadRows();
    
    request.setAttribute("totalRows", totalRows)
    request.setAttribute("unreadRows", unreadRows)
    return self.MESSAGE_INBOX_LIST
  
  # 发件箱箱的消息.  def outbox(self):    # 构造查询.
    query = MessageQuery(""" msg.id, msg.receiveId, msg.title, msg.content, msg.sendTime, msg.isRead,           msg.isSenderDel, recv.loginName, recv.trueName, recv.email, recv.blogName """)    query.sendId = self.loginUser.userId    # 发送者是自己.
    query.isSenderDel = False   # 没有被自己删除.    
    pager = self.createPager()
    pager.totalRows = query.count()
    message_list = query.query_map(pager)    
    request.setAttribute("pager", pager)
    request.setAttribute("message_list", message_list)

    return self.MESSAGE_OUTBOX_LIST
  
  # 回收站消息列表.
  def trash(self):
    query = MessageQuery(""" msg.id, msg.sendId, msg.title, msg.content, msg.sendTime, msg.isRead,                             msg.isReply, send.loginName, send.trueName, send.email """)    #query = self.createQuery()    query.receiveId = self.loginUser.userId     # 接收者是自己.    query.isDel = True          # 已经删除的.    
    pager = self.createPager() 
    pager.totalRows = query.count()
    message_list = query.query_map(pager)
    request.setAttribute("pager", pager)
    request.setAttribute("message_list", message_list)    
    return self.MESSAGE_TRASH_LIST
  
 
  # 写消息.
  def write(self):
    curLoginUserId = self.loginUser.userId
    # 得到登陆者的好友列表. 提供给发送短消息的好友(下拉框)列表.
    friend_list = self.friend_svc.getFriendList(curLoginUserId);
    request.setAttribute("friend_list", friend_list)    
    return self.MESSAGE_ADD  
  
  # 发送短消息
  def send(self):    # 得到参数
    curLoginUserId = self.loginUser.userId
    redirect = self.params.getStringParam("redirect")
    strMessageReceiver = self.params.safeGetStringParam("messageReceiver")
    messageTitle = self.params.getStringParam("messageTitle")
    strMessageContent = self.params.safeGetStringParam("messageContent")
        # 验证字段.
    if strMessageReceiver == "" or strMessageReceiver == None:
      self.addActionError(u"没有给出消息接收者.")
      return ActionResult.ERROR
    if messageTitle == "" or messageTitle == None:
      self.addActionError(u"消息标题不能为空.")
      return ActionResult.ERROR
    if strMessageContent == "" or strMessageContent == None:
      self.addActionError(u"消息内容不能未空.")
      return ActionResult.ERROR

    # 检查当前系统中是否存在将要被添加的用户.
    receiver = self.user_svc.getUserByLoginName(strMessageReceiver)    if receiver == None:      self.addActionError(u"接收者 %s 不存在, 其不是一个有效的用户登录名." % strMessageReceiver)      return ActionResult.ERROR 
    # TODO: 处理黑名单.        # TODO: 处理回复标志.        # 组装消息发出.    msg = Message()    msg.sendId = self.loginUser.userId    msg.receiveId = receiver.userId    msg.title = messageTitle    msg.content = strMessageContent        # 执行发送.    self.msg_svc.sendMessage(msg)    # 如果设置了 'redirect' 参数, 则重定向到该页面.    if redirect != None and redirect != "":      out = response.writer      out.write(u"<script>alert('消息已发送');window.location='" + self.getRefererHeader() + "';</script>")      return None
      # 其他操作成功后的处理(返回到前一个页面).    self.addActionLink(u"返回", "?cmd=inbox")    self.addActionMessage(u"消息 '%s' 已发送给了 %s(%s)." % (messageTitle, receiver.nickName, receiver.loginName));    return ActionResult.SUCCESS
    #self.addActionLink返回    
    
  # 删除发件箱的消息.
  def senderdel(self):    # 得到参数.
    ids = self.params.getIdList("messageId")
    if ids == None or ids.size() == 0: 
      self.addActionError(u"没有选择消息")
      return ActionResult.ERROR
        # 循环操作并验证.
    for msgId in ids:      msg = self.msg_svc.findById(msgId)      if msg == None:        self.addActionError(u"未找到指定标识为 %d 的短消息." % msgId)        continue      if msg.sendId != self.loginUser.userId:        self.addActionError(u"试图操作其它人发送的短消息.")        continue      
      self.msg_svc.senderDelMessage(msg)    # 发消息者删除发件箱的消息.    # end for msgId in ids.
       
    self.addActionMessage(u"操作成功")
    self.addActionLink(u"返回", "?cmd=outbox") #返回发件箱.
    return ActionResult.SUCCESS
  
  # 彻底删除短消息.
  def crash(self):
    ids = self.params.getIdList("messageId")
    
    if ids == None or ids.size() == 0:
      self.addActionError(u"没有选择消息")
      return ActionResult.ERROR
        # 循环操作并验证.    for msgId in ids:
      msg = self.msg_svc.findById(msgId)      if msg == None:        self.addActionError(u"未找到指定标识为 %d 的短消息." % msgId)        continue      if msg.receiveId != self.loginUser.userId:        self.addActionError(u"试图操作其它人的短消息.")        continue            self.msg_svc.crashMessage(msg)
    
    self.addActionMessage(u"操作成功")
    self.addActionLink(u"返回", "?cmd=trash") #返回回收站.
    return ActionResult.SUCCESS  
    
  # 回复.
  def reply(self):    # 得到要回复的短消息对象.
    messageId = self.params.getIntParam("messageId")
    message = self.msg_svc.findById(messageId)    if message == None:      self.addActionError(u"未找到指定标识的短消息, 请确定您点击的链接有效.")      return ActionResult.ERROR        if message.receiveId != self.loginUser.userId:      self.addActionError(u"不能回复他人的短消息.")      return ActionResult.ERROR        if message.sendId <= 0:      self.addActionError(u"该短消息是系统消息, 其不能回复.")      return ActionResult.ERROR    
    user = self.user_svc.getUserById(message.getSendId())
    request.setAttribute("user", user)
    request.setAttribute("message", message)    
    return self.MESSAGE_REPLY
    
  # 将消息删除至回收站.
  def delete(self):    # 得到参数.
    ids = self.params.getIdList("messageId")
    
    if ids == None or ids.size() == 0:
      self.addActionError(u"没有选择消息.")
      return ActionResult.ERROR
    # 循环操作并验证.
    for msgId in ids:
      msg = self.msg_svc.findById(msgId)      if msg == None:        self.addActionError(u"未找到指定标识为 %d 的短消息." % msgId)        continue      if msg.receiveId != self.loginUser.userId:        self.addActionError(u"试图操作其它人的短消息.")        continue            self.msg_svc.moveMessageToRecycle(msg)    
  # 恢复回收站中的消息.
  def recover(self):
    ids = self.params.getIdList("messageId")
    
    if ids == None or ids.size() == 0:
      self.addActionError(u"没有选择消息.")
      return ActionResult.ERROR
        # 循环操作并验证.
    for msgId in ids:
      msg = self.msg_svc.findById(msgId)      if msg == None:        self.addActionError(u"未找到指定标识为 %d 的短消息." % msgId)        continue      if msg.receiveId != self.loginUser.userId:        self.addActionError(u"试图操作其它人的短消息.")        continue            self.msg_svc.unMoveMessageToRecycle(msgId)
      
    self.addActionMessage(u"操作成功")
    self.addActionLink(u"返回", "?cmd=trash") # 返回到回收站.
    
    return ActionResult.SUCCESS 
    
  # 个人空间右下角有未读消息的提示.计算有多少条未读消息.
  def getNew(self):
    unreadRows = self.getMessageUnreadRows();
    if unreadRows > 0:       xjson = "[{uid:%d, msgcount:%d}]" % (self.loginUser.userId, unreadRows)
      response.setHeader("X-JSON", xjson)
    return None  
  
  # (ajax处理). 点击消息,动态更新未读消息数目.
  def show(self):    curLoginUserId = self.loginUser.userId
    unReadId = self.params.getIntParam("unreadId")    #print "unReadId = ", unReadId    if unReadId == 0: return None
    # 设置该消息已经读取过了.
    self.msg_svc.setMessageIsRead(unReadId, self.loginUser.userId)
    
    totalRows = self.msg_svc.getTotalMessages(curLoginUserId)
    unreadRows = self.msg_svc.getUnreadMessages(curLoginUserId)
        # 直接输出.
    response.writer.println(u"%d 条短消息, %d 条未读" % (totalRows, unreadRows))
    return None  #当前登录用户收到的短消息总数.  def getMessageTotalRows(self):    totalRows = self.msg_svc.getTotalMessages(self.loginUser.userId)    return totalRows      #当前登录用户收到的未读消息总数.  def getMessageUnreadRows(self):    unreadRows = self.msg_svc.getUnreadMessages(self.loginUser.userId);    return unreadRows  
  def createPager(self):    pager = self.params.createPager()    pager.itemName = u"短消息"    pager.itemUnit = u"条"    pager.pageSize = 20    return pager  #删除所有收件箱中的消息  def deleteAll(self):    self.msg_svc.moveAllMessageToRecycle(self.loginUser.userId)    self.addActionMessage(u"操作成功")    self.addActionLink(u"返回", "?cmd=inbox") #返回收件箱.    return ActionResult.SUCCESS  # 删除发件箱中的所有消息  def senderdelAll(self):    self.msg_svc.senderDelAllMessage(self.loginUser.userId)    # 发消息者删除发件箱的消息.    # end for msgId in ids.    self.addActionMessage(u"操作成功")    self.addActionLink(u"返回", "?cmd=outbox") #返回发件箱.    return ActionResult.SUCCESS   # 恢复收件箱中所有的信息  def recoverAll(self):    self.msg_svc.unMoveMessageToAllRecycle(self.loginUser.userId)          self.addActionMessage(u"操作成功")    self.addActionLink(u"返回", "?cmd=trash") # 返回到回收站.        return ActionResult.SUCCESS     # 彻底删除回收站中的所有短消息  def crashAll(self):    self.msg_svc.crashAllMessage(self.loginUser.userId)    self.addActionMessage(u"操作成功")    self.addActionLink(u"返回", "?cmd=trash") #返回回收站.    return ActionResult.SUCCESS