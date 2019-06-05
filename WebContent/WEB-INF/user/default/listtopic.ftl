<#if topic_list?? >
<ul class='item_ul'>
<#list topic_list as t>
<li>
  <span style='float:right'><a href='${SiteUrl}go.action?userId=${t.createUserId}'>${t.createUserName!?html}</a> ${t.createDate?string('yyyy-MM-dd')}</span>
  <a href='${SiteUrl}mod/topic/show_topic.action?guid=${parentGuid}&amp;type=${parentType}&amp;topicId=${t.plugInTopicId}&unitId=${unitId!}'>${t.title?html}</a>
</li>
</#list>
</ul>
</#if>
<div style='padding:2px;text-align:right;font-weight:bold;'>
<a href='${UserSiteUrl}html/topic_0.html'>更多…</a>
 | <a href='${SiteUrl}mod/topic/new_topic.action?guid=${parentGuid}&amp;type=${parentType}&amp;returl=${returl!?url}&unitId=${unitId!}'>发起讨论</a>
</div>