from cn.edustar.jitar.jython import JythonBaseAction
class check_login_status(JythonBaseAction):        
    def execute(self):
        if self.loginUser == None:
            response.getWriter().write("0")
        else:
            response.getWriter().write("1")