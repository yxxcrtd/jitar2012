from subject_page import *
from cn.edustar.jitar.data import *
from base_action import BaseAction, ActionResult, SubjectMixiner
from video_query import VideoQuery


class video(BaseSubject, SubjectMixiner):
    def __init__(self):
        BaseSubject.__init__(self)
        
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        cache = __jitar__.cacheProvider.getCache('page')
        
        self.templateName = "template1"
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName
        
        
        self.get_video_category()
        self.get_video_list()
        
        Page_Title = self.params.safeGetStringParam("title")
        if Page_Title == "":
            Page_Title = u"全部分类"
        request.setAttribute("Page_Title", Page_Title)
        request.setAttribute("subject", self.subject)
        request.setAttribute("head_nav", "video")
        return "/WEB-INF/subjectpage/" + self.templateName + "/video_page.ftl"

    def get_video_category(self):
        video_categories = __jitar__.categoryService.getCategoryTree('video')
        root_cates = []
        for c in video_categories.all:
          root_cates.append({'categoryId': c.categoryId, 'categoryName': c.name, 'parentId':c.parentId })
        request.setAttribute("root_cates", root_cates)
        
    def get_video_list(self):
        qry = VideoQuery(""" v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref """)
        qry.orderType = VideoQuery.ORDER_TYPE_VIDEOID_DESC
        qry.subjectId = self.subject.metaSubject.msubjId
        qry.gradeId = self.subject.metaGrade.gradeId
        if self.unitId != None and self.unitId != 0:
            qry.unitId = self.unitId
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
