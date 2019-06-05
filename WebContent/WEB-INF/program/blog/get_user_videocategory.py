# encoding=utf-8
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.data import Pager
from base_blog_page import *
from base_action import BaseAction
from java.net import URLDecoder
class get_user_videocategory(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def execute(self):
        self.params = ParamUtil(request)   
        wid = self.params.safeGetStringParam("wid")
        oldCateId = self.params.safeGetIntParam("oldCateId")
        count = self.params.safeGetIntParam("count")
        title = self.params.safeGetStringParam("title")
        if count == 0:count = 9
        if wid == None or wid == "":
            wid = ""
        
        loginName = request.getAttribute("loginName")
        if (loginName == None or loginName == ''):
            writer.write(u"没有该用户。")
            return
        
        # 加载当前用户对象.
        user = __jitar__.userService.getUserByLoginName(loginName)
        categoryService = __jitar__.categoryService
        itemType = CommonUtil.toUserVideoCategoryItemType(user.getUserId())
        usercate_tree = categoryService.getCategoryTree(itemType)
        request.setAttribute("wid", wid)
        request.setAttribute("count", count)
        request.setAttribute("oldCateId", oldCateId)
        request.setAttribute("title", title)
        request.setAttribute("usercate_tree", usercate_tree)       
        response.getWriter().write(title)
        return "/WEB-INF/user/default/get_user_videocategory.ftl"