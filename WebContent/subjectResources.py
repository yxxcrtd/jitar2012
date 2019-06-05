from cn.edustar.jitar import PythonAction
from cn.edustar.jitar.data import *
from cn.edustar.jitar.util import ParamUtil
from resource_query import ResourceQuery
from base_action import SubjectMixiner
from user_query import UserQuery 

cache = __jitar__.cacheProvider.getCache('main')

# 数据获取执行脚本.
class subjectResources(SubjectMixiner, PythonAction):
  def __init__(self):
    self.params = ParamUtil(request)
    self.subj_svc = __jitar__.subjectService
    self.sbj_svc = self.subj_svc

  def execute(self):
    self.putSubject()
    self.levelGradeId = self.params.getIntParam("levelGradeId")
    if (self.levelGradeId == None or self.levelGradeId == 0) and (self.grade != None):
      self.levelGradeId = self.grade.getGradeId()
      
    msid = self.subject.metaSubject.msubjId
    type = self.params.getStringParam("type")
    if type == None or type == "": type = "new"
    cache_key1 = type+"_outHtmlSubject" + str(msid) + str(self.levelGradeId)
    outHtml = cache.get(cache_key1)
    if outHtml== None or outHtml == "": 
      outHtml = ""
      outHtml = outHtml + "d.add(" + str(msid) + ",0,'" + self.grade.gradeName + self.subject.metaSubject.msubjName + "','subjectResources.py?type="+type+"&subjectId=" + str(msid) + "&gradeId=" + str(self.grade.gradeId) + "&target=child');"
      cache_key = type+"_gradeLevelList" + str(self.grade.getGradeId())
      gradeLevelList = cache.get(cache_key)
      if gradeLevelList == None:
        gradeLevelList =  self.subj_svc.getGradeLevelListByGradeId(self.grade.getGradeId())
        cache.put(cache_key, gradeLevelList)                        
      for glevel in gradeLevelList:
        outHtml = outHtml + "d.add(" + str(msid) + str(glevel.getGradeId()) + "," + str(msid) + ",'" + glevel.getGradeName() + "','subjectResources.py?type="+type+"&subjectId=" + str(msid) + "&gradeId=" + str(self.grade.gradeId) + "&level=1&levelGradeId=" + str(glevel.getGradeId()) + "');"
        cache.put(cache_key1, outHtml)
      
    request.setAttribute("outHtml",outHtml)  
        
    
    # 资源分类
    self.get_res_cates()
 
    # 资源上载排行
    self.get_upload_sorter()
    
    # 资源下载排行
    self.get_download_resource_list()
       
    # 高亮显示项目.
    request.setAttribute("head_nav", "resources")
    
    return "/WEB-INF/ftl/site_subject_resources.ftl"
    
  def get_current_subjectId(self):
    subjectId = self.params.getIntParam("subjectId")
    request.setAttribute("subjectId" ,subjectId)
    return subjectId
  def get_current_gradeId(self):
    gradeId = self.params.getIntParam("gradeId") 
    request.setAttribute("gradeId",self.gradeId)
    return gradeId


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
    qry.userStatus = 0
    upload_sorter = qry.query_map(20) 
    request.setAttribute("upload_sorter", upload_sorter)
    
  # 资源下载排行.
  def get_download_resource_list(self):
    qry = ResourceQuery(""" r.resourceId, r.href, r.title, r.downloadCount """)
    #qry.subjectId = self.params.getIntParamZeroAsNull("subjectId")
    #qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
    #qry.k = self.params.getStringParam("k")
    
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
    
    qry.subjectId = self.get_current_subjectId()
    
    
    #qry.gradeId = self.get_current_gradeId()
    qry.gradeId=self.levelGradeId
    qry.gradelevel = self.params.getIntParamZeroAsNull("level")
   
    
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