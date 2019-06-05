<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>评论管理</title>
	<link rel="stylesheet" href="../css/manage.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="../js/msgbox.js"></script>
     <!-- 配置上载路径 -->
    <script type="text/javascript">
        window.UEDITOR_UPLOAD_URL = "${SiteUrl}";
        window.UEDITOR_USERLOGINNAME = "<#if loginUser??>${loginUser.loginName!?js_string}</#if>";
    </script>
    <!-- 配置文件 -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.all.js"></script>
    <!-- 语言包文件(建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败) -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/lang/zh-cn/zh-cn.js"></script>  	
<script type="text/javascript">
<!--
var blnIsChecked = true;
function on_checkAll() {
  for (var i = 0; i < document.getElementsByName("guid").length; i++) {
	document.getElementsByName("guid")[i].checked = blnIsChecked;
  }
  blnIsChecked = !blnIsChecked;
}

function delSel() {
  var f = document.commentForm;
  if (hasChecked(f) == false) 
  {
    alert("没有选择要操作的评论.");
    return false;
  } 
  else 
  {
	  if(confirm("您是否确定要删除所选评论??"))
	  {
	   	f.cmd.value = "delete";
		f.submit();
	  }
  }
}
function hasChecked(vform) {
  var ids = document.getElementsByName("guid");
  for (var i = 0; i < ids.length; i++) {
    var e = ids[i];
    if (e.checked) {
        return true;
    }
  }
  return false;
}

//回复
function saveReplayComment(cmt)
{
 document.commentForm.cmd.value = "reply";
 document.commentForm.cmtId.value = cmt; 
}

//-->
</script>
</head>
<body>
<h2>相册评论管理</h2>
<#assign canManage = (loginUser.userStatus == 0) >

<div class='funcButton'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; 相册评论
</div>

<form name="commentForm" method="post">
<input type="hidden" name="cmd" value="" />
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
		<#list photo_comment_list as comment >
		<tr class="backNormalColor">
			<td>
				<input type="checkbox" name="guid" onclick="checkSelf(this)" value="${comment.id}" />
			</td>
			<td>
				<div class="articleTitle">
					图片标题：<a href="${SiteUrl}go.action?loginName=${user.loginName}/py/user_photo_show.py?photoId=${comment.objId}" target="_blank">${comment.title?html}</a> 
				</div>
				<div class="">
					评论者：<a href='${SiteUrl}go.action?loginName=${comment.loginName}' target='_blank'>${comment.trueName!}</a>&nbsp;&nbsp;&nbsp;&nbsp; 评论时间：${comment.createDate?string('yyyy-MM-dd HH:mm:ss')}<br />
					评论内容：
				</div>
				<div class="commentContent">
					${comment.content!}
				</div>
			</td>
			<td>
			 <#if comment.audit>审核通过<#else><font color='red'>未审核</font></#if>
			 <br/>
				${comment.trueName!?html}
				<br />
				${comment.ip!}
			</td>
			<td align="center">
		<#if canManage>
			<a href="" onclick="saveReplayComment(${comment.id});MessageBox.Show('MessageTip');return false;">回复</a>
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
<#if canManage>
    <input class="button" onClick="on_checkAll()" type="button" value=" 全 选 ">
	<input type="button" class='button' value="删除评论" onclick="delSel()" />
	<input type="button" class='button' value="通过审核" onclick="doAction('audit_comment')" />
	<input type="button" class='button' value="取消审核" onclick="doAction('unaudit_comment')" />
</#if>
</div>
<script>
<!--
function doAction(act) {
  var theForm = document.commentForm;
  theForm.cmd.value = act;
  theForm.submit();
}
//-->
</script>

<div id="blockUI" onclick="return false" onmousedown="return false" onmousemove="return false" onmouseup="return false" ondblclick="return false">
  &nbsp;
</div>
<div id='MessageTip' class='message_frame' style='width:600px;' onkeydown='MessageBox.Close()'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='../images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)"><img src='images/dialog.gif' align='absmiddle' hspace='3' />请输入回复内容</div>
  </div>
  <div style='padding:20px;'>
  	<input type='hidden' name='cmtId' value='' />

    <script id="DHtml" name="content" type="text/plain" style="width:540px;height:500px;">
    </script>
    <script type="text/javascript">
        var editor = UE.getEditor('DHtml');
    </script>  
           
  </div> 
  <div style='text-align:center;clear:both;padding:10px'>
  	<input type="submit" class='button' value="提交回复" />
    <input type='button' class='button' value = ' 取  消 ' onclick="return MessageBox.Close();" />
  </div>
</div>
</form>
</body>
</html>
