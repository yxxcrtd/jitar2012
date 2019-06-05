from subject_page import *

class clear_sitenav_cache(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        cache = __jitar__.cacheProvider.getCache('sitenav')
        if cache != None:                    
            cache_list = cache.getAllKeys()
            cache_key = "subject_nav_" + str(self.subject.subjectId)
            for c in cache_list:
                if c == cache_key:
                    cache.remove(c)
        response.getWriter().println(u"执行完毕。")
