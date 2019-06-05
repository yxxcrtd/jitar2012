from base_manage import *
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import AccessControl
class column_user(BaseManage):
    
    def execute(self):
        self.params = ParamUtil(request)
        if self.isSystemAdmin() == False:
            self.addActionError(u"没有管理权限，需要的权限为系统管理员.")
            return self.ERROR
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "left":
            jitarColumnService = __spring__.getBean("jitarColumnService")
            columnList = jitarColumnService.getJitarColumnList()
            request.setAttribute("columnList",columnList)
            return "/WEB-INF/ftl/admin/column_user_left.ftl"
        elif cmd=="right":
            columnId = self.params.safeGetIntParam("columnId")
            accessControlService = __spring__.getBean("accessControlService")
            accessControlList = None
            jitarColumn = None
            if columnId == 0:
                #加载权全部的用户
                accessControlList = accessControlService.getAllAccessControlByObject(AccessControl.OBJECTTYPE_CUSTORMCOLUMNADMIN, 0)
            else:
                jitarColumnService = __spring__.getBean("jitarColumnService")
                jitarColumn = jitarColumnService.getJitarColumnById(columnId)
                accessControlList = accessControlService.getAllAccessControlByObject(AccessControl.OBJECTTYPE_CUSTORMCOLUMNADMIN,columnId)
            request.setAttribute("jitarColumn",jitarColumn)
            request.setAttribute("accessControlList",accessControlList)
            return "/WEB-INF/ftl/admin/column_user_right.ftl"
        return "/WEB-INF/ftl/admin/column_user.ftl"