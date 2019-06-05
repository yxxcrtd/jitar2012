
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.data import DataTable
from cn.edustar.jitar.data import Command
from base_blog_page import *
from base_action import *
from cn.edustar.jitar.pojos import Group, GroupMember, User, Topic
from cn.edustar.jitar.service import CategoryService;

class listGroupTopic(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def __init__(self):                
        self.params = ParamUtil(request)
        self.group_svc = __jitar__.groupService 
        self.bbs_svc = __jitar__.bbsService 
        
    def execute(self):
        groupId =  self.params.getIntParam("groupId")
        if groupId == None or groupId == 0:
            return self.notFound()
        
        group = self.group_svc.getGroup(groupId)
        if group == None:
            return self.notFound()
        
        page = self.getGroupIndexPage(group)
        page = {
                "pageId":0,
                "layoutId":2, # 固定是布局
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
                       {"id": "1", "pageId":0, "columnIndex":1,"title":u"备课 组信息","module":"group_info", "ico":"", "data":""}
                      ]              
        else:
            request.setAttribute("isKtGroup", "0")
            widgets = [
                       {"id": "1", "pageId":0, "columnIndex":1,"title":u"协作组信息","module":"group_info", "ico":"", "data":""}
                      ]              

 
        
        if self.loginUser == None:
            isMember = None
        else:
            isMember = self.group_svc.getGroupMemberByGroupIdAndUserId(group.groupId, self.loginUser.userId)
        self.getGroupInfo(group.groupName)
        
        pager = self.params.createPager()
        pager.setPageSize(20)
        pager.itemName = u"主题"
        pager.itemUnit = u"个"
        
        topic_list = self.bbs_svc.getTopicDataTable(group.groupId, pager)
  
        request.setAttribute("page", page)
        request.setAttribute("widget_list", widgets)        
        request.setAttribute("group", group)
        request.setAttribute("topic_list", topic_list)
        request.setAttribute("pager", pager)
        request.setAttribute("loginUser", self.loginUser)
        request.setAttribute("isMember", isMember)
        
        
        response.contentType = "text/html; charset=UTF-8"
        return "/WEB-INF/group/default/show_group_topic_list.ftl"