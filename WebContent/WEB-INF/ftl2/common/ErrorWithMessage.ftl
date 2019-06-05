<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>错误信息</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/content.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<style>

</style>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<!--公用头部 End-->
<div class="main" id="Main">
    <div class="main mt25" style="background:#fff;padding:20px;text-align:center">
     <ul>
      <#if actionErrors?? >
        <#list actionErrors as error>
          <li>${error!""}</li>
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
  
      <div style='padding:20px;'>
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
</div>

<!--公共尾部 Start-->
<#include '/WEB-INF/ftl2/footer.ftl'>
<!--公共尾部 End-->

<#include "/WEB-INF/ftl2/common/ie6.ftl">
</body>
</html>