from unit_page import UnitBasePage
from resource_query import ResourceQuery
from cn.edustar.jitar.pojos import Resource
from base_action import SubjectMixiner

class unit_resource(UnitBasePage, SubjectMixiner):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.res_svc = __jitar__.resourceService
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
        if self.res_svc == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 resourceService 节点。")
            return self.ERROR
        
        cmd = self.params.safeGetStringParam("cmd")
        if cmd != "":
            self.clear_cache()
        
        if cmd == "list":
            return self.list()
        elif cmd == "audit":
            self.audit()
        elif cmd == "unaudit":
            self.unaudit()
        elif cmd == "rcmd":
            self.rcmd()
        elif cmd == "unrcmd":
            self.unrcmd()
        elif cmd == "delete":
            self.delete()
        elif cmd == "move_cate":
            self.move_cate()
        elif cmd == "push":
            self.push()
        elif cmd == "unpush":
            self.unpush()
            
        self.list()
        
        configService = __jitar__.configService
        sys_config = configService.getConfigure()
        if sys_config != None:
            if sys_config["topsite_url"] != None and str(sys_config["topsite_url"]).strip() != "":
                request.setAttribute("topsite_url", "")
                
        request.setAttribute("unit", self.unit)        
        return "/WEB-INF/unitsmanage/unit_owner_resource.ftl"    
    
    # 显示资源列表.
    def list(self):
        # 构造查询.
        query = ResourceQuery(""" r.resourceId, r.title, r.shareMode, r.createDate, r.href, r.subjectId, r.gradeId, 
            r.downloadCount, r.commentCount, r.fsize, r.auditState, r.recommendState, r.tags, r.addIp, r.pushState,
            u.userId, u.loginName, u.nickName, sc.name as sysCateName, subj.subjectName """)
        
        query.auditState = None
        query.delState = False         # 过滤被删除了的.
        
        recommendState = self.params.safeGetStringParam("recommendState")
        if recommendState == "1":
            query.rcmdState = 1
        elif recommendState == "0":
            query.rcmdState = 0
            
        auditState = self.params.safeGetStringParam("auditState")
        if auditState == "1":
            query.auditState = 1
        elif auditState == "0":
            query.auditState = 0
                    
        query.shareMode = None
        query.unitId = self.unit.unitId
        
        # 根据参数设置过滤条件.
        type = self.params.getStringParam("type")
        request.setAttribute("type", type)
        if type == "rcmd":        # 推荐.
            query.rcmdState = True
        elif type == "unaudit":     # 待审核.
            query.auditState = Resource.AUDIT_STATE_WAIT_AUDIT        
          
        query.subjectId = self.params.getIntParamZeroAsNull("su")
        request.setAttribute("su", query.subjectId)
        query.gradeId = self.params.getIntParamZeroAsNull("gradeId")
        request.setAttribute("gradeId", query.gradeId)
        query.sysCateId = self.params.getIntParamZeroAsNull("sc")
        request.setAttribute("sc", query.sysCateId)
        query.kk = self.params.getStringParam("k")
        request.setAttribute("k", query.kk)
        query.f = self.params.getStringParam("f")
        request.setAttribute("f", query.f)
        
        # 计算总量.
        pager = self.createPager()
        pager.pageSize = 10
        pager.totalRows = query.count()
        
        # 得到资源.
        resource_list = query.query_map(pager)
        
        request.setAttribute("pager", pager)
        request.setAttribute("resource_list", resource_list)
        self.putSubjectList()
        self.putGradeList()
        self.putResourceCategoryTree()
        
        request.setAttribute("recommendState", recommendState)
        request.setAttribute("auditState", auditState)
        return "/WEB-INF/unitsmanage/unit_owner_resource.ftl"    

    # 审核通过所选的资源.
    def audit(self):
        guids = self.params.safeGetIntValues("resourceId")
        for g in guids:
            resource = self.res_svc.getResource(g)
            if resource != None:
                self.res_svc.auditResource(resource)
    
    # 取消审核所选的资源, 设置其审核状态为 WAIT_AUDIT.
    def unaudit(self):
        guids = self.params.safeGetIntValues("resourceId")
        for g in guids:
            resource = self.res_svc.getResource(g)
            if resource != None:
                self.res_svc.unauditResource(resource)
    
    # 推送.
    def push(self):
        guids = self.params.safeGetIntValues("resourceId")
        for g in guids:
            resource = self.res_svc.getResource(g)
            if resource != None:
                if resource.auditState == 0 and resource.pushState == 0:
                    resource.setPushState(2)
                    resource.setPushUserId(self.loginUser.userId)
                    self.res_svc.updateResource(resource)
                
    # 推送.
    def unpush(self):
        guids = self.params.safeGetIntValues("resourceId")
        for g in guids:
            resource = self.res_svc.getResource(g)
            if resource != None:
                if resource.pushState == 2:
                    resource.setPushState(0)
                    resource.setPushUserId(None)
                    self.res_svc.updateResource(resource)
                
    # 推荐所选资源.
    def rcmd(self):
        guids = self.params.safeGetIntValues("resourceId")
        for g in guids:
            resource = self.res_svc.getResource(g)
            if resource != None:
                self.res_svc.rcmdResource(resource)
                
    # 取消推荐所选资源.
    def unrcmd(self):
        guids = self.params.safeGetIntValues("resourceId")
        for g in guids:
            resource = self.res_svc.getResource(g)
            if resource != None:
                self.res_svc.unrcmdResource(resource)

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
        guids = self.params.safeGetIntValues("resourceId")
        for g in guids:
            resource = self.res_svc.getResource(g)
            if resource != None:
                if resource.sysCateId != sysCateId:
                    self.res_svc.updateResourceSysCate(resource, sysCateId)
  
    # 删除所选的一个或多个资源.
    def delete(self):
        guids = self.params.safeGetIntValues("resourceId")
        for g in guids:
            resource = self.res_svc.getResource(g)
            if resource != None:
                self.res_svc.deleteResource(resource)
                
    def createPager(self):
        # private 构造资源的缺省 pager.
        pager = self.params.createPager()
        pager.itemName = u"资源"
        pager.itemUnit = u"个"
        return pager
  
    # 把资源分类放到 request 环境中.
    def putResourceCategoryTree(self):
        resource_categories = self.cate_svc.getCategoryTree('resource')
        request.setAttribute("resource_categories", resource_categories)
    def putResourceUserCategory(self):
        resource_user = self.cate_svc.getCategoryTree('user_res_1')
        request.setAttribute("resource_user", resource_user)
