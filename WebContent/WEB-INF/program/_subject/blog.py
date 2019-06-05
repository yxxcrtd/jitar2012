from subject_page import * 
from base_action import BaseAction, ActionResult, SubjectMixiner
from user_query import UserQuery
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.data import Pager

class blog(BaseSubject, SubjectMixiner):
    def __init__(self):
        BaseSubject.__init__(self)
        self.cate_svc = __jitar__.categoryService
        
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        self.templateName = "template1"
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName
            
        # 工作室分类
        #self.get_blog_cates()
        # 名师工作室
        self.get_famous_user_list()
        # 学科带头人工作室
        self.get_expert_user_list()
        # 工作室访问排行
        #教研员
        self.get_subject_comissioner_user_list()
        
        self.get_hot_user_list()
                
        self.query_blog()
        
        request.setAttribute("subject", self.subject)
        request.setAttribute("head_nav", "blog")
        return "/WEB-INF/subjectpage/" + self.templateName + "/blog_page.ftl"
    
    # 工作室分类.  
    def get_blog_cates(self): 
        blog_cates = self.cate_svc.getCategoryTree("default")
        request.setAttribute("blog_cates", blog_cates)
        self.get_current_gradeId()    
 
    # 名师工作室.
    def get_famous_user_list(self):
        famous_list = self.get_famous_list()
        request.setAttribute("famous_list", famous_list)
        
    def get_expert_user_list(self):
        expert_list = self.get_expert_list()
        request.setAttribute("expert_list", expert_list)
        
    def get_subject_comissioner_user_list(self):
        comissioner_list = self.get_subject_comissioner()
        request.setAttribute("comissioner_list", comissioner_list)
        
    def get_hot_user_list(self):
        hot_list = self.get_hot_list(10)
        request.setAttribute("hot_list", hot_list)
        
    # 根据条件查询工作室.
    def query_blog(self):        
        minNum = CommonUtil.convertRoundMinNumber(self.get_current_gradeId())
        maxNum = CommonUtil.convertRoundMaxNumber(self.get_current_gradeId())
        strOrderBy = "UserId DESC"
        strWhereClause = "(UserStatus=0 "
        strWhere1 = strWhereClause + " And GradeId >= " + str(minNum) + " And GradeId<" + str(maxNum) + " And SubjectId=" + str(self.get_current_subjectId())
        strWhere1 += ")"
        strWhere2 = ""
       
        if self.userIds != "":
            strWhere2 = " And UserId In(" + self.userIds + ")"
        
        if strWhere2 != "":
            strWhere2 = strWhereClause + strWhere2 + ")"
        if strWhere2 == "":
            strWhereClause = strWhere1
        else:
            strWhereClause = strWhere1 + " Or " + strWhere2
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "UserId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.spName = "findPagingUser"
        pagingQuery.tableName = "Jitar_User"
        pagingQuery.whereClause = strWhereClause
        pagingQuery.orderByFieldName  = strOrderBy
        totalCount = self.params.safeGetIntParam("totalCount")
        pager = Pager()
        pager.setCurrentPage(self.params.safeGetIntParam("page", 1))
        pager.setPageSize(10)
        pager.setItemNameAndUnit(u"用户", u"个")
        pager.setUrlPattern(self.params.generateUrlPattern())
        if totalCount == 0:
            pager.setTotalRows(pagingService.getRowsCount(pagingQuery))
        else:
            pager.setTotalRows(totalCount)
            
        blog_list = pagingService.getPagingList(pagingQuery, pager)
        request.setAttribute("blog_list", blog_list)
        request.setAttribute("pager", pager)
        return
      
    def createPager(self):
        pager = self.params.createPager()
        pager.pageSize = 10
        pager.itemName = u"工作室"
        pager.itemUnit = u"个"
        return pager