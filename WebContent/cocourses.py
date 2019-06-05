from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_preparecourse_page import *
from base_action import SubjectMixiner

cache = __jitar__.cacheProvider.getCache('page')

class cocourses(SubjectMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        self.cate_svc = __jitar__.categoryService
        self.sbj_svc = __jitar__.subjectService
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self):
        self.stage = self.params.safeGetStringParam("type")
        
        outHtml = cache.get("outHtml")
        if outHtml== None or outHtml == "": 
            cache_key = "subject_list"
            subject_list = cache.get(cache_key)
            if subject_list == None:
                subject_list = self.sbj_svc.getMetaSubjectList()
                cache.put(cache_key, subject_list)
            outHtml = ""
            for s in subject_list:
                msid = s.getMsubjId()
                outHtml = outHtml + "d.add(" + str(msid) + ",0,'" + s.getMsubjName() + "','cocourses.py?subjectId=" + str(msid) + "');"
                cache_key = "gradeIdList" + str(msid)
                gradeIdList = cache.get(cache_key)
                if gradeIdList == None:
                    gradeIdList = self.sbj_svc.getMetaGradeListByMetaSubjectId(msid)
                    cache.put(cache_key, gradeIdList)                
                if gradeIdList != None:
                    for gid in gradeIdList:     
                        outHtml = outHtml + "d.add(" + str(msid) + str(gid.getGradeId()) + "," + str(msid) + ",'" + gid.getGradeName() + "','cocourses.py?subjectId=" + str(msid) + "&gradeId=" + str(gid.getGradeId()) + "&target=child');"
                        cache_key = "gradeLevelList" + str(gid.getGradeId())
                        gradeLevelList = cache.get(cache_key)
                        if gradeLevelList == None:
                            gradeLevelList =  self.sbj_svc.getGradeLevelListByGradeId(gid.getGradeId())
                            cache.put(cache_key, gradeLevelList)                        
                        for glevel in gradeLevelList:
                            outHtml = outHtml + "d.add(" + str(msid) + str(gid.getGradeId()) + str(glevel.getGradeId()) + "," + str(msid) + str(gid.getGradeId()) + ",'" + glevel.getGradeName() + "','cocourses.py?subjectId=" + str(msid) + "&gradeId=" + str(glevel.getGradeId()) + "');"
                
            cache.put("outHtml", outHtml)
            
        request.setAttribute("outHtml",outHtml)  
        
        self.get_subject_list()
        self.get_grade_list()
        self.preparecourse_list()
                
        # 页面导航高亮'
        request.setAttribute("head_nav", "cocourses")
        return "/WEB-INF/ftl/site_courses.ftl" 
     
    def preparecourse_list(self): 
        
        pager = self.params.createPager()
        pager.itemName = u"备课"
        pager.itemUnit = u"个"
        pager.pageSize = 30
        qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,
                                    u.loginName,u.trueName
                                """)
        qry.status = 0
        if self.stage == "":self.stage = "running"
        qry.stage = self.stage
        
        if self.params.safeGetStringParam("target") == "child":
            qry.containChild = None
            request.setAttribute("target", "child")
        else:
            qry.containChild = True
        pager.totalRows = qry.count()
        course_list = qry.query_map(pager)
        sPrivateCount=0
        sEditCount=0
        privateCountList=[]
        editCountList=[]
        if course_list.size()>0:
            for pc in course_list:
                pcId=pc['prepareCourseId'] 
                sPrivateCount=self.pc_svc.getPrepareCourseContentCount(pcId)
                sEditCount=self.pc_svc.getPrepareCourseEditCount(pcId)
                privateCountList.append(sPrivateCount)
                editCountList.append(sEditCount)
        request.setAttribute("privateCountList", privateCountList)
        request.setAttribute("editCountList", editCountList)
        request.setAttribute("course_list", course_list)
        request.setAttribute("pager", pager)
        request.setAttribute("type", self.stage)        
        
    def get_grade_list(self):
        request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
        self.putGradeList()

    def get_subject_list(self):
        self.putSubjectList()