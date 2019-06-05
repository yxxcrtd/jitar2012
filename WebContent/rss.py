from article_query import ArticleQuery
from resource_query import ResourceQuery
from photo_query import PhotoQuery
from bbs_query import TopicQuery
from cn.edustar.jitar.util import CommonUtil 
from java.util import Date 

class rss:
  def __init__(self):
    pass

  def execute(self):
    request.setAttribute("today", CommonUtil.rssDateFormat(Date()))
    type = request.getParameter("type")
    if type == "article":
        return self.article()
    elif type == "resource":
        return self.resource()
    elif type == "photo":
        return self.photo()
    elif type == "topic":
        return self.topic()
    else:
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/site_rss.ftl"
          
  def article(self):
      qry = ArticleQuery(""" a.articleId, a.title, a.createDate, u.loginName, u.trueName, u.userIcon, subj.subjectName,
                           a.articleAbstract, a.articleContent, a.lastModified                           
                            """)
      userId = request.getParameter("userId")
      
      if(request.getParameter("userId") != None):
          qry.userId = int(userId)
          request.setAttribute("showType", "user")
                 
      qry.orderType = 0
      result = qry.query_map(20)
      #print "result = ", result
      for a in result:
          str = a['lastModified']
          str = CommonUtil.rssDateFormat(str)
          a['lastModifiedString'] = str      

                
      request.setAttribute("article_list", result)
      response.setContentType("text/xml; charset=UTF-8")
      return "/WEB-INF/ftl/site_rss_article.ftl"
  
  def resource(self):
      qry = ResourceQuery(""" r.resourceId, r.title, r.createDate, u.trueName,
                            u.loginName, u.userIcon, subj.subjectName,
                           r.summary, r.lastModified                           
                            """)
      userId = request.getParameter("userId")      
      if(request.getParameter("userId") != None):
          qry.userId = int(userId)
          request.setAttribute("showType", "user")
          
      qry.orderType = 0
      result = qry.query_map(20)
      
      #print "result = ", result
      #return
      for a in result:
          str = a['lastModified']
          str = CommonUtil.rssDateFormat(str)
          a['lastModifiedString'] = str         

                
      request.setAttribute("resource_list", result)
      response.setContentType("text/xml; charset=UTF-8")
      return "/WEB-INF/ftl/site_rss_resource.ftl"
  
  def photo(self):
      qry = PhotoQuery(""" p.photoId, p.title, p.createDate, p.lastModified, p.href,
                          u.loginName, u.userIcon, u.trueName, sc.name                                                     
                            """)
      userId = request.getParameter("userId")      
      if(request.getParameter("userId") != None):
          qry.userId = int(userId)
          request.setAttribute("showType", "user")
    
      qry.orderType = 0
      result = qry.query_map(20)
      #print "result = ", result
      #return
      for a in result:
          str = a['lastModified']
          str = CommonUtil.rssDateFormat(str)
          a['lastModifiedString'] = str         

                
      request.setAttribute("photo_list", result)
      
      response.setContentType("text/xml; charset=UTF-8")
      return "/WEB-INF/ftl/site_rss_photo.ftl"
  
  def topic(self):
      qry = TopicQuery(""" t.topicId, t.title, t.createDate, t.groupId, t.content,
                            u.loginName, u.userIcon, u.trueName, g.groupName                                                   
                            """)
      userId = request.getParameter("userId")      
      if(request.getParameter("userId") != None):
          qry.userId = int(userId)
          request.setAttribute("showType", "user")
          
      qry.isDeleted = 0
      qry.orderType = 0    
      result = qry.query_map(20)
      #print "result = ", result
      #return
      for a in result:
          str = a['createDate']
          str = CommonUtil.rssDateFormat(str)
          a['createDateString'] = str      
            
      request.setAttribute("topic_list", result)
      response.setContentType("text/xml; charset=UTF-8")
      return "/WEB-INF/ftl/site_rss_topic.ftl"