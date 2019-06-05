<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
  function doAction(param)
  {
  	document.getElementById("oForm").cmd.value = param;
  	document.getElementById("oForm").submit();
  }
  
  function select_all(o)
  {
    ele = document.getElementsByName("guid")
    for(i = 0;i<ele.length;i++) ele[i].checked=o.checked;
  }
  
  function doFilter()
  {
  	var f = document.getElementById('oForm');
   	var sauditState = f.auditState.options[f.auditState.selectedIndex].value;
  	var sdelState = f.delState.options[f.delState.selectedIndex].value;
  	<#if unit.parentId !=0>
  	var spushupState = f.pushupState.options[f.pushupState.selectedIndex].value;
  	<#else>
  	var spushupState = "";
  	</#if>
  	var srcmdState = f.rcmdState.options[f.rcmdState.selectedIndex].value;
  	var qs = "auditState=" + sauditState + "&delState=" + sdelState + "&pushupState=" + spushupState + "&rcmdState="+ srcmdState +"&type=filter"
  	var url = "unit_owner_article.py?unitId=${unit.unitId}&";
  	window.location.href = url + qs;  	
  }  
  </script>
</head>
<body>
<h2>本机构文章管理</h2>
<form method='GET' action='unit_owner_article.py'>
<input type='hidden' name='cmd' value='' />
<input type='hidden' name='unitId' value='${unit.unitId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='title'${(f=='title')?string(' selected="selected"','')}>文章标题、标签</option>
  <option value='uname'${(f=='uname')?string(' selected="selected"','')}>发表用户</option>
</select>
<input type='submit' value='搜索' />
</div>
</form>

<form method='post' id='oForm' action="?">
<input type='hidden' name='cmd' value='' />
<input type='hidden' name='unitId' value='${unit.unitId}' />
<#if article_list??>
<table class='listTable' cellspacing='1' id='listTable'>
<tr style='text-align:left;'>
<th style='width:20px'><input type='checkbox' onclick='select_all(this)' id='chk' /></th>
<th style='width:100%'>文章标题</th>
<th><nobr>作者</nobr></th>
<th><nobr>发表日期</nobr></th><#--
<th><nobr>
<select name='recommendState' onchange='doFilter()'>
	<option value=''>推荐状态</option>
	<option value='1' ${(recommendState=='1')?string(' selected="selected"','')}>已推荐</option>
	<option value='0' ${(recommendState=='0')?string(' selected="selected"','')}>未推荐</option>
</select>
</nobr>
</th>-->
<th>
<nobr>
<select name='auditState' onchange='doFilter()'>
	<option value=''>审核状态</option>
	<option value='0' ${(auditState=='0')?string(' selected="selected"','')}>已审</option>
	<option value='1' ${(auditState=='1')?string(' selected="selected"','')}>待审</option>
</select>
</nobr>
</th>
<th>
<nobr>
<select name='delState' onchange='doFilter()'>
	<option value=''>删除状态</option>
	<option value='1' ${(delState=='1')?string(' selected="selected"','')}>待删除</option>
	<option value='0' ${(delState=='0')?string(' selected="selected"','')}>正常</option>
</select>
</nobr>
</th>
<th>
<nobr>
<select name='rcmdState' onchange='doFilter()'>
	<option value=''>推荐状态</option>
	<option value='1' ${(rcmdState=='1')?string(' selected="selected"','')}>推荐</option>
	<option value='0' ${(rcmdState=='0')?string(' selected="selected"','')}>未推荐</option>
</select>
</nobr>
</th>
<#if unit.parentId !=0>
<th>
<nobr>
<select name='pushupState' onchange='doFilter()'>
	<option value=''>向上级推送状态</option>
	<option value='1' ${(pushupState=='1')?string(' selected="selected"','')}>已推送</option>
	<option value='0' ${(pushupState=='0')?string(' selected="selected"','')}>未推送</option>
</select>
</nobr>
</th>
</#if>
<#if platformType == "2">
<#if topsite_url??>
<th><nobr>状态</nobr></th>
</#if>
</#if>
<th><nobr>修改</nobr></th>
</tr>
<#list article_list as a >
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${a.articleId}' /></td>
<td><#if a.typeState == false>[原创]<#else>[转载]</#if><a target='_blank' href='${SiteUrl}showArticle.action?articleId=${a.articleId}'>${a.title?html}</a></td>
<td><nobr><a target='_blank' href='${SiteUrl}go.action?loginName=${a.loginName}'>${a.trueName?html}</a></nobr></td>
<td><nobr>${a.createDate?string('MM-dd HH:mm:ss')}</nobr></td>
<td><nobr>
<#if a.approvedPathInfo?? >
 <#if a.approvedPathInfo?index_of("/" + unit.unitId + "/") == -1 >
 <span style='color:red'> 未审核</span>
 <#else>
  已审核
 </#if>
<#else>
<span style='color:red'>未审核</span>
</#if>
</nobr></td>
<td><nobr>
<#if a.delState >
<span style='color:red'>待删除</span>
<#else>
正常
</#if>
</nobr></td>
<td><nobr>
<#if a.rcmdPathInfo??>
<#if a.rcmdPathInfo?index_of('/'+ unit.unitId +'/') &gt; -1 >
已推荐
</#if>
</#if>
</nobr></td>
<#if unit.parentId !=0>
<td><nobr>
<#if a.unitPathInfo?index_of("/"+ unit.parentId +"/") ==-1 >
<span style='color:red'>未推送</span>
<#else>
已推送
</#if></nobr>
</td>
</#if>
<#if platformType == "2">
<#if topsite_url??>
<td><nobr>
<#if a.pushState == 1 >已推送</#if>
<#if a.pushState == 2 ><span style='color:red'>待推送</span></#if>
</nobr>
</td>
</#if>
</#if>
<td><nobr><a href="admin_article_edit.py?articleId=${a.articleId}&unitId=${unit.unitId}&from=owner">修改</a></nobr></td>
</tr>
</#list>
</table>
</#if>
<div style='padding:6px'>
	<input class='button' type='button' value='全部选中' onclick='document.getElementById("chk").click()' />
<#if platformType == "2">
<#if topsite_url??>
	<input class='button' type='button' value='设为推送' onclick='doAction("push")' />
	<input class='button' type='button' value='取消推送' onclick='doAction("unpush")' />
</#if>
</#if>
<#if unit.parentId !=0>
	<input class='button' type='button' value='向上级推送' onclick='doAction("push_up")' />
	<input class='button' type='button' value='取消向上级推送' onclick='doAction("un_push_up")' />
</#if>
	<input class='button' type='button' value='审核文章' onclick='doAction("approve")' />
	<input class='button' type='button' value='取消审核' onclick='doAction("unapprove")' />

	<input class='button' type='button' value='设为推荐' onclick='doAction("rcmdPathInfo")' />
	<input class='button' type='button' value='取消推荐' onclick='doAction("unrcmdPathInfo")' />

	<input class='button' type='button' value='删除文章' onclick='if(window.confirm("你真的要删除这些文章吗？")){doAction("delete")}' />
</div>
<#if pager??>
	<#include "/WEB-INF/ftl/pager.ftl">		
</#if>
</form>
</body>
</html>