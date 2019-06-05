from user_query import UserQuery
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.push import PushData
from java.net import URLEncoder
from javax.servlet.http import HttpServletResponse
from cn.edustar.jitar.jython import BaseAdminAction
from base_action import ActionResult

class admin_push_famous_list(BaseAdminAction, ActionResult):
    
    ERROR = "/WEB-INF/ftl/Error.ftl"
    ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl"
    ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    LOGIN = "/login.jsp"
    
    def __init__(self):
        self.params = ParamUtil(request)   
        self.unitService = __spring__.getBean("unitService")
        self.userService = __spring__.getBean("userService")
        self.subjectService = __spring__.getBean("subjectService")
        self.topsiteUrl = None
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        if self.canAdmin() == False:
            self.addActionError(u"没有管理的权限.")
            return self.ERROR        
        
        cfg_svc = __spring__.getBean("configService")
        config = cfg_svc.getConfigure()
        if config == None:
            self.addActionError(u"不能加载配置对象 !")
            return self.ERROR
        if None == config["topsite_url"]:
            self.addActionError(u" topsite_url 配置项没配置!")
            return self.ERROR
        
        self.topsiteUrl = str(config["topsite_url"])
        cmd = self.params.safeGetStringParam("cmd")
        
        if cmd == "push":
            return self.push_content()
        elif cmd == "check":
            return self.check()
        elif cmd == "delete":
            return self.delete()
        else:
            return self.show_list()
    
    # 为了界面显示友好，每次只推送一个对象
    def push_content(self):
        userId = self.params.safeGetIntParam("userId")
        # 组合 xml 格式的字符串，以便进行提交，
        orginId = 0
        contentType = "blog"
        href = ""
        trueName = ""
        description = ""
        icon = ""
        subjectName = ""
        gradeName = ""
        unitName = ""
        unitTitle = ""
       
        unitId = 0
        gradeId = 0
        metaSubjectId = 0
      
        pushUserName = ""
        platformGuid = ""
        platformName = ""
        
        productConfigService = __spring__.getBean("ProductConfigService")
        productConfigService.isValid()
        platformGuid = productConfigService.getProductGuid()
        platformName = productConfigService.getProductName()
        
        #提交以下字段信息：
        href = self.get_site_url()
        user = self.userService.getUserById(userId)
        if user == None:
            response.getWriter().write("User not found")
            return
        
        pushUser = self.userService.getUserById(user.pushUserId)
        if pushUser == None:
            pushUserName = u"无法加载指定的用户"
        else:
            pushUserName = pushUser.trueName
            if pushUserName == None or pushUserName == "":
                pushUserName = pushUser.loginName            
            
        orginId = user.getUserId()
        trueName = user.trueName
        if trueName == None:trueName = user.nickName
        if trueName == None:trueName = user.loginName
        if user.getBlogIntroduce() != None:description = user.getBlogIntroduce()
        if user.getUserIcon() != None:
            icon = user.getUserIcon()
        else:
            icon = "images/default.gif"
        if user.getSubjectId() != None:
            metaSubject = self.subjectService.getMetaSubjectById(user.getSubjectId())
            if metaSubject != None:
                metaSubjectId = user.getSubjectId()
                subjectName = metaSubject.msubjName
                
        if user.getGradeId() != None:
            grade = self.subjectService.getGrade(user.getGradeId())
            if grade != None:
                gradeId = user.getGradeId()
                gradeName = grade.gradeName
                
        
        if user.getUnitId() != None:
            unit = self.unitService.getUnitById(user.getUnitId())
            if unit != None:
                unitId = user.getUnitId()
                unitName = unit.unitName 
                unitTitle = unit.unitTitle
                
                
        xml = "<root>"
        xml = xml + "<orginId>" + str(orginId) + "</orginId>"
        xml = xml + "<contentType>" + contentType + "</contentType>"
        xml = xml + "<href>" + CommonUtil.xmlEncode(href) + "</href>"
        xml = xml + "<trueName>" + CommonUtil.xmlEncode(trueName) + "</trueName>"
        xml = xml + "<description>" + CommonUtil.xmlEncode(description) + "</description>"
        xml = xml + "<icon>" + CommonUtil.xmlEncode(icon) + "</icon>"
        xml = xml + "<subjectName>" + CommonUtil.xmlEncode(subjectName) + "</subjectName>"
        xml = xml + "<gradeName>" + CommonUtil.xmlEncode(gradeName) + "</gradeName>"
        xml = xml + "<unitName>" + CommonUtil.xmlEncode(unitName) + "</unitName>"
        xml = xml + "<unitTitle>" + CommonUtil.xmlEncode(unitTitle) + "</unitTitle>"  
        xml = xml + "<unitId>" + str(unitId) + "</unitId>"
        xml = xml + "<gradeId>" + str(gradeId) + "</gradeId>"
        xml = xml + "<metaSubjectId>" + str(metaSubjectId) + "</metaSubjectId>"
        xml = xml + "<platformGuid>" + CommonUtil.xmlEncode(platformGuid) + "</platformGuid>"
        xml = xml + "<platformName>" + CommonUtil.xmlEncode(platformName) + "</platformName>"
        xml = xml + "<pushUserName>" + CommonUtil.xmlEncode(pushUserName) + "</pushUserName>"
        xml = xml + "</root>"
        postData = "data=" + URLEncoder.encode(xml, "utf-8")        
        
        pd = PushData()
        ret = pd.Push(postData, self.topsiteUrl + "mashup/receiver.py?type=bloggroup")
        if ret == True:
            #print "postData=",ret
            result = pd.getReturnResult()
            if result == "LOCKED":
                response.getWriter().write("LOCKED")
                return
            elif result == "DELETED":
                response.getWriter().write("DELETED")
                return

            self.userService.setPushed(user)
            response.getWriter().write("OK")                
        else:
            response.getWriter().write("ERROR")
        return
    
    def check(self):        
        pd = PushData()
        if pd.checkServerState(self.topsiteUrl + "mashup/check_status.py") == True:            
            response.setStatus(HttpServletResponse.SC_OK)
            response.getWriter().write("OK")
        else:
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            response.getWriter().write("ERROR")
        return
    
    def get_site_url(self):
        url = request.getScheme() + "://" + request.getServerName()
        if request.getServerPort() != 80:
            url = url + ":" + str(request.getServerPort())
        url = url + request.getContextPath() + "/"
        return url
    
    def delete(self):
        guids = self.params.safeGetIntValues("guid")
        for userId in guids:
            user = self.userService.getUserById(userId)
            if user != None:
                user.setPushState(0)
                self.userService.updateUser(user)                
        return self.show_list()         
        
    def show_list(self):
        qry = UserQuery(""" u.userId, u.loginName, u.nickName, u.trueName, u.userIcon, u.userStatus, u.pushState,u.pushUserId,
                             u.email, u.subjectId, u.gradeId, u.createDate, u.blogIntroduce,
                             subj.subjectName, grad.gradeName, unit.unitName, u.positionId
                              """)
        qry.userStatus = 0
        qry.pushState = 2 # 待推送 
        qry.isFamous = True
        pager = self.params.createPager()
        pager.itemName = u"名师"
        pager.itemUnit = u"位"
        pager.pageSize = 25
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)
        request.setAttribute("user_list", user_list)
        request.setAttribute("pager", pager)
        return "/WEB-INF/push/admin_push_famous_list.ftl" 
