# script 
from cn.edustar.jitar.pojos import Article
from cn.edustar.jitar.data import Command
from article_query import ArticleQuery
from base_blog_page import *
from base_action import BaseAction
from com.alibaba.fastjson import JSONObject
from cn.edustar.jitar.model import ObjectType


# 此文件的全局变量
user_svc = __jitar__.userService
art_svc = __jitar__.articleService
cate_svc = __jitar__.categoryService
pun_svc = __jitar__.UPunishScoreService

# 显示个人的文章
class show_article(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
  def execute(self):
    self.loginName = request.getAttribute("loginName")
    #print "Hello, show_article is executed"
    if self.parseUri() == False:
      return self.sendNotFound(self.uri)
    
    # 得到用户并验证用户状态
    self.user = user_svc.getUserByLoginName(self.loginName)
    request.setAttribute("user", self.user)
    #print "self.user = ", self.user
    if self.canVisitUser(self.user) == False:
      return self.ACCESS_ERROR
  
    # 得到文章并验证文章状态
    self.article = art_svc.getArticle(self.articleId)
    if self.article == None:
      return self.sendNotFound()
    canView = False
    if self.loginUser != None:
      accessControlService = __spring__.getBean("accessControlService")
      controlList = accessControlService.getAllAccessControlByUser(self.loginUser)
      canView = ((controlList != None) or (self.article.userId == self.loginUser.userId))
    punshScore= pun_svc.getUPunishScore(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId() , self.articleId)
    if punshScore!=None:
      if punshScore.getScore()<0 :
        request.setAttribute("scoreCreateUserId", punshScore.getCreateUserId())
        request.setAttribute("scoreCreateUserName", punshScore.getCreateUserName())
        request.setAttribute("score", -1*punshScore.getScore())
        request.setAttribute("scoreReason", punshScore.getReason())
        request.setAttribute("scoreDate", punshScore.getPunishDate())
        request.setAttribute("scoreObjId", punshScore.getObjId())
        request.setAttribute("scoreObjTitle", punshScore.getObjTitle())
                          
    # print "访问文章: self.article =", self.article, "by", self.user
    if canView == False:
      if self.article.auditState != Article.AUDIT_STATE_OK:
        return self.sendNotFound()    # TODO: 更好的提示
      elif self.article.draftState:
        return self.sendNotFound()    # TODO: 更好的提示
      elif self.article.delState:
        return self.sendNotFound()    # TODO: 更好的提示
    
    
    # 得到此文章的 page. 当前实现使用系统缺省的
    page = self.getUserEntryPage(self.user);
    #print "page = ", page
    if page == None:
      return self.sendNotFound()
    request.setAttribute("page", page)
    
    self.page = self.getUserProfilePage(self.user)
    if self.page.customSkin != None:
      customSkin = JSONObject.parse(self.page.customSkin)
      request.setAttribute("customSkin", customSkin)
          
    # 得到页面功能块
    widget_list = self.getPageWidgets(page)
    #print "widget_list = ", widget_list
    request.setAttribute("widget_list", widget_list)
    
    # 获得前一篇文章, 后一篇文章
    self.get_prev_article()
     
    # 相关文章
    self.getSimilarArticle()
    
    # 获得评论星级
    self.get_comment_star()
    
    # 增加访问计数
    art_svc.increaseViewCount(self.article.articleId, 1)
    
    self.get_System_Category()
    self.get_User_Category()
    #修正计数器加1，页面显示滞后 的问题
    self.article.viewCount += 1 
    request.setAttribute("article", self.article)
    return "/WEB-INF/user/default/user_entry.ftl"
    
    
  # 解析 uri, 从中得到 loginName, articleId 
  def parseUri(self):
    self.uri = self.getRequestURI()
    # print "requestURI is ", self.uri
    if self.uri == None or self.uri == "":
      return False
    
    # 例子 /Groups/u/admin/article/153.html ['', 'Groups', 'u', 'admin', 'article', '153.html'], 
    # 最小 /admin/article/153.html ['', 'admin', 'article', '153.html']
    arr = self.uri.split('/')
    arr_len = len(arr)
    if arr_len < 3:
      return False

    # 得到文章标识部分
    article_part = self.removeHtmlExt(arr[arr_len - 1])    # 153.html -> 153
    if isIntegerStrong(article_part) == False: 
      return False
    self.articleId = int(article_part)
    # print "self.articleId = ", self.articleId
    
    
    return True
    
    
  def get_System_Category(self):
    if self.article.sysCateId != None and self.article.sysCateId != 0 :
      SysCate = cate_svc.getCategory(self.article.sysCateId)
      request.setAttribute("SysCate", SysCate)
      
  def get_User_Category(self):
    if self.article.userCateId != None and self.article.userCateId != 0 :
      UserCate = cate_svc.getCategory(self.article.userCateId)
      request.setAttribute("UserCate", UserCate)    
   
  # 得到当前文章的前一篇后一篇
  def get_prev_article(self):
    qry = PrevNextArticleQuery(" a.articleId, a.title, a.typeState ")
    qry.userId = self.article.userId
    qry.articleId = self.article.articleId
    
    qry.prev = True
    prev_article = qry.first_map()
    #print "prev_article = ", prev_article
    request.setAttribute("prev_article", prev_article)
    
    qry.prev = False
    next_article = qry.first_map()
    #print "next_article = ", next_article
    request.setAttribute("next_article", next_article)
    return

    # 相关文章
  def getSimilarArticle(self):
      tagA = self.article.articleTags.split(",")
      # print "当前文章的标签：", tagA
      myarray = []
      for tag in tagA:
          qry = ArticleQuery(" a.articleId, a.title, a.articleAbstract, a.typeState, a.createDate ")
          qry.articleTags = tag
          article_list = qry.query_map()
          # print "文章列表：", article_list.size()
          if article_list.size() == 0:
              # print self.article.articleId
              lastAry = []
              lastAry.append(str(self.article.articleId))
              qury = ArticleQuery(""" a.articleId, a.title, a.articleAbstract, a.typeState, a.createDate """)
              qury.newArticleIds = lastAry
              new_article_list = qury.query_map()
              # 返回
              request.setAttribute("similarArticleList", new_article_list)
          
          if article_list.size() > 0:
              for a in article_list:
                  myarray.append(str(a['articleId']))                  
                  lastAry = list(set(myarray))
                  # print "去重后的数组：", lastAry
                  qury = ArticleQuery(" a.articleId, a.title, a.articleAbstract, a.typeState, a.createDate ")
                  qury.newArticleIds = lastAry
                  new_article_list = qury.query_map()
                  # 返回
                  request.setAttribute("similarArticleList", new_article_list)
  	
  
  # 得到星级评论结果
  def get_comment_star(self):    
    # 注意：如果没有记录，返回的结果是 StarCount 为 Null，StarNumber 为 0，所以 commentStar 永远不会为 None
    sql = " SELECT SUM(star) AS StarCount, COUNT(star) AS StarNumber FROM Comment WHERE objId = :articleId "
    cmd = Command(sql)
    cmd.setInteger("articleId", self.article.articleId)
    commentStar = cmd.first()
    if commentStar == None:
      return
    else:
      if commentStar[0] == None:
        return
      else:       
        StarCount = int(commentStar[0])        
        
      if commentStar[1] == None:
        return
      else:
        StarNumber = int(commentStar[1])
      if StarNumber < 1 :
        return

      AveStar = StarCount / StarNumber
      request.setAttribute("StarCount", StarCount)
      request.setAttribute("StarNumber", StarNumber)
      request.setAttribute("AveStar", AveStar)

# 查询指定用户的指定文章的前一篇后一篇的查询
class PrevNextArticleQuery(ArticleQuery):
  def __init__(self, fields):
    ArticleQuery.__init__(self, fields)
    self.articleId = 0
    self.prev = True
    
  def applyWhereCondition(self, qctx):
    ArticleQuery.applyWhereCondition(self, qctx)
    if self.prev:
      # 前一篇表示比当前文章 '发表早' 的 '最新' 一篇文章
      qctx.addAndWhere("a.articleId < :articleId")
    else:
      # 后一篇表示比当前文章 '发表晚' 的 '最旧' 一篇文章
      qctx.addAndWhere("a.articleId > :articleId")
    qctx.setInteger("articleId", self.articleId)

  def applyOrderCondition(self, qctx):
    if self.prev:
      # 前一篇表示比当前文章 '发表早' 的 '最新' 一篇文章
      qctx.addOrder("a.articleId DESC")
    else:
      # 后一篇表示比当前文章 '发表晚' 的 '最旧' 一篇文章
      qctx.addOrder("a.articleId ASC")