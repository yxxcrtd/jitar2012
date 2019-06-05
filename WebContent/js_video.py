from cn.edustar.jitar.util import ParamUtil
from video_query import VideoQuery

class js_video:
    def execute(self):
        # 完整的调用参数：
        # js_video.py?top=4&count=4&type=0&cateid=13
        
        # 默认支持 subjectId，gradeId，参数带这些值，默认是支持的
        # 更多参数请参见 js_video。
        
        self.params = ParamUtil(request)
        ShowCount = self.params.getIntParam("count")
        ShowTop = self.params.getIntParam("top")        
        unitId = self.params.getIntParamZeroAsNull("unitid")
        cateid = self.params.getIntParamZeroAsNull("cateid")
        if ShowTop == None or ShowTop == 0:
            ShowTop = 10
        if ShowCount == None or ShowCount == 0:
            ShowCount = 10
        qry = VideoQuery(" v.videoId, v.title, v.createDate ")        
        qry.categoryId = cateid        
        video_list = qry.query_map(ShowTop)
        
        request.setAttribute("video_list", video_list) 
        request.setAttribute("ShowCount", ShowCount)
        response.contentType = "text/html; charset=utf-8"
        return "/WEB-INF/ftl/js_video.ftl"