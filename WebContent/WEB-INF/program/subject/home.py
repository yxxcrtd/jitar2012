from cn.edustar.jitar.model import SiteUrlModel

class home:
    def execute(self):
        siteUrl = SiteUrlModel.getSiteUrl()
        response.setStatus(301)
        response.setHeader("Location", siteUrl)
        response.setHeader("Connection", "close")
        return
