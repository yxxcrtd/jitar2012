<#if friend_list?? >
<table  style='width:600px'>
<#list friend_list as u >
  <#if (u.trueName??)><#assign userName = u.trueName >
  <#elseif (u.nickName??)><#assign userName = u.nickName >
  <#elseif (u.loginName??)><#assign userName = u.loginName >
  </#if>
<tr>
<#if UserUrlPattern??>
<td style='width:36px'>
    <a href="${UserUrlPattern.replace('{loginName}',u.loginName)}" onmouseover="ToolTip.showUserCard(event,'${u.loginName}','${userName}','${u.userIcon!}','${u.qq?html}');">
    <img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='32' height='32' border='0' alt="${u.nickName?html}" /></a>
 </td>
<td>
<a href="${UserUrlPattern.replace('{loginName}',u.loginName)}">${u.nickName!?html}</a>
</td>
<#else>
<td style='width:36px'>
    <a href="${SiteUrl}${u.loginName}" onmouseover="ToolTip.showUserCard(event,'${u.loginName}','${userName}','${u.userIcon!}','${u.qq?html}');">
    <img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='32' height='32' border='0' alt="${u.nickName?html}" /></a>
 </td>
<td>
<a href="${SiteUrl}${u.loginName}">${u.nickName!?html}</a>
</td>
</#if>

<td align='right'>加入时间：${u.addTime!?html}
</td>
</tr>
</#list>
</table>
</#if>