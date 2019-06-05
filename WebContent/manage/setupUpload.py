from cn.edustar.jitar.pojos import Category
from cn.edustar.jitar.data import Command
from java.lang import RuntimeException
from base_action import *
from cn.edustar.jitar.util import XmlUtil; 

class setupUpload(ActionExecutor):
    
    # 构造.
    def __init__(self):
        self.params = ParamUtil(request)
        return
    # 从 ActionExecutor 调用, 根据 cmd 进行不同处理.
    def dispatcher(self, cmd):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return ActionResult.ERROR
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"只有超级管理员才能进行管理。")
            return ActionResult.ERROR
        if cmd == None or cmd == "" : cmd = "list"
        if cmd == "list":
            return self.list()
        if cmd == "save":
            return self.save()
    def save(self):
        filesize = self.params.getIntParam("filesize")
        filetype = self.params.getStringParam("filetype")
        if filesize==None:
            filesize=0
        filesize=filesize*1024*1024
        filesize=str(filesize)
        # print(filesize)
        # print(filetype)
        path=request.getRealPath("/WEB-INF/classes/configuration/struts_resource.xml")
        x=XmlUtil.loadXml(path)
        XmlUtil.setNodeText(x,"struts//package[@name='resource']//action[@name='resource']//interceptor-ref[@name='fileUpload']//param[@name='allowedTypes']",filetype)
        XmlUtil.setNodeText(x,"struts//package[@name='resource']//action[@name='resource']//interceptor-ref[@name='fileUpload']//param[@name='maximumSize']",filesize)
        XmlUtil.saveXml_UTF8(x,path)
        return ActionResult.SUCCESS
    
    def list(self):    
        path=request.getRealPath("/WEB-INF/classes/configuration/struts_resource.xml")
        x=XmlUtil.loadXml(path)
        s=XmlUtil.getNodeText(x,"struts//package[@name='resource']//action[@name='resource']//interceptor-ref[@name='fileUpload']//param[@name='allowedTypes']")
        request.setAttribute("param",s)
        s=XmlUtil.getNodeText(x,"struts//package[@name='resource']//action[@name='resource']//interceptor-ref[@name='fileUpload']//param[@name='maximumSize']")
        s=int(s) / 1024
        s=int(s) / 1024
        request.setAttribute("size",s)        
        return "/WEB-INF/ftl/admin/setup_resourceUpload.ftl"