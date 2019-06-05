<!doctype html><html itemscope="itemscope" itemtype="http://schema.org/WebPage">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta itemprop="image" content="${ContextPath}images/favicon.png">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">  
<meta name="publishtime" content="${Util.today()?string("yyyy-MM-dd HH:mm:ss")}" />
<title>错误报告</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css" />
<link rel="stylesheet" href="${SiteThemeUrl}css/index.css" type="text/css" />
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript" charset="utf-8"></script>

</head>
<body>
<!--公用头部 Start-->
<#include '/WEB-INF/ftl2/site_head.ftl'>
<!--公用头部 End-->
<div class="main mt25 clearfix">

<center>
  <div style='padding:20px; text-align:center;'>
    <font style="color: #FF0000; font-size: 26px; font-weight: bold;">不能进行访问</font>
  </div>
  <div class='info_box'>
  <ul>
  <#if actionErrors?? >
    <#list actionErrors as error>
      <li>${error}</li>
    </#list>
  </#if>
  <#-- 显示字段可能的错误 -->
  <#if fieldErrors?? >
  <#attempt>
    <#list fieldErrors?values as errors>
      <#list errors as err>
        <li>${err}</li>
      </#list>
    </#list>
  <#recover>
    <!-- error for fieldErrors -->
  </#attempt>
  </#if>
  </ul>
  
  <div style='padding:4px;'>
  <center>
  <#if actionLinks??>
    <#list actionLinks as link>
      [${link.html}] 
    </#list>
  <#else>
    [<a href='javascript:window.history.back();'>返 回</a>]
  </#if>
  </center>
  </div>
  
  </div>
</center>


</div>
<#include "/WEB-INF/ftl2/footer.ftl">
<script src="${ContextPath}js/new/imgScroll.js"></script>
</body>
</html>