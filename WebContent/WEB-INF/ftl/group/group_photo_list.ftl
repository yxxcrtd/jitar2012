<#assign grpName="协作组">
<#assign grpShowName="小组">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
        <#assign grpShowName="课题">
    <#elseif isKtGroup=="1">
        <#assign grpName="备课组"> 
        <#assign grpShowName="小组">
    <#else>
        <#assign grpName="协作组">
        <#assign grpShowName="小组">
    </#if>
</#if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>图片管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type='text/javascript' src='dialog/msg.js'></script>
  <script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body >
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <span>${grpName}图片管理</span> 
</div>

<#assign can_pub = (group_member?? && group_member.status == 0) >
<#assign can_manage = (group_member?? && group_member.status == 0 && group_member.groupRole >= 800) >

<form action='groupPhoto.action' method='get'>
<div id="bgs"  class='pager'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
  关键字：<input type='text' name='k' value="${k!?html}" size='16' />
  <select name='gcid'>
    <option value=''>${grpName}图片分类</option>
    <#list res_cate.all as category>
        <option ${((gcid!0) == category.categoryId)?string('selected', '')} value='${category.id}'>${category.treeFlag2 + category.name!?html}</option>
    </#list>
  </select>
  <input type='submit' class='button' value=' 搜索/过滤 ' />
</div>
</form>
<form name='theForm' action='groupPhoto.action' method='post'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
    
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='5%'>选择</th>
      <th width='43%'>标题</th>
      <th width='16%'>组内图片分类</th>
      <th width='20%'>上传人/日期</th>
      <th width='12%'>访问次数</th>
    </tr>
  </thead>
  <tbody>
  <#list photo_list as photo>
    <tr>
      <td>
        <input type='checkbox' name='photoId' value='${photo.photoId}' />
      </td>
      <td>
         <a href='${SiteUrl}go.action?photoId=${photo.photoId}' target='_blank'><img onload="CommonUtil.reFixImg(this,120,100)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' border='0' /></a><br />
         <a href='${SiteUrl}go.action?photoId=${photo.photoId}' target='_blank'>${photo.title!?html}</a>
      <#if photo.isGroupBest><img src='images/ico_best.gif' border='0' align='absmiddle' /></#if>
      </td>
      <td>
        <#if photo.groupCategory??>
          ${photo.groupCategory.name!?html}
        </#if>
      </td>
      <td>
        <#assign user = Util.userById(photo.userId)>
        <a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.nickName!?html}</a> (${photo.createDate?string('yyyy-MM-dd')})
      </td>
      <td align='right'>${photo.viewCount}</td>
    </tr>
  </#list>
  </tbody>
</table>
<div class='pager'>
  <#include '../inc/pager.ftl'>
</div>
<div class='funcButton'>
  <input type='hidden' name='groupCateId' id='groupCateId value='' />
<#if can_manage >
  <input type='button' class='button' value=' 全 选 ' onclick='select_all()' />
</#if>
<#if can_pub >
  <input type='button' class='button' value='  上传图片  ' onclick='document.uploadForm.submit();' />
</#if>
  <!-- groupRole = ${group_member.groupRole} -->
<#if can_manage >
  <input type='button' class='button' value=' 删 除 ' onclick='unref_r()' />
  <input type='button' class='button' value='移动到...' onclick='showDialog()' />
  <!--
  <input type='button' class='button' value='设为精华' onclick='best_r()' />
  <input type='button' class='button' value='取消精华' onclick='unbest_r()' />
  -->
  <!--
  <input type='button' class='button' value='平台推荐' onclick='rcmd_r()' />
  <input type='button' class='button' value='取消推荐' onclick='unrcmd_r()' />
  -->
</#if>
</div>

</form>

<form name='uploadForm' action='photo.action' method='get'>
  <input type='hidden' name='cmd' value='upload' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
</form>

<script>
function showDialog()
{
    if (has_item_selected() == false) {
    alert('未选择要操作的图片'); return;
   }
    var htmlContent=" <div>";
    htmlContent+="移动到群组分类:";
    htmlContent+="<select onchange=\"setValue(this.value);\" id=\"groupCateId_1\">";
    htmlContent+="<option value=\"\">群组图片分类</option>";
    htmlContent+=" <option value=\"\">不设置群组图片分类</option>";
    <#list res_cate.all as category>
    htmlContent+=" <option value=\"${category.id}\">${category.treeFlag2 + category.name!?html}</option>";
    </#list>
    htmlContent+=" </select>";
    htmlContent+="  </div>";
    msg("图片移动到群组分类",2,"280",htmlContent,"move_r()")
}
function setValue(val) {
    document.getElementById('groupCateId').value=val;
}


function select_all() {
  var ids = document.getElementsByName('photoId');
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
  var ids = document.getElementsByName('photoId');
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
  var div = document.getElementById('move_photo_to');
  div.style.display = div.style.display == 'none' ? 'block' : 'none';
}
function move_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的图片'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'move';
  f.groupCateId.value = document.getElementById("groupCateId_1").value;
  f.submit();
}
function unref_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的图片'); return;
  }
  if (confirm('您是否确定要删除选定的图片? (图片只是取消了到群组的引用, 未实际删除)') == false) return;
  var f = document.forms.theForm;
  f.cmd.value = 'unref';
  f.submit();
}
function rcmd_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的图片'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'rcmd';
  f.submit();
}
function unrcmd_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的图片'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'unrcmd';
  f.submit();
}
function best_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的图片'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'best';
  f.submit();
}
function unbest_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的图片'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'unbest';
  f.submit();
}
function share_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的图片'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'set_share_mode';
  f.submit();
}
function group_changed() {
  var sel = groupId = document.forms.theForm.groupId;
  var groupId = sel.value;
  var frame = document.frames.destinCategoryFrame;
  frame.document.location.href = 'photo.action?cmd=dest_cate&groupId=' + groupId;
}
</script>

</body>
</html>
