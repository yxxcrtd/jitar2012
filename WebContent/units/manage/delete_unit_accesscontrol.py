from unit_page import UnitBasePage
from cn.edustar.jitar.pojos import AccessControl

class delete_unit_accesscontrol(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        self.userService = __spring__.getBean("userService")
        self.accessControlService = __spring__.getBean("accessControlService")
        
    def execute(self):
        # 此页面需要系统管理员和机构用户管理员进行管理
        if self.loginUser == None:
            return self.LOGIN
                        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        if self.isUserAdmin() == False and self.isUnitAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        accessControlId = self.params.safeGetIntParam("accessControlId")
        if accessControlId == 0:
            self.addActionError(u"无效的标识。")
            return self.ERROR
        accessControl = self.accessControlService.getAccessControlById(accessControlId)
        if accessControl == None:
            self.addActionError(u"不能加载 accessControl 对象。")
            return self.ERROR
                
        # 判断当前用户是否可以管理该用户，只要是下级机构的用户，所有权限都可以删除，包括学科权限。
        
        userId = accessControl.getUserId()
        user = self.userService.getUserById(userId)
        if user == None:
            self.addActionError(u"不能加载被管理的 user 对象。")
            return self.ERROR
        if user.unitPathInfo != None:
            if user.unitPathInfo.find("/" + str(self.unit.unitId) + "/") > -1:
                self.accessControlService.deleteAccessControl(accessControl)
            else:
                self.addActionError(u"该用户不在你的管理范围。")
                return self.ERROR
        else:
            self.addActionError(u"用户信息缺少 unitPathInfo或者unitId信息，无法进行操作。")
            return self.ERROR
        response.sendRedirect("unit_down_user.py?unitId=" + str(self.unit.unitId)) 
        
