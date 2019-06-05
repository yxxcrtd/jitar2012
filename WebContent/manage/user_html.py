from base_action import ActionExecutor,ActionResult
from cn.edustar.jitar.pojos import Page
from cn.edustar.jitar.util import ParamUtil

class user_html(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.page_svc = __spring__.getBean("pageService")
        self.login_user = self.getLoginUser()

    def execute(self):
        if self.loginUser == None:
            response.sendRedirect("../login.jsp")
            return None
        page = self.page_svc.getUserIndexPage(self.login_user)
        if page == None:
            self.addActionError(u"没有找到您的首页。")
            return ActionResult.ERROR
        return "/WEB-INF/ftl/user/user_html.ftl"