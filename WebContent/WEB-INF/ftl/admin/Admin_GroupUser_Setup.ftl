<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>角色组用户管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" href="css/flora.all.css" type="text/css" title="Flora (Default)" />
  <script type="text/javascript">
    function returnx()
    {
		self.document.location.href="?cmd=list";	    
    }
  	function deluser(id)
  	{
  		if (confirm('您是否确定要删除该成员??') == false) return;
  		self.document.location.href="?cmd=deleteuser&groupid=${id}&id="+id;
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
    alert('没有选择要删除的成员.');
    return;
  }
  if (confirm('您是否确定要删除所选的成员??') == false) return;
  submit_command('deleteuser');
}
function submit_command(cmd) {
  if (has_selected() == false) {
    alert('没有选择要操作的成员.');
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

  function selectUser()
  {
    var url="${SiteUrl}selectUser.py?showgroup=0&singleuser=0&inputUser_Id=inviteUserId&inputUserName_Id=inviteUserName";
    window.open(url,'_blank','left=100,top=50,height=450,width=800,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
  }
  function postAction(cmd)
  {
   var f = document.getElementById("listForm")
   f.cmd.value = cmd
   f.submit();
  }

  </script>
  
</head>

<body>
<#assign typeTitle = '角色组用户管理'>
<h2>${typeTitle}</h2>

<form name='listForm' id='listForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='' />
  <input type='hidden' name='groupid' value='${id}' />
<h4>角色组[${groupName}]中特殊成员:</h4> 
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width="10%">用户ID</th>	
      <th>特殊成员名称</th>
      <th width="20%">从本组中删除</th>
    </tr>
  </thead>
  <tbody>
  <#list groupuserlist as gu>
    <#assign u = Util.userById(gu.userId)>
    <tr>
      <td>
        <input type='checkbox' name='id' value='${gu.userId}' />${gu.userId}
      </td>
      <td>
         ${u.trueName!}
      </td>
      <td style=" text-align: center;">
        <a href='#' onclick="deluser(${gu.userId})">删除</a> 
      </td>
    </tr>
  </#list>
  </tbody>
</table>

<div class='funcButton'>
    <input type='button'  class='button' value=' 全 选 ' onclick='select_all();'/>
	<input type='button' class='button' value=' 删除成员 ' onclick='delete_r();' />
	<input type='button' class='button' value=' 返 回 ' onclick='returnx();' />
</div>
<hr style="height:1px">
<h4>向[${groupName}]中增加特殊成员</h4>
<div>
    <input type='hidden' name='inviteUserId' id='inviteUserId' />
    <input type="text" id='inviteUserName' style="width:240px" readonly='readonly'/>
    <input type='button' class='button' value=' 选择成员 ' onclick='selectUser();' />
    <input type='button' class='button' value='保    存' onclick='postAction("adduser")' />
</div>

</form>
</body>
</html>
