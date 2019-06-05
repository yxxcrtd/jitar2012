from cn.edustar.jitar.model import ObjectType
from cn.edustar.jitar.pojos import LeaveWord
from cn.edustar.jitar.util import ParamUtil
from base_action import ActionExecutor
from base_blog_page import *
from leaveword_query import LeaveWordQuery
from cn.edustar.jitar.service import CategoryService;

class show_more_group_leaveword(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner):
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
     
        
        uuid=groupService.getGroupCateUuid(group)
        if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
            #课题
            request.setAttribute("isKtGroup", "1")
            # 构造widgets .
            widgets = [
                       {"id": "1", "pageId":0, "columnIndex":1,"title":u"课题介绍","module":"group_info", "ico":"", "data":""}
                      ]
        elif uuid==CategoryService.GROUP_CATEGORY_GUID_JTBK :
            #备课 
            request.setAttribute("isKtGroup", "2")
            # 构造widgets .
            widgets = [
                       {"id": "1", "pageId":0, "columnIndex":1,"title":u"备课组信息","module":"group_info", "ico":"", "data":""}
                      ]
        else:
            request.setAttribute("isKtGroup", "0")
            # 构造widgets .
            widgets = [
                       {"id": "1", "pageId":0, "columnIndex":1,"title":u"协作组信息","module":"group_info", "ico":"", "data":""}
                      ]

                    
        self.getGroupInfo(group.groupName)
        request.setAttribute("widget_list", widgets)
        request.setAttribute("widgets", widgets)
        request.setAttribute("page", page)
        
        pager = self.params.createPager()
        pager.itemName = u"留言"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        
        qry = LeaveWordQuery("lwd.id, lwd.title,lwd.content, lwd.createDate, lwd.userId")
        qry.objType = ObjectType.OBJECT_TYPE_GROUP.getTypeId()
        qry.objId = group.groupId
        pager.totalRows = qry.count()
        leaveword_list = qry.query_map(pager)
        
        request.setAttribute("leaveword_list", leaveword_list)        
        request.setAttribute("pager", pager)
        request.setAttribute("group", group)
        return "/WEB-INF/group/default/show_more_group_leaveword.ftl"