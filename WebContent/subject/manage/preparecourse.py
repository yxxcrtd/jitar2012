from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage, PrepareCourseMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *
from subject_page import *

class preparecourse(BaseSubject,SubjectMixiner):    
    def __init__(self):
        BaseSubject.__init__(self)
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.sub_svc = __jitar__.subjectService
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin == False and self.isContentAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        #if request.getMethod() == "POST" :
        self.clear_subject_cache()
        guid = self.params.safeGetIntValues("guid")
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "0":
            for id in guid:
                self.pc_svc.deletePrepareCourse(id)
                
        if cmd == "1":
            for id in guid:
                prepareCourse = self.pc_svc.getPrepareCourse(id)
                if prepareCourse != None:
                    prepareCourse.setStatus(0)
                    self.pc_svc.updatePrepareCourse(prepareCourse)
        
        if cmd == "2":
            for id in guid:
                prepareCourse = self.pc_svc.getPrepareCourse(id)
                if prepareCourse != None:
                    prepareCourse.setStatus(1)
                    self.pc_svc.updatePrepareCourse(prepareCourse)
                    
        if cmd == "3":
            for id in guid:
                prepareCourse = self.pc_svc.getPrepareCourse(id)
                if prepareCourse != None:
                    prepareCourse.setStatus(2)
                    self.pc_svc.updatePrepareCourse(prepareCourse)
        if cmd == "4":
            for id in guid:
                prepareCourse = self.pc_svc.getPrepareCourse(id)
                if prepareCourse != None:
                    prepareCourse.setRecommendState(True)
                    self.pc_svc.updatePrepareCourse(prepareCourse)
        if cmd == "5":
            for id in guid:
                prepareCourse = self.pc_svc.getPrepareCourse(id)
                if prepareCourse != None:
                    prepareCourse.setRecommendState(False)
                    self.pc_svc.updatePrepareCourse(prepareCourse)
        if cmd == "6":
            #导出Excel
            return self.printcourse()
        if cmd == "9":#查询
            return self.preparecourse_search()
                        
        self.preparecourse_list()        
        return "/WEB-INF/subjectmanage/preparecourse.ftl"

    def printcourse(self):
        request.setCharacterEncoding("utf-8")
        k = self.params.getStringParam("k")    #查询关键字
        ktype= self.params.getStringParam("ktype") #查询类型[关键字对应的类型]
        if ktype==None:
            ktype="1"
        course_BeginDate = self.params.getStringParam("course_BeginDate")
        subjectId = self.params.getIntParamZeroAsNull("id")
        subject = self.sub_svc.getSubjectById(int(subjectId))
        gradeId = self.params.getIntParamZeroAsNull("gradeId")
        if ktype=="2":
            qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,
                                    u.loginName,u.trueName
                                """)
        elif ktype=="3" or ktype=="4":
            qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,
                                    ul.loginName,ul.trueName
                                """)
        else:
            qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,
                                    u.loginName,u.trueName
                                """)  
        qry.stage = None
        #qry.status = 0
        qry.subjectId = subject.metaSubject.msubjId  
        if gradeId==None or gradeId=='':
            qry.gradeId = subject.metaGrade.gradeId
            qry.containChild = None
        else:
            qry.gradeId =gradeId
            qry.containChild = False
        self.putGradeListByGradeId(subject.metaGrade.gradeId)
        course_list = qry.query_map(-1)
        
        sPrivateCount = 0
        sEditCount = 0
        privateCountList = []
        editCountList = []
        if course_list.size() > 0:
            for pc in course_list:
                pcId = pc['prepareCourseId'] 
                sPrivateCount = self.pc_svc.getPrepareCourseContentCount(pcId)
                #print " PrivateCount="+str(sPrivateCount)
                sEditCount = self.pc_svc.getPrepareCourseEditCount(pcId)
                #print " sEditCount="+str(sEditCount)
                privateCountList.append(sPrivateCount)
                editCountList.append(sEditCount)
                
        request.setAttribute("preparecourse_list", course_list)
        request.setAttribute("privateCountList", privateCountList)
        request.setAttribute("editCountList", editCountList)
        
        response.reset()
        response.setContentType("application/vnd.ms-excel")
        response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312")
        #response.setCharacterEncoding("GB2312")
        #response.setLocale(Locale.SIMPLIFIED_CHINESE)
        response.addHeader("Content-Disposition", "attachment;filename=Course.xls")
        return "/WEB-INF/ftl/course/course_excel.ftl"
            
    def preparecourse_list(self):
        #只显示本学科的
        pager = self.params.createPager()
        pager.itemName = u"备课"
        pager.itemUnit = u"个"
        pager.pageSize = 25
        qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,
                                    u.loginName,u.trueName
                                """)        
        qry.stage = None
        #qry.status = 0
        subjectId = self.params.getIntParamZeroAsNull("id")
        subject = self.sub_svc.getSubjectById(int(subjectId))
        qry.subjectId = subject.metaSubject.msubjId  
        qry.containChild = None
        qry.gradeId = subject.metaGrade.gradeId
        self.putGradeListByGradeId(subject.metaGrade.gradeId)
        #print "qry.gradeId",qry.gradeId
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
                #print " PrivateCount="+str(sPrivateCount)
                sEditCount = self.pc_svc.getPrepareCourseEditCount(pcId)
                #print " sEditCount="+str(sEditCount)
                privateCountList.append(sPrivateCount)
                editCountList.append(sEditCount)
                
        request.setAttribute("preparecourse_list", course_list)
        request.setAttribute("privateCountList", privateCountList)
        request.setAttribute("editCountList", editCountList)
        request.setAttribute("pager", pager)

    def preparecourse_search(self):
        #只显示本学科的
        k = self.params.getStringParam("k")    #查询关键字
        ktype= self.params.getStringParam("ktype") #查询类型[关键字对应的类型]
        if ktype==None:
            ktype="1"
        course_BeginDate = self.params.getStringParam("course_BeginDate")
        subjectId = self.params.getIntParamZeroAsNull("id")
        subject = self.sub_svc.getSubjectById(int(subjectId))
        gradeId = self.params.getIntParamZeroAsNull("gradeId")
        
        pager = self.params.createPager()
        pager.itemName = u"备课"
        pager.itemUnit = u"个"
        pager.pageSize = 25

        if ktype=="2":
            qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,
                                    u.loginName,u.trueName
                                """)
        elif ktype=="3" or ktype=="4":
            qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,
                                    ul.loginName,ul.trueName
                                """)
        else:
            qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,
                                    u.loginName,u.trueName
                                """)        
        qry.stage = None
        #qry.status = 0
        qry.subjectId = subject.metaSubject.msubjId  
        if gradeId==None or gradeId=='':
            qry.gradeId = subject.metaGrade.gradeId
            qry.containChild = None
        else:
            qry.gradeId =gradeId
            qry.containChild = False
        self.putGradeListByGradeId(subject.metaGrade.gradeId)
        #print "qry.gradeId",qry.gradeId
        pager.totalRows = qry.count()
        course_list = qry.query_map(pager)
        
        sPrivateCount = 0
        sEditCount = 0
        privateCountList = []
        editCountList = []
        if course_list.size() > 0:
            for pc in course_list:
                pcId = pc['prepareCourseId'] 
                sPrivateCount = self.pc_svc.getPrepareCourseContentCount(pcId)
                #print " PrivateCount="+str(sPrivateCount)
                sEditCount = self.pc_svc.getPrepareCourseEditCount(pcId)
                #print " sEditCount="+str(sEditCount)
                privateCountList.append(sPrivateCount)
                editCountList.append(sEditCount)
                
        request.setAttribute("preparecourse_list", course_list)
        request.setAttribute("privateCountList", privateCountList)
        request.setAttribute("editCountList", editCountList)
        request.setAttribute("pager", pager)
        return "/WEB-INF/subjectmanage/preparecourse.ftl"