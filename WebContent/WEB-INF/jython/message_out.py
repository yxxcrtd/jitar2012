from site_config import SiteConfig

class MessagePrint:
    def __init__(self):
        self.msgDesc = ""
        self.hasError = False
        
    def addMessage(self,strErrMsg):
        if strErrMsg != None:
            self.hasError = True
            self.msgDesc =  self.msgDesc + "<li>" + strErrMsg
    
    def printMessage(self, strRedUrl, isBack):
        
        site_config = SiteConfig()
        site_config.get_config()
        
        request.setAttribute("ErrMsg", self.msgDesc)
        request.setAttribute("redUrl", strRedUrl)
        request.setAttribute("isBack", isBack)
        return "/WEB-INF/ftl/action/result.ftl"
    
class ShowError:
    def __init__(self):
        site_config = SiteConfig()
        site_config.get_config()
        self.msg = ""        
    def printError(self):
        request.setAttribute("msg",self.msg)
        return "/WEB-INF/ftl/course/result.ftl"