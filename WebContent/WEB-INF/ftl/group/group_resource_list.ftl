<#assign grpName="协作组">
<#assign grpShowName="小组">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
        <#assign grpShowName="课题">
    <#else>
        <#assign grpName="协作组">
        <#assign grpShowName="小组">
    </#if>
</#if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>资源管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type='text/javascript' src='dialog/msg.js'></script>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/msgbox.css" />
  <script type='text/javascript' src='${SiteUrl}js/msgbox.js'></script>
  
</head>
<body >
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <span>${grpName}资源管理</span> 
</div>

<#assign can_pub = (group_member?? && group_member.status == 0) >
<#assign can_manage = (group_member?? && group_member.status == 0 && group_member.groupRole >= 800) >

<form action='groupResource.action' method='get'>
<div id="bgs"  class='pager'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
  关键字：<input type='text' name='k' value="${k!?html}" size='16' />
  <select name='gcid'>
    <option value=''>${grpName}资源分类</option>
  	<#list res_cate.all as category>
    	<option ${((gcid!0) == category.categoryId)?string('selected', '')} value='${category.id}'>${category.treeFlag2 + category.name!?html}</option>
    </#list>
  </select>
  <input type='submit' class='button' value=' 搜索/过滤 ' />
</div>
</form>
<form name='theForm' id='theForm' action='groupResource.action' method='post'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
    
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='5%'>选择</th>
      <th width='43%'>标题</th>
      <th width='16%'>组内资源分类</th>
      <th width='20%'>上传人/日期</th>
      <th width='12%'>下载/评论</th>
    </tr>
  </thead>
  <tbody>
  <#list resource_list as resource>
    <tr>
      <td>
        <input type='checkbox' name='resourceId' value='${resource.resourceId}' />
      </td>
      <td>
        <a href='../showResource.action?resourceId=${resource.resourceId}' target='_blank'><img src='${Util.iconImage(resource.href!)}' border='0' align='absmiddle' hspace='3' />${resource.title!?html}</a>
        <#if resource.isGroupBest><img src='images/ico_best.gif' border='0' align='absmiddle' /></#if>
        <#if resource.recommendState><img src='images/ico_rcmd.gif' border='0' align='absmiddle' /></#if>
      </td>
      <td>
        <#if resource.groupCategory??>
          ${resource.groupCategory.name!?html}
        </#if>
      </td>
      <#--
      <td>
      <#if resource.groupResource?? && resource.groupResource.groupCateId?? >
      <#assign c = Util.getGroupArticleCate(resource.groupResource.groupCateId)>${c!}
      </#if>
      </td>
      -->
      <td>
        <#assign user = Util.userById(resource.userId)>
        <a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.nickName!?html}</a> (${resource.createDate?string('yyyy-MM-dd')})
      </td>
      <td align='right'>${resource.downloadCount}/${resource.commentCount}</td>
    </tr>
  </#list>
  </tbody>
</table>
<div class='pager'>
  <#include '../inc/pager.ftl'>
</div>
<div class='funcButton'>
  <input type='hidden' name='groupCateId' value='' />
<#if can_manage >
  <input type='button' class='button' value=' 全 选 ' onclick='select_all()' />
</#if>
<#if can_pub >
  <input type='button' class='button' value='  上传资源  ' onclick='document.uploadForm.submit();' />
</#if>
  <!-- groupRole = ${group_member.groupRole} -->
<#if can_manage >
  <input type='button' class='button' value=' 删 除 ' onclick='unref_r()' />
  <input type='button' class='button' value='移动到...' onclick='showDialog()' />
  <input type='button' class='button' value='设为精华' onclick='best_r()' />
  <input type='button' class='button' value='取消精华' onclick='unbest_r()' />
  <!--
  <input type='button' class='button' value='加分' onclick='doScore("addscore")' />
  <input type='button' class='button' value='罚分' onclick='doScore("minusscore")' />
  -->  
  <!--
  <input type='button' class='button' value='平台推荐' onclick='rcmd_r()' />
  <input type='button' class='button' value='取消推荐' onclick='unrcmd_r()' />
  -->
</#if>
</div>


<div id="blockUI" onclick="return false" onmousedown="return false" onmousemove="return false" onmouseup="return false" ondblclick="return false">
  &nbsp;
</div>
<#-- 加分对话框 -->
<div id='MessageAddScoreTip' class='message_frame' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='${SiteUrl}images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">资源加分</div>
  </div>
  <div style='padding:10px;'>
    <table style='width:300px' border='0'>
      <tr>
        <td style='width:60px'>加分分值:</td>
        <td>
          <select name="add_score">
            <option value="0">0分</option>
            <option value="1" selected>1分</option>
            <option value="2">2分</option>
            <option value="3">3分</option>
            <option value="4">4分</option>
            <option value="5">5分</option>
            <option value="6">6分</option>
            <option value="7">7分</option>
            <option value="8">8分</option>
            <option value="9">9分</option>
            <option value="10">10分</option>
          </select>
        </td>
        </tr>
        <tr>
        <td style="vertical-align:middle">加分原因:<br/>(百字以内)</td>
        <td>
          <textarea style="width:90%;height:80px" name="add_score_reason" maxlength="100"></textarea>
        </td>
      </tr>
    </table>
  </div> 
  <div style='text-align:center;clear:both;padding:10px'>
    <input type='button' class='button' value = ' 确  定 ' onclick='addscoreDo();' />
    <input type='button' class='button' value = ' 取  消 ' onclick="return MessageBox.Close();" />
  </div>
</div>

<#-- 减分对话框 -->
<div id='MessageMinusScoreTip' class='message_frame' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='${SiteUrl}images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">资源罚分</div>
  </div>
  <div style='padding:10px;'>
    <h4>注意：资源罚分处理将删除该资源</h4>
    <table style='width:300px' border='0'>
      <tr>
        <td style='width:60px'>罚分分值:</td>
        <td>
          <select name="minus_score">
            <option value="0">0分</option>
            <option value="-1" selected>1分</option>
            <option value="-2">2分</option>
            <option value="-3">3分</option>
            <option value="-4">4分</option>
            <option value="-5">5分</option>
            <option value="-6">6分</option>
            <option value="-7">7分</option>
            <option value="-8">8分</option>
            <option value="-9">9分</option>
            <option value="-10">10分</option>
          </select>
        </td>
      </tr>
       <tr> 
        <td>罚分原因:<br/>(百字以内)</td>
        <td>
          <textarea style="width:90%;height:80px" name="minus_score_reason" maxlength="100"></textarea>
        </td>
      </tr>
    </table>
  </div> 
  <div style='text-align:center;clear:both;padding:10px'>
    <input type='button' class='button' value = ' 确  定 ' onclick='minusscoreDo();' />
    <input type='button' class='button' value = ' 取  消 ' onclick="return MessageBox.Close();" />
  </div>
</div>

</form>

<form name='uploadForm' action='resource.action' method='get'>
  <input type='hidden' name='cmd' value='upload' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
</form>

<script>
function showDialog()
{
	if (has_item_selected() == false) {
    alert('未选择要操作的资源'); return;
   }
	var htmlContent=" <div>";
	htmlContent+="移动到群组分类:";
	htmlContent+="<select onchange=\"setValue(this.value);\" id=\"groupCateId_1\">";
	htmlContent+="<option value=\"\">群组资源分类</option>";
	htmlContent+=" <option value=\"\">不设置群组资源分类</option>";
	<#list res_cate.all as category>
	htmlContent+=" <option value=\"${category.id}\">${category.treeFlag2 + category.name!?html}</option>";
	</#list>
	htmlContent+=" </select>";
	htmlContent+="  </div>";
	msg("资源移动到群组分类",2,"280",htmlContent,"move_r()")
}
function setValue(val) {
    document.getElementById('groupCateId').value=val;
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
function refresh_p() {
  window.location.reload();
}
function show_move_r() {
  var div = document.getElementById('move_resource_to');
  div.style.display = div.style.display == 'none' ? 'block' : 'none';
}
function move_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'move';
  f.groupCateId.value = document.getElementById("groupCateId_1").value;
  f.submit();
}
function unref_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源'); return;
  }
  if (confirm('您是否确定要删除选定的资源? (资源只是取消了到群组的引用, 未实际删除)') == false) return;
  var f = document.forms.theForm;
  f.cmd.value = 'unref';
  f.submit();
}
function rcmd_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'rcmd';
  f.submit();
}
function unrcmd_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'unrcmd';
  f.submit();
}
function best_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'best';
  f.submit();
}
function unbest_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'unbest';
  f.submit();
}
function share_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的资源'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'set_share_mode';
  f.submit();
}
function group_changed() {
  var sel = groupId = document.forms.theForm.groupId;
  var groupId = sel.value;
  var frame = document.frames.destinCategoryFrame;
  frame.document.location.href = 'resource.action?cmd=dest_cate&groupId=' + groupId;
}

  function doScore(arg){
        //addscore  minusscore
    if(!has_item_selected()){
        alert("请选择资源");
        return;
    }     
    if(arg=="addscore"){
        //加分
        MessageBox.Show('MessageAddScoreTip'); 
        return;
    }
    if(arg=="minusscore"){
        //罚分
        MessageBox.Show('MessageMinusScoreTip'); 
        return;
    }
  }
  function addscoreDo(){
    if(!has_item_selected()){
        alert("请选择要加分的资源");
        return;
    }   
    document.getElementById('theForm').cmd.value="addscore";
    document.getElementById('theForm').submit();
      
  }
  function minusscoreDo(){
    if(!has_item_selected()){
        alert("请选择要罚分的资源");
        return;
    }  
    document.getElementById('theForm').cmd.value="minusscore";
    document.getElementById('theForm').submit();
  }
</script>

</body>
</html>
