from cn.edustar.jitar.util import ParamUtil
from group_member_query import GroupMemberQuery
from cn.edustar.jitar.pojos import Group
from base_action import ActionExecutor
from cn.edustar.jitar.service import CategoryService;
from base_blog_page import *

class group_member_list(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def __init__(self):                
        self.params = ParamUtil(request)
        self.group_svc = __jitar__.groupService        
        
    def execute(self):
        response.setContentType("text/html; charset=UTF-8")
        groupName = request.getAttribute("groupName")
        if groupName == None or groupName == "":
            return ""        
        
        group = group_svc.getGroupByName(groupName)
        if group == None:
            return self.notFound()
        
        
        page = self.getGroupIndexPage(group)
        page = {
                "pageId":0,
                "layoutId":2, # 固定是布局2
                "isSystemPage" : "true", 
                "owner" : "user", 
                "title" :"",
                "skin":page.skin
                }        
     
        uuid=self.group_svc.getGroupCateUuid(group)
        if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
            #课题 
            request.setAttribute("isKtGroup", "1")
            widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"课题介绍","module":"group_info", "ico":"", "data":""}
                  ]
        elif uuid==CategoryService.GROUP_CATEGORY_GUID_JTBK :
            #备课 
            request.setAttribute("isKtGroup", "2")
            widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"备课组信息","module":"group_info", "ico":"", "data":""}
                  ]
        else:
            request.setAttribute("isKtGroup", "0")
            widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"协作组信息","module":"group_info", "ico":"", "data":""}
                  ]
                    
        self.getGroupInfo(group.groupName)
        request.setAttribute("widget_list", widgets)
        request.setAttribute("widgets", widgets)
        request.setAttribute("page", page)
        pager = self.params.createPager()
        pager.itemName = u"成员"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        
        qry = GroupMemberQuery(""" u.loginName,u.userIcon,u.nickName,gm.joinDate,gm.teacherUnit,gm.teacherZYZW,gm.teacherXL,gm.teacherXW,gm.teacherYJZC """)
        qry.groupId = group.groupId
        qry.orderType = 0
        qry.memberStatus = 0
        pager.totalRows = qry.count()
        ugm_list = qry.query_map(pager)   
        request.setAttribute("pager", pager)
        request.setAttribute("ugm_list", ugm_list)        
        request.setAttribute("group", group)
        return "/WEB-INF/group/default/group_member_list.ftl" 