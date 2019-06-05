from cn.edustar.jitar.pojos import Placard
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command

class showPlacard:
    def execute(self):
        param = ParamUtil(request)
        placardId = param.getIntParam("placardId")
        if placardId == None or placardId == "":
            return
        self.pla_svc = __jitar__.getPlacardService()
        placard = self.pla_svc.getPlacard(placardId);
        
        if placard == None:
            return
        request.setAttribute("placard", placard)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/show_placard.ftl"