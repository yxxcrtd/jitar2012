# coding=utf-8
from cn.edustar.jitar.model import ObjectType
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from group_query import GroupQuery
from cn.edustar.jitar.data import Command
from placard_query import PlacardQuery
from cn.edustar.jitar.service import CategoryService;
from group_mutilcates_query import GroupMutilcatesQuery

# 课题组页面.
cate_svc = __jitar__.categoryService
class ktgroups(SubjectMixiner):
  def __init__(self):
    self.params = ParamUtil(request)
    return
  
  def execute(self):
    #得到课题组的分类！
    ktuuid=CategoryService.GROUP_CATEGORY_GUID_KTYJ
    ktcate=cate_svc.getCategory(ktuuid);
    #永远查找的是课题组的分类
    self.categoryId = ktcate.categoryId
     # 页面导航高亮为 'ktgroups'
    request.setAttribute("head_nav", "ktgroups")
    response.contentType = "text/html; charset=UTF-8"
    act = self.params.safeGetStringParam("act", "")
    if act=="all":
      type = self.params.safeGetStringParam("type", "")
      if type=="placard":
        self.get_group_placard_page_list()
        return "/WEB-INF/ftl/site_ktgroups_placard.ftl"
      elif type=="article":
        self.get_group_article_page_list()
        return "/WEB-INF/ftl/site_ktgroups_article.ftl"
      elif type=="resource":
        self.get_group_resource_page_list()
        return "/WEB-INF/ftl/site_ktgroups_resource.ftl"      
      elif type=="photo":
        self.get_group_photo_page_list()
        return "/WEB-INF/ftl/site_ktgroups_photo.ftl"
      elif type=="video":
        self.get_group_video_page_list()
        return "/WEB-INF/ftl/site_ktgroups_video.ftl"
        
        
    # 学科
    self.get_subject_list()
    # 学段分类.
    self.get_grade_list()
    
    #课题公告
    self.get_group_placard_list()
    
    #课题成果 文章 资源 图片 视频
    self.get_group_article_list()
    self.get_group_resource_list()
    self.get_group_photo_list()
    self.get_group_video_list()
    
    # 查询课题组.
    self.query_group_list()
    
    return "/WEB-INF/ftl/site_ktgroups.ftl"
  
  # 学科列表.
  def get_subject_list(self):
   self.putSubjectList()

  #学段
  def get_grade_list(self):
    request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
    self.putGradeList()  

  # 公告.
  def get_group_placard_list(self):
    qry = PlacardQuery("pld.id, pld.title, pld.createDate, pld.userId,g.groupTitle,g.groupName")
    qry.objType = ObjectType.OBJECT_TYPE_GROUP.getTypeId()
    qry.objId = None
    qry.groupCateId=self.categoryId
    placard_list = qry.query_map(5)
    request.setAttribute("placard_list", placard_list)
    
  def get_group_placard_page_list(self):
    qry = PlacardQuery("pld.id, pld.title, pld.createDate, pld.userId,g.groupTitle,g.groupName")
    qry.objType = ObjectType.OBJECT_TYPE_GROUP.getTypeId()
    qry.objId = None
    qry.groupCateId=self.categoryId
    pager = self.params.createPager()
    pager.itemName = u"公告"
    pager.itemUnit = u"个"
    pager.pageSize = 20
    pager.totalRows = qry.count()
    placard_list = qry.query_map(pager)
    request.setAttribute("pager", pager)
    request.setAttribute("placard_list", placard_list)
    
  # 成果文章.
  def get_group_article_list(self):
    qry = GroupMutilcatesQuery(""" a.articleId, a.title,a.userId,a.typeState,a.createDate,g.groupTitle,g.groupName """)
    qry.articleCateName=u"成果"
    qry.groupCateId=self.categoryId
    article_list = qry.query_map(5)
    request.setAttribute("article_list", article_list)

  # 成果文章.
  def get_group_article_page_list(self):
    qry = GroupMutilcatesQuery(""" a.articleId, a.title,a.userId,a.typeState,a.createDate,g.groupTitle,g.groupName """)
    qry.articleCateName=u"成果"
    qry.groupCateId=self.categoryId
    pager = self.params.createPager()
    pager.itemName = u"文章"
    pager.itemUnit = u"篇"
    pager.pageSize = 20
    pager.totalRows = qry.count()
    article_list = qry.query_map(pager)
    request.setAttribute("pager", pager)
    request.setAttribute("article_list", article_list)
    
  # 成果资源 .
  def get_group_resource_list(self):
    qry = GroupMutilcatesQuery(""" r.resourceId, r.title,r.userId,r.downloadCount,r.createDate,r.href,g.groupTitle,g.groupName """)
    qry.resourceCateName=u"成果"
    qry.groupCateId=self.categoryId
    resource_list = qry.query_map(5)
    request.setAttribute("resource_list", resource_list)

  # 成果资源 .
  def get_group_resource_page_list(self):
    qry = GroupMutilcatesQuery(""" r.resourceId, r.title,r.userId,r.downloadCount,r.createDate,r.href,g.groupTitle,g.groupName """)
    qry.resourceCateName=u"成果"
    qry.groupCateId=self.categoryId
    pager = self.params.createPager()
    pager.itemName = u"文章"
    pager.itemUnit = u"篇"
    pager.pageSize = 20
    pager.totalRows = qry.count()
    resource_list = qry.query_map(pager)
    request.setAttribute("pager", pager)
    request.setAttribute("resource_list", resource_list)

    
  # 成果照片 .
  def get_group_photo_list(self):
    qry = GroupMutilcatesQuery(""" p.photoId, p.title, p.tags, p.userId, p.createDate,p.href,g.groupTitle,g.groupName """)
    qry.photoCateName=u"成果"
    qry.groupCateId=self.categoryId
    photo_list = qry.query_map(4)
    request.setAttribute("photo_list", photo_list)
     
  # 成果照片 .
  def get_group_photo_page_list(self):
    qry = GroupMutilcatesQuery(""" p.photoId, p.title, p.tags, p.userId, p.createDate,p.href,g.groupTitle,g.groupName """)
    qry.photoCateName=u"成果"
    qry.groupCateId=self.categoryId
    pager = self.params.createPager()
    pager.itemName = u"照片"
    pager.itemUnit = u"张"
    pager.pageSize = 20
    pager.totalRows = qry.count()
    photo_list = qry.query_map(pager)
    request.setAttribute("pager", pager)
    request.setAttribute("photo_list", photo_list)
       
  # 成果视频.
  def get_group_video_list(self):
    qry = GroupMutilcatesQuery(""" v.videoId, v.title, v.tags, v.userId,v.href,v.flvHref,v.flvThumbNailHref,v.createDate,g.groupTitle,g.groupName """)
    qry.videoCateName=u"成果"
    qry.groupCateId=self.categoryId
    video_list = qry.query_map(4)
    request.setAttribute("video_list", video_list)

  # 成果视频.
  def get_group_video_page_list(self):
    qry = GroupMutilcatesQuery(""" v.videoId, v.title, v.tags, v.userId,v.href,v.flvHref,v.flvThumbNailHref,v.createDate,g.groupTitle,g.groupName """)
    qry.videoCateName=u"成果"
    qry.groupCateId=self.categoryId
    pager = self.params.createPager()
    pager.itemName = u"视频"
    pager.itemUnit = u"个"
    pager.pageSize = 20
    pager.totalRows = qry.count()
    video_list = qry.query_map(pager)
    request.setAttribute("pager", pager)
    request.setAttribute("video_list", video_list)
            
  # 查询课题组.
  def query_group_list(self):
    qry = GroupQuery(""" g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.userCount,
              g.visitCount, g.articleCount, g.topicCount, g.resourceCount, g.groupIntroduce,
              g.groupTags, subj.subjectName, grad.gradeName, sc.name as scName """)  
    qry.subjectId = self.params.getIntParamZeroAsNull('subjectId')
    qry.categoryId = self.categoryId
    qry.gradeId = self.params.getIntParamZeroAsNull('gradeId')
    qry.searchtype=self.params.getStringParam('searchtype')
    qry.kk = self.params.getStringParam("k")
        
    pager = self.createPager()
    
    pager.totalRows = qry.count() 
    group_list = qry.query_map(pager)
    request.setAttribute("group_list", group_list)
    ggg=[]
    for gg in group_list:
      gid=gg["groupId"]
      subqry = GroupQuery(""" g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.userCount,
                  g.visitCount, g.articleCount, g.topicCount, g.resourceCount, g.groupIntroduce,
                  g.groupTags """)
      subqry.parentId=gid
      subgroup_list= subqry.query_map()
      ggg.append(subgroup_list)
    
    request.setAttribute("subgroups", ggg)  
    request.setAttribute("pager", pager)
    request.setAttribute("subjectId", qry.subjectId)
    request.setAttribute("categoryId", qry.categoryId)
    request.setAttribute("gradeId", qry.gradeId)
    request.setAttribute("searchtype", qry.searchtype)
    request.setAttribute("k", qry.kk)
    
       
  # 创建分页对象.
  def createPager(self):
    pager = self.params.createPager()
    pager.itemName = u"课题组"
    pager.itemUnit = u"个"
    pager.pageSize = 10
    return pager

