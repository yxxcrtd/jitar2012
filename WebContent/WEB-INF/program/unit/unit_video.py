from unit_page import *
from video_query import VideoQuery

class unit_video(UnitBasePage):
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
        # 得到视频分类.
        self.get_video_category()
        self.get_video_list()
        
        request.setAttribute("type", "new")
        request.setAttribute("head_nav", "unit_video")
        request.setAttribute("unit", self.unit)      
    
        templateName = "template1"
        if self.unit.templateName != None:
            templateName = self.unit.templateName
        return "/WEB-INF/unitspage/" + templateName + "/unit_video.ftl"
    
    def get_video_category(self):
        video_categories = __jitar__.categoryService.getCategoryTree('video')
        root_cates = []
        for c in video_categories.all:
            root_cates.append({'categoryId': c.categoryId, 'categoryName': c.name,'parentId':c.parentId })
        request.setAttribute("root_cates", root_cates)
        
    def get_video_list(self):
        qry = VideoQuery(""" v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref """)
        qry.orderType = VideoQuery.ORDER_TYPE_VIDEOID_DESC
        qry.unitId = self.unit.unitId
        pager = self.createPager()
        pager.totalRows = qry.count()
        video_list = qry.query_map(pager)
        request.setAttribute("video_list", video_list)
        request.setAttribute("pager", pager)
        
    def createPager(self):
        # private 构造文章的缺省 pager.
        pager = self.params.createPager()
        pager.itemName = u"视频"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        return pager