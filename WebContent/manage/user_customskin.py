from base_action import ActionExecutor,ActionResult
from cn.edustar.jitar.pojos import Page
from cn.edustar.jitar.util import ParamUtil
from com.alibaba.fastjson import JSONObject

class user_customskin(ActionExecutor):
    def __init__(self):
        
        self.params = ParamUtil(request)
        self.page_svc = __spring__.getBean("pageService")
        self.login_user = self.getLoginUser()

    def execute(self):
        if self.loginUser == None:
            response.sendRedirect("../login.jsp")
            return None
        page = self.page_svc.getUserIndexPage(self.login_user)
        if page == None:
            self.addActionError(u"没有找到您的首页。")
            return ActionResult.ERROR
        if request.getMethod() == "POST":
            bgcolor = self.params.getStringParam("bgcolor")
            logo = self.params.getStringParam("logo")
            logoheight = self.params.getStringParam("logoheight")
            titletop = self.params.getStringParam("titletop")
            titleleft = self.params.getStringParam("titleleft")
            titledisplay = self.params.getStringParam("titledisplay")                
            jstring = '{"bgcolor":"' +  bgcolor + '","logo":"' + logo + '","logoheight":"' +  logoheight + '","titleleft":"' + titleleft + '","titletop":"' + titletop + '","titledisplay":"' + titledisplay + '"}'
            jsObj = JSONObject.parse(jstring)        
            self.page_svc.setPageCustomSkin(page, jsObj.toString())
            self.addActionMessage(u"自定义设置已保存")
            return ActionResult.SUCCESS            
        else:            
            if page.customSkin != None:
                customSkin = JSONObject.parse(page.customSkin)
                color = customSkin["bgcolor"]
                if len(color) > 0 and color[0:1] == "#": color = color[1:6]
                request.setAttribute("color", color)
                request.setAttribute("customSkin", customSkin)
            else:
                 customSkin = {"bgcolor":"", "logo":"", "logoheight":"", "titledisplay":"", "titleleft":"","titletop":""}              
                 request.setAttribute("customSkin", customSkin)
            return "/WEB-INF/ftl/user/user_custom_info.ftl"