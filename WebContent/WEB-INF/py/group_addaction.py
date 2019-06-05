from base_blog_page import *
from base_action import BaseAction
from cn.edustar.jitar.pojos import Group
from cn.edustar.jitar.pojos import Action
from cn.edustar.jitar.util import ParamUtil

class group_addaction(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def execute(self):
        self.writer = response.getWriter()
        self.params = ParamUtil(request)        
        self.actionId = request.getAttribute("actionId")
        if self.actionId == None or self.actionId == "":            
            if request.getMethod() == "POST":
                return self.saveAction()
            else:
                return self.createAction()
        else:
            return self.showAction()

        
    def createAction(self):
        self.grp_svc = __jitar__.groupService
        groupName = request.getAttribute("groupName")
        if groupName == None or groupName == "":
            self.writer.println(u"请通过正确的群组进行访问。")
            return
                
        group = self.grp_svc.getGroupByName(groupName)
        if group == None:
            self.writer.println(u"您的问的群组不存在或者暂时不能访问。")
            return
        page = {
                "pageId":0,
                "layoutId":2, # 固定是布局2
                "isSystemPage" : "true", 
                "owner" : "user", 
                "title" :"",
                "skin":"skin1"
                }        
        request.setAttribute("page", page)
        
        
        # 构造widgets .
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"协作组信息","module":"group_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        request.setAttribute("widgets",widgets)
        request.setAttribute("widget_list",widgets)
        request.setAttribute("group",group)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/group/default/group_createaction.ftl"
        
    def saveAction(self):
        action = Action()
        action.setTitle(request.getParameter("actionName"))
        action.setTitle(request.getParameter("actionTitle"))
        action.setTitle(request.getParameter("actionTitle"))
        action.setTitle(request.getParameter("actionTitle"))
        action.setTitle(request.getParameter("actionTitle"))
        action.setTitle(request.getParameter("actionTitle"))
        action.setTitle(request.getParameter("actionTitle"))
        
    def showAction(self):
        return "/WEB-INF/group/default/group_createaction.ftl"