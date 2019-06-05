from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage, PrepareCourseMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from cn.edustar.jitar.pojos import Message
from cn.edustar.jitar.model import ObjectType
from base_preparecourse_page import *

class manage_createPrepareCourse_ContentList(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()   
        self.pun_svc = __jitar__.UPunishScoreService
        self.msg_svc = __spring__.getBean("messageService")             
        self.group = None        
        
    def execute(self): 
        self.getBaseData()
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        if self.prepareCourseId == 0:
            self.printer.println(u"无效的备课标识。")
            return
    
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        if self.prepareCourse == None:
            self.printer.println(u"没有加载到所请求的备课。")
            return
        
        if self.canManage(self.prepareCourse) == False:
            self.printer.println(u"您无权管理本备课。")
            return
        
        self.group = self.pc_svc.getGroupOfPrepareCourse(self.prepareCourse.prepareCourseId)
        if self.group == None:
            self.printer.println(u"集备必须是在协作组之内。")
            return
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "8":
                self.clear_usercourse()
            if cmd == "9":
                self.set_Best()
            if cmd == "10":
                self.cancel_Best()
            elif cmd == "addscore":
                #加分
                score = self.params.safeGetIntParam("add_score")
                if score>0:
                    #注意：加分是正的，保存是负的
                    score = score*-1   
                #print "score="+str(score)
                score_reason = self.params.safeGetStringParam("add_score_reason")
                member_userId = self.params.safeGetIntValues("userId")
                for id in member_userId:
                    prepareCourseMember = self.pc_svc.getPrepareCourseMemberByCourseIdAndUserId(self.prepareCourseId, id)
                    if prepareCourseMember != None:   
                        upun =self.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_PREPARECOURSEMEMBER.getTypeId(),prepareCourseMember.prepareCourseMemberId,prepareCourseMember.userId,float(score),score_reason,self.loginUser.userId,self.loginUser.trueName)
                        self.pun_svc.saveUPunishScore(upun)
                        
            elif cmd == "minusscore":
                #罚分 发消息
                score = self.params.safeGetIntParam("minus_score")
                #print "score="+str(score)
                if score<0:
                    #注意：罚分是负的，保存是正的
                    score = score*-1   
                score_reason = self.params.safeGetStringParam("minus_score_reason")
                member_userId = self.params.safeGetIntValues("userId")
                for id in member_userId:
                    prepareCourseMember = self.pc_svc.getPrepareCourseMemberByCourseIdAndUserId(self.prepareCourseId, id)
                    if prepareCourseMember != None:   
                        upun =self.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_PREPARECOURSEMEMBER.getTypeId(),prepareCourseMember.prepareCourseMemberId,prepareCourseMember.userId,float(score),score_reason,self.loginUser.userId,self.loginUser.trueName)
                        self.pun_svc.saveUPunishScore(upun)
       
        pager = self.params.createPager()
        pager.itemName = u"成员"
        pager.itemUnit = u"个"
        qry = PrepareCourseMemberQuery(""" pcm.replyCount, pcm.joinDate,pcm.status, pcm.privateContent, pcm.bestState, u.userId, u.userIcon, u.loginName,u.trueName,u.nickName,u.unitId, unit.unitName""")     
        qry.prepareCourseId = self.prepareCourse.prepareCourseId
        qry.privateContentExist = True
        qry.orderType = 0
        qry.status = 0
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)      
           
        request.setAttribute("pager", pager)
        request.setAttribute("user_list", user_list)
        request.setAttribute("group", self.group)  
        request.setAttribute("prepareCourse", self.prepareCourse)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/manage_createPrepareCourse_ContentList.ftl"
    
    def clear_usercourse(self):
        member_userId = self.params.safeGetIntValues("userId")
        for id in member_userId:
            self.pc_svc.removePrepareCourseContent(self.prepareCourseId, id)

    def set_Best(self):
        member_userId = self.params.safeGetIntValues("userId")
        for id in member_userId:
            self.pc_svc.setPrepareCourseMemberBest(self.prepareCourseId, id, True)

    def cancel_Best(self):
        member_userId = self.params.safeGetIntValues("userId")
        for id in member_userId:
            self.pc_svc.setPrepareCourseMemberBest(self.prepareCourseId, id, False)
        
