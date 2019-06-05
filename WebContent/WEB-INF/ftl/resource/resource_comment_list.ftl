<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>资源评论管理</title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
  <script type="text/javascript" src="js/common.js"></script>
</head>

<body>
<h2>资源评论管理</h2>
<div class='funcButton'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='resource.action?cmd=list'>资源管理</a>
  &gt;&gt; <a href='resource.action?cmd=comment_list'>资源评论管理</a>
</div>

<form name='commentForm' method='post' action="resource.action">
<table id="ListTable" cellspacing="1" class='listTable'>
  <thead>
    <tr>
      <th width='32'></th>
      <th>评论</th>
      <th>评论者</th>
      <th>状态</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
  <#list data_list as data>
    <#assign comment = data[0]>
    <#assign resource = data[1]>
    <tr class="backNormalColor">
      <td><input type='checkbox' name='commentId' onclick='checkSelf(this)' value="${comment.id}" />${comment.id}</td>
      <td>
        <div class='articleTitle'>资源: <a href='?cmd=comment_list&amp;resourceId=${resource.resourceId}'>${resource.title!?html}</a> (发表于 ${resource.createDate})</div>
        <!--<div class='commentTitle'>评论标题: ${comment.title!?html}-->
        评论者：<#if comment.userId?? ><a href='${SiteUrl}go.action?userId=${comment.userId}' target='_blank'>${comment.userName!}</a>
          <#else >${comment.userName!'匿名用户'}</#if> &nbsp;&nbsp;&nbsp;&nbsp;发表于 ${comment.createDate}</div>
        <div class='commentContent'>${comment.content!}</div>
      </td>
      <td>
      <nobr>
        <#if comment.userId?? ><a href='${SiteUrl}go.action?userId=${comment.userId}' target='_blank'>${comment.userName!}</a>
          <#else >${comment.userName!'匿名用户'}</#if>
        <br/>${comment.ip!}
         </nobr>
        </td>
      <td><nobr>
        ${comment.audit?string('已审', '待审')} </nobr>
        </td>
      <td><nobr>
        <#if my==0>
        <a href='?cmd=reply_comment&amp;commentId=${comment.id}'>回复</a>
        </#if>
        <a href='?cmd=edit_comment&amp;commentId=${comment.id}'>修改</a>
        <a href='?cmd=delete_comment&amp;commentId=${comment.id}'>删除</a>
        </nobr>
      </td>
    </tr>
  </#list>
  </tbody>
  </table>
  
 <div class="pager" style='text-align:right; width:98%; padding:2px;'>
  <#include '../inc/pager.ftl' >
 </div>
  
 <div class="funcButton">
   <input type='hidden' name='cmd' value='comment_list' />
   <input type="button" class='button' name='selAll' value='全部选择' onclick="select_all(commentForm)" />
   <input type="button" class='button' value='删除评论' onclick="do_delete()" />
   <#if my==0>
	   <input type="button" class='button' value="通过审核" onclick="doActionNC('audit_comment')" />
	   <input type="button" class='button' value="取消审核" onclick="doActionNC('unaudit_comment')" />
	   <input type='button' class='button' value='重新统计评论数' onclick="doAction('comment_stat')" title='重新统计资源的评论数' />
   </#if>
 </div>
<script>
  var blnIsChecked = true;
function select_all(list_form){
  for (var i = 0; i < list_form.elements.length; i++) {
    if (list_form.elements[i].type == "checkbox" && !list_form.elements[i].disabled) {
    }
    list_form.elements[i].checked = blnIsChecked;
  }
  if(list_form.elements["selAll"]) {
    if(blnIsChecked) {
      list_form.elements["selAll"].value = "取消全选";
    } else {
      list_form.elements["selAll"].value = "全部选择"; 
    }
  }
  blnIsChecked = !blnIsChecked;
}

function hasChecked() {
  // 检查是否有选择.
  var ids = document.getElementsByName("commentId");
  var hc = false;
  for(i = 0;i<ids.length;i++){
    if(ids[i].checked){
      hc = true;
      break;
    }
  }
  return hc;
}

function do_delete() {
  if (hasChecked() == false) {
    alert('没有选择要操作的任何评论');
    return;
  }
  if (confirm('您是否确定删除选中的评论?') == false) return;
  doAction('delete_comment');
}

function doActionNC(act) {
  if (hasChecked() == false) {
    alert('没有选择要操作的任何评论');
    return;
  }
  doAction(act);
}
function doAction(act) {
  var theForm = document.forms.commentForm;
  theForm.cmd.value = act;
  theForm.submit();
}
</script>
</form>
 
</body>
</html>
