from cn.edustar.jitar.pojos import Page
from cn.edustar.jitar.util import ParamUtil
from com.alibaba.fastjson import JSONObject
from base_preparecourse_page import *

class manage_customskin(PrepareCoursePageService):
    def __init__(self):        
        self.params = ParamUtil(request)
        self.printer = response.getWriter()
        self.page_svc = __spring__.getBean("pageService")

    def execute(self):
        if self.loginUser == None:
            response.sendRedirect("../../login.jsp")
            return None
        self.getBaseData()
        self.getBasePrepareCourse()
       
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return
                
        if self.canManage(self.prepareCourse) == False:
            self.printer.write(u"你无权进行管理。")
            return
        
        self.prepareCourse = self.getBasePrepareCourse()
        page = self.getPrepareCoursePageWithCustomSkin(self.prepareCourse)

        if page == None:
            self.printer.write(u"没有找到备课首页。")         
            return
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "1":                
                bgcolor = self.params.getStringParam("bgcolor")
                logo = self.params.getStringParam("logo")
                logoheight = self.params.getStringParam("logoheight")
                titletop = self.params.getStringParam("titletop")
                titleleft = self.params.getStringParam("titleleft")
                titledisplay = self.params.getStringParam("titledisplay")                
                jstring = '{"bgcolor":"' +  bgcolor + '","logo":"' + logo + '","logoheight":"' +  logoheight + '","titleleft":"' + titleleft + '","titletop":"' + titletop + '","titledisplay":"' + titledisplay + '"}'
                jsObj = JSONObject.parse(jstring)        
                self.page_svc.setPageCustomSkin(page, jsObj.toString())
            if cmd == "2":
                bgcolor = ""
                logo = ""
                logoheight = ""
                titletop = ""
                titleleft = ""
                titledisplay = ""                
                jstring = '{"bgcolor":"' +  bgcolor + '","logo":"' + logo + '","logoheight":"' +  logoheight + '","titleleft":"' + titleleft + '","titletop":"' + titletop + '","titledisplay":"' + titledisplay + '"}'
                jsObj = JSONObject.parse(jstring)        
                self.page_svc.setPageCustomSkin(page, jsObj.toString())
            
            cache = __jitar__.cacheProvider.getCache('prepareCourse')
            cache_key = "prepareCourse_page_" + str(self.prepareCourseId )
            cache.remove(cache_key)
            self.printer.write(u"自定义设置已保存。")
            return    
        else:            
            request.setAttribute("prepareCourse", self.prepareCourse)
            if page.customSkin != None:
                customSkin = JSONObject.parse(page.customSkin)
                color = customSkin["bgcolor"]
                if len(color) > 0 and color[0:1] == "#": color = color[1:6]
                request.setAttribute("color", color)
                request.setAttribute("customSkin", customSkin)
            else:
                 customSkin = {"bgcolor":"", "logo":"", "logoheight":"", "titledisplay":"", "titleleft":"","titletop":""}               
                 request.setAttribute("customSkin", customSkin)
            return "/WEB-INF/ftl/course/manage_customskin.ftl"