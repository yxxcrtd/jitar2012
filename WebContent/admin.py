from cn.edustar.jitar.model import UserMgrModel
class admin:
    def execute(self):
        userMgrUrl = UserMgrModel.getUserMgrUrl()
        if userMgrUrl == None or userMgrUrl == "":
            response.getWriter().write(u"统一用户地址没有配置。请手动进行登录，")
        else:
            response.sendRedirect(userMgrUrl + "?redUrl=" + self.getSiteUrl() + "manage/")
            
    def getSiteUrl(self):
        if request.getServerPort() == 80:
            root = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/"
        else:
            root = request.getScheme() + "://" + request.getServerName() + ":" + str(request.getServerPort()) + request.getContextPath() + "/"
        return root
