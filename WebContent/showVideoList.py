from cn.edustar.jitar.util import ParamUtil
from video_query import VideoQuery

class showVideoList:        
    def __init__(self):                
        self.params = ParamUtil(request)
        self.categoryName = None
        self.type = "new"
        self.video_svc = __spring__.getBean("videoService")
        
    def execute(self):
        vid = self.params.getIntParam("vid")
        
        self.type = self.params.getStringParam("type")
        if self.type == None or self.type == "" :
            self.type = "new"
        
        if (vid != None) and (vid != 0):
            video = self.video_svc.findById(vid)
            self.categoryName = video.staple
        else:    
            self.categoryName = self.params.getStringParam("categoryName")
        
        request.setAttribute("categoryName", self.categoryName)
        request.setAttribute("categoryShowTitle", u"所有视频")
        if ((self.categoryName != None) and (self.categoryName != "")) :
            request.setAttribute("categoryShowTitle", self.categoryName + u"分类下的所有视频")
        else:
            if self.type == "new":
                request.setAttribute("categoryShowTitle", u"最新视频")
            if self.type == "hot":
                request.setAttribute("categoryShowTitle", u"视频排行")
                
        request.setAttribute("type", type)
        
        self.get_video_with_pager()
        
        # 页面导航高亮为 'video'
        request.setAttribute("head_nav", "video")
    
        return "/WEB-INF/ftl/show_video_list.ftl"
        
    def get_video_with_pager(self):
        pager = self.params.createPager()
        pager.itemName = u"视频"
        pager.itemUnit = u"个"
        pager.pageSize = 24
        qry = VideoQuery(""" v.videoId, v.title, v.createDate, v.href, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref """)
        if ((self.categoryName != None) and (self.categoryName != "")) :
            qry.sysCateTitle = self.categoryName
        if self.type == "new":
            qry.orderType = 1
        elif self.type == "hot":
            qry.orderType = 2
        else:
            qry.orderType = 0
        pager.totalRows = qry.count()
        video_list = qry.query_map(pager)
        request.setAttribute("video_list", video_list)
        request.setAttribute("pager", pager)