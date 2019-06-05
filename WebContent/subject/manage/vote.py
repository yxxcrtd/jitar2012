from subject_page import *
from vote_query import VoteQuery

class vote(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isContentAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.clear_subject_cache()
            self.save_post()
            response.sendRedirect(request.getHeader("Referer"))
            
        return self.article_list()
    
    def article_list(self):
        qry = VoteQuery(""" vote.voteId, vote.objectGuid, vote.title, vote.description, vote.createDate,
                         vote.createUserId, vote.subjectId, vote.gradeId, vote.parentGuid, vote.parentObjectType
                        """)
        
        
        return "/WEB-INF/subjectmanage/vote.ftl"

    def save_post(self):
        return