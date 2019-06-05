from subject_page import *
from java.io import File
from javax.servlet import ServletContext

class theme(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)

    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            tmpl = self.params.safeGetStringParam("tmpl")
            if tmpl == "":
                self.subject.setThemeName(None)
            else:
                self.subject.setThemeName(tmpl)
            self.subjectService.saveOrUpdateSubject(self.subject)
            return self.SUCCESS
        
        # 查找所有样式
        themeFolder = application.getRealPath("/") + "theme" + File.separator + "subject" + File.separator        
        file = File(themeFolder)
        if file.exists() == True:
            theme_list = []
            fs = file.list()
            for theme in fs:
                fd = File(themeFolder + theme )
                if fd.isDirectory() == True:
                    theme_list.append(theme)            
            if len(theme_list) > 0:
                request.setAttribute("theme_list",theme_list)
                
        request.setAttribute("subject",self.subject)
        return "/WEB-INF/subjectmanage/theme.ftl"