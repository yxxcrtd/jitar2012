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

# 协作组管理.
class articleGroupList (ActionExecutor, ShowGroupBase, PageCheckMixiner, ResponseMixiner):
    def __init__(self):
        ActionExecutor.__init__(self)
        self.params = ParamUtil(request)
        self.groupService = __jitar__.groupService
        
    def dispatcher(self, cmd):
        if self.loginUser == None: return ActionResult.LOGIN
        if self.canVisitUser(self.loginUser) == False: return ActionResult.ERROR
        return self.list_join()
    
    def list_join(self):
        # 提交的时候删除
        if self.params.existParam("groupArticleId"):            
            groupArticleId = self.params.safeGetIntParam("groupArticleId") 
            if groupArticleId > 0:
                groupArticle = self.groupService.getGroupArticle(groupArticleId)
                if groupArticle != None:
                    self.groupService.deleteGroupArticle(groupArticle)
                    
        qry = GroupArticleQuery(" ga.id, g.groupId, g.groupTitle,ga.pubDate ")
        articleId = self.params.safeGetIntParam("articleId")
        if articleId == 0:
            self.addActionError(u"缺少文章Id参数")
            return ActionResult.ERROR
        qry.articleId = articleId
        qry.articleState = None
        group_list = qry.query_map(-1)
        request.setAttribute("group_list", group_list)
        request.setAttribute("articleId", articleId)
        return "/WEB-INF/ftl/article/articleGroupList.ftl"
