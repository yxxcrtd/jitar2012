<!doctype html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>专题</title>
    <#include "/WEB-INF/ftl2/common/favicon.ftl" />
    <link rel="stylesheet" href="${SiteThemeUrl}css/index.css" type="text/css">
    <link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
    <link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
    <link rel="stylesheet" href="${SiteThemeUrl}css/fancybox/jquery.fancybox.css" type="text/css">
    <script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
    <script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
    <script src="${ContextPath}js/new/index.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<div class="secMain mt25 clearfix">
<h2 style='text-align:center;color:#f00'>当前没有任何专题，请<a style="font-size:1em" href='${SiteUrl}manage/admin.py?url=${(SiteUrl + "manage/specialsubject/admin_specialsubject_add.py")?url}'>创建一个专题</a>。</h2>
</div>

<#include '/WEB-INF/ftl2/footer.ftl'>
    <script src="${ContextPath}js/new/imgScroll.js"></script>
    <!--[if IE 6]>
    <script src="${ContextPath}js/new/ie6.js" type="text/javascript"></script>
    <script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder,.secList li,.secVideoPlay');
    </script>
    <![endif]-->
  </body>
</html>