from cn.edustar.jitar.data import Command
from preparecourse_member_query import PrepareCourseMemberQuery
from base_action import BaseAction

class SaveVideoToMyPrecareCourse(BaseAction):
  cfg_svc = __jitar__.configService
  
  def __init__(self):
    self.params = ParamUtil(request)
    self.pc_svc = __jitar__.getPrepareCourseService()
    self.video_svc = __spring__.getBean("videoService")
    return
 
  def execute(self): 
    if self.loginUser == None:
        self.out.write(u"请先登录。")
        request.setAttribute("msg", u"请先登录")
        return "SaveVideoToMyPrecareCourse.ftl"
    videoid = self.params.getIntParam("videoid")
    if videoid == None or videoid == 0:
        self.out.write(u"缺少VideoID参数。")
        request.setAttribute("msg", u"缺少VideoID参数")
        return "SaveVideoToMyPrecareCourse.ftl"
    
    video = self.video_svc.findById(videoid)
    videoTitle = video.title
    pcids = self.params.safeGetIntValues("pcid")
    if pcids == 0 or pcids == None:
        self.out.write(u"缺少pcid参数。")
        request.setAttribute("msg", u"缺少pcid参数")
        return "SaveVideoToMyPrecareCourse.ftl"
    
    userId = self.loginUser.userId
    for pcid in pcids:
      self.pc_svc.insertVideoToPrepareCourse(pcid, videoid, userId, videoTitle);
    
    request.setAttribute("msg", u"操作完成")  
    return "/WEB-INF/common/SaveVideoToMyPrecareCourse.ftl"

