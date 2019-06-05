 <#if article_list??>
 <#list article_list as article>
  <li<#if article_index % 2 == 1> class="liBg"</#if>>
      <span class="listWa1"><#if article.typeState == false>[原创]<#else>[转载]</#if><a href="${ContextPath}showArticle.action?articleId=${article.articleId}">${Util.getCountedWords(article.title!?html,16)}</a></span>
      <span class="listWa2"><a href="${ContextPath}go.action?userId=${article.userId}">${Util.getCountedWords(article.userTrueName!?html,3)}</a></span>
      <span class="listWa3">${article.createDate?string("yyyy/MM/dd")}</span>
      <span class="listWa4">${article.viewCount}</span>
      <span class="listWa5">${article.digg!0}</span>
      <span class="listWa6">${article.trample!0}</span>
      <span class="listWa7">${article.commentCount!0}</span>
  </li>
 </#list>
 </#if>
<div class="listPage clearfix" id="__pager">${HtmlPager}</div>