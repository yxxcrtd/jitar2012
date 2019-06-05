from base_action import *
from unit_query import UnitQuery
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Unit
from cn.edustar.jitar.jython import BaseAdminAction

class admin_subject (ActionExecutor):
  def __init__(self):
    self.params = ParamUtil(request)
    return
 
  def dispatcher(self, cmd):
    if cmd == "subject_options":   # ajax call.
      return self.subject_options()
    else :
      self.addActionError(u"未知的命令参数：" + cmd)
      return ActionResult.ERROR

  def subject_options(self):
    gradeId = self.params.safeGetStringParam("gradeId")
    if gradeId == None or gradeId == "":
      qry = Command(""" SELECT distinct metaSubject as metaSubject FROM Subject """)
    else:
      gradeId = gradeId[0] + "000"
      qry = Command(""" SELECT metaSubject as metaSubject FROM Subject Where metaGradeId =""" + gradeId)
    subjectList = qry.open()
    request.setAttribute("subject_list", subjectList)
    
    return "/WEB-INF/ftl/admin/subject_options_ajax.ftl"
