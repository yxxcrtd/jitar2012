<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="icon" href="${SiteUrl}images/favicon.ico" />
		<link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
		<title></title>
		<link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
		<script src='js/jitar/core.js'></script>  
	</head>
	
	<body>

<#if msgList??>
<table class="lastlist" cellspacing="1">
<tr>
	<th><nobr>发送日期</nobr></th>
	<th><nobr>发送人</nobr></th>
	<th><nobr>接收人</nobr></th>
	<th><nobr>内容</nobr></th>
</tr>
<#list msgList as msg>
	<tr>
		<td width="120">${msg.sendDate?string('yyyy-MM-dd HH:mm:ss')}</td>
		<td width="120">${msg.senderName}</td>
 		<td width="120">${msg.receiverName}</td>
 		<td>${msg.talkContent!}</td>
 	</tr>
 	</#list>
    </table>
	    <div class='pager'>
	      <#include '../inc/pager.ftl' >
	    </div>
	  </#if>  
</body>
</html>	  