<#if UserUrlPattern??>
    <#if video_list??>
	<table border='0' width='100%' style='background:#FFF'>
	<tr valign='top'>
	<#list video_list as p>
	<td class='video_list' style='padding:8px 0'>
	<div style='text-align:center;height:120px;background:#EEE'>
	<a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}'><img src='${p.flvThumbNailHref!?html}' border='0' /></a>
	</div>
	<div style='padding-top:4px;text-align:center'><a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}'>${p.title}</a></div>
	</td>
	</#list>
	</tr>
	</table>
	<#if video_list.size() &gt; 0 >
	<div style='text-align:right'><a href='${UserUrlPattern.replace('{loginName}',video_list[0].loginName)}videocate/0.html'>&gt;&gt;全部视频</a></div>
	</#if>
	</#if>
<#else>
    <#if video_list??>
	<table border='0' width='100%' style='background:#FFF'>
	<tr valign='top'>
	<#list video_list as p >
	<td class='video_list' style='padding:8px 0'>
	<div style='text-align:center;height:120px;background:#EEE'>
	<a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}'><img src='${p.flvThumbNailHref!?html}' border='0' /></a>
	</div>
	<div style='padding-top:4px;text-align:center'><a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}'>${p.title}</a></div>
	</td>
	</#list>
	</tr>
	</table>
	<#if video_list.size() &gt; 0 >
	<div style='text-align:right'><a href='${SiteUrl}${video_list[0].loginName}/videocate/0.html'>&gt;&gt;全部视频</a></div>
	</#if>
	</#if>
</#if>