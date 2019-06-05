#encoding=utf-8
from cn.edustar.jitar.data import BaseQuery
from base_action import *

# 下级推送文章查询
class MultiUnitArticleQuery (BaseQuery, SubjectMixiner):
    # 排序方式.
    ORDER_TYPE_ID_DESC = 0
    ORDER_TYPE_ARTICLEID_DESC = 0
    ORDER_TYPE_CREATEDATE_DESC = 1
    ORDER_TYPE_VIEWCOUNT_DESC = 2
    ORDER_TYPE_COMMENTCOUNT_DESC = 3
    
    # 显示数据的方方式
    SHOW_OWN_PUSHUP = 0           #本机构已经推送的
    SHOW_OWN_UNPUSHUP = 1         #本机构未推送给上级的
    SHOW_PUSH_NOT_APPROVED = 3    #下级机构推送给自己的，但还没有通过审核
    SHOW_PUSH_APPROVED = 4        #下级机构推送给自己的，并且经过审核的

    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
    
        self.subjectId = self.params.getIntParamZeroAsNull("subjectId")
        # 新闻所属学段
        self.gradeId = self.params.getIntParamZeroAsNull("gradeId")    
        self.userIsFamous = None
        self.unit = None
        
        # 是否精华，缺省为 null 表示所有文章; = true 表示获取精华的; = false 表示获取非精华的.
        self.bestState = None
        # 排序方式, 0 - 按照 id desc, 1 - createDate desc, 2 - viewCount desc, 3 - commentCount desc
        self.orderType = 0        
        self.userId = self.params.getIntParamZeroAsNull("userId")           # 用户筛选.
        self.sysCateId = self.params.getIntParamZeroAsNull("categoryId")        # 文章文章所属系统分类.
        
        self.k = self.params.getStringParam("k")          # 关键字
        self.f = self.params.getStringParam("f")          # 查询的字段
        if self.f == None:
          self.f = "title"
        if self.f == "":
          self.f = "title"
          
        request.setAttribute("categoryId", self.sysCateId)
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
        qctx.addAndWhere("a.auditState = 0")
        qctx.addAndWhere("a.hideState = 0")
        qctx.addAndWhere("a.draftState = 0")
        qctx.addAndWhere("a.delState = 0")
        
        qctx.addAndWhere("a.unitId <> :unitId")
        qctx.setInteger("unitId", self.unit.unitId)
        qctx.addAndWhere("a.unitPathInfo LIKE :unitPathInfo")
        qctx.setString("unitPathInfo", "%/" + str(self.unit.unitId) + "/%")
        if self.k !=None and self.k != "":
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            if self.f == "title":
                qctx.addAndWhere("a.title LIKE :title")
                qctx.setString("title", "%" + newKey + "%")
            if self.f == "uname":
                qctx.addAndWhere("(u.loginName LIKE :uname1 or u.trueName LIKE :uname2)")
                qctx.setString("uname1", "%" + newKey + "%")
                qctx.setString("uname2", "%" + newKey + "%")

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
