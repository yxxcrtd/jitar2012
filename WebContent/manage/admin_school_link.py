# script

from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Link
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.jython import BaseAdminAction

# 结果定义.
class ActionResult:
  ERROR = "/WEB-INF/ftl/Error.ftl"
  SUCCESS = "/WEB-INF/ftl/success.ftl"

# 机构风采管理.
class admin_school_link (BaseAdminAction, ActionResult) :
  # 常量 机构风采系统类型 = 100.
  ObjectType_System = 100
  # 常量 机构风采标识 = 1 .
  ObjectId_School = 1
   
  def __init__(self):
    self.link_svc = __spring__.getBean("linkService")
    self.params = ParamUtil(request)
    
    return


  # 执行请求.
  def execute(self):
    #print "hello, admin_school_link is executed"
    
    # 必须要求登录和具有管理权限.
    if self.loginUser == None: return ActionResult.LOGIN
    if self.canAdmin() == False:
      self.addActionError(u"没有管理机构链接的权限.")
      return ActionResult.ERROR
     
    cmd = self.params.safeGetStringParam("cmd")
    if cmd == "" or cmd == None :
      cmd = "list"

    if cmd == "list" :
      return self.list()
    elif cmd == "add" :
      return self.add()
    elif cmd == "edit":
      return self.edit()
    elif cmd == "save":
      return self.save()
    elif cmd == "delete":
      return self.delete()
    else:
      self.addActionError(u"未知命令参数 : " + cmd)
      return ActionResult.ERROR
  

  # 按照分页列出链接.
  def list(self):
    pager = self.params.createPager()
    pager.itemName = u"机构链接"
    pager.itemUnit = u"个"

    # 计算总数.    
    hql = """ SELECT COUNT(*) FROM Link 
         WHERE objectType = :objectType AND objectId = :objectId """
    cmd = Command(hql)
    cmd.setInteger("objectType", self.ObjectType_System)
    cmd.setInteger("objectId", self.ObjectId_School)
    pager.totalRows = cmd.int_scalar()
    
    # 获取当前页.
    hql = """ FROM Link 
         WHERE objectType = :objectType AND objectId = :objectId 
         ORDER BY linkId DESC """
    cmd = Command(hql)
    cmd.setInteger("objectType", self.ObjectType_System)
    cmd.setInteger("objectId", self.ObjectId_School)
    link_list = cmd.open(pager)
    
    request.setAttribute("link_list", link_list)
    request.setAttribute("pager", pager)
    
    #DEBUG: print "link_list = ", link_list
    return "/WEB-INF/ftl/admin/school_link_list.ftl"

  # 增加一个链接.
  def add(self):
    link = Link()
    link.linkAddress = "http://"
    link.linkType = 1
    request.setAttribute("link", link)
    
    return "/WEB-INF/ftl/admin/school_link_add.ftl"

  # 编辑一个现有链接.
  def edit(self):
    linkId = self.params.getIntParam("linkId") 
    link = self.link_svc.getLinkById(linkId)
    if link == None:
      self.addActionError(u"无法找到指定标识的机构风采链接对象")
      return ActionResult.ERROR
  
    request.setAttribute("link", link)
    
    return "/WEB-INF/ftl/admin/school_link_add.ftl"
  
  # 保存链接.  
  def save(self):
    link = self.collect()
    
    self.link_svc.saveLink(link)
    
    self.addActionMessage(u"保存链接 " + link.toDisplayString() + u"成功完成")

    return ActionResult.SUCCESS

  def delete(self):
    ids = self.params.getIdList("linkId")
    if ids == None or ids.size() == 0:
      self.addActionError(u"未选择任何要操作的链接")
      return ActionResult.ERROR
    
    oper_count = 0
    for linkId in ids:
      # 得到链接对象.
      link = self.link_svc.getLinkById(linkId)
      if link == None:
        self.addActionMessage(u"未能找到指定标识为 " + str(linkId) + u" 的机构链接")
        continue
    
      # TODO: 验证权限.
      
      # 实施删除.
      self.link_svc.deleteLink(link)
      self.addActionMessage(u"链接 " + link.toDisplayString() + u" 已经成功删除")
      oper_count += 1
      
    self.addActionMessage(u"共删除了 " + str(oper_count) + u" 个链接")

    return ActionResult.SUCCESS
    
  # 从页面提交参数收集 Link 对象.
  def collect(self):
    link = Link()
    link.linkId = self.params.getIntParam("linkId")
    # link.objectType = self.params.getIntParam("objectType")
    link.objectType = self.ObjectType_System
    # link.objectId = self.params.getIntParam("objectId")
    link.objectId = self.ObjectId_School
    link.title = self.params.getStringParam("title")
    link.linkAddress = self.params.getStringParam("linkAddress")
    link.linkType = self.params.getIntParam("linkType")
    link.description = self.params.getStringParam("description")
    link.linkIcon = self.params.getStringParam("linkIcon")
    return link;