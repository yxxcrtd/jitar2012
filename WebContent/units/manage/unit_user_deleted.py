from unit_page import UnitBasePage
from user_query import UserQuery
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.data import Command


class unit_user_deleted(UnitBasePage):
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
        
        request.setAttribute("unit", self.unit)
        return "/WEB-INF/unitsmanage/unit_user_deleted.ftl"
    def list_user(self):
        k = self.params.safeGetStringParam("k")
        qry = UserQuery(""" u.userId, u.loginName, u.nickName, u.trueName, u.userIcon, u.userStatus, u.email, u.subjectId, 
                        u.gradeId, u.createDate, u.userType
                        """)
        qry.orderType = 0
        qry.userStatus = 2
        qry.delState = None        
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
        request.setAttribute("user_list" , user_list)
    
    def save_post(self):
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            user = self.userService.getUserById(g)
            if user != None:
                if cmd == "delete":
                    #检查用户是否创建了群组：
                    sql = "SELECT COUNT(*) AS JoinedCount FROM Group WHERE createUserId=" + str(user.userId)
                    JoinedCount = Command(sql).int_scalar()
                    if JoinedCount > 0:
                        response.getWriter().println(u"用户【" + user.trueName + u"(" + user.loginName + u")】还有创建的协作组没有处理，请先转让该用户创建的协作组。")
                        return
                    self.userService.deleteUser(user.userId)
                elif cmd == "audit":
                    user.setUserStatus(0)
                    self.userService.updateUser(user,False)
                    self.userService.updateUserStatus(user, User.USER_STATUS_NORMAL)
                else:
                    self.addActionError(u"无效的命令。")
                    return self.ERROR
