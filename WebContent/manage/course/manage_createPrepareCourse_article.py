from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *
from cn.edustar.jitar.pojos import Message
from cn.edustar.jitar.model import ObjectType

class manage_createPrepareCourse_article(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()        
        self.pun_svc = __jitar__.UPunishScoreService
        self.msg_svc = __spring__.getBean("messageService")
        self.articleService = __spring__.getBean("articleService")
        
    def execute(self): 
        self.getBaseData()
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return
        
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        
        if self.canManage(prepareCourse) == False:
            self.printer.write(u"您无权管理本备课。")
            return
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "delete":
                prepareCourseArticleId = self.params.safeGetIntValues("prepareCourseArticleId")
                for id in prepareCourseArticleId:
                    self.pc_svc.deleteArticleToPrepareCourseStageById(id)
            elif cmd == "move":
                destStageId = self.params.safeGetIntParam("stageId")
                if destStageId > 0:
                    prepareCourseArticleId = self.params.safeGetIntValues("prepareCourseArticleId")
                    for id in prepareCourseArticleId:
                        self.pc_svc.moveArticleToPrepareCourseStageById(id,destStageId)
            elif cmd == "addscore":
                #加分
                score = self.params.safeGetIntParam("add_score")
                if score>0:
                    #注意：加分是正的，保存是负的
                    score = score*-1   
                #print "score="+str(score)
                score_reason = self.params.safeGetStringParam("add_score_reason")
                prepareCourseArticleId = self.params.safeGetIntValues("prepareCourseArticleId")
                for id in prepareCourseArticleId:
                    prepareCourseArticle=self.pc_svc.getPrepareCourseArticle(id)
                    art = self.articleService.getArticle(prepareCourseArticle.articleId)
                    if art != None:   
                        upun =self.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(),art.id,art.userId,float(score),score_reason,self.loginUser.userId,self.loginUser.trueName)
                        self.pun_svc.saveUPunishScore(upun)
                        
            elif cmd == "minusscore":
                #罚分 并删除删除文章，发消息
                score = self.params.safeGetIntParam("minus_score")
                #print "score="+str(score)
                if score<0:
                    #注意：罚分是负的，保存是正的
                    score = score*-1   
                score_reason = self.params.safeGetStringParam("minus_score_reason")
                prepareCourseArticleId = self.params.safeGetIntValues("prepareCourseArticleId")
                for id in prepareCourseArticleId:
                    prepareCourseArticle=self.pc_svc.getPrepareCourseArticle(id)
                    if prepareCourseArticle!=None:
                        art = self.articleService.getArticle(prepareCourseArticle.articleId)
                        self.pc_svc.deleteArticleToPrepareCourseStageById(id)
                        if art != None:
                            title=art.title
                            upun =self.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(),art.id,art.userId,float(score),score_reason,self.loginUser.userId,self.loginUser.trueName)
                            self.pun_svc.saveUPunishScore(upun)
                            
                            message = Message()
                            message.sendId = self.loginUser.userId
                            message.receiveId = art.userId
                            message.title = u"管理员从集备中移除了您的文章及扣分信息"
                            if score_reason != "":
                                message.content = u"您发布的文章 " + title + u"因为:" + score_reason + u" 原因,从 "+ prepareCourse.title +u" 中移除,并罚分" + str(score) + u"分"
                            else:  
                                message.content = u"您发布的文章 " + title + u" 从 "+ prepareCourse.title +u" 中移除,并罚分" + str(score) + u"分"
                            self.msg_svc.sendMessage(message)
                           
        prepareCourseStageList = self.pc_svc.getPrepareCourseStageList(self.prepareCourseId)
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"       
        qry = PrepareArticleQuery(""" pca.prepareCourseArticleId,pca.title,pca.userId,pca.articleId,pca.createDate,pcs.title as pcsTitle """)
        qry.prepareCourseId = prepareCourse.prepareCourseId
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager) 
           
        request.setAttribute("pager",pager) 
        request.setAttribute("article_list",article_list)
        request.setAttribute("stage_list",prepareCourseStageList)        
        request.setAttribute("prepareCourse",prepareCourse)       
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/manage_createPrepareCourse_article.ftl"