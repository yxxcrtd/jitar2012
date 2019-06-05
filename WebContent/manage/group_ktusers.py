from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import GroupKTUser
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.service import GroupKTUserService;
from base_action import *

# 本模块全局变量.
group_svc = __jitar__.groupService
cate_svc = __jitar__.categoryService
ktuser_svc = __jitar__.groupKTUserService

class group_ktusers(ActionExecutor,SubjectMixiner):
  def __init__(self):
    ActionExecutor.__init__(self)
    self.params = ParamUtil(request)
    
  # execute() 由 ActionExecutor 实现, 我们只需要实现 dispatcher 即可.
  def dispatcher(self, cmd):
    if self.loginUser == None:
      return ActionResult.LOGIN
    
    # 得到当前课题组.
    if self.getCurrentGroup() == False:
      return ActionResult.ERROR
    
    request.setAttribute("isKtGroup", "1")
    
    self.get_subject_list()
    # 学段分类.
    self.get_grade_list()
        
    if cmd == None or cmd == '': cmd = 'list'
    if cmd == 'list':
      return self.list()
    elif cmd == 'savektuser':
      return self.savektuser()
    elif cmd == 'delete':
      return self.delete()
    elif cmd == 'delete_ktuser':
      return self.deletektuser()
    elif cmd == 'edit_ktuser':
      return self.editktuser()
    elif cmd == 'save':
      return self.savektuserinfo()
    
    self.addActionError(u"未知命令 : " + cmd)
    return ActionResult.ERROR
  
  # 学科列表.
  def get_subject_list(self):
   self.putSubjectList()

  #学段
  def get_grade_list(self):
    self.putGradeList() 
      
  # 列出资源分类列表.
  def list(self):
    #print "ktuser_svc="+str(ktuser_svc)
    ktUserlist= ktuser_svc.GetGroupKTUsers(self.group.groupId);
    request.setAttribute("ktUserlist", ktUserlist)
    return "/WEB-INF/ftl/group/group_ktuser_list.ftl"
  
  def savektuserinfo(self):
    id = self.params.getIntParam('id')
    if id == 0:
      self.addActionError(u"缺少课题负责人id.")
      return ActionResult.ERROR
    ktuser=ktuser_svc.GetGroupKTUser(id)
    if ktuser==None:
      self.addActionError(u"没有找到课题负责人.")
      return ActionResult.ERROR
    ktuser.teacherXZZW=self.params.getStringParam('teacherXZZW')
    ktuser.teacherZYZW=self.params.getStringParam('teacherZYZW')
    ktuser.teacherXL=self.params.getStringParam('teacherXL')
    ktuser.teacherXW=self.params.getStringParam('teacherXW')
    ktuser.teacherYJZC=self.params.getStringParam('teacherYJZC')
    ktuser_svc.UpdateGroupKTUser(ktuser)
    return self.list();

  def deletektuser(self):
    id = self.params.getIntParam('id')
    if id == 0:
      self.addActionError(u"请选择要删除的课题负责人.")
      return ActionResult.ERROR
    ktuser_svc.DeleteGroupKTUser(id);
    return self.list();
    
  def editktuser(self):
    #修改课题负责人
    id = self.params.getIntParam("id");
    if id == 0:
      self.addActionError(u"请选择要修改的课题负责人.")
      return ActionResult.ERROR
    print "id="+str(id)
    ktuser=ktuser_svc.GetGroupKTUser(id)
    request.setAttribute("ktuser", ktuser)
    return "/WEB-INF/ftl/group/group_ktuser_edit.ftl"
    
    
  def delete(self):
    # 得到要删除的对象.
    ids = self.params.safeGetIntValues("ktuId");
    if ids == None or ids.size() == 0:
      self.addActionError(u"没有选择要删除的负责人")
      return ActionResult.ERROR;
    #aid=ids.split(',')
    for id in ids:
      ktuser_svc.DeleteGroupKTUser(int(id));
    #self.addActionMessage(u"课题负责人已经成功删除.")
    #return ActionResult.SUCCESS
    return self.list();
  
  # 得到当前课题组, 课题组标识由页面传递过来.
  # 返回 True 表示有课题组, False 表示没有.
  def getCurrentGroup(self):
    # 得到课题组参数.
    groupId = self.params.getIntParam('groupId')
    if groupId == 0:
      self.addActionError(u"未给出要管理的课题组标识, 请确定您是从有效的链接进入的.")
      return False
    
    # 得到课题组.
    self.group = group_svc.getGroupMayCached(groupId)
    if self.group == None:
      self.addActionError(u"未找到指定标识为 %d 的课题组, 请确定您是从有效的链接进入的." % groupId)
      return False
    request.setAttribute("group", self.group)
    
    # TODO: 验证课题组状态.
    
    # TODO: 得到当前登录用户在课题组的身份.
    
    
    return True
  
  # 设置 GroupResource.groupCateId == category.categoryId 都为 null
  def updateGroupResourceCategoryId(self, category):
    cmd = Command(""" UPDATE GroupResource SET groupCateId = NULL
        WHERE groupId = :groupId AND groupCateId = :groupCateId """)
    cmd.setInteger("groupId", self.group.groupId)
    cmd.setInteger("groupCateId", category.categoryId)
    count = cmd.update()
    