from base_blog_page import *
from base_action import BaseAction
from java.net import URLEncoder

user_svc = __jitar__.userService

# 样例页面：每个系统都会写出这样一个页面的。
# 本页面实现的功能：
# 得到用户对象user，得到用户的系统默认页page 和自定义皮肤信息customSkin ，得到用户的首页信息，设置内容返回地址
class userbasepage(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def execute(self):
        loginName = request.getAttribute("loginName")
        user = __jitar__.userService.getUserByLoginName(loginName)
        if user == None:
            actionErrors = [u"您请求的用户不存在。请检查是否拼写正确或者没有区分大小写。"]
            request.setAttribute("actionErrors", actionErrors)
            return "/WEB-INF/ftl/Error.ftl"        
        page = self.getUserEntryPage(user)
        if page == None:
            actionErrors = [u"您请求的用户页面不存在。请检查是否拼写正确或者没有区分大小写。"]
            request.setAttribute("actionErrors", actionErrors)
            return "/WEB-INF/ftl/Error.ftl"
        
        callbackUrl = request.getParameter("url")        
        if callbackUrl == None or callbackUrl == "":
            actionErrors = [u"您没有指定请求的页面地址，无需调用本页面。"]
            request.setAttribute("actionErrors", actionErrors)
            return "/WEB-INF/ftl/Error.ftl"
        #callbackUrl = URLEncoder.encode(callbackUrl, "UTF-8")
        # 构造widgets .
        widgets = [
                   {"id": "1", "pageId": page.pageId, "columnIndex":1,"title":u"个人档案","module":"profile", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId": page.pageId, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":"callbackurl:\'" + callbackUrl + "\'"}
                  ]
        request.setAttribute("widgets",widgets)
        request.setAttribute("user", user)
        request.setAttribute("page", page)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/user/default/userbasepage.ftl"