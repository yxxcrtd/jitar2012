from cn.edustar.jitar.util import ParamUtil, EncryptDecrypt, CommonUtil
from base_action import SubjectMixiner
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *
from java.net import URLEncoder
from java.io import *
from java.lang import Byte, String
from org.python.apache.xerces.impl.dv.util import Base64
import jarray

class upload_private_prepare_course(PrepareCoursePageService):
    def __init__(self):        
        self.printer = response.getOutputStream()
        self.sessionId = None
        self.userTicket = None
        
    def showAlert(self, msg):        
        self.printer.write(String("<script>alert(\"" + msg + "\")</script>").getBytes())
        
    def execute(self):
        self.getBaseData()
        
        if self.loginUser == None:
            self.showAlert(u"请先登录。")
            return
        if CommonUtil.GetPrepareCourseFolder(request)[0] == "":
            self.printer.write(String(u"<script>alert(\"服务器没有配置文件存储路径 preparecoursefolder，不能采用这种方法进行备课。\")</script>").getBytes())
            return
        if self.prepareCourseId == 0:
            self.showAlert(u"无效的课程标识。")
            return
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.showAlert(u"没有加载到所请求的备课。")
            return
        if self.canView(prepareCourse) == False:
            self.showAlert(u"您无权查看本内容。")
            return
        
        if self.isPrepareCourseMember() == False:
            self.showAlert(u"您无权编辑备课内容。")
            return
        
        self.getCookie()
        if self.sessionId == None or self.userTicket == None:
            self.showAlert(u"登录信息已经失效，请重新登录。")
            return
        
        privateCourseMember = pc_svc.getPrepareCourseMemberByCourseIdAndUserId(prepareCourse.prepareCourseId, self.loginUser.userId)
        if privateCourseMember == None or privateCourseMember.status != 0:
            self.showAlert(u"不是该集备的成员，或者状态不正常。")
            return
        
        if privateCourseMember.contentType != 2 and privateCourseMember.contentType != 3 and privateCourseMember.contentType != 4:
            self.showAlert(u"不是文档类型的个案。")
            return
        
        # 如果是分开的服务器，就转向另外的服务器进行处理。
        prepareCourseFileServer = request.getSession().getServletContext().getInitParameter("PrepareCourseFileServer")        
        if prepareCourseFileServer != None and prepareCourseFileServer != "":
            url = prepareCourseFileServer
        else:
            url = self.get_site_url()
            
        url = Base64.encode(String(url).getBytes("utf-8"))
        url = url.replace("/", "_").replace("+", "-").replace("=", "~")
        
        oldFilePath = CommonUtil.GetPrepareCourseFolder(request)[0] + str(prepareCourse.prepareCourseId) + File.separator
        #print "oldFilePath=",oldFilePath
        #print "privateCourseMember.privateContent=",privateCourseMember.privateContent
        hasPrivateFile = False
        if privateCourseMember.privateContent != None and privateCourseMember.privateContent != "":
            hasPrivateFile = True
            oldFilePath = oldFilePath + privateCourseMember.privateContent
        ed = EncryptDecrypt()
        strPrepareCourseId = ed.encrypt(str(self.prepareCourseId) + "|0")
        ed = None
        fileExt = ".doc"
        if privateCourseMember.contentType == 3 or privateCourseMember.contentType == 4 or privateCourseMember.contentType == 5 : fileExt = ".docx"
        #if privateCourseMember.privateContent.find(".") > -1:
        #    fileExt = privateCourseMember.privateContent[privateCourseMember.privateContent.find("."):]
        f = File(oldFilePath)
        # 如果客户端已经存在相同的文件，则不同的浏览器会在文件名尾部加-1等这样的内容，所以，需要间隔符类间隔我们的数据
        docFileName = self.sessionId + "!" + self.userTicket + "!" + strPrepareCourseId + "!" + url + "!0!x" + fileExt
        
        if prepareCourseFileServer != None and prepareCourseFileServer != "" and privateCourseMember.privateContent != None and privateCourseMember.privateContent != "":
            if prepareCourseFileServer.endswith("/") == False:
                prepareCourseFileServer += "/"
            response.sendRedirect(prepareCourseFileServer + "downloadCourseFile?returnFileName=" + docFileName + "&courseId=" + str(self.prepareCourseId) + "&courseType=0&fileName=" + privateCourseMember.privateContent)
            return        
        
        if fileExt == ".doc":
            response.setContentType("application/msword")
        elif fileExt == ".docx":
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
        elif fileExt == ".ppt":
            response.setContentType("application/vnd.ms-powerpoint")
        elif fileExt == ".pptx":
            response.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation")
        else:
            response.setContentType("application/x-download")
            
        response.setHeader("Content-Disposition", "attachment;filename=\"" + docFileName + "\"")
            
        if hasPrivateFile == False or f.exists() == False or f.isFile() == False:            
            response.setHeader("Content-Length", "0")
            response.getOutputStream().close()
            return
        else:
            outputStream = None 
            inputStream = None
            try:
                inputStream = FileInputStream(f)
                length = f.length()
                response.setHeader("Content-Length", str(length))
                bytes = jarray.zeros(length, 'b')
                offset = 0
                numRead = 0
                outputStream = response.getOutputStream()
                while offset < length:
                    if numRead >= 0:
                        numRead = inputStream.read(bytes, offset, length - offset)
                        outputStream.write(bytes, 0, numRead)
                        offset = offset + numRead
                outputStream.flush()
                
            except BaseException, err:
                print "下载文件错误：", err
            finally:
                if inputStream != None:
                    inputStream.close()
                    inputStream = None 
                if outputStream != None:
                    outputStream.close()
            return
        
    def getCookie(self):
        self.userTicket = self.loginUser.loginName
        cookies = request.getCookies()
        if cookies == None:
            return
        for c in cookies:
            if c.getName() == "JSESSIONID":
                self.sessionId = c.getValue()
                
    def get_site_url(self):
        return CommonUtil.getSiteUrl(request)