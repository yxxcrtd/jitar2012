from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil
from javax.servlet import ServletContext
from cn.edustar.jitar.model import SiteUrlModel
from java.io import File
from cn.edustar.jitar.util import ConfigurationFileIni

class admin_rankfield(BaseAdminAction):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        user = self.loginUser
        if user == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"只有 admin 用户才可进行配置.")
            return ActionResult.ERROR
        self.type=self.params.safeGetStringParam("type")
        
        self.section=u"个人排行"
        if self.type=="":
            self.type="1"
            
        if self.type=="1":
            self.section=u"个人排行"
        elif self.type=="2":
            self.section=u"教研员排行"
        elif self.type=="3":
            self.section=u"协作组排行"
        elif self.type=="4":
            self.section=u"机构排行"
        elif self.type=="5":
            self.section=u"区县排行"
        else:
            self.section=u"个人排行"    
            
        self.iniFilePath=request.getRealPath("/ranklist.ini")
        self.sourceField=ConfigurationFileIni.getValue(self.iniFilePath,self.section,"sourceField","")
        self.sourceCaption=ConfigurationFileIni.getValue(self.iniFilePath,self.section,"sourceCaption","")
        self.sourceOrder=ConfigurationFileIni.getValue(self.iniFilePath,self.section,"sourceOrder","")
            
        cmd = self.params.safeGetStringParam("cmd")
        
        # print "cmd=",cmd
        
        if cmd=="save":
            self.save_field()
            
        userField=ConfigurationFileIni.getValue(self.iniFilePath,self.section,"userField","")
        userCaption=ConfigurationFileIni.getValue(self.iniFilePath,self.section,"userCaption","")
        userOrder=ConfigurationFileIni.getValue(self.iniFilePath,self.section,"userOrder","")
        
        request.setAttribute("sourceField", self.sourceField.split(','))
        request.setAttribute("sourceCaption", self.sourceCaption.split(','))
        request.setAttribute("sourceOrder", self.sourceOrder)
        
        request.setAttribute("orderField", self.sourceField.split(','))
        request.setAttribute("orderCaption", self.sourceCaption.split(','))
        
        request.setAttribute("userField", userField.split(','))
        request.setAttribute("userCaption", userCaption.split(','))
        request.setAttribute("userOrder", userOrder)
        
        request.setAttribute("type", self.type)
        request.setAttribute("section", self.section)
        request.setAttribute("iniFilePath", self.iniFilePath)
            
        return "/WEB-INF/ftl/admin/admin_rankfield.ftl"
     
    def save_field(self):
        sfields = self.params.getStringParam("field")
        if(sfields==None or sfields==""):
            return
        fields=sfields.split(',')
        #print "fields=",fields
        userfields=""
        usercaptions=""
        aF=self.sourceField.split(',')
        aC=self.sourceCaption.split(',')
        for field in fields:
            # print "f=",field
            if userfields=="":
                userfields=field
                i=-1
                for f in aF:
                   i=i+1
                   if f==field:
                       usercaptions=aC[i]
                       break
            else:
                userfields=userfields+","+field
                i=-1
                for f in aF:
                   i=i+1
                   if f==field:
                       usercaptions=usercaptions+","+aC[i]
                       break
        
        #print "userfields=",userfields
        #print "usercaptions=",usercaptions
        orderby = self.params.getStringParam("orderby")
        if(orderby==None):
            orderby=""
        ConfigurationFileIni.setValue(self.iniFilePath,self.section,"userField",userfields)
        ConfigurationFileIni.setValue(self.iniFilePath,self.section,"userCaption",usercaptions)
        ConfigurationFileIni.setValue(self.iniFilePath,self.section,"userOrder",orderby)