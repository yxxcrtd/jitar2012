<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>信息提示</title>
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script> 
 </head>
 <body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<div class="secMain border">
<div style='padding-top:10px;text-align:center;font-size:24px;font-weight:bold;color:red'>网站访问提示</div>
<div style='padding:20px 300px;font-size:12px'>
 <div style='line-height:150%'>
 <#if ErrMsg != ''>
     <div style='font-weight:bold;'> 
      您的操作结果：
     </div> 
      ${ErrMsg!}
 <#else>
     <div style='font-weight:bold;'> 
     您的操作执行成功！
     </div> 
 </#if>
<br />
<br />
    <#if isBack?? >
        <#if isBack != "" >
            <a href='' onclick='window.history.back();return false;'>返回</a> 
        <#else>
        	<#if RedUrl?? && RedUrl != ''>
        	<a href='${SiteUrl}${RedUrl!}'>返回</a> 
        	</#if>
        </#if>
    <#else>
        <a href='' onclick='window.history.back();return false;'>返回</a> 
    </#if>
   </div>
 </div>
</div>
<#include '/WEB-INF/ftl2/footer.ftl'>
<!--[if IE 6]>
<script src="js/ie6.js" type="text/javascript"></script>
<script src="js/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
    DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder,.secList li,.secVideoPlay');
</script>
<![endif]-->
</body>
</html>