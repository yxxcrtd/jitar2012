#script 

from cn.edustar.jitar import PythonAction
from cn.edustar.jitar.data import *
from cn.edustar.jitar.util import ParamUtil
from resource_query import ResourceQuery
from base_action import *


# 数据获取执行脚本.
class site_subject_resources(SubjectMixiner):
  def __init__(self):
    self.params = ParamUtil(request)

  def execute(self):
    #print "site_subject_resources python 脚本正在执行"
    self.putSubject()
    
    # 资源分类
    self.get_res_cates()
 
    # 资源上载排行
    self.get_upload_sorter()
    
    # 资源下载排行
    self.get_download_resource_list()
       
    # 高亮显示项目.
    self.setData("head_nav", "resources")
    
    return "success"
    


  def _getCateSvc(self):
    return __jitar__.categoryService
  
  # 资源分类.
  def get_res_cates(self):
    res_cates = self._getCateSvc().getCategoryTree("resource")
    request.setAttribute("res_cates", res_cates)
    
    
    # 资源主列表.
    self.query_resource()
    #resource_list = QueryResourceBean()
    #resource_list.varName = "resource_list"
    #resource_list.usePager = 1
    #self.addBean(resource_list)
    
  # 资源上载排行.
  def get_upload_sorter(self):
    qry = UserQuery("""  u.resourceCount, u.loginName, u.nickName """)
    qry.orderType = 4 #UserQuery.ORDER_TYPE_RESOURCE_COUNT_DESC
    upload_sorter = qry.query_map(20) 
    request.setAttribute("upload_sorter", upload_sorter)
    
  # 资源下载排行.
  def get_download_resource_list(self):
    qry = ResourceQuery(""" r.resourceId, r.href, r.title, r.downloadCount """)
    qry.subjectId = self.params.getIntParamZeroAsNull("subjectId")
    qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
    qry.k = self.params.getStringParam("k")
    
    qry.orderType = 4
    download_resource_list = qry.query_map(20) 
    request.setAttribute("download_resource_list", download_resource_list)
    #print "download_resource_list = ", download_resource_list
    
        
  # 资源查询主列表.
  def query_resource(self):
    qry = ResourceQuery(""" r.resourceId, r.title, r.href, r.fsize, r.createDate, 
          u.loginName, u.nickName, grad.gradeName, sc.name as scName """)
    pager = self.createPager()
    
    # 根据页面参数处理.
    type = self.params.getStringParam("type")
    if type == "rcmd":
      qry.rcmdState = True
    elif type == "hot":
      qry.orderType = ResourceQuery.ORDER_TYPE_DOWNLOADCOUNT_DESC
    elif type == "cmt":
      qry.orderType = ResourceQuery.ORDER_TYPE_COMMENTCOUNT_DESC
    else:
      type = "new"
    request.setAttribute("type", type)
    
    qry.subjectId = self.subject.subjectId
    qry.k = self.params.getStringParam("k")
    qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
    
    # 查询数据.
    pager.totalRows = qry.count()
    resource_list = qry.query_map(pager)
    
    request.setAttribute("resource_list", resource_list)
    request.setAttribute("pager", pager)
    
  def createPager(self):
    pager = self.params.createPager()
    pager.pageSize = 20
    pager.itemName = u"资源"
    pager.itemUnit = u"个"
    return pager
    