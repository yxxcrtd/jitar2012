from base_action import *
from unit_query import UnitQuery
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Unit
from cn.edustar.jitar.pojos import Message
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.service import UnitQueryParam
from cn.edustar.jitar.action import ActionLink
from comment_query import CommentQuery

# 需要类名和文件名一致.
class admin_Comment (ActionExecutor):
  UNIT_LIST = "/WEB-INF/ftl/admin/comment_List.ftl"
  
  # 构造.
  def __init__(self):
    self.params = ParamUtil(request)
    self.cmt_svc = __jitar__.commentService
    self.pun_svc = __jitar__.UPunishScoreService
    self.msg_svc = __spring__.getBean("messageService")
    return
 
  
  # 根据'cmd'进行派发.
  def dispatcher(self, cmd):
    # 以下必须要求是登录用户和具有管理员的权限.
    if self.loginUser == None: return ActionResult.LOGIN
    accessControlService = __spring__.getBean("accessControlService")
    if False == accessControlService.isSystemContentAdmin(self.loginUser):
        ActionError(u"没有评论管理的权限.")
        return ActionResult.ERROR
    
    if cmd == "" or cmd == None :
      cmd = "list1"
    # 根据cmd参数，执行相应的方法.
    if cmd == "list1":
      return self.list(3)          # 列表显示. 3--博文
    elif cmd == "list2":
      return self.list(12)          # 列表显示. 12--资源
    elif cmd == "delete":
      return self.delete()        # 删除
    elif cmd == "audit":
      return self.audit()        # 审核通过
    elif cmd == "unaudit":
      return self.unaudit()        # 取消审核
    else :
      self.addActionError(u"未知的命令参数：" + cmd)
      return ActionResult.ERROR    


  # 系统所有机构的列表显示.
  def list(self, objTypeValue):
    # 构造查询.
    request.setAttribute("type", objTypeValue) 
    if objTypeValue == 3:
        query = AdminCommentQuery(""" cmt.id, cmt.title, cmt.content, cmt.audit, cmt.createDate, cmt.userId, cmt.userName, 
        cmt.ip, cmt.star, cmt.objType, cmt.objId,  
        u.loginName, u.nickName, u.trueName, a.articleId as sourceId,a.title as sourceTitle,a.userId as sourceUserId,a.createDate as sourceCreateDate """)
    elif objTypeValue == 12:
        query = AdminCommentQuery(""" cmt.id, cmt.title, cmt.content, cmt.audit, cmt.createDate, cmt.userId, cmt.userName, 
        cmt.ip, cmt.star, cmt.objType, cmt.objId,  
        u.loginName, u.nickName,  u.trueName, r.resourceId as sourceId,r.title as sourceTitle,r.userId as sourceUserId ,r.createDate as sourceCreateDate,r.href """)
    else: 
        objTypeValue = 3   
        query = AdminCommentQuery(""" cmt.id, cmt.title, cmt.content, cmt.audit, cmt.createDate, cmt.userId, cmt.userName, 
        cmt.ip, cmt.star, cmt.objType, cmt.objId,  
        u.loginName, u.nickName, u.trueName, a.articleId as sourceId,a.title as sourceTitle,a.userId as sourceUserId,a.createDate as sourceCreateDate """)

    query.objType = objTypeValue
    query.kk = self.params.getStringParam("k")
    request.setAttribute("k", query.kk)
    query.f = self.params.getStringParam("f")
    request.setAttribute("f", query.f)
    query.audit = None
    
    # 调用分页函数.
    pager = self.createPager()
    pager.totalRows = query.count()
        
    # 得到所有列表.
    commentList = query.query_map(pager)
    #print "unitList:", unitList
        
    # 传给页面.
    request.setAttribute("commentList", commentList)
    request.setAttribute("pager", pager)
    
    # 返回到要显示的页面.
    return self.UNIT_LIST
    
  # 审核通过所选的评论.
  def audit(self):
    ids = self.params.getIdList("commentId")
    if ids == None or ids.size() == 0 : 
      self.addActionError(u"没有选择评论")
      return ActionResult.ERROR
    oper_count = 0
    for id in ids:
      if id != None:
        comment = self.cmt_svc.getComment(id)
        if comment == None:
            self.addActionError(u"未能找到标识为 " + id + u" 的评论ﾵￄￆ￀ￂￛ")
        else:    
            self.cmt_svc.auditComment(comment)
        oper_count = oper_count + 1
    self.addActionMessage(u"共审核通过了 " + str(oper_count) + u" 条评论")  
    return ActionResult.SUCCESS

  # 审核通过所选的评论.
  def unaudit(self):
    ids = self.params.getIdList("commentId")
    if ids == None or ids.size() == 0 : 
      self.addActionError(u"没有选择评论")
      return ActionResult.ERROR
    oper_count = 0
    for id in ids:
      if id != None:
        comment = self.cmt_svc.getComment(id)
        if comment == None:
            self.addActionError(u"未能找到标识为 " + id + u" 的评论ﾵￄￆ￀ￂￛ")
        self.cmt_svc.unauditComment(comment)
        oper_count = oper_count + 1
    self.addActionMessage(u"共 " + str(oper_count) + u" 条评论取消了审核")  
    return ActionResult.SUCCESS

  # 删除.
  def delete(self):
    score = self.params.getIntParam("score")
    reason = self.params.getStringParam("reason")
    ids = self.params.getIdList("commentId")
    if ids == None or ids.size() == 0 : 
      self.addActionError(u"没有选择评论")
      return ActionResult.ERROR
    oper_count = 0
    for id in ids:
      if id != None:
        comment = self.cmt_svc.getComment(id)
        if comment != None:
          if score == None:
            upun = self.pun_svc.createUPunishScore(16, comment.id, comment.userId,self.loginUser.userId,self.loginUser.trueName)
          else:
            if score<0 :
              upun = self.pun_svc.createUPunishScore(16, comment.id, comment.userId, -1*score, reason,self.loginUser.userId,self.loginUser.trueName)
            else:
              upun = self.pun_svc.createUPunishScore(16, comment.id, comment.userId, score, reason,self.loginUser.userId,self.loginUser.trueName)
          self.pun_svc.saveUPunishScore(upun)
          #消息通知
          message = Message()
          message.sendId = self.loginUser.userId
          message.receiveId = comment.userId
          message.title = u"管理员删除了您的评论及扣分信息"
          if reason != "":
            message.content = u"您的评论" + comment.title + u"被删除,扣" + str(upun.score) + u"分,原因:" + reason
          else:  
            message.content = u"您的评论" + comment.title + u"被删除,扣" + str(upun.score) + u"分"
          self.msg_svc.sendMessage(message)
          
          self.cmt_svc.deleteComment(comment)
          oper_count = oper_count + 1
    self.addActionMessage(u"删除了" + str(oper_count) + u" 条评论");
    type = self.params.getStringParam("type")
    if type == "3":
      self.addActionLink(u"返回", "?cmd=list1&page=" + self.params.getStringParam("ppp"))
    elif type == "12":  
      self.addActionLink(u"返回", "?cmd=list2&page=" + self.params.getStringParam("ppp"))
    else:
      self.addActionLink(u"返回", "?cmd=list1&page=" + self.params.getStringParam("ppp"))
    #self.addActionLink(ActionLink.HISTORY_BACK);
    return ActionResult.SUCCESS
    

  # 创建并返回一个分页对象.
  def createPager(self):
    # 调用Java的函数.
    pager = self.params.createPager()
    pager.itemName = u"评论"
    pager.itemUnit = u"个"
    pager.pageSize = 10
    return pager
    



# 资源带高级过滤条件的搜索.
class AdminCommentQuery (CommentQuery):
  def __init__(self, selectFields):
    CommentQuery.__init__(self, selectFields)
    self.kk = None          # 查询关键字.
    self.f = 0              # kk 查询哪个字段.
    
  def applyWhereCondition(self, qctx):
    CommentQuery.applyWhereCondition(self, qctx)
    if self.kk != None and self.kk != '':
      self._applyKeywordFilter(qctx)
  
  def _applyKeywordFilter(self, qctx):
    newKey = self.kk.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
    if self.f == 'title':   # 用资源标题、标签过滤.
      qctx.addAndWhere('cmt.title LIKE :kk')
      qctx.setString('kk', '%' + newKey + '%')
    elif self.f == 'content': 
      qctx.addAndWhere('cmt.content LIKE :kk')
      qctx.setString('kk', '%' + newKey + '%')
    elif self.f == 'uname': # 用户名 (maybe id).
      try:
        userId = int(self.kk)
        qctx.addAndWhere('cmt.userId = :kk')
        qctx.setInteger('kk', userId)
      except:
        qctx.addAndWhere('u.loginName = :kk OR u.nickName = :kk')
        qctx.setString('kk', newKey)
