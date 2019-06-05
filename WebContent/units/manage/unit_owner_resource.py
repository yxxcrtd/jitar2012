from unit_page import UnitBasePage
from resource_query import ResourceQuery
from cn.edustar.jitar.pojos import Resource
from base_action import SubjectMixiner

class unit_owner_resource(UnitBasePage, SubjectMixiner):
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
        configService = __jitar__.configService
        sys_config = configService.getConfigure()
        if sys_config != None:
            if sys_config["topsite_url"] != None and str(sys_config["topsite_url"]).strip() != "":
                request.setAttribute("topsite_url", "")                
        
        return "/WEB-INF/unitsmanage/unit_owner_resource.ftl"
    
    def post_action(self):
        #计算 ParenUnitId       
        pid = self.unit.unitId
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "rcmdPathInfo":
            self.rcmdPathInfo()
        elif cmd == "unrcmdPathInfo":
            self.unrcmdPathInfo()
        elif cmd == "approve":
            self.approve()
        elif cmd == "unapprove":
            self.unapprove()       
        elif cmd == "delete":
            self.delete()    
        elif cmd == "undelete":
            self.undelete()        
        elif cmd == "push_up":
            self.push_up()
        elif cmd == "un_push_up":
            self.un_push_up()
        elif cmd == "push":
            self.push()
        elif cmd == "unpush":
            self.unpush()
        elif cmd == "move_cate":
            self.move_cate()
        else:
            self.addActionError(u"无效的命令。")
            return self.ERROR
            
    def get_list(self):
        query = ResourceQuery(""" r.resourceId, r.title, r.shareMode, r.createDate, r.href, r.subjectId, r.gradeId, r.rcmdPathInfo,
            r.delState, r.unitPathInfo, r.approvedPathInfo, r.downloadCount, r.commentCount, r.fsize, r.auditState, r.recommendState, r.tags, r.addIp, r.pushState,
            u.userId, u.loginName, u.nickName, sc.name as sysCateName, subj.subjectName """)
        
        query.unitId = None
        query.auditState = None
        query.delState = None
        
        multiPushState = self.params.safeGetStringParam("multiPushState")
        recommendState = self.params.safeGetStringParam("recommendState")
        auditState = self.params.safeGetStringParam("auditState")
        delState = self.params.safeGetStringParam("delState")
        
        custormAndWhereClause = "r.unitId = " + str(self.unit.unitId) + " And"
        
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
        
        if delState == "0":
            query.delState = False
        elif delState == "1":
            query.delState = True
        
        if custormAndWhereClause[len(custormAndWhereClause) - 3:] == "And":
            custormAndWhereClause = custormAndWhereClause[0:len(custormAndWhereClause) - 3]
        query.custormAndWhereClause = custormAndWhereClause
        #print custormAndWhereClause
        
        query.shareMode = None
        #query.unitId = self.unit.unitId
                
        
        # 计算总量.
        pager = self.createPager()
        pager.pageSize = 20
        pager.totalRows = query.count()
        
        # 得到资源.
        resource_list = query.query_map(pager)
        
        request.setAttribute("pager", pager)
        request.setAttribute("resource_list", resource_list)
        self.putSubjectList()
        self.putGradeList()
        self.putResourceCategoryTree()
        
        request.setAttribute("delState", delState)
        request.setAttribute("multiPushState", multiPushState)
        request.setAttribute("recommendState", recommendState)
        request.setAttribute("auditState", auditState)  
    
    def createPager(self):
        # private 构造文章的缺省 pager.
        pager = self.params.createPager()
        pager.itemName = u"资源"
        pager.itemUnit = u"条"
        pager.pageSize = 20
        return pager
    
    
    # 推荐文章，是推荐到本机构，而不是上级机构
    def rcmdPathInfo(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:
                
                rcmdPathInfo = resource.getRcmdPathInfo()
                if rcmdPathInfo == None:
                    resource.setRcmdPathInfo("/" + str(self.unit.unitId) + "/")
                    self.resourceService.updateResource(resource)
                elif rcmdPathInfo.find("/" + str(self.unit.unitId) + "/") == -1:
                    rcmdPathInfo = "/" + str(self.unit.unitId) + rcmdPathInfo
                    resource.setRcmdPathInfo(rcmdPathInfo)
                    self.resourceService.updateResource(resource)
                # 保留原先的字段
                self.resourceService.rcmdResource(resource)
    
    # 取消推荐文章，是取消推荐文章到本机构，
    def unrcmdPathInfo(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:
                
                rcmdPathInfo = resource.getRcmdPathInfo()
                if rcmdPathInfo != None and rcmdPathInfo.find("/" + str(self.unit.unitId) + "/") != -1:                    
                    rcmdPathInfo = rcmdPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                    if rcmdPathInfo == "/":
                        rcmdPathInfo = None
                    resource.setRcmdPathInfo(rcmdPathInfo)
                    self.resourceService.updateResource(resource)
                if resource.getRcmdPathInfo() == None:
                    # 保留原先的字段
                    self.resourceService.unrcmdResource(resource)
    
    # 审核资源
    def approve(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None: 
                # 保留之前的做法                                  
                self.resourceService.auditResource(resource)
                approvedPathInfo = resource.getApprovedPathInfo()
                if approvedPathInfo == None:
                    approvedPathInfo = "/" + str(self.unit.unitId) + "/"
                    resource.setApprovedPathInfo(approvedPathInfo)
                    self.resourceService.updateResource(resource)
                elif approvedPathInfo.find("/" + str(self.unit.unitId) + "/") == -1:
                    approvedPathInfo = "/" + str(self.unit.unitId) + approvedPathInfo
                    resource.setApprovedPathInfo(approvedPathInfo)
                    self.resourceService.updateResource(resource)
    
    #取消审核
    def unapprove(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:                                   
                self.resourceService.unauditResource(resource)
                approvedPathInfo = resource.getApprovedPathInfo()
                if approvedPathInfo != None and approvedPathInfo.find("/" + str(self.unit.unitId) + "/") != -1:
                    approvedPathInfo = approvedPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                    if approvedPathInfo == "/":
                        approvedPathInfo = None
                    resource.setApprovedPathInfo(approvedPathInfo)
                    self.resourceService.updateResource(resource)
    def delete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:                                   
                self.resourceService.deleteResource(resource)
    
    def undelete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:                                   
                self.resourceService.recoverResource(resource)
                    
    def push_up(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:
                #判断是否已经推送给上级
                if resource.unitPathInfo.find("/" + str(self.unit.parentId) + "/") == -1:
                    #未推送，则执行推送
                    resource.setUnitPathInfo("/" + str(self.unit.parentId) + resource.unitPathInfo)
                    self.resourceService.updateResource(resource)
    def un_push_up(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(int(g))
            if resource != None:
                #判断是否已经推送给上级
                if resource.unitPathInfo != str(self.unit.unitId) + "/":
                    #已经推送，则执行取消推送
                    resource.setUnitPathInfo("/" + str(self.unit.unitId) + "/")
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
                    
                    
    # 推送.
    def push(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(g)
            if resource != None:
                if resource.auditState == 0 and resource.pushState == 0:
                    resource.setPushState(2)
                    resource.setPushUserId(self.loginUser.userId)
                    self.resourceService.updateResource(resource)
                
    # 推送.
    def unpush(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            resource = self.resourceService.getResource(g)
            if resource != None:
                if resource.pushState == 2:
                    resource.setPushState(0)
                    resource.setPushUserId(None)
                    self.resourceService.updateResource(resource)
