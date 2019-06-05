from subject_page import *

cache = __jitar__.cacheProvider.getCache('subject')

class clear_cache(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False and self.isContentAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        cache_list = cache.getAllKeys()
        cache_key_head = "sbj" + str(self.subjectId)
        for c in cache_list:
            if c.split("_")[0] == cache_key_head:
                cache.remove(c)
        return self.SUCCESS
