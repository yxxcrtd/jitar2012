<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>用户资源选择</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
  <script type='text/javascript' src='../js/msgbox.js'></script>
  <script type='text/javascript' src='../js/jitar/core.js'></script>
</head>
<body>
<iframe name="hiddenframe" style="display:none"></iframe>
<div id="bgs" >
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

<form name="resourceForm" action="?" method="post">
<input type="hidden" name="cmd" value="userresource" />  
<table class="listTable" cellspacing="1">
    <thead>
        <tr>
            <th width="7%">选&nbsp;择</th>
            <th width="30%">[发布方式]&nbsp;资源名称</th>
            <th width="5%">协作组</th>
            <th width="12%">学科/学段</th>
            <th width="12%">系统分类/个人分类</th>
            <th width="10%">上传日期</th>
            <th width="6%">大小</th>
            <th width="6%">下载/评论</th>
        </tr>
    </thead>  
    <tbody>
        <#list resource_list as resource>
            <tr>
                <td style="text-align: center;">
                    <input type="checkbox" name="resourceId" value="${resource.resourceId}" />${resource.resourceId}
                </td>
                <td>
                    <#if resource.shareMode == 1000>[完全]<#elseif resource.shareMode == 500>[组内]<#else>[私有]</#if><a href="../showResource.action?resourceId=${resource.resourceId}&shareMode=${resource.shareMode}" target="_blank"><img src="${Util.iconImage(resource.href!)}" border="0" align="absmiddle" hspace="2" />${resource.title!?html}</a>
                    <#if resource.auditState==1>&nbsp;[<font color='red'>未审核</font>]</#if>
                </td>
                <td align="center">
                    <a href="#" onclick="opengroups(${resource.resourceId});return false;">查看</a>
                </td>
                <td style="text-align: center;">
                    <#if resource.gradeId??>${Util.gradeById(resource.gradeId).gradeName!?html}</#if><#if resource.gradeId?? || resource.subjectId??>/</#if><#if resource.subjectId??>${Util.subjectById(resource.subjectId).msubjName!?html}</#if>
                </td>
                <td style="text-align: center;">
                    <#if resource.systemCategory??>${resource.systemCategory.name!?html}</#if><#if resource.gradeId?? || resource.subjectId??>/</#if><#if resource.userCategory??>${resource.userCategory.name!?html}</#if>
                </td>
                <td style="text-align: center;">
                    ${resource.createDate?string('yyyy-MM-dd')}
                </td>
                <td style="text-align: center;">
                    ${Util.fsize(resource.fsize!)}
                </td>
                <td style="text-align: center;">
                    ${resource.downloadCount} / ${resource.commentCount}
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
    <input type='button' class='button' value = ' 确  定 ' onclick='selectedResource();' />
    <input type='button' class='button' value = ' 取  消 ' onclick="closeMe();" />
</div>

</div>
<script>
function selectedResource()
{
  if (has_item_selected() == false) {
    alert('未选择资源'); return;
  }
  var s=items_selected();
    window.returnValue=s;
    window.close();        
}
function closeMe()
{
    window.returnValue="";
    window.close();
}
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
function items_selected() {
  var ids = document.getElementsByName('resourceId');
  if (ids == null) return "";
  var s="";
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) 
    {
        if(s==""){
            s=ids[i].value;
        }else{
            s=s+","+ids[i].value;
        }
    }
  }
  return s;
}
function has_item_selected() {
  var ids = document.getElementsByName('resourceId');
  if (ids == null) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
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

function show_pub_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源.'); return;
  }
  MessageBox.Show('MessageTip_2');  
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

</script>   
</form>
</div>
</body>
</html>
