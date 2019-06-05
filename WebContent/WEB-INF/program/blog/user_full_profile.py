from friend_query import FriendQuery
from group_member_query import GroupMemberQuery
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from user_query import UserQuery
from base_action import BaseAction
from java.util import HashMap
from cn.edustar.jitar.util import FileCache

# 用户的完整档案.
class user_full_profile(BaseAction):
    user_svc = __jitar__.userService
    def execute(self):
        self.params = ParamUtil(request)
        self.userName = request.getAttribute("loginName")
        user = self.user_svc.getUserByLoginName(self.userName)
        if user == None:
            response.writer.println(u"不能加载该用户的信息")
            return
        if self.canVisitUser(user) == False:
            response.writer.println(u"用户 %s 无法访问." % self.userName)
            return      
        
        #self.get_user_info(loginName, user)
        fc = FileCache()
        # 14400 为10 天
        content = fc.getUserFileCacheContent(self.userName, "user_full_profile.html",14400)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("user", user)
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_full_profile.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "user_full_profile.html",content)        
        response.getWriter().write(content)
        fc = None
    
    def get_user_info(self, loginName, u): 
        request.setAttribute("user", u)
        
        # 得到好友列表.
        pager = self.params.createPager()  
        qry = FriendQuery(""" u.userId, u.loginName,u.nickName,u.trueName,u.userIcon, frd.addTime """)
        qry.userId = u.userId
        qry.isBlack = False
        qry.orderType = 0 
        pager.setPageSize(12)        
        pager.itemName = u"好友"
        pager.itemUnit = u"位"
        friend_list = qry.query_map(pager)
        pager.totalRows = friend_list.size()
        request.setAttribute("friend_list", friend_list)
        
        # 得到参加的协作组列表.
        pagergroup = self.params.createPager()  
        qry = GroupMemberQuery(""" gm.id, g.groupName, g.groupId, g.groupTitle, g.groupIcon """)
        qry.userId = u.userId
        qry.orderType = 0 
        pagergroup.setPageSize(16)        
        pagergroup.itemName = u"好友"
        pagergroup.itemUnit = u"位"
        group_list = qry.query_map(pagergroup)
        pagergroup.totalRows = friend_list.size()
        request.setAttribute("group_list", group_list)