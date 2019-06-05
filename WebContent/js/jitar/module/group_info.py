# script for 协作组信息.
from cn.edustar.jitar.service import CategoryService;

# 显示协作组信息.
class group_info:
  def __init__(self):
    self.group_svc = __jitar__.groupService
    self.subj_svc = __jitar__.subjectService
    
  def execute(self):
    #print "group_info module is execute"
    
    # 得到协作组参数.
    groupId = request.getParameter("groupId")
    if groupId == None or groupId == "":
      return self.notFound()
    
    try:
      groupId = int(groupId)      # may raise ValueError
    except ValueError:
      return self.notFound()
    
    group = self.group_svc.getGroupMayCached(groupId)
    if group == None:
      return self.notFound()  
    
    #print "group = ", group
    request.setAttribute("group", group)
    
    # 获得其学科学段信息.
    #if group.subjectId != None:
    #  subject = self.subj_svc.getMetaSubjectById(group.subjectId)
    #  if subject != None:
    #    #print "subject = ", subject
    #    request.setAttribute("subject", subject)
    #
    #if group.gradeId != None:
    #  grade = self.subj_svc.getGrade(group.gradeId)
    #  if grade != None:
    #    #print "grade = ", grade
    #    request.setAttribute("grade", grade)
    
    uuid=self.group_svc.getGroupCateUuid(group)
    if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
        request.setAttribute("isKtGroup", "1")
        response.contentType = "text/html; charset=UTF-8"
        return "/WEB-INF/group/default/groupkt_info.ftl"
    elif uuid==CategoryService.GROUP_CATEGORY_GUID_JTBK :
        request.setAttribute("isKtGroup", "2")  
    else:
        request.setAttribute("isKtGroup", "0")    
    return "/WEB-INF/group/default/group_info.ftl"
    
  def notFound(self):
    response.contentType = "text/html; charset=UTF-8"
    out = response.writer
    out.write("未找到该协作组或参数不正确")
    return None
