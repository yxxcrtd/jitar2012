from unit_page import UnitBasePage
from user_query import UserQuery
from cn.edustar.jitar.pojos import User
from base_action import *
from java.io import File
from cn.edustar.jitar.util import CommonUtil

class unit_modi_userinfo(UnitBasePage, SubjectMixiner):
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
        if self.isUnitAdmin() == False and self.isUserAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        userId = self.params.safeGetIntParam("userId")
        self.user = self.userService.getUserById(userId)
        if self.user == None:
            self.addActionError(u"无法加载用户对象，该用户可能已经不存在。")
            return self.ERROR
        
        if self.user.unitId != self.unit.unitId:
            self.addActionError(u"你所要修改的用户不是你机构的用户，无法进行修改。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            return self.save_post()
        self.user_info()
        
        request.setAttribute("unit", self.unit)
        return "/WEB-INF/unitsmanage/unit_modi_userinfo.ftl"
    
    def save_post(self):
        self.user.qq = self.params.getStringParam("QQ", "")
        self.user.idCard = self.params.getStringParam("IDCard", "")
        self.user.userIcon = self.params.getStringParam("userIcon", "images/default.gif")
        self.user.positionId = self.params.getIntParam("role")
        self.user.nickName = self.params.getStringParam("nickName")
        self.user.trueName = self.params.getStringParam("trueName")
        self.user.email = self.params.getStringParam("email")
        self.user.blogName = self.params.getStringParam("blogName")
        self.user.userTags = self.params.getStringParam("userTags")
        self.user.blogIntroduce = self.params.getStringParam("blogIntroduce")
        self.user.gender = self.params.getIntParam("gender")
        #self.user.subjectId = self.params.getIntParamZeroAsNull("subjectId")
        #self.user.gradeId = self.params.getIntParamZeroAsNull("gradeId")
        #self.user.categoryId = self.params.getIntParamZeroAsNull("categoryId")
        self.user.userStatus = 0
        try:
            self.userService.updateUser(self.user)
            self.userService.updateUserStatus(self.user, User.USER_STATUS_NORMAL)
        except:
            self.addActionError(u"保存用户信息时失败!")
            return self.ERROR
        errInfo = self.userService.getErrorInfo();
        if len(errInfo) > 0 :
            self.addActionError(u"保存用户信息时失败!"+errInfo)
            return self.ERROR
        return self.SUCCESS
    
    def user_info(self):        
        request.setAttribute("user", self.user)
        self.putSubjectList()
        self.putGradeList()
        self.putUserCategories()
    
        folder = File(application.getRealPath("/images/deficon/"));
        if folder.exists() == False or folder.isDirectory() == False:
            return
        
        icons = []
        for f in folder.listFiles():
            ext = CommonUtil.getFileExtension(f.getName()).lower()
            if "jpg" == ext or "gif" == ext or "png" == ext:
                icons.append("images/deficon/" + f.getName())
        if len(icons) > 0:
            request.setAttribute("icon_list", icons)
            
    def putUserCategories(self):
        user_categories = __jitar__.categoryService.getCategoryTree('blog')
        request.setAttribute("syscate_tree", user_categories)
        
