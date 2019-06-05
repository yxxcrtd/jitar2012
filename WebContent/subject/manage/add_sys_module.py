from subject_page import *
from cn.edustar.jitar.pojos import SubjectWebpart

class add_sys_module(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)

    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        webpartId = self.params.safeGetIntParam("webpartId")
        if webpartId == 0:
            subjectWebpart = SubjectWebpart()
            subjectWebpart.setModuleName("")
            subjectWebpart.setSystemModule(False)
            subjectWebpart.setSubjectId(self.subject.subjectId)
            subjectWebpart.setWebpartZone(1)
            subjectWebpart.setRowIndex(0)
            subjectWebpart.setContent("")
            subjectWebpart.setVisible(True)
            subjectWebpart.setSysCateId(0)
            subjectWebpart.setShowCount(8)
            subjectWebpart.setShowType(1)
            subjectWebpart.setPartType(1)
        else:
            subjectWebpart = self.subjectService.getSubjectWebpartById(webpartId)        
            
        if request.getMethod() == "POST":
            moduleName = self.params.safeGetStringParam("moduleName")
            webpartZone = self.params.safeGetIntParam("webpartZone")
            sysCateId = self.params.safeGetIntParam("sysCateId")
            showCount = self.params.safeGetIntParam("showCount")
            if showCount == 0:
                showCount = 8
            if "" == moduleName:
                self.addActionError(u"请输入模块名称。")
                return self.ERROR
            
            if 0 == webpartZone:
                self.addActionError(u"请选择模块位置。")
                return self.ERROR           
            
            if webpartId == 0:                
                subjectWebpart.setSubjectId(self.subject.subjectId)
                subjectWebpart.setRowIndex(0)
                subjectWebpart.setSystemModule(False)
                subjectWebpart.setVisible(True)
            subjectWebpart.setModuleName(moduleName)
            subjectWebpart.setDisplayName(moduleName)
            subjectWebpart.setWebpartZone(webpartZone)
            subjectWebpart.setContent(None)
            subjectWebpart.setSysCateId(sysCateId)
            subjectWebpart.setShowCount(showCount)
            self.subjectService.saveOrUpdateSubjectWebpart(subjectWebpart)
            response.sendRedirect("custormmodule.py?id=" + str(self.subject.subjectId))
                
        request.setAttribute("subjectWebpart", subjectWebpart)
        request.setAttribute("subject", self.subject)
        self.get_article_cate_list()
        return "/WEB-INF/subjectmanage/add_sys_module.ftl"
    
    def get_article_cate_list(self):
        article_cates = __jitar__.categoryService.getCategoryTree("default")
        request.setAttribute("article_cates", article_cates)
    
