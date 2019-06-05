from base_action import ActionExecutor,ActionResult
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from java.net import URLEncoder

class Favorite(ActionExecutor):
  def __init__(self):
    self.favo_svc =__spring__.getBean("UFavoritesService")
    self.params = ParamUtil(request)
    self.login_user = self.getLoginUser()
    self.writer = response.getWriter()
    return
    # 从 ActionExecutor 调用, 根据 cmd 进行不同处理.
  def dispatcher(self, cmd):
    if cmd == None or cmd == "" : cmd = "list"
    if cmd == "list":
      return self.list()
    if cmd == "add":
      return self.save()
    if cmd == "del":
      return self.Del()
  
  def Del(self):
    favId = self.params.getIntParam("favId")
    if favId!=None:
      self.favo_svc.Del(favId);
    return self.list()

  def list(self):
    if self.login_user == None:
      self.writer.write("logon")
      return
    favUser=self.login_user.userId;
    fav_list=self.favo_svc.getUFavoritesList(favUser);
    pager = self.createPager()
    if fav_list==None:
      pager.totalRows=0
    else:  
      pager.totalRows = fav_list.size()
    
    hql=""" from UFavorites Where favUser=:favUser ORDER BY favId DESC"""
    cmd = Command(hql)
    cmd.setInteger("favUser", favUser)
    fav_list = cmd.open(pager)
    request.setAttribute("pager", pager)
    request.setAttribute("fav_list", fav_list)
    return "/WEB-INF/ftl/user/fav_list.ftl"
    
  def save(self):
    if self.login_user == None:
      self.writer.write("logon")
      return
    favUser=self.login_user.userId;
    objectId = self.params.getIntParam("objectId")
    objectType = self.params.getIntParam("objectType")
    objectUuid = self.params.getStringParam("objectUuid")
    favHref= self.params.getStringParam("url")
    favTitle= self.params.getStringParam("title")
    #encodeTitle = URLEncoder.encode(favTitle, "UTF-8")
    favTypeID=0
    favInfo=""
    b=self.favo_svc.Exists(favUser,objectType,objectId,favHref)
    if b==True:
      self.writer.write("exist")
      return
    self.favo_svc.Save(favUser, objectType, objectUuid, objectId, favTitle, favInfo, favTypeID, favHref)
    self.writer.write("ok")
    return

  def createPager(self):
    pager = self.params.createPager()
    pager.itemName = u"收藏"
    pager.itemUnit = u"条"
    pager.pageSize = 25
    return pager