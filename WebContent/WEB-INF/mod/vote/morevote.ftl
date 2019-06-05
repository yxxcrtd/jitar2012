<#if vote_list?? >
<table style='width:100%'>
<tr>
<th>调查名称</th>
<th>发起时间</th>
<th>截止时间</th>
</tr>
<#list vote_list as vl>
<tr>
<td style="width: 100%;" class="list_icon"><a href='getcontent.action?guid=${parentGuid}&type=${parentType}&voteId=${vl.voteId}'>${vl.title}</a></td>
<td><nobr>${vl.createDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr>${vl.endDate?string('yyyy-MM-dd')}</nobr></td>
</tr>
</#list>
</table>
<#include ('../pager.ftl')>
</#if>