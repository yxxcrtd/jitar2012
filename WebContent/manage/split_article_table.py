from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Config, User
from base_action import ActionResult
from java.lang import String
from java.util import Calendar

class split_article_table(BaseAdminAction, ActionResult):
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理站点配置的权限，只有超级管理员才能进行管理。")
            return ActionResult.ERROR
        self.params = ParamUtil(request)
        cmd = self.params.getStringParam("cmd")
        webSiteManageService = __spring__.getBean("webSiteManageService")
        if cmd == "":            
            y = webSiteManageService.getArticleYearList()
            request.setAttribute("info","")
            ydata = []
            for yy in y:                
                if yy != Calendar.getInstance().get(Calendar.YEAR):
                    count = webSiteManageService.getYearArticleCount(yy)
                    ydata.append({"year":yy,"count":count})
            request.setAttribute("ydata",ydata)
            return "/WEB-INF/ftl/admin/split_article_table.ftl"
        elif cmd == "split":
            year = self.params.safeGetIntParam("year")
            if year == 0:
                self.addActionError(u"请选择一个年份。")
                return ActionResult.ERROR
            ret = webSiteManageService.slpitArticleTable(year)
            self.addActionMessage(u"操作成功！")
            self.addActionLink(u"返回前页", "?")
            return ActionResult.SUCCESS