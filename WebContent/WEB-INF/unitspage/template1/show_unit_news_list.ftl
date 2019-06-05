<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${unit.siteTitle?html}</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/units/${unit.themeName?default('theme1')}/index.css" />  
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
 </head>
<body>
<#include "/WEB-INF/unitspage/unit_total_header.ftl">

<div style='height:8px;font-size:0;clear:both;'></div>

<div class='head_nav'>
  <a href='index.py?unitId=${unit.unitId}'>首页</a> &gt; ${pager.itemName!}
 </div>

<div style='padding:20px 0'>
<#if news_list??>
    <ul class='item_ul'>
      <#list news_list as news>
      <#if type == 3>
      	<li><span>[${news.startDate?string("yyyy-MM-dd")} ~ ${news.endDate?string("yyyy-MM-dd")}]</span><a href='${SiteUrl}p/${news.prepareCourseId}/0/'>${news.title}</a></li>
      <#elseif type == 4>
      	<li><a href='${SiteUrl}g/${news.groupName}'>${news.groupTitle}</a></li> 
      <#else>
        <li><span>${news.createDate?string('yyyy-MM-dd')}</span><a href='${UnitRootUrl}py/show_news.py?unitNewsId=${news.unitNewsId}' target='_blank'>${news.title!?html}</a></li>
      </#if> 
      </#list>
    </ul>
  </#if>
  
  <div class='pgr'><#include '/WEB-INF/ftl/pager.ftl' ></div>
</div>
<div style="clear: both;"></div>   
<#include "/WEB-INF/unitspage/unit_footer.ftl">
</body>
</html>