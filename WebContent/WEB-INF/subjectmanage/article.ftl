<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/msgbox.css" />
  <script type='text/javascript' src='${SiteUrl}js/msgbox.js'></script>
  <script>
  function doPost(arg)
  {
  	document.getElementById('oForm').cmd.value=arg;
  	document.getElementById('oForm').submit();
  }
  
  function doFilter()
  {
  	var f = document.getElementById('oForm');
  	var ssysId = f.sc.options[f.sc.selectedIndex].value;
  	var sapprove = f.approvestate.options[f.approvestate.selectedIndex].value;
  	//var sbest = f.beststate.options[f.beststate.selectedIndex].value;
  	var srcmd = f.rcmdstate.options[f.rcmdstate.selectedIndex].value;
  	var sdelete = f.deletestate.options[f.deletestate.selectedIndex].value;

  	var qs = "ss=" + ssysId + "&sa=" + sapprove + "&sr=" + srcmd + "&sd=" + sdelete + "&type=filter"
  	var url = "article.py?id=${subject.subjectId}&";
  	window.location.href = url + qs;  	
  }
  function doScore(arg){
        //addscore  minusscore
    var f = document.getElementById('oForm');    
    if(!hasChecked(f,"guid")){
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
  var f = document.getElementById('oForm'); 
    if(!hasChecked(f,"guid")){
        alert("请选择要加分的文章");
        return;
    }   
    document.getElementById('oForm').cmd.value="addscore";
    document.getElementById('oForm').submit();
      
  }
  function minusscoreDo(){
  var f = document.getElementById('oForm'); 
    if(!hasChecked(f,"guid")){
        alert("请选择要罚分的文章");
        return;
    }  
    document.getElementById('oForm').cmd.value="minusscore";
    document.getElementById('oForm').submit();
  }
  
 function hasChecked (vform,elename)
{
    for (var i = 0; i < vform.elements.length; i++) {
        var e = vform.elements[i];
        if(e.name==elename){
            if (e.checked) {
                return true;
            }
        }
    }
    return false;  
}
  </script>
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script src='${SiteUrl}js/subject/util.js' type='text/javascript'></script>
</head>
<body>
<h2>学科文章管理</h2>
<form method='GET' action='article.py'>
<input name='id' type='hidden' value='${subject.subjectId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='title'${(f=='title')?string(' selected="selected"','')}>文章标题、标签</option>
  <option value='uname'${(f=='uname')?string(' selected="selected"','')}>发表用户</option>
  <option value='unit'${(f=='unit')?string(' selected="selected"','')}>用户所属机构</option>
</select>
<select name='ss'>
    <option value=''>所属文章分类</option>
    <#if article_categories??>
  	<#list article_categories.all as ct >
    <option value='${ct.categoryId}' ${(ss == ct.categoryId?string)?string(' selected', '')}>${ct.treeFlag2} ${ct.name!?html}</option>
   </#list>
   </#if>
</select>
<input type='submit' value='搜索' />
</div>
</form>
<form method='POST' id='oForm'>
<input name='cmd' type='hidden' value='' />
<#if article_list??>
<table class='listTable' cellspacing='1' id='listTable'>
<thead>
<tr>
<th><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");SetRowColorByName();' id='chk' /></th>
<th style='width:100%'>文章标题</th>
<th><nobr>文章作者</nobr></th>
<th><nobr>发布时间</nobr></th>
<th><nobr>
  <select name='sc' onchange='doFilter()'>
    <option value=''>文章分类</option>
    <#if article_categories??>
  	<#list article_categories.all as c >
    <option value='${c.categoryId}' ${(ss == c.categoryId?string)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
   </#list>
   </#if>
  </select>
</nobr></th>
<th><nobr>
<select name='approvestate' onchange='doFilter()'>
<option value=''${(sa == "")?string(" selected='selected'","")}>审核状态</option>
<option value='0'${(sa == "0")?string(" selected='selected'","")}>已审核</option>
<option value='1'${(sa == "1")?string(" selected='selected'","")}>待审核</option>
</select>
</nobr></th><#--
<th><nobr>
<select name='beststate' onchange='doFilter()'>
<option value=''${(sb == "")?string(" selected='selected'","")}>精华状态</option>
<option value='1'${(sb == "1")?string(" selected='selected'","")}>精华文章</option>
<option value='0'${(sb == "0")?string(" selected='selected'","")}>正常文章</option>
</select>
</nobr></th>-->
<th><nobr>
<select name='deletestate' onchange='doFilter()'>
<option value=''${(sd == "")?string(" selected='selected'","")}>删除状态</option>
<option value='1'${(sd == "1")?string(" selected='selected'","")}>待删除</option>
<option value='0'${(sd == "0")?string(" selected='selected'","")}>正常文章</option>
</select>
</nobr></th>
<th><nobr><select name='rcmdstate' onchange='doFilter()'>
<option value=''${(sr == "")?string(" selected='selected'","")}>推荐状态</option>
<option value='1'${(sr == "1")?string(" selected='selected'","")}>推荐文章</option>
<option value='0'${(sr == "0")?string(" selected='selected'","")}>正常文章</option>
<#--<option value='-1'${(sr == "-1")?string(" selected='selected'","")}>小组精华</option>-->
</select></nobr></th>

<th><nobr>修改</nobr></th>
<#if platformType?? && platformType == "2">
<th><nobr></nobr></th>
</#if>
</tr>
</thead>
<#list article_list as a >
<#assign user = Util.userById(a.userId)>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${a.articleId}' onclick='SetRowColor(event)' /></td>
<td>
<#if a.typeState == false>[原创]<#else>[转载]</#if>
<a href='${SiteUrl}showArticle.action?articleId=${a.articleId}' target='_blank'>${a.title!?html}</a></td>
<td><nobr><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></nobr></td>
<td><nobr>${a.createDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr>${a.sysCateName!?html}</nobr></td>
<td><nobr>
<#if a.auditState == 0 >
已审核
<#elseif a.auditState == 1 >
<span style='color:#f00'>待审核</span>
<#else>
未定
</#if>
</nobr></td>
<#--
<td><nobr><#if a.bestState>精华</#if></nobr></td>
-->
<td><nobr><#if a.delState>待删除</#if></nobr></td>
<td><nobr><#if a.recommendState>推荐</#if>
<#if a.isGroupBest??>
	<#if a.isGroupBest>&nbsp;小组精华</#if>
<#else>
	<#if groupbest_list??><#if groupbest_list[a_index]>&nbsp;小组精华</#if></#if>	
</#if>
</nobr>
</td>

<td><nobr><a href='admin_article_edit.py?articleId=${a.articleId}&id=${subject.subjectId}'>修改</a></nobr>
</td>
<#if platformType??>
<#if platformType == "2">
<td><nobr>
<#if a.pushState  == 1 >已推送</#if>
<#if a.pushState  == 2 ><span style='color:red'>待推送</span></#if>
</nobr>
</td>
</#if>
</#if>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全部选中' onclick='${"chk"}.click();SetRowColorByName();' />
  <input class='button' type='button' value='从学科中删除' onclick="doPost('remove')" />
  <input class='button' type='button' value='彻底删除' onclick="if(window.confirm('你真的要删除吗？\r\n\r\n彻底删除将真正删除文章，不可恢复。')){doPost('real_delete');}" />
  <input class='button' type='button' value='加分' onclick="doScore('addscore')" />
  <input class='button' type='button' value='罚分' onclick="doScore('minusscore')" />
  <select name='cmdtype'>
   <option value=''>选择一项操作</option>
   <option value='approve'>通过审核</option>
   <option value='unapprove'>取消审核</option>
   <option value='rcmd'>设为推荐</option>
   <option value='unrcmd'>取消推荐</option>
   <option value='delete'>设为待删除</option>
   <option value='undelete'>取消待删除</option>
  </select>
  <input class='button' type='button' value='执行选择的操作' onclick="doPost('select_type')" />
<#if platformType?? && platformType == "2">
<#if topsite_url??>
  <input class='button' type='button' value=' 推  送 ' onclick="doPost('push')" />
  <input class='button' type='button' value='取消推送' onclick="doPost('unpush')" />
</#if>
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

</body>
</html>