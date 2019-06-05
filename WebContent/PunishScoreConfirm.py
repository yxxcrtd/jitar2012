from cn.edustar.jitar.pojos import UPunishScore
from cn.edustar.jitar.data import Command
from base_action import *


class PunishScoreConfirm:
  cfg_svc = __jitar__.configService
  punish_svc=__jitar__.UPunishScoreService
  
  def __init__(self):
    self.params = ParamUtil(request)  
    return

  def execute(self):
    self.config = self.cfg_svc.getConfigure()
    seltype = self.params.getStringParam("seltype")
    #config['score.comment.adminDel']
    request.setAttribute("score", self.config[seltype])
    # 返回
    response.contentType = "text/html; charset=UTF-8"
    return "/WEB-INF/common/PunishScoreConfirm.ftl"

