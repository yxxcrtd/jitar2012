
# 显示文章
class export:
    def __init__(self):
        self.htmlGeneratorService = __spring__.getBean("htmlGeneratorService")
    def execute(self):
        self.htmlGeneratorService.SiteIndex()
        response.getWriter().write("<a href='index.htm' target='_blank'>ok</a>")
        
        