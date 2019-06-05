from cn.edustar.jitar.data import Command
from base_preparecourse_page import *
from base_action import BaseAction

class SelectMyPrecareCourse(BaseAction):
  cfg_svc = __jitar__.configService
  
  def __init__(self):
    self.params = ParamUtil(request)
    self.pc_svc = __jitar__.getPrepareCourseService()
    return
 
  def execute(self):
    if self.loginUser == None:
        response.getWriter().println(u"请先登录。")
        return
    videoId = self.params.safeGetIntParam("id")
    videoService = __spring__.getBean("videoService")
    video = videoService.findById(videoId)
    if video == None or video.userId == None:
        response.getWriter().println(u"不能加载视频")
        return
    
    # 先求出该用户参与的所有通过成员审核的集备id
    qry = PrepareCourseMemberQuery(""" pc.prepareCourseId """)
    qry.status = 0
    qry.userId = video.userId
    preparecourseId_list = qry.query_map(qry.count())
    arr = ""
    for id in preparecourseId_list:
        arr = arr + " pc.prepareCourseId=" + str(id["prepareCourseId"]) + " or"
    
    qry = PrepareCourseQuery(""" pc.title, pc.prepareCourseId, pc.createDate, pc.gradeId, pc.metaSubjectId, u.loginName,u.trueName """)
    qry.status = 0
    qry.stage = "running"
    if arr != "":
        arr = arr[0:len(arr) - 2]
        qry.custormAndWhere = " (" + arr + ") "
    pager = self.params.createPager()
    pager.itemName = u"集备"
    pager.itemUnit = u"个"
    pager.pageSize = 20
    pager.totalRows = qry.count()
    preparecourse_list = qry.query_map(pager)
    request.setAttribute("loginUser", self.loginUser)
    request.setAttribute("videoId", videoId)    
    request.setAttribute("pager", pager)
    request.setAttribute("preparecourse_list", preparecourse_list)    
    response.contentType = "text/html; charset=UTF-8"
    return "/WEB-INF/ftl/course/SelectMyPrecareCourse.ftl"