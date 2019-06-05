<html>
<head>
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<title>短消息发件箱</title>
	<link rel="stylesheet" type="text/css" href="../css/manage.css">
	<script type="text/javascript">
	<!--
	//全部选择和取消全选.
	var blnIsChecked = true;
	function on_checkAll(oForm) {
		for (var i = 0; i < oForm.elements.length; i++) {
			if (oForm.elements[i].type == "checkbox" && !oForm.elements[i].disabled) {
			}
			oForm.elements[i].checked = blnIsChecked;
		}
		if (oForm.elements["selAll"]) {
			if (blnIsChecked) {
				oForm.elements["selAll"].value = "取消全选";
			} else {
				oForm.elements["selAll"].value = "全部选择";
			}
		}
		blnIsChecked = !blnIsChecked;
	}
 	// 删除发件箱中的短消息.
 	function delete_outbox(list_form) {
		if (hasChecked(list_form) == false) {
  			alert("您还没有选择要操作的消息");
  			return false;
  		} else {
  			if (confirm("您是否确定删除该消息?") == false) {
  				return false;
  			}
  		}
  		document.list_form.cmd.value = "senderdel";
  		document.list_form.submit();
	}
	
	// 删除发件箱中所有短消息.
 	function delete_All_outBox() {
  		if (confirm("您是否确定删除收件箱中的所有消息?")) {
  			document.list_form.cmd.value = "senderdelAll";
  			document.list_form.submit();
  		}
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
  function show_hide(_obj) {
    var obj = document.getElementById("con_" + _obj);
    if (obj == null) return;
    if(obj.style.display == 'none') {
      obj.style.display = 'block';
    } else {
      obj.style.display = 'none';
      return;
    }
  }
	function delete_confirm() {
		return confirm("确定删除么?");
	}
	function move_confirm() {
		return confirm("您是否确定删除该消息?");
	}
	//-->
	</script>
		
<style type="text/css">
<!--
.read {
  font-weight: normal;
  background: url(images/read.jpg) no-repeat 0px 0px;
  padding-left: 20px;
}
.unread {
  font-weight: bold;
  background: url(images/latter.gif) no-repeat 0px 0px;
  padding-left: 20px;
}
.msgcon {
  border: 1px dotted #ececec;
  padding: 3px 20px 3px 20px; 
  margin: 3px; 
  line-height: 120%; 
}
-->
</style>
</head>
<body>

<h2>短消息发件箱</h2>
<div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='?cmd=inbox'>短消息管理</a>
  &gt;&gt; <a href='?cmd=outbox'>发件箱</a>
</div>

<form name="list_form" action="?" method="post">
<table class="listTable" cellspacing="1" >
	<thead>
		<tr>
      <th width="4%">选择</th>
      <th width="12%">接收者</th>
      <th width="60%">标题</th>
      <th width="10%">发送时间</th>
      <th width="8%">操作</th>
		</tr>
	</thead>
	<tbody>
	<#list message_list as message>
		<tr>
			<td align="center">
				<input type="checkbox" id="unreadId" name="messageId" value="${message.id}" />
			</td>
			<td align="center">
			<#if message.loginName??>
            <a href='${SiteUrl}go.action?loginName=${message.loginName!}' target='_blank'>${message.trueName!?html}</a>
            </#if>
			</td>
			<td>
        <div>
          <a class="<#if message.isRead>read<#else>unread</#if>" id="myread_${message.id}" href="javascript:show_hide('${message.id}');">${message.title!?html}</a>
        </div>
        <div id="con_${message.id}" class='msgcon' style='display:none;'>
          ${message.content!}
        </div>
			</td>
			<td align="center">
				${message.sendTime?string("MM-dd HH:mm")}
			</td>
			<td align="center">
				<a onclick="return delete_confirm();" href="?cmd=senderdel&messageId=${message.id}" >删除</a>
			</td>
		</tr>
	</#list>
</table>

		
<div class='pager'>
  <#include "../inc/pager.ftl">
</div>
<div class='funcButton'>
	<input type="hidden" name="cmd" value="" />
	<#if message_list?size != 0>
		<input class="button" id="selAll" name="sel_All"
			onClick="on_checkAll(list_form, 1)" type="button" value="全部选择">&nbsp;&nbsp;
		<input class="button" id="DelAll" name="Del_All" 
			onClick="delete_outbox(list_form)" type='button' value=' 删  除 '  />&nbsp;&nbsp;
		<input class="button" onClick ="delete_All_outBox()" type="button" value="清空发件箱"/>
	</#if>
</div>
</form>
		
</body>
</html>