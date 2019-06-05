from base_action import ActionExecutor, ActionResult
from cn.edustar.jitar.pojos import Page
from cn.edustar.jitar.util import ParamUtil

class user_widget_delete(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.pageService = __spring__.getBean("pageService")
        self.login_user = self.getLoginUser()

    def execute(self):
        if self.loginUser == None:
            response.sendRedirect("../login.jsp")
            return None
        page = self.pageService.getUserIndexPage(self.login_user)
        if page == None:
            self.addActionError(u"你的个人空间不存在。")
            return ActionResult.ERROR
        if request.getMethod() == "POST":
            ids = self.params.safeGetIntValues("guid")
            for id in ids:
                widget = self.pageService.getWidget(id)
                if widget != None and widget.pageId == page.pageId:
                     self.pageService.removeWidget(widget.id)
            self.addActionMessage(u"操作完成。")
            return ActionResult.SUCCESS          
        
        widget_list = self.pageService.getPageWidgets(page.pageId)
        request.setAttribute("widget_list", widget_list)
        return "/WEB-INF/ftl/user/user_widget_delete.ftl"