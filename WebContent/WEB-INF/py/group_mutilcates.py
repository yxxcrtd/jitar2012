from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from group_member_query import GroupMemberQuery
from cn.edustar.jitar.pojos import Group
from cn.edustar.jitar.pojos import GroupMutil
from group_mutilcates_query import GroupMutilcatesQuery
from cn.edustar.jitar.service import CategoryService;
 
class group_mutilcates(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        self.group_svc = __jitar__.groupService
        self.page_svc = __jitar__.pageService
    def execute(self):
        categoryService = __jitar__.categoryService
        
        groupName = request.getAttribute("groupName")
        if groupName == None or groupName == "":
            return self.notFound()     
        group = group_svc.getGroupByName(groupName)
        if group == None:
            return self.notFound()
        widgetId=self.params.getIntParamZeroAsNull("widgetId")
        if widgetId==None:
            return self.notFound()
        groupMutil=self.group_svc.getGroupMutilByWidgetId(widgetId)
        
        gm=None
        if self.loginUser != None:
            gm = self.group_svc.getGroupMemberByGroupIdAndUserId(group.groupId, self.loginUser.userId)
        request.setAttribute("group_member", gm)
        
        #课题研究组的分类
        self.groupPrentId=None
        isKtGroup=0
        uuid=self.group_svc.getGroupCateUuid(group)
        if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
            #课题
            isKtGroup=1
            request.setAttribute("isKtGroup", "1")
            #课题的文章资源图片视频需要把下级的汇聚过来
            self.groupPrentId=group.groupId
        elif uuid==CategoryService.GROUP_CATEGORY_GUID_JTBK :
            #备课 
            isKtGroup=2
            request.setAttribute("isKtGroup", "2")
        else:    
            isKtGroup=0
            request.setAttribute("isKtGroup", "0")
        
        cmd= self.params.getStringParam("cmd")
        if cmd=="savecate":
            self.savecate()
        elif cmd=="savetitle":
            self.savetitle()    
        
        itemType = CommonUtil.toGroupArticleCategoryItemType(group.getGroupId())
        articleCate_tree = categoryService.getCategoryTree(itemType)
        itemType = CommonUtil.toGroupResourceCategoryItemType(group.getGroupId())
        resourceCate_tree = categoryService.getCategoryTree(itemType)
        itemType = CommonUtil.toGroupPhotoCategoryItemType(group.getGroupId())
        photoCate_tree = categoryService.getCategoryTree(itemType)
        itemType = CommonUtil.toGroupVideoCategoryItemType(group.getGroupId())
        videoCate_tree = categoryService.getCategoryTree(itemType)

        request.setAttribute("articleCate_tree", articleCate_tree)
        request.setAttribute("resourceCate_tree", resourceCate_tree)
        request.setAttribute("photoCate_tree", photoCate_tree)
        request.setAttribute("videoCate_tree", videoCate_tree)
        
        article_cateId=0
        resource_cateId=0
        photo_cateId=0
        video_cateId=0
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
            qry1 = GroupMutilcatesQuery(""" v.videoId, v.title, v.tags, v.userId,v.href,v.flvHref,v.flvThumbNailHref,g.groupTitle,g.groupName """)
            if isKtGroup==1:
                if video_cateName!="":
                    qry1.videoCateName=video_cateName
                else:    
                    if groupMutil.videoCateId==None:
                        qry1.videoCateId=0
                    else:    
                        qry1.videoCateId=groupMutil.videoCateId
                qry1.groupPrentId =self.groupPrentId
            else:   
                if groupMutil.videoCateId==None: 
                    qry1.videoCateId=0
                else:
                    qry1.videoCateId=groupMutil.videoCateId
                qry1.widgetId=widgetId
            video_list = qry1.query_map(groupMutil.videoNumShow)
            request.setAttribute("video_list", video_list)
            
            qry2 = GroupMutilcatesQuery(""" p.photoId, p.title, p.tags, p.userId,p.isPrivateShow, p.createDate,p.href,g.groupTitle,g.groupName """)
            if isKtGroup==1:
                if photo_cateName!="":
                    qry2.photoCateName=photo_cateName
                else:    
                    if groupMutil.photoCateId==None:
                        qry2.photoCateId=0
                    else:    
                        qry2.photoCateId=groupMutil.photoCateId
                qry2.groupPrentId =self.groupPrentId
            else:    
                if groupMutil.photoCateId==None:
                    qry2.photoCateId=0
                else:    
                    qry2.photoCateId=groupMutil.photoCateId
                qry2.widgetId=widgetId
            photo_list = qry2.query_map(groupMutil.photoNumShow)
            request.setAttribute("photo_list", photo_list)
            
            qry3 = GroupMutilcatesQuery(""" a.articleId, a.title, a.userId ,a.createDate,a.typeState,g.groupTitle,g.groupName """)
            if isKtGroup==1:
                if article_cateName!="":
                    qry3.articleCateName=article_cateName
                else:    
                    if groupMutil.articleCateId==None:
                        qry3.articleCateId=0
                    else:    
                        qry3.articleCateId=groupMutil.articleCateId
                qry3.groupPrentId =self.groupPrentId
            else:
                if groupMutil.articleCateId==None:
                    qry3.articleCateId=0
                else:    
                    qry3.articleCateId=groupMutil.articleCateId
                qry3.widgetId=widgetId
            article_list = qry3.query_map(groupMutil.articleNumShow)
            request.setAttribute("article_list", article_list)
            
            qry4 = GroupMutilcatesQuery(""" r.resourceId, r.title,r.userId,r.createDate,g.groupTitle,g.groupName """)
            if isKtGroup==1:
                if resource_cateName!="":
                    qry4.resourceCateName=resource_cateName
                else:
                    if groupMutil.resourceCateId==None:
                        qry4.resourceCateId=0
                    else:    
                        qry4.resourceCateId=groupMutil.resourceCateId    
                qry4.groupPrentId =self.groupPrentId
            else:   
                if groupMutil.resourceCateId==None:
                    qry4.resourceCateId=0
                else:    
                    qry4.resourceCateId=groupMutil.resourceCateId                    
                qry4.widgetId=widgetId
            resource_list = qry4.query_map(groupMutil.resourceNumShow)
            request.setAttribute("resource_list", resource_list)
            
        widget=self.page_svc.getWidget(widgetId) 
        request.setAttribute("widget", widget)
        request.setAttribute("groupMutil", groupMutil)
        
        request.setAttribute("article_cateId", article_cateId)
        request.setAttribute("resource_cateId", resource_cateId)
        request.setAttribute("photo_cateId", photo_cateId)
        request.setAttribute("video_cateId", video_cateId)
        
        request.setAttribute("group",group)
        request.setAttribute("widgetId",widgetId)
        response.contentType = "text/html; charset=UTF-8"
        return "/WEB-INF/group/default/group_mutilcates.ftl"
    
    def notFound(self):
        response.contentType = "text/html; charset=UTF-8"
        out = response.writer
        out.write("未找到该协作组或参数不正确")
        return None
    def savetitle(self):
        widgetId=self.params.safeGetIntParam("widgetId")
        title=self.params.getStringParam("title")
        widget=self.page_svc.getWidget(widgetId) 
        if widget!=None:
            if title!="":
                widget.title=title
                self.page_svc.saveOrUpdate(widget)
    def savecate(self):
        widgetId=self.params.safeGetIntParam("widgetId")
        groupMutil=self.group_svc.getGroupMutilByWidgetId(widgetId)
        if groupMutil==None:
            groupMutil=GroupMutil()
        cate_art_Id=self.params.safeGetIntParam("cate_art_"+str(widgetId))
        cate_res_Id=self.params.safeGetIntParam("cate_res_"+str(widgetId))
        cate_pho_Id=self.params.safeGetIntParam("cate_pho_"+str(widgetId))
        cate_vio_Id=self.params.safeGetIntParam("cate_vio_"+str(widgetId))
        articlenum=self.params.safeGetIntParam("articlenum")
        if articlenum==0:
            articlenum=10
        resourcenum=self.params.safeGetIntParam("resourcenum")
        if resourcenu==0:
            resourcenum=10
        photonum=self.params.safeGetIntParam("photonum")
        if photonum==0:
            photonum=3
        videonum=self.params.safeGetIntParam("videonum")
        if videonum==0:
            videonum=3
        groupMutil.articleCateId=cate_art_Id
        groupMutil.resourceCateId=cate_res_Id
        groupMutil.photoCateId=cate_pho_Id
        groupMutil.videoCateId=cate_vio_Id
        groupMutil.articleNumShow=articlenum
        groupMutil.resourceNumShow=resourcenum
        groupMutil.photoNumShow=photonum
        groupMutil.videoNumShow=videonum
        self.group_svc.saveGroupMutil(groupMutil)
        
        