<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${user.blogName!?html}</title>
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
<#include '../user_script.ftl' >
<script type="text/javascript" src='${SiteUrl}js/jitar/lang.js'></script>
<script type="text/javascript" src='${SiteUrl}js/jitar/core.js'></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/pager.js"></script>
<body>
<#include ('childpage.ftl') >
<div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
<div id='header'>
  <div id='blog_name'><span>${user.blogName!?html}</span></div>
</div>
<#include ('../../layout/layout_2.ftl') >
<div id='placerholder1' title='' style='display:none;padding:1px'>
</div>
<script type="text/javascript">
App.start();
</script>
<div id="subMenuDiv"></div>
<script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
<#-- 原来这里是 include loginui.ftl  -->
<script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js" type="text/javascript"></script> 
</body>
</html>