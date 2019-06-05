<div>
<ul>
<#list article_list as article>
  <#assign user = Util.userById(article.userId)>
  <li><a href='#' 
    target='_blank'>${article.title!?html}</a> [${article.createDate?string('MM-dd hh:mm')} ${user.loginName}]</li>
</#list>
</ul>
 <li>subject = ${subject}
 <li>pager = ${pager}
</div>
