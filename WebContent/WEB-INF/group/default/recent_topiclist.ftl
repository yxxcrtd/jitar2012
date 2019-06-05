<ul class="listul">
<#list recent_topiclist as topic>
	<li><a href='${SiteUrl}g/${group.groupName}/py/showGroupTopic.py?groupId=${group.groupId}&amp;topicId=${topic.topicId}'
	  target='_blank'>${topic.title!?html}</a></li>
</#list>
</ul>
<div style='text-align:right'><#if loginUser??><a href='${SiteUrl}g/${group.groupName}/py/addGroupTopic.py?groupId=${group.groupId}'>发布主题</a> |</#if> <a href='${SiteUrl}g/${group.groupName}/py/listGroupTopic.py?groupId=${group.groupId}'>全部主题</a></div>