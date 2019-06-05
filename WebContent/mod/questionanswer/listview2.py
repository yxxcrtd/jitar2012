#encoding=utf-8
from common_data import CommonData

class listview2(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.questionAnswerService = __spring__.getBean("questionAnswerService")

    def execute(self):
                    
        if self.parentGuid == "":
            return "/WEB-INF/mod/questionanswer/not_found.ftl"
        q_list = self.questionAnswerService.getQuestionList(self.parentGuid,"new",5)
        request.setAttribute("q_list",q_list)
       
        refer = request.getHeader("referer")
        request.setAttribute("refer",refer)
        #/py/userbasepage.py?url=mod/questionanswer/getcontent.py
        return "/WEB-INF/mod/questionanswer2/listview.ftl" 
        