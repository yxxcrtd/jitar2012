
class admin_subject_manager_by_subject_main_left:
    def __init__(self):
        self.subjectService = __spring__.getBean("subjectService")
        self.subject_main_url = "admin_subject_manager_by_subject_main_right.py"
        
    def execute(self):
        subject_list = self.subjectService.getSubjectList()
        html = ""
        for s in subject_list:
            html = html + "d.add(" + str(s.subjectId) + ",-1,'" + s.subjectName + "','" + self.subject_main_url + "?subjectId=" + str(s.subjectId) + "','" + s.subjectName + "','subjectmain');\r\n"
        request.setAttribute("subject_list", subject_list)
        request.setAttribute("html", html)
        return "/WEB-INF/ftl/admin/admin_subject_manager_by_subject_main_left.ftl"
