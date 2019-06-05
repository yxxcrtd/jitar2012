<#if q_list??>
<form method='post'>
<table>
<thead>
<tr>
<th width="17px"><input type='checkbox' id='cxx' onclick="CommonUtil.SelectAll(this,'q_guid')" /></th>
<th width="100%">问题</th>
<th><nobr>提问人</nobr></th>
<th><nobr>提问时间</nobr></th>
<th></th>
</tr>
</thead>
<tbody>
<#list q_list as q>
<tr>
<td><input type='checkbox' name='q_guid' value='${q.questionId}' /></td>
<td><a href='${SiteUrl}mod/questionanswer/question_getcontent.action?guid=${parentGuid}&amp;type=${parentType}&amp;qid=${q.questionId}'>${q.topic}</a></td>
<td><nobr><a href='${SiteUrl}go.action?userId=${q.createUserId}'>${q.createUserName?html}</a></nobr></td>
<td><nobr>${q.createDate?string('yyyy/MM/dd')}</nobr></td>
</tr>
</#list>
</tbody>
</table>
<div style='padding:4px'>
<input class='button' type='button' value='全部选中' onclick='document.getElementById("cxx").click();CommonUtil.SelectAll(document.getElementById("cxx"),"q_guid")' />
<input class='button' type='submit' value='删除选中' />
</div>
</form>
<#include ('pager.ftl') >
</#if>