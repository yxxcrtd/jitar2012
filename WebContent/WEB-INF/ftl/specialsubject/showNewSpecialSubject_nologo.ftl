<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>候选主题列表 - <#include ('/WEB-INF/ftl/webtitle.ftl') ></title> 
  
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}special.css" />
  <script src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body style='margin:0;padding:0 0 0 4px'>
<h2>候选专题列表</h2> 			
<div>
<#if newSpecialSubjectList??>
<form method='post'>
  	<table class="listTable" cellspacing="1">
	  <tr>
	  <th style='font-weight:bold;width:20px'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid")' /></th>
	  <th style='font-weight:bold;width:600px'>候选专题名称</th>
	  <th style='font-weight:bold'>创建时间</th>
	  <th style='font-weight:bold'>提交人</th>
	  </tr>	
	<#list newSpecialSubjectList as s>		  
	  <tr>
	  <td><input type='checkbox' name='guid' value='${s.newSpecialSubjectId}' /></td>
	  <td>${s.newSpecialSubjectTitle?html}</td>
	  <td>${s.createDate?string('yyyy-MM-dd HH:mm:ss')}</td>
	  <td><a href='${SiteUrl}go.action?userId=${s.createUserId}' target='_blank'>${s.createUserName!?html}</a></td>	  
	  </tr>
	  <tr>
	  <td colspan='4'>${s.newSpecialSubjectContent}</td>
	  </tr>
	</#list>
	</table>
	<div style='padding:6px;text-align:center'><input type='submit' value=' 删  除 ' /></div>
</form>
</#if>
</body>
</html>