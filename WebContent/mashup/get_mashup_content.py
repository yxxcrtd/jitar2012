from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import EncryptDecrypt

ENABLE_CACHE = True
if ENABLE_CACHE:
    cache = __jitar__.cacheProvider.getCache('main')
else:
    cache = NoCache()
    
class get_mashup_content(JythonBaseAction):
    
    def execute(self):
        self.getEncryptLogin()
        self.show_platform()        
        return "/WEB-INF/mashup/get_mashup_content.ftl"
    
    def show_platform(self):
        cache_key = "platfotm_list"
        platfotm_list = cache.get(cache_key)
        if platfotm_list == None:
            mashupService = __spring__.getBean("mashupService")
            platfotm_list = mashupService.getAllMashupPlatform(True)                        
            cache.put(cache_key, platfotm_list)
        request.setAttribute("platfotm_list", platfotm_list)
        
    def getEncryptLogin(self):
        if self.loginUser != None:
            userGuid = self.loginUser.userGuid
            servletContext = request.getSession().getServletContext()
            privateKey = servletContext.getInitParameter("privateKey")
            if privateKey == None or privateKey == "":
                privateKey = "www.chinaedustar.com"

            des = EncryptDecrypt(privateKey)
            userGuid = des.encrypt(userGuid)
            request.setAttribute("encUserGuid", userGuid)