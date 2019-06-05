<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
  function doAction(param)
  {
  	document.forms[1].cmd.value = param;
  	document.forms[1].submit();
  }
  
  function check_all(o)
  {
   ele = document.getElementsByName("guid")
   for(i=0;i<ele.length;i++)
   { 
     ele[i].checked=o.checked;
   }
  }
  </script>
</head>
<body>
<h2>
本机构评论管理
</h2>
<form method='GET' action='unit_comment.py'>
<input type='hidden' name='unitId' value='${unit.unitId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='0'${(f=='0')?string(' selected="selected"','')}>评论内容</option>
  <option value='1'${(f=='1')?string(' selected="selected"','')}>发布人</option>
</select>
<input type='submit' value='搜索' />
</div>
</form>
<form method='post'>
<input type='hidden' name='cmd' value='' />
<#if comment_list??>
<table class='listTable' cellspacing='1' style='table-layout:fixed'>
<tr style='text-align:left;'>
<th><input type='checkbox' onclick='check_all(this)' /></th>
<th>标题</th>
<th>类型</th>
<th>发布人</th>
<th>发布日期</th>
<th>ip</th>
</tr>
<#list comment_list as comment >
<#assign u = Util.userById(comment.userId) >
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${comment.id}' /></td>
<td>
<#if comment.objType == 3>
<a target='_blank' href='${SiteUrl}showArticle.action?articleId=${comment.objId}'>${Util.styleNameById(3,comment.objId)}</a>
</#if>
<#if comment.objType == 12>
<a target='_blank' href='${SiteUrl}showResource.action?resourceId=${comment.objId}'>${Util.styleNameById(12,comment.objId)}</a>
</#if>
<#if comment.objType == 17>
<a target='_blank' href='${SiteUrl}manage/video.action?cmd=show&videoId=${comment.objId}'>${Util.styleNameById(17,comment.objId)}</a>
</#if>
</td>
<td>
<#if comment.objType == 3>
文章
</#if>
<#if comment.objType == 12>
资源
</#if>
<#if comment.objType == 17>
视频
</#if>
<#if comment.objType == 11>
相册
</#if>
</td>
<td><a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName?html}</a></td>
<td>${comment.createDate?string('MM-dd HH:mm:ss')}</td>
<td>${comment.ip!}</td>
</tr>
<tr>
<td colspan='6' style='background:#eee;padding:4px;word-break:break-all;word-wrap:break-word;'>
<p style="word-wrap:break-word;width:100%;left:0">
${comment.content}
</p>
</td>
</tr>
</#list>
</table>
</#if>
<div style='padding:6px'>
	<input class='button' type='button' value='删除评论' onclick='doAction("delete")' />
</div>
<#if pager??>
	<#include "/WEB-INF/ftl/pager.ftl">		
</#if>
</form>
</body>
</html>
