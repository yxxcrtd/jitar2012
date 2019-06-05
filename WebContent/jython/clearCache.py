from cn.edustar.jitar.util import ParamUtil

class clearCache:
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        cache_name = self.params.getStringParam("name")
        object_id = self.params.safeGetIntParam("id")
        if cache_name == "preparecourse":
            cache = __jitar__.cacheProvider.getCache('prepareCourse')
            cache_key = "prepareCourse_page_" + str(object_id)
            cache.remove(cache_key)