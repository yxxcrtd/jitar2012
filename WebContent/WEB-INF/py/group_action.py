from cn.edustar.jitar.util import ParamUtil
from group_member_query import GroupMemberQuery
from cn.edustar.jitar.pojos import Group
from action_query import ActionQuery

group_svc = __jitar__.groupService
class group_action:
    def execute(self):
        response.setContentType("text/html; charset=UTF-8")
        groupName = request.getAttribute("groupName")
        if groupName == None or groupName == "":
            return ""        
        
        group = group_svc.getGroupByName(groupName)
        if group == None:
            return self.notFound()
         
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount
                            """)
        qry.ownerType = "group"
        qry.status = 0
        qry.ownerId = group.groupId
        
        self.action_list = qry.query_map(10)
   
        request.setAttribute("action_list", self.action_list)
        request.setAttribute("group", group)     
        return "/WEB-INF/group/default/group_action.ftl"    
    
    def notFound(self):
        out = response.writer
        out.write(u"未找到该协作组或参数不正确")
        return None