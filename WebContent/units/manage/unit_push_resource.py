from unit_page import UnitBasePage
from resource_query import ResourceQuery
from cn.edustar.jitar.pojos import Resource
from base_action import SubjectMixiner

class unit_push_resource(UnitBasePage, SubjectMixiner):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.resourceService = __spring__.getBean("resourceService")
        self.cate_svc = __jitar__.categoryService
        self.pun_svc = __jitar__.UPunishScoreService
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.isContentAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.post_action()
            self.clear_cache()
            if request.getHeader("Referer") != None and request.getHeader("Referer") != "":
                response.sendRedirect(request.getHeader("Referer"))
        
        self.get_list()
        
        request.setAttribute("unit", self.unit)
        return "/WEB-INF/unitsmanage/unit_push_resource.ftl"
    
    def get_list(self):
        query = ResourceQuery(""" r.resourceId, r.title, r.shareMode, r.createDate, r.href, r.subjectId, r.gradeId, r.rcmdPathInfo,
            r.delState, r.unitPathInfo, r.approvedPathInfo, r.downloadCount, r.commentCount, r.fsize, r.auditState, r.recommendState, r.tags, r.addIp, r.pushState,
            u.userId, u.loginName, u.nickName, sc.name as sysCateName, subj.subjectName """)
        
        multiPushState = self.params.safeGetStringParam("multiPushState")
        recommendState = self.params.safeGetStringParam("recommendState")
        auditState = self.params.safeGetStringParam("auditState")
        
        query.shareMode = None
        query.unitId = None #不使用该字段
        
        custormAndWhereClause = "r.unitPathInfo LIKE '%/" + str(self.unit.unitId) + "/%' And r.unitId != " + str(self.unit.unitId) + " And"
        if multiPushState == "1":
            custormAndWhereClause += " r.unitPathInfo LIKE '%/" + str(self.unit.parentId) + "/%' And"
        elif multiPushState == "0":
            custormAndWhereClause += " r.unitPathInfo NOT LIKE '%/" + str(self.unit.parentId) + "/%' And"
        
        if recommendState == "1":
            custormAndWhereClause += " r.rcmdPathInfo LIKE '%/" + str(self.unit.unitId) + "/%' And"
        elif recommendState == "0":
            custormAndWhereClause += " (r.rcmdPathInfo Is Null or r.rcmdPathInfo Not LIKE '%/" + str(self.unit.unitId) + "/%') And"
        
        if auditState == "0":
            custormAndWhereClause += " r.approvedPathInfo LIKE '%/" + str(self.unit.unitId) + "/%' And"
        elif auditState == "1":
            custormAndWhereClause += " (r.approvedPathInfo Is Null or r.approvedPathInfo NOT LIKE '%/" + str(self.unit.unitId) + "/%') And"
        
        if custormAndWhereClause[len(custormAndWhereClause) - 3:] == "And":
            custormAndWhereClause = custormAndWhereClause[0:len(custormAndWhereClause) - 3]
            
        query.custormAndWhereClause = custormAndWhereClause
        
        # 计算总量.
        pager = self.createPager()
        pager.pageSize = 20
        pager.totalRows = query.count()
        
        # 得到资源.
        resource_list = query.query_map(pager)
        
        request.setAttribute("pager", pager)
        request.setAttribute("resource_list", resource_list)
        request.setAttribute("multiPushState", multiPushState)
        request.setAttribute("recommendState", recommendState)
        request.setAttribute("auditState", auditState)
        
    def post_action(self):
        parentId = self.unit.parentId
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "remove":
            self.remove()                    
        elif cmd == "approveLevel":
            self.approveLevel()
        elif cmd == "unapproveLevel":
            self.unapproveLevel()
        elif cmd == "pushup":
            self.pushup()                                   
        elif cmd == "unpushup":
            self.unpushup()
        elif cmd == "setRcmd":
            self.setRcmd()
        elif cmd == "unsetRcmd":
            self.unsetRcmd()
        else:
            self.addActionError("无效的命令。")
            return self.ERROR
    
    def remove(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:
                approvedPathInfo = resource.getApprovedPathInfo()
                unitPathInfo = resource.getUnitPathInfo()
                rcmdPathInfo = resource.getRcmdPathInfo()
                if approvedPathInfo != None and approvedPathInfo.find("/" + str(self.unit.unitId) + "/") > -1:
                    approvedPathInfo = approvedPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                    if approvedPathInfo == "/":
                        approvedPathInfo = None
                    resource.setApprovedPathInfo(approvedPathInfo)
                if unitPathInfo != None and unitPathInfo.find("/" + str(self.unit.unitId) + "/") > -1:
                    unitPathInfo = unitPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                    if unitPathInfo == "/":
                        unitPathInfo = None
                    resource.setUnitPathInfo(unitPathInfo)
                if rcmdPathInfo != None and rcmdPathInfo.find("/" + str(self.unit.unitId) + "/") > -1:
                    rcmdPathInfo = rcmdPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                    if rcmdPathInfo == "/":
                        rcmdPathInfo = None
                    resource.setRcmdPathInfo(rcmdPathInfo)  
                self.resourceService.updateResource(resource)
    
    def approveLevel(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:
                approvedPathInfo = resource.getApprovedPathInfo()
                if approvedPathInfo == None:
                    resource.setApprovedPathInfo("/" + str(self.unit.unitId) + "/")
                    self.resourceService.updateResource(resource)
                elif approvedPathInfo.find("/" + str(self.unit.unitId) + "/") == -1:
                    resource.setApprovedPathInfo("/" + str(self.unit.unitId) + approvedPathInfo)
                    self.resourceService.updateResource(resource)
                    
    def unapproveLevel(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:
                approvedPathInfo = resource.getApprovedPathInfo()
                if approvedPathInfo != None and approvedPathInfo.find("/" + str(self.unit.unitId) + "/") > -1:
                    if approvedPathInfo == "/" + str(self.unit.unitId) + "/":
                        resource.setApprovedPathInfo(None)
                    else:
                        approvedPathInfo = approvedPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                        if approvedPathInfo == "/":
                            approvedPathInfo = None
                        resource.setApprovedPathInfo(approvedPathInfo)
                    self.resourceService.updateResource(resource)
    def pushup(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:
                unitPathInfo = resource.getUnitPathInfo()
                if unitPathInfo.find("/" + str(self.unit.parentId) + "/") == -1:
                    unitPathInfo = "/" + str(self.unit.parentId) + unitPathInfo
                    resource.setUnitPathInfo(unitPathInfo)
                    self.resourceService.updateResource(resource)
    
    def unpushup(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:
                unitPathInfo = resource.getUnitPathInfo()
                if unitPathInfo != None and unitPathInfo.find("/" + str(self.unit.parentId) + "/") > -1:
                    unitPathInfo = unitPathInfo.replace("/" + str(self.unit.parentId) + "/", "/")
                    if unitPathInfo == "/":
                        unitPathInfo = "/" + str(self.unit.unitId) + "/"
                    resource.setUnitPathInfo(unitPathInfo)
                    self.resourceService.updateResource(resource)
    
    def setRcmd(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:                
                rcmdPathInfo = resource.getRcmdPathInfo()
                if rcmdPathInfo == None:
                    rcmdPathInfo = "/" + str(self.unit.unitId) + "/"
                    resource.setRcmdPathInfo(rcmdPathInfo)
                    self.resourceService.updateResource(resource)
                else:
                    if rcmdPathInfo.find("/" + str(self.unit.unitId) + "/") == -1:
                        rcmdPathInfo = "/" + str(self.unit.unitId) + "/" + rcmdPathInfo
                        resource.setRcmdPathInfo(rcmdPathInfo)
                        self.resourceService.updateResource(resource)
                self.resourceService.rcmdResource(resource)
            
    def unsetRcmd(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:                
                rcmdPathInfo = resource.getRcmdPathInfo()
                if rcmdPathInfo != None and rcmdPathInfo.find("/" + str(self.unit.unitId) + "/") > -1:
                    rcmdPathInfo = rcmdPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                    if rcmdPathInfo == "/" or rcmdPathInfo == "//":
                        rcmdPathInfo = None
                    resource.setRcmdPathInfo(rcmdPathInfo)
                    self.resourceService.updateResource(resource)
                if resource.getRcmdPathInfo() == None:
                    self.resourceService.unrcmdResource(resource)
                    
    def createPager(self):
        # private 构造文章的缺省 pager.
        pager = self.params.createPager()
        pager.itemName = u"资源"
        pager.itemUnit = u"条"
        pager.pageSize = 20
        return pager
