from cn.edustar.jitar.pojos import Group
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from base_action import *
from group_query import GroupQuery
from user_query import UserQuery

class searchUser1:
  cfg_svc = __jitar__.configService

  def __init__(self):
    self.params = ParamUtil(request)  
    return

  def execute(self):
    self.config = self.cfg_svc.getConfigure()
      
    # 群组
    self.groupList()
    user_list=None
    singleuser= self.params.getIntParam("singleuser")  #1 用户单选 2 用户多选
    if singleuser==None:
      singleuser=1
    
    closewindow  =self.params.getIntParam("closewindow")
    if closewindow==None:
      closewindow=0
    groupids=self.params.getStringParam("groupids")
    if groupids==None:
      closewindow=0
    else:
      if closewindow==1:
        hql = """SELECT new Map(u.userId as userId,u.gender, u.loginName as loginName, u.trueName as trueName) FROM User u Where u.userId In(Select gm.userId from GroupMember gm  Where gm.groupId In(""" + groupids +"""))""" 
        user_list = Command(hql).open()
    
    request.setAttribute("closewindow", closewindow)
    request.setAttribute("user_list", user_list)
    inputUser_Id= self.params.getStringParam("inputUser_Id")
    inputUserName_Id= self.params.getStringParam("inputUserName_Id")
    inputLoginName_Id = self.params.getStringParam("inputLoginName_Id")
    request.setAttribute("singleuser", singleuser)
    request.setAttribute("inputUser_Id", inputUser_Id)
    request.setAttribute("inputUserName_Id", inputUserName_Id)
    request.setAttribute("inputLoginName_Id", inputLoginName_Id)
    # 返回
    response.contentType = "text/html; charset=UTF-8"
    return "/WEB-INF/ftl/searchUserList1.ftl"

  # 列出协作组.
  def groupList(self):
    # 构造查询对象.
    qry = GroupQuery(""" g.groupId, g.groupName, g.groupTitle, g.groupIcon, g.createDate, g.subjectId, g.gradeId, 
          g.createUserId, g.groupTags, g.groupIntroduce, g.groupState, g.userCount, g.visitCount, 
          g.isBestGroup, g.isRecommend, u.trueName, u.loginName, subj.subjectName, sc.name as categoryName 
          """)
    qry.groupState = 0
    
    qry.kk = self.params.getStringParam("k")
    request.setAttribute("k", qry.kk)
    
    # 构造分页并查询数据.
    pager = self.createPager()
    pager.totalRows = qry.count()
    group_list = qry.query_map(pager)
    
    request.setAttribute("group_list", group_list)
    request.setAttribute("pager", pager)
    
  # 创建协作组管理所用的缺省分页对象.
  def createPager(self):
    pager = self.params.createPager()
    pager.itemName = u"协作组"
    pager.itemUnit = u"个"
    pager.pageSize = 10
    return pager

