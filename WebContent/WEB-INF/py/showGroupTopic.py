from cn.edustar.jitar.util import ParamUtil
from cn.edustar.data import DataTable
from cn.edustar.jitar.data import Command
from base_blog_page import *
from base_action import *
from cn.edustar.jitar.pojos import Group, GroupMember, User, Topic

# http://www.jitar.com.cn:8080/Groups/manage/groupBbs.action?cmd=reply_list&groupId=1&topicId=105
class showGroupTopic(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def __init__(self):                
        self.params = ParamUtil(request)
        self.group_svc = __jitar__.groupService 
        self.bbs_svc = __jitar__.bbsService 
        
    def execute(self):
        groupId =  self.params.getIntParam("groupId")
        topicId =  self.params.getIntParam("topicId")
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
        
        pager = self.params.createPager()
        pager.setPageSize(10)
        pager.itemName = u"回复"
        pager.itemUnit = u"条"
        topic = self.bbs_svc.getTopicById(topicId);
        reply_list = self.bbs_svc.getReplyDataTable(topicId, pager)
        request.setAttribute("page", page)
        request.setAttribute("group", group)
        request.setAttribute("topic", topic)
        request.setAttribute("pager", pager)
        request.setAttribute("reply_list", reply_list)
        request.setAttribute("loginUser", self.loginUser)
        request.setAttribute("isMember", isMember)
        
        response.contentType = "text/html; charset=UTF-8"
        return "/WEB-INF/group/default/show_group_topic.ftl"