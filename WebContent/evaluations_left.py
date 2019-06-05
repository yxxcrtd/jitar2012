from base_action import *
class evaluations_left(SubjectMixiner):
    def execute(self):
        subjectService = __jitar__.subjectService
        subject_list = subjectService.getMetaSubjectList()
        outHtml = ""
        for s in subject_list:
            msid = s.getMsubjId()
            outHtml = outHtml + "d.add(" + str(msid) + ",0,'" + s.getMsubjName() + "','evaluations_middle.py?subjectId=" + str(msid) + "','','_middle');"
            gradeIdList = subjectService.getMetaGradeListByMetaSubjectId(msid)
                             
            if gradeIdList != None:
                for gid in gradeIdList:
                    outHtml = outHtml + "d.add(" + str(msid) + str(gid.getGradeId()) + "," + str(msid) + ",'" + gid.getGradeName() + "','evaluations_middle.py?subjectId=" + str(msid) + "&gradeId=" + str(gid.getGradeId()) + "&target=child','','_middle');"
                
                    gradeLevelList = subjectService.getGradeLevelListByGradeId(gid.getGradeId())
                    for glevel in gradeLevelList:
                        outHtml = outHtml + "d.add(" + str(msid) + str(gid.getGradeId()) + str(glevel.getGradeId()) + "," + str(msid) + str(gid.getGradeId()) + ",'" + glevel.getGradeName() + "','evaluations_middle.py?subjectId=" + str(msid) + "&gradeId=" + str(glevel.getGradeId()) + "&level=1','','_middle');"

        request.setAttribute("outHtml", outHtml)
        return "/WEB-INF/ftl/evaluation/evaluations_left.ftl"