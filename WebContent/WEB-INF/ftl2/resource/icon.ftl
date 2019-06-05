<div class="picture">
<#assign resource_user = Util.userById(resource.userId)>
  <#if resource_user??>
  <a href="${ContextPath}u/${resource_user.loginName}" class="pictureHead"><img width="50" src="${SSOServerUrl}upload/${resource_user.userIcon!""}" onerror="this.src='${ContextPath}images/default.gif'" alt="${resource_user.trueName}" /></a>
  <p><a href="${ContextPath}u/${resource_user.loginName!}">${resource_user.trueName!}</a> 的资源</p>
  <p><#if UserCate??>${UserCate.name!?html}<#else>默认分类</#if> 下共${syscate_resource_Count!0}个</p>
  </#if>  
</div>