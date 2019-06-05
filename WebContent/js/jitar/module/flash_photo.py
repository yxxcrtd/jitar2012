from photo_query import PhotoQuery
from cn.edustar.jitar.util import ParamUtil

user_svc = __jitar__.userService

class flash_photo:
    def __init__(self):
        pass
    
    def execute(self):
        self.params = ParamUtil(request)    
        userId = self.params.safeGetIntParam("userId", 0)
        listCount = self.params.safeGetIntParam("count", 4)
        fwidth = self.params.safeGetIntParam("fwidth", 200)
        fheight = self.params.safeGetIntParam("fheight", 200)
        ftxtheight = self.params.safeGetIntParam("ftxtheight", 36)
        fbgcolor = self.params.safeGetStringParam("fbgcolor")
        if fbgcolor == "":
            fbgcolor = "#E5ECF4"
        if userId == 0: 
            request.setAttribute("ErrText", "没有找到所查询的图片")            
            return "/WEB-INF/ftl/webpart_error.ftl"
        user = user_svc.getUserById(userId)
        if user == None:
            request.setAttribute("ErrText", "没有找到该用户")            
            return "/WEB-INF/ftl/webpart_error.ftl"        

        qry = PhotoQuery(""" p.photoId, p.title, p.href """)
        qry.userId = userId
        qry.orderType = 0
        qry.extName = ".jpg"
        qry.isPrivateShow = None
        result = qry.query_map(listCount)
        request.setAttribute("user", user)
        request.setAttribute("photo_list", result)
        request.setAttribute("fwidth", fwidth)
        request.setAttribute("fheight", fheight)
        request.setAttribute("ftxtheight", ftxtheight)
        request.setAttribute("fbgcolor", fbgcolor)
        response.contentType = "text/html; charset=UTF-8"
        return "/WEB-INF/user/default/flash_photo.ftl"