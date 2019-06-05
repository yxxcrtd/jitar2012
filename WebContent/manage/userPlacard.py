from base_action import *
from cn.edustar.jitar.model import ObjectType
from placard_query import PlacardQuery
from base_action import ActionExecutor, ActionResult
from cn.edustar.jitar.pojos import Placard
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.util import PageContent
from cn.edustar.jitar.util import FileCache


class userPlacard (ActionExecutor):
  def __init__(self):
    self.params = ParamUtil(request)
    #self.pla_svc = __spring__.getBean("placardService")
    self.pla_svc = __jitar__.placardService
    self.categoryService = __jitar__.categoryService
    self.login_user = self.getLoginUser()

  def dispatcher(self, cmd):
    if self.getLoginUser() == None:
      self.addActionError(u"请先登录")
      return ActionResult.ERROR
    if cmd == "" or cmd == None: cmd = "list"    
    if cmd == "list":
      return self.list()
    elif cmd == "addPlacard":
      return self.add()
    elif cmd == "edit_placard":
      return self.edit()
    elif cmd == "save_placard":
      return self.save()
    
    elif cmd == "show":
      return self.show()
    elif cmd == "hide":
      return self.hide()
    elif cmd == "del_placard":
      return self.delete()
  
  #对公告的状态设置为显示状态  
  def show(self):
    
    ids = self.params.safeGetIntValues("placard")
    if ids == None or len(ids) == 0:
      self.addActionError(u"未选择要显示的公告")
      return ActionResult.ERROR
  
    oper_count = 0;
    for id in ids :
      placard = self.pla_svc.getPlacard(id)
      if placard == None :
        self.addActionError(u"未找到标识为" + str(id) + u"的公告")
        continue
        
      #权限验证
      if placard.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId() or placard.getObjId() != self.login_user.getUserId():
        self.addActionError(u"权限不足,不能操作他人的公告")
        continue
        
      self.pla_svc.showPlacard(placard)
      self.addActionMessage(u"公告 " + placard.getTitle() + u" 已显示")
      oper_count = oper_count + 1
      
    self.addActionMessage(u"共显示了" + str(oper_count) + u"条公告")
    return ActionResult.SUCCESS

  #对公告的状态设置为隐藏状态  
  def hide(self):
    
    ids = self.params.safeGetIntValues("placard")
    if ids == None or len(ids) == 0:
      self.addActionError(u"未选择要隐藏的公告")
      return ActionResult.ERROR
  
    oper_count = 0;
    for id in ids :
      placard = self.pla_svc.getPlacard(id)
      if placard == None :
        self.addActionError(u"未找到标识为" + str(id) + u"的公告")
        continue
        
      #权限验证
      if placard.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId() or placard.getObjId() != self.login_user.getUserId():
        self.addActionError(u"权限不足,不能操作他人的公告")
        continue
        
      self.pla_svc.hidePlacard(placard)
      self.addActionMessage(u"公告 " + placard.getTitle() + u" 已隐藏")
      oper_count = oper_count + 1
      
    self.addActionMessage(u"共隐藏了" + str(oper_count) + u"条公告")
    return ActionResult.SUCCESS
  
  #删除公告  
  def delete(self):

    ids = self.params.safeGetIntValues("placard")
    if ids == None or len(ids) == 0:
      self.addActionError(u"未选择要删除的公告")
      return ActionResult.ERROR
  
    oper_count = 0
    for id in ids:
      placard = self.pla_svc.getPlacard(id)
      if placard == None:
        self.addActionError(u"未找到标识为 " + str(id) + u" 的公告")
        continue 
        
      #权限验证.
      if placard.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId() or placard.getObjId() != self.getLoginUser().getUserId():
        self.addActionError(u"权限不足,不能删除他(她)人的公告")
        continue
         
      #删除公告.
      self.pla_svc.deletePlacard(placard)
      self.addActionMessage(u"公告 " + placard.getTitle() + u" 已删除")
      oper_count = oper_count + 1
      
    self.addActionMessage(u"共删除了 " + str(oper_count) + u" 条公告")
    return ActionResult.SUCCESS      
  
  #编辑公告
  def edit(self):

    placardId = self.params.getIntParam("placardId")
    #print placardId
    placard = self.pla_svc.getPlacard(placardId)
    if placard == None:
      self.addActionError(u"未找到标识为 " + str(placardId) + u"的公告")
      return  ActionResult.ERROR
  
    #权限验证
    if placard.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId() or placard.getObjId() != self.getLoginUser().getUserId():
      self.addActionError(u"权限不足,不能编辑他人的公告")
      return ActionResult.ERROR
  
    request.setAttribute("placardId", placardId)
    request.setAttribute("placard", placard)
    request.setAttribute("__referer", request.getHeader("Referer"))
    return "/WEB-INF/ftl/placard/placard_add_edit.ftl"
    
  #对增加或修改的公告进行保存
  def save(self):
      
    out = response.getWriter()
    #得到数据
    placardId = self.params.getIntParam("placardId")
    #权限验证
    if placardId > 0:
      placard = self.pla_svc.getPlacard(placardId)
      if placard.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId() or placard.getObjId() != self.getLoginUser().getUserId(): 
        self.addActionError(u"权限不足,不能编辑他人的公告")
        return ActionResult.ERROR
    
    redirect = self.params.safeGetStringParam("redirect")
    title = self.params.safeGetStringParam("title")
    content = self.params.safeGetStringParam("placardContent")
    #验证数据
    if title == None or title == "":
      out.append(PageContent.PAGE_UTF8)
      out.println(u"<script>alert('   公告标题不能为空！');window.history.go(-1);</script>")
      return
    if title != None and len(title) > 255:
      out.append(PageContent.PAGE_UTF8)
      out.println(u"<script>alert('   公告标题长度不能大于255！');window.history.go(-1);</script>")
      return
    if content == None and content.length() == 0:
      out.append(PageContent.PAGE_UTF8)
      out.println(u"<script>alert('   公告内容不能为空！');window.history.go(-1);</script>")
      return
  
    #给bean赋值.
    placard = Placard()
    login_user = self.getLoginUser()
    placard.setId(placardId)
    placard.setTitle(title)
    placard.setContent(content)
    placard.setObjType(ObjectType.OBJECT_TYPE_USER.getTypeId())
    placard.setObjId(login_user.getUserId())
    #保存公告.
    self.pla_svc.savePlacard(placard)
    
    #redirect:页面返回标识.
    if "" != redirect:
      response.sendRedirect(super.getRefererHeader())
      return ActionResult.NONE
  
    self.addActionMessage(u"公告 '" + placard.getTitle() + u"' 已保存")
    return ActionResult.SUCCESS

  #显示公告列表   
  def list(self):
    # print "list is executed, loginUesr = ", self.loginUser
    if self.loginUser == None:
      return "/WEB-INF/ftl/placard/placard_list.ftl"
    qry = PlacardQuery(""" pld.id, pld.title, pld.createDate, pld.hide """)
    qry.objType = ObjectType.OBJECT_TYPE_USER.typeId
    qry.objId = self.loginUser.userId
    qry.hideState = None
    placard_list = qry.query_map(10)    
    request.setAttribute("placard_list", placard_list)
    
    fc = FileCache()
    fc.deleteUserAllCache(self.loginUser.loginName)
    fc = None
    return "/WEB-INF/ftl/placard/placard_list.ftl"
  
  #增加公告
  def add(self):
    
    placard = Placard()
    request.setAttribute("placard", placard)
    request.setAttribute("__referer", request.getHeader("Referer"))
    
    return "/WEB-INF/ftl/placard/placard_add_edit.ftl"
