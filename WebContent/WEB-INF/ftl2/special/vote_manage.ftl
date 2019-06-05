<#if vote_list?? >
<form method='post'>
<table class="moreTable">
<thead>
<tr>
<th width="5%"><input type='checkbox' id='cxx' onclick="CommonUtil.SelectAll(this,'q_guid')" /></th>
<th width="50%" style="text-align: left;">调查投票名称</th>
<th width="10%"><nobr>创建人</nobr></th>
<th width="10%"><nobr>创建时间</nobr></th>
<th width="10%"><nobr>截止时间</nobr></th>
<th width="15%"><nobr>操作</nobr></th>
</tr>
</thead>
<tbody>
<#list vote_list as vote >
<tr>
<td height="40"><input type='checkbox' name='q_guid' value='${vote.voteId}' /></td>
<td style="text-align: left;"><a target='_blank' href='${SiteUrl}mod/vote/showresult.action?guid=${vote.parentGuid}&type=${vote.parentObjectType}&voteId=${vote.voteId}'>${vote.title}</a></td>
<td><nobr><a target='_blank href='${SiteUrl}go.actopn?userId=${vote.createUserId}'>${vote.createUserName}</a></nobr></td>
<td><nobr>${vote.createDate?string('yyyy/MM/dd')}</nobr></td>
<td><nobr>${vote.endDate?string('yyyy/MM/dd')}</nobr></td>
<td><nobr>
<a href='${SiteUrl}mod/vote/modify_vote.action?guid=${vote.parentGuid}&type=${vote.parentObjectType}&voteId=${vote.voteId}'>修改</a> | 
<a href='${SiteUrl}mod/vote/modi_order.py?guid=${vote.parentGuid}&type=${vote.parentObjectType}&voteId=${vote.voteId}'>调顺序</a>
</nobr></td>
</tr>
</#list>
</tbody>
</table>
<div style='text-align: center'>
<input class="specialSubmit" type='button' value='全部选中' onclick='document.getElementById("cxx").click();CommonUtil.SelectAll(document.getElementById("cxx"),"q_guid")' />
<input class="specialSubmit" type='submit' value='删除选中' />
</div>
<#include '../pager.ftl'>
</form>
</#if>