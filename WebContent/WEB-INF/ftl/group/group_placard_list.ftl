<#assign grpName="协作组">
<#assign grpShowName="小组">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
        <#assign grpShowName="课题">
    <#elseif isKtGroup=="2">
        <#assign grpName="备课组"> 
        <#assign grpShowName="小组">
    <#else>
        <#assign grpName="协作组">
        <#assign grpShowName="小组">
    </#if>
</#if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理${grpName}</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <span>${grpName}公告管理</span>
</div>
<br/>

<form name='theForm' action='groupPlacard.action' method='post'>
  <input type='hidden' name='cmd' value='list' /> 
  <input type='hidden' name='groupId' value='${group.groupId}' />
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th>选择</th>
      <th width='50%'>标题</th>
      <th width='12%'>发表人</th>
      <th><nobr>发表时间</nobr></th>
      <th width='10%'>状态</th>
      <th width='10%'>操作</th>
    </tr>
  </thead>
  <tbody>
  <#list placard_list as placard>
    <tr>
      <td><nobr><input type='checkbox' name='placard' value='${placard.id}' />${placard.id}</nobr></td>
      <td>
        <a href='../showPlacard.action?placardId=${placard.id}' target='_blank'>${placard.title!?html}</a>
      </td>
      <td>
        <#assign u = Util.userById(placard.userId) >
        <#if u?? && u.loginName?? && u.nickName?? >
          <a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.nickName!?html}</a>
        </#if>
      </td>
      <td  align='center'><nobr>${placard.createDate?string("MM-dd HH:mm")}</nobr></td>
      <td  align='center'>
        <#if placard.hide><font color='#999999'>隐藏</font></#if>
      </td>
      <td>
      <#if (group_member.groupRole >= 800)>
        <a href='?cmd=edit_placard&amp;groupId=${group.groupId}&amp;placardId=${placard.id}'>修改</a>
        <a href='?cmd=delete_placard&amp;groupId=${group.groupId}&amp;placard=${placard.id}' onclick='return confirm_delete();'>删除</a>
      </#if>
      </td>
    </tr>
  </#list>
  </tbody>
</table>

<div class='pager'>
  <#include '../inc/pager.ftl'>
</div>

<div class='funcButton'>
<#if (group_member.groupRole >= 800)>
  <input type='button' class='button' value=' 全 选 ' onclick='select_all()' />
  <input type='button' class='button' value=' 添加新公告 ' onclick='add_placard()' />
  <input type='button' class='button' value=' 删 除 ' onclick='delete_m();' />
  <input type='button' class='button' value=' 显 示 ' onclick='show_m();' />
  <input type='button' class='button' value=' 隐 藏 ' onclick='hide_m();' />
</#if>
</div>
</form>

<form name='addPlacardForm' method='get' style='display:none'>
  <input type='hidden' name='cmd' value='add_placard' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
</form>
<script language='javascript'>
function select_all() {
  var ids = document.getElementsByName('placard');
  if (ids == null) return;
  var has_set = false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked == false) {
      ids[i].checked = true;
      has_set = true;
    }
  }
  
  if (has_set == false) {
    for (var i = 0; i < ids.length; ++i) {
      ids[i].checked = false;
    }
  }
}
function has_item_selected() {
  var ids = document.getElementsByName('placard');
  if (ids == null) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}
function add_placard() {
  document.addPlacardForm.submit();
}
function confirm_delete() {
  return window.confirm('您是否确定删除选择的公告?');
}
function getTheForm() {
  return document.forms['theForm'];
}
function delete_m() {
	if(has_item_selected() == false) {
		alert("请选择要操作的公告.");	
		return false;	
	} else {
		if(confirm("确定删除么?") == false) {
			return false;
		}
	}
  submitCommand('delete_placard');
}
function show_m() {
	if(has_item_selected() == false) {
		alert("请选择要操作的公告.");
		return false;
	} 
  	submitCommand('show_placard');
}
function hide_m() {
	if(has_item_selected() == false) {
		alert("请选择要操作的公告.");
		return  false;
	}
 	submitCommand('hide_placard');
}
function submitCommand(cmd) {
  var form = getTheForm();
  form.cmd.value = cmd;
  form.submit();
}
function has_item_selected() {
  	var ids = document.getElementsByName('placard');
  	if (ids == null) return false;
  		for (var i = 0; i < ids.length; ++i) {
    		if (ids[i].checked) return true;
 		}
  		return false;
	}
</script>

<#--
<h2>DEBUG</h2>
<li>group = ${group}
<li>placard_list = ${placard_list}
-->

</body>
</html>
