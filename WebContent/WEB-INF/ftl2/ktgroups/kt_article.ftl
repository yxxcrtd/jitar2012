<!--课题文章 Start-->
<div class="specialLeft border">
    <h3 class="h3Head textIn"><a href="ktgroups.action?act=all&type=article" class="more">更多</a>课题成果-文章</h3>
    <div class="specialList clearfix">
    <#if (article_list??) && (article_list?size > 0) >
      <ul class="ulList">
        <#list article_list as article >
          <li><a title="${article.title!?html}" href='${ContextPath}showArticle.action?articleId=${article.articleId}' target='_blank'>${Util.getCountedWords(article.title!?html,15,1)}</a></li>
        </#list>
      </ul>
    </#if>   
    </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
</div>
<!--课题文章 End-->