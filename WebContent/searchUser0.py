from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from site_config import SiteConfig
from user_query import UserQuery
from group_query import GroupQuery
from java.lang import *

class searchUser0:  
  cfg_svc = __jitar__.configService

  def __init__(self):
    self.params = ParamUtil(request)  
    return

  def execute(self):
    self.config = self.cfg_svc.getConfigure()
      
    # 用户
    self.userList()
    
    # 机构列表.
    self.get_units_list()

    singleuser = self.params.getIntParam("singleuser")  #1 用户单选 2 用户多选
    if singleuser == None:
      singleuser = 1
      
    inputUser_Id = self.params.getStringParam("inputUser_Id")
    inputUserName_Id = self.params.getStringParam("inputUserName_Id")
    inputLoginName_Id = self.params.getStringParam("inputLoginName_Id")
    request.setAttribute("singleuser", singleuser)
    request.setAttribute("inputUser_Id", inputUser_Id)
    request.setAttribute("inputUserName_Id", inputUserName_Id)
    request.setAttribute("inputLoginName_Id", inputLoginName_Id)
    # 返回
    response.contentType = "text/html; charset=UTF-8"
    return "/WEB-INF/ftl/searchUserList0.ftl"

  def userList(self):
    unitId = self.params.getIntParamZeroAsNull("unitId")
    userName = self.params.getStringParam("userName")
    qry = UserQuery2(" u.userId, u.loginName, u.userIcon, u.gender, u.blogName, u.trueName, u.unitId,  unit.unitName, unit.unitTitle ")
    qry.userStatus = 0
    qry.kk = userName
    pager = self.createPager()
    pager.totalRows = qry.count()
    user_list = qry.query_map(pager) 
    request.setAttribute("unitId", unitId)
    request.setAttribute("user_list", user_list)
    request.setAttribute("userName", userName)
    request.setAttribute("pager", pager)

  # 机构列表.
  def get_units_list(self):
    hql = """SELECT new Map(ut.unitId as unitId, ut.unitTitle as unitTitle) 
         FROM Unit ut 
         ORDER BY ut.unitName """     
    unit_list = Command(hql).open()
    request.setAttribute("unit_list", unit_list)

  def createPager(self):
    # private 构造人员的缺省 pager.
    pager = self.params.createPager()
    pager.itemName = u"用户"
    pager.itemUnit = u"个"
    pager.pageSize = 10
    return pager

class UserQuery2(UserQuery):
  def __init__(self, select):
    UserQuery.__init__(self, select)
    self.kk = None
    self.ff = 'name'
    self.isAdmin = None     # 是否查找用户管理员.
    self.isCensor = None    # 是否查找内容管理员.
    self.isCommon = True    # 是否查找非删除人员
    
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
      
  def applyWhereCondition(self, qctx):
    UserQuery.applyWhereCondition(self, qctx)
    if self.kk != None and self.kk != '':
      newKey = self.kk.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
      if self.ff == "name":
        qctx.addAndWhere("u.loginName LIKE :keyword OR u.nickName LIKE :keyword OR u.trueName LIKE :keyword")
        qctx.setString("keyword", "%" + newKey + "%")
