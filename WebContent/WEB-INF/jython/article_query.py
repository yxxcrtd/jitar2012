# coding=utf-8
from cn.edustar.jitar.data import BaseQuery
from base_action import *
# =======================================================================
# 文章查询.
class ArticleQuery (BaseQuery, SubjectMixiner):
  # 排序方式.
  ORDER_TYPE_ID_DESC = 0
  ORDER_TYPE_ARTICLEID_DESC = 0
  ORDER_TYPE_CREATEDATE_DESC = 1
  ORDER_TYPE_VIEWCOUNT_DESC = 2
  ORDER_TYPE_COMMENTCOUNT_DESC = 3
    
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.params = ParamUtil(request)
    self.cate_svc = __jitar__.categoryService
    # 审核状态, 缺省 = AUDIT_STATE_OK 表示获取审核通过的. 
    self.auditState = 0
    # 是否被删除, 缺省 = false 表示选择未删除的; = true 表示获得删除的; = null 表示不限制此条件.
    self.delState = False
    # 查询隐藏状态，== null 表示不区分，缺省 = 0 查询非隐藏的.
    self.hideState = 0
    # 限定学科标识, 缺省 = 0 表示获取站点的; = null 表示不限制.
    self.subjectId = self.params.getIntParamZeroAsNull("subjectId")
    # 新闻所属学段
    self.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    # 查询草稿状态，== null 表示不区分，缺省 = false 查询非草稿的.
    self.draftState = False
  
    # 是否推荐, 缺省 = null 表示不限制.
    self.rcmdState = None
    
    self.userIsFamous = None
    
    # 专题内推荐，None 不设置，1:推荐    
    self.specialSubjectStatus = None
    
    self.unitId = None
    self.loginName = None
    self.unit = None
    self.pushState = None
    
    self.custormAndWhereClause = None  
    
    # 默认是精确匹配
    self.FuzzyMatch = False    
    # 文章标签
    self.articleTags = None
    self.newArticleIds = None
    self.specialSubjectId = None
    
    # 是否精华，缺省为 null 表示所有文章; = true 表示获取精华的; = false 表示获取非精华的.
    self.bestState = None
    # 排序方式, 0 - 按照 id desc, 1 - createDate desc, 2 - viewCount desc, 3 - commentCount desc
    self.orderType = 0
    
    
    self.userId = self.params.getIntParamZeroAsNull("userId")           # 用户筛选.
    self.sysCateId = self.params.getIntParamZeroAsNull("categoryId")        # 文章文章所属系统分类.
    self.userCateId = self.params.getIntParamZeroAsNull("userCateId")       # 文章文章所属用户分类.
    self.unitId = None #self.params.getIntParamZeroAsNull("unitId")          # 用户所属机构.
    self.k = self.params.getStringParam("k")          # 关键字
    self.f = self.params.getStringParam("f")          # 查询的字段
    if self.f == None:
      self.f = "title"
    if self.f == "":
      self.f = "title"
      
    request.setAttribute("categoryId", self.sysCateId)
    request.setAttribute("unitId", self.unitId)
    request.setAttribute("sysCateId", self.sysCateId)
    request.setAttribute("userId", self.userId)
    request.setAttribute("subjectId", self.subjectId)
    request.setAttribute("gradeId", self.gradeId)
    request.setAttribute("k", self.k)
    request.setAttribute("f", self.f)
    
  def initFromEntities(self, qctx):
    qctx.addEntity("Article", "a", "")
    
  def resolveEntity(self, qctx, entity):
    if "u" == entity:
      qctx.addEntity("User", "u", "a.userId = u.userId")
    elif "subj" == entity:
      qctx.addJoinEntity("a", "a.subject", "subj", "LEFT JOIN")
    elif ("grad" == entity):
      qctx.addJoinEntity("a", "a.grade", "grad", "LEFT JOIN")
    elif ("sc" == entity):
      qctx.addJoinEntity("a", "a.sysCate", "sc", "LEFT JOIN")
    elif ("uc" == entity):
      qctx.addJoinEntity("a", "a.userCate", "uc", "LEFT JOIN")
    else:
      BaseQuery.resolveEntity(self, qctx, entity)      

  # 提供 where 条件.
  def applyWhereCondition(self, qctx):
    if self.custormAndWhereClause != None:
      qctx.addAndWhere(" " + self.custormAndWhereClause + " ")
      
    if self.auditState != None:
      qctx.addAndWhere("a.auditState = :auditState")
      qctx.setInteger("auditState", self.auditState)
    if self.delState != None :
      qctx.addAndWhere("a.delState = :delState")
      qctx.setBoolean("delState", self.delState)
    if self.hideState != None :  
      qctx.addAndWhere("a.hideState = :hideState")
      qctx.setInteger("hideState", self.hideState)
    if self.subjectId != None:
      qctx.addAndWhere("a.subjectId = :subjectId")
      qctx.setInteger("subjectId", self.subjectId)
    if self.unitId != None:
      qctx.addAndWhere("a.unitId = :unitId")
      qctx.setInteger("unitId", self.unitId)      
    if self.gradeId != None:
      if self.FuzzyMatch == False:
        qctx.addAndWhere("a.gradeId = :gradeId")
        qctx.setInteger("gradeId", self.gradeId)
      else:
        qctx.addAndWhere("a.gradeId >= :gradeStartId AND a.gradeId < :gradeEndId")
        qctx.setInteger("gradeStartId", self.calcGradeStartId(self.gradeId))
        qctx.setInteger("gradeEndId", self.calcGradeEndId(self.gradeId))

    
    if self.draftState != None :
      qctx.addAndWhere("a.draftState = :draftState")
      qctx.setBoolean("draftState", self.draftState)
    if self.rcmdState != None :
      qctx.addAndWhere("a.recommendState = :rcmdState");
      qctx.setBoolean("rcmdState", self.rcmdState)
    if self.bestState != None :
      qctx.addAndWhere("a.bestState = :bestState")
      qctx.setBoolean("bestState", self.bestState)
    if self.userId != None:
      qctx.addAndWhere("a.userId = :userId")
      qctx.setInteger("userId", self.userId)
    if self.loginName != None:
      qctx.addAndWhere("a.loginName = :loginName")
      qctx.setString("loginName", self.loginName)
    if self.subjectId != None:
      qctx.addAndWhere("a.subjectId = :subjectId")
      qctx.setInteger("subjectId", self.subjectId)
    if self.sysCateId != None:
      #只查询分类自己的
      #qctx.addAndWhere("a.sysCateId = :sysCateId")
      #qctx.setInteger("sysCateId", self.sysCateId)
      #查询分类自己的和其下级的分类
      list=self.cate_svc.getCategoryIds(self.sysCateId)
      cateIds=""
      for c in list:
        if cateIds=="":
          cateIds=cateIds+str(c)
        else:
          cateIds=cateIds+","+str(c)
      qctx.addAndWhere("a.sysCateId IN (" + cateIds +")")
      
    if self.specialSubjectId != None:
      qctx.addAndWhere("a.specialSubjectId = :specialSubjectId")
      qctx.setInteger("specialSubjectId", self.specialSubjectId)
    
    if self.userCateId != None:
      qctx.addAndWhere("a.userCateId = :userCateId")
      qctx.setInteger("userCateId", self.userCateId)    
       
    if self.userIsFamous != None:
      qctx.addAndWhere("u.userType LIKE '%/1/%'")
      
    if self.pushState != None:
      qctx.addAndWhere("a.pushState = :pushState")
      qctx.setInteger("pushState", self.pushState)
       
    if self.specialSubjectStatus != None:
      qctx.addAndWhere("a.specialSubjectStatus = :specialSubjectStatus")
      qctx.setInteger("specialSubjectStatus", self.specialSubjectStatus)
      
    if self.articleTags != None:
      #qctx.addAndWhere("a.title LIKE :articleTags OR a.articleAbstract LIKE :articleTags OR a.articleContent LIKE :articleTags")
      qctx.addAndWhere("a.title LIKE :articleTags")
      qctx.setString("articleTags", "%" + self.articleTags + "%")
            
    if self.newArticleIds != None:
      #print "newArticleIds==", self.newArticleIds
      s = ','.join(self.newArticleIds)
      #print '===', s
      qctx.addAndWhere("a.articleId IN (" + s + ")")
      #qctx.setString("newArticleIds", newArticleIds)    
    
    if self.k != None and self.k != '':
      newKey = self.k.replace("%","[%]").replace("_","[_]").replace("[","[[]")
      if self.f == "title":
        qctx.addAndWhere("a.title LIKE :keyword OR a.articleTags LIKE :keyword")
        qctx.setString("keyword", "%" + newKey + "%")
      elif self.f == "intro":
        qctx.addAndWhere("a.articleAbstract LIKE :keyword ")
        qctx.setString("keyword", "%" + newKey + "%")
      elif self.f == "uname":
        qctx.addAndWhere("u.nickName LIKE :keyword OR u.trueName LIKE :keyword OR u.loginName LIKE :keyword")
        qctx.setString("keyword", "%" + newKey + "%")
      elif self.f == "unitName":
        qctx.addAndWhere("u.unit.unitName LIKE :keyword ")
        qctx.setString("keyword", "%" + newKey + "%")
      elif self.f == "unitTitle":
        qctx.addAndWhere("u.unit.unitTitle LIKE :keyword ")
        qctx.setString("keyword", "%" + newKey + "%")
      elif self.f == "unit":
        qctx.addAndWhere("u.unit.unitTitle LIKE :keyword ")
        qctx.setString("keyword", "%" + newKey + "%")
      else:
        qctx.addAndWhere("a.title LIKE :keyword OR a.articleTags LIKE :keyword")
        qctx.setString("keyword", "%" + newKey + "%")

    # 对于需要做除法的情况，除法不能为 0
    if self.orderType != None and self.orderType == 6:
      qctx.addAndWhere("a.commentCount > 0")
        

  # 提供排序 order 条件.
  def applyOrderCondition(self, qctx):
    # 排序方式, 0 - 按照 id desc, 1 - createDate desc, 2 - viewCount desc, 3 - commentCount desc
    # 顶、踩、星级
    if self.orderType == 0:
      qctx.addOrder("a.articleId DESC")
    elif self.orderType == 1:
      qctx.addOrder("a.createDate DESC")
    elif self.orderType == 2:
      qctx.addOrder("a.viewCount DESC")
    elif self.orderType == 3:
      qctx.addOrder("a.commentCount DESC")
    elif self.orderType == 4:
      qctx.addOrder("a.digg DESC")
    elif self.orderType == 5:
      qctx.addOrder("a.trample DESC")
    elif self.orderType == 6:
      qctx.addOrder("(a.starCount/a.commentCount) DESC")

# 增加了用户条件的文章查询.
class UserArticleQuery (ArticleQuery):
  def __init__(self, selectFields):
    ArticleQuery.__init__(self, selectFields)
    self.userIsFamous = None          # 是否名师, == null 表示不限制.

        
  def initFromEntities(self, qctx):
    qctx.addEntity("Article", "a", "")
    qctx.addEntity("User", "u", "a.userId = u.userId")

  def applyWhereCondition(self, qctx):
    ArticleQuery.applyWhereCondition(self, qctx)
    if self.userIsFamous != None:
      qctx.addAndWhere("u.userType LIKE '%/1/%'")    
    

# =======================================================================
# 协作组文章查询.
class GroupArticleQuery(BaseQuery):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.cate_svc = __jitar__.categoryService
    self.groupId = None         # int, 限定某个协作组.
    self.groupBest = None       # True /False 是否小组精华文章  
    self.gartCateId = None
    self.articleId = None
    self.orderType = 0
    self.articleState = True
    self.includeChildGroup=False  #是否包括子分组的
    self.gartCateName = None    #分类名
    
  def initFromEntities(self, qctx):
    qctx.addEntity("GroupArticle", "ga", "")


  def resolveEntity(self, qctx, entity):
    # 能够关联到协作组 g
    if "g" == entity:
      qctx.addEntity("Group", "g", "ga.groupId = g.groupId")
    else:
      BaseQuery.resolveEntity(self, qctx, entity)

  def applyWhereCondition(self, qctx):
    if self.groupId != None:
      #print "self.includeChildGroup="+str(self.includeChildGroup)
      if self.includeChildGroup==False:
        qctx.addAndWhere("ga.groupId = :groupId")
        qctx.setInteger("groupId", self.groupId)
      else:
        qctx.addAndWhere("ga.groupId = :groupId or ga.groupId In(SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE parentId=:parentId)")
        qctx.setInteger("groupId", self.groupId)
        qctx.setInteger("parentId", self.groupId)
    if self.gartCateName!=None:
      qctx.addAndWhere("ga.groupCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
      qctx.setString("catename", self.gartCateName)
    elif self.gartCateId != None:
      #只查询分类自己的
      #qctx.addAndWhere("ga.groupCateId = :groupCateId")
      #qctx.setInteger("groupCateId", self.gartCateId)
      #查询包含子孙分类的
      list=self.cate_svc.getCategoryIds(self.gartCateId)
      cateIds=""
      for c in list:
        if cateIds=="":
          cateIds=cateIds+str(c)
        else:
          cateIds=cateIds+","+str(c)
      qctx.addAndWhere("ga.groupCateId IN (" + cateIds +")")
      
    if self.groupBest != None:
      qctx.addAndWhere("ga.isGroupBest = :groupbest")
      qctx.setInteger("groupbest", self.groupBest)
    if self.articleId != None:
      qctx.addAndWhere("ga.articleId = :articleId")
      qctx.setInteger("articleId", self.articleId)
    if self.articleState != None:
      qctx.addAndWhere("ga.articleState = :articleState")
      qctx.setBoolean("articleState", self.articleState)
      
    BaseQuery.applyWhereCondition(self, qctx)
    
  def applyOrderCondition(self, qctx):
    if self.orderType == 0:
      qctx.addOrder("ga.id DESC")
    elif self.orderType == 1:
      qctx.addOrder("ga.articleId DESC")