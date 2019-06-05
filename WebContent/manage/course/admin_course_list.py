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

class admin_course_list(BaseAdminAction, ActionResult,SubjectMixiner):
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.meetingsService = __spring__.getBean("meetingsService")
        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if False == accessControlService.isSystemContentAdmin(self.loginUser):
            self.addActionError(u"没有管理站点配置的权限.")
            return ActionResult.ERROR

        self.putSubjectList()
    	self.putGradeList()
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
        if cmd == "7":
          for id in guid:
            self.meetingsService.openMeetings("jibei", id)
        if cmd == "8":
          for id in guid:
            self.meetingsService.closeMeetings("jibei", id)
        if cmd == "9":#查询
            return self.preparecourse_search()
        self.preparecourse_list()
        video_url = request.getSession().getServletContext().getInitParameter("video_url")
        if video_url != None and video_url != "":
            request.setAttribute("video_url", "video_url")
        return "/WEB-INF/ftl/course/admin_course_list.ftl"
    

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
    
    # 将学段是否是整数，例如: 3100 -》 3000
    def convertRoundMinNumber(self, intV):
        if intV == None:
            return 0    
        strV = str(intV)
        if strV.isdigit() == False:
            return 0    
        intStrLen = len(strV)        
        if intStrLen < 2:
            return intV
        strPad = "0"
        for i in range(2, intStrLen):
            strPad = strPad + "0"
            
        strV = strV[0:1] + strPad
        return int(strV)

    # 将学段格式化成整数，例如: 3100 -》 4000
    def convertRoundMaxNumber(self, intV):
        if intV == None:
            return 0    
        strV = str(intV)
        if strV.isdigit() == False:
            return 0    
        intStrLen = len(strV)        
        if intStrLen < 2:
            return intV
        strPad = "0"
        for i in range(2, intStrLen):
            strPad = strPad + "0"
        strV = str(int(strV[0:1]) + 1) + strPad
        return int(strV)

              
    def printcourse(self):
        request.setCharacterEncoding("utf-8")
        k = self.params.getStringParam("k")	#查询关键字
        ktype= self.params.getStringParam("ktype") #查询类型[关键字对应的类型]
        if ktype == None or ktype == '':
            ktype="1"
        unit = self.params.getStringParam("unit")  #主备人所属机构
        course_BeginDate = self.params.getStringParam("course_BeginDate")
        course_EndDate = self.params.getStringParam("course_EndDate")
        subjectId = self.params.getIntParamZeroAsNull("subjectId") 
        gradeId = self.params.getIntParamZeroAsNull("gradeId")
        request.setAttribute("subjectId", subjectId)
        request.setAttribute("gradeId", gradeId)
        request.setAttribute("k", k)
        request.setAttribute("ktype", ktype)
        request.setAttribute("unit", unit)
        request.setAttribute("course_BeginDate", course_BeginDate)
        request.setAttribute("course_EndDate", course_EndDate)
        hql=""" SELECT new Map(pc.createUserId as createUserId ,pc.leaderId as leaderId,pc.memberCount as memberCount,pc.articleCount as articleCount,pc.resourceCount as resourceCount,
                                pc.status as status,pc.actionCount as actionCount,pc.topicCount as topicCount,pc.topicReplyCount as topicReplyCount,pc.viewCount as viewCount,pc.startDate as startDate,pc.endDate as endDate,
                                pc.title as title, pc.prepareCourseId as prepareCourseId, pc.createDate as createDate, pc.metaSubjectId as metaSubjectId, pc.gradeId as gradeId,pc.recommendState as recommendState
                                ,pc.privateCount as privateCount,pc.editCount as editCount,pc.createUserName as createUserName,pc.createUserNickName as createUserNickName,pc.createUserUnitId as createUserUnitId
                                ,pc.leaderUserName as leaderUserName,pc.leaderUserNickName as leaderUserNickName,pc.leaderUserUnitId as leaderUserUnitId,pc.createUserUnitTitle as createUserUnitTitle,pc.leaderUserUnitTitle as leaderUserUnitTitle
            )"""
        hql=hql+" FROM VPrepareCourse pc "
        where = " WHERE pc.prepareCourseGenerated=true "
        if ktype=="2":
            if k != None and k != '':
                newKey = k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
                where=where+" AND (pc.createUserNickName LIKE '%" + newKey + "%' OR pc.createUserName LIKE '%" + newKey + "%')"
            if course_BeginDate != None and course_BeginDate != '':
                where=where+" AND pc.startDate >= '"+course_BeginDate+"'"    
            if course_EndDate != None and course_EndDate != '':
                where=where+" AND pc.startDate <= '"+course_EndDate+"'"    
            if subjectId != None:
                where=where+" AND pc.metaSubjectId = "+str(subjectId)    
            if gradeId != None:
                where=where+" AND (pc.gradeId >= "+ str(self.convertRoundMinNumber(gradeId)) +" And pc.gradeId < "+str(self.convertRoundMaxNumber(gradeId))+")"
        elif ktype=="3" or ktype=="4":
            if k != None and k != '':
                newKey = k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
                if ktype == "3":    #搜索主备人
                    where=where+" AND (pc.leaderUserName LIKE '%" + newKey + "%' OR pc.leaderUserNickName LIKE '%" + newKey + "%')"
                elif ktype == "4":    #搜索主备人所属机构
                    where=where+" AND pc.leaderUserUnitTitle LIKE '%"+ newKey +"%'"    
            if course_BeginDate != None and course_BeginDate != '':
                where=where+" AND pc.startDate >= '"+course_BeginDate+"'"    
            if course_EndDate != None and course_EndDate != '':
                where=where+" AND pc.startDate <= '"+course_EndDate+"'"    
            if subjectId != None:
                where=where+" AND pc.metaSubjectId = "+str(subjectId)    
            if gradeId != None:
                where=where+" AND (pc.gradeId >= "+ str(self.convertRoundMinNumber(gradeId)) +" And pc.gradeId < "+str(self.convertRoundMaxNumber(gradeId))+")"
        else:
            if k != None and k != '':
                newKey = k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
                if ktype == "1":#搜索标题
                    where=where+" AND pc.title LIKE '%"+ newKey +"%'"
            if course_BeginDate != None and course_BeginDate != '':
                where=where+" AND pc.startDate >= '"+course_BeginDate+"'"    
            if course_EndDate != None and course_EndDate != '':
                where=where+" AND pc.startDate <= '"+course_EndDate+"'"    
            if subjectId != None:
                where=where+" AND pc.metaSubjectId = "+str(subjectId)    
            if gradeId != None:
                where=where+" AND (pc.gradeId >= "+ str(self.convertRoundMinNumber(gradeId)) +" And pc.gradeId < "+str(self.convertRoundMaxNumber(gradeId))+")"
                
        hql=hql+where        
        hql=hql+" ORDER BY pc.prepareCourseId DESC"                        
        qry = Command(hql)  
        #Excel 行数最大是65535
        course_list = qry.open(65500) 
        request.setAttribute("preparecourse_list", course_list)
        response.reset()
        response.setContentType("application/vnd.ms-excel")
        response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312")
        ###response.setCharacterEncoding("GB2312")
        ###response.setLocale(Locale.SIMPLIFIED_CHINESE)
        response.addHeader("Content-Disposition", "attachment;filename=Course.xls")
        return "/WEB-INF/ftl/course/course_excel.ftl"
            
    def preparecourse_list(self):
        pager = self.params.createPager()
        pager.itemName = u"备课"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState
                                """)
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

    def preparecourse_search(self):
		pager = self.params.createPager()
		pager.itemName = u"备课"
		pager.itemUnit = u"个"
		pager.pageSize = 20

		k = self.params.getStringParam("k")	#查询关键字
		ktype= self.params.getStringParam("ktype") #查询类型[关键字对应的类型]
		if ktype==None:
			ktype="1"
		unit = self.params.getStringParam("unit")  #主备人所属机构
		course_BeginDate = self.params.getStringParam("course_BeginDate")
		subjectId = self.params.getIntParamZeroAsNull("subjectId") 
		gradeId = self.params.getIntParamZeroAsNull("gradeId")
		if ktype=="2": 
			qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,
                                    u.trueName
                                """)
		elif ktype=="3" or ktype=="4":
			qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,
                                    ul.trueName
                                """)
		else:
			qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState
                                """)
		if self.bNumber(gradeId)==True:
			qry.containChild=True
		else:
			qry.containChild=False	
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
		return "/WEB-INF/ftl/course/admin_course_list.ftl"