<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>排行榜管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" href="css/flora.all.css" type="text/css" title="Flora (Default)" />
  <script type="text/javascript">
  	
function select_all() {
  var ids = document.getElementsByName('field');
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
  var ids = document.getElementsByName('field');
  if (ids == null || ids.length == 0) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}

function save_Sel() {
  if (has_selected() == false) {
    alert('没有选择要显示的项目.');
    return;
  }
  document.getElementById('the_form').cmd.value = "save";
  document.getElementById('the_form').submit();
}  	

function selectType(sel)
{
  document.getElementById('the_form').cmd.value = "";
  document.getElementById('the_form').submit();
}
  </script>
 
</head>

<body>
<h2 style="display: inline;">排行榜项目管理</h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择要在排行榜中显示的项目

<form name="the_form" id="the_form" action='?' method='get'>

<div class='pager'>

类型：
<select name="type" onchange="selectType(this)">
	<option <#if type=="1">selected</#if> value="1">个人排行</option>
	<option <#if type=="2">selected</#if> value="2">教研员排行</option>
	<option <#if type=="3">selected</#if> value="3">协作组排行</option>
	<option <#if type=="4">selected</#if> value="4">学校排行</option>
	<option <#if type=="5">selected</#if> value="5">区县排行</option>
</select>
</div>

<input type='hidden' name='cmd' value='' />
<table class='listTable' cellspacing='1'>
  <tbody>
  <#list sourceField as sf>
    <#assign caption = sourceCaption[sf_index]>
    <tr>
      <td>
      	<#assign exists = "0">
      	<#list userField as uf>
      	<#if uf==sf>
      		<#assign exists = "1">
      	</#if>
      	</#list>
      	<#if exists = "1">
        <input type='checkbox' checked name='field' value='${sf}' />${caption}
        <#else>
        <input type='checkbox' name='field' value='${sf}' />${caption}
        </#if>
      </td>
    </tr>
  </#list>
  </tbody>
</table>
<span>
&nbsp;&nbsp;选择排序项目:&nbsp;<select name="orderby">
<#if userOrder=="">
	<#assign oof = sourceOrder>
<#else>
	<#assign oof = userOrder>	
</#if>
<#list orderField as ordf>
	<#if ordf==oof>
		<option selected value='${ordf}'>${orderCaption[ordf_index]}</option>
	<#else>
		<option value='${ordf}'>${orderCaption[ordf_index]}</option>
	</#if>
</#list>	
</select>
</span>

<div class='funcButton'>
  <input type='button'  class='button' value=' 全 选 ' onclick='select_all();'/>
  <input type='button'  class='button' value=' 保 存 ' onclick='save_Sel();'/>
</div>

</form>
</body>
</html>
