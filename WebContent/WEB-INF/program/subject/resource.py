from subject_page import * 
from resource_query import ResourceQuery
from user_query import UserQuery
from base_action import *

class resource(BaseSubject, SubjectMixiner):
    def __init__(self):
        BaseSubject.__init__(self)
        self.cate_svc = __jitar__.categoryService
    
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        cache = __jitar__.cacheProvider.getCache('main')
        self.templateName = "template1"
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName
        
        self.levelGradeId = self.params.getIntParam("levelGradeId")
        if self.levelGradeId == None or self.levelGradeId == 0:
            self.levelGradeId = self.subject.metaGrade.gradeId
        unitId = self.unitId
        if unitId == None:
            unitId = ""
        self.grade = self.subject.metaGrade        
        msid = self.subject.metaSubject.msubjId
        type = self.params.getStringParam("type")
        if type == None or type == "": type = "new"
        cache_key1 = type + "_outHtmlSubject" + str(msid) + str(self.levelGradeId)
        outHtml = cache.get(cache_key1)
        #outHtml = None
        if outHtml == None or outHtml == "": 
            outHtml = ""
            outHtml = outHtml + "d.add(" + str(msid) + ",0,'" + self.grade.gradeName + self.subject.metaSubject.msubjName + "','resource.py?type=" + type + "&subjectId=" + str(msid) + "&gradeId=" + str(self.grade.gradeId) + "&target=child&unitId=" + str(unitId) + "');"
            cache_key = type + "_gradeLevelList" + str(self.grade.getGradeId())
            gradeLevelList = cache.get(cache_key)
            gradeLevelList = None
            if gradeLevelList == None:
                gradeLevelList = self.subjectService.getGradeLevelListByGradeId(self.grade.getGradeId())
                #cache.put(cache_key, gradeLevelList)                        
                for glevel in gradeLevelList:
                    outHtml = outHtml + "d.add(" + str(msid) + str(glevel.getGradeId()) + "," + str(msid) + ",'" + glevel.getGradeName() + "','resource.py?type=" + type + "&subjectId=" + str(msid) + "&gradeId=" + str(self.grade.gradeId) + "&level=1&levelGradeId=" + str(glevel.getGradeId()) + "&unitId=" + str(unitId) + "');"
                    #cache.put(cache_key1, outHtml)
          
        request.setAttribute("outHtml", outHtml)

        # 资源分类
        self.get_res_cates()
         
        # 资源上载排行
        self.get_upload_sorter()
    
        # 资源下载排行
        self.get_download_resource_list()
        
        request.setAttribute("subject", self.subject)
        request.setAttribute("head_nav", "resource")
        request.setAttribute("grade", self.grade)
        request.setAttribute("unitId", self.unitId)
        return "/WEB-INF/subjectpage/" + self.templateName + "/resource_page.ftl"    
    
    # 资源分类.
    def get_res_cates(self):
        res_cates = self.cate_svc.getCategoryTree("resource")
        request.setAttribute("res_cates", res_cates)     
        # 资源主列表.
        self.query_resource()
        
        
    # 资源上载排行.
    def get_upload_sorter(self):        
        qry = UserQuery("""  u.resourceCount, u.loginName, u.nickName """)
        qry.metaSubjectId = self.subject.metaSubject.msubjId
        qry.metaGradeId = self.subject.metaGrade.gradeId
        qry.FuzzyMatch = True
        qry.orderType = 4 #UserQuery.ORDER_TYPE_RESOURCE_COUNT_DESC
        qry.userStatus = 0
        upload_sorter = qry.query_map(20) 
        request.setAttribute("upload_sorter", upload_sorter)
        
    # 资源下载排行.
    def get_download_resource_list(self):
        qry = ResourceQuery(""" r.resourceId, r.href, r.title, r.downloadCount """)
        qry.subjectId = self.subject.metaSubject.msubjId
        qry.gradeId = self.subject.metaGrade.gradeId
        qry.FuzzyMatch = True
        qry.orderType = 4
        download_resource_list = qry.query_map(20) 
        request.setAttribute("download_resource_list", download_resource_list)
       
            
    # 资源查询主列表.
    def query_resource(self):
        qry = ResourceQuery(""" r.resourceId, r.title, r.href, r.fsize, r.createDate, 
              u.loginName, u.nickName, grad.gradeName, sc.name as scName """)
        pager = self.createPager()
        if self.unitId != None and self.unitId != 0:
            qry.custormAndWhereClause = " r.approvedPathInfo LIKE '%/" + str(self.unitId) + "/%'"
        # 根据页面参数处理.
        type = self.params.getStringParam("type")
        if type == "rcmd":
            qry.rcmdState = True
            if self.unitId != None and self.unitId != 0:
                # 覆盖掉前面的设置
                qry.custormAndWhereClause = " r.rcmdPathInfo LIKE '%/" + str(self.unitId) + "/%'"
        elif type == "hot":
            qry.orderType = ResourceQuery.ORDER_TYPE_DOWNLOADCOUNT_DESC
        elif type == "cmt":
            qry.orderType = ResourceQuery.ORDER_TYPE_COMMENTCOUNT_DESC
        else:
            type = "new"
          
        request.setAttribute("type", type)
        qry.subjectId = self.get_current_subjectId()
        
        #qry.gradeId = self.get_current_gradeId()
        qry.gradeId = self.levelGradeId
        qry.gradelevel = self.params.getIntParamZeroAsNull("level")       
        qry.FuzzyMatch = True
        
        qry.k = self.params.getStringParam("k")
        qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
        
        # 查询数据.
        pager.totalRows = qry.count()
        resource_list = qry.query_map(pager)
        
        request.setAttribute("resource_list", resource_list)
        request.setAttribute("pager", pager)
        
    def createPager(self):
        pager = self.params.createPager()
        pager.pageSize = 40
        pager.itemName = u"资源"
        pager.itemUnit = u"个"
        return pager
    
    def get_current_subjectId(self):
        subjectId = self.subject.metaSubject.msubjId
        request.setAttribute("subjectId" , subjectId)
        return subjectId
    def get_current_gradeId(self):
        gradeId = self.subject.metaGrade.gradeId
        request.setAttribute("gradeId", gradeId)
        return gradeId
