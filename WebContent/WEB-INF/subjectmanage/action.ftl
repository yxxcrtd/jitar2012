<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script>
  function doPost(arg)
  {
  	document.forms[0].cmd.value=arg;
  	document.forms[0].submit();
  }
  
  function addNew()
  {
  	window.location.href = 'news_add.py?id=${subject.subjectId}'
  }
  </script>
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
</head>
<body>
<h2>
学科教研活动管理
</h2>
<form method='post'>
<input name='cmd' type='hidden' value='' />
<#if action_list??>
<table class='listTable' cellspacing='1'>
<thead>
<tr>
<th><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");SetRowColorByName();' id='chk' /></th>
<th style='width:100%'>活动名称</th>
<th><nobr>发起人</nobr></th>
<th><nobr>发起时间</nobr></th>
<th><nobr>开始时间</nobr></th>
<th><nobr>结束时间</nobr></th>
<th><nobr>状态</nobr></th>
<th><nobr>操作</nobr></th>
</tr>
</thead>
<#list action_list as a >
<#assign user = Util.userById(a.createUserId)>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${a.actionId}' onclick='SetRowColor(event)' /></td>
<td><a href='${SiteUrl}showAction.action?actionId=${a.actionId}' target='_blank'>${a.title!?html}</a></nobr></td>
<td><nobr><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></nobr></td>
<td><nobr>${a.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
<td><nobr>${a.startDateTime?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
<td><nobr>${a.finishDateTime?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
<td><nobr>
<#if a.status == 0 >
正常
<#elseif a.status ==1 >
待审批
<#elseif a.status ==2 >
已经关闭
<#elseif a.status ==3 >
锁定
<#elseif a.status ==4 >
名单已经打印
<#else>
未定义
</#if>
</nobr>
</td>
<td><nobr><a href='${SiteUrl}actionEdit.action?actionId=${a.actionId}' target='_blank'>修改</a></nobr>
</td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
 <input class='button' type='button' value='全部选中' onclick='${"chk"}.click();SetRowColorByName();' />
 <input class='button' type='button' value='删除活动' onclick='if(window.confirm("你真的要删除吗？")){doPost("-2")}' /> 
 <input class='button' type='button' value='锁定活动' onclick='doPost("3")' />
 <input class='button' type='button' value='关闭活动' onclick='doPost("2")' />
 <input class='button' type='button' value='设为已打印状态' onclick='doPost("4")' />
 <input class='button' type='button' value='设为正常' onclick='doPost("0")' />
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