<div style="height:10px;"></div>
<div class='placardlist'>
<#if placard_list??>
 <ul class="ulist">
  <#list placard_list as placard>
    <li><a href='channel.action?cmd=bulletin&channelId=${channel.channelId}&placardId=${placard.id}' target='_blank'>${placard.title!?html}</a> [${placard.createDate?string('MM-dd hh:mm')}]</li>
  </#list>
</ul>
</#if>
<#if pager??>
 <div class='pager'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>
</#if>
</div>