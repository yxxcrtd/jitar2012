from cn.edustar.jitar.jython import JythonBaseAction

class login_status(JythonBaseAction):
    def execute(self):
        userName = request.getAttribute("UserName")
        if userName == None or userName == "":
            user = None
        else:
            userService = __jitar__.userService
            user = userService.getUserByLoginName(userName)
            
        request.setAttribute("user",user)        
        return "/WEB-INF/user/default/login_status.ftl"