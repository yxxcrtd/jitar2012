<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="alternate" type="application/rss+xml" title="Recent Changes" href="/rss.py?type=article" />
  <#if SiteConfig ??>
  <meta name="keywords" content="${SiteConfig.site.keyword!}" /> 
  </#if>
  <title><#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
  <script src='js/jitar/core.js'></script>
  <script type='text/javascript'>
  //<![CDATA[
  var isHide = true;
  function showImg()
  {
    if(isHide)
    {
        document.getElementById("_userimg").src="${UserMgrClientUrl}authimg?tmp="+Date.parse(new Date())
    }
  }
  //]]>
  </script>
 </head>
 
<body>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div class='main'>
<h2>
<#if errMessage??>
${errMessage!?html}
<#else>
代码执行过程中出现错误，但没有定义错误输出信息。
</#if>
</h2>
</div>
<div style="clear: both;"></div>   
<#include 'footer.ftl'>
 </body>
</html>