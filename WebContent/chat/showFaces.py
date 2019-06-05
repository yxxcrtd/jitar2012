from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from base_action import ActionExecutor,ActionResult
from cn.edustar.jitar.service import ChatMessageService
from cn.edustar.jitar.pojos import ChatMessage
from cn.edustar.jitar.service import ChatUserService
from cn.edustar.jitar.pojos import ChatUser
from cn.edustar.jitar.pojos import User

class showFaces(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.userchat_svc = __spring__.getBean("chatUserService")
        
    def execute(self):
        list=self.userchat_svc.getFaceList() 
        
        request.setAttribute("faceList", list) 
        return "/WEB-INF/ftl/chat/showFaces.ftl"