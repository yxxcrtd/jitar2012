from cn.edustar.jitar.util import ParamUtil
from cn.edustar.data import DataTable
from cn.edustar.jitar.data import Command
from base_blog_page import *
from base_action import *
from cn.edustar.jitar.pojos import Group, GroupMember, User, Topic
from cn.edustar.jitar.service import CategoryService;


class addGroupTopic(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner):
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
        
        if self.loginUser == None:
            isMember = None
        else:
            isMember = self.group_svc.getGroupMemberByGroupIdAndUserId(group.groupId, self.loginUser.userId)
        self.getGroupInfo(group.groupName)
        
        page = self.getGroupIndexPage(group)
        page = {
                "pageId":0,
                "layoutId":8, # 固定是布局
                "isSystemPage" : "true", 
                "owner" : "user", 
                "title" :"",
                "skin":page.skin
                }
        uuid=group_svc.getGroupCateUuid(self.group)
        if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
            #课题
            request.setAttribute("isKtGroup", "1")
        elif uuid==CategoryService.GROUP_CATEGORY_GUID_JTBK :
            #备课 
            request.setAttribute("isKtGroup", "2")
        else:
            request.setAttribute("isKtGroup", "0")
              
        topic = Topic()
        topic.setTitle("")
        topic.setContent("")
        topic.setGroupId(group.groupId)
        topic.setTags("")
        
        request.setAttribute("page", page)
        request.setAttribute("group", group)
        request.setAttribute("loginUser", self.loginUser)
        request.setAttribute("isMember", isMember)
        request.setAttribute("redirect", "True")
        request.setAttribute("topic", topic)
        
        response.contentType = "text/html; charset=UTF-8"
        return "/WEB-INF/group/default/show_add_group_topic.ftl"
    