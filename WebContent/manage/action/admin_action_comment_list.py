from base_action import *
from cn.edustar.jitar.pojos import Action
from cn.edustar.jitar.util import ParamUtil
from action_query import ActionQuery
from cn.edustar.jitar.data import *
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.data import BaseQuery

class admin_action_comment_list(BaseAdminAction):
    
    def __init__(self):
        self.params = ParamUtil(request)
    
    def execute(self):
        if self.loginUser == None:
            self.out.println(u"请先<a href='../../login.jsp'>登录</a>，然后才能管理活动")
            return
        accessControlService = __spring__.getBean("accessControlService")
        if False == accessControlService.isSystemContentAdmin(self.loginUser):
            ActionError(u"没有管理权限。<a href='' onclick='window.history.back();return false;'>返回</a>")
            return ActionResult.ERROR
        
        self.pager = self.params.createPager()
        self.pager.itemName = u"活动"
        self.pager.itemUnit = u"个"
        qry = ActionCommentQuery(""" actr.actionReplyId, actr.actionId,
                                actr.topic, actr.createDate, actr.content, actr.addIp, act.title,u.loginName,u.trueName
                                """)
            
        self.pager.totalRows = qry.count()
        action_comment_list = qry.query_map(self.pager)
        request.setAttribute("action_comment_list", action_comment_list)
        request.setAttribute("pager", self.pager)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/action/admin_action_comment_list.ftl"
        
        
class ActionCommentQuery(BaseQuery):
    def __init__(self, selectFields):
      BaseQuery.__init__(self, selectFields)
      self.userId = None      
      self.orderType = 0
      self.actionId = None
      
    def initFromEntities(self, qctx):
      qctx.addEntity("ActionReply" , "actr" , "");
    
    def resolveEntity(self, qctx, entity):
      if "u" == entity:
        qctx.addEntity("User", "u", "actr.userId = u.userId")
      elif "act" == entity:
        qctx.addEntity("Action", "act", "act.actionId = actr.actionId")
      else:
        BaseQuery.resolveEntity(self, qctx, entity);
      
    # 提供 where 条件.
    def applyWhereCondition(self, qctx):
      if self.userId != None:
        qctx.addAndWhere("actr.userId = :userId")
        qctx.setInteger("userId", self.userId)
      if  self.actionId != None:
        qctx.addAndWhere("actr.actionId = :actionId")
        qctx.setInteger("actionId", self.actionId)
      
        
    # 提供排序 order 条件.
    def applyOrderCondition(self, qctx):
      # 排序方式, 0 - commentId DESC, 1 - createDate
      if self.orderType == 0:  
        qctx.addOrder("actr.actionReplyId DESC")