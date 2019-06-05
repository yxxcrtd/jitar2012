from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage,PrepareCourseEdit
from java.text import SimpleDateFormat
from java.util import Date
from message_out import MessagePrint
from base_action import BaseAction

class coEditCommonPrepareCourse(BaseAction):
    
    def __init__(self):
        self.prepareCourseEdit = None
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.printer = MessagePrint()
        
    def execute(self):
        if self.loginUser == None:            
            self.printer.addMessage(u"请先<a href='../../login.jsp'>登录</a>，然后才能编辑")
            return self.printer.printMessage("login.jsp","")
        site_config = SiteConfig()
        site_config.get_config()
        
        self.prepareCourseId =  self.params.getIntParam("prepareCourseId")
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)        
        
        if self.prepareCourse == None:            
            self.printer.addMessage(u"未能加载备课。请重新选择一次备课。")
            return self.printer.printMessage("p/" + str(self.prepareCourse.prepareCourseId) + "/0/","")
        
        if self.canAdmin() == "false":
            self.printer.addMessage(u"只有 管理员 和 主备人（备课创建者）才可以修改。")
            return self.printer.printMessage("p/" + str(self.prepareCourse.prepareCourseId) + "/0/","")
        
        #self.prepareCourseEdit = self.pc_svc.getLastestPrepareCourseEdit(self.prepareCourseId, self.loginUser.userId)
        
        if self.prepareCourse.lockedUserId > 0 and self.prepareCourse.lockedUserId != self.loginUser.userId:
            user = self.user_svc.getUserById(self.prepareCourse.lockedUserId )
            if user == None:
                self.printer.addMessage(u"此备课已经被 未知的人 签出，你暂时不能进行编辑该备课。")
            else:
                self.printer.addMessage(u"此备课已经被 " + user.trueName + u" 签出，你暂时不能进行编辑该备课。")
            return self.printer.printMessage("p/" + str(self.prepareCourse.prepareCourseId) + "/0/","")
        else:
            #设置锁标记
            
            self.prepareCourse.setLockedUserId(self.loginUser.userId)
            self.prepareCourse.setLockedDate(Date())
            self.pc_svc.updatePrepareCourse(self.prepareCourse)
        
        if request.getMethod() == "POST":
            return self.saveOrUpdate()
        
        prepareCourseEdit_list = self.pc_svc.getPrepareCourseEditList(self.prepareCourseId)
        request.setAttribute("prepareCourseEdit_list", prepareCourseEdit_list)
        request.setAttribute("loginUser", self.loginUser) 
        request.setAttribute("prepareCourse", self.prepareCourse)
        request.setAttribute("head_nav", "cocourses")
        return "/WEB-INF/ftl/course/coEditCommonPrepareCourse.ftl"        
        
        
    def saveOrUpdate(self):       
        commonContent = self.params.safeGetStringParam("commonContent")
        optype = self.params.getIntParam("optype")
        if optype == 0:            
            if commonContent == None or commonContent == "":
                self.printer.addMessage(u"请输入共案内容。")
                return self.printer.printMessage("manage/course/showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourseId),"")
            if self.prepareCourse.lockedUserId != self.loginUser.userId:
                user = self.user_svc.getUserById(self.prepareCourse.lockedUserId )
                if user == None:
                    self.printer.addMessage(u"本次编辑已经被 未知的人 签出。")
                else:
                    self.printer.addMessage(u"本次编辑已经被 " + user.trueName + u" 签出。")                
                return self.printer.printMessage("p/" + str(self.prepareCourse.prepareCourseId) + "/0/","")            
        
            self.prepareCourseEdit = PrepareCourseEdit()        
            self.prepareCourseEdit.setContent(commonContent)
            self.prepareCourseEdit.setEditDate(Date())
            self.prepareCourseEdit.setLockStatus(0)
            self.prepareCourseEdit.setEditUserId(self.loginUser.userId)
            self.prepareCourseEdit.setPrepareCourseId(self.prepareCourseId)        
            self.pc_svc.updatePrepareCourseEdit(self.prepareCourseEdit)
            
            self.prepareCourse.setLockedUserId(0)
            self.prepareCourse.setCommonContent(commonContent)
            self.prepareCourse.setPrepareCourseEditId(self.prepareCourseEdit.prepareCourseEditId)
            self.pc_svc.updatePrepareCourse(self.prepareCourse)
        else:
            self.prepareCourse.setLockedUserId(0)
            self.pc_svc.updatePrepareCourse(self.prepareCourse)
            
        self.printer.addMessage(u"操作成功。")
        self.printer.msgDesc = ""
        return self.printer.printMessage("p/" + str(self.prepareCourse.prepareCourseId) + "/0/","")