from cn.edustar.jitar.jython import JythonBaseAction

class getLoginUserNewMessageCount(JythonBaseAction):
    def execute(self):
        response.setHeader("Cache-Control", "no-cache")
        response.setHeader("Pragma", "no-cache")
        response.setDateHeader("Expires", -1)
        if self.loginUser == None:
            response.getWriter().println("")
        else:
            messageService = __spring__.getBean("messageService")
            if messageService == None:
                response.getWriter().println("")
            else:
                newMessageCount = messageService.getUnreadMessages(self.loginUser.userId)
                if newMessageCount > 0:
                    response.getWriter().println(str(newMessageCount))
                else:
                    response.getWriter().println("")
