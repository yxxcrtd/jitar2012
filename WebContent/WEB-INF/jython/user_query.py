# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery
from base_action import SubjectMixiner
from cn.edustar.jitar.util import ParamUtil

# 用户信息查询实现.
class UserQuery (BaseQuery, SubjectMixiner):
  ORDER_TYPE_USERID_DESC = 0
  ORDER_TYPE_VISITCOUNT_DESC = 1
  ORDER_TYPE_ARTICLECOUNT_DESC = 2
  ORDER_TYPE_RESOURCE_COUNT_DESC = 4
  ORDER_TYPE_SCORE_DESC = 6
  ORDER_TYPE_RANDOM = 100
  
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.params = ParamUtil(request)
    self.loginName = None			# 查询某个指定用户的时候使用.
    self.userStatus = None
    self.subjectId = None         # 所属学科, == null 表示不限制.
    self.gradeId = None           # 所属学段, == null 表示不限制.
    self.bmd = None               #  
    self.role = None
    self.pushState = None
    self.unit = None    
    # 模糊匹配
    self.FuzzyMatch = False
    
    #自定义过滤条件
    self.custormAndWhere = None
    
    #按用户类型，如名称、教研员查询
    self.userTypeId = None
    
    self.metaSubjectId = self.params.getIntParamZeroAsNull('subjectId')     # 所属元学科, == null 表示不限制.
    self.metaGradeId = self.params.getIntParamZeroAsNull('gradeId')       # 所属元学段, == null 表示不限制.  
    self.unitId = self.params.getIntParamZeroAsNull('unitId')            # 所属机构, == null 表示不限制.
    self.sysCateId = self.params.getIntParamZeroAsNull('categoryId')          # 工作室分类, == null 表示不限制.
    self.orderType = 0            # 0 - userId desc, 1 - visitCount desc, 2 - articleCount desc
    # 3 - userScore desc (unimpl), 4 - resourceCount desc
    self.k = self.params.getStringParam("k")         # 简单关键字, loginName, nickName, blogName, tags 查询.
    self.f = self.params.getStringParam("f")         # 查找类型 name,email,tags,intro,unit
    request.setAttribute("f", self.f)
    request.setAttribute("k", self.k)
    
    self.role = self.params.getIntParamZeroAsNull("role")
    
    request.setAttribute("subjectId", self.metaSubjectId)
    request.setAttribute("gradeId", self.metaGradeId)
    request.setAttribute("unitId", self.unitId)
    request.setAttribute("categoryId", self.sysCateId)
    request.setAttribute("role", self.role)
    
        
  def initFromEntities(self, qctx):
    qctx.addEntity("User", "u", "")
    
  def resolveEntity(self, qctx, entity):
    if entity == "subj":
      qctx.addJoinEntity("u", "u.subject", "subj", "LEFT JOIN")
    elif entity == "grad":
      qctx.addJoinEntity("u", "u.grade", "grad", "LEFT JOIN")
    elif entity == "unit":
      qctx.addJoinEntity("u", "u.unit", "unit", "LEFT JOIN")
    elif entity == "sc":
      qctx.addJoinEntity("u", "u.sysCate", "sc", "LEFT JOIN")
    else:  
      BaseQuery.resolveEntity(self, qctx, entity)

  # 提供 where 条件.
  def applyWhereCondition(self, qctx):
    if self.loginName != None:
      qctx.addAndWhere("u.loginName = :loginName")
      qctx.setString("loginName", self.loginName)
    
    if self.custormAndWhere != None:
      qctx.addAndWhere(self.custormAndWhere)
      
    if self.userStatus != None:
      qctx.addAndWhere("u.userStatus = :userStatus")
      qctx.setInteger("userStatus", self.userStatus)
    if self.role != None:
      qctx.addAndWhere("u.positionId = :role")
      qctx.setInteger("role", self.role)
      
    if self.subjectId != None:
      qctx.addAndWhere("u.subjectId = :subjectId")
      qctx.setInteger("subjectId", self.subjectId)
      #print "UserQuery.subjectId current is not supported, please check code"
    
    if self.metaSubjectId != None:
      qctx.addAndWhere("u.subjectId = :subjectId")
      qctx.setInteger("subjectId", self.metaSubjectId)    
    
    if self.gradeId != None:
      if self.FuzzyMatch == False:
        qctx.addAndWhere("u.gradeId = :gradeId")
        qctx.setInteger("gradeId", self.gradeId)
      else:
        qctx.addAndWhere("u.gradeId >= :gradeStartId AND u.gradeId < :gradeEndId")
        qctx.setInteger("gradeStartId", self.calcGradeStartId(self.metaGradeId))
        qctx.setInteger("gradeEndId", self.calcGradeEndId(self.metaGradeId))
        
    if self.metaGradeId != None:
      if self.FuzzyMatch == False:
        qctx.addAndWhere("u.gradeId = :gradeId")
        qctx.setInteger("gradeId", self.metaGradeId)
      else:
        qctx.addAndWhere("u.gradeId >= :gradeStartId AND u.gradeId < :gradeEndId")
        qctx.setInteger("gradeStartId", self.calcGradeStartId(self.metaGradeId))
        qctx.setInteger("gradeEndId", self.calcGradeEndId(self.metaGradeId))
    
    if self.pushState != None:
      qctx.addAndWhere("u.pushState = :pushState")
      qctx.setInteger("pushState", self.pushState)
    if self.userTypeId != None:
      qctx.addAndWhere("u.userType LIKE :userType")
      qctx.setString("userType", "%/" + str(self.userTypeId) + "/%")

    #if self.bmd != None:
    #  if self.metaGradeId != None:
    #    qctx.addAndWhere("((u.gradeId >= :gradeStartId AND u.gradeId < :gradeEndId) or (u.gradeId=null) or (u.gradeId=0))")
    #    qctx.setInteger("gradeStartId", self.calcGradeStartId(self.metaGradeId))
    #    qctx.setInteger("gradeEndId", self.calcGradeEndId(self.metaGradeId))
    #else:
    #  if self.metaGradeId != None:
    #    qctx.addAndWhere("u.gradeId = :gradeId")
    #    qctx.setInteger("gradeId", self.metaGradeId)
        #qctx.addAndWhere("u.gradeId >= :gradeStartId AND u.gradeId < :gradeEndId")
        #qctx.setInteger("gradeStartId", self.calcGradeStartId(self.metaGradeId))
        #qctx.setInteger("gradeEndId", self.calcGradeEndId(self.metaGradeId))
      
    if self.unitId != None:
      qctx.addAndWhere("u.unitId = :unitId")
      qctx.setInteger("unitId", self.unitId)
      
    if self.sysCateId != None:
      qctx.addAndWhere("u.categoryId = :sysCateId")
      qctx.setInteger("sysCateId", self.sysCateId)
    # 有查询条件
    if self.k != None and self.k != "":
      newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
      if self.f == 'email':
        qctx.addAndWhere("u.email LIKE :keyword")
      elif self.f == 'trueName':
        qctx.addAndWhere("u.trueName LIKE :keyword")
      elif self.f == 'tags':
        qctx.addAndWhere("u.userTags LIKE :keyword")
      elif self.f == 'intro':
        qctx.addAndWhere("u.blogName LIKE :keyword OR u.blogIntroduce LIKE :keyword")
      elif self.f == 'unit':
        qctx.addAndWhere("unit.unitName LIKE :keyword")
      elif self.f == 'unitTitle':
        qctx.addAndWhere("unit.unitTitle LIKE :keyword")
      elif self.f == 'loginName':
        qctx.addAndWhere("u.loginName LIKE :keyword")
      elif self.f == "name":
        qctx.addAndWhere("u.userId LIKE :keyword OR u.loginName LIKE :keyword OR u.nickName LIKE :keyword OR u.trueName LIKE :keyword")
      else :  
        qctx.addAndWhere("u.userId LIKE :keyword OR u.loginName LIKE :keyword OR u.nickName LIKE :keyword OR u.trueName LIKE :keyword OR u.blogName LIKE :keyword OR u.userTags LIKE :keyword OR u.blogIntroduce LIKE :keyword")
      qctx.setString("keyword", "%" + newKey + "%")

  # 提供排序 order 条件.
  def applyOrderCondition(self, qctx):
    if self.orderType == 0:
      qctx.addOrder("u.userId DESC")
    elif self.orderType == 1:
      qctx.addOrder("u.visitCount DESC")
    elif self.orderType == 2:
      qctx.addOrder("u.articleCount DESC")
    elif self.orderType == 4:
      qctx.addOrder("u.resourceCount DESC")
    elif self.orderType == 5:
      qctx.addOrder("u.userId ASC")
    elif self.orderType == 6:
      qctx.addOrder("u.userScore desc")
    elif self.orderType == 100:
      qctx.addOrder("newid()")