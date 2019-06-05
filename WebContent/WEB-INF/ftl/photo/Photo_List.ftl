<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><@s.text name="groups.photo.manage" /></title>
<link rel="styleSheet" type="text/css" href="../css/manage.css">		
<link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
<script type="text/javascript" src="../js/jitar/core.js"></script>
<script type="text/javascript" src="../js/msgbox.js"></script>
<script type="text/javascript">
	function resizeimg(ImgD, iwidth, iheight, photoId, mysrc) {
 		var image = new Image();
 		image.src = ImgD.src;
 		     		
 		if (image.width > 0 && image.height > 0) {
    		if (image.width / image.height >= iwidth / iheight) {
				if (image.width > iwidth) {
					ImgD.width = iwidth;
           			ImgD.height = (image.height * iwidth) / image.width;
       			} else {
					ImgD.width = image.width;
					ImgD.height = image.height;
				}
				ImgD.alt = image.width + "×" + image.height;
 		   	} else {
				if (image.height > iheight) {
					ImgD.height = iheight;
					ImgD.width = (image.width * iheight) / image.height;
				} else {
					ImgD.width = image.width;
					ImgD.height = image.height;
				}
				ImgD.alt = image.width + "×" + image.height;
			}
　　　　　	ImgD.style.cursor = "pointer"; //改变鼠标指针
　　　　　	ImgD.onclick = function() {
				window.open(mysrc);
				//document.listForm.action = "${SiteUrl}manage/photo.action?cmd=view&photoId=" + photoId
				//document.listForm.submit();
			}
			//点击打开原始图片
			ImgD.title = "点击可在新窗口打开原始图片";
			
			/**
			if (navigator.userAgent.toLowerCase().indexOf("ie") > -1) { // 判断浏览器，如果是IE
				ImgD.title = "使用鼠标滚轮可以缩放图片，点击图片可在新窗口打开";
　　　　　　		ImgD.onmousewheel = function img_zoom() { // 滚轮缩放　　　　　 
					var zoom = parseInt(this.style.zoom, 10) || 100;
					zoom += event.wheelDelta / 12;
					if (zoom > 0)　this.style.zoom = zoom + "%";
　　　　　　　　　　		return false;
　　　　　 		}
　　　  		} else { // 如果不是IE
				ImgD.title = "点击图片可在新窗口打开";
			}*/
		}
	}
	
	//改变状态
	function setShowState(evt,photoID)
	{
		ele = Platform.isIE?window.event.srcElement:evt.target
		document.getElementById("MsgDiv").innerHTML = "正在进行状态的修改，请稍候……";
		MessageBox.Show('MessageTip');
		var postData = 'photoId=' + photoID + '&show=' + ele.checked;
		var url = '${SiteUrl}manage/photo_show_state.py';
    	new Ajax.Request(url, { 
          method: 'post',
          parameters:postData,
          onSuccess:function(xhr){
          	if(xhr.responseText.indexOf("200 OK") == -1)
          	{	          
          		document.getElementById("MsgDiv").innerHTML = '修改数据失败。' + xhr.responseText;
				ele.checked = !ele.checked
          	}
			else
			{
				MessageBox.Close();
			}
          	},
          onFailure:function(xhr){
	          	document.getElementById("MsgDiv").innerHTML = '提交数据发生错误。' + xhr.responseText;
				ele.checked = !ele.checked  
          	}
        }
      );	
	}
		
function select_all() {
  var ids = document.getElementsByName('pId');
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
  var ids = document.getElementsByName('pId');
  if (ids == null) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}
function upload_r() {
  window.location = '?cmd=upload';
}	
function delete_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的图片'); return;
  }
  if (confirm('您是否确定要删除选定的图片') == false) return;
  var f = document.forms.listForm;
  f.cmd.value = 'delete';
  f.submit();
}	

function show_pub_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的图片.'); return;
  }
  MessageBox.Show('MessageTip_2');  
}   
function pub_r() {
  var f = document.forms.listForm;
  if (f.groupId.selectedIndex < 0) {
    alert('请选择发布的目标协作组.');
    return;
  }
  submit_command('pub_group', true);
}
function submit_command(cmd, check) {
  if (check == null || check == true) {
      if (has_item_selected() == false) {
        alert('未选择要操作的图片'); return;
      }
  }
  var f = document.forms.listForm;
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
    add_option(sel, '', '该协作组尚未建立图片分类');
    add_option(sel, '', '您可以直接将图片发布到该协作组');
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

		</script>
</head>

<body style="margin-top: 20px;">
<h2>
<@s.text name="groups.photo.manage" />
</h2>		

<table width="100%" cellSpacing="0" cellPadding="0" border="0">
<tbody>
<tr>
	<td>
		<table cellSpacing="0" cellPadding="12" border="0" align="left">
			<tbody>
				<tr>
					<td width="156" valign="top" align="center">
						<table width="130" cellSpacing="4" cellPadding="0" border="0" align="center" style="table-layout: fixed;">
							<tbody>
								<tr>
									<td>
										<div style="text-align: center; color: #0000FF;">
											默认分类
										</div>
									</td>
								</tr>
								<tr>
									<td width="107" height="92" background="${SiteUrl}images/photo.gif" style="background-repeat: no-repeat;">
										<a href="photo.action?cmd=list&amp;userStapleId=0"><img src="${SiteUrl}${defaultHref!SiteUrl+'images/photo_default.gif'}" style="margin:0 0 2px 18px; border:0px;" width="75" height="75" border="0" /></a>
									</td>
								</tr>
								<tr>
									<td style="text-align: center;">
										${default_photo_int}&nbsp;&nbsp;张照片
									</td>
								</tr>
							</tbody>
						</table>
					</td>								
							
					<#list userPhotoStapleList as staple>
					<#if staple_index + 1 == 5><#break></#if>
					<td width="156" valign="top" align="center">
						<table width="130" cellSpacing="4" cellPadding="0" border="0" align="center" style="table-layout: fixed;">
							<tbody>
								<tr>
									<td>
										<div style="text-align: center;">
											${staple.title!}
										</div>
									</td>
								</tr>
								<tr>
									<td width="107" height="92" background="${SiteUrl}images/photo.gif" style="background-repeat: no-repeat;">
										<a href="photo.action?cmd=list&amp;userStapleId=${staple.userStaple}">
										<img src="${Util.thumbNails(staple.userHref!SiteUrl + 'images/photo_default.gif')}" style="margin:0 0 2px 18px; border:0px;" width="75" height="75" border="0" /></a>
									</td>
								</tr>
								<tr>
									<td style="text-align: center;">
										${staple.count!}&nbsp;&nbsp;张照片
									</td>
								</tr>
							</tbody>
						</table>
					</td>
					</#list>
				</tr>
			</tbody>
		</table>
	</td>
</tr>
<#--
<tr>
	<td style="text-align: right;">
		<@s.a href="photo.action?cmd=allStaple">全部分类>></@s.a>
	</td>
</tr>
-->
</tbody>
</table>
<table align="center">
<tr>
<td colspan="2">
	<@s.actionerror cssStyle="color:#FF0000; font-weight:bold;" />
</td>
</tr>
</table>

<#if photoList?size != 0>
<div style="width: 98%; text-align: center; margin: 3px auto 3px;">
<#include "../inc/pager.ftl">
</div>
<#else>
<div style="color: #0000FF; font-weight: bold; text-align: center; padding: 10px;"">
该相册或分类中暂无照片，请<@s.a href="photo.action?cmd=upload">上传照片</@s.a>！
</div>
</#if>

<@s.form name="listForm" method="post">
<#if photoList.size() != 0>		
 <input type="hidden" name="cmd" value="list"/>
<#-- 定义要显示的列数 columnCount -->
<#assign columnCount = 2>

<#-- 计算显示当前记录集需要的表格行数 rowCount -->
<#if photoList.size() % columnCount == 0>
	<#assign rowCount = (photoList.size() / columnCount) - 1>
<#else>
	<#assign rowCount = (photoList.size() / columnCount)>
</#if>
<#-- 输出表格 -->
<table class="listTable" cellSpacing="1" align="center">					   		
	<#-- 外层循环输出表格的 tr -->
	<#list 0..rowCount as row >
		<tr>
			<#-- 内层循环输出表格的 td  -->
			<#list 0..columnCount - 1 as cell >
				<td style="padding-left: 90px;" width='${100 / columnCount}%'><br />
				<#-- 判断是否存在当前对象：存在就输出；不存在就输出空格 -->
				<#if photoList[row * columnCount + cell]??>						
					<#assign photo = photoList[row * columnCount + cell]>
					
					<div style="padding-left: 1px; padding-bottom: 8px;">
					   <input type="checkbox" name="pId" value="${photo.id}" />
					   ${photo.title!?html}
                    </div>
                    <div>
					   <img onLoad="javascript:resizeimg(this, 250, 375, '${photo.id}', '${Util.url(photo.href!)}')" src="${Util.thumbNails(photo.href!SiteUrl+'images/default.gif')}" />
					</div>
					<div style="padding-left: 0px; padding-top: 8px;">
                       <img src="../images/upd.gif" width="12" height="11" border="0" />&nbsp;<a href="photo.action?cmd=edit&photoId=${photo.id}">编辑</a>
                       <font color="#00FF00">|</font>
                       <img src="../images/dele.gif" width="9" height="9" border="0" />&nbsp;<a href="?cmd=del&photoId=${photo.id}" onClick="return confirm('<@s.text name="groups.public.delConfirm" />');">删除</a>
                       <font color="#00FF00">|</font>&nbsp;${photo.commentCount}&nbsp;条评论&nbsp;<font color="#00FF00">|</font>&nbsp;${photo.viewCount}&nbsp;次浏览 &nbsp;<label><input type='checkbox' value='${photo.id}' onclick='setShowState(event,${photo.id})' ${photo.isPrivateShow?string("checked","''")}/>只在个人空间显示</label><br />
					</div>
                    <div style="padding-left: 0px; padding-top: 8px;">
					   上传时间：${photo.createDate}
					   <#if photo.auditState??>
					   	<#if photo.auditState==1>
					   		<font color="#00FF00"> | </font><font color="#FF0000">未审核</font>
					   	</#if>
					   </#if>	
                    </div>                                    
					<div style="padding-left: 0px; padding-top: 8px; padding-bottom: 20px;">
					   标签：${photo.tags!}
                    </div>
				<#else>
					&nbsp;<br /><br />
				</#if>
				</td>
			</#list>
		</tr>
	</#list>
</table>
<#-- 发布到协作组对话框 -->
<div id='MessageTip_2' class='message_frame' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='../images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)"><img src='images/dialog.gif' align='absmiddle' hspace='3' />发布图片到协作组</div>
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
  <div style="text-align:left;padding-left:7px">请选择协作组图片分类:<br>
<div id='gres_loading' style='position:absolute; display:none; background:white; border:1px solid gray; padding:3px; margin:5px;'>
  <img src='../images/loading.gif' align='absmiddle' hspace='3' />请稍等获得协作组图片分类...
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

</#if>
<div id="blockUI" onclick="return false" onmousedown="return false" onmousemove="return false" onmouseup="return false" ondblclick="return false">
  &nbsp;
</div>
<div class='funcButton'>
  <input type='button' class='button' value=' 全 选 ' onclick='select_all()' />
  <input type='button' class='button' value=' 上 传 ' onclick='upload_r()' />
  <input type='button' class='button' value=' 删 除 ' onclick='delete_r()' />
  <input type='button' class='button' value='发布到组...' onclick='show_pub_r()' /> 
  <input type='button' class='button' value='发布到自定义频道...' onclick='show_pub_channel()' />
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
</@s.form>
<script>
//发布到自定义频道
function show_pub_channel() {
  if (has_item_selected() == false) {
    alert('未选择要操作的图片.'); return;
  }
  MessageBox.Show('MessageTip_3');  
}
function pub_channel() {
  if (has_item_selected() == false) {
    alert('没有选择要操作的图片.'); return;
  }
  var f = document.forms.listForm;
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
  var url = "${SiteUrl}manage/photo.action?cmd=channel_cate&channelId=" + channel_Id + '&tmp=' + (new Date()).getTime();
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
</body>
</html>
