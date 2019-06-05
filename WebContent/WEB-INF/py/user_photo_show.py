from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import PhotoStaple
from cn.edustar.data import Pager
from comment_query import CommentQuery
from base_blog_page import *
from cn.edustar.jitar.util.json import *
from base_action import BaseAction

class user_photo_show(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def execute(self):
        photoService = __jitar__.getPhotoService()
        photoStapleService = __jitar__.getPhotoStapleService()
        self.params = ParamUtil(request)
        writer = response.getWriter()
        
        loginName = request.getAttribute("loginName")
        photoId = self.params.safeGetIntParam("photoId")
        user = __jitar__.userService.getUserByLoginName(loginName)
        request.setAttribute("user", user)
        if self.canVisitUser(user) == False:
            return self.ACCESS_ERROR        
        # loginUser 对象来自基类 BaseAdminAction .
        request.setAttribute("loginUser", self.loginUser)
        
        # 得到照片对象
        photo = photoService.findById(photoId)
        if photo == None:
            self.addActionError(u"无法加载该照片。")
            return self.ERROR
            
        request.setAttribute("photo", photo)
        if photo.userStaple != None:
            photoStaple = photoStapleService.findById(photo.userStaple)
            request.setAttribute("photoStaple", photoStaple)
        
        cmd = Command(""" UPDATE Photo SET viewCount = viewCount + 1 WHERE photoId = :photoId """)
        cmd.setInteger("photoId", photo.photoId)
        cmd.update()
    
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
            customSkin = JSONValue.parse(self.page.customSkin)
            request.setAttribute("customSkin", customSkin)
        
        # 构造widgets .
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"个人档案","module":"profile", "ico":"", "data":""}
                   ,{"id": "2", "pageId":0, "columnIndex":1,"title":u"相册分类","module":"photo_cate", "ico":"", "data":""}
                  ,{"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        
        request.setAttribute("widgets", widgets)
        request.setAttribute("widget_list", widgets)
        
        qry = CommentQuery(""" cmt.content,cmt.createDate,cmt.star,cmt.title,u.loginName,u.userId,u.userIcon
                            """)
        qry.objType = 11
        qry.orderType = 3
        qry.objId = photo.photoId
        pager = self.params.createPager()
        pager.setPageSize(10)
        pager.totalRows = qry.count()
        pager.itemName = u"评论"
        pager.itemUnit = u"条"
        
        result = qry.query_map(pager)
        request.setAttribute("photo_comment_list",result)
        request.setAttribute("pager",pager)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/user/default/user_photo_show.ftl"