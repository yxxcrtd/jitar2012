<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>候选主题列表 - <#include ('/WEB-INF/ftl/webtitle.ftl') ></title> 
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}special.css" />
  <script src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div class='containter'>
  <div class='head_nav'><a href='index.action'>首页</a> &gt; <a href='${SiteUrl}specialSubject.action'>候选专题列表</a></div> 			
<div>
<#if newSpecialSubjectList??>
<form method='post'>
<div style='clear:both;height:10px;'></div>
<div class='longtab' style='width:100%'>
   <div class='longtab_head'>
    <div class='longtab_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;候选专题列表</div>
  </div>
  <div class='longtab_content'>
  	<table class="lastlist" cellspacing="1">
	  <tr>
	  <th style='font-weight:bold'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid")' /></th>
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
  </div>  
</div>
</form>
</#if>

<div style='clear:both;height:8px;'></div>

<#include ('/WEB-INF/ftl/footer.ftl') >

</body>
</html>