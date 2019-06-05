<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>机构风采</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>

<h2>主页机构风采链接管理</h2>

<form name='list_form' action='?' method='post'>
  <input class="button" type='hidden' name='cmd' value='list' />  
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='5%'>选择</th>
      <th width='20%'>链接名称</th>
      <th width='30%'>链接地址</th> 
      <th width='10%'>链接图片</th>
      <th width='10%'>创建日期</th>
      <th width='5%'>链接类型</th>
      <th width='10%'>操作</th>
    </tr>   
  </thead>
  <tbody>
  <#list link_list as link>
    <tr>
      <td align="center"><input type='checkbox' name='linkId' value='${link.linkId}' />${link.linkId}</td>
      <td align="center">${link.title!?html}</td>
      <td>
        <a href='${link.linkAddress!}' target='_blank'>${link.linkAddress!?html}</a> 
      </td> 
      <td>
        <#if link.linkIcon?? && link.linkIcon != ''>
	        <a href='${link.linkAddress!}' target="_blank"><img src="${Util.url(link.linkIcon)!}" 
	          width="60" border="0" /></a>
        </#if>
      </td>
      <td align="center">
        ${link.createDate!?string("MM-dd HH:mm")}
      </td>
      <td align="center">
	      <#if link.linkType == 1>外部
	      <#elseif link.linkType == 2>内部</#if>
      </td>
      <td align="center">
        <a href='?cmd=edit&amp;&amp;linkId=${link.linkId}'>修改</a>
        <a onclick='return confirm_delete()' href='?cmd=delete&amp;linkId=${link.linkId}'>删除</a>
      </td>
    </tr>
  </#list>
  </tbody>
</table>
<div class='pager'>
  <#include '../inc/pager.ftl' >
</div>
<div class='funcButton'>
  <input class="button" type='button' value='添加链接' onclick="document.addLinkForm.submit();" />&nbsp;&nbsp;
  <input class="button" type='button' value='删除链接' onclick='delete_link(list_form);' />&nbsp;&nbsp;
</div>
</form>
<form name='addLinkForm' action='?' method='get'>
  <input class="button" type='hidden' name='cmd' value='add' />  
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
  list_form.cmd.value='delete';
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
