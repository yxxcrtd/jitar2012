# coding=utf-8
from cn.edustar.jitar.model import SiteUrlModel
from cn.edustar.jitar.util import CommonUtil

class Util:
    
    def putSubjectList(self):
        subject_list = __jitar__.subjectService.getMetaSubjectList()
        request.setAttribute("subject_list", subject_list)
    def putGradeList(self):
        grade_list = __jitar__.subjectService.getGradeList()
        request.setAttribute("grade_list", grade_list)
        
    
    # 检查是否是同一学段的  5100 与  5000是相同的
    def checkIsSameGrade(self, int1, int2):
        if int1 == None or int2 == None:
            return False        
        str1 = str(int1)
        str2 = str(int2)
        if len(str1) != len(str2):
            return False
        if str1[0:1] == str2[0:1]:
            return True
        else:
            return False
    
    def getSiteUrl(self):
        return CommonUtil.getSiteUrl(request)
    
    def getSiteRootUrl(self):
        return CommonUtil.getSiteUrl(request) + "/"
    
    def getUserSiteUrl(self, userName):
        siteUrl = SiteUrlModel.getSiteUrl()
        userSiteUrl = request.getSession().getServletContext().getInitParameter("userUrlPattern");
        if userSiteUrl == None or userSiteUrl == "":
            userSiteUrl = siteUrl + "u/" + userName + "/"
        else:
            userSiteUrl = userSiteUrl.replace("{loginName}", userName)            
        return userSiteUrl
    
    