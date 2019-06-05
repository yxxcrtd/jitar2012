from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import EncryptDecrypt
from site_config import SiteConfig

class platform_mashup(JythonBaseAction):
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()
        
        self.mashupService = __spring__.getBean("mashupService")
        platfotm_list = self.mashupService.getAllMashupPlatform(True)
        if self.loginUser != None:
            userGuid = self.loginUser.userGuid
            servletContext = request.getSession().getServletContext()
            privateKey = servletContext.getInitParameter("privateKey")
            if privateKey == None or privateKey == "":
                privateKey = "www.chinaedustar.com"
                
            des = EncryptDecrypt(privateKey)
            userGuid = des.encrypt(userGuid)
            request.setAttribute("encUserGuid",userGuid)
            
        request.setAttribute("platfotm_list",platfotm_list)
        request.setAttribute("head_nav", "platform")     
        return "/WEB-INF/mashup/platform_mashup.ftl"