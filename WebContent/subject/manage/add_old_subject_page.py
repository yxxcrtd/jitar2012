from cn.edustar.jitar.pojos import SubjectWebpart
from cn.edustar.jitar.util import ParamUtil

class add_old_subject_page:
    def execute(self):
        self.subjectService = __spring__.getBean("subjectService")
        subject_list = self.subjectService.getSubjectList()
        for subject in subject_list:
            subject_webpart_list = self.subjectService.getSubjectWebpartList(subject.subjectId, None)
            if len(subject_webpart_list) == 0:                
                i = 0
                for m in SubjectWebpart.MODULE_NAME:
                    i = i + 1
                    subjectWebpart = SubjectWebpart()
                    subjectWebpart.setModuleName(m)
                    subjectWebpart.setRowIndex(i)
                    subjectWebpart.setVisible(True)
                    subjectWebpart.setSystemModule(1)
                    subjectWebpart.setSubjectId(subject.subjectId)
                    if i < 7:
                       subjectWebpart.setWebpartZone(3)
                    elif i <13 :
                        subjectWebpart.setWebpartZone(4)
                    else:
                        subjectWebpart.setWebpartZone(5)
                    self.subjectService.saveOrUpdateSubjectWebpart(subjectWebpart)
        subjectId = self.params.safeGetIntParam("id")
        if subjectId > 0:
            response.sendRedirect("../index.py?id="+str(subjectId))
            return
        else:
            return "/WEB-INF/ftl/success.ftl"