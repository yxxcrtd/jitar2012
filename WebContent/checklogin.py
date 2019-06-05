from cn.edustar.jitar.jython import JythonBaseAction
class checklogin(JythonBaseAction):
    
    def execute(self):
        user = self.loginUser
        if user == None:
            
            print "None"
            # 返回登录界面
        else:
            print user
            # 返回登录信息显示页面
    