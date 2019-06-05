from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from user_query import UserQuery
from resource_query import ResourceQuery
from base_action import SubjectMixiner

# 数据获取执行脚本.
class resources(SubjectMixiner):
  def __init__(self):
    self.params = ParamUtil(request)
    self.sbj_svc = __jitar__.subjectService
    self.unitService = __spring__.getBean("unitService")
    return
  
  
  def execute(self):    
    k = self.params.getStringParam("k")
    if k != "" and k != None:
      k = k.lower()
      if len(k) > 25 or k.find("'") > -1 or k.find("script")>-1 or k.find(">")>-1 or k.find("<")>-1  or k.find("\"")>-1  or k.find("&gt;")>-1:
        k = ""
        response.getWriter().write(u"请输入合法的文字，并且长度不大于25.")
        return
    #单独加的
    cmd= self.params.getStringParam("cmd")
    if cmd=="query":
      self.get_resource_querylist()
      return "/WEB-INF/ftl/queryresource.ftl"
      
    rootUnit = self.unitService.getRootUnit()
    if rootUnit == None:
      request.setAttribute("errMessage", u"没有根机构信息，请超级管理员登录到后台管理在“其它”-“组织机构管理”创建一个根机构信息。<a href='manage/admin.py'>进后台管理</a>")
      return "/WEB-INF/ftl/site_err.ftl"
    self.unit = rootUnit
    cache = __jitar__.cacheProvider.getCache('main')
    type = self.params.getStringParam("type")
    if type == None or type == "": type = "new"
    outHtml = cache.get(type + "_outHtml_resource")    
    if outHtml == None or outHtml == "": 
      cache_key = "_subject_list_resource"
      subject_list = cache.get(cache_key)
      if subject_list == None:
        subject_list = self.sbj_svc.getMetaSubjectList()
        cache.put(cache_key, subject_list)
      outHtml = ""
      for s in subject_list:
        msid = s.getMsubjId()
        outHtml = outHtml + "d.add(" + str(msid) + ",0,'" + s.getMsubjName() + "','resources.py?type=" + type + "&subjectId=" + str(msid) + "');"
        cache_key = "_gradeIdList_resource" + str(msid)
        gradeIdList = cache.get(cache_key)
        if gradeIdList == None:
          gradeIdList = self.sbj_svc.getMetaGradeListByMetaSubjectId(msid)
          cache.put(cache_key, gradeIdList) 
                         
        if gradeIdList != None:
           for gid in gradeIdList:     
             outHtml = outHtml + "d.add(" + str(msid) + str(gid.getGradeId()) + "," + str(msid) + ",'" + gid.getGradeName() + "','resources.py?type=" + type + "&subjectId=" + str(msid) + "&gradeId=" + str(gid.getGradeId()) + "&target=child');"
             cache_key = "_gradeLevelList_resource" + str(gid.getGradeId())
             gradeLevelList = cache.get(cache_key)
             if gradeLevelList == None:
               gradeLevelList = self.sbj_svc.getGradeLevelListByGradeId(gid.getGradeId())
               cache.put(cache_key, gradeLevelList)                        
             for glevel in gradeLevelList:
               outHtml = outHtml + "d.add(" + str(msid) + str(gid.getGradeId()) + str(glevel.getGradeId()) + "," + str(msid) + str(gid.getGradeId()) + ",'" + glevel.getGradeName() + "','resources.py?type=" + type + "&subjectId=" + str(msid) + "&gradeId=" + str(glevel.getGradeId()) + "&level=1');"
               
      cache.put(type + "_outHtml_resource", outHtml)        
    request.setAttribute("outHtml", outHtml)  
            
    # 学科, 区县, 资源类型.
    self.get_subject_list()
    self.get_res_cate()
    
    # 学段分类.
    self.get_grade_list()
    
    # 用户上载排行.
    self.get_upload_user_list()
    self.get_resource_list()

    self.get_download_resource_list()
    
    #self.get_dist_list()
    
    # 页面导航高亮为 'resources''
    request.setAttribute("head_nav", "resources")
    
    return "/WEB-INF/ftl/site_resources.ftl"
    

  def _getCateSvc(self):
    return __jitar__.categoryService
  
  # 资源分类.
  def get_res_cate(self):
    res_cate = self._getCateSvc().getCategoryTree("resource")
    request.setAttribute("res_cate", res_cate)
  
  # 学科列表.
  def get_subject_list(self):
   self.putSubjectList()
   
  #学段
  def get_grade_list(self):
    request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
    self.putGradeList()
    
  # 资源查询.
  def get_resource_querylist(self):
    qry = ResourceQuery(" r.resourceId, r.href, r.title, r.fsize, r.createDate,r.auditState ")
    resId=self.params.getStringParam("id")
    qry.resourceIds=resId
    qry.auditState=None
    resource_list = qry.query_map()
    request.setAttribute("resource_list", resource_list)
    
  # 资源查询.
  def get_resource_list(self):
    qry = ResourceQuery(" r.resourceId, r.href, r.title, r.fsize, r.createDate, u.loginName, u.nickName, r.subjectId as subjectId, grad.gradeName, sc.name as scName ")
    type = self.params.getStringParam("type")
    
    if type == None or type == "": type = "new"
    list_type = ""
    if type == "hot":
      qry.orderType = ResourceQuery.ORDER_TYPE_VIEWCOUNT_DESC
      qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
      list_type = u"最高人气"
    elif type == "rcmd":
      #qry.rcmdState = True
      qry.custormAndWhereClause = " r.rcmdPathInfo Like '%/" + str(self.unit.unitId) + "/%' And r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
      list_type = u"编辑推荐"
    elif type == "cmt":
      qry.orderType = ResourceQuery.ORDER_TYPE_COMMENTCOUNT_DESC
      qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
      list_type = u"评论最多"
    else:
      type = "new"
      qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
      list_type = u"最新资源"   
    request.setAttribute("type", type)
    request.setAttribute("list_type", list_type)
    
    qry.gradelevel = self.params.getIntParamZeroAsNull("level")
    qry.subjectId = self.params.getIntParamZeroAsNull("subjectId")
    qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
    qry.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    qry.k = self.params.getStringParam("k")
    
    pager = self.createPager()
    
    pager.totalRows = qry.count()
    resource_list = qry.query_map(pager)
    
    request.setAttribute("resource_list", resource_list)
    request.setAttribute("pager", pager)
    request.setAttribute("subjectId", qry.subjectId)
    request.setAttribute("categoryId", qry.sysCateId)   
    
  # 资源上载排行.
  def get_upload_user_list(self):
    qry = UserQuery(""" u.resourceCount, u.loginName,  u.nickName """)
    qry.orderType = 4 #UserQuery.ORDER_TYPE_RESOURCE_COUNT_DESC
    qry.userStatus = 0
    upload_user_list = qry.query_map(20) 
    request.setAttribute("upload_user_list", upload_user_list)
    
  # 资源下载排行.
  def get_download_resource_list(self):
    qry = ResourceQuery(""" r.resourceId, r.href, r.title, r.downloadCount """)
    qry.subjectId = self.params.getIntParamZeroAsNull("subjectId")
    qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
    qry.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    qry.k = self.params.getStringParam("k")
    
    qry.orderType = 4
    download_resource_list = qry.query_map(20) 
    request.setAttribute("download_resource_list", download_resource_list)
    request.setAttribute("subjectId", qry.subjectId)
    request.setAttribute("categoryId", qry.sysCateId)
    request.setAttribute("gradeId", qry.gradeId)
    request.setAttribute("k", qry.k)
    
  def createPager(self):
    pager = self.params.createPager()
    pager.itemName = u"资源"
    pager.itemUnit = u"个"
    pager.pageSize = 20
    return pager
