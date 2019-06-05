<#if placard_list??>
<ul class='listul'>
 <#list placard_list as placard>
  <#assign u = Util.userById(placard.userId) >
  <li><a href='${SiteUrl}showPlacard.action?placardId=${placard.id!}' target='_blank' title='发布时间：${placard.createDate!}&#13;发布人:${u.nickName!?html}'>${placard.title!?html}</a> [${placard.createDate?string('yy-MM-dd')}]</li>
 </#list>
</ul>
<div style="clear:both; text-align:right; margin-top:6px;"><a href='${SiteUrl}g/${group.groupName}/py/show_more_group_placard.py'>&gt;&gt; 更多公告</a></div>
</#if>