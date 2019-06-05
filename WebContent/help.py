from cn.edustar.jitar.jython import JythonBaseAction

class help(JythonBaseAction):  
    def execute(self):
        if self.loginUser == None:
            response.sendRedirect("login.jsp")
            return
        response.sendRedirect("help/helpindex.html")
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl2/site_help.ftl"
