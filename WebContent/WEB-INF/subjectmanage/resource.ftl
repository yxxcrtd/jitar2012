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
  	var ssharestate = f.sharestate.options[f.sharestate.selectedIndex].value;
  	var srcmd = f.rcmdstate.options[f.rcmdstate.selectedIndex].value;
  	var sdelete = f.deletestate.options[f.deletestate.selectedIndex].value;
  	var zyk= f.zyk?f.zyk.options[f.zyk.selectedIndex].value:"";
  	var qs = "ss=" + ssysId + "&sa=" + sapprove + "&sm=" + ssharestate + "&sr=" + srcmd + "&zyk=" + zyk + "&sd=" + sdelete + "&type=filter"
  	var url = "resource.py?id=${subject.subjectId}&";
  	window.location.href = url + qs;  	
  }
	function publishtozyk(resourceId){
		var url="${SiteUrl}manage/resource.action?cmd=select_resource_cate&resourceId="+resourceId;
		//window.open(url,"");
		window.showModelessDialog(url,"","dialogHeight:540px;dialogWidth:700px;dialogLeft:50px;dialogTop:50px;resizable:yes;");
	}
  function doScore(arg){
        //addscore  minusscore
    var f = document.getElementById('oForm');    
    if(!hasChecked(f,"guid")){
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
  var f = document.getElementById('oForm'); 
    if(!hasChecked(f,"guid")){
        alert("请选择要加分的资源");
        return;
    }   
    document.getElementById('oForm').cmd.value="addscore";
    document.getElementById('oForm').submit();
      
  }
  function minusscoreDo(){
  var f = document.getElementById('oForm'); 
    if(!hasChecked(f,"guid")){
        alert("请选择要罚分的资源");
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
</head>
<body>
<h2>学科资源管理</h2>
<form method='GET' action='resource.py'>
<input name='id' type='hidden' value='${subject.subjectId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='title'${(f=='title')?string(' selected="selected"','')}>资源标题、标签</option>
  <option value='uname'${(f=='uname')?string(' selected="selected"','')}>发表用户</option>
  <option value='unit'${(f=='unit')?string(' selected="selected"','')}>用户所属机构</option>
</select>
<select name='ss'>
 <option value=''>资源系统分类</option>
 <#if resource_categories??>
 <#list resource_categories.all as ct >
 <option value='${ct.categoryId}' ${(ss == ct.categoryId?string)?string(' selected', '')}>${ct.treeFlag2} ${ct.name!?html}</option>
 </#list>
 </#if>
</select>
<input type='submit' value='搜索' />
</div>
</form>
<form method='POST' id='oForm' style='padding-left:20px'>
<input name='cmd' type='hidden' value='' />
<#if resource_list??>
<table class='listTable' cellspacing='1' id='listTable'>
<thead>
<tr>
<th><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");SetRowColorByName();' id='chk' /></th>
<th style='width:100%'>资源标题</th>
<th><nobr>上传者</nobr></th>
<th><nobr>上传时间</nobr></th>
<th><nobr>大小</nobr></th>
<th><nobr>
  <select name='sc' onchange='doFilter()'>
    <option value=''>资源分类</option>
    <#if resource_categories??>
  	<#list resource_categories.all as c >
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
</nobr></th>
<th><nobr>
<select name='deletestate' onchange='doFilter()'>
<option value=''${(sd == "")?string(" selected='selected'","")}>删除状态</option>
<option value='1'${(sd == "1")?string(" selected='selected'","")}>待删除</option>
<option value='0'${(sd == "0")?string(" selected='selected'","")}>正常资源</option>
</select>
</nobr></th>
<th><nobr><select name='rcmdstate' onchange='doFilter()'>
<option value=''${(sr == "")?string(" selected='selected'","")}>推荐状态</option>
<option value='1'${(sr == "1")?string(" selected='selected'","")}>推荐资源</option>
<option value='0'${(sr == "0")?string(" selected='selected'","")}>非推荐资源</option>
<option value='-1'${(sr == "-1")?string(" selected='selected'","")}>小组精华</option>
</select></nobr></th>
<#if isHaszykUrl=='true'>
	<#if zyk??>
	<th><nobr><select name='zyk' onchange='doFilter()'>
	  	<option value=''>推送状态</option>
	  	<option <#if zyk=='1'>selected</#if> value='1'>已推送</option>
	  	<option <#if zyk=='0'>selected</#if> value='0'>未推送</option>
	  </select>
	</nobr></th>  
	</#if>
</#if>  
<th><nobr><select name='sharestate' onchange='doFilter()'>
<option value='-1'${(sm?string == "-1")?string(" selected='selected'","")}>发布方式</option>
  <option value='1000'${(sm?string == "1000")?string(" selected='selected'","")}>完全公开</option>
  <option value='500'${(sm?string == "500")?string(" selected='selected'","")}>组内公开</option>
  <option value='0'${(sm?string == "0")?string(" selected='selected'","")}>私有</option>
</select></nobr></th>
<#if platformType?? && platformType == "2">
<th><nobr></nobr></th>
</#if>
</tr>
</thead>
<#list resource_list as r>
<#assign user = Util.userById(r.userId)>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${r.resourceId}' onclick='SetRowColor(event)' /></td>
<td>
<img src='${Util.iconImage(r.href)}' />
<a href='${Util.url(r.href)}' target='_blank'>${r.title}</a></td>
<td><nobr><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></nobr></td>
<td><nobr>${r.createDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr>${Util.fsize(r.fsize)}</nobr></td>
<td><nobr>${r.sysCateName!?html}</nobr></td>
<td><nobr>
<#if r.auditState == 0 >
已审核
<#elseif r.auditState == 1 >
<span style='color:#f00'>待审核</span>
<#else>
未定
</#if>
</nobr></td>
<td><nobr><#if r.delState>待删除</#if></nobr></td>
<td><nobr><#if r.recommendState>推荐</#if>
<#if r.isGroupBest??>
	<#if r.isGroupBest>&nbsp;小组精华</#if>
<#else>
	<#if groupbest_list??><#if groupbest_list[r_index]>&nbsp;小组精华</#if></#if>	
</#if>
</nobr></td>

<#if isHaszykUrl=='true'>
<td><nobr>
<#if r.publishToZyk>
已推送
<#else>
<a href="#" onclick="publishtozyk(${r.resourceId});">资源库</a>
</#if>
</nobr></td>
</#if>

<td><nobr>
<#if r.shareMode == 1000>
完全公开
<#elseif r.shareMode == 500>
组内公开
<#elseif r.shareMode == 0>
私有
<#else>
未定义
</#if>
</nobr></td><#--
<td><nobr><a href='${SiteUrl}manage/admin_resource.py?cmd=edit&resourceId=${r.resourceId}&x=1&subId=${subject.subjectId}'>修改</a></nobr>
</td>-->
<#if platformType?? &&  platformType == "2">
<td><nobr>
<#if r.pushState  == 1 >已推送</#if>
<#if r.pushState  == 2 ><span style='color:red'>待推送</span></#if>
</nobr>
</td>
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
<script type='text/javascript'>
function SetRowColor(evt)
{
  var oCheckBox = Platform.isIE ? window.event.srcElement : evt.target;
  var oTr = oCheckBox;
  while(oTr.tagName && oTr.tagName != "TR")
  oTr = oTr.parentNode
  if(oTr)
  {  
	oTr.style.backgroundColor=oCheckBox.checked?'#e6f4b2':'#FFFFFF';
  }  
}

function SetRowColorByName()
{
  var oCheckBoxs = document.getElementsByName('guid');
  for(i=0;i<oCheckBoxs.length;i++)
  {
     var oTr = oCheckBoxs[i];
     while(oTr.tagName && oTr.tagName != "TR")
     oTr = oTr.parentNode
     if(oTr)
	  {
		oTr.style.backgroundColor=oCheckBoxs[i].checked?'#e6f4b2':'#FFFFFF';
	  }  
  }  
}

function colorTable()
{
  var t = document.getElementById('listTable')
  for(var i = 1;i<t.rows.length;i++)
  {
  	addEventHandler(t.rows[i],"mouseover",Mouse_Over);
  	addEventHandler(t.rows[i],"mouseout",Mouse_Out);
  }  
}

function Mouse_Over(evt)
{
  var oTr = Platform.isIE ? window.event.srcElement : evt.target;
  while(oTr.tagName && oTr.tagName != "TR")
  oTr = oTr.parentNode
  if(oTr)
  {
  	oTr.setAttribute('oldColor',oTr.style.backgroundColor);
  	oTr.style.backgroundColor='#dec8f1';
  }
}

function Mouse_Out(evt)
{
  var oTr = Platform.isIE ? window.event.srcElement : evt.target;
  while(oTr.tagName && oTr.tagName != "TR")
  oTr = oTr.parentNode
  if(oTr)
  {
  	oTr.style.backgroundColor = oTr.getAttribute('oldColor');
  }
}

function addEventHandler(oTarget, sEventType, fnHandler) {
　　if (oTarget.addEventListener) {
　　　　oTarget.addEventListener(sEventType, fnHandler, false);
　　} else if (oTarget.attachEvent) {
　　　　oTarget.attachEvent("on" + sEventType, fnHandler);
　　} else {
　　　　oTarget["on" + sEventType] = fnHandler;
　　}
};
//colorTable();
//
</script>
</body>
</html>