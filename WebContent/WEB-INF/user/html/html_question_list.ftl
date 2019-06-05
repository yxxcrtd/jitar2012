<#if q_list??>
<table>
<thead>
<tr>
<th width="100%">问题</th>
<th><nobr>提问人</nobr></th>
<th><nobr>提问时间</nobr></th>
<th></th>
</tr>
</thead>
<tbody>
<#list q_list as q>
<tr>
<td class='list_icon'><a href='${SiteUrl}mod/questionanswer/question_getcontent.action?guid=${parentGuid}&amp;type=${parentType}&amp;qid=${q.questionId}'>${q.topic}</a></td>
<td><nobr><a href='${SiteUrl}go.action?userId=${q.createUserId}'>${q.createUserName?html}</a></nobr></td>
<td><nobr>${q.createDate?string('yyyy/MM/dd')}</nobr></td>
</tr>
</#list>
</tbody>
</table>
</#if>