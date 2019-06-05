<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><@s.text name="groups.friend.title" /></title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css">
		<script type="text/javascript">
		<!--
		var blnIsChecked = true;
		function on_checkAll(oForm) {
			for (var i = 0; i < oForm.elements.length; i++) {
				if (oForm.elements[i].type == "checkbox" && !oForm.elements[i].disabled) {
				}
				oForm.elements[i].checked = blnIsChecked;
			}
			if (oForm.elements["selAll"]) {
				if (blnIsChecked) {
					oForm.elements["selAll"].value = "全部不选";
				} else {
					oForm.elements["selAll"].value = "全部选择";
				}
			}
			blnIsChecked = !blnIsChecked;
		}
		
		// 删除一个或多个记录
    	function delSel(list_form) {
    		if (hasChecked(list_form) == false) {
    			alert("请选择任意个将要删除的记录！");
    			return false;
    		} else {
	  			if (confirm("确定要删除选择的好友吗？") == false) {
	  				return false;
	  			}
  			}
  			list_form.cmd.value = "del_black";
  			list_form.submit();
   	 	}
   	 	
   	 	// 将选中的一个或多个记录添加到好友列表中
   	 	function addSel(list_form) {
    		if (hasChecked(list_form) == false) {
    			alert("请选择任意个将要添加到好友列表中的记录！");
    			return false;
    		} else {
	  			if (confirm("确定要将选择的黑名单添加进好友列表中吗？") == false) {
	  				return false;
	  			}
  			}
  			list_form.cmd.value = "move_to_friend";
  			list_form.submit();
   	 	}
   	 	
		// 检查有无选择记录
		function hasChecked(vform) {
			for (var i = 0; i < vform.elements.length; i++) {
				var e = vform.elements[i];
				if (e.checked) {
					return true;
				}
			}
			return false;
		}
		//-->
		</script>
	</head>
	
	<body>
		<h2>黑名单列表</h2>
<div class='funcButton'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='friend.action?cmd=list'>好友管理</a>
  &gt;&gt; 黑名单列表
</div>
<form name="list_form" action="friend.action" method="post">
  <input type="hidden" name="cmd" value="" />
	<table class="listTable" cellspacing="1" cellpadding="0">
		<thead>
		<tr>
			<th width="5%">选择</th>
			<th width="5%">头像</th>
			<th width="12%">黑名单</th>
			<th width="7%">工作室</th>
			<th width="8%">添加时间</th>
			<th width="5%">操作</th>
		</tr>
		</thead>
		
		<tbody>
		<#list black_list as black>
			<tr>
				<td align="center">
					<input type="checkbox" id="FId" name="myblackid" value="${black.id}" />
				</td>
				<td align="center">											 
					<a href="${SiteUrl}go.action?userId=${black.friendId}"><img src="${SSOServerUrl +'upload/'+black.userIcon!'images/default.gif'}" onerror="this.src='${ContextPath}images/default.gif'" width="48" height="48" border="0" alt="${black.nickName!}" />
					</a>
				</td>
				<td>
					<a href="${SiteUrl}go.action?userId=${black.friendId}">${black.loginName!}(${black.nickName!})</a>
				</td>
				<td>
					<a href="${SiteUrl}go.action?userId=${black.friendId}">${black.blogName!}</a>
				</td>
				<td align="center">
					${black.addTime}
				</td>
				<td align="center">
					<a href="message.action?cmd=write&friendId=${black.friendId}">发短消息</a>
					<a href="?cmd=del_black&myblackid=${black.id}" onClick="return confirm('确定要删除该黑名单吗？');">删除</a>
				</td>
			</tr>
		</#list>
  </tbody>
</table>
<div class="pager">
	<#include "../inc/pager.ftl">
</div>	
<div class='funcButton'>
  <input class="button" id="" name="" onClick="javascript:window.location='friend.action?cmd=add_black'" type="button" value="添加黑名单">&nbsp;&nbsp;
  <#if black_list?size != 0>
  <input class="button" id="selAll" name="sel_All" onClick="on_checkAll(list_form, 1)" type="button" value="全部选择">&nbsp;&nbsp;
  <input class="button" id="DelAll" name="Del_All" onClick="delSel(list_form)" type="button" value="删除选择">&nbsp;&nbsp;
  </#if>
  <input class="button" id="" name="" onClick="javascript:window.location='friend.action?cmd=list'" type="button" value="好友列表">&nbsp;&nbsp;
  <input class="button" id="AddAll" name="Add_All" onClick="addSel(list_form)" type="button" value="添加好友">&nbsp;&nbsp;
  
</div>		
</form>
		
</body>
</html>
