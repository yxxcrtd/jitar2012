<!doctype html><html itemscope="itemscope" itemtype="http://schema.org/WebPage">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /><meta itemprop="image" content="${SiteUrl}images/favicon.png">
  <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">  
  <link rel="icon" href="${SiteUrl}favicon.ico" /><link rel="shortcut icon" href="${SiteUrl}favicon.ico" /><link rel="alternate" type="application/rss+xml" title="最新文章" href="/rss.py?type=article" /><#if SiteConfig ??><meta name="keywords" content="${SiteConfig.site.keyword!}" /></#if><title><#include 'webtitle.ftl' ></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <#if preview_theme_url?? >
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/site/${preview_theme_url}/index.css" />
  <#else>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
  </#if>  
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script type='text/javascript'>
  //<![CDATA[
  var isHide = true;
  function showImg(){
    if(isHide){document.getElementById("_userimg").src="http://www.jitar.com.cn:8080/usermgr3/authimg?"+Date.parse(new Date());}
  }
  //]]>
  </script>
  <#if passportURL?? && passportURL != ""><#include '/zdsoft/zdjs.ftl' ></#if>
</head>
<body>
<noscript>
<h1>本网站需要启用 JavaScript</h1>
<p>当前浏览器不支持 JavaScript 或阻止了脚本。<br/><br/>若要查看您的浏览器是否支持 JavaScript 或允许使用脚本，请查看浏览器联机帮助。</p>
</noscript>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<#include 'mengv1/index/site_index_part_1.ftl' >
<div class='main'>  
   <div style="width:600px;"> 
  <form id='userlogin' method='post' action="${casServerUrl}/redirectlogin" style='padding-top:8px;'>
    <input type="hidden" id="targetService" name="service" value="${siteserverurl}/login/">
    <input type="hidden" name="failpage" value="${siteserverurl}">
    <input type="hidden" name="redUrl" value="${redUrl}">
    
    <div>用户名称：<input placeholder="请输入用户名" class='loginInput' name='username' id="login_username"/></div>
    <div>用户密码：<input placeholder="请输入用密码" class='loginInput' name='password' type='password' /></div>
    <#if "true" == is_show_verifyCode>
        <div>验 证 码：<input placeholder="请输入验证码" class='loginInput' name='vcode' autocomplete="off" maxLength="4" /></div>
        <div style='padding-left:22px;'><img alt='单击刷新' id='_userimg' src='${casServerUrl}/authimg?tmp="+Date.parse(new Date())' width='70' height='20' style="cursor: pointer;" onclick='this.src="${casServerUrl}/authimg?tmp="+Date.parse(new Date())' /></div>
    <#else>
        <input type="hidden" name="vercode" value="123456" />
    </#if>
    <div><input type='submit'  value='登录' class='subbtn' /> <input type='reset' class='subbtn' value='重置' /> </div>                              
    </form>
    </div>
<#include "footer.ftl">
</body>
</html>