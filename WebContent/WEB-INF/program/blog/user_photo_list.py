from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import PhotoStaple
from cn.edustar.data import Pager
from photo_query import PhotoQuery
from base_blog_page import *
from com.alibaba.fastjson import JSONObject
from base_action import BaseAction

# 显示用户的图片列表.
class user_photo_list(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def execute(self):
        #需要添加到内容：当前用户的首页skin，当前用户的用户对象，当前用户是否登录过.       
        writer = response.getWriter()
        
        photoStapleService = __jitar__.getPhotoStapleService()     
        
        self.params = ParamUtil(request)
        userStapleId = self.params.safeGetIntParam("userStapleId")
        loginName = request.getAttribute("loginName")
        if (loginName == None or loginName == ''):
            writer.println(u"没有该用户。")
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
        pager.setPageSize(6)
        qry = PhotoQuery(""" p.photoId, p.title, p.createDate, p.lastModified, p.href, p.commentCount,p.viewCount, p.userStaple, 
                           u.nickName, u.loginName, u.userIcon, stap.title as stapleTitle                                       
                            """)
        qry.userId = user.userId
        qry.isPrivateShow = None
        if userStapleId != 0 :
            qry.userStapleId = userStapleId
            photoStaple = photoStapleService.findById(userStapleId)
            if photoStaple != None:
                request.setAttribute("photoStaple", photoStaple)
        
        #print "userStapleId = ", userStapleId
        pager.totalRows = qry.count()
        pager.itemName = u"图片"
        pager.itemUnit = u"张"
        qry.orderType = 0
        
        result = qry.query_map(pager)
        request.setAttribute("photo_list", result)
        request.setAttribute("pager", pager)
        
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
        self.page = self.getUserProfilePage(user)
        if self.page.customSkin != None:
            customSkin = JSONObject.parse(self.page.customSkin)
            request.setAttribute("customSkin", customSkin)
        
        # 构造widgets .
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1, "title":u"个人档案", "module":"profile", "ico":"", "data":""}
                   , {"id": "2", "pageId":0, "columnIndex":1, "title":u"相册分类", "module":"photo_cate", "ico":"", "data":""}
                  , {"id": "placerholder1", "pageId":0, "columnIndex":2, "title":"", "module":"placeholder", "ico":"", "data":""}
                  ]
        
        request.setAttribute("widgets", widgets)
        request.setAttribute("widget_list", widgets)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/user/default/user_photos.ftl"
        
        
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
