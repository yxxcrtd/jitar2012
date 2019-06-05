# coding=utf-8
from cn.edustar.jitar.jython import JythonBaseAction
from base_action import *
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import GroupMember
from group_member_query import GroupMemberQuery
from group_data_list import GroupDataQueryList
from java.net import URLEncoder

class group_data_query(JythonBaseAction, ActionResult):
    def __init__(self):
        self.params = ParamUtil(request)
        self.groupService = __jitar__.groupService
        self.group = None
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        groupId = self.params.safeGetIntParam("groupId")
        self.group = self.groupService.getGroup(groupId)
        if self.group == None:
            self.addActionError(u"无法加载协作组。")
            return self.ERROR
        
        # 当前用户是否可以管理协作组
        if self.canManageGroup() == False:
            self.addActionError(u"您没有管理协作组的权限。")
            return self.ACCESS_DENIED

        uuid=group_svc.getGroupCateUuid(self.group)
        if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
            request.setAttribute("isKtGroup", "1")
        else:
            request.setAttribute("isKtGroup", "0")
                    
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "init":
            self.listMember()
        elif cmd == "query":
            self.dataquery()
        elif cmd == "list":
            return self.datalist()
        elif cmd == "export":
            return self.dataexport()
        else:
            self.addActionError(u"无效的命令。")
            return self.ERROR
            
        request.setAttribute("group", self.group)
        request.setAttribute("guid", self.params.safeGetStringParam("guid"))        
        return "/WEB-INF/ftl/group/group_data_query.ftl"
    
    def listMember(self):
        #删除过时数据
        self.groupService.deleteGroupDataQuery()
        guid = self.params.safeGetStringParam("guid")
        if guid != "":
            self.groupService.deleteGroupDataQueryByGuid(guid)
        beginDate = self.params.safeGetStringParam("beginDate")
        endDate = self.params.safeGetStringParam("endDate")
        
        qry = GroupMemberQuery("gm.userId, u.loginName,u.trueName")
        qry.memberStatus = 0
        qry.groupId = self.group.groupId
        qry.orderType = 2
        memberCount = qry.count()        
        member_list = qry.query_map(memberCount)
        if beginDate != "" and endDate != "":
            request.setAttribute("beginDate", beginDate)
            request.setAttribute("endDate", endDate)
        
        if len(member_list) > 0:
            request.setAttribute("member_list", member_list)
    
    def datalist(self):        
        guid = self.params.safeGetStringParam("guid")
        beginDate = self.params.safeGetStringParam("beginDate")
        endDate = self.params.safeGetStringParam("endDate")
        
        if guid == "":
            self.addActionError(u"没有查询的标识，请重新查询。")
            return self.ERROR
        qry = GroupDataQueryList(""" gdq.loginName, gdq.trueName, gdq.unitName, gdq.unitTitle,
                    gdq.metaSubjectId, gdq.metaSubjectTitle, gdq.gradeId, gdq.gradeTitle,
                    gdq.articleCount, gdq.bestArticleCount, gdq.resourceCount, gdq.bestResourceCount,
                    gdq.topicCount, gdq.replyCount                
                    """)
        qry.objectGuid = guid
        pager = self.params.createPager()
        pager.itemName = u"记录"
        pager.itemUnit = u"条"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        data_list = qry.query_map(pager)
        request.setAttribute("group", self.group)
        request.setAttribute("beginDate", beginDate)
        request.setAttribute("endDate", endDate)
        request.setAttribute("pager", pager)
        request.setAttribute("data_list", data_list)
        request.setAttribute("guid", guid)        
        return "/WEB-INF/ftl/group/group_data_query_list.ftl"
    
    def dataexport(self):
        guid = self.params.safeGetStringParam("guid")
        beginDate = self.params.safeGetStringParam("beginDate")
        endDate = self.params.safeGetStringParam("endDate")
        
        if guid == "":
            self.addActionError(u"没有查询的标识，请重新查询。")
            return self.ERROR
        qry = GroupDataQueryList(""" gdq.loginName, gdq.trueName, gdq.unitName, gdq.unitTitle,
                    gdq.metaSubjectId, gdq.metaSubjectTitle, gdq.gradeId, gdq.gradeTitle,
                    gdq.articleCount, gdq.bestArticleCount, gdq.resourceCount, gdq.bestResourceCount,
                    gdq.topicCount, gdq.replyCount                
                    """)
        qry.objectGuid = guid
        data_list = qry.query_map(qry.count())
        request.setAttribute("group", self.group)
        request.setAttribute("beginDate", beginDate)
        request.setAttribute("endDate", endDate)
        request.setAttribute("data_list", data_list)
        
        response.reset()
        request.setCharacterEncoding("utf-8")        
        response.setContentType("application/vnd.ms-excel")
        response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312")
        #response.setCharacterEncoding("GB2312")
        #response.setLocale(Locale.SIMPLIFIED_CHINESE)
        codedfilename = URLEncoder.encode(self.group.groupTitle, "UTF-8");  
        response.addHeader("Content-Disposition", "attachment;filename=" + codedfilename + ".xls")
        return "/WEB-INF/ftl/group/group_data_query_export.ftl"        
    
    def canManageGroup(self):
        if self.loginUser == None:
            return False
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser):
            return True
        
        groupMember = self.groupService.getGroupMemberByGroupIdAndUserId(self.group.groupId, self.loginUser.userId)
        if groupMember == None:
            return False
        role = groupMember.getGroupRole()
        grole = GroupMember.GROUP_ROLE_VICE_MANAGER  
        if role >= grole:
            return True
        return False
