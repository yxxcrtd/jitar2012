from cn.edustar.jitar.util import ParamUtil
from base_action import *

class articleGroupListFrame(ActionExecutor):
    def __init__(self):
        ActionExecutor.__init__(self)
        self.params = ParamUtil(request)
    def execute(self):
        if self.loginUser == None: return ActionResult.LOGIN
        articleId = self.params.safeGetIntParam("articleId")
        request.setAttribute("articleId",articleId)
        return "/WEB-INF/ftl/article/articleGroupListFrame.ftl"
        
    