<div style='clear:both;'>
<#if !(count??)><#assign count = 10 ></#if>
<#if friend_list??>
<#list friend_list as friend>
    <div class='frd'>
        <div style='text-align:center'>
            <table border='0' cellpadding='0' cellspacing='0' align='center'>
            <tr valign='top'>
            <#if (friend.trueName??)><#assign userName = friend.trueName >
            <#else><#assign userName = friend.loginName >
            </#if>
            <td class='imgborder'>
            <#if UserUrlPattern??>
            <a href="${UserUrlPattern.replace('{loginName}',friend.loginName)}" onmouseover="ToolTip.showUserCard(event,'${friend.loginName}','${userName}','${SSOServerUrl +"upload/"+friend.userIcon!'images/default.gif'}','${friend.qq!?html}');">
            <img src="${SSOServerUrl +'upload/'+friend.userIcon!'images/default.gif'}" onerror="this.src='${SiteUrl}images/default.gif'" width='48' height='48' border='0' alt="${friend.trueName?html}" /></a>
			<#else>
            <a href="${SiteUrl}${friend.loginName}" onmouseover="ToolTip.showUserCard(event,'${friend.loginName}','${userName}','${SSOServerUrl +"upload/"+friend.userIcon!'images/default.gif'}','${friend.qq!?html}');">
            <img src="${SSOServerUrl +'upload/'+friend.userIcon!'images/default.gif'}" onerror="this.src='${SiteUrl}images/default.gif'" width='48' height='48' border='0' alt="${friend.trueName?html}" /></a>
            </#if>
            </td>
            </tr>
            </table>
            <#if UserUrlPattern??>
            <div style='text-align:center;padding:6px 0;'><a href="${UserUrlPattern.replace('{loginName}',friend.loginName)}">${friend.trueName}</a></div>
            <#else>
            <div style='text-align:center;padding:6px 0;'><a href="${SiteUrl}go.action?loginName=${friend.loginName}">${friend.trueName}</a></div>
            </#if>
        </div>
    </div>
</#list>
</#if>
<div style='clear:both;text-align:right;'><a href='${UserSiteUrl}py/user_friend_list.py'>&gt;&gt;全部好友</a></div>
</div>