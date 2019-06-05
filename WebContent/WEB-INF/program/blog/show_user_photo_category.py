from cn.edustar.jitar.pojos import Category
from base_blog_page import *
from com.alibaba.fastjson import JSONObject
from base_action import BaseAction
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import PhotoStaple
from cn.edustar.data import Pager
from photo_query import PhotoQuery

# 本模块全局变量.
user_svc = __jitar__.userService
cate_svc = __jitar__.categoryService
photoStapleService = __jitar__.getPhotoStapleService()

class show_user_photo_category(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner, CategoryMixiner):
    def execute(self):
        self.params = ParamUtil(request)
        self.loginName = request.getAttribute("loginName")
        title = self.params.safeGetStringParam("title")
        if title != "":request.setAttribute("title", title)
        
        # 解析 uri.
        if self.parseUri() == False:
            return self.sendNotFound(self.uri)        
        
        # 得到要工作室主人, 并验证用户状态.
        self.user = user_svc.getUserByLoginName(self.loginName)
        request.setAttribute("user", self.user)
        request.setAttribute("loginUser", self.loginUser)
        #print "self.user = ", self.user]
        if self.canVisitUser(self.user) == False:
            return self.ACCESS_ERROR
        
        request.setAttribute("loginUser", self.loginUser)
        
       
        # 创建分页对象
        pager = self.params.createPager()
        pager.setPageSize(18)
        qry = PhotoQuery(""" p.photoId, p.title, p.createDate, p.lastModified, p.href, p.commentCount,p.viewCount, p.userStaple, 
                           u.nickName, u.loginName, u.userIcon, stap.title as stapleTitle                                       
                            """)
        qry.userId = self.user.userId
        qry.isPrivateShow = None
        if self.categoryId != 0 :
            qry.userStapleId = self.categoryId
            photoStaple = photoStapleService.findById(self.categoryId)
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
        
        hql = """SELECT new Map(p.skin as skin) FROM Page p 
             WHERE p.name = 'index' and p.objId = :userId and p.objType = 1
             """ 
        pageSkin = Command(hql).setInteger("userId", self.user.userId).first()
        
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
        self.page = self.getUserProfilePage(self.user)
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
        
    # 解析 uri, 从中获取要访问的 loginName, categoryId.
    def parseUri(self):
        self.uri = self.getRequestURI()
        #print "self.uri =", self.uri
        if self.uri == None or self.uri == "":
            return False
    
        # 例子: /Groups/liujunxing/rescate/0.html -> 
        #   ['', 'Groups', 'liujunxing', 'rescate', '0.html']
        # 其中最后一个是分类标识+'.html', 倒数第3个是用户登录名.
        arr = self.uri.split('/')
        arr_len = len(arr)
        if arr_len < 3:
            return False
    
        # 得到分类标识部分.
        category_part = self.removeHtmlExt(arr[arr_len - 1])    # 153.html -> 153
        if isIntegerStrong(category_part) == False: 
            return False
        self.categoryId = int(category_part)
        #print "self.categoryId = ", self.categoryId  
    
        return True
