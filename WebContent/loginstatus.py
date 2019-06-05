from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.model import Configure
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import ParamUtil,IPSeeker
from net.zdsoft.passport.service.client import PassportClient
from org.jasig.cas.client.util import CommonUtils
class loginstatus(JythonBaseAction):
    cfg_svc = __jitar__.configService
    
    def execute(self):
        self.config = self.cfg_svc.getConfigure()
        # 是否是浙大统一用户request.getServletContext().getServletRegistration("passportClientInit").getInitParameter("passportURL")
        self.passportURL = ""  
        if request.getServletContext().getServletRegistration("passportClientInit")!=None:
            self.passportURL = PassportClient.getInstance().getPassportURL()
        if self.passportURL == None:
            self.passportURL = ""
        if self.passportURL == "http://":
            self.passportURL = ""
        if self.passportURL != "":                
            self.passportServerId = PassportClient.getInstance().getServerId();
            self.passportVerifyKey = PassportClient.getInstance().getVerifyKey();
            request.setAttribute("passportURL", self.passportURL)
            request.setAttribute("passportServerId", self.passportServerId)
            request.setAttribute("passportVerifyKey", self.passportVerifyKey)
        
        #判断是否是东师理想的单点登录
        dsssoserverLogin = ""
        dsssoserver = ""
        if request.getServletContext().getFilterRegistration("filterchainproxy") != None:
            if request.getServletContext().getFilterRegistration("filterchainproxy").getClassName() == "dsidealsso.FilterChainProxy":
                dsssoserverLogin = request.getServletContext().getFilterRegistration("filterchainproxy").getInitParameter("casServerLoginUrl")
                dsssoserver = request.getServletContext().getFilterRegistration("filterchainproxy").getInitParameter("casServerUrlPrefix")
                request.setAttribute("dsssoserverLogin", dsssoserverLogin)
                request.setAttribute("dsssoserver", dsssoserver)
        
        # 根据后台配置，决定是否显示'注册的超连接'
        is_show_reg_link = self.config.getBoolValue(Configure.USER_REGISTER_ENABLED, True)
        if is_show_reg_link :
            is_show_reg_link = "true"
        else:
            is_show_reg_link = "false"
        request.setAttribute("is_show_reg_link", is_show_reg_link)
        
        # 根据后台配置，决定是否显示''
        is_show_verifyCode = self.config.getBoolValue(Configure.SITE_VERIFYCODE_ENABLED, True)
        if is_show_verifyCode :
            is_show_verifyCode = "true"
        else:
            is_show_verifyCode = "false"
        request.setAttribute("is_show_verifyCode", is_show_verifyCode)
        
        casServerUrl = ""
        if request.getServletContext().getFilterRegistration("CAS-Authentication-Filter")!=None:
            casServerUrl=request.getServletContext().getFilterRegistration("CAS-Authentication-Filter").getInitParameter("casServerLoginUrl")
            if casServerUrl=="" :
               casServerUrl=CommonUtils.getCasServerUrlPrefix(request)
            else:    
                if casServerUrl[-1]=='/':
                   casServerUrl=casServerUrl[:len(casServerUrl)-1]
                if casServerUrl[-6:]=='/login':
                   casServerUrl=casServerUrl[:-6]        
                
        request.setAttribute("casServerUrl",casServerUrl)

        if request.getServerPort() == 80:
            root = request.getScheme() + "://" + request.getServerName() +request.getContextPath()
        else:
            root = request.getScheme() + "://" + request.getServerName() + ":" + str(request.getServerPort()) +request.getContextPath()
        request.setAttribute("siteserverurl",root)
             
        if request.getServerPort() == 80:
            rootUrl = request.getScheme() + "://" + request.getServerName() 
        else:
            rootUrl = request.getScheme() + "://" + request.getServerName() + ":" + str(request.getServerPort())
        
        serverName=""    
        if request.getServletContext().getFilterRegistration("CAS-Authentication-Filter")!=None:
            serverName=request.getServletContext().getFilterRegistration("CAS-Authentication-Filter").getInitParameter("serverName")
            if serverName=="":
                serverName=root
        request.setAttribute("serverName",serverName)
        
        callbackLogOutUrl=""
        if request.getServletContext().getFilterRegistration("CAS-Authentication-Filter")!=None:
            callbackLogOutUrl=request.getServletContext().getFilterRegistration("CAS-Authentication-Filter").getInitParameter("callbackLogOutUrl")
            if callbackLogOutUrl=="":
                callbackLogOutUrl=root
            elif callbackLogOutUrl[0:7]!="http://" and callbackLogOutUrl[0:8]!="https://":
                if callbackLogOutUrl[0]=="/":
                    callbackLogOutUrl=root+callbackLogOutUrl
                else:    
                    callbackLogOutUrl=root+"/"+callbackLogOutUrl
                
        request.setAttribute("callbackLogOutUrl",callbackLogOutUrl)
            
        #判断是否登录过
        if self.loginUser != None:
            genUser = session.getAttribute(User.SESSION_LOGIN_GENUSER_KEY)
            if genUser != None:
                ip = IPSeeker.getInstance()
                request.setAttribute("lastLoginAddress", ip.getAddress(genUser.getLastLoginIp()))
                
        if self.passportURL != "":
            return "/zdsoft/login.ftl"
        elif dsssoserverLogin != "":
            return "/dsideal/login.ftl"
        else:
            return "/WEB-INF/ftl/mengv1/index/login.ftl"