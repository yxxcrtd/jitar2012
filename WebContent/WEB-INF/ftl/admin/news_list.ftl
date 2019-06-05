<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${displayName!}列表</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  
  <script>
	function sel_subNews() {
		var frm=document.getElementById("newsForm");
		frm.cmd.value = "list";
		frm.submit();
	}
  </script>
  
</head>
<body>
	<#if (subjectId>0)>
  		<h2>${subject.subjectName!}学科${displayName!}管理</h2>
  	<#else>
  		<h2>${displayName!}管理</h2>	
  	</#if>
  	
<form name='newsForm' id='newsForm'  action='admin_news.action' method='post'>
	<input type='hidden' name='cmd' value='list' />  
	<input type="hidden" name="type" value="${newsType!}" />
	  <div class='pager'>
		  <a href='?cmd=list&amp;subjectId=-1&type=${newsType!}'>所有${displayName!}</a> |
		  <a href='?cmd=list&amp;subjectId=0&type=${newsType!}'>主页${displayName!}</a> 
		  <select name="subjectId" onchange="sel_subNews();">
		    <option value=''>选择学科${displayName!}</option>
		  <#if subject_list?? && subject_list?size &gt; 0>  
		  <#list subject_list as s>			
		    <option value='${s.subjectId}'<#if subjectId?? && subjectId == s.subjectId> selected='selected'</#if>>${s.subjectName!}${displayName!}</option>
		  </#list>
		  </#if>
		  </select>
	  </div>
  
  
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th>ID</th>
      <th>${displayName!}标题</th>
      <th>阅读次数</th>
      <th>发布人</th>
      <th>来源</th>
      <th>发布时间</th>
      <th>状态</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
  <#if news_list?? && news_list?size &gt;0 >
  <#list news_list as news>
    <tr>
      <td><input type='checkbox' name='newsId' value='${news.newsId}' /></td>
      <td><#if news.picture?? && news.picture != ""><span style='color:red;'>[图]</span></#if><a href="${ContextPath}showNews.action?newsId=${news.newsId}" target="_blank">${news.title!?html}</a></td>
      <td>${news.viewCount}</td>
      <td><a href='${SiteUrl}go.action?loginName=${news.loginName}' target='_blank'>${news.trueName}</a></td>
      <td>${Util.styleNameById(14,news.subjectId)}</td>
      <td>${news.createDate}</td>
      <td>
        <#if news.status == 1><font color='red'>待审核</font>
        <#else>已审核</#if>
      </td>
      <td>
        <a href='?cmd=edit&amp;newsId=${news.newsId}&type=${newsType!}&subjectId=${subjectId!-1}'>修改</a>
        <#if news.status == 1><a href='?cmd=audit&amp;newsId=${news.newsId}&type=${newsType!}&subjectId=${subjectId!-1}'>审核</a></#if>
        <a href='?cmd=delete&amp;newsId=${news.newsId}&type=${newsType!}&subjectId=${subjectId!-1}' onclick='return confirm("您真的要删除吗？")'>删除</a>
      </td>
    </tr>
  </#list>
  </#if>
  </tbody>
</table>

<div class='pager'>
  <#include '../inc/pager.ftl' >  
</div>

<div class='funcButton'>
  <input type='button' class='button' value=' 全 选 ' onclick='select_all();' ondblclick='select_all();' />
  <input type='button' class='button' value=' 发布${displayName!} ' onclick='add_a();' />
  <input type='button' class='button' value=' 删 除 ' onclick='if(confirm("您真的要删除吗？")){delete_a();}' />
  <input type='button' class='button' value=' 审核通过 ' onclick='audit_a();' />
  <input type='button' class='button' value=' 取消审核 ' onclick='unaudit_a();' />
</div>
</form>
<!-- 发布使用的 form -->
<form name='addForm' method='get' action='admin_news.action' style='display:none'>
  <input type='hidden' name='cmd' value='add' />
  <input type="hidden" name="type" value="${newsType!}" />
  <input type='hidden' name='subjectId' value='${subjectId!}' />
</form>

<script>
function select_all() {
  var ids = document.getElementsByName('newsId');
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
function add_a() {
  document.forms['addForm'].submit();
}
function delete_a() {
  submit_command('delete');
}
function audit_a() {
  submit_command('audit');
}
function unaudit_a() {
  submit_command('unaudit');
}
function submit_command(cmd) {
  var the_form = document.forms['newsForm'];
  if (the_form == null) {
    alert('Can\'t find form.');
    return;
  }
  the_form.cmd.value = cmd;
  the_form.submit();
}
</script>
</body>
</html>
