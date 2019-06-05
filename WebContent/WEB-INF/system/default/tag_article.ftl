<div>
<ul>
<#list article_list as article>
  <li><a href='${SiteUrl}s/article/${article.articleId}.html' 
    target='_blank'>${article.title!?html}</a> [${article.createDate?string('MM-dd hh:mm')}]</li>
</#list>
</ul>
</div>

<h3>DEBUG</h3>
<li>tag = ${tag}
<li>pager = ${pager!}
