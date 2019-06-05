from cn.edustar.jitar.util import ParamUtil
from base_action import ActionExecutor
from base_blog_page import *
from cn.edustar.jitar.pojos import GroupMutil
from group_mutilcates_query import GroupMutilcatesQuery
from links_query import LinksQuery
from cn.edustar.jitar.service import CategoryService;

class group_mutilcates_list(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        self.group_svc = __jitar__.groupService
        self.page_svc = __jitar__.pageService   
         
    def execute(self):
        self.params = ParamUtil(request)
        categoryService = __jitar__.categoryService
        
        response.setContentType("text/html; charset=UTF-8")
        groupName = request.getAttribute("groupName")
        if groupName == None or groupName == "":
            return ""
        
        group = self.group_svc.getGroupByName(groupName)
        if group == None:
            return self.notFound()
        widgetId=self.params.getIntParamZeroAsNull("widgetId")
        if widgetId==None:
            return self.notFound()
        groupMutil=self.group_svc.getGroupMutilByWidgetId(widgetId)
                
        page = self.getGroupIndexPage(group)
        page = {
                "pageId":0,
                "layoutId":2, # 固定是布局2
                "isSystemPage" : "true", 
                "owner" : "user", 
                "title" :"",
                "skin":page.skin
                }
        
        self.groupPrentId=None
        isKtGroup=0
        
        uuid=self.group_svc.getGroupCateUuid(group)
        if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
            #课题
            request.setAttribute("isKtGroup", "1")
            isKtGroup=1
            #课题的文章资源图片视频需要把下级的汇聚过来
            self.groupPrentId=group.groupId
            
            # 构造widgets .
            widgets = [
                       {"id": "1", "pageId":0, "columnIndex":1,"title":u"课题介绍","module":"group_info", "ico":"", "data":""}
                      ]
        elif uuid==CategoryService.GROUP_CATEGORY_GUID_JTBK :
            #备课 
            isKtGroup=2
            request.setAttribute("isKtGroup", "2")
            # 构造widgets .
            widgets = [
                       {"id": "1", "pageId":0, "columnIndex":1,"title":u"备课组信息","module":"group_info", "ico":"", "data":""}
                      ]
        else:
            isKtGroup=0
            request.setAttribute("isKtGroup", "0")        
            # 构造widgets .
            widgets = [
                       {"id": "1", "pageId":0, "columnIndex":1,"title":u"协作组信息","module":"group_info", "ico":"", "data":""}
                      ]
        
        self.getGroupInfo(group.groupName)
        request.setAttribute("widget_list", widgets)
        request.setAttribute("widgets", widgets)
        request.setAttribute("page", page)

        if groupMutil!=None:
            
            article_cateId=groupMutil.articleCateId
            resource_cateId=groupMutil.resourceCateId
            photo_cateId=groupMutil.photoCateId
            video_cateId=groupMutil.videoCateId
            
            article_cateName=""
            resource_cateName=""
            photo_cateName=""
            video_cateName=""
            if isKtGroup==1:
                cateArt=categoryService.getCategory(article_cateId)
                cateRes=categoryService.getCategory(resource_cateId)
                catePho=categoryService.getCategory(photo_cateId)
                cateVio=categoryService.getCategory(video_cateId)
                if cateArt!=None:
                    article_cateName=cateArt.name
                if cateRes!=None:
                    resource_cateName=cateRes.name
                if catePho!=None:
                    photo_cateName=catePho.name
                if cateVio!=None:
                    video_cateName=cateVio.name
                                
            qry1 = GroupMutilcatesQuery(""" v.videoId, v.title, v.tags, v.userId,v.href,v.flvHref,v.flvThumbNailHref,g.groupTitle,g.groupName,v.createDate """)
            if isKtGroup==1:
                if video_cateName!="":
                    qry1.videoCateName=video_cateName
                else:    
                    qry1.videoCateId=groupMutil.videoCateId
                qry1.groupPrentId =self.groupPrentId
            else:    
                qry1.videoCateId=groupMutil.videoCateId
                qry1.widgetId=widgetId
                
            video_list = qry1.query_map()
            request.setAttribute("video_list", video_list)
            
            qry2 = GroupMutilcatesQuery(""" p.photoId, p.title, p.tags, p.userId,p.isPrivateShow, p.createDate,p.href,g.groupTitle,g.groupName,p.createDate """)
            if isKtGroup==1:
                if photo_cateName!="":
                    qry2.photoCateName=photo_cateName
                else:    
                    qry2.photoCateId=groupMutil.photoCateId
                qry2.groupPrentId =self.groupPrentId
            else:    
                qry2.photoCateId=groupMutil.photoCateId
                qry2.widgetId=widgetId
            photo_list = qry2.query_map()
            request.setAttribute("photo_list", photo_list)
            
            qry3 = GroupMutilcatesQuery(""" a.articleId, a.title,a.userId,g.groupTitle,g.groupName,a.createDate,a.typeState """)
            if isKtGroup==1:
                if article_cateName!="":
                    qry3.articleCateName=article_cateName
                else:    
                    qry3.articleCateId=groupMutil.articleCateId
                qry3.groupPrentId =self.groupPrentId
            else:    
                qry3.articleCateId=groupMutil.articleCateId
                qry3.widgetId=widgetId
            article_list = qry3.query_map()
            request.setAttribute("article_list", article_list)
            
            qry4 = GroupMutilcatesQuery(""" r.resourceId, r.title,r.userId,g.groupTitle,g.groupName,r.createDate """)
            if isKtGroup==1:
                if resource_cateName!="":
                    qry4.resourceCateName=resource_cateName
                else:
                    qry4.resourceCateId=groupMutil.resourceCateId    
                qry4.groupPrentId =self.groupPrentId
            else:    
                qry4.resourceCateId=groupMutil.resourceCateId
                qry4.widgetId=widgetId
            resource_list = qry4.query_map()
            request.setAttribute("resource_list", resource_list)
            
        widget=self.page_svc.getWidget(widgetId) 
        request.setAttribute("widget", widget)
        request.setAttribute("groupMutil", groupMutil)
        request.setAttribute("group", group)
        return "/WEB-INF/group/default/show_more_group_mutilcates.ftl"