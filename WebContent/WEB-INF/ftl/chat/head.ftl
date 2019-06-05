<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${ChatRoomName}</title>
  <link rel="stylesheet" href="${SiteUrl}css/manage.css" type="text/css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
</head>
<body>
<#if type==2>
	<#include ('../../group/default/func.ftl') >
<#elseif type==1>
	<#include ('../../ftl/course/func.ftl') >
<#else>
	
</#if>
</body>
</html>
