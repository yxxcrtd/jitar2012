<div style="text-align:center;">
    <div style="text-align:center;">
	<img src="${SSOServerUrl +'upload/'+ user.userIcon!''}" onerror="this.src='${SiteUrl}images/default.gif';" title='${user.trueName!}' width='128' border='0' />
	</div>
		
	<#if user.userType??>
	<div style="padding:4px 0;text-align:center;font-weight:bold;color:#f00">
	<#assign showTypeName = Util.typeIdToName(user.userType) >
    <#if showTypeName??>
        <#list showTypeName?split("/") as x>
        <#if (x?length) &gt; 0 >${x} </#if>
        </#list> 
    </#if>
	</div>
	</#if>
	<div style="text-align: center; padding: 4px;">
		${user.trueName}
		<#if user.positionId == 1>系统管理员
		<#elseif user.positionId == 2>机构管理员
		<#elseif user.positionId == 3>教师
		<#elseif user.positionId == 4>教育局职工
		<#elseif user.positionId == 5>学生
		</#if>
		<#if user.qq??>
		<#if user.qq!="">
		<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${user.qq!?html}&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:${user.qq!?html}:45" align="absmiddle" alt="QQ" title="QQ"></a>
		</#if>
		</#if>
	</div>
</div>
<div style="text-align:center;line-height:200%">

<a class="linkButton icoBlog" href='${UserSiteUrl}'>个人空间</a>
<a class="linkButton icoProfile" href='${UserSiteUrl}profile'>查看档案</a>

<br/>
<a class="linkButton icoFriend"  href='${SiteUrl}manage?url=friend.action%3Fcmd%3Dadd' onclick="LoginUI.checkLogin('${user.loginName!?js_string}','friend');return false;">加为好友</a>
<a class="linkButton icoMessage" href='${SiteUrl}manage?url=message.action%3Fcmd%3Dwrite' onclick="LoginUI.checkLogin('${user.loginName!?js_string}','message');return false;">发送消息</a>

<br/>
<a class="linkButton icoRss"  href='${SiteUrl}rss.py?type=article&amp;userId=${user.userId}'>订阅文章</a>
<a class="linkButton icoRss"  href='${SiteUrl}rss.py?type=resource&amp;userId=${user.userId}'>订阅资源</a>
<br/>
<a class="linkButton icoRss"  href='${SiteUrl}rss.py?type=photo&amp;userId=${user.userId}'>订阅图片</a>
<a class="linkButton icoRss"  href='${SiteUrl}rss.py?type=topic&amp;userId=${user.userId}'>订阅话题</a>
<br />
<#if ("0" != Util.isOpenMeetings())>
	<#if (1 == Util.isVideoUser(user.userId))>
		<a class="linkButton icoVideo" href="${Util.isOpenMeetings()}&amp;user=${user.loginName!}" target="_blank">进入个人会议室</a>
	</#if>
</#if>
</div>
