<ul>
    <li class="listContTitle">
        <div class="listContTitleBg">
            <span class="listWa1" style="width:360px">标题</span>
            <span class="listWa2">作者</span>
            <span class="listWa3">发表日期</span>
            <span class="listWa4">点击率</span>
            <#--<span class="listWa5">顶数</span>
            <span class="listWa6">踩数</span>-->
            <span class="listWa7">评论数</span>
        </div>
    </li>
<#if article_list?? && article_list?size &gt; 0 >
<#list article_list as article>
  <li<#if article_index % 2 == 1> class="liBg"</#if>>
      <span class="listWa1" style="width:360px"><#if article.typeState == false>[原创]<#else>[转载]</#if> <a href="${ContextPath}showArticle.action?articleId=${article.articleId}"<#if article.title?length &gt; 23> title="${article.title}"</#if>>${Util.getCountedWords(article.title!?html,24)}</a></span>
      <span class="listWa2"><a <#if article.userTrueName?length &gt; 3> title="${article.userTrueName!?html}"</#if> href="${ContextPath}go.action?userId=${article.userId}">${Util.getCountedWords(article.userTrueName!?html,3)}</a></span>
      <span class="listWa3">${article.createDate?string("yyyy/MM/dd")}</span>
      <span class="listWa4">${article.viewCount}</span>
      <span class="listWa7">${article.commentCount!0}</span>
  </li>
</#list>
<#else>
<li>无数据返回。</li>
</#if>
</ul>
<div class="listPage clearfix">${HtmlPager}</div>