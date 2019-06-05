from subject_page import *
from cn.edustar.jitar.pojos import Message
from cn.edustar.jitar.model import ObjectType
from video_query import VideoQuery

class video(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.pun_svc = __jitar__.UPunishScoreService
        self.msg_svc = __spring__.getBean("messageService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin == False and self.isContentAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR        
        
        if request.getMethod() == "POST":
            self.clear_subject_cache()
            self.save_post()
        return self.get_method()
    
    def save_post(self):
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        videoService = __spring__.getBean("videoService")
        
        if cmd == "remove":
            for g in guids:
                video = videoService.findById(g)
                if video != None:
                    video.setSubjectId(0)
                    videoService.updateVideo(video)
                    
        if cmd == "approve":
            for g in guids:
                video = videoService.findById(g)
                if video != None:                   
                    videoService.auditVideo(video)
                    
        if cmd == "unapprove":
            for g in guids:
                video = videoService.findById(g)
                if video != None: 
                    #删除资源的罚分机制
                    #upun =self.pun_svc.createUPunishScore(17,video.id,video.userId,self.loginUser.userId,self.loginUser.trueName)
                    #self.pun_svc.saveUPunishScore(upun)
                    #正式删除资源                 
                    videoService.unauditVideo(video)
        elif cmd == "addscore":
            #加分
            score = self.params.safeGetIntParam("add_score")
            if score>0:
                #注意：加分是正的，保存是负的
                score = score*-1   
            #print "score="+str(score)
            score_reason = self.params.safeGetStringParam("add_score_reason")
            for guid in guids:
                video = videoService.findById(guid)
                if video != None:   
                    upun =self.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(),video.id,video.userId,float(score),score_reason,self.loginUser.userId,self.loginUser.trueName)
                    self.pun_svc.saveUPunishScore(upun)
        elif cmd == "minusscore":
            #罚分 并删除删除文章，发消息
            score = self.params.safeGetIntParam("minus_score")
            #print "score="+str(score)
            if score<0:
                #注意：罚分是负的，保存是正的
                score = score*-1   
            score_reason = self.params.safeGetStringParam("minus_score_reason")
            for guid in guids:
                video = videoService.findById(guid)
                if video != None:
                    title=video.title
                    upun =self.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(),video.id,video.userId,float(score),score_reason,self.loginUser.userId,self.loginUser.trueName)
                    self.pun_svc.saveUPunishScore(upun)
                    
                    #发送消息：管理员删除了您的文章/资源/视频及扣分信息，内容为您的 ***（内容标题） 被删除，罚*分，原因：***。
                    message = Message()
                    message.sendId = self.loginUser.userId
                    message.receiveId = video.userId
                    message.title = u"管理员删除了您的视频及扣分信息"
                    if score_reason != "":
                        message.content = u"您的视频" + title + u" 被删除,扣" + str(score) + u"分,原因:" + score_reason
                    else:  
                        message.content = u"您的视频 " + title + u" 被删除,扣" + str(score) + u"分"
                    self.msg_svc.sendMessage(message)
                   
                    #删除                 
                    videoService.delVideo(video)  
                    #videoService.delVideo(video)                      
        if cmd == "real_delete":
            for g in guids:
                video = videoService.findById(g)
                if video != None:                   
                    videoService.delVideo(video)
                    
    def get_method(self):
        self.collectionQueryString()
        
        qry = VideoQuery(""" v.title, v.videoId, v.flvThumbNailHref, v.createDate, v.href,
                        v.flvHref, v.categoryId, v.addIp, v.typeState, v.userId, v.auditState,u.loginName
                         """)
        qry.subjectId = self.subject.metaSubject.msubjId
        qry.gradeId = self.subject.metaGrade.gradeId
        if self.approveState == "" or self.approveState.isdigit() == False:
            qry.auditState = None
        else:
            qry.auditState = int(self.approveState)
        qry.k = self.params.safeGetStringParam("k")
        qry.f = self.params.safeGetStringParam("f")
        pager = self.params.createPager()
        pager.itemName = u"视频"
        pager.itemUnit = u"个"
        pager.pageSize = 25
        pager.totalRows = qry.count()
        video_list = qry.query_map(pager)
        request.setAttribute("video_list", video_list)
        request.setAttribute("pager", pager)
        request.setAttribute("subject", self.subject)
        request.setAttribute("k", qry.k)
        request.setAttribute("f", qry.f)
        return "/WEB-INF/subjectmanage/video.ftl"
    
    
    def collectionQueryString(self):
        self.approveState = self.params.safeGetStringParam("sa", "")

        request.setAttribute("sa", self.approveState)
        
    def triblesimu(self, int1):
        if int1 == "0":
            return False
        else:
            return True
