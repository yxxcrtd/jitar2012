<#if video_list??>
<ul class="ulist">
<#list video_list as v>
<li>
<span><a href="${SiteUrl}go.action?loginName=${v.loginName}" title="${v.userTrueName!?html}">${Util.getCountedWords(v.userTrueName!?html,4)}</a> ${v.createDate?string("MM-dd")}</span>
<a href='${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}' target='_blank' title='${v.title!?html}'>${Util.getCountedWords(v.title!?html,26)}</a></li>
</#list>
</ul>
</#if>