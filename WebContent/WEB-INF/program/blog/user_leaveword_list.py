from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.data import Pager
from leaveword_query import LeaveWordQuery
from base_blog_page import *
from com.alibaba.fastjson import JSONObject
from base_action import BaseAction

class user_leaveword_list(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def execute(self):
        writer = response.getWriter()
        
        self.params = ParamUtil(request)
        loginName = request.getAttribute("loginName")
        if (loginName == None or loginName == ''):
            writer.write(u"没有该用户。")
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
        qry = LeaveWordQuery(""" lwd.title, lwd.content, lwd.loginName, lwd.nickName, lwd.createDate, lwd.reply                             
                            """)
        qry.objId = user.userId
        qry.objType = 1
        
        pager.totalRows = qry.count()
        pager.itemName = u"留言"
        pager.itemUnit = u"条"
        pager.pageSize = 20
        
        result = qry.query_map(pager)
        request.setAttribute("user_leaveword_list",result)
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
        self.page = self.getUserProfilePage(user)
        if self.page.customSkin != None:
            customSkin = JSONObject.parse(self.page.customSkin)
            request.setAttribute("customSkin", customSkin)
        
        # 构造widgets .
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"个人档案","module":"profile", "ico":"", "data":""}
                   ,{"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        
        request.setAttribute("widgets", widgets)
        request.setAttribute("widget_list", widgets)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/user/default/user_leaveword_list.ftl"