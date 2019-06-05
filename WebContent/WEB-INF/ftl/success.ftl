<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>操作成功</title>
	<link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
	<style>   
  .info_box {border:1px solid blue; width:75%; padding:2px; text-align:left; margin-top:12px;}
  .info_box li {margin: 4px 0 4px 0;}
  </style>   
</head>
    
<body>
<center>
	<font style="color: green; font-size: 26px; font-weight: bold;">操作成功完成</font>
	
	<div class='info_box'>
	<#if actionMessages??>
		<ul>
		<#list actionMessages as message>
			<li>${message}</li>
		</#list>
		</ul>
	</#if>
	<#if (actionErrors??) && (actionErrors?size > 0)>
		<h4>附加信息为</h4>
		<ul>
		<#list actionErrors as error>
			<li>${error}</li>
		</#list>
		</ul>
	</#if>
	<div style='margin:4px;'>
		<center>
			<#if actionLinks??>
				<#list actionLinks as link>
				<#if link.text != "">[${link.html}]</#if> 
				</#list>
			<#else>
				[<a href='javascript:window.history.back();'>返 回</a>]
			</#if>
		</center>
	</div>
	</div>
</center>

</body>
</html>