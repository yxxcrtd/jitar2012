from cn.edustar.jitar.util import ParamUtil
from base_action import *
from site_config import SiteConfig
from cn.edustar.jitar.data import Command

class showPhoto  :
  def __init__(self):
    self.params = ParamUtil(request)
    self.photo_svc = __jitar__.photoService
    self.cate_svc = __jitar__.categoryService
    
  def execute(self): 
    site_config = SiteConfig()
    site_config.get_config()

    photoId = self.params.getIntParam("photoId")
    
    photo = self.photo_svc.findById(photoId)
    request.setAttribute("photo", photo)
    #DEBUG print "photoId = ", photoId, ", photo = ", self.photo
    #print "===photo.sysCateId===:",photo.sysCateId
    if photo != None:
      if photo.sysCateId != None:
        category = self.cate_svc.getCategory(photo.sysCateId)
        request.setAttribute("category", category)
      # 更新访问计数 
      cmd = Command(""" UPDATE Photo SET viewCount = viewCount + 1 WHERE photoId = :photoId """)
      cmd.setInteger("photoId", photo.photoId)
      cmd.update()
    
    return "/WEB-INF/ftl/showPhoto.ftl"