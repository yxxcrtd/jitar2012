<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理协作组</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />  
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>协作组管理首页</a>
  &gt;&gt; <span>协作组统计查询</span> 
</div>
<br/>
<#if data_list??>
<form name='theForm' action='?groupId=${group.groupId}&guid=${guid}&cmd=init' method='POST'>
开始日期：<input name="beginDate" value="${beginDate!?html}" size="10" maxlength="10" title="请按照  yyyy-MM-dd 格式输入，例如：2010-09-01" />
结束日期：<input name="endDate" value="${endDate!?html}" size="10" maxlength="10" title="请按照  yyyy-MM-dd 格式输入，例如：2010-09-30" />
<input type="submit" value="重新查询" />
<input type="button" value=" 导  出 " onclick='exportQuery(this.form)' />
<table class='listTable' cellspacing='1'>
<thead>
  <tr>
    <th>组长真实姓名</th>
    <th>所在机构</th>
    <th>学科学段</th>
    <th>发布文章数</th>
    <th>精华文章数</th>
    <th>发布资源数</th>
    <th>精华资源数</th>
    <th>发布主题数</th>
    <th>回复主题数</th>
  </tr>
</thead>
<tbody>
<#list data_list as m>
	<tr>
		<td><a href='${SiteUrl}go.action?loginName=${m.loginName}' target='_blank'>${m.trueName!?html}</a></td>		
		<td><a href='${SiteUrl}go.action?unitName=${m.unitName!?url}' target='_blank'>${m.unitTitle!?html}</a></td>
		<td>
			<#if m.metaSubjectTitle??>${m.metaSubjectTitle!?html}</#if>
			<#if m.gradeTitle??>${m.gradeTitle!?html}</#if>
		</td>
		<td>${m.articleCount}</td>
		<td>${m.bestArticleCount}</td>
		<td>${m.resourceCount}</td>
		<td>${m.bestResourceCount}</td>
		<td>${m.topicCount}</td>
		<td>${m.replyCount}</td>
  </tr>
</#list>
</tbody>
</table>
</form>
<div class='pager' style='text-align:center;'>
<#if pager??><#include "/WEB-INF/ftl/inc/pager.ftl"></#if>
</div>
<iframe name="export" style="display:none"></iframe>
<script>
function exportQuery(oForm)
{
	var url = "?groupId=${group.groupId}&guid=${guid}&cmd=export&beginDate="+encodeURIComponent(oForm.beginDate.value)+"&endDate="+encodeURIComponent(oForm.endDate.value);
	window.frames["export"].location.href = url;
}
</script>
<#else>
<h4 style="text-align:center;color:red">没有查询结果。</h4>
</#if>
</body>
</html>