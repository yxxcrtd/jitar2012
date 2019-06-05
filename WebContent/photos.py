from photo_query import PhotoQuery
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command

# 数据获取脚本.
class photos:
  def __init__(self):
    self.cate_svc = __jitar__.categoryService
    self.params = ParamUtil(request)
    
  def execute(self):
    # 最新图片.
    
    self.get_new_photo_list()

    # 图片点击排行.
    self.get_hot_photo_list()
    
    # 得到图片分类.
    self.get_photo_category()
    
   
    # 页面导航高亮为 'gallery'
    request.setAttribute("head_nav", "gallery")

    return "/WEB-INF/ftl/site_photos.ftl"

  # 获得最新图片列表.
  def get_new_photo_list(self):
    qry = PhotoQuery(""" p.photoId, p.title, p.createDate, p.href, u.userId, u.loginName, u.nickName, p.summary """)
    qry.orderType = PhotoQuery.ORDER_TYPE_PHOTOID_DESC
    new_photo_list = qry.query_map(10)
    request.setAttribute("new_photo_list", new_photo_list)
    #DEBUG: print "new_photo_list = ", new_photo_list

    
  # 图片点击排行.
  def get_hot_photo_list(self):
    qry = PhotoQuery(""" p.photoId, p.title,u.loginName """)
    qry.orderType = PhotoQuery.ORDER_TYPE_VIEWCOUNT_DESC
    hot_photo_list = qry.query_map(20)
    request.setAttribute("hot_photo_list", hot_photo_list)
    #DEBUG: print "hot_photo_list = ", hot_photo_list

  # 得到相册分类.
  def get_photo_category(self):
    # 得到根一级图片分类.
    photo_cates = self.get_photo_root_category()
    
    # 得到每个分类的最新的 6 张图片.
    qry = PhotoQuery(""" p.photoId, p.title, p.href, u.loginName """)
    for c in photo_cates:
      qry.sysCateId = c["categoryId"]
      c["photo_list"] = qry.query_map(6) 
    
    #如果没有分类，则显示当前的全部图片
    if len(photo_cates) == 0:     
      photo_cates = None
      pager = self.params.createPager()
      pager.itemName = u"图片"
      pager.itemUnit = u"张"
      pager.pageSize = 24
      qry = PhotoQuery(""" p.photoId, p.title, p.createDate, p.href, u.userId, u.loginName, u.nickName, p.summary """)
      qry.orderType = 0
      pager.totalRows = qry.count()
      photo_list_all = qry.query_map(pager)
      request.setAttribute("photo_list_all", photo_list_all)
      request.setAttribute("pager", pager)      
      
    #DEBUG: print "photo_cates = ", photo_cates
    request.setAttribute("photo_cates", photo_cates)
  
  # 得到图片分类的根分类, 如果想显示所有分类, 把 for c in photo_categories.root 变成 for c in photo_categories 即可.
  def get_photo_root_category(self):
    photo_categories = self.cate_svc.getCategoryTree('photo')
    #print "photo_categories = ", photo_categories
    root_cates = []
    for c in photo_categories.root:
      root_cates.append({'categoryId': c.categoryId, 'categoryName': c.name })
    return root_cates
  