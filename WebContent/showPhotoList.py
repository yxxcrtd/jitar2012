from cn.edustar.jitar.util import ParamUtil
from photo_query import PhotoQuery
from site_config import SiteConfig

class showPhotoList:        
    def __init__(self):                
        self.params = ParamUtil(request)
        self.categoryId = None
        self.cate_svc = __jitar__.categoryService
        
        
    def execute(self):
        #print "photos 脚本执行"
        site_config = SiteConfig()
        site_config.get_config()        
        
        self.categoryId = self.params.safeGetIntParam("categoryId")
        if self.categoryId == 0 :
            response.writer.write(u"没有该分类。")
            return
        
        category = self.cate_svc.getCategory(self.categoryId)
        if category == None :
            response.writer.write(u"没有该分类记录。")
            return
        
        request.setAttribute("category", category)
        
        # 最新图片.
        self.get_new_photo_list()
        
        # 图片点击排行.
        self.get_hot_photo_list()
       
        self.get_photo_with_pager()
        
        # 页面导航高亮为 'gallery'
        request.setAttribute("head_nav", "gallery")
    
        return "/WEB-INF/ftl/show_photo_list.ftl"
    
       
    
    # 获得最新图片列表.
    def get_new_photo_list(self):
        qry = PhotoQuery(""" p.photoId, p.title, p.createDate, p.href, u.userId, u.loginName, u.nickName, p.summary """)
        qry.sysCateId = int(self.categoryId)
        qry.orderType = 0       # photoId DESC
        new_photo_list = qry.query_map(10)
        request.setAttribute("new_photo_list", new_photo_list)
        #DEBUG: print "new_photo_list = ", new_photo_list
    
    
    # 图片点击排行.
    def get_hot_photo_list(self):
        qry = PhotoQuery(""" p.photoId, p.title,u.loginName """)
        qry.sysCateId = int(self.categoryId)
        qry.orderType = 2       # viewCount DESC
        hot_photo_list = qry.query_map(20)
        request.setAttribute("hot_photo_list", hot_photo_list)
        
    def get_photo_with_pager(self):
        pager = self.params.createPager()
        pager.itemName = u"图片"
        pager.itemUnit = u"张"
        pager.pageSize = 24
        qry = PhotoQuery(""" p.photoId, p.title, p.createDate, p.href, u.userId, u.loginName, u.nickName, p.summary """)
        qry.sysCateId = int(self.categoryId)
        qry.orderType = 0
        pager.totalRows = qry.count()
        photo_list = qry.query_map(pager)
        request.setAttribute("photo_list", photo_list)
        request.setAttribute("pager", pager)