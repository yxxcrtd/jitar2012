from cn.edustar.jitar.jython import JythonBaseAction

class usefulltools(JythonBaseAction):
	def execute(self):
		if self.loginUser == None:
			return "/WEB-INF/ftl/Error.ftl"
		return "/WEB-INF/ftl/user/usefulltools.ftl"