#encoding=utf-8
from unit_page import UnitBasePage
from photo_query import PhotoQuery
from cn.edustar.jitar.pojos import Photo

class unit_image(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.photoService = __jitar__.photoService
        self.categoryService = __jitar__.categoryService
        self.photo_service = __spring__.getBean("photoStapleService")
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
        
        cmd = self.params.getStringParam("cmd")
        self.clear_cache()
        if cmd == "audit" :
            self.Audit()         # 审核通过
        elif cmd == "unaudit" :
            self.UnAudit()       # 取消审核
        elif cmd == "delete" :
            self.Delete()        # 删除(放入回收站)
        elif cmd == "private" :   # 将选择的照片只在个人空间显示            
            self.Show()

        self.List()          # 列表显示
        
        request.setAttribute("unit",self.unit)        
        return "/WEB-INF/unitsmanage/unit_owner_image.ftl"
    
    def Audit(self):
        photoId = self.params.safeGetIntValues("photoId")
        for fid in photoId:
            photo = self.photoService.findById(fid)
            if photo != None:
                if photo.auditState != Photo.AUDIT_STATE_OK:
                    self.photoService.auditPhoto(photo)
        
    def UnAudit(self):
        photoId = self.params.safeGetIntValues("photoId")
        for fid in photoId:
            photo = self.photoService.findById(fid)
            if photo != None:
                if photo.auditState != Photo.AUDIT_STATE_WAIT_AUDIT:
                    self.photoService.unAuditPhoto(photo)
                    
    def Delete(self):
        photoId = self.params.safeGetIntValues("photoId")
        for fid in photoId:
            photo = self.photoService.findById(fid)
            if photo != None:
                self.photoService.delPhoto(photo)
                    
    def Show(self):
        photoId = self.params.safeGetIntValues("photoId")
        for fid in photoId:
            photo = self.photoService.findById(fid)
            if photo != None and photo.isPrivateShow == False:
                self.photoService.privateShow(fid)
                                
    # 系统所有照片的列表显示.
    def List(self) :
        # 构造查询.
        query = PhotoQuery(""" p.photoId, p.title, p.summary, p.href, p.tags, stap.title as stapTitle, 
                           sc.name as sysPhotoName, p.viewCount, p.createDate, p.addIp, 
                           u.nickName, u.loginName, p.auditState, p.delState, p.isPrivateShow """)
        
        query.unitId = self.unit.unitId
        query.isPrivateShow = None
        query.auditState = None
        query.delState = None
        
        auditState = self.params.safeGetStringParam("auditState")
        if auditState=="1":
            query.auditState = 1
        elif auditState=="0":
            query.auditState = 0
        
        isPrivateShow = self.params.safeGetStringParam("isPrivateShow")
        if isPrivateShow=="1":
            query.isPrivateShow = 1
        elif isPrivateShow=="0":
            query.isPrivateShow = 0
            
        query.k = self.params.safeGetStringParam("k")
        query.f = self.params.safeGetStringParam("f")
        query.sysCateId = self.params.getIntParamZeroAsNull("sc")
        query.photoStaple = self.params.getIntParamZeroAsNull("ps")
        self.putSysCategoryTree()
        self.putPhotoStaple()
        
        # TODO: 权限检查.
        
        # 调用分页函数.
        pager = self.createPager()
        pager.totalRows = query.count()
            
        # 得到所有照片的列表.
        photoList = query.query_map(pager)
        #print "photoList = ", photoList
            
        # 传给页面.
        request.setAttribute("photoList", photoList)
        request.setAttribute("pager", pager)
        request.setAttribute("auditState", auditState)
        request.setAttribute("isPrivateShow", isPrivateShow)
        request.setAttribute("k", query.k)
        request.setAttribute("f", query.f)
        request.setAttribute("sc", query.sysCateId)
        request.setAttribute("ps", query.photoStaple)
        

    # 把文章分类树放置到 request 中.
    def putSysCategoryTree(self):
        syscate_tree = self.categoryService.getCategoryTree('photo')
        request.setAttribute("syscate_tree", syscate_tree)


    # 所有照片的分类列表.
    def putPhotoStaple(self):
        photoStapleList = self.photo_service.getPhotoStapleList()
        request.setAttribute("photoStapleList", photoStapleList)
        
    def createPager(self):
        # 调用Java的函数.
        pager = self.params.createPager()
        pager.itemName = u"图片"
        pager.itemUnit = u"张"
        pager.pageSize = 10
        return pager