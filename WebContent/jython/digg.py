from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Article

class digg:
    def execute(self):
        param = ParamUtil(request)
        type = param.getRequestParam("cmd")
        articleId = param.getIntParam("id")
        out = response.writer
        articleService = __jitar__.articleService
        if type == "digg":
            articleService.addDigg(articleId)
            out.write(str(articleService.getDigg(articleId)))
            return            
        
        elif type == "trample":
            articleService.addTrample(articleId)
            out.write(str(articleService.getTrample(articleId)))
            return  
        else:
            out.write(0)