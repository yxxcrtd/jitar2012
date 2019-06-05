from cn.edustar.jitar.pojos import Group, GroupMember, Resource, Message
from cn.edustar.jitar.model import ObjectType
from cn.edustar.jitar.util import ParamUtil
from base_blog_page import PageCheckMixiner, ShowGroupBase, ResponseMixiner
from group_query import GroupQuery
from placard_query import PlacardQuery
from bbs_query import TopicQuery 
from article_query import GroupArticleQuery
from resource_query import GroupResourceQuery
from base_action import *
from message_query import MessageQuery
from java.util import UUID

class resourceGroupList (ActionExecutor, ShowGroupBase, PageCheckMixiner, ResponseMixiner):
    def __init__(self):
        ActionExecutor.__init__(self)
        self.params = ParamUtil(request)
        self.groupService = __jitar__.groupService
        
    def dispatcher(self, cmd):
        if self.loginUser == None: return ActionResult.LOGIN
        if self.canVisitUser(self.loginUser) == False: return ActionResult.ERROR
        return self.list_join()
    
    def list_join(self):
        qry = GroupResourceQuery("g.groupTitle, gr.id, gr.pubDate")
        resourceId = self.params.safeGetIntParam("resourceId")
        if resourceId == 0:
            self.addActionError(u"缺少资源 Id 参数")
            return ActionResult.ERROR
        
        if self.params.existParam("groupResourceId"):
            groupResourceId = self.params.safeGetIntParam("groupResourceId")
            groupResource = self.groupService.getGroupResource(groupResourceId)
            if groupResource != None:
                self.groupService.deleteGroupResource(groupResource)
        
        qry.resourceId = resourceId
        group_list = qry.query_map(-1)
        request.setAttribute("resourceId", resourceId)
        request.setAttribute("group_list", group_list)
        return "/WEB-INF/ftl/resource/resourceGroupList.ftl"