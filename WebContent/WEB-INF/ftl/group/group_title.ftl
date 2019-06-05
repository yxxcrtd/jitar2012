<#if group??>
    <#assign grpName="协作组">
    <#assign grpShowName="小组">
    <#if isKtGroup??>
        <#if isKtGroup=="1">
            <#assign grpName="课题组"> 
            <#assign grpShowName="课题">
        <#elseif isKtGroup=="2">
            <#assign grpName="备课组"> 
            <#assign grpShowName="小组">
        <#else>
            <#assign grpName="协作组">
            <#assign grpShowName="小组">
        </#if>
    </#if>
	<table border="0" width="100%" cellspacing="0" cellpading="0">
		<tr>
			<td width="50" align="center">
				<a href="${SiteUrl}go.action?groupId=${group.groupId}" title="${group.groupTitle!?html}" target="_blank">
					<img src="${Util.url(group.groupIcon!SiteUrl + "images/group_default.gif")}" border="0" width="60" />
				</a>
			</td>
			<td valign="top" align="left">
				<div style="font-size: 24px; font-weight:bold; margin-bottom:4px;">
					<a href="${SiteUrl}go.action?groupId=${group.groupId}" target="_blank" title="点击进入${group.groupTitle!?html}首页">${group.groupTitle!?html}</a>
					&nbsp;
					<a href="${SiteUrl}go.action?groupId=${group.groupId}" target="_blank" title="点击进入${group.groupTitle!?html}首页">进入${grpName}首页</a>
					&nbsp;
					<#if group.parentId&gt;0>
					<a href="${SiteUrl}go.action?groupId=${group.parentId}" target="_blank">进入主课题组首页</a>
					</#if>				
				</div>
				<br />
				<div>
					${grpName}简介：<font style="font-size: 14px; font-weight: bold;">${group.groupIntroduce!}</font>
				</div>
			</td>
		</tr>
	</table>
</#if>
