from cn.edustar.jitar.util import ParamUtil, EncryptDecrypt, CommonUtil
from base_action import BaseAction, ActionResult, SubjectMixiner
from cn.edustar.jitar.converter import DocConverter
from cn.edustar.jitar.converter import JacobPDFConverter
from cn.edustar.jitar.converter import SWFToolsSWFConverter
from java.io import File
from org.apache.commons.io import FileUtils
from com.chinaedustar.fcs.bi.service import FCSConverter

class previewResource(BaseAction, SubjectMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        if self.loginUser == None:
            request.setAttribute("error", u"请重新登录。")
            return "/WEB-INF/ftl/show_resource_swf.ftl"
  
        param = ParamUtil(request)
        resourceId = self.params.safeGetIntParam("resource")
        resourceService = __jitar__.getResourceService()    
        
        if resourceId == 0:
            request.setAttribute("error", u"缺少文件信息。")
            return "/WEB-INF/ftl/show_resource_swf.ftl"
        
        resource = resourceService.getResource(resourceId)
        if resource == None:
            request.setAttribute("error", u"无法加载资源。")
            return "/WEB-INF/ftl/show_resource_swf.ftl"
        #=======================================================================
        # enc = EncryptDecrypt("zhongjiaoqixing")
        # try:
        #    resfile = enc.decrypt(resfile)
        # except:
        #    request.setAttribute("error", u"解压缩过程中出现错误。")
        #    return "/WEB-INF/ftl/show_resource.ftl"
        # finally:
        #    encc = None
        #=======================================================================
        try:
            resfile = resource.href
            filename = resfile.lower()
            fileUserConfigPath = request.getSession().getServletContext().getInitParameter("userPath")
            if fileUserConfigPath == None or fileUserConfigPath == "":
                resfileapth = request.getSession().getServletContext().getRealPath("/" + resfile)
            else:
                if fileUserConfigPath.endswith("\\") == False:
                    fileUserConfigPath = fileUserConfigPath + "\\"
                resfileapth = fileUserConfigPath + resfile.replace("/","\\")
            
            ff = File(resfileapth)
            if ff.isFile() == False or ff.exists() == False:
                request.setAttribute("error", u"资源文件不存在，无法进行预览。")
                return "/WEB-INF/ftl/show_resource_swf.ftl"
            
            #这些文件直接显示
            if filename.endswith(".jpg") or filename.endswith(".gif") or filename.endswith(".png"):
               response.sendRedirect(CommonUtil.getSiteUrl(request) + resfile)
               return
            if filename.endswith(".txt"):
                content = FileUtils.readFileToString(ff, CommonUtil.getFileEncoding(resfileapth));
                request.setAttribute("content", content)
                return "/WEB-INF/ftl/show_resource_txt.ftl" 
           
            #PDF2SWFPath = request.getSession().getServletContext().getInitParameter("pdf2swfPath")
            #if PDF2SWFPath == None or PDF2SWFPath == "":
                #request.setAttribute("error", u"没有配置文件转换服务。")
                #return "/WEB-INF/ftl/show_resource_swf.ftl"
            
            if filename.endswith(".doc") or filename.endswith(".docx"):# or filename.endswith(".ppt") or filename.endswith(".pptx") or filename.endswith(".xls") or filename.endswith(".xlsx"):
                swf = resfileapth[0:resfileapth.find(".")] + ".swf"
                file = File(swf)
                if file.isFile() == False or file.exists() == False:                    
                    #converter = DocConverter(JacobPDFConverter(), SWFToolsSWFConverter(PDF2SWFPath))
                    #converter.convert(resfileapth)
                    #converter = None
                    
                    #转换服务
                    server_ip = request.getSession().getServletContext().getInitParameter("server_ip")
                    server_port = request.getSession().getServletContext().getInitParameter("server_port")
                    timeout = request.getSession().getServletContext().getInitParameter("timeout")
                    fcs = FCSConverter()
                    fcs.fcs(server_ip, server_port, timeout, resource.title, resfileapth[0:resfileapth.find(".")] + "." + filename.split(".")[-1], file.toString(), "0")
                    
                    request.setAttribute("resourceId", resourceId)
                    request.setAttribute("showWaiting", self.params.safeGetStringParam("showWaiting"))
                swf = CommonUtil.getSiteUrl(request) + resfile[0:resfile.find(".")] + ".swf"
                request.setAttribute("swf", swf)                
                return "/WEB-INF/ftl/show_resource_swf.ftl"
            #if filename.endswith(".pdf"):
                #swf = resfileapth[0:resfileapth.find(".")] + ".swf"
                #file = File(swf)
                #if file.isFile() == False or file.exists() == False:                    
                    #converter = SWFToolsSWFConverter(PDF2SWFPath)
                    #converter.convert2SWF(resfileapth)
                    #converter = None
                    #request.setAttribute("resourceId", resourceId)
                    #request.setAttribute("showWaiting", self.params.safeGetStringParam("showWaiting"))
                #swf = CommonUtil.getSiteUrl(request) + resfile[0:resfile.find(".")] + ".swf"
                #request.setAttribute("swf", swf)
                #return "/WEB-INF/ftl/show_resource_swf.ftl"
            
            if filename.endswith(".swf"):
                file = File(resfileapth)
                if file.isFile() == False or file.exists() == False:
                    request.setAttribute("error", u"swf 文件不存在，无法进行预览。")
                    return "/WEB-INF/ftl/show_resource_swf.ftl"            
                swf = CommonUtil.getSiteUrl(request) + resfile
                request.setAttribute("orginIsSwf", "")
                request.setAttribute("swf", swf)
                return "/WEB-INF/ftl/show_resource_swf.ftl"
            
            request.setAttribute("error", u"此文件不支持预览。")
            return "/WEB-INF/ftl/show_resource_swf.ftl"
        except BaseException,varExption:
            if varExption != None:
                request.setAttribute("error", u"预览此文件时出错，并且已经被捕获。<br/><br/>" + str(varExption))
            else:
                request.setAttribute("error", u"预览此文件时出错，并且并且没有捕获到错误信息。")
            return "/WEB-INF/ftl/show_resource_swf.ftl"
