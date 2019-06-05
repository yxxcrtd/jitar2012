from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.data import Pager
from resource_query import ResourceQuery
from base_blog_page import *
from base_action import BaseAction

class user_resource_list(BaseAction, RequestMixiner, ResponseMixiner):
    def execute(self):
        writer = response.getWriter()
        
        self.params = ParamUtil(request)
        loginName = request.getAttribute("loginName")
        if (loginName == None or loginName == ''):
            writer.write("没有该用户。")
            return
        
        # 加载当前用户对象.
        user = __jitar__.userService.getUserByLoginName(loginName)
        request.setAttribute("user", user)
        if self.canVisitUser(user) == False:
            return self.ACCESS_ERROR
        
        # loginUser 对象来自基类 BaseAdminAction .
        request.setAttribute("loginUser", self.loginUser)
        
        # 创建分页对象
        pager = self.params.createPager()
        pager.setPageSize(20)
        qry = ResourceQuery(""" r.resourceId, r.title, r.createDate                              
                            """)
        qry.userId =  user.userId
        #if userStapleId != 0 :
        #    qry.userStapleId = userStapleId
        
        #print "userStapleId = ", userStapleId
        pager.totalRows = qry.count()
        pager.itemName = u"资源"
        pager.itemUnit = u"个"
        qry.orderType = 0
        
        result = qry.query_map(pager)
        request.setAttribute("user_resource_list",result)
        request.setAttribute("pager",pager)
        
        hql = """SELECT new Map(p.skin as skin)
             FROM Page p 
             WHERE p.name = 'index' and p.objId = :userId and p.objType = 1
             """ 
        pageSkin = Command(hql).setInteger("userId", user.userId).first()
        
        # 构造页面数据，由于页面不是在数据库存在的，这里的数据是虚拟数据.
        #pages : [{id: ${page.pageId}, title: '${user.blogName!?js_string}', layoutId: ${page.layoutId!0} }],
        page = {
                "pageId":0,
                "layoutId":2, # 固定是布局2
                "isSystemPage" : "true", 
                "owner" : "user", 
                "title" :"",
                "skin":pageSkin["skin"]
                }        
        request.setAttribute("page", page)
        
        
        # 构造widgets .
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"个人档案","module":"profile", "ico":"", "data":""}
                   ,{"id": "2", "pageId":0, "columnIndex":1,"title":u"资源分类","module":"user_rcate", "ico":"", "data":""}
                  ,{"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        
        request.setAttribute("widgets", widgets)
        request.setAttribute("widget_list", widgets)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/user/default/user_resource_list.ftl"
        
        
    # 解析页面参数.
    def parseUri(self):
      self.uri = self.getRequestURI()
      #print "self.uri =", self.uri
      if self.uri == None or self.uri == "":
        return False
      
      # 例子: /Groups/liujunxing/photo -> 
      #   ['', 'Groups', 'liujunxing', 'photo']
      # 其中最后一个是 photo, 倒数第二个是用户名.
      arr = self.uri.split('/')
      arr_len = len(arr)
      if arr_len < 2:
        return False
      
      # 得到访问的用户名, 并初步验证其合法性.
      self.loginName = arr[arr_len - 2]   # 'admin'
      # print "self.loginName = ", self.loginName
      if isValidName(self.loginName) == False:
        return False
      
      return True