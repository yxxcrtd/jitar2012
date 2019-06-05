<#assign grpName="协作组">
<#assign grpShowName="小组">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
        <#assign grpShowName="课题">
    <#else>
        <#assign grpName="协作组">
        <#assign grpShowName="小组">
    </#if>
</#if>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>${grpName}管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body style="margin-top: 20px;">
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <span>${grpName}链接管理</span> 
</div>
<br/>

<form name='list_form' action='groupLink.action' method='post'>
  <input class="button" type='hidden' name='cmd' value='list' />  
  <input class="button" type='hidden' name='groupId' value='${group.groupId!}' />
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='5%'>选择</th>
      <th width='10%'>链接名称</th>
      <th width='40%'>链接地址</th> 
      <th width='10%'>链接图片</th>
      <th width='10%'>创建日期</th>
      <th width='5%'>链接类型</th>
      <th width='10%'>操作</th>
    </tr>   
  </thead>
  <tbody>
  <#list linkList as link>
    <tr>
      <td align="center"><input type='checkbox' name='linkId' value='${link.linkId}' />${link.linkId}</td>
      <td align="center">${link.title!?html}</td>
      <td>
      	<a href='${link.linkAddress}' target='_blank'>${link.linkAddress!?html}</a> 
      </td> 
      <td>
      	<a href='${link.linkAddress}' target="_blank"><img src="${link.linkIcon}" width="60" height="25" border="0" ></img></a>
      </td>
      <td align="center">
        ${link.createDate!?string("MM-dd HH:mm")}
      </td>
      <#if link.linkType == 1> 
      <td align="center">外部</td>
      <#elseif link.linkType == 2>
      <td align="center">内部</td>
      </#if>
      <td align="center">
        <a href='?cmd=editLink&amp;groupId=${group.groupId!}&amp;linkId=${link.linkId}'>修改</a>
        <a onclick='return confirm_delete()' href='?cmd=deleteLink&amp;groupId=${group.groupId}&amp;linkId=${link.linkId}'>删除</a>
      </td>
    </tr>
  </#list>
  </tbody>
</table>
<div class='funcButton'>
  <input class="button" type='button' value='添加链接' onclick="document.addLinkForm.submit();" />&nbsp;&nbsp;
  <input class="button" type='button' value='删除链接' onclick='delete_link(list_form);' />&nbsp;&nbsp;
</div>
</form>
<form name='addLinkForm' action='groupLink.action' method='get'>
  <input class="button" type='hidden' name='cmd' value='write' />  
  <input class="button" type='hidden' name='groupId' value='${group.groupId!}' />
</form>

<script type='text/javascript'>
function delete_link(list_form) {
	if(has_item_selected() == false) {
		alert("请选择您要删除的链接");
		return false;
	} else {
		if(confirm("确定要删除当前的链接么?") == false) {
			return false;
		}
	}
	list_form.cmd.value='deleteLink';
	list_form.submit();
}
function has_item_selected() {
	var ids = document.getElementsByName('linkId');
	if(ids == null) return false;
	for(var i = 0; i < ids.length; ++i)  {
		if(ids[i].checked) return true;	
	}
	return false;
}

function confirm_delete() {
  return window.confirm('你是否确定删除选择的链接?');
}
</script>

</body>
</html>
