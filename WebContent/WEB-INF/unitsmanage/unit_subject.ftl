<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
 <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
 <link rel="stylesheet" type="text/css" href="${SiteUrl}css/msgbox.css" />
 <script src='${SiteUrl}js/msgbox.js' type='text/javascript'></script>
<script type='text/javascript'>
function doAddAction()
{
 document.getElementById('oForm').cmd.value="add"
 document.getElementById('oForm').submit();
}

function doDisplayNamePost()
{
 document.getElementById('oForm').cmd.value="setDisplayName"
 document.getElementById('oForm').submit();
}
function doDeletePost()
{
 if(window.confirm("你真的要删除这些内容吗？"))
 {
 document.getElementById('oForm').cmd.value="delete"
 document.getElementById('oForm').submit();
 }
}

function close_dialog(event) {
if (event.keyCode == 27) {
MessageBox.Close();
}
}
</script>
</head>
<body onKeyDown="close_dialog(event);">
<form method='post' id='oForm'>
<input type='hidden' name='cmd' value='' />
<h2>机构学科配置</h2>
<table class="listTable" cellSpacing="1">
<thead>
<tr style='text-align:left'>
<th style='width:17px'></th>
<th>学科名称</th>
<th>显示名称</th>
</tr>
</thead>
<#list unit_subject_list as usl>
<#assign subject = Util.subjectBySubjectId(usl.subjectId)>
<tr>
<td><input type='checkbox' name='guid' value='${usl.unitSubjectId}' /></td>
<td>
<#if subject?? && subject.subjectId??>
<a href='${SiteUrl}subject.py?id=${subject.subjectId}' target="_blank">${subject.subjectName!}</a>
</#if>
</td>
<td><input name='displayName${usl.unitSubjectId}' value="${usl.displayName!?html}" /><input type='hidden' name='unitSubjectId' value='${usl.unitSubjectId}' /></td>
</tr>
</#list>
</table>
<div style='padding:4px 0'>
<input type='button' value='删除学科' class='button' onclick='doDeletePost()' />
<input type='button' value='添加学科' class='button' onclick="MessageBox.Show('MessageTip')" />
<input type='button' value='修改显示名称' class='button' onclick="doDisplayNamePost()" />
</div>
<div id="blockUI" onClick="return false" onMousedown="return false" onMousemove="return false" onMouseup="return false" onDblclick="return false">&nbsp;</div>
<div id="MessageTip" class="hidden">
	<div class="boxHead">
		<div class="boxCloseButton" onclick="return MessageBox.Close();"><img src="${SiteUrl}images/dele.gif" /></div>
		<div class="boxTitle" onmousedown="MessageBox.dragStart(event)">请选择学科</div>
	</div>
	<div style="padding:10px;padding-right:0; text-align:left;overflow:auto;height:400px">
  <#if subject_list??>
  <#list subject_list as subject>
  <div><label><input type="checkbox" name="subId" value="${subject.subjectId}" />${subject.subjectName}</label></div>
  </#list>
  </#if>  
  </div>
  <div style="text-align:center;padding:4px">
  <input type="button" class="button" value="将学科添加到机构属性里" onClick="doAddAction();" />
  </div>
</div>
</form>
</body>
</html>