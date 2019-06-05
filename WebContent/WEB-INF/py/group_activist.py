from cn.edustar.jitar.util import ParamUtil
from group_member_query import GroupMemberQuery
from cn.edustar.jitar.pojos import Group


group_svc = __jitar__.groupService

class group_activist :
    def execute(self):
        
        response.setContentType("text/html; charset=UTF-8")
        groupName = request.getAttribute("groupName")
        if groupName == None or groupName == "":
            return ""
        
        group = group_svc.getGroupByName(groupName)
        if group == None:
            return self.notFound()
         
        qry = GroupMemberQuery(""" u.loginName,u.userIcon,u.nickName,gm.joinDate """)
        qry.groupId = group.groupId
        qry.orderType = 1
        qry.memberStatus = 0
        ugm_list = qry.query_map(6)

        request.setAttribute("ugm_list", ugm_list)        
        return "/WEB-INF/group/default/group_activist.ftl"    
    
    def notFound(self):
        out = response.writer
        out.write(u"未找到该协作组或参数不正确")
        return None