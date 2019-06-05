from cn.edustar.jitar.util import ParamUtil

class colorpicker:    
    def __init__(self):        
        self.params = ParamUtil(request)        
        
    def execute(self): 
        color = self.params.getStringParam("color")
        request.setAttribute("color", color)       
        return "/WEB-INF/ftl/user/colorpicker.ftl"