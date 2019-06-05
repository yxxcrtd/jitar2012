<#if UserUrlPattern??>
    <#if photo_list??>
	<table border='0' width='100%' style='background:#FFF'>
	<tr valign='top'>
	<#list photo_list as p >
	<td class='photo_list' style='padding:8px 0'>
	<div style='text-align:center;height:120px;background:#EEE'>
	<a href='${UserUrlPattern.replace('{loginName}',p.loginName)}photo/${p.photoId}.html'><img style="width:105px;height:120px" src='${Util.thumbNails(p.href!)}' border='0' /></a>
	</div>
	<div style='padding-top:4px;text-align:center'><a href='${UserUrlPattern.replace('{loginName}',p.loginName)}photo/${p.photoId}.html'>${Util.getCountedWords(p.title,10)}</a></div>
	</td>
	</#list>
	</tr>
	</table>
	<#if photo_list.size() &gt; 0 >
	<div style='text-align:right'><a href='${UserSiteUrl}py/user_photo_list.py'>&gt;&gt;全部照片</a></div>
	</#if>
	</#if>
<#else>
    <#if photo_list??>
	<table border='0' width='100%' style='background:#FFF'>
	<tr valign='top'>
	<#list photo_list as p >
	<td class='photo_list' style='padding:8px 0'>
	<div style='text-align:center;height:120px;background:#EEE'>
	<a href='${SiteUrl}${p.loginName}/photo/${p.photoId}.html'><img style="width:105px;height:120px" src='${Util.thumbNails(p.href!)}' border='0' /></a>
	</div>
	<div style='padding-top:4px;text-align:center'><a href='${SiteUrl}${p.loginName}/photo/${p.photoId}.html'>${Util.getCountedWords(p.title,10)}</a></div>
	</td>
	</#list>
	</tr>
	</table>
	<#if photo_list.size() &gt; 0 >
	<div style='text-align:right'><a href='${UserSiteUrl}py/user_photo_list.py'>&gt;&gt;全部照片</a></div>
	</#if>
	</#if>
</#if>