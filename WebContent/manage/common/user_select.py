from cn.edustar.jitar.util import ParamUtil

class user_select:
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        # 选择类型，单选还是多选？type:1单选,0多选
        type = self.params.safeGetStringParam("type")
        idTag = self.params.safeGetStringParam("idTag")
        titleTag = self.params.safeGetStringParam("titleTag")
        backType = self.params.safeGetStringParam("back")
        
        request.setAttribute("type", type)
        request.setAttribute("idTag", idTag)
        request.setAttribute("titleTag", titleTag)
        request.setAttribute("back", backType)
        return "/WEB-INF/common/user_select.ftl"
