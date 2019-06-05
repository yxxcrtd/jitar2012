<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>资源管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
  <script type='text/javascript' src='../js/msgbox.js'></script>
  <script type='text/javascript' src='../js/jitar/core.js'></script>
<script>
function close_dialog(event) {
  if (event.keyCode == 27) {
    MessageBox.Close();
  }
}
function publishtozyk(resourceId){
	var url="resource.action?cmd=select_resource_cate&resourceId="+resourceId;
	//window.open(url,"");
	window.showModelessDialog(url,"","dialogHeight:540px;dialogWidth:700px;dialogLeft:50px;dialogTop:50px;resizable:yes;");
}

</script>

</head>
<body onkeydown="close_dialog(event);">
<iframe name="hiddenframe" style="display:none"></iframe>
<div id="bgs" >
  <h2>资源管理</h2>
<div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='?cmd=list'>资源管理</a>
</div>

<form action='?' method='get'>
<div id="bgs" class='pager'>
  <#if !(ucid??)><#assign ucid = 0></#if>
  <input type='hidden' name='cmd' value='list' />
  关键字：<input type='text' name='k' value="${k!?html}" size='16' />
  <select name='ucid'>
    <option value=''>个人资源分类</option>
  <#list user_res_cate.all as c>
    <option value='${c.id}' ${(ucid == c.categoryId)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
  </#list>
  </select>
  <input type='submit' class='button' value=' 搜索/过滤 ' />
</div>
</form>
<form name='publishtozykForm' action='${zskUrl!}' target='_blank' method='post'>
  <input type='hidden' name='cmd' value='publishtozsk' />
  <input type='hidden' name='title' value='' />
  <input type='hidden' name='reshref' value='' />
  <input type='hidden' name='catatoryId' value='' />
</form>

<form name="resourceForm" action="?" method="post">
<input type="hidden" name="cmd" value="" />  
<table class="listTable" cellspacing="1">
<thead>
<tr>
    <th><nobr>选择</nobr></th>
    <th><nobr>[发布方式]资源名称</nobr></th>
    <th><nobr>协作组</nobr></th>
    <th><nobr>学科/学段</nobr></th>
    <th><nobr>系统分类/个人分类</nobr></th>
    <th><nobr>上传日期</nobr></th>
    <th><nobr>大小</nobr></th>
    <th><nobr>下载/评论</nobr></th>
    <#if isHaszykUrl=='true'><th><nobr>发布到</nobr></th></#if>
    <th><nobr>状态</nobr></th>
    <th><nobr>操作</nobr></th>
</tr>
</thead>  
<tbody>
<#list resource_list as resource>
    <tr>
        <td style="text-align: center;">
            <input type="checkbox" name="resourceId" value="${resource.resourceId}" />
        </td>
        <td>
            <#if resource.shareMode == 1000>[完全]<#elseif resource.shareMode == 500>[组内]<#else>[私有]</#if><a href="../showResource.action?resourceId=${resource.resourceId}&shareMode=${resource.shareMode}" target="_blank"><img src="${Util.iconImage(resource.href!'',1)}" border="0" align="absmiddle" hspace="2" />${resource.title!?html}</a>                    
        </td>
		<td align="center">
			<nobr><a href="#" onclick="opengroups(${resource.resourceId});return false;">查看</a></nobr>
		</td>
        <td style="text-align: center;">
            <nobr><#if resource.gradeId??>${Util.gradeById(resource.gradeId).gradeName!?html}</#if><#if resource.gradeId?? || resource.subjectId??>/</#if><#if resource.subjectId??>${Util.subjectById(resource.subjectId).msubjName!?html}</#if></nobr>
        </td>
        <td style="text-align: center;">
            <nobr><#if resource.systemCategory??>${resource.systemCategory.name!?html}</#if><#if resource.gradeId?? || resource.subjectId??>/</#if><#if resource.userCategory??>${resource.userCategory.name!?html}</#if></nobr>
        </td>
        <td style="text-align: center;">
           <nobr> ${resource.createDate?string('yyyy-MM-dd')}</nobr>
        </td>
        <td style="text-align: center;">
            <nobr>${Util.fsize(resource.fsize!)}</nobr>
        </td>
        <td style="text-align: center;">
           <nobr> ${resource.downloadCount} / ${resource.commentCount}</nobr>
        </td>
      	<#if isHaszykUrl=='true'>
      	<td style="text-align: center;"><nobr>
   	       <#if !resource.publishToZyk>
   	       	  <#if resource.auditState==1>
   	       	  <#else>
   	       	  <a href="#" onclick="publishtozyk(${resource.resourceId});">资源库</a>
   	       	  </#if>	
   	       	  	
   	       	  <!-- 	
  			  <a href="resource.action?cmd=publishtozyk&resourceId=${resource.resourceId}">资源库</a>
  			  -->
			</#if></nobr>
      	</td>
        </#if>
        <td><nobr><#if resource.auditState==1><font color='red'>未审核</font></#if><#if resource.delState><font color='red'>待删除</font></#if></nobr>
        </td>
        <td style="text-align: center;">
         <nobr><a href='?cmd=edit&amp;resourceId=${resource.resourceId}'>编辑</a>&nbsp;<a href='?cmd=comment_list&amp;resourceId=${resource.resourceId}'>评论</a></nobr>
        </td>
    </tr>
</#list>
</tbody>
</table>

<div class="pager">
    <#include "../inc/pager.ftl">
</div>

<div class='funcButton'>
  <input type='button' class='button' value=' 全 选 ' onclick='select_all()' />
  <input type='button' class='button' value=' 上 传 ' onclick='upload_r()' />
  <input type='button' class='button' value=' 删 除 ' onclick='delete_r()' />
  <select name='shareMode'>
    <option value='1000'>完全公开</option>
    <option value='500'>组内公开</option>
    <option value='0'>私有</option>
  </select>
  <input type='button' class='button' value='设置发布方式' onclick='share_r()' />
  <input type='button' class='button' value='移动到...' onclick='show_move_r()' />
  <input type='button' class='button' value='发布到组...' onclick='show_pub_r()' />
  <input type='button' class='button' value='发布到自定义频道...' onclick='show_pub_channel()' />
  <input type='button' class='button' value=' 刷 新 ' onclick='refresh_p()' />
 
<div id="blockUI" onclick="return false" onmousedown="return false" onmousemove="return false" onmouseup="return false" ondblclick="return false">
  &nbsp;
</div>

<#-- 移动分类对话框 -->
<div id='MessageTip' class='message_frame' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='../images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)"><img src='images/dialog.gif' align='absmiddle' hspace='3' />移动资源到目标分类</div>
  </div>
  <div style='padding:10px;'>
    <table width='100%' border='0'>
      <tr>
        <td width='50%'>
          <div>系统分类:</div>
          <select name='sysCateId' size='20' style='width:160px; height:200px;'>
            <option value="-1" selected='selected'>不更改系统分类</option>
            <option value="">取消系统分类</option>
			    <#list res_cate.all as category>
			      <option value="${category.id}">${category.treeFlag2 + category.name!?html}</option>
			    </#list>
          </select>
        </td>
        <td width='50%'>
          <div>个人分类:</div>
          <select name="userCateId" size='20' style='width:160px; height:200px;'>
            <option value="-1" selected='selected'>不更改个人分类</option>
            <option value="">取消个人分类</option>
          <#list user_res_cate.all as category>
            <option value="${category.id}">${category.treeFlag2 + category.name!?html}</option>
          </#list>
          </select>
        </td>
      </tr>
    </table>
  </div> 
  <div style='text-align:center;clear:both;padding:10px'>
    <input type='button' class='button' value = ' 确  定 ' onclick='move_r();' />
    <input type='button' class='button' value = ' 取  消 ' onclick="return MessageBox.Close();" />
  </div>
</div>

<#-- 发布到协作组对话框 -->
<div id='MessageTip_2' class='message_frame' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='../images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)"><img src='images/dialog.gif' align='absmiddle' hspace='3' />发布资源到协作组</div>
  </div>
  <div style='padding:10px;'>
    <table width='100%' border='0'>
      <tr>
        <td align='left' valign='top' width='50%'>
			    <div style="text-align:left;padding-left:7px">选择要发布到的协作组:<br>
			      <select id='pubr_groupId' name="groupId" size='20' style='width:100%;' onchange='g_c(this)'>
			      <#list joined_groups as group>
			        <option value="${group.groupId}">${group.groupTitle!}</option>
			      </#list>
			      </select>
			    </div>
			  </td>
			  <td align='left' valign='top' width='50%'>
          <div style="text-align:left;padding-left:7px">请选择协作组资源分类:<br>
            <div id='gres_loading' style='position:absolute; display:none; background:white; border:1px solid gray; padding:3px; margin:5px;'>
              <img src='../images/loading.gif' align='absmiddle' hspace='3' />请稍等获得协作组资源分类...
            </div>
			      <select id='groupCateId' name="groupCateId" size='20' style='width:100%;'>
	            <option value=''>请选择协作组</option>
			      </select>
		      </div>
		    </td>
		  </tr>
		</table>
  </div>   
  <div style='text-align:center;clear:both;padding:10px'>
    <input type='button' class='button' value = ' 确  定 ' onclick='pub_r();' />
    <input type='button' class='button' value = ' 取  消 ' onclick="return MessageBox.Close();" />
  </div>
</div>


<#-- 发布到频道对话框 -->
<div id='MessageTip_3' class='message_frame' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='../images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)"><img src='images/dialog.gif' align='absmiddle' hspace='3' />发布文章到自定义频道</div>
  </div>
  <div style='padding:10px;'>
  <#if channel_List??>
    <table width='100%' border='0'>
      <tr>
        <td align='left' valign='top' width='50%'>
          <div style="text-align:left;padding-left:7px">选择要发布到的频道:<br>
            <select name="channelId" size='20' style='width:100%;' onchange='get_channel_resource_catetory(this)'>
            <#list channel_List as channel>
              <option value="${channel.channelId}">${channel.title!?html}</option>
            </#list>
            </select>
          </div>
        </td>
        <td align='left' valign='top' width='50%'>
          <div style="text-align:left;padding-left:7px">请选择协作组文章分类:<br>
            <div id='channel_loading' style='position:absolute; display:none; background:white; border:1px solid gray; padding:3px; margin:5px;'>
              <img src='../images/loading.gif' align='absmiddle' hspace='3' />请稍等获得自定义频道资源分类...
            </div>
            <select id="channelCateId" name="channelCateId" size='20' style='width:100%;'>
              <option value=''>请选择自定义频道</option>
            </select>
          </div>
        </td>
      </tr>
    </table>
    <#else>
    您目前没有参加任何自定义频道。
    </#if>
  </div> 
  <div style='text-align:center;clear:both;padding:10px'>
    <input type='button' class='button' value = ' 确  定 ' onclick='pub_channel();' />
    <input type='button' class='button' value = ' 取  消 ' onclick="return MessageBox.Close();" />
  </div>
</div>


</div>
<script>
function opengroups(id)
{
	var toolbarSty = "dialogWidth:800px;dialogHeight:600px;dialogLeft=" + (window.screen.width - 800)/2 + "px;";
	var url = "${SiteUrl}manage/resourceGroupListFrame.py?resourceId="+id;
	var res = window.showModalDialog(url,null,toolbarSty);
	return;
}
function select_all() {
  var ids = document.getElementsByName('resourceId');
  if (ids == null) return;
  var has_set = false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked == false) {
      ids[i].checked = true;
      has_set = true;
    }
  }
  
  if (has_set == false) {
    for (var i = 0; i < ids.length; ++i) {
      ids[i].checked = false;
    }
  }
}
function has_item_selected() {
  var ids = document.getElementsByName('resourceId');
  if (ids == null) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}
function upload_r() {
  window.location = '?cmd=upload';
}
function refresh_p() {
  window.location.reload();
}
function show_move_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源.'); return;
  }
  MessageBox.Show('MessageTip');  
}
function move_r() {
  var f = document.forms.resourceForm;
  var sc = f.sysCateId.options[f.sysCateId.selectedIndex].value;
  var uc = f.userCateId.options[f.userCateId.selectedIndex].value;
  if (sc == '-1' && uc == '-1') {
    alert('即未选择目标系统分类, 也未选择目标个人分类.')
    return;
  }
  MessageBox.Close();
  submit_command('move', true);
}
function show_pub_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源.'); return;
  }
  MessageBox.Show('MessageTip_2');  
}
function pub_r() {
  var f = document.forms.resourceForm;
  if (f.groupId.selectedIndex < 0) {
    alert('请选择发布的目标协作组.');
    return;
  }
  submit_command('pub_res', true);
}
function delete_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源'); return;
  }
  if (confirm('您是否确定要删除选定的资源') == false) return;
  var f = document.forms.resourceForm;
  f.cmd.value = 'delete';
  f.submit();
}
function share_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源'); return;
  }
  var f = document.forms.resourceForm;
  f.cmd.value = 'set_share_mode';
  f.submit();
}

function submit_command(cmd, check) {
  if (check == null || check == true) {
	  if (has_item_selected() == false) {
	    alert('未选择要操作的资源'); return;
	  }
  }
  var f = document.forms.resourceForm;
  f.cmd.value = cmd;
  f.submit();
}

function g_c(sel) {
  var groupId = sel.options[sel.selectedIndex].value;
  if (groupId == null || groupId == '') return;
  group_changed(groupId);
}
function group_changed(groupId) {
  document.getElementById('gres_loading').style.display = '';
  document.getElementById('pubr_groupId').disabled = true;
  
  var url = "?cmd=dest_cate&groupId=" + groupId + '&tmp=' + Math.random();
	var myAjax = new Ajax.Request(
	url, {
		method: 'get',
		onComplete: fill_gres_cate,    // 指定回调函数.
		asynchronous: true             // 是否异步发送请求.
	});
}
function fill_gres_cate(request) {
  try {
    do_fill(request);
  } finally {
    document.getElementById('gres_loading').style.display = 'none';
    document.getElementById('pubr_groupId').disabled = false;
  }
}
function do_fill(request) {
  var sel = document.getElementById('groupCateId');
  sel.length = 0;   // 清空所有选项. ?? 兼容各个浏览器吗??
  
  var gres_categories = eval(request.responseText);
  if (gres_categories == null || gres_categories.length == null || gres_categories.length == 0) {
    add_option(sel, '', '该协作组尚未建立资源分类');
    add_option(sel, '', '您可以直接将资源发布到该协作组');
    return;
  }
  
  for (var i = 0; i < gres_categories.length; ++i) {
    c = gres_categories[i];
    add_option(sel, c.id, c.treeFlag2 + ' ' + c.name);
  }
}
function add_option(sel, val, text) {
  var op = sel.ownerDocument.createElement('option');
  op.value = val;
  op.text = text.replace(/&nbsp;/g," ");;
  sel.options.add(op);
}

//发布到自定义频道
function show_pub_channel() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源.'); return;
  }
  MessageBox.Show('MessageTip_3');  
}
function pub_channel() {
  if (has_item_selected() == false) {
    alert('没有选择要操作的资源.'); return;
  }
  var f = document.forms.resourceForm;
  f.cmd.value = 'pub_to_channel';
  f.submit();
}


function get_channel_resource_catetory(sel)
{
  var channel_Id = sel.options[sel.selectedIndex].value;
  if (channel_Id == '')
  {
   alert("请选择一个自定义频道。");
   return;
  }
  
  $('channel_loading').style.display = '';
  var url = "${SiteUrl}manage/resource.action?cmd=channel_cate&channelId=" + channel_Id + '&tmp=' + (new Date()).getTime();
  var myAjax = new Ajax.Request(
    url, {
    method: 'get',
    onComplete: fill_channel_cate,    // 指定回调函数.
    asynchronous: true             // 是否异步发送请求.
  });
}

function fill_channel_cate(xhr)
{
  $('channel_loading').style.display = 'none';
  var cCateId = $("channelCateId");
  cCateId.options.length = 0;
  var c_categories = eval(xhr.responseText);
  if (c_categories == null || c_categories.length == null || c_categories.length == 0) {
    add_option(cCateId, '', '该自定义频道尚未建立文章分类');
    return;
  }
  add_option(cCateId, '', '删除自定义频道文章分类设置');
  for (var i = 0; i < c_categories.length; ++i) {
    c = c_categories[i];
    add_option(cCateId, c.path, c.treeFlag2 + ' ' + c.name);
  } 
}

</script>	
</form>
</div>
</body>
</html>
