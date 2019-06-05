# -*- coding: utf-8 -*-
from cn.edustar.jitar.pojos import Resource
from cn.edustar.jitar.data import BaseQuery
from base_action import *  

# =======================================================================
# 资源查询.
class ResourceQuery (BaseQuery, SubjectMixiner):
  ORDER_TYPE_ID_DESC = 0
  ORDER_TYPE_CREATEDATE_DESC = 1
  ORDER_TYPE_VIEWCOUNT_DESC = 2
  ORDER_TYPE_COMMENTCOUNT_DESC = 3
  ORDER_TYPE_DOWNLOADCOUNT_DESC = 4
  
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.params = ParamUtil(request)
    self.cate_svc = __jitar__.categoryService
    #多个资源查询
    self.resourceIds = None
    #单个资源Id
    self.resourceId = None
    #发布到资源库
    self.publishToZyk = None
    
    # 审核状态, 缺省 = AUDIT_STATE_OK 表示获取审核通过的. 
    self.auditState = 0
    # 是否被删除, 缺省 = false 表示选择未删除的; = true 表示获得删除的; = null 表示不限制此条件.
    self.delState = False
    # 是否推荐, 缺省 = null 表示不限制.
    self.rcmdState = None
    # 共享模式, 缺省 = SHARE_MODE_FULL 也就是获取完全共享的资源.
    self.shareMode = Resource.SHARE_MODE_FULL
 
    # 资源类型, 缺省 = null 不限制.
    self.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
    # 资源所属学科, 缺省 = null 不限制. 
    self.subjectId = self.params.getIntParamZeroAsNull("subjectId")
    # 资源所属学段
    self.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    # 资源所属学段是否精确 level=1 精确处理
    self.gradelevel = self.params.getIntParamZeroAsNull("level")
    
    #个人分类
    self.userCateId = self.params.getIntParamZeroAsNull("userCateId")
    
    # 排序方式, 0 - 按照 id desc, 1 - createDate desc, 2 - viewCount desc, 3 - commentCount desc
    #   4 - downloadCount desc, ...
    self.orderType = 0
    self.k = self.params.getStringParam("k")        # title, tags 查询关键字.
    self.f = self.params.getStringParam("f")        # title, tags 查询关键字字段.
    
    self.unitId = None #self.params.getIntParamZeroAsNull("unitId")            # 用户所属机构.
    self.userId = self.params.getIntParamZeroAsNull("userId")   
    
    self.pushState = None
    # 默认是精确匹配
    self.FuzzyMatch = False
    
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
    
    #自定义条件
    self.custormAndWhereClause = None

    
  def initFromEntities(self, qctx):
    qctx.addEntity("Resource", "r", "")
    
  def resolveEntity(self, qctx, entity):
    if "u" == entity:
      qctx.addEntity("User", "u", "r.userId = u.userId")
    elif ("msubj" == entity):
      qctx.addEntity("MetaSubject", "msubj", "r.subjectId=msubj.msubjId")
    elif "subj" == entity:
      qctx.addJoinEntity("r", "r.subject", "subj", "LEFT JOIN")
    elif ("grad" == entity):
      qctx.addJoinEntity("r", "r.grade", "grad", "LEFT JOIN")
    elif ("rt" == entity):
      qctx.addJoinEntity("r", "r.resType", "rt", "LEFT JOIN")
    elif ("sc" == entity):
      qctx.addJoinEntity("r", "r.sysCate", "sc", "LEFT JOIN")
    elif ("uc" == entity):
      qctx.addJoinEntity("r", "r.userCate", "uc", "LEFT JOIN")
    elif ("vc" == entity):
      qctx.addEntity("ViewCount", "vc", "r.objectUuid = vc.objGUID")
    else :
      BaseQuery.resolveEntity(self, qctx, entity);

  # 提供 where 条件.
  def applyWhereCondition(self, qctx):
    if self.userId != None:
      qctx.addAndWhere("r.userId = :userId")
      qctx.setInteger("userId", self.userId)
    if self.subjectId != None:
      qctx.addAndWhere("r.subjectId = :subjectId")
      qctx.setInteger("subjectId", self.subjectId)
    
    if self.custormAndWhereClause != None:
      qctx.addAndWhere(self.custormAndWhereClause)
      
    if self.gradeId != None:
      if self.gradelevel != None:
        if self.gradelevel == 1:
          qctx.addAndWhere("r.gradeId = :gradeStartId")
          qctx.setInteger("gradeStartId", self.gradeId)
        else:
          if self.FuzzyMatch == False:
            qctx.addAndWhere("r.gradeId = :gradeId")
            qctx.setInteger("gradeId", self.gradeId)
          else:
            qctx.addAndWhere("r.gradeId >= :gradeStartId AND r.gradeId < :gradeEndId")
            qctx.setInteger("gradeStartId", self.calcGradeStartId(self.gradeId))
            qctx.setInteger("gradeEndId", self.calcGradeEndId(self.gradeId))
      else:
        if self.FuzzyMatch == False:
          qctx.addAndWhere("r.gradeId = :gradeId")
          qctx.setInteger("gradeId", self.gradeId)
        else:
          qctx.addAndWhere("r.gradeId >= :gradeStartId AND r.gradeId < :gradeEndId")
          qctx.setInteger("gradeStartId", self.calcGradeStartId(self.gradeId))
          qctx.setInteger("gradeEndId", self.calcGradeEndId(self.gradeId))
        
      #if self.gradelevel != None:
      #  if self.gradelevel == 1:
      #    qctx.addAndWhere("r.gradeId = :gradeStartId")
      #    qctx.setInteger("gradeStartId", self.gradeId)
      #  else:
      #    qctx.addAndWhere("r.gradeId >= :gradeStartId AND r.gradeId < :gradeEndId")
      #    qctx.setInteger("gradeStartId", self.calcGradeStartId(self.gradeId))
      #    qctx.setInteger("gradeEndId", self.calcGradeEndId(self.gradeId))
      #else:
      #  qctx.addAndWhere("r.gradeId = :gradeId")
      #  qctx.setInteger("gradeId", self.gradeId)
        #qctx.addAndWhere("r.gradeId >= :gradeStartId AND r.gradeId < :gradeEndId")
        #qctx.setInteger("gradeStartId", self.calcGradeStartId(self.gradeId))
        #qctx.setInteger("gradeEndId", self.calcGradeEndId(self.gradeId))
      
    if (self.auditState != None):
      qctx.addAndWhere("r.auditState = :auditState");
      qctx.setInteger("auditState", self.auditState);
      
    if self.unitId != None:
      qctx.addAndWhere("r.unitId = :unitId")
      qctx.setInteger("unitId", self.unitId)
      
    if self.pushState != None:
      qctx.addAndWhere("r.pushState = :pushState")
      qctx.setInteger("pushState", self.pushState)     
      
    if (self.delState != None) :
      qctx.addAndWhere("r.delState = :delState")
      qctx.setBoolean("delState", self.delState)
    if (self.rcmdState != None) :
      qctx.addAndWhere("r.recommendState = :rcmdState")
      qctx.setBoolean("rcmdState", self.rcmdState)
      
    if (self.shareMode != None):
      if(self.shareMode >= 1000):
        qctx.addAndWhere("r.shareMode >= :shareMode")
        qctx.setInteger("shareMode", self.shareMode)
      elif(self.shareMode == 500):
        # TODO:测试     这行被修改为 >= 的条件，可能导致一些问题，未测试
        qctx.addAndWhere("r.shareMode >= :shareMode")
        qctx.setInteger("shareMode", self.shareMode)
      elif(self.shareMode == 0):
        qctx.addAndWhere("r.shareMode >= :shareMode")
        qctx.setInteger("shareMode", self.shareMode)
      else:
        qctx.addAndWhere("r.shareMode >= :shareMode")
        qctx.setInteger("shareMode", self.shareMode)
      
    if self.sysCateId != None:
      #只查询分类自己的  
      #qctx.addAndWhere("r.sysCateId = :sysCateId")
      #qctx.setInteger("sysCateId", self.sysCateId)
      #查询自己和子孙分类
      list=self.cate_svc.getCategoryIds(self.sysCateId)
      cateIds=""
      for c in list:
        if cateIds=="":
          cateIds=cateIds+str(c)
        else:
          cateIds=cateIds+","+str(c)
      qctx.addAndWhere("r.sysCateId IN (" + cateIds + ")")
            
    if self.userCateId != None:
      qctx.addAndWhere("r.userCateId = :userCateId")
      qctx.setInteger("userCateId", self.userCateId)
      
    if self.k != None and self.k != '':
      newKey = self.k.replace("%","[%]").replace("_","[_]").replace("[","[[]")
      if self.f == "title":
        qctx.addAndWhere("r.title LIKE :keyword OR r.tags LIKE :keyword")
        qctx.setString("keyword", "%" + newKey + "%")
      elif self.f == "intro":
        qctx.addAndWhere("r.summary LIKE :keyword ")
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
        qctx.addAndWhere("r.title LIKE :keyword OR r.tags LIKE :keyword")
        qctx.setString("keyword", "%" + newKey + "%")
    
      
    if self.resourceId != None:
      qctx.addAndWhere("r.resourceId = :resourceId")
      qctx.setInteger("resourceId", self.resourceId)
    if self.resourceIds != None:
      strWhereClause = " r.resourceId IN (" + self.resourceIds + ")" 
      qctx.addAndWhere(strWhereClause)
      
    if self.publishToZyk != None:
      qctx.addAndWhere("r.publishToZyk = :publishToZyk")
      qctx.setInteger("publishToZyk", self.publishToZyk)
      
  # 提供排序 order 条件.
  def applyOrderCondition(self, qctx):
    # 排序方式, 0 - 按照 id desc, 1 - createDate desc, 2 - viewCount desc, 3 - commentCount desc
    #   4 - downloadCount desc, ...
    if self.orderType == 0:
      qctx.addOrder("r.resourceId DESC")
    elif self.orderType == 1:
      qctx.addOrder("r.createDate DESC")
    elif self.orderType == 2:
      qctx.addOrder("r.viewCount DESC")
    elif self.orderType == 3:
      qctx.addOrder("r.commentCount DESC")
    elif self.orderType == 4:
      qctx.addOrder("r.downloadCount DESC")



# =======================================================================
# 协作组资源查询.
class GroupResourceQuery(ResourceQuery):
  def __init__(self, selectFields):
    ResourceQuery.__init__(self, selectFields)
    self.cate_svc = __jitar__.categoryService
    self.groupId = None       # int, 限定某个协作组.
    self.isGroupBest = None   # boolean, 是否限定协作组精华.
    self.gresCateId = None    # int, 限定协作组资源分类.
    #发布到资源库
    self.publishToZyk = None
    self.includeChildGroup=False  #是否包括子分组的
    self.gartCateName = None    #分类名    

  def initFromEntities(self, qctx):
    qctx.addEntity("GroupResource", "gr", "gr.resourceId = r.resourceId")
    qctx.addEntity("Resource", "r", "")


  def resolveEntity(self, qctx, entity):
    # 能够关联到协作组 g, 协作组资源分类 grc.
    if "g" == entity:
      qctx.addEntity("Group", "g", "gr.groupId = g.groupId")
    elif "grc" == entity:
      qctx.addJoinEntity("gr", "gr.groupCate", "grc", "LEFT JOIN")
    else:
      ResourceQuery.resolveEntity(self, qctx, entity)


  def applyWhereCondition(self, qctx):
    if self.groupId != None:
      if self.includeChildGroup==False:
        qctx.addAndWhere("gr.groupId = :groupId");
        qctx.setInteger("groupId", self.groupId);
      else:
        qctx.addAndWhere("gr.groupId = :groupId or gr.groupId In(SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE parentId=:parentId)")
        qctx.setInteger("groupId", self.groupId)
        qctx.setInteger("parentId", self.groupId)
    if self.isGroupBest != None:
      qctx.addAndWhere("gr.isGroupBest = :isGroupBest");
      qctx.setBoolean("isGroupBest", self.isGroupBest);
    if self.gartCateName!=None:
      qctx.addAndWhere("gr.groupCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
      qctx.setString("catename", self.gartCateName)
    elif self.gresCateId != None:
      #只查询分类自己的  
      #qctx.addAndWhere("gr.groupCateId = :gresCateId");
      #qctx.setInteger("gresCateId", self.gresCateId);
      #查询自己和子孙分类
      list=self.cate_svc.getCategoryIds(self.gresCateId)
      cateIds=""
      for c in list:
        if cateIds=="":
          cateIds=cateIds+str(c)
        else:
          cateIds=cateIds+","+str(c)
      qctx.addAndWhere("gr.groupCateId IN (" + cateIds + ")")
            
    if self.publishToZyk != None:
      qctx.addAndWhere("r.publishToZyk = :publishToZyk")
      qctx.setInteger("publishToZyk", self.publishToZyk)
    ResourceQuery.applyWhereCondition(self, qctx)
    
  def applyOrderCondition(self, qctx):
    if self.orderType == 0:
      qctx.addOrder("gr.id DESC")
