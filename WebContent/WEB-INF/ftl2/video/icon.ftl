<div class="picture">
<#assign video_user = Util.userById(video.userId)>
  <#if video_user??>
  <a href="${ContextPath}u/${video_user.loginName}" class="pictureHead"><img width="50" src="${SSOServerUrl}upload/${video_user.userIcon!""}" onerror="this.src='${ContextPath}images/default.gif'" alt="${video_user.trueName}" /></a>
  <p><a href="${ContextPath}u/${video_user.loginName}">${video_user.trueName}</a> 的视频</p>
  </#if>
  <p><#if UserCate??>${UserCate.name!?html}<#else>默认分类</#if> 下共${syscate_video_Count!0}个</p>
</div>