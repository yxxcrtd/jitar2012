from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from base_blog_page import *
from base_action import BaseAction
from base_preparecourse_page import *
user_svc = __jitar__.userService

class user_joinedpreparecourse(BaseAction, RequestMixiner, ResponseMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        self.loginName = request.getAttribute("loginName")          
        writer = response.getWriter()                
        # 加载当前用户对象.
        self.user = user_svc.getUserByLoginName(self.loginName)
        request.setAttribute("user", self.user)
        
        #得到当前用户的创建的活动
        qry = PrepareCourseMemberQuery(""" pc.prepareCourseId,pc.title """)
        qry.status = 0
        qry.userId = self.user.userId
        course_list = qry.query_map(10)
        request.setAttribute("course_list", course_list)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/user/default/user_joinedpreparecourse.ftl"