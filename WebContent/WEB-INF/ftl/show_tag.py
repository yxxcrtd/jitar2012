# script 

from cn.edustar.jitar.ui import ShowTagAction
from cn.edustar.jitar.data import *

# 数据获取脚本.
class show_tag(ShowTagAction):
  def execute(self):
    #print "show_tag 脚本执行"

    # 使用此标签的用户/博客/工作室.
    tag_user_list = TagUserBean()
    tag_user_list.varName = "tag_user_list"
    self.addBean(tag_user_list)
    
    # 使用此标签的协作组.
    tag_group_list = TagGroupBean()
    tag_group_list.varName = "tag_group_list"
    self.addBean(tag_group_list)
    
    # 使用此标签的文章.
    tag_article_list = TagArticleBean()
    tag_article_list.varName = "tag_article_list"
    self.addBean(tag_article_list)

    # 使用此标签的资源.
    tag_resource_list = TagResourceBean()
    tag_resource_list.varName = "tag_resource_list"
    self.addBean(tag_resource_list)
    
    return "success"
