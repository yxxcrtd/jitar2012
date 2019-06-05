<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 标签中心</title>
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script> 
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<div class="secMain border">
    <h3 class="h3Head textIn"><a href='tags.action'>标签中心</a></h3>
    <div class="clearfix">
    <div class="listCont">
        <ul>
        <li class="listContTitle">
            <div class="listContTitleBg">
                <span class="listWr1" style="width:600px">标签名称</span>
                <span class="listWr2">引用次数</span>
                <span class="listWr3">查看次数</span>
            </div>
        </li>   

  <#if tag_list??>
  <#list tag_list as tag>
      <#if tag_index % 2 = 1>
       <li class="liBg">
      <#else> 
      <li>
      </#if>  

    <span class="listWr2" style="width:600px;text-align:left;"><a class='tagLink' href='showTag.action?tagId=${tag.tagId}'>${tag.tagName!?html}</a></span>
    <span class="listWr2">${tag.refCount}</span>
    <span class="listWr3">${tag.viewCount}</span>
    </li>
  </#list>
  </#if>
</ul>
</div></div></div>
<#include '/WEB-INF/ftl2/pager.ftl'>

<#include '/WEB-INF/ftl2/footer.ftl'>
<#include "/WEB-INF/ftl2/common/ie6.ftl">
</body>
</html>