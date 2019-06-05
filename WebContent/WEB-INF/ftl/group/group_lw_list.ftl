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
  <title>${grpName}管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <span>${grpName}留言管理</span> 
</div>
<br/>
 
<form name='theForm' action='groupLeaveword.action' method='post'>
  <table class='listTable' cellspacing='1'>
    <thead>
      <tr>
        <th width='48'>选择</th>
        <th width='43%'>标题</th>
        <th width='12%'>留言者</th>
        <th width='20%'>留言日期</th>
        <th width='5%'>回复</th>
        <th width='10%'>操作</th>
      </tr>
    </thead>
    <tbody>
    <#list leaveword_list as leaveword>
      <tr>
        <td><input type='checkbox' name='leavewordId' value='${leaveword.id}' />${leaveword.id}</td>
        <td><a href='?cmd=reply&amp;groupId=${group.groupId}&amp;leavewordId=${leaveword.id}'>${leaveword.title!?html}</a></td>
        <td>
          ${leaveword.nickName!?html}
        </td>
        <td>${leaveword.createDate!}</td>
        <td>${leaveword.replyTimes}</td>
        <td>
          <a href='?cmd=reply&amp;groupId=${group.groupId}&amp;leavewordId=${leaveword.id}'>回复</a>
          <#if (group_member.groupRole >= 800)>
          <a onclick='return confirm_delete()' href='?cmd=delete&amp;groupId=${group.groupId}&amp;leavewordId=${leaveword.id}'>删除</a>
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
	  <input type='hidden' name='cmd' value='' />  
	  <input type='hidden' name='groupId' value='${group.groupId}' />
	 <#if (group_member.groupRole >= 800)> 
    <input type='button' class='button' value=' 删 除 ' onclick='delete_m();' />
    </#if>
  </div>
</form>

<script language='javascript'>
function confirm_delete() {
  return window.confirm('你是否确定删除选择的留言?');
}
function getTheForm() {
  return document.forms['theForm'];
}
function delete_m() {
  if (confirm_delete() == false) return;
  submitCommand('delete');
}
function submitCommand(cmd) {
  var form = getTheForm();
  form.cmd.value = cmd;
  form.submit();
}
</script>

<#--
<h2>DEBUG</h2>
<li>group = ${group}
<li>leaveword_list = ${leaveword_list}
-->

</body>
</html>
