from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from base_blog_page import *
from cn.edustar.jitar.util.json import *
from base_action import BaseAction
from action_query import ActionQuery

user_svc = __jitar__.userService


class user_actions(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        self.loginName = request.getAttribute("loginName")
        # 加载当前用户对象.
        self.user = user_svc.getUserByLoginName(self.loginName)
        request.setAttribute("user", self.user)
        
        pager = self.params.createPager()  
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount
                            """)
        qry.ownerType = "user"
        qry.status = 0
        qry.createUserId = self.user.userId
        pager.setPageSize(16)        
        pager.itemName = u"活动"
        pager.itemUnit = u"个"
        action_list = qry.query_map(pager)
        pager.totalRows = action_list.size()
        
        #print "group_list = " , group_list
        request.setAttribute("action_list", action_list)        
        request.setAttribute("pager",pager)        
        hql = """SELECT p.skin
             FROM Page p 
             WHERE p.name = 'index' and p.objId = :userId and p.objType = 1
             """ 
        pageSkin = Command(hql).setInteger("userId", self.user.userId).scalar()
        #print "pageSkin = ", pageSkin
        if pageSkin == None or pageSkin == "": pageSkin = "skin1"
        
        # 构造页面数据，由于页面不是在数据库存在的，这里的数据是虚拟数据.
        #pages : [{id: ${page.pageId}, title: '${user.blogName!?js_string}', layoutId: ${page.layoutId!0} }],
        page = {
                "pageId":0,
                "layoutId":2, # 固定是布局2
                "isSystemPage" : "true", 
                "owner" : "user", 
                "title" :"",
                "skin":pageSkin
                }        
        request.setAttribute("page", page)
        self.page = self.getUserProfilePage(self.user)
        if self.page.customSkin != None:
            customSkin = JSONValue.parse(self.page.customSkin)
            request.setAttribute("customSkin", customSkin)
                
        # 构造widgets .
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"个人档案","module":"profile", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        request.setAttribute("widgets",widgets)
        request.setAttribute("widget_list",widgets)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/user/default/user_actions.ftl"
