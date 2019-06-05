from video_query import VideoQuery
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command

# 数据获取脚本12
class video_list:
    def __init__(self):
        self.params = ParamUtil(request)
    def execute(self):
        type = self.params.safeGetStringParam("type")
        
        qry = VideoQuery(""" v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref """)
        if type == "hot":
            qry.orderType = VideoQuery.ORDER_TYPE_VIEWCOUNT_DESC
        else:
            qry.orderType = VideoQuery.ORDER_TYPE_VIDEOID_DESC
        
        t = u"最新上传"
        if type == "hot":
            t = u"热门排行"
        elif type == "search":
            t = u"关键字：" + qry.k        
            
        pager = self.createPager()
        pager.totalRows = qry.count()
        video_list = qry.query_map(pager)
        
        request.setAttribute("video_list", video_list)
        
        request.setAttribute("t", t)
        request.setAttribute("type", type)
        request.setAttribute("pager", pager)
        request.setAttribute("head_nav", "videos")
        return "/WEB-INF/ftl/site_video_list.ftl"

    def createPager(self):
        # private 构造文章的缺省 pager.
        pager = self.params.createPager()
        pager.itemName = u"视频"
        pager.itemUnit = u"个"
        pager.pageSize = 30
        return pager
