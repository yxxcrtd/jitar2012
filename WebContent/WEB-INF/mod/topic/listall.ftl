<#if topic_list?? >
<table class="lastlist" cellspacing="1" style='width:100%'> 
  <tr> 
  <th style='font-weight:bold;width:100%'>讨论话题名称</th> 
  <th style='font-weight:bold'><nobr>创建人</nobr></th> 
  <th style='font-weight:bold'><nobr>创建时间</nobr></th>
  </tr>
  <#list topic_list as t>	
  <tr>
  <td><a href='${SiteUrl}mod/topic/show_topic.action?guid=${parentGuid}&amp;type=${parentType}&topicId=${t.plugInTopicId}'>${t.title?html}</a></td> 
  <td><nobr><a href='${SiteUrl}go.action?userId=${t.createUserId}'>${t.createUserName!?html}</a></nobr></td> 
  <td><nobr>${t.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td> 
  </tr> 
  </#list>
</table>
<#include '/WEB-INF/ftl/inc/pager.ftl'>
</#if>