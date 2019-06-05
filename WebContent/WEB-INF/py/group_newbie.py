from cn.edustar.jitar.util import ParamUtil
from group_member_query import GroupMemberQuery
from cn.edustar.jitar.pojos import Group
from cn.edustar.jitar.service import CategoryService;


group_svc = __jitar__.groupService

class group_newbie :
    def execute(self):
        
        response.setContentType("text/html; charset=UTF-8")
        groupName = request.getAttribute("groupName")
        if groupName == None or groupName == "":
            return ""        
        
        group = group_svc.getGroupByName(groupName)
        if group == None:
            return self.notFound()
         
        qry = GroupMemberQuery(""" u.loginName,u.userIcon,u.nickName,gm.joinDate,gm.teacherUnit,gm.teacherZYZW,gm.teacherXL,gm.teacherXW,gm.teacherYJZC """)
        qry.groupId = group.groupId
        qry.orderType = 0
        qry.memberStatus = 0
        ugm_list = qry.query_map(6)
        
        request.setAttribute("group", group) 
        request.setAttribute("ugm_list", ugm_list)  
        
        uuid=group_svc.getGroupCateUuid(group)
        if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
            #课题
            request.setAttribute("isKtGroup", "1")
        elif uuid==CategoryService.GROUP_CATEGORY_GUID_JTBK :
            #备课 
            request.setAttribute("isKtGroup", "2")
        else:
            request.setAttribute("isKtGroup", "0")
        
        return "/WEB-INF/group/default/group_newbie.ftl"    
    
    def notFound(self):
        out = response.writer
        out.write(u"未找到该协作组或参数不正确")
        return None
