from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Widget
from base_action import ActionExecutor

class group_mutilcates_title(ActionExecutor):
    def __init__(self):                
        self.params = ParamUtil(request)
        self.page_svc = __jitar__.pageService        
        
    def execute(self):
        response.setContentType("text/html; charset=UTF-8")
        widgetId=self.params.getIntParamZeroAsNull("widgetId")
        if widgetId==none:
            out = response.writer
            out.write("未找到参数widgetId")
            return None
        widget=self.page_svc.getWidget(widgetId)    
        if widget==none:
            out = response.writer
            out.write("未找到参数widget")
            return None
        
        request.setAttribute("widget", widget)
        action = self.params.getStringParam("act")
        if action != None or action != "":            
            if request.getMethod() == "POST":
                return self.saveWidget()
        return "/WEB-INF/group/default/group_mutilcates_title.ftl"
    
    def saveWidget(self):
        #放弃了
        return None 