from cn.edustar.jitar.util import ParamUtil
from javax.servlet.http import HttpServletResponse
from cn.edustar.push import MashupPlatform

class receiver:
    def __init__(self):
        self.mashupService = __spring__.getBean("mashupService")
        
    def execute(self):
        self.params = ParamUtil(request)
        pushtype = self.params.safeGetStringParam("type")
        data = self.params.safeGetStringParam("data")
        if "bloggroup" == pushtype:
            mashupBlogGroup = self.mashupService.createMashupBlogGroupFromXml(data)
            if None == mashupBlogGroup:
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                response.getWriter().write("ERROR")
            else:
                if self.mashupService.mashupBlogGroupIsExist(mashupBlogGroup) == False:
                    mashupPlatform = self.mashupService.getMashupPlatformByGuid(mashupBlogGroup.platformGuid)
                    if mashupPlatform == None:
                        mashupPlatform = MashupPlatform()
                        mashupPlatform.setPlatformGuid(mashupBlogGroup.getPlatformGuid())
                        mashupPlatform.setPlatformName(mashupBlogGroup.getPlatformName())
                        mashupPlatform.setPlatformHref(mashupBlogGroup.getHref())
                        mashupPlatform.setPlatformState(0)                 
                        self.mashupService.saveOrUpdateMashupPlatform(mashupPlatform)
                    platformState = mashupPlatform.getPlatformState()
                    if platformState == 1:
                        response.setStatus(HttpServletResponse.SC_OK)
                        response.getWriter().write("LOCKED")
                    elif platformState == 2:
                        response.setStatus(HttpServletResponse.SC_OK)
                        response.getWriter().write("DELETED")
                    else:     
                        self.mashupService.saveMashupBlogGroup(mashupBlogGroup)
                        response.setStatus(HttpServletResponse.SC_OK)
                        response.getWriter().write("OK")
        else:               
            mashupContent = self.mashupService.createMashupContentFromXml(data)
            if mashupContent == None:
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                response.getWriter().write("ERROR")
            else:
                if self.mashupService.mashupContentIsExist(mashupContent) == False:
                    mashupPlatform = self.mashupService.getMashupPlatformByGuid(mashupContent.platformGuid)
                    if mashupPlatform == None:
                        mashupPlatform = MashupPlatform()
                        mashupPlatform.setPlatformGuid(mashupContent.getPlatformGuid())
                        mashupPlatform.setPlatformName(mashupContent.getPlatformName())
                        mashupPlatform.setPlatformHref(mashupContent.getHref())
                        mashupPlatform.setPlatformState(0)                 
                        self.mashupService.saveOrUpdateMashupPlatform(mashupPlatform)
                    
                    platformState = mashupPlatform.getPlatformState()
                                    
                    if platformState == 1:
                        response.setStatus(HttpServletResponse.SC_OK)
                        response.getWriter().write("LOCKED")
                    elif platformState == 2:
                        response.setStatus(HttpServletResponse.SC_OK)
                        response.getWriter().write("DELETED")
                    else:     
                        self.mashupService.saveMashupContent(mashupContent)
                        response.setStatus(HttpServletResponse.SC_OK)
                        response.getWriter().write("OK")