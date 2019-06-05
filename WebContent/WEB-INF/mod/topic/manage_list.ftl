<#if t_list??>
<form method='post'>
<table>
<thead>
<tr>
<th width="17px"><input type='checkbox' id='cxx' onclick="CommonUtil.SelectAll(this,'q_guid')" /></th>
<th width="100%">讨论话题名称</th>
<th><nobr>发起人</nobr></th>
<th><nobr>发起时间</nobr></th>
<th></th>
</tr>
</thead>
<tbody>
<#list t_list as t>
<tr>
<td><input type='checkbox' name='q_guid' value='${t.plugInTopicId}' /></td>
<td><a target='_blank' href='${SiteUrl}mod/topic/show_topic.action?guid=${parentGuid}&type=${parentType}&topicId=${t.plugInTopicId}'>${t.title!?html}</a></td>
<td><nobr><a href='${SiteUrl}go.action?userId=${t.createUserId}'>${t.createUserName?html}</a></nobr></td>
<td><nobr>${t.createDate?string('yyyy/MM/dd')}</nobr></td>
</tr>
</#list>
</tbody>
</table>
<div style='padding:4px'>
<input class='button' type='button' value='全部选中' onclick='document.getElementById("cxx").click();CommonUtil.SelectAll(document.getElementById("cxx"),"q_guid")' />
<input class='button' type='submit' value='删除选中' />
</div>
</form>
<#include "/WEB-INF/ftl/inc/pager.ftl" >
</#if>