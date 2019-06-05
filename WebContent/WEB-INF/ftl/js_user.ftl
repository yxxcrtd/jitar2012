<#if user_list?? >
document.writeln("<style>");
document.writeln(".news_new_item_ul {margin:0;padding:0;list-style-type:none;}");
document.writeln(".news_new_item_ul li{padding-left:6px;}");
document.writeln("</style>");
document.writeln("<ul class='news_new_item_ul'>");
<#list user_list as u >
document.write("<li><a target='_blank' href='${SiteUrl}go.action?userId=${u.userId}'>${Util.getCountedWords(u.trueName!?js_string,ShowCount)}</a></li>");
</#list>
document.writeln("</ul>");
</#if>