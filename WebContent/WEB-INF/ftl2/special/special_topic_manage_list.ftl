<style>
.commentTable {width:100%;background:pink; margin:0 0 6px 0;}
.commentLeft{ width:60px; background:#fff; text-align:center; vertical-align:top; padding:10px;} 
.commentLeft img{ width:64px;height:64px;border:0px; }
.commentRight{background:#FFF;width:100%;padding:6px;}
.commentHeader{ width:100%; border-bottom:1px solid #DDD; padding-bottom:4px;}
.commentContent {  padding: 3px; border: 1px solid #dddddd; background-color: #ececec; margin:5px 0px 2px 0px;}
.commentTitle { font-weight:bold; }
</style>
<script type="text/javascript">
function msg() {
	if (confirm("确定要删除选中的讨论吗？")) {
		document.newsform.submit();
		return true;
	} else {
		return false;
	}
}
</script>
<#if t_list??>
<form method='post' name="newsform" onsubmit="return msg()">
<table class="moreTable">
	<thead>
		<tr class="moreThead">
			<th width="5%"><input type='checkbox' id='cxx' onclick="CommonUtil.SelectAll(this,'q_guid')" /></th>
			<th width="75%" style="padding-left: 10px; text-align: left;">讨论话题名称</th>
			<th width="10%"><nobr>发起人</nobr></th>
			<th width="10%"><nobr>发起时间</nobr></th>
		</tr>
	</thead>

<tbody>
<#list t_list as t>
<tr class="moreThead">
	<td height="40"><input type='checkbox' name='q_guid' value='${t.plugInTopicId}' /></td>
	<td style="padding-left: 10px; text-align: left;"><a target='_blank' href='${SiteUrl}mod/topic/show_topic.action?guid=${parentGuid}&type=${parentType}&topicId=${t.plugInTopicId}'>${t.title!?html}</a></td>
	<td><nobr><a href='${SiteUrl}go.action?userId=${t.createUserId}'>${t.createUserName?html}</a></nobr></td>
	<td><nobr>${t.createDate?string('yyyy/MM/dd')}</nobr></td>
</tr>
</#list>
</tbody>
</table>

<div style='text-align: center;'>
<input class="specialSubmit" type='button' value='全部选中' onclick='document.getElementById("cxx").click();CommonUtil.SelectAll(document.getElementById("cxx"),"q_guid")' />
<input class="specialSubmit" type='submit' value='删除选中' />
</div>
</form>
</#if>