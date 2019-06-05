# -*- coding: UTF-8 -*-
# coding=UTF-8
# script 

from java.util import ArrayList
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import User
from org.apache.commons.logging import Log
from org.apache.commons.logging import LogFactory

# 模块文件由于编码问题, 字符串中暂时不能用中文, 该问题有待解决.
# print "模块文件由于编码问题, 字符串中暂时不能用中文, 该问题有待解决."

# 定义常用返回值.
class ActionResult:
  ERROR = "/WEB-INF/ftl/Error.ftl"
  ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl"
  ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
  SUCCESS = "/WEB-INF/ftl/success.ftl"
  LOGIN = "/login.jsp"
  def __donothing__():
    return True


# 什么功能都不添加的 BaseAction, 提供给其它页面用做基类.
class BaseAction(JythonBaseAction, ActionResult):
  def getRequestURI(self):
    uri = request.getAttribute('javax.servlet.forward.request_uri')
    if uri != None and uri != "":
      return uri
    uri = request.requestURI
    return uri
  
  def getImpersonateUser(self):
    userGuid = session.getAttribute("platuser")
    if None == userGuid or userGuid == "":
      return None
    u = User()
    u.setUserGuid(userGuid)
    u.setLoginName("LogonedUser")
    u.setTrueName("平台模拟帐户")
    u.setNickName("平台模拟帐户")
    return u

  pass

# 重载了 execute() 并进行一些预处理和后处理的 action 基类.
class ActionExecutor(JythonBaseAction, ActionResult):
  def __init__(self):
    self.params = ParamUtil(request)
    self.logger = LogFactory.getLog(self.__class__);
    
    
  def execute(self):
    # 得到命令, 并调用 dispatcher() 进行命令派发.
    cmd = self.params.getStringParam("cmd")    
    result = self.dispatcher(cmd)
    
    # 如果没有任何链接加入到 actionLinks 中, 则添加一个缺省的.
    if not self.hasActionLinks():
      self.addDefaultReturnActionLink()
    return result
  
  
  # 给用户提示未知命令 cmd. 
  def unknownCommand(self, cmd='unknown'):
    self.addActionError("Unknown Command : " + cmd)
    return ActionResult.ERROR
  

# 支持学科辅助 mixiner.
class SubjectMixiner:
  def putSubjectList(self):
    subject_list = self._get_subj_svc().getMetaSubjectList()
    request.setAttribute("subject_list", subject_list)
  
  # 将当前学科包括元学科、元学段放入 self, request.
  def putSubject(self):
    self.subjectId = self.params.getIntParam("subjectId")
    self.gradeId = self.params.getIntParam("gradeId")
    
    request.setAttribute("subjectId", self.subjectId)
    request.setAttribute("gradeId", self.gradeId)
    
    # 此处进行了修改，return 之后的为原来的，获取对象的方法不同    
    self.subject = self._get_subj_svc().getSubjectByMetaData(self.subjectId, self.gradeId)
    #print "Subject:", self.subject
    
    self.grade = self._get_subj_svc().getGrade(self.gradeId)
    request.setAttribute("grade", self.grade)
    
    if self.subject != None:
      request.setAttribute("subject", self.subject)
      request.setAttribute("metaSubject", self.subject.metaSubject)
      request.setAttribute("metaGrade", self.subject.metaGrade)
      return
    
    return


    self.subject = self._get_subj_svc().getSubjectById(self.subjectId)
    if self.subject != None:
      request.setAttribute("subject", self.subject)
      request.setAttribute("metaSubject", self.subject.metaSubject)
      request.setAttribute("metaGrade", self.subject.metaGrade)
      
  def putGradeListByGradeId(self,gradeId):
    grade_list = self._get_subj_svc().getGradeLevelListByGradeId(gradeId)
    request.setAttribute("grade_list", grade_list)
    
  def putGradeList(self):
    grade_list = self._get_subj_svc().getGradeList()
    request.setAttribute("grade_list", grade_list);
    
  def _get_subj_svc(self):
    return __jitar__.subjectService
  
  # 设置查询时显示学科限定条件, 其将被分解为 metaSubject 和 metaGrade, 派生类必须支持才可实现.
  def setSubjectCondition(self, subject):
    if subject == None:
      self.metaSubjectId = 0
      self.metaGradeId = 0
    else:
      self.metaSubjectId = subject.metaSubject.msubjId
      self.metaGradeId = subject.metaGrade.gradeId
    return
  
  # 将学段格式化成整数，例如: 3100 -》 3000
  def convertRoundMinNumber(self, intV):
    if intV == None:
      return 0    
    strV = str(intV)
    if strV.isdigit() == False:
      return 0    
    intStrLen = len(strV)        
    if intStrLen < 2:
      return intV
    strPad = "0"
    for i in range(2, intStrLen):
      strPad = strPad + "0"
    
    strV = strV[0:1] + strPad
    return int(strV)
    
  # 将学段格式化成整数，例如: 3100 -》 4000
  def convertRoundMaxNumber(self, intV):
    if intV == None:
      return 0    
    strV = str(intV)
    if strV.isdigit() == False:
      return 0    
    intStrLen = len(strV)        
    if intStrLen < 2:
      return intV
    strPad = "0"
    for i in range(2, intStrLen):
      strPad = strPad + "0"
    strV = str(int(strV[0:1]) + 1) + strPad
    return int(strV)

   
  # 计算指定学段的开始 id, 一般等于 gradeId, 参见 Grade.startId.
  def calcGradeStartId(self, gradeId):
    return self.convertRoundMinNumber(gradeId)
    #以下是老算法
    return gradeId

  # 计算指定学段的结束 id, 一般等于 gradeId + 10**, 参见 Grade.endId.
  def calcGradeEndId(self, gradeId):
    return self.convertRoundMaxNumber(gradeId)

    # 计算方法？？？以下是以前的方法
    if (gradeId % 10000) == 0:
      return gradeId + 10000
    if (gradeId % 1000) == 0:
      return gradeId + 1000
    if (gradeId % 100) == 0:
      return gradeId + 100
    if (gradeId % 10) == 0:
      return gradeId + 10
    return gradeId + 1

# 一个空函数, 用于 BusinessBatcher 指定某个方法不用实际执行.
def empty_func(*args, **kw):
  return True
  
# 批量执行某个业务的辅助类.
# 每次批处理包括

# initer(batcher)
#   data = get_data(task, batcher)
#   check_logic(data, batcher)
#   check_right(data, batcher)
#   do_business(data, batcher)
#   do_log(data, batcher)
# finisher(batcher)
class BusinessBatcher:
  def __init__(self, initer=empty_func, finisher=empty_func):
    self.initer = initer        # 批任务初始化.
    self.finisher = finisher    # 批任务结束.
    
    self.get_data = None            # 获取数据.
    self.check_logic = empty_func   # 检测逻辑.
    self.check_right = empty_func   # 检测权限.
    self.do_business = empty_func   # 执行业务.
    self.do_log = empty_func        # 成功执行之后记录日志.
    
    self.operate = "指定操作"
    self.taskList = []
    self.result = None          # 执行结果, 由调用者使用.
    self.success = False        # 是否完全执行成功.
    self.count = 0              # 成功进行的操作数. 

  
  # 开始批处理.
  def execute(self):
    # 初始化任务.
    if not self.initer(self):
      return False
    
    # 执行每个任务.
    try:
      for task in self.taskList:
        # 如果任务返回 false, 则中断批任务.
        if self.executeTask(task) == False:
          break
        
      self.success = True
    finally:
      # 结束任务.
      self.finisher(self)
      
    return self.success

  # 执行某个任务.
  def executeTask(self, task):
    # return self.executor(self, task)
    data = self.get_data(task, self)
    if data == None: 
      return
    if self.check_logic(data, self) == False: 
      return
    if self.check_right(data, self) == False: 
      return
    if self.do_business(data, self) == False: 
      return
    self.do_log(data, self)