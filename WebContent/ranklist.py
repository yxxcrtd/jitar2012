from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.util import ConfigurationFileIni
from cn.edustar.jitar.query import UserStatQueryParam
from cn.edustar.jitar.service import GroupQueryParam
from cn.edustar.jitar.service import UnitQueryParam
from base_action import SubjectMixiner
from article_query import ArticleQuery
from java.util import ArrayList

# 显示文章
class ranklist (SubjectMixiner):
  def __init__(self):
    self.params = ParamUtil(request)
    self.cate_svc = __jitar__.categoryService
    self.userStatManage = __spring__.getBean("userStatManage")
    self.groupService = __spring__.getBean("groupService")
    self.unitService = __spring__.getBean("unitService")
    return
  
  def execute(self):
    
    self.iniFilePath=request.getRealPath("/ranklist.ini")
    userOrder4=ConfigurationFileIni.getValue(self.iniFilePath,u"学校排行","userOrder","")
    
    list_type = "个人排行"
    type = self.params.getStringParam("type")
    if type == "" or type == None:
      type = "user"
    if type == "user":
      list_type = u"个人排行"
      self.get_user_list()
    elif type == "ts":
      list_type = u"教研员排行"
      self.get_ts_list()
    elif type == "group":
      list_type = u"协作组排行"
      self.get_group_list()
    elif type == "school":
      list_type = u"学校排行"
      self.get_school_list()
    else:
      type = 'user'
      self.get_user_list()
      
    request.setAttribute("type", type)
    request.setAttribute("list_type", list_type)
    
    # 页面导航高亮为 'rank'
    
    request.setAttribute("iniFilePath", self.iniFilePath)
    request.setAttribute("head_nav", "rank")
    response.contentType = "text/html; charset=UTF-8"
    return "/WEB-INF/ftl/site_ranklist.ftl"
  
 
  # 查询个人排行列表
  def get_user_list(self):
    userOrder1=ConfigurationFileIni.getValue(self.iniFilePath,u"个人排行","userOrder","")
    param = UserStatQueryParam("User");
    if(userOrder1==None or userOrder1==""):
      param.orderType=3
    elif userOrder1=="trueName":
      param.orderType= 31
    elif userOrder1=="userScore":
      param.orderType= 32
    elif userOrder1=="visitCount":
      param.orderType= 33
    elif userOrder1=="visitArticleCount":
      param.orderType= 34
    elif userOrder1=="visitResourceCount":
      param.orderType= 35
    elif userOrder1=="myArticleCount":
      param.orderType= 36
    elif userOrder1=="otherArticleCount":
      param.orderType= 37
    elif userOrder1=="recommendArticleCount":
      param.orderType= 38
    elif userOrder1=="articleCommentCount":
      param.orderType= 39
    elif userOrder1=="articleICommentCount":
      param.orderType= 40
    elif userOrder1=="resourceCount":
      param.orderType= 41
    elif userOrder1=="recommendResourceCount":
      param.orderType= 42
    elif userOrder1=="resourceCommentCount":
      param.orderType= 43
    elif userOrder1=="resourceICommentCount":
      param.orderType= 44
    elif userOrder1=="resourceDownloadCount":
      param.orderType= 45
    elif userOrder1=="prepareCourseCount":
      param.orderType= 46
    elif userOrder1=="createGroupCount":
      param.orderType= 47
    elif userOrder1=="jionGroupCount":
      param.orderType= 48
    elif userOrder1=="photoCount":
      param.orderType= 49
    elif userOrder1=="videoCount":
      param.orderType= 50
    elif userOrder1=="articlePunishScore":
      param.orderType= 51
    elif userOrder1=="resourcePunishScore":
      param.orderType= 52
    elif userOrder1=="commentPunishScore":
      param.orderType= 53
    elif userOrder1=="photoPunishScore":
      param.orderType= 54
    elif userOrder1=="videoPunishScore":
      param.orderType= 55        
    else:
      param.orderType=3
    pager = self.createUserPager()
    userList = self.userStatManage.getUserStatList(param, pager)
    request.setAttribute("userList", userList)
    request.setAttribute("pager", pager)
    
  # 查询教研员排行列表
  def get_ts_list(self):
    param = UserStatQueryParam("User");
    userOrder1=ConfigurationFileIni.getValue(self.iniFilePath,u"教研员排行","userOrder","")
    if(userOrder1==None or userOrder1==""):
      param.orderType=3
    elif userOrder1=="trueName":
      param.orderType= 31
    elif userOrder1=="userScore":
      param.orderType= 32
    elif userOrder1=="visitCount":
      param.orderType= 33
    elif userOrder1=="visitArticleCount":
      param.orderType= 34
    elif userOrder1=="visitResourceCount":
      param.orderType= 35
    elif userOrder1=="myArticleCount":
      param.orderType= 36
    elif userOrder1=="otherArticleCount":
      param.orderType= 37
    elif userOrder1=="recommendArticleCount":
      param.orderType= 38
    elif userOrder1=="articleCommentCount":
      param.orderType= 39
    elif userOrder1=="articleICommentCount":
      param.orderType= 40
    elif userOrder1=="resourceCount":
      param.orderType= 41
    elif userOrder1=="recommendResourceCount":
      param.orderType= 42
    elif userOrder1=="resourceCommentCount":
      param.orderType= 43
    elif userOrder1=="resourceICommentCount":
      param.orderType= 44
    elif userOrder1=="resourceDownloadCount":
      param.orderType= 45
    elif userOrder1=="prepareCourseCount":
      param.orderType= 46
    elif userOrder1=="createGroupCount":
      param.orderType= 47
    elif userOrder1=="jionGroupCount":
      param.orderType= 48
    elif userOrder1=="photoCount":
      param.orderType= 49
    elif userOrder1=="videoCount":
      param.orderType= 50
    elif userOrder1=="articlePunishScore":
      param.orderType= 51
    elif userOrder1=="resourcePunishScore":
      param.orderType= 52
    elif userOrder1=="commentPunishScore":
      param.orderType= 53
    elif userOrder1=="photoPunishScore":
      param.orderType= 54
    elif userOrder1=="videoPunishScore":
      param.orderType= 55        
    else:
      param.orderType=3
    param.teacherType=4
    pager = self.createUserPager()
    userList = self.userStatManage.getUserStatList(param, pager)
    request.setAttribute("userList", userList)
    request.setAttribute("pager", pager)
        
  # 查询群组排行列表
  def get_group_list(self):
    #统计精华
    self.groupService.UpdateBestGroupArticleAndResource()
    
    param = GroupQueryParam()

    userOrder=ConfigurationFileIni.getValue(self.iniFilePath,u"协作组排行","userOrder","")
    if userOrder=="groupTitle":
      param.orderType=7
    elif userOrder=="visitCount":
      param.orderType=8
    elif userOrder=="userCount":
      param.orderType=11
    elif userOrder=="articleCount":
      param.orderType=12
    elif userOrder=="bestArticleCount":
      param.orderType=13
    elif userOrder=="resourceCount":
      param.orderType=14
    elif userOrder=="bestResourceCount":
      param.orderType=15
    elif userOrder=="topicCount":
      param.orderType=9
    elif userOrder=="discussCount":
      param.orderType=16
    elif userOrder=="actionCount":
      param.orderType=17
    elif userOrder=="ALLCCOUNT":    
      param.orderType=10
    else:
      param.orderType=10
        
    pager = self.createGroupPager()
    groupList = self.groupService.getGroupList(param, pager) 
    request.setAttribute("groupList", groupList)
    request.setAttribute("pager", pager)

  # 查询学校排行列表
  def get_school_list(self):
    #计算学校的平均分
    hql = """UPDATE Unit SET aveScore = totalScore/userCount Where userCount>0 """ 
    Command(hql).update()
    hql="""UPDATE Unit SET aveScore = 0  Where userCount<=0"""
    Command(hql).update()
    #查询学校排行列表
    param = UnitQueryParam()
    userOrder=ConfigurationFileIni.getValue(self.iniFilePath,u"学校排行","userOrder","")
    if userOrder=="unitName":
      param.orderType=0
    elif userOrder=="studioCount":
      param.orderType=11
    elif userOrder=="articleCount":
      param.orderType=12
    elif userOrder=="recommendArticleCount":
      param.orderType=13
    elif userOrder=="resourceCount":
      param.orderType=14
    elif userOrder=="recommendResourceCount":
      param.orderType=15
    elif userOrder=="photoCount":
      param.orderType=16
    elif userOrder=="videoCount":
      param.orderType=17
    elif userOrder=="totalScore":
      param.orderType=18
    elif userOrder=="aveScore":
      param.orderType=99
    else:
      param.orderType=99  #按照平均分排序
      
    pager = self.createUnitPager()
    unitList = self.unitService.getUnitList(param, pager) 
    request.setAttribute("unitList", unitList)
    request.setAttribute("pager", pager)

  # 查询区县排行列表
  #def get_district_list(self):
  #  userOrder=ConfigurationFileIni.getValue(self.iniFilePath,"区县排行","userOrder","")
  #  #districtName,unitCount,studioCount,articleCount,recommendArticleCount,resourceCount,recommendResourceCount,photoCount,videoCount,totalScore,aveScore
  #  hql = """SELECT new Map(dt.districtId as districtId, dt.districtName as districtName,
  #        dt.unitCount as unitCount,dt.studioCount as studioCount,dt.articleCount as articleCount,dt.recommendArticleCount as recommendArticleCount,dt.resourceCount as resourceCount,dt.recommendResourceCount as recommendResourceCount,
  #        dt.totalScore as totalScore,dt.photoCount as photoCount,dt.videoCount as videoCount,dt.aveScore as aveScore)
  #       FROM District dt """
  #  
  #  if userOrder=="districtName":      
  #    hql=hql+" ORDER BY dt.districtName ASC """
  #  elif userOrder=="unitCount":
  #    hql=hql+" ORDER BY dt.unitCount DESC """
  #  elif userOrder=="studioCount":
  #    hql=hql+" ORDER BY dt.studioCount DESC """
  #  elif userOrder=="articleCount":
  #    hql=hql+" ORDER BY dt.articleCount DESC """
  #  elif userOrder=="recommendArticleCount":
  #    hql=hql+" ORDER BY dt.recommendArticleCount DESC """
  #  elif userOrder=="resourceCount":
  #    hql=hql+" ORDER BY dt.resourceCount DESC """
  #  elif userOrder=="recommendResourceCount":
  #    hql=hql+" ORDER BY dt.recommendResourceCount DESC """
  #  elif userOrder=="totalScore":
  #    hql=hql+" ORDER BY dt.totalScore DESC """
  #  elif userOrder=="photoCount":
  #    hql=hql+" ORDER BY dt.photoCount DESC """
  #  elif userOrder=="videoCount":
  #    hql=hql+" ORDER BY dt.videoCount DESC """
  #  elif userOrder=="aveScore":
  #    hql=hql+" ORDER BY dt.aveScore DESC """
  #  else:
  #    hql=hql+" ORDER BY dt.aveScore DESC """
  #  dist_list = Command(hql).open()
    
    #循环每个区县
    #distexlist=ArrayList()
    #for dist in dist_list:
    #  distId=dist["districtId"]
    #  distEx=DistrictEx()
    #  distEx.districtId=dist["districtId"]
    #  distEx.districtName=dist["districtName"]
    #  unitList = self.unitService.getUnitListByDistrictId(distId)
    #  for unit in unitList:
    #    distEx.unitCount=distEx.unitCount+1
    #    distEx.studioCount=distEx.studioCount+unit.studioCount
    #    distEx.articleCount=distEx.articleCount+unit.articleCount
    #    distEx.recommendArticleCount=distEx.recommendArticleCount+unit.recommendArticleCount
    #    distEx.resourceCount=distEx.resourceCount+unit.resourceCount
    #    distEx.recommendResourceCount=distEx.recommendResourceCount+unit.recommendResourceCount
    #    distEx.totalScore=distEx.totalScore+unit.totalScore
    #    distEx.photoCount=distEx.photoCount+unit.photoCount
    #    distEx.videoCount=distEx.videoCount+unit.videoCount
    #  if distEx.studioCount>0:  
    #    distEx.aveScore=round(float(distEx.totalScore)/float(distEx.studioCount),2)
    #  
    #  distexlist.add(distEx)
    ##排序
    #distexlist2=ArrayList()
    #while distexlist.size()>0:
    #  score=-1000000
    #  i=0
    #  for dist in distexlist:
    #    i=i+1
    #    if i==1:
    #      score=dist.aveScore
    #    if dist.aveScore>=score:
    #        score=dist.aveScore
    #        distx=dist
    #  distexlist2.add(distx)
    #  distexlist.remove(distx)
    #request.setAttribute("distexlist", distexlist2)
    
    #request.setAttribute("distexlist", dist_list)

  def createUserPager(self):
    # private 构造个人排行的缺省 pager.
    pager = self.params.createPager()
    pager.itemName = u"用户"
    pager.itemUnit = u"个"
    pager.pageSize = 30
    return pager

  def createGroupPager(self):
    # private 构造个人排行的缺省 pager.
    pager = self.params.createPager()
    pager.itemName = u"组"
    pager.itemUnit = u"个"
    pager.pageSize = 30
    return pager

  def createUnitPager(self):
    # private 构造个人排行的缺省 pager.
    pager = self.params.createPager()
    pager.itemName = u"机构"
    pager.itemUnit = u"个"
    pager.pageSize = 30
    return pager