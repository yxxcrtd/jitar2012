# script for 协作组链接.

from cn.edustar.jitar.data import Command

OBJECT_TYPE_GROUP = 2

# 显示下级协作组链接.
class group_children:
  
  def __init__(self):
    self.group_svc = __jitar__.groupService
    
  def execute(self):
    #print "group_children module is execute"
    
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

    self.get_childs(group)
    
    response.contentType = "text/html; charset=UTF-8"
    return "/js/jitar/module/group_children.ftl"
    
  # 得到当前协作组链接.
  def get_childs(self, group):
    hql = """ FROM Group WHERE parentId = :parentId ORDER BY id DESC """
    cmd = Command(hql)
    cmd.setInteger("parentId", group.groupId)
    group_list = cmd.open(-1)
    request.setAttribute("group_list", group_list)
    return
  
  def notFound(self):
    response.contentType = "text/html; charset=UTF-8"
    out = response.writer
    out.write("未找到该协作组或参数不正确")
    return None
