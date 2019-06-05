<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>资源管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
	<style>   
  .info_box {border:1px solid blue; width:75%; padding:2px; text-align:left; margin-top:12px;}
  .info_box li {margin: 4px 0 4px 0;}
  </style>   
  
  <script language="javascript">
  	function windowclose()
  	{
  		window.open("${SiteUrl}manage/resource.action?cmd=list","main");
  		window.close();
  	}
  	function windowclose1()
  	{
  		window.close();
  	}
  </script>
</head>
<body>  
<#if error??>
	<#if error!="">
	
	<font style="color: red; font-size: 26px; font-weight: bold;">${error}</font>
	<br/>
	<div style='margin:4px;'>
	<center>
	<a href="#" onclick="window.close();return false;">关闭</a>
	</center>
	</div>
	</#if>
</#if>
<#if success??>
	<#if success!="">
	<font style="color: green; font-size: 26px; font-weight: bold;">${success}</font>
	<br/>
	<div style='margin:4px;'>
	<center>
	<a href="#" onclick="windowclose();return false;">关闭</a>
	</center>
	</div>
	</#if>
</#if>
</body>
</html>