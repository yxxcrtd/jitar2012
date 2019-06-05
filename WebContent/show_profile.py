from cn.edustar.jitar.util import ParamUtil

class show_profile:
    def execute(self):
        self.params = ParamUtil(request)
        loginName = self.params.safeGetStringParam("loginName")
        UserUrlPattern = request.getAttribute("UserUrlPattern")
        retUrl = ""
        if UserUrlPattern == None or len(UserUrlPattern) < 1:
            retUrl = loginName + "/profile"
        else:
            UserUrlPattern = UserUrlPattern.replace("{loginName}",loginName)
            retUrl = UserUrlPattern + "py/show_user_profile.py"
        response.sendRedirect(retUrl)
        return