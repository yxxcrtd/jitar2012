<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>增加角色组</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" href="css/flora.all.css" type="text/css" title="Flora (Default)" />
  <script type="text/javascript">

    
    function returnx()
    {
		self.document.location.href="?cmd=list";	    
    }
  	function save()
  	{
	  var the_form = document.forms['listForm'];
	  if (the_form == null) {
	    alert('Can\'t find listForm form.');
	    return;
	  }
	  if(the_form.groupName.value=="")
	  {
	    alert('请输入组名称.');
	    return;
	  }
	  the_form.submit();
  	}
  	


  </script>
  
</head>

<body>
<#assign typeTitle = '增加角色组'>
<#if id!=0>
	<#assign typeTitle = '修改角色组'>
</#if>
<h2>${typeTitle}</h2>
<form name='listForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='saveadd' />
  <input type='hidden' name='id' value='${id}' />
<table cellspacing='1'>
  <tbody>
    <tr>
      <td>角色组名称:</td>
      <td><input type="text" style="width:300px" name="groupName" Value="${groupName!}"> </td>
    </tr>  
    <tr>
      <td>角色组描述:</td>
      <td><textarea name="groupInfo" style="width:300px;height:60px">${groupInfo!}</textarea></td>
    </tr>  
  </tbody>
</table>
<div class='funcButton'>
    <input type='button'  class='button' value=' 保 存 ' onclick='save();'/>
	<input type='button' class='button' value=' 返 回 ' onclick='returnx();' />
</div>
</form>
</body>
</html>
