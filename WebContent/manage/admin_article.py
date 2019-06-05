#encoding=utf-8
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.pojos import Article
from cn.edustar.jitar.pojos import UPunishScore
from cn.edustar.jitar.pojos import Message
from base_action import *
from article_query import ArticleQuery

class admin_article (ActionExecutor, SubjectMixiner):
  # 常量定义.
  ARTICLE_LIST = "/WEB-INF/ftl/admin/article_list.ftl"
  ARTICLE_EDIT = "/WEB-INF/ftl/admin/article_edit.ftl"
  ARTICLE_RECYCLE_LIST = "/WEB-INF/ftl/admin/article_recycle_list.ftl"
  
  def __init__(self):
    self.params = ParamUtil(request)
    self.art_svc = __jitar__.articleService
    self.cate_svc = __jitar__.categoryService
    self.pun_svc = __jitar__.UPunishScoreService
    self.cfg_svc = __spring__.getBean('configService')
    self.msg_svc = __spring__.getBean("messageService")
    self.channelPageService = __spring__.getBean("channelPageService")
    self.userService = __jitar__.userService
    self.specialSubjectService = __spring__.getBean("specialSubjectService")
    return

  def dispatcher(self, cmd):
    # 必须要求登录和具有管理权限.
    if self.loginUser == None:
      return ActionResult.LOGIN

    accessControlService = __spring__.getBean("accessControlService")
    if False == accessControlService.isSystemContentAdmin(self.loginUser):
      self.addActionError(u"您不具有文章管理权限, 或者您的信息填写不完整, 或未经系统管理员审核.")
      return ActionResult.ERROR
    
    if cmd == "list":
      return self.list()
    elif cmd == "edit":
      return self.edit()
    elif cmd == "recycle_list":
      return self.recycle_list()
    elif cmd == "audit":
      return self.audit()
    elif cmd == "unaudit":
      return self.unaudit()
    elif cmd == "rcmd":
      return self.rcmd()
    elif cmd == "unrcmd":
      return self.unrcmd()
    elif cmd == "best":
      return self.best()
    elif cmd == "unbest":
      return self.unbest()
    elif cmd == "valid":
      return self.valid()
    elif cmd == "invalid":
      return self.invalid()
    elif cmd == "delete":
      return self.delete()
    elif cmd == "recover":
      return self.recover()
    elif cmd == "crash":
      return self.crash()
    elif cmd == "save":
      return self.save()
    elif cmd == "move":
      return self.moveCate()
    elif cmd == "push":
      return self.push()
    elif cmd == "unpush":
      return self.unpush()
    
    self.addActionError(u"未知命令: " + cmd)
    return ActionResult.ERROR
  
  # 列出待管理的文章.
  def list(self):
    configService = __jitar__.configService
    sys_config = configService.getConfigure()
    if sys_config != None:
      if sys_config["topsite_url"] != None and str(sys_config["topsite_url"]).strip() != "":
        request.setAttribute("topsite_url", "")      
      
    # 构造查询.
    query = self.createQuery()
    query.delState = False          # 过滤被删除了的.
    
    # 根据参数设置过滤条件.
    type = self.params.getStringParam("type")
    request.setAttribute("type", type)
    if type == 'best':    # 精华.
      query.bestState = True
    elif type == 'rcmd':  # 推荐.
      query.rcmdState = True
    elif type == 'unaudit':   # 待审核.
      query.auditState = Article.AUDIT_STATE_WAIT_AUDIT

    query.subjectId = self.params.getIntParamZeroAsNull("su")
    request.setAttribute("su", query.subjectId)
    query.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    request.setAttribute("gradeId", query.gradeId)
    query.sysCateId = self.params.getIntParamZeroAsNull("sc")
    request.setAttribute("sc", query.sysCateId)
    query.kk = self.params.getStringParam("k")
    request.setAttribute("k", query.kk)
    query.f = self.params.getStringParam("f")
    request.setAttribute("f", query.f)
    
    # 根据管理员权限再设置过滤条件.
    #self.applyDocPrivFilter(self.loginUser, query)
    
    # 构造分页对象并计算总量. 
    pager = self.createPager()
    pager.totalRows = query.count()
    
    # 得到文章列表.
    article_list = query.query_map(pager)
    
    request.setAttribute("pager", pager)
    request.setAttribute("article_list", article_list)
    self.putSubjectList()
    self.putGradeList()
    self.putArticleCategoryTree()

    return self.ARTICLE_LIST 

  
  # 编辑一篇文章(可能是他人的).
  def edit(self):
    # 得到参数.
    articleId = self.params.getIntParam("articleId")
    if articleId == 0:
      self.addActionError(u"未给出要编辑的文章标识.")
      return ActionResult.ERROR
    
    article = self.art_svc.getArticle(articleId)
    if article == None:
      self.addActionError(u"未能找到指定标识为 %d 的文章." % articleId)
      return ActionResult.ERROR
    
    # 管理员修改模式下.
    request.setAttribute("manageMode", "admin")
        
    request.setAttribute("article", article)
    request.setAttribute("instance", self)
    
    self.putSubjectList()
    self.putGradeList()
    self.putArticleCategoryTree()
    
    
    ss = self.specialSubjectService.getValidSpecialSubjectList()
    if ss != None:
      request.setAttribute("specialsubject_list", ss)
      
    specialSubjectArticle = self.specialSubjectService.getSpecialSubjectArticleByArticleId(article.objectUuid)    
    if specialSubjectArticle == None:
      request.setAttribute("specialSubjectId", 0)
    else:
      request.setAttribute("specialSubjectId", specialSubjectArticle.specialSubjectId)
    
    return self.ARTICLE_EDIT

  # 保存.
  def save(self):
    # 得到页面的 articleId，如果出现网络问题或从地址栏非法输入而获得失败的，则提示'未找到...'
    articleId = self.params.getIntParam("articleId")
    if articleId == 0 or articleId == None:
      self.addActionError(u"未能找到指定标识为 %d 的文章。" % articleId)
      return ActionResult.ERROR
    
    # 根据'articleId'获得其对应的文章对象.
    article = self.art_svc.getArticle(articleId)
    
    # 设置新的数据.
    self.setArticle(article)
    
    # 检查文章的合法性.
    if not self.checkArticle(article):
      return ActionResult.ERROR
    
    # 保存
    self.art_svc.updateArticle(article);
    
    # 处理专题文章、自定义频道文章    
    specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
    channelId = self.params.safeGetIntParam("channelId")
    specialSubjectArticle = self.specialSubjectService.getSpecialSubjectArticleByArticleId(article.objectUuid)
    if specialSubjectArticle != None:
      if specialSubjectId == 0 or specialSubjectId == None:
        self.specialSubjectService.deleteSubjectArticleByArticleGuid(article.objectUuid)
      else:
        specialSubjectArticle.setSpecialSubjectId(specialSubjectId)
        specialSubjectArticle.setTitle(article.title)
        self.specialSubjectService.saveOrUpdateSpecialSubjectArticle(specialSubjectArticle)
    if channelId > 0:
      channelArticle = self.channelPageService.getChannelArticle(article.objectUuid)
      if channelArticle != None:
        channelArticle.setTitle(article.title)
        if self.params.safeGetStringParam("channelCate") == "":
          channelArticle.setChannelCate(None)
        else:
          channelArticle.setChannelCate(self.params.safeGetStringParam("channelCate"))
        self.channelPageService.saveOrUpdateChannelArticle(channelArticle)      
    
    self.addActionMessage(u"对文章 %d 成功地执行了'修改保存'操作." % articleId)
    self.addActionLink(u"返回", "javascript:history.go(-1)")
    self.addActionLink(u"文章管理", "?cmd=list")
    return ActionResult.SUCCESS


  # 修改文章对象.
  def setArticle(self, article):
    article.title = self.params.getStringParam("articleTitle")
    article.articleContent = self.params.getStringParam("articleContent")
    article.articleAbstract = self.params.getStringParam("articleAbstract")
    article.articleTags = self.params.getStringParam("articleTags")
    article.subjectId = self.params.getIntParamZeroAsNull("subjectId")
    #article.userCate = self.params.getIntParamZeroAsNull("userCate") 系统管理员不能修改别人的'个人分类'
    article.sysCateId = self.params.getIntParamZeroAsNull("sysCate")
    article.draftState = self.params.getBooleanParam("draftState")
    article.commentState = self.params.getBooleanParam("commentState") # 评论
    article.hideState = self.params.getIntParam("hideState") # 隐藏
    article.recommendState = self.params.getBooleanParam("rcmd") # 推荐
    audit = self.params.getStringParam("audit") # 审核
    if audit == None or audit == "":
      article.auditState = 1        #未审核
    else:  
      article.auditState = 0        #通过
    article.bestState = self.params.getBooleanParam("best") # 精华
    #article.articlePassword = self.params.getStringParam("articlePassword", "")
    article.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    article.typeState = self.params.getBooleanParam("articleType")
    return article
    

  # 验证文章的标题.
  def checkArticle(self, article):
    if article.title.strip() == "":
      self.addActionError(u"文章标题不能为空！")
      return False
    return True
    

  # 文章回收站列表.
  def recycle_list(self):
    # 构造查询.
    query = self.createQuery()
    query.delState = True           # 过滤被删除了的.
    query.draftState = None
    query.subjectId = self.params.getIntParamZeroAsNull("su")
    request.setAttribute("su", query.subjectId)
    query.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    request.setAttribute("gradeId", query.gradeId)
    query.sysCateId = self.params.getIntParamZeroAsNull("sc")
    request.setAttribute("sc", query.sysCateId)
    query.kk = self.params.getStringParam("k")
    request.setAttribute("k", query.kk)
    query.f = self.params.getStringParam("f")
    request.setAttribute("f", query.f)
    
    # 构造分页对象并计算总量.
    pager = self.createPager()
    pager.totalRows = query.count()
    
    # 得到文章列表.
    article_list = query.query_map(pager)
    
    request.setAttribute("pager", pager)
    request.setAttribute("article_list", article_list)
    self.putSubjectList()
    self.putGradeList()
    self.putArticleCategoryTree()

    return self.ARTICLE_RECYCLE_LIST 


  # 审核通过所选文章.
  def audit(self):
    def check_logic(au, batcher):
      # 验证逻辑: 已经审核的不再次审核; 删除了的，未发布的草稿，非法文章不能进行审核.
      if au.article.auditState == Article.AUDIT_STATE_OK:
        self.addActionError(u"文章 %s 已经审核通过了, 没有再次执行审核操作." % au.artDisplay())
        return False
      if au.article.delState:
        self.addActionError(u"文章 %s 被删除了, 其不能进行审核操作." % au.artDisplay())
        return False
      if au.article.draftState:
        self.addActionError(u"文章 %s 是未发布的草稿, 其不能进行审核操作." % au.artDisplay())
        return False
      
      return True
    
    # 业务部分.
    def do_business(au, batcher, *args):
      
      #管理员直接全部审核
      au.article.setUnitPathInfo(au.article.orginPath)
      au.article.setApprovedPathInfo(au.article.orginPath)      
      self.art_svc.updateArticle(au.article)
      self.art_svc.auditArticle(au.article)
      return True
    
    # 审核通过选中的一个或多个文章.
    batcher = self.createBatcher(operate=u"审核通过", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS
  
  
  # 取消所选文章的审核.
  def unaudit(self):
    def check_logic(au, batcher):
      # 验证逻辑: 未审核通过的不用进行操作.
      if au.article.auditState != Article.AUDIT_STATE_OK:
        self.addActionError(u"文章 %s 未通过审核, 不需要进行取消审核." % au.artDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(au, batcher, *args):      
      au.article.setUnitPathInfo("/" + str(au.article.unitId) + "/")
      au.article.setApprovedPathInfo(None)
      self.art_svc.updateArticle(au.article)
      self.art_svc.unauditArticle(au.article)
      return True
    
    # 取消审核选中的一个或多个文章.
    batcher = self.createBatcher(operate=u"取消审核", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS
    
  
  # 推荐所选文章.
  def rcmd(self):
    def check_logic(au, batcher):
      # 验证逻辑: 未通过审核的不能推荐; 非法文章不能推荐.
      if au.article.recommendState:
        self.addActionError(u"文章 %s 已经是推荐文章了, 不需要进行设置推荐操作." % au.artDisplay())
        return False
      if au.article.auditState != Article.AUDIT_STATE_OK:
        self.addActionError(u"文章 %s 未通过审核, 不能设置为推荐文章." % au.artDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(au, batcher, *args):      
      au.article.setRcmdPathInfo(au.article.orginPath)
      self.art_svc.updateArticle(au.article)
      self.art_svc.rcmdArticle(au.article)
      return True
    
    batcher = self.createBatcher(operate=u"设为推荐", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS
    

  # 取消推荐所选文章.
  def unrcmd(self):
    def check_logic(au, batcher):
      # 验证逻辑: 不是推荐的不用操作.
      if au.article.recommendState == False:
        self.addActionError(u"文章 %s 不是推荐文章, 不需要进行取消推荐操作." % au.artDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(au, batcher, *args):
      #self.art_svc.unrcmdArticle(au.article)
      au.article.setRcmdPathInfo(None)
      au.article.setRecommendState(False)
      self.art_svc.updateArticle(au.article)      
      return True
    
    batcher = self.createBatcher(operate=u"取消推荐", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS

  # 推送文章
  def push(self):
    def check_logic(au, batcher):
      # 验证逻辑: 未通过审核的不能推荐; 非法文章不能推荐.
      if au.article.auditState != Article.AUDIT_STATE_OK:
        self.addActionError(u"文章 %s 还未被审核, 不能进行推送." % au.artDisplay())
        return False
      if au.article.pushState == 1:
        self.addActionError(u"文章 %s 已经是被推送了, 不需要进行设置操作." % au.artDisplay())
        return False
       
      return True    
    # 业务部分.
    def do_business(au, batcher, *args):
      au.article.setPushState(2)
      au.article.setPushUserId(self.loginUser.userId)
      self.art_svc.updateArticle(au.article)
      return True    
    batcher = self.createBatcher(operate=u"设为推送", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS
 
  # 取消推送文章，只能取消待推送的，已经完成的无法取消
  def unpush(self):
    def check_logic(au, batcher):
      # 验证逻辑: 未通过审核的不能推荐; 非法文章不能推荐.
      if au.article.pushState == 1:
        self.addActionError(u"文章 %s 已经是被推送了, 不能进行取消操作." % au.artDisplay())
        return False
      if au.article.pushState == 0:
        self.addActionError(u"文章 %s 还没有设置推送, 不需要进行取消推送." % au.artDisplay())
        return False  
      return True    
    # 业务部分.
    def do_business(au, batcher, *args):
      au.article.setPushState(0)
      au.article.setPushUserId(None)
      self.art_svc.updateArticle(au.article)
      return True    
    batcher = self.createBatcher(operate=u"取消推送", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS

  # 设置为精华.
  def best(self):
    def check_logic(au, batcher):
      # 验证逻辑: 未通过审核的不能推荐; 非法文章不能推荐.
      if au.article.bestState:
        self.addActionError(u"文章 %s 已经是精华文章了, 不需要进行设置精华操作." % au.artDisplay())
        return False
      if au.article.auditState != Article.AUDIT_STATE_OK:
        self.addActionError(u"文章 %s 未通过审核, 不能设置为精华文章." % au.artDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(au, batcher, *args):
      self.art_svc.bestArticle(au.article)
      return True
    
    batcher = self.createBatcher(operate=u"设为精华", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS


  # 取消精华.
  def unbest(self):
    def check_logic(au, batcher):
      # 验证逻辑.
      if au.article.bestState == False:
        self.addActionError(u"文章 %s 不是精华文章, 不需要进行取消精华操作." % au.artDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(au, batcher, *args):
      self.art_svc.unbestArticle(au.article)
      return True
    
    batcher = self.createBatcher(operate=u"取消精华", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS    


  # 设置为合法文章.
  def valid(self):
    def check_logic(au, batcher):
      return True
    
    # 业务部分.
    def do_business(au, batcher, *args):
      self.art_svc.validArticle(au.article)
      return True
    
    batcher = self.createBatcher(operate=u"设为合法", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS



  # 设置为非法文章.
  def invalid(self):
    def check_logic(au, batcher):
      return True
    
    # 业务部分.
    def do_business(au, batcher, *args):
      self.art_svc.invalidArticle(au.article)
      return True
    
    batcher = self.createBatcher(operate=u"设为非法文章", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS


  # 删除文章.
  def delete(self):
    def check_logic(au, batcher):
      if au.article.delState:
        self.addActionError(u"文章 %s 已经删除, 不需要再次进行删除操作." % au.artDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(au, batcher, *args):
      self.art_svc.deleteArticle(au.article)
      score = self.params.getIntParam("score")
      reason = self.params.getStringParam("reason")
      if score == None:
        upun = self.pun_svc.createUPunishScore(3, au.article.id, au.article.userId,self.loginUser.userId,self.loginUser.trueName)
      else:  
        if score<0 :
          upun = self.pun_svc.createUPunishScore(3, au.article.id, au.article.userId, -1*score, reason,self.loginUser.userId,self.loginUser.trueName)
        else:
          upun = self.pun_svc.createUPunishScore(3, au.article.id, au.article.userId, score, reason,self.loginUser.userId,self.loginUser.trueName)
      self.pun_svc.saveUPunishScore(upun)
      #消息通知
      message = Message()
      message.sendId = self.loginUser.userId
      message.receiveId = au.article.userId
      message.title = u"管理员删除了您的文章及扣分信息"
      if reason != "":
        message.content = u"您的 " + au.article.title + u" 被删除,扣" + str(upun.score) + u"分,原因:" + reason
      else:  
        message.content = u"您的 " + au.article.title + u" 被删除,扣" + str(upun.score) + u"分"
      self.msg_svc.sendMessage(message)
      return True
    
    self.addActionLink(u"返回", "?cmd=list&page=" + self.params.getStringParam("ppp"))
    batcher = self.createBatcher(operate=u"删除", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS


  # 恢复文章.
  def recover(self):
    def check_logic(au, batcher):
      if au.article.delState == False:
        self.addActionError(u"文章 %s 未被删除, 不能进行恢复操作." % au.artDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(au, batcher, *args):
      self.art_svc.recoverArticle(au.article)
      upun = self.pun_svc.getUPunishScore(3, au.article.id)
      if upun != None:
        self.pun_svc.deleteUPunishScore(upun)
      return True
    
    batcher = self.createBatcher(operate=u"恢复", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS


  # 彻底删除文章.
  def crash(self):
    def check_logic(au, batcher):
      if au.article.delState == False:
        self.addActionError(u"文章 %s 未在回收站中, 不能进行彻底删除操作." % au.artDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(au, batcher, *args):
      self.art_svc.crashArticle(au.article)
      return True
    
    batcher = self.createBatcher(operate=u"彻底删除", do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS


        

  # 创建后台管理中使用的查询对象.
  def createQuery(self):
    # 构造查询.
    query = AdminArticleQuery(""" a.articleId, a.title, a.createDate, a.addIp, a.articleContent, a.subjectId, a.gradeId,
          a.lastModified, a.auditState, a.bestState, a.recommendState,  a.hideState, a.typeState,a.pushState,
          u.loginName, u.nickName, subj.subjectName, sc.name as sysCateName """)
    query.auditState = None
    query.bestState = None
    query.rcmdState = None
    query.hideState = None
    query.draftState = False        # 还是草稿的归用户管理.
    #query.custormAndWhereClause = "(a.orginPath LIKE '%/" + str(self.loginUser.unitId) + "/%')"
    return query

  # 根据当前页面参数构造一个文章管理使用的分页对象.
  def createPager(self):
    # private 构造文章的缺省 pager.
    pager = self.params.createPager()
    pager.itemName = u"文章"
    pager.itemUnit = u"篇"
    pager.pageSize = 10
    return pager


  # 检查是否对文章具有指定操作权限.
  def default_check_right(self, au, batcher):
    # 检测能否操作该文章.
    #if self.canManageArticle(au.user, au.article) == False:
    #  self.addActionError("不具有对文章 " + au.artDisplay() + " 的操作权限, 可能其不属于您所管理的范围.")
    #  return False
    
    return True
  
  # 得到指定标识的文章及其作者的信息.
  def getArticleAndAuthor(self, articleId):
    cmd = Command("SELECT a, u FROM Article a, User u WHERE a.userId = u.userId AND a.articleId = :articleId")
    cmd.setInteger("articleId", articleId)
    list = cmd.open(1)
    if list == None or list.size() == 0:
      return None
    au = list[0]
    return ArticleAndUser(au[0], au[1])
    
    
  def createBatcher(self, operate= u"执行操", check_logic=empty_func, do_business=None):
    # 获取数据步骤.
    def get_data(articleId, batcher):
      au = self.getArticleAndAuthor(articleId)
      if au == None:
        self.addActionError(u"未能找到指定标识为 %d 的文章." % articleId)
        return None
      batcher.data = au
      return au;
    
    # 记录日志.
    def do_log(au, batcher):
      batcher.count += 1
      self.addActionMessage(u"对文章 %s 成功地执行了%s操作." % (au.artDisplay(), batcher.operate))
      return
    
    
    batcher = BusinessBatcher(initer=self.batcher_initer,
                              finisher=self.batcher_finisher)
    batcher.result = ActionResult.ERROR
    batcher.cmd = self.params.getStringParam("cmd")
    batcher.operate = operate
    batcher.get_data = get_data
    batcher.check_logic = check_logic
    batcher.check_right = self.default_check_right
    batcher.do_business = do_business
    batcher.do_log = do_log
    
    return batcher
  

  # 标准的批处理初始化, 将所选中的文章标识做为任务.
  def batcher_initer(self, batcher):
    if self.loginUser == None:
      batcher.result = ActionResult.LOGIN
      return False

    art_ids = self.params.getIdList("articleId")
    if art_ids == None or art_ids.size() == 0:
      self.addActionError(u"没有选择要操作的文章.")
      batcher.result = ActionResult.ERROR
      return False
    
    batcher.taskList = art_ids
    return True


  # 标准的批处理结束.
  def batcher_finisher(self, batcher):
    self.addActionMessage(u"共对  " + str(batcher.count) + u"个文章执行了"+ batcher.operate + u"操作.")

    return True
    
  
  # 把文章分类树放置到 request 中.
  def putArticleCategoryTree(self):
    article_categories = self.cate_svc.getCategoryTree('default')
    request.setAttribute("article_categories", article_categories)
    
  # 把四名工程文章分类树放置到 request 中.
  def putChannelArticleCategoryTree(self,art):
    if art.userId == None:return
    articleUser = self.userService.getUserById(art.userId)
    if articleUser.channelId != None:
      channel = self.channelPageService.getChannel(articleUser.channelId)
      if channel == None:return
    channel_article_categories = self.cate_svc.getCategoryTree("channel_article_" + str(articleUser.channelId))
    request.setAttribute("channel_article_categories", channel_article_categories)    
    
  # 设置文章分类.
  def moveCate(self):
    # 得到目标分类参数, 并验证该分类的正确性.
    sysCateId = self.params.getIntParamZeroAsNull("sysCateId")
    #print sysCateId
    if sysCateId != None:
      category = self.cate_svc.getCategory(sysCateId)
      if category == None:
        self.addActionError(u"指定标识的文章分类不存在.")
        return ActionResult.ERROR
      #if category.itemType != 'article':
        #self.addActionError("指定标识的分类 %s 不是一个正确的文章分类, 请确定您是从有效链接提交的数据." % category.name)
        #return ActionResult.ERROR
         
    def check_logic(au, batcher):
      if au.article.sysCateId == sysCateId:
        self.addActionError(u"文章 %s 分类已经是所选分类, 未进行转移新分类操作.")
        return False
      return True
    
    # 业务部分.
    def do_business(au, batcher, *args):
      self.art_svc.moveArticleSysCate(au.article, sysCateId)
      return True
    
    # 取消推荐选中的一个或多个资源.
    batcher = self.createBatcher(operate=u'转移文章分类', do_business=do_business, check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS
  

# 包括 article, user 两个对象的容器.
class ArticleAndUser:
  def __init__(self, article, user):
    self.article = article
    self.user = user
  def artDisplay(self):
    return self.article.toDisplayString()
  def userDisplay(self):
    return self.user.toDisplayString()
    
# 文章管理中使用高级查询过滤.
class AdminArticleQuery (ArticleQuery):
  def __init__(self, selectFields):
    ArticleQuery.__init__(self, selectFields)
    self.kk = None
    self.f = None
    
    
  def applyWhereCondition(self, qctx):
    ArticleQuery.applyWhereCondition(self, qctx)
    #if self.kk != None and self.kk != '':
    #  self._applyKeywordFilter(qctx)

  def _applyKeywordFilter(self, qctx):
    newKey = self.kk.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
    if self.f == 'title':   # 用文章标题、标签.
      qctx.addAndWhere('a.title LIKE :kk OR a.articleTags LIKE :kk')
      qctx.setString('kk', '%' + newKey + '%')
    elif self.f == 'intro': # 用简介过滤.
      qctx.addAndWhere('a.articleAbstract LIKE :kk')
      qctx.setString('kk', '%' + newKey + '%')
    elif self.f == 'uname': # 用户名 (maybe id).
      try:
        userId = int(self.kk)
        qctx.addAndWhere('a.userId = :kk')
        qctx.setInteger('kk', userId)
      except:
        qctx.addAndWhere('u.loginName = :kk OR u.nickName = :kk OR u.trueName = :kk')
        qctx.setString('kk', newKey)
    elif self.f == 'unit':  # 用户所在机构.
      qctx.addAndWhere('u.unit.unitName LIKE :kk')
      qctx.setString('kk', '%' + newKey + '%')