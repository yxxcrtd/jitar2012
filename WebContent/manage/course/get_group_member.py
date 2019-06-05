from group_member_query import GroupMemberQuery
from base_preparecourse_page import PrepareCourseMemberQuery

class get_group_member(SubjectMixiner,BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)       
        self.groupId = 0
        
    def execute(self):
        self.groupId = self.params.safeGetIntParam("groupId")
        inputUser_Id = self.params.getStringParam("inputUser_Id")
        inputUserName_Id = self.params.getStringParam("inputUserName_Id")       
        
        if self.groupId == 0:
            actionErrors = [u"请输入群组标识。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        qry = GroupMemberQuery("  u.userId, u.trueName ")
        qry.memberStatus = 0        
        qry.groupId = self.groupId
        group_member = qry.query_map(qry.count())
        #去除已经是成员的用户
        qry = PrepareCourseMemberQuery("pcm.userId")
        qry.prepareCourseId = self.params.safeGetIntParam("prepareCourseId")
        member_list = qry.query_map(qry.count())
        if group_member != None and len(group_member) > 0 and member_list != None and len(member_list) > 0:
            for m in member_list:
                for g in group_member:
                    if g["userId"]==m["userId"]:
                        group_member.remove(g)
                        break
        if len(group_member) > 0:
            request.setAttribute("group_member", group_member)
        request.setAttribute("inputUserName_Id", inputUserName_Id)
        request.setAttribute("inputUser_Id", inputUser_Id)
        return "/WEB-INF/ftl/course/get_group_member.ftl"
        