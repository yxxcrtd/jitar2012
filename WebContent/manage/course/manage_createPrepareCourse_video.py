from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage,PrepareCourseMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from cn.edustar.jitar.pojos import Message
from cn.edustar.jitar.model import ObjectType

from base_preparecourse_page import *

class manage_createPrepareCourse_video(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()     
        self.pun_svc = __jitar__.UPunishScoreService
        self.msg_svc = __spring__.getBean("messageService")
        self.videoService = __spring__.getBean("videoService")
        self.group = None        
        
    def execute(self): 
        self.getBaseData()
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return
    
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        if self.prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        
        if self.canManage(self.prepareCourse) == False:
            self.printer.write(u"您无权管理本备课。")
            return
        
        self.group = self.pc_svc.getGroupOfPrepareCourse(self.prepareCourse.prepareCourseId)
        if self.group == None:
            self.printer.write(u"集备必须是在协作组之内。")
            return
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "1":
                self.delete_precarecoursevideo()
            elif cmd == "addscore":
                #加分
                score = self.params.safeGetIntParam("add_score")
                if score>0:
                    #注意：加分是正的，保存是负的
                    score = score*-1   
                #print "score="+str(score)
                score_reason = self.params.safeGetStringParam("add_score_reason")
                vId = self.params.safeGetIntValues("vId")
                for id in vId:
                    video = self.videoService.findById(id)
                    if video != None:   
                        upun =self.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(),video.id,video.userId,float(score),score_reason,self.loginUser.userId,self.loginUser.trueName)
                        self.pun_svc.saveUPunishScore(upun)
                        
            elif cmd == "minusscore":
                #罚分 发消息
                score = self.params.safeGetIntParam("minus_score")
                #print "score="+str(score)
                if score<0:
                    #注意：罚分是负的，保存是正的
                    score = score*-1   
                score_reason = self.params.safeGetStringParam("minus_score_reason")
                vId = self.params.safeGetIntValues("vId")
                for id in vId:
                    video = self.videoService.findById(id)
                    self.pc_svc.removePrepareCourseVideo(self.prepareCourseId,id)
                    if video != None:
                        title=video.title
                        upun =self.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(),video.id,video.userId,float(score),score_reason,self.loginUser.userId,self.loginUser.trueName)
                        self.pun_svc.saveUPunishScore(upun)
                        
                        #发送消息：管理员删除了您的文章/资源/视频及扣分信息，内容为您的 ***（内容标题） 被删除，罚*分，原因：***。
                        message = Message()
                        message.sendId = self.loginUser.userId
                        message.receiveId = video.userId
                        message.title = u"管理员从集备中移除了您的视频及扣分信息"
                        if score_reason != "":
                            message.content = u"您的视频" + title + u"因为:" + score_reason + u" 原因，从 "+ self.prepareCourse.title +u" 中移除,并罚分" + str(score) + u"分"
                        else:  
                            message.content = u"您的视频 " + title + u" 从 "+ self.prepareCourse.title +u" 中移除,并罚分" + str(score) + u"分"
                        self.msg_svc.sendMessage(message)
                       
        pager = self.params.createPager()
        pager.itemName = u"视频"
        pager.itemUnit = u"个"
        pager.pageSize = 10
        qry = PrepareCourseVideoQuery(""" u.userId,u.loginName,u.trueName,u.nickName,pcv.videoTitle,pcv.videoId,pcv.createDate,v.flvThumbNailHref,v.flvHref """)     
        qry.prepareCourseId = self.prepareCourse.prepareCourseId
        qry.orderType =0
        pager.totalRows = qry.count()
        pcv_list = qry.query_map(pager)      
           
        request.setAttribute("pager",pager)
        request.setAttribute("pcv_list",pcv_list)
        request.setAttribute("group",self.group)  
        request.setAttribute("prepareCourse",self.prepareCourse)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/manage_createPrepareCourse_VideoList.ftl"
    
    def delete_precarecoursevideo(self):
        vId = self.params.safeGetIntValues("vId")
        for id in vId:
            self.pc_svc.removePrepareCourseVideo(self.prepareCourseId,id)