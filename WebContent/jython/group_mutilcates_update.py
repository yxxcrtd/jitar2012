from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Group
from cn.edustar.jitar.pojos import GroupMutil
 
class group_mutilcates_update(ActionExecutor, RequestMixiner, ResponseMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        self.group_svc = __jitar__.groupService
        self.page_svc = __jitar__.pageService
    def execute(self):
        cmd= self.params.getStringParam("cmd")
        if cmd=="savecate":
            self.savecate()
        elif cmd=="savetitle":
            self.savetitle()    
    
    def notFound(self):
        response.contentType = "text/html; charset=UTF-8"
        out = response.writer
        out.write("未找到该协作组或参数不正确")
        return None
    def savetitle(self):
        widgetId=self.params.safeGetIntParam("widgetId")
        title=self.params.getStringParam("title")
        groupId=self.params.safeGetIntParam("groupId")
        widget=self.page_svc.getWidget(widgetId) 
        if widget!=None:
            if title!="":
                widget.title=title
                self.page_svc.saveOrUpdate(widget)
        #response.contentType = "text/html; charset=UTF-8"
        #out = response.writer
        #out.write("200 OK")
        response.sendRedirect(self.get_site_url() + "go.action?groupId=" + str(groupId))
    def savecate(self):
        widgetId=self.params.safeGetIntParam("widgetId")
        groupMutil=self.group_svc.getGroupMutilByWidgetId(widgetId)
        if groupMutil==None:
            groupMutil=GroupMutil()
            groupMutil.widgetId=widgetId
        cate_art_Id=self.params.safeGetIntParam("cate_art_"+str(widgetId))
        cate_res_Id=self.params.safeGetIntParam("cate_res_"+str(widgetId))
        cate_pho_Id=self.params.safeGetIntParam("cate_pho_"+str(widgetId))
        cate_vio_Id=self.params.safeGetIntParam("cate_vio_"+str(widgetId))
        articlenum=self.params.safeGetIntParam("articlenum")
        if articlenum==0:
            articlenum=10
        resourcenum=self.params.safeGetIntParam("resourcenum")
        if resourcenum==0:
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
        
        groupId=self.params.safeGetIntParam("groupId")
        
        #response.contentType = "text/html; charset=UTF-8"
        #out = response.writer
        #out.write("200 OK")
        #response.getWriter().write("" + errCode + " " + msg);
        #return ;
        response.sendRedirect(self.get_site_url() + "go.action?groupId=" + str(groupId))
    def get_site_url(self):
        url = request.getScheme() + "://" + request.getServerName()
        if request.getServerPort() != 80:
            url = url + ":" + str(request.getServerPort())
        url = url + request.getContextPath() + "/"
        return url        