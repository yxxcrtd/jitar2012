<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>推送名师</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type='text/javascript'>
  var url = "${SiteUrl}push/admin_push_famous_list.py?cmd=push";
  function ProcessData()
  {
  	var sts = document.getElementById('_status');
  	sts.innerHTML = "正在执行服务器检查……";
  	sts.style.display = "";
  	
  	// 检查服务器是否能够联通  	
  	if(false == CheckConnection())
  	{
  	 sts.style.display = "none";
  	 alert('服务器无法连通或者服务器配置不正确，无法进行推送，请咨询网站管理员进行适当的配置。\r\n\r\n在进行推送之前，请先由网站管理员配置服务器地址。')
  	 return;
  	}
  	
  	sts.innerHTML = "正在执行推送……";
  	var chks = document.getElementsByName('guid');
  	
  	for(i = 0;i < chks.length; i++)
  	{
  		if(chks[i].checked)
  		{
  			ShowProcessbar(chks[i]);
  			PushData(chks[i]);
  		}
  	}
  	sts.style.display="none";
  }
  
  function PushData(oChk)
  {
  	postData = 'userId=' + oChk.value;
  	new Ajax.Request(url, { 
	          method: 'post',
	          parameters:postData,
	          onSuccess:function(xhr){
	          	if(xhr.responseText.indexOf("OK") >-1)
	          	{
					ShowFinishedTask(oChk);
	          	}
	          	else if(xhr.responseText.indexOf("LOCKED") >-1)
	          	{
	          		ShowLockedTask(oChk);
	          	}
	          	else if(xhr.responseText.indexOf("DELETED") >-1)
	          	{
	          		ShowDeletedTask(oChk);
	          	}
	          	else
	          	{
	          		ShowErrorTask(oChk);
	          	}
	          	},	          	
	          onFailure:function(xhr){ShowErrorTask(oChk);document.write('修改数据失败。' + xhr.responseText);}
	        }
	  );
  }
  
  function ShowProcessbar(oChk)
  {
  	var oTr = oChk.parentNode;
  	while(oTr.tagName != "TR")
  	{
  	 oTr = oTr.parentNode;
  	}
  	if(oTr.tagName == "TR")
  	{
  		oTr.style.backgroundColor = '#a4d888';
  		oTr.cells[oTr.cells.length-1].innerHTML = "正在推送";
  	}
  }
  function ShowFinishedTask(oChk)
  {
  	var oTr = oChk.parentNode;
  	while(oTr.tagName != "TR")
  	{
  	 oTr = oTr.parentNode;
  	}
  	if(oTr.tagName == "TR")
  	{
  		oTr.style.backgroundColor = '#ffffe1';
  		oTr.cells[oTr.cells.length-1].innerHTML = "完成";
  	}
  }
  function ShowErrorTask(oChk)
  {
  	var oTr = oChk.parentNode;
  	while(oTr.tagName != "TR")
  	{
  	 oTr = oTr.parentNode;
  	}
  	if(oTr.tagName == "TR")
  	{
  		oTr.style.backgroundColor = '#f28626';
  		oTr.cells[oTr.cells.length-1].innerHTML = "推送失败";
  	}
  }
  
  function ShowLockedTask(oChk)
  {
  	var oTr = oChk.parentNode;
  	while(oTr.tagName != "TR")
  	{
  	 oTr = oTr.parentNode;
  	}
  	if(oTr.tagName == "TR")
  	{
  		oTr.style.backgroundColor = '#f00';
  		oTr.cells[oTr.cells.length-1].innerHTML = "平台被锁定";
  	}
  }
  
  function ShowDeletedTask(oChk)
  {
  	var oTr = oChk.parentNode;
  	while(oTr.tagName != "TR")
  	{
  	 oTr = oTr.parentNode;
  	}
  	if(oTr.tagName == "TR")
  	{
  		oTr.style.backgroundColor = 'green';
  		oTr.cells[oTr.cells.length-1].innerHTML = "平台被删除";
  	}
  }
  
    
  var ConnectionState = false;
  function CheckConnection()
  {
    var xmlhttp = null;
   	if(window.XMLHttpRequest)
   	{
   	  xmlhttp = new window.XMLHttpRequest();
   	}
   	else
   	{
   	  var MX = ["MSXML2.XMLHTTP.6.0", "MSXML2.XMLHTTP.5.0",
    		  "MSXML2.XMLHTTP.4.0", "MSXML2.XMLHTTP.3.0",
    		  "MSXML2.XMLHTTP.2.6", "MSXML2.XMLHTTP",
    		  "Microsoft.XMLHTTP"];
      for(k = 0;k<MX.length;k++)
      {
      	try
      	{
      		xmlhttp = new ActiveXObject(MX[k]); 
      		break;
      	}
      	catch(ex){}
      }
   	}
   	if(xmlhttp == null)
   	{
   	  alert('不能创建 XMLHttpRequest 对象。\r\n\r\n请检查你的浏览器安全性设置，和管理加载项。');
   	  return false;
   	}   	
    ConnectionState = false;
    var url = '${SiteUrl}push/admin_push_famous_list.py?cmd=check&tmp=' + Math.random();
    xmlhttp.open("GET",url,false);
    xmlhttp.setRequestHeader("Connection", "close");
    xmlhttp.send(null);
    if(xmlhttp.readyState == 4)
    {
	    if(xmlhttp.status >= 200 && xmlhttp.status <=302)
	    {
	      if(xmlhttp.responseText.indexOf("OK") >-1)
	      {
	      	ConnectionState = true;
	      }
	      else
	      {
	      	ConnectionState = false;
	      }
	    }
    }
    return ConnectionState;
  }
 
 function doPost(st)
 {
   document.getElementById('oForm').cmd.value=st;
   document.getElementById('oForm').submit();
 }
  </script>
 </head>
<body>
<h2>名师推送管理</h2>
<#if user_list??>
<form id='oForm' method='POST' action='${SiteUrl}push/admin_push_famous_list.py'>
<input name='cmd' type='hidden' value='' />
<table class='listTable' cellspacing="1">
<thead>
<tr>
<th style='width:20px;text-align:left;'><input type='checkbox' id='chk' onclick='CommonUtil.SelectAll(this,"guid")' /></th>
<th>头像</th>
<th>登录名,昵称，真实姓名</th>
<th>学科、学段</th>
<th>机构</th>
<th>推送人</th>
<th>执行状态</th>
</tr>
</thead>
<#list user_list as user>
<#if user.pushUserId??>
<#assign pushuser = Util.userById(user.pushUserId)>
</#if>
<tr title='${user.blogIntroduce!?html}'>
  <td><input type="checkbox" name="guid" value="${user.userId}" /></td>
  <td style='width:48px'><a href="${SiteUrl}go.py?loginName=${user.loginName!}" target="_blank"><span style="border:1px solid #ececec;"><img src="${Util.url(user.userIcon!"images/default.gif")}" onerror="this.src='${SiteUrl}images/default.gif';" width="48" border="0" title="${user.blogName!}" /></span></a></td>
  <td style="padding-left: 10px;"><a href="${SiteUrl}go.py?loginName=${user.loginName!}" title="点击访问该工作室" target="_blank">${user.loginName!}</a>
  	<div style="line-height: 6px;"><br /></div>
    ${user.nickName!?html}
    <div style="line-height: 6px;"><br /></div>
    ${user.trueName!?html}
  </td>      
  <td style="padding-left: 10px;">
   <#if user.subjectId??>${Util.subjectById(user.subjectId).msubjName!?html}</#if>
   <div style="line-height: 6px;"><br /></div>
   <#if user.gradeId??>${Util.gradeById(user.gradeId).gradeName!?html}</#if>
  </td>
  <td style="padding-left: 10px;">
    <div style="line-height: 6px;"><br /></div>
    <#if user.unitTitle??>${user.unitTitle?html}</#if>
  </td>
 <td>               
<#if pushuser??>
<a href='${SiteUrl}go.py?loginName=${pushuser.loginName!}' target='_blank'>${pushuser.trueName!?html}</a>
</#if>
</td>
<td>
<#if user.pushState??>
<#if user.pushState == 2><span style='color:red'>待推送</span></#if>
</#if>
</td>
</tr>
</#list>
</table>
<div style='text-align:center'>
  <#include '/WEB-INF/ftl/inc/pager.ftl' >
</div>
<input type='button' class='button' value='全部选中' onclick='document.getElementById("chk").click();CommonUtil.SelectAll(document.getElementById("chk"),"guid")' />
<input type='button' class='button' value='推送名师' onclick='ProcessData()' />
<input type='button' class='button' value='删除推送状态' onclick='doPost("delete")' />
<span id='_status' style='display:none;'>正在执行推送……</span>
</form>
<#else>
没有需要推送的名师。
</#if>
</body>
</html>