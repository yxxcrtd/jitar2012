<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>协作组管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
   <script type="text/javascript">
  	//检查是否选择了数据.
   function has_item_selected() {
  	var ids = document.getElementsByName('chatroomId');
  	if (ids == null) return false;
  		for (var i = 0; i < ids.length; ++i) {
    		if (ids[i].checked) return true;
 		}
  		return false;
	}
    
    //删除记录.
    function delete_room(list_form) {
    	if(has_item_selected() == false) {
    		alert("请选择您想要删除的讨论室");
    		return false;
    	} else {
    		if(confirm("确定要删除当前讨论室么?") == false) {
    			return false;
    		}
    	}
    	document.list_form.cmd.value = 'delRoom';
    	document.list_form.submit();
    }
    
    //添加.
    function add_room() {
		window.location = "?cmd=addRoom&groupId=" + document.forms['list_form'].groupId.value;
    }
  </script>
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>协作组管理首页</a>
  &gt;&gt; <span>协作组讨论室列表</span>
</div>
<br/>

<form name='list_form' action='groupTalkRoom.action' method='post'>
  <input class="button" type='hidden' name='cmd' value='' />  
  <input class="button" type='hidden' name='groupId' value='${group.groupId}' />
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='6%'>选择</th>
      <th width='43%'>讨论室名称</th>
      <th width='12%'>创建人</th>
      <th width='20%'>创建时间</th>
      <th width='5%'>在线人数</th>
    </tr>
  </thead>
  <tbody>
  <#list room_list as room>
    <tr>
		<td align="center">
			<input type='checkbox' name='chatroomId' value='${room.roomId}' />${room.roomId}
		</td>
		<td><a href='?cmd=talkingRoom&amp;groupId=${room.roomId}&amp;roomId=${room.roomId}' >${room.roomName!?html}</a></td>
		<td align="center">${room.createrName!}</td>
		<td align="center">${room.createDate!?string("MM-dd HH:mm")}</td>
		<td align="center">6 TODO</td>
     </tr>
  </#list>
  </tbody>
</table>
  
<div class='funcButton'>
  <input class="button" type='button' value='创建讨论室' onclick='add_room();'>&nbsp;&nbsp;&nbsp;&nbsp;
  <input class="button" type='button' value='删除讨论室' onclick='delete_room();' />
</div>
</form>

</body>
</html>
