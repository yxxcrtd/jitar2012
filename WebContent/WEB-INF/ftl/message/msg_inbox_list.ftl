<html>
<head>
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<title>我的收件箱</title>
	<link rel="stylesheet" type="text/css" href="../css/manage.css">
	<link rel="stylesheet" type="text/css" href="../css/msgbox.css" />	
	<script type="text/javascript" src="../js/json.js"></script>
	<script type="text/javascript" src="../js/prototype.js"></script>
	<script type="text/javascript" src="../js/msgbox.js"></script>
    <script type="text/javascript" src="../js/jitar/core.js"></script>
	
	<script type="text/javascript">
	<!--
	//全部选择和取消全选.
	var JITAR_ROOT = "${SiteUrl}";
	var frdName = "";
	function addToFriend(uloginName)
	{
		frdName = uloginName;
		MessageBox.Show('MessageTip');		
	}
	
	function ConfirmAddToFriend()
	{
	  addFriendForm = document.getElementById("friendForm");
	  addFriendForm.remark.value = document.getElementById("remarkInput").value;
	  addFriendForm.friendName.value = frdName;
	  addFriendForm.submit();
	  MessageBox.Close();
	}
	
	var blnIsChecked = true;
	function on_checkAll(oForm) 
	{
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
  	function delSel(list_form) {
  		if (hasChecked(list_form) == false) {
  			alert("您还没有选择要操作的消息");
  			return false;
  		} else {
  			if (confirm("删除后, 数据将不可恢复. 确定删除么") == false) {
  				return false;
  			}
			}
			list_form.cmd.value = "del" ;
			list_form.submit();
 	 	}
 	 	//将消息放入回收站中.
 	 	function delToRecycle(list_form) {
		if (hasChecked(list_form) == false) {
  			alert("您还没有选择要操作的消息");
  			return false;
  		} else {
  			if (confirm("您是否确定删除所选择的消息??") == false) {
  				return false;
  			}
  		}
  		document.list_form.cmd.value = "delete";
  		document.list_form.submit();
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
     
    var myread = document.getElementById("myread_" + _obj);
    if (myread.className == 'read') return;
      
		var url = "?cmd=show&unreadId=" + _obj;
		// var params = Form.Element.serialize('unreadId'); // 设置是否序列化该属性.
		//alert(params);
		// 创建Ajax.Request对象，对应于发送请求.
		var myAjax = new Ajax.Request(
		url,
		{
			method: 'post',       // 请求方式：POST.
			parameters: null,       // 请求参数.
			onComplete: processResponse,        // 指定回调函数.
			asynchronous: true        // 是否异步发送请求.
		});
		
    myread.className = 'read';
	}
	function processResponse(request) {
		$("tip").innerHTML = request.responseText;
	}
	function delete2_confirm() {
		return confirm("删除后 数据将无法恢复. 确定删除吗??");
	}
	function delete_confirm() {
		return confirm("您是否确定删除该消息?");
	}
	
	//清除所有的消息	
	function delAllMes(){
		if(confirm("你是否确认清空所有的消息")){
			document.list_form.cmd.value = "deleteAll";
  		    document.list_form.submit();
		}
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

<h2>短消息收件箱</h2>
<div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='?cmd=inbox'>短消息收件箱</a>
</div>

<div align="center">
	<span id="tip">${totalRows} 条短消息, ${unreadRows} 条未读</span>
</div>
  		  		
<form name="list_form" action="?" method="post">
<table class="listTable" cellspacing="1" >
	<thead>
		<tr>
			<th width="4%">选择</th>
			<th width="12%">发送者</th>
			<th width="1%"></th>
			<th width="60%">标题</th>
			<th width="10%">发送时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	<#list message_list as message>
		<tr>
			<td align="center">
				<input type="checkbox" id="unreadId" name="messageId" value="${message.id}" />
			</td>
			<td>
			<#if message.loginName??>
			<a href='${SiteUrl}go.action?loginName=${message.loginName!}' target='_blank'>${message.trueName!?html}</a>
			<#else>
        <span>(无)</span>
			</#if>
			</td>
			<td>
			<#if message.isReply>
				<img src="images/list.gif" />
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
			<#if message.loginName?? && message.loginName != ""><a href="#" onclick="addToFriend('${message.loginName}');return false;">加为好友</a></#if>
				<a href="?cmd=reply&messageId=${message.id}">回复</a>
				<a onclick="return delete_confirm();" href="?cmd=delete&messageId=${message.id}" >删除</a>
			</td>
		</tr>
	</#list>
	</tbody>
</table>
<div id='shareDiv'></div>
		
<div class='pager'>
	<#include "../inc/pager.ftl">
</div>
<div class='funcButton'>
	<input type="hidden" name="cmd" value="" />
	<input type="hidden" name="type" value="${type!?html}" />
	<input class="button" onclick="document.writeMsgForm.submit();"
		type="button" value="写短消息" />
<#if message_list?size != 0>
	<input class="button" id="selAll" name="sel_All"
		onClick="on_checkAll(list_form, 1)" type="button" value="全部选择">
	<input class="button" id="DelAll" name="Del_All" 
		onclick="delToRecycle(list_form)" type='button' value=' 删  除 '  />
    <input type="button" onclick="delAllMes()" class = "button" value="清空收件箱"/>
</#if>
</div>

<div id="blockUI" onClick="return false" onMousedown="return false" onMousemove="return false" onMouseup="return false" onDblclick="return false">&nbsp;</div>
<div id="MessageTip" class="hidden">
    <div class="boxHead">
        <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src="../images/dele.gif" /></div>
        <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">加为好友：</div>
    </div>
    <div style="padding:60px 0; text-align: center;">

              发送信息：<input type="text" id="remarkInput" style="width:300px" />
    <input type="button" class="button" value=" 确  定 " onClick="ConfirmAddToFriend();" />

    </div>
</div>
	
</form>
		
<div style='display:none'>	
<form name='writeMsgForm' action='?' method='get' >
  <input type='hidden' name='cmd' value='write' />		
</form>
</div>
<form id="friendForm" style="display:none" action="${SiteUrl}manage/friend.action" method="post" target="myiframe">
<input type="hidden" name="cmd" value="save" />
<input type="hidden" name="friendName" />			
<input type="hidden" name="remark" />
</form>
<iframe name="myiframe" style="display:none"></iframe>
</form>
</body>
</html>