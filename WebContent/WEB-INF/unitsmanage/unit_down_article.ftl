<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
  
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
  var article = [];
  
  function Filter()
  {
  	var app = document.getElementById("F2").approve.value;
  	var rcd = document.getElementById("F2").rcmd.value;
  	var delState = document.getElementById("F2").delState.value;
  	var url = "${SiteUrl}units/manage/unit_down_article.py?unitId=${unit.unitId}&auditState="+app+"&delState="+delState+"&rcmdState=" + rcd;
  	window.location.href=url;
  }
  </script>
</head>
<body>
<h2>下级全部文章管理</h2>
<form method='GET' action='unit_down_article.py'>
<input type='hidden' name='cmd' value='' />
<input type='hidden' name='unitId' value='${unit.unitId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='title'${(f=='title')?string(' selected="selected"','')}>文章标题、标签</option>
  <option value='uname'${(f=='uname')?string(' selected="selected"','')}>文章作者</option>
</select>
<input type='submit' value='搜索' />
</div>
</form>

<form method='post' id='F2'>
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
<th><nobr>
<select name="delState" onchange="Filter()">
<option value="">删除状态</option>
<option value="0"<#if delState=="0"> selected="selected"</#if>>正常</option>
<option value="1"<#if delState=="1"> selected="selected"</#if>>待删除</option>
</select>
</nobr></th>
<th><nobr>
<select name="approve" onchange="Filter()">
<option value="">审核状态</option>
<option value="0"<#if auditState=="0"> selected="selected"</#if>>已审核</option>
<option value="1"<#if auditState=="1"> selected="selected"</#if>>未审核</option>
</select>
</nobr></th>
<th><nobr>
<select name="rcmd" onchange="Filter()">
<option value="">推荐状态</option>
<option value="1"<#if rcmdState=="1"> selected="selected"</#if>>已推荐</option>
<option value="0"<#if rcmdState=="0"> selected="selected"</#if>>未推荐</option>
</select>
</nobr></th>
<th><nobr>修改</nobr></th>
</tr>
</thead>
<#list article_list as a >
<tr style='background:#eee'>
<td style='width:17px'><input type='checkbox' name='guid' value='${a.articleId}' /></td>
<td><a target='_blank' href='${SiteUrl}showArticle.action?articleId=${a.articleId}'>${a.title?html}</a></td>
<td><nobr><a target='_blank' href='${SiteUrl}go.action?loginName=${a.loginName}'>${a.trueName?html}</a></nobr></td>
<td><nobr>
<#if a.unitId??>
<#assign articleUnit = Util.unitById(a.unitId)>
<#if articleUnit?? && articleUnit!="">
<a href='${SiteUrl}go.action?unitName=${articleUnit.unitName}' target='_blank'>${articleUnit.unitTitle!?html}</a>
<#else>
单位不存在
</#if>
</#if>
</nobr>
</td>
<td><nobr>${a.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
<td style="color:red"><nobr><#if a.delState>待删除</#if></nobr></td>
<td id='container_a_${a.articleId}' title="原始路径：${a.orginPath!} 审核过的路径：${a.approvedPathInfo!}">
<script>
article.push(${a.articleId});
</script>
</td>
<td id='container_r_${a.articleId}' title="原始路径：${a.orginPath!} 推荐的路径：${a.rcmdPathInfo!}"></td>
<td><nobr><a href="admin_article_edit.py?articleId=${a.articleId}&unitId=${unit.unitId}&from=down">修改</a></nobr></td>
</tr>
<tr>
<td colspan='9'>
<strong>机构审核标识：</strong>${a.approvedPathInfo!} <#if a.approvedPathInfo??>
<#assign ut = Util.convertUnitStringPathToUnitTitlePath(a.approvedPathInfo)>
${ut!}
</#if>
<#if a.rcmdPathInfo??>
<br/>
<strong>机构推荐标识：</strong>
${a.rcmdPathInfo!} 
<#assign ct = Util.convertUnitStringPathToUnitTitlePath(a.rcmdPathInfo)>
${ct!}
</#if>
</td>
</tr>
</#list>
</table>
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全选' onclick='document.getElementById("chk").click()' />
  <input class='button' type='button' value='待删除' onclick='doAction("delete")' />
  <input class='button' type='button' value='撤销待删除' onclick='doAction("undelete")' />
  <input class='button' type='button' value='彻底删除' onclick='doAction("crash")' />
  <input class='button' type='button' value='审核' onclick='doAction("approveLevel")' />
  <input class='button' type='button' value='取消审核' onclick='doAction("unapproveLevel")' />
  <input class='button' type='button' value='设为推荐' onclick='doAction("setRcmd")' />
  <input class='button' type='button' value='取消推荐' onclick='doAction("unsetRcmd")' />
</div>
<#if pager??>
  <#include "/WEB-INF/ftl/pager.ftl">		
</#if>
</form>
<script>
if(article.length>0)
{
postData = "unitId=${unit.unitId}&data=" + article.toString();
new Ajax.Request('${SiteUrl}jython/getArticleStatus.py?'+ (Date.parse(new Date())), { 
      method: 'post',
      parameters:postData,
      onSuccess:function(xport){
      retId = xport.responseText.replace(/\r\n/g,"")
      retId = retId.split(",")
      for(i=0;i<retId.length;i++)
      {
      if(retId[i]!="")
      {
       document.getElementById("container_a_"+retId[i]).innerHTML="<span style='color:red'>待审核</span>";
      }
      }
      
      },
      onException: function(xport, ex){}        
    }
  );
  
new Ajax.Request('${SiteUrl}jython/getArticleRcmdStatus.py?'+ (Date.parse(new Date())), { 
      method: 'post',
      parameters:postData,
      onSuccess:function(xport){
      retId = xport.responseText.replace(/\r\n/g,"")
      retId = retId.split(",")
      for(i=0;i<retId.length;i++)
      {
      if(retId[i]!="")
      {
       document.getElementById("container_r_"+retId[i]).innerHTML="<span style='color:red'>未推荐</span>";
      }
      }
      
      },
      onException: function(xport, ex){}        
    }
  );
   
}
</script>
</body>
</html>