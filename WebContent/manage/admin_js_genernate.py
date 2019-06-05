from cn.edustar.jitar.jython import BaseAdminAction
from base_action import ActionResult
from base_action import SubjectMixiner

class admin_js_genernate(BaseAdminAction, ActionResult, SubjectMixiner):
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        if self.canAdmin() == False:
            self.addActionError(u"没有管理站点配置的权限.")
            return ActionResult.ERROR
        
        self.cate_svc = __jitar__.categoryService
        article_cates = self.cate_svc.getCategoryTree("default")
        request.setAttribute("article_cates", article_cates)        
        
        res_cate = self.cate_svc.getCategoryTree("resource")
        request.setAttribute("res_cate", res_cate)
        
        group_cate = self.cate_svc.getCategoryTree("group")
        request.setAttribute("group_cate", group_cate)
        
        video_cate = self.cate_svc.getCategoryTree("video")
        request.setAttribute("video_cate", video_cate)
        
        user_cate = self.cate_svc.getCategoryTree("blog")
        request.setAttribute("user_cate", user_cate)
        
        user_type_list = __jitar__.userService.getAllUserType()
        if user_type_list != None and len(user_type_list) > 0:
            request.setAttribute("user_type_list",user_type_list)
        return "/WEB-INF/ftl/admin/admin_js_genernate.ftl"    
