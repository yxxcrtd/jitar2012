from subject_page import *
from cn.edustar.jitar.data import *
from base_action import BaseAction, ActionResult, SubjectMixiner
from base_preparecourse_page import *

class preparecourse(BaseSubject, SubjectMixiner):
    def __init__(self):
        BaseSubject.__init__(self)
        self.cate_svc = __jitar__.categoryService
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        cache = __jitar__.cacheProvider.getCache('page')
        self.stage = self.params.safeGetStringParam("showtype")
        if self.stage == "":
            self.stage = "running"
        
        strUnitId = ""
        if self.unitId != None and self.unitId != 0:
            strUnitId = "&unitId=" + str(self.unitId)
        
        if request.getAttribute("SubjectRootUrl") == None:
            configSubjectSiteRoot = request.getSession().getServletContext().getInitParameter("subjectUrlPattern")
            if configSubjectSiteRoot == None or configSubjectSiteRoot == "":
                subjectRootUrl = self.getCurrentSiteUrl(request) + "k/" + self.subject.subjectCode + "/"
            else:
                subjectRootUrl = configSubjectSiteRoot.replaceAll("\\{subjectCode\\}", self.subject.subjectCode)
            request.setAttribute("SubjectRootUrl", subjectRootUrl)   
        

        self.templateName = "template1"
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName

        self.levelGradeId = self.params.getIntParamZeroAsNull("levelGradeId")
        
        self.grade = self.subject.metaGrade
        
        msid = self.subject.metaSubject.msubjId
        cache_key1 = "outHtmlSubject" + str(msid) + str(self.levelGradeId)
        outHtml = cache.get(cache_key1)
        # 考虑到添加了单位信息，不再进行缓存
        outHtml = ""
        if outHtml == None or outHtml == "": 
            outHtml = ""
            outHtml = outHtml + "d.add(" + str(msid) + ",0,'" + self.grade.gradeName + self.subject.metaSubject.msubjName + "','preparecourse.py?subjectId=" + str(msid) + "&gradeId=" + str(self.grade.gradeId) + strUnitId + "');"
            cache_key = "gradeLevelList" + str(self.grade.getGradeId())
            gradeLevelList = cache.get(cache_key)
            if gradeLevelList == None:
                gradeLevelList = self.subjectService.getGradeLevelListByGradeId(self.grade.getGradeId())
                cache.put(cache_key, gradeLevelList)                        
            for glevel in gradeLevelList:
                outHtml = outHtml + "d.add(" + str(msid) + str(glevel.getGradeId()) + "," + str(msid) + ",'" + glevel.getGradeName() + "','preparecourse.py?subjectId=" + str(msid) + "&gradeId=" + str(self.grade.gradeId) + "&levelGradeId=" + str(glevel.getGradeId()) + strUnitId + "');"
                cache.put(cache_key1, outHtml)
            
        request.setAttribute("outHtml", outHtml)
        
        # 查询列表
        self.get_course_list()

        request.setAttribute("type", self.params.getStringParam("type"))
        request.setAttribute("levelGradeId", self.levelGradeId)
        request.setAttribute("grade", self.grade)
        request.setAttribute("subject", self.subject)
        request.setAttribute("head_nav", "preparecourse")
        return "/WEB-INF/subjectpage/" + self.templateName + "/preparecourse_page.ftl"
    
    def get_course_list(self):
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
        qry.stage = self.stage
        #print "self.get_current_subjectId()=",self.get_current_subjectId()
        qry.subjectId = self.get_current_subjectId()
        if self.levelGradeId == None:
            # 得到全学段的内容
            qry.containChild = None
            qry.gradeId = self.subject.metaGrade.gradeId
            #print "qry.gradeId=",qry.gradeId
        else:            
            qry.containChild = True
            qry.gradeId = self.levelGradeId
            #print "qry.gradeId=",qry.gradeId
        pager.totalRows = qry.count()
        course_list = qry.query_map(pager)
        

        sPrivateCount = 0
        sEditCount = 0
        privateCountList = []
        editCountList = []
        if course_list != None and course_list.size() > 0:
            for pc in course_list:
                pcId = pc['prepareCourseId'] 
                sPrivateCount = self.pc_svc.getPrepareCourseContentCount(pcId)
                sEditCount = self.pc_svc.getPrepareCourseEditCount(pcId)
                privateCountList.append(sPrivateCount)
                editCountList.append(sEditCount)
        request.setAttribute("privateCountList", privateCountList)
        request.setAttribute("editCountList", editCountList)
        request.setAttribute("course_list", course_list)
        request.setAttribute("pager", pager)
        request.setAttribute("showtype", self.stage)
        request.setAttribute("levelGradeId", self.levelGradeId)
        
    
    def get_current_gradeId(self):
        self.gradeId = self.subject.metaGrade.gradeId
        request.setAttribute("gradeId", self.gradeId)
        return self.gradeId
    
    def get_current_subjectId(self):
        subjectId = self.subject.metaSubject.msubjId
        request.setAttribute("subjectId" , subjectId)
        return subjectId
