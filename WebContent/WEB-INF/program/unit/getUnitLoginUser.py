from unit_page import UnitBasePage

class getUnitLoginUser(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
    def execute(self):
        # 返回json格式        {"LoginStatus":"0","MessageCount":"xxx","CanManage":""}
       
        response.setHeader("Cache-Control", "no-cache")
        response.setHeader("Pragma", "no-cache")
        response.setDateHeader("Expires", -1)
        
        self.unit = self.getUnit()
        if self.unit == None or self.loginUser == None:
            response.getWriter().write("{\"LoginStatus\":\"0\",\"MessageCount\":\"0\",\"CanManage\":\"0\"}")
            return
        
        LoginStatus = str(self.loginUser.userId)
        
        CanManage = "0"
        if self.canManege():CanManage = "1"
        
        MessageCount = "0"
        if self.loginUser != None:
            messageService = __spring__.getBean("messageService")
            if messageService != None:
                newMessageCount = messageService.getUnreadMessages(self.loginUser.userId)
                if newMessageCount > 0:
                    MessageCount = (str(newMessageCount))
                    
        
        response.getWriter().write("{\"LoginStatus\":\"" + LoginStatus + "\",\"MessageCount\":\"" + MessageCount + "\",\"CanManage\":\"" + CanManage + "\"}")
        return