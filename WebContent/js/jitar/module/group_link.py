# script for 协作组链接.

from cn.edustar.jitar.data import Command

OBJECT_TYPE_GROUP = 2

# 显示协作组链接.
class group_link:
  
  def __init__(self):
    self.group_svc = __jitar__.groupService
    
  def execute(self):
    #print "group_link module is execute"
    
    # 得到协作组参数.
    groupId = request.getParameter("groupId")
    if groupId == None or groupId == "":
      return self.notFound()
    
    try:
      groupId = int(groupId)      # may raise ValueError
    except ValueError:
      return self.notFound()
    
    group = self.group_svc.getGroupMayCached(groupId)
    if group == None:
      return self.notFound()
    
    #print "group = ", group
    request.setAttribute("group", group)

    # 得到该协作组的链接.
    self.get_links(group)
    
    response.contentType = "text/html; charset=UTF-8"
    return "/js/jitar/module/group_link.ftl"
    
  # 得到当前协作组链接.
  def get_links(self, group):
    hql = """ FROM Link WHERE objectType = :objectType AND objectId = :objectId 
          ORDER BY linkId DESC """
    cmd = Command(hql)
    cmd.setInteger("objectType", OBJECT_TYPE_GROUP) 
    cmd.setInteger("objectId", group.groupId)
    # 取出最新 5 个友情链接.
    link_list = cmd.open(5)
    request.setAttribute("link_list", link_list)
    return
  
  def notFound(self):
    response.contentType = "text/html; charset=UTF-8"
    out = response.writer
    out.write("未找到该协作组或参数不正确")
    return None
