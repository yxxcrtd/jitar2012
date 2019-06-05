from video_query import VideoQuery
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command

# 数据获取脚本12
class videos:
  def __init__(self):
    self.video_svc = __spring__.getBean("videoService")
    self.params = ParamUtil(request)
    
  def execute(self):    
    #单独加的
    cmd= self.params.getStringParam("cmd")
    if cmd=="query":
      self.get_video_querylist()
      return "/WEB-INF/ftl/queryvideo.ftl"
    # 最新视频    
    self.get_new_video_list()
    # 视频点击排行
    self.get_hot_video_list()    
    # 得到视频分类
    self.get_video_category_ex()
        
    # 页面导航高亮为 'gallery'
    request.setAttribute("head_nav", "videos")
    return "/WEB-INF/ftl/site_videos.ftl"

  def get_video_querylist(self):
    qry = VideoQuery(" v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, v.flvThumbNailHref,v.auditState ")
    qry.auditState = None
    qry.orderType = VideoQuery.ORDER_TYPE_VIDEOID_DESC
    vIds=self.params.getStringParam("id")
    qry.videoIds=vIds
    video_list = qry.query_map()
    request.setAttribute("video_list", video_list)
    
  # 获得最新视频列表
  def get_new_video_list(self):
    qry = VideoQuery(" v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, v.flvThumbNailHref ")
    qry.orderType = VideoQuery.ORDER_TYPE_VIDEOID_DESC
    new_video_list = qry.query_map(10)
    request.setAttribute("new_video_list", new_video_list)
  # 视频点击排行
  def get_hot_video_list(self):
    qry = VideoQuery(" v.videoId, v.title , u.loginName,v.flvThumbNailHref ")
    qry.orderType = VideoQuery.ORDER_TYPE_VIEWCOUNT_DESC
    hot_video_list = qry.query_map(10)
    request.setAttribute("hot_video_list", hot_video_list)
    
  # 得到视频分类
  def get_video_category(self):
    video_cates = self.get_video_category_ex()    
    # 得到每个分类的最新的 6 张视频
    qry = VideoQuery(" v.videoId, v.title, v.href,u.loginName,v.flvThumbNailHref")
    for c in video_cates:
      qry.sysCateTitle = c["categoryName"]
      c["video_list"] = qry.query_map(5)
    
    #如果没有分类，则显示当前的全部图片
    if len(video_cates) == 0:     
      video_cates = None
      pager = self.params.createPager()
      pager.itemName = u"视频"
      pager.itemUnit = u"个"
      pager.pageSize = 24
      qry = VideoQuery(" v.videoId, v.title, v.createDate, v.href, v.userId, u.loginName, v.flvThumbNailHref ")
      qry.orderType = 0
      pager.totalRows = qry.count()
      video_list_all = qry.query_map(pager)
      request.setAttribute("video_list_all", video_list_all)
      request.setAttribute("pager", pager)
    request.setAttribute("video_cates", video_cates)
  
  # 得到视频分类, 如果想显示所有分类
  def get_video_category_ex(self):
    video_categories = __jitar__.categoryService.getCategoryTree('video')
    root_cates = []
    for c in video_categories.all:
      root_cates.append({'categoryId': c.categoryId, 'categoryName': c.name, 'parentId':c.parentId })
    request.setAttribute("root_cates", root_cates)