<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>举报管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script src="${ContextPath}js/jquery.js"></script>
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

function doPost(st){
 document.listForm.cmd.value=st;
 document.listForm.action="?";
 document.listForm.submit();
}
</script>
</head>
<body>
<h2>举报内容管理</h2>
<form action='?' method='get' style="text-align:right;">
类型：
<select name="type">
  <option value="">举报类型</option>
  <#list reportTypeName as key>
  <option value="${key}"<#if reportType?? && key == reportType> selected="selected"</#if>>${key}</option>
</#list>
</select>
<input type='hidden' name='cmd' value='list' />
  关键字：<input type='text' name='k' value="${k!?html}" size='16' />
      <input type='submit' class='button' value=' 查找 ' />
</form>
<form name='listForm' action='?' method='post'>
<input type='hidden' name='cmd' value='' />
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th style="width:17px"></th>
      <th>举报内容标题</th>
      <th>举报人姓名（登录名）</th>
      <th>举报原因</th>
      <th>举报时间</th>
      <th>状态</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
  <#if reports??>
  <#list reports as r>
    <tr>
      <td>
        <input type='checkbox' name='id' value='${r.reportId}' />
      </td>
      <td><a href="${ContextPath}go.action?objType=${r.objType}&objId=${r.objId}" target="_blank">${r.objTitle!}</a></td>
      <td><a href="${ContextPath}go.action?loginName=${r.loginName}" target="_blank">${r.trueName!}（${r.loginName}）</a></td>
      <td>${r.reportType!}</td>
      <td>${r.createDate?string("yyyy-mm-dd HH:mm:ss")}</td>
      <td>${r.status?string("已处理","<span style='color:#f00'>未处理</span>")}</td>
      <td><a href='adminReport.action?cmd=delete&reportId=${r.reportId}'>删除此记录</a> <#--| <a href="adminReport.action?cmd=unreport&reportId=${r.reportId}">撤销举报</a> -->| <a href="adminReport.action?cmd=deleteObject&reportId=${r.reportId}">删除举报的内容</a></td>
    </tr>    
  </#list>
  <#else>
    <tr>
      <td colspan='6' style='padding:12px' align='center' valign='center'>没有找到符合条件的</td>
    </tr>
  </#if>
  </tbody>
</table>
<div class='pager'>
  <#include '../inc/pager.ftl' >  
</div>

<div class='funcButton'>
 <input type='button'  class='button' value=' 全 选 ' onclick='select_all();'/>
 <input type='button' class='button' value='删除举报记录' onclick='doPost("deleteAll");' />
 <input type='button' class='button' value='标记为已处理' onclick='doPost("processed");' />
</div>

</form>
<style>
.over{background:#E6F4B2;}
</style>
<script>
$(function(){
//鼠标移入该行和鼠标移除该行的事件
$("table.listTable tr").mouseover(function(){$(this).addClass("over");
}).mouseout(function(){
 $(this).removeClass("over");
});
});
</script>
</body>
</html>
