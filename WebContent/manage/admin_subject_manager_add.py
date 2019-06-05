from cn.edustar.jitar.util import ParamUtil
from base_action import *
from cn.edustar.jitar.pojos import AccessControl
from cn.edustar.jitar.jython import BaseAdminAction

class admin_subject_manager_add(BaseAdminAction, SubjectMixiner):
    ADMIN_MAIN = "/WEB-INF/ftl/admin/main.ftl"
    ERROR = "/WEB-INF/ftl/Error.ftl"    
    SUCCESS = "/WEB-INF/ftl/success.ftl" 
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.subjectService = __spring__.getBean("subjectService")
        self.subject = None
        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            if accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SUPERADMIN, 0) == False and accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SYSTEMUSERADMIN, 0) == False:      # 没有用户管理权.
                self.addActionError(u"您不具有用户管理权限。")
                return ActionResult.ERROR
            
        subjectId = self.params.safeGetIntParam("subjectId")
        self.subject = self.subjectService.getSubjectById(subjectId)
        if self.subject == None:
            self.addActionError(u"无法加载学科.")
            return ActionResult.ERROR
        
        if request.getMethod() == "POST":
            accessControlService = __spring__.getBean("accessControlService")
            controlType = self.params.safeGetStringParam("controlType")
            uid = self.params.safeGetIntValues("uid")
            if not(controlType == "7" or controlType == "8" or controlType == "9"):
                self.addActionError(u"请选择管理员类型.")
                return ActionResult.ERROR
            #print "controlType = ", controlType
            #print "uid", uid
            if len(uid) < 1:
                self.addActionError(u"请选择一个用户.")
                return ActionResult.ERROR
            for userId in uid:
                # 检查是否已经是管理员
                if False == accessControlService.checkUserAccessControlIsExists(userId, int(controlType), self.subject.subjectId):                        
                    accessControl = AccessControl()
                    accessControl.setUserId(userId)
                    accessControl.setObjectType(int(controlType))
                    accessControl.setObjectId(self.subject.subjectId)
                    accessControl.setObjectTitle(self.subject.subjectName)
                    accessControlService.saveOrUpdateAccessControl(accessControl)
            response.sendRedirect("admin_subject_manager_by_subject_main_right.py?subjectId=" + str(self.subject.subjectId))
            
        request.setAttribute("subject", self.subject)
        
        return "/WEB-INF/ftl/admin/admin_subject_manager_add.ftl"
