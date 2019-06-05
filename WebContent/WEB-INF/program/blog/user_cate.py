from cn.edustar.jitar.util import ParamUtil
from base_action import BaseAction
from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.model import SiteUrlModel

class user_cate(BaseAction):
    def execute(self):
        self.userName = request.getAttribute("loginName")
        userService = __jitar__.userService
        categoryService = __jitar__.categoryService
        user = userService.getUserByLoginName(self.userName)
        if user == None:
            response.writer.println(u"不能加载该用户的信息")
            return
        if self.canVisitUser(user) == False:
            response.writer.println(u"用户  %s 无法访问." % self.userName)
            return
        fc = FileCache()
        # 14400 为10 天
        content = fc.getUserFileCacheContent(self.userName, "user_cate.html",14400)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        itemType = CommonUtil.toUserArticleCategoryItemType(user.getUserId())        
        usercate_tree = categoryService.getCategoryTree(itemType)
        
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("user", user)
        map.put("usercate_tree", usercate_tree)
        map.put("UserSiteUrl", self.getUserSiteUrl())
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_cate.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "user_cate.html",content)        
        response.getWriter().write(content)
        fc = None
        
    def getUserSiteUrl(self):
        siteUrl = SiteUrlModel.getSiteUrl()
        userSiteUrl = request.getSession().getServletContext().getInitParameter("userUrlPattern");
        if userSiteUrl == None or userSiteUrl == "":
            userSiteUrl = siteUrl + "u/" + self.userName + "/"
        else:
            userSiteUrl = userSiteUrl.replace("{loginName}", self.userName)            
        return userSiteUrl