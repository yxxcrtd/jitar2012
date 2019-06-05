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
  	var sapprove = f.approvestate.options[f.approvestate.selectedIndex].value;
  	var qs = "sa=" + sapprove + "&type=filter"
  	var url = "video.action?id=${subject.subjectId}&";
  	window.location.href = url + qs;  	
  }
    function doScore(arg){
        //addscore  minusscore
    var f = document.getElementById('oForm');    
    if(!hasChecked(f,"guid")){
        alert("请选择视频");
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
        alert("请选择要加分的视频");
        return;
    }   
    document.getElementById('oForm').cmd.value="addscore";
    document.getElementById('oForm').submit();
      
  }
  function minusscoreDo(){
  var f = document.getElementById('oForm'); 
    if(!hasChecked(f,"guid")){
        alert("请选择要罚分的视频");
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
<h2>学科视频管理</h2>
<form method='GET' action='video.py'>
<input name='id' type='hidden' value='${subject.subjectId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='0'${(f=='0')?string(' selected="selected"','')}>标题、标签</option>
  <option value='1'${(f=='1')?string(' selected="selected"','')}>视频概要</option>
  <option value='2'${(f=='2')?string(' selected="selected"','')}>发表用户</option>
</select>
<input type='submit' value='搜索' />
</div>
</form>
<form method='POST' id='oForm'>
<input name='cmd' type='hidden' value='' />
<#if video_list??>
<table class='listTable' cellspacing='1' id='listTable'>
<thead>
<tr>
<th><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");SetRowColorByName();' id='chk' /></th>
<th><nobr>缩略图</nobr></th>
<th style='width:100%'>视频标题</th>
<th><nobr>发布人</nobr></th>
<th><nobr>发布时间</nobr></th>
<th><nobr>
<select name='approvestate' onchange='doFilter()'>
<option value=''${(sa == "")?string(" selected='selected'","")}>审核状态</option>
<option value='0'${(sa == "0")?string(" selected='selected'","")}>已审核</option>
<option value='1'${(sa == "1")?string(" selected='selected'","")}>待审核</option>
</select>
</nobr></th>
</tr>
</thead>
<#list video_list as v >
<#assign user = Util.userById(v.userId)>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${v.videoId}' onclick='SetRowColor(event)' /></td>
<td><nobr><img src='${v.flvThumbNailHref!?html}' border='0' /></nobr></td>
<td>
<#if v.typeState>
[原创]
<#else>
[转载]
</#if>
<a href='${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}' target='_blank'>${v.title}</a></td>
<td><nobr><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></nobr></td>
<td><nobr>${v.createDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr>
<#if v.auditState == 0 >
已审核
<#elseif v.auditState == 1 >
<span style='color:#f00'>待审核</span>
<#else>
未定
</#if>
</nobr></td><#--
<td><nobr><a href='${SiteUrl}manage/video.action?cmd=edit&videoId=${v.videoId}&admin=1'>修改</a></nobr>
</td>-->
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全部选中' onclick='${"chk"}.click();SetRowColorByName();' />
  <input class='button' type='button' value=' 审  核 ' onclick="doPost('approve')" />
  <input class='button' type='button' value='取消审核' onclick="doPost('unapprove')" />
  <input class='button' type='button' value='从学科中删除' onclick="doPost('remove')" />
  <input class='button' type='button' value='彻底删除' onclick="if(window.confirm('你真的要删除吗？\r\n\r\n彻底删除将真正删除文章，不可恢复。')){doPost('real_delete');}" />
  <input class='button' type='button' value='加分' onclick="doScore('addscore')" />
  <input class='button' type='button' value='罚分' onclick="doScore('minusscore')" />  
</div>

<div id="blockUI" onclick="return false" onmousedown="return false" onmousemove="return false" onmouseup="return false" ondblclick="return false">
  &nbsp;
</div>
<#-- 加分对话框 -->
<div id='MessageAddScoreTip' class='message_frame' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='${SiteUrl}images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">视频加分</div>
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
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">视频罚分</div>
  </div>
  <div style='padding:10px;'>
    <h4>注意：视频罚分处理将删除该视频</h4>
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