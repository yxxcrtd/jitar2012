<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script>
  function doPost(arg)
  {
  	document.getElementById('oForm').cmd.value=arg;
  	document.getElementById('oForm').submit();
  }
  
  function doFilter()
  {
  	var f = document.getElementById('oForm');
  	var ssysId = f.sc.options[f.sc.selectedIndex].value;
    var sarticleState = f.articleState.options[f.articleState.selectedIndex].value;
  	var qs = "ss=" + ssysId + "&sarticleState=" + sarticleState + "&type=filter"
  	var url = "channelarticle.action?channelId=${channel.channelId}&";
  	window.location.href = url + qs;
  }
function checkSelectedItem()
{
  gs = document.getElementsByName("guid");
  for(i=0;i<gs.length;i++)
  {
   if(gs[i].checked) return true;
  }
  
  alert("请选择一篇文章。");
  return false;
}
  </script>
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script src='${SiteUrl}js/subject/util.js' type='text/javascript'></script>
</head>
<body>
<h2>频道文章管理</h2>
<form method='GET' action='channelarticle.action'>
<input name='channelId' type='hidden' value='${channel.channelId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='title'${(f=='title')?string(' selected="selected"','')}>文章标题</option>
  <option value='uname'${(f=='uname')?string(' selected="selected"','')}>发表用户</option>
</select>
<select name='ss'>
    <option value=''>频道文章分类</option>
    <#if article_categories??>
	  <#list article_categories.all as category>
	  <#assign cp = Util.convertIntFrom36To10(category.parentPath) + category.id?string + "/" >
	  <option value="${cp}"${(ss == cp)?string(' selected', '')}>${category.treeFlag2} ${category.name!?html}</option>  
	  </#list>
   </#if>
</select>
<input type='submit' value='搜索' />
</div>
</form>
<form method='POST' id='oForm' action="channelarticle.action">
<input name='cmd' type='hidden' value='' />
<input name='channelId' type='hidden' value='${channel.channelId}' />
<#if article_list??>
<table class='listTable' cellspacing='1' id='listTable'>
<thead>
<tr>
<th><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");SetRowColorByName();' id='chk' /></th>
<th style='width:100%'>文章标题</th>
<th><nobr>文章作者</nobr></th>
<th><nobr>发布时间</nobr></th>
<th>
  <select name='sc' onchange='doFilter()'>
    <option value=''>文章分类</option>
    <#if article_categories??>	
  	<#list article_categories.all as c >
  	<#assign cp = Util.convertIntFrom36To10(c.parentPath) + c.id?string + "/" >
    <option value='${cp}' ${(ss == cp)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
   </#list>
   </#if>
  </select>
</th>
<th>
  <select name='articleState' onchange='doFilter()'>
    <option value=''${(articleState == "")?string(" selected='selected'","")}>显示状态</option>
    <option value='1'${(articleState == "1")?string(" selected='selected'","")}>显示</option>
    <option value='0'${(articleState == "0")?string(" selected='selected'","")}>不显示</option>    
  </select>
</th>
</tr>
</thead>
<#list article_list as a >
<#assign user = Util.userById(a.userId)>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${a.channelArticleId}' onclick='SetRowColor(event)' /></td>
<td>
<a href='${SiteUrl}showArticle.action?articleId=${a.articleId}' target='_blank'>${a.title!?html}</a></td>
<td><nobr><a href='${SiteUrl}go.action?loginName=${a.loginName}' target='_blank'>${a.userTrueName}</a></nobr></td>
<td><nobr>${a.createDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr>
<#if a.channelCateId??>
<#assign cate = Util.getCategory(a.channelCateId)>
<#if cate??>
${cate.name!?html}
</#if>
</#if>
</nobr></td>
<td><nobr><#if a.articleState>显示<#else><font style="color:red">不显示</font></#if></nobr></td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全部选中' onclick='${"chk"}.click();SetRowColorByName();' />
  <input class='button' type='button' value='从频道中删除' onclick="if(checkSelectedItem() && confirm('真的要删除这些文章吗？')){doPost('remove');}" />  
  <select name="newCate">
  <option value="">取消分类</option>
  <#if article_categories??>
      <#list article_categories.all as category>
      <#assign cp = Util.convertIntFrom36To10(category.parentPath) + category.id?string + "/" >
      <option value="${cp}">${category.treeFlag2} ${category.name!?html}</option>  
      </#list>
   </#if>
  </select>
  <input class='button' type='button' value='转移到选定分类' onclick="if(checkSelectedItem() && confirm('真的要对这些文章进行重新分类吗？')){doPost('recate');}" />
</div>
</form>
<div style="padding:10px 0;color:red">说明：文章状态是指该文章能否在本频道显示，是由原文章的删除状态、草稿状态、隐藏状态和审核状态共同决定是否显示。</div>
</body>
</html>