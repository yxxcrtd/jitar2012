<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>评论管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" href="css/flora.all.css" type="text/css" title="Flora (Default)" />
  <script type="text/javascript" src='js/admin_comment.js'></script>
  <script type="text/javascript">
  	function confirmDel(id)
  	{
  		if (!confirm("您确定要删除该评论吗?"))
  			return;
		var toolbarSty = "dialogwidth=240px;dialogheight=160px;scrolling:no;border=no;status=no;help=no";
		var url = "${SiteUrl}PunishScoreConfirm.py?seltype=score.comment.adminDel";
		var res = window.showModalDialog(url,null,toolbarSty);
		if(res && res.indexOf("|")>-1){		
			var arr=res.split("|");
  		window.location.href="admin_Comment.py?type=${type!?html}&ppp=${pager.currentPage}&cmd=delete&commentId="+id+"&score="+arr[0]+"&reason="+encodeURI(arr[1]);
  	}
  	}

  	
  </script>
  
</head>

<body>
<#if !(type??)><#assign type = ''></#if>
<#if type == 3><#assign typeTitle = '文章评论管理'>
<#elseif type == 12><#assign typeTitle = '资源评论管理'>
<#else><#assign typeTitle = '评论管理'>
</#if>
<h2>${typeTitle}</h2>

<form action='?' method='get'>
<div class='pager'>
  <#if type == 3><input type='hidden' name='cmd' value='list1' />
  <#elseif type == 12><input type='hidden' name='cmd' value='list2' />
  <#else><input type='hidden' name='cmd' value='list1' />
  </#if>
  关键字：<input type='text' name='k' value="${k!?html}" size='16' />
<#if !(f??)><#assign f = 'title'></#if>
  <select name='f'>
    <!--<option value='title' ${(f == 'title')?string('selected', '')}>评论标题</option>-->
    <option value='content' ${(f == 'content')?string('selected', '')}>评论内容</option>
  </select>
  <input type='submit' class='button' value=' 查找 ' />
</div>
</form>

<form name='listForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='' />
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th>ID</th>
      <th>评论</th>
      <th>评论者</th>
      <th width="10%">操作</th>
    </tr>
  </thead>
  <tbody>
  <#if commentList?size == 0>
    <tr>
      <td colspan='8' style='padding:12px' align='center' valign='center'>没有找到符合条件的评论</td>
    </tr>
  </#if>
  <#list commentList as comment>
    <#assign u = Util.userById(comment.userId)>
    <tr>
      <td style='background-color:#eeeeee'>
        <input type='checkbox' name='commentId' value='${comment.id}' />
      </td>
      <td style='background-color:#eeeeee'>
        <#if !(type??)><#assign typeName = ''></#if>
        <#if type == 3><#assign typeName = '文章:'>
        <#elseif type == 12><#assign typeName = '资源:'>
        <#else><#assign typeName = ''>
        </#if>
        ${typeName}
        <#if type == 12>
          <a href='../showResource.action?resourceId=${comment.objId!}' target="_blank">
          <img src='${Util.iconImage(comment.href!)}' border='0' hspace='4' align='absmiddle' />${comment.sourceTitle!}</a>(发表于${comment.sourceCreateDate!})<br/>
           <!--评论标题:${comment.title!}-->评论者:${comment.trueName!}&nbsp;&nbsp;&nbsp;&nbsp;发表于:${comment.createDate!}<br/>
          <div class='commentContent'>${comment.content!}</div>
        <#else>
          <a href='${SiteUrl}showArticle.action?articleId=${comment.objId!}' target="_blank">
          ${comment.sourceTitle!}</a>(发表于${comment.sourceCreateDate!})<br/>
           <!--评论标题:${comment.title!}-->评论者:${comment.trueName!}&nbsp;&nbsp;&nbsp;&nbsp;发表于:${comment.createDate!}<br/>
          <div class='commentContent'>${comment.content!}</div>
        </#if>  
      </td>
      <td style="background-color: #EEEEEE; text-align: center;">
        <a href='${SiteUrl}go.action?loginName=${comment.loginName}' target='_blank'>${comment.trueName!}</a><br/>
        ${comment.ip}
      </td>
      
      <td style="background-color: #EEEEEE; text-align: center;">
      <#if comment.audit>
      <#else>
        <a href='?cmd=audit&amp;commentId=${comment.id}'>通过审核</a><br/><br/>
      </#if>
        <a href='#' onclick="confirmDel(${comment.id})">删除</a>
      </td>
    </tr>
  </#list>
  </tbody>
</table>

<div class='pager'>
  <#include '../inc/pager.ftl' >  
</div>

<div class='funcButton'>
  <input type='button'  class='button' value=' 全 选 ' onclick='select_all();' ondblclick='select_all();' />
  <input type='button'  class='button' value=' 审核通过 ' onclick='audit_r();' />
  <input type='button'  class='button' value=' 取消审核 ' onclick='unaudit_r();' />
  <input type='button' class='button' value=' 删 除 ' onclick='delete_r();' />
  
</div>

</form>
<br/><br/><br/><br/>

<#-- 
  <h2>DEBUG</h2>
  <li>resource_list = ${commentList}
  <li>pager = ${pager}
--> 
</body>
</html>
