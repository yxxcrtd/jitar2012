from cn.edustar.jitar.util import ParamUtil
from video_query import VideoQuery
from site_config import SiteConfig

class showCommentSummary:        
    def __init__(self):                
        self.params = ParamUtil(request)
        self.video_svc =__spring__.getBean("videoService")
        
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()        
        summary =""
        vid= self.params.getIntParam("id")
        if (vid!=None) and (vid!=0):
            video=self.video_svc.findById(vid)
            summary=video.getSummary()
        else:    
            return "";
        
        request.setAttribute("videoId",vid)
        request.setAttribute("summary",summary)
        return "/WEB-INF/ftl/video/show_video_summary.ftl"
   