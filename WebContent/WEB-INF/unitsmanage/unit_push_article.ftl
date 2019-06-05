<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
  function doAction(param)
  {
  	document.getElementById("F2").cmd.value = param;
  	document.getElementById("F2").submit();
  }
  function select_all(o)
  {
    ele = document.getElementsByName("guid")
    for(i = 0;i<ele.length;i++) ele[i].checked=o.checked;
  }
  </script>
</head>
<body>
<h2>下级推送文章管理</h2>
<form method='GET' action='unit_push_article.py'>
<input type='hidden' name='cmd' value='' />
<input type='hidden' name='unitId' value='${unit.unitId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='title'${(f=='title')?string(' selected="selected"','')}>文章标题</option>
  <option value='uname'${(f=='uname')?string(' selected="selected"','')}>发表用户</option>
</select>
<input type='submit' value='搜索' />
</div>
</form>

<form method='post' id='F2' action="?">
<input type='hidden' name='unitId' value='${unit.unitId}' />
<input type='hidden' name='cmd' value='' />
<#if article_list??>
<table class='listTable' cellspacing='1' border="1">
<thead>
<tr style='text-align:left;'>
<th><input type='checkbox' onclick='select_all(this)' id='chk' /></th>
<th style="width:100%">文章标题</th>
<th><nobr>作者</nobr></th>
<th><nobr>作者机构</nobr></th>
<th><nobr>发表日期</nobr></th>
<th><nobr>本级审核状态</nobr></th>
<#if unit.parentId !=0>
<th><nobr>向上级推送状态</nobr></th>
</#if>
<th><nobr>推荐状态</nobr></th>
<th><nobr>修改</nobr></th>
</tr>
</thead>
<#list article_list as a >
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${a.articleId}' /></td>
<td><a target='_blank' href='${SiteUrl}showArticle.action?articleId=${a.articleId}'>${a.title?html}</a></td>
<td><nobr><a target='_blank' href='${SiteUrl}go.action?loginName=${a.loginName}'>${a.trueName?html}</a></nobr></td>
<td><nobr>
<#if a.unitId??>
<#assign articleUnit = Util.unitById(a.unitId)>
<#if articleUnit??>
<a href='${SiteUrl}go.action?unitName=${articleUnit.unitName!}' target='_blank'>${articleUnit.unitTitle!?html}</a>
</#if>
</#if>
</nobr>
</td>
<td><nobr>${a.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
<td>
<nobr>
<#if a.approvedPathInfo??>
	<#if a.approvedPathInfo?index_of("/" + unit.unitId + "/") == -1>
	<span style='color:#f00'>本级未审</span>
	<#else>
	本级已审
	</#if>
<#else>
本级未审
</#if>
</nobr>
</td>
<#if unit.parentId !=0>
<td><nobr>
<#if a.unitPathInfo??>
	<#if a.unitPathInfo?index_of("/"+ unit.parentId +"/") == -1>
	<span style='color:#f00'>未向上级推送</span>
	<#else>
	已向上级推送
	</#if>
<#else>
未向上级推送
</#if>
</nobr>
</td>
</#if>
<td>
<nobr>
<#if a.rcmdPathInfo??>
<#if a.rcmdPathInfo?index_of("/"+unit.unitId+"/") &gt;-1 >
已推荐
</#if>
</#if>
</nobr>
</td>
<td><nobr><a href="admin_article_edit.py?articleId=${a.articleId}&unitId=${unit.unitId}&from=push">修改</a></nobr></td>
</tr>
</#list>
</table>
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全选' onclick='document.getElementById("chk").click()' />
  <input class='button' type='button' value='删除' onclick='doAction("remove")' />
  <input class='button' type='button' value='审核' onclick='doAction("approveLevel")' />
  <input class='button' type='button' value='取消审核' onclick='doAction("unapproveLevel")' />
  <#if unit.parentId!=0>
	<input class='button' type='button' value='向上级推送' onclick='doAction("pushup")' />
	<input class='button' type='button' value='取消向上级推送' onclick='doAction("unpushup")' />
	</#if>
	<input class='button' type='button' value='设为推荐' onclick='doAction("setRcmd")' />
	<input class='button' type='button' value='取消推荐' onclick='doAction("unsetRcmd")' />
</div>
<#if pager??>
	<#include "/WEB-INF/ftl/pager.ftl">		
</#if>
</form>
</body>
</html>