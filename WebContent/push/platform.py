from cn.edustar.jitar.util import EncryptDecrypt
from cn.edustar.jitar.util import ParamUtil

class platform:
    def execute(self):
        self.params = ParamUtil(request)
        
        self.userGuid = self.params.safeGetStringParam("g")
        servletContext = request.getSession().getServletContext()
        privateKey = servletContext.getInitParameter("privateKey")
        if privateKey == None or privateKey == "":
            privateKey = "www.chinaedustar.com"
        if self.userGuid != "":            
            des = EncryptDecrypt(privateKey)
            self.userGuid = des.decrypt(self.userGuid)
            session.setAttribute("platuser",self.userGuid)
        
        response.sendRedirect(self.get_site_url())
        
    def get_site_url(self):
        url = request.getScheme() + "://" + request.getServerName()
        if request.getServerPort() != 80:
            url = url + ":" + str(request.getServerPort())
        url = url + request.getContextPath() + "/"
        return url