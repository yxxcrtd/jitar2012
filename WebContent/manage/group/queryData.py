from cn.edustar.jitar.jython import JythonBaseAction
from base_action import *
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import CommonUtil
from java.util import Date
from java.text import SimpleDateFormat
from cn.edustar.jitar.pojos import GroupDataQuery
from resource_query import GroupResourceQuery
from article_query import GroupArticleQuery

class queryData(JythonBaseAction, ActionResult):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        
        if self.loginUser == None:
            response.addHeader("result", "error")
            response.getWriter().println(u"请先登录。")
            return
        
        groupService = __spring__.getBean("groupService")
        
        beginDate = self.params.safeGetStringParam("beginDate")
        endDate = self.params.safeGetStringParam("endDate")
        groupId = self.params.safeGetIntParam("groupId")
        guid = self.params.safeGetStringParam("guid")
        type = self.params.safeGetStringParam("type")
        u = self.params.safeGetIntParam("userId")
        
        #验证是否是日期格式：
        if CommonUtil.isYYYYMMDDDate(beginDate) == False or CommonUtil.isYYYYMMDDDate(endDate) == False:
            response.addHeader("result", "error")
            response.getWriter().println(u"无效的日期格式。必须是yyyy-MM-dd格式。")
            return
        if beginDate == "":
            beginDate = "2008-01-01"
        if endDate == "":
            sdf = SimpleDateFormat("yyyy-MM-dd")
            endDate = sdf.format(Date())             
        
        b = SimpleDateFormat("yyyy-MM-dd").parse(beginDate)
        b = SimpleDateFormat("yyyy-MM-dd").format(b)
        if beginDate != b:
            response.addHeader("result", "error")
            response.getWriter().println(u"开始日期的日期格式无效：必须是yyyy-MM-dd格式。")
            return
        
        e = SimpleDateFormat("yyyy-MM-dd").parse(endDate)
        e = SimpleDateFormat("yyyy-MM-dd").format(e)
        if endDate != e:
            response.addHeader("result", "error")
            response.getWriter().println(u"结束日期的日期格式无效：必须是yyyy-MM-dd格式。")
            return
                
        #response.addHeader("beginDate", beginDate)
        #response.addHeader("endDate", endDate)
        beginDate = beginDate + " 0:0:0"
        endDate = endDate + " 23:59:59"
        
        if type == "check":
            if groupId == 0:
                response.addHeader("result", "error")
                response.getWriter().println(u"请输入协作组标识。")
                return
            if guid == "":
                response.addHeader("result", "error")
                response.getWriter().println(u"缺少 GUID 标识。")
                return            
        elif type == "query":
            userService = __spring__.getBean("userService")
            user = userService.getUserById(u)
            if user == None:
                response.addHeader("result", "error")
                response.getWriter().println(u"无法加载该用户。")
                return
            LoginName = user.loginName
            TrueName = user.trueName
            UnitName = None
            UnitTitle = None
            MetaSubjectId = None
            MetaSubjectTitle = None
            GradeId = None
            GradeTitle = None
            if user.unitId != None:
                unitService = __spring__.getBean("unitService")
                unit = unitService.getUnitById(user.unitId)
                if unit != None:
                    UnitName = unit.unitName
                    UnitTitle = unit.unitTitle
                    
            if user.subjectId != None:
                subjectService = __spring__.getBean("subjectService")
                subject = subjectService.getMetaSubjectById(user.subjectId)
                if subject != None:
                    MetaSubjectId = subject.msubjId
                    MetaSubjectTitle = subject.msubjName
            
            if user.gradeId != None:
                subjectService = __spring__.getBean("subjectService")
                grade = subjectService.getGrade(user.gradeId)
                if grade != None:
                    GradeId = grade.gradeId
                    GradeTitle = grade.gradeName
            
            qry = GroupArticleQuery("ga.articleId, ga.groupId")
            qry.groupId = groupId      
            ArticleCount = qry.count()
            
            qry = GroupArticleQuery("ga.articleId, ga.groupId")
            qry.groupId = groupId      
            qry.groupBest = True
            BestArticleCount = qry.count()
            
            qry = GroupResourceQuery("r.resourceId,gr.groupId")
            qry.groupId = groupId            
            ResourceCount = qry.count()
            
            qry = GroupResourceQuery("r.resourceId,gr.groupId")
            qry.groupId = groupId
            qry.isGroupBest = True     
            BestResourceCount = qry.count()
            
            sql = "SELECT COUNT(*) AS TopicCount FROM Topic WHERE createDate Between '" + beginDate + "' And '" + endDate + "' And userId = " + str(u) + " And groupId=" + str(groupId)
            TopicCount = Command(sql).int_scalar()
            
            sql = "SELECT COUNT(*) AS ReplyCount FROM Reply WHERE createDate Between '" + beginDate + "' And '" + endDate + "' And userId = " + str(u) + " And groupId=" + str(groupId)
            ReplyCount = Command(sql).int_scalar()
            #returnValue = returnValue + str(u) + "|" + str(ArticleCount) + "|" + str(BestArticleCount) + "|" + str(ResourceCount) + "|" + str(BestResourceCount) + "|" + str(TopicCount) + "|" + str(ReplyCount) + ","
            groupDataQuery = GroupDataQuery()
            groupDataQuery.setObjectGuid(guid)
            groupDataQuery.setBeginDate(beginDate)
            groupDataQuery.setEndDate(endDate)
            groupDataQuery.setLoginName(LoginName)
            groupDataQuery.setTrueName(TrueName)
            groupDataQuery.setUnitName(UnitName)
            groupDataQuery.setUnitTitle(UnitTitle)
            groupDataQuery.setMetaSubjectId(MetaSubjectId)
            groupDataQuery.setMetaSubjectTitle(MetaSubjectTitle)
            groupDataQuery.setGradeId(GradeId)
            groupDataQuery.setGradeTitle(GradeTitle)
            groupDataQuery.setArticleCount(ArticleCount)
            groupDataQuery.setBestArticleCount(BestArticleCount)
            groupDataQuery.setResourceCount(ResourceCount)
            groupDataQuery.setBestResourceCount(BestResourceCount)
            groupDataQuery.setTopicCount(TopicCount)
            groupDataQuery.setReplyCount(ReplyCount)            
            groupService.saveOrUpdateGroupDataQuery(groupDataQuery)          
            
        else:
            response.addHeader("result", "error")
            response.getWriter().println(u"无效的命令。")
            return
