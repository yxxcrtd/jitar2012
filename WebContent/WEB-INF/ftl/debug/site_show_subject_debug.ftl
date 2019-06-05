<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 </head>
 <body>
  <h2>showSubject.py 调试页面</h2>
  
  <h3>当前学科</h3>
  <ul>
    <li>subject = ${subject}</li>
  </ul>

  <h3>学科博客(用户)</h3>
  <#if subj_user_list?? >
    <ul> 
      <#list subj_user_list as user>
        <li><a href='${SiteUrl}${user.loginName}'>${user.nickName}</a></li>
      </#list>
    </ul>
  </#if>
  <div><a href='showSubjectUser.action?subjectId=${subject.subjectId}'>点击查看更多...</a></div>
  
  <h3>学科协作组</h3>
  <#if subj_group_list?? >
    <ul>
      <#list subj_group_list as group>
        <li><a href='${SiteUrl}g/${group.groupName}'>${group.groupTitle}</a></li>
      </#list>
    </ul>
  </#if>
  <div><a href='showSubjectGroup.action?subjectId=${subject.subjectId}'>点击查看更多...</a></div>

  <h3>学科文章</h3>
  <#if subj_article_list?? >
    <ul>
      <#list subj_article_list as article>
        <li><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}'>${article.title}</a></li>
      </#list>
    </ul>
  </#if>
  <div><a href='showSubjectArticle.py?subjectId=${subject.subjectId}'>点击查看更多...</a></div>
  
  <h3>学科资源</h3>
  <#if subj_resource_list??>
    <ul>
      <#list subj_resource_list as resource>
        <li><a href='#'>${resource.title}</a></li>
      </#list>
    </ul>
  </#if>
  <div><a href='showSubjectResource.action?subjectId=${subject.subjectId}'>点击查看更多...</a></div>
  
 </body>
</html>
