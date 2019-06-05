from cn.edustar.jitar.util import CommonUtil, ParamUtil, IPSeeker
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.service import ViewCountService
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.model import Configure
from cn.edustar.jitar.util import ProductInfo
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import EncryptDecrypt
from cn.edustar.jitar import JitarRequestContext
from site_config import SiteConfig
from site_news_query import SiteNewsQuery
from article_query import ArticleQuery, UserArticleQuery
from resource_query import ResourceQuery
from comment_query import CommentQuery
from action_query import ActionQuery
from user_query import UserQuery
from group_query import GroupQuery
from base_specialsubject_page import SpecialSubjectQuery
from bbs_query import TopicQuery
from photo_query import PhotoQuery
from video_query import VideoQuery
from tag_query import TagQuery
from base_preparecourse_page import *
from base_specialsubject_page import *
from placard_query import PlacardQuery
from java.lang import *
from java.io import File, FileWriter
from java.text import SimpleDateFormat
from java.util import Date

from net.zdsoft.passport.service.client import PassportClient

class SiteFactory:
    params = ParamUtil(request)
    configService = __jitar__.configService
    productConfigService = __spring__.getBean("ProductConfigService")
    viewCountService = __spring__.getBean("viewCountService")
    articleService = __spring__.getBean("articleService")    
    unitService = __spring__.getBean("unitService")
      
    def GenernateIndex(self):
        fileIndex = JitarRequestContext.getRequestContext().getServletContext().getRealPath("/")
        fileIndex = fileIndex + "index.html"
        try:
            file = File(fileIndex)
            fw = FileWriter(file, False)
            fw.write("<!doctype html>")          
            fw.write("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"><title></title>")
            fw.close()
        finally:
            fw = None
            
        
        
        print fileIndex
        
    
    
    
    
