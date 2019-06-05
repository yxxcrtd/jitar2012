<div class="picture">
  <#if articleAuthor??>
  <a href="${ContextPath}u/${articleAuthor.loginName}" class="pictureHead"><img width="50" src="${SSOServerUrl}upload/${articleAuthor.userIcon!""}" onerror="this.src='${ContextPath}images/default.gif'" alt="${articleAuthor.trueName}" /></a>
  <p><a href="${ContextPath}u/${articleAuthor.loginName}">${articleAuthor.trueName}</a> 的文章</p>
  <p><#if UserCate??><a href="${ContextPath}u/${articleAuthor.loginName}/category/${UserCate.id}.html">${UserCate.name!?html}</a><#else>默认分类</#if> 下共${UserCateArticleCount!0}个</p>
  </#if>
</div>