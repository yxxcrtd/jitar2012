<#if group_list?? >
document.writeln("<style>");
document.writeln(".news_new_item_ul {margin:0;padding:0;list-style-type:none;}");
document.writeln(".news_new_item_ul li{padding-left:6px;}");
document.writeln("</style>");
document.writeln("<ul class='news_new_item_ul'>");
<#list group_list as g >
document.write("<li><a target='_blank' href='${SiteUrl}g/${g.groupName}'>${g.groupTitle!?js_string}</a></li>");
</#list>
document.writeln("</ul>");
</#if>