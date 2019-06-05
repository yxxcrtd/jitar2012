<!--右边最上边-->
<div class="picture">
  <#if photoAuthor??>
  <a href="${ContextPath}u/${photoAuthor.loginName}" class="pictureHead"><img width="50" src="${SSOServerUrl}upload/${photoAuthor.userIcon!""}" onerror="this.src='${ContextPath}images/default.gif'" alt="${photoAuthor.trueName}" /></a>
  <p><a href="${ContextPath}u/${photoAuthor.loginName}">${photoAuthor.trueName}</a> 的图片</p>
  <p><#if UserCate??>${UserCate.name!?html}<#else>默认分类</#if> 下共${UserCateCount!0}个</p>
  </#if>
</div>