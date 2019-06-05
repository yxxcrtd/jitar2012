from base_action import *
from java.util import Date
from cn.edustar.jitar.action import ActionLink
from cn.edustar.jitar.pojos import NewSpecialSubject

class saveNewSpecialSubject(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.specialSubject_svc = __spring__.getBean("specialSubjectService")
        
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN
        
        st = self.params.safeGetStringParam("stitle")
        sd = self.params.safeGetStringParam("scontent")
        if st == "" or sd == "":
            self.addActionError(u"请输入候选 主题名称 或 提交说明。")
            return self.ERROR
        newSpecialSubject = NewSpecialSubject(st,sd,self.loginUser.userId,self.loginUser.trueName,Date(),self.get_client_ip())
        self.specialSubject_svc.addNewSpecialSubject(newSpecialSubject)
        #out = response.getWriter()
        #out.println(1)
        
        #actionMessages = [u"添加成功。"]
        #a = []
        #a.append(ActionLink(u"返回专题首页", "../specialSubject.action"))
        #a.append(ActionLink(u"查看候选专题", "showNewSpecialSubject.py"))
        #request.setAttribute("actionLinks", a)
        self.addActionMessage(u"添加成功。")
        return self.SUCCESS
        
        
    def get_client_ip(self):
        strip = request.getHeader("x-forwarded-for")
        if strip == None or strip == "":
            strip = request.getRemoteAddr()
        return strip