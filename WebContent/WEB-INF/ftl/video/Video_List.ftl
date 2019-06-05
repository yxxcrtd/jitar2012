<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>视频管理</title>
	<link rel="styleSheet" type="text/css" href="../css/manage.css">		
	<link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
	<script type="text/javascript" src="../js/jitar/core.js"></script>
    <script type="text/javascript" src="../js/msgbox.js"></script>
  <script type="text/javascript">
  	function confirmDel(id, delurl) {
  		if (!confirm("<@s.text name="groups.public.delConfirm" />")) {
  			return;
		}	  		
  		if (0 != ${admin}) {
			var toolbarSty = "dialogwidth=240px;dialogheight=160px;scrolling:no;border=no;status=no;help=no";
			var url = "${SiteUrl}PunishScoreConfirm.py?seltype=score.video.adminDel";
			var res = window.showModalDialog(url,null,toolbarSty);
			if(res==undefined){
				res="";
				return;
			}
			var arr=res.split("|");
  			self.document.location.href=delurl+"&amp;score="+arr[0]+"&amp;reason="+encodeURI(arr[1]);
  		} else {
  			self.document.location.href = delurl;
  		}
  	}
  	
  	function InsertPrepareCourse(id)
  	{
		var toolbarSty = "dialogWidth:800px;dialogHeight:500px;dialogLeft=" + (window.screen.width - 800)/2 + "px;";
		var url = "${SiteUrl}SelectMyPrecareCourse.py?id="+id;
		var res = window.showModalDialog(url,null,toolbarSty);
		return;
		if(res==undefined){
			res="";
			return;
		}
		url= "${SiteUrl}SaveVideoToMyPrecareCourse.py?videoid="+id+"&pcid="+res;
		window.open(url,"hiddenframe");
  	}
  </script>  
  
</head>
<body style="margin-top: 20px;">
	<h2>视频管理</h2>
	<iframe name="hiddenframe" style="display:none"></iframe>
	<table align="center">
		<tr>
			<td colspan="2">
				<@s.actionerror cssClass="actionError" />
			</td>
		</tr>
	</table>
	
	<#if videoList?? && videoList?size == 0>
		<#if admin??>
			<#if admin==0>
				<div style="color: #0000FF; font-weight: bold; text-align: center; padding: 10px;"">
					暂无视频，请<@s.a href="video.action?cmd=upload">上传视频</@s.a>!!
				</div>
			<#else>
			</#if>
		<#else>
		<div style="color: #0000FF; font-weight: bold; text-align: center; padding: 10px;"">
			暂无视频，请<@s.a href="video.action?cmd=upload">上传视频</@s.a>！
		</div>
		</#if>
	</#if>
	<form name="videoForm" id="videoForm" action="video.action">
	<div style="text-align: right; width: 100%;">
			关键字：
			<input type="text" size="30" name="k" value="${k!?html}" onMouseOver="this.select();" />
			<#if !(f??)><#assign f = '0'></#if>
            <select name="f">
                <option value="0" ${(f == '0')?string('selected', '')}>视频标题</option>
                <option value="1" ${(f == '1')?string('selected', '')}>视频分类</option>
                <option value="2" ${(f == '2')?string('selected', '')}>上传用户</option>
            </select>
            <#if !(auditState??)><#assign auditState = '-1'></#if>
            <select name="auditState">
                <option value="-1" ${(auditState == '-1')?string('selected', '')}>审核状态</option>
                <option value="0" ${(auditState == '0')?string('selected', '')}>已审核</option>
                <option value="1" ${(auditState == '1')?string('selected', '')}>未审核</option>
            </select>
	  		<input type="submit" class="button" value="检  索" />
	</div>		
		<#if admin??>
			<#if admin==0>
				<input type="hidden" name="cmd" value="list"/>
			<#else>
				<input type="hidden" name="cmd" value="admin_list"/>
			</#if>
		<#else>
			<input type="hidden" name="cmd" value="list"/>
		</#if>
	</form>
	<form method="post" action="video.action" id="actionVideo" name="actionVideo">
	<input type="hidden" name="cmd" value=""/>
		<table class="listTable" cellSpacing="1">
			<thead>
				<tr>
					<th width="7%">选择</th>
					<th width="10%">视频截图</th>
					<th width="7%">视频状态</th>
                    <th width="20%">视频标题</th>
                    <th width="6%">评论</th>
                    <th width="6%">播放</th>
                    <th width="11%">上传者</th>
                    <th width="10%">系统分类</th>
                    <th width="7%">推送</th>
					<th width="7%">审核状态</th>
					<th width="8%">操&nbsp;&nbsp;作</th>
				</tr>
			</thead>
			<#if videoList??>
			<tbody>
				<#if videoList?size == 0>
				<tr>
					<td colSpan="10" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
						没有符合条件的视频信息！
					</td>
				</tr>
				</#if>
				
				<#list videoList as video>
				<tr>
					<td style="text-align: center;">
						<input type="checkbox" name="vId" value="${video.videoId}" /><br/>
						${video.videoId}
					</td>
					<td style="text-align: center;">
					   <a href="?cmd=show&amp;videoId=${video.id}" target="_blank"><img src="${video.flvThumbNailHref!}" border="none" /></a>
					</td>
                    <td style="text-align: center;">
                        <#if (-1 == video.status)>文件非法
                        	<#elseif (0 == video.status)>待转换
                        	<#elseif (1 == video.status)><font style="color: #00FF00;">转换成功</font>
                        	<#elseif (2 == video.status)>未转换完成
                        	<#elseif (3 == video.status)>正在转换...
                        </#if>
                    </td>
                    <td style="padding-left: 10px;">
                        ${video.title!?html}
                    </td>
                    <td style="text-align: center;">
                    	${video.commentCount}
                    </td>
                    <td style="text-align: center;">
                        ${video.viewCount}
                    </td>
                    <td style="text-align: center;">
                        ${userlist['v'+video.videoId]!}
                        <br />
                        ${video.createDate}
                    </td>
                    <td style="text-align: center;">
                     <#if video.categoryId?? && video.sysCate??><a target="_blank" href='${SiteUrl}videos.py?categoryId=${video.categoryId}'>${video.sysCate.name}</a></#if>
                    </td>
                    <td style="text-align: center;">
	                    <#if video.auditState==0>
	                    	<a href="#" onclick="InsertPrepareCourse(${video.videoId});return false;" title="推送到我参与的集备">推送</a>
	                    </#if>
                    </td>
					<td style="text-align: center;">
					   <#if video.auditState==0>
					   	已审核
					   <#elseif video.auditState==1>
					    <font color=red>待审核</font>
					   <#elseif video.auditState==2>
					   </#if>
					</td>
					<td style="text-align: center;">
    					<a href="video.action?cmd=edit&videoId=${video.id}&admin=${admin!?html}">编辑</a>&nbsp;&nbsp;
    					<a href="#" onClick="confirmDel(${video.id},'?cmd=<#if admin??><#if admin==1>delete_videos<#else>del</#if></#if>&vId=${video.id}');">删除</a>
					</td>
				</tr>
				</#list>
			</tbody>
			</#if>
		</table>
<#-- 发布到协作组对话框 -->
<div id='MessageTip_2' class='message_frame' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='../images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)"><img src='images/dialog.gif' align='absmiddle' hspace='3' />发布视频到协作组</div>
  </div>
  <div style='padding:10px;'>
<table width='100%' border='0'>
  <tr>
    <td align='left' valign='top' width='50%'>
            <div style="text-align:left;padding-left:7px">选择要发布到的协作组:<br>
              <select id='pubr_groupId' name="groupId" size='20' style='width:100%;' onchange='g_c(this)'>
              <#if joined_groups??>
              <#list joined_groups as group>
                <option value="${group.groupId}">${group.groupTitle!}</option>
              </#list>
              </#if>
              </select>
            </div>
          </td>
          <td align='left' valign='top' width='50%'>
      <div style="text-align:left;padding-left:7px">请选择协作组视频分类:<br>
        <div id='gres_loading' style='position:absolute; display:none; background:white; border:1px solid gray; padding:3px; margin:5px;'>
          <img src='../images/loading.gif' align='absmiddle' hspace='3' />请稍等获得协作组视频分类...
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

		<div style="width: 98%; text-align: center; margin: 3px auto 3px;">
    		<#include "../inc/pager.ftl">
		</div>
        <input type="button" class='button' name='selAll' value=' 全 选 ' onclick="select_all(document.getElementById('actionVideo'))" />
        <input type='button' class='button' value=' 上 传 ' onclick='upload_r()' />
        <input type='button' class='button' value=' 删 除 ' onclick='do_delete()' />
        
	<#if admin??>
	<#if admin==1>
	   <input type="button" class='button' value="通过审核" onclick="doActionNC('audit_video')" />
	   <input type="button" class='button' value="取消审核" onclick="doActionNC('unaudit_video')" />
	</#if>
	</#if>	
	<input type='button' class='button' value='发布到组...' onclick='show_pub_r()' />
	<input type='button' class='button' value='发布到自定义频道...' onclick='show_pub_channel()' />
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
	  var ids = document.getElementsByName("vId");
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
	    alert('没有选择要删除的视频');
	    return;
	  }
	  if (confirm('您确定删除选中的视频?') == false) return;
	  doAction('delete_videos');
	}
	
	function doActionNC(act) {
	  if (hasChecked() == false) {
	    alert('没有选择');
	    return;
	  }
	  doAction(act);
	}	
	function doAction(act) {	
	  var f = document.getElementById('actionVideo');
	  f.method="POST"
	  f.cmd.value = act;
	  f.submit();
	}	
function has_item_selected() {
  var ids = document.getElementsByName('vId');
  if (ids == null) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}	
function upload_r() {
  window.location = '?cmd=upload';
}
function show_pub_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的视频.'); return;
  }
  MessageBox.Show('MessageTip_2');  
}	
function pub_r() {
  var f = document.forms.actionVideo;
  if (f.groupId.selectedIndex < 0) {
    alert('请选择发布的目标协作组.');
    return;
  }
  submit_command('pub_group', true);
}
function submit_command(cmd, check) {
  if (check == null || check == true) {
      if (has_item_selected() == false) {
        alert('未选择要操作的视频'); return;
      }
  }
  var f = document.forms.actionVideo;
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
    add_option(sel, '', '该协作组尚未建立视频分类');
    add_option(sel, '', '您可以直接将视频发布到该协作组');
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
    alert('未选择要操作的视频.'); return;
  }
  MessageBox.Show('MessageTip_3');  
}
function pub_channel() {
  if (has_item_selected() == false) {
    alert('没有选择要操作的视频.'); return;
  }
  var f = document.forms.actionVideo;
  f.cmd.value = 'pub_to_channel';
  f.submit();
}



function get_channel_video_catetory(sel)
{
  var channel_Id = sel.options[sel.selectedIndex].value;
  if (channel_Id == '')
  {
   alert("请选择一个自定义频道。");
   return;
  }
  
  $('channel_loading').style.display = '';
  var url = "${SiteUrl}manage/video.action?cmd=channel_cate&channelId=" + channel_Id + '&tmp=' + (new Date()).getTime();
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
<div id="blockUI" onclick="return false" onmousedown="return false" onmousemove="return false" onmouseup="return false" ondblclick="return false">
  &nbsp;
</div>

<#-- 对话框 -->
<div id='MessageTip' class='message_frame' style='width:260px;'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='../images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)"><img src='images/dialog.gif' align='absmiddle' hspace='3' />信息提示</div>
  </div>
  <div style='padding:20px;' id='MsgDiv'>
        
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
            <select name="channelId" size='20' style='width:100%;' onchange='get_channel_video_catetory(this)'>
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
</form> 
<br/><br/><br/>
</body>
</html>
