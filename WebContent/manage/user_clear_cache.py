from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import FileCache

class user_clear_cache(JythonBaseAction):
    def execute(self):
        currentUser = self.loginUser
        if currentUser == None:
            response.getWriter().write(u"请先登录。")
            return
        
        fc = FileCache()
        fc.deleteUserAllCache(currentUser.loginName)
        fc = None
        response.getWriter().write(u"清空完毕。")