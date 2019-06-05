from unit_page import UnitBasePage
from user_query import UserQuery

class unit_down_user(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        self.userService = __spring__.getBean("userService")
        
    def execute(self):
        # 此页面需要系统管理员和机构用户管理员进行管理
        if self.loginUser == None:
            return self.LOGIN
        if self.unitService == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 unitService 节点。")
            return self.ERROR
        if self.userService == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 userService 节点。")
            return self.ERROR
                
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        if self.isUserAdmin() == False and self.isUnitAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "unitSystemAdmin" or cmd == "ununitSystemAdmin" or cmd == "unitUserAdmin" or cmd == "ununitUserAdmin":
                settingUnitId = self.params.safeGetIntParam("unit_id")
                if settingUnitId == self.unit.unitId:
                    self.addActionError(u"你只能设置下级机构的系统管理员或者机构用户管理员。")
                    return self.ERROR
            
            self.post_request()
            if request.getHeader("Referer") != None and request.getHeader("Referer") != "":
                response.sendRedirect(request.getHeader("Referer"))
        
        self.get_request()
        request.setAttribute("unit", self.unit)
        return "/WEB-INF/unitsmanage/unit_down_user.ftl"
    
    def get_request(self):
        k = self.params.safeGetStringParam("k")
        f = self.params.safeGetStringParam("f")
        userState = self.params.safeGetStringParam("userState")
        qry = UserQuery(""" u.userId, u.loginName, u.nickName, u.trueName, u.userIcon, u.userStatus, 
                            u.subjectId, u.gradeId, u.createDate, u.userType, u.unitId, unit.unitTitle
                         """)
        qry.orderType = 0
        if userState != "" and userState.isdigit() == True:
            qry.userStatus = int(userState)
        else:
            qry.userStatus = None
        qry.delState = None
        
        request.setAttribute("f", f)
        if k != "":       
            qry.k = k
            request.setAttribute("k", k)
            
            
        # 不使用unitId 查询过滤
        qry.unitId = None
        qry.custormAndWhere = "(u.unitPathInfo LIKE '%/" + str(self.unit.unitId) + "/%') And (u.unitId <> " + str(self.unit.unitId) + ")"
        pager = self.params.createPager()
        pager.itemName = u"用户"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)
        request.setAttribute("pager", pager)        
        request.setAttribute("user_list" , user_list)
        request.setAttribute("userState", userState)
        
    def post_request(self):
        cmd = self.params.safeGetStringParam("cmd")              
        guids = self.params.safeGetIntValues("guid")
        #TODO: 重复的循环
        for g in guids:
            user = self.userService.getUserById(g)
            if user != None:
                if cmd == "delete":
                    #self.userService.deleteUser(user.userId)
                    self.userService.updateUserStatus(user, User.USER_STATUS_DELETED)
                elif cmd == "crash":
                    deleteComplexObjectService = __spring__.getBean("deleteComplexObjectService")
                    deleteComplexObjectService.deleteUser(user)
                elif cmd == "audit":
                    user.setUserStatus(0)
                    self.userService.updateUser(user)
                    self.userService.updateUserStatus(user, User.USER_STATUS_NORMAL)
                elif cmd == "unaudit":
                    user.setUserStatus(1)
                    self.userService.updateUser(user)        
                    self.userService.updateUserStatus(user, User.USER_STATUS_WAIT_AUTID) 
                elif cmd == "contentAdmin":
                    self.contentAdmin()
                elif cmd == "uncontentAdmin":
                    self.uncontentAdmin()                    
                elif cmd == "unitUserAdmin":
                    self.unitUserAdmin()
                elif cmd == "ununitUserAdmin":
                    self.ununitUserAdmin()                    
                elif cmd == "unitSystemAdmin":
                    self.unitSystemAdmin()
                elif cmd == "ununitSystemAdmin":
                    self.ununitSystemAdmin()
                elif cmd == "resetpassword":
                    self.resetpassword(user)                    
                
                elif cmd == "setting":
                    usersetting = self.params.safeGetStringParam("usersetting")
                    if usersetting == "unit_admin":
                        self.addActionError(u"该功能的实现方法已经被修改，如看到此提示，请通知软件开发商。")
                        return self.ERROR
                    elif usersetting == "remove_unit_admin":
                        self.addActionError(u"该功能的实现方法已经被修改，如看到此提示，请通知软件开发商。")
                        return self.ERROR
                    elif usersetting == "content_admin":
                        self.addActionError(u"该功能的实现方法已经被修改，如看到此提示，请通知软件开发商。")
                        return self.ERROR
                    elif usersetting == "remove_content_admin":
                        self.addActionError(u"该功能的实现方法已经被修改，如看到此提示，请通知软件开发商。")
                        return self.ERROR
                        
                    elif usersetting == "famous":
                        self.userService.setUserFamous(user, True)
                    elif usersetting == "remove_famous":
                        self.userService.setUserFamous(user, False)
                    elif usersetting == "expert":
                        self.userService.setUserExpert(user, True)
                    elif usersetting == "remove_expert":
                        self.userService.setUserExpert(user, False)
                        
                    elif usersetting == "reseacher":
                        self.userService.setUserComissioner(user, True)
                    elif usersetting == "remove_reseacher":
                        self.userService.setUserComissioner(user, False)
                    elif usersetting == "rcmd":
                        self.userService.setUserRecommend(user, True)
                    elif usersetting == "remove_rcmd":
                        self.userService.setUserRecommend(user, False)
                    elif usersetting == "star":
                        self.userService.setUserMien(user, 2)
                    elif usersetting == "remove_star":
                        self.userService.deleteUserMien(user, 2)
    
    def contentAdmin(self):
        settingUnitId = self.params.safeGetIntParam("unit_id")
        settingUnit = self.unitService.getUnitById(settingUnitId)
        if settingUnit == None:            
            return
        if self.checkUserCanManageUnitUser(settingUnit) == False:
            return
        accessControlService = __spring__.getBean("accessControlService")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            #检查是否已经是机构内容管理员
            if False == accessControlService.checkUserAccessControlIsExists(g, AccessControl.OBJECTTYPE_UNITCONTENTADMIN, settingUnit.unitId):
                accessControl = AccessControl()
                accessControl.setUserId(g)
                accessControl.setObjectType(AccessControl.OBJECTTYPE_UNITCONTENTADMIN)
                accessControl.setObjectId(settingUnit.unitId)
                accessControl.setObjectTitle(settingUnit.unitTitle)
                accessControlService.saveOrUpdateAccessControl(accessControl)
                
    def uncontentAdmin(self):
        settingUnitId = self.params.safeGetIntParam("unit_id")
        settingUnit = self.unitService.getUnitById(settingUnitId)
        if settingUnit == None:            
            return
        if self.checkUserCanManageUnitUser(settingUnit) == False:
            return
        
        
        accessControlService = __spring__.getBean("accessControlService")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            #检查是否已经是本机构内容管理员，只删除本机构设置的
            if True == accessControlService.checkUserAccessControlIsExists(g, AccessControl.OBJECTTYPE_UNITCONTENTADMIN, settingUnit.unitId):
                accessControl = accessControlService.deleteAccessControlByUserIdObjectTypeObjectId(g, AccessControl.OBJECTTYPE_UNITCONTENTADMIN, settingUnit.unitId)                
    
    def unitUserAdmin(self):
        settingUnitId = self.params.safeGetIntParam("unit_id")
        settingUnit = self.unitService.getUnitById(settingUnitId)
        if settingUnit == None:            
            return
        if self.checkUserCanManageUnitUser(settingUnit) == False:
            return
        
        accessControlService = __spring__.getBean("accessControlService")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            #检查是否已经是机构用户管理员
            if False == accessControlService.checkUserAccessControlIsExists(g, AccessControl.OBJECTTYPE_UNITUSERADMIN, settingUnit.unitId):
                accessControl = AccessControl()
                accessControl.setUserId(g)
                accessControl.setObjectType(AccessControl.OBJECTTYPE_UNITUSERADMIN)
                accessControl.setObjectId(settingUnit.unitId)
                accessControl.setObjectTitle(settingUnit.unitTitle)
                accessControlService.saveOrUpdateAccessControl(accessControl)
    
    def ununitUserAdmin(self):
        settingUnitId = self.params.safeGetIntParam("unit_id")
        settingUnit = self.unitService.getUnitById(settingUnitId)
        if settingUnit == None:            
            return
        if self.checkUserCanManageUnitUser(settingUnit) == False:
            return        
        
        accessControlService = __spring__.getBean("accessControlService")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            #检查是否已经是本机构用户管理员，只删除本机构设置的
            if True == accessControlService.checkUserAccessControlIsExists(g, AccessControl.OBJECTTYPE_UNITUSERADMIN, settingUnit.unitId):
                accessControl = accessControlService.deleteAccessControlByUserIdObjectTypeObjectId(g, AccessControl.OBJECTTYPE_UNITUSERADMIN, settingUnit.unitId)                
    
    def unitSystemAdmin(self):
        settingUnitId = self.params.safeGetIntParam("unit_id")
        settingUnit = self.unitService.getUnitById(settingUnitId)
        if settingUnit == None:            
            return
        if self.checkUserCanManageUnitUser(settingUnit) == False:
            return        
        
        accessControlService = __spring__.getBean("accessControlService")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            #检查是否已经是机构系统管理员
            if False == accessControlService.checkUserAccessControlIsExists(g, AccessControl.OBJECTTYPE_UNITSYSTEMADMIN, settingUnit.unitId):
                accessControl = AccessControl()
                accessControl.setUserId(g)
                accessControl.setObjectType(AccessControl.OBJECTTYPE_UNITSYSTEMADMIN)
                accessControl.setObjectId(settingUnit.unitId)
                accessControl.setObjectTitle(settingUnit.unitTitle)
                accessControlService.saveOrUpdateAccessControl(accessControl)                
        
    def ununitSystemAdmin(self):
        settingUnitId = self.params.safeGetIntParam("unit_id")
        settingUnit = self.unitService.getUnitById(settingUnitId)
        if settingUnit == None:            
            return
        if self.checkUserCanManageUnitUser(settingUnit) == False:
            return        
        
        accessControlService = __spring__.getBean("accessControlService")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            #检查是否已经是本机构用户管理员，只删除本机构设置的
            if True == accessControlService.checkUserAccessControlIsExists(g, AccessControl.OBJECTTYPE_UNITSYSTEMADMIN, settingUnit.unitId):
                accessControl = accessControlService.deleteAccessControlByUserIdObjectTypeObjectId(g, AccessControl.OBJECTTYPE_UNITSYSTEMADMIN, settingUnit.unitId)
                
    def checkUserCanManageUnitUser(self, toManageUnit):
        accessControlService = __spring__.getBean("accessControlService")
        return accessControlService.userIsUnitUserAdmin(self.loginUser, toManageUnit) or accessControlService.userIsUnitSystemAdmin(self.loginUser, toManageUnit)
    
    def resetpassword(self,user):
        if user == None:
            return
        newPwd = self.params.safeGetStringParam("reset_password")
        self.userService.resetPassword(user.loginName, newPwd)