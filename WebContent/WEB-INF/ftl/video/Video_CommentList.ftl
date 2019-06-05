<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	  <title>视频评论管理</title>
	  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
	  <link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
	  <script type="text/javascript" src="../js/common.js"></script>
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
  	function confirmDel(id)
  	{
  		if (!confirm("您确定要删除该评论吗?"))
  			return;
  		<#if admin??>	
	  		<#if admin==1>
				var toolbarSty = "dialogwidth=240px;dialogheight=160px;scrolling:no;border=no;status=no;help=no";
				var url = "${SiteUrl}PunishScoreConfirm.py?seltype=score.comment.adminDel";
				var res = window.showModalDialog(url,null,toolbarSty);
				if(res && res.indexOf("|")>-1){			
				var arr=res.split("|");
		  		window.location.href="?cmd=delete_comment&commentId="+id+"&score="+arr[0]+"&reason="+encodeURI(arr[1]);
		  	<#else>
		  		window.location.href="?cmd=delete_comment&commentId="+id;	
	  		</#if>
	  	<#else>
	  		window.location.href="?cmd=delete_comment&commentId="+id;	
  		</#if>
  		}
  	}
  </script>
	  
	</head>

	<body>
	<h2>视频评论管理</h2>
	<#if my != 0>
	<div class='funcButton'>
	  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
	  &gt;&gt; <a href='video.action?cmd=list'>视频管理</a>
	  &gt;&gt; <a href='video.action?cmd=comment_list'>视频评论管理</a>
	</div>
	</#if>
	
	<form name='commentForm' method='post' action="video.action">
	<table id="ListTable" cellspacing="1" class='listTable'>
	  <thead>
	    <tr>
	      <th width='32'></th>
	      <th>评论</th>
	      <th width='80'>评论者</th>
	      <th width='24'>状态</th>
	      <th width='80'>操作</th>
	    </tr>
	  </thead>
	  <tbody>
	  <#list videoCommentList as data>
	    <#assign comment = data[0]>
	    <#assign resource = data[1]>
	    <tr class="backNormalColor">
	      <td><input type='checkbox' name='commentId' value="${comment.id}" />${comment.id}</td>
	      <td>
	        <div class='articleTitle'>视频: <a href='?cmd=comment_list&amp;videoId=${resource.videoId}'>${resource.title!?html}</a> (发表于 ${resource.createDate})</div>
	        <!--<div class='commentTitle'>评论标题: ${comment.title!?html}-->
	        评论者：<#if comment.userId?? ><a href='${SiteUrl}go.action?userId=${comment.userId}' target='_blank'>${comment.userName!}</a>
	          <#else >${comment.userName!'匿名用户'}</#if> &nbsp;&nbsp;&nbsp;&nbsp;发表于 ${comment.createDate}</div>
	        <div class='commentContent'>${comment.content!}</div>
	      </td>
	      <td>
	        <#if comment.userId?? ><a href='${SiteUrl}go.action?userId=${comment.userId}' target='_blank'>${comment.userName!}</a>
	          <#else >${comment.userName!'匿名用户'}</#if>
	        <br/>${comment.ip!}</td>
	      <td>
	        ${comment.audit?string('', '待审')}
	        </td>
	      <td>
	      <#if my==0>
	      	<a href="" onclick="saveReplayComment(${comment.id});MessageBox.Show('MessageTip');return false;">回复</a>
	      </#if>	
	        <a href='?cmd=edit_comment&amp;commentId=${comment.id}'>修改</a>
    	    <a href='#' onclick="confirmDel(${comment.id})">删除</a>
	      </td>
	    </tr>
	  </#list>
	  </tbody>
	  </table>
	  
	 <div class="pager" style='text-align:right; width:98%; padding:2px;'>
	  <#include '../inc/pager.ftl' >
	 </div>
	  
	 <div class="funcButton">
	   <#if admin??>
	   		<#if admin==1>
	   			<input type='hidden' name='cmd' value='comment_admin_list' />
	   		<#else> 
	   			<input type='hidden' name='cmd' value='comment_list' />
	   		</#if>	
	   <#else>	
	   		<input type='hidden' name='cmd' value='comment_list' />
	   </#if>
	   <input type="button" class='button' name='selAll' value='全部选择' onclick="select_all(commentForm)" />
	   <input type="button" class='button' value='删除评论' onclick="do_delete()" />
	   <#if admin??>
	   		<#if admin==1>
			   <input type="button" class='button' value="通过审核" onclick="doActionNC('audit_comment')" />
			   <input type="button" class='button' value="取消审核" onclick="doActionNC('unaudit_comment')" />
			</#if>   
		</#if>	
	   <#if admin??>
	   		<#if admin==1>
	   			<input type='button' class='button' value='重新统计评论数' onclick="doAction('comment_admin_stat')" title='重新统计视频的评论数' />
	   		<#else> 
	   			<#if my==0>
	   				<input type='button' class='button' value='重新统计评论数' onclick="doAction('comment_stat')" title='重新统计视频的评论数' />
	   			</#if>	
	   		</#if>	
	   <#else>
	   		<input type='button' class='button' value='重新统计评论数' onclick="doAction('comment_stat')" title='重新统计视频的评论数' />
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
	
//回复
function saveReplayComment(cmt)
{
 document.commentForm.cmd.value = "reply_comment";
 document.commentForm.cmtId.value = cmt; 
}
	
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

        <script id="DHtml" name="content" type="text/plain" style="width:840px;height:500px;">
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








