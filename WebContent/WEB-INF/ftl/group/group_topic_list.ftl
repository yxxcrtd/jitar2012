<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>论坛主题列表</title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
  <script type="text/javascript" src="js/common.js"></script>
  <script type="text/javascript">
  <!--
  //全部选择和取消全选.
  var blnIsChecked = true;
  function on_AllSelect(oForm){
	 	for (var i = 0; i < oForm.elements.length; i++) {
				if (oForm.elements[i].type == "checkbox" && !oForm.elements[i].disabled) {
					oForm.elements[i].checked = blnIsChecked;
				}
				
		}
	 	if(oForm.elements["selAll"]) {
	 		if(blnIsChecked) {
	 			oForm.elements["selAll"].value = "取消全选";
	 		} else {
	 			oForm.elements["selAll"].value = "全部选择"; 
	 		}
	 	}
	 	blnIsChecked = !blnIsChecked;
	}
	// 删除一个或多个主题.
	function delSel(list_form) {
		if (has_item_selected(list_form) == false) {
			alert("请选择要操作的主题.");
			return false;
		} else {
			if (confirm("您确定要删除选中的主题吗?") == false) {
				return false;
			}
		}
		list_form.cmd.value = 'delete_topic';
		list_form.submit();
 	}
   	 	
   	 //设置精华主题状态.
	function submit_command(cmd) {
		if(has_item_selected() == false) {
			alert('没有选择任何要操作的主题');
			return;
		}
		document.forms.list_form.cmd.value = cmd;
		document.forms.list_form.submit();
	}
	
	//检查是否选择了数据.
	function has_item_selected() {
  	var ids = document.getElementsByName('topicId');
  	if (ids == null) return false;
  		for (var i = 0; i < ids.length; ++i) {
    		if (ids[i].checked) return true;
 		}
  		return false;
	}
	
	// 发布新主题.
	function add_topic() {
	  document.addTopicForm.submit();
	}
	// 主题置顶.
	function top_topic() {
		if(has_item_selected() == false) {
			alert('没有选择任何要操作的主题');
			return;
		}
		submit_command('top_topic');
	}
	//取消置顶.
	function untop_topic() {
		if(has_item_selected() == false) {
			alert('没有选择任何要操作的主题');
			return;
		}
		submit_command('untop_topic');
	}
	//移除.
	function unref_topic() {
		if(has_item_selected() == false) {
			alert('没有选择任何要操作的主题');
			return;
		}
		if(confirm('您是否确定要移除选中的主题? \r\n\r\n主题不会被删除, 只是不显示在协作组中.') == false) return;
	    submit_command('unrefTopic');
	}
	//恢复引用.
	function ref_topic() {
		if(has_item_selected() == false) {
			alert('没有选择任何要操作的主题');
			return;
		}
		if(confirm('您是否确定要恢复选中的主题?') == false ) return;
		submit_command('refTopic');
	}
	//-->
	</script>
</head>

<body>
<#include 'group_title.ftl' >
    <#assign grpName="协作组">
    <#assign grpShowName="小组">
    <#if isKtGroup??>
        <#if isKtGroup=="1">
            <#assign grpName="课题组"> 
            <#assign grpShowName="课题">
        <#else>
            <#assign grpName="协作组">
            <#assign grpShowName="小组">
        </#if>
    </#if>

<div class='pos'>
  您现在的位置: <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a> 
  &gt;&gt; <span>${grpName}论坛</span>
</div>
<br/>

<form name='addTopicForm' action='groupBbs.action' method='get' style='display:none'>
  <input type='hidden' name='cmd' value='add_topic' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
</form>

<#assign can_pub = (group_member?? && group_member.status == 0) >
<#assign can_manage = (group_member?? && group_member.status == 0 && group_member.groupRole >= 800) >
<#if can_pub>
<div class="funcButton">
  <input type="button" class='button' onclick="add_topic()"  value="发表新主题" />
</div>
</#if>

<form name="list_form" action="groupBbs.action" method="post">
  <input type='hidden' name='cmd' value='list'/>
  <input type='hidden' name='groupId' value='${group.groupId}' />
<table class="listTable" cellspacing='1'>
	<tr>
		<td width="2%"><input type="checkbox" onclick="on_AllSelect(list_form)" /></th>
		<th width="4%" align="center">状态</th>
		<th width="50%">主题</th>
		<th width="8%">作者</th>
		<th width="8%">发表时间</th>
		<th width="8%">回复/点击</th>
		<th width="4%">编辑</th>
	</tr>
<#if top_topic_list??>
	<#list top_topic_list as topic>
		<tr>
			<td><input type="checkbox" name="topicId" value="${topic.topicId!}" /> </td>
			<td align="center"><@topicImage topic /></td>
			<td><a href="groupBbs.action?cmd=reply_list&amp;topicId=${topic.topicId}&amp;groupId=${group.groupId}" target='_blank'>${topic.title!?html}</a></td>
			<td align='center'>${topic.nickName!?html}</td> 
			<td align='center'>${topic.createDate?string("MM-dd HH:mm")}</td>
			<td align='center'>${topic.replyCount!}/${topic.viewCount!}</td>
			<td align='center'>
      <#if can_manage >
			  <a href="groupBbs.action?cmd=edit_topic&amp;topicId=${topic.topicId}&amp;groupId=${group.groupId}">编辑</a>
			</#if>
			</td>
		</tr>
	</#list>
</#if>
	
	<#list topic_list as topic>
		<tr>
			<td><input type="checkbox" name="topicId" value="${topic.topicId!}" /></td>
			<td align="center"><@topicImage topic /></td> 	
			<td><a href="groupBbs.action?cmd=reply_list&amp;topicId=${topic.topicId}&amp;groupId=${topic.groupId}" target='_blank'>${topic.title!?html}</a></td>
			<td align='center'>${topic.nickName!?html}</td> 
			<td align='center'>${topic.createDate?string("MM-dd HH:mm")}</td>
			<td align='center'>${topic.replyCount!}/${topic.viewCount!}</td>
			<td align='center'>
			<#if can_manage >
			  <a href="groupBbs.action?cmd=edit_topic&amp;topicId=${topic.topicId}&amp;groupId=${group.groupId}">编辑</a>
			</#if>
			</td>
		</tr>
	</#list>
	<tr>
		<td><input type="checkbox" onclick="on_AllSelect(list_form)" /></th>
		<th width="4%" align="center">状态</th>
		<th width="50%">主题</th>
		<th width="8%">作者</th>
		<th width="8%">发表时间</th>
		<th width="8%">回复/点击</th>
		<th width="4%">编辑</th>
	</tr>
	
</table>

<div class="pager">
	<#include "../inc/pager.ftl">
</div>
<div class="funcButton">
  <#if can_manage >
    <input id="selAll" class='button' name="Sel_All" onclick="on_AllSelect(list_form)" type="button" value="全部选择" />&nbsp;&nbsp;
  </#if>
  <#if can_pub >
    <input type="button" class='button' value="发表主题" onclick="add_topic()"  />&nbsp;&nbsp;
  </#if>
  <#if can_manage >
		<input type="button" class='button' value="删除主题" onclick="delSel(list_form)"  />&nbsp;&nbsp;
		<input type='button' class='button' value='设置精华' onclick='submit_command("best_topic")' />&nbsp;&nbsp;
		<input type='button' class='button' value='取消精华' onclick='submit_command("unbest_topic")' />&nbsp;&nbsp;
	<#--
		<input type='button' class='button' value='移除主题' onclick='unref_topic()' />&nbsp;&nbsp;
		<input type='button' class='button' value='恢复主题' onclick='ref_topic()' />&nbsp;&nbsp;
	-->
		<input type='button' class='button' value='主题置顶' onclick='top_topic()' />&nbsp;&nbsp;
		<input type='button' class='button' value='取消置顶' onclick='untop_topic()' />&nbsp;&nbsp;
	</#if>
</div>
</form>

</body>
</html>
<#macro topicImage topic>
  <#if topic.isTop><img src='images/ico_top.gif' border='0' />
  <#elseif topic.isBest><img src='images/ico_best.gif' border='0' />
  <#elseif (topic.viewCount >= 1000)><img src='images/ico_hot.gif' border='0' />
  <#-- ?? 比较日期然后显示 new, 或者在程序中计算 -->
  </#if>
</#macro>