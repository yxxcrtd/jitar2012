<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>消息回收站管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type="text/javascript">
  function crash_confirm() {
  	return confirm("彻底删除数据后不可恢复. 您确定删除吗??");
  }
  
  var blnIsChecked = true;
	function sellect_all(list_form) {
		for (var i = 0; i < list_form.elements.length; i++) {
			if (list_form.elements[i].type == "checkbox" && !list_form.elements[i].disabled) {
			}
			list_form.elements[i].checked = blnIsChecked;
		}
		if (list_form.elements["selAll"]) {
			if (blnIsChecked) {
				list_form.elements["selAll"].value = "取消全选";
			} else {
				list_form.elements["selAll"].value = "全部选择";
			}
		}
		blnIsChecked = !blnIsChecked;
	}
		
	function delSel(list_form) {
		if (hasChecked(list_form) == false) {
			alert("您还没有选择要操作的消息");
			return false;
		} else {
			if (confirm("彻底删除, 数据将不可恢复.  确定删除么") == false) {
				return false;
			}
		}
		list_form.cmd.value = "del" ;
		list_form.submit();
 	}   	
 	 	
	function hasChecked(vform) {
		for (var i = 0; i < vform.elements.length; i++) {
			var e = vform.elements[i];
			if (e.checked) {
				return true;
			}
		}
		return false;
	}
		
	function crash_message(list_form) {
		if (hasChecked(list_form) == false) {
			alert("您还没有选择要操作的消息");
			return false;
		} else {
			if (confirm("彻底删除短消息数据将不可恢复. 确定删除吗??") == false) {
				return false;
			}
		}
		document.list_form.cmd.value = "crash";
		document.list_form.submit();
 	}
 	function crash_recycle_All() {
		if (confirm("彻底删除短消息数据将不可恢复. 确定删除吗??")) {
			document.list_form.cmd.value = "crashAll";
			document.list_form.submit();
		}
 	}
 	function submit_recycle(list_form) {
 		if (hasChecked(list_form) == false) {
			alert("您还没有选择要操作的消息.");
			return false;
		}
		document.list_form.cmd.value = "recover";
		document.list_form.submit();
 	}  	 
 	function submit_recycleAll() {
 		if (confirm("是否确认恢复所有的收件箱信息?")) {
			document.list_form.cmd.value = "recoverAll";
			document.list_form.submit();
		}
 	}	
  </script>
</head>

<body>
<h2>消息回收站管理</h2>
<div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='?cmd=inbox'>短消息管理</a>
  &gt;&gt; <a href='?cmd=trash'>消息回收站管理</a>
</div>
<br/>

<form name="list_form" action="?" method="post">
	<input type="hidden" name="cmd" value="" />
<table class="listTable" cellspacing="1">
	<thead>
		<tr>
			<th width="2%">选择</th>
			<th width="6%">发送者	</th>
			<th width="50%">标题</th>
			<th width="8%">发送时间</th>
			<th width="6%">操作</th>
		</tr>
	</thead>
	<tbody>
		<#list message_list as msg>
			<tr>
				<td align="center">
					<input type="checkbox" id="messageId" name="messageId" value="${msg.id}" />
				</td>
				<td align="center">
					<#if msg.loginName??> 
            <a href='${SiteUrl}go.action?loginName=${msg.loginName!}' target='_blank'>${msg.trueName!?html}</a>
          <#else>
            <span>(无)</span>
					</#if>
				</td>
				<td>
					<span>${msg.title!?html}</span>
				</td>
				<td align="center">
					${msg.sendTime?string("MM-dd HH:mm")}
				</td>
				<td align="center">
					<a href="?cmd=recover&messageId=${msg.id}">恢复</a>
					<a onclick="return crash_confirm();" href="?cmd=crash&messageId=${msg.id}">删除</a>
				</td>
			</tr> 
		</#list> 
	</tbody>
</table>
<div class='pager'>
	<#include "../inc/pager.ftl">
</div>
<div class='funcButton'>
  <input class="button" type='button' value='全部选择' id ="selAll" onclick='sellect_all(list_form);' />
	<input class="button" type='button' value='恢复消息' onclick='submit_recycle(list_form);' />
	<input class="button" type='button' value='回收站恢复' onclick='submit_recycleAll();' />
	<input class="button" type='button' value='回收站清空' onClick='crash_recycle_All();' />
	<input class="button" type='button' value='彻底删除' onclick='crash_message(list_form); '>
</div>
</form>

</body>
