from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage, PrepareCourseMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *
from cn.edustar.jitar.jython import BaseAdminAction

class searchRelatedPrepareCourse(BaseAdminAction, ActionResult,SubjectMixiner):
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        
        #print "prepareCourseId="+self.params.safeGetStringParam("prepareCourseId")
        
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        if self.prepareCourseId == 0:
            print(u"无效的备课标识[prepareCourseId]!")
            return  
        request.setAttribute("prepareCourseId", self.prepareCourseId)
        self.putSubjectList()
    	self.putGradeList()
        guid = self.params.safeGetIntValues("guid")
        cmd = self.params.safeGetStringParam("cmd")
    	if cmd=="2":
            for id in guid:
                self.pc_svc.insertRelatedPrepareCourse(self.prepareCourseId,id,self.loginUser.userId)    
    	    return "/WEB-INF/ftl/closeOpener.ftl"
        return self.preparecourse_search()

    # 例如: 3100 -》 false  3000 ->> true
    def bNumber(self, intV):
        if intV == None:
            return False    
        strV = str(intV)
        if strV.isdigit() == False:
            return False    
        intStrLen = len(strV)        
        if intStrLen < 2:
            return False
        strPad = "0"
        b=True
        for i in range(1, intStrLen):
            if strV[i]!='0':
               b=False
        return b
    def preparecourse_search(self):
        pager = self.params.createPager()
        pager.itemName = u"备课"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        k = self.params.getStringParam("k")
        ktype= self.params.getStringParam("ktype") #查询类型[关键字对应的类型]
        if ktype==None:
            ktype="1"
        unit = self.params.getStringParam("unit")  #主备人所属机构
        course_BeginDate = self.params.getStringParam("course_BeginDate")
        subjectId = self.params.getIntParamZeroAsNull("subjectId") 
        gradeId = self.params.getIntParamZeroAsNull("gradeId")
        if ktype=="2":
            qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,
                                    u.trueName
                                """)
        elif ktype=="3" or ktype=="4":
            qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,
                                    ul.trueName
                                """)
        else:
            qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState
                                """)
        if self.bNumber(gradeId)==True:
            qry.containChild=True
        else:
            qry.containChild=False	
        pager.totalRows = qry.count()
        course_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
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
        return "/WEB-INF/ftl/course/searchRelatedPrepareCourse.ftl"