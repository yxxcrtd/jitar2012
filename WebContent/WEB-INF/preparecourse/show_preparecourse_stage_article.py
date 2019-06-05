from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from preparecourse_member_query import PrepareCourseMemberQuery
from base_preparecourse_page import *

class show_preparecourse_stage_article(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self):
        self.getBaseData()
        
        response.setContentType("text/html; charset=UTF-8")
        
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的课程标识。")
            return
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        if self.canView(prepareCourse) == False:
            self.printer.write(u"您无权查看本内容。")
            return
        
        if self.prepareCourseStageId == 0:
            currentStage = self.pc_svc.getCurrentPrepareCourseStage(self.prepareCourseId)            
        else:
            currentStage = self.pc_svc.getPrepareCourseStage(self.prepareCourseStageId)
        if currentStage == None:
            self.printer.write(u"当前日期不在任何一个流程之内。")
            return

        qry = PrepareArticleQuery(""" pca.userId,pca.articleId,pca.title,pca.createDate """)
        if self.prepareCourseStageId == 0:
            qry.prepareCourseStageId = currentStage.prepareCourseStageId
        else:
            qry.prepareCourseStageId = self.prepareCourseStageId
        artile_list = qry.query_map(10)
        # 检查当前用户是否是成员
        if self.loginUser != None:
            if self.pc_svc.checkUserInPreCourse(self.prepareCourseId, self.loginUser.userId) == True:
                request.setAttribute("isMember","1")
        
        request.setAttribute("prepareCourseStageId",qry.prepareCourseStageId)
        request.setAttribute("artile_list",artile_list)
        request.setAttribute("currentStage",currentStage)
        
        return "/WEB-INF/ftl/course/show_preparecourse_stage_article.ftl"