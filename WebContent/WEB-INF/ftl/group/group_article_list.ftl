<#assign grpName="协作组">
<#assign grpShowName="小组">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
        <#assign grpShowName="课题">
    <#elseif isKtGroup=="2">
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
  <title>${grpName}文章管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type='text/javascript' src='dialog/msg.js'></script>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/msgbox.css" />
  <script type='text/javascript' src='${SiteUrl}js/msgbox.js'></script>
  
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <span>${grpName}文章管理</span> 
</div>
<br/>
<#assign can_pub = (group.groupState != 2 && group_member?? && group_member.status == 0) >
<#assign can_manage = (group.groupState != 2 && group_member?? && group_member.status == 0 && group_member.groupRole >= 800) >

<form action='groupArticle.action' method='get'>
<div id="bgs"  class='pager'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
  关键字：<input type='text' name='k' value="${k!?html}" size='16' />
  <select name='gcid'>
    <option value=''>${grpName}文章分类</option>
  	<#list res_cate.all as category>
    	<option ${((gcid!0) == category.categoryId)?string('selected', '')} value='${category.id}'>${category.treeFlag2 + category.name!?html}</option>
    </#list>
  </select>
  <input type='submit' class='button' value=' 搜索/过滤 ' />
</div>
</form>

<form name='theForm' id='theForm' action='groupArticle.action' method='post'>
<table class='listTable' cellspacing="1">
  <thead>
  <tr>
    <th style="width:17px"></th>
    <th>标题</th>
    <th>发表人</th>
    <th style="width:120px">发表时间</th>
    <th>组内文章分类</th>
    <th style="width:80px">操作</th>
  </tr>
  </thead>
  <tbody>
<#list article_list as article>
  <#assign u = Util.userById(article.userId) >
  <tr>
    <td><input type='checkbox' name='articleId' value='${article.articleId}' /></td>
    <td><#if article.typeState == false>[原创]<#else>[转载]</#if> <a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a>
    	<#if article.isGroupBest><img src='images/ico_best.gif' border='0' /></#if>
    </td>
    <td><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.nickName}</a></td>
    <td>${article.createDate?string('yyyy-MM-dd hh:mm')}</td>
    <td>
    <#if article.groupCateId?? >
    <#assign c = Util.getGroupArticleCate(article.groupCateId)>${c!}
    </#if>
    </td>
    <td>   
    <#if can_manage >
      <a href='?cmd=unref&amp;groupId=${group.groupId}&amp;articleId=${article.articleId}'>移除</a> | 
      <a href='group/group_admin_edit_article.py?&groupId=${group.groupId}&articleId=${article.articleId}&from=group'>修改</a>
    </#if>
    </td>
  </tr>
  </#list>
  </tbody>
</table>

<div class='pager'>
  <#include '../inc/pager.ftl'>
</div>

<div class='funcButton'>
  <input type='hidden' name='cmd' value='' />
  <input type='hidden' id='groupCateId' name='groupCateId' value='' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
<#if can_manage >
  <input type='button' class='button' value=' 全 选 ' onclick='select_all()' />
</#if>
<#if can_pub >
  <input type='button' class='button' value=' 发表文章 ' onclick='document.addArticleForm.submit();' />
</#if>
  <!-- groupRole = ${group_member.groupRole} -->
<#if can_manage >
  <input type='button' class='button' value=' 移 除 ' onclick='unref_article()' />
  <input type='button' class='button' value='移动到...' onclick='showDialog()' />
  <input type='button' class='button' value='设置精华' onclick='submit_command("best")' />
  <input type='button' class='button' value='取消精华' onclick='submit_command("unbest")' />
  <!--
  <input type='button' class='button' value='加分' onclick='doScore("addscore")' />
  <input type='button' class='button' value='罚分' onclick='doScore("minusscore")' />
  -->
  <!--
  <input type='button' class='button' value='平台推荐' onclick='submit_command("rcmd")' />
  <input type='button' class='button' value='取消推荐' onclick='submit_command("unrcmd")' />
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
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">文章加分</div>
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
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">文章罚分</div>
  </div>
  <div style='padding:10px;'>
    <h4>注意：文章罚分处理将删除该文章</h4>
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

<!-- 发表文章时候提交的表单 -->
<form name='addArticleForm' action='article.action' method='get' style='display:none'>
  <input type='hidden' name='cmd' value='input' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
  <#if returnPage??><input type='hidden' name='returnPage' value='${returnPage}' /></#if>
</form>

<script>
function select_all() {
  var ids = document.getElementsByName('articleId');
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
  var ids = document.getElementsByName('articleId');
  if (ids == null) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}
function unref_article() {
  if (has_item_selected() == false) {
    alert('没有选择任何要操作的文章');
    return;
  }
  
  if (confirm('您是否确定要移除选中的文章? \r\n\r\n提示: 文章不会被删除, 只是不显示在协作组中.') == false) return;
  submit_command('unref');
}
function submit_command(cmd) {
  if (has_item_selected() == false) {
    alert('没有选择任何要操作的文章');
    return;
  }
  document.forms.theForm.cmd.value = cmd;
  document.forms.theForm.submit();
}

function showDialog()
{
	if (has_item_selected() == false) {
    alert('未选择要操作的文章'); return;
   }
	var htmlContent=" <div>";
	htmlContent+="移动到群组文章分类:";
	htmlContent+="<select onchange=\"setValue(this.value);\" id=\"groupCateId_1\">";
	htmlContent+="<option value=\"\">群组文章分类</option>";
	htmlContent+=" <option value=\"\">不设置群组文章分类</option>";
	<#list res_cate.all as category>
	htmlContent+=" <option value=\"${category.id}\">${category.treeFlag2 + category.name!?html}</option>";
	</#list>
	htmlContent+=" </select>";
	htmlContent+="  </div>";
	msg("文章移动到群组文章分类",2,"280",htmlContent,"move_r()")
}
function setValue(val) {
    document.getElementById('groupCateId').value=val;
}
function move_r() {
  if (has_item_selected() == false) {
    alert('未选择要操作的文章'); return;
  }
  var f = document.forms.theForm;
  f.cmd.value = 'move';
  f.groupCateId.value = document.getElementById("groupCateId_1").value;
  f.submit();
}

  function doScore(arg){
        //addscore  minusscore
    if(!has_item_selected()){
        alert("请选择文章");
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
        alert("请选择要加分的文章");
        return;
    }   
    document.getElementById('theForm').cmd.value="addscore";
    document.getElementById('theForm').submit();
      
  }
  function minusscoreDo(){
    if(!has_item_selected()){
        alert("请选择要罚分的文章");
        return;
    }  
    document.getElementById('theForm').cmd.value="minusscore";
    document.getElementById('theForm').submit();
  }
  


</script>
</body>
</html>
