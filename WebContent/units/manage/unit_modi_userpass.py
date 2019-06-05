from unit_page import UnitBasePage
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.util import MD5
class unit_modi_userpass(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.userService = __spring__.getBean("userService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        if self.userService == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 userService 节点。")
            return self.ERROR
                
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        if self.isUnitAdmin() == False and self.isUserAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        userId = self.params.safeGetIntParam("userId")
        self.user = self.userService.getUserById(userId)
        
        if self.user == None:
            self.addActionError(u"该用户不存在。")
            return self.ERROR
        
        if self.user.unitId != self.unit.unitId:
            self.addActionError(u"你所要修改的用户不是你机构的用户，无法进行修改。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.save_post()
            return self.SUCCESS
        request.setAttribute("user", self.user)
        return "/WEB-INF/unitsmanage/unit_modi_userpass.ftl"
    
    def save_post(self):
        pwd1 = self.params.safeGetStringParam("pwd1")
        pwd2 = self.params.safeGetStringParam("pwd2")
        
        if pwd1 != pwd2:
            self.addActionError(u"两次输入的密码不一致。")
            return self.ERROR
        try:
            self.userService.resetPassword(self.user.loginName, pwd2)
        except:
            errInfo = self.userService.getErrorInfo()
            if len(errInfo) > 0 :
                self.addActionError(u""+errInfo)
            else:    
                self.addActionError(u"更改密码失败")
            return self.ERROR
        errInfo = self.userService.getErrorInfo()
        if len(errInfo) > 0 :
            self.addActionError(u""+errInfo)
            return self.ERROR
            