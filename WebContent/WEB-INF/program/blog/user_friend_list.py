from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from base_blog_page import *
from com.alibaba.fastjson import JSONObject
from base_action import BaseAction
from friend_query import FriendQuery

user_svc = __jitar__.userService

# 显示用户好友列表.
class user_friend_list(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
    
    def execute(self):
        self.loginName = request.getAttribute("loginName")
        # 解析 uri.
        if self.parseUri() == False:
            return self.sendNotFound(self.uri)
          
        writer = response.getWriter()
        
        # 加载当前用户对象.
        self.user = user_svc.getUserByLoginName(self.loginName)
        request.setAttribute("user", self.user)
        #print "user = ", self.user
        if self.canVisitUser(self.user) == False:
            return self.ACCESS_ERROR
            
        # loginUser 对象来自基类 BaseAction.
        request.setAttribute("loginUser", self.loginUser)
        
        pager = self.params.createPager()  
        qry = FriendQuery(""" u.loginName, u.nickName, u.trueName, u.userIcon, u.qq, frd.addTime """)
        qry.userId = self.user.userId
        qry.isBlack = False
        qry.orderType = 0 
        pager.setPageSize(16)        
        pager.itemName = u"好友"
        pager.itemUnit = u"位"        
        pager.totalRows = qry.count()
        friend_list = qry.query_map(pager)
        
        #print "friend_list = " , friend_list
        request.setAttribute("friend_list", friend_list)        
        request.setAttribute("pager",pager)        
        hql = """SELECT p.skin
             FROM Page p 
             WHERE p.name = 'index' and p.objId = :userId and p.objType = 1
             """ 
        pageSkin = Command(hql).setInteger("userId", self.user.userId).scalar()
        #print "pageSkin = ", pageSkin
        if pageSkin == None or pageSkin == "": pageSkin = "skin1"
        
        # 构造页面数据，由于页面不是在数据库存在的，这里的数据是虚拟数据.
        #pages : [{id: ${page.pageId}, title: '${self.user.blogName!?js_string}', layoutId: ${page.layoutId!0} }],
        page = {
                "pageId": 0,
                "layoutId": 2, # 固定是布局2
                "isSystemPage" : "true", 
                "owner" : "user", 
                "title" :"",
                "skin": pageSkin
                }        
        request.setAttribute("page", page)
        self.page = self.getUserProfilePage(self.user)
        if self.page.customSkin != None:
            customSkin = JSONObject.parse(self.page.customSkin)
            request.setAttribute("customSkin", customSkin)
        # 构造widgets .
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"个人档案","module":"profile", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        request.setAttribute("widgets", widgets)
        request.setAttribute("widget_list", widgets)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/user/default/user_friends.ftl"


    # 解析页面参数.
    def parseUri(self):
      self.uri = self.getRequestURI()
      #print "self.uri =", self.uri
      if self.uri == None or self.uri == "":
        return False
      
      # 例子: /Groups/liujunxing/friend -> 
      #   ['', 'Groups', 'liujunxing', 'friend']
      # 其中最后一个是 friend, 倒数第二个是用户名.
      arr = self.uri.split('/')
      arr_len = len(arr)
      if arr_len < 2:
        return False
      return True