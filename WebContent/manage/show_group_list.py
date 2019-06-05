from cn.edustar.jitar.util import ParamUtil
from group_query import GroupQuery
from base_action import ActionResult
from cn.edustar.jitar.jython import JythonBaseAction

class show_group_list(JythonBaseAction):
    def execute(self):
        self.params = ParamUtil(request)
        k = self.params.safeGetStringParam("k")
        gid = self.params.safeGetStringParam("gid")
        if gid == "":
            self.addActionError(u"请选择id的接收者.")
            self.addActionLink(u"关闭", "javascript:window.close();")
            return ActionResult.ERROR 
        pager = self.params.createPager()
        pager.itemName = u"协作组"
        pager.itemUnit = u"个"
        pager.pageSize = 10
        qry = GroupQuery("g.groupId,g.groupTitle")
        if k != "":
            qry.k = k
            request.setAttribute("k", k)
        pager.totalRows = qry.count()
        group_list = qry.query_map(pager)
        request.setAttribute("group_list", group_list)
        request.setAttribute("pager", pager)
        request.setAttribute("gid", gid)
        return "/WEB-INF/ftl/admin/show_group_list.ftl"
