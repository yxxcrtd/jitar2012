from subject_page import *
from cn.edustar.jitar.pojos import Message
from cn.edustar.jitar.model import ObjectType
from resource_query import ResourceQuery, GroupResourceQuery
 
class resource(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.resourceService = __spring__.getBean("resourceService")
        self.groupService = __spring__.getBean("groupService")
        self.categoryService = __spring__.getBean("categoryService")
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
            response.sendRedirect(request.getHeader("Referer"))
        
        configService = __jitar__.configService
        sys_config = configService.getConfigure()
        if sys_config != None:
            if sys_config["topsite_url"] != None and str(sys_config["topsite_url"]).strip() != "":
                request.setAttribute("topsite_url", "")
                
        return self.article_list()
    
    def save_post(self):
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        if cmd == "remove":
            for guid in guids:
                r = self.resourceService.getResource(guid)
                if r != None:
                    r.subjectId = None
                    self.resourceService.updateResource(r)
        elif cmd == "push":
            for guid in guids:
                r = self.resourceService.getResource(guid)
                if r != None and r.pushState == 0 and r.auditState == 0:
                    r.setPushState(2)
                    r.setPushUserId(self.loginUser.userId)
                    self.resourceService.updateResource(r)
                    
        elif cmd == "unpush":
            for guid in guids:
                r = self.resourceService.getResource(guid)
                if r != None and r.pushState == 2:
                    r.setPushState(0)
                    r.setPushUserId(None)
                    self.resourceService.updateResource(r)
                           
        elif cmd == "real_delete":
            for guid in guids:
                r = self.resourceService.getResource(guid)
                if r != None:     
                    #删除资源的罚分机制
                    #upun =self.pun_svc.createUPunishScore(12,r.id,r.userId,self.loginUser.userId,self.loginUser.trueName)
                    #self.pun_svc.saveUPunishScore(upun)
                    #正式删除资源                 
                    self.resourceService.crashResource(r)  
        elif cmd == "addscore":
            #加分
            score = self.params.safeGetIntParam("add_score")
            if score>0:
                #注意：加分是正的，保存是负的
                score = score*-1   
            #print "score="+str(score)
            score_reason = self.params.safeGetStringParam("add_score_reason")
            for guid in guids:
                r = self.resourceService.getResource(guid)
                if r != None:   
                    upun =self.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(),r.id,r.userId,float(score),score_reason,self.loginUser.userId,self.loginUser.trueName)
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
                r = self.resourceService.getResource(guid)
                if r != None:
                    title=r.title
                    upun =self.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(),r.id,r.userId,float(score),score_reason,self.loginUser.userId,self.loginUser.trueName)
                    self.pun_svc.saveUPunishScore(upun)
                    
                    #发送消息：管理员删除了您的文章/资源/视频及扣分信息，内容为您的 ***（内容标题） 被删除，罚*分，原因：***。
                    message = Message()
                    message.sendId = self.loginUser.userId
                    message.receiveId = r.userId
                    message.title = u"管理员删除了您的资源及扣分信息"
                    if score_reason != "":
                        message.content = u"您的资源" + title + u" 被删除,扣" + str(score) + u"分,原因:" + score_reason
                    else:  
                        message.content = u"您的资源 " + title + u" 被删除,扣" + str(score) + u"分"
                    self.msg_svc.sendMessage(message)
                   
                    #删除                 
                    self.resourceService.crashResource(r)  
                    #self.resourceService.deleteResource(r)                            
        elif cmd == "select_type":
            cmd_type = self.params.safeGetStringParam("cmdtype")
            if cmd_type == "":
                return
            for guid in guids:
                r = self.resourceService.getResource(guid)
                if r != None:
                    if cmd_type == "approve":
                        r.setApprovedPathInfo(r.getOrginPathInfo())
                        self.resourceService.updateResource(r)
                        self.resourceService.auditResource(r)
                    elif cmd_type == "unapprove":
                        r.setApprovedPathInfo(None)
                        self.resourceService.updateResource(r)
                        self.resourceService.unauditResource(r)
                    elif cmd_type == "rcmd":
                        r.setRcmdPathInfo(r.getOrginPathInfo())
                        self.resourceService.updateResource(r)
                        self.resourceService.rcmdResource(r)
                    elif cmd_type == "unrcmd":
                        r.setRcmdPathInfo(None)
                        self.resourceService.updateResource(r)
                        self.resourceService.unrcmdResource(r)
                    elif cmd_type == "delete":
                        self.resourceService.deleteResource(r)
                    elif cmd_type == "undelete":
                        self.resourceService.recoverResource(r)
    
    def article_list(self):
        self.collectionQueryString()
        if self.rcmdState == "-1":
            qry = GroupResourceQuery(""" r.resourceId, r.title, r.shareMode, r.createDate, r.href, r.delState, r.pushState,
                    r.downloadCount, r.commentCount, r.fsize, r.auditState, r.recommendState, r.tags, r.addIp, r.publishToZyk,
                    u.userId, u.loginName, u.nickName, sc.name as sysCateName, subj.subjectName, gr.isGroupBest 
                    """)
        else:
            qry = ResourceQuery(""" r.resourceId, r.title, r.shareMode, r.createDate, r.href, r.delState, r.pushState,
                    r.downloadCount, r.commentCount, r.fsize, r.auditState, r.recommendState, r.tags, r.addIp,r.publishToZyk, 
                    u.userId, u.loginName, u.nickName, sc.name as sysCateName, subj.subjectName 
                    """)
        if self.sysCateId != "" and self.sysCateId.isdigit() == True:
            qry.sysCateId = int(self.sysCateId)            
        
        if self.approveState == "" or self.approveState.isdigit() == False:
            qry.auditState = None
        else:
            qry.auditState = int(self.approveState)
            
        if self.rcmdState == "-1":
            #小组文章的精华 GroupResource 中的  isGroupBest = True    
            qry.isGroupBest = True
        else:    
            if self.rcmdState == "" or self.rcmdState.isdigit() == False:
                qry.rcmdState = None
            else:
                qry.rcmdState = self.triblesimu(self.rcmdState)
        
        if self.deleteState == "" or self.deleteState.isdigit() == False:
            qry.delState = None
        else:
            qry.delState = self.triblesimu(self.deleteState)
        
        if self.shareState == "" or self.shareState.isdigit() == False or self.shareState == "-1":
            qry.shareMode = None
        else:
            qry.shareMode = int(self.shareState)
        
        # shareMode:ResourceQuery中采用Resource.shareMode > 设置值的方法导致无法精确查询到某个shareMode的资源。
        
        servletContext = request.getSession().getServletContext()
        zyk_url = servletContext.getInitParameter("reslib_url")
        request.setAttribute("zyk_url", zyk_url)
        request.setAttribute("isHaszykUrl", "false")
        if (zyk_url != None and zyk_url != ""):
            request.setAttribute("isHaszykUrl", "true");
        
        zyk = self.params.getStringParam("zyk")
        #print "zyk=",zyk
        qry.publishToZyk = None
        if(zyk != None and zyk != ""):
            if zyk == "0":
                qry.publishToZyk = False
            if zyk == "1": 
                qry.publishToZyk = True
        request.setAttribute("zyk", zyk)              
        qry.subjectId = self.metaSubjectId
        qry.gradeId = self.metaGradeId
        qry.FuzzyMatch = True
        
        pager = self.params.createPager()
        pager.itemName = u"资源"
        pager.itemUnit = u"个"
        pager.pageSize = 25
        pager.totalRows = qry.count()
        resource_list = qry.query_map(pager)
        groupbest_list = []
        if self.rcmdState != "-1":
            #增加小组资源精华的字段
            for a in resource_list :
                resId = a['resourceId']
                groupbest_list.append(self.groupService.isBestInGroupResource(resId))
        
        grade_list = self.subjectService.getMainGradeList()
        resource_categories = self.categoryService.getCategoryTree('resource')        
        request.setAttribute("resource_categories", resource_categories)
        request.setAttribute("grade_list", grade_list)   
        request.setAttribute("groupbest_list", groupbest_list)
        request.setAttribute("resource_list", resource_list)
        request.setAttribute("pager", pager)
        request.setAttribute("subject", self.subject)
        return "/WEB-INF/subjectmanage/resource.ftl"       
        
        
    def collectionQueryString(self):
        self.sysCateId = self.params.safeGetStringParam("ss", "")
        self.approveState = self.params.safeGetStringParam("sa", "")
        self.rcmdState = self.params.safeGetStringParam("sr", "")
        self.deleteState = self.params.safeGetStringParam("sd", "")
        self.shareState = self.params.safeGetStringParam("sm", "")        
        
        request.setAttribute("ss", self.sysCateId)
        request.setAttribute("sa", self.approveState)
        request.setAttribute("sr", self.rcmdState)
        request.setAttribute("sd", self.deleteState)
        request.setAttribute("sm", self.shareState)
        
    def triblesimu(self, int1):
        if int1 == "0":
            return False
        else:
            return True
