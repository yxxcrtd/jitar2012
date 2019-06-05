from unit_page import UnitBasePage
from video_query import VideoQuery
from cn.edustar.jitar.pojos import Video

class unit_video(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.videoService = __spring__.getBean("videoService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.videoService == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 videoService 节点。")
            return self.ERROR        
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.isContentAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.form_post()
            self.clear_cache()
            if request.getHeader("Referer") != None and request.getHeader("Referer") != "":
                response.sendRedirect(request.getHeader("Referer"))
            
        self.video_list()
        
        request.setAttribute("unit", self.unit)
        
        
        return "/WEB-INF/unitsmanage/unit_video.ftl"
    
    def video_list(self):
        qry = VideoQuery(""" v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref,
                            v.tags, v.categoryId, v.auditState, v.addIp """)
        qry.orderType = VideoQuery.ORDER_TYPE_VIDEOID_DESC
        auditState = self.params.safeGetStringParam("auditState")
        qry.auditState = None
        if auditState == "1":
            qry.auditState = 1
        elif auditState == "0":
            qry.auditState = 0
        qry.k = self.params.safeGetStringParam("k")
        qry.f = self.params.safeGetStringParam("f")
        qry.delState = None   
        qry.unitId = self.unit.unitId
        pager = self.createPager()
        pager.totalRows = qry.count()
        video_list = qry.query_map(pager)
        request.setAttribute("video_list", video_list)
        request.setAttribute("pager", pager)
        request.setAttribute("auditState", auditState)
        request.setAttribute("k", qry.k)
        request.setAttribute("f", qry.f)
        
    def form_post(self):
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        if cmd == "delete":
            for g in guids:
                video = self.videoService.findById(g)
                if video != None:
                    self.videoService.delVideo(video)
                
        elif cmd == "approve":
            for g in guids:
                video = self.videoService.findById(g)
                if video != None:
                    self.videoService.auditVideo(video)
        elif cmd == "unapprove":
            for g in guids:
                video = self.videoService.findById(g)
                if video != None:
                    self.videoService.unauditVideo(video)
        
    def createPager(self):
        pager = self.params.createPager()
        pager.itemName = u"视频"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        return pager
        
