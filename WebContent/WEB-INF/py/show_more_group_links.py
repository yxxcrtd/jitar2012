from cn.edustar.jitar.util import ParamUtil
from base_action import ActionExecutor
from base_blog_page import *
from links_query import LinksQuery

class show_more_group_links(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def execute(self):
        self.params = ParamUtil(request)
        groupService = __jitar__.groupService
        response.setContentType("text/html; charset=UTF-8")
        groupName = request.getAttribute("groupName")
        if groupName == None or groupName == "":
            return ""
        
        group = groupService.getGroupByName(groupName)
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
        
        # 构造widgets .
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"协作组信息","module":"group_info", "ico":"", "data":""}
                  ]
        
        self.getGroupInfo(group.groupName)
        request.setAttribute("widget_list", widgets)
        request.setAttribute("widgets", widgets)
        request.setAttribute("page", page)
        
        pager = self.params.createPager()
        pager.itemName = u"链接"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        
        qry = LinksQuery("lnk.linkId, lnk.title, lnk.linkAddress, lnk.linkIcon")
        qry.objectType = ObjectType.OBJECT_TYPE_GROUP.getTypeId()
        qry.objectId = group.getGroupId()
        pager.totalRows = qry.count()
        link_list = qry.query_map(pager)        
        print "link_list=",link_list
        request.setAttribute("link_list", link_list)        
        request.setAttribute("pager", pager)
        request.setAttribute("group", group)
        return "/WEB-INF/group/default/show_more_group_links.ftl"