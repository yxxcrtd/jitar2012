<div class="contentRelation">
  <h3><a href="${ContextPath}articles.action?type=new" class="more">更多</a>最新文章</h3>
    <ul class="ulList">
    <#if new_article_list?? && new_article_list?size &gt;0 >
      <#list new_article_list as article>
      <li<#if article.title?size &gt; 13> title="${article.title?html}"</#if>><em class="emDate">${article.createDate?string("MM-dd")}</em>[<#if article.typeState == false>原创<#else>转载</#if>] <a href="${ContextPath}showArticle.action?articleId=${article.articleId}">${Util.getCountedWords(article.title,14)}</a></li>
      </#list>
    </#if>
    </ul>
</div>