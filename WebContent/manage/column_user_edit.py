from base_manage import *
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import AccessControl

class column_user_edit(BaseManage):    
    def execute(self):
        self.params = ParamUtil(request)
        if self.isSystemAdmin() == False:
            self.addActionError(u"没有管理权限，需要的权限为系统管理员.")
            return self.ERROR
        cmd = self.params.safeGetStringParam("cmd")
        accessControlService = __spring__.getBean("accessControlService")            
        if cmd=="Add":
            columnId = self.params.safeGetIntParam("columnId")
            jitarColumnService = __spring__.getBean("jitarColumnService")
            jitarColumn = jitarColumnService.getJitarColumnById(columnId)
            if jitarColumn == None:
                self.addActionError(u"没有指定要管理的栏目.")
                return self.ERROR
            userids = self.params.safeGetIntValues("userid")
            for id in userids:
                if accessControlService.checkUserAccessControlIsExists(id, AccessControl.OBJECTTYPE_CUSTORMCOLUMNADMIN,columnId) == False:
                    accessControl = AccessControl()
                    accessControl.setUserId(id)
                    accessControl.setObjectType(AccessControl.OBJECTTYPE_CUSTORMCOLUMNADMIN)
                    accessControl.setObjectId(columnId)
                    accessControl.setObjectTitle(jitarColumn.columnName)
                    accessControlService.saveOrUpdateAccessControl(accessControl)
            response.sendRedirect("column_user.py?cmd=right&columnId=" + str(columnId))
        elif cmd == "Delete":
            accessControlIds = self.params.safeGetIntValues("guid")
            for id in accessControlIds:
                ac = accessControlService.getAccessControlById(id)
                if ac != None:
                    accessControlService.deleteAccessControl(ac)
            if self.params.existParam("columnId"):
                columnId = self.params.safeGetIntParam("columnId")
                if columnId == 0:
                    response.sendRedirect("column_user.py?cmd=right")
                else:
                    response.sendRedirect("column_user.py?cmd=right&columnId=" + str(columnId))
            else:
                response.sendRedirect("column_user.py?cmd=right")
        else:
            self.addActionError(u"无效的命令.")
            return self.ERROR
            