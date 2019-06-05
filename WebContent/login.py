from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from site_news_query import SiteNewsQuery
from user_query import UserQuery
from cn.edustar.jitar.model import Configure
from cn.edustar.jitar.util import ProductInfo
from org.jasig.cas.client.util import CommonUtils
from java.lang import *

class login:
    
    cfg_svc = __jitar__.configService
    
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):  
        self.config = self.cfg_svc.getConfigure()
        
        dsssoserverLogin = ""
        dsssoserver = ""
        if request.getServletContext().getFilterRegistration("filterchainproxy") != None:
            if request.getServletContext().getFilterRegistration("filterchainproxy").getClassName() == "dsidealsso.FilterChainProxy":
                dsssoserverLogin = request.getServletContext().getFilterRegistration("filterchainproxy").getInitParameter("casServerLoginUrl")
                dsssoserver = request.getServletContext().getFilterRegistration("filterchainproxy").getInitParameter("casServerUrlPrefix")
                        
        self.passportURL = ""
        if request.getServletContext().getServletRegistration("passportClientInit")!=None:
            self.passportURL = PassportClient.getInstance().getPassportURL()
            if self.passportURL == None:
                self.passportURL = ""
        if self.passportURL != "":
            if request.getContextPath()!="":
                return "/"+request.getContextPath()+"/index.py"
            else:
                return "/index.py"
        elif dsssoserverLogin != "":
            if request.getContextPath()!="":
                return "/"+request.getContextPath()+"/dsideal/index.jsp"
            else:
                return "/dsideal/index.jsp"
        else:
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
            casServerUrl = request.getServletContext().getFilterRegistration("CAS-Authentication-Filter").getInitParameter("casServerLoginUrl")
            if casServerUrl == "" :
               casServerUrl = CommonUtils.getCasServerUrlPrefix(request)
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
                    
            preview = self.params.safeGetStringParam("preview")
            if preview != "":
                request.setAttribute("preview_theme_url", preview)
            
            url = self.params.safeGetStringParam("redUrl")
            
            
            if url == None or url=="":
                url = self.params.safeGetStringParam("ru")
                
            if url == None or url=="":
                if request.getContextPath()!="":
                    url = ""+request.getContextPath()+"/index.py"
                else:
                    url = "/index.py"
            url=str(url)
            if url[0:7]!="http://" and url[0:8]!="https://":
                if url[0]!="/":
                   url="/"+url 
                if request.getServerPort() == 80:
                    url = request.getScheme() + "://" + request.getServerName() + url
                else:
                    url = request.getScheme() + "://" + request.getServerName() + ":" + str(request.getServerPort()) + url
            request.setAttribute("redUrl",url)
            serverName=request.getServletContext().getFilterRegistration("CAS-Authentication-Filter").getInitParameter("serverName")
            if serverName=="":
                serverName=root
            request.setAttribute("serverName",serverName)
            
            callbackLogOutUrl=request.getServletContext().getFilterRegistration("CAS-Authentication-Filter").getInitParameter("callbackLogOutUrl")
            if callbackLogOutUrl=="":
                callbackLogOutUrl=root
            elif callbackLogOutUrl[0:7]!="http://" and callbackLogOutUrl[0:8]!="https://":
                if callbackLogOutUrl[0]=="/":
                    callbackLogOutUrl=root+callbackLogOutUrl
                else:    
                    callbackLogOutUrl=root+"/"+callbackLogOutUrl
                    
            request.setAttribute("callbackLogOutUrl",callbackLogOutUrl)
            
            response.contentType = "text/html; charset=UTF-8"
            return "/WEB-INF/ftl/userlogin.ftl"
        