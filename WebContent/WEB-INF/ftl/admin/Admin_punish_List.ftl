<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>加、罚分管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" href="css/flora.all.css" type="text/css" title="Flora (Default)" />
  <script type="text/javascript">
  	function confirmDel(id)
  	{
  		if (!confirm("您确定要删除该罚分吗?"))
  			return;
  		self.document.location.href="?cmd=delete&amp;id="+id;
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
    alert('没有选择要操作的评论.');
    return;
  }
  if (confirm('您是否确定要删除所选的评论??') == false) return;
  submit_command('delete');
}
function submit_command(cmd) {
  if (has_selected() == false) {
    alert('没有选择要操作的评论.');
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
<#assign typeTitle = '加、罚分管理'>
<h2>${typeTitle}</h2>

<form action='?' method='get'>
<div class='pager'>
类型：
<select name="type">
	<option value=""></option>
	<option <#if objType??><#if objType=="-1">selected</#if></#if> value="-1">评判人</option>
	<option <#if objType??><#if objType=="-2">selected</#if></#if> value="-2">被评判人</option>
	<option <#if objType??><#if objType=="3">selected</#if></#if> value="3">文章</option>
	<option <#if objType??><#if objType=="11">selected</#if></#if> value="11">照片</option>
	<option <#if objType??><#if objType=="12">selected</#if></#if> value="12">资源</option>
	<option <#if objType??><#if objType=="16">selected</#if></#if> value="16">评论</option>
	<option <#if objType??><#if objType=="17">selected</#if></#if> value="17">视频</option>
	<option <#if objType??><#if objType=="20">selected</#if></#if> value="20">个案</option>
	
</select>
<input type='hidden' name='cmd' value='list' />
  关键字：<input type='text' name='k' value="${k!?html}" size='16' />
  <input type='submit' class='button' value=' 查找 ' />
</div>
</form>

<form name='listForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='' />
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th style="width:80px">ID</th>
      <th style="width:80px">评判人</th>
      <th style="width:140px">评判时间</th>
      <th style="width:80px">被评判人</th>
      <th>内容</th>
      <th style="width:70px">奖惩分值</th>
      <th>原因</th>
      <th style="width:50px">操作</th>
    </tr>
  </thead>
  <tbody>
   <#if punishList??>
   
  <#list punishList as punish>
	<#if punish.objType == 3>
		<#assign typename = '文章'>
	<#elseif  punish.objType == 11>
		<#assign typename = '照片'>
	<#elseif  punish.objType == 12>
		<#assign typename = '资源'>
	<#elseif  punish.objType == 16>
		<#assign typename = '评论'>
	<#elseif  punish.objType == 17>
		<#assign typename = '视频'>
    <#elseif  punish.objType == 20>
        <#assign typename = '个案'>		
	</#if>
    <#assign u = Util.userById(punish.userId)>
    <tr>
      <td>
        <input type='checkbox' name='id' value='${punish.id}' />${punish.id}
      </td>
      <td>${punish.createUserName!}</td>
      <td>${punish.punishDate?string("yyyy-mm-dd HH:mm:ss")}</td>
      <td>
          ${u.trueName!}
      </td>
      <td>
          [${typename!}]${punish.objTitle!?html}
      </td>
      <td>
          ${-1*punish.score!}
      </td>
      <td>
          ${punish.reason!}
      </td>
      
      <td style=" text-align: center;">
        <a href='#' onclick="confirmDel(${punish.id})">撤销</a>
      </td>
    </tr>
  </#list>
  <#else>
    <tr>
      <td colspan='8' style='padding:12px' align='center' valign='center'>没有找到符合条件的</td>
    </tr>  
  </#if>
  </tbody>
</table>

<div class='pager'>
  <#include '../inc/pager.ftl' >  
</div>

<div class='funcButton'>
      	<input type='button'  class='button' value=' 全 选 ' onclick='select_all();'/>

  <input type='button' class='button' value=' 撤 销 ' onclick='delete_r();' />
  
</div>

</form>
</body>
</html>
