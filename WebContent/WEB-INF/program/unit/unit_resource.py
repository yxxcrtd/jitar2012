from unit_page import *
from base_action import SubjectMixiner
from resource_query import ResourceQuery

class unit_resource(UnitBasePage, SubjectMixiner):
    def __init__(self):
        UnitBasePage.__init__(self)
        
    def execute(self):
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR        
        if self.unit.delState == True:
            self.addActionError(u"您所访问的机构已经被删除！")
            return self.ERROR
        
        self.get_resource_list()
    
        #res_cate = __jitar__.categoryService.getCategoryTree("resource")
        #request.setAttribute("res_cate", res_cate)
        self.get_cate_tree_without_cache()
        request.setAttribute("head_nav", "unit_resource")
        request.setAttribute("unit", self.unit)      
        self.putGradeList()
        self.putSubjectList()
        self.putResouceCateList()
        templateName = "template1"
        if self.unit.templateName != None:
            templateName = self.unit.templateName
        return "/WEB-INF/unitspage/" + templateName + "/unit_resource.ftl"
    
    def get_resource_list(self):
        qry = ResourceQuery(""" r.resourceId, r.href, r.title, r.fsize, r.createDate, r.recommendState, 
            u.loginName, u.nickName, r.subjectId as subjectId, grad.gradeName, sc.name as scName """)
        #qry.unitId = self.unit.unitId
        type = self.params.getStringParam("type")
        if type == None or type == "": type = "new"
        list_type = ""
        if type == "hot":
            qry.orderType = ResourceQuery.ORDER_TYPE_VIEWCOUNT_DESC
            qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%'"
            list_type = u"最高人气"
        elif type == "rcmd":
            #qry.recommendState = True
            #qry.rcmdState = True
            qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' And r.rcmdPathInfo Like '%/" + str(self.unit.unitId) + "/%'"
            list_type = u"编辑推荐"
        elif type == "cmt":
            qry.orderType = ResourceQuery.ORDER_TYPE_COMMENTCOUNT_DESC
            qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%'"
            list_type = u"评论最多"
        else:
            type = "new"
            qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%'"
            list_type = u"最新资源"   
        request.setAttribute("type", type)
        request.setAttribute("list_type", list_type)
        
        qry.gradelevel = self.params.getIntParamZeroAsNull("level")
        qry.subjectId = self.params.getIntParamZeroAsNull("subjectId")
        qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
        qry.gradeId = self.params.getIntParamZeroAsNull("gradeId")
        qry.k = self.params.getStringParam("k")
        
        pager = self.createPager()
        
        pager.totalRows = qry.count()
        resource_list = qry.query_map(pager)
        
        request.setAttribute("resource_list", resource_list)
        request.setAttribute("pager", pager)
        request.setAttribute("subjectId", qry.subjectId)
        request.setAttribute("categoryId", qry.sysCateId)
    
    def get_cate_tree_without_cache(self):               
        self.sbj_svc = __jitar__.subjectService
        type = self.params.getStringParam("type")
        if type == None or type == "": type = "new"
        outHtml = ""
        subject_list = self.sbj_svc.getMetaSubjectList()
        for s in subject_list:
            msid = s.getMsubjId()
            outHtml = outHtml + "d.add(" + str(msid) + ",0,'" + s.getMsubjName() + "','unit_resource.py?type=" + type + "&subjectId=" + str(msid) + "&unitId=" + str(self.unit.unitId) + "');"
            gradeIdList = self.sbj_svc.getMetaGradeListByMetaSubjectId(msid)
            if gradeIdList != None:
                for gid in gradeIdList:
                    outHtml = outHtml + "d.add(" + str(msid) + str(gid.getGradeId()) + "," + str(msid) + ",'" + gid.getGradeName() + "','unit_resource.py?type=" + type + "&subjectId=" + str(msid) + "&gradeId=" + str(gid.getGradeId()) + "&target=child&unitId=" + str(self.unit.unitId) + "');"
                    gradeLevelList = self.sbj_svc.getGradeLevelListByGradeId(gid.getGradeId())
                    for glevel in gradeLevelList:
                        outHtml = outHtml + "d.add(" + str(msid) + str(gid.getGradeId()) + str(glevel.getGradeId()) + "," + str(msid) + str(gid.getGradeId()) + ",'" + glevel.getGradeName() + "','unit_resource.py?type=" + type + "&subjectId=" + str(msid) + "&gradeId=" + str(glevel.getGradeId()) + "&level=1&unitId=" + str(self.unit.unitId) + "');"   
            
        request.setAttribute("outHtml", outHtml)
        
    def get_cate_tree(self):
        #下面的带缓存的版本有bug，没有过滤机构
        cache = __jitar__.cacheProvider.getCache('category')
        self.sbj_svc = __jitar__.subjectService
        type = self.params.getStringParam("type")
        if type == None or type == "": type = "new"
        outHtml = cache.get(type + "_outHtml_resource")
        if outHtml == None or outHtml == "": 
          cache_key = "_subject_list_resource"
          subject_list = cache.get(cache_key)
          if subject_list == None:
            subject_list = self.sbj_svc.getMetaSubjectList()
            cache.put(cache_key, subject_list)
          outHtml = ""
          for s in subject_list:
            msid = s.getMsubjId()
            outHtml = outHtml + "d.add(" + str(msid) + ",0,'" + s.getMsubjName() + "','unit_resource.py?type=" + type + "&subjectId=" + str(msid) + "&unitId=" + str(self.unit.unitId) + "');"
            cache_key = "_gradeIdList_resource" + str(msid)
            gradeIdList = cache.get(cache_key)
            if gradeIdList == None:
              gradeIdList = self.sbj_svc.getMetaGradeListByMetaSubjectId(msid)
              cache.put(cache_key, gradeIdList) 
                             
            if gradeIdList != None:
               for gid in gradeIdList:     
                 outHtml = outHtml + "d.add(" + str(msid) + str(gid.getGradeId()) + "," + str(msid) + ",'" + gid.getGradeName() + "','unit_resource.py?type=" + type + "&subjectId=" + str(msid) + "&gradeId=" + str(gid.getGradeId()) + "&target=child&unitId=" + str(self.unit.unitId) + "');"
                 cache_key = "_gradeLevelList_resource" + str(gid.getGradeId())
                 gradeLevelList = cache.get(cache_key)
                 if gradeLevelList == None:
                   gradeLevelList = self.sbj_svc.getGradeLevelListByGradeId(gid.getGradeId())
                   cache.put(cache_key, gradeLevelList)                        
                 for glevel in gradeLevelList:
                   outHtml = outHtml + "d.add(" + str(msid) + str(gid.getGradeId()) + str(glevel.getGradeId()) + "," + str(msid) + str(gid.getGradeId()) + ",'" + glevel.getGradeName() + "','unit_resource.py?type=" + type + "&subjectId=" + str(msid) + "&gradeId=" + str(glevel.getGradeId()) + "&level=1&unitId=" + str(self.unit.unitId) + "');"
                   cache.put(type + "_outHtml_resource", outHtml)
          
            
        request.setAttribute("outHtml", outHtml) 
    
    def createPager(self):
        pager = self.params.createPager()
        pager.itemName = u"资源"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        return pager
