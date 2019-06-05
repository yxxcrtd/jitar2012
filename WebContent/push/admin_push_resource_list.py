from resource_query import ResourceQuery
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.push import PushData
from java.net import URLEncoder
from javax.servlet.http import HttpServletResponse
from cn.edustar.jitar.jython import BaseAdminAction
from base_action import ActionResult

class admin_push_resource_list(BaseAdminAction, ActionResult):    
    ERROR = "/WEB-INF/ftl/Error.ftl"
    ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl"
    ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    LOGIN = "/login.jsp"
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.resourceService = __spring__.getBean("resourceService")        
        self.unitService = __spring__.getBean("unitService")
        self.userService = __spring__.getBean("userService")
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
    
    # 为了界面显示友好，每次只推送一篇文章
    def push_content(self):
        resourceId = self.params.safeGetIntParam("resourceId")
        # 组合 xml 格式的字符串，以便进行提交，
        title = ""
        documentType = "resource"
        href = ""
        author = ""
        unitName = ""
        unitTitle = ""
        pushUserName = ""
        platformGuid = ""
        platformName = ""
        orginId = 0
        
        productConfigService = __spring__.getBean("ProductConfigService")
        productConfigService.isValid()
        platformGuid = productConfigService.getProductGuid()
        platformName = productConfigService.getProductName()
        
        #提交以下字段信息：
        resource = self.resourceService.getResource(resourceId)
        if resource == None:
            response.getWriter().write("Resource not found")
            return
        
        pushUser = self.userService.getUserById(resource.pushUserId)
        if pushUser == None:
            pushUserName = u"无法加载指定的用户"
        else:
            pushUserName = pushUser.trueName
            if pushUserName == None or pushUserName == "":
                pushUserName = pushUser.loginName
                
                
        orginId = resource.id
        title = resource.title
        href = self.get_site_url()
        
        user = self.userService.getUserById(resource.userId)
        
        if user == None:
            response.getWriter().write("Author not found")
            return
        author = user.trueName
        if author == None or author == "":
            author = user.loginName
        
        if resource.unitId != None:
            unit = self.unitService.getUnitById(resource.unitId)
            if unit != None:
                unitName = unit.unitName
                unitTitle = unit.unitTitle
        
        xml = "<root>"
        xml = xml + "<title>" + CommonUtil.xmlEncode(title) + "</title>"
        xml = xml + "<documentType>" + documentType + "</documentType>"
        xml = xml + "<href>" + CommonUtil.xmlEncode(href) + "</href>"
        xml = xml + "<author>" + CommonUtil.xmlEncode(author) + "</author>"
        xml = xml + "<unitName>" + CommonUtil.xmlEncode(unitName) + "</unitName>"
        xml = xml + "<unitTitle>" + CommonUtil.xmlEncode(unitTitle) + "</unitTitle>"
        xml = xml + "<platformGuid>" + CommonUtil.xmlEncode(platformGuid) + "</platformGuid>"
        xml = xml + "<platformName>" + CommonUtil.xmlEncode(platformName) + "</platformName>"
        xml = xml + "<orginId>" + str(orginId) + "</orginId>"
        xml = xml + "<pushUserName>" + CommonUtil.xmlEncode(pushUserName) + "</pushUserName>"
        xml = xml + "</root>"
        postData = "data=" + URLEncoder.encode(xml, "utf-8")
        
        pd = PushData()
        ret = pd.Push(postData, self.topsiteUrl + "mashup/receiver.py")
        if ret == True:
            result = pd.getReturnResult()
            if result == "LOCKED":
                response.getWriter().write("LOCKED")
                return
            elif result == "DELETED":
                response.getWriter().write("DELETED")
                return
            
            self.resourceService.setPushState(resource, 1)
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
    
    def delete(self):
        guids = self.params.safeGetIntValues("guid")
        for resourceId in guids:
            resource = self.resourceService.getResource(resourceId)
            if resource != None:
                self.resourceService.setPushState(resource, 0)                
        return self.show_list()
    
    def get_site_url(self):
        url = request.getScheme() + "://" + request.getServerName()
        if request.getServerPort() != 80:
            url = url + ":" + str(request.getServerPort())
        url = url + request.getContextPath() + "/"
        return url
                
    def show_list(self):
        qry = ResourceQuery(""" r.resourceId,r.title,r.userId,r.pushState,r.unitId,r.href,r.pushUserId """)
        qry.pushState = 2 # 待推送
        pager = self.params.createPager()
        pager.itemName = u"资源"
        pager.itemUnit = u"个"
        pager.pageSize = 25
        pager.totalRows = qry.count()
        resource_list = qry.query_map(pager)
        request.setAttribute("resource_list", resource_list)
        request.setAttribute("pager", pager)
        return "/WEB-INF/push/admin_push_resource_list.ftl" 
