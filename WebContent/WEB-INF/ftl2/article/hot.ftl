<div class="contentRelation">
  <h3><a href="${ContextPath}articles.action?type=hot" class="more">更多</a>热门文章排行</h3>
    <ul class="ulList">
    <#if hot_article_list?? && hot_article_list?size &gt;0 >
      <#list hot_article_list as article>
      <li<#if article.title?size &gt; 13> title="${article.title?html}"</#if>><em class="emDate">${article.createDate?string("MM-dd")}</em><span class="numIcon<#if article_index<3> numRed</#if>">${article_index+1}</span><a href="${ContextPath}showArticle.action?articleId=${article.articleId}">${Util.getCountedWords(article.title,14)}</a></li>
      </#list>
    </#if>
    </ul>
</div>