<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>角色组管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" href="css/flora.all.css" type="text/css" title="Flora (Default)" />
  <script type="text/javascript">
    function add()
    {
    	self.document.location.href="?cmd=add";
    }
    function confirmChange(id)
    {
    	self.document.location.href="?cmd=edit&id="+id;
    }
    function setupPower(id)
    {
    	self.document.location.href="?cmd=setuppower&id="+id;
    }
    function setupUser(id)
    {
    	self.document.location.href="?cmd=setupuser&id="+id;
    }
    function autoAddUser()
    {
    	self.document.location.href="?cmd=setcondition";
    }
  	function confirmDel(id)
  	{
  		if (!confirm("您确定要删除该组吗?"))
  			return;
  		self.document.location.href="?cmd=delete&id="+id;
  	}
  	
function select_all() {
  var ids = document.getElementsByName('id');
  if (ids == null || ids.length == 0) return;
  var set_checked = false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked == false) {
      ids[i].checked = true;
      set_checked = true;
    }
  }
  
  if (set_checked == false) {
    for (var i = 0; i < ids.length; ++i) {
      ids[i].checked = false;
    }
  }
}
function has_selected() {
  var ids = document.getElementsByName('id');
  if (ids == null || ids.length == 0) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}
function delete_r() {
  if (has_selected() == false) {
    alert('没有选择要删除的组.');
    return;
  }
  if (confirm('您是否确定要删除所选的组??') == false) return;
  submit_command('delete');
}
function submit_command(cmd) {
  if (has_selected() == false) {
    alert('没有选择要操作的组.');
    return;
  }
  var the_form = document.forms['listForm'];
  if (the_form == null) {
    alert('Can\'t find listForm form.');
    return;
  }
  the_form.cmd.value = cmd;
  the_form.submit();
}  	
  </script>
  
</head>

<body>
<#assign typeTitle = '角色组管理'>
<h2>${typeTitle}</h2>

<form name='listForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='' />
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th>ID</th>
      <th>角色组名称</th>
      <th>描述</th>
      <th width="10%">组权限</th>
      <th width="10%">特殊组成员</th>
      <th width="15%">操作</th>
    </tr>
  </thead>
  <tbody>
  <#if userGroups?size == 0>
    <tr>
      <td colspan='5' style='padding:12px' align='center' valign='center'>没有任何组</td>
    </tr>
  </#if>
  <#list userGroups as ug>
    <tr>
      <td>
        <input type='checkbox' name='id' value='${ug.groupId}' />${ug.groupId}
      </td>
      <td>
         ${ug.groupName!}
      </td>
      <td>
         ${ug.groupInfo!}
      </td>
      <td style=" text-align: center;">
        <a href='#' onclick="setupPower(${ug.groupId})">设置</a> 
      </td>
      <td style=" text-align: center;">
        <a href='#' onclick="setupUser(${ug.groupId})">设置</a> 
      </td>
      <td style=" text-align: center;">
        <a href='#' onclick="confirmChange(${ug.groupId})">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick="confirmDel(${ug.groupId})">删除</a>
      </td>
    </tr>
  </#list>
  </tbody>
</table>

<div class='funcButton'>
    <input type='button'  class='button' value=' 全 选 ' onclick='select_all();'/>
	<input type='button' class='button' value=' 删除组 ' onclick='delete_r();' />
	<input type='button' class='button' value=' 增加组 ' onclick='add();' />
	<input type='button' class='button' value=' 按条件设置组成员 ' onclick='autoAddUser();' />
</div>
</form>
</body>
</html>
