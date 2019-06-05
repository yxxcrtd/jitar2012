<#if placard_list??>
<ul class="ulist">
<#list placard_list as p>
<li><span>${p.createDate?string("yyyy-MM-dd")}</span> <a href="channel.action?cmd=bulletin&channelId=${channel.channelId}&placardId=${p.id}">${Util.getCountedWords(p.title!?html,15)}</a></li>
</#list>
</ul>
</#if>