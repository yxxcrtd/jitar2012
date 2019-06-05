from base_action import ActionExecutor,ActionResult
from cn.edustar.jitar.pojos import Photo
from cn.edustar.jitar.util import ParamUtil

class photo_show_state(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.p_svc = __spring__.getBean("photoService")
        self.login_user = self.getLoginUser()
        self.writer = response.getWriter()
        
    def execute(self):
        if self.login_user == None:
            self.writer.write(u"请先登录。")
            return
        photoId = self.params.getIntParam("photoId")
        show = self.params.safeGetStringParam("show")
        photo = self.p_svc.findById(photoId)
        if photo == None:
            self.writer.write(u"没有找到相应的照片。")
            return
        if show == "true":
            photo.setIsPrivateShow(True)
        else:
            photo.setIsPrivateShow(False)
        self.p_svc.updatePhoto(photo)
        #更新频道图片状态
        channelPageService = __spring__.getBean("channelPageService")
        channelPhotoList = channelPageService.getChannelPhotoList(photo.photoId)
        if channelPhotoList != None and len(channelPhotoList) > 0:
            for cp in channelPhotoList:
                cp.title = photo.title
                cp.showState = (photo.delState == False and photo.auditState == 0 and photo.getIsPrivateShow() == False)
                channelPageService.saveOrUpdateChannelPhoto(cp)
        
        self.writer.write("200 OK")
        return
    