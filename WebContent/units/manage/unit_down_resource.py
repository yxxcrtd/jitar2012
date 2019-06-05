from unit_page import UnitBasePage
from resource_query import ResourceQuery
from cn.edustar.jitar.pojos import Resource
from base_action import SubjectMixiner
from cn.edustar.jitar.util import CommonUtil

class unit_down_resource(UnitBasePage, SubjectMixiner):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.resourceService = __spring__.getBean("resourceService")
        self.cate_svc = __jitar__.categoryService
        self.pun_svc = __jitar__.UPunishScoreService
        
    def execute(self):
         
        # 此页面需要系统管理员和机构内容管理员进行管理
        if self.loginUser == None:
            return self.LOGIN        
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        if self.isUserAdmin() == False and self.isContentAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.post_request()
            if request.getHeader("Referer") != None and request.getHeader("Referer") != "":
                response.sendRedirect(request.getHeader("Referer"))
        
        self.get_request()
        request.setAttribute("unit", self.unit)
        
        
        return "/WEB-INF/unitsmanage/unit_down_resource.ftl"
    
    def get_request(self):
        query = ResourceQuery(""" r.resourceId, r.title, r.shareMode, r.createDate, r.href, r.subjectId, r.gradeId, r.rcmdPathInfo,r.unitId,
            r.delState, r.unitPathInfo, r.approvedPathInfo, r.downloadCount, r.commentCount, r.fsize, r.auditState, r.recommendState, r.tags, r.addIp, 
            u.userId, u.loginName, u.nickName, sc.name as sysCateName """)
        
        query.unitId = None
        query.auditState = None
        query.delState = None
        
        multiPushState = self.params.safeGetStringParam("multiPushState")
        recommendState = self.params.safeGetStringParam("recommendState")
        auditState = self.params.safeGetStringParam("auditState")
        delState = self.params.safeGetStringParam("delState")
        
        custormAndWhereClause = "r.orginPathInfo LIKE '%/" + str(self.unit.unitId) + "/%' And r.unitId <> " + str(self.unit.unitId) + " And"
        
        # 得到全部的下级单位
        unitList = self.unitService.getDownUnitList(self.unit)
        
        if recommendState == "1":
            strRcmd = ""
            for ut in unitList:
                strRcmd = strRcmd + " r.rcmdPathInfo LIKE '%/" + str(ut.unitId) + "/%' or"
            if strRcmd != "":
                strRcmd = strRcmd[0:len(strRcmd)-2]
                custormAndWhereClause += " ("+strRcmd+") And"
        elif recommendState == "0":
            strRcmd = ""
            for ut in unitList:
                strRcmd = strRcmd + " r.rcmdPathInfo Not LIKE '%/" + str(ut.unitId) + "/%' And"
            if strRcmd != "":
                strRcmd = strRcmd[0:len(strRcmd)-3]
                custormAndWhereClause += " (r.rcmdPathInfo Is Null or ("+strRcmd+")) And"
        
        if auditState == "0":
            strApprove = ""
            for ut in unitList:
                strApprove = strApprove + " r.approvedPathInfo LIKE '%/" + str(ut.unitId) + "/%' or"
            if strApprove != "":
                strApprove = strApprove[0:len(strApprove)-2]
                custormAndWhereClause += "("+strApprove+") And"
        elif auditState == "1":
            strApprove = ""
            for ut in unitList:
                strApprove = strApprove + " r.approvedPathInfo NOT LIKE '%/" + str(ut.unitId) + "/%' And"
            if strApprove != "":
                strApprove = strApprove[0:len(strApprove)-3]
                custormAndWhereClause += " (r.approvedPathInfo Is Null or ("+strApprove+")) And" 
                
        """
        if auditState == "0":
            custormAndWhereClause += " r.approvedPathInfo LIKE '%/" + str(self.unit.unitId) + "/%' And"
        elif auditState == "1":
            custormAndWhereClause += " (r.approvedPathInfo Is NUll or r.approvedPathInfo NOT LIKE '%/" + str(self.unit.unitId) + "/%') And"
        
        if multiPushState == "1":
            custormAndWhereClause += " r.unitPathInfo LIKE '%/" + str(self.unit.parentId) + "/%' And"
        elif multiPushState == "0":
            custormAndWhereClause += " r.unitPathInfo NOT LIKE '%/" + str(self.unit.parentId) + "/%' And"
        
        if recommendState == "1":
            custormAndWhereClause += " r.rcmdPathInfo LIKE '%/" + str(self.unit.unitId) + "/%' And"
        elif recommendState == "0":
            custormAndWhereClause += " (r.rcmdPathInfo Is NULL or r.rcmdPathInfo NOT LIKE '%/" + str(self.unit.unitId) + "/%') And"
        """
        
        
        
        if delState == "0":
            query.delState = False
        elif delState == "1":
            query.delState = True
        
        if custormAndWhereClause[len(custormAndWhereClause) - 3:] == "And":
            custormAndWhereClause = custormAndWhereClause[0:len(custormAndWhereClause) - 3]
        query.custormAndWhereClause = custormAndWhereClause
        #print custormAndWhereClause
        
        # 计算总量.
        pager = self.createPager()
        pager.pageSize = 20
        pager.totalRows = query.count()
        
        # 得到资源.
        resource_list = query.query_map(pager)
        
        request.setAttribute("pager", pager)
        request.setAttribute("resource_list", resource_list)
        #self.putSubjectList()
        #self.putGradeList()
        #self.putResourceCategoryTree()
        
        request.setAttribute("delState", delState)
        request.setAttribute("multiPushState", multiPushState)
        request.setAttribute("recommendState", recommendState)
        request.setAttribute("auditState", auditState)  
        #self.putSubjectList()
        #self.putGradeList()
        self.putResourceCategoryTree()
        
    def createPager(self):
        # private 构造文章的缺省 pager.
        pager = self.params.createPager()
        pager.itemName = u"资源"
        pager.itemUnit = u"条"
        pager.pageSize = 20
        return pager
    
    def post_request(self):
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "delete":
            self.delete()
        elif cmd == "undelete":
            self.undelete()
        elif cmd == "crash":
            self.crash()
        elif cmd == "approveLevel":
            self.approveLevel()
        elif cmd == "unapproveLevel":
            self.unapproveLevel()
        elif cmd == "setRcmd":
            self.setRcmd()
        elif cmd == "unsetRcmd":
            self.unsetRcmd()
        elif cmd == "move_cate":
            self.move_cate()

        
    def delete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(g)
            if resource != None:                    
                if resource.delState != True:
                    self.resourceService.deleteResource(resource)
                
    def undelete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(g)
            if resource != None:
                if resource.delState == True:
                    self.resourceService.recoverResource(resource)
    
    def crash(self):
        guids = self.params.safeGetIntValues("guid")
        deleteComplexObjectService = __spring__.getBean("deleteComplexObjectService")
        for g in guids:
            resource = self.resourceService.getResource(g)
            if resource != None:
                deleteComplexObjectService.deleteResource(resource)
    
    def approveLevel(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(g)
            if resource != None:
                self.resourceService.auditResource(resource)
                resourceUnitId = resource.unitId
                if resourceUnitId != None:
                    resourceUnit = self.unitService.getUnitById(resourceUnitId)
                    if resourceUnit != None:
                        unitPath = self.unitService.getUnitPathBetweenUnits(self.unit, resourceUnit)
                        if unitPath != "":
                            unitPath = CommonUtil.sortNumberString(unitPath)
                            approvedPathInfo = resource.approvedPathInfo
                            if approvedPathInfo == None:
                                resource.setApprovedPathInfo(unitPath)
                            else:                                
                                arraypath = unitPath.split("/")
                                for p in range(len(arraypath) - 1, -1, -1):
                                    if arraypath[p] != "":
                                        partInfo = "/" + arraypath[p] + "/"
                                        approvedPathInfo = resource.approvedPathInfo
                                        if approvedPathInfo.find(partInfo) == -1:
                                            PathInfo = "/" + arraypath[p] + approvedPathInfo
                                            resource.setApprovedPathInfo(CommonUtil.sortNumberString(PathInfo))
                            self.resourceService.updateResource(resource)               
                
    
    def unapproveLevel(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(g)
            if resource != None:
                resourceUnitId = resource.unitId
                if resourceUnitId != None:
                    resourceUnit = self.unitService.getUnitById(resourceUnitId)
                    if resourceUnit != None:
                        unitPath = self.unitService.getUnitPathBetweenUnits(self.unit, resourceUnit)                        
                        if unitPath != "":
                            unitPath = CommonUtil.sortNumberString(unitPath)
                            approvedPathInfo = resource.approvedPathInfo
                            if approvedPathInfo != None:      
                                arraypath = unitPath.split("/")
                                for p in range(len(arraypath) - 1, -1, -1):
                                    if arraypath[p] != "":
                                        partInfo = "/" + arraypath[p] + "/"
                                        approvedPathInfo = resource.approvedPathInfo
                                        if approvedPathInfo.find(partInfo) > -1:
                                            approvedPathInfo = approvedPathInfo.replace(partInfo, "/")
                                            if approvedPathInfo == "/":
                                                approvedPathInfo = None
                                                self.resourceService.unauditResource(resource)
                                            if approvedPathInfo != None:
                                                approvedPathInfo = CommonUtil.sortNumberString(approvedPathInfo)                                                
                                            resource.setApprovedPathInfo(approvedPathInfo)
                            self.resourceService.updateResource(resource)
    
    def setRcmd(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(g)
            if resource != None:
                resourceUnitId = resource.unitId
                if resourceUnitId != None:
                    resourceUnit = self.unitService.getUnitById(resourceUnitId)
                    if resourceUnit != None:
                        unitPath = self.unitService.getUnitPathBetweenUnits(self.unit, resourceUnit)
                        if unitPath != "":
                            unitPath = CommonUtil.sortNumberString(unitPath)
                            rcmdPathInfo = resource.rcmdPathInfo
                            if rcmdPathInfo == None:
                                resource.setRcmdPathInfo(unitPath)
                            else:                                
                                arraypath = unitPath.split("/")
                                for p in range(len(arraypath) - 1, -1, -1):
                                    if arraypath[p] != "":
                                        partInfo = "/" + arraypath[p] + "/"
                                        rcmdPathInfo = resource.rcmdPathInfo
                                        if rcmdPathInfo.find(partInfo) == -1:
                                            PathInfo = "/" + arraypath[p] + rcmdPathInfo
                                            PathInfo = CommonUtil.sortNumberString(PathInfo)
                                            resource.setRcmdPathInfo(PathInfo)
                            self.resourceService.updateResource(resource)
                            
    
    def unsetRcmd(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(g)
            if resource != None:
                resourceUnitId = resource.unitId
                if resourceUnitId != None:
                    resourceUnit = self.unitService.getUnitById(resourceUnitId)
                    if resourceUnit != None:
                        unitPath = self.unitService.getUnitPathBetweenUnits(self.unit, resourceUnit)
                        if unitPath != "":
                            unitPath = CommonUtil.sortNumberString(unitPath)
                            rcmdPathInfo = resource.rcmdPathInfo
                            if rcmdPathInfo != None:      
                                arraypath = unitPath.split("/")
                                for p in range(len(arraypath) - 1, -1, -1):
                                    if arraypath[p] != "":
                                        partInfo = "/" + arraypath[p] + "/"
                                        rcmdPathInfo = resource.rcmdPathInfo
                                        if rcmdPathInfo.find(partInfo) > -1:
                                            rcmdPathInfo = rcmdPathInfo.replace(partInfo, "/")
                                            if rcmdPathInfo == "/":
                                                rcmdPathInfo = None
                                            
                                            if rcmdPathInfo != None:
                                                rcmdPathInfo = CommonUtil.sortNumberString(rcmdPathInfo)
                                            resource.setRcmdPathInfo(rcmdPathInfo)
                            self.resourceService.updateResource(resource)
        
    # 把资源分类放到 request 环境中.
    def putResourceCategoryTree(self):
        resource_categories = self.cate_svc.getCategoryTree('resource')
        request.setAttribute("resource_categories", resource_categories)
    def putResourceUserCategory(self):
        resource_user = self.cate_svc.getCategoryTree('user_res_1')
        request.setAttribute("resource_user", resource_user)

    # 设置资源所属资源类型.
    def move_cate(self):
        sysCateId = self.params.getIntParamZeroAsNull("sysCateId")
        if sysCateId != None:
            category = self.cate_svc.getCategory(sysCateId)
            if category == None:
                self.addActionError(u"指定标识的资源类型不存在.")
                return self.ERROR
            if category.itemType != 'resource':
                self.addActionError(u"指定标识的分类 %s 不是一个正确的资源类型, 请确定您是从有效链接提交的数据." % category.name)
                return self.ERROR
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                resource = self.resourceService.getResource(g)
                if resource != None:
                    if resource.sysCateId != sysCateId:
                        self.resourceService.updateResourceSysCate(resource, sysCateId)
        else:
            #设置为空类型
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                resource = self.resourceService.getResource(g)
                if resource != None:
                    self.resourceService.updateResourceSysCate(resource, sysCateId)
