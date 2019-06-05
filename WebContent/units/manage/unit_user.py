from unit_page import UnitBasePage
from user_query import UserQuery
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.pojos import AccessControl

class unit_user(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        self.userService = __spring__.getBean("userService")
        
    def execute(self):
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
        if self.isUserAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.save_post()
            self.clear_cache()
            if request.getHeader("Referer") != None and request.getHeader("Referer") != "":
                response.sendRedirect(request.getHeader("Referer"))

        self.list_user()
 
        if self.isUnitAdmin() == True:
            request.setAttribute("unitAdmin", "1")
        else:
            request.setAttribute("unitAdmin", "0")
                
        request.setAttribute("unit", self.unit)
        return "/WEB-INF/unitsmanage/unit_user.ftl"
    
    def list_user(self):
        userState = self.params.safeGetStringParam("userState")
        k = self.params.safeGetStringParam("k")
        qry = UserQuery(""" u.userId, u.loginName, u.nickName, u.trueName, u.userIcon, u.userStatus, u.email, 
                        u.subjectId, u.gradeId, u.createDate, u.userType   """)
        qry.orderType = 0
        if userState != "" and userState.isdigit() == True:
            qry.userStatus = int(userState)
        else:
            qry.userStatus = None
        if k != "":
            qry.f = "name"
            qry.k = k
            request.setAttribute("k", k)
            
        qry.unitId = self.unit.unitId
        pager = self.params.createPager()
        pager.itemName = u"用户"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)
        
        request.setAttribute("pager", pager)
        request.setAttribute("user_list", user_list)
        request.setAttribute("userState", userState)
        
    def save_post(self):
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            user = self.userService.getUserById(g)
            if user != None:
                if cmd == "delete":
                    #self.userService.deleteUser(user.userId)
                    self.userService.updateUserStatus(user, User.USER_STATUS_DELETED)
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
    
    def contentAdmin(self):
        accessControlService = __spring__.getBean("accessControlService")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            #检查是否已经是机构内容管理员
            if False == accessControlService.checkUserAccessControlIsExists(g, AccessControl.OBJECTTYPE_UNITCONTENTADMIN, self.unit.unitId):
                accessControl = AccessControl()
                accessControl.setUserId(g)
                accessControl.setObjectType(AccessControl.OBJECTTYPE_UNITCONTENTADMIN)
                accessControl.setObjectId(self.unit.unitId)
                accessControl.setObjectTitle(self.unit.unitTitle)
                accessControlService.saveOrUpdateAccessControl(accessControl)
                
    def uncontentAdmin(self):
        accessControlService = __spring__.getBean("accessControlService")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            #检查是否已经是本机构内容管理员，只删除本机构设置的
            if True == accessControlService.checkUserAccessControlIsExists(g, AccessControl.OBJECTTYPE_UNITCONTENTADMIN, self.unit.unitId):
                accessControl = accessControlService.deleteAccessControlByUserIdObjectTypeObjectId(g, AccessControl.OBJECTTYPE_UNITCONTENTADMIN, self.unit.unitId)
                
    
    def unitUserAdmin(self):
        accessControlService = __spring__.getBean("accessControlService")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            #检查是否已经是机构用户管理员
            if False == accessControlService.checkUserAccessControlIsExists(g, AccessControl.OBJECTTYPE_UNITUSERADMIN, self.unit.unitId):
                accessControl = AccessControl()
                accessControl.setUserId(g)
                accessControl.setObjectType(AccessControl.OBJECTTYPE_UNITUSERADMIN)
                accessControl.setObjectId(self.unit.unitId)
                accessControl.setObjectTitle(self.unit.unitTitle)
                accessControlService.saveOrUpdateAccessControl(accessControl)
    
    def ununitUserAdmin(self):
        accessControlService = __spring__.getBean("accessControlService")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            #检查是否已经是本机构用户管理员，只删除本机构设置的
            if True == accessControlService.checkUserAccessControlIsExists(g, AccessControl.OBJECTTYPE_UNITUSERADMIN, self.unit.unitId):
                accessControl = accessControlService.deleteAccessControlByUserIdObjectTypeObjectId(g, AccessControl.OBJECTTYPE_UNITUSERADMIN, self.unit.unitId)
                
    
    def unitSystemAdmin(self):
        accessControlService = __spring__.getBean("accessControlService")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            #检查是否已经是机构系统管理员
            if False == accessControlService.checkUserAccessControlIsExists(g, AccessControl.OBJECTTYPE_UNITSYSTEMADMIN, self.unit.unitId):
                accessControl = AccessControl()
                accessControl.setUserId(g)
                accessControl.setObjectType(AccessControl.OBJECTTYPE_UNITSYSTEMADMIN)
                accessControl.setObjectId(self.unit.unitId)
                accessControl.setObjectTitle(self.unit.unitTitle)
                accessControlService.saveOrUpdateAccessControl(accessControl)
        
    def ununitSystemAdmin(self):
        accessControlService = __spring__.getBean("accessControlService")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            #检查是否已经是本机构用户管理员，只删除本机构设置的
            if True == accessControlService.checkUserAccessControlIsExists(g, AccessControl.OBJECTTYPE_UNITSYSTEMADMIN, self.unit.unitId):
                accessControl = accessControlService.deleteAccessControlByUserIdObjectTypeObjectId(g, AccessControl.OBJECTTYPE_UNITSYSTEMADMIN, self.unit.unitId)
                
