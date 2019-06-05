from cn.edustar.jitar.data import *
from article_query import ArticleQuery
from base_action import *
from base_preparecourse_page import *

cache = __jitar__.cacheProvider.getCache('page')

class subjectPrepareCourses(SubjectMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        self.cate_svc = __jitar__.categoryService
        self.subj_svc = __jitar__.subjectService

    def execute(self):
        self.putSubject()
        
        self.levelGradeId = self.params.getIntParam("levelGradeId")
        if self.levelGradeId == None or self.levelGradeId == 0:
            self.levelGradeId = self.grade.getGradeId()
        
        msid = self.subject.metaSubject.msubjId
        cache_key1 = "outHtmlSubject" + str(msid) + str(self.levelGradeId)
        outHtml = cache.get(cache_key1)
        if outHtml== None or outHtml == "": 
            outHtml = ""
            outHtml = outHtml + "d.add(" + str(msid) + ",0,'" + self.grade.gradeName + self.subject.metaSubject.msubjName + "','subjectPrepareCourses.py?subjectId=" + str(msid) + "&gradeId=" + str(self.grade.gradeId) + "&target=child');"
            cache_key = "gradeLevelList" + str(self.grade.getGradeId())
            gradeLevelList = cache.get(cache_key)
            if gradeLevelList == None:
                gradeLevelList =  self.subj_svc.getGradeLevelListByGradeId(self.grade.getGradeId())
                cache.put(cache_key, gradeLevelList)                        
            for glevel in gradeLevelList:
                outHtml = outHtml + "d.add(" + str(msid) + str(glevel.getGradeId()) + "," + str(msid) + ",'" + glevel.getGradeName() + "','subjectPrepareCourses.py?subjectId=" + str(msid) + "&gradeId=" + str(self.grade.gradeId) + "&levelGradeId=" + str(glevel.getGradeId()) + "');"
                cache.put(cache_key1, outHtml)
            
        request.setAttribute("outHtml",outHtml)
        
        # 查询列表
        self.get_course_list()
        # 高亮显示项目.
        request.setAttribute("head_nav", "preparecourse")    
        request.setAttribute("type", self.params.getStringParam("type"))
        request.setAttribute("levelGradeId", self.levelGradeId)
    
        return "/WEB-INF/ftl/site_subject_prepareCourses.ftl"
    

    def get_current_gradeId(self): 
        gradeId = self.params.getIntParam("gradeId")
        request.setAttribute("gradeId",gradeId)
        return gradeId
    def get_current_subjectId(self):
        subjectId = self.params.getIntParam("subjectId")
        request.setAttribute("subjectId", subjectId)
        return subjectId 

    def get_course_list(self):
        pager = self.params.createPager()
        pager.itemName = u"备课"
        pager.itemUnit = u"个"
        pager.pageSize = 30
        qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,
                                    u.loginName,u.trueName
                                """)
        if self.params.safeGetStringParam("target") == "child":
            qry.containChild = True
        
        qry.status = 0
        qry.metaSubjectId = self.get_current_subjectId()
        qry.gradeId = self.levelGradeId
        pager.totalRows = qry.count()
        course_list = qry.query_map(pager)
        request.setAttribute("course_list", course_list)
        request.setAttribute("pager", pager)