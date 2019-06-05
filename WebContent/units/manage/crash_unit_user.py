from unit_page import UnitBasePage
from cn.edustar.jitar.pojos import UnitLinks, AccessControl

class crash_unit_user(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.unitService == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 unitService 节点。")
            return self.ERROR   
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
                
        if self.isUserAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        userId = self.params.safeGetIntParam("userId")
        userService = __jitar__.userService
        deleteUser = userService.getUserById(userId)
        if deleteUser == None:
            self.addActionError(u"该用户不存在。")
            return ActionResult.ERROR
        
        if deleteUser.userStatus != 2:
            self.addActionError(u"该用户不属于待删除状态，不能进行彻底删除。")
            return ActionResult.ERROR
        
        #当前用户是否可以管理指定的用户？
        accessControlService = __spring__.getBean("accessControlService")
        hasRight = accessControlService.checkUserAccessControlIsExists(self.loginUser.userId,AccessControl.OBJECTTYPE_UNITUSERADMIN,deleteUser.unitId) == True or accessControlService.checkUserAccessControlIsExists(self.loginUser.userId,AccessControl.OBJECTTYPE_UNITSYSTEMADMIN,deleteUser.unitId) == True or accessControlService.isSystemAdmin(self.loginUser) == True or accessControlService.isSystemUserAdmin(self.loginUser) == True
        if hasRight == False :
            self.addActionError(u"你无权管理该用户。")
            return ActionResult.ERROR
        
        #检查该用户是否是群组的创建者，需要先解除群组的创建者
        groupService = __jitar__.groupService
        glist = groupService.getAllUserCreatedGroupByUserId(deleteUser.userId)
        if glist != None and len(glist) > 0:
            request.setAttribute("glist", glist)
            return "/WEB-INF/ftl/admin/crash_unit_user.ftl"
        
        deleteComplexObjectService = __spring__.getBean("deleteComplexObjectService")
        deleteComplexObjectService.deleteUser(deleteUser)
        self.addActionLink(u"返回", request.getHeader("Referer"))
        return ActionResult.SUCCESS
