<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>协作组资源管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body style="margin-top: 20px;">
  <h2>协作组: ${group.groupTitle!}</h2>
  <h2>组内</h2>

<form name='theForm' action='groupResource.action' method='post'>
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='10%'>选择</th>
      <th width='43%'>标题</th>
      <th>资源目录</th>
      <th>上传人</th>
      <th width='20%'>上传日期</th>
      <th>资源类型</th>
      <th width='5%'>状态</th>
      <th width='12%'>下载/评论</th>
      <th width='10%'>操作</th>
    </tr>
  </thead>
  <tbody>
  <#list resource_list as resource>
    <tr>
      <td>
        <input type='checkbox' name='resourceId' value='${resource.resourceId}' />${resource.resourceId}
      </td>
      <td><a href='?cmd=view&amp;groupId=${group.groupId}&amp;resourceId=${resource.resourceId}' target='_blank'>${resource.title!?html}</a></td>
      <td>资源目录</td>
      <td>上传人</td>
      <td>${resource.createDate?string('yyyy-MM-dd')}</td>
      <td>资源类型</td>
      <td>
        ${resource.isGroupBest?string('精', '')}
      </td>
      <td align='right'>${resource.downloadCount}/${resource.commentCount}</td>
      <td>
        <a href='?cmd=unref&amp;groupId=${group.groupId}&amp;resourceId=${resource.resourceId}'>移除</a>
      </td>
    </tr>
  </#list>
  </tbody>
</table>
<div align='center'>
  <#include '../inc/pager.ftl'>
</div>
<div>
  <input type='hidden' name='cmd' value='' />  
  <input type='hidden' name='groupId' value='${group.groupId}' />
 <input type='button' value=' 全 选 ' onclick='select_all()' />
 <input type='button' value='上传资源' onclick='upload_r()' />
 <input type='button' value=' 移 除 ' onclick='unref_resource()' />
 <input type='button' value=' 设置精华 ' onclick='submit_command("best")' />
 <input type='button' value=' 取消精华 ' onclick='submit_command("unbest")' />
</div>
</form>

<script>
function select_all() {
  var ids = document.getElementsByName('resourceId');
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
  var ids = document.getElementsByName('resourceId');
  if (ids == null) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}
function upload_r() {
  submit_command2('upload', true);
}
function unref_resource() {
  if (has_item_selected() == false) {
    alert('没有选择任何要操作的资源');
    return;
  }
  
  if (confirm('您是否确定要移除选中的资源? \r\n\r\n资源不会被删除, 只是不显示在协作组中.') == false) return;
  submit_command2('unref');
}
function submit_command(cmd) {
  if (has_item_selected() == false) {
    alert('没有选择任何要操作的资源');
    return;
  }
  submit_command2(cmd);
}
function submit_command2(cmd) {
  document.forms.theForm.cmd.value = cmd;
  document.forms.theForm.submit();
}
</script>

<h2>DEBUG</h2>
<li>group = ${group}
<li>resource_list = ${resource_list}
<li>pager = ${pager}

</body>
</html>
