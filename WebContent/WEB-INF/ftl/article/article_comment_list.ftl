<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>评论管理</title>
	<link rel="stylesheet" href="../css/manage.css" type="text/css" />
	<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
<!--
var blnIsChecked = true;
function on_checkAll(oForm) {
  for (var i = 0; i < oForm.elements.length; i++) {
    if (oForm.elements[i].type == "checkbox" && !oForm.elements[i].disabled) {
      oForm.elements[i].checked = blnIsChecked;
    }
  }
  blnIsChecked = !blnIsChecked;
}
function delSel() {
  var f = document.forms.commentForm;
  if (hasChecked(f) == false) {
    alert("没有选择要操作的评论.");
    return false;
  } else {
    if (confirm("您是否确定要删除所选评论??") == false) {
      return false;
    }
  }
  f.cmd.value = "delete_comment";
  f.submit();
}
function hasChecked(vform) {
  var ids = document.getElementsByName("id");
  for (var i = 0; i < ids.length; i++) {
    var e = ids[i];
    if (e.checked) {
        return true;
    }
  }
  return false;
}
//-->
</script>
</head>
<body>
<h2>文章评论管理</h2>
<#if loginUser??>
<#assign canManage = (loginUser.userStatus == 0) >
<#else>
<#assign canManage = (1==0) >
</#if>

<div class='funcButton'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='?cmd=list'>文章管理</a>
  &gt;&gt; <a href='?cmd=comment_list'>评论管理</a>
</div>

<form name="commentForm" method="post" action="?">
	<input type="hidden" name="cmd" value="comment_list" />
<table id="ListTable" cellspacing="1" class="listTable">
	<thead>
		<tr>
			<th></th>
			<th>评论</th>
			<th>评论者</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<#list comment_list as data> <#assign comment = data[0]>
		<tr class="backNormalColor">
			<td>
				<input type="checkbox" name="id" onclick="checkSelf(this)"
					value="${comment.id}" />
				${comment.id}
			</td>
			<td>
				<div class="articleTitle">
					文章标题：<a href="${SiteUrl}showArticle.action?articleId=${data[1].articleId}" target="_blank">${data[1].title!?html}</a> (发表时间：${data[1].createDate})
				</div>
				<div class="">
					<!--评论主题：${comment.title!?html}-->评论者： ${comment.userName!}&nbsp;&nbsp;&nbsp;&nbsp; 评论时间：${comment.createDate}<br />
					评论内容：
				</div>
				<div class="commentContent">
					${comment.content!}
				</div>
			</td>
			<td>
			 <#if comment.audit>审核通过<#else><font color='red'>未审核</font></#if>
			 <br/>
				${comment.userName!?html}
				<br />
				${comment.ip!}
			</td>
			<td align="center">
		<#if canManage>
			<#if !comment.audit>
			  <#if my==0>	
			  	<a href="?cmd=audit_comment&amp;id=${comment.id}">审核</a>
			  </#if>
			</#if>
			<#if my==0>
				<a href="?cmd=reply_comment&amp;id=${comment.id}">回复</a>
			</#if>	
				<a href="?cmd=edit_comment&amp;id=${comment.id}">修改</a>
				<a href="?cmd=delete_comment&amp;id=${comment.id}">删除</a>
		</#if>
			</td>
		</tr>
		</#list>
	</tbody>
</table>

<div class="pager">
	<#include "../inc/pager.ftl" >
</div>

<div class="funcButton">
	<input type="hidden" name="funcType" value="" />
<#if canManage>
  <input class="button" id="selAll" name="sel_All" onClick="on_checkAll(commentForm, 1)" type="button" value=" 全 选 ">
	<input type="button" class='button' value="删除评论" onclick="delSel()" />
	<#if my==0>
		<input type="button" class='button' value="通过审核" onclick="doAction('audit_comment')" />
		<input type="button" class='button' value="取消审核" onclick="doAction('unaudit_comment')" />
	</#if>
</#if>
<#if my==0>	
	<input type="button" class='button' value="重新统计" onclick="doAction('comment_stat')" title="重新统计文章的评论数" />
</#if>	
</div>
<script>
<!--
function doAction(act) {
  var theForm = document.forms.commentForm;
  theForm.cmd.value = act;
  theForm.submit();
}
//-->
</script>
</form>

</body>
</html>
