from cn.edustar.jitar.util import ParamUtil
from base_action import *

class resourceGroupListFrame(ActionExecutor):
    def __init__(self):
        ActionExecutor.__init__(self)
        self.params = ParamUtil(request)
    def execute(self):
        if self.loginUser == None: return ActionResult.LOGIN
        resourceId = self.params.safeGetIntParam("resourceId")
        request.setAttribute("resourceId",resourceId)
        return "/WEB-INF/ftl/resource/resourceGroupListFrame.ftl"
        
    