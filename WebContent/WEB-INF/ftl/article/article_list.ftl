<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文章管理</title>
<link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
  <script type='text/javascript' src='../js/msgbox.js'></script>
  <script type='text/javascript' src='../js/jitar/core.js'></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript">
	<!--
		var blnIsChecked = true;
		function select_all(list_form) {
 		for (var i = 0; i < list_form.elements.length; i++) {
			if (list_form.elements[i].type == "checkbox" && !list_form.elements[i].disabled) {
			}
			list_form.elements[i].checked = blnIsChecked;
		}
 		blnIsChecked = !blnIsChecked;
	}
	function confirm_delete() {
 			return confirm("你确定要删除选择的文章吗??\r\n\r\n注意：删除之后只能通过系统管理员在回收站中才能恢复该文章.");
	}
  // 检查是否有选择.
	function has_item_selected() {
    var ids = document.getElementsByName("articleId");
    for (i = 0;i<ids.length;i++) {
      if(ids[i].checked)
        return true;
    }
    return false;
	}
	function doAction(actionType) {
		var ids = document.getElementsByName("id");
		if (has_item_selected() == false) {
 			alert("没有选择要操作的文章.")
 			return;
		}
		if (actionType == "delete_article") {
  		if (confirm_delete() == false) {
    			return;
  		}
		}
		submit_command(actionType);
	}
	function submit_command(cmd) {
	  var f = document.forms.BlogArticle;
	  f.cmd.value = cmd;
	  f.submit();
	}
function show_pub_a() {
  if (has_item_selected() == false) {
    alert('未选择要操作的文章.'); return;
  }
  MessageBox.Show('MessageTip_2');  
}
function pub_a() {
  if (has_item_selected() == false) {
    alert('没有选择要操作的文章.'); return;
  }
  var f = document.forms.articleForm;
  f.cmd.value = 'pub_to_group';
  f.submit();
}

//发布到自定义频道
function show_pub_channel() {
  if (has_item_selected() == false) {
    alert('未选择要操作的文章.'); return;
  }
  MessageBox.Show('MessageTip_3');  
}
function pub_channel() {
  if (has_item_selected() == false) {
    alert('没有选择要操作的文章.'); return;
  }
  var f = document.forms.articleForm;
  f.cmd.value = 'pub_to_channel';
  f.submit();
}

//-->
</script>
</head>
<body>
<h2>文章管理</h2>
<#assign canManage = (loginUser.userStatus == 0) >
<div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='?cmd=list'>文章管理</a><span style="color:red;font-weight:bold;margin-left:100px;">注意：未审核、草稿、待删除的文章，将在年终归档时进行删除。</span>
</div>

<form name="search_form" method="get" action="?">
  <input type='hidden' name='cmd' value='list' />
	<div class="search">
		输入关键字：<input name="k" value="${k!?html}" size="12" />
		<select name="f">
		  <#if !(f??) ><#assign f = 0></#if>
			<option value="0">全部状态</option>
			<optgroup label="审核状态">
				<option value="1" ${(f==1)?string('selected', '')}>已审核</option>
				<option value="2" ${(f==2)?string('selected', '')}>未审核</option>
			</optgroup>
			<optgroup label="精华状态">
				<option value="3" ${(f==3)?string('selected', '')}>精华</option>
				<option value="4" ${(f==4)?string('selected', '')}>非精华</option>
			</optgroup>
			<optgroup label="草稿状态">
				<option value="5" ${(f==5)?string('selected', '')}>草稿</option>
				<option value="6" ${(f==6)?string('selected', '')}>非草稿</option>
			</optgroup>
		</select>

		<select name="sc">
			<option value="">选择系统分类</option>
			<#list article_categories.all as category>
			<option value="${category.id}" ${(category.id == sc!0)?string('selected', '')} >
				${category.treeFlag2} ${category.name?html}
			</option>
			</#list>
		</select>

		<select name="uc">
			<option value="">选择个人分类</option>
			<#list usercate_tree.all as category>
			<option value="${category.id}" ${(category.id == uc!0)?string('selected', '')}>
				${category.treeFlag2} ${category.name?html}
			</option>
			</#list>
		</select>
		<input type='submit' class='button' value=" 搜索 " />
	</div>
</form>

<form name="articleForm" method="post" id="BlogArticle" action="?">
<input type='hidden' name='cmd' value='list' />
<table id="ListTable" class="listTable">
	<thead>
		<tr>
			<th style="width:17px"><input type='checkbox' onclick="select_all(articleForm)" /></th>
			<th>文章标题</th>
			<th>协作组</th>
			<th>系统分类/个人分类</th>
			<th>评论/点击</th>
			<th>发表时间</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	<#if article_list?size == 0>
		<tr>
			<td colspan='7' align='center' valign='center' style='padding:12px'>
				<div>没有找到符合条件的文章</div>
			</td>
		</tr>
	<#else>
	<#list article_list as article>
		<tr class="backNormalColor">
			<td>
				<input type="checkbox" name="articleId" onclick="checkSelf(this)" value="${article.articleId}" />
			</td>
			<td>
				<#if article.typeState == false>[原创]<#else>[转载]</#if>
				<a href="${SiteUrl}showArticle.action?articleId=${article.articleId}" target="_blank">${article.title!?html}</a>
				<#if article.bestState><img src='images/ico_best.gif' border='0' align='absmiddle' hspace='2'/></#if>
				<#if article.recommendState><img src='images/ico_rcmd.gif' border='0' align='absmiddle' hspace='2'/></#if>
			</td>
			<td align="center">
				<nobr><a href="#" onclick="opengroups(${article.articleId});return false;">查看</a></nobr>
			</td>
			<td align="center">
			<nobr>
			<#if article.sysCateId??><#assign syscate = Util.getCategory(article.sysCateId)><#if syscate??>${(syscate.name!)}</#if></#if>/
			<#if article.userCateId??><#assign usercate = Util.getCategory(article.userCateId)><#if usercate??>${(usercate.name!)}</#if></#if>
			</nobr>
			</td>
			<td align="center">
				<nobr>${article.commentCount} / ${article.viewCount}</nobr>
			</td>
			<td align="center">
				<nobr>${article.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr>
			</td>
			<td align="center">
				<nobr>
				<#if article.auditState != 0><font color='red'>待审</font></#if>
				<#if article.hideState != 0><font color='gray'>隐藏</font></#if>
				<#if article.draftState><font color='blue'>草稿</font></#if>
				<#if article.delState><font color="red">待删除</font></#if>
				</nobr>
			</td>
			<td align="center">
				<nobr>
				<#if canManage>
					<a href="?cmd=edit&amp;articleId=${article.articleId}">修改</a>
					<a onclick="return confirm_delete()" href="?cmd=delete&amp;articleId=${article.articleId}">删除</a>
				</#if>
				</nobr>
			</td>
		</tr>
	</#list>
	</#if>
	</tbody>
</table>

<div class="pager">
	<#include "../inc/pager.ftl" >
</div>

<div class="funcButton">
	<input type="hidden" class='button' name="funcType" value="" />
<#if canManage>
	<input type="button" class='button' value=" 全 选 " onclick="select_all(articleForm)" id="selAll" />
	<input type="button" class='button' value=" 删 除 " onclick="doAction('delete')" />
	<input type="button" class='button' value="设为隐藏" onclick="doAction('hide')" />
	<input type="button" class='button' value="取消隐藏" onclick="doAction('unhide')" />
	<input type="button" class='button' value="设为草稿" onclick="doAction('draft')" />
	<input type="button" class='button' value="取消草稿" onclick="doAction('undraft')" />
	<input type="button" class='button' value="移动分类..." onclick="show_move_a()" />
 	<input type='button' class='button' value='发布到组...' onclick='show_pub_a()' />
 	<input type='button' class='button' value='发布到自定义频道...' onclick='show_pub_channel()' />
</#if>
	<input type="button" class='button' value="重新统计" onclick="submit_command('stat')" title="重新统计文章总数和每个分类下文章数等" />
</div>

<script>
function show_move_a() {
  if (has_item_selected() == false) {
    alert('未选择要操作的文章.'); return;
  }
  MessageBox.Show('MessageTip');  
}
function move_a() {
  var f = document.forms.articleForm;
  var sc = f.sysCateId.options[f.sysCateId.selectedIndex].value;
  var uc = f.userCateId.options[f.userCateId.selectedIndex].value;
  if (sc == '-1' && uc == '-1') {
    alert('即未选择目标系统分类, 也未选择目标个人分类.')
    return;
  }
  MessageBox.Close();
  submit_command('move', true);
}

function opengroups(id)
{
	var toolbarSty = "resizable:1;dialogWidth:800px;dialogHeight:600px;dialogLeft:100px;";
	var url = "${SiteUrl}manage/articleGroupListFrame.py?articleId="+id;
	window.showModalDialog(url,"",toolbarSty);
}
</script>

<div id="blockUI" onclick="return false" onmousedown="return false" onmousemove="return false" onmouseup="return false" ondblclick="return false">
  &nbsp;
</div>

<#-- 移动分类对话框 -->
<div id='MessageTip' class='message_frame' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='../images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)"><img src='images/dialog.gif' align='absmiddle' hspace='3' />移动文章到目标分类</div>
  </div>
  <div style='padding:10px;'>
    <table width='100%' border='0'>
      <tr>
        <td width='50%'>
          <div>系统分类:</div>
          <select name='sysCateId' size='20' style='width:160px; height:200px;'>
            <option value="-1" selected='selected'>不更改系统分类</option>
            <option value="">取消系统分类</option>
          <#list article_categories.all as category>
            <option value="${category.id}">${category.treeFlag2 + category.name!?html}</option>
          </#list>
          </select>
        </td>
        <td width='50%'>
          <div>个人分类:</div>
          <select name="userCateId" size='20' style='width:160px; height:200px;'>
            <option value="-1" selected='selected'>不更改个人分类</option>
            <option value="">取消个人分类</option>
          <#list usercate_tree.all as category>
            <option value="${category.id}">${category.treeFlag2 + category.name!?html}</option>
          </#list>
          </select>
        </td>
      </tr>
    </table>
  </div> 
  <div style='text-align:center;clear:both;padding:10px'>
    <input type='button' class='button' value = ' 确  定 ' onclick='move_a();' />
    <input type='button' class='button' value = ' 取  消 ' onclick="return MessageBox.Close();" />
  </div>
</div>

		

<#-- 发布到协作组对话框 -->
<div id='MessageTip_2' class='message_frame' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='../images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)"><img src='images/dialog.gif' align='absmiddle' hspace='3' />发布文章到协作组</div>
  </div>
  <div style='padding:10px;'>
    <table width='100%' border='0'>
      <tr>
        <td align='left' valign='top' width='50%'>
          <div style="text-align:left;padding-left:7px">选择要发布到的协作组:<br>
            <select id='pubr_groupId' name="groupId" size='20' style='width:100%;' onchange='g_c(this)'>
            <#list joined_groups as group>
              <option value="${group.groupId}">${group.groupTitle!?html}</option>
            </#list>
            </select>
          </div>
        </td>
        <td align='left' valign='top' width='50%'>
          <div style="text-align:left;padding-left:7px">请选择协作组文章分类:<br>
            <div id='gres_loading' style='position:absolute; display:none; background:white; border:1px solid gray; padding:3px; margin:5px;'>
              <img src='../images/loading.gif' align='absmiddle' hspace='3' />请稍等获得协作组文章分类...
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
    <input type='button' class='button' value = ' 确  定 ' onclick='pub_a();' />
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
            <select name="channelId" size='20' style='width:100%;' onchange='get_channel_article_catetory(this)'>
            <#list channel_List as channel>
              <option value="${channel.channelId}">${channel.title!?html}</option>
            </#list>
            </select>
          </div>
        </td>
        <td align='left' valign='top' width='50%'>
          <div style="text-align:left;padding-left:7px">请选择协作组文章分类:<br>
            <div id='channel_loading' style='position:absolute; display:none; background:white; border:1px solid gray; padding:3px; margin:5px;'>
              <img src='../images/loading.gif' align='absmiddle' hspace='3' />请稍等获得自定义频道文章分类...
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
<script>

function get_channel_article_catetory(sel)
{
  var channel_Id = sel.options[sel.selectedIndex].value;
  if (channel_Id == '')
  {
   alert("请选择一个自定义频道。");
   return;
  }
  
  $('channel_loading').style.display = '';
  var url = "${SiteUrl}manage/article.action?cmd=channel_cate&channelId=" + channel_Id + '&tmp=' + (new Date()).getTime();
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

function g_c(sel) {
  var groupId = sel.options[sel.selectedIndex].value;
  if (groupId == null || groupId == '')
  {
  alert("请选择一个协作组。");
   return;
  }
  group_changed(groupId);
}
function group_changed(groupId) {

  document.getElementById('gres_loading').style.display = '';
  document.getElementById('pubr_groupId').disabled = true;
  
  var url = "${SiteUrl}manage/article.action?cmd=dest_cate&groupId=" + groupId + '&tmp=' + Math.random();

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
    add_option(sel, '', '该协作组尚未建立文章分类');
    add_option(sel, '', '您可以直接将文章发布到该协作组');
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
  op.text = text.replace(/&nbsp;/g," ");
  sel.options.add(op);
}
</script>
<br/><br/>
</body>
</html>
